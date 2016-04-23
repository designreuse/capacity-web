package de.egore911.capacity.ui.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.model.IntegerDbObject;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.capacity.persistence.selector.HolidaySelector;
import de.egore911.capacity.ui.dto.Event;
import de.egore911.capacity.util.VersionExtractor;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Date;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.DtStamp;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;

@Path("calendar")
public class CalendarService {

	@Context
	private ServletContext servletContext;

	private Uid generateUid(IntegerDbObject entity) {
		final StringBuilder b = new StringBuilder();
		b.append(entity.getClass().getSimpleName());
		b.append('-');
		b.append(entity.getId());
		return new Uid(b.toString());
	}

	@GET
	@Path("{id}.ics")
	@Produces("text/calendar")
	public Response getIcal(@PathParam("id") String id) {
		Calendar calendar = new Calendar();
		calendar.getProperties()
				.add(new ProdId("-//Christoph Brill//capacity "
						+ VersionExtractor.getMavenVersion(servletContext, "de.egore911.capacity", "capacity-web")
						+ "//EN"));
		calendar.getProperties().add(Version.VERSION_2_0);
		calendar.getProperties().add(CalScale.GREGORIAN);
		if ("holidays".equals(id)) {
			List<HolidayEntity> holidays = new HolidaySelector().findAll();
			for (HolidayEntity holiday : holidays) {
				VEvent event = new VEvent(new Date(holiday.getDate().toDate()), holiday.getName());
				event.getProperties().add(generateUid(holiday));
				event.getProperties().add(new DtStamp(new DateTime(holiday.getCreated().toDate())));
				calendar.getComponents().add(event);
			}
		} else if ("absences".equals(id)) {
			List<AbsenceEntity> absences = new AbsenceSelector().findAll();
			for (AbsenceEntity absence : absences) {
				VEvent event = new VEvent(new Date(absence.getStart().toDate()), new Date(absence.getEnd().toDate()),
						absence.getReason() + ": " + absence.getEmployee().getName());
				event.getProperties().add(generateUid(absence));
				event.getProperties().add(new DtStamp(new DateTime(absence.getCreated().toDate())));
				calendar.getComponents().add(event);
			}
		} else {
			throw new NotFoundException("Calendar with id " + id + " not found");
		}

		return Response.ok(calendar.toString()).build();
	}

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
		} else if ("absences".equals(id)) {
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
		} else {
			throw new NotFoundException("Calendar with id " + id + " not found");
		}
	}

}
