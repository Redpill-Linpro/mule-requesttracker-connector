package org.mule.extension.requesttracker.internal;

import org.mule.extension.requesttracker.api.exceptions.RequestTrackerConnectionException;
import org.mule.extension.requesttracker.api.models.request.CreateUser;
import org.mule.extension.requesttracker.api.models.request.SearchRequest;
import org.mule.extension.requesttracker.api.models.request.UpdateUser;
import org.mule.extension.requesttracker.api.models.request.UserFields;
import org.mule.extension.requesttracker.api.models.response.UpdateItemResponse;
import org.mule.extension.requesttracker.api.models.response.User;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerError;
import org.mule.extension.requesttracker.internal.errors.RequestTrackerErrorTypeProvider;
import org.mule.extension.requesttracker.internal.utils.FieldUtils;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerRequestConverter;
import org.mule.extension.requesttracker.internal.utils.RequestTrackerResponseConverter;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.mule.runtime.extension.api.annotation.param.display.Summary;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RequestTrackerUserOperations extends RequestTrackerBaseOperations {
    @Summary("Search for users")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public List<User> searchUsers(@Connection RequestTrackerConnection connection, @ParameterGroup(name = "Search") SearchRequest searchRequest, @ParameterGroup(name = "Fields") UserFields fields) throws RequestTrackerConnectionException {
        try {
            Map<String, String> params = searchRequest.asParams();
            params.put("fields", FieldUtils.asParam(fields, UserFields.class));
            return get(connection, "search/user", params, User.class);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Searching for Users: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Searching for Users", e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Get user by user ID or user login")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public User getUser(@Connection RequestTrackerConnection connection, @Summary("you can use user login instead of user ID") String userId) throws RequestTrackerConnectionException {
        try {
            List<User> users = get(connection, "user/" + userId, User.class);
            return users.isEmpty() ? null : users.get(0);
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while getting User " + userId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while getting User " + userId, e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Create new user")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateItemResponse createUser(@Connection RequestTrackerConnection connection, CreateUser newUser) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertCreateUserResponse(connection.post("user/new", RequestTrackerRequestConverter.convertToRequestTrackerBody(newUser, CreateUser.class, true)).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Creating User: " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Creating User", e, RequestTrackerError.CONNECTIVITY);
        }
    }

    @Summary("Update existing user")
    @Throws(RequestTrackerErrorTypeProvider.class)
    public UpdateItemResponse updateUser(@Connection RequestTrackerConnection connection, String userId, UpdateUser userUpdates) throws RequestTrackerConnectionException {
        try {
            return RequestTrackerResponseConverter.convertEditUserResponse(connection.post("user/" + userId + "/edit", RequestTrackerRequestConverter.convertToRequestTrackerBody(userUpdates, UpdateUser.class,true)).getBody());
        } catch (IOException e) {
            throw new RequestTrackerConnectionException("Error while Updating User " + userId + ": " + e.getMessage(), e, RequestTrackerError.CONNECTIVITY);
        } catch (TimeoutException e) {
            throw new RequestTrackerConnectionException("Timeout while Updating User " + userId, e, RequestTrackerError.CONNECTIVITY);
        }
    }
}
