package org.mule.extension.requesttracker.internal;

import org.mule.extension.requesttracker.api.exceptions.RequestTrackerConnectionException;
import org.mule.extension.requesttracker.api.exceptions.RequestTrackerException;
import org.mule.extension.requesttracker.api.models.request.QueueFields;
import org.mule.extension.requesttracker.api.models.request.SearchRequest;
import org.mule.extension.requesttracker.api.models.response.Queue;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerErrorTypeProvider;
import org.mule.extension.requesttracker.internal.utils.FieldUtils;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class RequestTrackerQueueOperations extends RequestTrackerBaseOperations {

  @Summary("Search for Queues")
  @Throws(RequestTrackerErrorTypeProvider.class)
  public List<Queue> searchQueues(@Connection RequestTrackerConnection connection,
                                  @ParameterGroup(name = "Search") SearchRequest searchRequest,
                                  @ParameterGroup(name = "Fields") QueueFields fields) throws RequestTrackerConnectionException {
    Map<String, String> params = searchRequest.asParams();
    params.put("fields", FieldUtils.asParam(fields, QueueFields.class));
    try {
      return get(connection, "search/queue", params, Queue.class);
    } catch (IOException e) {
      throw new RequestTrackerConnectionException("Error while getting Queue Search: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
    } catch (TimeoutException e) {
      throw new RequestTrackerConnectionException("Timeout while getting Queue Search", e, RequestTrackerError.CONNECTIVITY);
    } catch (Exception e) {
      throw new RequestTrackerException("Unable to search queues: " + e.getMessage(), e);
    }
  }

  @Summary("Get Queue by ID")
  @Throws(RequestTrackerErrorTypeProvider.class)
  public Queue getQueue(@Connection RequestTrackerConnection connection, String queueId) throws RequestTrackerConnectionException {
    try {
      List<Queue> queues = get(connection, "queue/" + queueId + "/show", Queue.class);
      return queues.isEmpty() ? null : queues.get(0);
    } catch (IOException e) {
      throw new RequestTrackerConnectionException("Error while getting Queue " + queueId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
    } catch (TimeoutException e) {
      throw new RequestTrackerConnectionException("Timeout while getting Queue " + queueId, e, RequestTrackerError.CONNECTIVITY);
    }
  }
}
