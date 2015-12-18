
public class Board {

	private int[] board;
	private int dim;
	private int zero;
	
	public Board(int[][] blocks) { 	//construct a board from an N-by-N array of blocks
		dim = blocks.length;		
		board = new int[dim * dim];
		//StdOut.println("dim = " + dim);
		//StdOut.println("dim^2 = " + (dim * dim));
		
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				board[dim*i + j] = blocks[i][j];
				if (blocks[i][j] == 0) { zero = dim*i + j; }
			}	
		}
	}
	
	public int dimension() { return dim; } // board dimension N
	
	public int hamming() { 	// number of blocks out of place
		int count = 0;
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 0) continue;
			if (board[i] != i + 1) { count++; }
		}
		return count;
		
	}
	
	public int manhattan() { // sum of Manhattan distances between blocks and goal
		int count = 0;
		int p;
		int q;
		int r;
		int s;
		
		for (int i = 0; i < board.length; i++) {
			if (board[i] == 0) { continue; }
			p = i/dim;
			q = i % dim;
			r = (board[i] - 1) / dim;
			s = (board[i] - 1) % dim;
			count = count + Math.abs(p - r) + Math.abs(q - s);
		}
		return count;
	}
	
	public boolean isGoal() { 	// is this board the goal board?
		if (manhattan() == 0) { return true; }
		else return false;		
	}
	
	public Board twin() { 	// a board that is obtained by exchanging two adjacent blocks in the same row
		
		if (zero != 0 && zero != 1) { //Switches the first two values of the first row if one is not zero
			int dup = board[0];
			board[0] = board[1];
			board[1] = dup;
		}
		else { //else, switches the first two values of the second row
			int dup = board[dim];
			board[dim] = board[dim + 1];
			board[dim + 1] = dup;	
		}
		
		
		
		int[][] copy; //Makes a new baord based on the current board with the switch
		copy = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				copy[i][j] = board[dim*i + j];	
			}	
		}
		Board twin;
		twin = new Board(copy);
		
		
		if (zero != 0 && zero != 1) { // Undoes the switch
			int dup = board[0];
			board[0] = board[1];
			board[1] = dup;
		}
		else { 
			int dup = board[dim];
			board[dim] = board[dim + 1];
			board[dim + 1] = dup;	
		}
		
		
		return twin;
	}
	
	
	public String toString() {  // string representation of this board
		StringBuilder s = new StringBuilder();
		s.append(dim + "\n");
		for (int i = 0; i < dim; i++) {
		        for (int j = 0; j < dim; j++) {
		            s.append(String.format("%2d ", board[i*dim + j]));
		        }
		        s.append("\n");
		    }
		return s.toString();
	}
	

	public boolean equals(Object y) {
		if (this == y) return true;
		if (y == null) return false;
		if (this.getClass() != y.getClass()) return false;
		Board that = (Board) y;
		if (this.dimension() != that.dimension()) return false;
		for (int i = 0; i < board.length; i++) {
				
			if (this.board[i] != that.board[i]) return false;
		}
		return true;
	}


	public Iterable<Board> neighbors() {

		Stack<Board> stack = new Stack<Board>();
		
		if (zero/dim != 0) { //Zero is not on top row, so switch zero with above value
			
			int dup = board[zero - dim]; //SWITCH zero with above value
			board[zero - dim] = 0;
			board[zero] = dup;
			
			
			int[][] copy; //Create a new board with the switch and push it into the stack
			copy = new int[dim][dim];
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					copy[i][j] = board[dim*i + j];	
				}	
			}
			Board ngh;
			ngh = new Board(copy);
			stack.push(ngh);
			
			board[zero] = 0; //Undo the switch
			board[zero - dim] = dup;
			
		}
		
		
		if (zero/dim != dim-1) { //Zero is not on bottom row, so switch zero with below value
			
			int dup = board[zero + dim]; //SWITCH zero with below value
			board[zero + dim] = 0;
			board[zero] = dup;
			
			
			int[][] copy; //Create a new board with the switch and push it into the stack
			copy = new int[dim][dim];
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					copy[i][j] = board[dim*i + j];	
				}	
			}
			Board ngh;
			ngh = new Board(copy);
			stack.push(ngh);
			
			board[zero] = 0; //Undo the switch
			board[zero + dim] = dup;
			
		}
		
		
		if (zero % dim != 0) { //Zero is not in left column, so switch zero with left value
			
			int dup = board[zero - 1]; //SWITCH zero with left value
			board[zero - 1] = 0;
			board[zero] = dup;
			
			
			int[][] copy; //Create a new board with the switch and push it into the stack
			copy = new int[dim][dim];
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					copy[i][j] = board[dim*i + j];	
				}	
			}
			Board ngh;
			ngh = new Board(copy);
			stack.push(ngh);
			
			board[zero] = 0; //Undo the switch
			board[zero - 1] = dup;
			
		}
		
		
		if (zero % dim != dim-1) { //Zero is not in right column, so switch zero with right value
			
			int dup = board[zero + 1]; //SWITCH zero with right value
			board[zero + 1] = 0;
			board[zero] = dup;
			
			
			int[][] copy; //Create a new board with the switch and push it into the stack
			copy = new int[dim][dim];
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					copy[i][j] = board[dim*i + j];	
				}	
			}
			Board ngh;
			ngh = new Board(copy);
			stack.push(ngh);
			
			board[zero] = 0; //Undo the switch
			board[zero + 1] = dup;
			
		}
		
		return stack;
		
		}
	
	
	
	
	public static void main(String[] args) {
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		
		int[][] blocks = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blocks[i][j] = in.readInt();	
			}	
		}
		Board testBoard = new Board(blocks);
		
		StdOut.println(testBoard.toString());
	}
	
	
	
}
