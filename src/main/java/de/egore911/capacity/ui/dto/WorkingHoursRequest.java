package de.egore911.capacity.ui.dto;

import java.util.List;

import org.joda.time.LocalDate;

public class WorkingHoursRequest {

	private boolean useVelocity;
	private Integer episodeId;
	private LocalDate start;
	private LocalDate end;
	private List<List<Ability>> filter;

	public boolean isUseVelocity() {
		return useVelocity;
	}

	public void setUseVelocity(boolean useVelocity) {
		this.useVelocity = useVelocity;
	}

	public Integer getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(Integer episodeId) {
		this.episodeId = episodeId;
	}

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

	public List<List<Ability>> getFilter() {
		return filter;
	}

	public void setFilter(List<List<Ability>> filter) {
		this.filter = filter;
	}

}
