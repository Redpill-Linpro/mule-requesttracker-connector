package org.mule.extension.requesttracker.internal;

import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.Sources;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;
import org.mule.runtime.extension.api.annotation.error.ErrorTypes;

/**
 * Request Tracker connector used to integrate with a Request Tracker REST 1.0 API.
 * <p>
 * This class serves as both extension definition and configuration.
 */
@Extension(name = "RequestTracker")
@Xml(prefix = "requesttracker")
@ConnectionProviders({RequestTrackerConnectionProvider.class})
@Sources(RequestTrackerTicketListener.class)
@Operations({RequestTrackerTicketOperations.class, RequestTrackerUserOperations.class, RequestTrackerQueueOperations.class})
@ErrorTypes(RequestTrackerError.class)
public class RequestTrackerConnector {

}
