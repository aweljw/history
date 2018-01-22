CREATE TABLE `authorities` (
  `user_id` varchar(100) DEFAULT NULL,
  `authority` varchar(500) NOT NULL,
  KEY `authorities_user_account_FK` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
