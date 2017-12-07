package LMS;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {
	public static DatabaseConnector conn = null;
	Connection db_connection;
	java.sql.Statement stmt;
	private DatabaseConnector(String url, String uname, String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			db_connection = DriverManager.getConnection(url, uname, pass);
			stmt = db_connection.createStatement();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch blockpas
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	public static DatabaseConnector getInstance() {
		if(conn == null) {
			conn = new DatabaseConnector("dbc:mysql://localhost:3306/Library", "root", "r%p4m");
		}
		return conn;
	}
	public ResultSet executeQuery(String query) {
		try {
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public boolean execute(String query) {
		try {
			return stmt.execute(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public void closeConnection() {
		try {
			db_connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
