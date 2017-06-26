package com.penghai.store.category.dao.mybatis;

import java.util.List;

import com.penghai.store.category.model.IndustryCategory;

public interface IndustryCategoryMapper {

	int deleteByPrimaryKey(String id);

	int insert(IndustryCategory record);

	/**
	 * 新增分类
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:37:43
	 * @param record
	 * @return
	 */
	int insertSelective(IndustryCategory record);

	/**
	 * 获取分类详情
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:37:27
	 * @param id
	 * @return
	 */
	IndustryCategory selectByPrimaryKey(String id);

	/**
	 * 编辑分类
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:38:07
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(IndustryCategory record);

	int updateByPrimaryKey(IndustryCategory record);

	/**
	 * 检查主键是否重复,防止抛错
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:36:33
	 * @param id
	 * @return
	 */
	int checkCateIdExist(String id);

	/**
	 * 检查分类名称是否重复
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午3:04:07
	 * @param cateName
	 * @return
	 */
	int checkCateNameExist(String cateName);

	/**
	 * 根据上级分类ID查询下级分类列表
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:37:17
	 * @param parentId
	 * @return
	 */
	List<IndustryCategory> selectChildCateList(String parentId);

	/**
	 * 批量删除分类
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:53:11
	 * @param cateIds
	 * @return
	 */
	int deleteCategorysByCateIdList(List<String> cateIds);

	/**
	 * 根据条件查询分类
	 * 
	 * @author 徐超
	 * @Date 2017年4月22日 下午1:54:35
	 * @param record
	 * @return
	 */
	List<IndustryCategory> selectCategoryByCondition(IndustryCategory record);

	/**
	 * 根据分类ID查询所属的分类代码
	 * 
	 * @author 李浩
	 * @Date 2017年4月25日
	 * @return
	 */
	String selectCatagoryCodeById(Integer id);

	/**
	 * 根据层级数查询该层级所有的分类对象
	 * 
	 * @author 李浩
	 * @Date 2017年4月25日
	 * @return
	 */
	List<IndustryCategory> selectByLevel(Integer level);

}