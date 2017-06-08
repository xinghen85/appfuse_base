package com.btxy.basis.common;

import java.util.HashMap;
import java.util.Map;

import com.btxy.basis.common.model.StartRowInfo;

public class StartRowContext {
	
	private static StartRowContext startRowContext=null;

	private Map<Long,StartRowInfo> map=new HashMap<Long,StartRowInfo>();
	
	public static StartRowContext getInstance(){
		if(startRowContext==null){
			return new StartRowContext();
		}
		return startRowContext;
	}
	public StartRowInfo getStartRow(Long reportInstanceId){
		if(!map.containsKey(reportInstanceId)){
			map.put(reportInstanceId, new StartRowInfo());
		}
		StartRowInfo sri=map.get(reportInstanceId);
		return sri;
	}
	public StartRowInfo getStartRow(Long reportInstanceId,int offset){
		if(!map.containsKey(reportInstanceId)){
			map.put(reportInstanceId, new StartRowInfo());
		}
		StartRowInfo sri=map.get(reportInstanceId);
		sri.increase(offset);
		return sri;
	}
	
	public void init(Long reportInstanceId){
		if(map.containsKey(reportInstanceId)){
			map.remove(reportInstanceId);
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
