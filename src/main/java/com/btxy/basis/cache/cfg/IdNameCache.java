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
	//private static AuthPrivilegeInfoCache instance;
	private static Map<String,String> idNameMap=new HashMap<String,String>();
	static IdNameCache instance=null;
	public static IdNameCache getInstance(){
		if(instance==null) {
			instance=new IdNameCache();
		}
		return instance;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	
	class RowMapp implements RowMapper<LabelValue>{

		@Override
		public LabelValue mapRow(ResultSet rs, int arg1) throws SQLException {
			LabelValue lableValue=new LabelValue();
			lableValue.setLabel(rs.getString("id"));
			lableValue.setValue(rs.getString("name"));
			return lableValue;
		}
	}
	public IdNameCache() {
	}
	static List<LabelValue> dbList=new ArrayList<LabelValue>();
	static List<LabelValue> dbMap=new ArrayList<LabelValue>();
	static{
		dbList.add(new LabelValue("colColumn","select id,col_name from col_column"));
	}
	public void init(JdbcTemplate jdbcTemplate){
		for (LabelValue labelValue : dbList) {
			List<LabelValue> list=jdbcTemplate.query(labelValue.getValue(), new RowMapp());
			for (LabelValue labelValue1 : list) {
				idNameMap.put(labelValue.getLabel()+labelValue1.getLabel(), labelValue1.getValue());
			}
		}
	}
	public Map<String, String> getMap() {
		return idNameMap;
	}
}
