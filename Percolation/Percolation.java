
public class Percolation {

	/* Declare "grid" as a 2-D integer array.
	 * Private because the outside client should not be able to directly access the array,
	 * but rather must use our instance methods.*/
	private int[][] grid;
	private int size;
	private WeightedQuickUnionUF gridid;
	private WeightedQuickUnionUF gridid2;
	private int topid;
	
	/*The Percolation constructor creates the 2-d array called grid with dimensions N by N.
	 * The default value is 0, which here will mean blocked. 1 will mean open.*/
	public Percolation(int N)  { //WHY CAN'T THIS BE VOID????? Because a constructor returns the new object created.
		if (N <= 0) { throw new IllegalArgumentException("N is less than or equal to zero!"); }
		grid = new int[N][N];
		size = N;
		topid = (int) Math.ceil(((double) size*size)/2);
		gridid = new WeightedQuickUnionUF(topid + 2);
		gridid2 = new WeightedQuickUnionUF(topid + 2);
		//StdOut.println("topid = " + topid);
		//StdOut.println("size = " + size);
		for (int k = 0; k < Math.ceil(((double) size)/2); k++) {
			gridid.union(k, topid);
			gridid2.union(k, topid);
			}

		//gridid2 = new WeightedQuickUnionUF(size*size + 2);
		//for (int k = 0; k < size; k++) {
		//	gridid2.union(k, size*size);
		//	}
		}
	
	
	private int loc(int i, int j) {
		return ((int) Math.ceil( ((double) (size*i)) /2)) + (int) Math.floor(j/2);
	}
	
	/* Set the 0 in the ith row and jth column equal to 1, signifying open
	 * Connect with neighboring points;
	 * Must have conditions for if point is on an edge, corner, or in the interior. */
	public void open(int c, int r)  {
		int i = c-1;
		int j = r-1;
		
		//StdOut.println("We open i,j = " + i + "," + j);
		//StdOut.println("Does i,j lie on a square?: " + ((i % 2) == (j % 2)));
		grid[i][j] = 1;
		if ((i % 2) != (j % 2)) { //i,j different parity = off grid
			
			//Link will contain the identities of the 
			//surrounding boxes we want to be connected.
			//"-1" means don't connect/
			int[] link = new int[4];
			for (int k = 0; k < 4; k++) { link[k] = -1; }
			
			//If there is a box above that is open, we will want to connect it.
			if (i != 0) { 
				if (isOpen(c-1, r)) { link[0] = loc(i-1, j); }
				}
			
			//The box above may be the virtual top!!!
			if (i == 0) {
				link[0] = topid;
			}
				
			//If there is a box below that is open, we will want to connect it.
			if (i != size-1) { 
				if (isOpen(c+1, r)) { link[1] = loc(i+1, j); }
			}		
			
			//If there is a box to the left that is open, we will want to connect it.
			if (j != 0) { 
				if (isOpen(c, r-1)) { link[2] = loc(i, j-1); }
			}
			
			//If there is a box to the right that is open, we will want to connect it.
			if (j != size-1) { 
				if (isOpen(c, r+1)) { link[3] = loc(i, j+1); }
			}
			
			//Now we connect the boxes surrounding i,j that are open and not yet connected
			for (int k = 0; k < 4; k++) {
				if (link[k] != -1) {
					for (int l = k+1; l < 4; l++) {
						if (link[l] != -1) {
							if (gridid.connected(link[k], link[l]) == false) {
								gridid.union(link[k], link[l]);	
								gridid2.union(link[k], link[l]);
							}
						}
					}
				}
			}
			
			}
		
		if ((i % 2) == (j % 2)) { //i,j same parity = on grid
			
			//linkTo will contain the identities of the 
			//surrounding boxes we want to be connected TO i,j.
			//"-1" means don't connect/
			int[] linkTo = new int[12];
			for (int k = 0; k < 12; k++) { linkTo[k] = -1; }
			
			//If there is a box ABOVE that is open,
			//we want to connect i,j to the 3 surrounding boxes if they exist, and are open
			if (i != 0) { 
				if (isOpen(c-1, r)) {

					//LEFT
					if (j != 0) { 
						if (isOpen(c-1, r-1)) {
							linkTo[0] = loc(i-1, j-1);
							}
						}
					//UP
					if (i-1 != 0) { 
						if (isOpen(c-2, r)) { 
							linkTo[1] = loc(i-2, j); 
							} 
						}
					//IF 2 up is the TOP, then it should be connected to the virtual top
					if (i-1 == 0) {
							if (!gridid.connected(loc(i, j), topid)) {
								gridid.union(loc(i, j), topid);
								gridid2.union(loc(i, j), topid);
							}
					}
					
					//RIGHT
					if (j != size-1) { 
						if (isOpen(c-1, r+1)) { 
							linkTo[2] = loc(i-1, j+1); 
							} 
					}
				}			
			}
			
			
			//If there is a box BELOW that is open,
			//we want to connect i,j to the 3 surrounding boxes if they exist, and are open
			if (i != size-1) {
				if (isOpen(c+1, r)) {

					//LEFT
					if (j != 0) { 
						if (isOpen(c+1, r-1)) {
							linkTo[3] = loc(i+1, j-1);
							}
						}
					//DOWN
					if (i+1 != size-1) { 
						if (isOpen(c+2, r)) { 
							linkTo[4] = loc(i+2, j); 
							} 
						}
					
					if (i+1 == size-1) {
						gridid2.union(loc(i, j), topid+1);	
					}
					
					//RIGHT
					if (j != size-1) { 
						if (isOpen(c+1, r+1)) { 
							linkTo[5] = loc(i+1, j+1); 
							} 
						}
				}			
			}
			
			if (i == size-1) {
				gridid2.union(loc(i, j), topid+1);
			}
				
			
			//If there is a box LEFT that is open,
			//we want to connect i,j to the 3 surrounding boxes if they exist, and are open
			if (j != 0) {
				if (isOpen(c, r-1)) {

					//UP
					if (i != 0) { 
						if (isOpen(c-1, r-1)) {
							linkTo[6] = loc(i-1, j-1);
							}
						}
					//LEFT
					if (j-1 != 0) { 
						if (isOpen(c, r-2)) { 
							linkTo[7] = loc(i, j-2); 
							} 
						}
					
					//DOWN
					if (i != size-1) { 
						if (isOpen(c+1, r-1)) { 
							linkTo[8] = loc(i+1, j-1); 
							} 
						}
				}			
			}
			
			
			//If there is a box RIGHT that is open,
			//we want to connect i,j to the 3 surrounding boxes if they exist, and are open
			if (j != size-1) {
				if (isOpen(c, r+1)) {

					//UP
					if (i != 0) { 
						if (isOpen(c-1, r+1)) {
							linkTo[9] = loc(i-1, j+1);
							}
						}
					//RIGHT
					if (j+1 != size-1) { 
						if (isOpen(c, r+2)) { 
							linkTo[10] = loc(i, j+2); 
							} 
						}
					
					//DOWN
					if (i != size-1) { 
						if (isOpen(c+1, r+1)) { 
							linkTo[11] = loc(i+1, j+1); 
							} 
						}
				}			
			}

			//Now connect
			for (int k = 0; k < 12; k++) {

				if (linkTo[k] != -1) {
					if (gridid.connected(loc(i, j), linkTo[k]) == false) {
						gridid.union(loc(i, j), linkTo[k]);
						gridid2.union(loc(i, j), linkTo[k]);
					}
				}
			}
			
			
		}
		
		//StdOut.println("Is i,j connected to topid?: " + gridid.connected(loc(i, j), topid));
		//StdOut.println("Is i,j full?: " + isFull(c, r));
		//StdOut.println("Is i,j connected to topid+1?: " + gridid.connected(loc(i, j), topid+1));
		

		
		/*
		if (i != 0) { // Connect box above
			if (isOpen(c-1, r)) { 
				if (gridid.connected(i*size + j, (i-1)*size + j) == false) {
					gridid.union(i*size + j, (i-1)*size + j);
					gridid2.union(i*size + j, (i-1)*size + j);
				}
			}
		}
		if (i != size-1) { // Connect box below
			if (isOpen(c+1, r)) {
				if (gridid.connected(i*size + j, (i+1)*size + j) == false) {
					gridid.union(i*size + j, (i+1)*size + j);
					gridid2.union(i*size + j, (i+1)*size + j);
				}
			}
		}
		else {
			if (gridid.connected(i*size + j, size*size + 1) == false) {
				gridid2.union(i*size + j, size*size + 1);
			}
		}
		if (j != 0) { // Connect box left
			if (isOpen(c, r-1)) {
				if (gridid.connected(i*size + j, i*size + (j-1)) == false) {
					gridid.union(i*size + j, i*size + (j-1));
					gridid2.union(i*size + j, i*size + (j-1));
				}
			}
		}
		if (j != size-1) { // Connect box right
			if (isOpen(c, r+1)) {
				if (gridid.connected(i*size + j, i*size + (j+1)) == false) {
					gridid.union(i*size + j, i*size + (j+1));
					gridid2.union(i*size + j, i*size + (j+1));
				}
			}
		}
		*/
		
	}
	
	public boolean isOpen(int c, int r) {
		return (grid[c-1][r-1] == 1);
	}
	
	public boolean isFull(int c, int r) {
		int i = c - 1;
		int j = r - 1;
		if (isOpen(c, r)) {
			
			if ((i % 2) != (j % 2)) { //Not on a square, need to check surroundings
				boolean isfull = false;
				
				if (i == 0) { isfull = true; }
				
				//UP
				if (i != 0) { 
					if (isOpen(c-1, r)) {
						if (gridid.connected(loc(i-1, j), topid) == true) {
							isfull = true;
						}
					}
				}
				 
				//LEFT
				if (j != 0) { 
					if (isOpen(c, r-1)) {
						if (gridid.connected(loc(i, j-1), topid) == true) {
							isfull = true;
						}
					}
				}
				
				//DOWN
				if (i != size-1) { 
					if (isOpen(c+1, r)) {
						if (gridid.connected(loc(i+1, j), topid) == true) {
							isfull = true;
						}
					}
				}
				
				//RIGHT
				if (j != size-1) { 
					if (isOpen(c, r+1)) {
						if (gridid.connected(loc(i, j+1), topid) == true) {
							isfull = true;
						}
					}
				}

			return isfull;
				
			}
			
			else {	
				return gridid.connected(loc(i, j), topid);
			}	
			
			}
		else { return false; }
		}
	
	public boolean percolates() {
		return gridid2.connected(topid, topid+1);	
	}

	
	//TEST CLIENT
	//Takes arguments of the form (N, i,j,i,j,i,j...)
	//where N is the size of the grid and the i,j are to be made open.
	/*
	public static void main(String[] args) {
		if (args.length % 2 == 0) {
			StdOut.println("Even number of arguments!");
			return;
		}
		int M = Integer.parseInt(args[0]);
		//StdOut.println("Integers in input = " + args.length);
		//StdOut.println("Grid is " + M + " by " + M);
		Percolation test = new Percolation(M);
		int k = 1;
		while (k < args.length) {
			int p = Integer.parseInt(args[k]);
			k++;
			int q = Integer.parseInt(args[k]);
			k++;
			//StdOut.println("Is " + p + ", " + q + " open?: " + test.isOpen(p, q));
			test.open(p, q);
			//StdOut.println("Is " + p + ", " + q + " open?: " + test.isOpen(p, q));
			//StdOut.println("Is " + p + ", " + q + " full?: " + test.isFull(p, q));
			//StdOut.println("Does the system percolate yet?: " + test.percolates());
		}

		if (test.percolates()) {
			StdOut.println("It percolates.");
		}
			else StdOut.println("It does not percolate.");	
	}
	
	*/
}