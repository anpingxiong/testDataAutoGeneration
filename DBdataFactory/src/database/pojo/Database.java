package database.pojo;

/**
 * 数据库参数类
 * @author anping
 *
 */
public class Database {
	
	private static String drivername;
	private static String url;
	private static String username;
	private static String password;
	
	public static String getDrivername() {
		return drivername;
	}
	public static void setDrivername(String drivername) {
		Database.drivername = drivername;
	}
	public static String getUrl() {
		return url;
	}
	public static void setUrl(String url) {
		Database.url = url;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		Database.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		Database.password = password;
	}
	
}
