package com.dynamo2.tianma.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBService {
	private String collection;
	private List<Map<String,Object>> collections = null;
	
	public DBService(String coll) {
		this.collection = coll;
		collections = (List<Map<String,Object>>)DBData.DBDataMap.get(this.collection);
		if(collections == null){
			collections = new ArrayList<Map<String,Object>>();
		}
	}
	
	public Map<String,Object> get(String id){
		if(id == null || id.trim().isEmpty()){
			return null;
		}
		
		id = id.trim();
		for(Map<String,Object> coll : collections){
			if(coll.get("id").equals(id)){
				return coll;
			}
		}
		
		return null;
	}
	
	public List<Map<String,Object>> query(){
		return this.collections;
	}
	
	public static void main(String[] args) {
		DBService db = new DBService("ProductType");
		
		System.out.println("query = "+db.invoke("query", null));
	}
	
	public Object invoke(String method,Object value){
		if("get".equals(method)){
			String v = value==null?null:value.toString();
			return this.get(v);
		}
		
		if("query".equals(method) || "search".equals(method)){
			return this.query();
		}
		
		/*
		try {
			Class vc = value == null?null:value.getClass();
			Method md = DBService.class.getMethod(method,vc);
			return md.invoke(this,value);
		} catch (Exception e) {}
		*/
		
		return null;
	}
}
