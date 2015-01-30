ALTER TABLE `customer` CHANGE COLUMN `service_status` `service_status` BIGINT NULL DEFAULT NULL COMMENT 'Columens refer to value in Enum_list table for status label'  ;

ALTER TABLE `enum_list` ADD COLUMN `show_position` INT NULL COMMENT 'enum value postion in whole enum list.'  AFTER `value` ;