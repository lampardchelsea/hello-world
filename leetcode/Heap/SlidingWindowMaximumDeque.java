import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Refer to
 * https://segmentfault.com/a/1190000003903509
 * 双向队列
 * 复杂度
 * 时间 O(N) 空间 O(K)
 * 思路
 * 我们用双向队列可以在O(N)时间内解决这题。当我们遇到新的数时，将新的数和双向队列的末尾比较，如果末尾比新数小，
 * 则把末尾扔掉，直到该队列的末尾比新数大或者队列为空的时候才住手。这样，我们可以保证队列里的元素是从头到尾降序的，
 * 由于队列里只有窗口内的数，所以他们其实就是窗口内第一大，第二大，第三大...的数。保持队列里只有窗口内数的方法和
 * 上个解法一样，也是每来一个新的把窗口最左边的扔掉，然后把新的加进去。然而由于我们在加新数的时候，已经把很多没用
 * 的数给扔了，这样队列头部的数并不一定是窗口最左边的数。这里的技巧是，我们队列中存的是那个数在原数组中的下标，
 * 这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数。这里为什么时间复杂度是O(N)呢？因为每个数
 * 只可能被操作最多两次，一次是加入队列的时候，一次是因为有别的更大数在后面，所以被扔掉，或者因为出了窗口而被扔掉
 * 
 * Relation between Java LinkedList implementation and Deque
 * Refer to
 * https://www.quora.com/What-is-the-difference-between-a-deque-and-a-doubly-linked-list
 * A deque is an abstract data structure. It can be implemented either using a linked list or an array. 
 * It is a just concept, and the implementation depends upon the programmer. Think of it as a special 
 * type of queue which can accept elements both at the head and the tail.
 * On the other hand, a doubly linked list is a concrete data structure, i.e. 
 * its implementation would be same for every programmer. It is a way for storing data. Just as arrays.
 * 
 * http://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
 * public class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, Serializable
 * Doubly-linked list implementation of the List and Deque interfaces. 
 * Implements all optional list operations, and permits all elements (including null).
 * All of the operations perform as could be expected for a doubly-linked list. Operations that index into 
 * the list will traverse the list from the beginning or the end, whichever is closer to the specified index.
 * 
 * http://docs.oracle.com/javase/7/docs/api/java/util/Deque.html
 * public interface Deque<E> extends Queue<E>
 * A linear collection that supports element insertion and removal at both ends. The name deque is short for 
 * "double ended queue" and is usually pronounced "deck". Most Deque implementations place no fixed limits 
 * on the number of elements they may contain, but this interface supports capacity-restricted deques as 
 * well as those with no fixed size limit.
 * 
 * Deque Implementation
 * Refer to
 * http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 * http://www.jyuan92.com/blog/coursera-algorithmprinceton-hw2-queues/#Deque-2
 * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/LinkedList.java#LinkedList
 */
public class MaxSlidingWindowDeque {
	public int[] maxSlidingWindow(int[] nums, int k) {
		int len = nums.length;
		if(nums == null || len == 0) {
			return new int[0];
		}
		// 我们队列中存的是那个数在原数组中的下标，这样我们既可以直到这个数的值，也可以知道该数是不是窗口最左边的数
		Deque<Integer> deque = new Deque<Integer>();
		int[] result = new int[len - k + 1];
		for(int i = 0; i < len; i++) {
			// 每当新数进来时，如果发现队列头部的数的下标，是窗口最左边数的下标，则扔掉
			if(!deque.isEmpty() && deque.peekFirst() == i - k) {
				deque.poll();
			}
			// 把队列尾部所有比新数小的都扔掉，保证队列是降序的
			while(!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
				deque.removeLast();
			}
			// 加入新数
			deque.offerLast(i);
			// 队列头部就是该窗口内第一大的
			if(i + 1 >= k) {
				result[i + 1 - k] = nums[deque.peek()];
			}
		}
		return result;
	}
	
	private class Deque<E> {
		// The header is a dummy entry, initialize with value null on element
		private Entry<E> header = new Entry<E>(null, null, null);
		// The counter used for record how many entries exist in Deque
		private int size = 0;

		private class Entry<E> {
			E element;
			Entry<E> next;
			Entry<E> previous;
			Entry(E element, Entry<E> next, Entry<E> previous) {
				this.element = element;
				this.next = next;
				this.previous = previous;
			}
		}
		
		// Construct an empty deque
		public Deque() {
			// Set up dummy header, its next node and previous node
			// both point to itself initially
			header.next = header.previous = header;
		}
		
		/** Utility Methods */
		public int size() {
			return size;
		}
		
		public boolean isEmpty() {
			return size() == 0;
		}
		
		public Entry<E> addBefore(E e, Entry<E> entry) {
			// Correspond to constructor of Entry<E>, the second parameter
			// 'entry' is successor entry of 'newEntry' after insert,
			// 'entry.previous' is predecessor entry of 'newEntry' after insert
			Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
			// As newEntry already set one side relatinoship as
			// 'this.next = next' and 'this.previous = previous'
			// when it constructed, we still need to set up another
			// side relationship to construct mutual relationship
			newEntry.previous.next = newEntry;
			newEntry.next.previous = newEntry;
			// Only when we add real entry into Deque the counter
			// will increase, the dummy header not included in counter
			size++;
			return newEntry;
		}
		
		public E remove(Entry<E> e) {
			if(e == header) {
				throw new NoSuchElementException();
			}
			E result = e.element;
			e.previous.next = e.next;
			e.next.previous = e.previous;
			e.previous = e.next = null;
			e.element = null;
			size--;
			return result;
		}
		
		
		/** Insert Methods */
		// Insert the specified element at the beginning of this list
		public void addFirst(E e) {
			// Think as add before the first real node after dummy header,
			// represent as 'header.next', that's exactly "add before" mean
			addBefore(e, header.next);
		}
		
		// Insert the specified element at the front of this list, return true
		public boolean offerFirst(E e) {
			addFirst(e);
			return true;
		}
		
		// Appends the specified element to the end of this list
		public void addLast(E e) {
			// Think as add after the last real node but before dummy header,
			// represent as 'header', that's exactly "add before" mean
			addBefore(e, header);
		}
		
		// Insert the specified element at the end of this list, return true
		public boolean offerLast(E e) {
			addLast(e);
			return true;
		}
		
		/** Remove Methods */
		// Removes and returns the first element from this list
		public E removeFirst() {
			return remove(header.next);
		}
		
		// Retrieves and removes the first element of this list, or return null if this list is empty
		public E pollFirst() {
			if(size == 0) {
				return null;
			}
			return removeFirst();
		}
		
		// Removes and returns the last element from this list
		public E removeLast() {
			return remove(header.previous);
		}
		
		// Retrieve snd removes the last element of this list or returns null if this list is empty
		public E pollLast() {
			if(size == 0) {
				return null;
			}
			return remove(header.previous);
		}
		
		// Retrieves and removes the head (first element) of this list
		// Not throw exception like removeFirst()
		public E poll() {
			if(size == 0) {
				return null;
			}
			return removeFirst();
		}
		
		/** Examine Methods */
		// Returns the first element in this list
		public E getFirst() {
			if(size == 0) {
				throw new NoSuchElementException();
			}
			return header.next.element;
		}
		
		// Retrieves, but does not remove, the first element of this list, 
		// or returns null if this list is empty
		public E peekFirst() {
			if(size == 0) {
				return null;
			}
			return getFirst();
		}
		
		// Returns the last element in this list
		public E getLast() {
			if(size == 0) {
				throw new NoSuchElementException();
			}
			return header.previous.element;
		}
		
		// Retrieves, but does not remove, the first element of this list, 
		// or returns null if this list is empty
		public E peekLast() {
			if(size == 0) {
				return null;
			}
			return getLast();
		}
		
		// Retrieves, but does not remove, the head (first element) of this list.
		// Not throw exception like getFirst()
		public E peek() {
			if(size == 0) {
				return null;
			}
			return getFirst();
		}

		/** Iterator Methods*/
		/**
		 * Returns a list-iterator of the elements in this list(in proper sequence),
		 * starting at the specified position in the list. Obeys the general contract
		 * of List.listIterator(int).
		 * 
		 * The list-iterator is fail-fast: if the list is structurally modified at any
		 * time after the Iterator is created, in any way except through the list-iterator's
		 * own remove or add methods, the list-iterator will throw a 
		 * ConcurrentModificationException. Thus, in the face of concurrent modification,
		 * the iterator fails quickly and cleanly, rather than risking arbitrary,
		 * non-deterministic behavior at an undetermined time in the future.
		 * 
		 * @param index The index of the first element to be returned from the list-iterator
		 *              by a call to next
		 * @return a ListIterator of the elements at the specified position in the list
		 */
		public ListIterator<E> listIterator(final int index) {
			return new ListItr(index);
		}
		
		private class ListItr implements ListIterator<E> {
			// The last time returned entry
			private Entry<E> lastReturned = header;
			// Next entry
			private Entry<E> next;
			// Next entry index 
			private int nextIndex;
			/**
			 * Refer to
			 * modCount declaration in AbstractList class
			 * http://grepcode.com/file/repository.grepcode.com/java/root/jdk/openjdk/6-b14/java/util/AbstractList.java#AbstractList.0modCount
			 * The number of times this list has been <i>structurally modified</i>.
		     * Structural modifications are those that change the size of the
		     * list, or otherwise perturb it in such a fashion that iterations in
		     * progress may yield incorrect results.
		     *
		     * <p>This field is used by the iterator and list iterator implementation
		     * returned by the {@code iterator} and {@code listIterator} methods.
		     * If the value of this field changes unexpectedly, the iterator (or list
		     * iterator) will throw a {@code ConcurrentModificationException} in
		     * response to the {@code next}, {@code remove}, {@code previous},
		     * {@code set} or {@code add} operations.  This provides
		     * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
		     * the face of concurrent modification during iteration.
		     *
		     * <p><b>Use of this field by subclasses is optional.</b> If a subclass
		     * wishes to provide fail-fast iterators (and list iterators), then it
		     * merely has to increment this field in its {@code add(int, E)} and
		     * {@code remove(int)} methods (and any other methods that it overrides
		     * that result in structural modifications to the list).  A single call to
		     * {@code add(int, E)} or {@code remove(int)} must add no more than
		     * one to this field, or the iterators (and list iterators) will throw
		     * bogus {@code ConcurrentModificationExceptions}.  If an implementation
		     * does not wish to provide fail-fast iterators, this field may be
		     * ignored.		   
			 */
			// As no multiple thread used now, ignore 'modCount' for current use
			//private int expectedModCount = modCount;
			
			public ListItr(int index) {
				if(index < 0 || index > size) {
					throw new IndexOutOfBoundsException("Index: " + index + ", Size:" + size);
				}
				
				// Refer to
				// Bit shift operator
				// http://stackoverflow.com/questions/1050989/double-greater-than-sign-in-java
				// Optimization on loop direction
				// http://www.jianshu.com/p/0c458f335daf
				if(index < (size >> 1)) {
					next = header.next;
					for(nextIndex = 0; nextIndex < index; nextIndex++) {
						next = next.next;
					}
				} else {
					next = header;
					for(nextIndex = size; nextIndex > index; nextIndex--) {
						next = next.previous;
					}
				}
			}

			@Override
			public boolean hasNext() {
				return nextIndex != size;
			}

			@Override
			public E next() {
				//checkForComodification();
				if(nextIndex == size) {
					throw new NoSuchElementException();
				}
				lastReturned = next;
				next = next.next;
				nextIndex++;
				return lastReturned.element;
			}

			@Override
			public boolean hasPrevious() {
				return nextIndex != 0;
			}

			@Override
			public E previous() {
				if(nextIndex == 0) {
					throw new NoSuchElementException();
				}
				// Refer to
				// Right-associativity of assignment operators
				// https://en.wikipedia.org/wiki/Operator_associativity
				// Equal to
				// next = next.previous;
				// lastReturned = next;
				lastReturned = next = next.previous;
				nextIndex--;
				//checkForComodification();
				return lastReturned.element;
			}

			@Override
			public int nextIndex() {
				return nextIndex;
			}

			@Override
			public int previousIndex() {
				return nextIndex - 1;
			}

			@Override
			public void remove() {}

			@Override
			public void set(E e) {}

			@Override
			public void add(E e) {}
		}
	}

	
	public static void main(String[] args) {
		int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
		int k = 3;
//		int[] nums = {1, -1};
//		int k = 1;
		MaxSlidingWindow m = new MaxSlidingWindow();
		int[] result = m.maxSlidingWindow(nums, k);
		for(int i = 0; i < result.length; i++) {
			System.out.println(result[i]);
		}
	}
}

