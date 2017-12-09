package LMS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import LMS.GlobalEnum.BookStatus;
import LMS.GlobalEnum.BookType;
import LMS.GlobalEnum.MemberType;

public class Library {
	
	public Library() {
		
	}
	public static void main(String args[]) {
		Library l = new Library();
		l.addMember(111403050, "Abhijeet", "Computer", MemberType.PRIVILEGED);
	}
	
	public void addMember(int mis, String name, String dept, MemberType type) {
    	MyLogger.getInstance().log(Level.ALL, "request to add member " + mis);
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.add_member, mis, name, dept, type))) {
			MyLogger.getInstance().log(Level.ALL, "Successfully added member " + mis);
		}
		else {
			MyLogger.getInstance().log(Level.ALL, "Failed to add member " + mis);	
		}
	}
	
    public void deleteMember(int mis) {
    	MyLogger.getInstance().log(Level.ALL, "request to delete member with mis " + mis);
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.del_book, mis))) {
    		MyLogger.getInstance().log(Level.ALL, "Successfully deleted member with MIS " + mis);
    	}
    	else {	
    		MyLogger.getInstance().log(Level.ALL, "Failed to delete memberwith MIS " + mis);
		}
    }
    
    public void addBook(String name, String author, BookType type) {
    	MyLogger.getInstance().log(Level.ALL, "request to add " + name);
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.add_book, name, author, type, BookStatus.AVAILABLE, "0001-01-01"))) {
    		MyLogger.getInstance().log(Level.ALL, "Successfully added " + name);
    	}
    	else {
    		MyLogger.getInstance().log(Level.ALL, "Failed to add " + name);
    	}
    }
    
    public void deleteBook(Book b) {
    	MyLogger.getInstance().log(Level.ALL, "request to delete " + b.toString());
    	if(DatabaseConnector.getInstance().execute(String.format(SqlQueries.del_book, b.id))) {
    		MyLogger.getInstance().log(Level.ALL, "Successfully deleted " + b.toString());
    	}
    	else {	
    		MyLogger.getInstance().log(Level.ALL, "Failed to delete" + b.toString());
		}
    }
    
    public static boolean handleIssueRequest(Person p, Book b, boolean isOrdinary) {
    	MyLogger.getInstance().log(Level.ALL, p.toString() + "requested to issue " + b.toString());
		if(b.status != BookStatus.AVAILABLE) {
    		MyLogger.getInstance().log(Level.ALL, "Illegal call to function by " + p.toString() + " for " + b.toString());
    		return false;
    	}
    	if(isOrdinary) {
    		if(b.type == BookType.JOURNAL) {
    			if( p.journalsIssued < 1) {
    				fireQueriesForIssuingBook(p, b);
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " successfully issued " + b.toString());
    				return true;
    			}
    			else
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " failed to issue " + b.toString());
					return false;
    		}
    		else if(b.type == BookType.TECHNICAL) {
    			if(p.techIssued < 1) {
    				fireQueriesForIssuingBook(p, b);
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " successfully issued " + b.toString());
    				return true;
    			}
    			else
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " failed to issue " + b.toString());
					return false;
    		}
    	}
    	else {
    		if(b.type == BookType.JOURNAL) {
    			if( p.journalsIssued < 2) {
    				fireQueriesForIssuingBook(p, b);
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " successfully issued " + b.toString());
    				return true;
    			}
    			else
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " failed to issue " + b.toString());
					return false;
    		}
    		else if(b.type == BookType.TECHNICAL) {
    			if(p.techIssued < 2) {
    				fireQueriesForIssuingBook(p, b);
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " successfully issued " + b.toString());
    				return true;
    			}
    			else
    				MyLogger.getInstance().log(Level.ALL, p.toString() + " Failed to issue " + b.toString());
					return false;
    		}
    	}
    	MyLogger.getInstance().log(Level.ALL, p.toString() + " Failed to issue " + b.toString());
		return false;
    }
    
    public static void handleReturnRequest(Person p, Book b, boolean isOrdinary) {
    	MyLogger.getInstance().log(Level.ALL, p.toString() + " Requested to return " + b.toString());
		if(b.status != BookStatus.ISSUED) {
			MyLogger.getInstance().log(Level.ALL, "Illegal returning of " + b.toString() + " by " + p.toString());
    		return;
    	}
    	Date d = new Date();
    	long diff = Math.abs(d.getTime() - b.lastDateOfIssue.getTime());
    	long diffDays = diff / (24 * 60 * 60 * 1000);
    	if(isOrdinary) {
    		if(diffDays > 7) {
    			MyLogger.getInstance().log(Level.ALL," Fine imposed on " + p.toString() +" for late return of " + b.toString());
			}
    	}
    	else {
    		if(diffDays > 14) {
    			MyLogger.getInstance().log(Level.ALL, " Fine imposed on " + p.toString() +" for late return of " + b.toString());
			}
    	}
    	fireQueriesForReturingBook(p, b);
    }
    
    public static void handleLostRequest(Person p, Book b) {
    	MyLogger.getInstance().log(Level.ALL, p.toString() + " reports he/she lost request " + b.toString());
		String date = new SimpleDateFormat("yyyy-mm-dd").format(new Date());
    	DatabaseConnector.getInstance().execute(String.format(SqlQueries.set_lostBook_date, date, b.id));
    	DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_book_status, BookStatus.LOST, b.id));
    	if(b.type == BookType.JOURNAL)
			DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_member_journalIssued, --p.journalsIssued, p.mis));
		else if (b.type == BookType.TECHNICAL)
			DatabaseConnector.getInstance().execute(String.format(SqlQueries.update_member_technicalIssued, --p.techIssued, p.mis));
    	b.status = BookStatus.LOST;
    	MyLogger.getInstance().log(Level.ALL, " Book status changed to LOST");
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
