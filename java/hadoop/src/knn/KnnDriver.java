package knn;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

import utils.MongoUtils;

@SuppressWarnings("deprecation")
public class KnnDriver {
	// File Constant
	private static final String INPUT_TEXT_FILE = "src/knn/data.txt";
	private static final String TEST_TEXT_FILE = "src/knn/newpoint.txt";
	private static final String OUTPUT = "output";

	// Mongo Constant
	private static final String DBNAME = "knn";
	private static final String TRAIN_COLLECTION = "train_point";
	private static final String TEST_COLLECTION = "new_point";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Create a configuration object for the job
		try {
			JobConf job_conf = new JobConf(KnnDriver.class);
			DistributedCache.addLocalFiles(job_conf, TEST_TEXT_FILE);
			// Set a name of the Job
			job_conf.setJobName("Word Count1");

			// Specify data type of output key and value
			job_conf.setOutputKeyClass(Text.class);
			job_conf.setOutputValueClass(Text.class);

			// Specify names of Mapper and Reducer Class
			job_conf.setMapperClass(KnnMapper.class);
			job_conf.setReducerClass(KnnReducer.class);

			// Specify formats of the data type of Input and output
			job_conf.setInputFormat(TextInputFormat.class);
			job_conf.setOutputFormat(TextOutputFormat.class);
			job_conf.setMapOutputKeyClass(Text.class);
			job_conf.setMapOutputValueClass(Text.class);

			// Set input and output directories using command line arguments,
			// arg[0] = name of input directory on HDFS, and arg[1] = name of output
			// directory to be created to store the output file.

			// FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
			// FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));
			FileInputFormat.setInputPaths(job_conf, new Path(INPUT_TEXT_FILE));
			FileOutputFormat.setOutputPath(job_conf, new Path(OUTPUT));
			
			Path outputPath = new Path(OUTPUT);
			outputPath.getFileSystem(job_conf).delete(outputPath, true);

			MongoClient mongoClient = MongoUtils.getMongoClient();
			DB database = mongoClient.getDB(DBNAME);

			// Tao du lieu train
			// DBCollection trainCollection = database.getCollection(TRAIN_COLLECTION);

			// MongoUtils.generateData(trainCollection, 1000, 3, true);

			// MongoUtils.writeToFile(trainCollection, INPUT_TEXT_FILE);
			
			
			// Tao du lieu test
			// DBCollection testCollection = database.getCollection(TEST_COLLECTION);
			
			// MongoUtils.generateData(testCollection, 4, 3, false);
			
			// MongoUtils.writeToFile(testCollection, TEST_TEXT_FILE);

			// Run the job
			 JobClient.runJob(job_conf);

			System.out.println("KNN OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
