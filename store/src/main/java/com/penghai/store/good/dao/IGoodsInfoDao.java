package com.penghai.store.good.dao;

import com.penghai.store.good.model.GoodsDetailInfo;

public interface IGoodsInfoDao {

	/**
	 * 封装商品详情实体,包含图片列表和文件列表
	 * 
	 * @author 徐超
	 * @Date 2017年4月25日 下午1:38:10
	 * @param goodsId
	 * @return
	 */
	GoodsDetailInfo getGoodsDetailByGoodsId(Integer goodsId);
}
