package com.btxy.basis.morphia.aspect.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.btxy.basis.cache.cfg.CfgFormInfoCache;
import com.btxy.basis.cache.model.ExtendFormInfo;

@Aspect
@Component 
public class SaveInterceptor<T> {
	private static Map<String,ModelInterceptorInterface<Serializable>> doInterceptorMap=new HashMap<String,ModelInterceptorInterface<Serializable>>();
	
	@Pointcut("execution(* com.btxy.basis.service.base.MgGenericManagerImpl.save(..)) ||"
			+ "execution(* com.btxy.basis.service.base.MgGenericManagerImpl.saveMainBody(..)) ")  
    private void save(){}//定义一个切入点   
	
    @Around("save()")  
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
        System.out.println("进入环绕通知");  
        Object object = pjp.proceed();//执行该方法  
        try{
	        String serviceFullName=pjp.getTarget().getClass().getName();
	        if(!doInterceptorMap.containsKey(serviceFullName)){
	        	//doInterceptorMap.put(serviceFullName, null);
	        	if(serviceFullName!=null ){
	            	String[] a=serviceFullName.split("\\.");
	            	if(a!=null && a.length>0){
	            		String serviceName=a[a.length-1];
	            		if(serviceName!=null){
	            			serviceName=serviceName.substring(0,serviceName.length()-11);
	            			if(serviceName!=null && serviceName.length()>1){
	            				String formPowerName=serviceName.substring(0,1).toLowerCase()+serviceName.substring(1);
	            				ExtendFormInfo cfi=CfgFormInfoCache.getInstance().getCfgFormInfoByFormCode(formPowerName);
	            				
	            				
	            				if("BBB".equals(cfi.getFormInfo().getValueChangeDoType())){
	            					ModelInterceptorImpl<Serializable> mii=new ModelInterceptorImpl();
	            					doInterceptorMap.put(serviceFullName, mii);
	            				}else if("BBC".equals(cfi.getFormInfo().getValueChangeDoType())){
	            					String iiiname=serviceFullName.replaceFirst("com.btxy.basis.service", "com.btxy.basis.morphia.aspect");
	            					iiiname=iiiname.substring(0,iiiname.length()-11)+"InterceptorImpl";
	            					doInterceptorMap.put(serviceFullName, (ModelInterceptorInterface<Serializable>)Class.forName(iiiname).newInstance());
	            				}
	            			}
	            			
	            		}
	            	}
	            }
	        }
	        	
        	if(doInterceptorMap.get(serviceFullName)!=null && pjp.getArgs()!=null && pjp.getArgs().length>0){
        		Serializable ddd =(Serializable) pjp.getArgs()[0];
				if("save".equals(pjp.getSignature().getName())){
        			if(pjp.getArgs().length>1){
        				if(pjp.getArgs()[1]!=null){
        					Boolean b=Boolean.parseBoolean(pjp.getArgs()[1].toString());
        					if(b){
        						doInterceptorMap.get(serviceFullName).onChange(ddd, ModelInterceptorImpl.CHANGE_TYPE_INSERT);
        					}else{
        						doInterceptorMap.get(serviceFullName).onChange(ddd, ModelInterceptorImpl.CHANGE_TYPE_UPDATE);
        					}
        				}
        				
        			}else{
        				doInterceptorMap.get(serviceFullName).onChange(ddd, ModelInterceptorImpl.CHANGE_TYPE_SAVE);
        			}
        		}else if("saveMainBody".equals(pjp.getSignature().getName())){
        			doInterceptorMap.get(serviceFullName).onChange(ddd, ModelInterceptorImpl.CHANGE_TYPE_UPDATE);
        		}
        		
        	}
        
        }catch(Exception e){
        	e.printStackTrace();
        }
        System.out.println("退出方法");  
        return object;  
    }  
}
