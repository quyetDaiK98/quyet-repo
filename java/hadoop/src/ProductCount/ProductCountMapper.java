package ProductCount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class ProductCountMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	private Text keyIn = new Text();
	private Text valIn = new Text();
	private final static int one = 1;
	
	public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {

		String valueString = value.toString();
		String[] valueArr = valueString.split(",");
		
		keyIn.set(valueArr[0]);
		valIn.set(String.valueOf(one)+"|"+valueArr[1]);
        output.collect(keyIn, valIn);
        
	}
}
