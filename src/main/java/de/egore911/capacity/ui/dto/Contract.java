package de.egore911.capacity.ui.dto;

import java.time.LocalDate;
import java.util.List;

public class Contract {

	private LocalDate start;
	private LocalDate end;
	private int vacationDaysPerYear;
	private List<WorkingHours> workingHours;
	private Integer workingHoursPerWeek;

	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

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

	public List<WorkingHours> getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(List<WorkingHours> workingHours) {
		this.workingHours = workingHours;
	}

	public Integer getWorkingHoursPerWeek() {
		return workingHoursPerWeek;
	}

	public void setWorkingHoursPerWeek(Integer workingHoursPerWeek) {
		this.workingHoursPerWeek = workingHoursPerWeek;
	}

}