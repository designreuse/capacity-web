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
package de.egore911.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.egore911.persistence.selector.AbstractSelector;
import de.egore911.persistence.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractDao<T> {

	private static final Logger log = LoggerFactory
			.getLogger(AbstractDao.class);

	public List<T> findAll() {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s", getClass().getSimpleName());
		}
		return createSelector().findAll();
	}

	public List<T> findAll(int offset, int limit) {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s from {} to {}", getClass()
					.getSimpleName(), offset, offset + limit);
		}
		AbstractSelector<T> selector = createSelector();
		selector.setOffset(offset);
		selector.setLimit(limit);
		return selector.findAll();
	}

	public List<T> findAll(int offset, int limit, String sortColumn, boolean asc) {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s from {} to {}", getClass()
					.getSimpleName(), offset, offset + limit);
		}
		AbstractSelector<T> selector = createSelector();
		selector.setOffset(offset);
		selector.setLimit(limit);
		selector.setSortColumn(sortColumn);
		selector.setAscending(asc);
		return selector.findAll();
	}

	public T findById(Integer id) {
		if (log.isTraceEnabled()) {
			log.trace("Selecting {}", getClass().getSimpleName());
		}
		EntityManager em = EntityManagerUtil.getEntityManager();
		return em.find(getEntityClass(), id);
	}

	public long count() {
		if (log.isTraceEnabled()) {
			log.trace("Selecting all {}s", getClass().getSimpleName());
		}
		return createSelector().count();
	}

	public T save(T entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		boolean ourOwnTransaction = !em.getTransaction().isActive();
		if (ourOwnTransaction) {
			em.getTransaction().begin();
		}
		try {
			entity = em.merge(entity);
			if (ourOwnTransaction) {
				em.getTransaction().commit();
			}
			return entity;
		} finally {
			if (ourOwnTransaction && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	public T reattach(T entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		if (!em.contains(entity)) {
			entity = em.merge(entity);
		}
		return entity;
	}

	public void remove(T entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		boolean ourOwnTransaction = !em.getTransaction().isActive();
		if (ourOwnTransaction) {
			em.getTransaction().begin();
		}
		try {
			em.remove(entity);
			if (ourOwnTransaction) {
				em.getTransaction().commit();
			}
		} finally {
			if (ourOwnTransaction && em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	protected abstract Class<T> getEntityClass();

	protected abstract AbstractSelector<T> createSelector();

}
