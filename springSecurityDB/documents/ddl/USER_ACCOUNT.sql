CREATE TABLE `user_account` (
  `user_id` varchar(100) NOT NULL DEFAULT '',
  `password` varchar(500) NOT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='유저정보';
