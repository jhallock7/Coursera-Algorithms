
public class Solver {


	private MinPQ<SearchNode> pq;
	private Board initial;
	private SearchNode answer;
	
	private class SearchNode implements Comparable<SearchNode> {
		Board current;
		SearchNode previous;
		int moves;
		int Manhattan;
		int Hamming;
		
		SearchNode(Board board1, SearchNode prevNode) {
			current = board1;
			previous = prevNode;
			moves = prevNode.moves + 1;
			Manhattan = current.manhattan() + moves;
			Hamming = current.hamming() + moves;
			
		}
		SearchNode(Board board1) {
			current = board1;
			previous = null;
			moves = 0;
			Manhattan = 0;
			Hamming = 0;
		}
		
		public int compareTo(SearchNode that) {
			if (this.Manhattan == that.Manhattan) { 
				return this.current.manhattan() - that.current.manhattan();
				}
			else { return this.Manhattan - that.Manhattan; }
		}
		
	}
	
	public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
		pq = new MinPQ<SearchNode>();
		this.initial = initial;
		SearchNode initialNode = new SearchNode(initial);
		SearchNode initialTwin = new SearchNode(initial.twin());
		pq.insert(initialNode);
		pq.insert(initialTwin);
		
		answer = initialNode;
		
		
		
		while (answer.current.isGoal() != true) {
			answer = pq.delMin();
			for (Board s: answer.current.neighbors()) {
				if (answer.previous != null) {
					if (s.equals(answer.previous.current) == true) { continue; }
				}
					SearchNode addNode = new SearchNode(s, answer);
					pq.insert(addNode);
			}
		}
	}
	
	public boolean isSolvable() { 
		SearchNode answerBack = answer;
		while (answerBack.previous != null) {
			answerBack = answerBack.previous;
		}
		if (initial.equals(answerBack.current)) { return true; }
		else { return false; }
	}
	
	
	public int moves() { 
		if (isSolvable() == true) return answer.moves;
		else return -1;
		}
	
	public Iterable<Board> solution() {
		if (isSolvable() == false) { return null; }
		Stack<Board> stack = new Stack<Board>();
		stack.push(answer.current);
		
		SearchNode answerBack = answer;
		
		while (answerBack.previous != null) {
			answerBack = answerBack.previous;
			stack.push(answerBack.current);
		}
		
		return stack;
	}

	public static void main(String[] args) {
		//long lStartTime = System.nanoTime();
	    // create initial board from file
	    In in = new In(args[0]);
	    int N = in.readInt();
	    int[][] blocks = new int[N][N];
	    for (int i = 0; i < N; i++)
	        for (int j = 0; j < N; j++)
	            blocks[i][j] = in.readInt();
	    Board initial = new Board(blocks);

	    // solve the puzzle
	    Solver solver = new Solver(initial);

	    // print solution to standard output
	    if (!solver.isSolvable())
	        StdOut.println("No solution possible");
	    else {
	        StdOut.println("Minimum number of moves = " + solver.moves());
	        for (Board board : solver.solution())
	            StdOut.println(board);
	    }
	    //long lEndTime = System.nanoTime();
	    //long difference = lEndTime - lStartTime;
	    //System.out.println("Elapsed milliseconds: " + difference/1000000);
	    // StdOut.println("size = " + solver.pq.size());
	    
	}
	
	
}
