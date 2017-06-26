package com.penghai.store.user.business;

import java.util.Map;

import com.penghai.store.user.model.User;

public interface RegisterBusiness {

	Map<String, Object> userRegister(Map<String, Object> map, User user);

}
