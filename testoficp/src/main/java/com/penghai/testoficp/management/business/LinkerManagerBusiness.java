/*
 * function: linker接口类
 * author： LiuLihua
 * date：2017-05-09
 */

package com.penghai.testoficp.management.business;

public interface LinkerManagerBusiness {
	
	//linker注册接口
	String linkerRegister(String linkerId);
	
	//linker上报错误接口
	String linkerReportError(String linkerId, String errorType, String errorInformation);
	
}
