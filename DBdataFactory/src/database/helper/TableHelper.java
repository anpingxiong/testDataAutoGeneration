package database.helper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Statement;

import database.pojo.ColumnInfo;
import database.pojo.ForignKeyPo;

/**
 * 
 * @author anping 数据库表格操作类
 */
public class TableHelper {
	/**
	 * @param tables
	 * @return anping TODO 将数据库表格分为三种返回， 分别是无外键型 noForeign
	 *         ，有外键型（不包含既是外键又是主键的列）hasForeign ，既有外键又是主键的列 hasPrimary_Foreign
	 *         下午2:56:18
	 * @throws SQLException
	 */
	public static Map<String, List<String>> sortTableByThreeType(
			Connection conn, boolean closeConnection) throws SQLException {
		List<String> tablesNames = getAllTableName(conn, false);
		Map<String, List<String>> tableContext = new HashMap<String, List<String>>(
				3);
		tableContext.put("noForeign", new ArrayList<String>(10));
		tableContext.put("hasForeign", new ArrayList<String>(10));
		tableContext.put("hasPrimary_Foreign", new ArrayList<String>(10));
		Lable:
		for (String tableName : tablesNames) {
			 Map<String, ForignKeyPo> forignKeys = TableHelper
					.getOneTableForignInfo(tableName, conn, false);

			if (forignKeys.size() == 0) {// 如果外键等于0 则表示没有外键
				List<String> noForeign = tableContext.get("noForeign");
				noForeign.add(tableName);
				continue;
			}

			List<String> primarys = getOneTablePrimaryKeyInfo(tableName, conn,
					false);// 获取主键

			for(String primary:primarys){
				if(forignKeys.get(primary)!=null){  //如果主键又是外键则put hasPrimary_Foreign
					List<String> hasPrimary_Foreign = tableContext.get("hasPrimary_Foreign");
					hasPrimary_Foreign.add(tableName);
					continue Lable;
				} 
			}
			
			List<String> hasForeign = tableContext.get("hasForeign");
			hasForeign.add(tableName);
	
		}
		if(closeConnection)
			conn.close();
			
		return tableContext;
	}

	/**
	 * 
	 * @return anping TODO 获取一个表格中的所有数据 下午8:44:59
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> getDataByTableName(
			String tableName, Connection conn, boolean closeConnection)
			throws SQLException {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>(50);
		String sql = "select * from  " + tableName + ";";

		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		System.out.println(sql);
		ResultSetMetaData resultSetMeta = rs.getMetaData();

		while (rs.next()) {
			Map<String, Object> data = new HashMap<String, Object>();
			for (int i = 1; i <= resultSetMeta.getColumnCount(); i++) {
				data.put(resultSetMeta.getColumnLabel(i),
						rs.getObject(resultSetMeta.getColumnLabel(i)));

			}
			datas.add(data);
		}

		rs.close();
		statement.close();
		if (closeConnection)
			conn.close();
		return datas;
	}

	/**
	 * 
	 * @return anping sql = select primaryKey from tableName ; TODO获取主键数据
	 *         下午7:26:55
	 */
	public static List<Object> getDataByTablePrimaryKeyName(String tableName,
			String primaryKey, Connection conn, boolean closeConnection)
			throws SQLException {
		List<Object> primaryKeys = new ArrayList<Object>(15);
		Statement statement = conn.createStatement();
		String sql = "select " + primaryKey + " from " + tableName;
		System.out.println(sql);
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			primaryKeys.add(rs.getObject(primaryKey));
		}
		rs.close();
		statement.close();
		if (closeConnection) {
			conn.close();
		}
		return primaryKeys;
	}

	/**
	 * 
	 * @param tableName
	 * @param conn
	 * @param closeConnection
	 * @return
	 * @throws SQLException
	 *             anping TODO 获取一个表格的所有的主键信息 下午9:00:42
	 */
	public static List<String> getOneTablePrimaryKeyInfo(String tableName,
			Connection conn, boolean closeConnection) throws SQLException {

		List<String> primaryKeys = new ArrayList<String>(2);
		ResultSet rs = conn.getMetaData().getPrimaryKeys(conn.getCatalog(),
				null, tableName);

		while (rs.next()) {
			primaryKeys.add(rs.getString("COLUMN_NAME"));

		}
		rs.close();
		if (closeConnection)
			conn.close();
		return primaryKeys;
	}

	/**
	 * @param conn
	 * @param closeConnection
	 * @param tableName
	 * @return anping TODO 获取指定表格的所有列的信息 上午9:41:56 使用map的原因是获取指定的列名速度快
	 * @throws SQLException
	 */
	public static List<ColumnInfo> getTableColumnInfo(Connection conn,
			String tableName, boolean closeConnection) throws SQLException {
		List<ColumnInfo> columnInfos = new ArrayList<ColumnInfo>(10);
		ResultSet columns = conn.getMetaData().getColumns(conn.getCatalog(),
				null, tableName, null);
		while (columns.next()) {
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnName(columns.getString("COLUMN_NAME"));
			columnInfo.setColumnType(Integer.parseInt(columns
					.getString("DATA_TYPE")));
			columnInfo.setNullAble("YES".equals(columns
					.getString("IS_NULLABLE")) ? true : false);
			columnInfo.setAutoIncreseAble("YES".equals(columns
					.getString("IS_AUTOINCREMENT")) ? true : false);
			columnInfo
					.setCharMaxLength(columns.getString("CHAR_OCTET_LENGTH") == null ? 0
							: Integer.parseInt(columns
									.getString("CHAR_OCTET_LENGTH")));
			columnInfos.add(columnInfo);
		}
		return columnInfos;
	}

	/**
	 * @param conn
	 *            数据库连接对象
	 * @param closeConnection是否关闭链接
	 *            true 为关闭 false为不关闭
	 * @return Map<String,Map<String,ForignKeyPo>>
	 *         如:Map<TableName,Map<ForignColumnName,ForignKeyPo>> anping TODO
	 *         为了获取某个表格中外键所对应的表格的列信息 上午8:50:18
	 * @throws SQLException
	 */
	public static Map<String, Map<String, ForignKeyPo>> getTableForginInfo(
			Connection conn, boolean closeConnection) throws SQLException {

		Map<String, Map<String, ForignKeyPo>> datas = new HashMap<String, Map<String, ForignKeyPo>>(
				20);
		// 获取所有的表格
		List<String> tables = TableHelper.getAllTableName(conn, false);

		// 循环所有的表格获取所有有关的外键信息
		for (String table : tables) {
			Map<String, ForignKeyPo> tableForignKey = TableHelper
					.getOneTableForignInfo(table, conn, false);
			datas.put(table, tableForignKey);
		}

		if (closeConnection)
			conn.close();
		return datas;

	}

	/**
	 * 
	 * @param tableName
	 * @param conn
	 * @param datas
	 * @param closeConnection
	 *            true则关闭连接 false则不关闭连接 将表格的信息存储在map中
	 * @throws SQLException
	 *             anping TODO获取一个表格的外键信息 上午10:17:59
	 */
	public static Map<String, ForignKeyPo> getOneTableForignInfo(
			String tableName, Connection conn, boolean closeConnection)
			throws SQLException {
		Map<String, ForignKeyPo> forignKeys = new HashMap<String, ForignKeyPo>(
				2);
		System.out.println(conn);
		// 获取该表格的外键对应信息
		ResultSet rs = conn.getMetaData()
				.getImportedKeys(null, null, tableName);

		while (rs.next()) {
			ForignKeyPo forignKeyPo = new ForignKeyPo();

			forignKeyPo.setForignKeyName(rs.getString("PKCOLUMN_NAME"));
			forignKeyPo.setForignKeyTable(rs.getString("PKTABLE_NAME"));
			forignKeys.put(rs.getString("FKCOLUMN_NAME"), forignKeyPo);
		}
		rs.close();
		if (closeConnection)
			conn.close();
		return forignKeys;
	}

	/**
	 * 
	 * @param conn
	 *            数据库链接对象
	 * @param closeConnection
	 *            是否关闭此处链接 true为关闭 false为不关闭
	 * @return
	 * @throws SQLException
	 *             anping TODO 获取数据库中的所有表格 上午8:43:52
	 */
	public static List<String> getAllTableName(Connection conn,
			boolean closeConnection) throws SQLException {

		List<String> tablesName = new ArrayList<String>(10);

		ResultSet rs = conn.getMetaData().getTables(conn.getCatalog(), null,
				null, new String[] { "TABLE" });

		while (rs.next()) {
			tablesName.add(rs.getString("TABLE_NAME"));

		}
		rs.close();

		if (closeConnection) {
			conn.close();
		}
		return tablesName;
	}

}
