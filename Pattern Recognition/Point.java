import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();       // compare points by slope to this point

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    
    private class BySlope implements Comparator<Point> {
    	public int compare(Point that1, Point that2) {
    		if (slopeTo(that1) == slopeTo(that2)) { return 0; }
    		if (slopeTo(that1) > slopeTo(that2)) { return 1; }
    		else return -1;
    	}
    }
   
    // create the point (x, y) GOOD
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing GOOD
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing GOOD
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	int xdif = this.x - that.x; //Should an exception be thrown if xdif = 0???
    	int ydif = this.y - that.y;
    	double slope = ((double) ydif) / ((double) xdif);
    	if (slope == Double.NEGATIVE_INFINITY) //Ensure that ininity will be POSITIVE
    		{ slope = Double.POSITIVE_INFINITY; }
    	if (this.y == that.y) { //Ensure that zero will be positive
    		if (this.x == that.x) {
    			slope = Double.NEGATIVE_INFINITY;
    		}
    		else slope = 0;
    		}
    	return slope;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {

    	if (this.y == that.y) {
    		return this.x - that.x;
    	}
    	else return this.y - that.y;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}