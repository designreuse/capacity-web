package de.egore911.capacity.persistence.model;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Type;
import org.joda.time.LocalTime;

@Embeddable
public class WorkingHoursEntity {

	private int dayOfWeek;
	private LocalTime start;
	private LocalTime end;

	public int getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	public LocalTime getStart() {
		return start;
	}

	public void setStart(LocalTime start) {
		this.start = start;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
	public LocalTime getEnd() {
		return end;
	}

	public void setEnd(LocalTime end) {
		this.end = end;
	}

}
