//import Point2D.DistanceToOrder;
import java.util.Arrays;
import java.util.Comparator;

//import java.util.TreeSet;


public class PointSET {

	private RedBlackBST<Point2D, Integer> points;
	
	// construct an empty set of points 
	public PointSET() { 
		points = new RedBlackBST<Point2D, Integer>();
	}
	
	// is the set empty?
	public boolean isEmpty() { return points.isEmpty(); }  
	
	// number of points in the set 
	public int size() { return points.size(); } 
	
	// add the point to the set (if it is not already in the set)
	public void insert(Point2D p) { points.put(p,1); } 
	
	// does the set contain point p? 
	public boolean contains(Point2D p) { return points.contains(p); } 
	
	// draw all points to standard draw 
	public void draw() {
		for (Point2D s : points.keys()) { s.draw();	}
	}
	
	// all points that are inside the rectangle 
	public Iterable<Point2D> range(RectHV rect) {
		Point2D lo = new Point2D(rect.xmin(), rect.ymin());
		Point2D hi = new Point2D(rect.xmax(), rect.ymax());
		Queue queue = new Queue();
		for (Point2D s : points.keys(lo, hi))  {
			if (rect.contains(s)) { queue.enqueue(s); }
		}
		return queue;
	}
	
	// a nearest neighbor in the set to point p; null if the set is empty 
	public Point2D nearest(Point2D p) {
		return p;
		
		/*
		if (points.size() == 0) { return null; }
		
		MinPQ<Point2D> pq = new MinPQ<Point2D>(p.DISTANCE_TO_ORDER);
		for (Point2D s : points.keys()) { pq.insert(s); }
		return pq.delMin();
		*/
		
		/*
		Point2D[] array = new Point2D[points.size()];
		int i = 0;
		for (Point2D s : points.keys()) {
			array[i] = s;
			i++;
			}
		Arrays.sort(array, p.DISTANCE_TO_ORDER);
		return array[0];
		*/
		
	}
	
	
	
	
}
