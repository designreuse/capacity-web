package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDate;
import org.junit.Test;

import de.egore911.capacity.ui.dto.Absence;

public class AbsenceCRUDTest extends AbstractUiTest {

	@Test
	public void testCRUD() {

		// Create an absence fixture
		Absence absence = new Absence();
		absence.setStart(LocalDate.now());
		absence.setEnd(LocalDate.now());
		absence.setReason(UUID.randomUUID().toString());
		absence.setEmployeeId(17);

		// when: we create an absence
		Entity<Absence> entity = Entity.entity(absence, MediaType.APPLICATION_JSON);
		Integer id = target("absence").request().post(entity, Integer.class);

		// then: we should get the ID back
		assertThat(id, notNullValue());
		Absence created = target("absence/" + id).request().get(Absence.class);
		assertThat(created.getStart(), equalTo(absence.getStart()));
		assertThat(created.getEnd(), equalTo(absence.getEnd()));
		assertThat(created.getReason(), equalTo(absence.getReason()));
		assertThat(created.getEmployeeId(), equalTo(absence.getEmployeeId()));

		// Modify our fixture
		absence.setId(id);
		absence.setReason(UUID.randomUUID().toString());
		assertThat(created.getReason(), not(equalTo(absence.getReason())));

		// when: we update the absence
		entity = Entity.entity(absence, MediaType.APPLICATION_JSON);
		target("absence/" + id).request().put(entity, Integer.class);

		// then: we should have an updated absence
		Absence updated = target("absence/" + id).request().get(Absence.class);
		assertThat(updated.getStart(), equalTo(absence.getStart()));
		assertThat(updated.getEnd(), equalTo(absence.getEnd()));
		assertThat(updated.getReason(), equalTo(absence.getReason()));
		assertThat(updated.getEmployeeId(), equalTo(absence.getEmployeeId()));

		// when: we delete the absence
		target("absence/" + id).request().delete();

		// then: we can no longer load the absence
		Absence deleted = target("absence/" + id).request().get(Absence.class);
		assertThat(deleted, nullValue());
	}

}
