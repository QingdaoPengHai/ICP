package com.penghai.store.order.model;

import java.math.BigDecimal;
import java.util.Date;

import com.penghai.store.util.common.CommonUtils;

public class Order {
	private Integer id;

	private String orderCode;

	private Integer userId;

	private String userName;

	/** 查询用 开始 */
	private Date beginDate;

	private Date endDate;

	private BigDecimal lowPrice;

	private BigDecimal highPrice;
	/** 查询用 结束 */

	// private List<Good> goods;

	private Date buyTime;

	private String buyTimeStr;

	private BigDecimal totalPrice;

	private Byte status;

	private String statusName;
	// 用户删除:1
	private String isDelete;

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(BigDecimal lowPrice) {
		this.lowPrice = lowPrice;
	}

	public BigDecimal getHighPrice() {
		return highPrice;
	}

	public void setHighPrice(BigDecimal highPrice) {
		this.highPrice = highPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode == null ? null : orderCode.trim();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
		if (buyTime != null) {
			setBuyTimeStr(CommonUtils.formatData(null, this.buyTime));
		}
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
		// TODO 根据状态赋值状态名
	}

	public String getBuyTimeStr() {
		return buyTimeStr;
	}

	public void setBuyTimeStr(String buyTimeStr) {
		this.buyTimeStr = buyTimeStr;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}