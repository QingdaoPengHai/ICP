package com.penghai.linker.dataAcquisition.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.IConfigurationManagementBusiness;
import com.penghai.linker.dataAcquisition.business.IDataAcquisitionBusiness;
import com.penghai.linker.dataAcquisition.business.IDataAcquisitionJobFactory;
import com.penghai.linker.dataAcquisition.model.DataAcquisitionControlModel;
import com.penghai.linker.dataAcquisition.model.TargetDatabaseRoot;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_INFO_DATA;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;
import com.penghai.linker.util.EhcacheUtil;
import com.penghai.linker.util.SpringUtil;

/**
 * 数据采集控制器类
 * @author 徐超
 * @Date 2017年5月16日 下午5:18:46
 */
@Controller
@RequestMapping("/dataAcquisition")
public class DataAcquisitionController{
	
	Logger log = Logger.getLogger(DataAcquisitionController.class);
	@Autowired
	private IDataAcquisitionJobFactory dataAcquisitionJobFactoryI;
	
	@Autowired
	private DataAcquisitionControlModel dataAcquisitionControlModel;

	@Autowired
	private IDataAcquisitionBusiness iDataAcquisitionBusiness;
	
	@Autowired
	private IConfigurationManagementBusiness iConfigurationManagementBusiness;
	/**
	 * 启动全部定时任务
	 * @author 徐超
	 * @Date 2017年5月22日 上午10:06:18
	 */
	@RequestMapping(value = "/start",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject start(){
		JSONObject resultJson = new JSONObject();
		try {
			//1.判断当前的注册状态
			JSONObject statusJson = iConfigurationManagementBusiness.getLinkerConfigStatus();
			String registerStatus = statusJson.getString("linkerConfigStatus");
			//2.根据状态选择执行的服务
			if("0".equals(registerStatus)){
				//重置并恢复
				resultJson = iDataAcquisitionBusiness.linkerRegist();
				//修改linker注册状态
				if(CM_MESSAGE_INFO.RESULT_SUCCESS_CODE.equals(resultJson.getString(CM_MESSAGE_INFO.RETURN_RESULT_KEY))){
					iConfigurationManagementBusiness.updateLinkerConfigStatus("1");
				}
				dataAcquisitionControlModel.resumeAllTimer();
			}else if("1".equals(registerStatus)){
				//仅仅恢复
				dataAcquisitionControlModel.resumeAllTimer();
				resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
				resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.START_TIMER_SUCCESS_RESUME);
			}else{
				//状态为其他值,报错
				resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
				resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.SYSTEM_ERROR);
			}
			EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
			long currentTime = System.currentTimeMillis()/1000;
			ehcacheUtil.put("startTime", currentTime);
			return resultJson;
		} catch (Exception e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY, CM_INFO_DATA.SYSTEM_ERROR);
			return resultJson;
		}
	}
	
	/**
	 * 暂停全部定时任务
	 * @author 徐超
	 * @Date 2017年5月22日 上午10:06:34
	 */
	@RequestMapping(value = "/pause" ,method = RequestMethod.POST)
	@ResponseBody
	public JSONObject pauseAllTask(){
		JSONObject resultJson = new JSONObject();
		try {
			Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
			scheduler.pauseAll();
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY,CM_INFO_DATA.PAUSE_TIMER_SUCCESS);
		} catch (SchedulerException e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			resultJson.put(CM_MESSAGE_INFO.RETURN_RESULT_KEY, CM_MESSAGE_INFO.RESULT_ERROR_CODE);
			resultJson.put(CM_MESSAGE_INFO.RETURN_MESSAGE_KEY,CM_INFO_DATA.PAUSE_TIMER_ERROR);
		}
		return resultJson;
	}
	
	
	/**
	 * 重置参数
	 * @author 徐超
	 * @Date 2017年5月23日 上午10:26:12
	 * @return
	 */
	@RequestMapping(value = "/resetParameter" ,method = RequestMethod.POST)
	@ResponseBody
	public JSONObject resetParameter(){
		JSONObject resultJson = new JSONObject();
		
		resultJson = iDataAcquisitionBusiness.resetParameter();
		
		return resultJson;
	}
	
	/**
	 * 获取定时器状态
	 * @author 徐超
	 * @Date 2017年5月23日 下午1:03:04
	 * @return
	 */
	@RequestMapping(value = "/getTimerStatus",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getTimerStatus(){
		JSONObject resultJson = new JSONObject();
		
		resultJson = iDataAcquisitionBusiness.getTimerStatus();
		
		return resultJson;
	}
	
}
