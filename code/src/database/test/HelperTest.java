package database.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import database.helper.DataBaseConnectionHelper;
import database.helper.TableHelper;
import database.po.Database;
import database.po.ForignKeyPo;

/**
 * @author anping 测试database.helper下的类
 */
public class HelperTest {
	static {
		Database.setDrivername("com.mysql.jdbc.Driver");
		Database.setUrl("jdbc:mysql://127.0.0.1:3306/blog");
		Database.setUsername("root");
		Database.setPassword("root");
	}

	
	
	@Test
	public void testGetOneTablePrimaryKeyInfo() throws SQLException{
		Connection conn = DataBaseConnectionHelper.getConnection();
		TableHelper tableHelper = new TableHelper();
		System.out.println(tableHelper.getOneTablePrimaryKeyInfo("t_user", conn,true));
		
	}
	
	/**
	 * 测试TableHelper类的获取所有列的信息
	 * 
	 * anping
	 * TODO
	 * 上午11:42:28
	 * @throws SQLException 
	 */
	
	
	@Test
	public void testGetAllColumnInfo() throws SQLException{
		Connection conn = DataBaseConnectionHelper.getConnection();
		TableHelper tableHelper = new TableHelper();
		tableHelper.getTableColumnInfo(conn,"t_user", true);
		
		
	}
	
	
	
	/**
	 * anping TODO 测试获取所有表格的外键信息 下午3:53:24
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetTableForginInfo() throws SQLException {
		Connection conn = DataBaseConnectionHelper.getConnection();
		TableHelper tableHelper = new TableHelper();
		Map<String, Map<String, ForignKeyPo>> datas = tableHelper
				.getTableForginInfo(conn, true);
		Set<Entry<String, Map<String, ForignKeyPo>>> entrys = datas.entrySet();
		Iterator<Entry<String, Map<String, ForignKeyPo>>> iterator = entrys
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, Map<String, ForignKeyPo>> entry = iterator.next();
			System.out.println(entry.getKey());
			Map<String, ForignKeyPo> forignKeyPo = entry.getValue();

		}

	}

	/**
	 * anping TODO测试获取所有的表格名 下午3:47:29
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetAllTable() throws SQLException {
		Connection connection = DataBaseConnectionHelper.getConnection();
		TableHelper tableHelper = new TableHelper();
		List<String> tables = tableHelper.getAllTableName(connection, true);
		System.out.println("------------");
		for (String tableName : tables) {

			System.out.println(tableName);
		}
	}
}
