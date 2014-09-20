package database.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import database.data.strategy.InsertDataStrategy;
import database.helper.DataBaseConnectionHelper;
import database.helper.InsertDataToTableHelper;
import database.pojo.ColumnInfo;
import database.pojo.Database;

/**
 * 像
 * 
 * @author anping
 * 
 */
public class InsertDataToTableHelperTest {

	static {
		Database.setDrivername("com.mysql.jdbc.Driver");
		Database.setUrl("jdbc:mysql://127.0.0.1:3306/blog");
		Database.setUsername("root");
		Database.setPassword("root");
	}
	
	
	/**
	 * anping
	 * TODO 测试更新表格外键的方法
	 * 下午8:07:28
	 * @throws SQLException 
	 */
	@Test
	public void testUpdateDataForForignKeyTable() throws SQLException{
		
		InsertDataToTableHelper helper = new InsertDataToTableHelper();
		Connection conn = DataBaseConnectionHelper.getConnection();
		helper.updateDataForForignKeyTable(conn, "t_test_book",true);
		
	}
	
	@Test
	public void testInsertDataToHavePrimaryForignKeyTable2() throws SQLException{
		InsertDataToTableHelper helper = new InsertDataToTableHelper();
		Connection conn = DataBaseConnectionHelper.getConnection();
	     helper.insertDataToHavePrimaryForignKeyTable(conn, "t_test_book", new InsertDataStrategy() {
			 	@Override
			public List<Map<String, Object>> dataGeneration(
					List<ColumnInfo> tableColumnMessage) {
				// TODO Auto-generated method stub
				List<Map<String,Object>>  testDatas= new ArrayList<Map<String,Object>>(3);
				Map<String,Object>  datas =new HashMap<String,Object>(2);
				datas.put("id",0);
				datas.put("uid",100);
				
				Map<String,Object>  datas2 =new HashMap<String,Object>(2);
				datas2.put("id",1);
				datas2.put("uid",101);
				
				Map<String,Object>  datas3 =new HashMap<String,Object>(2);
				datas3.put("id",1);
				datas3.put("uid",102);
				
				testDatas.add(datas);
				testDatas.add(datas2);
				testDatas.add(datas3);
				return testDatas;
			}
		},true);
	}
	 /**
	  * 
	  * @throws SQLException
	  * anping
	  * TODO 测试插入含有即是主键又是外键的表格
	  * 下午8:05:30
	  */
	@Test
	public void testInsertDataToHavePrimaryForignKeyTable() throws SQLException{
		
		InsertDataToTableHelper helper = new InsertDataToTableHelper();
		Connection conn = DataBaseConnectionHelper.getConnection();
		helper.insertDataToHavePrimaryForignKeyTable(conn, "t_test_user", new InsertDataStrategy() {
			
			@Override
			public List<Map<String, Object>> dataGeneration(
					List<ColumnInfo> tableColumnMessage) {
				// TODO Auto-generated method stub
				List<Map<String,Object>>  testDatas= new ArrayList<Map<String,Object>>(3);
				Map<String,Object>  datas =new HashMap<String,Object>(2);
				datas.put("id",100);
				datas.put("name","anping");
				
				Map<String,Object>  datas2 =new HashMap<String,Object>(2);
				datas2.put("id",101);
				datas2.put("name","anping2");
				
				Map<String,Object>  datas3 =new HashMap<String,Object>(2);
				datas3.put("id",102);
				datas3.put("name","anping3");
				
				testDatas.add(datas);
				testDatas.add(datas2);
				testDatas.add(datas3);
				return testDatas;
			}
		},true);
	}

	@Test
	public void testInsertNoForignKeyTableData() throws SQLException {
		InsertDataToTableHelper helper = new InsertDataToTableHelper();
		Connection conn = DataBaseConnectionHelper.getConnection();

		helper.insertDataToNoForignKeyTable(conn, "t_friends",
				new InsertDataStrategy() {
					@Override
					public List<Map<String, Object>> dataGeneration(
							List<ColumnInfo> tableColumnMessage) {

						List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>(
								3);

						Map<String, Object> data1 = new HashMap<String, Object>(
								3);

						data1.put("version", 1);
						data1.put("theme", "hello");
						Map<String, Object> data2 = new HashMap<String, Object>(
								3);

						data2.put("version", 2);
						data2.put("theme", "word");
						datas.add(data1);
						datas.add(data2);
						return datas;
					}
				}, true);

	}

}
