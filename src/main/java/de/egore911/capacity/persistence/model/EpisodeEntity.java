package de.egore911.capacity.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity(name = "Episode")
@Table(name = "episode")
public class EpisodeEntity extends IntegerDbObject {

	private static final long serialVersionUID = -8378906131677316919L;

	private LocalDate start;
	private LocalDate end;
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
