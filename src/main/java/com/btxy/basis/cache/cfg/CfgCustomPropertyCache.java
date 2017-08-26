package com.btxy.basis.cache.cfg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.btxy.basis.cache.model.BaseCacheByLibrary;
import com.btxy.basis.model.CfgCustomProperty;
import com.btxy.basis.util.map.MapUtil;

public class CfgCustomPropertyCache extends BaseCacheByLibrary<CfgCustomProperty,Long>{
	//private static AuthPrivilegeInfoCache instance;
	
	public static CfgCustomPropertyCache getInstance(Long library){
		return (CfgCustomPropertyCache) getInstance(CfgCustomPropertyCache.class,library);	
	}
	
	///////////////////////////////////////////////////////////////////////////////
	public CfgCustomPropertyCache(Long library) {
		super(CfgCustomProperty.class,library,false);
		init();
	}
	
	public CfgCustomProperty getCfgCustomProperty(Long id){
		return map.get(id);
	}
	public CfgCustomProperty getCfgCustomProperty(String code){
		return defineMapWithCode.get(code);
	}
	
	public List<CfgCustomProperty> getCfgCustomPropertyList(String formCode){
		if(defineMapWithCode.containsKey(formCode)){
			return cfgFixedPropertyWithFormCode.get(defineMapWithCode.get(formCode).getFormInfo().getFormId());
		}else{
			return new ArrayList<CfgCustomProperty>();
		}
		
	}
	
	
	Map<Long,List<CfgCustomProperty>> cfgFixedPropertyWithFormCode=new HashMap<Long,List<CfgCustomProperty>>();

	Map<String,CfgCustomProperty> defineMapWithCode=new HashMap<String,CfgCustomProperty>();
	public void init(){
		List<CfgCustomProperty> list=dao.find().asList();
		for(CfgCustomProperty one:list){
			map.put(one.getPropertyId(), one);
			defineMapWithCode.put(one.getPropertyCode(), one);
			if(one.getFormInfo()!=null){
				MapUtil.appendListEntityToMap(cfgFixedPropertyWithFormCode, one.getFormInfo().getFormId(), one);
			}
		}
		
	}
	}
