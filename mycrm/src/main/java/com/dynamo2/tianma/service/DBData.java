package com.dynamo2.tianma.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bson.types.ObjectId;

public class DBData {
	public final static Map<String,Object> ObjectMetadataMap = new HashMap<String,Object>();
	public final static Map<String,Object> DBDataMap = new HashMap<String,Object>();
	
	
	static {
		initMetadata();
		initBrands();
		initProductType();
		initProduct();
		
		//System.out.println(DBDataMap.get("Product"));
	}
	
	public static void main(String[] args) {
		Random r = new Random();
		
		for(int i = 0; i < 10;i++){
			System.out.println(r.nextInt(10));
		}
	}
	
	
	/**
	 * 
	 * Product:[{name:滋阴乳液125ml,product_type:面霜/乳液,brand:雪花秀,price:368.00,picture:pic,description:aa},"
				+ "{name:弹力面霜75ml,product_type:面霜/乳液,brand:雪花秀,price:520.00,picture:pic,description:aa},"
				+ "{name:天气丹华泫平衡化妆水150ml,product_type:保湿水,brand:Whoo,price:478.00,picture:pic,description:aa}]
	 * **/
	private static void initProduct(){
		String ps = "Product:[{name:滋阴乳液125ml,product_type:面霜/乳液,brand:雪花秀,price:368.00,picture:pic,description:aa},"
				+ "{name:弹力面霜75ml,product_type:面霜/乳液,brand:雪花秀,price:520.00,picture:pic,description:aa},"
				+ "{name:天气丹华泫平衡化妆水150ml,product_type:保湿水,brand:Whoo,price:478.00,picture:pic,description:aa}]";
		
		Map<String,Object> data = MapHelper.curlyToMap(ps);
		DBDataMap.putAll(data);
		
		List<Map<String,Object>> products = (List<Map<String,Object>>)data.get("Product");
		for(Map<String,Object> product:products){
			product.put("id", new ObjectId());
			//product.put("production_date", new Date());
			//product.put("expiry_date", new Date());
			
			innerObject(product,"product_type","ProductType");
			innerObject(product,"brand","Brand");
		}
	}
	
	public static Map<String,Object> getRandom(String collSymbol){
		List<Map<String,Object>> products = (List<Map<String,Object>>)DBDataMap.get(collSymbol);
		if(products == null || products.size() == 0){
			return null;
		}

		return products.get(new Random().nextInt(products.size()));
	}
	
	private static void innerObject(Map<String,Object> obj,String fieldName,String collName){
		Object value = obj.get(fieldName);
		value = get(collName,"name",value);
		obj.put(fieldName, value);
	}
	
	public static Map<String,Object> get(String collection,String key,Object value){
		List<Map<String,Object>> list = (List<Map<String,Object>>)DBDataMap.get(collection);
		for(Map<String,Object> obj:list){
			if(value.equals(obj.get(key))){
				return obj;
			}
		}
		
		return null;
	}
	
	private static void initBrands(){
		String[] bnames = new String[]{"海蓝之谜","sisley","雪花秀","黛珂","Whoo","pola","科莱丽"};
		List<Map<String,Object>> brands = new ArrayList<Map<String,Object>>();
		DBDataMap.put("Brand", brands);
		
		for(int i = 0; i < bnames.length; i++){
			ObjectId id = new ObjectId();
			Map<String,Object> brand = new HashMap<String,Object>();
			brand.put("id", id);
			brand.put("name", bnames[i]);
			
			brands.add(brand);
		}
	}
	
	private static void initProductType(){
		String[] tnames = new String[]{"洁面","卸妆","爽肤水","保湿水","精华","面霜/乳液","眼霜","面膜","去角质产品"};
		List<Map<String,Object>> types = new ArrayList<Map<String,Object>>();
		DBDataMap.put("ProductType", types);
		
		for(int i = 0; i < tnames.length; i++){
			ObjectId id = new ObjectId();
			Map<String,Object> type = new HashMap<String,Object>();
			type.put("id", id);
			type.put("name", tnames[i]);
			
			types.add(type);
		}
	}
	
	/**
	 * Product {
	 *   _id:objectId,
	 * 	 name:'Product',
	 * 	 fields:[{name:name,type:string,label:名称,unique:{scope:brand}},
	 *       {name:product_type,type:object,related:ProductType,label:分类,edit:{type:select}},
	 *       {name:brand,type:object,related:Brand,label:品牌},
	 *       {name:price,type:double,label:价格},
	 *       {name:production_date,type:date,label:生产日期},
	 *       {name:expiry_date,type:date,label:有效期},
	 *       {name:picture,type:string,label:图片,edit:{type:file}},
	 *       {name:description,type:string,label:描述,edit:{type:html}}
	 * 	 ]
	 * }
	 * **/
	private static void initMetadata(){
		String product = "{_id:objectId,name:Product,"
				+ "fields:[{name:name,type:string,label:名称,unique:{scope:brand}},"
				+ "{name:product_type,type:object,related:ProductType,label:分类,edit:{type:select}},"
				+ "{name:brand,type:object,related:Brand,label:品牌,edit:{type:select}},"
				+ "{name:price,type:double,label:价格},"
				+ "{name:production_date,type:date,label:生产日期},"
				+ "{name:expiry_date,type:date,label:有效期},"
				+ "{name:picture,type:string,label:图片,edit:{type:file}},"
				+ "{name:description,type:string,label:描述,edit:{type:html}}]}";
		ObjectMetadataMap.put("Product", MapHelper.curlyToMap(product));
		
		String brand = "{_id:objectId,name:Brand,fields:[{name:name,type:string,label:名称}]}";
		ObjectMetadataMap.put("Brand", MapHelper.curlyToMap(brand));
		
		String productType =" {_id:objectId,name:ProductType,fields:[{name:name,type:string,label:名称}]}";
		ObjectMetadataMap.put("ProductType", MapHelper.curlyToMap(productType));
	}
}
