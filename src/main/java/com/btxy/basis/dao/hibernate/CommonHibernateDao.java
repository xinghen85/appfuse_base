package com.btxy.basis.dao.hibernate;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.SearchConditionValue;
@Service("commonHibernateDao")
public class CommonHibernateDao {
	public interface Para{
		public void assembleQryParam(SearchConditionValue searchValue,Criteria cr);
	}
    @Resource
    private SessionFactory sessionFactory;
    private Session getSession() {
        Session sess = sessionFactory.getCurrentSession();
        if (sess == null) {
            sess = sessionFactory.openSession();
        }
        return sess;
    }
	public <T> Long count(final Class<T> persistentClass,SearchConditionValue searchValue,Para Para) {
		Criteria cr = getSession().createCriteria(persistentClass);
		
		Para.assembleQryParam(searchValue,cr);
	    	cr.setProjection(Projections.rowCount());
	    	return (Long)(cr.list().get(0));

	}
	@Transactional
	public <T> PaginatedListHelper<T> find(final Class<T> persistentClass,int currentPage, int pageSize, SearchConditionValue searchValue,Para para) {
		return find(persistentClass, currentPage, pageSize, "", searchValue, para);
	}
	@Transactional
	public <T> PaginatedListHelper<T> find(final Class<T> persistentClass,int currentPage, int pageSize,String orderType, SearchConditionValue searchValue,Para para) {
		Criteria cr = getSession().createCriteria(persistentClass); 
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();
    	
		para.assembleQryParam(searchValue,cr);
		
		Long totalSize = this.count(persistentClass,searchValue,para);
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);
		if (totalSize > (currentPage - 1) * pageSize) {
			int startIndex = (currentPage - 1) * pageSize;
			cr.setFirstResult(startIndex).setMaxResults(pageSize);
			if (StringUtils.isBlank(orderType)) {
				cr.addOrder(Order.desc("id"));// add by liuxf 2016-07-25
			} else {
				String[] orders = orderType.split(",");// module|1,channel|0(1正序0倒序)
				for (String str : orders) {
					String[] aa = str.split("\\|");
					if (aa[1].equals("1")) {
						cr.addOrder(Order.asc(aa[0]));
					} else {
						cr.addOrder(Order.desc(aa[0]));
					}
				}
			}
			ph.setList(cr.list());
			return ph;
		} else {
			return ph;
		}
	}

}
