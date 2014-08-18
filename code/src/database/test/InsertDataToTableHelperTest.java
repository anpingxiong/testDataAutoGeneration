package database.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import database.helper.DataBaseConnectionHelper;
import database.helper.InsertDataToTableHelper;
import database.insert.strategy.InsertDataStrategy;
import database.po.ColumnInfo;
import database.po.Database;

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
	@Test
	public void testInsertNoForignKeyTableData() throws SQLException {
		InsertDataToTableHelper helper = new InsertDataToTableHelper();
		Connection conn = DataBaseConnectionHelper.getConnection();

		helper.insertNoForignKeyTableData(conn, "t_theme_type",
				new InsertDataStrategy() {
					@Override
					public List<Map<String, Object>> dataGeneration(
							List<ColumnInfo> tableColumnMessage) {

						List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>(
								3);

						Map<String, Object> data1 = new HashMap<String, Object>(
								3);

						data1.put("version", 1);
						data1.put("theme", "fuck you");
						Map<String, Object> data2= new HashMap<String, Object>(
								3);

						data2.put("version",2 );
						data2.put("theme", "fuck you");
						datas.add(data1);
						datas.add(data2);
						return datas;
					}
				});

		
	}

}
