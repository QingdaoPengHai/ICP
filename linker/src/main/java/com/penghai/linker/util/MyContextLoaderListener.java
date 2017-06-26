package com.penghai.linker.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
/**
 * 获取SpringServletContext对象
 * @author 徐超
 * @Date 2017年5月17日 下午9:15:23
 */
public class MyContextLoaderListener extends ContextLoaderListener{
	/**
	 * 将ServletContext对象初始化至SpringUtil中,共全局调用
	 * @author 徐超
	 * @Date 2017年5月17日 下午9:15:51
	 * @param event
	 */
	public void contextInitialized(ServletContextEvent event){
		ServletContext context = event.getServletContext();
		super.contextInitialized(event);
		SpringUtil.setContext(context);
	}

}
