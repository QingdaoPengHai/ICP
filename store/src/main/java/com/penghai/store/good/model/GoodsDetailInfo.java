package com.penghai.store.good.model;

import java.util.List;

public class GoodsDetailInfo {
	private Goods goods;
	private List<GoodsFile> goodsFileList;
	private List<GoodsPicture> goodsPictureList;
	
	public Goods getGoods() {
		return goods;
	}
	public void setGoods(Goods goods) {
		this.goods = goods;
	}
	public List<GoodsFile> getGoodsFileList() {
		return goodsFileList;
	}
	public void setGoodsFileList(List<GoodsFile> goodsFileList) {
		this.goodsFileList = goodsFileList;
	}
	public List<GoodsPicture> getGoodsPictureList() {
		return goodsPictureList;
	}
	public void setGoodsPictureList(List<GoodsPicture> goodsPictureList) {
		this.goodsPictureList = goodsPictureList;
	}
	
}
