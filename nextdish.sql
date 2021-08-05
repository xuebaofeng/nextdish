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
	date TEXT,
	PRIMARY KEY(date)
);

drop table payment_last;
CREATE TABLE payment_last (
	amount REAL,
	date TEXT,
	PRIMARY KEY(date)
);

SELECT * from payment p where p.date > (select max(date) from payment_last ps)  order by date desc;
select * from payment_sms p where p.date > (select max(date) from payment_last ps) order by date desc;

SELECT sum(amount) from payment p where p.status ='Paid' and p.date > (select max(date) from payment_last ps);


select * from payment_last ps;

update payment  set status = 'Paid' where status = '已付款';

select  '总收款:' || (select sum(amount) from payment_sms p where p.date > (select max(date) from payment_last ps) and p.date <= (select max(date) from payment_sms ps)) 
|| ', 应付保锋:' || sum(amount) from payment p where p.status ='Paid' and p.note <> 'Customer Tips'  and p.date > (select max(date) from payment_last ps)  and p.date <= (select max(date) from payment_sms ps);


/*
 * Dear Nextdish driver, your delivery payment before 08/03 , a total of $514.33, has been transferred to your bank account. You should receive it in 2-3 days. Please feel free to contact us if you have any question
 * Dear Nextdish driver, your delivery payment before 07/27 , a total of $470.33, has been transferred to your bank account. You should receive it in 2-3 days. Please feel free to contact us if you have any question
 */

INSERT INTO payment_last (amount, date) VALUES(1376, '20210427');

INSERT INTO payment_sms (amount, date) VALUES(514.33, '20210803');
INSERT INTO payment_sms (amount, date) VALUES(470.33, '20210727');
INSERT INTO payment_sms (amount, date) VALUES(492.83, '20210720');
INSERT INTO payment_sms (amount, date) VALUES(265.75, '20210713');
INSERT INTO payment_sms (amount, date) VALUES(407.5, '20210706');
INSERT INTO payment_sms (amount, date) VALUES(360.84, '20210630');
INSERT INTO payment_sms (amount, date) VALUES(373.41, '20210623');
INSERT INTO payment_sms (amount, date) VALUES(433.33, '20210616');
INSERT INTO payment_sms (amount, date) VALUES(173.50, '20210609');
INSERT INTO payment_sms (amount, date) VALUES(345.33, '20210602');
INSERT INTO payment_sms (amount, date) VALUES(118.75, '20210525');
INSERT INTO payment_sms (amount, date) VALUES(175.83, '20210518');
INSERT INTO payment_sms (amount, date) VALUES(151.33, '20210511');
INSERT INTO payment_sms (amount, date) VALUES(391.32, '20210505');
INSERT INTO payment_sms (amount, date) VALUES(187.33, '20210427');
INSERT INTO payment_sms (amount, date) VALUES(449.50, '20210421');
INSERT INTO payment_sms (amount, date) VALUES(361.25, '20210414');
INSERT INTO payment_sms (amount, date) VALUES(416.81, '20210406');
INSERT INTO payment_sms (amount, date) VALUES(209.75, '20210330');



SELECT sum(amount) from payment p;
SELECT sum(amount) from payment p where p.note = 'Customer Tips' and p.date > '20210714';
SELECT * from payment p where p.note = 'Customer Tips' order by date desc;

