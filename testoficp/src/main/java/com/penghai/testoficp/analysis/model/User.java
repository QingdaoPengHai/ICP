package com.penghai.testoficp.analysis.model;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * 用户信息实体
 * @author 徐超
 * @Date 2017年5月10日 上午9:52:14
 */
public class User {

	//用户名
	private String name;
	//用户密码
	private String password;
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * 生成创建用户语句
	 * @author 徐超
	 * @Date 2017年5月4日 下午9:33:28
	 * @return
	 */
	public String createSQL(){
		StringBuffer sql = new StringBuffer();
		
		
		
		
		return sql.toString();
	}
}
