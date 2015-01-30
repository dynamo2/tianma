package com.dynamo2.myerp.crm.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.crm.dao.entities.Notes;

@Repository("notesDAO")
public class NotesDAO extends AbstractBaseDAO<Notes> {

	@SuppressWarnings("unchecked")
	public List<Notes> listAllByForId(Long forId, String forType) {
		Query query = em.createQuery("from Notes n where n.forId=? and n.forType=? order by n.created desc");
		query.setParameter(1, forId);
		query.setParameter(2, forType);

		return query.getResultList();
	}

}
