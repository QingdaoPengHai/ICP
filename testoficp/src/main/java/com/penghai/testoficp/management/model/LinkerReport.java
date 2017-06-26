/*
 * function: linkerReport model
 * author： LiuLihua
 * date：2017-05-10
 */

package com.penghai.testoficp.management.model;

import java.util.Date;


public class LinkerReport {
	private String linkerId;
	private String reportErrorType;
	private String reportErrorDetail;
	private Date reportErrorTime;
	
	public String getLinkerId() {
		return linkerId;
	}
	
	public void setLinkerId(String linkerId) {
		this.linkerId = linkerId;
	}
	
	public String getReportErrorType() {
		return reportErrorType;
	}

	public void setReportErrorType(String reportErrorType) {
		this.reportErrorType = reportErrorType;
	}

	public String getReportErrorDetail() {
		return reportErrorDetail;
	}
	
	public void setReportErrorDetail(String reportErrorDetail) {
		this.reportErrorDetail = reportErrorDetail;
	}

	public Date getReportErrorTime() {
		return reportErrorTime;
	}
	
	public void setReportErrorTime(Date reportErrorTime) {
		this.reportErrorTime = reportErrorTime;
	}

}
