package de.egore911.capacity.persistence.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "Location")
@Table(name = "location")
public class LocationEntity extends IntegerDbObject {

	private static final long serialVersionUID = 101306763923465235L;

	private String name;
	private Set<HolidayEntity> holidays;
	private Set<EmployeeEntity> employees;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToMany
	@JoinTable(name = "holiday_location", joinColumns = {
			@JoinColumn(name = "location_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "holiday_id", referencedColumnName = "id") })
	public Set<HolidayEntity> getHolidays() {
		return holidays;
	}

	public void setHolidays(Set<HolidayEntity> holidays) {
		this.holidays = holidays;
	}

	@OneToMany(mappedBy = "location")
	public Set<EmployeeEntity> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<EmployeeEntity> employees) {
		this.employees = employees;
	}

}
