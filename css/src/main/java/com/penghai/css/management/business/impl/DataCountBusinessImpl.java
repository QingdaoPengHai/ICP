package com.penghai.css.management.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.penghai.css.management.business.DataCountBusiness;
import com.penghai.css.management.dao.mybatis.DataCountDaoI;
import com.penghai.css.management.model.databaseModel.Database;
import com.penghai.css.management.model.databaseModel.Schema;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.MongoUtil;
import com.penghai.css.util.xml.XmlUtil;
import com.penghai.css.util.xml.XmlUtil.CollectionWrapper;

/**
 * 数据库统计汇总服务
 * @author 秦超
 * 2017-05-10 09:38:45
 */
@Service
public class DataCountBusinessImpl implements DataCountBusiness{
	@Autowired
	private DataCountDaoI dataCountDaoI;
	@Autowired
	private MongoUtil mongoUtil;
	
	private static Logger log = Logger.getLogger(DataCountBusinessImpl.class);
	/**
	 * 获取数据库列表
	 * @author 秦超
	 */
	@Override
	public String getDatabaseList() {
		Schema schemaObject = new Schema();
		try {
			String xmlInfo = getLatestXmlFileInfo();
			if("".equals(xmlInfo)){
				return "";
			}
			XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
			schemaObject = resultBinder.fromXml(xmlInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Database> databaseList = schemaObject.getDatabases();
		
		JSONArray resultArray = new JSONArray();
		for(Database database:databaseList){
			JSONObject databaseJson = new JSONObject();
			
			databaseJson.put("databaseName", database.getName());
			resultArray.add(databaseJson);
		}
		
		// TODO Auto-generated method stub
		return resultArray.toJSONString();
	}

	/**
	 * 获取数据库下表列表及统计信息
	 * @author 秦超
	 */
	@Override
	public String getTableDataCount(String databaseName) {
		JSONArray resultArray = new JSONArray();
		List<HashMap<String, Object>> tableDataCount = null;
		try {
			tableDataCount = mongoUtil.getDatabaseTableCount(databaseName);
			
			for(HashMap<String, Object> tableCount:tableDataCount){
				JSONObject tableJson = new JSONObject();
				
				tableJson.put("tableName", tableCount.get("tableName").toString());
				tableJson.put("count", tableCount.get("count").toString());
				resultArray.add(tableJson);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		
		// TODO Auto-generated method stub
		return resultArray.toJSONString();
	}

	/**
	 * 获取数据库统计信息
	 * @author 秦超
	 */
	@Override
	public String getDatabaseDataCount() {
		Schema schemaObject = new Schema();
		try {
			String xmlInfo = getLatestXmlFileInfo();
			if("".equals(xmlInfo)){
				return "";
			}
			XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
			schemaObject = resultBinder.fromXml(xmlInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Database> databaseList = schemaObject.getDatabases();
		
		JSONArray resultArray = new JSONArray();
		List<HashMap<String, Object>> tableDataCount;
		for(Database database:databaseList){
			JSONObject databaseJson = new JSONObject();
			JSONArray tableArray = new JSONArray();
			try {
				//获取库下的所有表统计信息
				tableDataCount = dataCountDaoI.getDatabaseTableCount(database.getName());
				for(HashMap<String, Object> tableCount:tableDataCount){
					JSONObject tableJson = new JSONObject();
					
					tableJson.put("tableName", tableCount.get("tableName").toString());
					tableJson.put("count", tableCount.get("count").toString());
					tableArray.add(tableJson);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			databaseJson.put("databaseName", database.getName());
			databaseJson.put("tableCount", tableArray);
			resultArray.add(databaseJson);
		}
		
		return resultArray.toJSONString();
	}
	
	/**
	 * 获取最新的xml文件文本信息
	 * @return xml文件文本信息
	 * @throws IOException
	 * @author 秦超
	 * @time 2017-05-10 10:46:52
	 */
	private static String getLatestXmlFileInfo() throws IOException{
		String xmlFilePrefix = CM_CONFIG_PROPERTIES.XML_FILE_PATH;
		
		File f = new File(xmlFilePrefix);
		if (!f.exists()) {
			log.info(xmlFilePrefix + " not exists");
		    return "";
		}

		File files[] = f.listFiles();
		long latestFileTime = 0;
		String latsestFileName = "";
		//比较文件修改时间
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			
			long lastModifiedTime = file.lastModified();
			if(lastModifiedTime > latestFileTime){
				latestFileTime = lastModifiedTime;
				latsestFileName = file.getName();
			}
		}
		
		String filepath = xmlFilePrefix + latsestFileName;
		
		String temp  = "";
		FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuffer buffer = new StringBuffer();
		
        //读取文件信息
		File file = new File(filepath);//文件路径(包括文件名称)
        try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			
			//文件原有内容
			for(int i=0;(temp =br.readLine())!=null;i++){
				buffer.append(temp);
				// 行与行之间的分隔符 相当于“\n”
				buffer = buffer.append(System.getProperty("line.separator"));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally {
            //不要忘记关闭
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
		
        return buffer.toString();
	}
	
	/**
	 * 基于mongoDB的获取数据库信息
	 * @author 徐超
	 * @Date 2017年7月12日 下午4:00:56
	 * @return
	 */
	@Override
	public JSONObject getDatabaseInfo(){
		//定义返回的Json对象
		JSONObject resultJson = new JSONObject();
		try {
			JSONArray dataArray  = new JSONArray();
			//通过最新的xml文件获取数据库列表
			String databaseList = getDatabaseList();
			//将数据库列表转为数组
			JSONArray databaseArray = JSON.parseArray(databaseList);
			//遍历数据库数组
			for(int i=0;i<databaseArray.size();i++){
				//返回数据中的节点
				JSONArray tableCount = new JSONArray();
				JSONObject dataJson = new JSONObject();
				JSONObject databaseJson = databaseArray.getJSONObject(i);
				//获取数据库名称
				String databaseName = databaseJson.getString("databaseName");
				//获得MongoDB数据库对象
				DB db = mongoUtil.getDataBases(databaseName);
				//获取指定数据库的所有集合列表
				Set<String> collectionSet = db.getCollectionNames();
				//遍历集合列表
				for(String collectionName:collectionSet){
					JSONObject tableCountJson = new JSONObject();
					//获取集合对象
					DBCollection collection = mongoUtil.useCollection(db,collectionName);
					//获取集合数据数量
					long count = collection.count();
					tableCountJson.put("tableName", collectionName);
					tableCountJson.put("count", String.valueOf(count));
					tableCount.add(tableCountJson);
				}
				dataJson.put("databaseName", databaseName);
				dataJson.put("tableCount", tableCount);
				dataArray.add(dataJson);
			}
			resultJson.put("code", "0");
			resultJson.put("message", "获取成功");
			resultJson.put("data", dataArray);
		
		} catch (Exception e) {
			resultJson.put("code", "2");
			resultJson.put("message", "获取失败，发生未知错误");
			resultJson.put("data", null);
		}
		return resultJson;
	}
}
