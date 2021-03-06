package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
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

public class AbsentServiceTest extends AbstractUiTest {

	@Test
	public void checkAbsencesTenthOfFebruary() throws IOException {
		String absences = target("absent").queryParam("date", "2015-02-10").request().get(String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Employee> employees = mapper.readValue(absences, new TypeReference<List<Employee>>() {
		});

		assertThat("Two employees expected: ID 1 has an absence, ID 17 has non-working day", employees, hasSize(2));

		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(1))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(17))));

	}

	@Test
	public void checkAbsencesThirdOfMarch() throws IOException {
		String absences = target("absent").queryParam("date", "2015-03-03").request().get(String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Employee> employees = mapper.readValue(absences, new TypeReference<List<Employee>>() {
		});

		assertThat("One employee expected: ID 17 has non-working day", employees, hasSize(1));

		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(17))));

	}

	@Test
	public void checkAbsencesToday() throws IOException {
		String absences = target("absent").request().get(String.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		List<Employee> employees = mapper.readValue(absences, new TypeReference<List<Employee>>() {
		});

		if (DayOfWeek.TUESDAY == LocalDate.now().getDayOfWeek()) {
			assertThat("Three employees expected: all have non-working day", employees, hasSize(3));
		} else {
			assertThat("Four employees expected: all have non-working day", employees, hasSize(4));

			assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(2))));
		}
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(8))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(6))));
		assertThat(employees, hasItem(Matchers.<Employee> hasProperty("id", equalTo(4))));
	}
}
