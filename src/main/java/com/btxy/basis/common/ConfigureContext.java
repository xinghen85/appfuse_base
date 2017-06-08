package com.btxy.basis.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.btxy.basis.model.CfgParameter;

public class ConfigureContext  extends PropertyPlaceholderConfigurer{
	
	static Map<String,CfgParameter> cfgParameters=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(1);
		System.out.println(ConfigureContext.getDbCfgParemeters("deployType2"));
		System.out.println(1);
		
	}
	public static void refreshDbParameters(){
		cfgParameters=new HashMap<String,CfgParameter>();
		
		List<CfgParameter> list=SpringContext.getDatastore().find(CfgParameter.class).asList();
		if(list!=null){
			for(CfgParameter one:list){
				cfgParameters.put(one.getName(), one);
			}
		}
	}
	public static CfgParameter getDbCfgParemeters(String name){
		if(cfgParameters==null){
			refreshDbParameters();
		}
		return cfgParameters.get(name);
	}
	/*public static String getShareFilePath(){
		CfgParameter cp=ConfigureContext.getDbCfgParemeters("shareFilePath");
        if(cp==null|| cp.getValue()==null || "".equals(cp.getValue())){
        	return "E:\\Tmp";
        }else{
        	return cp.getValue();
        }
	}
	*/
	
	private static Map<String, Object> ctxPropertiesMap;  
	  
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props)throws BeansException {  
  
        super.processProperties(beanFactory, props);  
        //load properties to ctxPropertiesMap  
        ctxPropertiesMap = new HashMap<String, Object>();  
        for (Object key : props.keySet()) {  
            String keyStr = key.toString();  
            String value = props.getProperty(keyStr);  
            ctxPropertiesMap.put(keyStr, value);  
        }  
    }  
  
    //static method for accessing context properties  
    public static Object getContextProperty(String name) {  
        return ctxPropertiesMap.get(name);  
    }  
    //static method for accessing context properties  
    public static String getContextPropertyToString(String name) {  
        return ctxPropertiesMap.get(name)==null?null:ctxPropertiesMap.get(name).toString();  
    }  
}
