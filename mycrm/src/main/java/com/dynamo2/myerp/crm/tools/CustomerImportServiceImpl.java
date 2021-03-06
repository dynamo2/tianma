package com.dynamo2.myerp.crm.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.CustomerServiceImpl;
import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;

@Service("customerImportServiceImpl")
public class CustomerImportServiceImpl extends CustomerServiceImpl {

	@Override
	protected Account getCurrentAccount() {
		Account acct = new Account();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");
		acct.setAccount("importer_" + sdf.format(new Date()));

		return acct;
	}

	@Override
	protected boolean hasPermission(PERMISSION_DEFINITION_ENUM perm) {
		return true;
	}
}
