package de.egore911.capacity.persistence.model;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity(name = "Employee")
@Table(name = "employee")
public class EmployeeEntity extends IntegerDbObject {

	private static final long serialVersionUID = 6250519976635138525L;

	private String name;
	private String email;
	private Set<AbilityEntity> abilities;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ElementCollection
	@CollectionTable(name = "ability", joinColumns = @JoinColumn(name = "employee_id") )
	public Set<AbilityEntity> getAbilities() {
		return abilities;
	}

	public void setAbilities(Set<AbilityEntity> abilities) {
		this.abilities = abilities;
	}
}
