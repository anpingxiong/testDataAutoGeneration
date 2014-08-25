package database.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import database.helper.DataBaseConnectionHelper;
import database.helper.InsertDataToTableHelper;
import database.helper.TableHelper;
import database.pojo.Database;
import database.pojo.ForignKeyPo;

/**
 * @author anping 测试database.helper下的类
 */
public class TableHelperTest {
	static {
		Database.setDrivername("com.mysql.jdbc.Driver");
		Database.setUrl("jdbc:mysql://127.0.0.1:3306/test");
		Database.setUsername("root");
		Database.setPassword("root");
	}
	
	
	/**
	 *  测试修改策略数据方法
	 * anping
	 * TODO
	 * 下午7:32:32
	 */
	@Test
	public void testModifyStrategyData(){
		InsertDataToTableHelper helper = new InsertDataToTableHelper();
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		Map<String,List<Object>> primaryValue= new HashMap<String,List<Object>>();
		
		Map<String,Object> data1 = new HashMap<String,Object>();
		data1.put("username","anping1");
		data1.put("password","12345data1");
		data1.put("age", 3);
		
		Map<String,Object> data2 = new HashMap<String,Object>();
		data2.put("username","anping2");
		data2.put("password","12345data2");
		data2.put("age", 3);
		
		Map<String,Object> data3 = new HashMap<String,Object>();
		data3.put("username","anping3");
		data3.put("password","12345data3");
		data3.put("age", 3);
		
		datas.add(data1);
		datas.add(data2);
		datas.add(data3);
		//----------------策略数据已准备
		List<Object> primary = new ArrayList<Object>();
		primary.add("anping4");
		primary.add("anping5");
		primary.add("anping6");
		
		primaryValue.put("username", primary);
		helper.modifyStrategyData(datas, primaryValue);
		
		for(Map<String,Object> dataForPrint:datas){
			Set<String> keys = dataForPrint.keySet();
			for(String string:keys){
				System.out.println(dataForPrint.get(string));
			}
		}
	}

	/**
	 * anping
	 * TODO  测试通过主键名来获取主键的数据
	 * 下午7:37:18
	 */
	@Test
	public void testGetDataByTablePrimaryKey() throws SQLException{
		Connection  conn = DataBaseConnectionHelper.getConnection();
		TableHelper helper = new TableHelper();
	 	List<Object>  datas =  helper.getDataByTablePrimaryKeyName("t_student", "id", conn,true);
	 	for(Object object:datas){
	 		System.out.println(object);
	 	}
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
