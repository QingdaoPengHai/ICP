package com.penghai.store.good.model;

import java.math.BigDecimal;
import java.util.Date;

public class Goods {
	private Integer id;

	private Integer goodCate;

	private String goodCateName;

	private String goodName;

	private String description = "";

	private BigDecimal price;

	private String goodModel;

	private Date expiryDate;

	private Boolean isForSale;

	private Byte status;

	private Date addTime;

	private Date updateTime;

	private int pageIndex;

	private int pageSize;

	private String defaultPicture;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGoodCate() {
		return goodCate;
	}

	public void setGoodCate(Integer goodCate) {
		this.goodCate = goodCate;
	}

	public String getGoodCateName() {
		return goodCateName;
	}

	public void setGoodCateName(String goodCateName) {
		this.goodCateName = goodCateName;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName == null ? null : goodName.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getGoodModel() {
		return goodModel;
	}

	public void setGoodModel(String goodModel) {
		this.goodModel = goodModel == null ? null : goodModel.trim();
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Boolean getIsForSale() {
		return isForSale;
	}

	public void setIsForSale(Boolean isForSale) {
		this.isForSale = isForSale;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDefaultPicture() {
		return defaultPicture;
	}

	public void setDefaultPicture(String defaultPicture) {
		this.defaultPicture = defaultPicture;
	}

}