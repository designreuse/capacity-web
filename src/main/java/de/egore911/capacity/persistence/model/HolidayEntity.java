package de.egore911.capacity.persistence.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity(name = "Holiday")
@Table(name = "holiday")
public class HolidayEntity extends IntegerDbObject {

	private static final long serialVersionUID = 3113650984470473152L;

	private LocalDate date;
	private String name;
	private int hoursReduction;
	private List<LocationEntity> locations;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public int getHoursReduction() {
		return hoursReduction;
	}

	public void setHoursReduction(int hoursReduction) {
		this.hoursReduction = hoursReduction;
	}

	@ManyToMany(mappedBy = "holidays")
	public List<LocationEntity> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationEntity> locations) {
		this.locations = locations;
	}

}
