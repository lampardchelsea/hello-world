/**
 * Analyze
 * The implementation class use array structure to implement a queue attribute FIFO and
 * cicularly insert at the end or delete element at the start of array.  
 * Use two pointer front(point to start index of array) and rear(point to end index of array) 
 * to build circular array, use additional variable "count" to record elemements number in 
 * circular array, which can help identify array is empty or full.
 * If front = rear, count = 0, circular array is empty.
 * If front = rear, count = array.length, circular array is full.
 * 
 * Note: 
 * 1. In below link is interface circular array must implement.
 * http://faculty.washington.edu/moishe/javademos/ch07%20Code/jss2/QueueADT.java
 * 
 * 2. Genertic symbol T explanation below, T is any non-primitive type. 
 * http://stackoverflow.com/questions/6008241/java-generics-e-and-t-what-is-the-difference
 * 
 * 3. How to define an interface, an abstract method within an interface is followed by a semicolon, 
 * but no braces (an abstract method does not contain an implementation).
 * https://docs.oracle.com/javase/tutorial/java/IandI/interfaceDef.html
 * 
*/
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
  
  
  
}



