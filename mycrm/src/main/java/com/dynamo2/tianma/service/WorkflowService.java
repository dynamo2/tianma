package com.dynamo2.tianma.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import org.bson.types.ObjectId;

public class WorkflowService {
	public static Map<String,Object> ObjectMetadataMap = new HashMap<String,Object>();
	static {
		initObjectMetadataMap();
	}
	
	/**
	 * NewProduct {
	 *   _id:objectId,
	 *   name:'new product',
	 *   flows:[{step:1,forms:[
	 *            {name:'product.name',type:'text'},
	 *            {name:'product.brand',type:'select',
	 *               values:{
	 *              	list:db.Brand.query({name:''}),
	 *              	key:object.id,
	 *                  value:object.name}
	 *            },
	 *            {name:'product.price',type:'text'},
	 *            {name:'product.expiry_date',type:'text'},
	 *            {name:'product.picture',type:'text'}]},
	 *       {step:2,function:db.Product.save(request.product)}
	 *   ]
	 * }
	 * 
	 * 
	 * EditProduct {
	 *   _id:objectId,
	 *   name:'edit product',
	 *   flows:[{step:1,
	 *   		datas:[{product:db.Product.get(request.id?)}],
	 *   		forms:[
	 *            {name:'product.id',type:'hidden',value:product.id},
	 *            {name:'product.name',type:'text',value:product.name},
	 *            {name:'product.brand',type:'select',value:product.brand,
	 *               values:{
	 *              	list:db.Brand.query({name:''}),
	 *              	key:object.id,
	 *                  value:object.name}
	 *            },
	 *            {name:'product.price',type:'text',value:product.price},
	 *            {name:'product.expiry_date',type:'text',value:product.expiry_date},
	 *            {name:'product.picture',type:'text',value:product.picture}],
	 *           response:'forms'},
	 *       {step:2,function:db.Product.save(request.product)}
	 *   ]
	 * }
	 * 
	 * SearchProduct {
	 *   _id:objectId,
	 *   name:'search product',
	 *   flows:[{step:1,
	 *   		datas:[{product_list:db.Product.search({name $like request.product.name 
	 *          				and brand $eq request.product.brand 
	 *          				and price $gte request.product.min_price 
	 *          				and price $lte request.product.max_price
	 *          				and expiry_date $gte request.product.min_expiry_date?
	 *          				and expiry_date $lte request.product.min_expiry_date?}),
	 *          		brand_list:db.Brand.search({name $eq 'ww'})}],
	 *   		forms:[
	 *            {name:'product.id',type:'hidden',value:product.id},
	 *            {name:'product.name',type:'text',value:product.name},
	 *            {name:'product.brand',type:'select',value:product.brand,
	 *               values:{
	 *              	data:brand_list,
	 *              	key:brand_list.object.id,
	 *                  value:brand_list.object.name}
	 *            },
	 *            {name:'product.min_price',type:'text',value:product.min_price},
	 *            {name:'product.max_price',type:'text',value:product.max_price},
	 *            {name:'product.expiry_date',type:'text',value:product.expiry_date},
	 *            ],
	 *           list_table:{data:list,page:true,
	 *           	columns:[{label:'名称',value:list.object.name},
	 *           		{label:'品牌',value:list.object.brand.name},
	 *           		{label:'价格',value:list.object.price},
	 *           		{label:'有效期',value:list.object.expiry_date}]
	 *           }
	 *           response:[forms,list_table]},
	 *       {step:2,function:db.Product.save(request.product)}
	 *   ]
	 * }
	 * 
	 * DetailProduct {
	 *   _id:objectId,
	 *   name:'search product',
	 *   flows:[{step:1,
	 *   		datas:[{list:db.Product.search(request.product)}],
	 *   		forms:[
	 *            {name:'product.id',type:'hidden',value:product.id},
	 *            {name:'product.name',type:'text',value:product.name},
	 *            {name:'product.brand',type:'select',value:product.brand,
	 *               values:{
	 *              	list:db.Brand.query({name:''}),
	 *              	key:object.id,
	 *                  value:object.name}
	 *            },
	 *            {name:'product.price',type:'text',value:product.price},
	 *            {name:'product.expiry_date',type:'text',value:product.expiry_date},
	 *            {name:'product.picture',type:'text',value:product.picture}],
	 *           responce:'forms'},
	 *       {step:2,function:db.Product.save(request.product)}
	 *   ]
	 * }
	 * 
	 * DeleteProduct {
	 *   _id:objectId,
	 *   name:'delete product',
	 *   flows:[{step:1,
	 *   		function:product:db.Product.delete(request.id),
	 *          responce:SearchProduct}]
	 * }
	 * 
	 * OrderDetail {
	 *   _id:objectId,
	 *   name:'order detail',
	 *   flows:[{step:1,
	 *   		datas:[{order:db.Order.search({id $eq request.order.id}),
	 *          		member:db.Member.search({id $eq order.member.id})}],
	 *   		forms:[
	 *            {name:'product.id',type:'hidden',value:product.id},
	 *            {name:'product.name',type:'text',value:product.name},
	 *            {name:'product.brand',type:'select',value:product.brand,
	 *               values:{
	 *              	data:brand_list,
	 *              	key:brand_list.object.id,
	 *                  value:brand_list.object.name}
	 *            },
	 *            {name:'product.min_price',type:'text',value:product.min_price},
	 *            {name:'product.max_price',type:'text',value:product.max_price},
	 *            {name:'product.expiry_date',type:'text',value:product.expiry_date},
	 *            ],
	 *           list_table:{data:list,page:true,
	 *           	columns:[{label:'名称',value:list.object.name},
	 *           		{label:'品牌',value:list.object.brand.name},
	 *           		{label:'价格',value:list.object.price},
	 *           		{label:'有效期',value:list.object.expiry_date}]
	 *           }
	 *           response:[forms,list_table]},
	 *       {step:2,function:db.Product.save(request.product)}
	 *   ]
	 * }
	 * 
	 * SendOrder {
	 *   _id:objectId,
	 *   name:'order send',
	 *   flows:[{step:1,
	 *   		datas:[{order:db.Order.search({id $eq request.order.id}),
	 *          		member:db.Member.search({id $eq order.member.id})}],
	 *   		forms:[
	 *            {name:'product.id',type:'hidden',value:product.id},
	 *            {name:'product.name',type:'text',value:product.name},
	 *            {name:'product.brand',type:'select',value:product.brand,
	 *               values:{
	 *              	data:brand_list,
	 *              	key:brand_list.object.id,
	 *                  value:brand_list.object.name}
	 *            },
	 *            {name:'product.min_price',type:'text',value:product.min_price},
	 *            {name:'product.max_price',type:'text',value:product.max_price},
	 *            {name:'product.expiry_date',type:'text',value:product.expiry_date},
	 *            ],
	 *           list_table:{data:list,page:true,
	 *           	columns:[{label:'名称',value:list.object.name},
	 *           		{label:'品牌',value:list.object.brand.name},
	 *           		{label:'价格',value:list.object.price},
	 *           		{label:'有效期',value:list.object.expiry_date}]
	 *           }
	 *           response:[forms,list_table]},
	 *       {step:2,function:db.Product.save(request.product)}
	 *   ]
	 * }
	 * **/
	public static void main() {
		Map<String,Object> newProduct = new HashMap<String,Object>();
		newProduct.put("_id", new ObjectId());
	}
	
	/**
	 * 
	 * ==== field标签解析：====
	 * name:属性名
	 * type:属性类型
	 * label:页面标签名称
	 * unique:属性值是否唯一
	 *     true：唯一，作用域：全表
	 *     {scope:在作用域内唯一}，如{scope:'brand'}，在同一brand值下，该属性值唯一
	 * edit:编辑选型
	 * 		type：
	 *          text:input type=text
	 *          button:input type=button
	 *          textarea:textarea
	 *          html:html editor
	 *          select:select
	 *          radio:input type=radio
	 *          checkbox:input type=checkbox
	 *          待续...
	 * 		value：
	 * 		label：
	 * 
	 * 
	 * === Collection Metadata ===
	 * 产品：ID，名称，分类，品牌，价格，生产日期，有效期，图片，描述
	 * Product {
	 *   _id:objectId,
	 * 	 name:'Product',
	 * 	 fields:[{name:'name',type:'string',label:'名称',unique:{scope:'brand'}},
	 *       {name:'product_type',type:'object',related:'ProductType',label:'分类',edit:{type:select}},
	 *       {name:'brand',type:'object',related:'Brand',label:'品牌'},
	 *       {name:'price',type:'double',label:'价格'},
	 *       {name:'production_date',type:'date',label:'生产日期'},
	 *       {name:'expiry_date',type:'date',label:'有效期'},
	 *       {name:'picture',type:'string',label:'图片',edit_type:'file'},
	 *       {name:'description',type:'string',label:'描述',edit_type:'html'}
	 * 	 ]
	 * }
	 * 
	 * 品牌：ID，名称
	 * Brand {
	 *   _id:objectId,
	 *   name:'Brand',
	 *   fields:[{name:'name',type:'string',label:'名称'}]
	 * }
	 * 
	 * 商品分类：ID，名称
	 * ProductType {
	 *   _id:objectId,
	 *   name:'ProductType',
	 *   fields:[{name:'name',type:'string',label:'名称'}]
	 * }
	 * 
	 * 会员：ID，用户名，密码，真实姓名，生日，手机号码，email，会员等级
	 * User {
	 *   _id:objectId,
	 *   name:'User',
	 *   fields:[{name:'user_name',type:'string',label:'用户名'},
	 *   {name:'password',type:'string',label:'密码'},
	 *   {name:'real_name',type:'string',label:'真实姓名'},
	 *   {name:'birthday',type:'date',label:'真实姓名'},
	 *   {name:'mobile',type:'string',label:'手机号码'},
	 *   {name:'email',type:'string',label:'email'},
	 *   {name:'user_grade',type:'object',related:'UserGrade',label:'会员等级',edit:{type:'radio',value:'id',label:'name'}}]
	 * }
	 * 
	 * 会员等级：ID，名称，商品折扣
	 * UserGrade {
	 *   _id:objectId,
	 *   name:'UserGrade',
	 *   fields:[{name:'name',type:'string',label:'等级 名称'},
	 *   {name:'discount',type:'double',label:'商品折扣'}]
	 * }
	 * 
	 * 
	 * 
	 * 产品：ID，名称，分类，品牌，价格，生产日期，有效期，图片，描述
	 * 品牌：ID，名称
	 * 分类：ID，名称
	 * 会员：ID，用户名，密码，真实姓名，生日，手机号码，email，会员等级
	 * 客户：ID，公司名称，联系人
	 * 收货地址：ID，会员ID，省，市，区，详细地址，是否默认
	 * 订单：ID，订单号，收货地址，明细，订购时间，状态，支付状态，支付类型
	 *   订单明细：ID，商品名称，订购数量，已发货数量
	 * 支付方式：ID，名称
	 * 会员等级：ID，名称，商品折扣
	 * 发货单：
	 * 仓库：
	 * 
	 * 
	 * 
	 * === Function ===
	 * 商品管理
	 *   发布商品，编辑商品，删除商品，商品列表，商品详情
	 * 订单管理
	 *   订单列表，订单详情，发货
	 *   
	 *   
	 * 扩展内容：
	 *   常规字段：ID，create_date，creater，modify_date,modifier
	 *   操作日志
	 * */
	public static void initObjectMetadataMap() {
		Map<String,Object> product = new HashMap<String,Object>();
		product.put("_id", new ObjectId());
		product.put("name", "Product");
		
		List<Map> fields = new ArrayList<Map>();
		Map<String,Object> field = new HashMap<String,Object>();
		field.put("name", "name");
		field.put("type", "string");
		fields.add(field);
		
		field = new HashMap<String,Object>();
		field.put("name", "brand");
		field.put("type", "object");
		field.put("related", "Brand");
		field.put("redundance", new String[]{"id","name"});
		fields.add(field);
		
		field = new HashMap<String,Object>();
		field.put("name", "price");
		field.put("type", "double");
		fields.add(field);
		
		field = new HashMap<String,Object>();
		field.put("name", "expiry_date");
		field.put("type", "date");
		fields.add(field);
		
		field = new HashMap<String,Object>();
		field.put("name", "picture");
		field.put("type", "string");
		fields.add(field);
		
		product.put("fields", fields);
		ObjectMetadataMap.put("Product", product);
		
		Map<String,Object> brand = new HashMap<String,Object>();
		fields = new ArrayList<Map>();
		field = new HashMap<String,Object>();
		field.put("name", "name");
		field.put("type", "string");
		fields.add(field);
		
		brand.put("_id", new ObjectId());
		brand.put("name", "Brand");
		brand.put("fields", fields);
		ObjectMetadataMap.put("Brand", brand);
	}
	
	
	public static void main(String[] args) {
		edit();
	}
	
	/***
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
	 * 
	 * 
	 * EditProduct {
	 *   _id:objectId,
	 *   name:'edit product',
	 *   flows:[{step:1,flow:EditProductFlow1.id},
	 *       {step:2,flow:EditProductFlow2.id}
	 *   ]
	 * }
	 * 
	 * EditProductFlow1 {
	 *   datas:[{product:db.Product.get(request.id)?request.id,brands:db.Brand.search()}],
	 *   type:edit,
	 *   collection:Product,
	 *   form:{data:product,
	 *   	additions:[{field:brand,values:{list:brands,key:object.id,value:object.name}}]}
	 * }
	 * 
	 * EditProductFlow2 {
	 *   function:db.Product.save(request.Product)
	 * }
	 * 
	 * 
	 * **/
	public static void edit(){
		String str = "{datas:{product:db.Product.get(request.id)?request.id,brands:db.Brand.query(),"
						+ "types:db.ProductType.search()},"
						+ "type:edit,collection:Product,form:{data:product,"
						+ "additions:{brand:{values:{list:brands,key:id,value:name}},"
						+ "product_type:{values:{list:types,key:id,value:name}}}]}}";
		Map<String,Object> editProductFlow1 = MapHelper.curlyToMap(str);
		
		Map<String,Object> product = DBData.getRandom("Product");
		System.out.println(product);
		
		//Map<String,Object> params = MapHelper.curlyToMap("{request:{id:"+product.get("id")+"}}");
		Map<String,Object> params = MapHelper.curlyToMap("{request:{id:"+product.get("id")+"}}");
		readFlow(editProductFlow1,params);
	}
	
	/**
	 * SearchProduct {
	 *   _id:objectId,
	 *   name:'search product',
	 *   flows:[{step:1,
	 *   		datas:[{product_list:db.Product.search({name $like request.product.name 
	 *          				and brand $eq request.product.brand 
	 *          				and price $gte request.product.min_price 
	 *          				and price $lte request.product.max_price
	 *          				and expiry_date $gte request.product.min_expiry_date?
	 *          				and expiry_date $lte request.product.min_expiry_date?}),
	 *          		brand_list:db.Brand.search({name $eq 'ww'})}],
	 *   		forms:[data:request.product,
	 *            {name:'product.id',type:'hidden',value:product.id},
	 *            {name:'product.name',type:'text',value:product.name},
	 *            {name:'product.brand',type:'select',value:product.brand,
	 *               values:{list:brand_list,key:id,value:name}
	 *            },
	 *            {name:'product.min_price',type:'text',value:product.min_price},
	 *            {name:'product.max_price',type:'text',value:product.max_price},
	 *            {name:'product.expiry_date',type:'text',value:product.expiry_date},
	 *            ],
	 *           list_table:{data:list,page:true,
	 *           	columns:[{label:'名称',value:list.object.name},
	 *           		{label:'品牌',value:list.object.brand.name},
	 *           		{label:'价格',value:list.object.price},
	 *           		{label:'有效期',value:list.object.expiry_date}]
	 *           }
	 *           response:[forms,list_table]}
	 *   ]
	 * }
	 * **/
	public static void search(){
		
	}
	
	
	/**
	 * search_form:{view:form,type:search,
	 * 		form:{name:product.searchForm},
	 *      inputs:[{name:fid,type:hidden,value:request.fid},
	 *            {name:product.name,type:text,label:名称,value:request.product.name},
	 *            {name:product.brand,type:select,label:品牌,value:request.product.brand,
	 *               	values:{list:brand_list,key:id,value:name}},
	 *            {name:product.price,type:text,operator:between,value:request.product.price},
	 *            {name:'product.expiry_date',type:'text',value:request.product.expiry_date}]}
	 * **/
	public static void responseForm(){
		String str = "forms:[data:request.product,"
				+ "{name:'product.id',type:'hidden',value:product.id},"
				+ "{name:'product.name',type:'text',label:名称,value:product.name},"
				+ "{name:'product.brand',type:'select',label:,value:product.brand,"
					+ "values:{list:brand_list,key:id,value:name}},"
				+ "{name:'product.min_price',type:'text',value:product.min_price},"
				+ "{name:'product.max_price',type:'text',value:product.max_price},"
				+ "{name:'product.expiry_date',type:'text',value:product.expiry_date}]";
	}
	
	/**
	 * EditProductFlow1 {
	 *   datas:{product:db.Product.get(request.id)?request.id,
	 *   		brands:db.Brand.query(),
	 *   		types:db.ProductType.search()},
	 *   type:edit,
	 *   collection:Product,
	 *   form:{data:product,
	 *   	additions:{brand:{values:{list:brands,key:id,value:name}},
	 *   		product_type:{values:{list:types,key:id,value:name}}}]}
	 * }
	 * **/
	private static void readFlow(Map<String,Object> flow,Map<String,Object> params){
		Map<String,Object> datas = null;
		if(flow.containsKey("datas")){
			datas = (Map<String,Object>)flow.get("datas");
			readDatas(datas,params);
		}

		String formJson = readForm(flow,params);
		System.out.println(formJson);
	}
	
	private static String readForm(Map<String,Object> flow,Map<String,Object> params){
		Map<String,Object> datas = (Map<String,Object>)flow.get("datas");
		Object v = flow.get("type");
		if(v != null){
			String type = (String)v;
			if(type.equals("edit")){
				List json = new ArrayList();
				
				String dataKey = (String)MapHelper.readValue(flow, "form.data");
				Map<String,Object> editCollection = (Map<String,Object>)MapHelper.readValue(datas,dataKey);
				
				String collection = (String)flow.get("collection");
				Map<String,Object> collMetadata = (Map<String,Object>)DBData.ObjectMetadataMap.get(collection);
				List<Map<String,Object>> fields = (List<Map<String,Object>>)collMetadata.get("fields");
				for(Map<String,Object> field : fields){
					Map<String,Object> fieldJson = new HashMap<String,Object>();
					
					Map<String,Object> fieldInfo = new HashMap<String,Object>();
					String fieldName = (String)field.get("name");
					Object fieldValue = null;
					if(editCollection != null){
						fieldValue = editCollection.get(fieldName);
					}
					
					fieldInfo.put("name", collection+"."+fieldName);
					if(fieldValue != null){
						if("object".equals(field.get("type"))){
							fieldValue = ((Map<String,Object>)fieldValue).get("id").toString();
						}
						fieldInfo.put("value", fieldValue);
					}
					
					fieldJson.put("label", field.get("label"));
					fieldJson.put("info", fieldInfo);

					String inputType = (String)MapHelper.readValue(field, "edit.type","text");
					if("select".equals(inputType)){
						inputType = "select";
						fieldInfo.put("options",readOptions(flow,fieldName));
					}else if("radio".equals(v)){
						inputType = "radio";
					}else if("checkbox".equals(v)){
						inputType = "checkbox";
					}
					
					fieldJson.put("type", inputType);
					json.add(fieldJson);
				}

				if(editCollection != null && editCollection.get("id") != null){
					String str = "{type:hidden,info:{name:"+collection+".id,value:"+editCollection.get("id")+"}}";
					json.add(MapHelper.curlyToMap(str));
				}

				//System.out.println(JSONArray.fromObject(json).toString());
				return JSONArray.fromObject(json).toString();
			}
		}
		return null;
	}
	
	private static List<Map<String,Object>> readOptions(Map<String,Object> flow,String fieldName){
		Map<String,Object> addition = (Map<String,Object>)MapHelper.readValue(flow, "form.additions."+fieldName);
		
		String dataKey = (String)MapHelper.readValue(addition, "values.list");
		List<Map<String,Object>> list = (List<Map<String,Object>>)MapHelper.readValue(flow, "datas."+dataKey);
		List<Map<String,Object>> options = new ArrayList<Map<String,Object>>();
		if(list == null || list.size()== 0){
			return options;
		}
		
		Object key = MapHelper.readValue(addition, "values.key","id");
		Object value = MapHelper.readValue(addition, "values.value","name");
		for(Map<String,Object> obj:list){
			Map option = new HashMap();
			
			option.put("key", obj.get(key).toString());
			option.put("label", obj.get(value).toString());
			
			options.add(option);
		}
		return options;
	}
	
	private static void readDatas(Map<String,Object> datas,Map<String,Object> params){
		for(Map.Entry<String, Object> entry : datas.entrySet()){
			String expression = (String)entry.getValue();
			if(expression.contains("?")){
				String[] sa = expression.split("\\?");
				String condition = sa[1];
				
				if(MapHelper.readValue(params,condition) == null){
					entry.setValue(null);
					params.put(entry.getKey(),null);
					
					continue;
				}
				
				expression = sa[0];
			}
			
			StringBuffer sbf = null;
			boolean readAll = false;
			List<String> keys = new ArrayList<String>();
			for(int i = 0; i < expression.length(); i++){
				char c = expression.charAt(i);
				
				if(!readAll && c == '.'){
					if(sbf != null){
						keys.add(sbf.toString());
						sbf = null;
						
						continue;
					}
				}
				
				if(c == '('){
					if(sbf != null){
						keys.add(sbf.toString());
						readAll = true;
						sbf = null;
						
						continue;
					}
				}
				
				if(c == ')'){
					if(sbf != null){
						keys.add(sbf.toString());
						readAll = false;
						sbf = null;
						
						continue;
					}
				}
				
				if(sbf == null){
					sbf = new StringBuffer();
				}
				sbf.append(c);
			}
			
			Object value = null;
			if(keys.get(0).equals("db")){
				String collection = keys.get(1);
				String method = keys.get(2);
				String parameter = null;	
				Object paramValue = null;
				if(keys.size() == 4){
					parameter = keys.get(3);
					paramValue = MapHelper.readValue(params,parameter);
				}
				
				DBService dbService = new DBService(collection);
				value = dbService.invoke(method, paramValue);
			}
			
			params.put(entry.getKey(), value);
			entry.setValue(value);
		}
	}
}
