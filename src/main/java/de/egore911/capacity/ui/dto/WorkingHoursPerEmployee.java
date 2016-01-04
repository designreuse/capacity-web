package de.egore911.capacity.ui.dto;

public class WorkingHoursPerEmployee {

	private Employee employee;
	private WorkingHoursList workingHours;
	private int velocity;

	public WorkingHoursPerEmployee() {
	}

	public WorkingHoursPerEmployee(Employee employee, WorkingHoursList workingHours, int velocity) {
		this.employee = employee;
		this.workingHours = workingHours;
		this.velocity = velocity;
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

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

}
