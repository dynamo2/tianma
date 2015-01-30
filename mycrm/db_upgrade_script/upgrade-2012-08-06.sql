delimiter $$

CREATE  TABLE `mycrm`.`customer_relationships_graph` (
  `id` BIGINT NOT NULL AUTO_INCREMENT ,
  `left_customer_id` BIGINT NOT NULL ,
  `right_customer_id` BIGINT NOT NULL ,
  `type` VARCHAR(45) NOT NULL ,
  `created` datetime DEFAULT NULL,
  `created_by` varchar(45) DEFAULT NULL,
  `last_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=29 DEFAULT CHARSET=utf8$$


ALTER TABLE `mycrm`.`customer` ADD COLUMN `is_distributor` BIT NOT NULL DEFAULT 0  AFTER `notes` $$
