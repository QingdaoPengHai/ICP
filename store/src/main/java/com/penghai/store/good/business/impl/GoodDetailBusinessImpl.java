package com.penghai.store.good.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghai.store.good.business.GoodDetailBusiness;
import com.penghai.store.good.dao.IGoodsInfoDao;
import com.penghai.store.good.model.GoodsDetailInfo;

@Service
public class GoodDetailBusinessImpl implements GoodDetailBusiness {
	@Autowired
	private IGoodsInfoDao iGoodsInfoDao;

	@Override
	public GoodsDetailInfo getGoodInfoById(int goodsId) {
		
		GoodsDetailInfo goodsDetailInfo = iGoodsInfoDao.getGoodsDetailByGoodsId(goodsId);
		
		return goodsDetailInfo;
	}
	
}
