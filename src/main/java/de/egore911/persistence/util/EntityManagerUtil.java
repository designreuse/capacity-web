/*
 * Copyright 2013-2015  Christoph Brill <egore911@gmail.com>
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
package de.egore911.persistence.util;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to hold the current entity manager for the duration of a
 * request. Done using a {@link ThreadLocal}.
 * 
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public class EntityManagerUtil {

	private static final Logger log = LoggerFactory
			.getLogger(EntityManagerUtil.class);

	private static ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<>();

	/**
	 * @return the current entity manager for the request
	 * @see #setEntityManager(EntityManager)
	 */
	public static EntityManager getEntityManager() {
		return entityManagerHolder.get();
	}

	/**
	 * @param entityManager
	 *            the current entity manager for the request
	 * @see #getEntityManager()
	 */
	public static void setEntityManager(EntityManager entityManager) {
		if (entityManagerHolder.get() != null) {
			log.error("Replacing existing EntityManger");
		}
		entityManagerHolder.set(entityManager);
	}

	/**
	 * Remove the current entitymanager from the request.
	 */
	public static void clearEntityManager() {
		entityManagerHolder.set(null);
	}
}
