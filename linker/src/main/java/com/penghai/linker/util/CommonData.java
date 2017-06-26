package com.penghai.linker.util;


/**
 * 公有文本数据,以接口形式存在
 * @author 徐超
 * @Date 2017年5月15日 下午3:31:55
 */
public class CommonData {

	/**
	 * linker接口返回码
	 * author： LiuLihua
	 * Date：2017-05-09
	 */
	public interface CM_LINKER_RETURN_CODE {
		
		public static final String LINKER_RETURN_OK = "0"; //OK
		public static final String LINKER_RETURN_NOK = "1"; //失败
		public static final String LINKER_INVALID_LINKERID = "2"; //无效linkerId
		
		//linker注册返回码
		public static final String LINKER_REGISTER_REGISTERED = "10"; //linker已注册
		public static final String LINKER_REGISTER_XML_FAILED = "11"; //获取targetDatabase数据失败
		
		//linker上报错误信息返回码
		public static final String LINKER_REPORT_INVALID_DATA = "20"; //上报数据无效
		
	}
	
	/**
	 * 交互信息公用文本
	 * 
	 * @author 徐超
	 * @Date 2017年4月24日 下午18:20:39
	 */
	public interface CM_INFO_DATA {
		// 系统错误
		public static final String SYSTEM_ERROR = "系统错误";
		// Linker注册失败
		public static final String LINKER_REGISTER_FAILED = "Linker注册失败";
		// xml文本内容为空
		public static final String XML_TEXT_NULL = "xml文本内容为空";
		// 启动定时器成功信息--恢复
		public static final String START_TIMER_SUCCESS_RESUME = "恢复任务成功";
		// 启动定时器成功信息--重启并恢复
		public static final String START_TIMER_SUCCESS_RESTART_RESUME = "重启并恢复任务成功";
		// 创建定时器报错
		public static final String CREATE_TIMER_ERROR = "创建任务时抛出异常";
		// 暂停定时任务成功
		public static final String PAUSE_TIMER_SUCCESS = "停止任务成功";
		// 暂停定时任务失败
		public static final String PAUSE_TIMER_ERROR = "停止任务失败";
		// 重置参数成功
		public static final String RESET_PARAMETER_SUCCESS = "重置参数成功";
		// 重置参数失败
		public static final String RESET_PARAMETER_ERROR = "重置参数失败";
	}
	
	/**
	 * 与页面交互信息文本
	 * @author 秦超
	 * @time 2017年5月17日
	 */
	public interface CM_MESSAGE_INFO{
		//获取信息成功
		public static final String RESULT_SUCCESS_CODE = "1";
		//获取信息失败
		public static final String RESULT_ERROR_CODE = "0";
		//返回结果KEY
		public static final String RETURN_RESULT_KEY = "result";
		//返回信息KEY
		public static final String RETURN_MESSAGE_KEY = "message";
		
	}
	
	/**
	 * 配置管理文件存放地址
	 * @author 秦超
	 * @time 2017年5月18日
	 */
	public interface CM_CONFIGURATION_PATH{
		//配置文件路径
		public static final String PROPERTIES_FILEPATH = "configs/configuration.properties";
	}
	
	/**
	 * 读取配置文件属性
	 * 
	 * @author 徐超
	 * @Date 2017年5月18日10:15:12
	 */
	public interface CM_CONFIG_PROPERTIES {
		// 定时器GROUP名称
		public static final String TIMER_GROUP_NAME = CommonUtils.getProperties("commonDataConfig.properties",
				"timerGroupName");
		//监控文件日志存放路径
		public static final String LOG_FILE_PATH = CommonUtils.getProperties("commonDataConfig.properties",
				"logFilePath");
		//监控文件日志存放路径
		public static final String LOG_REFRESH_TIME = CommonUtils.getProperties("commonDataConfig.properties",
				"logRefreshTime");
		//监控文件日志存放路径
		public static final String COUNT_REFRESH_TIME = CommonUtils.getProperties("commonDataConfig.properties",
				"countRefreshTime");
		//监控文件日志存放路径
		public static final String DATA_REFRESH_TIME = CommonUtils.getProperties("commonDataConfig.properties",
				"dataRefreshTime");
		//数据库Driver
		public static final String DATABASE_DRIVER = CommonUtils.getProperties("commonDataConfig.properties",
				"driver");
		//一次读取数据库的条数
		public static final String READ_DATA_COUNT = CommonUtils.getProperties("commonDataConfig.properties",
				"readDataCount");
		//MQ交换机名称
		public static final String EXCHANG_NAME = CommonUtils.getProperties("commonDataConfig.properties",
				"exchangename");
		//LinkerId
		public static final String LINKER_ID = CommonUtils.getProperties("commonDataConfig.properties",
				"linkerId");
		//日志刷新条数
		public static final String LOG_REFRESH_NUMBER = CommonUtils.getProperties("commonDataConfig.properties",
				"logRefreshNumber");
		//scada的mysql连接状态缓存的key--0异常1正常
		public static final String SCADA_MYSQL_STATUS_CACHE_KEY = CommonUtils.getProperties("commonDataConfig.properties",
				"scadaMysqlStatusCacheKey");
		//MQ的连接状态缓存的key--0异常1正常
		public static final String CSS_MQ_STATUS_CACHE_KEY = CommonUtils.getProperties("commonDataConfig.properties",
				"cssMQStatusCacheKey");
	}
	
	/**
	 * ehcache连接信息
	 * @author 秦超
	 * @time 2017年5月22日
	 */
	public interface CM_EHCACHE_CONFIG{
		//ehcache配置xml路径
		public static final String EHCACHE_XML_PATH = "configs/ehcache.xml";
		//ehcache存储名
		public static final String LINKER_EHCACHE_NAME = "linkerCache";		
	}
}
