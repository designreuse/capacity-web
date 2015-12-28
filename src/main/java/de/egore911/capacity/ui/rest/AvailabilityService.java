package de.egore911.capacity.ui.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.ui.dto.Employee;

@Path("available")
public class AvailabilityService extends AbstractService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getEmployees() {
		return getMapper().mapAsList(new EmployeeSelector().withAvailability(LocalDate.now()).findAll(),
				Employee.class);
	}

}
