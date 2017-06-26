package com.penghai.css.analysis.model;

import javax.xml.bind.annotation.XmlAttribute;

public class MycatWriteHost {

	private String host;
	private String url;
	private String user;
	private String password;
	
	@XmlAttribute(name = "host")
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	@XmlAttribute(name = "url")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@XmlAttribute(name = "user")
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	@XmlAttribute(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
