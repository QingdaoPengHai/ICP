package com.penghai.store.admin.dao.mybatis;

import java.util.Map;

public interface AdminMapper {
	/*
	 * 验证用户名
	 */
	String checkAddUnique(Map<String, Object> map);

	String checkAuth(Map<String, Object> map);

	Map<String, Object> selectByCondition(Map<String, Object> map);
}
