package com.penghai.store.order.model;

import java.math.BigDecimal;

/**
 * 购物车实体类
 * @author 徐超
 * @Date 2017年4月26日 上午11:06:36
 */
public class ShoppingCart {

	//用户ID
	private String userId;
	//商品ID
	private String goodsId;
	//商品名称
	private String goodsName;
	//商品数量
	private String goodsNumber;
	//商品价格
	private BigDecimal goodsPrice;
	//商品图片地址
	private String pictureURL;
	//商品描述
	private String description;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsNumber() {
		return goodsNumber;
	}
	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}
	public BigDecimal getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(BigDecimal goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getPictureURL() {
		return pictureURL;
	}
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
