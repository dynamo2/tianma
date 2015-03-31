package com.dynamo2.tianma.dao;

import java.util.Map;

import com.dynamo2.tianma.model.ObjectMetadata;
import com.mongodb.BasicDBObject;

public class BasicDao extends AbstractDao{
	private static String OBJECT_METADATA_KEY = "_object_metadata_";
	private ObjectMetadataDao metaDao = new ObjectMetadataDao();
	
	public void save(BasicDBObject doc,String collName) {
		this.getDBCollection(collName).save(doc);
	}
	
	public BasicDBObject buildBasicDBObject(Map<String,Object> datas,String objectUniqueMark){
		//String collName = (String)datas.get(OBJECT_METADATA_KEY);
		
		ObjectMetadata metadata = this.metaDao.getByUniqueMark(objectUniqueMark);
		
		return null;
	}
}
