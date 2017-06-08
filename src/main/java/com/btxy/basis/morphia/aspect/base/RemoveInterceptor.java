package com.btxy.basis.morphia.aspect.base;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.btxy.basis.model.CfgFormInfo;

@Aspect
@Component 
public class RemoveInterceptor {
	@Pointcut("execution(* com.btxy.basis.service.base.MgGenericManagerImpl.remove(..))")  
    private void remove(){}//定义一个切入点   
	
    @Around("remove()")  
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable{  
        System.out.println("进入环绕通知");  
        
        
        Object object = pjp.proceed();//执行该方法  
        Object[] objs=pjp.getArgs();
        
        System.out.println("退出方法");  
        return object;  
    }  
}
