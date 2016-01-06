package de.egore911.capacity.persistence.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Embeddable
public class ContractEntity {

	private LocalDate start;
	private LocalDate end;
	private int vacationDaysPerYear;
	private List<WorkingHoursEntity> workingHours;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	@Column(name = "`end`")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public int getVacationDaysPerYear() {
		return vacationDaysPerYear;
	}

	public void setVacationDaysPerYear(int vacationDaysPerYear) {
		this.vacationDaysPerYear = vacationDaysPerYear;
	}

	@ElementCollection
	@CollectionTable(name = "working_hours", joinColumns = @JoinColumn(name = "employee_id") )
	public List<WorkingHoursEntity> getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(List<WorkingHoursEntity> workingHours) {
		this.workingHours = workingHours;
	}

}
