package com.penghai.store.user.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.penghai.store.user.dao.IUserDao;
import com.penghai.store.user.dao.mybatis.UserMapper;
import com.penghai.store.user.model.User;
import com.penghai.store.util.common.CommonData.CM_INFO_DATA;

@Repository
public class IUserDaoImpl implements IUserDao, CM_INFO_DATA{

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 用户相关数据访问层封装
	 * @author 徐超、秦超
	 * @Date 2017年4月24日 下午6:45:11
	 */
	@Override
	public Map<String, Object> regist(User user){
		Map<String, Object> map = new HashMap<String,Object>();
		int userIsExist = userMapper.checkAddUnique(user);
		//校验邮箱是否存在
		if(userIsExist == 1){
			map.put("state", 0);
			map.put("message", REGIST_EMAIL_EXIST_INFO);
			return map;
		}
		//注册用户
		Date registrationTime = new Date();
		user.setRegistrationTime(registrationTime);
		user.setStatus(new Byte("1"));
		try {
			userMapper.insertSelective(user);
			map.put("state", 1);
			map.put("message", REGIST_SUCCESS_INFO);
			return map;
		} catch (Exception e) {
			map.put("state", 0);
			map.put("message", REGIST_ERROR_INFO);
			return map;
		}
	}
	
	/**
	 * 用户登录
	 * 
	 * @author 高源
	 * @Date 2017年4月24日 下午4:39:39
	 * @param user(email,
	 *            password)
	 * @return null:用户不存在或密码错误；User对象：用户验证通过
	 * @see com.penghai.store.user.dao.IUserDao#login(com.penghai.store.user.model.User)
	 */
	@Override
	public User login(User user) {
		int loginAuth = -1;
		// 1.检查是否有该用户
		int num = userMapper.checkAddUnique(user);
		User user2 = null;
		// 2.有用户则验证密码
		if (num == 1) {
			loginAuth = userMapper.checkAuth(user);
		}
		// 3.验证密码通过则查出该用户信息返回
		if (loginAuth == 1) {
			user2 = userMapper.selectByCondition(user).get(0);
		}
		return user2;
	}
	/**
	 * @author 刘晓强
	 * 改密码
	 */
	@Override
	public int changePassword(User user){
		return userMapper.changePassword(user);
	}
}
