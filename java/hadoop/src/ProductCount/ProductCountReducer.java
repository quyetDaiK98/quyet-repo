package ProductCount;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class ProductCountReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	private Text out = new Text();
	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException {
		Text key = t_key;
		int sum = 0;
		double total = 0;
		while (values.hasNext()) {
			// replace type of value with the actual type of our value
			String[] arr = values.next().toString().split("\\|");
			sum += Integer.valueOf(arr[0]);
			total += Double.parseDouble(arr[1].toString());
		}
		out.set("sum:" + String.valueOf(sum) + "|" + "avg:" + String.valueOf(total/sum));
		output.collect(key, out);
	}
}