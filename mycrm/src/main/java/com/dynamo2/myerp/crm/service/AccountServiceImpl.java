package com.dynamo2.myerp.crm.service;

import java.util.*;

import com.sun.org.apache.xalan.internal.xsltc.dom.AnyNodeCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.AccountDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.crm.service.constant.Roles_ENUM;
import com.dynamo2.myerp.service.ServiceException;

@Service("accountService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class AccountServiceImpl extends AbstractBaseService<Account> implements AccountService {

    private Map<String, Account> accountMap = new HashMap<String, Account>();

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public List<Account> listAll() {
        List<Account> accounts = accountDAO.listAllAccounts();
        if (accounts == null) {
            return Collections.emptyList();
        }

        return accounts;
    }

    @Override
    protected AbstractBaseDAO<Account> getDao() {
        return accountDAO;
    }

    //@Override
    public Account findByAccount(String accountString) {

        Account account = null;
        synchronized (accountMap) {
            account = accountMap.get(accountString);
            if (account == null) {
                account = accountDAO.findByAccount(accountString);
                if (account == null) {
                    return null;
                }

                accountMap.put(account.getAccount(), account);
            }
        }

        return account;
    }

    //@Override
    public Account findWithChildrenByAccount(String accountString) {
        return accountDAO.findWithChildrenByAccount(accountString);
    }

    @Override
    public void newOrUpdate(Account entity) throws ServiceException {

        // Always add supervisor role for supervisor
        if (getCurrentAccount().getRoleList().contains(Roles_ENUM.ROLE_SUPERVISOR.name())
                && getCurrentAccount().getAccount().equals(entity.getAccount())
                && !entity.getRoleList().contains(Roles_ENUM.ROLE_SUPERVISOR.name())) {
            entity.getRoleList().add(Roles_ENUM.ROLE_SUPERVISOR.name());
            entity.setRoleList(entity.getRoleList());
        }

        super.newOrUpdate(entity);

        synchronized (accountMap) {
            accountMap.remove(entity.getAccount());
        }
    }

    //@Override
    public Account findAllChildrenByAccount(String account){
        //return findAccountFromTree(account,generateAccountTree());

        List<Account> accs = accountDAO.listAllAccounts();
        if(accs != null && !accs.isEmpty()){
            for(Account acc:accs){
                if(acc.getAccount().equals(account)){
                    return acc;
                }
            }
        }

        return null;
    }

//    private Account findAccountFromTree(String account,List<Account> accList){
//        if(accList != null && !accList.isEmpty()){
//            for(Account acc:accList){
//                if(acc.getAccount().equals(account)){
//                    return acc;
//                }
//
//                List<Account> ca = acc.getChildren();
//                if(ca != null && !ca.isEmpty()){
//                    Account nacc = this.findAccountFromTree(account,ca);
//                    if(nacc != null){
//                        return nacc;
//                    }
//                }
//            }
//        }
//
//        return null;
//    }
//
//    public List<Account> generateAccountTree(){
//        List<Account> accs = accountDAO.listAllAccounts();
//        List<Account> accTree = new ArrayList<Account>();
//        if(accs != null && !accs.isEmpty()){
//            Map<Long,Account> accm = new HashMap<Long, Account>();
//            for(Account acc:accs){
//                accm.put(acc.getId(),acc);
//            }
//
//            for(Account acc : accs){
//                if(acc.getParentId() == null){
//                    accTree.add(acc);
//                }else {
//                    Account pa = accm.get(acc.getParentId());
//                    pa.addChild(acc);
//                    acc.setParent(pa);
//                }
//            }
//        }
//
//        return accTree;
//    }

}
