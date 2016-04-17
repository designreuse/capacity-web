package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import de.egore911.capacity.ui.rest.CalendarService;
import de.egore911.capacity.ui.rest.JerseyConfig;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class CalendarServiceTest extends AbstractUiTest {

	@Override
	protected JerseyConfig configure() {
		JerseyConfig application = super.configure();
		application.register(CalendarService.class);
		return application;
	}

	@Test
	public void testHolidays() throws IOException, ParserException {
		String ics = target("calendar/holidays.ics").request().get(String.class);

		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(new ByteArrayInputStream(ics.getBytes(StandardCharsets.UTF_8)));
		ComponentList<CalendarComponent> components = calendar.getComponents("VEVENT");

		assertThat("No holidays defined in import.sql, should be empty", components, empty());
	}

	@Test
	public void test() throws IOException, ParserException {
		String ics = target("calendar/absences.ics").request().get(String.class);

		CalendarBuilder builder = new CalendarBuilder();
		Calendar calendar = builder.build(new ByteArrayInputStream(ics.getBytes(StandardCharsets.UTF_8)));
		ComponentList<CalendarComponent> components = calendar.getComponents("VEVENT");

		assertThat("One ansence defined in import.sql, 'Whole February: User 2015 complete'", components, hasSize(1));

		// Check properties
		CalendarComponent component = components.get(0);
		matchProperty(component, "SUMMARY", "Whole February: User 2015 complete");
		matchProperty(component, "DTSTART", "20150131");
		matchProperty(component, "DTEND", "20150227");
	}

	private static void matchProperty(CalendarComponent component, String propertyName, String propertyValue) {
		Property property = component.getProperty(propertyName);
		assertThat(property, notNullValue());
		assertThat(property.getValue(), equalTo(propertyValue));
	}

}
