package de.egore911.capacity.ui.rest;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.ui.dto.Ability;

@Path("abilities")
public class AbilityService extends AbstractService {

	private static final Transformer<String, Ability> TRANSFORMER = Ability::new;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ability> getAbilities() {
		List<String> abilities = new EmployeeDao().findAbilities();
		return CollectionUtils.collect(abilities, TRANSFORMER);
	}
}
