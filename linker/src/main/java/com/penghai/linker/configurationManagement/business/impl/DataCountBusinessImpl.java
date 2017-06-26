package com.penghai.linker.configurationManagement.business.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IDataCountBusiness;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;
import com.penghai.linker.util.EhcacheUtil;
/**
 * 数据库统计分析业务处理
 * @author 秦超
 * @time 2017年5月22日
 */
@Service
public class DataCountBusinessImpl implements IDataCountBusiness {
	/**
	 * 数据表信息统计、最新数据显示
	 * @author 秦超
	 * @return
	 * @time 2017年5月22日
	 */
	@Override
	public JSONObject getTablesAcount() {
		JSONObject resultJson = new JSONObject();
		//获取数据库表列表
		EhcacheUtil ehcache = EhcacheUtil.getInstance();
		Object targetTableListObject= ehcache.get("targetTableList");
		if(targetTableListObject==null){
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}else {			
			String targetTableList = targetTableListObject.toString();
			String[] tableList = targetTableList.split(",");
			JSONArray tableArray = new JSONArray();
			//获取每个库的信息
			for(int i=0; i<tableList.length;i++){
				String tableName = tableList[i];
				Object SelectCountNumberObject = ehcache.get(tableName+"SelectCountNumber");
				Object SaveCountNumberObject = ehcache.get(tableName+"SaveCountNumber");
				String SelectCountNumber = SelectCountNumberObject==null?"0":SelectCountNumberObject.toString();
				String SaveCountNumber = SaveCountNumberObject==null?"0":SaveCountNumberObject.toString();
				JSONObject tableInfo = new JSONObject();
				tableInfo.put("tableName", tableName);
				tableInfo.put("SelectCountNumber", SelectCountNumber);
				tableInfo.put("SaveCountNumber", SaveCountNumber);
				
				tableArray.add(tableInfo);
			}
			resultJson.put("tableData", tableArray);
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		}
		
		return resultJson;
	}

}
