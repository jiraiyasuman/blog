package com.blog.blog_login_module.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);
	@Pointcut("execution(* com.blog.blog_login_module.controller*.*(..)")
	private void forControllerPackage() {	
	}
	@Pointcut("execution(* com.blog.blog_login_module.service*.*(...))")
	public void forService() {
	}
	@Pointcut("execution(* com.blog.blog_login_module.repository*.*(...))")
	public void forRepository() {
	}
	@Pointcut("forControllerPackage() || forServicePackage() || forRepositoryPackage())")
	public void forAppFlow() {
	}
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		// display method we are calling
        String theMethod = joinPoint.getSignature().toShortString();
        LOGGER.info("=====>> in @Before: calling method: " + theMethod);

        // display the arguments to the method

        // get the arguments
        Object[] args = joinPoint.getArgs();

        // loop thru and display args
        for (Object tempArg : args) {
        	LOGGER.info("=====>> argument: " + tempArg);
        }
	}
	
	@AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult) {

        // display method we are returning from
        String theMethod = theJoinPoint.getSignature().toShortString();
        LOGGER.info("=====>> in @AfterReturning: from method: " + theMethod);

        // display data returned
        LOGGER.info("=====>> result: " + theResult);
    }
	@Around("execution(* com.blog.blog_login_module.controller..*(..))")
	public Object jointPointController(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		LOGGER.info("Calling :: {} :: {}()",className,methodName);
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long duration = System.currentTimeMillis()-start;
		LOGGER.info("Exceution End :: {} :: {}() ::{} ms",className,methodName,duration);
		return result;
	}
	@Around("execution(* com.blog.blog_login_module.service..*(..))")
	public Object jointPointService(ProceedingJoinPoint joinPoint) throws Throwable
	{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		LOGGER.info("Calling :: {} :: {}()",className,methodName);
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long duration = System.currentTimeMillis()-start;
		LOGGER.info("Exceution End :: {} :: {}() ::{} ms",className,methodName,duration);
		return result;
	}
}
