package de.egore911.capacity.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import de.egore911.capacity.persistence.dao.EmployeeDao;
import de.egore911.capacity.persistence.model.EmployeeEntity;
import de.egore911.capacity.persistence.model.HolidayEntity;
import de.egore911.capacity.persistence.selector.HolidaySelector;
import de.egore911.capacity.ui.exceptions.BadArgumentException;
import de.egore911.capacity.ui.exceptions.NullArgumentException;

@Path("capacity")
public class CapacityService {

	@GET
	@Path("workinghours/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public int getWorkingHours(@PathParam("employeeId") Integer employeeId) {
		return getWorkingHours(employeeId, LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), null);
	}

	/**
	 * 
	 * @param start
	 *            start date (inclusive)
	 * @param end
	 *            end date (inclusive)
	 */
	@POST
	@Path("workinghours/{employeeId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public int getWorkingHours(@PathParam("employeeId") Integer employeeId, @FormParam("start") LocalDate start,
			@FormParam("end") LocalDate end, @FormParam("locationId") Integer locationId) {
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

		int workinghours = getWorkingHours(start, end, employee);

		List<HolidayEntity> holidays = new HolidaySelector().withStartInclusive(start).withEndInclusive(end)
				.withLocationId(locationId).findAll();

		for (HolidayEntity holiday : holidays) {
			if (holiday.getDate().isBefore(end) && holiday.getDate().isAfter(start)) {
				workinghours -= holiday.getHoursReduction();
			}
		}

		return workinghours;
	}

	private int getWorkingHours(LocalDate start, LocalDate end, EmployeeEntity employee) {
		int workinghours = 0;
		LocalDate date = start;
		while (date.isBefore(end)) {
			int dayOfWeek = date.getDayOfWeek();
			if (dayOfWeek != DateTimeConstants.SATURDAY && dayOfWeek != DateTimeConstants.SUNDAY) {
				workinghours += employee.getContract().getWorkHoursPerDay();
			}
			date = date.plusDays(1);
		}
		return workinghours;
	}
}
