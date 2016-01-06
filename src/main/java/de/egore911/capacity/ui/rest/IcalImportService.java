package de.egore911.capacity.ui.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.LocalDateTime;

import de.egore911.capacity.persistence.dao.IcalImportDao;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.persistence.selector.IcalImportSelector;
import de.egore911.capacity.ui.dto.IcalImport;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.util.importer.IcalImporter;

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
	@Produces(MediaType.APPLICATION_JSON)
	public ImportResult importIcal(@PathParam("id") Integer icalImportId) {
		IcalImportEntity icalImportEntity = new IcalImportSelector().withId(icalImportId).find();
		if (icalImportEntity == null) {
			throw new BadArgumentException("Given ID not found in database");
		}
		ImportResult importIcal = new IcalImporter().importIcal(icalImportEntity.getUrl());
		icalImportEntity.setLastImported(LocalDateTime.now());
		new IcalImportDao().save(icalImportEntity);
		return importIcal;
	}

}
