-- Add columns to save the permission control based on account in system
ALTER TABLE `mycrm`.`form_metadata` ADD COLUMN `can_read_account_list` TEXT NULL  AFTER `form_short_name` , ADD COLUMN `can_write_account_list` TEXT NULL  AFTER `can_read_account_list` , ADD COLUMN `can_create_account_list` TEXT NULL  AFTER `can_write_account_list` , ADD COLUMN `can_remove_account_list` TEXT NULL  AFTER `can_create_account_list` ;

-- Add table to save the audit log
delimiter $$

CREATE TABLE `dynamic_form_default_audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_id` bigint(20) NOT NULL,
  `account` varchar(45) NOT NULL,
  `operation` varchar(45) NOT NULL,
  `plain_log` text NOT NULL,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8$$



-- Add customer_id field
ALTER TABLE `account` ADD COLUMN `customer_id` BIGINT(20) NULL  AFTER `person_id` ;