package com.dynamo2.tianma.test;

import java.util.ArrayList;
import java.util.List;

import com.dynamo2.tianma.dao.AbstractDao;
import com.dynamo2.tianma.expression.BasicExpression;
import com.dynamo2.tianma.expression.Expression;
import com.dynamo2.tianma.expression.comparison.EqExpression;
import com.dynamo2.tianma.expression.comparison.GteExpression;
import com.dynamo2.tianma.expression.comparison.NinExpression;
import com.dynamo2.tianma.expression.logical.AndExpression;
import com.dynamo2.tianma.expression.logical.OrExpression;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class TestDao extends AbstractDao {
	protected static String COLLECTION_NAME = "user";
	protected DBCollection dbColl = null;
	
	public TestDao(){
		dbColl = this.getDBCollection();
	}
	
	private DBCollection getDBCollection(){
		return this.getDBCollection(COLLECTION_NAME);
	}
	
	public void save(User u) {
		BasicDBObject doc = encode(u);
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
	
	public static void print(List<DBObject> ul){
		for(DBObject dbo : ul){
			System.out.println(dbo);
		}
	}
	
	public void testAnd(){
		List<BasicDBObject> and = new ArrayList<BasicDBObject>();
		and.add(new BasicDBObject("name", "wzq"));
		and.add(new BasicDBObject("age", 10));
		and.add(new BasicDBObject("mobile", "1328976876"));
//		and.append("age", 10);
//		and.append("mobile", "1328976876");
	
		List<BasicDBObject> or = new ArrayList<BasicDBObject>();
		or.add(new BasicDBObject("$and",and));
		or.add(new BasicDBObject("name", "wmx"));
		
		BasicDBObject all = new BasicDBObject("$or",or);
		//all.put("$or",or);
		
		List<DBObject> ul = find(all);
		print(ul);
		
		//System.err.println(this.getByID("5507e1a98e4f0d42b943cb3f"));
	}
	
	public void testAnd2(){
		EqExpression nameExpre = new EqExpression("name","wzq");
		EqExpression ageExpre = new EqExpression("age",10);
		EqExpression mobileExpre = new EqExpression("mobile","1328976876");
		
		AndExpression andExpre = new AndExpression();
		andExpre.add(nameExpre);
		andExpre.add(ageExpre);
		andExpre.add(mobileExpre);
		
		OrExpression orExpre = new OrExpression();
		orExpre.add(andExpre);
		orExpre.add(new EqExpression("name","wmx"));
		
		andExpre = new AndExpression();
		andExpre.add(orExpre);
		andExpre.add(new EqExpression("password","987067"));
		
		orExpre = new OrExpression();
		orExpre.add(andExpre);
		orExpre.add(new EqExpression("name","wf"));
		
		
		GteExpression gte = new GteExpression("age",20);
		
		List<Object> ages = new ArrayList<Object>();
		ages.add(10);
		ages.add(20);
		ages.add(29);
		Expression exp = new NinExpression("age",ages);
		
		andExpre = new AndExpression();
		andExpre.add(exp);
		andExpre.add(new EqExpression("mobile","1328976876"));
		
		
		
		
		
		String expre = "{name $in ['wmx','wf']}";
				//"{{{name $eq 'wzq'} $and {age $eq 10}} $or {{{name $eq 'wmx'} $or {name $eq 'wf'}} $and {age $gte 20}}}";
		Expression be = BasicExpression.compile(expre,null);
		
		List<DBObject> ul = find(be.generateQuery());
		print(ul);
		
		//System.err.println(this.getByID("5507e1a98e4f0d42b943cb3f"));
	}
	
	public static void main(String[] args) {
		TestDao td = new TestDao();
		
		td.testAnd2();
		
		System.out.println("");
		System.out.println("****************");
		td.printAll();
	}
	
	public void printAll(){
		List<DBObject> ul = find(null);
		print(ul);
	}
	
	public void testInit(){
		User u = new User();
		u.setName("wmx");
		u.setAge(20);
		u.setEmail("wmx@tm.com");
		u.setMobile("13432345434");
		u.setPassword("546543");
		u.setPoint(90);
		this.save(u);
		
		u = new User();
		u.setName("wf");
		u.setAge(29);
		u.setEmail("wf@tm.com");
		u.setMobile("13509876786");
		u.setPassword("786509");
		u.setPoint(123);
		this.save(u);
		
		u = new User();
		u.setName("wzq");
		u.setAge(10);
		u.setEmail("wzq@tm.com");
		u.setMobile("1328976876");
		u.setPassword("987067");
		u.setPoint(187);
		this.save(u);
		
		u = new User();
		u.setName("wq");
		u.setAge(10);
		u.setEmail("wq@tm.com");
		u.setMobile("13434509873");
		u.setPassword("478034");
		u.setPoint(134);
		this.save(u);
		
		u = new User();
		u.setName("wzq");
		u.setAge(13);
		u.setEmail("wqz@tm.com");
		u.setMobile("1328976876");
		u.setPassword("897909");
		u.setPoint(345);
		this.save(u);
		
		u = new User();
		u.setName("wzq");
		u.setAge(10);
		u.setEmail("wzqwf@tm.com");
		u.setMobile("1332345654");
		u.setPassword("789065");
		u.setPoint(987);
		this.save(u);
		
		u = new User();
		u.setName("wzq");
		u.setAge(10);
		u.setEmail("wzq@tm.com");
		u.setMobile("13563823098");
		u.setPassword("760339");
		u.setPoint(249);
		this.save(u);
		
		u = new User();
		u.setName("wzq");
		u.setAge(10);
		u.setEmail("wzq@tm.com");
		u.setMobile("1328976876");
		u.setPassword("897678");
		u.setPoint(673);
		this.save(u);
		
		u = new User();
		u.setName("wzq");
		u.setAge(10);
		u.setEmail("wzq@tm.com");
		u.setMobile("1328976876");
		u.setPassword("987067");
		u.setPoint(2122);
		this.save(u);
	}
	
	
		
}