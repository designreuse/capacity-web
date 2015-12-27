package de.egore911.capacity.ui.dto;

import org.joda.time.LocalDate;

public class Contract {

	private LocalDate start;
	private LocalDate end;
	private int workHoursPerDay;

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

	public int getWorkHoursPerDay() {
		return workHoursPerDay;
	}

	public void setWorkHoursPerDay(int workHoursPerDay) {
		this.workHoursPerDay = workHoursPerDay;
	}

}