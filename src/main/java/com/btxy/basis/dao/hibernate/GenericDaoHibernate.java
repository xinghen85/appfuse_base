package com.btxy.basis.dao.hibernate;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.IdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.orm.ObjectRetrievalFailureException;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.dao.GenericDao;

public abstract class GenericDaoHibernate<T,PK extends Serializable> implements GenericDao<T, PK> {
	
    protected final Log log = LogFactory.getLog(getClass());
    private Class<T> persistentClass;
    @Resource
    private SessionFactory sessionFactory;
    private Analyzer defaultAnalyzer;
    
    public GenericDaoHibernate(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_35);
    }
    
    public GenericDaoHibernate(final Class<T> persistentClass, SessionFactory sessionFactory) {
        this.persistentClass = persistentClass;
        this.sessionFactory = sessionFactory;
        defaultAnalyzer = new StandardAnalyzer(Version.LUCENE_35);
    }
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() throws HibernateException {
        Session sess = getSessionFactory().getCurrentSession();
        if (sess == null) {
            sess = getSessionFactory().openSession();
        }
        return sess;
    }

    @Autowired
    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    
	@Override
	public List<T> getAll() {
        Session sess = getSession();
        return sess.createCriteria(persistentClass).list();
	}

	@Override
	public List<T> getAll(Long library) {
    	return null;
	}
	@Override
	public List<T> find(SearchConditionValue searchValue) {
		Criteria cr = getSession().createCriteria(persistentClass);
		assembleQryParam(searchValue,cr);
        return cr.list();
	}
	@Deprecated
	@Override
	public List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(this.getSession());
		return  (List<T>) criteria.list();
	}
	//implements by subclass @Cre 2016-03-07
	protected abstract void assembleQryParam(SearchConditionValue searchValue,Criteria cr);
	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,String orderType, SearchConditionValue searchValue) {
		Criteria cr = getSession().createCriteria(persistentClass); 
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();
    	
		assembleQryParam(searchValue,cr);
		
		Long totalSize = this.count(searchValue);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);
		if (totalSize > (currentPage - 1) * pageSize) {
			int startIndex = (currentPage - 1) * pageSize;
			cr.setFirstResult(startIndex).setMaxResults(pageSize);
			if (StringUtils.isBlank(orderType)) {
				cr.addOrder(Order.desc("id"));// add by liuxf 2016-07-25
			} else {
				cr.addOrder(Order.asc(orderType));
			}
			ph.setList(cr.list());
			return ph;
		} else {
			return ph;
		}
	}
	
	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,SearchConditionValue searchValue) {
		return this.find(currentPage,pageSize,"",searchValue);
	}
	

	@Override
	public PaginatedListHelper<T> getAll(int currentPage, int pageSize) {
    	return this.getAll(currentPage, pageSize,null);
	}

	
	@Override
	public T get(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);

        if (entity == null) {
            log.warn("Uh oh, '" + this.persistentClass + "' object with id '" + id + "' not found...");
            throw new ObjectRetrievalFailureException(this.persistentClass, id);
        }

        return entity;
	}

	@Override
	public boolean exists(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        return entity != null;
	}
	
	
	@Override
	public void saveMainBody(T object) {
		Session sess = getSession();
		sess.save(object);
	}


	@Override
	public T save(T object, boolean ifNew) {
		   Session sess = getSession();
	       return (T) sess.merge(object);
	}

	@Override
	public void remove(T object) {
        Session sess = getSession();
        sess.delete(object);	
	}

	@Override
	public void remove(PK id) {
        Session sess = getSession();
        IdentifierLoadAccess byId = sess.byId(persistentClass);
        T entity = (T) byId.load(id);
        sess.delete(entity);	
	}
	
	
	@Override
	public Long count(SearchConditionValue searchValue) {
		Criteria cr = getSession().createCriteria(persistentClass);
		
	    	assembleQryParam(searchValue,cr);
	    	cr.setProjection(Projections.rowCount());
	    	return (Long)(cr.list().get(0));

	}
	
	@Override
	public List<T> find(Long library, SearchConditionValue searchValue) {
		return null;
	}
	
	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,Long library, SearchConditionValue searchValue) {
		return null;
	}
	@Override
	public PaginatedListHelper<T> getAll(int currentPage, int pageSize,Long library) {
		return null;
	}
	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,Long library, String orderType, SearchConditionValue searchValue) {
		return null;
	}	
   
}
