package com.penghai.css.management.controller;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.penghai.css.common.controller.BaseController;
import com.penghai.css.management.dao.impl.IUserDaoImpl;
import com.penghai.css.management.model.Linker;
import com.penghai.css.management.model.LinkerReport;
import com.penghai.css.management.model.User;
import com.penghai.css.util.HTTPUtil;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_LOGOUT_DATA;
import com.penghai.css.util.CommonData.CM_STORE_SERVER_FUNCTION;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 所有页面相关的方法
 * @author 刘晓强
 * @date 2017年5月10日
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	@Autowired
	private IUserDaoImpl iUserDaoImpl;
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
	public String checkLogin(String email, String password) {
		try {
			//1.store验证
			Map<String,String> map = new HashMap<String,String>();
			map.put("userEmail", email);//登录名
			map.put("password",password);//登录密码
		//	String requestLoginUrl = "http://localhost:8080/store/login/checkLoginforCSS";
			String requestLoginUrl = CM_CONFIG_PROPERTIES.STORE_SERVER_URL + CM_STORE_SERVER_FUNCTION.LOGIN_CHECKIN;
			String resultStr = HTTPUtil.loadURL(requestLoginUrl+"?userEmail="+email+"&password="+password);
			//String resultStr = HTTPUtil.httpRequest(requestLoginUrl, "POST", JSONObject.fromObject(map).toString());
	    	//2.session存储用户登录信息
	    	net.sf.json.JSONObject resultJobj = JSONObject.fromObject(URLDecoder.decode(resultStr,"UTF-8"));
	    	Boolean result = (Boolean) resultJobj.get("result");
	    	if(result){
	    		net.sf.json.JSONObject resultUserJobj = JSONObject.fromObject(resultJobj.getString("userInfo"));
	    		// 成功后session设置用户登录信息
				if (session.get("userId") != null && !session.get("userId").equals("null")) {
					session.hdel("userId", "nickname", "email","password");
				}
				session.set("userId", resultUserJobj.getString("id"));
				session.set("nickname", resultUserJobj.getString("nickname"));
				session.set("email", resultUserJobj.getString("email"));
				session.set("password", password);
				//3.存数据库
		    	User user = new User();
		    	user.setEmail(email);
		    	user.setPassword(password);
		    	user.setNickname(resultUserJobj.getString("nickname"));
		    	user.setEnterpriseName(resultUserJobj.getString("enterpriseName"));;
		    	user.setEnterpriseURL(resultUserJobj.getString("enterpriseURL"));;
		    	user.setOrganizationCode(resultUserJobj.getString("organizationCode"));;
		    	user.setAddress(resultUserJobj.getString("address"));;
		    	user.setTel(resultUserJobj.getString("tel"));;
		    	user.setContacts(resultUserJobj.getString("contacts"));
		    	user.setCompanyAddress(resultUserJobj.getString("companyAddress"));;
		    	user.setCompanyEmail(resultUserJobj.getString("companyEmail"));;
		    	user.setCompanyTel(resultUserJobj.getString("companyTel"));;
		    	Map<String, Object> res = iUserDaoImpl.saveLoginUser(user);
		    	System.out.println(res);
	    	}
	    	
	    	return URLDecoder.decode(resultStr,"UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 用户个人订单页（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserOrderList")
	public ModelAndView toUserOrderList(Model model) {
		try{
			String email = session.get("email");
			String password = session.get("password");
			if(email!=null && !email.equals("")){
				//1.store取数据
			//	String requestLoginUrl = "http://localhost:8080/store/order/getUserOrderXmlList";
				String requestLoginUrl = CM_CONFIG_PROPERTIES.STORE_SERVER_URL + CM_STORE_SERVER_FUNCTION.ORDER_XML_LIST;
				String resultStr = HTTPUtil.loadURL(requestLoginUrl+"?email="+email+"&password="+password);
		    	if(resultStr!=null && !resultStr.equals("")){
		    		net.sf.json.JSONObject resultJobj = JSONObject.fromObject(URLDecoder.decode(resultStr,"UTF-8"));
			    	String result = resultJobj.getString("result");
			    	if(result.equals("1")){
			    		net.sf.json.JSONArray userOrdersJobj = JSONArray.fromObject(resultJobj.getString("orderList"));
			    		model.addAttribute("ordersAndGoods", userOrdersJobj);
			    	}
		    	}
			}
		}catch (Exception e){
			model.addAttribute("ordersAndGoods", null);
		}
		return new ModelAndView("userOrderList");
	}
	/**
	 * 用户个人linker页面（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserLinker")
	public ModelAndView toUserLinker(Model model) {
		try{
			//查询所有linker信息
			List<Linker> linkers = iUserDaoImpl.getLinkerInfo();
			if(linkers!=null && linkers.size()>0){
				for(Linker linkerInfo:linkers){
					List<LinkerReport> linkerReports = iUserDaoImpl.selectLinkerReportsbyLinkerId(linkerInfo.getLinkerId());
					linkerInfo.setLinkerReports(linkerReports);
				}
				model.addAttribute("linkers",linkers);
			}
		}catch (Exception e){
			model.addAttribute("linkers", null);
		}
		return new ModelAndView("userLinker");
	}
	/**
	 * 
	 * @author 刘晓强
	 * @date 2017年5月19日
	 * @param linkerId
	 * @return
	 */
	@RequestMapping(value = "/getLinkerReport")
	@ResponseBody
	public JSONObject getLinkerReport(String linkerId) {
		JSONObject returnObject = new JSONObject();
		try{
			List<LinkerReport> LinkerReports = iUserDaoImpl.selectLinkerReportsbyLinkerId(linkerId);
			returnObject.put("LinkerReports", LinkerReports);
		}catch(Exception e){
			returnObject.put("LinkerReports", null);
		}
		return returnObject;
	}
	/**
	 * 用户个人linker页面（待转移）
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/toUserDatabase")
	public ModelAndView toUserDatabase(Model model) {
		try{
			//查询所有linker信息
			List<Linker> linkerInfo = iUserDaoImpl.getLinkerInfo();
			if(linkerInfo!=null && linkerInfo.size()>0){
				model.addAttribute("linkerInfo",linkerInfo);
			}
		}catch (Exception e){
			model.addAttribute("linkerInfo", null);
		}
		return new ModelAndView("userDatabase");
	}
	/**
	 * 用户登出
	 * 
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
