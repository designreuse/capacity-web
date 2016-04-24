package de.egore911.capacity.ui.dto;

import java.time.LocalDate;
import java.util.List;

public class Episode extends AbstractDto {

	private LocalDate start;
	private LocalDate end;
	private String name;
	private List<EmployeeEpisode> employeeEpisodes;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<EmployeeEpisode> getEmployeeEpisodes() {
		return employeeEpisodes;
	}
	
	public void setEmployeeEpisodes(List<EmployeeEpisode> employeeEpisodes) {
		this.employeeEpisodes = employeeEpisodes;
	}

}
