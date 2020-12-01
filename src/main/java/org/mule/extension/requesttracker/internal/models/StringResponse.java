package org.mule.extension.requesttracker.internal.models;

import org.mule.runtime.core.api.util.IOUtils;

import java.io.InputStream;
import java.util.Map;

public class StringResponse {

    private int statusCode;
    private Map<String, String> headers;
    private String body;

    public StringResponse(int statusCode, Map<String, String> headers, InputStream body) {
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = IOUtils.toString(body, "UTF-8");
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
