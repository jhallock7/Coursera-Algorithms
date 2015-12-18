
public class Subset {

	private int N;
	//private RandomizedQueue queue;
	
	
	public static void main(String[] args) {
		//int N = args.length;
		int k = Integer.parseInt(args[0]);
		//StdOut.println("N = " + N);
		//StdOut.println("k = " + k);
		RandomizedQueue<String> queue;
		queue = new RandomizedQueue<String>();	

		while (!StdIn.isEmpty()) {
			queue.enqueue(StdIn.readString());
		}
		
		int j = 0;
		while (j < k) {
			String a = queue.dequeue();
			StdOut.println(a);
			j++;
		}
	}
}
