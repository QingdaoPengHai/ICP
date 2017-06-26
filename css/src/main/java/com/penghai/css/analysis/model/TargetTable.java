package com.penghai.css.analysis.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * 表对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:49:01
 */
public class TargetTable {

	//表名
	private String name;
	//与target表库对应的表名
	private String targetRef;
	//读取时间间隔
	private int queryInterval;
	//表列表
	private List<TargetColumn> columns;
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "targetRef")
	public String getTargetRef() {
		return targetRef;
	}
	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	
	@XmlAttribute(name = "queryInterval")
	public int getQueryInterval() {
		return queryInterval;
	}
	public void setQueryInterval(int queryInterval) {
		this.queryInterval = queryInterval;
	}
	
	@XmlElement(name = "column")
	public List<TargetColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<TargetColumn> columns) {
		this.columns = columns;
	}
	
}
