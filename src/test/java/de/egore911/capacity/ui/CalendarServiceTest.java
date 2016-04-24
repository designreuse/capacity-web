package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;

import org.junit.Test;

import de.egore911.capacity.ui.dto.Event;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class CalendarServiceTest extends AbstractUiTest {

	@Test
	public void testHolidays() throws IOException, ParserException {
		List<Event> events = target("calendar/events/holidays").request().get(new GenericType<List<Event>>() {
		});

		assertThat(events, hasSize(4));
	}

	@Test
	public void testHolidaysIcs() throws IOException, ParserException {
		String ics = target("calendar/holidays.ics").request().get(String.class);

		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(new ByteArrayInputStream(ics.getBytes(StandardCharsets.UTF_8)));
		ComponentList<CalendarComponent> components = calendar.getComponents("VEVENT");

		assertThat(components, hasSize(4));
	}

	@Test
	public void testAbsences() throws IOException, ParserException {
		List<Event> events = target("calendar/events/absences").request().get(new GenericType<List<Event>>() {
		});

		assertThat(events, hasSize(1));
	}

	@Test
	public void testAbsencesIcs() throws IOException, ParserException {
		String ics = target("calendar/absences.ics").request().get(String.class);

		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(new ByteArrayInputStream(ics.getBytes(StandardCharsets.UTF_8)));
		ComponentList<CalendarComponent> components = calendar.getComponents("VEVENT");

		assertThat("One ansence defined in import.sql, 'Whole February: User 2015 complete'", components, hasSize(1));

		// Check properties
		CalendarComponent component = components.get(0);
		matchProperty(component, "SUMMARY", "Whole February: User 2015 complete");
		matchProperty(component, "DTSTART", "20150201");
		matchProperty(component, "DTEND", "20150228");
	}

	private static void matchProperty(CalendarComponent component, String propertyName, String propertyValue) {
		Property property = component.getProperty(propertyName);
		assertThat(property, notNullValue());
		assertThat(property.getValue(), equalTo(propertyValue));
	}

	@Test(expected = NotFoundException.class)
	public void testInvalid() throws IOException, ParserException {
		target("calendar/invalid.ics").request().get(String.class);
	}
}
