package com.btxy.basis.service;

import java.io.Serializable;
import java.util.List;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.SearchConditionValue;


public interface GenericManager<T, PK extends Serializable> {
	  /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> find(SearchConditionValue searchValue);
    
    List<T> getAll();
    
    PaginatedListHelper<T> find(int currentPage,int pageSize,SearchConditionValue searchValue);
    PaginatedListHelper<T> find(int currentPage,int pageSize,String orderType,SearchConditionValue searchValue);
    
    Long count(SearchConditionValue searchValue);
    T get(PK id);

    boolean exists(PK id);
    void saveMainBody(T object);
    T save(T object);
    T save(T object,boolean ifNew);
    void remove(T object);

    void remove(PK id);
    

}
