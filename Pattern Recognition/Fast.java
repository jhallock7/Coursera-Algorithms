import java.util.Arrays;


public class Fast {
	
	private Point[] points;
	private Point[] aux;
	private int N;
	
	private Fast(int N) {
		points = new Point[N];
		aux = new Point[N];
		this.N = N;
	}
	
	
	
	private int getIncrement(int i) {
		Point set;
		set = aux[i];
		int j = 1;
		int count = 1;
		
		//StdOut.println("i = " + i);
		//StdOut.println("j = " + i);
		//StdOut.println("aux.length = " + aux.length);
		while (i+j < N) {
			if (aux[0].slopeTo(aux[i]) == aux[0].slopeTo(aux[i+j])) {
				count++;
				j++;
			}
			else break;
		}
		
		//StdOut.println("For j = " + i + ", count = " + count);
		
		return count;
		
	}
	
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
        
        Fast fast;
        fast = new Fast(N);
        
        //StdOut.println("N = " + N);
        for (int i = 0; i < N; i++) { //Fills the points array with the points in the file, and draws them.
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            fast.points[i] = p;
            fast.aux[i] = p;
        }
        

        Arrays.sort(fast.points);
        
        /*
        StdOut.println("points:");
        for (int i = 0; i < N; i++) {
        	StdOut.println(fast.points[i].toString());
        }
        StdOut.println("Lines:");
        */
        
        for (int i = 0; i < N; i++) { 
        	//Sort aux based on slope with points[i]
        	Arrays.sort(fast.aux, fast.points[i].SLOPE_ORDER);
        	
        	/*
        	StdOut.println("For i = " + i + ", fast.point[i] = " + fast.points[i]);
        	StdOut.println("aux:");
            for (int l = 0; l < N; l++) {
               	StdOut.println(fast.aux[l].toString());
            }
        	*/
        	
        	for (int j = 0; j < N; j = j + fast.getIncrement(j)) {
        		//StdOut.println("j = " + j);
        		if (fast.getIncrement(j) > 2) {
        			//StdOut.println("getIncrement was > 2; getIncrement = " + fast.getIncrement(j));
        			Arrays.sort(fast.aux, j, j+fast.getIncrement(j));
        			if (fast.points[i].compareTo(fast.aux[j]) < 0) { //This is a new line; Print the line and Draw the line.
        				StdOut.print(fast.aux[0].toString() + " -> ");
        				for (int k = j; k < j + fast.getIncrement(j) - 1; k++) {
        					StdOut.print(fast.aux[k].toString() + " -> ");
        				}
        				StdOut.print(fast.aux[j + fast.getIncrement(j) - 1].toString() + "\n");
        				fast.points[i].drawTo(fast.aux[j + fast.getIncrement(j) - 1]);
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
