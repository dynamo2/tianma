package com.dynamo2.myerp.dynamicform.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.dynamicform.dao.entities.FormWorkflow;

@Repository("formWorkflowDAO")
public class FormWorkflowDAO extends AbstractBaseDAO<FormWorkflow> {

	/**
	 * List all work flow steps of form by form metadata id
	 * 
	 * @param formMetadataId
	 *            form metadata id
	 * @return Work flow steps
	 */
	public List<FormWorkflow> listWorkFlowSteps(Long formMetadataId) {
		Query q = em.createNamedQuery("listWorkFlowSteps");
		q.setParameter(1, formMetadataId);

		return q.getResultList();
	}

}
