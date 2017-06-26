package com.penghai.linker.log;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 系统日志输出到指定位置
 * @author 秦超
 *
 */
@Aspect
@Component
public class LogOutPut {
	private static Logger log = Logger.getLogger(LogOutPut.class);
	
	/**
	 * @Title: before
	 * @Description: 执行前,log输出方法和参数
	 * @param @param joinPoint
	 * @return void
	 * @throws
	 */
	@Before(value = "execution(* com.penghai.linker.*.controller..*.*(..))")
	public void before(JoinPoint joinPoint) {
		Signature sig = joinPoint.getSignature();
		Object[] ob = joinPoint.getArgs();
		log.info("执行方法:" + sig.getDeclaringTypeName() + "." + sig.getName());
		for (int i = 0; i < ob.length; i++) {
			if (ob[i] != null) {
				log.info("参数" + i + "=" + ob[i]);
			}
		}
	}
	@Before(value = "execution(* com.penghai.linker.*.business..*.*(..))")
	public void beforeBusiness(JoinPoint joinPoint) {
		Signature sig = joinPoint.getSignature();
		Object[] ob = joinPoint.getArgs();
		log.info("执行方法:" + sig.getDeclaringTypeName() + "." + sig.getName());
		for (int i = 0; i < ob.length; i++) {
			if (ob[i] != null) {
				log.info("参数" + i + "=" + ob[i]);
			}
		}
	}

	/**
	 * @Title: after
	 * @Description: 执行后
	 * @param @param joinpoint
	 * @return void
	 * @throws
	 */
	@After(value = "execution(* com.penghai.linker.*.controller..*.*(..))")
	public void after(JoinPoint joinPoint) {
		Signature sig = joinPoint.getSignature();
		log.info("方法结束:" + sig.getDeclaringTypeName() + "." + sig.getName());
	}
	@After(value = "execution(* com.penghai.linker.*.business..*.*(..))")
	public void afterBusiness(JoinPoint joinPoint) {
		Signature sig = joinPoint.getSignature();
		log.info("方法结束:" + sig.getDeclaringTypeName() + "." + sig.getName());
	}

	/**
	 * @Title: doThrowing
	 * @Description: 抛出异常
	 * @param @param e
	 * @return void
	 * @throws
	 */
	@AfterThrowing(value = "execution(* com.penghai.linker.*.controller..*.*(..))", throwing = "e")
	public void doThrowing(JoinPoint joinPoint, Exception e) {
		Signature sig = joinPoint.getSignature();
		log.error("执行方法:" + sig.getDeclaringTypeName() + "." + sig.getName()
				+ "异常");
		log.error("异常--------" + e.getStackTrace());

	}
	@AfterThrowing(value = "execution(* com.penghai.linker.*.business..*.*(..))", throwing = "e")
	public void doThrowingBusiness(JoinPoint joinPoint, Exception e) {
		Signature sig = joinPoint.getSignature();
		log.error("执行方法:" + sig.getDeclaringTypeName() + "." + sig.getName()
				+ "异常");
		log.error("异常--------" + e.getStackTrace());

	}
}
