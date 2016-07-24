package de.egore911.capacity.ui.rest;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.model.IntegerDbObject;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.capacity.persistence.selector.HolidaySelector;
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
public class CalendarService extends AbstractService {

	@Context
	private ServletContext servletContext;

	private Uid generateUid(IntegerDbObject entity) {
		String b = entity.getClass().getSimpleName() + '-' + entity.getId();
		return new Uid(b);
	}

	@GET
	@Path("{id}.ics")
	@Produces("text/calendar")
	public Response getIcal(@PathParam("id") String id) throws ParseException {
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
				VEvent event = new VEvent(toIcalDate(holiday.getDate()), holiday.getName());
				event.getProperties().add(generateUid(holiday));
				event.getProperties().add(toIcalDate(holiday.getCreated()));
				calendar.getComponents().add(event);
			}
		} else if ("absences".equals(id)) {
			List<AbsenceEntity> absences = new AbsenceSelector().findAll();
			for (AbsenceEntity absence : absences) {
				VEvent event = new VEvent(toIcalDate(absence.getStart()), toIcalDate(absence.getEnd()),
						absence.getReason() + ": " + absence.getEmployee().getName());
				event.getProperties().add(generateUid(absence));
				event.getProperties().add(toIcalDate(absence.getCreated()));
				calendar.getComponents().add(event);
			}
		} else {
			throw new NotFoundException("Calendar with id " + id + " not found");
		}

		return Response.ok(calendar.toString()).build();
	}

	private Date toIcalDate(LocalDate date) {
		return new Date(Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
	}

	private DtStamp toIcalDate(LocalDateTime dateTime) {
		return new DtStamp(new DateTime(Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())));
	}

}
