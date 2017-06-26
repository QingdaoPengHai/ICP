package com.penghai.store.admin.business.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.admin.business.AdminBusiness;
import com.penghai.store.admin.dao.mybatis.AdminMapper;
import com.penghai.store.common.model.HttpSession;
import com.penghai.store.good.dao.mybatis.GoodsMapper;
import com.penghai.store.good.model.Goods;
import com.penghai.store.util.common.CommonData.CM_ADMIN_ADDGOOD;
import com.penghai.store.util.common.CommonData.CM_LOGIN_DATA;
import com.penghai.store.util.common.CommonFunction;

@Service
public class AdminBusinessImpl implements AdminBusiness {
	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private HttpSession session;
	@Autowired
	private GoodsMapper goodsMapper;

	/*
	 * 验证用户登录信息,返回登录数据
	 * 
	 */
	@Override
	public JSONObject adminLogin(Map<String, Object> map) {
		JSONObject result = new JSONObject();
		String isUser = adminMapper.checkAddUnique(map);
		String isRight = adminMapper.checkAuth(map);
		if (!isUser.equals("1") || !isRight.equals("1")) {
			result.put("message", CM_LOGIN_DATA.LOGIN_ERROR_INFO);
			result.put("code", CM_LOGIN_DATA.LOGIN_ERROR_CODE);
			return result;
		}
		Map<String, Object> admin = adminMapper.selectByCondition(map);
		result.put("message", CM_LOGIN_DATA.LOGIN_SUCCESS_INFO);
		result.put("code", CM_LOGIN_DATA.LOGIN_SUCCESS_CODE);
		// 成功后session设置用户登录信息
		if (session.get("adminUserId") != null && !session.get("adminUserId").equals("null")) {
			session.hdel("adminUserId", "adminNickname", "adminEmail");
		}
		session.set("adminUserId", "" + admin.get("id"));
		session.set("adminNickname", (String) admin.get("name"));
		session.set("adminEmail", (String) admin.get("email"));

		return result;
	}

	/**
	 * 添加商品业务
	 */
	@Override
	public JSONObject addGood(Map<String, Object> map, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		// 封装商品对象
		Goods goods = new Goods();
		JSONObject result = new JSONObject();
		goods.setGoodCate(Integer.valueOf((String) map.get("goodCate")));
		goods.setGoodName((String) map.get("goodName"));
		goods.setDescription((String) map.get("description"));
		goods.setPrice(new BigDecimal((String) map.get("price")));
		goods.setIsForSale(CM_ADMIN_ADDGOOD.GOOD_SALE);
		goods.setStatus(CM_ADMIN_ADDGOOD.GOOD_STATUS_ONE);
		goods.setGoodModel((String) map.get("goodModel"));
		goods.setAddTime(new Date());
		goods.setUpdateTime(new Date());
		String pictureName = "";
		try {
			pictureName = CommonFunction.upLoadImage(file, request, response);
		} catch (IOException e) {
			e.printStackTrace();
			// 图片上传失败
			result.put("success", false);
			result.put("message", CM_ADMIN_ADDGOOD.IMAGE_UPLOAD_ERROR);
			return result;
		}
		if (pictureName.equals("")) {
			// 商品上传失败
			result.put("success", false);
			result.put("message", CM_ADMIN_ADDGOOD.GOOD_UPLOAD_ERROR);
		}
		goods.setDefaultPicture(pictureName);
		int success = goodsMapper.insertSelective(goods);
		// 商品上传成功
		if (success > 0) {
			result.put("success", true);
			result.put("goodId", success);
			result.put("message", CM_ADMIN_ADDGOOD.GOOD_UPLOAD_SUCCESS);
		} else {
			// 商品上传失败
			result.put("success", false);
			result.put("message", CM_ADMIN_ADDGOOD.GOOD_UPLOAD_ERROR);
		}
		return result;
	}

	/**
	 * 删除商品
	 */
	public JSONObject deleteGoodById(int id) {
		JSONObject result = new JSONObject();
		String defaultPicture = goodsMapper.selectPictureNameById(id);
		boolean deletePictureSuccess = CommonFunction.deleteFile(defaultPicture);
		if(deletePictureSuccess){
			int success = goodsMapper.deleteByPrimaryKey(id);
			if(success == 1){
				result.put("success", true);
			}else{
				result.put("success", false);
			}
		}else{
			result.put("success", false);
		}
		return result;
	}

	/**
	 * 修改商品
	 */
	public JSONObject updateGood(Map<String, Object> map, MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		Goods goods = new Goods();
		JSONObject result = new JSONObject();
		goods.setId(Integer.valueOf((String) map.get("id")));
		goods.setGoodCate(Integer.valueOf((String) map.get("goodCate")));
		goods.setGoodName((String) map.get("goodName"));
		goods.setDescription((String) map.get("description"));
		goods.setPrice(new BigDecimal((String) map.get("price")));
		goods.setDefaultPicture((String) map.get("defaultPicture"));
		goods.setIsForSale(CM_ADMIN_ADDGOOD.GOOD_SALE);
		goods.setStatus(CM_ADMIN_ADDGOOD.GOOD_STATUS_ONE);
		goods.setGoodModel((String) map.get("goodModel"));
		goods.setUpdateTime(new Date());
		String pictureName = "";
		if (CommonFunction.isFileExist((String) map.get("oldPicture"))) {
			try {
				CommonFunction.deleteFile((String) map.get("oldPicture"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println((String) map.get("oldPicture") + "图片删除失败");
			}
		} else {
			try {
				pictureName = CommonFunction.upLoadImage(file, request, response);
			} catch (IOException e) {
				e.printStackTrace();
				// 图片上传失败
				result.put("success", false);
				result.put("message", CM_ADMIN_ADDGOOD.IMAGE_UPLOAD_ERROR);
				return result;
			}
		}

		goods.setDefaultPicture(pictureName);
		int success = goodsMapper.updateByPrimaryKeySelective(goods);
		if (success > 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		return result;
	}

	/**
	 * 按条件查询商品
	 */
	public List<Goods> queryAllGoods(Map<String, Object> map) {

		List<Goods> result = goodsMapper.getGoodsByCondition(map);
		return result;
	}

}
