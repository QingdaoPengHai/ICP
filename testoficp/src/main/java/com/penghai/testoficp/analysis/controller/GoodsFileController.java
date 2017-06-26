package com.penghai.testoficp.analysis.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.penghai.testoficp.analysis.business.GoodsFileBusiness;
import com.penghai.testoficp.common.controller.BaseController;

/**
 * 订单文件处理
 * @author 秦超
 *
 */
@Controller
@RequestMapping(value="/goodsFile")
public class GoodsFileController extends BaseController{
	@Autowired
	private GoodsFileBusiness goodsFileBusiness;
	
	/**
	 * 获取订单xml信息详情
	 * @param request
	 * @param xmlId 
	 * @return
	 */
	@RequestMapping(value="/getXmlInfo")
	@ResponseBody
	public Map<String, Object> getXmlInfo(HttpServletRequest request, String xmlId){
		//TODO 获取xml文件内容
		Map<String, Object> resultMap = new HashMap<>();
		
//		String xmlId = request.getAttribute("xmlId").toString();
		
		resultMap = goodsFileBusiness.getXmlInfo(xmlId);
		
		return resultMap;
	}
	
	/**
	 * 获取linker数据库结构xml片段
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getDatabaseInfoFromXml")
	@ResponseBody
	public String getDatabaseInfoFromXml(HttpServletRequest request){
		//TODO 获取xml文件内容
		Map<String, Object> resultMap = new HashMap<>();
		
		String linkerId = request.getParameter("linkerId");
//		String xmlId = request.getAttribute("xmlId").toString();
		
		String result = goodsFileBusiness.getDatabaseInfoFromXml(linkerId);
		
		return result;
	}
}
