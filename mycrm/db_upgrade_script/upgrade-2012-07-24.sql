delimiter $$

-- Work Flow Table
CREATE TABLE `form_workflow` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `form_metadata_id` bigint(20) NOT NULL,
  `step_name` varchar(45) NOT NULL,
  `status_name` varchar(45) NOT NULL,
  `status_label` varchar(45) NOT NULL,
  `step_sequence_number` int(11) NOT NULL,
  `assignee_account` varchar(255) DEFAULT NULL,
  `assignee_role` varchar(255) DEFAULT NULL,
  `approve_to_step_name` varchar(45) DEFAULT NULL,
  `reject_to_step_name` varchar(45) DEFAULT NULL,
  `back_to_step_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_step_name` (`form_metadata_id`,`step_name`),
  UNIQUE KEY `unique_step_seq` (`form_metadata_id`,`step_sequence_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8$$


