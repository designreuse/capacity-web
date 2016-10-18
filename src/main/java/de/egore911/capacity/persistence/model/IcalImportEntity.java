package de.egore911.capacity.persistence.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity(name = "IcalImport")
@Table(name = "ical_import")
public class IcalImportEntity extends IntegerDbObject {

	private static final long serialVersionUID = 7125037687341625543L;

	public enum Auth {
		NONE, BASIC
	}

	private String name;
	private String url;
	private LocalDateTime lastImported;
	private Auth auth = Auth.NONE;
	private String username;
	private String password;

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

	@Enumerated(EnumType.STRING)
	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
