delimiter $$

CREATE TABLE `file_upload` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `customer_id` bigint(20) DEFAULT NULL,
  `for_type` varchar(45) DEFAULT NULL,
  `for_id` bigint(20) DEFAULT NULL,
  `keywords` varchar(45) DEFAULT NULL,
  `comments` varchar(45) DEFAULT NULL,
  `filename` varchar(45) DEFAULT NULL,
  `absolute_path` varchar(255) NOT NULL,
  `file_download_url` varchar(255) DEFAULT NULL,
  `folders` varchar(255) NOT NULL DEFAULT '/',
  `created` datetime DEFAULT NULL,
  `last_modified_by` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8$$

