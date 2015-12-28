package de.egore911.capacity.ui.dto;

public class WorkingHoursPerEmployee {

	private Employee employee;
	private WorkingHours workingHours;

	public WorkingHoursPerEmployee() {
	}

	public WorkingHoursPerEmployee(Employee employee, WorkingHours workingHours) {
		this.employee = employee;
		this.workingHours = workingHours;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public WorkingHours getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(WorkingHours workingHours) {
		this.workingHours = workingHours;
	}

}
