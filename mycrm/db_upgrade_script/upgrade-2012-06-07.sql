-- Add type column for todo
ALTER TABLE `mycrm`.`todo` ADD COLUMN `type` VARCHAR(45) NULL  AFTER `last_modified_by` ;