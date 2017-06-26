package com.penghai.css.management.model.databaseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * xml总体映射对象,xml根节点
 * @author 徐超
 * @Date 2017年5月3日 上午10:47:29
 */
@XmlRootElement(name = "schema")
public class Schema {

	//配置文件名称
	private String name;
	//要新增的数据库的列表
	private List<Database> databases;
	//要查询的数据库的列表
	private List<TargetDatabase> targetDatabases;
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "database")
	public List<Database> getDatabases() {
		return databases;
	}
	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}
	
	@XmlElement(name = "targetDatabase")
	public List<TargetDatabase> getTargetDatabases() {
		return targetDatabases;
	}
	public void setTargetDatabases(List<TargetDatabase> targetDatabases) {
		this.targetDatabases = targetDatabases;
	}
	/**
	 * 拼接所有的
	 * @author 徐超
	 * @Date 2017年5月6日 下午2:18:25
	 * @return
	 */
	public String conbineAllCreateSQL(){
		StringBuffer sql = new StringBuffer();
		
		for(Database database : databases){
			
			sql.append(database.conbineAllCreateSQL());
//			sql.append(" USE css; ");
		} 
		return sql.toString();
	}

	/**
	 * 获取schema中所有的数据库及其所有表中的索引字段
	 * 
	 * @return
	 */
	public HashMap<String, Map<String, List<String>>> getIndexNames() {
		if (databases != null) {
			// 所有数据库
			HashMap<String, Map<String, List<String>>> result = new HashMap<>();
			for(Database database:databases){
				Map<String, List<String>> tables = new HashMap<>();
				for (Table table : database.getTables()) {
					// 每个集合中所有的索引名称
					List<String> indexs = new ArrayList<>();
					for (Column column : table.getColumns()) {
						if (column.getPrimaryKey() || column.getCreateIndex()) {
							// 添加索引字段
							indexs.add(column.getName());
						}
					}
					tables.put(table.getName(), indexs);
				}
				result.put(database.getName(), tables);
			}
			return result;
		} else {
			return null;
		}
	}

}
