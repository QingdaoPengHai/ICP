package com.penghai.store.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.penghai.store.common.model.HttpSession;

/**
 * admin登录拦截器
 * 
 * @author 李浩
 *
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private HttpSession session;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();
		if (url.equals("/store/admin/login")||url.equals("/store/admin/checkLogin")) {
			return true;
		} else {
			String adminUserId = session.get("adminUserId");
			// 若用户没有登录则请求登录界面
			if (adminUserId.equals("null") || adminUserId.equals("")) {
				// 请求转发为登录界面
				request.getRequestDispatcher("/admin/login").forward(request, response);
				return false;
			}
		}
		return true;
	}

}
