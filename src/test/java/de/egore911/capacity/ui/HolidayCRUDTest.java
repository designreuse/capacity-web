package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.UUID;

import org.joda.time.LocalDate;

import de.egore911.capacity.ui.dto.Holiday;

public class HolidayCRUDTest  extends AbstraceCRUDTest<Holiday> {

	@Override
	protected Holiday createFixture() {
		Holiday fixture = new Holiday();
		fixture.setDate(LocalDate.now());
		fixture.setName(UUID.randomUUID().toString());
		return fixture;
	}

	@Override
	protected String getPath() {
		return "holiday";
	}

	@Override
	protected Class<Holiday> getFixtureClass() {
		return Holiday.class;
	}

	@Override
	protected void modifyFixture(Holiday fixture) {
		fixture.setName(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(Holiday fixture, Holiday created) {
		assertThat(created.getDate(), equalTo(fixture.getDate()));
		assertThat(created.getName(), equalTo(fixture.getName()));
	}

}
