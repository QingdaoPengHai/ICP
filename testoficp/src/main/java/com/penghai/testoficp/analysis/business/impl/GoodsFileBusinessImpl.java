package com.penghai.testoficp.analysis.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.testoficp.analysis.business.GoodsFileBusiness;
import com.penghai.testoficp.analysis.business.XMLDatabaseTransferBusinessI;
import com.penghai.testoficp.analysis.model.Schema;
import com.penghai.testoficp.analysis.model.TargetDatabase;
import com.penghai.testoficp.common.model.HttpSession;
import com.penghai.testoficp.util.HTTPUtil;
import com.penghai.testoficp.util.JedisUtils;
import com.penghai.testoficp.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.testoficp.util.CommonData.CM_LINKER_REDIS_PROPERTIES;
import com.penghai.testoficp.util.CommonData.CM_STORE_SERVER_FUNCTION;
import com.penghai.testoficp.util.file.FileUtil;
import com.penghai.testoficp.util.xml.XmlUtil;
import com.penghai.testoficp.util.xml.XmlUtil.CollectionWrapper;

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
	/**
	 * 获取单条订单xml文件详情信息
	 * @author 秦超
	 */
	@Override
	public Map<String, Object> getXmlInfo(String xmlId){
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
				//保存xml文件
				FileUtil.createFile(fileName, xmlContent);
				//根据xml信息获取scheme实体，提取linkerId进行比对 
				Schema schemaObject = new Schema();
				XmlUtil resultBinder = new XmlUtil(Schema.class,CollectionWrapper.class);
				schemaObject = resultBinder.fromXml(xmlContent);
				List<TargetDatabase> targetDatabases = schemaObject.getTargetDatabases();
				//依次比对linkerId在Redis中存储的值中是否存在
				for(TargetDatabase targetDatabase: targetDatabases){
					String linkerId = targetDatabase.getLinkerId();
					setLinkerIdToRedis(linkerId);
				}
				//获取建库语句
				String createSql = xmlDatabaseTransferBusinessI.analyzeXMLStringToSQL(xmlContent);
				//执行建库
				xmlDatabaseTransferBusinessI.executeSQL(createSql);
				
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
		}
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
	
}
