package test9;

import java.util.List;

public class Consumer implements Runnable {
	private final List<Integer> taskQueue;
	public Consumer(List<Integer> sharedQueue) {
		this.taskQueue = sharedQueue;
	} 
	
	@Override
	public void run() {
		while(true) {
			try {
				consume();
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
	 * Here “consume()” code has been written inside infinite loop so that 
	 * consumer keeps consuming elements whenever it finds something in taskQueue.
	 * 
	 * Once the wait() is over, consumer removes an element in taskQueue and 
	 * called notifyAll() method. Because the last-time wait() method was called 
	 * by producer thread (that’s why producer is in waiting state), producer 
	 * gets the notification.
	 * 
	 * Producer thread after getting notification, if ready to produce the 
	 * element as per written logic.
	 */
	private void consume() throws InterruptedException {
		synchronized(taskQueue) {
			while(taskQueue.isEmpty()) {
				System.out.println("Queue is empty " + Thread.currentThread().getName() 
						+ " is waiting , size: " + taskQueue.size());
				taskQueue.wait();
			}
			Thread.sleep(1000);
			int i = (Integer)taskQueue.remove(0);
			System.out.println("Consumed: " + i);
			taskQueue.notifyAll();
		}
	}

}
