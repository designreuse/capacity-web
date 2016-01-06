package de.egore911.capacity.persistence.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.collection.internal.PersistentBag;
import org.joda.time.LocalDate;

@Entity(name = "Episode")
@Table(name = "episode")
public class EpisodeEntity extends IntegerDbObject {

	private static final long serialVersionUID = -8378906131677316919L;

	private LocalDate start;
	private LocalDate end;
	private String name;
	private List<EmployeeEpisodeEntity> employeeEpisodes;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	@Column(name = "`end`")
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

	@OneToMany(mappedBy = "episode", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<EmployeeEpisodeEntity> getEmployeeEpisodes() {
		return employeeEpisodes;
	}

	public void setEmployeeEpisodes(List<EmployeeEpisodeEntity> employeeEpisodes) {
		if (this.employeeEpisodes != null && this.employeeEpisodes instanceof PersistentBag &&
				employeeEpisodes != null && !(employeeEpisodes instanceof PersistentBag)) {
			this.employeeEpisodes.clear();
			this.employeeEpisodes.addAll(employeeEpisodes);
		} else {
			this.employeeEpisodes = employeeEpisodes;
		}
	}

}
