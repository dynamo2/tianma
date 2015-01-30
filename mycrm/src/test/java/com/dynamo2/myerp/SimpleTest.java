package com.dynamo2.myerp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import sun.misc.BASE64Decoder;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class SimpleTest {

	private final static String data = "UEsDBBQACAAIAPAykUEAAAAAAAAAAAAAAAAhAAAAU0ZBUElfMTIxNzA2MjMwMjI1MF9DT01QX1BMQU4uY3N2U/JNLcosUdJRci4tKkrNK1FwzMsrTcxRCE7MSSyqVOLlAgBQSwcIOwRSiCMAAAAhAAAAUEsBAhQAFAAIAAgA8DKRQTsEUogjAAAAIQAAACEAAAAAAAAAAAAAAAAAAAAAAFNGQVBJXzEyMTcwNjIzMDIyNTBfQ09NUF9QTEFOLmNzdlBLBQYAAAAAAQABAE8AAAByAAAAAAA=";
	
	public static void main(String[] args) throws IOException {
		BASE64Decoder de = new BASE64Decoder();
		byte[] result = de.decodeBuffer(data);
		
		InputStream in = new ByteArrayInputStream(result);
		ZipInputStream zip = new ZipInputStream(in);
		ZipEntry entry = zip.getNextEntry();
		BufferedReader reader = new BufferedReader(new InputStreamReader(zip));
		while(reader.ready()) {
			System.out.println(reader.readLine());
		}
		
		MongoClient mongoClient = new MongoClient();
		DB db = mongoClient.getDB("mydb");
		DBCollection coll = db.getCollection("goods");
		
		BasicDBObject doc = new BasicDBObject("name", "MongoDB")
								.append("type", "database")
								.append("count", 1)
								.append("info", new BasicDBObject("x", 203).append("y", 102));
		coll.insert(doc);

		System.out.println(coll.findOne());
		
	}
}
