package de.egore911.capacity.ui.dto;

public class ImportResult {

	private int skipped;
	private int created;
	private int updated;
	private int deleted;

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getUpdated() {
		return updated;
	}

	public void setUpdated(int updated) {
		this.updated = updated;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public void skip() {
		skipped++;
	}

	public void create() {
		created++;
	}

	public void update() {
		updated++;
	}

}
