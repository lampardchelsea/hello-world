// Solution 1: Princeton Solution (Not Recommended)
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
 * http://stackoverflow.com/questions/40956297/how-doublylinkedlist-princeton-version-remove-method-works
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


// Similar To Princeton Version Recommended Solution (Both contain sentinel header/trailer nodes, 
// not similar to Java Standard Library only contain one header sentinel node)
// Refer to
// http://stackoverflow.com/questions/40956297/how-doublylinkedlist-princeton-version-remove-method-works?noredirect=1#comment69131584_40956297
// https://www.cpp.edu/~ftang/courses/CS240/lectures/dlist.htm




// Java Standard Library Solution
// Refer to
// http://www.jianshu.com/p/681802a00cdf#
// http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/LinkedList.java
// http://opendatastructures.org/ods-python-screen.pdf
// http://faculty.cs.uwlax.edu/~mallen/courses/cs340/lec08.pdf
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Refer to
 * http://www.jianshu.com/p/681802a00cdf#
 * http://grepcode.com/file_/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/LinkedList.java/?v=source
 */
public class QuanLinkedList<E> implements List<E> {
    // The transient keyword in Java is used to indicate that a field should not be serialized
	// The header is a dummy entry, initialize with value null on element
    private transient Entry<E> header = new Entry<E>(null, null, null);
    // The counter used for record how many entries exist in LinkedList
    private int size = 0;
    
	private static class Entry<E> {
		E element;
		Entry<E> next;
		Entry<E> previous;
		
		Entry(E element, Entry<E> next, Entry<E> previous) {
			this.element = element;
			this.next = next;
			this.previous = previous;
		} 
	}
	
    /**
     * Constructs an empty list.
     */
	public QuanLinkedList() {
		// Set up dummy header, its next node and previous node
		// both point to itself initially
		header.next = header.previous = header;
	}

    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     *
     * @param  c the collection whose elements are to be placed into this list
     * @throws NullPointerException if the specified collection is null
     */
	public QuanLinkedList(Collection<? extends E> c) {
		this();
		addAll(c);
	}
	
    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
	@Override
	public boolean contains(Object o) {
		return indexOf(o) != -1;
	}

	@Override
	public Iterator<E> iterator() {
		return listIterator();
	}

    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list
     *         in proper sequence
     */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for(Entry<E> e = header.next; e != header; e = e.next) {
			result[i++] = e.element;
		}
		return result;
	}

    /**
     * Appends the specified element to the end of this list.
     *
     * <p>This method is equivalent to {@link #addLast}.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
	@Override
	public boolean add(E e) {
		// Add before dummy header equals to add after tailer,
		// because the new entry will be insert between header.previous
		// and header, and header.previous equals to tailer as
		// design based on double circular linked list
		addBefore(e, header);
		return true;
	}

	
	private Entry<E> addBefore(E e, Entry<E> entry) {
		// Correspond to constructor of Entry<E>, the second parameter
		// 'entry' is successor entry of 'newEntry' after insert, 
		// 'entry.previous' is predecessor entry of 'newEntry' after insert 
		Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
		// As newEntry already set one side relationship as
		// 'this.next = next' and 'this.previous = previous'
		// when it constructed, we still need to set up another
		// side relationship to construct mutual relationship
		newEntry.previous.next = newEntry;
		newEntry.next.previous = newEntry;
		// Only when we add real entry into LinkedList the counter
		// will increase, the dummy header not included in counter
		size++;
		return newEntry;
	}

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
	@Override
	public boolean remove(Object o) {
		// Loop through the list to find entry need to remove
		if(o == null) {
			for(Entry<E> e = header.next; e != header; e = e.next) {
				if(e.element == null) {
					remove(e);
					return true;
				}
			}
		} else {
			for(Entry<E> e = header.next; e != header; e = e.next) {
				if(o.equals(e.element)) {
					remove(e);
					return true;
				}
			}
		}
		return false;
	}

	public E remove(Entry<E> e) {
		if(e == header) {
			throw new NoSuchElementException();
		}
		E result = e.element;
		// Delete mutual relationship of predecessor and successor
		e.previous.next = e.next;
		e.next.previous = e.previous;
		// Also need to delete entry e itself, will add into gc
		e.previous = e.next = null;
		e.element = null;
		size--;
		return result;
	}
	
    /**
     * Refer to {@code AbstractCollection<E>}
     * {@inheritDoc}
     *
     * <p>This implementation iterates over the specified collection,
     * checking each element returned by the iterator in turn to see
     * if it's contained in this collection.  If all elements are so
     * contained <tt>true</tt> is returned, otherwise <tt>false</tt>.
     *
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @see #contains(Object)
     */
	@Override
	public boolean containsAll(Collection<?> c) {
		for(Object e : c) {
			if(!contains(e)) {
				return false;
			}
		}
		return true;
	}
	
    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the specified
     * collection's iterator.  The behavior of this operation is undefined if
     * the specified collection is modified while the operation is in
     * progress.  (Note that this will occur if the specified collection is
     * this list, and it's nonempty.)
     *
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// Add the collection to the end of current list, index given as 'size'
		return addAll(size, c);
	}

    /**
     * Inserts all of the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the list in the order that they are returned by the
     * specified collection's iterator.
     *
     * @param index index at which to insert the first element
     *              from the specified collection
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
	@SuppressWarnings("unchecked")
	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		// Boundary exceeds check
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		// How many entries need to insert
		Object[] a = c.toArray();
		int numNew = a.length;
		if(numNew == 0) {
			return false;
		}
		// Find successor and predecessor of entries need to insert
		// Find the first entry after indexed position, if index equals list size,
		// then next entry is header, otherwise find the index entry by entry(index)
		Entry<E> successor = (index == size ? header : entry(index));
		// Find the first entry before indexed position, as we already get successor
		// entry of indexed position and no insertion happen for now, the first entry
		// before indexed position is apparently as previous entry of successor
		Entry<E> predecessor = successor.previous;
		// Insert all entries in a loop
		for(int i = 0; i < numNew; i++) {
			// Cast Object a[i] to E type
			Entry<E> e = new Entry<E>((E)a[i], successor, predecessor);
			predecessor.next = e;
			// Reset the predecessor as new joined entry for next loop
			// e.g Original list is 1-2-3 need to add {4, 5} at index = 2,
			// as entry(2) will return successor as 3, predecessor as 2,
			// we will insert 4 first, 4's successor is 3, predecessor
			// is 2, list change to 1-2-4-3, then we update predecessor
			// from 2 to 4, but keep successor as 3 and insert 5, finally
			// the list change to 1-2-4-5-3
			predecessor = e;
		}
		// Finally set the successor point to its predecessor
		// to consummate mutual relationship
		successor.previous = predecessor;
		size += numNew;
		return true;
	}

	/**
     * Returns the indexed entry.
     */
	private Entry<E> entry(int index) {
		// Boundary exceeds check
		if(index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
		// Retrieve dummy header
		Entry<E> e = header;
		// Simple binary search(divide only once, size >> 1) to promote 
		// searching indexed entry more efficiently, start from header,
		// index for header is 0
		if(index < (size >> 1)) {
			// If near mid-index of list, loop from left to right by next
			for(int i = 0; i <= index; i++) {
				e = e.next;
			}
		} else {
			// If far from mid-index of list, loop from right to left by previous
			for(int i = size; i > index; i--) {
				e = e.previous;
			}
		}
		return e;
	}

    /**
     * Removes all of the elements from this list.
     */
	@Override
	public void clear() {
		// Retrieve the real entry after dummy header
		Entry<E> e = header.next;
		while(e != header) {
			// Save the next entry for next loop
			Entry<E> next = e.next;
			e.next = e.previous = null;
			e.element = null;
			e = next;
		}
		// Individually process header
		header.next = header.previous = null;
		size = 0;
	}

    /**
     * Returns the element at the specified position in this list.
     *
     * @param index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	@Override
	public E get(int index) {
		return entry(index).element;
	}

    /**
     * Replaces the element at the specified position in this list with the
     * specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	@Override
	public E set(int index, E element) {
		Entry<E> e = entry(index);
		E oldVal = e.element;
		e.element = element;
		return oldVal;
	}

    /**
     * Inserts the specified element at the specified position in this list.
     * Shifts the element currently at that position (if any) and any
     * subsequent elements to the right (adds one to their indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	@Override
	public void add(int index, E element) {
		addBefore(element, (index == size ? header : entry(index)));
	}

    /**
     * Removes the element at the specified position in this list.  Shifts any
     * subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     *
     * @param index the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
	@Override
	public E remove(int index) {
		return remove(entry(index));
	}

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the first occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
	@Override
	public int indexOf(Object o) {
		int index = 0;
		if(o == null) {
			// Loop from the real entry, one behind dummy header
			for(Entry<E> e = header.next; e != header; e = e.next) {
				if(e.element == null) {
					return index;
				}
				index++;
			}
		} else {
			for(Entry<E> e = header.next; e != header; e = e.next) {
				if(o.equals(e.element)) {
					return index;
				}
				index++;
			}
		}
		return -1;
	}

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     *
     * @param o element to search for
     * @return the index of the last occurrence of the specified element in
     *         this list, or -1 if this list does not contain the element
     */
	@Override
	public int lastIndexOf(Object o) {
		int index = size;
		if(o == null) {
			for(Entry<E> e = header.previous; e != header; e = e.previous) {
				// Because we set index = size (not size - 1) at first,
				// but list index range is 0 to size - 1, so we need
				// to minus 1 first and then return when hit the loop
				// at first time
				index--;
				if(e.element == null) {
					return index;
				}
			}
		} else {
			for(Entry<E> e = header.previous; e != header; e = e.previous) {
				index--;
				if(o.equals(e.element)) {
					return index;
				}
			}
		}
		return -1;
	}

	// Iterator(not focus on this part)
    /**
     * Refer to {@code AbstractSequentialList<E>} and {@code AbstractList<E>}
     */
	@Override
	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

    /**
     * Refer to {@code AbstractList<E>}
     */
	@Override
	public ListIterator<E> listIterator(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
        //return new ListItr(index);
        // As not focus on this part, set dummy return to null
        return null;
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		// As not focus on this part, set dummy return to null
		return null;
	}
	
    /**
     * Refer to {@code AbstractCollection<E>}
     */
	@Override
    public <T> T[] toArray(T[] a) {
		// As not focus on this part, set dummy return to null
        return null;
    }
	
    /**
     * Refer to {@code AbstractCollection<E>}
     */
	@Override
	public boolean removeAll(Collection<?> c) {
		// As not focus on this part, set dummy return to null
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// As not focus on this part, set dummy return to null
		return false;
	}
}

