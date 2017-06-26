package com.penghai.store.order.dao.mybatis;

import java.util.List;

import com.penghai.store.order.model.OrderGoodsFile;

public interface OrderGoodsFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderGoodsFile record);

    int insertSelective(OrderGoodsFile record);

    OrderGoodsFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderGoodsFile record);
    /**
     * 根据主键id修改文件状态
     * @param id 主键id
     * @param status 状态
     * @return
     */
    int updateOrderGoodsFileStatusByPrimaryKey(OrderGoodsFile record);

    int updateByPrimaryKey(OrderGoodsFile record);
    /**
     * 根据订单ID和商品ID有选择性的更新
     * @author 徐超
     * @Date 2017年4月28日 下午1:29:05
     * @param record
     * @return
     */
    int updateByOrderIdAndGoodsIdSelective(OrderGoodsFile record);
    /**
     * 获取用户所有订单对应的XML_List
     * @author 徐超
     * @Date 2017年4月28日 下午4:58:58
     * @param email
     * @return
     */
    List<OrderGoodsFile> getUserOrderXmlList(String email);
    /**
     * 获取一条订单对应的XML_List
     * @param orderId
     * @author Q.chao
     * @return
     */
    List<OrderGoodsFile> getOrderXmlList(Integer orderId);
    /**
     * 获取用户指定xml详细信息
     * @author 徐超
     * @Date 2017年4月28日 下午5:33:20
     * @param xmlId
     * @return
     */
    OrderGoodsFile getUserOrderXmlDetail(String xmlId);
    /**
     * 根据goodsId和orderId获取用户指定xml详细信息
     * @author 徐超
     * @Date 2017年4月29日 下午9:54:28
     * @param orderId
     * @param goodsId
     * @return
     */
    OrderGoodsFile getUserOrderXmlDetailByOrderIdAndGoodsId(OrderGoodsFile record);

}