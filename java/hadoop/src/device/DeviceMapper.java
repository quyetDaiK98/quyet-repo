package device;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class DeviceMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
	private Text deviceID = new Text();
	private static FloatWritable x ;
	
	public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {

//		String valueString = value.toString();
//		String[] SingleCountryData = valueString.split(" ");
//		output.collect(new Text(SingleCountryData[7]),x);
		
		StringTokenizer itr = new StringTokenizer(value.toString());
	      while (itr.hasMoreTokens()) {
	    	  String s = itr.nextToken();
	    	 deviceID.set(s.split(",")[0]);
	    	 x =  new FloatWritable(Float.parseFloat(s.split(",")[1]));
	        output.collect(deviceID, x);
	      }
	}

}
