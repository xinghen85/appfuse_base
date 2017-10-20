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

	public String getNoLimitSql() {
		String nolimitsql = "select * from " + tableName + getWhere() + orderList;
		return nolimitsql.replaceAll("  ", " ").trim();
	}
	public String getSql() {
		String nolimitsql="select count(*) from "+tableName+getWhere();
		log.debug(nolimitsql);
		return nolimitsql.replaceAll("  ", " ").trim();
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
					where=where+ " and ("+or+")";
				}
			}else {
				where=where+ or;
			}
		}
		return where;
	}
	/**
	  * 添加排序
	  * @param orders name desc,age asc
	  */
	 public Sql addOrder(String orders){ 
		 if(!StringUtils.isEmpty(orders)) {
			   orderList = " order by "+orders; 
		 }
		 return this;
	 }
	public Sql addAndList(List<String>list,String dbKey) {
		if(list !=null && list.size()>0) {
			if(list.size()>1) {
				and=_getAnd()+dbKey+" in(";
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
				and=_getAnd()+dbKey+" =?";
				andObjs.add(list.get(0));
			}
		}
		return this;
	}
	public Sql addAnd(Object object, String dbKey) {
		if(object !=null) {
			and=_getAnd()+dbKey+" =?";
			andObjs.add(object);
		}
		return this;
	}

	public Sql addAndLike(String text,String dbKey) {
		return addAndLike(text,dbKey,false);
	}
	public Sql addAndLike(String text,String dbKey,boolean hasComma) {
		if(!StringUtils.isEmpty(text)) {
			List<String> texts=new ArrayList<String>();
			texts.add(text);
			return addAndLikes(texts,dbKey,false);
		}
		return this;
	}
	public Sql addAndLikes(List<String> texts,String dbKey) {
		return addAndLikes(texts, dbKey,false);
	}

	public Sql addAndLikes(List<String> texts,String dbKey,boolean hasComma) {
		if (texts != null&&texts.size()>0) {
			String _or=null;
			for (int i = 0; i < texts.size(); i++) {
				String text=texts.get(i);
				_or =processFirstUse(_or,false) + dbKey + " like ?";
				if(hasComma) {
					andObjs.add("%," + text + ",%");
				}else {
					andObjs.add("%" + text + "%");
				}
			}
			if(texts.size()>1) {
				and=_getAnd()+" ("+_or+")";
			}else {
				and=_getAnd()+_or;
			}
		}
		return this;
	}

	public Sql addOrLike(String textValue, String dbColumn) {
		return addOrLike(textValue, dbColumn, false);
	}

	public Sql addOrLike(String textValue, String dbColumn, boolean hasComma) {
		if (textValue != null) {
			or = _getOr() + dbColumn + " like ? ";
			if (hasComma) {
				orObjs.add("%," + textValue + ",%");
			} else {
				orObjs.add("%" + textValue + "%");
			}
		}
		return this;
	}
	
	
	

	private String _getAnd() {
		return processFirstUse(and,true);
	}
	private String _getOr() {
		return processFirstUse(or,false);
	}
	private String processFirstUse(String _and,boolean isAnd) {
		if (!StringUtils.isEmpty(_and)) {
			return  _and + (isAnd?" and ":" or ");
		} else {
			return "";
		}
	}
}
