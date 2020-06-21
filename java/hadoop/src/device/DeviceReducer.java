package device;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class DeviceReducer extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> {
	public void reduce(Text t_key, Iterator<FloatWritable> values, OutputCollector<Text, FloatWritable> output,
			Reporter reporter) throws IOException {
		Text key_min = new Text(t_key.toString() + "_min");
		Text key_max = new Text(t_key.toString() + "_max");
		float min=999999;
		float max=-999999;
		while (values.hasNext()) {
			// replace type of value with the actual type of our value
			FloatWritable value = (FloatWritable) values.next();
			float val = value.get();
			if(min > val) min = val;
			if(max < val) max = val;
		}
		output.collect(key_min, new FloatWritable(min));
		output.collect(key_max, new FloatWritable(max));
	}
}
