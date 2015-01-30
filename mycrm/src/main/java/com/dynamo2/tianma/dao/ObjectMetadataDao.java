package com.dynamo2.tianma.dao;

import java.util.ArrayList;
import java.util.List;

import com.dynamo2.tianma.model.ObjectField;
import com.dynamo2.tianma.model.ObjectMetadata;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class ObjectMetadataDao  extends AbstractDao {

	private static String COLLECTION_NAME = "object_metadata";
	
	static {
		INNER_TYPE.put("ObjectMetadata.fields",ObjectField.class);
	}
	
	private DBCollection getDBCollection(){
		return this.getDBCollection(COLLECTION_NAME);
	}

	public void save(ObjectMetadata om) {
		DBCollection coll = this.getDBCollection();
		
		BasicDBObject doc = encode(om);

		coll.save(doc);
	}
	
	public List<ObjectMetadata> list(){
		DBCollection coll = this.getDBCollection();
		DBCursor cursor = coll.find();
		
		return readCursor(cursor,ObjectMetadata.class);
	}
	
	public static void main(String[] args) {
		ObjectMetadata om = new ObjectMetadata();
		om.setName("订单2");
		om.setUniqueMark("order");

		List<ObjectField> fields = new ArrayList<ObjectField>();

		ObjectField of = new ObjectField();
		of.setName("订单号2");
		of.setUniqueMark("order_num");
		fields.add(of);

		of = new ObjectField();
		of.setName("下单人2");
		of.setUniqueMark("user");
		fields.add(of);

		of = new ObjectField();
		of.setName("订单总价2");
		of.setUniqueMark("price");
		fields.add(of);

		om.setFields(fields);

		ObjectMetadataDao dao = new ObjectMetadataDao();
		dao.list();
	}
	
	
}
