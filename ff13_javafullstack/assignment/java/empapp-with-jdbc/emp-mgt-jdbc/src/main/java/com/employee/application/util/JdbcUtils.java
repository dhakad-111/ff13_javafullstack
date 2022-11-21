package com.employee.application.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/jdbc_training";
	private static String userName = "root";
	private static String password = "root";
	
	private static final Connection CONNECTION = builConnection();

	private JdbcUtils() {
	}

	public static Connection builConnection() {
		Connection connection = null;
		try {
			Class.forName(driver);
			try {
				connection = DriverManager.getConnection(url, userName, password);
				return connection;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}


	
	public static Connection getConnection() {
		return CONNECTION;
	}
	
	public static void closeConnection() throws SQLException {
		getConnection().close();
	}

}
