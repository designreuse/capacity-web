package de.egore911.capacity.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

@Entity(name = "Absence")
@Table(name = "absence")
public class AbsenceEntity extends IntegerDbObject {

	private static final long serialVersionUID = 1338275104365902115L;

	private EmployeeEntity employee;
	private LocalDate start;
	private LocalDate end;
	private String reason;
	private String externalId;
	private IcalImportEntity icalImport;

	@ManyToOne
	@JoinColumn(name = "employee_id", nullable = false)
	public EmployeeEntity getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeEntity employee) {
		this.employee = employee;
	}

	@Column(nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getStart() {
		return start;
	}

	public void setStart(LocalDate start) {
		this.start = start;
	}

	@Column(nullable = false, name = "`end`")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	public LocalDate getEnd() {
		return end;
	}

	public void setEnd(LocalDate end) {
		this.end = end;
	}

	@Column(nullable = false)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	@ManyToOne
	@JoinColumn(name = "icalimport_id", nullable = true)
	public IcalImportEntity getIcalImport() {
		return icalImport;
	}

	public void setIcalImport(IcalImportEntity icalImport) {
		this.icalImport = icalImport;
	}

}
