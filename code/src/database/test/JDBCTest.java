package database.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;

import org.junit.Test;

import database.helper.DataBaseConnectionHelper;
import database.pojo.Database;

public class JDBCTest {

	static {
		Database.setDrivername("com.mysql.jdbc.Driver");
		Database.setUrl("jdbc:mysql://127.0.0.1:3306/blog");
		Database.setUsername("root");
		Database.setPassword("root");
	}
   
	@Test
	public void testInsertData() throws SQLException{
		Connection connection = DataBaseConnectionHelper.getConnection();
		String sql ="insert into  t_data values('"+new Date(1,12,10)+"')";
		Statement st  =  connection.createStatement();
		 st.execute(sql);
		 st.close();
		 connection.close();
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

		Connection connection = DataBaseConnectionHelper.getConnection();
		ResultSet rs = connection.getMetaData().getPrimaryKeys(connection.getCatalog(), null, "t_article_info");
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();

		for (int i = 1; i < numberOfColumns; i++) {
			System.out.println(rsmd.getColumnLabel(i));

		}
		while (rs.next()) {
			System.out.println("1111111111111111");
			// 外键表对应
			System.out.println(rs.getString("COLUMN_NAME"));
		 
		}
		rs.close();
		connection.close();
	}

}
