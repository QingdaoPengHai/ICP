package com.penghai.store.util.common;

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

		public static final String ORRDER_ID_PRIFIX = "ICP";
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
		// 商品分类结构
		public static final String REDIS_CATEGORYS = "categorys";
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
		// 商品图片上传地址
		public static final String GOODS_PICTURE_UPLOAD_URL = CommonUtils.getProperties("commonDataConfig.properties",
				"goodsPictureUploadURL");
		/**
		 * 商品图片查看地址
		 */
		public static final String GOODS_PICTURE_READ_URL = CommonUtils.getProperties("commonDataConfig.properties", "goodsPictureReadURL");
		// 商品文件上传地址
		public static final String GOODS_FILE_UPLOAD_URL = CommonUtils.getProperties("commonDataConfig.properties",
				"goodsFileUploadURL");
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
}
