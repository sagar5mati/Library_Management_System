DROP DATABASE library;
CREATE DATABASE library;

CREATE TABLE member (
	MIS INT(10) UNSIGNED NOT NULL PRIMARY KEY,
	Name VARCHAR(255) NOT NULL,
	Dept VARCHAR(255) NOT NULL,
	Age TINYINT UNSIGNED NOT NULL,
	Type ENUM('ORDINARY', 'PRIVILEGED') NOT NULL,
	BooksIssued TINYINT UNSIGNED NOT NULL,
	JournalsIssued TINYINT UNSIGNED NOT NULL);

CREATE TABLE book (
	BID INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	Name VARCHAR(255) NOT NULL,
	Author VARCHAR(255) NOT NULL,
	Type ENUM('BOOK', 'JOURNAL') NOT NULL,
	Status ENUM('AVAILABLE', 'ISSUED', 'ARCHIVED', 'LOST') NOT NULL,
	LastIssued DATE NOT NULL);

CREATE TABLE IssuedBooks (
	IID INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	MIS INT(10) UNSIGNED NOT NULL,
	BID INT UNSIGNED NOT NULL,
	IssueDate DATE NOT NULL,
	FOREIGN KEY (MIS) REFERENCES member(MIS) ON DELETE CASCADE,
	FOREIGN KEY (BID) REFERENCES book(BID) ON DELETE CASCADE);
