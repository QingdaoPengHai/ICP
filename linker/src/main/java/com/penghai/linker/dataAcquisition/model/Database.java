package com.penghai.linker.dataAcquisition.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.JSONObject;
/**
 * 数据库对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:48:45
 */
public class Database {

	//数据库名
	private String name;
	//新建用户信息
	private User user;
	//表的列表
	private List<Table> tables;
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "user")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@XmlElement(name = "table")
	public List<Table> getTables() {
		return tables;
	}
	public void setTables(List<Table> tables) {
		this.tables = tables;
	}
	/**
	 * 返回建库语句
	 * @author 徐超
	 * @Date 2017年5月3日 下午6:03:05
	 * @return
	 */
	public String createSQL(){
		StringBuffer sql = new StringBuffer();
		sql = sql.append("CREATE DATABASE IF NOT EXISTS ")
				 .append(this.name)
				 .append(" CHARACTER SET = utf8 COLLATE = utf8_general_ci; ");
		return sql.toString();
	}
	/**
	 * 返回创建用户的sql
	 * @author 徐超
	 * @Date 2017年5月6日 上午11:23:23
	 * @return
	 */
	public String createUserSql(){
		StringBuffer sql = new StringBuffer();
		
		sql.append("GRANT ALL PRIVILEGES ON *.* TO '")
		   .append(this.user.getName()).append("'@'%' IDENTIFIED BY '")
		   .append(this.user.getPassword()).append("' ").append("WITH GRANT OPTION; ")
		   .append("FLUSH PRIVILEGES; ");
		
		return sql.toString();
	}
	/**
	 * 拼接生成的创建表的SQL语句
	 * @author 徐超
	 * @Date 2017年5月6日 下午1:11:35
	 * @return
	 */
	public String conbineCreateTableSQL(){
		StringBuffer sql = new StringBuffer();
		
		for(Table table : tables){
			//指定执行的数据库
			sql.append(table.createTableSQL(this.name));
			//.append(" USE ").append(this.name).append(";")
		}
		
		return sql.toString();
	}
	
	/**
	 * 拼接生成的创建外键约束的SQL语句
	 * @author 徐超
	 * @Date 2017年5月6日 下午3:01:57
	 * @return
	 */
	public String conbineCreateForeignKeySQL(){
		StringBuffer sql = new StringBuffer();
		
		for(Table table : tables){
			if(!"".equals(table.createForeignKeySQL(this.name))){
				//指定执行的数据库
				sql.append(table.createForeignKeySQL(this.name));
				//.append(" USE ").append(this.name).append(";")
			}
		}
		return sql.toString();
	}
	
	/**
	 * 拼接一个数据库的完整建库SQL
	 * @author 徐超
	 * @Date 2017年5月6日 下午4:11:33
	 * @return
	 */
	public String conbineAllCreateSQL(){
		StringBuffer sql = new StringBuffer();
		
		sql.append(createSQL()).append(createUserSql()).append(conbineCreateTableSQL()).append(conbineCreateForeignKeySQL());
		
		return sql.toString();
	}
	/**
	 * 返回数据库对象jsonString
	 * @author 徐超
	 * @Date 2017年5月3日 下午6:03:14
	 * @return
	 */
	public String createJsonString(){
		String jsonString = "";
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", this.name);
		jsonObject.put("tables", this.tables);
		jsonString = jsonObject.toJSONString();
		return jsonString;
	}
}
