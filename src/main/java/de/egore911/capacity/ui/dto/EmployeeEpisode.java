package de.egore911.capacity.ui.dto;

public class EmployeeEpisode {
	
	private Integer id;
	private Employee employee;
	private Integer velocity;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Integer getVelocity() {
		return velocity;
	}
	
	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
	}

}
