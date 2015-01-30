package com.dynamo2.myerp;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

public abstract class AbstractBaseDAO<T> {

	@SuppressWarnings("unused")
	private static final Log logger = LogFactory.getLog(AbstractBaseDAO.class);

	private Class<T> persistentClass;

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	public AbstractBaseDAO() {
		persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void saveOrUpdate(T ob, String currentAccount) {
		assert null != ob;
		try {
			Method method = persistentClass.getMethod("getId");
			fillInLatestModified(ob, currentAccount);
			Long id = (Long) method.invoke(ob);

			if (null == id) {
				fillInCreated(ob, currentAccount);
			}

			getSession().saveOrUpdate(ob);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	private void fillInCreated(T ob, String currentAccount) {
		try {
			Method setCreatedByMethod = ob.getClass().getMethod("setCreatedBy", String.class);
			Method setCreatedMethod = ob.getClass().getMethod("setCreated", Date.class);
			setCreatedByMethod.invoke(ob, currentAccount);
			setCreatedMethod.invoke(ob, new Date());
		} catch (Exception e) {
			// Do Nothing
		}

	}

	private void fillInLatestModified(T ob, String currentAccount) {
		try {
			Method setLatestModifiedByMethod = ob.getClass().getMethod("setLastModifiedBy", String.class);
			Method setLastModifiedMethod = ob.getClass().getMethod("setLastModified", Date.class);
			setLatestModifiedByMethod.invoke(ob, currentAccount);
			setLastModifiedMethod.invoke(ob, new Date());
		} catch (Exception e) {
			// Do Nothing
		}
	}

	public void removeById(Long id) {
		Query q = em.createQuery("delete from " + persistentClass.getName() + " as e where e.id=" + id);
		q.executeUpdate();
	}

	public T findById(Long id) {
		return (T) em.find(persistentClass, id);
	}

	public T get(String sqlName, Map params) {
		Query query = em.createNamedQuery(sqlName);
		setQueryParameters(query, params);

		List<T> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0)
			return (T) resultList.get(0);

		return null;
	}

	public T getWithoutOwnerId(String sqlName, Map params) {
		Query query = em.createNamedQuery(sqlName);
		setQueryParameters(query, params);

		List<T> resultList = query.getResultList();
		if (resultList != null && resultList.size() > 0)
			return (T) resultList.get(0);

		return null;
	}

	public List<T> listWithoutOwnerId(String sqlName) {
		Query query = em.createNamedQuery(sqlName);
		setQueryParameters(query, (Map) null);
		return query.getResultList();
	}

	public List<T> list(String sqlName) {
		Query query = em.createNamedQuery(sqlName);
		setQueryParameters(query, (Map) null);
		return query.getResultList();
	}

	public List<T> list(String sqlName, Map params) {
		Query query = em.createNamedQuery(sqlName);
		setQueryParameters(query, params);

		return query.getResultList();
	}

	@SuppressWarnings("rawtypes")
	protected List list(String sqlName, Object[] objects) {
		Query query = em.createNamedQuery(sqlName);
		for (int i = 0; i < objects.length; i++) {
			query.setParameter(i + 1, objects[i]);
		}

		return query.getResultList();
	}

	public List<T> pageList(String sqlName, Map params, int index, int pageCount) {
		Query query = em.createNamedQuery(sqlName);
		setQueryParameters(query, params);

		if (index >= 0)
			query = query.setFirstResult(index);
		if (pageCount >= 0)
			query = query.setMaxResults(pageCount);

		return query.getResultList();
	}

	public int rowCount(String sqlName, Map params) {
		return ((Long) this.getQueryWithoutOwnerId(sqlName, params).getSingleResult()).intValue();
	}

	public void executeSQLName(String sqlName, Map params) {
		this.getQueryWithoutOwnerId(sqlName, params).executeUpdate();
	}

	public void executeSQLName(String sqlName, Object[] params) {
		Query query = em.createNamedQuery(sqlName);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		query.executeUpdate();
	}

	public void executeSQL(String sql, Map params) {
		Query query = em.createQuery(sql);
		setQueryParameters(query, params);

		query.executeUpdate();
	}

	private Query getQueryWithoutOwnerId(String sqlName, Map params) {
		Query query = em.createNamedQuery(sqlName);
		this.setQueryParameters(query, params);

		return query;
	}

	private void setQueryParameters(Query query, Map params) {
		if (params != null && params.size() > 0) {
			// TODO Can be optimized to use entrySet
			Iterator ie = params.keySet().iterator();

			while (ie != null && ie.hasNext()) {
				String paramName = (String) ie.next();
				Object paramValue = params.get(paramName);

				query.setParameter(paramName, paramValue);
			}
		}
	}

	public void executeNativeSQL(String sql, Map params) {
		Query query = em.createNativeQuery(sql);
		setQueryParameters(query, params);

		query.executeUpdate();
	}

	/***
	 * Alias for DetachedCriteria
	 * **/
	public DetachedCriteria getDC() {
		DetachedCriteria dc = DetachedCriteria.forClass(persistentClass);

		return dc;
	}

	/***
	 * Alias for DetachedCriteria
	 * **/
	public DetachedCriteria getDCWithoutOwnerId() {
		DetachedCriteria dc = DetachedCriteria.forClass(persistentClass);

		return dc;
	}

	public T load(DetachedCriteria dc) {
		Session session = (Session) this.em.getDelegate();
		return (T) dc.getExecutableCriteria(session).uniqueResult();
	}

	public List<T> list(DetachedCriteria dc) {
		Session session = (Session) this.em.getDelegate();
		boolean needClose = false;
		if (!session.isOpen()) {
			session = session.getSessionFactory().openSession();
			needClose = true;
		}

		try {
			return (List<T>) dc.getExecutableCriteria(session).list();
		} finally {
			if (needClose) {
				session.close();
			}
		}
	}

	public List<T> pageList(DetachedCriteria dc, int index, int pageCount) {
		Session session = (Session) this.em.getDelegate();
		boolean needClose = false;
		if (!session.isOpen()) {
			session = session.getSessionFactory().openSession();
			needClose = true;
		}

		Criteria c = dc.getExecutableCriteria(session);
		if (index >= 0)
			c = c.setFirstResult(index);
		if (pageCount >= 0)
			c = c.setMaxResults(pageCount);

		try {
			return c.list();
		} finally {
			if (needClose) {
				session.close();
			}
		}
	}

	public int resetStatus(long status, Long id) {
		Query q = em.createQuery("update " + this.persistentClass.getSimpleName() + " set status=:status where id=:id");

		Map p = new HashMap();
		p.put("status", status);
		p.put("id", id);
		this.setQueryParameters(q, p);
		return q.executeUpdate();
	}

	public Integer getRowCountOfDCRst(DetachedCriteria dc) {
		Session session = (Session) this.em.getDelegate();
		boolean needClose = false;
		if (!session.isOpen()) {
			session = session.getSessionFactory().openSession();
			needClose = true;
		}

		ScrollableResults sc = null;
		try {
			Criteria c = dc.getExecutableCriteria(session);
			sc = c.scroll(ScrollMode.FORWARD_ONLY);
			sc.last();
			int count = sc.getRowNumber() + 1;

			return count;
		} finally {
			if (sc != null) {
				sc.close();
				sc = null;
			}

			if (needClose) {
				session.close();
				session = null;
			}

		}
	}

	/***
	 * 根据检索条件，查询记录是否存在 *
	 **/
	public boolean exists(DetachedCriteria dc) {
		if (this.getRowCountOfDCRst(dc) > 0)
			return true;

		return false;
	}

	protected Session getSession() {
		return (Session) this.em.getDelegate();
	}

	public List<T> listAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("from ").append(persistentClass.getSimpleName()).append(" t ");

		try {
			Method m = persistentClass.getMethod("getLastModified");
			if (m != null) {
				sql.append(" order by t.lastModified DESC");
			}
		} catch (SecurityException e) {
			// Do nothing
		} catch (NoSuchMethodException e) {
			// Do nothing
		}

		Query q = em.createQuery(sql.toString());

		return q.getResultList();
	}

	public List<T> findByField(String fieldName, Object value) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ").append(persistentClass.getSimpleName()).append(" t ")
				.append(" where t." + fieldName + "=");
		if (value instanceof String) {
			hql.append("'").append(value).append("'");
		} else if (value instanceof Long || value instanceof Integer) {
			hql.append(value.toString());
		} else {
			throw new UnsupportedOperationException("Don't support type" + value.getClass().getName()
					+ " in search.");
		}

		Query q = em.createQuery(hql.toString());

		return q.getResultList();
	}
	
	public List<T> findByFields(String[] fieldNames, Object[] values) {
		StringBuilder hql = new StringBuilder();
		hql.append("from ").append(persistentClass.getSimpleName()).append(" t ")
				.append(" where 1=1 ");
		for (int i=0; i<fieldNames.length; i++) {
			hql.append(" and ").append(fieldNames[i]).append("=");
			Object value = values[i];
			if (value instanceof String) {
				hql.append("'").append(value).append("'");
			} else if (value instanceof Long || value instanceof Integer) {
				hql.append(value.toString());
			} else {
				throw new UnsupportedOperationException("Don't support type" + value.getClass().getName()
						+ " in search.");
			}
		}

		Query q = em.createQuery(hql.toString());

		return q.getResultList();
	}
}
