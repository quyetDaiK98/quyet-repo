package provincecount;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class ProvinceCountReducer extends MapReduceBase implements Reducer<Text, Text, Text, FloatWritable> {
	private FloatWritable out = new FloatWritable();
	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text, FloatWritable> output,
			Reporter reporter) throws IOException {
		Text key = t_key;
		int sum = 0;
		double total = 0;
		while (values.hasNext()) {
			// replace type of value with the actual type of our value
			String[] arr = values.next().toString().split("\\|");
			sum += Integer.valueOf(arr[0]);
			total += Float.valueOf(arr[1]);
		}
		out.set((float) (total/sum));
		output.collect(key, out);
		System.out.println("NguyenVanQuyet_"+key.toString());
	}
}