package kmean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class KmeansReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

	/*
	 * Reduce function will emit all the points to that center and calculate the
	 * next center for these points
	 */
	@Override
	public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
			throws IOException {
		try {
			List<Double> newCenter = new ArrayList<Double>();
			List<Double> sums = new ArrayList<Double>();
			int no_elements = 0;
			String points = "";
			int it = 0;
			
			
			while (values.hasNext()) {
				List<Double> point = KMeans.toListDouble(values.next().toString());
				if (it == 0)
					for (int i = 0; i < point.size(); i++)
						sums.add((double) 0);
				else
					for (int i = 0; i < point.size(); i++)
						sums.set(i, sums.get(i) + point.get(i));

				points = points + " " + KMeans.toText(point);
				++no_elements;
				it++;
			}

			// We have new center now
			for (Double sum : sums) 
				newCenter.add(sum / no_elements);
			
			// Emit new center and point
			output.collect(new Text(KMeans.toText(newCenter)), new Text(points));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
