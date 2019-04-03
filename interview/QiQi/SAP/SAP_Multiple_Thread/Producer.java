package test9;

import java.util.List;

public class Producer implements Runnable{
	private final List<Integer> taskQueue;
	private final int MAX_CAPACITY;
	
	public Producer(List<Integer> sharedQueue, int size) {
		this.taskQueue = sharedQueue;
		this.MAX_CAPACITY = size;
	}
	
	@Override
	public void run() {
		int counter = 0;
		while(true) {
			try {
				produce(counter++);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * wait() method syntax
		synchronized(lockObject) {
		    while(!condition) {
		        lockObject.wait();
		    }
		    //take the action here;
		}
	 * 
	 * notify() method syntax
		synchronized(lockObject) {
		    //establish_the_condition;
		    lockObject.notify();
		    //any additional code if needed
		}
	 * 
	 * Here “produce(counter++)” code has been written inside infinite loop 
	 * so that producer keeps producing elements at regular interval.
	 * 
	 * We have written the produce() method code following the general 
	 * guideline to write wait() method as mentioned in first section.
	 * 
	 * Once the wait() is over, producer add an element in taskQueue and 
	 * called notifyAll() method. Because the last-time wait() method was 
	 * called by consumer thread (that’s why producer is out of waiting state), 
	 * consumer gets the notification.
	 * 
	 * Consumer thread after getting notification, if ready to consume the 
	 * element as per written logic.
	 * 
	 * Note that both threads use sleep() methods as well for simulating time 
	 * delays in creating and consuming elements.
	 */
	private void produce(int i) throws InterruptedException {
		synchronized(taskQueue) {
			while(taskQueue.size() == MAX_CAPACITY) {
				System.out.println("Queue is full " + Thread.currentThread().getName() 
						+ " is waiting, size: " + taskQueue.size());
				taskQueue.wait();
			}
			Thread.sleep(1000);
			taskQueue.add(i);
			System.out.println("Produced: " + i);
			taskQueue.notifyAll();
		}
	}

}
