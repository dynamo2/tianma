-- Add
ALTER TABLE `mycrm`.`form_metadata` ADD COLUMN `form_category` VARCHAR(45) NOT NULL DEFAULT 'no_category'  AFTER `can_remove_account_list` ;


