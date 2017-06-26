package com.penghai.store.good.dao.mybatis;

import java.util.List;

import com.penghai.store.good.model.GoodsFile;

public interface GoodsFileMapper {
    int deleteByPrimaryKey(Integer id);
    
    int insert(GoodsFile record);

    int insertSelective(GoodsFile record);

    GoodsFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GoodsFile record);

    int updateByPrimaryKey(GoodsFile record);
    
    /**
     * 根据商品ID获取文件列表
     * @param goodsId
     * @return
     */
    List<GoodsFile> getFileListByGoodsId(Integer goodsId);
    
    /**
     * 插入文件列表
     * @param record
     * @return
     */
    int insertFileList(List<GoodsFile> record);
    /**
     * 删除商品文件列表
     * @param goodsId
     * @return
     */
    int deleteFileList(String goodsId);
}