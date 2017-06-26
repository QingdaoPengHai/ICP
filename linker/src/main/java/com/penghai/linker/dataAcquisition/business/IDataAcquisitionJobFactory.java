package com.penghai.linker.dataAcquisition.business;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.dataAcquisition.model.TargetDatabaseRoot;

/**
 * 定时任务工厂类接口
 * @author 徐超
 * @Date 2017年5月16日 下午4:20:01
 */
public interface IDataAcquisitionJobFactory {

	/**
	 * linker注册服务业务逻辑
	 * @author 徐超
	 * @Date 2017年5月17日 上午9:42:38
	 * @param linkerId
	 * @return 返回xml文本
	 * @throws Exception
	 */
	JSONObject linkerRegister(String linkerId);
	
	/**
	 * 将xml文本转为TargetDatabase对象
	 * @author 徐超
	 * @Date 2017年5月17日 下午2:43:41
	 * @param xmlContent
	 * @return
	 */
	TargetDatabaseRoot transferXML2Model(String xmlContent);
	
}
