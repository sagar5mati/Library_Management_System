package LMS;

import java.sql.ResultSet;

public abstract class Person {
	final String name;
	final int mis;
	String dept;
	int journalsIssued;
	int techIssued;
	
	public Person(int mis) {
		this.mis = mis;
		ResultSet rs = DatabaseConnector.getInstance().executeQuery(/*put a query here*/"");
		this.name = rs.getString(arg0);
		this.dept = rs.getString(arg0);
		this.journalsIssued = rs.getInt(arg0);
		this.techIssued = rs.getInt(arg0);
	}
	public abstract void issueBook(Book b);
	
	public abstract void returnBook(Book b);
	
	public abstract void reportLostBook(Book b);
	}
