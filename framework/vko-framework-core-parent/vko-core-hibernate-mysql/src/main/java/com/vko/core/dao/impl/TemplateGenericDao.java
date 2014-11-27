package com.vko.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.vko.core.entity.Page;

public class TemplateGenericDao<T> extends GenericHibernateDao {
	protected final Class<T> clazz;

	/**
	 * Inject domain's class type in constructor.
	 * 
	 * @param clazz
	 *            Domain's class.
	 */
	public TemplateGenericDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T query(Serializable id) {
		T t = (T) hibernateTemplate.get(clazz, id);
		/*
		 * if (t == null) throw new
		 * DataRetrievalFailureException("Object not found.");
		 */
		// it is strange that load() method return a lazy-loading proxy object
		// and it may cause LazyInitializationException!
		return t;
	}

	/**
	 * Default implementation of creating new domain object.
	 */
	public void create(T t) {
		hibernateTemplate.save(t);
	}

	/**
	 * Default implementation of deleting new domain object.
	 */
	public void delete(T t) {
		hibernateTemplate.delete(t);
	}

	/**
	 * Default implementation of updating domain object.
	 */
	public void update(T t) {
		hibernateTemplate.update(t);
	}

	/**
	 * Do an update hql query, return the affected rows.
	 * 
	 * @param updateHql
	 *            Update HQL.
	 * @param values
	 *            Parameters or null if none.
	 * @return The affected rows.
	 */
	protected int executeUpdate(final String updateHql, final Object[] values) {
		HibernateCallback updateCallback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(updateHql);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return new Integer(query.executeUpdate());
			}
		};
		return ((Integer) hibernateTemplate.execute(updateCallback)).intValue();
	}

	/**
	 * Prepared for sub-class for convenience. Query for list and also return
	 * total results' number.
	 * 
	 * @param selectCount
	 *            HQL for "select count(*) from ..." and should return a Long.
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	@SuppressWarnings("unchecked")
	protected List queryForList(final String selectCount, final String select,
			final Object[] values, final Page page) {
		Long count = (Long) queryForObject(selectCount, values);
		if (null != count)
			page.setTotalCount(count.intValue());
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForList(select, values, page);
	}

	/**
	 * Prepared for sub-class for convenience. Query for list and also return
	 * total results' number.
	 * 
	 * @param selectCount
	 *            HQL for "select count(*) from ..." and should return a Long.
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	@SuppressWarnings("unchecked")
	protected List queryForList(final int count, final String select,
			final Object[] values, final Page page) {
		// Long count = (Long) queryForObject(selectCount, values);
		// if(null != count)
		page.setTotalCount(count);
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForList(select, values, page);
	}

	/**
	 * Prepared for sub-class for convenience. Query for list and also return
	 * total results' number.
	 * 
	 * @param selectCount
	 *            HQL for "select count(*) from ..." and should return a Long.
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	@SuppressWarnings("unchecked")
	protected List queryForListAndLike(final String selectCount,
			final String select, final Object[] values, final Page page) {
		Long count = (Long) queryForObjectAndLike(selectCount, values);
		page.setTotalCount(count.intValue());
		if (page.isEmpty())
			return Collections.EMPTY_LIST;
		return queryForListAndLike(select, values, page);
	}

	/**
	 * Prepared for sub-class for convenience. Query for unique result.
	 * 
	 * @param select
	 *            HQL for "select * from ..." and should return unique object.
	 * @param values
	 *            For prepared statements.
	 */
	protected Object queryForObjectAndLike(final String select,
			final Object[] values) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						// query.setParameter(i, values[i]);
						query.setString(0, "%" + values[i] + "%");
				}
				return query.uniqueResult();
			}
		};
		return hibernateTemplate.execute(selectCallback);
	}

	/**
	 * Prepared for sub-class for convenience. Query for list but do not return
	 * total results' number.
	 * 
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	@SuppressWarnings("unchecked")
	protected List queryForListAndLike(final String select,
			final Object[] values, final Page page) {
		// select:
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						// query.setParameter(i, values[i]);
						query.setString(0, "%" + values[i] + "%");
				}
				return query.setFirstResult(page.getFirstResult())
						.setMaxResults(page.getPageSize()).list();
			}
		};
		return (List) hibernateTemplate.executeFind(selectCallback);
	}

	/**
	 * Prepared for sub-class for convenience. Query for list but do not return
	 * total results' number.
	 * 
	 * @param select
	 *            HQL for "select * from ..." and should return object list.
	 * @param values
	 *            For prepared statements.
	 * @param page
	 *            Page object for store page information.
	 */
	@SuppressWarnings("unchecked")
	protected List queryForList(final String select, final Object[] values,
			final Page page) {
		// select:
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return query.setFirstResult(page.getFirstResult())
						.setMaxResults(page.getPageSize()).list();
			}
		};
		return (List) hibernateTemplate.executeFind(selectCallback);
	}

	/**
	 * Prepared for sub-class for convenience. Query for unique result.
	 * 
	 * @param select
	 *            HQL for "select * from ..." and should return unique object.
	 * @param values
	 *            For prepared statements.
	 */
	protected Object queryForObject(final String select, final Object[] values) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (values != null) {
					for (int i = 0; i < values.length; i++)
						query.setParameter(i, values[i]);
				}
				return query.uniqueResult();
			}
		};
		return hibernateTemplate.execute(selectCallback);
	}

	protected Object queryForObject(final DetachedCriteria dc) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return dc.getExecutableCriteria(session).uniqueResult();
			}
		};
		return hibernateTemplate.execute(callback);
	}

	/**
	 * Prepared for sub-class for convenience.
	 */
	@SuppressWarnings("unchecked")
	protected List queryForList(final DetachedCriteria dc, final Page page) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Criteria c = dc.getExecutableCriteria(session);
				if (page == null)
					return c.list();
				return PaginationCriteria.query(c, page);
			}
		};
		return hibernateTemplate.executeFind(callback);
	}

	/**
	 * Prepared for sub-class for convenience.
	 */
	protected Object uniqueResult(final DetachedCriteria dc) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				return dc.getExecutableCriteria(session).uniqueResult();
			}
		};
		return hibernateTemplate.execute(callback);
	}
}

/**
 * A PaginationCriteria can execute both "select count(*)" and "select * from"
 * queries, and set Page object automatically. This class uses reflect to remove
 * and restore "order by" conditions.
 * 
 * @author sunyi
 */
class PaginationCriteria {

	private static final Logger logger = LoggerFactory
			.getLogger(PaginationCriteria.class);

	private static Field orderEntriesField = getField(Criteria.class,
			"orderEntries");

	@SuppressWarnings("unchecked")
	public static List query(Criteria c, Page page) {
		// first we must detect if any Order defined:
		// Hibernate code: private List orderEntries = new ArrayList();
		List _old_orderEntries = (List) getFieldValue(orderEntriesField, c);
		boolean restore = false;
		if (_old_orderEntries.size() > 0) {
			restore = true;
			setFieldValue(orderEntriesField, c, new ArrayList());
		}
		c.setProjection(Projections.rowCount());
		int rowCount = ((Long) c.uniqueResult()).intValue();
		page.setTotalCount(rowCount);
		if (rowCount == 0) {
			// no need to execute query:
			return Collections.EMPTY_LIST;
		}
		// query:
		if (restore) {
			// restore order conditions:
			setFieldValue(orderEntriesField, c, _old_orderEntries);
		}
		return c.setFirstResult(page.getFirstResult()).setMaxResults(
				page.getPageSize()).setFetchSize(page.getPageSize()).list();
	}

	@SuppressWarnings("unchecked")
	private static Field getField(Class clazz, String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (Exception e) {
			logger.warn("Class.getDeclaredField(String) failed.", e);
			throw new RuntimeException(e);
		}
	}

	private static Object getFieldValue(Field field, Object obj) {
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (Exception e) {
			logger.warn("Field.get(Object) failed.", e);
			throw new RuntimeException(e);
		}
	}

	private static void setFieldValue(Field field, Object target, Object value) {
		field.setAccessible(true);
		try {
			field.set(target, value);
		} catch (Exception e) {
			logger.warn("Field.set(Object, Object) failed.", e);
			throw new RuntimeException(e);
		}
	}
}
