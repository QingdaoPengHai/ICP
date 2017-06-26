package com.penghai.css.management.model.databaseModel;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.alibaba.fastjson.JSONObject;
/**
 * 数据库对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:48:45
 */
public class TargetDatabase {

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
	
	
	
	
}
