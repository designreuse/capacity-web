package de.egore911.capacity.ui.dto;

import java.util.ArrayList;
import java.util.List;

import de.egore911.capacity.persistence.model.Permission;

public class Role extends AbstractDto {

	private String name;
	private List<Permission> permissions = new ArrayList<>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

}
