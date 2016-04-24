package de.egore911.capacity.ui.rest;

import java.time.LocalDate;
import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.ui.dto.Employee;

@Path("available")
public class AvailabilityService extends AbstractService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getEmployees(@DefaultValue("") @QueryParam("date") LocalDate date) {
		if (date == null) {
			date = LocalDate.now();
		}
		List<EmployeeEntity> availableEmployees = new EmployeeSelector()
				.withAvailability(date)
				.withActiveContract(date, date)
				.findAll();
		return getMapper().mapAsList(availableEmployees,
				Employee.class);
	}

}
