package com.penghai.store.user.business.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghai.store.user.business.RegisterBusiness;
import com.penghai.store.user.dao.IUserDao;
import com.penghai.store.user.dao.mybatis.UserMapper;
import com.penghai.store.user.model.User;

@Service
public class RegisterBusinessImpl implements RegisterBusiness{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private IUserDao userDao;
	@Override
	public Map<String, Object> userRegister(Map<String, Object> map, User user) {

		return userDao.regist(user);
		
	}
		
		

}
