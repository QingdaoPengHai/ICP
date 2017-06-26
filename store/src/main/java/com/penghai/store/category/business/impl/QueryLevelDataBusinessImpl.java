package com.penghai.store.category.business.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.QueryLevelDataBusiness;
import com.penghai.store.category.dao.mybatis.IndustryCategoryMapper;
import com.penghai.store.category.model.IndustryCategory;
import com.penghai.store.util.common.CommonData.CM_REDIS_KEY;
import com.penghai.store.util.common.JedisUtils;

import redis.clients.jedis.Jedis;

@Service
public class QueryLevelDataBusinessImpl implements QueryLevelDataBusiness {
	@Autowired
	private IndustryCategoryMapper industryCategoryMapper;

	@Autowired
	private JedisUtils jedisUtils;
	/**
	 * 查询一级子目录
	 */
	@Override
	public List<IndustryCategory> queryFirstLevelData() {
		List<IndustryCategory> list = industryCategoryMapper.selectByLevel(1);
		return list;
	}

	/**
	 * 查询一级和二级子目录
	 */
	@Override
	public List<Map<String, Object>> queryTwoLevelData() {
		// 请求一级和二级子目录
		List<IndustryCategory> list1 = industryCategoryMapper.selectByLevel(1);
		List<IndustryCategory> list2 = industryCategoryMapper.selectByLevel(2);
		// 生成返回列表
		List<Map<String, Object>> returnList = new ArrayList<>();
		// 遍历数据，生成父子结构
		for (IndustryCategory category1 : list1) {
			Map<String, Object> resultMap = new HashMap<>();
			List<Map<String, Object>> arrayList = new ArrayList<>();
			for (IndustryCategory category2 : list2) {
				if (category2.getParentId().equals(category1.getId())) {
					Map<String, Object> maps = new HashMap<>();
					maps.put("childrenId", category2.getId());
					maps.put("childrenName", category2.getName());
					maps.put("categoryCode", category2.getCategoryCode());
					maps.put("description", category2.getDescription());
					arrayList.add(maps);
				}
			}
			resultMap.put("id", category1.getId());
			resultMap.put("parentName", category1.getName());
			resultMap.put("description", category1.getDescription());
			resultMap.put("categoryCode", category1.getCategoryCode());
			resultMap.put("children", arrayList);
			returnList.add(resultMap);
		}
		return returnList;
	}

	/**
	 * 获取四级分类结构信息
	 * @author 秦超
	 */
	@Override
	public JSONObject getFourLevelCategory(){
		//获取Redis信息
		String redisCategorys = getRedisCategorys();
		JSONObject resultJson = new JSONObject();
		//Redis中有存储信息，直接转为json返回
		if(!"".equals(redisCategorys)){
			resultJson = JSONObject.parseObject(redisCategorys);
			return resultJson;
		}
		List<IndustryCategory> list1 = industryCategoryMapper.selectByLevel(1);
		List<IndustryCategory> list2 = industryCategoryMapper.selectByLevel(2);
		// 请求三级和四级子目录
		List<IndustryCategory> list3 = industryCategoryMapper.selectByLevel(3);
		List<IndustryCategory> list4 = industryCategoryMapper.selectByLevel(4);
		JSONArray resultArray = new JSONArray();
		for (IndustryCategory category1 : list1) {
			JSONObject resultMap = new JSONObject();
			JSONArray children1 = new JSONArray();
			for (IndustryCategory category2 : list2) {
				JSONObject resultMap2 = new JSONObject();
				JSONArray children2 = new JSONArray();
				if (category2.getParentId().equals(category1.getId())) {
					for (IndustryCategory category3 : list3) {
						JSONObject resultMap3 = new JSONObject();
						JSONArray children3 = new JSONArray();

						if (category3.getParentId().equals(category2.getId())) {
							// 第四层级
							for (IndustryCategory category4 : list4) {
								if (category4.getParentId().equals(category3.getId())) {
									JSONObject maps4 = new JSONObject();
									maps4.put("childrenId", category4.getId());
									maps4.put("childrenName", category4.getName());
									maps4.put("categoryCode", category4.getCategoryCode());
									maps4.put("description", category4.getDescription());
									children3.add(maps4);
								}
							}
							resultMap3.put("id", category3.getId());
							resultMap3.put("name", category3.getName());
							resultMap3.put("description", category3.getDescription());
							resultMap3.put("categoryCode", category3.getCategoryCode());
							resultMap3.put("children", children3);
							children2.add(resultMap3);
						}
					}

					resultMap2.put("id", category2.getId());
					resultMap2.put("name", category2.getName());
					resultMap2.put("description", category2.getDescription());
					resultMap2.put("categoryCode", category2.getCategoryCode());
					resultMap2.put("children", children2);
					children1.add(resultMap2);
				}
			}
			resultMap.put("id", category1.getId());
			resultMap.put("name", category1.getName());
			resultMap.put("description", category1.getDescription());
			resultMap.put("categoryCode", category1.getCategoryCode());
			resultMap.put("children", children1);
			resultArray.add(resultMap);
		}
		resultJson.put("result", resultArray);
		
		setRedisCategorys(resultJson.toJSONString());
		
		return resultJson;
	}

	/**
	 * 往redis中插入商品分类信息
	 * @author 秦超
	 * @time 2017年5月12日
	 */
	private void setRedisCategorys(String jedisCategorys) {
		String jedisCategorysEncode = "";
		try {
			jedisCategorysEncode = URLEncoder.encode(jedisCategorys, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Jedis jedis = jedisUtils.getJedis();
		String jedisCategorysKey = CM_REDIS_KEY.REDIS_CATEGORYS;
		jedis.set(jedisCategorysKey, jedisCategorysEncode);
		jedisUtils.releaseJedis(jedis);
	}

	/**
	 * 获取redis中存储的商品分类信息
	 * @author 秦超
	 * @time 2017年5月12日
	 */
	private String getRedisCategorys() {
		Jedis jedis = jedisUtils.getJedis();
		String jedisCategorysKey = CM_REDIS_KEY.REDIS_CATEGORYS;
		String jedisCategorys = jedis.get(jedisCategorysKey);
		jedisUtils.releaseJedis(jedis);
		
		if(jedisCategorys==null || "null".equals(jedisCategorys)){
			return "";
		}
		
		String resultString = "";
		try {
			resultString = URLDecoder.decode(jedisCategorys, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return resultString;
	}
}
