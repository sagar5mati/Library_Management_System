package LMS;

public interface SqlQueries {
	//Add member
	static final String add_member = "INSERT INTO member VALUES ('%d', '%s', '%s', '%d', '0', '0')";
	 
	//Delete member
	static final String del_member = "DELETE FROM member WHERE MIS=%d";

	//Update member
	static final String update_member_dept = "UPDATE member SET Dept=%s WHERE MIS=%d";
	static final String update_member_technicalIssued = "UPDATE member SET BooksIssued=%d WHERE MIS=%d";
	static final String update_member_journalIssued = "UPDATE member SET JournalsIssued=%d WHERE MIS=%d";
	static final String update_member_type = "UPDATE member SET Type=%d WHERE MIS=%d";

	//Find member by MIS
	static final String find_member = "SELECT * FROM member WHERE MIS=%d";

	//Add book
	static final String add_book = "INSERT INTO book VALUES ( NULL, %s, %s, %d, %d, %s)";

	//Delete book
	static final String del_book = "DELETE FROM book WHERE BID=%d";

	//Update book
	static final String update_book_status = "UPDATE book SET Status=%d WHERE BID=%d";
	static final String update_book_lastIssued = "UPDATE book SET LastIssued=%s WHERE BID=%d";

	//Find book by Name
	static final String find_book_byName = "SELECT * FROM book WHERE Name like '%s'";

	//Find book by Author
	static final String find_book_byAuthor = "SELECT * FROM book WHERE Author like '%s'";

	//Find book by Name, Author
	static final String find_book_bynameAuthor = "SELECT * FROM book WHERE Name like '%s' AND Author like '%s'";

	//Issue a book
	static final String issue_book = "INSERT INTO IsuedBooks VALUES(NULL, %d, %d, '%s', '0000-00-00')";
	
	//update the returned date
	static final String return_book = "UPDATE IssuedBooks SET ReturnDate=%s WHERE BID=%d";
	
	//update hte lost date
	static final String set_lostBook_date = return_book;
}
