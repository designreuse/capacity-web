package de.egore911.capacity.ui.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 4390417386853213093L;

	public NotFoundException(String message) {
		super(Response.status(Response.Status.NOT_FOUND).entity(message).type(MediaType.TEXT_PLAIN).build());
	}

}
