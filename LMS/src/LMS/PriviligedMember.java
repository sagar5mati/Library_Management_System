package LMS;

public class PriviligedMember extends Person{

	public PriviligedMember(int mis) {
		super(mis);
	}

	public void issueBook(Book b) {
		// TODO Auto-generated method stub
		Library.handleIssueRequest(this, b, false);
	}

	public void returnBook(Book b) {
		// TODO Auto-generated method stub
		Library.handleReturnRequest(this, b, false);
	}

	public void reportLostBook(Book b) {
		// TODO Auto-generated method stub
		Library.handleLostRequest(this, b);
	}
}
