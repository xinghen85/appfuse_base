package com.btxy.basis.morphia.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectAdvice {
	@Pointcut("execution(* com.btxy.basis.common.model.*.*(..))")
	public void anyMethod() {
	}

	// 前置通知
	// 在切点方法集合执行前，执行前置通知
	@Before("execution(* com.btxy.basis.common.model.*.*(..))")
	public void doBefore(JoinPoint jp) {
		System.out.println("===========进入before advice============ \n");

		System.out.print("准备在" + jp.getTarget().getClass() + "对象上用");
		System.out.print(jp.getSignature().getName() + "方法进行对 '");
		System.out.print(jp.getArgs()[0] + "'进行删除！\n\n");

		System.out.println("要进入切入点方法了 \n");
	}

	// 后置通知
	@AfterReturning(value = "anyMethod()", returning = "result")
	public void doAfter(JoinPoint jp, String result) {
		System.out.println("==========进入after advice=========== \n");
		System.out.println("切入点方法执行完了 \n");

		System.out.print(jp.getArgs()[0] + "在");
		System.out.print(jp.getTarget().getClass() + "对象上被");
		System.out.print(jp.getSignature().getName() + "方法删除了");
		System.out.print("只留下：" + result + "\n\n");
	}

	// 环绕通知（##环绕通知的方法中一定要有ProceedingJoinPoint 参数,与
	// Filter中的 doFilter方法类似）

	@Around("execution(* com.btxy.basis.common.model.*.*(..))")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("===========进入around环绕方法！=========== \n");
		
		// 调用目标方法之前执行的动作
		System.out.println("调用方法之前: 执行！\n");

		// 调用方法的参数
		Object[] args = pjp.getArgs();
		// 调用的方法名
		String method = pjp.getSignature().getName();
		// 获取目标对象(形如：com.action.admin.LoginAction@1a2467a)
		Object target = pjp.getTarget();
		// 获取目标对象的类名(形如：com.action.admin.LoginAction)
//		String targetName = pjp.getTarget().getClass().getName();
		// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
		Object result = pjp.proceed();// result的值就是被拦截方法的返回值

		System.out.println("输出：" + args[0] + ";" + method + ";" + target + ";"
				+ result + "\n");
		System.out.println("调用方法结束：之后执行！\n");
		return result;
	}

	// 异常通知

	@AfterThrowing(value = "execution(* com.btxy.basis.common.model.*.*(..))", throwing = "e")
	public void doThrow(JoinPoint jp, Throwable e) {
		System.out.println("删除出错啦");
	}
}
