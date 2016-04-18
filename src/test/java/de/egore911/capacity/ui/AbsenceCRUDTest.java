package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import org.joda.time.LocalDate;

import de.egore911.capacity.ui.dto.Absence;

public class AbsenceCRUDTest extends AbstractCRUDTest<Absence> {

	@Override
	protected Absence createFixture() {
		Absence fixture = new Absence();
		fixture.setStart(LocalDate.now());
		fixture.setEnd(LocalDate.now());
		fixture.setReason(UUID.randomUUID().toString());
		fixture.setEmployeeId(17);
		return fixture;
	}

	@Override
	protected String getPath() {
		return "absence";
	}

	@Override
	protected Class<Absence> getFixtureClass() {
		return Absence.class;
	}

	@Override
	protected void modifyFixture(Absence fixture) {
		fixture.setReason(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(Absence fixture, Absence created) {
		assertThat(created.getStart(), equalTo(fixture.getStart()));
		assertThat(created.getEnd(), equalTo(fixture.getEnd()));
		assertThat(created.getReason(), equalTo(fixture.getReason()));
		assertThat(created.getEmployeeId(), equalTo(fixture.getEmployeeId()));
	}

	@Override
	protected GenericType<List<Absence>> getGenericType() {
		return new GenericType<List<Absence>>() {
		};
	}

}
