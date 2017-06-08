package com.btxy.basis.cache.cfg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.btxy.basis.cache.model.BaseCache;
import com.btxy.basis.model.CfgStateMachineButton;
import com.btxy.basis.model.CfgStateMachineValue;
import com.btxy.basis.util.map.MapUtil;

public class CfgStateMachineValueCache extends BaseCache<CfgStateMachineValue,Long>{
	
	public static CfgStateMachineValueCache getInstance(){
		return (CfgStateMachineValueCache) getInstance(CfgStateMachineValueCache.class);	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	public CfgStateMachineValueCache(){
		super(CfgStateMachineValue.class);
		List<CfgStateMachineValue> list1=this.getList();
		if(list1!=null){
			for(CfgStateMachineValue id:list1){
				MapUtil.appendListEntityToMap(valueListMapWithDefineId, id.getMachineId(), id);
				MapUtil.appendMapEntityToMap(valueMapWithDefineIdAndValue, id.getMachineId(), id.getSourceStat(), id);
				if(id.getButtons()!=null){
					for(CfgStateMachineButton bt:id.getButtons()){
						buttonMap.put(bt.getButtonId(),bt);
					}
				}
	
			}
			
		}
	}
	public CfgStateMachineButton getCfgStateMachineButton(Long buttonId){
		return buttonMap.get(buttonId);
	}
	
	public CfgStateMachineValue getStateMachineValue(Long machineId,String state){
		if( this.valueMapWithDefineIdAndValue.get(machineId)!=null && this.valueMapWithDefineIdAndValue.get(machineId).containsKey(state)){
			return this.valueMapWithDefineIdAndValue.get(machineId).get(state);
		}
		return null;
	}
	public List<CfgStateMachineValue> getStateMachineValueList(Long machineId){
		return this.valueListMapWithDefineId.get(machineId);
	}
	Map<Long,List<CfgStateMachineValue>> valueListMapWithDefineId=new HashMap<Long,List<CfgStateMachineValue>>();
	Map<Long,Map<String,CfgStateMachineValue>> valueMapWithDefineIdAndValue=new HashMap<Long,Map<String,CfgStateMachineValue>>();
	
	//Map<Long,OrderedMap<Long,CfgStateMachineButton>> buttonMap=new HashMap<Long,OrderedMap<Long,CfgStateMachineButton>>();
	Map<Long,CfgStateMachineButton> buttonMap=new HashMap<Long,CfgStateMachineButton>();
}
