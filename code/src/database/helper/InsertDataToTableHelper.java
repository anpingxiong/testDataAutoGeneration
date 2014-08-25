package database.helper;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.Types;

import java.sql.SQLException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import database.insert.strategy.InsertDataStrategy;
import database.pojo.ColumnInfo;
import database.pojo.ForignKeyPo;
import java.util.HashMap;

public class InsertDataToTableHelper {

	/**
	 * @param conn
	 * @param tablesName
	 * @param strategy
	 * @param closeConnection
	 * @return boolean true为插入成功，false为失败 anping TODO插入既含有主键又是外键的列的表格
	 * 
	 *         在此不去判断是否它含有主外键的列，有外部调用是做判断 下午6:40:47
	 * @throws SQLException
	 */
	private boolean insertDataToHavePrimaryForignKeyTable(Connection conn,
			String tablesName, InsertDataStrategy strategy,
			boolean closeConnection) throws SQLException {
		List<String> primaryKeys = TableHelper.getOneTablePrimaryKeyInfo(
				tablesName, conn, false);// 获取主键信息
		Map<String, ForignKeyPo> forignKey = TableHelper.getOneTableForignInfo(
				tablesName, conn, false);// 获取外键信息
		Map<String, List<Object>> primaryKeysValue = new HashMap<String, List<Object>>(
				2); // 存放主外键的名称和存储在数据库中主外键的值
		// --获取表格中所有的主外键信息 开始
		for (String primary : primaryKeys) {
			if (forignKey.get(primary) != null) {
				primaryKeysValue.put(primary, TableHelper
						.getDataByTablePrimaryKeyName(forignKey.get(primary)
								.getForignKeyTable(), primary, conn, false));
				forignKey.remove(primary);// 清楚外键信息列中 即使主键又是外键的那个特殊列
			}
		}

		// --获取结束

		List<ColumnInfo> columnInfos = TableHelper.getTableColumnInfo(conn,
				tablesName, false);
		this.cleanForeignKeyColumn(forignKey, columnInfos);
		this.createBeforeBodySqlByColumns(tablesName, columnInfos);//
		List<Map<String, Object>> datas = strategy.dataGeneration(columnInfos);
		// 开始

		return true;
	}

	/**
	 * @param conn
	 * @param tablesName
	 * @param strategy
	 * @param closeConnection
	 *            true 为关闭 false 为不关闭
	 * @throws SQLException
	 *             anping TODO 更新表格的外键信息 下午6:48:43
	 *             通过获取外键列信息（其中包括外键对应的表格，以及外键对应表格的主键）
	 */
	public void updateDataForForignKeyTable(Connection conn, String tablesName,
			InsertDataStrategy strategy, boolean closeConnection)
			throws SQLException {
		// 获取该表格的所有主键,然后一个一个插入数据

		// 获取表格中的所有外键信息
		Map<String, ForignKeyPo> columnInfos = TableHelper
				.getOneTableForignInfo(tablesName, conn, false);
		// 需要更新所有的外键就需要知道对应的值
		Set<Entry<String, ForignKeyPo>> entrySets = columnInfos.entrySet();
		// 获取
		for (Entry<String, ForignKeyPo> entry : entrySets) {

		}

	}

	/**
	 * 
	 * @param conn
	 * @param tablesName
	 *            默认是不含有即是主键又是外键列的表格
	 * @param strategy
	 * @throws SQLException
	 *             anping TODO 向无外键约束的表格插入数据 下午6:48:43
	 *             ps(不处理含有即是主键又是外键的列，外键的值暂时为插入 null)
	 * 
	 * 
	 */
	public void insertDataToNoForignKeyTable(Connection conn,
			String tablesName, InsertDataStrategy strategy,
			boolean closeConnection) throws SQLException {

		// 获取一个表格的所有列信息
		List<ColumnInfo> columnInfos = TableHelper.getTableColumnInfo(conn,
				tablesName, false);

		// 获取到该表格的外键信息，目的是取出columnInfos里的外键，以便所有的外键都暂时为null
		Map<String, ForignKeyPo> forignColumnInfo = TableHelper
				.getOneTableForignInfo(tablesName, conn, false);

		// -----清楚外键列开始
		this.cleanForeignKeyColumn(forignColumnInfo, columnInfos);
		// -------清楚外键列结束

		// 如果表格里没有了列则直接退出
		if (columnInfos.size() == 0)
			return;

		// ------------生成insert sql开始
		// 通过数据生成策略获取了数据
		List<Map<String, Object>> datas = strategy.dataGeneration(columnInfos);
		String sql = this.createBeforeBodySqlByColumns(tablesName, columnInfos);
		sql = this.createAfterbodySqlByColumns(datas, columnInfos, sql);
		System.out.println(sql);
		// ----------生成sql结束
		Statement st = conn.createStatement();
		st.execute(sql);
		st.close();
		if (closeConnection)
			conn.close();

	}

	/*
	 * 判断 该表格 是否是即含有 既是主键又是外键的列
	 */
	private boolean judgeColumnIsPrimaryAndForeign(Connection conn,
			String tablesName) throws SQLException {
		// 获取到该表格的外键信息，目的是取出columnInfos里的外键，以便所有的外键都暂时为null
		Map<String, ForignKeyPo> forignColumnInfo = TableHelper
				.getOneTableForignInfo(tablesName, conn, false);

		// ---------获取主键信息
		List<String> primaryKeys = TableHelper.getOneTablePrimaryKeyInfo(
				tablesName, conn, false);
		boolean result = false;
		for (String primary : primaryKeys) {
			if (forignColumnInfo.get(primary) != null) {
				result = true;
				break;
			}
		}
		return result;
	}

	/*
	 * 清理掉外键
	 */
	private void cleanForeignKeyColumn(Map<String, ForignKeyPo> foreignKeys,
			List<ColumnInfo> columnInfos) {
		Set<Entry<String, ForignKeyPo>> entrySet = foreignKeys.entrySet();
		for (Entry<String, ForignKeyPo> entry : entrySet) {
			String columnName = entry.getKey();
			ColumnInfo columnInfo = null;
			if ((columnInfo = this.isListHaveColumn(columnInfos, columnName)) != null) {
				columnInfos.remove(columnInfo);
			}

		}
	}

	/*
	 * 修改掉测试数据 将 主外键信息修改为从数据库中获取的数据
	 */
	public  void modifyStrategyData(List<Map<String, Object>> datas,
			Map<String, List<Object>> primaryValue) {
		Set<Entry<String, List<Object>>> entrys = primaryValue.entrySet();
		
		
		for (Entry<String, List<Object>> entry : entrys) {
			int i =0 ;
			String key = entry.getKey();
			List<Object> values = entry.getValue();
		    
			
			for (Map<String, Object> mapsValus : datas) {
				Object  columnValue = values.get(i%values.size());
				 System.out.println(columnValue+"-------------");
			    mapsValus.put(key, columnValue);
			
			    i++;
			}
			
		}

	}

	private String createAfterbodySqlByColumns(List<Map<String, Object>> datas,
			List<ColumnInfo> columnInfos, String beforeBodySql) {
		// 在此开始拼接数据
		for (Map<String, Object> data : datas) {
			beforeBodySql = beforeBodySql + "(";
			for (ColumnInfo column : columnInfos) {
				if (!column.isAutoIncreseAble()) {
					beforeBodySql += this.createSqlByColumnType(column, data)
							+ ",";
				}
			}
			beforeBodySql = beforeBodySql.substring(0,
					beforeBodySql.length() - 1) + "),";
		}
		return beforeBodySql;
	}

	/*
	 * 拼接部分sql后段语句
	 */
	private String createBeforeBodySqlByColumns(String tablesName,
			List<ColumnInfo> columnInfos) {
		String sql = "insert into  " + tablesName + "(";
		for (ColumnInfo columnInfo : columnInfos) {
			if (!columnInfo.isAutoIncreseAble()) {
				sql += columnInfo.getColumnName() + ",";
			}
		}
		sql = sql.substring(0, sql.length() - 1);

		sql = sql + ") values";
		return sql;
	}

	private ColumnInfo isListHaveColumn(List<ColumnInfo> columnInfos,
			String name) {
		ColumnInfo flag = null;
		for (ColumnInfo column : columnInfos) {
			if (column.getColumnName().equals(name)) {
				flag = column;
				break;
			}
		}
		return flag;
	}

	/**
	 * 
	 * @param columnInfo
	 * @param data
	 * @return anping TODO 拼接sql语句片,通过列的属性，比如列属性为varchar 则拼接为
	 *         sql="'"+data.get(columnInfo.getName())+"'";下午6:53:03
	 */
	public String createSqlByColumnType(ColumnInfo columnInfo,
			Map<String, Object> data) {
		String sql = null;
		switch (columnInfo.getColumnType()) {
		case Types.BIGINT: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.BLOB: {
			// 简直醉了 ，不处理这个，谁回去存储文件到数据库中啊？？
			sql = "null";
		}
			break;// 对应二进制大对象，即对象流花了的byte[]
		case Types.BOOLEAN: {
			// 在数据库中存储的就是 true=1 , false=0
			sql = (boolean) data.get(columnInfo.getColumnName()) == true ? "1"
					: "0";
		}
			break;
		case Types.CHAR: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.CLOB: {
			// 简直又是醉了，不处理
			sql = "null";
		}
			break;// 可以存储varchar都存储不下的数据量 对应String
		case Types.DATE: {
			// 使用 java.sql.Date 需要加 ''
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.DECIMAL: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.DOUBLE: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.FLOAT: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.INTEGER: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.LONGNVARCHAR: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.LONGVARCHAR: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.NCHAR: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.NCLOB: {
			sql = "null";
		}
			break;
		case Types.NUMERIC: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.SMALLINT: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.TIME: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.TIMESTAMP: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		case Types.TINYINT: {
			sql = data.get(columnInfo.getColumnName()) + "";
		}
			break;
		case Types.VARBINARY: {
			sql = "null";
		}
			break;
		case Types.VARCHAR: {
			sql = "'" + data.get(columnInfo.getColumnName()) + "'";
		}
			break;
		default: {
			sql = "null";
		}

		}
		return sql;
	}

}
