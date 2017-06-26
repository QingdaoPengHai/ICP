package com.penghai.css.storage.business;
/**
 * 存储数据模块定时器接口
 * @author 徐超
 * @Date 2017年5月26日 上午12:11:23
 */
public interface IStorageScheduleBusiness {

	/**
	 * 使用Quartz定时器实现的读取redis并存储数据实现方法
	 * @author 徐超
	 * @Date 2017年5月25日 下午11:21:28
	 */
	void quartzSaveDatasToDatabase();
}
