package de.egore911.capacity.ui.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;
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

import org.apache.shiro.subject.Subject;
import org.secnod.shiro.jaxrs.Auth;

import de.egore911.capacity.persistence.dao.IcalImportDao;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.persistence.model.Permission;
import de.egore911.capacity.persistence.selector.IcalImportSelector;
import de.egore911.capacity.ui.async.ProgressEndpoint;
import de.egore911.capacity.ui.dto.IcalImport;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.dto.Progress;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.ui.exceptions.NotAuthorizedException;
import de.egore911.capacity.util.importer.IcalImporter;
import de.egore911.capacity.util.listener.StartupListener;
import de.egore911.persistence.util.EntityManagerUtil;

@Path("ical_import")
public class IcalImportService extends AbstractResourceService<IcalImport, IcalImportEntity> {

	@Override
	protected Class<IcalImport> getDtoClass() {
		return IcalImport.class;
	}

	@Override
	protected Class<IcalImportEntity> getEntityClass() {
		return IcalImportEntity.class;
	}

	@Override
	protected IcalImportSelector getSelector(Subject subject) {
		return new IcalImportSelector();
	}

	@Override
	protected IcalImportDao getDao() {
		return new IcalImportDao();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<IcalImport> getAll(@Auth Subject subject) {
		List<IcalImport> map = super.getAll(subject);
		if (!subject.isPermitted(Permission.ADMIN_ICAL_IMPORTS.name())) {
			for (IcalImport icalImport : map) {
				icalImport.setPassword(null);
			}
		}
		return map;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public IcalImport create(IcalImport t, @Auth Subject subject) {
		if (!subject.isPermitted(Permission.ADMIN_ICAL_IMPORTS.name())) {
			throw new NotAuthorizedException();
		}
		return super.create(t, subject);
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public IcalImport getById(@PathParam("id") Integer id, @Auth Subject subject) {
		IcalImport map = super.getById(id, subject);
		if (!subject.isPermitted(Permission.ADMIN_ICAL_IMPORTS.name())) {
			map.setPassword(null);
		}
		return map;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void update(@PathParam("id") @Nonnull Integer id, IcalImport t, @Auth Subject subject) {
		if (!subject.isPermitted(Permission.ADMIN_ICAL_IMPORTS.name())) {
			throw new NotAuthorizedException();
		}
		super.update(id, t, subject);
	}
	
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") @Nonnull Integer id, @Auth Subject subject) {
		if (!subject.isPermitted(Permission.ADMIN_ICAL_IMPORTS.name())) {
			throw new NotAuthorizedException();
		}
		super.delete(id, subject);
	}
	
	@GET
	@Path("import/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String importIcal(@PathParam("id") final Integer icalImportId) {
		final Progress<ImportResult> progress = new Progress<>();
		String id = UUID.randomUUID().toString();
		ProgressEndpoint.CACHE.put(id, progress);
		final EntityManager entityManager = EntityManagerUtil.getEntityManager();
		EntityManagerUtil.clearEntityManager();
		StartupListener.EXECUTOR.execute(() -> {
			EntityManagerUtil.setEntityManager(entityManager);
			try {
				IcalImportEntity icalImportEntity = new IcalImportSelector().withId(icalImportId).find();
				if (icalImportEntity == null) {
					throw new BadArgumentException("Given ID not found in database");
				}
				new IcalImporter().importIcal(icalImportEntity, progress);
				icalImportEntity.setLastImported(LocalDateTime.now());
				new IcalImportDao().save(icalImportEntity);
			} finally {
				EntityManagerUtil.clearEntityManager();
				entityManager.close();
			}
		});
		return id;
	}

}
