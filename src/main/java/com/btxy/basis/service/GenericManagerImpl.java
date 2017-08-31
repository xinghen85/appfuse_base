package com.btxy.basis.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.dao.GenericDao;

public class GenericManagerImpl<T,PK extends Serializable> implements GenericManager<T,PK > {
	private GenericDao<T,PK> dao;
	public GenericManagerImpl(GenericDao<T,PK> dao){
		this.dao = dao;
	}
	@Override
	public List<T> find(SearchConditionValue searchValue) {
		return dao.find(searchValue);
	}

	@Override
	public List<T> find(Long library, SearchConditionValue searchValue) {
		return dao.find(library, searchValue);
	}

	@Override
	public List<T> getAll() {
		return dao.getAll();
	}

	@Override
	public List<T> getAll(Long library) {
		return dao.getAll();
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,
			SearchConditionValue searchValue) {
		return dao.find(currentPage, pageSize, searchValue);
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,
			String orderType, SearchConditionValue searchValue) {
		return dao.find(currentPage, pageSize, orderType, searchValue);
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,
			Long library, SearchConditionValue searchValue) {
		return dao.find(currentPage, pageSize, library, searchValue);
	}

	@Override
	public PaginatedListHelper<T> find(int currentPage, int pageSize,
			Long library, String orderType, SearchConditionValue searchValue) {
		return dao.find(currentPage, pageSize, library, orderType, searchValue);
	}

	@Override
	public PaginatedListHelper<T> getAll(int currentPage, int pageSize) {
		return dao.getAll(currentPage, pageSize);
	}

	@Override
	public PaginatedListHelper<T> getAll(int currentPage, int pageSize,
			Long library) {
		// TODO Auto-generated method stub
		return dao.getAll(currentPage, pageSize, library);
	}

	@Override
	public Long count(SearchConditionValue searchValue) {
		return dao.count(searchValue);
	}

	@Override
	public Long count(Long library, SearchConditionValue searchValue) {
		// TODO Auto-generated method stub
		return dao.count(library, searchValue);
	}

	@Override
	public T get(PK id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	@Override
	public boolean exists(PK id) {
		return dao.exists(id);
	}

	@Override
	public void saveMainBody(T object) {
		dao.saveMainBody(object);
	}

	@Override
	public T save(T object) {
		return dao.save(object,true);
	}

	@Override
	public T save(T object, boolean ifNew) {
		return dao.save(object, ifNew);
	}

	@Override
	public void remove(T object) {
		dao.remove(object);
	}

	@Override
	public void remove(PK id) {
		dao.remove(id);
	}
	@Deprecated
	@Override
	public List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return dao.findByDetachedCriteria(detachedCriteria);
	} 

	


}
