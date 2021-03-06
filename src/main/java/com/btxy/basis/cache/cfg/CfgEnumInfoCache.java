package com.btxy.basis.cache.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.btxy.basis.cache.model.BaseCache;
import com.btxy.basis.model.CfgEnumInfo;
import com.btxy.basis.model.CfgEnumValueInfo;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineValue;

public class CfgEnumInfoCache extends BaseCache<CfgEnumInfo,Long>{
	//private static AuthPrivilegeInfoCache instance;
	private Map<String,String> cfgAllEnumValueMap=new HashMap<String,String>();
	private Map<String,CfgEnumInfo> cfgEnumInfoMap=new HashMap<String,CfgEnumInfo>();
	
	public static CfgEnumInfoCache getInstance(){
		return (CfgEnumInfoCache) getInstance(CfgEnumInfoCache.class);	
	}
	
	///////////////////////////////////////////////////////////////////////////////
	public CfgEnumInfoCache() {
		super(CfgEnumInfo.class);
		init();
	}
	public List<CfgEnumValueInfo> getEnumValueList(String code,Long machineId,String state){
		if(cfgEnumInfoMap.containsKey(code)){
			List<CfgEnumValueInfo> list=new ArrayList<CfgEnumValueInfo>();
			
			CfgEnumInfo cfgEnumInfo=cfgEnumInfoMap.get(code);
			
			CfgStateMachineValue machineValue=CfgStateMachineValueCache.getInstance().getStateMachineValue(machineId, state);
			
			if(machineValue!=null && machineValue.getButtons()!=null){
				
				if(cfgEnumInfo!=null && cfgEnumInfo.getValues()!=null){
						for(CfgEnumValueInfo one:cfgEnumInfo.getValues()){
							if(state!=null && state.equals(one.getFullCode())){
								list.add(one);
							}else{
								for(CfgStateMachineButton button:machineValue.getButtons()){
									if(button.getTargetStat()!=null && button.getTargetStat().equals(one.getFullCode())){
										list.add(one);
									} 
								}
							}
							
						}
						return list;
				}
			}else{
				for(CfgEnumValueInfo one:cfgEnumInfo.getValues()){
					if(state!=null && state.equals(one.getFullCode())){
						list.add(one);
					}
				}
				return list;
			}
			
		}
		return null;
	}
	public List<CfgEnumValueInfo> getEnumValueList(String code){
		if(cfgEnumInfoMap.containsKey(code)){
			CfgEnumInfo cfgEnumInfo=cfgEnumInfoMap.get(code);
			if(cfgEnumInfo!=null){
				return cfgEnumInfo.getValues();
			}
		}
		return null;
	}
	protected void init(){
		for(CfgEnumInfo cfgEnumInfo:map.getList()){
			for(int j=0;j<cfgEnumInfo.getValues().size();j++){
				cfgEnumInfo.getValues().get(j).setFullCode(cfgEnumInfo.getEnumCode()+cfgEnumInfo.getValues().get(j).getCode());
			}
		}
		List<CfgEnumInfo> list2=dao.find().asList();
		for(int i=0;i<list2.size();i++){
			CfgEnumInfo cfgEnumInfo=list2.get(i);
			
			for(int j=0;j<cfgEnumInfo.getValues().size();j++){
				cfgEnumInfo.getValues().get(j).setFullCode(cfgEnumInfo.getEnumCode()+cfgEnumInfo.getValues().get(j).getCode());				
				cfgAllEnumValueMap.put(cfgEnumInfo.getValues().get(j).getFullCode(), cfgEnumInfo.getValues().get(j).getValue());
			}
			cfgEnumInfoMap.put(cfgEnumInfo.getEnumCode(), cfgEnumInfo);
		}
	}
	public CfgEnumInfo getCfgEnumInfoById(Long enumId) {
		return map.get(enumId);
	}
	public Map<String, CfgEnumInfo> getCfgEnumInfoMap() {
		return cfgEnumInfoMap;
	}
	public String getEnumValueByEnumCode(String enumCode){
		return cfgAllEnumValueMap.get(enumCode);
	}
	public Map<String, String> getCfgAllEnumValueMap() {
		return cfgAllEnumValueMap;
	}
}
