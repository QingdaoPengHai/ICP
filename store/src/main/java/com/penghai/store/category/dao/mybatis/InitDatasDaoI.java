package com.penghai.store.category.dao.mybatis;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.penghai.store.category.model.Category;

@Repository
public interface InitDatasDaoI {

	int getNum();
	
	void saveCategoryList(@Param ("category") Category category) throws Exception;
}
