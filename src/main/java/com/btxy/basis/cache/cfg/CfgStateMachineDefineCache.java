package com.btxy.basis.cache.cfg;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.btxy.basis.cache.model.BaseCache;
import com.btxy.basis.model.CfgFormInfo;
import com.btxy.basis.model.CfgStateMachineDefine;

public class CfgStateMachineDefineCache extends BaseCache<CfgStateMachineDefine,Long>{
	
	public static CfgStateMachineDefineCache getInstance(){
		
		return (CfgStateMachineDefineCache) getInstance(CfgStateMachineDefineCache.class);	

	}
	
	///////////////////////////////////////////////////////////////////////////////
	Map<Long,Method> stateGetMethodMap=new HashMap<Long,Method>();
	Map<Long,Method> stateSetMethodMap=new HashMap<Long,Method>();
	Map<Long,Class<?>> formClassMap=new HashMap<Long,Class<?>>();
	
	Map<String,CfgStateMachineDefine> stateMachineWithCodeMap=new HashMap<String,CfgStateMachineDefine>();
	public CfgStateMachineDefineCache(){
		super(CfgStateMachineDefine.class);
		
		if(super.getList()!=null){
			for(CfgStateMachineDefine define:super.getList()){
				Long enumId=define.getEnumId();
				
				if(enumId!=null && enumId.longValue()>0){
					define.setEnumInfo(CfgEnumInfoCache.getInstance().getCfgEnumInfoById(enumId));
				}
				
				Long formId=define.getFormId();
				
				if(formId!=null){
					CfgFormInfo form=CfgFormInfoCache.getInstance().getEntityById(formId);
					String formClassName=form.getModelClassName();
					
					stateMachineWithCodeMap.put(form.getFormCode()+"."+define.getFieldName(), define);
					try {
						Class<?> formClass=Class.forName(formClassName);
						
						PropertyDescriptor pd = new PropertyDescriptor(define.getFieldName(),formClass);
						Method nameGetMethod = pd.getReadMethod();//获得get方法
						Method nameSetMethod = pd.getWriteMethod();
						stateGetMethodMap.put(define.getMachineId(), nameGetMethod);
						stateSetMethodMap.put(define.getMachineId(), nameSetMethod);
						formClassMap.put(define.getMachineId(), formClass);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IntrospectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	public CfgStateMachineDefine getCfgStateMachineDefineByCode(String code){
		return stateMachineWithCodeMap.get(code);
	}
	public Method getStateFieldGetMethod(Long machineId){
		return stateGetMethodMap.get(machineId);
	}
	public Method getStateFieldSetMethod(Long machineId){
		return stateSetMethodMap.get(machineId);
	}
	public Class<?> getFormClass(Long machineId){
		return formClassMap.get(machineId);
	}
	
}
