package de.egore911.capacity.ui.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;
import org.joda.time.Hours;
import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.EmployeeEpisodeEntity;
import de.egore911.capacity.persistence.model.EpisodeEntity;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.model.WorkingHoursEntity;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.persistence.selector.EpisodeSelector;
import de.egore911.capacity.persistence.selector.HolidaySelector;
import de.egore911.capacity.ui.dto.Employee;
import de.egore911.capacity.ui.dto.WorkingHoursDetails;
import de.egore911.capacity.ui.dto.WorkingHoursList;
import de.egore911.capacity.ui.dto.WorkingHoursPerEmployee;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.ui.exceptions.NullArgumentException;

@Path("capacity")
public class CapacityService extends AbstractService {

	@GET
	@Path("workinghours")
	@Produces(MediaType.APPLICATION_JSON)
	public List<WorkingHoursPerEmployee> getWorkingHours(@QueryParam("employeeIds") List<Integer> employeeIds,
			@DefaultValue("") @QueryParam("start") LocalDate start,
			@DefaultValue("") @QueryParam("end") LocalDate end,
			@DefaultValue("false") @QueryParam("useVelocity") boolean useVelocity,
			@DefaultValue("") @QueryParam("episodeId") Integer episodeId) {

		final Map<Integer, Integer> velocities = new HashMap<>();
		if (episodeId != null) {
			EpisodeEntity episode = new EpisodeSelector().withId(episodeId).find();
			start = episode.getStart();
			end = episode.getEnd();
			List<Integer> tmpEmployeeIds = (List<Integer>) CollectionUtils.collect(episode.getEmployeeEpisodes(), new Transformer<EmployeeEpisodeEntity, Integer>() {
				@Override
				public Integer transform(EmployeeEpisodeEntity arg0) {
					Integer velocity = arg0.getVelocity();
					if (velocity != null) {
						velocities.put(arg0.getEmployee().getId(), velocity);
					}
					return arg0.getEmployee().getId();
				}
			});
			tmpEmployeeIds.retainAll(employeeIds);
			employeeIds = tmpEmployeeIds;
		} else {

			// Check if valid dates are passed, otherwise look for 10 days into the future and into the past
			if (start == null) {
				start = LocalDate.now().minusDays(10);
			}
			if (end == null) {
				end = LocalDate.now().plusDays(10);
			}
		}

		// Load all employees that have an active contract in the timespan
		List<EmployeeEntity> employees = new EmployeeSelector().withActiveContract(start, end).withIds(employeeIds)
				.findAll();

		// Put velocities into lookup (when they were not overridden in the episode)
		for (EmployeeEntity employee : employees) {
			if (!velocities.containsKey(employee.getId())) {
				velocities.put(employee.getId(), employee.getVelocity());
			}
		}

		// Calculate the capatcity per employee
		List<WorkingHoursPerEmployee> result = new ArrayList<>(employees.size());
		for (EmployeeEntity employee : employees) {
			int velocity = velocities.get(employee.getId());
			if (useVelocity && velocity == 0) {
				continue;
			}
			int actualVelocity = useVelocity ? velocity : 100;
			result.add(new WorkingHoursPerEmployee(getMapper().map(employee, Employee.class),
					getWorkingHoursForEmployee(employee, start, end, actualVelocity), actualVelocity));
		}

		return result;
	}

	@GET
	@Path("workinghours/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public WorkingHoursList getWorkingHoursForEmployee(@PathParam("employeeId") Integer employeeId,
			@DefaultValue("") @QueryParam("start") LocalDate start,
			@DefaultValue("") @QueryParam("end") LocalDate end,
			@DefaultValue("false") @QueryParam("useVelocity") boolean useVelocity) {

		// Check for valid date ranges
		if (start == null || end == null) {
			throw new NullArgumentException("start and end");
		}
		if (!start.isBefore(end)) {
			throw new BadArgumentException("start must be before end");
		}

		// Check for valid employee IDs
		if (employeeId == null) {
			throw new NullArgumentException("employeeId");
		}

		EmployeeEntity employee = new EmployeeDao().findById(employeeId);
		if (employee == null) {
			throw new BadArgumentException("Employee with ID " + employeeId + " not found");
		}

		return getWorkingHoursForEmployee(employee, start, end, useVelocity ? employee.getVelocity() : 100);
	}

	private WorkingHoursList getWorkingHoursForEmployee(@Nonnull EmployeeEntity employee,
			@Nonnull LocalDate start,
			@Nonnull LocalDate end,
			int velocity) {
		Map<LocalDate, Integer> reductions = new HashMap<>();

		List<HolidayEntity> holidays = new HolidaySelector().withStartInclusive(start).withEndInclusive(end)
				.withLocation(employee.getLocation()).findAll();

		for (HolidayEntity holiday : holidays) {
			if (holiday.getDate().isBefore(end) && holiday.getDate().isAfter(start)) {
				reductions.put(holiday.getDate(), holiday.getHoursReduction());
			}
		}

		Map<Integer, Hours> durations = getWorkingHourDurations(employee);

		List<AbsenceEntity> absences = employee.getAbsences();
		for (AbsenceEntity absence : absences) {
			LocalDate date = absence.getStart();
			while (date.isBefore(end) && !date.isAfter(absence.getEnd())) {
				if (!date.isBefore(start)) {
					Hours hours = durations.get(date.getDayOfWeek());
					reductions.put(date, hours != null ? hours.getHours() : 0);
				}
				date = date.plusDays(1);
			}
		}

		WorkingHoursList workinghours = getWorkingHoursForEmployee(start, end, employee, reductions, velocity);

		return workinghours;
	}

	private WorkingHoursList getWorkingHoursForEmployee(LocalDate start, LocalDate end, EmployeeEntity employee,
			Map<LocalDate, Integer> reductions, int velocity) {
		double workinghours = 0;
		List<WorkingHoursDetails> details = new ArrayList<>();

		Map<Integer, Hours> durations = getWorkingHourDurations(employee);

		LocalDate date = start;
		while (!date.isAfter(end)) {
			double workinghoursOfDay;
			if (employee.getContract().getStart() != null && date.isBefore(employee.getContract().getStart()) ||
					employee.getContract().getEnd() != null && date.isAfter(employee.getContract().getEnd())) {
				workinghoursOfDay = 0;
			} else {
				int dayOfWeek = date.getDayOfWeek();
				Hours hours = durations.get(dayOfWeek);
				workinghoursOfDay = hours != null ? hours.getHours() : 0;

				if (reductions.containsKey(date)) {
					workinghoursOfDay -= reductions.get(date);
				}

				workinghoursOfDay = Math.max(workinghoursOfDay, 0);
				if (velocity != 100) {
					workinghoursOfDay *= velocity / 100d;
				}
			}
			workinghours += workinghoursOfDay;
			details.add(new WorkingHoursDetails(date, workinghoursOfDay));
			date = date.plusDays(1);
		}
		return new WorkingHoursList(start, end, workinghours, details);
	}

	private Map<Integer, Hours> getWorkingHourDurations(EmployeeEntity employee) {
		Map<Integer, Hours> durations = new HashMap<>(7);
		for (WorkingHoursEntity workingHours : employee.getContract().getWorkingHours()) {
			durations.put(workingHours.getDayOfWeek(),
					Hours.hoursBetween(workingHours.getStart(), workingHours.getEnd()));
		}
		return durations;
	}
}
