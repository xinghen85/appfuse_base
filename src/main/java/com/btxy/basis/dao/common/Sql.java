package com.btxy.basis.dao.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class Sql{
	static Logger log=Logger.getLogger(Sql.class);
	String and=null;
	String or=null;
	List<Object> andObjs=new ArrayList<Object>();
	List<Object> orObjs=new ArrayList<Object>();
	String tableName="";
	boolean isAddLimit=false;
	String orderList = "";
	public String getLimitSql() {
		String limitsql="select * from "+tableName+getWhere()+orderList +" limit ?,?";
		log.debug(limitsql);
		return limitsql.replaceAll("  ", " ").trim();
	}
	public String getSql() {
		String nolimitsql="select count(*) from "+tableName+getWhere();
		log.debug(nolimitsql);
		return nolimitsql.replaceAll("  ", " ").trim();
	}
	/**
	  * 添加排序
	  * @param orders name desc,age asc
	  */
	 public void addOrderList(String orders){ 
		 if(!StringUtils.isEmpty(orders)) {
			   orderList = " order by "+orders; 
		 }
	 }
	public Object[] getLimitObject(long offset,long limit) {
		List<Object> rtn=new ArrayList<Object>();
		rtn.addAll(andObjs);
		rtn.addAll(orObjs);
		rtn.add(offset);
		rtn.add(limit);
		return rtn.toArray();
	}
	public Object[] getObject() {
		List<Object> rtn=new ArrayList<Object>();
		rtn.addAll(andObjs);
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
	public void addAndList(List<String>list,String dbKey) {
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
					andObjs.add(list.get(i));
				}
				and=and+")";
			}else if(list.size()==1) {
				and=and+dbKey+" =?";
				andObjs.add(list.get(0));
			}
		}
	}
	public void addAndList(Object object, String dbKey) {
		if(object !=null) {
			if(!StringUtils.isEmpty(and)) {
				and=and+" and ";
			}else {
				and="";
			}
			and=and+dbKey+" =?";
			andObjs.add(object);
		}
	}

	public void addAndLike(String text,String dbKey) {
		addAndLike(text,dbKey,false);
	}
	public void addAndLike(String text,String dbKey,boolean hasComma) {
		if (text != null) {
			if (!StringUtils.isEmpty(and)) {
				and = and + " and ";
			} else {
				and = "";
			}
			and = and + dbKey + " like ?";
			if(hasComma) {
				andObjs.add("%," + text + ",%");
			}else {
				andObjs.add("%" + text + "%");
			}
		}
	}
	public void addOrLike(String textValue, String dbColumn) {
		if (textValue != null) {
			if(!StringUtils.isEmpty(or)) {
				or=or+" or ";
			}else {
				or="";
			}
			or=or+dbColumn+" like ? ";
			orObjs.add("%"+textValue+"%");
		}
	}
}
