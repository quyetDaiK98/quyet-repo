package knn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class KnnReducer extends MapReduceBase implements Reducer<Text, Text, Text, IntWritable> {
	private Text out = new Text();
	private final static int k = 11;
	
	short predict(List<Neighbor> kNeighbors) {
		Map<Short, Short> countMap = new HashMap<Short, Short>();
		
		for (Neighbor neighbor : kNeighbors) 
			if(countMap.containsKey(neighbor.label)) 
				countMap.put(neighbor.label, (short) (countMap.get(neighbor.label) + 1));
			else countMap.put(neighbor.label, (short) 0);
		
		short maxCount = 0;
		short predict = 0;
		
		for (Map.Entry<Short, Short> item : countMap.entrySet()) {
			if(maxCount <= item.getValue()) {
				maxCount = item.getValue();
				predict = item.getKey();
			}
				
		}
		
		return predict;
	}
	
	@SuppressWarnings("unchecked")
	public void reduce(Text t_key, Iterator<Text> values, OutputCollector<Text, IntWritable> output,
			Reporter reporter) throws IOException {
		
		try {
			Text key = t_key;
			List<Neighbor> allNeighbors = new ArrayList<Neighbor>();
			
			while (values.hasNext()) {
				// replace type of value with the actual type of our value
				String[] arr = values.next().toString().split("\\|");
				Neighbor neighbor = new Neighbor();
				neighbor.distance = Float.valueOf(arr[0]);
				neighbor.label = Short.valueOf(arr[1]);
				allNeighbors.add(neighbor);
			}
			Collections.sort(allNeighbors);
			
			List<Neighbor> kNearestNeighbors = allNeighbors.subList(0, k);
			output.collect(key, new IntWritable(predict(kNearestNeighbors)));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}