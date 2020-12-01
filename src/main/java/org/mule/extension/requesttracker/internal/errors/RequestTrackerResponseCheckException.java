package org.mule.extension.requesttracker.internal.errors;

import org.apache.commons.lang3.StringUtils;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.runtime.extension.api.exception.ModuleException;

import java.util.List;
import static org.mule.runtime.api.i18n.I18nMessageFactory.createStaticMessage;

public class RequestTrackerResponseCheckException extends ModuleException {
    private final int statusCode;
    public RequestTrackerResponseCheckException(String message) {
        super(createStaticMessage(message), RequestTrackerError.EMPTY_RESPONSE);
        this.statusCode = 0;
    }
    public RequestTrackerResponseCheckException(List<String> lines, int statusCode) {
        super(createStaticMessage(StringUtils.join(lines, System.lineSeparator())), errorTypeByStatusCode(statusCode));
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    private static RequestTrackerError errorTypeByStatusCode(int statusCode) {
        if (statusCode == 400 || statusCode == -1) {
            return RequestTrackerError.BAD_REQUEST;
        }
        if (statusCode == 401) {
            return RequestTrackerError.INVALID_CREDENTIALS;
        }
        if (statusCode == 404) {
            return RequestTrackerError.NOT_FOUND;
        }
        if (statusCode == 408) {
            return RequestTrackerError.CONNECTION_TIMEOUT;
        }
        if (statusCode > 500) {
            return RequestTrackerError.SERVER_ERROR;
        }
        return RequestTrackerError.OTHER;
    }
}
