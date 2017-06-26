package com.penghai.store.good.dao.mybatis;

import java.util.List;

import com.penghai.store.good.model.GoodsPicture;
/**
 * 商品图片数据库接口
 * @author 徐超
 * @Date 2017年4月25日 上午11:15:37
 */
public interface GoodsPictureMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsPicture record);

    int insertSelective(GoodsPicture record);

    GoodsPicture selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsPicture record);

    int updateByPrimaryKey(GoodsPicture record);
    
    /**
     * 根据商品ID获取图片列表
     * @param goodsId
     * @return
     */
    List<GoodsPicture> getPictureListByGoodsId(Integer goodsId);
    /**
     * 插入图片列表
     * @param record
     * @return
     */
    int insertPictureList(List<GoodsPicture> record);
    /**
     * 删除图片列表
     * @param goodsId
     * @return
     */
    int deletePictureList(String goodsId);
}