package com.dynamo2.myerp.crm.service;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.Account;

public interface AccountService extends AbstractBaseServiceInterface<Account> {

	public final static String ACCOUNT_SYSTEM = "system";
	
	public Account findByAccount(String account);

    public Account findWithChildrenByAccount(String account);

    public Account findAllChildrenByAccount(String account);
}
