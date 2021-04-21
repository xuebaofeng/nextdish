drop table payment;

CREATE TABLE payment (
	date TEXT NOT NULL,
	note TEXT NOT NULL,
	amount REAL NOT NULL,
	status TEXT NOT NULL,
	PRIMARY KEY(date, note)
);
drop table payment_sms;
CREATE TABLE payment_sms (
	amount REAL,
	date TEXT
);

--DELETE from payment ;

SELECT * from payment p ;

SELECT sum(amount) from payment p ;
SELECT sum(amount) from payment p where p.status ='Paid';
SELECT sum(amount) from payment p where p.status ='Paid' and p.note <> 'Customer Tips';

INSERT INTO payment_sms (amount, date) VALUES(449.50, '2021-04-21');
INSERT INTO payment_sms (amount, date) VALUES(361.25, '2021-04-14');
INSERT INTO payment_sms (amount, date) VALUES(416.81, '2021-04-06');
INSERT INTO payment_sms (amount, date) VALUES(209.75, '2021-03-30');

select sum(amount) from payment_sms;
