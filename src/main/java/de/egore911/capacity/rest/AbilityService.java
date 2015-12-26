package de.egore911.capacity.rest;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import de.egore911.capacity.ui.dto.Ability;
import de.egore911.persistence.util.EntityManagerUtil;

@Path("abilities")
public class AbilityService extends AbstractService {
	
	private static final Transformer<String, Ability> TRANSFORMER = new Transformer<String, Ability>() {
		@Override
		public Ability transform(String name) {
			return new Ability(name);
		}
	};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Ability> getAbilities() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		List<String> abilities = em.createQuery("select distinct a.name from Employee e join e.abilities a", String.class).getResultList();
		
		return CollectionUtils.collect(abilities, TRANSFORMER);
	}
}
