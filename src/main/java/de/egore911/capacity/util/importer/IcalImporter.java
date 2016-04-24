package de.egore911.capacity.util.importer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;

import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.capacity.persistence.dao.AbsenceDao;
import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.IcalImportEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.ui.dto.ImportResult;
import de.egore911.capacity.ui.dto.Progress;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.ui.exceptions.BadStateException;
import de.egore911.persistence.util.EntityManagerUtil;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;

public class IcalImporter {

	private static final Logger LOG = LoggerFactory.getLogger(IcalImporter.class);

	public void importIcal(@Nonnull IcalImportEntity icalImport, @Nonnull Progress<ImportResult> progress) {
		String url = icalImport.getUrl();
		ImportResult result = new ImportResult();
		try {
			Calendar calendar = loadCalendar(progress, url);

			EntityManager em = EntityManagerUtil.getEntityManager();
			em.getTransaction().begin();
			try {
				progress.setMessage("Importing events");
				ComponentList<CalendarComponent> components = calendar.getComponents("VEVENT");
				progress.setMax(components.size());
				int i = 0;
				Set<Integer> absenceIds = new HashSet<>();
				AbsenceDao absenceDao = new AbsenceDao();
				for (Component component : components) {
					Property uidProperty = component.getProperty("UID");
					String uid = uidProperty != null ? uidProperty.getValue() : null;
					if (uid == null) {
						LOG.warn("Event did not have an UID, skipping");
						result.skip();
						continue;
					}
					Property emailProperty = component.getProperty("ATTENDEE");
					String email = emailProperty != null ? emailProperty.getValue() : null;
					if (email == null) {
						LOG.warn("Event " + uid + " did not have an email");
						result.skip();
						continue;
					}
					if (email.startsWith("mailto:")) {
						email = email.substring("mailto:".length());
					}
					email = email.toLowerCase();
					EmployeeEntity employee = new EmployeeSelector().withEmail(email).find();
					if (employee == null) {
						LOG.warn("Employee with e-mail " + email + " not found, skipping");
						result.skip();
						continue;
					}
					AbsenceEntity absence = null;
					for (AbsenceEntity existingAbsence : employee.getAbsences()) {
						if (uid.equals(existingAbsence.getExternalId()) && existingAbsence.getIcalImport() != null && existingAbsence.getIcalImport().getId().equals(icalImport.getId())) {
							absence = existingAbsence;
							break;
						}
					}
					if (absence == null) {
						absence = new AbsenceEntity();
						employee.getAbsences().add(absence);
						absence.setEmployee(employee);
						absence.setExternalId(uid);
						absence.setIcalImport(icalImport);
					}

					Property summaryProperty = component.getProperty("SUMMARY");
					String reason = summaryProperty != null ? summaryProperty.getValue() : "Unnamed";
					absence.setReason(reason);
					try {
						absence.setStart(
								ISODateTimeFormat.basicDate().parseLocalDate(component.getProperty("DTSTART").getValue()));
						absence.setEnd(
								ISODateTimeFormat.basicDate().parseLocalDate(component.getProperty("DTEND").getValue()).minusDays(1));
					} catch (IllegalArgumentException e) {
						// TODO We don't handle vacations for half days
						result.skip();
						e.printStackTrace();
						continue;
					}
					if (absence.getId() != null) {
						result.update();
					} else {
						result.create();
					}
					absence = absenceDao.save(absence);
					absenceIds.add(absence.getId());
					progress.setValue(++i);
				}

				if (!absenceIds.isEmpty()) {
					result.setDeleted(absenceDao.deleteFromIcalImportExcept(icalImport, absenceIds));
				}

				progress.setValue(progress.getMax());
				em.getTransaction().commit();
			} finally {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			}

			progress.setMessage("Import completed");

		} catch (MalformedURLException e) {
			progress.setSuccess(false);
			progress.setMessage(e.getMessage());
			throw new BadArgumentException("Not a valid URL");
		} catch (IOException | ParserException | RuntimeException e) {
			progress.setSuccess(false);
			progress.setMessage(e.getMessage());
			throw new BadStateException(e.getMessage());
		} finally {
			progress.setCompleted(true);
			progress.setResult(result);
		}
	}

	private Calendar loadCalendar(Progress<ImportResult> progress, String url)
			throws IOException, ParserException {
		progress.setMessage("Downloading calendar");
		URL validUrl = new URL(url);
		URLConnection connection = validUrl.openConnection();

		if (connection instanceof HttpURLConnection) {
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			if (responseCode != 200) {
				throw new BadStateException("Got HTTP status " + responseCode + ", expected 200");
			}
		}
		progress.setMessage("Parsing calendar");
		CalendarBuilder builder = new CalendarBuilder();

		try (InputStream inputStream = connection.getInputStream()) {
			return builder.build(inputStream);
		}
	}

}
