package knn;

public class Neighbor implements Comparable<Neighbor>{
	float distance;
	
	short label;

	@Override
	public int compareTo(Neighbor o) {
		// TODO Auto-generated method stub
		return new Float(distance).compareTo(o.distance);
	}
	
	
}
