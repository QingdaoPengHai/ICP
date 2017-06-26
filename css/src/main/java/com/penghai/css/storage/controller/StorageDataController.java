package com.penghai.css.storage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.penghai.css.util.MongoUtil;
import com.penghai.css.util.SpringUtil;

/**
 * 
 * @author 徐超
 * @Date 2017年6月21日 下午4:38:00
 */
@Controller
@RequestMapping(value = "/storage")
public class StorageDataController {

	@Autowired
	private MongoUtil mongoUtil;
	
	/**
	 * 暂停定时任务
	 * @author 徐超
	 * @Date 2017年6月21日 下午4:56:04
	 */
	@RequestMapping(value = "/pauseJob")
	public void pauseJob(){
		
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("scheduler");
		
		try {
			//暂停定时任务
			scheduler.pauseAll();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 恢复定时任务
	 * @author 徐超
	 * @Date 2017年6月21日 下午4:56:08
	 */
	@RequestMapping(value = "/resumeJob")
	public void resumeJob(){
		
		Scheduler scheduler = (Scheduler)SpringUtil.getInstance().getBeanById("scheduler");
		
		try {
			//恢复定时任务
			scheduler.resumeAll();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}
}
