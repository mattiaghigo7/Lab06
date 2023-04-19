package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class ConnectDB {
	
	// check user e password
//	static private final String jdbcUrl = "jdbc:mysql://localhost/meteo?user=root&password=root";
	

//	public static Connection getConnection() {
//
//		try {
//				Connection connection = DriverManager.getConnection(jdbcUrl);
//				return connection;
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//			throw new RuntimeException("Cannot get a connection " + jdbcUrl, e);
//		}
//	}

	static HikariDataSource dataSource;
	
	public static Connection getConnection() throws SQLException{
		if(dataSource == null) {
			dataSource = new HikariDataSource();
			dataSource.setJdbcUrl("jdbc:mariadb://localhost/meteo");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
		}
		
		return dataSource.getConnection();
		
	}
}
