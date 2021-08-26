package tr.com.kurban.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ObjectHelper {

	private static String userName="root";
	private static String password="1234";
	private static String url="jdbc:mysql://localhost:3306/veritabani";
	private static String driver="com.mysql.jdbc.Driver";
	
	static {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	protected static Connection getConnection() {
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, userName, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
}
