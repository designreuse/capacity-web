package de.egore911.capacity.ui.dto;

import java.util.Map;

import org.joda.time.LocalDate;

public class WorkingHours {
	private LocalDate from;
	private LocalDate until;
	private int hours;
	private Map<LocalDate, Integer> details;

	public WorkingHours() {
	}

	public WorkingHours(LocalDate from, LocalDate until, int hours, Map<LocalDate, Integer> details) {
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
	
	public Map<LocalDate, Integer> getDetails() {
		return details;
	}
	
	public void setDetails(Map<LocalDate, Integer> details) {
		this.details = details;
	}
}