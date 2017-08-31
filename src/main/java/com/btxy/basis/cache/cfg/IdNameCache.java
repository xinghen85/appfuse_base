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
	private static List<LabelValue> dbList=new ArrayList<LabelValue>();
	private static Map<String,LabelValue> dbMap=new HashMap<String,LabelValue>();
	private static IdNameCache instance=null;
	public static IdNameCache getInstance(){
		if(instance==null) {
			instance=new IdNameCache();
		}
		return instance;
	}
	public static interface DD{
		void set(List<LabelValue> dbList,Map<String,LabelValue> dbMap);
	}
	public void init(DD dd) {
		dd.set(dbList, dbMap);
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
