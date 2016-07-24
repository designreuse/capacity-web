package de.egore911.capacity.ui.rest;

import de.egore911.capacity.persistence.model.Permission;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

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

}
