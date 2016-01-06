package de.egore911.capacity.ui.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.util.importer.IcalImporter;

@Path("import")
public class ImportService {

	@GET
	@Path("absence")
	@Produces(MediaType.APPLICATION_JSON)
	public ImportResult importAbsences(@QueryParam("url") String url) {
		return new IcalImporter().importIcal(url);
	}

}
