package de.egore911.capacity.util.importer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.dto.Progress;

public class IcalImporterTest extends AbstractDatabaseTest {

	@Test
	public void testSabre() {
		IcalImportEntity icalImport = mock(IcalImportEntity.class);
		when(icalImport.getUrl()).thenReturn(this.getClass().getResource("/ical/sabre.ics").toString());
		Progress<ImportResult> progress = new Progress<>();
		new IcalImporter().importIcal(icalImport, progress);
		assertThat(progress.isCompleted(), equalTo(true));
		assertThat(progress.getResult().getCreated(), equalTo(0));
		assertThat(progress.getResult().getDeleted(), equalTo(0));
		assertThat(progress.getResult().getUpdated(), equalTo(0));
		assertThat(progress.getResult().getSkipped(), equalTo(1));
	}


	@Test
	public void testWikipedia() {
		IcalImportEntity icalImport = mock(IcalImportEntity.class);
		when(icalImport.getUrl()).thenReturn(this.getClass().getResource("/ical/wikipedia.ics").toString());
		Progress<ImportResult> progress = new Progress<>();
		new IcalImporter().importIcal(icalImport, progress);
		assertThat(progress.isCompleted(), equalTo(true));
		assertThat(progress.getResult().getCreated(), equalTo(0));
		assertThat(progress.getResult().getDeleted(), equalTo(0));
		assertThat(progress.getResult().getUpdated(), equalTo(0));
		assertThat(progress.getResult().getSkipped(), equalTo(1));
	}
}
