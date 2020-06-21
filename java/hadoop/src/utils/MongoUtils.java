package utils;

import java.io.FileWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoUtils {
	private static final String HOST = "localhost";
	private static final int PORT = 27017;

	public static MongoClient getMongoClient() throws UnknownHostException {
		MongoClient mongoClient = new MongoClient(HOST, PORT);
		return mongoClient;
	}

	public static boolean generateData(DBCollection dbCollection, int totalRecord, int dim, boolean hasLabel) {
		Random generator = new Random();
		try {
			for (int i = 0; i < totalRecord; i++) {
				BasicDBObject document = new BasicDBObject();
				
				
				List<Float> value = new ArrayList<Float>();
				for (int j = 0; j < dim; j++) 
					value.add(100 + generator.nextFloat() * (1000 - 100));
				
				if(hasLabel) {
					String label = String.valueOf(generator.nextInt((5 - 1) + 1) + 1);
					value.add(Float.valueOf(label));
				}
				
				document.put("val", value);
				dbCollection.insert(document);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}

		return false;
	}
	
	public static boolean writeToFile(DBCollection dbCollection, String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath);
			 DBCursor cursor = dbCollection.find();
		      while (cursor.hasNext()) {
		    	  ArrayList<Double> values = (ArrayList<Double>) cursor.next().get("val");
		    	  String line = String.join(",", values.stream().map(item -> String.valueOf(item)).collect(Collectors.toList()));
		    	  line += "\r";
		    	  System.out.println(line);
		    	  fw.write(line);
		      }
			fw.close();
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}
