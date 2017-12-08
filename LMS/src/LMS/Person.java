package LMS;

import java.sql.ResultSet;
import java.sql.SQLException;

import LMS.GlobalEnum.MemberType;

public class Person {
	String name;
	int mis;
	String dept;
	int journalsIssued;
	int techIssued;
	MemberType type;
	
	public Person(int mis) {
		this.mis = mis;
		ResultSet rs = DatabaseConnector.getInstance().executeQuery(String.format(SqlQueries.find_member, mis));
		try {
			if(rs.next()) {
				this.name = rs.getString(2);
				this.dept = rs.getString(3);
				this.journalsIssued = rs.getInt(6);
				this.techIssued = rs.getInt(5);
				this.type = MemberType.valueOf(rs.getString(4));
			}
			else {
				//log
			}
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void issueBook(Book b) {
		boolean isOrdinary = true;
		if(type == MemberType.PRIVILEGED) {
			isOrdinary = false;
		}
		Library.handleIssueRequest(this, b, isOrdinary);
	}
	
	public void returnBook(Book b) {
		boolean isOrdinary = true;
		if(type == MemberType.PRIVILEGED) {
			isOrdinary = false;
		}
		Library.handleReturnRequest(this, b, isOrdinary);
	}
	
	public void reportLostBook(Book b){
		Library.handleLostRequest(this, b);
	}
}
