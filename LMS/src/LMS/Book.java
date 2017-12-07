package LMS;

import java.sql.Date;

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
		 
	 }
	 public Book(String name) {
		 
	 }
	// public Enum Season = new Enum() {
}
