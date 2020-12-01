package org.mule.extension.requesttracker.internal;

import org.mule.runtime.api.meta.ExpressionSupport;
import org.mule.runtime.extension.api.annotation.Expression;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.Password;
import org.mule.runtime.extension.api.annotation.param.display.Placement;
import org.mule.runtime.extension.api.annotation.param.display.Summary;
import org.mule.runtime.http.api.HttpConstants;

public class ConnectionSettings {

  @Parameter
  private String username;

  @Parameter
  @Password
  private String password;

  /**
   * Protocol to use for communication. Valid values are HTTP and HTTPS. Default value is HTTP. When using HTTPS the HTTP
   * communication is going to be secured using TLS / SSL. If HTTPS was configured as protocol then the user can customize the
   * tls/ssl configuration by defining the tls:context child element of this listener-config. If not tls:context is defined then
   * the default JVM certificates are going to be used to establish communication.
   */
  @Parameter
  @Optional(defaultValue = "HTTP")
  @Expression(ExpressionSupport.NOT_SUPPORTED)
  @Summary("Protocol to use for communication. Valid values are HTTP and HTTPS")
  @Placement(order = 1)
  private HttpConstants.Protocol protocol;

  /**
   * Host where the requests will be sent.
   */
  @Parameter
  @Placement(order = 2)
  private String host;

  /**
   * Port where the requests will be sent. If the protocol attribute is HTTP (default) then the default value is 80, if the
   * protocol attribute is HTTPS then the default value is 443.
   */
  @Parameter
  @Optional
  @Placement(order = 3)
  private Integer port;

  public HttpConstants.Protocol getProtocol() {
    return protocol;
  }

  public String getHost() {
    return host;
  }

  public Integer getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getOrigin() {
    return protocol + "://" + host + (port > 0 ? ":" + port : "");
  }
}
