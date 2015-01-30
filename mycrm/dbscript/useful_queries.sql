-- Query customer with Contact and Distributor
SELECT 
	c.id, c.customer_id,c.name customer,ct2.name distributor,c.phone company_phone,c.email,c.address1,c.fax1,c.website,c.city,c.province,c.created_by,c.created,
	ct.title contact_tile, concat(p.last_name, p.first_name) contact_name, p.phone contact_phone, p.mobile contact_mobile, p.email contact_email
FROM customer c
left join contact ct on ct.customer_id = c.id 
left join person p on ct.person_id = p.id
left join customer_relationships_graph cr on cr.right_customer_id = c.id
left join customer ct2 on cr.left_customer_id = ct2.id
-- where concat(p.last_name, p.first_name) like '%赵%';
-- where c.name like '%卓%';

-- Export data
mysqldump --defaults-extra-file="/tmp/tmpiX4HZ4/extraparams.cnf"  --no-create-info=FALSE --order-by-primary=FALSE --force=FALSE --no-data=FALSE --tz-utc=TRUE --flush-privileges=FALSE --compress=FALSE --replace=FALSE --host=uhz002786.chinaw3.com --insert-ignore=FALSE --extended-insert=TRUE --user=fan --quote-names=TRUE --hex-blob=FALSE --complete-insert=FALSE --add-locks=TRUE --port=3306 --disable-keys=TRUE --delayed-insert=FALSE --create-options=TRUE --delete-master-logs=FALSE --comments=TRUE --default-character-set=utf8 --max_allowed_packet=1G --flush-logs=FALSE --dump-date=TRUE --lock-tables=TRUE --allow-keywords=FALSE --events=FALSE --databases --routines "mycrm"