package com.btxy.basis.dao;

import java.io.Serializable;
import java.util.List;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.SearchConditionValue;

public interface GenericDao<T,PK extends Serializable> {
    List<T> find(SearchConditionValue searchValue);
    
    List<T> getAll();
    
    PaginatedListHelper<T> find(int currentPage,int pageSize,SearchConditionValue searchValue);
    PaginatedListHelper<T> find(int currentPage,int pageSize,String orderType,SearchConditionValue searchValue);
    
    Long count(SearchConditionValue searchValue);
    T get(PK id);

    boolean exists(PK id);
    void saveMainBody(T object);
    public T save(T object,boolean ifNew);
    void remove(T object);

    void remove(PK id);
    
    
}
