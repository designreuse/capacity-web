package de.egore911.capacity.ui.rest;

import java.util.UUID;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDateTime;

import de.egore911.capacity.persistence.dao.IcalImportDao;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.persistence.selector.IcalImportSelector;
import de.egore911.capacity.ui.async.ProgressEndpoint;
import de.egore911.capacity.ui.dto.IcalImport;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.dto.Progress;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
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
	protected IcalImportSelector getSelector() {
		return new IcalImportSelector();
	}

	@Override
	protected IcalImportDao getDao() {
		return new IcalImportDao();
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
		StartupListener.EXECUTOR.execute(new Runnable() {
			@Override
			public void run() {
				EntityManagerUtil.setEntityManager(entityManager);
				try {
					IcalImportEntity icalImportEntity = new IcalImportSelector().withId(icalImportId).find();
					if (icalImportEntity == null) {
						throw new BadArgumentException("Given ID not found in database");
					}
					new IcalImporter().importIcal(icalImportEntity.getUrl(), progress);
					icalImportEntity.setLastImported(LocalDateTime.now());
					new IcalImportDao().save(icalImportEntity);
				} finally {
					EntityManagerUtil.clearEntityManager();
					entityManager.close();
				}
			}
		});
		return id;
	}

}
