package LMS;

public class OrdinaryMember extends Person{

	public OrdinaryMember(int mis) {
		super(mis);
	}

	public void issueBook(Book b) {
		// TODO Auto-generated method stub
		Library.handleIssueRequest(this, b,true);
	}

	public void returnBook(Book b) {
		// TODO Auto-generated method stub
		Library.handleReturnRequest(this, b, true);
	}

	public void reportLostBook(Book b) {
		// TODO Auto-generated method stub
		Library.handleLostRequest(this, b);
	}

}
