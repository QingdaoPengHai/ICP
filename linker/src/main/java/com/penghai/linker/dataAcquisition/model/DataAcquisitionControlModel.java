package com.penghai.linker.dataAcquisition.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Component;

import com.penghai.linker.dataAcquisition.business.impl.DataAcquisitionJobFactoryImpl;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_INFO_DATA;
import com.penghai.linker.util.EhcacheUtil;
import com.penghai.linker.util.SpringUtil;

/**
 * 数据采集实体类管理器
 * @author 徐超
 * @Date 2017年5月18日 上午9:19:25
 */
@Component
public class DataAcquisitionControlModel {
	
	Logger log = Logger.getLogger(DataAcquisitionControlModel.class);
	/**
	 * 启动所有定时任务
	 * @author 徐超
	 * @Date 2017年5月22日 下午2:28:06
	 */
	public void startAllTimer(){
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 暂停所有定时任务
	 * @author 徐超
	 * @throws SchedulerException 
	 * @Date 2017年5月22日 下午2:28:14
	 */
	public void pauseAllTimer() throws SchedulerException{
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
			scheduler.pauseAll();
	}
	
	/**
	 * 恢复所有定时任务
	 * @author 徐超
	 * @Date 2017年5月22日 下午2:28:25
	 */
	public void resumeAllTimer(){
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
		try {
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			log.error(CM_INFO_DATA.SYSTEM_ERROR,e);
		}
	}
	/**
	 * 删除所有定时任务
	 * @author 徐超
	 * @throws SchedulerException 
	 * @Date 2017年5月22日 下午2:29:35
	 */
	public void deleteAllTimer() throws SchedulerException{
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
		
		//获取所有已经定义的定时器
		GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
		Set<JobKey> jobKeySet = scheduler.getJobKeys(matcher);
		List<JobKey> jobKeyList = new ArrayList<JobKey>(jobKeySet);
		//删除所有定时器
		scheduler.deleteJobs(jobKeyList);
	}
	
	/**
	 * 根据target表列表创建定时任务
	 * @author 徐超
	 * @Date 2017年5月18日 上午9:30:59
	 * @param list
	 * @throws SchedulerException 
	 */
	public void createTimers(List<HashMap<String,Object>> list) throws SchedulerException{
		//初始化Ehcache对象
		EhcacheUtil ehcacheUtil = EhcacheUtil.getInstance();
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("schedulerFactoryBean");
		StringBuffer targetTableList = new StringBuffer();
		for(HashMap<String,Object> map:list){
			ScheduleJobModel scheduleJobModel = new ScheduleJobModel();
			String jobName = (String)map.get("targetTable");
			int queryInterval = (int)map.get("queryInterval");
			targetTableList.append(jobName).append(",");
			//保存定时器信息实体
			scheduleJobModel.setStorageTable((String)map.get("storageTable"));
			scheduleJobModel.setSelectSQL((String)map.get("selectSQL"));
			scheduleJobModel.setTargetTable((String)map.get("targetTable"));
			scheduleJobModel.setJobId(jobName);
			scheduleJobModel.setJobName(jobName);
			scheduleJobModel.setJobGroup(CM_CONFIG_PROPERTIES.TIMER_GROUP_NAME);
			scheduleJobModel.setJobStatus("1");
			scheduleJobModel.setPrimaryKey((String)map.get("primaryKey"));
			scheduleJobModel.setPrimaryKeyOrder((String)map.get("primaryKeyOrder"));
			scheduleJobModel.setRefTableName((String)map.get("refTableName"));
			scheduleJobModel.setRefColumns((String)map.get("refColumns"));
			scheduleJobModel.setRefPrimaryKey((String)map.get("refPrimaryKey"));
			//获取任务执行间隔
			long queryIntervalLong = queryInterval;
			//创建定时器任务
			JobDetail job = JobBuilder
							.newJob(DataAcquisitionJobFactoryImpl.class)
							.withIdentity(jobName, CM_CONFIG_PROPERTIES.TIMER_GROUP_NAME)
							.build();
			job.getJobDataMap().put("scheduleJob", scheduleJobModel);
			//创建定时器触发器
			Trigger trigger = TriggerBuilder
								.newTrigger().startAt(new Date(new Date().getTime()+1000L))
								.withIdentity(jobName, CM_CONFIG_PROPERTIES.TIMER_GROUP_NAME)
								.withSchedule(SimpleScheduleBuilder.simpleSchedule()
										.withIntervalInMilliseconds(queryIntervalLong)
										.repeatForever())
								.build();
			//将任务细节及定时器添加到定时任务
			scheduler.scheduleJob(job, trigger);
		}
		//先将任务暂停
		scheduler.pauseAll();
		targetTableList.deleteCharAt(targetTableList.length()-1);
		//将所有表的名称添加到缓存
		ehcacheUtil.put("targetTableList", targetTableList.toString());
	}
}
