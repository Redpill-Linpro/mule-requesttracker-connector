package org.mule.extension.requesttracker.internal;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.CachedConnectionProvider;

import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.http.api.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class RequestTrackerConnectionProvider implements CachedConnectionProvider<RequestTrackerConnection> {

  private final Logger LOGGER = LoggerFactory.getLogger(RequestTrackerConnectionProvider.class);

  @Parameter
  @Optional (defaultValue = "5000")
  @Placement (tab = "Advanced")
  int connectionTimeout;

  @ParameterGroup(name = "Connection")
  ConnectionSettings configuration;

  @Inject
  private HttpService httpService;

  @Override
  public RequestTrackerConnection connect() throws ConnectionException {
    return new RequestTrackerConnection(httpService, configuration, connectionTimeout);
  }

  @Override
  public void disconnect(RequestTrackerConnection connection) {
    try {
      connection.invalidate();
    } catch (Exception e) {
      LOGGER.error("Error while disconnecting", e);
    }
  }

  @Override
  public ConnectionValidationResult validate(RequestTrackerConnection connection) {
    ConnectionValidationResult result;
    try {
      if (connection.isConnected()) {
        result = ConnectionValidationResult.success();
      } else {
        result = ConnectionValidationResult.failure("Connection failed", new Exception());
      }
    } catch (Exception e) {
      result = ConnectionValidationResult.failure("Connection failed: " + e.getMessage(), e);
    }
    return result;
  }
}
