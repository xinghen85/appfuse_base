package com.btxy.basis.dao.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.btxy.basis.common.model.SearchConditionValue;
import com.btxy.basis.util.list.ListUtil;

public class Sql{
	static Logger log=Logger.getLogger(Sql.class);
	String and=null;
	String or=null;
	List<Object> objs=new ArrayList<Object>();
	List<Object> orObjs=new ArrayList<Object>();
	String tableName="";
	boolean isAddLimit=false;
	public String getLimitSql() {
		String limitsql="select * from "+tableName+getWhere()+" limit ?,?";
		log.debug(limitsql);
		return limitsql.replaceAll("  ", " ").trim();
	}
	public String getSql() {
		String nolimitsql="select count(*) from "+tableName+getWhere();
		log.debug(nolimitsql);
		return nolimitsql.replaceAll("  ", " ").trim();
	}
	public Object[] getLimitObject(long offset,long limit) {
		List<Object> rtn=new ArrayList<Object>();
		rtn.addAll(objs);
		rtn.addAll(orObjs);
		rtn.add(offset);
		rtn.add(limit);
		return rtn.toArray();
	}
	public Object[] getObject() {
		List<Object> rtn=new ArrayList<Object>();
		rtn.addAll(objs);
		rtn.addAll(orObjs);
		return rtn.toArray();
	}
	public Sql(String tableName) {
		this.tableName=tableName;
	}

	private String getWhere() {
		String where="";
		if(!StringUtils.isEmpty(and)||!StringUtils.isEmpty(or)) {
			where=" where ";
			if(!StringUtils.isEmpty(and)) {
				where=where+ and;
				if(!StringUtils.isEmpty(or)) {
					where=where+ " and("+or+")";
				}
			}else {
				where=where+ or;
			}
		}
		return where;
	}
	public void addAndList(SearchConditionValue searchValue, String key, String dbKey) {
		if (searchValue.getCombinedConditionValue().containsKey(key)) {
			Object cvalue = searchValue.getCombinedConditionValue().get(key);
			if (cvalue != null && !cvalue.toString().trim().equals("")) {
				List<String> list = ListUtil.pasreStringList(cvalue.toString(), ",");
				if(list.size()>0) {
					if(!StringUtils.isEmpty(and)) {
						and=and+" and ";
					}else {
						and="";
					}
					if(list.size()>1) {
						and=and+dbKey+" in(";
						for (int i = 0; i < list.size(); i++) {
							if(i==0) {
								and=and+"?";
							}else {
								and=and+",?";
							}
							objs.add(list.get(i));
						}
						and=and+")";
					}else if(list.size()==1) {
						and=and+dbKey+" =?";
						objs.add(list.get(0));
					}
				}
			}
		}
	}
	public void addAndList(Object object, String dbKey) {
		if(!StringUtils.isEmpty(and)) {
			and=and+" and ";
		}else {
			and="";
		}
		and=and+dbKey+" =?";
		objs.add(object);
	}
	public void addSearch(SearchConditionValue searchValue, String dbColumn) {
		if (searchValue.getTextValue() != null) {
			if(!StringUtils.isEmpty(or)) {
				or=or+" or ";
			}else {
				or="";
			}
			or=or+dbColumn+" like ? ";
			orObjs.add("%"+searchValue.getTextValue()+"%");
		}
	}
}
