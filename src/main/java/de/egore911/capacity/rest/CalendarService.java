package de.egore911.capacity.rest;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDate;
import org.joda.time.format.ISODateTimeFormat;

import de.egore911.capacity.ui.dto.Event;

@Path("calendar")
public class CalendarService {

	@GET
	@Path("events/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Event> getEvents(@PathParam("id") String id,
			@QueryParam("start") LocalDate start,
			@QueryParam("end") LocalDate end) {
		if ("holidays".equals(id)) {
			Event event = new Event();
			event.setTitle("Test holiday");
			event.setStart(ISODateTimeFormat.date().print(LocalDate.now()));
			return Collections.singletonList(event);
		} else {
			Event event = new Event();
			event.setTitle("Test employee");
			event.setStart(ISODateTimeFormat.date().print(LocalDate.now().plusDays(1)));
			return Collections.singletonList(event);
		}
	}

}
