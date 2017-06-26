package com.penghai.store.good.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.penghai.store.good.dao.IGoodsInfoDao;
import com.penghai.store.good.dao.mybatis.GoodsFileMapper;
import com.penghai.store.good.dao.mybatis.GoodsMapper;
import com.penghai.store.good.dao.mybatis.GoodsPictureMapper;
import com.penghai.store.good.model.GoodsDetailInfo;

@Repository
public class IGoodsDetailInfoImpl implements IGoodsInfoDao {

	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private GoodsPictureMapper goodsPictureMapper;
	@Autowired
	private GoodsFileMapper goodsFileMapper;

	/**
	 * 封装商品详情实体,包含图片列表和文件列表
	 * 
	 * @author 徐超
	 * @Date 2017年4月25日 下午1:38:10
	 * @param goodsId
	 * @return
	 */
	public GoodsDetailInfo getGoodsDetailByGoodsId(Integer goodsId) {
		GoodsDetailInfo goodsDetailInfo = new GoodsDetailInfo();
		try {
			goodsDetailInfo.setGoods(goodsMapper.selectByPrimaryKey(goodsId));
			goodsDetailInfo.setGoodsPictureList(goodsPictureMapper.getPictureListByGoodsId(goodsId));
			goodsDetailInfo.setGoodsFileList(goodsFileMapper.getFileListByGoodsId(goodsId));
		} catch (Exception e) {
			e.printStackTrace();
			return goodsDetailInfo;
		}
		return goodsDetailInfo;
	}

}
