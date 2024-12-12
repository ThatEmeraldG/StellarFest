package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnect {
//	1. Initialize Attributes relevan untuk connect ke Database SQL
	private final String USERNAME = "root"; 
	private final String PASSWORD = ""; 
	private final String DATABASE = "stellarfest"; 
	private final String HOST = "localhost:3306"; 
	private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE); 
	
//	2. Initialize Objects
	public ResultSet rs; // menampung data hasil query select
	public ResultSetMetaData rsm; // menampung meta data dari hasil query
	private Connection con; 
	private Statement st; // menampung statement utk execute query SQL (select, insert, update) 
	
//	3. Constructor
	private SQLConnect() {
//		set driver MySQL ke dalam memory 
//		driver -> jembatan antara program kt dengan db nya 
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD); 
			st = con.createStatement(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
//	4. Implement Singleton
	private static SQLConnect connect = null; 
	public static SQLConnect getInstance() {
		if (connect == null) {
			connect = new SQLConnect(); 
		}
		return connect; 
	}
	
//	5. SELECT DATA -> simpan di variable ResultSet (rs)
	public ResultSet execQuery(String Query) {
		try {
			rs = st.executeQuery(Query);
			rsm = rs.getMetaData(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return rs; 
	}
	
//	6. UPDATE / MANIPULASI DATA (CREATE, UPDATE, DELETE)
	public void execUpdate(String Query) {
		try {
			st.executeUpdate(Query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
//	7. Prepared statement 
	public PreparedStatement preparedStatement(String query) {
		PreparedStatement ps = null; 
		
		try {
			ps = con.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return ps; 
	}
}
