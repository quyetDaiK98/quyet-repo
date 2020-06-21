package device;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class DeviceDriver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JobClient my_client = new JobClient();
		// Create a configuration object for the job
		JobConf job_conf = new JobConf(DeviceDriver.class);

		// Set a name of the Job
		job_conf.setJobName("Word Count1");

		// Specify data type of output key and value
		job_conf.setOutputKeyClass(Text.class);
		job_conf.setOutputValueClass(FloatWritable.class);

		// Specify names of Mapper and Reducer Class
		job_conf.setMapperClass(DeviceMapper.class);
		job_conf.setReducerClass(DeviceReducer.class);

		// Specify formats of the data type of Input and output
		job_conf.setInputFormat(TextInputFormat.class);
		job_conf.setOutputFormat(TextOutputFormat.class);

		// Set input and output directories using command line arguments, 
		//arg[0] = name of input directory on HDFS, and arg[1] =  name of output directory to be created to store the output file.
		
		FileInputFormat.setInputPaths(job_conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(job_conf, new Path(args[1]));
//		FileInputFormat.setInputPaths(job_conf, new Path("C:\\hadoop-2.7.3\\test\\input\\input.txt"));
//		FileOutputFormat.setOutputPath(job_conf, new Path("C:\\hadoop-2.7.3\\test\\output"));
		
		my_client.setConf(job_conf);
		try {
			// Run the job 
			JobClient.runJob(job_conf);
	    	System.out.println("device min max OK");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
