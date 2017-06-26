package com.penghai.linker.configurationManagement.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.penghai.linker.configurationManagement.business.ILogMonitorBusiness;
import com.penghai.linker.util.CommonData.CM_CONFIG_PROPERTIES;
import com.penghai.linker.util.CommonData.CM_MESSAGE_INFO;

/**
 * 日志监控处理
 * @author 秦超
 * @time 2017年5月19日
 */
@Service
public class LogMonitorBusinessImpl implements ILogMonitorBusiness {
	/**
	 * 获取最新的日志信息
	 * @author 秦超
	 * @return
	 * @throws IOException
	 * @time 2017年5月19日
	 */
	@Override
	public JSONObject getRecentLog() throws IOException {
		JSONObject resultJson = new JSONObject();
		String logFilePath = CM_CONFIG_PROPERTIES.LOG_FILE_PATH;
		
		FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        //读取文件信息
		File file = new File(logFilePath);//文件路径(包括文件名称)
        try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			//读取log信息
			List<String> logs = new ArrayList<String>();
			String temp  = "";
			for(int i=0;(temp =br.readLine())!=null;i++){
				logs.add(temp);
			}
			//截取最新log片段
			StringBuffer buffer = new StringBuffer();
			int rows = logs.size();
			for(int j=rows-Integer.parseInt(CM_CONFIG_PROPERTIES.LOG_REFRESH_NUMBER);j<rows;j++){
				if(j>=0){
					buffer.append(logs.get(j));
					// 行与行之间的分隔符 相当于“\n”
					buffer = buffer.append(System.getProperty("line.separator"));				
				}
			}
			
	        String logString = buffer.toString();
			resultJson.put("logInfo", logString);
	        resultJson.put("code", CM_MESSAGE_INFO.RESULT_SUCCESS_CODE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			resultJson.put("code", CM_MESSAGE_INFO.RESULT_ERROR_CODE);
		}
        //不要忘记关闭
        if (br != null) {
            br.close();
        }
        if (isr != null) {
            isr.close();
        }
        if (fis != null) {
            fis.close();
        }
        
        return resultJson;
	}
}
