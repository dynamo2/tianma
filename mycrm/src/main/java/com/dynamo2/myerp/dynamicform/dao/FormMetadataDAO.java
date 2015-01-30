package com.dynamo2.myerp.dynamicform.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import com.dynamo2.myerp.dynamicform.dao.entities.resultmap.FormMDStatisticsEntry;
import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.FormMetadata;

@Repository("formMetadataDAO")
public class FormMetadataDAO extends AbstractBaseDAO<FormMetadata> {

	public FormMetadata findFormMetadataByName(String formName) {
		Query q = em.createNamedQuery("findFormMetadataByName");
		q.setParameter(1, formName);
		List rst = q.getResultList();
		if (rst == null || rst.isEmpty()) {
			return null;
		}

		return (FormMetadata) rst.get(0);
	}

	public List<FormMetadata> listAll() {
		Query q = em.createNamedQuery("listAll");

		List rst = q.getResultList();
		if (rst == null || rst.isEmpty()) {
			return Collections.emptyList();
		}

		return rst;
	}

	public List<FormMetadata> listAllByCategory(String category) {
		Query q = em.createNamedQuery("listAllByCategory");
		q.setParameter(1, category.toLowerCase());

		return q.getResultList();
	}

    public List<FormMetadata> listByParentForm(Long parentFormID) {
        Query q = em.createNamedQuery("listByParentForm");
        q.setParameter(1, parentFormID);

        return q.getResultList();
    }

    public List<FormMDStatisticsEntry> statisticsByStatus() {
        Query q = em.createNamedQuery("statisticsByStatus");

        return q.getResultList();
    }

    public void updateCategory(FormMetadata fm){
        Query q = em.createQuery("update table form_metadata set nav_category_id=? and seq_number=? where id=?");

        q.setParameter(0,fm.getNavCategoryId());
        q.setParameter(1,fm.getSeqNumber());
        q.setParameter(2,fm.getId());

        q.executeUpdate();
    }
}
