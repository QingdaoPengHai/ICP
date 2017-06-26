package com.penghai.css.management.dao.mybatis;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface DataCountDaoI {

	List<String> getDatabasesList();

	List<HashMap<String, Object>> getDatabaseTableCount(@Param("databaseName") String databaseName) throws Exception;

}
