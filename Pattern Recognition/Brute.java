import java.util.Arrays;


public class Brute {
	
	public static void main(String[] args) {
		
		// rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        //StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read in the input
        String filename = args[0];
        
        In in = new In(filename);
        int N = in.readInt();
        
        Point[] points = new Point[N];
        //StdOut.println("N = " + N);
        for (int i = 0; i < N; i++) { //Fills the points array with the points in the file, and draws them.
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
        }
        
        for (int i = 0; i < N-3; i++) {
        	
        	for (int j = i+1; j < N-2; j++) {
        		
        		for (int k = j+1; k < N-1; k++) {
        			
        			double slope1 = points[i].slopeTo(points[j]);
    				double slope2 = points[i].slopeTo(points[k]);
    				if (slope1 == slope2) {
        			
	        			for (int l = k+1; l < N; l++) {
	        				
	        				double slope3 = points[i].slopeTo(points[l]);
	        				
	        				if (slope1 == slope3) {
	        					StdOut.println(points[i].toString() + " -> " +
	        								   points[j].toString() + " -> " +
	        								   points[k].toString() + " -> " +
	        								   points[l].toString());
	        					
	        					Point[] line = new Point[4];
	        					line[0] = points[i];
	        					line[1] = points[j];
	        					line[2] = points[k];
	        					line[3] = points[l];
	        					
	        					Arrays.sort(line);
	        					line[0].drawTo(line[3]);
	        					
	        				}
	        				else;
	        			}
        			}
        		}
        	}
        }
        
        
        
        

        // display to screen all at once
        //StdDraw.show(0);

        // reset the pen radius
        StdDraw.setPenRadius();
		
	
	}
}
