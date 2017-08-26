package com.btxy.basis.cache.cfg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mongodb.morphia.query.Query;

import com.btxy.basis.cache.model.BaseCacheByLibrary;
import com.btxy.basis.cache.model.ExtendFormInfo;
import com.btxy.basis.cache.model.FixedPropertyEnum;
import com.btxy.basis.common.SpringContext;
import com.btxy.basis.model.CfgFixedPropertyDefine;
import com.btxy.basis.model.CfgFixedPropertyValue;
import com.btxy.basis.util.map.MapUtil;

public class CfgFixedPropertyDefineCache extends BaseCacheByLibrary<CfgFixedPropertyDefine,Long>{
	//private static AuthPrivilegeInfoCache instance;
	
	public static CfgFixedPropertyDefineCache getInstance(Long library){
		return (CfgFixedPropertyDefineCache) getInstance(CfgFixedPropertyDefineCache.class,library);	
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public CfgFixedPropertyDefineCache(Long library) {
		super(CfgFixedPropertyDefine.class,library,false);
		init();
	}
	
	Map<Long,List<CfgFixedPropertyDefine>> cfgFixedPropertyWithFormCode=new HashMap<Long,List<CfgFixedPropertyDefine>>();

	Map<String,CfgFixedPropertyDefine> defineMapWithCode=new HashMap<String,CfgFixedPropertyDefine>();
	public void init(){
		List<CfgFixedPropertyDefine> list=dao.find().asList();
		if(list!=null){
			for(CfgFixedPropertyDefine one:list){
				map.put(one.getPropertyId(), one);
				defineMapWithCode.put(one.getPropertyCode(), one);
				for(int i=0;one.getFormList()!=null && i<one.getFormList().size();i++){
					MapUtil.appendListEntityToMap(cfgFixedPropertyWithFormCode, one.getFormList().get(i).getFormId(), one);
				}	
				init(one);
			}
		}
		
	}
	public String getEnumTextByEnumId(Long enumId){
		return allMapWithId.get(enumId);
	}
	private Map<Long,List<FixedPropertyEnum>> fixedPropertyEnumMap=new Hashtable<Long,List<FixedPropertyEnum>>();
	
	private Map<Long,String> allMapWithId=new Hashtable<Long,String>();
	//private Map<String,String> allMapWithFullId=new Hashtable<String,String>();
	//private Map<Long,Map<String,List<FixedPropertyEnum>>> formCacheMap=new HashMap<Long,Map<String,List<FixedPropertyEnum>>>();
	
	public List<FixedPropertyEnum> getEnumListByPropertyId(Long id){
		return getNoNullList(this.fixedPropertyEnumMap.get(id));
	}
	
	public List<FixedPropertyEnum> getEnumListByPropertyCode(String code){
		List<FixedPropertyEnum> list =null;
		if(this.defineMapWithCode.get(code)!=null){
			list = this.fixedPropertyEnumMap.get(this.defineMapWithCode.get(code).getPropertyId());
		}
		return getNoNullList(list);
	}

	private <T> List<T> getNoNullList(List<T> list) {
		if(list==null){
			list=Collections.emptyList();
		}
		return list;
	}
	private void init(CfgFixedPropertyDefine one) {
		Long libraryId=library;
		// TODO Auto-generated method stub
		if("AXA".equals(one.getValueType())){
			Query<CfgFixedPropertyValue>  query=SpringContext.getDatastore().createQuery(CfgFixedPropertyValue.class);
			query.field("cfgFixedPropertyDefineId").equal(one.getPropertyId());
			query.and(query.criteria("library").equal(libraryId));
			List<CfgFixedPropertyValue> list1=query.asList();
			if(list1!=null){
				for(CfgFixedPropertyValue o1:list1){
					FixedPropertyEnum fpe=new FixedPropertyEnum();
					fpe.setFullId(""+o1.getPropertyValueId());
					fpe.setId(o1.getPropertyValueId());
					fpe.setName(o1.getPropertyName());
					fpe.setFullName(o1.getPropertyName());
					MapUtil.appendListEntityToMap(fixedPropertyEnumMap, one.getPropertyId(), fpe);
					
					allMapWithId.put(fpe.getId(), o1.getPropertyName());
					
				}
			}
			
		}else if("AXB".equals(one.getValueType())){
			Query<CfgFixedPropertyValue>  query=SpringContext.getDatastore().createQuery(CfgFixedPropertyValue.class);
			query.field("cfgFixedPropertyDefineId").equal(one.getPropertyId());
			query.and(query.criteria("library").equal(libraryId));
			List<CfgFixedPropertyValue> list1=query.asList();
			if(list1!=null){
				for(CfgFixedPropertyValue o1:list1){
					if(o1.getParent()!=null && o1.getParent().longValue()!=0){
						for(CfgFixedPropertyValue o2:list1){
							if(o2.getPropertyValueId()!=null && o2.getPropertyValueId().longValue()==o1.getParent().longValue()){
								FixedPropertyEnum fpe=new FixedPropertyEnum();
								fpe.setId(o1.getPropertyValueId());
								fpe.setFullId(o2.getPropertyValueId()+"-"+o1.getPropertyValueId());
								fpe.setName(o2.getPropertyName()+"->"+o1.getPropertyName());
								MapUtil.appendListEntityToMap(fixedPropertyEnumMap, one.getPropertyId(), fpe);
								
								allMapWithId.put(fpe.getId(), fpe.getName());
								
								allMapWithId.put(o2.getPropertyValueId(), o2.getPropertyName());
							}
						}
						
					}
					
				}
			}
		}else if("AXC".equals(one.getValueType())){
			Query<CfgFixedPropertyValue>  query=SpringContext.getDatastore().createQuery(CfgFixedPropertyValue.class);
			query.field("cfgFixedPropertyDefineId").equal(one.getPropertyId());
			query.and(query.criteria("library").equal(libraryId));
			List<CfgFixedPropertyValue> list1=query.asList();
			if(list1!=null){
				Map<Long,List<CfgFixedPropertyValue>> map=new HashMap<Long,List<CfgFixedPropertyValue>>();
		    	for(int i=0;list1!=null && i<list1.size();i++){
		    		 MapUtil.appendListEntityToMap(map, list1.get(i).getParent(), list1.get(i));
		    	}
		    	initTree("","",0l,map,one.getPropertyId(),libraryId);
			}
		}
	}
	private void initTree(String parentIds,String parentLab,Long parentId,Map<Long,List<CfgFixedPropertyValue>> map,Long propertyId,Long libraryId){
    	List<CfgFixedPropertyValue> list1=map.get(parentId);
    	if(list1!=null){
    		for(CfgFixedPropertyValue one:list1){
    			FixedPropertyEnum fpe=new FixedPropertyEnum();
    			if(parentIds!=null && !parentIds.equals("")){
    				fpe.setId(one.getPropertyValueId());
    				fpe.setFullId(parentIds+"-"+one.getPropertyValueId());
    				fpe.setName(parentLab+"->"+one.getPropertyName());
    				
    				MapUtil.appendListEntityToMap(fixedPropertyEnumMap, propertyId, fpe);
    				allMapWithId.put(fpe.getId(), fpe.getName());
        			initTree(parentIds+"-"+one.getPropertyValueId(),parentLab+"->"+one.getPropertyName(),one.getPropertyValueId(),map,propertyId,libraryId);
    			}else{
    				fpe.setId(one.getPropertyValueId());
    				fpe.setFullId(""+one.getPropertyValueId());
    				fpe.setName(one.getPropertyName());
    				
    				MapUtil.appendListEntityToMap(fixedPropertyEnumMap, propertyId, fpe);
    				allMapWithId.put(fpe.getId(), fpe.getName());
        			initTree(""+one.getPropertyValueId(),one.getPropertyName(),one.getPropertyValueId(),map,propertyId,libraryId);
    			}
				
				
    		}
    	}
    }
	////////////////////////////////////////////////////////////////////////
	/*鏍规嵁tableCode鑾峰彇*/
	public List<CfgFixedPropertyDefine> getCfgFixedPropertyListByFormCode(String tableCode){
				
		ExtendFormInfo form=CfgFormInfoCache.getInstance().getCfgFormInfoByFormCode(tableCode);
		if(form!=null){
			return cfgFixedPropertyWithFormCode.get(form.getFormInfo().getFormId());
		}else{
			return new ArrayList<CfgFixedPropertyDefine>();
		}
		
	}
	public List<CfgFixedPropertyDefine> getCfgFixedPropertyListByFormCode(List<Long> ids){
		
		List<CfgFixedPropertyDefine> list=new ArrayList<CfgFixedPropertyDefine>();
		if(ids!=null){
			for(Long id:ids){
				CfgFixedPropertyDefine cfp1=this.getEntityById(id);
				list.add(cfp1);
				
			}
		}
		
		return list;
	}
	public List<FixedPropertyEnum> getFixedPropertyEnumByPropertyCodeAndLibrary(Long propertyId){
		
		return this.fixedPropertyEnumMap.get(propertyId);
				
	}
	public List<FixedPropertyEnum> getFixedPropertyEnumByPropertyCodeAndLibrary(String propertyCode){
		CfgFixedPropertyDefine define=this.defineMapWithCode.get(propertyCode);
		if(define!=null){
			return this.fixedPropertyEnumMap.get(define.getPropertyId());
		}
		return new ArrayList<FixedPropertyEnum>();
	}
	public Map<Long,List<FixedPropertyEnum>> getCfgFixedPropertyEnumMap(List<Long> ids){
		
		Map<Long,List<FixedPropertyEnum>> map=new HashMap<Long,List<FixedPropertyEnum>>();
		if(ids!=null){
			for(Long id:ids){
				List<FixedPropertyEnum> result=getFixedPropertyEnumByPropertyCodeAndLibrary(id);
				map.put(id, result);
			}
		}
		return map;
	}
	public Map<Long,String> getAllFixedPropertyEnumValueInfoMap(String tableCode){
		
		
		return this.allMapWithId;
	}
	public Map<Long,String> getAllFixedPropertyEnumValueInfoMap(List<Long> ids){
		
		
		return this.allMapWithId;
	}
	public Map<String,List<FixedPropertyEnum>> getCfgFixedPropertyEnumMap(String tableCode){
		
		Map<String,List<FixedPropertyEnum>> map=new HashMap<String,List<FixedPropertyEnum>>();
		
		ExtendFormInfo form=CfgFormInfoCache.getInstance().getCfgFormInfoByFormCode(tableCode);
		if(form!=null){
			if(this.cfgFixedPropertyWithFormCode.containsKey(form.getFormId())){
				List<CfgFixedPropertyDefine> list1=cfgFixedPropertyWithFormCode.get(form.getFormId());
				if(list1!=null){
					for(CfgFixedPropertyDefine one:list1){
						List<FixedPropertyEnum> result=getFixedPropertyEnumByPropertyCodeAndLibrary(one.getPropertyCode());
						map.put(one.getPropertyCode(), result);
					}
				}
			}
		}
		
		
		return map;
	}
	
}
