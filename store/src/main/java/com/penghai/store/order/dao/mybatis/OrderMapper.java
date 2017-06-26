package com.penghai.store.order.dao.mybatis;

import java.util.List;

import com.penghai.store.order.model.Order;

/**
 * 订单主表数据访问接口
 * @author 高源
 * @Date 2017年4月22日 下午1:40:02
 */
public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    /**
     * 新增订单
     * @author 高源
     * @Date 2017年4月22日 下午1:40:36
     * @see 
     * @param record
     * @return
     */
    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    /**
     * 修改订单
     * @author 高源
     * @Date 2017年4月22日 下午1:40:44
     * @see 
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    /**
     * 通过条件查询订单
     * @author 高源
     * @Date 2017年4月22日 下午1:40:32
     * @see 
     * @param order
     * @return
     */
    List<Order> selectByCondition(Order order);
    
    /**
     * 通过条件查询订单总数
     * @author 高源
     * @Date 2017年4月22日 下午1:41:28
     * @see 
     * @param order
     * @return
     */
    int selectCountByCondition(Order order);

	List<Order> getOrderListByEmail(String email);
}