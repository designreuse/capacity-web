package de.egore911.capacity.ui.dto;

import java.time.LocalDate;
import java.util.List;

public class WorkingHoursList {
	private LocalDate from;
	private LocalDate until;
	private double hours;
	private List<WorkingHoursDetails> details;

	public WorkingHoursList() {
	}

	public WorkingHoursList(LocalDate from, LocalDate until, double hours, List<WorkingHoursDetails> details) {
		this.from = from;
		this.until = until;
		this.hours = hours;
		this.details = details;
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getUntil() {
		return until;
	}

	public void setUntil(LocalDate until) {
		this.until = until;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}
	
	public List<WorkingHoursDetails> getDetails() {
		return details;
	}
	
	public void setDetails(List<WorkingHoursDetails> details) {
		this.details = details;
	}
}