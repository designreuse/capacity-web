package de.egore911.capacity.ui.dto;

import java.time.LocalDateTime;

public class IcalImport extends AbstractDto {

	public enum Auth {
		NONE, BASIC
	}

	private String name;
	private String url;
	private LocalDateTime lastImported;
	private Auth auth;
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
