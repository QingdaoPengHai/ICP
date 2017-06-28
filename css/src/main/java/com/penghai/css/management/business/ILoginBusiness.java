package com.penghai.css.management.business;
/**
 * 登陆业务接口类
 * @author 徐超
 * @Date 2017年6月27日 下午4:55:42
 */
public interface ILoginBusiness {

	/**
	 * 移动端登录方法
	 * @author 徐超
	 * @Date 2017年6月27日 下午5:36:09
	 * @param email 邮箱
	 * @param password 密码
	 * @return
	 */
	String mobileLogin(String email,String password);
}
