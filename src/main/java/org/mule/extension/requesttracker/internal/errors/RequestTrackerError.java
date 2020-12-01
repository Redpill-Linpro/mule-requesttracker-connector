package org.mule.extension.requesttracker.internal.errors;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
import org.mule.runtime.extension.api.error.MuleErrors;

import java.util.Optional;

public enum RequestTrackerError implements ErrorTypeDefinition<RequestTrackerError> {

    EMPTY_RESPONSE,

    SERVER_ERROR,

    BAD_REQUEST,

    NOT_FOUND,

    OTHER,


    // Connection related errors

    CONNECTIVITY(MuleErrors.CONNECTIVITY),

    INVALID_CREDENTIALS(CONNECTIVITY),

    CONNECTION_TIMEOUT(CONNECTIVITY);

    private ErrorTypeDefinition<? extends Enum<?>> error;

    RequestTrackerError(ErrorTypeDefinition<? extends Enum<?>> error) {
        this.error = error;
    }

    RequestTrackerError() {

    }

    @Override
    public Optional<ErrorTypeDefinition<? extends Enum<?>>> getParent() {
        return Optional.ofNullable(error);
    }
}
