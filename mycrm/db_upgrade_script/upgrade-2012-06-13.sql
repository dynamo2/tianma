DELIMITER $$

-- Add "form_short_name" column to indicate the form id prefix
ALTER TABLE `mycrm`.`form_metadata` ADD COLUMN `form_short_name` VARCHAR(45) NULL  AFTER `form_status` $$

-- Add title in TODO
ALTER TABLE `mycrm`.`todo` ADD COLUMN `title` VARCHAR(255) NULL  AFTER `type` $$

-- Add Form Business Id String
ALTER TABLE `mycrm`.`dynamic_form_default` ADD COLUMN `form_biz_id` VARCHAR(100) NULL  AFTER `form_name` 
, ADD INDEX `form_biz_id_index` (`form_biz_id` ASC) $$

-- Add Customer Id String
ALTER TABLE `mycrm`.`dynamic_form_default` ADD COLUMN `customer_id` BIGINT NULL  AFTER `form_biz_id` $$

-- Add summary field
ALTER TABLE `mycrm`.`dynamic_form_default` ADD COLUMN `summary` VARCHAR(255) NULL  AFTER `customer_id` $$

-- Change the create_dynamic_form_table routine
-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------

CREATE DEFINER=`root`@`localhost` PROCEDURE `create_dynamic_form_table`(
	table_name varchar(20)
)
BEGIN

DECLARE while_count INT DEFAULT 0;

-- TODO need to check if table with 'table_name' already exists.

drop table if exists dynamic_form_record;
CREATE TABLE dynamic_form_record (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `form_metadata_id` BIGINT not NULL ,
  `form_name` varchar(255) not null,
  `form_biz_id` VARCHAR(100) not NULL ,
  `customer_id` BIGINT NULL ,
  `summary` VARCHAR(255) NULL ,
  `attachment` BLOB NULL ,
  `created` DATETIME NOT NULL ,
  `created_by` VARCHAR(45) NOT NULL ,
  `last_modified` DATETIME NOT NULL ,
  `last_modified_by` VARCHAR(45) NOT NULL ,
  `assignee_account` VARCHAR(45) NOT NULL ,
  `status` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

ALTER TABLE `dynamic_form_record` ADD INDEX `form_biz_id_index` (`form_biz_id` ASC) ;

set while_count = 1;
WHILE while_count < 51 DO
set @alter_table_sql = concat('alter table dynamic_form_record add column custom_varchar_', 
					while_count, ' varchar(255)');
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;
SET while_count = while_count + 1;
END WHILE;

set while_count = 1;
WHILE while_count < 11 DO
set @alter_table_sql = concat('alter table dynamic_form_record add column custom_int_', 
					while_count, ' int');
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;
SET while_count = while_count + 1;
END WHILE;

set while_count = 1;
WHILE while_count < 6 DO
set @alter_table_sql = concat('alter table dynamic_form_record add column custom_double_', 
					while_count, ' double');
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;
SET while_count = while_count + 1;
END WHILE;

set while_count = 1;
WHILE while_count < 6 DO
set @alter_table_sql = concat('alter table dynamic_form_record add column custom_datetime_', 
					while_count, ' datetime');
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;
SET while_count = while_count + 1;
END WHILE;

set while_count = 1;
WHILE while_count < 2 DO
set @alter_table_sql = concat('alter table dynamic_form_record add column custom_text_', 
					while_count, ' text');
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;
SET while_count = while_count + 1;
END WHILE;

set @alter_table_sql = concat('alter table dynamic_form_record RENAME dynamic_form_', 
					table_name);
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;

END $$
-- Change the create_dynamic_form_table routine end