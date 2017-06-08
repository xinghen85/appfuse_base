package com.btxy.basis.service.base;

import java.io.Serializable;
import java.util.List;

import com.btxy.basis.common.model.PaginatedListHelper;
import com.btxy.basis.common.model.QueryContditionSet;

/**
 * Generic Manager that talks to GenericDao to CRUD POJOs.
 *
 * <p>Extend this interface if you want typesafe (no casting necessary) managers
 * for your domain objects.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Updated by jgarcia: added full text search + reindexing
 * @param <T> a type variable
 * @param <PK> the primary key for that type
 */
public interface MgGenericManager<T, PK extends Serializable> {

    /**
     * Generic method used to get all objects of a particular type. This
     * is the same as lookup up all rows in a table.
     * @return List of populated objects
     */
    List<T> find(QueryContditionSet<T> qcs);
    List<T> find(Long library,QueryContditionSet<T> qcs);
    
    List<T> getAll();
    List<T> getAll(Long library);
    
    PaginatedListHelper<T> find(int currentPage,int pageSize,QueryContditionSet<T> qcs);
    PaginatedListHelper<T> find(int currentPage,int pageSize,String orderType,QueryContditionSet<T> qcs);
    PaginatedListHelper<T> find(int currentPage,int pageSize,Long library,QueryContditionSet<T> qcs);
    PaginatedListHelper<T> find(int currentPage,int pageSize,Long library,String orderType,QueryContditionSet<T> qcs);
    PaginatedListHelper<T> getAll(int currentPage,int pageSize);
    PaginatedListHelper<T> getAll(int currentPage,int pageSize,Long library);
    
    Long count(QueryContditionSet<T> qcs);
    Long count(Long library,QueryContditionSet<T> qcs);
    /**
     * Generic method to get an object based on class and identifier. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if
     * nothing is found.
     *
     * @param id the identifier (primary key) of the object to get
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    T get(PK id);

    /**
     * Checks for existence of an object of type T using the id arg.
     * @param id the identifier (primary key) of the object to get
     * @return - true if it exists, false if it doesn't
     */
    boolean exists(PK id);

    /**
     * Generic method to save an object - handles both update and insert.
     * @param object the object to save
     * @return the updated object
     */
    void saveMainBody(T object);
    T save(T object);
    T save(T object,boolean ifNew);

    /**
     * Generic method to delete an object
     * @param object the object to remove
     */
    void remove(T object);

    /**
     * Generic method to delete an object based on class and id
     * @param id the identifier (primary key) of the object to remove
     */
    void remove(PK id);

    /**
     * Generic method to search for an object.
     * @param searchTerm the search term
     * @param clazz type of class to search for.
     * @return a list of matched objects
     */
    /*List<T> search(String searchTerm, Class clazz);*/
    /**
     * Generic method to regenerate full text index of the persistent class T
     */
    /*void reindex();*/

    /**
     * Generic method to regenerate full text index of all indexed classes
     *
     * @param async
     *            true to perform the reindexing asynchronously
     */
   /* void reindexAll(boolean async);*/
}
