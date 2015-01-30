-- --------------------------------------------------------------------------------
-- Routine DDL
-- --------------------------------------------------------------------------------
DELIMITER $$

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
		
		if field_count > field_count_max then
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

END