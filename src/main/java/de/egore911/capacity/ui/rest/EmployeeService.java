package de.egore911.capacity.ui.rest;

import javax.ws.rs.Path;

import org.apache.shiro.subject.Subject;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.ui.dto.Employee;

@Path("employee")
public class EmployeeService extends AbstractResourceService<Employee, EmployeeEntity> {

	@Override
	protected Class<Employee> getDtoClass() {
		return Employee.class;
	}

	@Override
	protected Class<EmployeeEntity> getEntityClass() {
		return EmployeeEntity.class;
	}

	@Override
	protected EmployeeSelector getSelector(Subject subject) {
		return new EmployeeSelector();
	}

	@Override
	protected EmployeeDao getDao() {
		return new EmployeeDao();
	}

}
