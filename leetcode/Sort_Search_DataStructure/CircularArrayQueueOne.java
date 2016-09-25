/**
 * Analyze
 * The implementation class use array to implement a queue structur which attribute as FIFO and
 * cicularly insert at the end or delete element at the start of array.  
 * Use two pointer front(point to start index of current array, operate with dequeue method) 
 * and rear(point to end index of current array, operate with enqueue method) 
 * to build circular array.
 * Use additional variable "count" to record elemements number in circular array, which 
 * can help identify array is empty or full.
 * If front = rear, count = 0, circular array is empty.
 * If front = rear, count = array.length, circular array is full.
 * 
 * Best example
 * http://www.javamadesoeasy.com/2015/01/circular-queue.html
 * 
 * Note: 
 * 1. In below links are interface circular array must implement and its implementation.
 * http://faculty.washington.edu/moishe/javademos/ch07%20Code/jss2/QueueADT.java
 * http://faculty.washington.edu/moishe/javademos/ch07%20Code/jss2/CircularArrayQueue.java
 * 
 * 2. Genertic symbol T explanation below, T is any non-primitive type. 
 * http://stackoverflow.com/questions/6008241/java-generics-e-and-t-what-is-the-difference
 * 
 * 3. How to define an interface, an abstract method within an interface is followed by a semicolon, 
 * but no braces (an abstract method does not contain an implementation).
 * https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html
 * 
 * 4. How to create a generic type of array
 * http://stackoverflow.com/questions/529085/how-to-create-a-generic-array-in-java
 * 
 * 5. How to create a generic constructor for a generic class in java?
 * http://stackoverflow.com/questions/8680442/how-to-create-a-generic-constructor-for-a-generic-class-in-java
 * 
 * 6. How to create a generic constructor
 * http://www.java2s.com/Tutorials/Java/Java_Object_Oriented_Design/0370__Java_Generic_Methods_Constructors.htm
 * 
 * 7. What is @SuppressWarnings annotation in Java ?
 * http://javarevisited.blogspot.com/2015/09/what-is-suppresswarnings-annotation-in-java-unchecked-raw-serial.html
 * http://stackoverflow.com/questions/197986/what-causes-javac-to-issue-the-uses-unchecked-or-unsafe-operations-warning?rq=1
 * 
*/

/**
 * There are multiple issues in below code:
 * 1. public CircularArrayQueue<T>() {} is wrong format
 * 2. queue = new T[DEFAULT_CAPACITY]; is wrong format
 * 3. dequeue() and first() method lake of handling of exception, and this chagne also impact QueueADT interface
 *    which declare the protype method
 * 4. enqueue() method lake of isFull() method check, otherwise it will override already existing elements, and 
 *    variable "count" can increase with no limitation, isFull() and isEmpty() methods which based on "count"
 *    cannot work properly.
 *    e.g Insert A,B,C,D to DEFAULT_CAPACITY = 3 circular array, the element A previously enqueued will override 
 *    as D, this case should avoid (No already existing element should be replaced).
 * 5. toString() method need to handle null value, as dequeue will reset array index value to null or DEFAULT_CAPACITY
 *    larger than current element numbers in array, remained index are still null.
 * 
public interface QueueADT<T> {
  // add an element to the rear of the queue
  public void enqueue(T element);
  
  // remove and return an element at the front of the queue
  public T dequeue();
  
  // returns without removing the element at the front of the queue
  public T first();
  
  // returns true if the queue is empty
  public boolean isEmpty();
  
  // returns true if the queue is full
	public boolean isFull();
  
  // returns number of elements in the queue
  public int size();
  
  // returns a string representation of queue
  public String toString();
}


public class CircularArrayQueue<T> implements QueueADT<T> {
  private final int DEFAULT_CAPACITY = 10;
  private int front;
  private int rear;
  private int count;
  private T[] queue;
  
  public CircularArrayQueue<T>() {
    queue = new T[DEFAULT_CAPACITY];
    front = 0;
    rear = 0;
    count = 0;
  }
  
  @Override
  public void enqueue(T element) {
    // The order of assign element to queue[rear] and increase
    // rear is first assign then increase
    queue[rear] = element;
    rear = (rear + 1) % DEFAULT_CAPACITY;
    count++;
    
    if(isFull()) {
      System.out.println("Circular array is full, element at rear index is " + element.toString());
	  } else if(count > DEFAULT_CAPACITY) {
	    System.out.println("Circular array is full, replace current rear index " + rear + 
	    			" original element " + originalValue.toString() + " with " + element.toString());
	  }
  }
  
  @Override
  public T dequeue() {
    // Enqueue method no need to consider empty queue issue, just override
    // privious value, but dequeue method requires
    if(isEmpty()) {
      throw new EmptyCollectionException("circular array queue is empty");
    }
    
    // The order of assign queue[front] to element and increase
    // front is first assign then increase
    element = queue[front];
    queue[front] = null;
    front = (front + 1) % DEFAULT_CAPACITY;
    count--;
    
    return element;
  }
  
  @Override
  public T first() {
    if(isEmpty()) {
      throw new EmptyCollectionException("circular array queue is empty");
    }
    return queue[front];
  }
  
  @Override
  public boolean isEmpty() {
    return (count == 0);
  }
  
  @Override
  public boolean isFull() {
    return (count == DEFAULT_CAPACITY);
  }
  
  @Override
  public int size() {
    return count;
  }
  
  @Override
  public String toString() {
    String result = "";
    for(int scan = 0; scan < count; scan++) {
      result += (queue[scan].toString() + " ");
    }
    
    return result;
  }
  
  public static void main(String[] args) {
		@SuppressWarnings("rawtypes")
		CircularArrayQueue queue = new CircularArrayQueue();
		queue.enqueue("A");
		queue.enqueue("B");
		queue.enqueue("C");
		queue.enqueue("D");
		//queue.enqueue("E");
		try {
			queue.dequeue();
			queue.dequeue();
			queue.dequeue();
			//queue.dequeue();
		} catch (EmptyCollectionException e) {
			System.out.println("Empty Collection Exception happen: " + e.getMessage());
		}
		
		System.out.println("All elements in circular array queue are: " + queue);
	}
}


public class EmptyCollectionException extends Exception {
  public EmptyCollectionException() {}
  
  public EmptyCollectionException(String errorMsg) {
    super(errorMsg);
  }
}
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

@SuppressWarnings("unchecked")
public class CircularArrayQueue<T> implements QueueADT<T> {
	private final int DEFAULT_CAPACITY = 10;
	private int front;
	private int rear;
	private int count;
	private T[] queue;
	
	// If define an generic constructor, the wrong way is
	// e.g public CircularArrayQueue<T>() {}
	public CircularArrayQueue() {
		// To create a generic type of array, the wrong way is
		// e.g queue = new T[DEFAULT_CAPACITY];
		queue = (T[])new Object[DEFAULT_CAPACITY];
		front = 0;
		rear = 0;
		count = 0;
	}
	 
	// enqueue() method need handle(try/catch or throw out exception)  
	@Override
	public void enqueue(T element) throws FullCollectionException {
		// Enqueue method need to consider circular array already full 
		// issue, otherwise will override previously enqueued element.
		// e.g Insert A,B,C,D to DEFAULT_CAPACITY = 3 circular array,
		// the element A previously enqueued will override as D, this
		// case should avoid (No already existing element should be replaced).
		if(isFull()) {
	    	System.out.println("Circular array is full, no more element to insert");
	    	throw new FullCollectionException("circular array queue is full");
		}
		
		// The order of assign element to queue[rear] and increase
	    // rear is first assign then increase
	    queue[rear] = element;
	    
	    System.out.println("Insert element at current rear index " + rear + " as " + element);
	    
	    rear = (rear + 1) % DEFAULT_CAPACITY;
	    count++;
	}
	
	// dequeue() method need handle(try/catch or throw out exception)
	@Override
	public T dequeue() throws EmptyCollectionException {
		// Enqueue method no need to consider empty queue issue, just override
	    // privious value, but dequeue method requires
	    if(isEmpty()) {
	    	System.out.println("Circular array is empty, no element to remove");
	    	throw new EmptyCollectionException("circular array queue is empty");
	    }
	    
	    // The order of assign queue[front] to element and increase
	    // front is first assign then increase
	    T element = queue[front];
	    queue[front] = null;
	    
	    System.out.println("Remove element at current front index " + front + " as " + element);
	    
	    front = (front + 1) % DEFAULT_CAPACITY;
	    count--;
	    
	    return element;
	}
	
	// first() method need handle(try/catch or throw out exception)
	@Override
	public T first() throws EmptyCollectionException {
	    if(isEmpty()) {
	      throw new EmptyCollectionException("circular array queue is empty");
	    }
	    return queue[front];
	}
	  
	@Override
	public boolean isEmpty() {
	    return (count == 0);
	}
	
	@Override
	public boolean isFull() {
	    // The compare is between count and DEFAULT_CAPACITY, not (DEFAULT_CAPACITY - 1),
	    // as count is increase as 1 after each enqueue.
		return (count == DEFAULT_CAPACITY);
	}
	
	@Override
	public int size() {
	    return count;
	}
	  
	@Override
	public String toString() {
	    String result = "";
	    for(int scan = 0; scan < DEFAULT_CAPACITY; scan++) {
		    // Add trinary logic here to solve case as queue is not empty, but element already
		    // set to null as locate on front index.
	    	String value = queue[scan] == null ? "AS_NULL" : queue[scan].toString();
	    	result += (value + " ");
	    }
	    
	    return result;
	}
	
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
			queue.dequeue();
			queue.dequeue();
			queue.dequeue();
			queue.enqueue("F");
			queue.enqueue("G");
			queue.enqueue("H");
			queue.enqueue("I");
			queue.enqueue("J");
			queue.enqueue("K");
			queue.enqueue("L");
			queue.enqueue("M");
			//queue.enqueue("N");
			//queue.enqueue("O");
		} catch (EmptyCollectionException e) {
			System.out.println("Empty Collection Exception happen: " + e.getMessage());
		} catch (FullCollectionException e1) {
			System.out.println("Full Collection Exception happen: " + e1.getMessage());
		}
		
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

