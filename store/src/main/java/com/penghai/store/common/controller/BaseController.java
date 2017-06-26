package com.penghai.store.common.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.penghai.store.common.model.HttpSession;

/**
 * BaseController 用于处理用户会话、cookies
 * 
 * @author 李浩
 * @date 2017年4月24日
 */
public class BaseController {
	public HttpServletRequest request;
	public HttpServletResponse response;
	// 获取会话对象
	@Autowired
	public HttpSession session;
	// 启用日志
	Logger log = Logger.getLogger(BaseController.class);
	// 后台页面框架
	protected final String CMS_CONTENT = "cmsContent";
	private final String JSP_PREFIX = "/WEB-INF/jsp/webmaster/";
	private final String JSP_SUFFIX = ".jsp";

	/**
	 * 初始化参数，把用户session放到cookie
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	@ModelAttribute
	public void initial(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		this.request = request;
		this.response = response;
		String sessionId = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			// 获取用户cookie的sessionId
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("sessionId")) {
					sessionId = cookie.getValue();
					log.info(sessionId);
					break;
				}
			}

			// 如果cookie里面有sessionId就更新sessionId过期时间.没有就创建session,放到cookie里面
			if (sessionId != null && !sessionId.equals("")) {
				// 更新sessionId过期时间，如果更新失败，说明jedis的sessionId已经过期，重新创建session
				boolean sessionUpdate = session.upDate(sessionId);
				String userId = session.get("userId");
				String adminUserId = session.get("adminUserId");
				// 获取到后返回请求中
				if (!userId.equals("null") && !userId.equals("")) {
					String nickname = session.get("nickname");
					String email = session.get("email");
					this.request.setAttribute("userId", userId);
					this.request.setAttribute("nickname", nickname);
					this.request.setAttribute("email", email);
				}
				if (!adminUserId.equals("null") && !adminUserId.equals("")) {
					String adminNickname = session.get("adminNickname");
					String adminEmail = session.get("email");
					this.request.setAttribute("adminUserId", adminUserId);
					this.request.setAttribute("adminNickname", adminNickname);
					this.request.setAttribute("adminEmail", adminEmail);
				}
				if (sessionUpdate) {
					// 获取session中用户信息
					return;
				} else {
					// session过期，创建session,sessionId放到cookie里。
					// createSession();
					log.info("更新sessionId：" + sessionId + "失败");
				}
			} else {
				// 首次登录，创建session,sessionId放到cookie里
				log.info("用户sessionId没有获取到");
				createSession();
			}
		} else {
			// 清空cookie，创建session.
			createSession();
		}
	}

	// 创建session，并加入cookie
	private void createSession() throws ServletException, IOException {
		session.creatSession();
		Cookie cookie = new Cookie("sessionId", session.getSessionId());
		cookie.setPath("/");
		this.response.reset();
		this.response.addCookie(cookie);
	}

	/*
	 * 获取request中的所有数据，传入参数名与返回参数名相同
	 */
	protected Map<String, Object> collectData() {
		Map<String, Object> params = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = request.getParameterValues(name)[0];
			params.put(name, value);
		}
		return params;
	}

	/**
	 * 视图封装
	 * 
	 * @author liuxiaoqiang
	 *
	 */
	protected class CMSModelAndView extends ModelAndView {

		private static final String CMS_VIEW = "webmaster/cms-frame";

		public CMSModelAndView() {
			super(CMS_VIEW);
		}
	}

	/**
	 * 获取jsp的相对路径
	 * 
	 * @param jspPathName
	 * @return
	 */
	protected String getJsp(String jspPathName) {
		StringBuffer jspPath = new StringBuffer();
		jspPath.append(JSP_PREFIX);
		jspPath.append(jspPathName);
		jspPath.append(JSP_SUFFIX);
		return jspPath.toString();
	}
}
