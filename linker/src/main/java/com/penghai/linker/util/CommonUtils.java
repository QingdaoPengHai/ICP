package com.penghai.linker.util;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {
		
		/**
		 * 日期格式化
		 * @param format
		 * @param date
		 * @return
		 */
		public static String formatData(String format,Date date) {
			if (StringUtils.isBlank(format)) {
				format="yyyy-MM-dd HH:mm:ss";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		}
		
		/**
		 * 将字符格式日期转换成日期格式
		 * @param format
		 * @param dateStr
		 * @return
		 */
		public static Date getDate(String format,String dateStr){
			Date date = null;
			try {
				if (StringUtils.isBlank(format)) {
					format="yyyy-MM-dd";
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				date = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}

		/**
		 * 读取配置文件属性
		 * @author 徐超
		 * @Date 2017年4月27日 上午11:06:40
		 * @param fileName 配置文件名 相对于config目录
		 * @param propertyName 属性名
		 * @return
		 */
		public static String getProperties(String fileName,String propertyName){
			
			Properties properties = new Properties();
			String value = "";
			try {
				//读入文件
				InputStream in = CommonUtils.class.getClassLoader().getResourceAsStream("configs/"+fileName);
				//加载数据
				properties.load(in);
				//获取具体配置
				value = properties.getProperty(propertyName);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
				return value;
			}
			return value;
		}
}
