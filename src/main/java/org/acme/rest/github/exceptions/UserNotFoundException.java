package org.acme.rest.github.exceptions;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.acme.rest.github.dto.ErrorResponse;

public class UserNotFoundException extends WebApplicationException {

    public UserNotFoundException(String username) {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), "User " + username + " not found"))
                .build());
    }
}