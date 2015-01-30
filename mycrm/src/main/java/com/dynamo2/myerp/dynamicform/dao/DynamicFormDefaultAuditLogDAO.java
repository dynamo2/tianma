package com.dynamo2.myerp.dynamicform.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Account;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefaultAuditLog;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefaultAuditLog.LOG_OPERATIONS_ENUM;

@Repository("dynamicFormDefaultAuditLogDAO")
public class DynamicFormDefaultAuditLogDAO extends AbstractBaseDAO<DynamicFormDefaultAuditLog> {

	public void addLog(LOG_OPERATIONS_ENUM op, Long formId, Account currentAccount, String logMsg) {
		DynamicFormDefaultAuditLog log = new DynamicFormDefaultAuditLog();
		log.setAccount(currentAccount.getAccount());
		log.setFormId(formId);
		log.setPlainLog(logMsg);
		log.setOperation(op.name());

		super.saveOrUpdate(log, currentAccount.getAccount());
	}

	public List<DynamicFormDefaultAuditLog> listAuditLog(Long formId) {
		Query q = em.createNamedQuery("listAuditLog");
		q.setParameter(1, formId);

		return q.getResultList();
	}

	public List<DynamicFormDefaultAuditLog> listAuditLogByAccount(Account currentAccount) {
		Query q = em.createNamedQuery("listAuditLogByAccount");
		q.setParameter(1, currentAccount.getAccount());
		q.setMaxResults(100);

		return q.getResultList();
	}
}
