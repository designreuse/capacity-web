package de.egore911.capacity.ui.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.egore911.capacity.persistence.model.IntegerDbObject;
import de.egore911.capacity.persistence.selector.AbstractResourceSelector;
import de.egore911.capacity.ui.dto.AbstractDto;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.ui.exceptions.NotFoundException;
import de.egore911.capacity.ui.exceptions.NullArgumentException;
import de.egore911.persistence.dao.AbstractDao;
import de.egore911.persistence.util.EntityManagerUtil;

public abstract class AbstractResourceService<T extends AbstractDto, U extends IntegerDbObject> extends AbstractService {
	
	protected abstract Class<T> getDtoClass();
	protected abstract Class<U> getEntityClass();
	protected abstract AbstractResourceSelector<U> getSelector();
	protected abstract AbstractDao<U> getDao();

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<T> getAll() {
		return getMapper().mapAsList(getSelector().findAll(), getDtoClass());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(T t) {
		if (t == null) {
			throw new NullArgumentException("t");
		}
		if (t.getId() != null) {
			throw new BadArgumentException("Cannot create an entity already having an ID");
		}
		getDao().save(getMapper().map(t, getEntityClass()));
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public T getById(@PathParam("id") Integer id) {
		if (id == null) {
			throw new NullArgumentException("id");
		}
		return getMapper().map(getSelector().withId(id).find(), getDtoClass());
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") Integer id, T t) {
		if (t == null) {
			throw new NullArgumentException("t");
		}
		if (t.getId() == null) {
			throw new BadArgumentException("Cannot update an entity without an ID");
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			U entity = getSelector().withId(id).find();
			if (entity == null) {
				throw new NotFoundException("Could not find t with ID " + id);
			}
			getMapper().map(t, entity);
			getDao().save(entity);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}

	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Integer id) {
		if (id == null) {
			throw new NullArgumentException("id");
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		try {
			U entity = getSelector().withId(id).find();
			if (entity == null) {
				throw new IllegalArgumentException();
			}
			getDao().remove(entity);
			em.getTransaction().commit();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

}
