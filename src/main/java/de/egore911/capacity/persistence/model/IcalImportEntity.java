package de.egore911.capacity.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "IcalImport")
@Table(name = "ical_import")
public class IcalImportEntity extends IntegerDbObject {

	private static final long serialVersionUID = 7125037687341625543L;

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
