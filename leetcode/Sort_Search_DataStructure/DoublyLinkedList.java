// Solution 1: Princeton Solution
/******************************************************************************
 *  Refer to
 *  http://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html
 * 
 *  Compilation:  javac DoublyLinkedList.java
 *  Execution:    java DoublyLinkedList
 *  Dependencies: StdOut.java
 *
 *  A list implemented with a doubly linked list. The elements are stored
 *  (and iterated over) in the same order that they are inserted.
 * 
 *  % java DoublyLinkedList 10
 *  10 random integers between 0 and 99
 *  24 65 2 39 86 24 50 47 13 4 
 *
 *  add 1 to each element via next() and set()
 *  25 66 3 40 87 25 51 48 14 5 
 *
 *  multiply each element by 3 via previous() and set()
 *  75 198 9 120 261 75 153 144 42 15 
 *
 *  remove elements that are a multiple of 4 via next() and remove()
 *  75 198 9 261 75 153 42 15 
 *
 *  remove elements that are even via previous() and remove()
 *  75 9 261 75 153 15 
 *
 ******************************************************************************/
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Refer to
 * http://stackoverflow.com/questions/40956297/how-doublylinkedlistprinceton-version-remove-method-works
 * As suggest, this implementation should not used in practice.
 * 
 * Refer to
 * http://algs4.cs.princeton.edu/13stacks/
 * http://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html
 * 
 * Iterator refer to
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Sort_Search_DataStructure/Bag.java
 * http://stackoverflow.com/questions/25060742/confusion-regarding-the-initial-position-of-the-pointer-in-a-list-iterated-by-th
 * http://mrbool.com/how-to-create-iterator-in-java/26422
 * 
 * List iterators. 
 * We might also want to include methods hasPrevious() and previous() for going backwards in the list. 
 * To implement previous() we can use a doubly-linked list. Program DoublyLinkedList.java implements this strategy. 
 * It uses Java's ListIterator interface to support moving forwards and backwards. We implement all optional methods, 
 * including remove(), set(), and add(). The method remove() deletes the last element returned by either next() or 
 * previous(). The method set() overwrites the value of the last element returned by either next() or previous(). 
 * The method add() inserts an element before the next element that would be returned by next(). It is legal to call 
 * set() and remove() only after a call either to next() or previous(), and with no intervening calls to either 
 * remove() or add().
 * 
 * We use a dummy head and tail node to avoid extra cases. We also store an extra variable lastAccessed which stores 
 * the node accessed in the most recent call to next() or previous(). After removing an element, we reset lastAccessed 
 * to null; this designates that calling remove() is illegal (until after a subsequent call to either next() or previous().
 */
public class DoublyLinkedList<Item> implements Iterable<Item> {
	private int n;
	private Node pre;
	private Node post;  
	
	private class Node {
		private Item item;
		private Node next;
		private Node prev;
	}
	
	// Why pre.next = post; post.prev = pre;
	// Refer to
	// http://www.jianshu.com/p/681802a00cdf#
	public DoublyLinkedList() {
		pre = new Node();
		post = new Node();
		pre.next = post;
		post.prev = pre;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public int size() {
		return n;
	}
	
	public void add(Item item) {
		Node last = post.prev;
		Node x = new Node();
		// Build four links(x-post/post-x/x-last/last-x) 
		// for mutual connection design and assign one item 
		x.item = item;
		x.prev = last;
		x.next = post;
		post.prev = x;
		last.next = x;
		n++;
	}
    
	public ListIterator<Item> iterator() {
		return new DoublyLinkedListIterator();
	}
	
	private class DoublyLinkedListIterator implements ListIterator<Item> {
		private Node current = pre.next;
		// the last node to be returned by prev() or next()
        // reset to null upon intervening remove() or add()
		private Node lastAccessed = null;
		private int index = 0;

		@Override
		public boolean hasNext() {
			return index < n;
		}

		@Override
		public Item next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			lastAccessed = current;
			Item item = current.item;
			current = current.next;
			index++;
			return item;
		}

		@Override
		public boolean hasPrevious() {
			return index > 0;
		}

		@Override
		public Item previous() {
			if(!hasPrevious()) {
				throw new NoSuchElementException();
			}
			// E.g After insert 28-19-82 by next() method, as
            // current = current.next; happen 3 times, current
            // is now as same as dummy node 'post'(the real 
			// structure is pre-28-19-82-post), should move
            // back for one step to find the lastAccessed node,
            // in example here is move back to 82 at first time	
			current = current.prev;
			lastAccessed = current;
			index--;
			return current.item;

		}

		@Override
		public int nextIndex() {
			return index;
		}

		@Override
		public int previousIndex() {
			return index - 1;
		}

		@Override
		public void remove() {
			if(lastAccessed == null) {
				throw new IllegalStateException();
			}
			// Must use lastAccessed, otherwise, if use current
			// will mistakenly point to next node, e.g if need
			// to move 60 from 87-60-249, when calling remove() as
			//    while (iterator.hasNext()) {
            //       int x = iterator.next();
            //       if (x % 4 == 0) iterator.remove();
            //    }
		    // the current = current.next() will make current point
			// to 249, but as lastAccessed = current store real current
			// previously than current = current.next(), we can use
			// lastAccessed to point to right node
			Node x = lastAccessed.prev;
			Node y = lastAccessed.next;
			x.next = y;
			y.prev = x;
			n--;
			// Refer to
			// http://stackoverflow.com/questions/40956297/how-doublylinkedlistprinceton-version-remove-method-works
			// The if condition distinguishes between a remove called after a call to previous or next. 
			// The else branch covers the next case.
			if(current == lastAccessed) {
				current = y;
			} else {
				index--;
			}
			lastAccessed = null;
		}


		@Override
		public void set(Item item) {
			if(lastAccessed == null) {
				throw new IllegalStateException();
			}
			lastAccessed.item = item;
		}

		@Override
		public void add(Item item) {
			Node x = current.prev;
			Node y = new Node();
			Node z = current;
			x.next = y;
			y.next = z;
			z.prev = y;
			y.prev = x;
			y.item = item;
			n++;
			index++;
			lastAccessed = null;
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(Item item : this) {
			s.append(item + " ");
		} 
		return s.toString();

	}

    // a test client
    public static void main(String[] args) {
        int n  = 3;

        // add elements 1, ..., n
        StdOut.println(n + " random integers between 0 and 99");
        DoublyLinkedList<Integer> list = new DoublyLinkedList<Integer>();
        for (int i = 0; i < n; i++)
            list.add(StdRandom.uniform(100));
        StdOut.println(list);
        StdOut.println();

        ListIterator<Integer> iterator = list.iterator();

        // go forwards with next() and set()
        StdOut.println("add 1 to each element via next() and set()");
        while (iterator.hasNext()) {
            int x = iterator.next();
            iterator.set(x + 1);
        }
        StdOut.println(list);
        StdOut.println();
        
        // go backwards with previous() and set()
        StdOut.println("multiply each element by 3 via previous() and set()");
        while (iterator.hasPrevious()) {
            int x = iterator.previous();
            iterator.set(x + x + x);
        }
        StdOut.println(list);
        StdOut.println();

        // test on remove() if-else if branch as (current == lastAccessed)
        // this remove() case only happen after a call to iterator.previous()
        StdOut.println("remove all elements");
        while (iterator.hasNext()) {
        	int x = iterator.next();
        	// in this test, we will remove all elements as x will not be 0,
        	// Note: below line is original document condition to use remove()
            // remove the element that was last accessed by next() or previous()
            // condition: no calls to remove() or add() after last call to next() or previous()
        	if(x != 0) iterator.remove();
        }
        StdOut.println(list);
        StdOut.println("-----empty list------");
        
//        // remove all elements that are multiples of 4 via next() and remove()
//        StdOut.println("remove elements that are a multiple of 4 via next() and remove()");
//        while (iterator.hasNext()) {
//            int x = iterator.next();
//            if (x % 4 == 0) iterator.remove();
//        }
//        StdOut.println(list);
//        StdOut.println();
//
//
//        // remove all even elements via previous() and remove()
//        StdOut.println("remove elements that are even via previous() and remove()");
//        while (iterator.hasPrevious()) {
//            int x = iterator.previous();
//            if (x % 2 == 0) iterator.remove();
//        }
//        StdOut.println(list);
//        StdOut.println();
//
//
//        // add elements via next() and add()
//        StdOut.println("add elements via next() and add()");
//        while (iterator.hasNext()) {
//            int x = iterator.next();
//            iterator.add(x + 1);
//        }
//        StdOut.println(list);
//        StdOut.println();
//
//        // add elements via previous() and add()
//        StdOut.println("add elements via previous() and add()");
//        while (iterator.hasPrevious()) {
//            int x = iterator.previous();
//            iterator.add(x * 10);
//            iterator.previous();
//        }
//        StdOut.println(list);
//        StdOut.println();
    }
	
}

// Java Standard Library Solution
// Refer to
// http://www.jianshu.com/p/681802a00cdf#
// http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/LinkedList.java
// http://opendatastructures.org/ods-python-screen.pdf
// http://faculty.cs.uwlax.edu/~mallen/courses/cs340/lec08.pdf
