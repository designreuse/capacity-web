package de.egore911.capacity.ui.dto;

import org.joda.time.LocalDate;

public class WorkingHoursDetails {

	private LocalDate date;
	private int hours;

	public WorkingHoursDetails() {
	}

	public WorkingHoursDetails(LocalDate date, int hours) {
		this.date = date;
		this.hours = hours;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

}
