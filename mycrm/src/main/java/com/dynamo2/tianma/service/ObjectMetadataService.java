package com.dynamo2.tianma.service;

import java.util.List;

import com.dynamo2.tianma.dao.ObjectMetadataDao;
import com.dynamo2.tianma.model.ObjectMetadata;

public class ObjectMetadataService {
	private ObjectMetadataDao dao = new ObjectMetadataDao();
	
	public List<ObjectMetadata> list(){
		return dao.list();
	}
}
