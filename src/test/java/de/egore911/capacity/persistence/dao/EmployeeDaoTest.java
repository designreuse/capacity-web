package de.egore911.capacity.persistence.dao;

import java.util.UUID;

import de.egore911.capacity.persistence.model.ContractEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity;

public class EmployeeDaoTest extends AbstractDaoCRUDTest<EmployeeEntity> {

	@Override
	protected EmployeeEntity createFixture() {
		EmployeeEntity employee = new EmployeeEntity();
		employee.setEmail(UUID.randomUUID().toString());
		employee.setName(UUID.randomUUID().toString());
		employee.setColor("#00ff00");
		employee.setContract(new ContractEntity());
		employee.getContract().setVacationDaysPerYear(100);
		return employee;
	}

	@Override
	protected void modifyFixture(EmployeeEntity employee) {
		employee.setName(UUID.randomUUID().toString());
	}

	@Override
	protected EmployeeDao getDao() {
		return new EmployeeDao();
	}

}
