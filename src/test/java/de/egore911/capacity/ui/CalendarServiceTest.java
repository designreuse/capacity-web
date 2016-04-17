package de.egore911.capacity.ui;

import org.junit.Test;

import de.egore911.capacity.ui.rest.CalendarService;
import de.egore911.capacity.ui.rest.JerseyConfig;

public class CalendarServiceTest extends AbstractUiTest {

	@Override
	protected JerseyConfig configure() {
		JerseyConfig application = super.configure();
		application.register(CalendarService.class);
		return application;
	}

	@Test
	public void test() {
		String hello = target("calendar/holidays.ics").request().get(String.class);
		System.err.println(hello);
	}

}
