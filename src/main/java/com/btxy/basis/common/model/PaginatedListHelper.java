package com.btxy.basis.common.model;

import java.util.ArrayList;
import java.util.List;

import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.SortOrderEnum;

public class PaginatedListHelper<T> implements PaginatedList {

	private int fullListSize; // 总记录数
    private List<T> list=new ArrayList<T>(); // 每页列表
    private int objectsPerPage;// 每页记录数 page size
    private int pageNumber = 1; // 当前页码
    private String searchId;
    private String sortCriterion;
    private SortOrderEnum sortDirection;
    public int getFullListSize() {
        return fullListSize;
    }
    public void setFullListSize(int fullListSize) {
        this.fullListSize = fullListSize;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
    public int getObjectsPerPage() {
        return objectsPerPage;
    }
    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }
    public int getPageNumber() {
        return pageNumber;
    }
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    public String getSearchId() {
        return searchId;
    }
    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }
    public String getSortCriterion() {
        return sortCriterion;
    }
    public void setSortCriterion(String sortCriterion) {
        this.sortCriterion = sortCriterion;
    }
    public SortOrderEnum getSortDirection() {
        return sortDirection;
    }
    public void setSortDirection(SortOrderEnum sortDirection) {
        this.sortDirection = sortDirection;
    }
	

}
