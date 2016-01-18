package de.egore911.capacity.util.importer;

import org.junit.Ignore;
import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.dto.Progress;

public class IcalImporterTest extends AbstractDatabaseTest {

	@Test
	@Ignore
	public void test() {
		IcalImporter importer = new IcalImporter();
		importer.importIcal("Some ICS url", new Progress<ImportResult>());
	}
}
