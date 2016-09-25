/**
 * Analyze
 * The implementation class use array to implement a queue structur which attribute as FIFO and
 * cicularly insert at the end or delete element at the start of array.
 * Without using variable "count" to identify whether circular array is empty or full
 * 
 * Trouble: The condition front pointer == rear pointer can indicate: an empty buffer or a full buffer
 * Traditionally, the following trick is used to to break the ambiguity: We assume that the circular 
 * buffer is full when: There is one empty slot left in the circular buffer.
 * In other words, we use the following test to check if the circular buffer is full:
 *    read pointer == ( write pointer + 1 ) % (buf.length)
 * And this will cause real full size of circular array is 1 less than DEFAULT_CAPACITY.
 * e.g 
 * If set DEFAULT_CAPACITY = 10, when throw out FullExceptionCollection, the size is 10 - 1 = 9.
 * In code example below, the out put is:
 * Full Collection Exception happen: circular array queue is full
 * Circular array size is: 9
 * All elements in circular array queue are: AS_NULL B C D E F G H I J 
 * 
 * Note: There are two case to calculate size, as it is circular array, front can be either larger
 * or smaller than rear.
 * 
 * Best example
 * http://www.mathcs.emory.edu/~cheung/Courses/171/Syllabus/8-List/array-queue2.html
*/
package CircularArrayQueue;

public interface QueueADT<T> {
	// add an element to the rear of the queue
	public void enqueue(T element) throws FullCollectionException;
  
	// remove and return an element at the front of the queue
	public T dequeue() throws EmptyCollectionException;
  
	// returns without removing the element at the front of the queue
	public T first() throws EmptyCollectionException;
  
	// returns true if the queue is empty
	public boolean isEmpty();
	
	// returns true if the queue is full
	public boolean isFull();
  
	// returns number of elements in the queue
	public int size();
  
	// returns a string representation of queue
	public String toString();
}


package CircularArrayQueue;

public class CircularArrayQueue<T> implements QueueADT<T> {
	private final int DEFAULT_CAPACITY = 10;
	private int front;
	private int rear;
	private T[] queue;
	
	@SuppressWarnings("unchecked")
	public CircularArrayQueue() {
		queue = (T[])new Object[DEFAULT_CAPACITY];
		front = 0;
		rear = 0;
	}
	
	@Override
	public void enqueue(T element) throws FullCollectionException {
		if(isFull()) {
			throw new FullCollectionException("circular array queue is full");
		}
		
		queue[rear] = element;
		rear = (rear + 1) % DEFAULT_CAPACITY;
	}
	
	@Override
	public T dequeue() throws EmptyCollectionException {
		if(isEmpty()) {
			throw new EmptyCollectionException("circular array queue is empty");
		}
		
		T element = queue[front];
		queue[front] = null;
		front = (front + 1) % DEFAULT_CAPACITY;
		
		return element;
	}
	
	@Override
	public boolean isEmpty() {
		return (front == rear);
	}
	
	@Override
	public boolean isFull() {
		return (front == (rear + 1) % DEFAULT_CAPACITY);
	}
	
	@Override
	public T first() {
		return queue[front];
	}
	
	@Override
	public String toString() {
		String result = "";
		for(int scan = 0; scan < DEFAULT_CAPACITY; scan++) {
			String value = queue[scan] == null ? "AS_NULL" : queue[scan].toString();
			result += (value + " ");
		}
		return result;
	}
	
	@Override
	public int size() {
		// To calculate size, we have two cases, one is rear bigger than front,
		// another is because it is circular, then rear may smaller than front.
		int size;
		if(rear > front) {
			size = rear - front;
		} else {
			size = rear + DEFAULT_CAPACITY - front;
		}
		
		return size;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		@SuppressWarnings("rawtypes")
		CircularArrayQueue queue = new CircularArrayQueue();
		
		try {
			queue.enqueue("A");
			queue.enqueue("B");
			queue.enqueue("C");
			queue.enqueue("D");
			queue.enqueue("E");
			queue.dequeue();
			//queue.dequeue();
			//queue.dequeue();
			//queue.dequeue();
			queue.enqueue("F");
			queue.enqueue("G");
			queue.enqueue("H");
			queue.enqueue("I");
			queue.enqueue("J");
			queue.enqueue("K");
			queue.enqueue("L");
			queue.enqueue("M");
			queue.enqueue("N");
			queue.enqueue("O");
			//queue.enqueue("P");
		} catch (EmptyCollectionException e) {
			System.out.println("Empty Collection Exception happen: " + e.getMessage());
		} catch (FullCollectionException e1) {
			System.out.println("Full Collection Exception happen: " + e1.getMessage());
		}
		
		System.out.println("Circular array size is: " + queue.size());
		System.out.println("All elements in circular array queue are: " + queue);
	}
}


package CircularArrayQueue;

public class EmptyCollectionException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmptyCollectionException() {}
	
	public EmptyCollectionException(String errorMsg) {
		super(errorMsg);
	}
}


package CircularArrayQueue;

public class FullCollectionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public FullCollectionException() {}
	
	public FullCollectionException(String msg) {
		super(msg);
	}
}
