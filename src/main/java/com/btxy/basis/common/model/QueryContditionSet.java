package com.btxy.basis.common.model;

import org.mongodb.morphia.query.Query;

public interface QueryContditionSet<T> {
	public void setContdition(Query<T> q);
}
