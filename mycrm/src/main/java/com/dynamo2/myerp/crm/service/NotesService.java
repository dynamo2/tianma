package com.dynamo2.myerp.crm.service;

import java.util.List;

import com.dynamo2.myerp.AbstractBaseServiceInterface;
import com.dynamo2.myerp.crm.dao.entities.Notes;

public interface NotesService extends AbstractBaseServiceInterface<Notes> {

	public List<Notes> listAll(Long forId, String forType);

}
