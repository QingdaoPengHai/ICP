/*
 * function: linkerReport model
 * author： LiuLihua
 * date：2017-05-10
 */

package com.penghai.css.management.model;

import java.text.DateFormat;
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
	/**
	 * 页面显示时间
	 * @author 刘晓强
	 * @date 2017年5月19日
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public String getReportErrorStringTime() {
		return reportErrorTime.toLocaleString();
		//return DateFormat.format(this.reportErrorTime);
	}
	
	public void setReportErrorTime(Date reportErrorTime) {
		this.reportErrorTime = reportErrorTime;
	}

}
