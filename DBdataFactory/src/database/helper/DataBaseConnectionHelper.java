package database.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.pojo.Database;

public class DataBaseConnectionHelper {
	 
	 

	static {
		try {
			System.out.println(Database.getDrivername());
			Class.forName(Database.getDrivername());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(Database.getUrl(),
					Database.getUsername(), Database.getPassword());
		} catch (SQLException e) {
		  	e.printStackTrace();
		}
		return connection;
	}
	
	public static PreparedStatement getPreparStatement(String sql){
		PreparedStatement ps=null;
		try {
			ps= getConnection().prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	

}
