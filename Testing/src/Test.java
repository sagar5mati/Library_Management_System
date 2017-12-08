import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Test {
	static Connection db_connection = null;
	static Statement stmt = null;
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			db_connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "r%p4m");
			stmt = db_connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from member");
			int mis = rs.getInt(0);
			System.out.println(mis);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
