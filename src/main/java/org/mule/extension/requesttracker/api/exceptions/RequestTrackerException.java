package org.mule.extension.requesttracker.api.exceptions;

import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.runtime.extension.api.exception.ModuleException;

public class RequestTrackerException extends ModuleException {

    public RequestTrackerException(String message, Throwable cause) {
        super(message, RequestTrackerError.OTHER, cause);
    }
}
