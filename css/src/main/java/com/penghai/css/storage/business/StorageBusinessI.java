package com.penghai.css.storage.business;

import java.util.List;
import java.util.Set;

/**
 * 存储模块业务类接口
 * @author 徐超
 * @Date 2017年5月10日 上午9:53:18
 */
public interface StorageBusinessI {

	/**
	 * 将Josn数据转为可执行的sql
	 * @author 徐超
	 * @Date 2017年5月9日 下午3:21:35
	 * @param json
	 * @return
	 */
	String transferJsonToSql(String json) throws Exception;
	
	/**
	 * 将Josn数据转为可执行的sql,for Mycat
	 * @author 徐超
	 * @Date 2017年6月8日22:30:50
	 * @param json
	 * @return
	 */
	String transferJsonToSqlForMycat(String json,long sequenceStart) throws Exception;
	
	/**
	 * 将批量的JosnSet数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	String transferBulkJsonToSql(Set<String> jsonSet) throws Exception;
	
	/**
	 * 将批量的JosnList数据转为可执行的sql,一个sql
	 * @author 徐超
	 * @Date 2017年5月10日 下午11:08:49
	 * @param jsonSet
	 * @return
	 * @throws Exception
	 */
	String transferBulkJsonListToSql(List<String> jsonList) throws Exception;
	/**
	 * 将批量的JosnList数据转为可执行的sql,一个sql,for Mycat
	 * @author 徐超
	 * @Date 2017年6月8日 下午10:28:32
	 * @param jsonList
	 * @return
	 * @throws Exception
	 */
	String transferBulkJsonListToSqlForMycat(List<String> jsonList,long currentSequence) throws Exception;
	/**
	 * 获取当次插入的数据数量
	 * @author 徐超
	 * @Date 2017年6月9日 上午8:34:59
	 * @return
	 */
	int getInsertNumber(List<String> jsonList);
	/**
	 * 获取并修改全局序列信息
	 * @author 徐超
	 * @Date 2017年6月9日 上午8:45:15
	 * @param insertNumber 当次写入的条数
	 * @return long 本次序列的起始值 -1表示有错误
	 */
	long getAndChangeCurrentsequenceInfo(int insertNumber);
}
