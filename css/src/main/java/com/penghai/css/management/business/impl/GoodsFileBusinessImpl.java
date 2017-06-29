package com.penghai.css.management.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.css.common.model.HttpSession;
import com.penghai.css.management.business.GoodsFileBusiness;
import com.penghai.css.management.business.ICreateDataBaseAndTableByMongo;
import com.penghai.css.management.business.IQueryIfExistSchema;
import com.penghai.css.management.business.XMLDatabaseTransferBusinessI;
import com.penghai.css.management.model.databaseModel.Database;
import com.penghai.css.management.model.databaseModel.Schema;
import com.penghai.css.management.model.databaseModel.Table;
import com.penghai.css.management.model.databaseModel.TargetDatabase;
import com.penghai.css.management.model.databaseModel.TargetTable;
import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.css.util.CommonData.CM_LINKER_REDIS_PROPERTIES;
import com.penghai.css.util.CommonData.CM_STORE_SERVER_FUNCTION;
import com.penghai.css.util.HTTPUtil;
import com.penghai.css.util.JedisUtils;
import com.penghai.css.util.file.FileUtil;
import com.penghai.css.util.xml.XmlUtil;
import com.penghai.css.util.xml.XmlUtil.CollectionWrapper;

import redis.clients.jedis.Jedis;

/**
 * 订单文件处理
 * @author 秦超
 *
 */
@Service
public class GoodsFileBusinessImpl implements GoodsFileBusiness{
	@Autowired
	public HttpSession session;
	@Autowired
	private JedisUtils jedisUtils;
	@Autowired
	public XMLDatabaseTransferBusinessI xmlDatabaseTransferBusinessI;
	@Autowired
	private ICreateDataBaseAndTableByMongo iCreateDataBaseAndTableByMongo;
	@Autowired
	private IQueryIfExistSchema iQueryIfExistSchema;
	/**
	 * 获取单条订单xml文件详情信息
	 * @author 秦超
	 */
	@Override
	public Map<String, Object> getXmlInfo(String xmlId, int label) {
		Map<String, Object> resultMap = new HashMap<>();
		//请求url
		String getXmlInfoUrl = CM_CONFIG_PROPERTIES.STORE_SERVER_URL + CM_STORE_SERVER_FUNCTION.ORDER_XML_DETAIL;
		String updateStatusUrl = CM_CONFIG_PROPERTIES.STORE_SERVER_URL + CM_STORE_SERVER_FUNCTION.UPDATE_ORDER_STATUS;
		
		String email = session.get("email");
		String password = session.get("password");
		
		String xmlInfoResult = "";
		String updateStatusResult = "";
		try {
//			xmlInfoResult = HTTPUtil.loadURL(getXmlInfoUrl+"?email=qc@qq.com&password=123123&xmlId="+xmlId);
			xmlInfoResult = HTTPUtil.loadURL(getXmlInfoUrl+"?email=" + email + "&password=" + password + "&xmlId="+xmlId);
			String loginResultDecode = URLDecoder.decode(xmlInfoResult,"UTF-8"); 
			JSONObject json = JSONObject.parseObject(loginResultDecode);
			if("1".equals(json.getString("result"))){
				JSONObject xmlInfo = json.getJSONObject("xmlDetail");
				String fileName = xmlInfo.getString("fileName");
				String xmlContent = xmlInfo.getString("xmlContent");
				
				//根据xml信息获取scheme实体，提取linkerId进行比对 
				Schema schemaObject = new Schema();
				XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
				schemaObject = resultBinder.fromXml(xmlContent);
				// 用户不关心数据库及集合名是否重名 则跳过
				if (label == 0) {
					// 用户点击下载则返回查询结果
					return iQueryIfExistSchema.queryIfExistSchema(schemaObject);
				}
				//保存xml文件
				boolean createFile =  FileUtil.createFile(fileName, xmlContent);
				if(createFile == false){
					resultMap.put("code", 0);
					resultMap.put("message", "xml文件名已存在，保存失败！");
					return resultMap;
				}
				List<TargetDatabase> targetDatabases = schemaObject.getTargetDatabases();
				//依次比对linkerId在Redis中存储的值中是否存在
				for(TargetDatabase targetDatabase: targetDatabases){
					String linkerId = targetDatabase.getLinkerId();
					setLinkerIdToRedis(linkerId);
				}
				//更新本地静态文件中记录的linkerId信息
				updateLinkerIds();
				/*
				 * //获取建库语句; String createSql =
				 * xmlDatabaseTransferBusinessI.analyzeXMLStringToSQL(xmlContent
				 * ); //执行建库 xmlDatabaseTransferBusinessI.executeSQL(createSql);
				 */
				iCreateDataBaseAndTableByMongo.createDataBaseAndTable(schemaObject);
//				updateStatusResult = HTTPUtil.loadURL(updateStatusUrl+"?email=qc@qq.com&password=123123&xmlId="+xmlId);
				updateStatusResult = HTTPUtil.loadURL(updateStatusUrl+"?email=" + email + "&password=" + password + "&xmlId="+xmlId);
			}else {
				resultMap.put("code", 0);
				resultMap.put("message", "未检索到文件！");
				return resultMap;
			}
		} catch (Exception e) {
			resultMap.put("code", 0);
			resultMap.put("message", "未知错误！");
			e.printStackTrace();
			return resultMap;
		}
		
		resultMap.put("code", 1);
		resultMap.put("message", "下载成功！");
		return resultMap;
	}

	/**
	 * 更新本地文件中记录的linkerId信息
	 * @author 秦超
	 * @time 2017年5月12日
	 */
	private void updateLinkerIds() throws IOException {
		Jedis jedis = jedisUtils.getJedis();
		String redisLinkerKey = CM_LINKER_REDIS_PROPERTIES.LINKER_KEY;
		String linkerIds = jedis.get(redisLinkerKey);
		jedisUtils.releaseJedis(jedis);
		
		String LinkerIdsFile = CM_CONFIG_PROPERTIES.LINKERID_FILE_PATH;
        FileOutputStream fos  = null;
        PrintWriter pw = null;
        StringBuffer buffer = new StringBuffer();
		
		File file = new File(LinkerIdsFile);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			buffer.append(linkerIds);
            
            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
        }finally {
            //不要忘记关闭
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
	}

	/**
	 * 将传入的字符串与Redis中存储的做比较，存在则不处理，不存在就添加进去
	 * @author 秦超
	 * @time 2017年5月11日
	 */
	private void setLinkerIdToRedis(String linkerId) {
		Jedis jedis = jedisUtils.getJedis();
		String redisLinkerKey = CM_LINKER_REDIS_PROPERTIES.LINKER_KEY;
		String linkerIds = jedis.get(redisLinkerKey);
		if(linkerIds!=null && !"null".equals(linkerIds)){			
			if(!(linkerIds.contains(linkerId))){
				String newLinkerIds = linkerIds + "," + linkerId;
				jedis.set(redisLinkerKey, newLinkerIds);
			}
		}else {
			jedis.set(redisLinkerKey, linkerId);
		}
		jedisUtils.releaseJedis(jedis);
	}

	/**
	 * 获取linker数据库格式xml文件块
	 * @param linkerId linker设备的访问id
	 * @author 秦超
	 */
	@Override
	public String getDatabaseInfoFromXml(String linkerId) {
		
		//获取最新的xml文件路径及文件名
		String filepath = getLatestXmlFilePath();
		
		String xmlTargetDatabase = "";
		try {
			xmlTargetDatabase = getXmlTargetDatabase(filepath, linkerId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return xmlTargetDatabase;
	}
	
	/**
	 * 获取linker所需xml片段--包含database和targetDatabase
	 * @author 徐超
	 * @Date 2017年6月8日 上午11:39:49
	 * @param linkerId
	 * @return
	 */
	@Override
	public JSONObject getDatabaseAndTargetDatabaseInfoFromXml(String linkerId){
		JSONObject resultJson = new JSONObject();
		
		//获取最新的xml文件路径及文件名
		String filepath = getLatestXmlFilePath();
		
		try {
			resultJson = getXmlTargetDatabaseAndDatabase(filepath, linkerId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resultJson;
	}
	
	/**
	 * 获取最新的xml文件
	 * @return
	 * @author 秦超
	 */
	private static String getLatestXmlFilePath(){
//		String xmlFilePrefix = "D:\\file\\";
		String xmlFilePrefix = CM_CONFIG_PROPERTIES.XML_FILE_PATH;
		
		File f = new File(xmlFilePrefix);
		if (!f.exists()) {
		System.out.println(xmlFilePrefix + " not exists");
		    return "";
		}

		File files[] = f.listFiles();
		long latestFileTime = 0;
		String latsestFileName = "";
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			
			long lastModifiedTime = file.lastModified();
			if(lastModifiedTime > latestFileTime){
				latestFileTime = lastModifiedTime;
				latsestFileName = file.getName();
			}
		}
		
		return xmlFilePrefix + latsestFileName;
	}
	
	/**
	 * 根据filepath和linkerId获取对应所需xml的<targatDatabase>字段
	 * @param filepath 文件路径+文件名
	 * @param linkerId linkerId
	 * @return xml格式文本信息    <targatDatabase> ... </targatDatabase>
	 * @throws IOException
	 * @author 秦超
	 */
	private static String getXmlTargetDatabase(String filepath, String linkerId) throws IOException{
		
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
		
        //对xml文本信息进行截取
        String xmlInfo = buffer.toString();
        xmlInfo = xmlInfo.replace("</targetDatabase>", "#");
        xmlInfo = xmlInfo.replace("<targetDatabase", "@");
        xmlInfo = xmlInfo.replace(linkerId, "&");

        String[] allList = xmlInfo.split("@");
        
        for(String xmlPartString: allList){
        	String targetDatabasesString = xmlPartString.substring(0,xmlPartString.indexOf("#")+1);
        	String[] targetDatabasesList = targetDatabasesString.split("&");
        	//判断所需信息条件
        	if (targetDatabasesList.length > 1) {
        		targetDatabasesString = targetDatabasesString.replace("&", linkerId);
        		targetDatabasesString = targetDatabasesString.replace("#", "</targetDatabase>");
        		
        		return "<targetDatabase" + targetDatabasesString;
			}
        	
        }
		
		return "";
	}
	
	/**
	 * 根据filepath和linkerId获取对应所需xml的<targatDatabase>以及<Database>字段
	 * @author 徐超
	 * @Date 2017年6月8日 上午11:55:08
	 * @param filepath
	 * @param linkerId
	 * @return json 包含targetDatabase和database
	 * @throws IOException
	 */
	private static JSONObject getXmlTargetDatabaseAndDatabase(String filepath, String linkerId) throws IOException{
		
		JSONObject resultJson = new JSONObject();
		
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
		
		//对xml文本信息进行截取
		String xmlInfo = buffer.toString();
		String xmlOriginal = xmlInfo;
		xmlInfo = xmlInfo.replace("</targetDatabase>", "#");
		xmlInfo = xmlInfo.replace("<targetDatabase", "@");
		xmlInfo = xmlInfo.replace(linkerId, "&");
		
		String[] allList = xmlInfo.split("@");
		
		for(String xmlPartString: allList){
			String targetDatabasesString = xmlPartString.substring(0,xmlPartString.indexOf("#")+1);
			String[] targetDatabasesList = targetDatabasesString.split("&");
			//判断所需信息条件
			if (targetDatabasesList.length > 1) {
				targetDatabasesString = targetDatabasesString.replace("&", linkerId);
				targetDatabasesString = targetDatabasesString.replace("#", "</targetDatabase>");
				String dataBaseInfoJson = getDatabaseInfoJson(xmlOriginal,linkerId);
				String targetDataBaseInfoJson = getTargetDatabaseInfoJson(xmlOriginal, linkerId);
				resultJson.put("databaseInfoJson", dataBaseInfoJson);
				resultJson.put("targetDatabaseInfoJson", targetDataBaseInfoJson);
				resultJson.put("xmlTargetDatabase", "<targetDatabase" + targetDatabasesString);
				return resultJson;
			}
		}
		resultJson.put("xmlTargetDatabase", "");
		resultJson.put("databaseInfoJson", "");
		return resultJson;
	}
	/**
	 * 获取database与targetdatabase关联信息
	 * @author 徐超
	 * @Date 2017年6月8日 下午2:15:21
	 * @param xmlInfo
	 * @param linkerId
	 * @return
	 */
	public static String getDatabaseInfoJson(String xmlInfo,String linkerId){
		JSONArray dataBaseInfoJsonArray = new JSONArray();
		
		Schema schemaObject = new Schema();
		XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
		schemaObject = resultBinder.fromXml(xmlInfo);
		
		List<TargetDatabase> targetDatabaseList = schemaObject.getTargetDatabases();
		List<Database> databaseList = schemaObject.getDatabases();
		
		String refDatabaseName = "";
		List<TargetTable> targetTableList = new ArrayList<TargetTable>();
		List<Table> tableList = new ArrayList<Table>();
		
		//根据linkerId查询对应的
		for(TargetDatabase targetDatabase:targetDatabaseList){
			if(linkerId.equals(targetDatabase.getLinkerId())){
				refDatabaseName = targetDatabase.getTargetRef();
				targetTableList = targetDatabase.getTargetTables();
			}
		}
		
		//获取该linker对应的css中的数据库的表列表
		for(Database database:databaseList){
			if(refDatabaseName.equals(database.getName())){
				tableList = database.getTables();
			}
		}
		
		//拼接Json
		for(TargetTable targetTable:targetTableList){
			JSONObject dataBaseInfoJsonObject = new JSONObject();
			dataBaseInfoJsonObject.put("targetTableName", targetTable.getName());
			for(Table table:tableList){
				if(targetTable.getTargetRef().equals(table.getName())){
					dataBaseInfoJsonObject.put("refTableName", table.getName());
					dataBaseInfoJsonObject.put("refColumns", table.getColumnsListString());
					dataBaseInfoJsonObject.put("refPrimaryKey", table.getPrimaryKey());
					break;
				}
			}
			dataBaseInfoJsonArray.add(dataBaseInfoJsonObject);
		}
		
		return dataBaseInfoJsonArray.toJSONString();
	}
	/**
	 * 获取linker对应targetdatabase实体json信息
	 * @param xmlInfo xml文本信息
	 * @param linkerId 
	 * @return
	 * @author 秦超
	 */
	public static String getTargetDatabaseInfoJson(String xmlInfo,String linkerId){
		Schema schemaObject = new Schema();
		XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
		schemaObject = resultBinder.fromXml(xmlInfo);
		
		List<TargetDatabase> targetDatabaseList = schemaObject.getTargetDatabases();
		TargetDatabase returnTargetDatabase = new TargetDatabase();
		
		//根据linkerId查询对应的TargetDatabase
		for(TargetDatabase targetDatabase:targetDatabaseList){
			if(linkerId.equals(targetDatabase.getLinkerId())){
				returnTargetDatabase = targetDatabase;
			}
		}
		
		String targetDatabaseString = JSON.toJSONString(returnTargetDatabase);
		
		return targetDatabaseString;
	}
	
}
