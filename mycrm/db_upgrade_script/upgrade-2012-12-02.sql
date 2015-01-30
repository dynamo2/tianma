delimiter $$

CREATE TABLE `email_box` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `messages` text NOT NULL,
  `mail_to` varchar(255) NOT NULL,
  `status` varchar(45) NOT NULL,
  `err_msg` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `mail_from` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` varchar(45) NOT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$

