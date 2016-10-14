package de.egore911.capacity.ui.rest;

import de.egore911.capacity.persistence.model.Permission;
import de.egore911.capacity.persistence.model.RoleEntity;
import de.egore911.capacity.persistence.selector.RoleSelector;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("permissions")
public class PermissionService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getPermissions() {
		List<String> permissions = new ArrayList<>();
		for (Permission permission : Permission.values()) {
			permissions.add(permission.name());
		}
		return permissions;
	}

	@GET
	@Path("my")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getMyPermissions(@Auth Subject subject) {
		List<RoleEntity> roles = new RoleSelector().withUserLogin((String) subject.getPrincipal()).findAll();
		return roles.stream()
				.map(RoleEntity::getPermissions)
				.flatMap(List::stream)
				.map(Permission::name)
				.distinct()
				.collect(Collectors.toList());
	}

}
