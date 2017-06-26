package com.penghai.store.order.dao.mybatis;

import java.util.List;
import java.util.Map;

import com.penghai.store.order.model.OrderInfo;
import com.penghai.store.order.model.OrderInfoKey;

/**
 * 订单详情数据访问接口
 * 
 * @author 高源
 * @Date 2017年4月22日 下午1:41:57
 */
public interface OrderInfoMapper {
	int deleteByPrimaryKey(OrderInfoKey key);

	int insert(OrderInfo record);

	int insertSelective(OrderInfo record);

	OrderInfo selectByPrimaryKey(OrderInfoKey key);

	int updateByPrimaryKeySelective(OrderInfo record);

	int updateByPrimaryKey(OrderInfo record);

	/**
	 * 通过订单id查询该订单详情
	 * 
	 * @author 高源
	 * @Date 2017年4月22日 下午1:42:46
	 * @see
	 * @param record
	 * @return
	 */
	List<Map<String, Object>> selectByOrderId(OrderInfo record);

	/**
	 * 通过订单id删除相应订单详情
	 * 
	 * @author 高源
	 * @Date 2017年4月22日 下午1:42:21
	 * @see
	 * @param record
	 * @return
	 */
	int deleteByOrderId(OrderInfo record);

	/**
	 * 批量插入订单详情
	 * 
	 * @author 高源
	 * @Date 2017年4月22日 下午1:42:11
	 * @see
	 * @param record
	 * @return
	 */
	int insertList(List<OrderInfo> record);
}