package com.penghai.store.good.dao.mybatis;

import java.util.List;

import com.penghai.store.good.model.RecommendGoods;

public interface RecommendGoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecommendGoods record);

    int insertSelective(RecommendGoods record);

    RecommendGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecommendGoods record);

    int updateByPrimaryKey(RecommendGoods record);
    /**
     * 获取推荐商品列表
     * @return
     */
    //TODO 排序规则、最大显示条数
    List<RecommendGoods> getRecommendGoodsList();
    
}