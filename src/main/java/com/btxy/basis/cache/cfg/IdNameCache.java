package com.btxy.basis.cache.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.btxy.basis.model.LabelValue;

public class IdNameCache {
	private static Map<String,String> idNameMap=new HashMap<String,String>();
	private List<LabelValue> sqlList=new ArrayList<LabelValue>();
	private static Map<String,LabelValue> sqlMap=new HashMap<String,LabelValue>();
	private static IdNameCache instance=null;
	public static IdNameCache getInstance(){
		return instance;
	}
	public void init(InitSqlList initSqlList) {
		initSqlList.exe(sqlList,sqlMap);
	}
	///////////////////////////////////////////////////////////////////////////////


	public void init(JdbcTemplate jdbcTemplate){
		if(instance==null) {
			instance=new IdNameCache();
		}
		for (LabelValue sqlDefine : sqlList) {
			List<LabelValue> list=jdbcTemplate.query(sqlDefine.getValue(), new RowMapp());
			for (LabelValue idName : list) {
				idNameMap.put(sqlDefine.getLabel()+idName.getLabel(), idName.getValue());
			}
		}
	}
	public void reset(JdbcTemplate jdbcTemplate,String key){
		if(instance==null) {
			instance=new IdNameCache();
		}
		if(sqlMap.get(key)!=null) {
			List<LabelValue> list=jdbcTemplate.query(sqlMap.get(key).getValue(), new RowMapp());
			for (LabelValue idName : list) {
				idNameMap.put(key+idName.getLabel(), idName.getValue());
			}
		}
	}
	public Map<String, String> getMap() {
		return idNameMap;
	}
	
	
	
	
	
	public interface InitSqlList{
		void exe(List<LabelValue> sqlList,Map<String,LabelValue> sqlMap);
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
	
}
