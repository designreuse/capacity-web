package de.egore911.capacity.ui.dto;

import java.util.List;

import org.joda.time.LocalDate;

public class WorkingHours {
	private LocalDate from;
	private LocalDate until;
	private int hours;
	private List<WorkingHoursDetails> details;

	public WorkingHours() {
	}

	public WorkingHours(LocalDate from, LocalDate until, int hours, List<WorkingHoursDetails> details) {
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

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public List<WorkingHoursDetails> getDetails() {
		return details;
	}
	
	public void setDetails(List<WorkingHoursDetails> details) {
		this.details = details;
	}
}