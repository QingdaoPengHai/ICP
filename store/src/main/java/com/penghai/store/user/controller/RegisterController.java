package com.penghai.store.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penghai.store.common.controller.BaseController;
import com.penghai.store.user.business.IUserService;
import com.penghai.store.user.business.RegisterBusiness;
import com.penghai.store.user.model.User;

/**
 * 用户注册
 * @author Q.chao
 * 2017-04-24 15:47:42
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController extends BaseController{
	@Autowired
	private RegisterBusiness registerBusiness;
	@Autowired
	private IUserService iUserService;
	/**
	 * 跳转注册页面
	 * @param model 
	 * @param returnUrl 前一页面Url
	 * @return 
	 */
	@RequestMapping(value = "/toRegister")
	public ModelAndView toRegister(Model model, String returnUrl){
		
		model.addAttribute("returnUrl", returnUrl);
		
		return new ModelAndView("register");
	}
	
	/**
	 * 用户注册
	 * @param user
	 * @return 
	 */
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userRegister(User user){
		String code = request.getParameter("code");  
		HttpSession session = request.getSession();  
		String sessionCode = (String) session.getAttribute("code");  
		Map<String, Object> resultMap = new HashMap<>();
		if (!StringUtils.equalsIgnoreCase(code, sessionCode)) {  //忽略验证码大小写  
		//校验验证码
		//if (!code.equals(sessionCode)) { 
		    //throw new RuntimeException("验证码对应不上code=" + code + "  sessionCode=" + sessionCode);  
			resultMap.put("state", 0);
			resultMap.put("message", "验证码输入有误");
			return resultMap;
		}  
		//获取request信息
		Map<String, Object> map = collectData();
		//1.用户校验、注册
		resultMap = registerBusiness.userRegister(map, user);
		//2.用户登录
		if(1==Integer.parseInt(resultMap.get("state").toString())){
			iUserService.checkIsUser(map);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/validateCode")
	public String ValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{  
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");  
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");  
	    response.setHeader("Cache-Control", "no-cache");  
	    response.setDateHeader("Expires", 0);  
	  
	    HttpSession session = request.getSession();  
	  
	    ValidateCode vCode = new ValidateCode(120,40,5,100);  
	    session.setAttribute("code", vCode.getCode());  
	    vCode.write(response.getOutputStream());  
	    return null;  
	}  
}
