package com.penghai.css.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.penghai.css.util.CommonData.CM_CONFIG_PROPERTIES;

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
		String fileName = file.getOriginalFilename();
		File tempFile = new File(IMAGEPATH, new Date().getTime() + String.valueOf(fileName));
		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}
		if (!tempFile.exists()) {
			tempFile.createNewFile();
		}
		file.transferTo(tempFile);
		// 返回路径+文件名
		return CM_CONFIG_PROPERTIES.GOODS_PICTURE_UPLOAD_URL + "/" + tempFile.getName();
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
	public static void deleteFile(String srcFileName) throws Exception {
		boolean success = true;
		File file = new File(srcFileName);
		if (file.exists()) {
			success = file.delete();
		}
		if (!success) {
			throw new Exception("删除图片:" + srcFileName + "不成功！");
		} else {
			return;
		}
	}

}
