package com.dynamo2.myerp.dynamicform.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.FormFieldMetadata;

@Repository("formFieldMetadataDAO")
public class FormFieldMetadataDAO extends AbstractBaseDAO<FormFieldMetadata> {

	public List<FormFieldMetadata> listFieldsOfFormMetadata(Long formMetadataId) {
		Query q = em.createNamedQuery("listFieldsOfForm");
		q.setParameter(1, formMetadataId);

		return q.getResultList();
	}

	public String findUnusedField(Long formMetadataId, String fieldType) {
		Query q = em.createNativeQuery("{ call dynamic_form_find_unused_field(?,?) }");

		q.setParameter(1, formMetadataId);
		q.setParameter(2, fieldType);

		Object[] rst = (Object[]) q.getSingleResult();
		String errCode = rst[0].toString();
		String columnName = rst[1].toString();
		if ("ok".equals(errCode)) {
			return columnName;
		} else {
			return null;
		}
	}

	public FormFieldMetadata findFieldByFormMetadataIdAndFieldName(Long formMetadataId, String fieldName) {
		Query q = em.createNamedQuery("findFieldByFormMetadataIdAndFieldName");
		q.setParameter(1, formMetadataId);
		q.setParameter(2, fieldName);

		List rst = q.getResultList();
		if (rst != null && !rst.isEmpty()) {
			return (FormFieldMetadata) rst.get(0);
		}

		return null;
	}
}
