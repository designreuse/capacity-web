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
package de.egore911.persistence.selector;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;

import de.egore911.persistence.util.EntityManagerUtil;

/**
 * @author Christoph Brill &lt;egore911@gmail.com&gt;
 */
public abstract class AbstractSelector<T> implements Serializable {

	private static final long serialVersionUID = 3479024193093886962L;

	private TypedQuery<T> buildQuery() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = builder.createQuery(getEntityClass());
		Root<T> from = cq.from(getEntityClass());
		List<Predicate> predicates = generatePredicateList(builder, from, cq);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		cq.orderBy(generateOrderList(builder, from));
		cq.select(from);
		return em.createQuery(cq);
	}

	@Nonnull
	public List<T> findAll() {
		TypedQuery<T> q = buildQuery();
		if (limit != null) {
			q.setMaxResults(limit);
		}
		if (offset != null) {
			q.setFirstResult(offset);
		}
		return q.getResultList();
	}

	@Nullable
	public T find() {
		TypedQuery<T> q = buildQuery();
		try {
			return q.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public long count() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = builder.createQuery(Long.class);
		Root<T> from = cq.from(getEntityClass());
		List<Predicate> predicates = generatePredicateList(builder, from, cq);
		cq.where(predicates.toArray(new Predicate[predicates.size()]));
		cq.select(builder.count(from));
		TypedQuery<Long> q = em.createQuery(cq);
		return q.getSingleResult();
	}

	@Nonnull
	protected abstract Class<T> getEntityClass();

	@Nonnull
	protected abstract List<Predicate> generatePredicateList(@Nonnull CriteriaBuilder builder, @Nonnull Root<T> from,
			@Nonnull CriteriaQuery<?> criteriaQuery);

	@Nonnull
	protected List<Order> generateOrderList(@Nonnull CriteriaBuilder builder, @Nonnull Root<T> from) {
		if (StringUtils.isNotEmpty(sortColumn)) {
			if (!Boolean.FALSE.equals(ascending)) {
				return Collections.singletonList(builder.asc(from.get(sortColumn)));
			}
			return Collections.singletonList(builder.desc(from.get(sortColumn)));
		}
		return getDefaultOrderList(builder, from);
	}

	@SuppressWarnings("unused")
	protected List<Order> getDefaultOrderList(@Nonnull CriteriaBuilder builder, @Nonnull Root<T> from) {
		return Collections.emptyList();
	}

	private Integer offset;
	private Integer limit;
	private String sortColumn;
	private Boolean ascending;

	public AbstractSelector<T> withOffset(Integer offset) {
		this.offset = offset;
		return this;
	}

	public AbstractSelector<T> withLimit(Integer limit) {
		this.limit = limit;
		return this;
	}

	public AbstractSelector<T> withSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
		return this;
	}

	public AbstractSelector<T> withAscending(Boolean ascending) {
		this.ascending = ascending;
		return this;
	}

}
