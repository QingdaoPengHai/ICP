package com.penghai.linker.dataAcquisition.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.Trigger.TriggerState;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IConfigurationManagementBusiness;
import com.penghai.linker.dataAcquisition.business.IDataAcquisitionBusiness;
import com.penghai.linker.dataAcquisition.business.IDataAcquisitionJobFactory;
import com.penghai.linker.dataAcquisition.model.DataAcquisitionControlModel;
import com.penghai.linker.dataAcquisition.model.TargetDatabaseRoot;
import com.penghai.linker.util.SpringUtil;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_INFO_DATA;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;
import com.penghai.linker.util.EhcacheUtil;
/**
 * 数据采集服务接口类--与页面交互
 * @author 徐超
 * @Date 2017年5月22日 下午5:50:38
 */
@Service
public class DataAcquisitionBusinessImpl implements IDataAcquisitionBusiness{
	
	@Autowired
	private IDataAcquisitionJobFactory dataAcquisitionJobFactoryI;
	
	@Autowired
	private DataAcquisitionControlModel dataAcquisitionControlModel;
	
	@Autowired
	private IConfigurationManagementBusiness iConfigurationManagementBusiness;

	/**
	 * linker注册服务
	 * @author 徐超
	 * @Date 2017年5月23日 上午9:16:17
	 * @return
	 */
	@Override
	public JSONObject linkerRegist(){
		
		JSONObject resultJson = new JSONObject();
		
		//获取配置的linkerId
		String linkerId = CM_CONFIG_PROPERTIES.LINKER_ID;
		//构建结果集
		//向存储系统注册Linker,并获取xml文本
		JSONObject targetDatabaseAndRefDatabaseInfoJson = dataAcquisitionJobFactoryI.linkerRegister(linkerId);
		String databaseXml = targetDatabaseAndRefDatabaseInfoJson.getString("databaseXml");
		String refDatabaseInfoJson = targetDatabaseAndRefDatabaseInfoJson.getString("refDatabaseInfoJson");
		if(null==databaseXml || "".equals(databaseXml) || null==refDatabaseInfoJson || "".equals(refDatabaseInfoJson)){
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.SYSTEM_ERROR);
			return resultJson;
		}
		//解析xml至targetDatabase对象
		TargetDatabaseRoot targetDatabase = dataAcquisitionJobFactoryI.transferXML2Model(databaseXml);
		//将targetDatabase对象转换为查询sql语句list
		List<HashMap<String ,Object>> list = targetDatabase.createSelectSqlMap(refDatabaseInfoJson);
		try {
			//根据列表创建多个定时任务
			dataAcquisitionControlModel.createTimers(list);
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.START_TIMER_SUCCESS_RESUME);
			return resultJson;
		} catch (SchedulerException e) {
			e.printStackTrace();
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.SYSTEM_ERROR);
			return resultJson;
		}
	}
	
	
	/**
	 * 重置参数
	 * @author 徐超
	 * @Date 2017年5月23日 上午10:27:01
	 * @return
	 */
	public JSONObject resetParameter(){
		JSONObject resultJson = new JSONObject();
		try {
			//1.删除定时器
			dataAcquisitionControlModel.deleteAllTimer();
			//2.修改配置文件状态
			iConfigurationManagementBusiness.updateLinkerConfigStatus("0");
			//3.清除缓存
			EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
			ehcacheUtil.removeAll();
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.RESET_PARAMETER_SUCCESS);
		} catch (SchedulerException e) {
			e.printStackTrace();
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.RESET_PARAMETER_ERROR);
		}
		return resultJson;
	}
	
	
	/**
	 * 判断定时器状态
	 * @author 徐超
	 * @Date 2017年5月23日 下午1:03:40
	 * @return
	 */
	public JSONObject getTimerStatus(){
		JSONObject resultJson = new JSONObject();
		//获取定时任务控制器实例
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
		try {
			//获取系统中的trigger对象
			GroupMatcher<TriggerKey> matcher = GroupMatcher.anyTriggerGroup(); 
			Set<TriggerKey> TriggerKey = scheduler.getTriggerKeys(matcher);
			TriggerKey trigger = null;
			for(TriggerKey triggerKey:TriggerKey){
				trigger = triggerKey;
				break;
			}
			//获取trigger状态
			TriggerState status = scheduler.getTriggerState(trigger);
			if(TriggerState.NORMAL.equals(status) || TriggerState.BLOCKED.equals(status)){
				//正常
				resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
				resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, status);
			}else{
				//非正常
				resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
				resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, status);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
		return resultJson;
	}
}
