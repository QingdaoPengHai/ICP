/*
 * function: linker model
 * author： LiuLihua
 * date：2017-05-10
 */

package com.penghai.css.management.model;

import java.util.Date;
import java.util.List;

import com.penghai.css.util.CommonData;



public class Linker {
	private String linkerId;
	private String linkerIPAddress;
	private Date registerTime;
	private String linkerStatus;
	private List<LinkerReport> LinkerReports;
	public String getLinkerId() {
		return linkerId;
	}
	
	public void setLinkerId(String linkerId) {
		this.linkerId = linkerId;
	}
	
	public String getLinkerIPAddress() {
		return linkerIPAddress;
	}
	
	public void setLinkerIPAddress(String linkerIPAddress) {
		this.linkerIPAddress = linkerIPAddress;
	}

	public String getLinkerStatus() {
		return linkerStatus;
	}
	
	public void setLinkerStatus(String linkerStatus) {
		this.linkerStatus = linkerStatus;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public List<LinkerReport> getLinkerReports() {
		return LinkerReports;
	}

	public void setLinkerReports(List<LinkerReport> linkerReports) {
		LinkerReports = linkerReports;
	}

	/**
	 * 返回给页面展示字段
	 * @author 刘晓强
	 * @date 2017年5月10日
	 * @return
	 */
	public String getLinkStatusWord() {
		if(linkerStatus.equals(CommonData.CM_LINKER_CONFIGURE.LINKER_REGISTERED)){
			return "已注册";
		}else{
			return "未注册";
		}
	}
}
