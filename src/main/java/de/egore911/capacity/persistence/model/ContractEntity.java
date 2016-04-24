package de.egore911.capacity.persistence.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class ContractEntity {

	private LocalDate start;
	private LocalDate end;
	private int vacationDaysPerYear;
	private List<WorkingHoursEntity> workingHours;

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	@Column(name = "`end`")
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
