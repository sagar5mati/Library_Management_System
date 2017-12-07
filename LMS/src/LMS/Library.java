package LMS;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import LMS.GlobalEnum.BookStatus;
import LMS.GlobalEnum.BookType;
import LMS.GlobalEnum.MemberType;

public class Library {
	
	public void addMember(Person p, MemberType type) {
		if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.add_member, p.mis, p.name, p.dept, p.age, type))) {
			//success//log
		}
		else {
			//failure//log
		}
	}
	
    public void deleteMember(int mis) {
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.del_book, mis))) {
    		//success//log
    	}
    	else {
    		//failure//log
    	}
    }
    
    public void addBook(Book b) {
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.add_book, b.name, b.author, b.type, b.status))) {
    		//log
    	}
    	else {
    		//log
    	}
    }
    
    public void deleteBook(Book b) {
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.del_book, b.id))) {
    		//log
    	}
    	else {
    		//log
    	}
    }
    
    public static boolean handleIssueRequest(Person p, Book b, boolean isOrdinary) {
    	if(b.status != BookStatus.AVAILABLE) {
    		//log
    		return false;
    	}
    	if(isOrdinary) {
    		if(b.type == BookType.JOURNAL) {
    			if( p.journalsIssued < 1) {
    				fireQueriesForIssuingBook(p, b);
    				//log
    				return true;
    			}
    			else
    				//log
    				return false;
    		}
    		else if(b.type == BookType.TECHNICAL) {
    			if(p.techIssued < 1) {
    				fireQueriesForIssuingBook(p, b);
    				//log
    				return true;
    			}
    			else
    				//log
    				return false;
    		}
    	}
    	else {
    		if(b.type == BookType.JOURNAL) {
    			if( p.journalsIssued < 2) {
    				fireQueriesForIssuingBook(p, b);
    				//log
    				return true;
    			}
    			else
    				//log
    				return false;
    		}
    		else if(b.type == BookType.TECHNICAL) {
    			if(p.techIssued < 2) {
    				fireQueriesForIssuingBook(p, b);
    				//log
    				return true;
    			}
    			else
    				//log
    				return false;
    		}
    	}
    	//log
    	return false;
    }
    
    public static void handleReturnRequest(Person p, Book b, boolean isOrdinary) {
    	if(b.status != BookStatus.ISSUED) {
    		//log
    		return;
    	}
    	Date d = new Date();
    	long diff = Math.abs(d.getTime() - b.lastDateOfIssue.getTime());
    	long diffDays = diff / (24 * 60 * 60 * 1000);
    	if(isOrdinary) {
    		if(diffDays > 7) {
    			//impose fine
    		}
    	}
    	else {
    		if(diffDays > 14) {
    			//impose fine
    		}
    	}
    	fireQueriesForReturingBook(p, b);
    }
    
    public static void handleLostRequest(Person p, Book b) {
    	//log impose fine
    	String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
    	DatabaseConnector.getInstance().execute(String.format(SqlQueries.set_lostBook_date, date, b.id));
    	DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_book_status, BookStatus.LOST, b.id));
    	b.status = BookStatus.LOST;
    }
    
    private static void fireQueriesForIssuingBook(Person p, Book b) {
		String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
		DatabaseConnector.getInstance().execute(String.format(SqlQueries.issue_book, p.mis, b.id, date));
		DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_book_status, BookStatus.ISSUED, b.id));
		DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_book_lastIssued, date,b.id));
		if(b.type == BookType.JOURNAL)
			DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_member_journalIssued, ++p.journalsIssued, p.mis));
		else if (b.type == BookType.TECHNICAL)
			DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_member_technicalIssued, ++p.techIssued, p.mis));
		b.status = BookStatus.ISSUED;
		try {
			b.lastDateOfIssue = new SimpleDateFormat("yyyy-mm-dd").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static void fireQueriesForReturingBook(Person p, Book b) {
		String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());;
		DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_book_status, BookStatus.AVAILABLE, b.id));
		DatabaseConnector.getInstance().execute(String.format(SqlQueries.return_book, date,b.id));
		if(b.type == BookType.JOURNAL)
			DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_member_journalIssued, --p.journalsIssued, p.mis));
		else if (b.type == BookType.TECHNICAL)
			DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_member_technicalIssued, --p.techIssued, p.mis));
		b.status = BookStatus.AVAILABLE;
    }
}
