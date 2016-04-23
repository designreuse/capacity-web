package de.egore911.capacity.util.importer;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;

import org.junit.Test;

import de.egore911.capacity.AbstractDatabaseTest;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.dto.Progress;
import de.egore911.persistence.util.EntityManagerUtil;

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

	@Test
	public void testNoAttendee() {
		IcalImportEntity icalImport = mock(IcalImportEntity.class);
		when(icalImport.getUrl()).thenReturn(this.getClass().getResource("/ical/atendees/no_attendee.ics").toString());
		when(icalImport.getId()).thenReturn(Integer.valueOf(1));
		Progress<ImportResult> progress = new Progress<>();
		new IcalImporter().importIcal(icalImport, progress);
		assertThat(progress.isCompleted(), equalTo(true));
		assertThat(progress.getResult().getCreated(), equalTo(0));
		assertThat(progress.getResult().getDeleted(), equalTo(0));
		assertThat(progress.getResult().getUpdated(), equalTo(0));
		assertThat(progress.getResult().getSkipped(), equalTo(1));
	}

	@Test
	public void testOneAttendee() {
		IcalImportEntity icalImport = mock(IcalImportEntity.class);
		when(icalImport.getUrl()).thenReturn(this.getClass().getResource("/ical/atendees/one_attendee.ics").toString());
		when(icalImport.getId()).thenReturn(Integer.valueOf(1));
		Progress<ImportResult> progress = new Progress<>();
		new IcalImporter().importIcal(icalImport, progress);
		assertThat(progress.isCompleted(), equalTo(true));
		assertThat(progress.getResult().getCreated(), equalTo(1));
		assertThat(progress.getResult().getDeleted(), equalTo(0));
		assertThat(progress.getResult().getUpdated(), equalTo(0));
		assertThat(progress.getResult().getSkipped(), equalTo(0));

		removeAbsences();
	}

	private void removeAbsences() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.createQuery("delete from Absence where icalImport.id = 1").executeUpdate();
		em.getTransaction().commit();
	}

	@Test
	public void testTwoAttendee() {
		IcalImportEntity icalImport = mock(IcalImportEntity.class);
		when(icalImport.getUrl()).thenReturn(this.getClass().getResource("/ical/atendees/two_attendee.ics").toString());
		when(icalImport.getId()).thenReturn(Integer.valueOf(1));
		Progress<ImportResult> progress = new Progress<>();
		new IcalImporter().importIcal(icalImport, progress);
		assertThat(progress.isCompleted(), equalTo(true));
		assertThat(progress.getResult().getCreated(), equalTo(1));
		assertThat(progress.getResult().getDeleted(), equalTo(0));
		assertThat(progress.getResult().getUpdated(), equalTo(0));
		assertThat(progress.getResult().getSkipped(), equalTo(0));

		removeAbsences();
	}
}
