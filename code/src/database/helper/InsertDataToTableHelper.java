package database.helper;

import java.sql.Connection;
import java.sql.Time;
import java.sql.Types;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import database.insert.strategy.InsertDataStrategy;
import database.po.ColumnInfo;

public class InsertDataToTableHelper {

	/**
	 * 
	 * @param columnInfo
	 * @param data
	 * @return anping TODO 拼接sql语句片（通过列的属性，比如列属性为varchar 则拼接为
	 *         sql="'"+data.get(columnInfo.getName())+"'";） 下午6:53:03
	 */
	public String createSqlByColumnType(ColumnInfo columnInfo,
			Map<String, Object> data) {
		switch (columnInfo.getColumnType()) {
		case Types.BIGINT: {
		}
			break;
		case Types.BLOB: {
		}
			break;// 对应二进制大对象，即对象流花了的byte[]
		case Types.BOOLEAN: {
		}
			break;
		case Types.CHAR: {
		}
			break;
		case Types.CLOB: {
		}
			break;// 可以存储varchar都存储不下的数据量 对应String
		case Types.DATALINK: {
		}
			break;
		case Types.DATE: {
		}
			break;
		case Types.DECIMAL: {
		}
			break;
		case Types.DOUBLE: {
		}
			break;
		case Types.FLOAT: {
		}
			break;
		case Types.INTEGER: {
		}
			break;
		case Types.LONGNVARCHAR: {
		}
			break;
		case Types.LONGVARCHAR: {
		}
			break;
		case Types.NCHAR: {
		}
			break;
		case Types.NCLOB: {
		}
			break;
		case Types.NUMERIC: {
		}
			break;
		case Types.SMALLINT: {
		}
			break;
		case Types.TIME: {
		}
			break;
		case Types.TIMESTAMP: {
		}
			break;
		case Types.TINYINT: {
		}
			break;
		case Types.VARBINARY: {
		}
			break;
		case Types.VARCHAR: {
		}
			break;

		}
		return null;
	}

	/**
	 * 
	 * @param conn
	 * @param tablesName
	 * @param strategy
	 * @throws SQLException
	 *             anping TODO 向无外键约束的表格插入数据 下午6:48:43
	 */
	public void insertNoForignKeyTableData(Connection conn, String tablesName,
			InsertDataStrategy strategy) throws SQLException {
		List<ColumnInfo> columnInfos = TableHelper.getTableColumnInfo(conn,
				tablesName, false);
		// 通过数据生成策略获取了数据
		List<Map<String, Object>> datas = strategy.dataGeneration(columnInfos);

		String sql = "insert into  " + tablesName + "(";
		for (ColumnInfo columnInfo : columnInfos) {
			if (!columnInfo.isAutoIncreseAble()) {
				sql += columnInfo.getColumnName() + ",";
			}
		}
		sql = sql.substring(0, sql.length() - 1);

		sql = sql + ") values";
		// 在此开始拼接数据
		for (Map<String, Object> data : datas) {
			sql = sql + "(";
			for (ColumnInfo column : columnInfos) {
				if (!column.isAutoIncreseAble()) {
					sql += data.get(column.getColumnName()) + ",";
				}
			}
			sql = sql.substring(0, sql.length() - 1) + "),";
		}
		sql = sql.substring(0, sql.length() - 1);
		System.out.println(sql);
	}

}
