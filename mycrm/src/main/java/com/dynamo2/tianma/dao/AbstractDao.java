package com.dynamo2.tianma.dao;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import com.dynamo2.tianma.model.MongodbModel;
import com.dynamo2.tianma.model.ObjectField;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class AbstractDao  {
	protected static MongoClient mongoClient;
	protected static DB db;
	
	@SuppressWarnings("rawtypes")
	protected static Map<String,Class> INNER_TYPE = new HashMap<String,Class>();
	static {
		try {
			mongoClient = new MongoClient();
			db = mongoClient.getDB("tianma");
		} catch (UnknownHostException ue) {
		}
	}
	
	protected DBCollection getDBCollection(String name){
		return db.getCollection(name);
	}
	
	protected BasicDBObject getObjectId(String oid){
		return new BasicDBObject("_id",new ObjectId(oid));
	}
	
	protected <T> List<T> readCursor(DBCursor cursor,Class<T> cls){
		List<T> result = new ArrayList<T>();
		while(cursor.hasNext()){
			result.add(decode((BasicDBObject)cursor.next(),cls));
		}
		
		return result;
	}
	
	protected <T> T decode(BasicDBObject dbObj,Class<T> cls) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(cls);
			T result = cls.newInstance();
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
			for(PropertyDescriptor pd : pds){
				String dbName = SpellUtil.toUnderlineName(pd.getName());
				if(dbName.equalsIgnoreCase("id"))dbName = "_id";
				
				if(!dbObj.containsField(dbName))continue;

				Object value = dbObj.get(dbName);
				if(value instanceof BasicDBObject){
					value = decode((BasicDBObject)value,pd.getPropertyType());
				}else if(value instanceof BasicDBList){
					List vlist = new ArrayList();
					Class innerCls = INNER_TYPE.get(cls.getSimpleName()+"."+pd.getName());
					
					for(Object dbj:(BasicDBList)value){
						vlist.add(decode((BasicDBObject)dbj,innerCls));
					}
					
					value = vlist;
				}
				
				if(pd.getName().equalsIgnoreCase("id")){
					value = dbObj.getObjectId("_id");
				}
				
				pd.getWriteMethod().invoke(result,value);
			}
			
			return result;
		} catch (Exception e) {}
		
		return null;
	}
	
	protected BasicDBObject encode(Object obj) {
		BasicDBObject doc = new BasicDBObject();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
	        if(pds != null && pds.length>0){
	            for(PropertyDescriptor pd : pds){
	            	String dbName = SpellUtil.toUnderlineName(pd.getName());
	            	if(dbName.equalsIgnoreCase("class")) continue;
	            	
	            	try {
	            		Object value = pd.getReadMethod().invoke(obj);
	            		
	            		if(dbName.equalsIgnoreCase("id")){
	            			if(value == null)continue;
	            			
	            			dbName = "_id";
	            		}else if(value instanceof MongodbModel){
	            			value = encode(value);
	            		}else if(value instanceof Collection){
	            			List<Object> vl = new ArrayList<Object>();
							for(Object val : (Collection)value){
	            				if(val instanceof MongodbModel){
	            					val = encode(val);
	            				}
	            				
	            				vl.add(val);
	            			}
	            			
	            			value = vl;
	            		}
	            		doc.append(dbName,value);
	            	}catch(Exception e){
	            		doc.append(dbName,null);
	            	}
	            	
	            }
	        }
		} catch (IntrospectionException e1) {}
        
		return doc;
	}
}
