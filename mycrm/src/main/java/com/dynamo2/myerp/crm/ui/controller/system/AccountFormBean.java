package com.dynamo2.myerp.crm.ui.controller.system;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import com.dynamo2.myerp.service.PERMISSION_DEFINITION_ENUM;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.icefaces.bean.ViewRetained;
import org.icefaces.bean.WindowDisposed;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.dao.entities.Person;
import com.dynamo2.myerp.crm.service.AccountService;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;
import com.dynamo2.myerp.service.ServiceException;

import java.util.*;

@ManagedBean(name = "systemAccountForm")
@ViewScoped
@ViewRetained
@WindowDisposed
public class AccountFormBean extends AbstractManagementBean {
	private static final String INIT_PASSWORD = "111111";
	private Account account;
	private Long id;
	private final static Log LOGGER = LogFactory.getLog(AccountFormBean.class);

	@ManagedProperty(value = "#{accountService}")
	private AccountService accountService;

	private boolean isAccountDuplicated;

    private List<Account> allAccounts;

	protected void init() {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Form id:[" + id + "]");
		}

		if (id != null) {
			account = accountService.loadById(id);
            account = accountService.findWithChildrenByAccount(account.getAccount());

			if (account.getPerson() == null) {
				account.setPerson(new Person());
			}

		} else {
			account = new Account();
			account.setPerson(new Person());
			account.setPassword(INIT_PASSWORD);
		}
    }

    public List<Account> getAccountList(){
        List<Account> al = this.getAllAccounts();
        Account accountWithChildren = null;
        if(id != null){
            accountWithChildren = accountService.findWithChildrenByAccount(this.getAccount().getAccount());
        }

        List<Account> accounts = new ArrayList<Account>();
        if(al != null && al.size() > 0){
            for(Account ac : al){
                if(isSameAccount(ac,accountWithChildren)
                        || isChildren(accountWithChildren,ac)){
                    continue;
                }

                accounts.add(ac);
            }
        }

        return accounts;
    }

    public boolean getIsOwner(){
        return appSessionParams.getCurrentAccount().getAccount().equals(this.getAccount().getAccount());
    }

    public boolean getAccountEditable(){
        return this.appSessionParams.hasPermission(PERMISSION_DEFINITION_ENUM.ACCOUNT_EDIT);
    }

    private boolean isSameAccount(Account first,Account second){
        if(first == null || second == null){
            return false;
        }

        return first.getAccount().equals(second.getAccount());
    }

    private boolean isEmpty(List<Account> l){
        return l == null || l.isEmpty();
    }

    private boolean isEmpty(String l){
        return l == null || l.isEmpty();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public Account getAccount() {
		if (account == null) {
			init();
		}

		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String save() throws ServiceException {
        if(this.account.getParentId() == 0){
            this.account.setParentId(null);
        }

        accountService.newOrUpdate(account);

		if (getCurrentAccount().getId().equals(account.getId())) {
			return null;
		} else {
			return "system_account_list.xhtml?faces-redirect=true";
		}
	}

	public void validateAccount() {
		isAccountDuplicated = true;
	}

	public boolean isAccountDuplicated() {
		return isAccountDuplicated;
	}

	public void resetPassword() throws ServiceException {
		account.setPassword(INIT_PASSWORD);
		accountService.newOrUpdate(account);
	}

    private List<Account> getAllAccounts(){
        if(this.allAccounts == null){
            this.allAccounts = accountService.listAll();
        }

        if(this.allAccounts == null){
            return Collections.EMPTY_LIST;
        }

        return this.allAccounts;
    }

    private Account getAccountFromList(String account){
        for(Account a : this.getAllAccounts()){
            if(a.getAccount().equals(account)){
                return a;
            }
        }

        return this.accountService.findWithChildrenByAccount(account);
    }

    private boolean isChildren(Account parent,Account child){
        if(parent == null || child == null){
            return false;
        }

        if(CollectionUtils.isEmpty(parent.getChildren())){
            return false;
        }

        for(Account a : parent.getChildren()){
            if(isSameAccount(a,child)){
                return true;
            }

            Account accountWithChildren = this.getAccountFromList(a.getAccount());
            if(isChildren(accountWithChildren,child)){
                return true;
            }
        }
        return false;
    }
}
