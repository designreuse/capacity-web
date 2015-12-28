package de.egore911.capacity.ui.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BadStateException  extends WebApplicationException {

	private static final long serialVersionUID = -6356020778089345408L;

	public BadStateException(String message) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
