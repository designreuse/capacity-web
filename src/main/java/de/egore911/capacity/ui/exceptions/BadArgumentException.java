package de.egore911.capacity.ui.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BadArgumentException extends WebApplicationException {

	private static final long serialVersionUID = -115985314007602297L;

	public BadArgumentException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
