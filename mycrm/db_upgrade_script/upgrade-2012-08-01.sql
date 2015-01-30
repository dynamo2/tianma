delimiter $$

-- Alter dynamic_form_default to add more custom fields for double and datetime type
ALTER TABLE `mycrm`.`dynamic_form_default` 
	ADD COLUMN `custom_double_6` DOUBLE NULL  AFTER `custom_double_5`,
	ADD COLUMN `custom_double_7` DOUBLE NULL  AFTER `custom_double_6`,
	ADD COLUMN `custom_double_8` DOUBLE NULL  AFTER `custom_double_7`,
	ADD COLUMN `custom_double_9` DOUBLE NULL  AFTER `custom_double_8`,
	ADD COLUMN `custom_double_10` DOUBLE NULL  AFTER `custom_double_9`,
	ADD COLUMN `custom_datetime_6` datetime NULL  AFTER `custom_datetime_5`,
	ADD COLUMN `custom_datetime_7` datetime NULL  AFTER `custom_datetime_6`,
	ADD COLUMN `custom_datetime_8` datetime NULL  AFTER `custom_datetime_7`,
	ADD COLUMN `custom_datetime_9` datetime NULL  AFTER `custom_datetime_8`,
	ADD COLUMN `custom_datetime_10` datetime NULL  AFTER `custom_datetime_9`$$

-- Alter store procedure to create the dynamic form table
drop procedure if exists `create_dynamic_form_table` $$

CREATE PROCEDURE `create_dynamic_form_table`(
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
WHILE while_count < 11 DO
set @alter_table_sql = concat('alter table dynamic_form_record add column custom_double_', 
					while_count, ' double');
prepare alter_table_stmt from @alter_table_sql;
execute alter_table_stmt;
DROP PREPARE alter_table_stmt;
SET while_count = while_count + 1;
END WHILE;

set while_count = 1;
WHILE while_count < 11 DO
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

-- Alter store procedure to get unused custom field
drop procedure if exists `dynamic_form_find_unused_field` $$

CREATE PROCEDURE `dynamic_form_find_unused_field`(
	_form_metadata_id bigint(20),
	_field_type varchar(255)
)
BEGIN

declare err_code varchar(20) default 'ok';
declare field_count int default 0;
declare _column_name varchar(20);
declare column_count int;
declare field_count_max int;

if _field_type = 'varchar' then
	set field_count_max = 50;
elseif _field_type = 'int' then
	set field_count_max = 10;
elseif _field_type = 'double' then
	set field_count_max = 10;
elseif _field_type = 'datetime' then
	set field_count_max = 10;
elseif _field_type = 'text' then
	set field_count_max = 1;
else 
	set err_code = 'invalid_field_type';
end if;

if err_code = 'ok' then
	find_unused_field: LOOP
		set field_count = field_count + 1;
		set _column_name = concat('custom_', _field_type, '_', field_count);
		
		if field_count >= field_count_max then
			set err_code = 'no_unused_field';
			LEAVE find_unused_field;
		end if;

		select count(id) into column_count 
		from form_field_metadata
		where form_metadata_id = _form_metadata_id and
			column_name = _column_name;

		if column_count = 0 then
			LEAVE find_unused_field;
		end if;
	END LOOP find_unused_field;
end if;

select err_code, _column_name;

END $$

-- Add expression label
ALTER TABLE `mycrm`.`form_field_metadata` ADD COLUMN `expression_label` VARCHAR(255) NULL  AFTER `expression` ;