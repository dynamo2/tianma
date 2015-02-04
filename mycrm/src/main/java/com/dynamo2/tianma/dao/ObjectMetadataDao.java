package com.dynamo2.tianma.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;

import com.dynamo2.tianma.model.MongodbModel;
import com.dynamo2.tianma.model.ObjectField;
import com.dynamo2.tianma.model.ObjectMetadata;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ObjectMetadataDao  extends AbstractDao {

	private static String COLLECTION_NAME = "object_metadata";
	private DBCollection dbColl = null;
	
	static {
		INNER_TYPE.put("ObjectMetadata.fields",ObjectField.class);
	}
	
	public ObjectMetadataDao(){
		dbColl = this.getDBCollection();
	}
	
	private DBCollection getDBCollection(){
		return this.getDBCollection(COLLECTION_NAME);
	}

	public void save(ObjectMetadata om) {
		BasicDBObject doc = encode(om);
		this.dbColl.save(doc);
	}
	
	public BasicDBObject getByID(String oid){
		DBCursor cursor = this.dbColl.find(this.getObjectId(oid));
		while(cursor.hasNext()){
			return (BasicDBObject)cursor.next();
		}
		
		return null;
	}
	
	public List<DBObject> find(BasicDBObject query){
		DBCursor cursor = this.dbColl.find(query);
		return cursor.toArray();
	}
	
	public void saveFields(String oid,ObjectField field) {
		if(this.isDuplicateMark(oid, field)){
			throw new RuntimeException("Is duplicate field.name");
		}
		
		if(field.getId() == null) {
			field.setId(new ObjectId());
			
			BasicDBObject doc = encode(field);
			this.dbColl.update(this.getObjectId(oid), new BasicDBObject("$push",new BasicDBObject("fields",doc)));
		}else {
			BasicDBObject doc = encode(field);
			this.dbColl.update(new BasicDBObject("fields._id",field.getId()), new BasicDBObject("$set",new BasicDBObject("fields.$",doc)));
		}
	}
	
	public List<ObjectMetadata> list(){
		DBCursor cursor = this.dbColl.find();
		
		return readCursor(cursor,ObjectMetadata.class);
	}
	
	public static void main(String[] args) {
		ObjectMetadataDao dao = new ObjectMetadataDao();
		dao.testFind();
		
		System.out.println(UUID.randomUUID().toString().length());
	}
	
	private void testFind(){
		//BasicDBObject doc = (BasicDBObject)this.dbColl.find(new BasicDBObject("id", 10)).toArray().get(0);
		//BasicDBObject doc = (BasicDBObject)this.dbColl.find(this.getObjectId("54c9e4b6fc778a8689446ce7")).toArray().get(0);

		//BasicDBObject bc = (BasicDBObject)this.dbColl.find(new BasicDBObject("fields.unique_mark","user")).toArray().get(0);
		//System.out.println(bc);

		System.out.println(this.getByID("54c9e4b6fc778a8689446ce7"));
		ObjectField of = new ObjectField();
		//of.setId(new ObjectId("54d0371d50f1324ab162ba7a"));
		of.setName("zerofdsa345");
		of.setUniqueMark("322100");
		
		//this.saveFields("54c9e4b6fc778a8689446ce7", of);
		
		//System.out.println(this.getByID("54c9e4b6fc778a8689446ce7"));
		
		System.out.println(isDuplicateMark("54c9e4b6fc778a8689446ce7",of));
	}
	
	/**
	 * 判断fields.unique_mark是否有重复的值
	 * **/
	protected boolean isDuplicateMark(String oid,ObjectField field){
		return isDuplicate(oid,"fields","unique_mark",field.getUniqueMark(),field);
	}
	
	protected boolean isDuplicate(String oid,String parentKey,String fieldKey,String fieldValue,MongodbModel model){
		BasicDBObject doc = new BasicDBObject("_id",new ObjectId(oid));
		doc.put(parentKey+"."+fieldKey, fieldValue);
		
		BasicDBObject incKey = new BasicDBObject(parentKey+".$",1);
		List<DBObject> results = this.dbColl.find(doc,incKey).toArray();
		
		if(results.size() == 0)return false;
		
		BasicDBObject result = (BasicDBObject)results.get(0);
		if(!result.containsField(parentKey))return false;
		
		List<BasicDBObject> fields = (List<BasicDBObject>)result.get(parentKey);
		if(fields == null || fields.size() == 0){
			return false;
		}
		
		ObjectField oldField = this.decode(fields.get(0), ObjectField.class);
		if(oldField.getId().equals(model.getId())) {
			return false;
		}
		return oldField.getName().equalsIgnoreCase(fieldValue);
	}
	
	private void testSave(){
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
		
		this.save(om);
	}
	
	
}
