package com.wipro.sales.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	public static Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Connection conn;
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "salesdb", "wipro");
		return conn;
	}
}