package com.penghai.css.analysis.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.penghai.css.analysis.business.IMycatBusiness;
import com.penghai.css.analysis.business.impl.GoodsFileBusinessImpl;
import com.penghai.css.analysis.model.Database;
import com.penghai.css.analysis.model.MycatWriteHost;
import com.penghai.css.analysis.model.MycatWriteHostList;
import com.penghai.css.analysis.model.Schema;
import com.penghai.css.util.CommonData.CM_MYCAT_CONFIGURATION;
import com.penghai.css.util.CommonData.CM_MYCAT_TEST;
import com.penghai.css.util.xml.XmlUtil;
import com.penghai.css.util.xml.XmlUtil.CollectionWrapper;

@Controller
@RequestMapping(value = "/test")
public class ControllerTest {
	
	@Autowired
	private IMycatBusiness iMycatBusiness;

	@RequestMapping(value = "/prop")
	public void testProperties(){
		String XMLString = CM_MYCAT_CONFIGURATION.WRITE_HOSTLIST;
		MycatWriteHostList list = new MycatWriteHostList();
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/business")
	public void testBusiness(){
		System.out.println("==========this is a test start==========");
		
		String xmlContent = CM_MYCAT_TEST.XML_CONTENT;
		
		Schema schema = iMycatBusiness.analyzedSchemaXmlContent(xmlContent);
		
		List<MycatWriteHost> writeHosts = iMycatBusiness.getWriteHosts();
		
		String createSQL = schema.conbineAllCreateSQL();
		
		boolean result = iMycatBusiness.createDatabasesAndTablesOnWriteHosts(writeHosts, createSQL);
		
		if(result){
			System.out.println("建库成功");
		}else{
			System.out.println("建库失败");
		}
		
		JSONObject json = iMycatBusiness.getMycatConfigFilesContent(schema);
		
		Database database = (Database) json.get("sequenceDataNodeDatabase");
		MycatWriteHost writeHost = (MycatWriteHost) json.get("sequenceDataNodeWriteHost");
		
		boolean createSequenceResult = iMycatBusiness.createGlobalSequence(database, writeHost);
		if(createSequenceResult){
			System.out.println("全局序列创建成功");
			System.out.println(json.getString("sequence_db_conf"));
		}else{
			System.out.println("全局序列创建失败");
		}
		
		boolean createFileResult = iMycatBusiness.saveMycatConfigfiles(json);
		
		if(createFileResult){
			System.out.println("文件保存成功");
		}else{
			System.out.println("文件保存失败");
		}
		
		boolean executeResult = iMycatBusiness.restartMycat();
		
		if(executeResult){
			System.out.println("重启成功");
		}else{
			System.out.println("重启失败");
		}
		
			
		System.out.println("==========this is a test end==========");
		
	}
	
	@RequestMapping(value = "/file")
	public void file(){
		String filePath = "G:/file.txt";
		
		File file = new File(filePath);
		
		try {
//			判断文件是否存在,不存在则创建文件,并向文件中写入内容
			if(!file.exists()){
				file.createNewFile();
			}
			FileWriter resultFile = new FileWriter(file);
			PrintWriter myFile = new PrintWriter(resultFile);
			myFile.println("asdasd123");
			resultFile.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/linux")
	public void linux(){
		InputStream in = null;
		try {
			Process pro = Runtime.getRuntime().exec("sh /usr/local/mycat/bin/mycat restart");
			pro.waitFor();
			in = pro.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			String result = read.readLine();
			System.out.println("=============INFO:"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/json")
	public void testJson(){
		
		String xmlInfo = CM_MYCAT_TEST.XML_CONTENT;
		String linkerId = "1Lk9hgz4NASgyrtpoLWfnTqt3HEK1lu2";
		String aa = GoodsFileBusinessImpl.getDatabaseInfoJson(xmlInfo, linkerId);
		System.out.println(aa);
	}
	

}
