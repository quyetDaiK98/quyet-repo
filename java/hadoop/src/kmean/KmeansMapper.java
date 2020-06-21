package kmean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/*
 * In Mapper class we are overriding configure function. In this we are
 * reading file from Distributed Cache and then storing that into instance
 * variable "mCenters"
 */
@SuppressWarnings("deprecation")
public class KmeansMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	public static List<List<Double>> mCenters = new ArrayList<List<Double>>();

	@Override
	public void configure(JobConf job) {
		try {
			// Fetch the file from Distributed Cache Read it and store the
			// centroid in the ArrayList
			Path[] cacheFiles = DistributedCache.getLocalCacheFiles(job);
			if (cacheFiles != null && cacheFiles.length > 0) {
				String line;
				mCenters.clear();
				System.out.println("cacheFiles[0]: " + cacheFiles[0].toString());
				BufferedReader cacheReader = new BufferedReader(new FileReader(cacheFiles[0].toString()));
				try {
					// Read the file split by the splitter and store it in
					// the list
					while ((line = cacheReader.readLine()) != null) {
						String temp = line.split("\\s+")[0];
						List<Double> centers = KMeans.toListDouble(temp);
						
						mCenters.add(centers);
					}
				} finally {
					cacheReader.close();
				}
			}
		} catch (IOException e) {
			System.err.println("Exception reading DistribtuedCache: " + e);
		}
	}

	/*
	 * Map function will find the minimum center of the point and emit it to the
	 * reducer
	 */
	@Override
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException {
		
		try {
			List<Double> point = KMeans.toListDouble(value.toString());
			double distance, minDistance = Double.MAX_VALUE;
			List<Double> nearest_center = mCenters.get(0);
			
			
			// Find the minimum center from a point
			for (List<Double> center : mCenters) {
				distance = KMeans.distance(center, point);
				if (Math.abs(distance) < Math.abs(minDistance)) {
					nearest_center = center;
					minDistance = distance;
				}
			}
			// Emit the nearest center and the point
			output.collect(new Text(KMeans.toText(nearest_center)), new Text(KMeans.toText(point)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
