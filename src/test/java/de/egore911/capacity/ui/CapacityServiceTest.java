package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import de.egore911.capacity.ui.dto.Employee;
import de.egore911.capacity.ui.dto.WorkingHoursPerEmployee;
import de.egore911.capacity.ui.dto.WorkingHoursRequest;

public class CapacityServiceTest extends AbstractUiTest {

	@Test
	public void testEpisodeOne() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(1);
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Two employees are assigned to episode 1", horkingHoursPerEmployee, hasSize(2));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(1)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
	}

	@Test
	public void testEpisodeTwo() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(2);
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Four employees are assigned to episode 2, one does not have a valid contract", horkingHoursPerEmployee, hasSize(4));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(1)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(3)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(4)))));
	}

}
