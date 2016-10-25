/******************************************************************************
 *  Compilation:  javac Bag.java
 *  Execution:    java Bag < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  A generic bag or multiset, implemented using a singly-linked list.
 *
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java Bag < tobe.txt
 *  size of bag = 14
 *  is
 *  -
 *  -
 *  -
 *  that
 *  -
 *  -
 *  be
 *  -
 *  to
 *  not
 *  or
 *  be
 *  to
 *
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *  The {@code Bag} class represents a bag (or multiset) of 
 *  generic items. It supports insertion and iterating over the 
 *  items in arbitrary order.
 *  <p>
 *  This implementation uses a singly-linked list with a static nested class Node.
 *  See {@link LinkedBag} for the version from the
 *  textbook that uses a non-static nested class.
 *  The <em>add</em>, <em>isEmpty</em>, and <em>size</em> operations
 *  take constant time. Iteration takes time proportional to the number of items.
 *  <p>
 *  
 *  @param <Item> the generic type of an item in this bag
 */
public class Bag<Item> implements Iterable<Item> {
	private Node<Item> first;
	private int n; 
	
	// Initial Bag as empty
	public Bag() {
		first = null;
		n = 0;
	}
	
	// Helper linked list class
	private static class Node<Item> {
		private Item item;
		private Node<Item> next;
	}
	
	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return n;
	}
	
	// Add item into Bag
	public void add(Item item) {
		// Insert at the beginning
		// http://algs4.cs.princeton.edu/13stacks/
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}

	private class ListIterator<Item> implements Iterator<Item> {
		// Define a iterator(current) which the same type as elements need to iterate
		private Node<Item> current; 
		
	    // How to create a generic constructor for a generic class in java?	
		// http://stackoverflow.com/questions/8680442/how-to-create-a-generic-constructor-for-a-generic-class-in-java
		public ListIterator(Node<Item> first) {
			// When initialize the iterator, binding the iterator(start position which prior to 
			// the first element in chain need to iterate) to first element
			this.current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		// Confusion regarding the initial position of the pointer in a list iterated by the Iterator interface
		// http://stackoverflow.com/questions/25060742/confusion-regarding-the-initial-position-of-the-pointer-in-a-list-iterated-by-th
		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			
			// As iterator(current) initialized position prior to the first element,
			// next() method need to return the initial binding as first element's item
			// initially. Iterator(current) always binding(point to) the element right
			// behind it, e.g Initially iterator locate prior than element at position 0,
			// and point to element at position 0, when move to next, iterator locate at
			// the gap between element at position 0 and position 1, and point to element
			// at position 1. The next() method actually describe at the view of iterator,
			// element at position 0 which is the first element will be treated as first
			// element next to iterator and return by next() method.
			Item item = current.item;
			
			// The iterator will move to next position, e.g initial position is prior to
			// the first element, after iteration it will move to the gap between first
			// element and second element as if second element exists, also, its binding
			// will change from first element to second element.
			current = current.next;
			
			return item;
		}

		 // A bag is a collection where removing items is not supported—its purpose is to provide clients with the 
		 // ability to collect items and then to iterate through the collected items.
		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
