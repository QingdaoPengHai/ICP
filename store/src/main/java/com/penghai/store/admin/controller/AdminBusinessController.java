package com.penghai.store.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.admin.business.AdminBusiness;
import com.penghai.store.admin.business.impl.LogoutBusinessImpl;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.order.business.OrderManageBusiness;

/**
 * 后台管理中心
 * 
 * @author 李浩
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminBusinessController extends BaseController {
	@Autowired
	private AdminBusiness adminBusiness;
	@Autowired
	private LogoutBusinessImpl logoutBusinessImpl;
	@Autowired
	private OrderManageBusiness orderManageBusiness;
	/**
	 * 请求登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login() {
		return "admin";
	}

	/*
	 * 验证用户并登录
	 */
	@RequestMapping(value = "/checkLogin")
	@ResponseBody
	public JSONObject checkLogin() {
		Map<String, Object> map = collectData();
		JSONObject result = adminBusiness.adminLogin(map);
		return result;
	}

	/**
	 * 跳转至管理员中心页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/adminHomePage")
	public String adminHomePage() {
		return "adminHomePage";
	}

	/**
	 * 退出账号
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public JSONObject logout() {
		return logoutBusinessImpl.logout();
	}
	/**
	 * 商品分类页（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toCommodityCategory")
	public ModelAndView toCommodityCategory(Model model) {

		model.addAttribute(CMS_CONTENT, getJsp("commodityCategory"));
		return new CMSModelAndView();
	}
	/**
	 * 商品管理页（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toCommodityManagement")
	public ModelAndView toCommodityManagement(Model model) {
		
		model.addAttribute(CMS_CONTENT, getJsp("commodityManagement"));
		return new CMSModelAndView();
	}
	/**
	 * 订单管理页（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toOrderManagement")
	public ModelAndView toOrderManagement(Model model) {
		model.addAttribute("ordersAndGoods", orderManageBusiness.getAllOrderEveryOne());
		model.addAttribute(CMS_CONTENT, getJsp("orderManagement"));
		return new CMSModelAndView();
	}
}
