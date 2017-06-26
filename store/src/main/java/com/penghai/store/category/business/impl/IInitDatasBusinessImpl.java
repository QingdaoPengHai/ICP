package com.penghai.store.category.business.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.penghai.store.category.business.IInitDatasBusiness;
import com.penghai.store.category.dao.mybatis.InitDatasDaoI;
import com.penghai.store.category.model.Category;

@Service
public class IInitDatasBusinessImpl implements IInitDatasBusiness{

	@Autowired
	private InitDatasDaoI initDatasDaoI;
	
	/**
	 * 初始化行业分类列表
	 * @param fileAbsolutePath 文件绝对路径
	 * @param startRows 数据起始行
	 * @throws Exception 
	 */
	public void initIndustryCategory(String fileAbsolutePath,int startRows) throws Exception{
		/**
		 * 读取Excel表中的所有数据
		 */
		
			Workbook workbook = getWeebWork(fileAbsolutePath);
			System.out.println("总表页数为：" + workbook.getNumberOfSheets());// 获取表页数
			Sheet sheet = workbook.getSheetAt(0);
			int rownum = sheet.getLastRowNum();// 获取总行数
			for (int i = startRows; i <= rownum; i++) {
				Category category = new Category();
				Row row = sheet.getRow(i);
				for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
					String str = "";
					Cell celldata = row.getCell(j);
					if(null!=celldata){
						celldata.setCellType(CellType.STRING);
						str= celldata.getStringCellValue();
					}
					switch (j) {
					case 0:
						category.setId(str);
						break;
					case 1:
						category.setParentId(str);
						break;
					case 2:
						category.setLevel(str);
						break;
					case 3:
						category.setName(str);
						break;
					case 4:
						category.setCategoryCode(str);
						break;
					case 5:
						category.setDescription(str);
						break;
					case 6:
						category.setStatus(str);
						break;
					}
					System.out.print(str + "\t");
				}
				System.out.println(category.toString());
				initDatasDaoI.saveCategoryList(category);
			}
	}
	
	
	/**
	 * 
	 * @Title: getWeebWork
	 * @Description: (根据传入的文件名获取工作簿对象(Workbook))
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWeebWork(String filename) throws IOException {
		Workbook workbook = null;
		if (null != filename) {
			String fileType = filename.substring(filename.lastIndexOf("."),
					filename.length());
			FileInputStream fileStream = new FileInputStream(new File(filename));
			if (".xls".equals(fileType.trim().toLowerCase())) {
				workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
			} else if (".xlsx".equals(fileType.trim().toLowerCase())) {
				workbook = new XSSFWorkbook(fileStream);// 创建 Excel 2007 工作簿对象
			}
		}
		return workbook;
	}
}
