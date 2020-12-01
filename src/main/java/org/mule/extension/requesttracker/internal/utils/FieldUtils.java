package org.mule.extension.requesttracker.internal.utils;

import org.apache.commons.lang3.StringUtils;
import org.mule.extension.requesttracker.api.models.request.Fields;
import org.mule.extension.requesttracker.internal.annotations.RTName;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FieldUtils {
    private final static Logger LOGGER = LoggerFactory.getLogger(FieldUtils.class);

    public static String asParam(Fields fieldsObj, Class<?> clazz) {
        List<String> fields = new ArrayList<>();
        fields.add("id");
        for (Field field : clazz.getDeclaredFields()) {
            PropertyDescriptor pd;
            try {
                if (field.isAnnotationPresent(Parameter.class)) {
                    pd = new PropertyDescriptor(field.getName(), clazz);
                    if (field.getType().equals(boolean.class) && (boolean)pd.getReadMethod().invoke(fieldsObj)) {
                        if (field.isAnnotationPresent(RTName.class)) {
                            fields.add(field.getAnnotation(RTName.class).value());
                        } else {
                            fields.add(StringUtils.capitalize(field.getName()));
                        }
                    } else if (field.getType().equals(List.class)) {
                        List<String> _customFields = (List<String>) pd.getReadMethod().invoke(fieldsObj);
                        if (_customFields != null) {
                            for (String customField : _customFields) {
                                fields.add("CF.{" + customField + "}");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Could not get value from field " + field.getName()+ " for class " + clazz.getName(), e);
            }
        }
        return StringUtils.join(fields, ",");
    }

}
