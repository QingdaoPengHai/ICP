package com.penghai.testoficp.analysis.model;

import javax.xml.bind.annotation.XmlAttribute;
/**
 * 列对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:49:22
 */
public class TargetColumn {

	//字段名称
	private String name;
	//字段类型
	private String type;
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
