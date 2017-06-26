package com.penghai.store.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.QueryLevelDataBusiness;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.order.business.OrderManageBusiness;
import com.penghai.store.user.business.IUserService;
import com.penghai.store.user.dao.IUserDao;
import com.penghai.store.user.dao.mybatis.UserMapper;
import com.penghai.store.user.model.User;
import com.penghai.store.util.common.CommonData.CM_LOGIN_DATA;
import com.penghai.store.util.common.CommonData.CM_LOGOUT_DATA;
/**
 * 进入jsp页面相关方法
 * @author 刘晓强、李浩
 * @date 2017年5月11日
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	@Autowired
	private IUserService iUserService;
	@Autowired
	private OrderManageBusiness orderManageBusiness;
	@Autowired
	private QueryLevelDataBusiness queryLevelDataBusiness;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private UserMapper userMapper;
	/**
	 * 登录页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toLogin")
	public String toLogin(Model model) {
		// 得到上个页面的URL
		String url = request.getHeader("Referer");
		request.setAttribute("returnUrl", url);

		return "login";
	}

	/**
	 * Ajax请求跳转至登录验证,返回JSON数据
	 * 
	 * @return JSONObject
	 */
	@RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject logining() {
		Map<String, Object> map = collectData();
		JSONObject result = iUserService.checkIsUser(map);
		return result;
	}
	/**
	 * css登录校验
	 * 
	 * @return JSONObject
	 */
	@RequestMapping(value = "/checkLoginforCSS")
	@ResponseBody
	public JSONObject checkLoginforCSS(HttpServletRequest request) {
//		String userEmail = userLoginInfo.getString("userEmail");
//		String password = userLoginInfo.getString("password");
		String userEmail = request.getParameter("userEmail");
		String password = request.getParameter("password");
		Map<String, Object> map = new HashMap<String, Object>();
		JSONObject resultJson  = new JSONObject();
		User user = new User(userEmail,password);
		User resultUser = userDao.login(user);
		if (resultUser != null) {
			// 登录成功
			map.put("result", true);
			map.put("code", CM_LOGIN_DATA.LOGIN_SUCCESS_CODE);
			map.put("userInfo", resultUser);
			resultJson.put("result", true);
			resultJson.put("code", CM_LOGIN_DATA.LOGIN_SUCCESS_CODE);
			resultJson.put("userInfo", resultUser);
		} else {
			// 登录失败
			map.put("result", false);
			map.put("code", CM_LOGIN_DATA.LOGIN_ERROR_CODE);
			map.put("message", CM_LOGIN_DATA.LOGIN_ERROR_INFO);
			resultJson.put("result", false);
			resultJson.put("code", CM_LOGIN_DATA.LOGIN_ERROR_CODE);
			resultJson.put("userInfo", null);
		}
		return resultJson;
	}

	/**
	 * 用户个人主页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toPersonalCenter")
	public ModelAndView toPersonalCenter(Model model) {
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		model.addAttribute("doubleLevel", doubleLevel);
		return new ModelAndView("personalCenter");
	}

	/**
	 * 用户个人订单页（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserOrderList")
	public ModelAndView toUserOrderList(Model model) {
		String userId = session.get("userId");
		if(userId==null || userId.equals("null") || userId.equals("")){
			return new ModelAndView("redirect:/goodsList/toHomePage");
		}
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		model.addAttribute("doubleLevel", doubleLevel);
		if(userId!=null && !userId.equals("")){
			com.alibaba.fastjson.JSONArray ordersAndGoods = orderManageBusiness.getAllOrderAndGoods(Integer.valueOf(userId));
			model.addAttribute("ordersAndGoods", ordersAndGoods);
		}else{
			model.addAttribute("ordersAndGoods", null);
		}
		return new ModelAndView("userOrderList");
	}
	/**
	 * 用户个人信息页（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserInfo")
	public ModelAndView toUserInfo(Model model) {
		String userId = session.get("userId");
		if(userId==null || userId.equals("null") || userId.equals("")){
			return new ModelAndView("redirect:/goodsList/toHomePage");
		}
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		model.addAttribute("doubleLevel", doubleLevel);
		try{
			User ut = new User();
			ut.setEmail(session.get("email"));
			//用户信息
			User user = userMapper.selectByCondition(ut).get(0);
			model.addAttribute("user", user);
		}catch(Exception e){
			model.addAttribute("user", null);
		}
		return new ModelAndView("userInfo");
	}
	/**
	 * 用户修改密码页（待转移）
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserChangePassword")
	public ModelAndView toUserChangePassword(Model model) {
		String userId = session.get("userId");
		if(userId==null || userId.equals("null") || userId.equals("")){
			return new ModelAndView("redirect:/goodsList/toHomePage");
		}
		//获取两级分类列表
		List<Map<String, Object>> doubleLevel = queryLevelDataBusiness.queryTwoLevelData();
		model.addAttribute("doubleLevel", doubleLevel);
		return new ModelAndView("userChangePassword");
	}
	/**
	 * 用户修改密码
	 * @author 刘晓强
	 * @date 2017年5月12日
	 * @param oldPassword
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "/userChangePassword")
	@ResponseBody
	public JSONObject userChangePassword(String oldPassword,String password) {
		JSONObject returnObject = new JSONObject();
		try{
			String userId = session.get("userId");
			User user = new User();
			user.setId(Integer.parseInt(userId));
			user.setPassword(password);
			//1.check old password is correct
			User originalUser = userMapper.selectByPrimaryKey(Integer.parseInt(userId));
			if(originalUser.getPassword().equals(oldPassword)){
				//2.change password
				userDao.changePassword(user);
				returnObject.put("success", true);
				returnObject.put("message", "修改密码成功!");
			}else{
				returnObject.put("success", false);
				returnObject.put("message", "原密码输入错误!");
			}
			
		}catch(Exception e){
			returnObject.put("success", false);
			returnObject.put("message", "操作失败!");
		}
		return returnObject;
	}
	/**
	 * 用户登出
	 * 
	 * @author 李浩
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public JSONObject logout() {
		String userId = session.get("userId");
		JSONObject returnObject = new JSONObject();
		returnObject.put("user", userId);
		if (userId != null && !userId.equals("null")) {
			int result = session.hdel("userId", "email", "nickname");
			if (result != 0) {
				returnObject.put("success", true);
				returnObject.put("message", CM_LOGOUT_DATA.LOGOUT_SUCCESS);
			} else {
				returnObject.put("success", false);
				returnObject.put("message", CM_LOGOUT_DATA.LOGOUT_ERROR_SYSTEM);
			}
		} else {
			returnObject.put("success", false);
			returnObject.put("message", CM_LOGOUT_DATA.LOGOUT_ERROR_USER);
		}
		return returnObject;
	}

}
