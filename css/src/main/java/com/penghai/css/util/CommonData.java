package com.penghai.css.util;

public class CommonData {

	/**
	 * 商城订单公有数据
	 * 
	 * @author lihao
	 */
	public interface CM_ORDER_DATA {
		// 用户删除订单状态
		public static final String ORDER_DELETE = "1";
		public static final String ORRDER_NODELETE = "0";
	}

	/**
	 * HttpSession数据
	 * 
	 * @author lihao
	 * @date 2017年4月25日
	 */
	// Jedis操作所用的公有数据
	public interface CM_JEDIS_DATA {
		// Session过期时间,1200秒
		public static final int SESSION_TIMEOUT = 1200;
		// session长度,32位
		public static final int SESSION_LEN = 32;
		// 设置cookie的过期时间,14天
		public static final int COOKIE_TIMEOUT = 14;
	}

	/**
	 * 交互信息公用文本
	 * 
	 * @author 徐超
	 * @Date 2017年4月24日 下午18:20:39
	 */
	public interface CM_INFO_DATA {
		// 注册完成
		public static final String REGIST_SUCCESS_INFO = "注册完成";
		// 注册出错
		public static final String REGIST_ERROR_INFO = "注册出错";
		// 注册邮箱已存在
		public static final String REGIST_EMAIL_EXIST_INFO = "注册邮箱已存在";
		// 编辑成功
		public static final String EDIT_SUCCESS = "编辑成功";
		// 系统错误
		public static final String SYSTEM_ERROR = "系统错误";
	}

	/**
	 * redis_key
	 * 
	 * @author 徐超
	 * @Date 2017年4月26日 下午4:03:31
	 */
	public interface CM_REDIS_KEY {
		// 用户购物车
		public static final String REDIS_SHOPPING_CART = "shoppingCart";
	}

	/**
	 * 登录信息文本
	 * 
	 * @author 李浩
	 * @date 2017年4月25日
	 */
	public interface CM_LOGIN_DATA {
		// 登录的返回消息
		public static final String LOGIN_SUCCESS_INFO = "登录成功";
		public static final String LOGIN_ERROR_INFO = "登录失败，用户名或密码错误";
		// 登陆的返回代码
		public static final String LOGIN_SUCCESS_CODE = "1";
		public static final String LOGIN_ERROR_CODE = "0";

	}

	/**
	 * 查询商品返回信息数据
	 * 
	 * @author 李浩
	 * @date 2017年4月25日
	 */
	public interface CM_QUERY_INFO {
		// 查询成功
		public static final String QUERY_SUCCESS_INFO = "查询成功";
		// 查询失败
		public static final String QUERY_ERROR_INFO = "查询失败";
		// 查询无记录
		public static final String QUERY_NODATA_INFO = "无记录";
	}

	/**
	 * 读取配置文件属性
	 * 
	 * @author 徐超
	 * @Date 2017年4月27日 上午11:15:59
	 */
	public interface CM_CONFIG_PROPERTIES {
		// 商店服务根地址
		public static final String STORE_ROOT_URL = CommonUtils.getProperties("commonDataConfig.properties",
				"storeRootUrl");
		// 商品图片上传地址
		public static final String GOODS_PICTURE_UPLOAD_URL = CommonUtils.getProperties("commonDataConfig.properties",
				"goodsPictureUploadURL");
		// 商品文件上传地址
		public static final String GOODS_FILE_UPLOAD_URL = CommonUtils.getProperties("commonDataConfig.properties",
				"goodsFileUploadURL");
		// 商品xml文件存储地址
		public static final String XML_FILE_PATH = CommonUtils.getProperties("commonDataConfig.properties",
				"xmlFilePath");
		// LinkerIds记录文件存储地址
		public static final String LINKERID_FILE_PATH = CommonUtils.getProperties("commonDataConfig.properties",
				"linkerIdFilePath");
		// 商店系统发布url
		public static final String STORE_SERVER_URL = CommonUtils.getProperties("commonDataConfig.properties",
				"storeServerUrl");
		// rabbitMQ服务IP
		public static final String RABBIT_HOST = CommonUtils.getProperties("rabbitMQ.properties",
				"host");
		// rabbitMQ服务端口号
		public static final String RABBIT_PORT = CommonUtils.getProperties("rabbitMQ.properties",
				"port");
		// rabbitMQ用户名
		public static final String RABBIT_USERNAME = CommonUtils.getProperties("rabbitMQ.properties",
				"username");
		// rabbitMQ密码
		public static final String RABBIT_PASSWORD = CommonUtils.getProperties("rabbitMQ.properties",
				"password");
		// rabbitMQ交换机名称
		public static final String EXCHANG_NAME = CommonUtils.getProperties("rabbitMQ.properties",
				"exchangename");
		// rabbitMQ队列名称
		public static final String QUEUE_NAME = CommonUtils.getProperties("rabbitMQ.properties",
				"queuename");
		//缓存到redis的最大条数
		public static final String MAX_CACHE_NUMBER = CommonUtils.getProperties("rabbitMQ.properties",
				"maxCacheNumber");
		//rabbitMQ数据存储到Redis中的key
		public static final String MQ_DATA_KEY_NAME = CommonUtils.getProperties("rabbitMQ.properties",
				"MQDataKeyName");
	}

	/**
	 * linkerId存入Redis所需信息
	 */
	public interface CM_LINKER_REDIS_PROPERTIES {		
		// 商品文件上传地址
		public static final String LINKER_IDS = CommonUtils.getProperties("linker.properties",
				"linkerIdList");
		// 商品文件上传地址
		public static final String LINKER_KEY_EXPIRATION = CommonUtils.getProperties("linker.properties",
				"linkerKeyExpiration");
		// 商品文件上传地址
		public static final String LINKER_KEY = CommonUtils.getProperties("linker.properties",
				"linkerKey");
		// 商品文件上传地址
		public static final String REDIS_HOME = CommonUtils.getProperties("redis.properties",
				"redis.host");
		// 商品文件上传地址
		public static final String REDIS_PORT = CommonUtils.getProperties("redis.properties",
				"redis.port");
	}
	
	/**
	 * 登出返回的信息数据
	 * 
	 * @author 李浩
	 */
	public interface CM_LOGOUT_DATA {
		// 成功登出
		public static final String LOGOUT_SUCCESS = "成功登出";
		// 用户错误,登出失败
		public static final String LOGOUT_ERROR_USER = "用户错误,登出失败";
		// 系统错误,登出失败
		public static final String LOGOUT_ERROR_SYSTEM = "系统错误,登出失败";
	}

	/**
	 * 管理员新增商品
	 */
	public interface CM_ADMIN_ADDGOOD {
		// 上架
		public static final boolean GOOD_SALE = true;
		public static final String GOOD_UPLOAD_SUCCESS = "商品上传成功";
		public static final String GOOD_UPLOAD_ERROR = "商品上传失败";
		// 下架
		public static final boolean GOOD_NOSALE = false;
		// 状态
		public static final Byte GOOD_STATUS_ZERO = 0;
		public static final Byte GOOD_STATUS_ONE = 1;
		public static final Byte GOOD_STATUS_TWO = 2;

		// 图片上传失败
		public static final String IMAGE_UPLOAD_ERROR = "图片上传失败";
		//

	}
	
	public interface CM_STORE_SERVER_FUNCTION{
		// 单条订单xml文件信息详情
		public static final String ORDER_XML_DETAIL = "/order/getUserOrderXmlDetail";
		// 修改订单xml文件信息下载状态
		public static final String UPDATE_ORDER_STATUS = "/order/updateOrderStatus";
		//订单列表
		public static final String ORDER_XML_LIST = "/order/getUserOrderXmlList";
		//订单列表BY userId
		public static final String ORDER_LIST_BY_USERID = "/order/getUserOrderListByUserId";
		//store用户登录验证
		public static final String LOGIN_CHECKIN = "/login/checkLoginforCSS";
	}

	/**
	 * linker 上报错误信息
	 * author： LiuLihua
	 * Date：2017-05-09
	 */
	public interface CM_LINKER_ERROR {
		//linker上报错误信息
		public static final String LINKER_NORMAL = "0";
		public static final String LINKER_ERROR_DATABASE = "1";
		public static final String LINKER_ERROR_OTHER = "1000";
	}

	/**
	 * linker 配置状态
	 * author： LiuLihua
	 * Date：2017-05-09
	 */
	public interface CM_LINKER_CONFIGURE {
		//linker配置状态
		public static final String LINKER_UNREGISTERED = "0"; //未注册
		public static final String LINKER_REGISTERED = "1";   //已注册
	}

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
	
}
