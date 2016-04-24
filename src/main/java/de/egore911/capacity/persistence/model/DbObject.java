/*
 * Copyright 2013  Christoph Brill <egore911@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.egore911.capacity.persistence.model;

import java.io.Serializable;

import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.Hibernate;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import de.egore911.capacity.persistence.listeners.ModifiedListener;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
@MappedSuperclass
@EntityListeners({ ModifiedListener.class })
public abstract class DbObject<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = -3422412128666825823L;

	private LocalDateTime created;
	private LocalDateTime modified;
	private UserEntity createdBy;
	private UserEntity modifiedBy;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	public LocalDateTime getModified() {
		return modified;
	}

	public void setModified(LocalDateTime modified) {
		this.modified = modified;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "id_creator", nullable = true)
	public UserEntity getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserEntity createdBy) {
		this.createdBy = createdBy;
	}

	@ManyToOne(optional = true)
	@JoinColumn(name = "id_modificator", nullable = true)
	public UserEntity getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(UserEntity modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Transient
	public abstract ID getId();

	public abstract void setId(ID id);

	@Override
	public int hashCode() {
		if (getId() == null) {
			return super.hashCode();
		}
		final int prime = 31;
		int result = 1;
		result = prime * result + getId().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (Hibernate.getClass(this) != Hibernate.getClass(obj)) {
			return false;
		}
		DbObject<?> other = (DbObject<?>) obj;
		if (getId() == null) {
			return false;
		} else if (!getId().equals(other.getId())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getSimpleName());
		if (getId() != null) {
			builder.append("@").append(getId());
		} else {
			builder.append("#").append(hashCode());
		}
		return builder.toString();
	}

}
