package com.dynamo2.myerp.crm.dao;

import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.FileUpload;

import javax.persistence.Query;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository("fileuploadDAO")
public class FileuploadDAO extends AbstractBaseDAO<FileUpload> {
    public List<FileUpload> findFilesByCustomerAndAccount(Account account, Long customerId) {
        Query q = em.createNamedQuery("findFilesByCustomerAndAccount");

        q.setParameter(1, customerId);
        q.setParameter(2, customerId);
        q.setParameter(3, account.getAccount());
        q.setParameter(4, account.getAccount());
        q.setParameter(5, account.getAccount());

        return q.getResultList();
    }

}
