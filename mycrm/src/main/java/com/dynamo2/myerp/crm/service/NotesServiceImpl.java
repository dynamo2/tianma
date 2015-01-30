package com.dynamo2.myerp.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.dynamo2.myerp.AbstractBaseDAO;
import com.dynamo2.myerp.AbstractBaseService;
import com.dynamo2.myerp.crm.dao.NotesDAO;
import com.dynamo2.myerp.crm.dao.entities.Notes;

@Service("notesService")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class NotesServiceImpl extends AbstractBaseService<Notes> implements NotesService {

	@Autowired
	private NotesDAO notesDAO;

	public List<Notes> listAll(Long forId, String forType) {
		return notesDAO.listAllByForId(forId, forType);
	}

	@Override
	protected AbstractBaseDAO<Notes> getDao() {
		return notesDAO;
	}
}
