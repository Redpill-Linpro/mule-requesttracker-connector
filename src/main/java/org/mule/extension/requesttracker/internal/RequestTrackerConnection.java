package org.mule.extension.requesttracker.internal;

import org.apache.commons.io.IOUtils;
import org.mule.extension.requesttracker.internal.models.StringResponse;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerResponseConverter;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.util.MultiMap;
import org.mule.runtime.http.api.HttpConstants;
import org.mule.runtime.http.api.HttpService;
import org.mule.runtime.http.api.client.HttpClient;
import org.mule.runtime.http.api.client.HttpClientConfiguration;
import org.mule.runtime.http.api.domain.entity.multipart.HttpPart;
import org.mule.runtime.http.api.domain.entity.multipart.MultipartHttpEntity;
import org.mule.runtime.http.api.domain.message.request.HttpRequest;
import org.mule.runtime.http.api.domain.message.request.HttpRequestBuilder;
import org.mule.runtime.http.api.domain.message.response.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public final class RequestTrackerConnection {

  private HttpClient httpClient;
  private final ConnectionSettings configuration;
  private final int connectionTimeout;

  public RequestTrackerConnection(HttpService httpService, ConnectionSettings configuration, int connectionTimeout) {
    this.configuration = configuration;
    this.connectionTimeout = connectionTimeout;
    initHttpClient(httpService);
  }

  private void initHttpClient(HttpService httpService) {
    HttpClientConfiguration.Builder builder = new HttpClientConfiguration.Builder();
    builder.setName("RequestTrackerRESTAPI");
    httpClient = httpService.getClientFactory().create(builder.build());

    httpClient.start();
  }

  public void invalidate() {
    httpClient.stop();
  }

  public boolean isConnected() throws IOException, TimeoutException, ConnectionException {
    MultiMap<String, String> params = new MultiMap<>();
    params.put("user", configuration.getUsername());
    params.put("pass", configuration.getPassword());


    HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();
    HttpRequest request = httpRequestBuilder
            .method(HttpConstants.Method.GET)
            .uri(buildPath("user"))
            .queryParams(params)
            .build();

    HttpResponse response = httpClient.send(request,connectionTimeout,false, null);
    if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
      StringResponse stringResponse = httpResponseToStringResponse(response);
      int rtResponseCode = RequestTrackerResponseConverter.checkStatus(RequestTrackerResponseConverter.getLines(stringResponse.getBody()));
      if (rtResponseCode == 200) {
        return true;
      } else {
        throw new ConnectionException("Request Tracker not pleased with request. Error code: " + rtResponseCode + " - " + response);
      }
    } else {
      throw new ConnectionException("Error connecting to server: Error code: " + response.getStatusCode() + " - " + response);
    }
  }

  StringResponse get(String path) throws IOException, TimeoutException {
    return get(path, null);
  }
  StringResponse get( String path,  Map<String, String> params) throws IOException, TimeoutException {
    MultiMap<String, String> qParams = new MultiMap<>();
    if (params != null) {
      qParams.putAll(params);
    }
    qParams.put("user", configuration.getUsername());
    qParams.put("pass", configuration.getPassword());

    HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();
    HttpRequest request = httpRequestBuilder
            .method(HttpConstants.Method.GET)
            .uri(buildPath(path))
            .queryParams(qParams)
            .build();

    HttpResponse response = httpClient.send(request, connectionTimeout, false, null);
    return httpResponseToStringResponse(response);
  }

  StringResponse post(String path, String content) throws IOException, TimeoutException {
      return post(path, content, null);
  }
  StringResponse post(String path, String content, Map<String, TypedValue<InputStream>> attachments) throws IOException, TimeoutException {
    MultiMap<String, String> qParams = new MultiMap<>();
    qParams.put("user", configuration.getUsername());
    qParams.put("pass", configuration.getPassword());

    HttpRequestBuilder httpRequestBuilder = HttpRequest.builder();
    HttpRequestBuilder builder = httpRequestBuilder
            .method(HttpConstants.Method.POST)
            .uri(buildPath(path))
            .queryParams(qParams);

    if (content != null) {
      byte[] bytes = content.getBytes(Charset.defaultCharset());
      List<HttpPart> parts = new ArrayList<>();
      parts.add(new HttpPart("content", bytes, "text/plain", bytes.length));
      if (attachments != null && !attachments.isEmpty()) {
        int idx = 1;
        for (Map.Entry<String, TypedValue<InputStream>> attachment : attachments.entrySet()) {
          byte[] file = IOUtils.toByteArray(attachment.getValue().getValue());
          parts.add(new HttpPart("attachment_" + (idx++),
                  attachment.getKey(),
                  file,
                  attachment.getValue().getDataType().getMediaType().toRfcString(),
                  (int)attachment.getValue().getByteLength().orElse(file.length)));
        }
      }
      builder.entity(new MultipartHttpEntity(parts));
    }

    HttpResponse response = httpClient.send(builder.build(), connectionTimeout, false, null);
    return httpResponseToStringResponse(response);
  }

  private StringResponse httpResponseToStringResponse( HttpResponse response) {
    Map<String, String> headers = new HashMap<>();
    for (Map.Entry<String, String> header : response.getHeaders().entrySet()) {
      headers.put(header.getKey(), header.getValue());
    }
    return new StringResponse(response.getStatusCode(), headers, response.getEntity().getContent());
  }

  private String buildPath(String path) {
    return configuration.getOrigin() + "/REST/1.0/" + path;
  }
}
