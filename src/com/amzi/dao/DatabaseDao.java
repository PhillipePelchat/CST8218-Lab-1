package com.amzi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseDao {
	private static String url = "jdbc:mysql://localhost:3306/";
	private static String dbName = "mealreview";
	private static String driver = "com.mysql.jdbc.Driver";
	private static String userName = "root";
	private static String password = "password";
	private static Connection conn = null;
	
	public DatabaseDao() {
		try {
			if (conn == null || conn.isValid(0) || conn.isClosed()) {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url + dbName, userName, password);
			}
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
	public boolean closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
				return true;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return false;
	} 
		
	public Connection getConnection() {
		return conn;
	}
}

