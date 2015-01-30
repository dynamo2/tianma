delimiter $$

CREATE TABLE `enum_list` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `language` varchar(45) DEFAULT NULL,
  `value` varchar(45) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `last_modified_by` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$





ALTER TABLE `customer` ADD COLUMN `service_status` VARCHAR(45) NULL  AFTER `quality` $$
