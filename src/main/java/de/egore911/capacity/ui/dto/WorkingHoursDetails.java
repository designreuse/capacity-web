package de.egore911.capacity.ui.dto;

import java.time.LocalDate;

public class WorkingHoursDetails {

	private LocalDate date;
	private double hours;

	public WorkingHoursDetails() {
	}

	public WorkingHoursDetails(LocalDate date, double hours) {
		this.date = date;
		this.hours = hours;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

}
