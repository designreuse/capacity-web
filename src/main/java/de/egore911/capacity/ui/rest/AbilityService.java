package de.egore911.capacity.ui.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.ui.dto.Ability;

@Path("abilities")
public class AbilityService extends AbstractService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ability> getAbilities() {
		return new EmployeeDao().findAbilities().stream()
				.map(Ability::new)
				.collect(Collectors.toList());
	}
}
