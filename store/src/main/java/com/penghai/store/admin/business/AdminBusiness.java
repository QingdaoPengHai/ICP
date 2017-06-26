package com.penghai.store.admin.business;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.good.model.Goods;

public interface AdminBusiness {
	/**
	 * 管理员登录接口
	 * 
	 * @param map
	 */
	JSONObject adminLogin(Map<String, Object> map);

	/**
	 * 添加商品接口
	 * 
	 * @param map
	 */
	JSONObject addGood(Map<String, Object> map, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 删除某个商品
	 */
	JSONObject deleteGoodById(int id);

	/**
	 * 更新某个商品
	 * 
	 * @param map
	 * @return
	 */
	JSONObject updateGood(Map<String, Object> map, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * 查询所有商品
	 */
	List<Goods> queryAllGoods(Map<String, Object> map);
}
