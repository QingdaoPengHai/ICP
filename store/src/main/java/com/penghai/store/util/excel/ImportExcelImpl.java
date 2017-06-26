package com.penghai.store.util.excel;

import java.io.IOException;


public class ImportExcelImpl {

	public static void main(String[] args) throws IOException {
		String filePath = "C:/category.xlsx";
		ExcelUtil.readFromExcelDemo(filePath);//从一个指定的excel文件中读取内容
	}
}
