package de.egore911.capacity.ui.dto;

import org.joda.time.LocalDateTime;

public class IcalImport extends AbstractDto {

	private String name;
	private String url;
	private LocalDateTime lastImported;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getLastImported() {
		return lastImported;
	}

	public void setLastImported(LocalDateTime lastImported) {
		this.lastImported = lastImported;
	}

}
