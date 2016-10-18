package de.egore911.capacity.ui.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotAuthorizedException extends WebApplicationException {

	private static final long serialVersionUID = -2414323686774901121L;

	public NotAuthorizedException() {
		super(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.TEXT_PLAIN).build());
	}

	public NotAuthorizedException(String message) {
		super(Response.status(Response.Status.UNAUTHORIZED).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
