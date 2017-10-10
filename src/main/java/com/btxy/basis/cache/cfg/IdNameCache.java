package com.btxy.basis.cache.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.btxy.basis.model.LabelValue;

public class IdNameCache {
	static Logger log =Logger.getLogger(IdNameCache.class);
	private Map<String,String> idNameMap=new HashMap<String,String>();
	private List<LabelValue> sqlList=new ArrayList<LabelValue>();
	private Map<String,LabelValue> sqlMap=new HashMap<String,LabelValue>();
	private Map<String, List<String>> classMap=new HashMap<String, List<String>>();
	private static IdNameCache instance=null;
	public static IdNameCache getInstance(){
		if(instance==null) {
			instance=new IdNameCache();
		}
		return instance;
	}
	///////////////////////////////////////////////////////////////////////////////


	public void init(JdbcTemplate jdbcTemplate){
		for (LabelValue sqlDefine : sqlList) {
			List<LabelValue> list=jdbcTemplate.query(sqlDefine.getValue(), new RowMapp());
			for (LabelValue idName : list) {
				idNameMap.put(sqlDefine.getLabel()+idName.getLabel(), idName.getValue());
			}
		}
	}
	public Map<String, String> getMap() {
		return idNameMap;
	}
	
	
	class RowMapp implements RowMapper<LabelValue>{
		@Override
		public LabelValue mapRow(ResultSet rs, int arg1) throws SQLException {
			LabelValue lableValue=new LabelValue();
			lableValue.setLabel(rs.getString("id"));
			lableValue.setValue(rs.getString("name"));
			return lableValue;
		}
	}

	public void add(String key,String sql,String className) {
		LabelValue e= new LabelValue(key,sql);
		sqlList.add(e);
		sqlMap.put(e.getLabel(),e);
		if(classMap.get(className)==null) {
			classMap.put(className,new ArrayList<String>());
		}
		classMap.get(className).add(key);
	}
	public void reset(JdbcTemplate jdbcTemplate,String key){
		if(sqlMap.get(key)!=null) {
			List<LabelValue> list=jdbcTemplate.query(sqlMap.get(key).getValue(), new RowMapp());
			for (LabelValue idName : list) {
				idNameMap.put(key+idName.getLabel(), idName.getValue());
			}
		}
	}
	public void resetByClassName(JdbcTemplate jdbcTemplate, String className) {
		log.info("idName:"+className);
		List<String> list = classMap.get(className);
		if(list!=null) {
			for (String string : list) {
				reset(jdbcTemplate,string);
			}
		}
	}

	public String getName(String id, String key) {
		String[] d = id.split(",");
		StringBuilder rtn = new StringBuilder("");
		for (String string : d) {
			String name = idNameMap.get(key + string);
			if (name == null) {
				name = string;
			}
			if (StringUtils.isNotEmpty(rtn.toString())) {
				rtn.append("," + name);
			} else {
				rtn.append(name);
			}
		}
		return rtn.toString();
	}
}
