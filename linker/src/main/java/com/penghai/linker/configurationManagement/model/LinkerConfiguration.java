package com.penghai.linker.configurationManagement.model;

/**
 * 配置管理监控实体
 * @author 秦超
 * @time 2017年5月22日
 */
public class LinkerConfiguration {
	//存储系统访问ip
	String serverIp;
	//数据库连接ip
	String databaseIp;
	//数据库连接端口号
	String databasePort;
	//数据库连接登录名
	String databaseUsername;
	//数据库连接登录密码
	String databasePassword;
	//rabbitMQ连接ip
	String rabbitMqIp;
	//rabbitMQ连接端口号
	String rabbitMqPort;
	//rabbitMQ连接登录名
	String rabbitMqUsername;
	//rabbitMQ连接登录密码
	String rabbitMqPassword;
	//linker服务配置状态
	String linkerRunningStatus;
	
	
	public String getLinkerRunningStatus() {
		return linkerRunningStatus;
	}
	public void setLinkerRunningStatus(String linkerRunningStatus) {
		this.linkerRunningStatus = linkerRunningStatus;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getDatabaseIp() {
		return databaseIp;
	}
	public void setDatabaseIp(String databaseIp) {
		this.databaseIp = databaseIp;
	}
	public String getDatabasePort() {
		return databasePort;
	}
	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}
	public String getDatabaseUsername() {
		return databaseUsername;
	}
	public void setDatabaseUsername(String databaseUsername) {
		this.databaseUsername = databaseUsername;
	}
	public String getDatabasePassword() {
		return databasePassword;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	public String getRabbitMqIp() {
		return rabbitMqIp;
	}
	public void setRabbitMqIp(String rabbitMqIp) {
		this.rabbitMqIp = rabbitMqIp;
	}
	public String getRabbitMqPort() {
		return rabbitMqPort;
	}
	public void setRabbitMqPort(String rabbitMqPort) {
		this.rabbitMqPort = rabbitMqPort;
	}
	public String getRabbitMqUsername() {
		return rabbitMqUsername;
	}
	public void setRabbitMqUsername(String rabbitMqUsername) {
		this.rabbitMqUsername = rabbitMqUsername;
	}
	public String getRabbitMqPassword() {
		return rabbitMqPassword;
	}
	public void setRabbitMqPassword(String rabbitMqPassword) {
		this.rabbitMqPassword = rabbitMqPassword;
	}

	
}
