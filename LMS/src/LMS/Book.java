package LMS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import LMS.GlobalEnum.BookStatus;
import LMS.GlobalEnum.BookType;

public class Book {
	 int id;
	 String name;
	 String author;
	 Date lastDateOfIssue;
	 BookStatus status;
	 BookType type;
	 public Book(String name, String author) {
		 String date;
		 ResultSet rs = DatabaseConnector.getInstance().executeQuery(String.format(SqlQueries.find_book_bynameAuthor, name, author));
		 try {
			 if(rs.next()) {
				 date = rs.getString(6);
			 	lastDateOfIssue = new SimpleDateFormat("yyyy-mm-dd").parse(date);
			 	status = BookStatus.valueOf(rs.getString(5));
			 	type = BookType.valueOf(rs.getString(4));
			 	this.id = rs.getInt(1);
			 	this.name = name;
			 	this.author = author;
			 }
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public Book(String name) {
		 String date;
		 ResultSet rs = DatabaseConnector.getInstance().executeQuery(String.format(SqlQueries.find_book_byName, name));
		 try {
			 if(rs.next()) {
				 date = rs.getString(6);
			 	lastDateOfIssue = new SimpleDateFormat("yyyy-mm-dd").parse(date);
			 	status = BookStatus.valueOf(rs.getString(5));
			 	type = BookType.valueOf(rs.getString(4));
			 	this.id = rs.getInt(1);
			 	this.name = name;
			 	this.author = author;
			 }
			 else {
				 //log
			 }
		 } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	// public Enum Season = new Enum() {
}
