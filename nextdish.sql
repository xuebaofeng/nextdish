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

CREATE TABLE payment_last (
	date TEXT
);

--DELETE from payment ;

SELECT * from payment p where p.date > (select max(date) from payment_last ps)  order by date desc;

SELECT sum(amount) from payment p where p.date > (select max(date) from payment_last ps);
SELECT sum(amount) from payment p where p.status ='Paid' and p.date > (select max(date) from payment_last ps);
SELECT sum(amount) from payment p where p.status ='Paid' and p.note <> 'Customer Tips' and p.date > (select max(date) from payment_last ps);

select * from payment_sms ps;
select * from payment_last ps;
select * from payment ps;

INSERT INTO payment_last (date) VALUES('20210427');

INSERT INTO payment_sms (amount, date) VALUES(391.32, '20210505');
INSERT INTO payment_sms (amount, date) VALUES(187.33, '20210427');
INSERT INTO payment_sms (amount, date) VALUES(449.50, '20210421');
INSERT INTO payment_sms (amount, date) VALUES(361.25, '20210414');
INSERT INTO payment_sms (amount, date) VALUES(416.81, '20210406');
INSERT INTO payment_sms (amount, date) VALUES(209.75, '20210330');

select sum(amount) from payment_sms p where p.date > (select max(date) from payment_last ps);


--update payment  set status = 'Paid' where status = '已付款';

select  '总收款:' || (SELECT sum(amount) from payment p where p.status ='Paid'  and p.date > (select max(date) from payment_last ps)) 
|| ', 应付保锋:' || sum(amount) from payment p where p.status ='Paid' and p.note <> 'Customer Tips'  and p.date > (select max(date) from payment_last ps);