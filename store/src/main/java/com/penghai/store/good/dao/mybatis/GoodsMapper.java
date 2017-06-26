package com.penghai.store.good.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.penghai.store.good.model.Goods;

public interface GoodsMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Goods record);

	/**
	 * 新增商品
	 * 
	 * @param record
	 * @return
	 */
	int insertSelective(Goods record);

	/**
	 * 根据商品ID获取商品详情
	 * 
	 * @param goodsId
	 * @return
	 */
	Goods selectByPrimaryKey(Integer id);

	/**
	 * 编辑商品
	 * 
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(Goods record);

	/**
	 * 根据商品的类别ID获取商品列表
	 * 
	 * @param goodCate
	 * @return List
	 */
	List<Goods> selectByGoodCateCode(String catagoryCode);

	int updateByPrimaryKey(Goods record);

	/**
	 * 获取全部商品
	 * 
	 * @return
	 */
	/**
	 * TODO 1.排序规则 updateTime DESC 2.是否分页 3.状态码过滤 0--正常 1--已删除 2--待定义
	 * 4.具体返回字段--现全表
	 */
	List<Goods> getGoodsAll(Goods record);

	/**
	 * 根据条件获取商品列表
	 * 
	 * @param Goods.goodCate
	 *            分类ID
	 * @param Goods.goodModel
	 *            商品类型
	 * @param Goods.goodCateName
	 *            分类名称
	 * @param Goods.pageIndex
	 *            起始页
	 * @param Goods.pageSize
	 *            每页条数
	 * @return
	 */
	List<Goods> getGoodsListByCondition(Goods record);

	/**
	 * 检查商品名称存在性
	 * 
	 * @param goodsName
	 * @return
	 */
	int checkGoodsNameExist(String goodsName);

	/**
	 * 商品上下架
	 * 
	 * @param Map.goodsIsList
	 *            List<int>
	 * @param Map.status
	 *            0--下架 1--上架
	 * @return
	 */
	int changeGoodsForSaleStatus(Map<String, Object> record);

	/**
	 * 查询所有商品
	 * 
	 * @author 李浩
	 * @return List<Map>
	 */
	List<Map<String, Object>> selectAllList();

	/**
	 * 根据ID查询图片名称
	 * 
	 * @author 李浩
	 * @return
	 */
	String selectPictureNameById(int id);

	/**
	 * 根据条件获取商品列表
	 * 
	 * @param Goods.goodCate
	 *            分类ID
	 * @param Goods.goodModel
	 *            商品类型
	 * @param Goods.goodCateName
	 *            分类名称
	 * @param Goods.pageIndex
	 *            起始页
	 * @param Goods.pageSize
	 *            每页条数
	 * @return
	 */
	List<Goods> getGoodsByCondition(Map<String, Object> map);
}