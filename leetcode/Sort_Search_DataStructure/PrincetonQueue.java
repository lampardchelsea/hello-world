/******************************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt  
 *
 *  A generic queue, implemented using a linked list.
 *
 *  % java Queue < tobe.txt 
 *  to be or not to be (2 left on queue)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code Queue} class represents a first-in-first-out (FIFO)
 *  queue of generic items.
 *  It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *  operations, along with methods for peeking at the first item,
 *  testing if the queue is empty, and iterating through
 *  the items in FIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a static nested class for
 *  linked-list nodes. See {@link LinkedQueue} for the version from the
 *  textbook that uses a non-static nested class.
 *  The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *
 *  @param <Item> the generic type of an item in this queue
 */
public class PrincetonQueue<Item> implements Iterable<Item> {
	// beginning of queue
	private Node<Item> first;
	
	// end of queue
	private Node<Item> last;
	
	// number of elements on queue
	private int n;
	
	// helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}
	
    /**
     * Initializes an empty queue.
     */
	public PrincetonQueue() {
		first = null;
		last = null;
		n = 0;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	public int size() {
		return n;
	}

    /**
     * Adds the item to this queue.
     *
     * @param  item the item to add
     */
	public void enqueue(Item item) {
		Node<Item> oldlast = last;
		last = new Node<Item>();
		last.item = item;
		last.next = null;
		if(isEmpty()) {
			// Initial status of queue is empty need special
			// handling as set up Node<Item> first = last,
			// when enqueue happens, last node generate a copy as
			// oldlast node to save initial status, as first time
			// enqueue, the counter n = 0, isEmpty() is true,
			// so we set first = last, e.g when enqueue "0" as first
			// item onto queue the first node is same as last
			// node as {item = 0, next = null}
			first = last;
		} else {
			// When the second time enqueue, last node assigned as
			// new value, but the copy of last node as oldlast
			// not changed until now, and oldlast node is also the 
			// copy of original first node as we set first = last 
			// when first time enqueue happens, so now oldlast.next = last 
			// will cause the same behavior as first.next = last,
			// which concatenate first node to next node.
			oldlast.next = last;
		}
		n++;
	}
	
    /**
     * Removes and returns the item on this queue that was least recently added.
     *
     * @return the item on this queue that was least recently added
     * @throws NoSuchElementException if this queue is empty
     */
	public Item dequeue() {
		if(isEmpty()) {
			throw new NoSuchElementException("Queue underflow");
		}
		Item item = first.item;
		first = first.next;
		n--;
		// to avoid loitering
		// Loitering: Holding a reference to an object when it is no longer needed.
		// In Queue case is dequeue only clear Node<Item> first to null, but 
		// Node<Item> last is still keep as is, so need to clear Node<Item> last
		// as well to make sure no loitering
		if(isEmpty()) {
			last = null;
		}
		return item;
	}
	
	/**
	 * Returns the item least recently added to this queue.
	 *
	 * @return the item least recently added to this queue
	 * @throws NoSuchElementException if this queue is empty
	 */
	public Item peek() {
		if(isEmpty()) {
			throw new NoSuchElementException("Queue underflow");
		}
		return first.item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}
	
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;
		
		private ListIterator(Node<Item> first) {
			current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Item item : this) {
			s.append(item + " ");
		}
		
		return s.toString(); 
	}
	
	/**
	 * Unit tests the {@code Queue} data type.
	 *
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {
		PrincetonQueue<String> queue = new PrincetonQueue<String>();
		for(int i = 0; i <= 5; i++) {
			queue.enqueue(String.valueOf(i));
		}
		
		for(int i = 0; i <= 5; i++) {
			String s = queue.dequeue();
			StdOut.print(s + " ");
		}
		
        // This part is hard to implement on eclipse
		// as StdIn cannot used without terminal, use
		// above two for loops to instead unit test.
//		while(!StdIn.isEmpty()) {
//			String item = StdIn.readString();
//			if(!item.equals("-")) {
//				queue.enqueue(item);
//			} else if(!queue.isEmpty()) {
//				String s = queue.dequeue();
//				StdOut.print(s + " ");
//			}
//		}
		
		StdOut.println("(" + queue.size() + " left on queue)");
	}
}
