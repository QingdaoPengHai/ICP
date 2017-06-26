package com.penghai.css.management.model.databaseModel;

import javax.xml.bind.annotation.XmlAttribute;
/**
 * 列对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:49:22
 */
public class Column extends TargetColumn{

	//是否为主键
	private boolean primaryKey;
	//长度
	private int length;
	//精度
	private int precision;
	//是否为分区字段
	private boolean partition;
	//是否必填
	private boolean isNull;
	//外键名称
	private String foreinKey;
	//默认值
	private String defaultValue = null;
	//是否建立索引
	private boolean createIndex;
	
	@XmlAttribute(name = "primaryKey")
	public boolean getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}
	
	@XmlAttribute(name = "length")
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	
	@XmlAttribute(name = "precision")
	public int getPrecision() {
		return precision;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
	@XmlAttribute(name = "partition")
	public boolean getPartition() {
		return partition;
	}
	public void setPartition(boolean partition) {
		this.partition = partition;
	}
	
	@XmlAttribute(name = "isNull")
	public boolean getIsNull() {
		return isNull;
	}
	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}
	
	@XmlAttribute(name = "foreinKey")
	public String getForeinKey() {
		return foreinKey;
	}
	public void setForeinKey(String foreinKey) {
		this.foreinKey = foreinKey;
	}
	
	@XmlAttribute(name = "defaultValue")
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@XmlAttribute(name = "createIndex")
	public boolean getCreateIndex() {
		return createIndex;
	}
	public void setCreateIndex(boolean createIndex) {
		this.createIndex = createIndex;
	}
	
	
}
