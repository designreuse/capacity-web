package de.egore911.capacity.ui.rest;

import java.util.ArrayList;
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

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.capacity.persistence.selector.HolidaySelector;
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
			List<HolidayEntity> holidays = new HolidaySelector()
				.withStartInclusive(start)
				.withEndInclusive(end)
				.findAll();
			List<Event> result = new ArrayList<>(holidays.size());
			for (HolidayEntity holiday : holidays) {
				Event event = new Event();
				event.setTitle(holiday.getName());
				event.setStart(holiday.getDate());
				result.add(event);
			}
			return result;
		} else {
			List<AbsenceEntity> absences = new AbsenceSelector()
				.withStartInclusive(start)
				.withEndInclusive(end)
				.findAll();
			List<Event> result = new ArrayList<>(absences.size());
			for (AbsenceEntity absence : absences) {
				Event event = new Event();
				event.setTitle(absence.getEmployee().getName() + ": " + absence.getReason());
				event.setStart(absence.getStart());
				event.setEnd(absence.getEnd());
				event.setColor(absence.getEmployee().getColor());
				result.add(event);
			}
			return result;
		}
	}

}
