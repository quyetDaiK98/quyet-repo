package kmean;

/*
 * @author Himank Chaudhary
 */

import java.util.*;
import java.util.stream.Collectors;
import java.io.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

@SuppressWarnings("deprecation")
public class KMeans {
	public static String OUT = "outfile";
	public static String IN = "inputlarger";
	public static String CENTROID_FILE_NAME = "centroid.txt";
	public static String OUTPUT_FILE_NAME = "part-00000";
	public static String DATA_FILE_NAME = "data.txt";
	public static String JOB_NAME = "KMeans";
	public static String SPLITTER = "\t| ";
	
	public static double distance(List<Double> p1, List<Double> p2) {
		double sum = 0;
		for(int i = 0; i < p1.size(); i++) 
			sum += Math.pow(p1.get(i) - p2.get(i), 2);
		
		return Math.sqrt(sum);
	}
	
	public static String toText(List<Double> points) {
		return String.join(",", points.stream().map(x -> String.valueOf(x)).toArray(String[]::new));
	}
	
	public static List<Double> toListDouble(String line) {
		return Arrays.asList(line.split(",")).stream().map(c -> Double.parseDouble(c)).collect(Collectors.toList());
	}


	public static void main(String[] args) throws Exception {
		try {
			IN = "src/kmean/";
			OUT = "output";
			String input = IN;
			String output = OUT + System.nanoTime() + "/";
			String again_input = output;

			// Reiterating till the convergence
			int iteration = 0;
			boolean isdone = false;
			while (isdone == false) {
				System.out.println("iteration: " + Integer.toString(iteration));
				//String output = OUT + System.nanoTime() + "/";
				//String again_input = output;
				System.out.println("output: " + output);
				System.out.println("again_input: " + again_input);
				
				JobConf conf = new JobConf(KMeans.class);
				if (iteration == 0) {
					Path hdfsPath = new Path(input + CENTROID_FILE_NAME);
					DistributedCache.addLocalFiles(conf, input + CENTROID_FILE_NAME);
					System.out.println("CENTROID: " + hdfsPath.toUri().toString());
				} else {
					Path hdfsPath = new Path(again_input + OUTPUT_FILE_NAME);
					// upload the file to hdfs. Overwrite any existing copy.
					DistributedCache.addLocalFiles(conf, again_input + OUTPUT_FILE_NAME);
					System.out.println("CENTROID: " + hdfsPath.toUri().toString());
				}

				conf.setJobName(JOB_NAME);
				conf.setMapOutputKeyClass(Text.class);
				conf.setMapOutputValueClass(Text.class);
				conf.setOutputKeyClass(Text.class);
				conf.setOutputValueClass(Text.class);
				conf.setMapperClass(KmeansMapper.class);
				conf.setReducerClass(KmeansReducer.class);
				conf.setInputFormat(TextInputFormat.class);
				conf.setOutputFormat(TextOutputFormat.class);

				FileInputFormat.setInputPaths(conf, new Path(input + DATA_FILE_NAME));
				System.out.println("data: " + input + DATA_FILE_NAME);
				FileOutputFormat.setOutputPath(conf, new Path(output));
				System.out.println("output: " + output);

				JobClient.runJob(conf);
				isdone = true;
				
				Path ofile = new Path(output + OUTPUT_FILE_NAME);
				FileSystem fs = FileSystem.get(new Configuration());
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fs.open(ofile)));
				List<List<Double>> centers_next = new ArrayList<List<Double>>();
				String line = br.readLine();
				while (line != null) {
					String[] sp = line.split("\\s+");
					List<Double> c = toListDouble(sp[0]);
					centers_next.add(c);
					line = br.readLine();
				}
				br.close();

				String prev;
				if (iteration == 0) {
					prev = input + CENTROID_FILE_NAME;
				} else {
					prev = again_input + OUTPUT_FILE_NAME;
				}
				Path prevfile = new Path(prev);
				FileSystem fs1 = FileSystem.get(new Configuration());
				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						fs1.open(prevfile)));
				List<List<Double>> centers_prev = new ArrayList<List<Double>>();
				String l = br1.readLine();
				while (l != null) {
					List<Double> cen = toListDouble(l.split("\\s+")[0]);
					centers_prev.add(cen);
					l = br1.readLine();
				}
				br1.close();

				// Sort the old centroid and new centroid and check for convergence
				// condition
//				Collections.sort(centers_next);
//				Collections.sort(centers_prev);

				Iterator<List<Double>> it = centers_prev.iterator();
				for (List<Double> cen : centers_next) {
					List<Double> temp = it.next();
					if (distance(cen, temp) <= 0.1) {
						isdone = true;
					} else {
						isdone = false;
						break;
					}
				}
				++iteration;
				
				again_input = output;
				output = OUT + System.nanoTime() + "/";
			}
			System.out.println("Finish!");
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}