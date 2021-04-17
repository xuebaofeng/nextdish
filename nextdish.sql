drop table payment;

CREATE TABLE payment (
	date TEXT NOT NULL,
	note TEXT NOT NULL,
	amount REAL NOT NULL,
	status TEXT NOT NULL,
	PRIMARY KEY(date, note)
);

--INSERT INTO payment (date, note, amount, status) VALUES('', '', 0, '');

--DELETE from payment ;

SELECT * from payment p ;

SELECT sum(amount) from payment p ;
SELECT sum(amount) from payment p where p.status ='Paid';
SELECT sum(amount) from payment p where p.status ='Paid' and p.note <> 'Customer Tips';
