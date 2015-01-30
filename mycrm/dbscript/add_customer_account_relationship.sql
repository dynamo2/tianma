-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

CREATE PROCEDURE `add_customer_account_relationship`(
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
END