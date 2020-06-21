package naivebayes;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class BayesMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable>{
	private Text word = new Text();
	private final static IntWritable one = new IntWritable(1);

	public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {

		String valueString = value.toString();
		String[] valueArr = valueString.split(" ");
		
		word.set("Play:" + valueArr[4]);
        output.collect(word, one);
        
        word.set("Outlook:" + valueArr[0] + "|" + valueArr[4]);
        output.collect(word, one);
        
        word.set("Temp:" + valueArr[1] + "|" + valueArr[4]);
        output.collect(word, one);
        
        word.set("Humidity:" + valueArr[2] + "|" + valueArr[4]);
        output.collect(word, one);
        
        word.set("Windy:" + valueArr[3] + "|" + valueArr[4]);
        output.collect(word, one);
		
		
		
		
		
		
//		StringTokenizer itr = new StringTokenizer(value.toString());
//	      while (itr.hasMoreTokens()) {
//	        word.set(itr.nextToken());
//	        output.collect(word, one);
//	      }
	}
}
