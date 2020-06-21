package knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;


public class KnnMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
//	public static Point newPoint1 = new Point(100 + new Random().nextFloat() * (1000 - 100),100 + new Random().nextFloat() * (1000 - 100),100 + new Random().nextFloat() * (1000 - 100));
//	public static Point newPoint2 = new Point(100 + new Random().nextFloat() * (1000 - 100),100 + new Random().nextFloat() * (1000 - 100),100 + new Random().nextFloat() * (1000 - 100));
//	public Point newPoint3 = new Point();
	public static List<Point> newPoints = new ArrayList<Point>();
	
	float distance(Point p1, Point p2) {
		float sum = 0;
		for(int i = 0; i < p1.values.size(); i++) 
			sum += Math.pow(p1.values.get(i) - p2.values.get(i), 2);
		
		return (float) Math.sqrt(sum);
		
//		double a = Math.sqrt(Math.pow(p1.x1 - p2.x1, 2) + Math.pow(p1.x2 - p2.x2, 2) + Math.pow(p1.x3 - p2.x3, 2));
//		return (float) Math.sqrt(Math.pow(p1.x1 - p2.x1, 2) + Math.pow(p1.x2 - p2.x2, 2) + Math.pow(p1.x3 - p2.x3, 2));
	}
	
	@Override
	@SuppressWarnings("deprecation")
	public void configure(JobConf job) {
		try {
			// Fetch the file from Distributed Cache Read it and store the
			// URI[] cacheURIs = DistributedCache.getCacheFiles(job);
			// System.out.println("cacheURIs[0]: " + cacheURIs[0].toString());
			// centroid in the ArrayList
			Path[] cacheFiles = DistributedCache.getLocalCacheFiles(job);
			if (cacheFiles != null && cacheFiles.length > 0) {
				String line;
				System.out.println("cacheFiles[0]: " + cacheFiles[0].toString());
				BufferedReader cacheReader = new BufferedReader(new FileReader(cacheFiles[0].toString()));
				try {
					// Read the file split by the splitter and store it in
					// the list
					String name = "NEWPOINT ";
					int count = 1;
					while ((line = cacheReader.readLine()) != null) {
						List<String> temp = Arrays.asList(line.split(","));
						Point newPoint = new Point(temp);
						newPoint.name = name + count;
						newPoints.add(newPoint);
						count++;
					}
				}
				finally {
					cacheReader.close();
				}
			}
		} catch (IOException e) {
			System.err.println("Exception reading DistribtuedCache: " + e);
		}
	}
	
	
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
		try {
			List<String> rowInput = Arrays.asList(value.toString().split(","));
			
			Point point = new Point(rowInput , rowInput.get(rowInput.size() - 1));
			
			for (Point newPoint : newPoints) 
				output.collect(new Text(newPoint.name), new Text(String.valueOf(distance(point, newPoint)) + "|" + String.valueOf(point.label)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}