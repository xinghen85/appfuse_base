package com.btxy.basis.service.base;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mongodb.morphia.dao.DAO;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;

/**
 * This class serves as the Base class for all other Managers - namely to hold
 * common CRUD methods that they might all use. You should only need to extend
 * this class when your require custom CRUD logic.
 * <p/>
 * <p>
 * To register this class in your Spring context file, use the following XML.
 * 
 * <pre>
 *     &lt;bean id="userManager" class="com.btxy.basis.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.btxy.basis.dao.hibernate.GenericDaoHibernate"&gt;
 *                 &lt;constructor-arg value="com.btxy.basis.model.User"/&gt;
 *                 &lt;property name="sessionFactory" ref="sessionFactory"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * <p/>
 * <p>
 * If you're using iBATIS instead of Hibernate, use:
 * 
 * <pre>
 *     &lt;bean id="userManager" class="com.btxy.basis.service.impl.GenericManagerImpl"&gt;
 *         &lt;constructor-arg&gt;
 *             &lt;bean class="com.btxy.basis.dao.ibatis.GenericDaoiBatis"&gt;
 *                 &lt;constructor-arg value="com.btxy.basis.model.User"/&gt;
 *                 &lt;property name="dataSource" ref="dataSource"/&gt;
 *                 &lt;property name="sqlMapClient" ref="sqlMapClient"/&gt;
 *             &lt;/bean&gt;
 *         &lt;/constructor-arg&gt;
 *     &lt;/bean&gt;
 * </pre>
 * 
 * @param <T>
 *            a type variable
 * @param <PK>
 *            the primary key for that type
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by
 *         jgarcia: added full text search + reindexing
 */
public class MgGenericManagerImpl<T, PK extends Serializable> implements MgGenericManager<T, PK> {
	/**
	 * Log variable for all child classes. Uses LogFactory.getLog(getClass())
	 * from Commons Logging
	 */
	protected final Log log = LogFactory.getLog(getClass());

	/**
	 * GenericDao instance, set by constructor of child classes
	 */
	protected DAO<T, PK> dao;

	public MgGenericManagerImpl() {
	}

	public MgGenericManagerImpl(DAO<T, PK> genericDao) {
		this.dao = genericDao;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll() {
		Query<T> q = dao.createQuery();

		q.order("-" + Mapper.ID_KEY);
		return dao.find(q).asList();
	}

	/**
	 * {@inheritDoc}
	 */
	public List<T> getAll(Long library) {
		Query<T> q = dao.createQuery();
		q.or(q.criteria("library").equal(library), q.criteria("overt").equal(true));

		q.order("-" + Mapper.ID_KEY);

		return dao.find(q).asList();
	}

	@Override
	public List<T> find(QueryContditionSet<T> qcs) {
		Query<T> q = dao.createQuery();
		if (qcs != null) {
			qcs.setContdition(q);
		}
		q.order("-" + Mapper.ID_KEY);
		return dao.find(q).asList();
	}

	@Override
	public List<T> find(Long library, QueryContditionSet<T> qcs) {
		Query<T> q = dao.createQuery();
		q.or(q.criteria("library").equal(library), q.criteria("overt").equal(true));
		if (qcs != null) {
			qcs.setContdition(q);
		}
		q.order("-" + Mapper.ID_KEY);

		return dao.find(q).asList();
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize, String orderType, QueryContditionSet<T> qcs) {
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();

		Query<T> q = dao.createQuery();

		if (qcs != null) {
			qcs.setContdition(q);
		}

		Long totalSize = dao.count(q);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);

		if (totalSize > (currentPage - 1) * pageSize) {
			q.order(orderType);
			q.offset((currentPage - 1) * pageSize);
			q.limit(pageSize);

			ph.setList(dao.find(q).asList());

			return ph;
		} else {
			return ph;
		}
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize, QueryContditionSet<T> qcs) {
		return find(currentPage, pageSize, "-" + Mapper.ID_KEY, qcs);
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize, Long library, QueryContditionSet<T> qcs) {
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();

		Query<T> q = dao.createQuery();
		q.or(q.criteria("library").equal(library), q.criteria("overt").equal(true));
		if (qcs != null) {
			qcs.setContdition(q);
		}
		Long totalSize = dao.count(q);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);
		if (totalSize > (currentPage - 1) * pageSize) {
			q.order("-" + Mapper.ID_KEY);
			q.offset((currentPage - 1) * pageSize);
			q.limit(pageSize);
			ph.setList(dao.find(q).asList());
			return ph;
		} else {
			return ph;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public PaginatedListHelper<T> getAll(int currentPage, int pageSize) {

		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();

		Query<T> q = dao.createQuery();
		Long totalSize = dao.count(q);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);

		if (totalSize > (currentPage - 1) * pageSize) {
			q.order("-" + Mapper.ID_KEY);
			q.offset((currentPage - 1) * pageSize);
			q.limit(pageSize);

			ph.setList(dao.find(q).asList());

			return ph;
		} else {
			return ph;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public PaginatedListHelper<T> getAll(int currentPage, int pageSize, Long library) {
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();

		Query<T> q = dao.createQuery();
		q.or(q.criteria("library").equal(library), q.criteria("overt").equal(true));

		Long totalSize = dao.count(q);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);
		if (totalSize > (currentPage - 1) * pageSize) {
			q.order("-" + Mapper.ID_KEY);
			q.offset((currentPage - 1) * pageSize);
			q.limit(pageSize);
			ph.setList(dao.find(q).asList());
			return ph;
		} else {
			return ph;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public T get(PK id) {
		return dao.get(id);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean exists(PK id) {
		return dao.exists("_id", id);
	}

	/**
	 * {@inheritDoc}
	 */
	public void saveMainBody(T object) {
		dao.save(object);
	}

	public T save(T object) {
		dao.save(object);
		return object;
	}

	public T save(T object, boolean ifNew) {
		dao.save(object);
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(T object) {
		dao.delete(object);
	}

	/**
	 * {@inheritDoc}
	 */
	public void remove(PK id) {
		dao.deleteById(id);
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize, Long library, String orderType, QueryContditionSet<T> qcs) {
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();
		Query<T> q = dao.createQuery();
		q.or(q.criteria("library").equal(library), q.criteria("overt").equal(true));
		if (qcs != null) {
			qcs.setContdition(q);
		}
		Long totalSize = dao.count(q);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);
		if (totalSize > (currentPage - 1) * pageSize) {
			q.order(orderType);
			q.offset((currentPage - 1) * pageSize);
			q.limit(pageSize);
			ph.setList(dao.find(q).asList());
			return ph;
		} else {
			return ph;
		}
	}

	@Override
	public Long count(QueryContditionSet<T> qcs) {
		Query<T> q = dao.createQuery();
		if (qcs != null) {
			qcs.setContdition(q);
		}
		Long totalSize = dao.count(q);
		return totalSize;
	}

	@Override
	public Long count(Long library, QueryContditionSet<T> qcs) {
		Query<T> q = dao.createQuery();
		q.or(q.criteria("library").equal(library), q.criteria("overt").equal(true));
		if (qcs != null) {
			qcs.setContdition(q);
		}
		Long totalSize = dao.count(q);
		return totalSize;
	}

}
