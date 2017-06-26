package com.penghai.css.management.model.databaseModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * 表对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:49:01
 */
public class Table {

	//表名
	private String name;
	//分区个数
	private int partitionNumber = 1024;
	//关联表名称
	private String ref;
	//列的列表
	private List<Column> columns;
	//使用的存储引擎
	private String engine = "InnoDB";
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlAttribute(name = "partitionNumber")
	public int getPartitionNumber() {
		return partitionNumber;
	}
	public void setPartitionNumber(int partitionNumber) {
		this.partitionNumber = partitionNumber;
	}
	@XmlAttribute(name = "ref")
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	@XmlElement(name = "column")
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	@XmlAttribute(name = "engine")
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	/**
	 * 返回建表语句
	 * @author 徐超
	 * @Date 2017年5月3日 下午11:20:22
	 * @return
	 */
	public String createTableSQL(String databaseName){
		//TODO 若拼接时发现有错误,直接返回空字符串
		//表名
		StringBuffer sql = new StringBuffer();
		sql = sql.append(" CREATE TABLE IF NOT EXISTS ")
				 .append(databaseName).append(".`")
				 .append(this.name).append("` ( ");
		
		StringBuffer columnsSql = new StringBuffer();
		
		//暂存主键字段名
		String primaryKeyColumnname = "";
		//暂存需要建立索引字段名列表
		List<String> indexColumnNameList = new ArrayList<String>();
		//暂存需要分区字段名
		String partitionColumnName = "";
		//暂存分区个数,默认1024个
		int partitionNumber = 1024;
		
		//循环column对象,拼接创建字段语句
		for(Column column : this.columns){
			
			//字段名及字段类型、长度
			columnsSql.append(" `").append(column.getName()).append("` ")
					  .append(column.getType()).append(" ");
			//若指定了长度
			if(0 != column.getLength()){
				columnsSql.append(" (").append(column.getLength());	
				
				//判断是否指定了精度
				if(0!=column.getPrecision()){
					columnsSql.append(",").append(column.getPrecision());
				}
				columnsSql.append(") ");
			}else if("varchar".equals(column.getType().toLowerCase())){
				//因varchar类型必须指定长度
				columnsSql.append(" (").append("255");
				columnsSql.append(") ");
			}
			//判断是否指定非空
			if(column.getIsNull()){
				columnsSql.append(" NOT NULL ");
			}
			//判断是否指定了默认值
			if(null != column.getDefaultValue()){
				columnsSql.append(" DEFAULT '").append(column.getDefaultValue()).append("' ");
			}
			columnsSql.append(",");
			
			//暂存Start
			//暂存主键字段名
			if(column.getPrimaryKey()){
				primaryKeyColumnname = column.getName();
			}
			//暂存创建索引字段名列表
			if(column.getCreateIndex()){
				indexColumnNameList.add(column.getName());
			}
			//暂存分区字段名及分区数量
			if(column.getPartition()){
				partitionColumnName = column.getName();
				partitionNumber = this.partitionNumber;
			}
			//暂存End
		}
		//删除最后一个逗号
		columnsSql.deleteCharAt(columnsSql.length()-1);
		
		//添加主键约束
		if(!"".equals(primaryKeyColumnname)){
			columnsSql.append(" , PRIMARY KEY (`")
					  .append(primaryKeyColumnname).append("`) ");
		}
		//添加索引
		if(0<indexColumnNameList.size()){
			for(String columnName : indexColumnNameList){
				columnsSql.append(" , KEY `").append(columnName).append("` ")
						  .append("(`").append(columnName).append("`) ");
			}
		}
		
		sql.append(columnsSql).append(" ) ")
		   .append("ENGINE=").append(this.engine)
		   .append(" DEFAULT CHARSET=utf8;");
		
		//添加分区信息
		if(!"".equals(partitionColumnName) && null!=partitionColumnName && !"null".equals(partitionColumnName)){
			//删除分号
			sql.deleteCharAt(sql.length()-1);
			sql.append(" /*!50100 PARTITION BY HASH (")
			   .append(partitionColumnName).append(")")
			   .append("PARTITIONS ").append(partitionNumber).append(" */;");
		}
		
		return sql.toString();
	}
	/**
	 * 生成添加外键sql
	 * @author 徐超
	 * @Date 2017年5月5日 下午5:41:13
	 * @return
	 */
	public String createForeignKeySQL(String databaseName){
		//TODO 若拼接时发现有错误,直接返回空字符串
		StringBuffer sql = new StringBuffer();
		
		//需要关联外键的表名
		String tableNameNeedReference = "";
		//被关联外键的表名
		String tableNameBeReferenced = "";
		//需要关联外键的列名
		String columnNameNeedReference = "";
		//被关联外键的列名
		String columnNameBeReferenced = "";
		if(!"".equals(this.ref) && null!=this.ref && !"null".equals(this.ref)){
			tableNameNeedReference = this.name;
			tableNameBeReferenced = this.ref;
		}
		
		//循环column对象,拼接创建字段语句
		for(Column column : this.columns){
			//判断是否指定了外键字段
			if(!"".equals(column.getForeinKey()) && null!=column.getForeinKey() && !"null".equals(column.getForeinKey())){
				columnNameNeedReference = column.getName();
				columnNameBeReferenced = column.getForeinKey();
			}
		}
		if(	   !"".equals(tableNameNeedReference) 
			&& !"".equals(tableNameBeReferenced) 
			&& !"".equals(columnNameNeedReference) 
			&& !"".equals(columnNameBeReferenced)){
			//添加外键约束
			sql.append(" ALTER TABLE ").append(databaseName).append(".`")
			   .append(tableNameNeedReference).append("` ")
			   .append(" ADD CONSTRAINT `").append(columnNameNeedReference).append("` ")
			   .append(" FOREIGN KEY (`").append(columnNameNeedReference).append("`) ")
			   .append(" REFERENCES `").append(tableNameBeReferenced).append("` ")
			   .append(" (`").append(columnNameBeReferenced).append("`); ");
		}
		return sql.toString();
	}
	
	/**
	 * 获取该表的主键字段名称
	 * @author 徐超
	 * @Date 2017年6月5日 下午5:57:09
	 * @return
	 */
	public String getPrimaryKey(){
		String primaryKey = "";
		for(Column column:this.columns){
			if(column.getPrimaryKey()){
				primaryKey = column.getName();
			}
		}
		return primaryKey;
	}
	
	/**
	 * 获取列名列表String
	 * @author 徐超
	 * @Date 2017年6月8日 下午3:56:23
	 * @return
	 */
	public String getColumnsListString(){
		StringBuffer columnsList = new StringBuffer();
		columnsList.append("(");
		for(Column column:this.columns){
			columnsList.append(column.getName()).append(",");
		}
		columnsList.deleteCharAt(columnsList.length()-1);
		columnsList.append(")");
		
		return columnsList.toString();
	}
	
}
