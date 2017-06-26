/*
 * function: linker控制器
 * author: LiuLihua
 * Date: 2017-05-09
 */

package com.penghai.css.management.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.penghai.css.common.controller.BaseController;
import com.penghai.css.management.business.LinkerManagerBusiness;

@Controller
@RequestMapping(value="/linkerManager")
public class LinkerManagerController{
	
	@Autowired
	private LinkerManagerBusiness linkerManagerBusiness;

	//linker注册接口
	@RequestMapping(value="/linkerRegister")
	@ResponseBody
	public String linkerRegister(HttpServletRequest request){
		String result = null;
		String linkerId = request.getParameter("linkerId");
		//调用linkerRegister接口注册linker
		result = linkerManagerBusiness.linkerRegister(linkerId);
		return result;
	}
	
	//linker上报错误接口
	@RequestMapping(value="/linkerReportError")
	@ResponseBody
	public String linkerReportError(HttpServletRequest request){
		
		String result = null;
		String linkerId = request.getParameter("linkerId");
		String reportErrorType = request.getParameter("reportErrorType");
		String reportErrorDetail = request.getParameter("reportErrorDetail");
		//调用linkerReportError接口上报linker错误信息
		result = linkerManagerBusiness.linkerReportError(linkerId, reportErrorType, reportErrorDetail);
		return result;
	}
	
	
	
	
	
}
