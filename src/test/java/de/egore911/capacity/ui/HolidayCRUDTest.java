package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import org.joda.time.LocalDate;

import de.egore911.capacity.ui.dto.Holiday;

public class HolidayCRUDTest  extends AbstractCRUDTest<Holiday> {

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

	@Override
	protected GenericType<List<Holiday>> getGenericType() {
		return new GenericType<List<Holiday>>() {
		};
	}

}
