package database.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import database.data.strategy.InsertDataStrategy;
import database.helper.DataBaseConnectionHelper;
import database.helper.InsertDataToTableHelper;
import database.helper.TableHelper;
import database.service.DBdataService;

public class DBDataServiceImpl implements
		DBdataService {
	/*
	 *方案：
	 * 1.首先插入那些没有外键的表格数据
	 * 2.再插入那些有外键的表格数据（该表格不包含主外键，以及不插入外键）
	 * 3.再插入那些既有主键又有外键列的表格信息(该表格包含了有既是主键又是外键的列，不插入外键)
	 * 4.更新所有表格的外键
	 * 5.结束
	 */
	@Override
	public void generationDataByDatabaseObject(InsertDataStrategy dataStratregy)  {
		// TODO Auto-generated method stub
		Connection conn = 	DataBaseConnectionHelper.getConnection();
		Map<String,List<String>> tables= null;
		try{
		   tables = TableHelper.sortTableByThreeType(conn, false);  //获取所有的表格数据
		}catch(Exception e){
			e.printStackTrace();
		}
		
		List<String>  noForeignKeyTable= tables.get("noForeign"); //获取无外键的所有表格
		List<String>  hasForeignKeyTable = tables.get("hasForeign"); //获取有外键的所有表格
		List<String>  hasForeign_primaryKeyTable= tables.get("hasPrimary_Foreign");//获取既有主键又有外键的所有表格
		
		InsertDataToTableHelper dataHelper = new  InsertDataToTableHelper();
		
		for(String tableName:noForeignKeyTable){  //先处理那些没有外键的表格
			try {
				dataHelper.insertDataToNoForignKeyTable(conn, tableName, dataStratregy, false);
			} catch (SQLException e) {
				 e.printStackTrace();
			}
		}
		
		for(String tableName:hasForeignKeyTable){  //在处理那些有外键的所有表格
			try {
				dataHelper.insertDataToNoForignKeyTable(conn, tableName, dataStratregy, false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(String tableName:hasForeign_primaryKeyTable){//最后处理那些既含有主键有含有外键列的表格
			try {
				dataHelper.insertDataToHavePrimaryForignKeyTable(conn, tableName, dataStratregy, false);
			} catch (SQLException e) {
				 	e.printStackTrace();
			}
		}
		
		for(String tableName:hasForeignKeyTable){ //更新所有有外键的表格
			try {
				dataHelper.updateDataForForignKeyTable(conn, tableName, false);
			} catch (SQLException e) {
				 e.printStackTrace();
			}
		}
		
		for(String tableName:hasForeign_primaryKeyTable){  //更新有外键的表格
			try {
				dataHelper.updateDataForForignKeyTable(conn, tableName, false);
			} catch (SQLException e) {
				 	e.printStackTrace();
			}
		}
		
		try {
			conn.close();//关闭连接
		} catch (SQLException e) {
	     	e.printStackTrace();
		}
	}

	@Override
	public void clearAllTestData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void rebuildTestData(InsertDataStrategy dataStratregy) {
		// TODO Auto-generated method stub
		this.clearAllTestData();
		this.rebuildTestData(dataStratregy);
	}

}
