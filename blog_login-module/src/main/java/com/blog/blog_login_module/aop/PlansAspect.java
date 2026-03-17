package com.blog.blog_login_module.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
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
public class PlansAspect {

	private Logger log = LoggerFactory.getLogger(getClass().getName());
	@Pointcut("execution(* com.blog.blog_login_module.controller.*.*(...))")
	private void forControllerPackage() {
		log.info("Plan package controller is being executed.....");
	}
	@Pointcut("execution(* com.blog.blog_login_module.service.*.*(...)))")
	private void forServicePackage() {
		log.info("Plan package service is being executed......");
	}
	@Pointcut("execution(* com.blog.blog_login_module.repository.*.*(...)))")
	private void forRepositoryPackage() {
		log.info("Plan package repository is being executed.....");
	}
	@Before("forAppFlow()")
	private void before(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		log.info("In before: Calling method:"+method);
		Object[] args = joinPoint.getArgs();
		for(Object tempArgs: args) {
			log.info("Argument: "+args);
		}
	}
	@After("execution(* com.blog.blog_login_module.*.*(...)))")
	public void afterMethodExecution() {
		log.info("After advice: Method execution completed");
	}
	@AfterReturning(pointcut = " forAppFlow()", returning ="theResult")
	public void afterReturning(JoinPoint joinPoint, Object theResult) {
		String method = joinPoint.getSignature().toShortString();
		log.info("===> In AfterReturning: from method:"+method);
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Calling :: {} :: {}()",className,methodName);
		log.info("===> Result: "+theResult);
	}
	@Around("execution(* com.blog.blog_login_module.*.*(...)))")
	public void measureExecutionTime(ProceedingJoinPoint joinPoint)throws Throwable{
		long start =  System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long executionTime = System.currentTimeMillis();
		System.out.println(joinPoint.getSignature()+"executed In"+executionTime+"ms");
	}
	@Around("execution(* com.blog.blog_login_module.*.*(...)))")
	public Object joinPointController(ProceedingJoinPoint joinPoint) throws Throwable{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getSimpleName();
		String methodName = signature.getName();
		log.info("Calling :: {} :: {}()",className,methodName);
		long start = System.currentTimeMillis();
		long duration = System.currentTimeMillis()-start;
		Object result = joinPoint.proceed();
		log.info("Execution End :: {} :: {}() ms",className,methodName,duration);
		return result;
	}
	@Around("* execution(* com.blog.blog_login_module.*.*(...))))")
	public Object joinPointService(ProceedingJoinPoint joinPoint)throws Throwable{
		Signature signature = joinPoint.getSignature();
		String className = signature.getDeclaringType().getName();
		String methodName = signature.getName();
		log.info("Calling :: {} :: {}()",className,methodName);
		long start = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long duration = System.currentTimeMillis() - start;
		log.info("Execution End :: {}() :: {} ms",className,methodName,duration);
		return result;
	}
}
