CREATE DATABASE  IF NOT EXISTS `mycrm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mycrm`;
-- MySQL dump 10.13  Distrib 5.1.60, for redhat-linux-gnu (x86_64)
--
-- Host: localhost    Database: mycrm
-- ------------------------------------------------------
-- Server version	5.1.60

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping routines for database 'mycrm'
--
/*!50003 DROP PROCEDURE IF EXISTS `add_customer_account_relationship` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `add_customer_account_relationship`(
	param_customer_id int, 
	param_bound_account varchar(45))
BEGIN
	DECLARE car_count int;


	select count(*) into car_count 
	from customer_account_relationship 
	where customer_id = param_customer_id and
		bound_account = param_bound_account;

	if car_count = 0 then
		insert into customer_account_relationship(
			customer_id, bound_account)
		values (param_customer_id, param_bound_account);
	end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `create_dynamic_form_table` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `create_dynamic_form_table`(
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

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `dynamic_form_find_unused_field` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `dynamic_form_find_unused_field`(
	_form_metadata_id bigint(20),
	_field_type varchar(255)
)
BEGIN

declare err_code varchar(20) default 'ok';
declare field_count int default 0;
declare _column_name varchar(20);
declare column_count int;
declare column_count_max int;

if _field_type = 'varchar' then
	set column_count_max = 50;
elseif _field_type = 'int' then
	set column_count_max = 10;
elseif _field_type = 'double' then
	set column_count_max = 5;
elseif _field_type = 'datetime' then
	set column_count_max = 5;
elseif _field_type = 'text' then
	set column_count_max = 1;
else 
	set err_code = 'invalid_field_type';
end if;

if err_code = 'ok' then
	find_unused_field: LOOP
		set field_count = field_count + 1;
		set _column_name = concat('custom_', _field_type, '_', field_count);

		select count(id) into column_count 
		from form_field_metadata
		where form_metadata_id = _form_metadata_id and
			column_name = _column_name;

		if column_count = 0 then
			LEAVE find_unused_field;
		end if;
		
		if column_count > column_count_max then
			set err_code = 'no_unused_field';
			LEAVE find_unused_field;
		end if;
	END LOOP find_unused_field;
end if;

select err_code, _column_name;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `find_duplicated_customer` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `find_duplicated_customer`(
  `_id` bigint(20),
  `_name` varchar(255),
  `_address1` varchar(255) ,
  `_address2` varchar(255) ,
  `_address3` varchar(255) ,
  `_fax1` varchar(45) ,
  `_fax2` varchar(45) ,
  `_fax3` varchar(45) ,
  `_website` varchar(255) ,
  `_phone` varchar(255) ,
  `_email` varchar(255)
)
BEGIN

declare sqlStr text default 'na';
declare emailDomain varchar(255) default 'na';
declare domainInWhiteList bit default 0;
declare outCustomerName varchar(255) default null;
declare outCustomerId bigint(20) default null;

if _id is null then 
	set _id = 0;
end if;

select id, name into outCustomerId, outCustomerName 
from customer
where ((name is not null and name <> '' and name = _name) or
	(address1 is not null and address1 <> '' and address1 = _address1) or
	(address2 is not null and address2 <> '' and  address2 = _address2) or
	(address3 is not null and address3 <> '' and  address3 = _address3) or
	(fax1 is not null and fax1 <> '' and fax1 = _fax1) or
	(fax2 is not null and fax2 <> '' and fax2 = _fax2) or
	(fax3 is not null and fax2 <> '' and fax3 = _fax3) or
	(website is not null and website <> '' and website = _website) or
	(phone is not null and phone <> '' and phone = _phone) or
	exists (
		select contact.id from contact left join person on contact.person_id = person.id 
		where 
		contact.customer_id = customer.id and 
		(
			(person.phone is not null and person.phone <> '' and person.phone = _phone) or
			(person.mobile is not null and person.mobile <> '' and person.mobile = _phone)
		)
	)) and id != _id
limit 1;

if outCustomerId is null then

	-- Handle email check				
	if _email is not null and _email <> '' then
		if locate('@',_email) > 0 then
			set emailDomain = substr(_email,locate('@',_email));
		end if;
		if emailDomain != 'na' then 
			-- Check if domain is in domain white list
			select 1 into domainInWhiteList from email_domain_white_list where `domain` = emailDomain;
			if !domainInWhiteList then
				select id, name into outCustomerId, outCustomerName 
				from customer
				where email is not null and substr(email,locate('@',email)) = emailDomain and id != _id
				limit 1;
			else 
				select id, name into outCustomerId, outCustomerName 
				from customer
				where email is not null and email = _email and id != _id
				limit 1;
			end if;
		end if;
	end if;

end if;

if outCustomerId is null then
	-- Handle email check in contact						
	if emailDomain != 'na' then
		if !domainInWhiteList then -- same email domain should not exsit
			select id, name into outCustomerId, outCustomerName 
			from customer
			where ( 
				exists (
					select contact.id from contact left join person on contact.person_id = person.id 
					where person.email is not null and
						substr(person.email,locate('@',person.email)) = emailDomain
				)) and id != _id
			limit 1;
		else -- same email should not exsit
			select id, name into outCustomerId, outCustomerName 
			from customer
			where (
				exists (
					select contact.id from contact left join person on contact.person_id = person.id 
					where person.email is not null and
						person.email = _email
				)) and id != _id
			limit 1;
		end if;
	end if;

end if;

select outCustomerId, outCustomerName;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-05-28  8:41:18
