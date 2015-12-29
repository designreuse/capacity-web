package de.egore911.capacity.persistence.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "EmployeeEpisode")
@Table(name = "employee_episode")
public class EmployeeEpisodeEntity extends IntegerDbObject {

	private static final long serialVersionUID = 3638573182102449011L;

	private EmployeeEntity employee;
	private EpisodeEntity episode;
	private Integer velocity;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	public EmployeeEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}

	@ManyToOne
	@JoinColumn(name = "episode_id", nullable = false)
	public EpisodeEntity getEpisode() {
		return episode;
	}

	public void setEpisode(EpisodeEntity episode) {
		this.episode = episode;
	}

	public Integer getVelocity() {
		return velocity;
	}

	public void setVelocity(Integer velocity) {
		this.velocity = velocity;
	}

}
