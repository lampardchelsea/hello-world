/******************************************************************************
 *  Compilation:  javac Stack.java
 *  Execution:    java Stack < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/13stacks/tobe.txt
 *
 *  A generic stack, implemented using a singly-linked list.
 *  Each stack element is of type Item.
 *
 *  This version uses a static nested class Node (to save 8 bytes per
 *  Node), whereas the version in the textbook uses a non-static nested
 *  class (for simplicity).
 *  
 *  % more tobe.txt 
 *  to be or not to - be - - that - - - is
 *
 *  % java Stack < tobe.txt
 *  to be not that or be (2 left on stack)
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 *  The {@code Stack} class represents a last-in-first-out (LIFO) stack of generic items.
 *  It supports the usual <em>push</em> and <em>pop</em> operations, along with methods
 *  for peeking at the top item, testing if the stack is empty, and iterating through
 *  the items in LIFO order.
 *  <p>
 *  This implementation uses a singly-linked list with a static nested class for
 *  linked-list nodes. See {@link LinkedStack} for the version from the
 *  textbook that uses a non-static nested class.
 *  The <em>push</em>, <em>pop</em>, <em>peek</em>, <em>size</em>, and <em>is-empty</em>
 *  operations all take constant time in the worst case.
 *  <p>
 *
 *  @param <Item> the generic type of an item in this stack
 */
public class PrincetonStack<Item> implements Iterable<Item> {
	private Node<Item> first;
	private int n;
	
	public PrincetonStack() {
		first = null;
		n = 0;
	}
	
	private class Node<Item> {
		private Item item;
		private Node<Item> next;
	}
	
	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return n;
	}
	
	public void push(Item item) {
		Node<Item> oldfirst = first;
		first = new Node<Item>();
		first.item = item;
		first.next = oldfirst;
		n++;
	}

	public Item pop() {
		// Don't forget to check whether it is empty.
		if(isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
		Item item = first.item;
		first = first.next;
		n--;
		return item;
	}
	
	public Item peek() {
		if(isEmpty()) {
			throw new NoSuchElementException("Stack underflow");
		}
		return first.item;
	}
	
    /**
     * Returns an iterator to this stack that iterates through the items in LIFO order.
     * Also the iterator scanning order of stack is opposite to java source Stack
     * implementation which iterate from buttom-up because it extends Vector
     * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/Stack.java 
     * @return an iterator to this stack that iterates through the items in LIFO order
     */
	@Override
	public Iterator<Item> iterator() {
		return new ListIterator<Item>(first);
	}
	
	private class ListIterator<Item> implements Iterator<Item> {
		private Node<Item> current;
		
		public ListIterator(Node<Item> first){
			this.current = first;
		}
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order, separated by spaces
     */
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Item item : this) {
			s.append(item + " ");
		}
		
		return s.toString();
	}
	
    /**
     * Unit tests the {@code Stack} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        PrincetonStack<String> stack = new PrincetonStack<String>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        stack.push("5");
        for(String s : stack) {
        	System.out.println("Iterator result: " + s);
        }
    }
}
