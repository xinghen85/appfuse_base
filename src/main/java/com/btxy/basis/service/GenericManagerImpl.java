package com.btxy.basis.service;

import java.io.Serializable;
import java.util.List;

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
	public List<T> getAll() {
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
	public Long count(SearchConditionValue searchValue) {
		return dao.count(searchValue);
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
	


}
