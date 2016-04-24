package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.List;

import org.hamcrest.Matchers;
import java.time.LocalDate;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import de.egore911.capacity.ui.dto.Employee;

public class AvailabilityServiceTest extends AbstractUiTest {

	@Test
	public void checkAvailabilitiesTenthOfFebruary() throws IOException {
		String availabilities = target("available").queryParam("date", "2015-02-10").request().get(String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Employee> employees = mapper.readValue(availabilities, new TypeReference<List<Employee>>() {
		});

		assertThat(
				"6 employees expected: ID 1 has an absence, all others have working day, no absence and a valid contract",
				employees, hasSize(6));

		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(2))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(15))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(13))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(5))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(3))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(7))));

	}

	@Test
	public void checkAvailabilitiesThirdOfMarch() throws IOException {
		String availabilities = target("available").queryParam("date", "2015-03-03").request().get(String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Employee> employees = mapper.readValue(availabilities, new TypeReference<List<Employee>>() {
		});

		assertThat("Sevent employee expected: all have working day, no absence and a valid contract", employees,
				hasSize(7));

		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(1))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(2))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(15))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(13))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(5))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(3))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(7))));
	}

	@Test
	public void checkAvailabilitiesToday() throws IOException {
		String availabilities = target("available").request().get(String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Employee> employees = mapper.readValue(availabilities, new TypeReference<List<Employee>>() {
		});

		if (DayOfWeek.TUESDAY == LocalDate.now().getDayOfWeek()) {
			assertThat("One employee expected: ID 2 has a working day and a valid contract", employees, hasSize(1));

			assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(2))));
		} else {
			assertThat("No employees expected: noone has a working day", employees, empty());
		}
	}
}
