package de.egore911.capacity.ui.dto;

import org.joda.time.LocalDate;

public class Holiday {

	private LocalDate date;
	private Location location;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
