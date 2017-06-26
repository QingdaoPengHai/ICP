package com.penghai.linker.dataAcquisition.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.util.EhcacheUtil;
import com.penghai.linker.util.SpringUtil;

/**
 * 数据库对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:48:45
 */
@XmlRootElement(name = "targetDatabase")
public class TargetDatabaseRoot {

	//与target数据库对应的库名
	private String targetRef;
	//要查询的数据库名称
	private String name;
	//所对应的linker的ID
	private String linkerId;
	//所属表的列表
	private List<TargetTable> targetTables;
	//连接该数据库所使用的用户名密码
	private User user;
	
	@XmlAttribute(name = "targetRef")
	public String getTargetRef() {
		return targetRef;
	}
	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "linkerId")
	public String getLinkerId() {
		return linkerId;
	}
	public void setLinkerId(String linkerId) {
		this.linkerId = linkerId;
	}
	
	@XmlElement(name = "targetTable")
	public List<TargetTable> getTargetTables() {
		return targetTables;
	}
	public void setTargetTables(List<TargetTable> targetTables) {
		this.targetTables = targetTables;
	}
	
	@XmlElement(name = "user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * 生成查询语句map列表
	 * @author 徐超
	 * @Date 2017年5月17日 下午3:29:53
	 * @return
	 */
	public List<HashMap<String,Object>> createSelectSqlMap(){
		//初始化Ehcache对象
		EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		//获取目标数据库名称和LinkerId以及要查询的数据库名称
		String targetDatabaseName = this.targetRef;
		String linkerId = this.linkerId;
		String selectDatabaseName = this.name;
		
		ehcacheUtil.put("targetDatabaseName", targetDatabaseName);
		ehcacheUtil.put("linkerId", linkerId);
		ehcacheUtil.put("selectDatabaseName", selectDatabaseName);
		
		//遍历TargetTableList
		for(TargetTable table:this.targetTables){
			HashMap<String,Object> tableMap = new HashMap<String, Object>();
			StringBuffer selectOneTableSQL = new StringBuffer();
			//拼接查询语句,无LIMIT字句
			selectOneTableSQL.append("SELECT ").append(table.getColumnList())
							 .append(" FROM ").append(this.name)
							 .append(".`").append(table.getName()).append("` ");
			tableMap.put("storageTable", table.getTargetRef());
			tableMap.put("targetTable", table.getName());
			tableMap.put("queryInterval", table.getQueryInterval());
			tableMap.put("selectSQL",selectOneTableSQL.toString());
			tableMap.put("primaryKey", table.getPrimaryKeyAndOrder().get("primaryKey"));
			tableMap.put("primaryKeyOrder", table.getPrimaryKeyAndOrder().get("order"));
			list.add(tableMap);
		}
		
		return list;
	}
	/**
	 * 重载--生成查询语句map列表--带database对象的,添加列名列表
	 * @author 徐超
	 * @Date 2017年6月8日11:03:27
	 * @return
	 */
	public List<HashMap<String,Object>> createSelectSqlMap(String refDatabaseInfoJson){
		
		JSONArray refDatabaseInfoJsonArray = JSONArray.parseArray(refDatabaseInfoJson);
		//初始化Ehcache对象
		EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		
		//获取目标数据库名称和LinkerId以及要查询的数据库名称
		String targetDatabaseName = this.targetRef;
		String linkerId = this.linkerId;
		String selectDatabaseName = this.name;
		
		ehcacheUtil.put("targetDatabaseName", targetDatabaseName);
		ehcacheUtil.put("linkerId", linkerId);
		ehcacheUtil.put("selectDatabaseName", selectDatabaseName);
		
		//遍历TargetTableList
		for(TargetTable table:this.targetTables){
			HashMap<String,Object> tableMap = new HashMap<String, Object>();
			StringBuffer selectOneTableSQL = new StringBuffer();
			//拼接查询语句,无LIMIT字句
			selectOneTableSQL.append("SELECT ").append(table.getColumnList())
			.append(" FROM ").append(this.name)
			.append(".`").append(table.getName()).append("` ");
			tableMap.put("storageTable", table.getTargetRef());
			tableMap.put("targetTable", table.getName());
			tableMap.put("queryInterval", table.getQueryInterval());
			tableMap.put("selectSQL",selectOneTableSQL.toString());
			tableMap.put("primaryKey", table.getPrimaryKeyAndOrder().get("primaryKey"));
			tableMap.put("primaryKeyOrder", table.getPrimaryKeyAndOrder().get("order"));
			for(int i=0;i<refDatabaseInfoJsonArray.size();i++){
				JSONObject json = refDatabaseInfoJsonArray.getJSONObject(i);
				if(table.getName().equals(json.getString("targetTableName"))){
					tableMap.put("refTableName", json.getString("refTableName"));
					tableMap.put("refColumns", json.getString("refColumns"));
					tableMap.put("refPrimaryKey", json.getString("refPrimaryKey"));
					break;
				}
			}
			list.add(tableMap);
		}
		
		return list;
	}
	
	
}
