package com.penghai.store.category.business;

public interface IInitDatasBusiness {

	/**
	 * 初始化行业分类列表
	 * @param fileAbsolutePath 文件绝对路径
	 * @param startRows 数据起始行
	 * @throws Exception 
	 */
	void initIndustryCategory (String fileAbsolutePath,int startRows) throws Exception;
}
