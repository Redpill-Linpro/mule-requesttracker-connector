package org.mule.extension.requesttracker.internal;

import org.mule.extension.requesttracker.internal.errors.RequestTrackerResponseCheckException;
import org.mule.extension.requesttracker.api.models.request.AttachmentsGroup;
import org.mule.extension.requesttracker.internal.models.RequestTrackerObject;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerResponseConverter;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.transformation.TransformationService;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static org.mule.runtime.api.metadata.DataType.INPUT_STREAM;
import static org.mule.runtime.api.metadata.DataType.fromObject;

public abstract class RequestTrackerBaseOperations {

    @Inject
    private TransformationService transformationService;


    protected <T extends RequestTrackerObject> List<T> get(RequestTrackerConnection connection, String path, Class<T> tClass) throws IOException, TimeoutException, RequestTrackerResponseCheckException {
        return RequestTrackerResponseConverter.convertResponse(connection.get(path).getBody(), tClass);
    }
    protected <T extends RequestTrackerObject> List<T> get(RequestTrackerConnection connection, String path, Map<String, String> params, Class<T> tClass) throws IOException, TimeoutException, RequestTrackerResponseCheckException {
        return RequestTrackerResponseConverter.convertResponse(connection.get(path, params).getBody(), tClass);
    }

    /**
     * A utility method that ensures that all attachments are of type {@link InputStream},
     * otherwise they will be transformed.
     *
     * @param attachments to ensure and transform.
     * @return a new {@link Map} of attachments represented in {@link InputStream}
     */
    protected Map<String, TypedValue<InputStream>> transformAttachments(AttachmentsGroup attachments) {
        Map<String, TypedValue<InputStream>> newAttachments = new HashMap<>();
        for (Map.Entry<String, TypedValue<InputStream>> attachment : attachments.getAttachments().entrySet()) {
            newAttachments.put(attachment.getKey(), getTransformTypedValue(attachment.getValue()));
        }
        return newAttachments;
    }

    private TypedValue<InputStream> getTransformTypedValue(TypedValue typedValue) {

        Object value = typedValue.getValue();
        if (value instanceof InputStream) {
            return typedValue;
        }
        InputStream result = (InputStream) transformationService.transform(value, fromObject(value), INPUT_STREAM);
        return new TypedValue<>(result, DataType.builder().type(result.getClass()).mediaType(typedValue.getDataType().getMediaType())
                .build());
    }
}
