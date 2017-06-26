package com.penghai.store.util.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.penghai.store.util.common.CommonData.CM_CONFIG_PROPERTIES;

/**
 * 公共方法
 * 
 * @author 李浩
 *
 */
public class CommonFunction {
	/*
	 * 图片保存路径
	 */
	private static final String IMAGEPATH = CM_CONFIG_PROPERTIES.GOODS_PICTURE_UPLOAD_URL;
	private static final String IMAGEREADPATH = CM_CONFIG_PROPERTIES.GOODS_PICTURE_READ_URL;

	/**
	 * 图片保存方法
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return String
	 * @throws IOException
	 */
	public static String upLoadImage(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// 获取图片后缀名
		String name = file.getOriginalFilename();
		String[] suffix = name.split("\\.");
		String newFileName = "good_" + new Date().getTime() + "." + suffix[1];
		String newFilePath = IMAGEPATH;
		// 判断路径是否存在
		File fileDir = new File(newFilePath);
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		try {
			FileOutputStream out = new FileOutputStream(newFilePath + newFileName);
			out.write(file.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			return "";
		}
	//	return newFilePath + newFileName;
		//存储商品图片，和读取商品图片路径分开
		return IMAGEREADPATH + newFileName;
	}

	/**
	 * 判断图片是否在某路径下存在
	 */
	public static boolean isFileExist(String fileName) {
		if (fileName == null) {
			return false;
		}
		return (new File(fileName)).exists();
	}

	/**
	 * 删除图片
	 */
	@SuppressWarnings("finally")
	public static boolean deleteFile(String srcFileName) {
		boolean success = true;
		try{
			File file = new File(srcFileName);
			if (file.exists()) {
				success = file.delete();
			}
			if (!success) {
				System.out.println(srcFileName+" 删除失败！");
			} 
		}catch (Exception e){
			e.printStackTrace();
			success = false;
		}finally{
			return success;
		}
	}

}
