package com.dynamo2.myerp.crm.ui.controller.system;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.crm.ui.controller.AbstractManagementBean;

@ManagedBean(name = "systemAccountMgmt")
@RequestScoped
public class AccountManagementBean extends AbstractManagementBean {
    // TODO Removed it after verify
	private Account account;

    private Account accountWithChildren;

	private Long id;

	private List<Account> allAccounts;

	private Map<String, List<Account>> accountsWithRole;

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Account> getAllAccounts() {
		if (allAccounts == null) {
			allAccounts = accountService.listAll();
			accountsWithRole = new HashMap<String, List<Account>>();
			for (Account acct : allAccounts) {
				addAccountToMap(acct);
			}
		}

		return allAccounts;
	}

    public Account getAccountWithChildren() {
        if (accountWithChildren == null) {
            resetAccountWithChildren();
        }

        return accountWithChildren;
    }

    public boolean isHaveChildren() {
        if (accountWithChildren == null) {
            resetAccountWithChildren();
        }

        return accountWithChildren.getChildren() != null && !accountWithChildren.getChildren().isEmpty();
    }

    private void resetAccountWithChildren(){
        accountWithChildren = accountService.findWithChildrenByAccount(getCurrentAccount().getAccount());
    }

	public Map<String, List<Account>> getAccountsWithRole() {
		if (accountsWithRole == null) {
			getAllAccounts();
		}
		return accountsWithRole;
	}

	private void addAccountToMap(Account acct) {
		if (acct.getRoles() != null && !acct.getRoles().isEmpty()) {
			List<String> roles = acct.getRoleList();
			for (String role : roles) {
				if (Roles_ENUM.contains(role)) {
					if (!accountsWithRole.containsKey(role)) {
						accountsWithRole.put(role, new LinkedList<Account>());
					}
					accountsWithRole.get(role).add(acct);
				}
			}
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void delete(AjaxBehaviorEvent event) throws AbortProcessingException {
		accountService.deleteById(id);
	}

	public TimeZone getTimeZone() {
		// TODO Fan Change to timezone seleted by user.
		return TimeZone.getTimeZone("GMT+8");
	}
}
