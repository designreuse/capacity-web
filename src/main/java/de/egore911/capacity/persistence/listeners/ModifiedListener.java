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
package de.egore911.capacity.persistence.listeners;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import de.egore911.capacity.persistence.model.DbObject;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class ModifiedListener {

	@PrePersist
	public void prePersist(DbObject<?> o) {
		o.setCreated(LocalDateTime.now());
		o.setModified(o.getCreated());

		//Subject user = SecurityUtils.getSubject();
		//if (user.isAuthenticated()) {
			//o.setCreatedBy(user.get);
			//o.setModifiedBy(user);
		//}
	}

	@PreUpdate
	public void preUpdate(DbObject<?> o) {
		o.setModified(LocalDateTime.now());

		//Subject user = SecurityUtils.getSubject();
		//if (user.isAuthenticated()) {
			//o.setModifiedBy(user);
		//}
	}

}
