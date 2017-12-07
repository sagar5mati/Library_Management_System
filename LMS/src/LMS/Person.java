package LMS;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Person {
	String name;
	int mis;
	int age;
	String dept;
	int journalsIssued;
	int techIssued;
	
	public Person(int mis) {
		this.mis = mis;
		ResultSet rs = DatabaseConnector.getInstance().executeQuery(/*put a query here*/"");
		try {
			this.name = rs.getString(1);
			this.dept = rs.getString(2);
			this.age = rs.getInt(3);
			this.journalsIssued = rs.getInt(6);
			this.techIssued = rs.getInt(5);
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public abstract void issueBook(Book b);
	
	public abstract void returnBook(Book b);
	
	public abstract void reportLostBook(Book b);
	}
