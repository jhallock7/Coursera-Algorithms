
public class PercolationStats {
//??What is the best place to put the Percolation board declaration/instantiation??
	private int size;
	//private Percolation board;
	private double[] outcomes;
	private int numExpts;
	
	public PercolationStats(int N, int T) {
		if (N <= 0) { throw new IllegalArgumentException("N is less than or equal to zero!"); }
		if (T <= 0) { throw new IllegalArgumentException("T is less than or equal to zero!"); }
		size = N;
		//NOT int numExpts = T;  Why not??
		numExpts = T;
		outcomes = new double[T];
		//Percolation board = new Percolation(size);
		for (int count = 0; count < numExpts; count++) { //count indicates what experiment.
			Percolation board = new Percolation(size);
			double fracOpen = 0;
			int numberOpen = 0; //numberOpen is the number of opened sites in that experiment
			while (!board.percolates()) {
				int p = StdRandom.uniform(N);
				int q = StdRandom.uniform(N);
				if (!board.isOpen(p+1, q+1)) {
					board.open(p+1, q+1);
					numberOpen++;
					//StdOut.println(p + " " + q);
				}
			}
			fracOpen = (double) numberOpen/(size*size); //IF SIZE*SIZE IS REPLACE WITH THE NUMBER??
			outcomes[count] = fracOpen;
			//StdOut.println("Expt " + count + " percolated at a ratio of " + fracOpen + " with " +numberOpen + " open, and a size of " + size*size);
		}
		//StdOut.println("numExpts = " + numExpts);
		//StdOut.println("size = " + size);
	}
	
	
	
	public double mean() {
		double sum = 0;
		for (int k = 0; k < numExpts; k++) {
			sum = sum + outcomes[k];
			//StdOut.println(sum/(size*size));
			}
		//double mean = sum/numExpts;
		return ((sum)/numExpts);
	}
	
	public double stddev() {
		double sumSqRes = 0;
		for (int k = 0; k < numExpts; k++) {
			sumSqRes = sumSqRes + (outcomes[k] - mean())*(outcomes[k] - mean());
		}
		double var = sumSqRes/(numExpts - 1);
		return Math.sqrt(var);
	}
	
	public double confidenceLo() {
		return mean() - 1.96*stddev()/Math.sqrt(numExpts);
	}
	
	public double confidenceHi() {
		return mean() + 1.96*stddev()/Math.sqrt(numExpts);
	}
	
	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats test = new PercolationStats(N, T);
		//StdOut.println("Percolation on a " + N + "x" + N + " grid, repeated " + T + " times");
		//StdOut.println(test.outcomes[0]);
		//StdOut.println("mean " + test.mean());
		//StdOut.println("stddev " + test.stddev());
		//StdOut.println("95% confidence interval = " + test.confidenceLo() + ", " + test.confidenceHi());	
	}
}