/*
 * function: linker接口实现类
 * author： LiuLihua
 * date：2017-05-09
 */

package com.penghai.css.management.business.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.penghai.css.management.business.GoodsFileBusiness;
import com.penghai.css.management.business.LinkerManagerBusiness;
import com.penghai.css.management.dao.mybatis.LinkerMapper;
import com.penghai.css.management.model.Linker;
import com.penghai.css.management.model.LinkerReport;
import com.penghai.css.util.CommonData.CM_INFO_DATA;
import com.penghai.css.util.CommonData.CM_LINKER_CONFIGURE;
import com.penghai.css.util.CommonData.CM_LINKER_REDIS_PROPERTIES;
import com.penghai.css.util.CommonData.CM_LINKER_RETURN_CODE;
import com.penghai.css.util.JedisUtils;

import redis.clients.jedis.Jedis;


@Service
public class LinkerManagerBusinessImpl implements LinkerManagerBusiness{
	
	Logger logger = Logger.getLogger(LinkerManagerBusinessImpl.class);
	
	@Autowired
	private LinkerMapper linkerMapper;
	
	@Autowired
	private GoodsFileBusiness goodsFileBusiness;
	
	@Autowired
	private JedisUtils jedisUtils;
	
	/**
	 * linker注册
	 * @author LiuLihua
	 * @Date 2017-05-09
	 */
	@Override
	public String linkerRegister(String linkerId){
		
		Linker linkerQuery = null;
		JSONObject resultJson = new JSONObject();
		String targetDatabaseInfoJson = "";
		String databaseInfoJson = "";
		String xmlTargetDatabase = "";
		String resultMapEncode = "";	
		Jedis jedis = null;
		boolean findLinkerId = false;
		String validLinkerLists = "";
		
		//判断linker是否有效
		try {
			jedis = jedisUtils.getJedis();
			validLinkerLists = jedis.get(CM_LINKER_REDIS_PROPERTIES.LINKER_KEY);
			if(validLinkerLists != null && validLinkerLists != ""){
				if(validLinkerLists.contains(linkerId.trim()))
					findLinkerId = true; 
			}
		} catch (Exception e) {
			logger.error(CM_INFO_DATA.SYSTEM_ERROR,e);
		} finally {
			if (jedis != null)
				jedis.close();
		}

		//linker无效
		if(!findLinkerId){ 
			logger.error("Invalid linkerId");
			resultJson.put("databaseXml", "");
			resultJson.put("code", CM_LINKER_RETURN_CODE.LINKER_INVALID_LINKERID);
		//linker有效
		}else{
			JSONObject xmlDatabasesJson = goodsFileBusiness.getDatabaseAndTargetDatabaseInfoFromXml(linkerId);
			targetDatabaseInfoJson = xmlDatabasesJson.getString("targetDatabaseInfoJson");
			xmlTargetDatabase = xmlDatabasesJson.getString("xmlTargetDatabase");
			databaseInfoJson = xmlDatabasesJson.getString("databaseInfoJson");
			//解析xml文件失败
			if(xmlTargetDatabase == "" || databaseInfoJson == ""){
				logger.error("Failed to get target database or database info from getDatabaseInfoFromXml()");
				resultJson.put("databaseXml", xmlTargetDatabase);
				resultJson.put("code", CM_LINKER_RETURN_CODE.LINKER_REGISTER_XML_FAILED);	
			//获取targetDatabase数据成功
			} else{
				//查询数据库是否有linkerId记录
				linkerQuery = linkerMapper.selectLinker(linkerId);
				//数据库无linker记录，插入linker信息
				if(linkerQuery == null){
					logger.info("insert linker database");
					Linker linkerObject = new Linker();
					linkerObject.setLinkerId(linkerId);
					linkerObject.setLinkerIPAddress("");
					linkerObject.setLinkerStatus(CM_LINKER_CONFIGURE.LINKER_REGISTERED);
					linkerObject.setRegisterTime(new Date());
					linkerMapper.insertLinker(linkerObject);
				//数据库有linker记录，更新linker信息
				}else{
					logger.info("update linker database");
					Linker linkerObject = new Linker();
					linkerObject.setLinkerId(linkerId);
					linkerObject.setLinkerIPAddress(linkerQuery.getLinkerIPAddress());
					linkerObject.setLinkerStatus(CM_LINKER_CONFIGURE.LINKER_REGISTERED);
					linkerObject.setRegisterTime(new Date());
					linkerMapper.updateLinker(linkerObject);					
				}
				
				//设置返回值
				resultJson.put("targetDatabaseInfoJson", targetDatabaseInfoJson);
				resultJson.put("databaseInfoJson", databaseInfoJson);
				resultJson.put("targetDatabaseXml", xmlTargetDatabase);
				resultJson.put("code", CM_LINKER_RETURN_CODE.LINKER_RETURN_OK);	
			}
		}
		
		//UTF-8编码
		try {
			resultMapEncode = URLEncoder.encode(resultJson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error(CM_INFO_DATA.SYSTEM_ERROR,e);
		}
		
		return resultMapEncode;
	}

	/**
	 * linker上报错误信息
	 * @author LiuLihua
	 * @Date 2017-05-09
	 */
	@Override
	public String linkerReportError(String linkerId, String reportErrorType, String reportErrorDetail) {
		Linker linkerQuery = null;
		JSONObject resultJson = new JSONObject();
		String resultCode = "";	
		
		//上报数据无效
		if(reportErrorType == null || reportErrorType == ""){
			logger.info("errorType is invalid");
			resultJson.put("code", CM_LINKER_RETURN_CODE.LINKER_REPORT_INVALID_DATA);
			try {
				resultCode = URLEncoder.encode(resultJson.toJSONString(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return resultCode;
		}
		
		linkerQuery = linkerMapper.selectLinker(linkerId);
		//linkerId不存在
		if(linkerQuery == null){ 
			resultJson.put("code", CM_LINKER_RETURN_CODE.LINKER_INVALID_LINKERID);
		//linker存在
		}else{ 
			//插入linker上报错误信息
			LinkerReport linkerReportObject = new LinkerReport();
			linkerReportObject.setLinkerId(linkerId);
			linkerReportObject.setReportErrorType(reportErrorType);
			linkerReportObject.setReportErrorDetail(reportErrorDetail);
			linkerReportObject.setReportErrorTime(new Date());
			linkerMapper.insertLinkerReport(linkerReportObject);
			//设置返回值
			resultJson.put("code", CM_LINKER_RETURN_CODE.LINKER_RETURN_OK);				
		}
		//UTF-8编码
		try {
			resultCode = URLEncoder.encode(resultJson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return resultCode;		
	}

}
