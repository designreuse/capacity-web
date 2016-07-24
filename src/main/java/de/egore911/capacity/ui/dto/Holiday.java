package de.egore911.capacity.ui.dto;

import java.time.LocalDate;
import java.util.List;

public class Holiday extends AbstractDto implements Event {

	private LocalDate date;
	private String name;
	private int hoursReduction;
	private List<Location> locations;

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

	public int getHoursReduction() {
		return hoursReduction;
	}

	public void setHoursReduction(int hoursReduction) {
		this.hoursReduction = hoursReduction;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

}
