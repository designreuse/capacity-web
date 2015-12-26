package de.egore911.capacity.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AbilityEntity {

	private String name;

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
