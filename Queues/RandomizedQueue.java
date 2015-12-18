import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

	private Item[] queue;
	//queue = (Item[]) new Object[1];
	private int N = 0;
	private int last;
	
	public RandomizedQueue() {
		queue = (Item[]) new Object[1];
		N = 0;
		last = -1;
	}

	private void resize(int capacity) {
		Item[] copy;
		copy = (Item[]) new Object[capacity];
		int k = 0;
		for (int i = 0; i <= last; i++) {
			if (queue[i] != null)	{
				copy[k] = queue[i];
				k++; }
		}
		queue = copy;
	}
	
	public boolean isEmpty() { return (N == 0); }
	
	public int size() { return N; }
	
	public void enqueue(Item item) {
		if (item == null) throw new NullPointerException("Can't add a null item."); 
		if (last == queue.length-1) {
			if (N == queue.length) { resize(2*queue.length); }
			else {
				resize(queue.length);
				last = N-1;
			}
		}
		last++;
		queue[last] = item;
		N++;
	}
	
	public Item dequeue() { //WE ASSUME THE QUEUE IS NONEMPTY; ADD DOWNSIZE
		if (last == -1) throw new NoSuchElementException("The queue is empty."); 
		int removefrom = StdRandom.uniform(0, last+1); //Pick a random integer between 0 and last
		while (queue[removefrom] == null) { //Pick a new number until the item at that position is not null
			removefrom = StdRandom.uniform(0, last+1);
		}
		Item item = queue[removefrom];
		queue[removefrom] = queue[last];
		queue[last] = null;
		N--;
		return item;
	}
		
	public Item sample() { //WE ASSUME THE QUEUE IS NONEMPTY
		if (last == -1) throw new NoSuchElementException("The queue is empty."); 
		int removefrom = StdRandom.uniform(0, last+1); //Pick a random integer between 0 and last
		while (queue[removefrom] == null) {
			removefrom = StdRandom.uniform(0, last+1);
		}
		Item item = queue[removefrom];
		return item;
	}
	

	public Iterator<Item> iterator() { return new QueueIterator(); }
	
	private class QueueIterator implements Iterator<Item> { //CREATE A CONDENSED COPY OF THE QUEUE, THEN SHUFFLE IT; 
		private Item[] copy = (Item[]) new Object[N]; //WHY COULDN'T I BREAK THIS INTO A DECLARATION AND INSTANTIATION? Not allowed for instance variables.
		private int count = 0;

		private QueueIterator() {
			int k = 0;
			for (int i = 0; i <= last; i++) {
				if (queue[i] != null)	{
					copy[k] = queue[i];
					k++;
					//StdOut.print(copy[k]);
					}
			}
			
			//SHUFFLE
			StdRandom.shuffle(copy);

		}
		
		public boolean hasNext() { return (count < N); }
		
		public void remove() {
			throw new UnsupportedOperationException(); 
		};
		
		public Item next() {
			if (!hasNext())  throw new NoSuchElementException("There is no item to remove.");
			Item item = copy[count];
			count++;
			return item;
		}	
	}
	
	
	public static void main(String[] args) { //Take in a list of numbers and print them in a random order
		RandomizedQueue<Integer> store;
		store = new RandomizedQueue<Integer>();
		int k = args.length;
		for (int i = 0; i < k; i++) {
			store.enqueue(Integer.parseInt(args[i]));
		}
		for (Integer s : store) {
			StdOut.println("s = " + s);			
		}
	}
	
	
	
}
