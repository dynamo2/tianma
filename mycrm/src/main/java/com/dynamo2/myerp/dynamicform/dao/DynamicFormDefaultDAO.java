package com.dynamo2.myerp.dynamicform.dao;

import java.util.*;

import javax.persistence.Query;

import com.dynamo2.myerp.crm.dao.entities.Account;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.DynamicFormDefault;
import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormListEntry;

@Repository("dynamicFormDefaultDAO")
public class DynamicFormDefaultDAO extends AbstractBaseDAO<DynamicFormDefault> {
    private static String BASIC_NATIVE_SQL = "select d.customer_id customerId, d.summary, d.form_biz_id formBizId, d.id, d.status, d.created_by createdBy, d.created, d.last_modified lastModified, d.last_modified_by lastModifiedBy, d.assignee_account assigneeAccount, f.form_label formLabel"
            + " from dynamic_form_default d left join form_metadata f on (d.form_metadata_id=f.id) "
            + " where (d.status<>'DELETED') and (d.form_metadata_id = ?) ";

    private static String DEFAULT_ACCOUNT_CRITERIA = " (d.created_by = ? or d.last_modified_by = ? or d.assignee_account = ?) ";
    private static String DEFAULT_ORDER_BY = " order by d.last_modified desc";

	public List<FormListEntry> listFormsToMe(Account account, Long formMetadataId) {
		Query q = em.createNamedQuery("listFormsToMe");
		q.setParameter(1, account);
		q.setParameter(2, account);
		q.setParameter(3, account);
		q.setParameter(4, formMetadataId);

		return q.getResultList();
	}

    public List<FormListEntry> listBelongedFormsToMe(Account account, Long formMetadataId) {
        Query q = em.createNamedQuery("listBelongedFormsToMe");

        Set<String> accList = new HashSet<String>();
        this.appendChildrenAccount(account,accList);

        q.setParameter("createBy", accList);
        q.setParameter("modifiedBy", accList);
        q.setParameter("assigneeAccount", accList);
        q.setParameter("formId", formMetadataId);

        return q.getResultList();
    }

    public List<FormListEntry> listFormsToParentForm(String parentFormBizId,Long formMetadataId) {
        Query q = em.createNamedQuery("listFormsToParentForm");

        q.setParameter(1, parentFormBizId);
        q.setParameter(2, formMetadataId);

        return q.getResultList();
    }

    private void appendChildrenAccount(Account account,Set<String> accList){
        accList.add(account.getAccount());

        List<Account> ca = account.getChildren();
        if(ca != null && !ca.isEmpty()){
            for(Account acc:ca){
                appendChildrenAccount(acc, accList);
            }
        }
    }

	public void updateDynamicFormDefaultAssignTo(long formId, String assigneeAccount) {
		Query q = em.createNamedQuery("updateDynamicFormDefaultAssignTo");
		q.setParameter(1, assigneeAccount);
		q.setParameter(2, formId);

		q.executeUpdate();
	}

	public void updateDynamicFormDefaultStatus(long formId, String status) {
		Query q = em.createNamedQuery("updateDynamicFormDefaultStatus");
		q.setParameter(1, status);
		q.setParameter(2, formId);

		q.executeUpdate();
	}

	public List<FormListEntry> listAllForms(Long formMetadataId) {
		Query q = em.createNamedQuery("listAllForms");
		q.setParameter(1, formMetadataId);

		return q.getResultList();
	}

	public List<FormListEntry> listAllFormsForCustomer(Long customerId, Long formMetadataId) {
		Query q = em.createNamedQuery("listAllFormsForCustomer");
		q.setParameter(1, customerId);
		q.setParameter(2, formMetadataId);

		return q.getResultList();
	}

	public List<FormListEntry> searchByCriteria(Map<String, String> searchCriteriaValueMap,Map<String, List<String>> notEqualCriteriaValueMap, String account,
			Long formMetadataId) {

		StringBuilder nativeSql = new StringBuilder(BASIC_NATIVE_SQL);
		nativeSql.append(" and ").append(DEFAULT_ACCOUNT_CRITERIA);

        setCriterias(nativeSql,searchCriteriaValueMap);
        setNECriterias(nativeSql,notEqualCriteriaValueMap);

		nativeSql.append(DEFAULT_ORDER_BY);

		Query q = em.createNativeQuery(nativeSql.toString(), FormListEntry.class);
		q.setParameter(1, formMetadataId);
        q.setParameter(2, account);
        q.setParameter(3, account);
        q.setParameter(4, account);

		return q.getResultList();
	}

	public List<FormListEntry> searchByCriteria(Map<String, String> searchCriteriaValueMap,Map<String, List<String>> notEqualCriteriaValueMap, Long formMetadataId) {
		StringBuilder nativeSql = new StringBuilder(BASIC_NATIVE_SQL);

        setCriterias(nativeSql,searchCriteriaValueMap);
        setNECriterias(nativeSql,notEqualCriteriaValueMap);

		nativeSql.append(DEFAULT_ORDER_BY);

		Query q = em.createNativeQuery(nativeSql.toString(), FormListEntry.class);
		q.setParameter(1, formMetadataId);

		return q.getResultList();

	}

	public Long getFormIdByFormBizId(String formBizId) {
		Query q = em.createNamedQuery("getFormIdByBizId");
		q.setParameter(1, formBizId);

		List rst = q.getResultList();
		if (rst == null || rst.isEmpty()) {
			return null;
		}

		return (Long) rst.get(0);

	}

	public Object[] searchCertification(String certificationSN, String verficationCode) {
		Query q = em
				.createNativeQuery("select d.custom_varchar_1, d.custom_varchar_2, d.custom_varchar_5, d.custom_datetime_1, "
						+ "d.custom_datetime_2, d.custom_varchar_6, d.custom_varchar_7, d.custom_varchar_8, c.name "
						+ "from mycrm.dynamic_form_default d "
						+ " left join mycrm.form_metadata f on d.form_metadata_id = f.id "
						+ " left join mycrm.customer c on c.id = d.customer_id "
						+ " where f.form_name = 'Certificate_Mange' and d.custom_varchar_1 = '"
						+ certificationSN
						+ "' and d.custom_varchar_2 = '" + verficationCode + "'");

		List rst = q.getResultList();
		if (rst != null && !rst.isEmpty()) {
			return (Object[]) rst.get(0);
		} else {
			return null;
		}
	}

	public List<FormListEntry> listFormsOperatedByMe(String account) {
		Query q = em.createNamedQuery("listFormsOperatedByMe");
		q.setParameter(1, account);
		q.setMaxResults(100);

		return q.getResultList();
	}

    private void setCriterias(StringBuilder nativeSql,Map<String, String> searchCriteriaValueMap){
        if(searchCriteriaValueMap == null)
            return;

        for (Map.Entry<String, String> c : searchCriteriaValueMap.entrySet()) {
            if (c.getValue() != null) {
                setStringParam(nativeSql,c.getKey(),c.getValue(),true);
            }
        }
    }

    private void setNECriterias(StringBuilder nativeSql,Map<String, List<String>> notEqualCriteriaValueMap){
        if(notEqualCriteriaValueMap == null)
            return;

        for (Map.Entry<String, List<String>> c : notEqualCriteriaValueMap.entrySet()) {
            if(CollectionUtils.isEmpty(c.getValue())){
                continue;
            }

            for(String v : c.getValue()){
                setStringParam(nativeSql,c.getKey(),v,false);
            }
        }
    }

    private void setStringParam(StringBuilder nativeSql,String field,String value,boolean isEqual){
        nativeSql.append(" and d.").append(field)
                .append(isEqual?"=":"<>").append("'")
                .append(value).append("' ");
    }
}
