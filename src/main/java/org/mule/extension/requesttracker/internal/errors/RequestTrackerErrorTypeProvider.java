package org.mule.extension.requesttracker.internal.errors;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

import java.util.HashSet;
import java.util.Set;

/**
 * Errors that can be thrown in the operations.
 *
 * @since 1.0
 */
public class RequestTrackerErrorTypeProvider implements ErrorTypeProvider {

    @Override
    public Set<ErrorTypeDefinition> getErrorTypes() {
        Set<ErrorTypeDefinition> errorTypes = new HashSet<>();
        errorTypes.add(RequestTrackerError.EMPTY_RESPONSE);
        errorTypes.add(RequestTrackerError.BAD_REQUEST);
        errorTypes.add(RequestTrackerError.NOT_FOUND);
        errorTypes.add(RequestTrackerError.CONNECTION_TIMEOUT);
        errorTypes.add(RequestTrackerError.CONNECTIVITY);
        errorTypes.add(RequestTrackerError.INVALID_CREDENTIALS);
        errorTypes.add(RequestTrackerError.SERVER_ERROR);
        errorTypes.add(RequestTrackerError.OTHER);
        return errorTypes;
    }
}

