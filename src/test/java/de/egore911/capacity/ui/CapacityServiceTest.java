package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.hamcrest.Matchers;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.egore911.capacity.ui.dto.Ability;
import de.egore911.capacity.ui.dto.Employee;
import de.egore911.capacity.ui.dto.WorkingHoursPerEmployee;
import de.egore911.capacity.ui.dto.WorkingHoursRequest;

public class CapacityServiceTest extends AbstractUiTest {

	@Test
	public void testEpisodeOne_withAbilities_And() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(1);
		request.setFilter(Collections.singletonList(Arrays.asList(new Ability("Working"), new Ability("Relaxing"))));
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("One employees assigned to episode 1 having both Working and Relaxing abilities", horkingHoursPerEmployee, hasSize(1));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
	}

	@Test
	public void testEpisodeOne_withAbilities_Or() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(1);
		request.setFilter(Arrays.asList(Collections.singletonList(new Ability("Working")), Collections.singletonList(new Ability("Relaxing"))));
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Three employees are assigned to episode 1, having either Working or Relaxing as ability", horkingHoursPerEmployee, hasSize(3));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(1)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(3)))));
	}

	@Test
	public void testEpisodeOne_withAbilities_OnlyOne() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(1);
		request.setFilter(Collections.singletonList(Collections.singletonList(new Ability("Working"))));
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Two employees are assigned to episode 1, having either Working ability", horkingHoursPerEmployee, hasSize(2));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(1)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
	}

	@Test
	public void testEpisodeOne() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(1);
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Two employees are assigned to episode 1", horkingHoursPerEmployee, hasSize(3));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(1)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(3)))));
	}

	@Test
	public void testEpisodeTwo() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(2);
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

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

	@Test(expected = BadRequestException.class)
	public void testEpisodeThree() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setEpisodeId(3);
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);

		target("capacity/workinghours").request().post(entity, String.class);
	}

	@Test
	public void testDuration() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		request.setStart(LocalDate.parse("2015-01-01"));
		request.setEnd(LocalDate.parse("2015-06-30"));
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Eight employees are working from 2015-01-01 to 2015-06-30", horkingHoursPerEmployee, hasSize(8));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(1)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(15)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(13)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(17)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(5)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(3)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(7)))));
	}

	@Test
	public void testDurationEmpty() throws IOException {
		WorkingHoursRequest request = new WorkingHoursRequest();
		Entity<WorkingHoursRequest> entity = Entity.entity(request, MediaType.APPLICATION_JSON);
		String workingHours = target("capacity/workinghours").request().post(entity, String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<WorkingHoursPerEmployee> horkingHoursPerEmployee = mapper.readValue(workingHours,
				new TypeReference<List<WorkingHoursPerEmployee>>() {
				});

		assertThat("Four employees are working indefinitely", horkingHoursPerEmployee, hasSize(4));

		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(2)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(8)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(6)))));
		assertThat(horkingHoursPerEmployee, hasItem(Matchers.<WorkingHoursPerEmployee> hasProperty("employee",
				Matchers.<Employee> hasProperty("id", equalTo(4)))));
	}

}
