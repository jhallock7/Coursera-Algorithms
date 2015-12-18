

import java.util.Iterator;
import java.util.NoSuchElementException;
public class Deque<Item> implements Iterable<Item> {
	
	private Node first; 	//First item in deque.
	private Node last; 		//Last item in deque.
	private int N = 0;  		//Number of items in deque.
	
	private class Node { 	//Nexted class to define nodes.
		Item item;
		Node next;
		Node previous;
	}
	
	public boolean isEmpty() {
		return first == null;
		}
	
	public int size() {
		return N;
		}
	
	public void addFirst(Item item) {
		if (item == null) throw new NullPointerException("Can't add a null item."); 
		if (N == 0) {
			first = new Node();
			first.item = item;
			last = first;
		}
		else {
			Node oldfirst = first;
			first = new Node();
			first.item = item;
			first.next = oldfirst;
			oldfirst.previous = first;
		}		
		N++;	
	}
	
	public void addLast(Item item) {
		if (item == null) throw new NullPointerException("Can't add a null item."); 
		if (N == 0) {
			first = new Node();
			first.item = item;
			last = first;
		}
		else {
			Node oldlast = last;
			last = new Node();
			last.item = item;
			last.previous = oldlast;
			oldlast.next = last;
		}
		N++;
	}
	
	public Item removeFirst() {
		if (N <= 0) throw new NoSuchElementException("There is no item to remove."); 
		if (N == 1) {
			Item removing = first.item;
			first = null;
			last = null;
			N--;
			return removing;
		}
		else {
			Item removing = first.item;
			first = first.next;
			first.previous = null;
			N--;
			return removing;
			}
	}
	
	public Item removeLast() {
		if (N <= 0) throw new NoSuchElementException("There is no item to remove."); 
		if (N == 1) {
			Item removing = last.item;
			first = null;
			last = null;
			N--;
			return removing;
		}
		Item removing = last.item;
		last = last.previous;
		last.next = null;
		N--;
		return removing;
	}
	
	
	public Iterator<Item> iterator() {
		return new ListIterator();		
	}
	
	private class ListIterator implements Iterator<Item> {
		private Node current = first; //Switch "first" to "last" and later "next" to previous" to reverse order of iteration
		public boolean hasNext() {
			return current != null;
		}
		public void remove() {
			throw new UnsupportedOperationException(); 
		};
		public Item next() {
			if (current == null) throw new NoSuchElementException("There is no item to remove."); 
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	
	public static void main(String[] args) {
		Deque<Integer> deque = new Deque<Integer>();
		//StdOut.println("Blah1");
		StdOut.println("You inputted " + args.length + " integers");
		int k = 0;
		while (k < args.length) {
			deque.addFirst(Integer.parseInt(args[k]));
			k++;
		}
		
		for (int s : deque) {
			StdOut.println(s);
		}
	}
		

	
}


