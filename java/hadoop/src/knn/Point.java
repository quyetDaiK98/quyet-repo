package knn;

import java.util.ArrayList;
import java.util.List;

public class Point {
	String name;
	List<Float> values = new ArrayList<Float>();
	short label;

	public Point() {
	}

	public Point(List<String> values, String label) {
		for (int i = 0; i < values.size() - 1; i++)
			this.values.add(Float.valueOf(values.get(i)));
		
		this.label = Float.valueOf(label).shortValue();
	}

	public Point(List<String> values) {
		for (String value : values)
			this.values.add(Float.valueOf(value));

	}
}
