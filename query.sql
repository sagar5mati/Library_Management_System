Add member
INSERT INTO member VALUES ( $MIS, $Name, $Dept, $Type, 0, 0);
 
Delete member
DELETE FROM member WHERE MIS=$MIS;

Update member
UPDATE member SET Dept=$Dept WHERE MIS=$MIS;
UPDATE member SET BooksIssued=$BooksIssued WHERE MIS=$MIS;
UPDATE member SET JournalsIssued=$JournalIssued WHERE MIS=$MIS;
UPDATE member SET Type=$Type WHERE MIS=$MIS;

Find member by MIS
SELECT * FROM member WHERE MIS=$MIS;

Add book
INSERT INTO book VALUES ( NULL, $Name, $Author, $Type, 'AVAILABLE', $LastIssued);

Delete book
DELETE FROM book WHERE BID=$BID;

Update book
UPDATE book SET Status=$Status WHERE BID=$BID;
UPDATE book SET LastIssued=$LastIssued WHERE BID=$BID;

Find book by Name
SELECT * FROM book WHERE Name like '$Name';

Find book by Author
SELECT * FROM book WHERE Author like '$Author';

Find book by Name, Author
SELECT * FROM book WHERE Name like '$Name' AND '$Author';

Create event archive_book

Set book status to LOST
UPDATE book SET Status='LOST' WHERE BID=$BID;

Set book status to AVAILABLE
UPDATE book SET Status=$Status WHERE BID=$BID;
Set book status to ISSUED
