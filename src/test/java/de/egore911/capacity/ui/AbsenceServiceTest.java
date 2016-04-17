package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import de.egore911.capacity.ui.dto.Employee;

public class AbsenceServiceTest extends AbstractUiTest {

	@Test
	public void checkAbsences() throws JsonParseException, JsonMappingException, IOException {
		String absences = target("absent").queryParam("date", "2015-02-10").request().get(String.class);

		System.err.println(absences);

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());

		List<Employee> employees = mapper.readValue(absences, new TypeReference<List<Employee>>() {});

		assertThat("Two employees expected: 1 has an absence, 17 has non-working day", employees, hasSize(2));
		Employee employee = employees.get(0);

		assertThat(employee.getId(), equalTo(1));
	}
}