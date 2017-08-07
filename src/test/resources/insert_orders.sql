INSERT INTO orders(
  version, insert_ts, update_ts, username, date_time_of_deal, is_paid, active, deal_type, completiondate, partner_id, user_login)
VALUES
      (0,'2016-09-26 16:47:10','2016-09-26 16:47:11','kkm','2016-09-20',0x01,0x00,'PURCHASE','2016-09-21',1,1),
      (0,'2016-09-26 16:59:47','2016-09-26 16:59:49','kkm','2016-09-24',0x01,0x00,'SELL','2016-09-25',2,1),
      (0,'2016-09-26 17:02:08','2016-09-26 17:02:08','kkm','2016-09-24',0x00,0x00,'SELL','2016-09-25',2,1);

INSERT INTO order_detail(
  version, insert_ts, update_ts, username, qnt, summ, product_id, order_id)
VALUES
    (0,'2016-09-26 16:48:21','2016-09-26 16:48:21','kkm',50,10750.00,1,1),
    (0,'2016-09-26 16:48:59','2016-09-26 16:48:59','kkm',10,1150.00,2,1),
    (0,'2016-09-26 17:01:16','2016-09-26 17:01:17','kkm',2,640.00,1,2),
    (0,'2016-09-26 17:03:08','2016-09-26 17:03:09','kkm',1,300.00,1,3);