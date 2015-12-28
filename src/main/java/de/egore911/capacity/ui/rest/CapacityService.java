package de.egore911.capacity.ui.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.AbsenceEntity;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.selector.AbsenceSelector;
import de.egore911.capacity.persistence.selector.EmployeeSelector;
import de.egore911.capacity.persistence.selector.HolidaySelector;
import de.egore911.capacity.ui.dto.Employee;
import de.egore911.capacity.ui.dto.WorkingHours;
import de.egore911.capacity.ui.dto.WorkingHoursDetails;
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
			@DefaultValue("") @QueryParam("end") LocalDate end) {
		if (start == null) {
			start = LocalDate.now().minusDays(10);
		}
		if (end == null) {
			end = LocalDate.now().plusDays(10);
		}

		List<EmployeeEntity> employees = new EmployeeSelector()
				.withActiveContract(start, end)
				.withIds(employeeIds)
				.findAll();

		List<WorkingHoursPerEmployee> result = new ArrayList<>(employees.size());
		for (EmployeeEntity employee : employees) {
			result.add(new WorkingHoursPerEmployee(getMapper().map(employee, Employee.class), getWorkingHoursForEmployee(employee.getId(), start, end)));
		}

		return result;
	}

	@GET
	@Path("workinghours/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public WorkingHours getWorkingHoursForEmployee(@PathParam("employeeId") Integer employeeId,
			@QueryParam("start") LocalDate start,
			@QueryParam("end") LocalDate end) {
		if (start == null || end == null) {
			throw new NullArgumentException("start and end");
		}

		if (!start.isBefore(end)) {
			throw new BadArgumentException("start must be before end");
		}

		if (employeeId == null) {
			throw new NullArgumentException("employeeId");
		}

		EmployeeEntity employee = new EmployeeDao().findById(employeeId);
		if (employee == null) {
			throw new BadArgumentException("Employee with ID " + employeeId + " not found");
		}

		Map<LocalDate, Integer> reductions = new HashMap<>();

		List<HolidayEntity> holidays = new HolidaySelector().withStartInclusive(start).withEndInclusive(end)
				.withLocation(employee.getLocation()).findAll();

		for (HolidayEntity holiday : holidays) {
			if (holiday.getDate().isBefore(end) && holiday.getDate().isAfter(start)) {
				reductions.put(holiday.getDate(), holiday.getHoursReduction());
			}
		}

		List<AbsenceEntity> absences = new AbsenceSelector().withEmployeeId(employeeId).findAll();
		for (AbsenceEntity absence : absences) {
			LocalDate date = absence.getStart();
			while (date.isBefore(end) && !date.isAfter(absence.getEnd())) {
				if (!date.isBefore(start)) {
					reductions.put(date, employee.getContract().getWorkHoursPerDay());
				}
				date = date.plusDays(1);
			}
		}

		WorkingHours workinghours = getWorkingHoursForEmployee(start, end, employee, reductions);

		return workinghours;
	}

	private WorkingHours getWorkingHoursForEmployee(LocalDate start, LocalDate end, EmployeeEntity employee,
			Map<LocalDate, Integer> reductions) {
		int workinghours = 0;
		List<WorkingHoursDetails> details = new ArrayList<>();

		LocalDate date = start;
		while (date.isBefore(end)) {
			int workinghoursOfDay;
			int dayOfWeek = date.getDayOfWeek();
			if (dayOfWeek != DateTimeConstants.SATURDAY && dayOfWeek != DateTimeConstants.SUNDAY) {
				workinghoursOfDay = employee.getContract().getWorkHoursPerDay();
				if (reductions.containsKey(date)) {
					workinghoursOfDay -= reductions.get(date);
				}
			} else {
				workinghoursOfDay = 0;
			}
			workinghours += workinghoursOfDay;
			details.add(new WorkingHoursDetails(date, workinghoursOfDay));
			date = date.plusDays(1);
		}
		return new WorkingHours(start, end, workinghours, details);
	}
}
