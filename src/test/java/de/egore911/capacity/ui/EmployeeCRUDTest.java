package de.egore911.capacity.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.GenericType;

import de.egore911.capacity.ui.dto.Ability;
import de.egore911.capacity.ui.dto.Contract;
import de.egore911.capacity.ui.dto.Employee;

public class EmployeeCRUDTest extends AbstractCRUDTest<Employee> {

	@Override
	protected Employee createFixture() {
		Employee fixture = new Employee();
		fixture.setName(UUID.randomUUID().toString());
		fixture.setEmail(UUID.randomUUID().toString());
		fixture.setAbilities(Collections.singleton(new Ability("Working")));
		fixture.setColor("#00ff00");
		Contract contract = new Contract();
		contract.setVacationDaysPerYear(2);
		fixture.setContract(contract);
		return fixture;
	}

	@Override
	protected String getPath() {
		return "employee";
	}

	@Override
	protected Class<Employee> getFixtureClass() {
		return Employee.class;
	}

	@Override
	protected void modifyFixture(Employee fixture) {
		fixture.setName(UUID.randomUUID().toString());
	}

	@Override
	protected void compareDtos(Employee fixture, Employee created) {
		assertThat(created.getName(), equalTo(fixture.getName()));
		assertThat(created.getEmail(), equalTo(fixture.getEmail()));
		assertThat(created.getAbilities().size(), equalTo(fixture.getAbilities().size()));
		assertThat(created.getAbilities().iterator().next().getName(), equalTo(fixture.getAbilities().iterator().next().getName()));
	}

	@Override
	protected GenericType<List<Employee>> getGenericType() {
		return new GenericType<List<Employee>>() {
		};
	}

}
