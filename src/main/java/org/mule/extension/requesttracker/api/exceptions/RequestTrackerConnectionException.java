package org.mule.extension.requesttracker.api.exceptions;

import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.exception.ModuleException;

public class RequestTrackerConnectionException extends ConnectionException {

        /**
         * Creates a new instance with the specified detail {@code message}, {@code cause} and {@code error}
         *
         * @param message the detail message
         * @param cause   the exception's cause
         * @param error   the correspondent {@link RequestTrackerError} with the created exception
         */
        public RequestTrackerConnectionException(String message, Throwable cause, RequestTrackerError error) {
            super(message, new ModuleException(error, cause));
        }
}
