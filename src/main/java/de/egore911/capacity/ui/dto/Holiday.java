package de.egore911.capacity.ui.dto;

import org.joda.time.LocalDate;

public class Holiday extends AbstractDto {

	private LocalDate date;
	private String name;
	private Location location;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
