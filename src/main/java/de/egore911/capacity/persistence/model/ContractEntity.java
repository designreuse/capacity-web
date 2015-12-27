package de.egore911.capacity.persistence.model;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Embeddable
public class ContractEntity {

	private LocalDate start;
	private LocalDate end;
	private int workHoursPerDay;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	public int getWorkHoursPerDay() {
		return workHoursPerDay;
	}

	public void setWorkHoursPerDay(int workHoursPerDay) {
		this.workHoursPerDay = workHoursPerDay;
	}

}
