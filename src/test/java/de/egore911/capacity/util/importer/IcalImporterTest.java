package de.egore911.capacity.util.importer;

import org.junit.Ignore;
import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;

public class IcalImporterTest extends AbstractDatabaseTest {

	@Test
	@Ignore
	public void test() {
		IcalImporter importer = new IcalImporter();
		importer.importIcal("Some ICS url");
	}
}
