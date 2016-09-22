/**
 * Analyze
 * The implementation class use array structure to implement a queue attribute FIFO and
 * cicularly insert at the end or delete element at the start of array.  
 * Use two pointer front(point to start index of current array, operate with dequeue method) 
 * and rear(point to end index of current array, operate with enqueue method) 
 * to build circular array, use additional variable "count" to record elemements number in 
 * circular array, which can help identify array is empty or full.
 * If front = rear, count = 0, circular array is empty.
 * If front = rear, count = array.length, circular array is full.
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
*/

/**
 * There are multiple issues in below code:
 * 1. public CircularArrayQueue<T>() {} is wrong format
 * 2. queue = new T[DEFAULT_CAPACITY]; is wrong format
 * 3. dequeue() and first() method lake of handling of exception, and this chagne also impact QueueADT interface
 *    which declare the protype method
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
}


public class EmptyCollectionException extends Exception {
  public EmptyCollectionException() {}
  
  public EmptyCollectionException(String errorMsg) {
    super(errorMsg);
  }
}
*/
