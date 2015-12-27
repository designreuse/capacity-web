package de.egore911.capacity.ui.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.ui.dto.Employee;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.ui.exceptions.NotFoundException;
import de.egore911.capacity.ui.exceptions.NullArgumentException;
import de.egore911.persistence.util.EntityManagerUtil;

@Path("employee")
public class EmployeeService extends AbstractService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Employee> getAll() {
		return getMapper().mapAsList(new EmployeeSelector().findAll(), Employee.class);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(Employee employee) {
		if (employee == null) {
			throw new NullArgumentException("employee");
		}
		if (employee.getId() != null) {
			throw new BadArgumentException("Cannot create an entity already having an ID");
		}
		new EmployeeDao().save(getMapper().map(employee, EmployeeEntity.class));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getById(@PathParam("id") Integer id) {
		if (id == null) {
			throw new NullArgumentException("id");
		}
		return getMapper().map(new EmployeeSelector().withId(id).find(), Employee.class);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") Integer id, Employee employee) {
		if (employee == null) {
			throw new NullArgumentException("employee");
		}
		if (employee.getId() == null) {
			throw new BadArgumentException("Cannot update an entity without an ID");
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			EmployeeEntity employeeEntity = new EmployeeSelector().withId(id).find();
			if (employeeEntity == null) {
				throw new NotFoundException("Could not find employee with ID " + id);
			}
			getMapper().map(employee, employeeEntity);
			new EmployeeDao().save(employeeEntity);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}

	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Integer id) {
		if (id == null) {
			throw new NullArgumentException("id");
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			EmployeeEntity employeeEntity = new EmployeeSelector().withId(id).find();
			if (employeeEntity == null) {
				throw new IllegalArgumentException();
			}
			new EmployeeDao().remove(employeeEntity);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

}
