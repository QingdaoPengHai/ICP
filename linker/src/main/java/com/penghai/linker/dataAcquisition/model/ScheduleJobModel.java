package com.penghai.linker.dataAcquisition.model;
/**
 * 定时任务属性实体
 * @author 徐超
 * @Date 2017年5月16日 下午4:19:27
 */
public class ScheduleJobModel {

	/** 任务id */
    private String jobId;
    /** 任务名称 */
    private String jobName;
    /** 任务分组 */
    private String jobGroup;
    /** 任务状态 0禁用 1启用 2删除*/
    private String jobStatus;
    /** 任务运行时间表达式 */
    private String cronExpression;
    /** 任务描述 */
    private String desc;
    /** 当前查询条数--主键的值 */
    private int currentSelectColumn;
    /** 当前存储条数--至MQ--主键的值 */
    private int currentStorageColumn;
    /** 当前查询条数--条数数量 */
    private int currentSelectColumnNumber;
    /** 当前存储条数--至MQ--条数数量 */
    private int currentStorageColumnNumber;
    /** 查询表名 */
    private String targetTable;
    /** 存储表名 */
    private String storageTable;
    /** 执行的SQL */
    private String selectSQL;
    /** 主键名称 */
    private String primaryKey;
    /** 主键位置 */
    private String primaryKeyOrder;
    /** css关联表名 */
    private String refTableName;
    /** css关联表列名列表 */
    private String refColumns;
    /** css关联表主键 */
    private String refPrimaryKey;
    
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}
	public String getStorageTable() {
		return storageTable;
	}
	public void setStorageTable(String storageTable) {
		this.storageTable = storageTable;
	}
	public String getSelectSQL() {
		return selectSQL;
	}
	public void setSelectSQL(String selectSQL) {
		this.selectSQL = selectSQL;
	}
	public int getCurrentSelectColumn() {
		return currentSelectColumn;
	}
	public void setCurrentSelectColumn(int currentSelectColumn) {
		this.currentSelectColumn = currentSelectColumn;
	}
	public int getCurrentStorageColumn() {
		return currentStorageColumn;
	}
	public void setCurrentStorageColumn(int currentStorageColumn) {
		this.currentStorageColumn = currentStorageColumn;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getPrimaryKeyOrder() {
		return primaryKeyOrder;
	}
	public void setPrimaryKeyOrder(String primaryKeyOrder) {
		this.primaryKeyOrder = primaryKeyOrder;
	}
	public int getCurrentSelectColumnNumber() {
		return currentSelectColumnNumber;
	}
	public void setCurrentSelectColumnNumber(int currentSelectColumnNumber) {
		this.currentSelectColumnNumber = currentSelectColumnNumber;
	}
	public int getCurrentStorageColumnNumber() {
		return currentStorageColumnNumber;
	}
	public void setCurrentStorageColumnNumber(int currentStorageColumnNumber) {
		this.currentStorageColumnNumber = currentStorageColumnNumber;
	}
	public String getRefTableName() {
		return refTableName;
	}
	public void setRefTableName(String refTableName) {
		this.refTableName = refTableName;
	}
	public String getRefColumns() {
		return refColumns;
	}
	public void setRefColumns(String refColumns) {
		this.refColumns = refColumns;
	}
	public String getRefPrimaryKey() {
		return refPrimaryKey;
	}
	public void setRefPrimaryKey(String refPrimaryKey) {
		this.refPrimaryKey = refPrimaryKey;
	}
    
    
}
