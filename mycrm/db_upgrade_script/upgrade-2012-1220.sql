delimiter $$

CREATE TABLE `ROLE_TO_PERMISSIONS` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ROLE_NAME` varchar(45) NOT NULL,
  `PERMISSIONS` text NOT NULL,
  `created` datetime DEFAULT NULL,
  `last_modified_by` varchar(45) DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$

ALTER TABLE `mycrm`.`ROLE_TO_PERMISSIONS` 
ADD UNIQUE INDEX `rolename` (`ROLE_NAME` ASC) $$

ALTER TABLE `notes` ADD COLUMN `for_role_name` VARCHAR(45) NOT NULL  AFTER `last_modified_by` $$

