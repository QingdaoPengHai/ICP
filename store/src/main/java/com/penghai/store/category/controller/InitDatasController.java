package com.penghai.store.category.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.penghai.store.category.business.IInitDatasBusiness;
import com.penghai.store.common.controller.BaseController;
import com.penghai.store.common.model.HttpSession;
import com.penghai.store.good.dao.mybatis.GoodsMapper;
import com.penghai.store.good.model.Goods;
import com.penghai.store.order.business.IShoppingCartBusiness;
import com.penghai.store.order.model.ShoppingCart;
import com.penghai.store.user.dao.mybatis.UserMapper;
import com.penghai.store.user.model.User;
import com.penghai.store.util.common.CommonData.CM_CONFIG_PROPERTIES;



@Controller
public class InitDatasController extends BaseController implements CM_CONFIG_PROPERTIES{

	@Autowired
	private IInitDatasBusiness initDatasService;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private GoodsMapper goodsMapper;
	
	// 获取会话对象
	@Autowired
	public HttpSession session;
	@Autowired
	public IShoppingCartBusiness iShoppingCartBusiness;
	
	@RequestMapping(value = "/initIndustryCategory")
	public String initIndustryCategory(Model model){
		try {
			initDatasService.initIndustryCategory("C:/category.xlsx", 1);
			return "initIndustryCategorySuccess";
		} catch (Exception e) {
			e.printStackTrace();
			return "initIndustryCategoryError";
		}
	}
	
	@RequestMapping(value = "/test")
	public void unitTestDao() {
		
//		User user = new User();
		/**
		 * test of 插入用户
		 */
//		user.setNickname("小米");
//		user.setEmail("xiaomi@163.com");
//		user.setPassword("123123");
//		user.setEnterpriseName("北京小米科技有限责任公司");
//		user.setOrganizationCode("551385082");
//		user.setAddress("北京市海淀区清河中街68号");
//		user.setContacts("雷军");
//		user.setTel("13300000000");
//		user.setStatus(new Byte("1"));
//		user.setRegistrationTime(new Date());
//		int i = userMapper.insertSelective(user);
//		int num = userMapper.checkAddUnique(user);
//		System.out.println(i);
		/**
		 * test of 检测邮箱唯一性
		 */
//		user.setEmail("xiaomi@163.com2");
//		int num = userMapper.checkAddUnique(user);
//		System.out.println(num);
		
//		/**
//		 * test of 获取商品列表
//		 */
//		List<Goods> list = new ArrayList<Goods>();
//		Goods goodsParam = new Goods();
//		goodsParam.setPageIndex(0);
//		goodsParam.setPageSize(8);
//		list = goodsMapper.getGoodsListByCondition(goodsParam);
//		System.out.println(list.size());
		
		/**
		 * test of 存储session,获取session
		 */
//		ShoppingCart userShoppingCart = new ShoppingCart();
//		userShoppingCart.setGoodsName("商品");
//		userShoppingCart.setGoodsNumber("1");
//		userShoppingCart.setGoodsPrice("123.123");
//		userShoppingCart.setPictureURL("http://123123.sdasd.com");
//		userShoppingCart.setGoodsId("231");
		
//		List<ShoppingCart> cartList = new ArrayList<ShoppingCart>();
//		cartList.add(userShoppingCart);
		
//		
//		String userShoppingCartString = JSONObject.toJSONString(userShoppingCart);
//		String cartListString = JSONObject.toJSONString(cartList);
//		
//		List<ShoppingCart> cartList2 = new ArrayList<ShoppingCart>();
//		cartList2 = JSONObject.parseArray(cartListString, ShoppingCart.class);
		
//		Map<String, ShoppingCart> cartListMap = new HashMap<String, ShoppingCart>();
//		
//		cartListMap.put("1", userShoppingCart);
//		cartListMap.put("2", userShoppingCart);
//		
//		
//		String cartString = JSONObject.toJSONString(cartListMap);
//		
//		
//		System.out.println(cartListMap.get("2").getGoodsName());
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		int a = iShoppingCartBusiness.addGoodsIntoShoppingCart("2");
//		
//		resultMap = iShoppingCartBusiness.getAllgoodsListFromShoppingCart();
//		System.out.println(123);
		
//		Properties pro = new Properties();
//		try {
//			InputStream in = this.getClass().getClassLoader().getResourceAsStream("configs/commonDataConfig.properties");
//			pro.load(in);
//			
//			String aa = pro.getProperty("goodsPictureUploadURL");
//			System.out.println(aa);
//			in.close();
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
		
		System.out.println(GOODS_PICTURE_UPLOAD_URL);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
