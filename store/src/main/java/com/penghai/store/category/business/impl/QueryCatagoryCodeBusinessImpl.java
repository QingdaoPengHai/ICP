package com.penghai.store.category.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghai.store.category.business.QueryCatagoryCodeBusiness;
import com.penghai.store.category.dao.mybatis.IndustryCategoryMapper;

@Service
public class QueryCatagoryCodeBusinessImpl implements QueryCatagoryCodeBusiness {
	@Autowired
	private IndustryCategoryMapper industryCategoryMapper;

	@Override
	public String queryCatagoryCode(Integer id) {
		String code = industryCategoryMapper.selectCatagoryCodeById(id);
		return code;
	}

}
