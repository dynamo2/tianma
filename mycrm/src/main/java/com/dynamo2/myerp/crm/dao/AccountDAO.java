package com.dynamo2.myerp.crm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;

@Repository("accountDAO")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountDAO extends AbstractBaseDAO<Account> {

	@SuppressWarnings("unchecked")
	public List<Account> listAllAccounts() {
		// TODO Optimize to return Account only.
		List<Object[]> rst = em.createQuery("from Account acct left join acct.person per order by acct.account ASC")
				.getResultList();
		if (rst == null || rst.isEmpty()) {
			return Collections.emptyList();
		}

		List<Account> accounts = new ArrayList<Account>(rst.size());
		for (Object[] obj : rst) {
			accounts.add((Account) obj[0]);
		}

		return accounts;
	}

	public Account findByAccount(String account) {
		if (account == null || account.isEmpty()) {
			return null;
		}

		Query q = em.createQuery("from Account as acct join fetch acct.person where acct.account=?");
		q.setParameter(1, account);
		List<Account> rs = q.getResultList();
		if (rs == null || rs.isEmpty()) {
			return null;
		}

		return rs.get(0);
	}

    public Account findWithChildrenByAccount(String account) {
        Account result = findByAccount(account);
        if(result == null){
            return null;
        }

        Query q = em.createQuery("from Account as acct join fetch acct.person where acct.parent.id=?");
        q.setParameter(1, result.getId());
        List<Account> rs = q.getResultList();
        if (!isEmpty(rs)) {
            result.setChildren(rs);
        }else {
            result.setChildren(new ArrayList<Account>());
        }

        return result;
    }

    private boolean isEmpty(List l){
        return l == null || l.isEmpty();
    }
}
