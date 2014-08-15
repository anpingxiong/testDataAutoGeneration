package database.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import database.helper.DataBaseConnectionHelper;
import database.po.Database;

public class JDBCTest {

	static {
		Database.setDrivername("com.mysql.jdbc.Driver");
		Database.setUrl("jdbc:mysql://127.0.0.1:3306/blog");
		Database.setUsername("root");
		Database.setPassword("root");
	}

	// 获取所有的表格信息
	@Test
	public void testGetAllTable() throws SQLException {

		DataBaseConnectionHelper databaseHelper = new DataBaseConnectionHelper();

		Connection connection = databaseHelper.getConnection();
		ResultSet rs = connection.getMetaData().getTables(
				connection.getCatalog(), null, null, new String[] { "TABLE" });
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		for (int i = 1; i < numberOfColumns; i++) {
			System.out.println(rsmd.getColumnLabel(i));
		}
		while (rs.next()) {
			System.out.println(rs.getString("TABLE_NAME"));

		}

		rs.close();
		connection.close();
	}

	// 获取两个表格之间外键连接的信息
	@Test
	public void testGetTableForignKeyInfo() throws SQLException {
		DataBaseConnectionHelper databaseHelper = new DataBaseConnectionHelper();

		Connection connection = databaseHelper.getConnection();
		ResultSet rs = connection.getMetaData().getCrossReference(null, null,
				"t_user", null, null, "t_article_info");
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		for (int i = 1; i < numberOfColumns; i++) {
			// System.out.println(rsmd.getColumnLabel(i));

		}
		while (rs.next()) {
			System.out.println(rs.getString("PKTABLE_NAME"));
			System.out.println(rs.getString("PKCOLUMN_NAME"));
			System.out.println(rs.getString("FKTABLE_NAME"));
			System.out.println(rs.getString("FKCOLUMN_NAME"));
		}
		rs.close();
		connection.close();
	}

	// 获取单个表格的外键对应的信息
	@Test
	public void testGetTablePrimaryForignKeyInfo() throws SQLException {
		DataBaseConnectionHelper databaseHelper = new DataBaseConnectionHelper();

		Connection connection = databaseHelper.getConnection();
		ResultSet rs = connection.getMetaData().getImportedKeys(null, null,
				"t_article_info");
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();

		for (int i = 1; i < numberOfColumns; i++) {
			// System.out.println(rsmd.getColumnLabel(i));

		}
		while (rs.next()) {
			System.out.println("1111111111111111");
			// 外键表对应
			System.out.println(rs.getString("PKTABLE_NAME"));
			System.out.println(rs.getString("PKCOLUMN_NAME"));
			// 本表表对应
			System.out.println(rs.getString("FKTABLE_NAME"));
			System.out.println(rs.getString("FKCOLUMN_NAME"));
			// //System.out.println(rs.getString("FK_NAME"));
			// System.out.println(rs.getString("PK_NAME"));
		}
		rs.close();
		connection.close();
	}

}
