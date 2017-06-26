package com.penghai.linker.dataAcquisition.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
/**
 * 表对象
 * @author 徐超
 * @Date 2017年5月3日 上午10:49:01
 */
public class TargetTable {

	//表名
	private String name;
	//与target表库对应的表名
	private String targetRef;
	//读取时间间隔
	private int queryInterval;
	//表列表
	private List<TargetColumn> columns;
	
	@XmlAttribute(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@XmlAttribute(name = "targetRef")
	public String getTargetRef() {
		return targetRef;
	}
	public void setTargetRef(String targetRef) {
		this.targetRef = targetRef;
	}
	
	@XmlAttribute(name = "queryInterval")
	public int getQueryInterval() {
		return queryInterval;
	}
	public void setQueryInterval(int queryInterval) {
		this.queryInterval = queryInterval;
	}
	
	@XmlElement(name = "column")
	public List<TargetColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<TargetColumn> columns) {
		this.columns = columns;
	}
	/**
	 * 生成字段列表
	 * @author 徐超
	 * @Date 2017年5月17日 下午9:57:44
	 * @return
	 */
	public String getColumnList(){
		StringBuffer columnListString = new StringBuffer();
		//遍历列对象列表,拼接列字段名
		for(TargetColumn column:this.columns){
			columnListString.append(column.getName()).append(",");
		}
		//删除最后一个逗号
		columnListString.deleteCharAt(columnListString.length()-1);
		return columnListString.toString();
	}
	
	/**
	 * 获取表中的主键字段名及顺序
	 * @author 徐超
	 * @Date 2017年5月18日 下午9:18:14
	 * @return
	 */
	public Map<String,String> getPrimaryKeyAndOrder(){
		Map<String,String> map = new HashMap<String,String>();
		//主键名称
		String primaryKey = "";
		//主键顺序
		int order = 0;
		//拼接键值对
		for(int i=0;i<this.columns.size();i++){
			if(this.columns.get(i).getPrimaryKey()){
				primaryKey = this.columns.get(i).getName();
				order = i;
			}
		}
		//组装返回map
		map.put("primaryKey", primaryKey);
		map.put("order", String.valueOf(order));
		return map;
	}
	
}
