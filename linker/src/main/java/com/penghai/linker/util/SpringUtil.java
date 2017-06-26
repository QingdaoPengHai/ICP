package com.penghai.linker.util;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class SpringUtil extends SpringBeanAutowiringSupport{

	//注入Bean工厂
	@Autowired
	private BeanFactory beanFactory;
	
	private static ServletContext context;
	
	private SpringUtil(){
		
	}
	
	private static SpringUtil instance;
	
	static{
		//静态块,初始化实例
		instance = new SpringUtil();
	}
	/**
	 * 实例方法,使用的时候先通过getInstance方法获取实例
	 * @author 徐超
	 * @Date 2017年5月16日 下午9:10:36
	 * @param beanId
	 * @return
	 */
	public Object getBeanById(String beanId){
		return beanFactory.getBean(beanId);
	}
	
	public static SpringUtil getInstance(){
		return instance;
	}

	public static ServletContext getContext() {
		return context;
	}

	public static void setContext(ServletContext context) {
		SpringUtil.context = context;
	}
	
	
	
}
