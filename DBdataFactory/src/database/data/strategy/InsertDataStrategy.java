package database.data.strategy;

import java.util.List;
import java.util.Map;

import database.pojo.ColumnInfo;

public interface InsertDataStrategy {
	/**
	 * @param tableColumnMessage 这是表格的列信息比如 t_user表中有id int型 username varchar(20)
	 * 则tableColumnMessage = {{id,{id,interger,true}},{username,{username,varchar,false}}}
	 * @return 返回的数据恰好对应了表格的多行数据
	 * anping
	 * TODO 数据生成策略
	 * 上午9:22:57
	 */
	public List<Map<String, Object>>  dataGeneration(List<ColumnInfo> tableColumnMessage);
}
