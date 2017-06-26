package com.penghai.store.category.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.AdminCategoryManageBusiness;
import com.penghai.store.category.dao.mybatis.IndustryCategoryMapper;
import com.penghai.store.category.model.IndustryCategory;
import com.penghai.store.util.common.CommonData.CM_REDIS_KEY;
import com.penghai.store.util.common.JedisUtils;

import redis.clients.jedis.Jedis;

@Service
public class AdminCategoryManageBusinessImpl implements AdminCategoryManageBusiness {
	@Autowired
	private IndustryCategoryMapper industryCategoryMapper;
	@Autowired
	private JedisUtils jedisUtils;

	@Override
	public JSONObject addCategory(IndustryCategory industryCategory) {
		JSONObject jsonObject = new JSONObject();
		/*IndustryCategory industryCategory = new IndustryCategory();
		industryCategory.setParentId(Integer.valueOf((String) map.get("parentId")));
		industryCategory.setLevel((Byte.valueOf((String) map.get("level"))));
		industryCategory.setName((String) map.get("name"));
		industryCategory.setCategoryCode((String) map.get("categoryCode"));
		industryCategory.setDescription((String) map.get("description"));
		industryCategory.setStatus((Byte.valueOf((String) map.get("status"))));*/
		int success = industryCategoryMapper.insertSelective(industryCategory);
		if (success > 0) {
			//删除redis中存储的商品分类信息
			delCategorysFromredis();
			jsonObject.put("success", true);
		} else {
			jsonObject.put("success", false);
		}

		return jsonObject;
	}

	/**
	 * 删除redis中存储的商品分类信息
	 * @author 秦超
	 * @time 2017年5月12日
	 */
	private void delCategorysFromredis() {
		Jedis jedis = jedisUtils.getJedis();
		String jedisCategorysKey = CM_REDIS_KEY.REDIS_CATEGORYS;
		jedis.del(jedisCategorysKey);
		jedisUtils.releaseJedis(jedis);
	}

}
