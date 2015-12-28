package de.egore911.capacity.ui.dto;

public class WorkingHoursPerEmployee {

	private Employee employee;
	private WorkingHoursList workingHours;

	public WorkingHoursPerEmployee() {
	}

	public WorkingHoursPerEmployee(Employee employee, WorkingHoursList workingHours) {
		this.employee = employee;
		this.workingHours = workingHours;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public WorkingHoursList getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(WorkingHoursList workingHours) {
		this.workingHours = workingHours;
	}

}
