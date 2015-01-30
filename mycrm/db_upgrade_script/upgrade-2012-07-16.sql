DELIMITER $$

--Add expression
-- ALTER TABLE `mycrm`.`form_field_metadata` ADD COLUMN `expression` VARCHAR(255) NULL  AFTER `input_rows` $$

--Add Customer_Id
ALTER TABLE `mycrm`.`customer` ADD COLUMN `customer_id` VARCHAR(45) NULL  AFTER `name` 
, ADD UNIQUE INDEX `customer_id_UNIQUE` (`customer_id` ASC) $$


--Add Customer Seq Number
ALTER TABLE `mycrm`.`customer` ADD COLUMN `customer_seq_num` VARCHAR(45) NULL  AFTER `customer_id` 
, ADD UNIQUE INDEX `customer_seq_num_UNIQUE` (`customer_seq_num` ASC) $$

--Add more phone number
ALTER TABLE `mycrm`.`customer` ADD COLUMN `phone2` VARCHAR(255) NULL  AFTER `phone` , ADD COLUMN `phone3` VARCHAR(255) NULL  AFTER `phone2` $$

--Find duplicated_customer
-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
drop PROCEDURE if exists `mycrm`.`find_duplicated_customer`$$

CREATE PROCEDURE `mycrm`.`find_duplicated_customer` (
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
	(phone3 is not null and phone3 <> '' and phone3 = _phone) or
	(phone2 is not null and phone2 <> '' and phone2 = _phone) or
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

END$$
