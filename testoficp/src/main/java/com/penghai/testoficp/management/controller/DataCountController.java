package com.penghai.testoficp.management.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.penghai.testoficp.common.controller.BaseController;
import com.penghai.testoficp.management.business.DataCountBusiness;

/**
 * 数据统计汇总
 * @author 秦超
 * 2017-05-09 16:16:07
 */
@Controller
@RequestMapping(value="/dataCount")
public class DataCountController extends BaseController{
	@Autowired
	private DataCountBusiness dataCountBusiness;
	
	/**
	 * 获取数据库列表
	 * @author 秦超
	 * @return
	 */
	@RequestMapping(value="/getDatabaseList")
	@ResponseBody
	public String getDatabaseList(){
		return dataCountBusiness.getDatabaseList();
	}
	
	/**
	 * 获取数据库下表列表及统计信息
	 * @param request
	 * @param databaseName 数据库名
	 * @author 秦超
	 * @return
	 */
	@RequestMapping("/getTableDataCount")
	@ResponseBody
	public String getTableDataCount(HttpServletRequest request){
		String databaseName = request.getParameter("databaseName").toString();
		
		return dataCountBusiness.getTableDataCount(databaseName);
	}
	
	/**
	 * 获取数据库统计信息
	 * @return
	 * @author 秦超
	 * @time 2017-05-10 14:06:22
	 */
	@RequestMapping("/getDatabaseDataCount")
	@ResponseBody
	public String getDatabaseDataCount(){
		
		return dataCountBusiness.getDatabaseDataCount();
	}
}
