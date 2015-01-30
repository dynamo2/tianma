delimiter $$

CREATE TABLE `help_content` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `page_file_path` varchar(255) NOT NULL,
  `type` varchar(45) NOT NULL,
  `content` longtext NOT NULL,
  `language` varchar(45) NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_modified_by` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$

