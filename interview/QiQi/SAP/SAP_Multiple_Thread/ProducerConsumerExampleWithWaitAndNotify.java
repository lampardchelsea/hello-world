package test9;

import java.util.ArrayList;
import java.util.List;

/**
	3.1. What happens when notify() is called and no thread is waiting?
	In general practice, this will not be the case in most scenarios if these 
	methods are used correctly. Though if the notify() method is called when 
	no other thread is waiting, notify() simply returns and the notification is lost.
	
	Since the wait-and-notify mechanism does not know the condition about which 
	it is sending notification, it assumes that a notification goes unheard if 
	no thread is waiting. A thread that later executes the wait() method has to 
	wait for another notification to occur.
	
	3.2. Can there be a race condition during the period that the wait() method 
	releases OR re-acquires the lock?
	The wait() method is tightly integrated with the lock mechanism. The object 
	lock is not actually freed until the waiting thread is already in a state in 
	which it can receive notifications. It means only when thread state is changed 
	such that it is able to receive notifications, lock is held. The system prevents 
	any race conditions from occurring in this mechanism.
	
	Similarly, system ensures that lock should be held by object completely before 
	moving the thread out of waiting state.
	
	3.3. If a thread receives a notification, is it guaranteed that the condition 
	is set correctly?
	Simply, no. Prior to calling the wait() method, a thread should always test the 
	condition while holding the synchronization lock. Upon returning from the wait() 
	method, the thread should always re-test the condition to determine if it should 
	wait again. This is because another thread can also test the condition and 
	determine that a wait is not necessary — processing the valid data that was set 
	by the notification thread.
	
	This is a common case when multiple threads are involved in the notifications. 
	More particularly, the threads that are processing the data can be thought of 
	as consumers; they consume the data produced by other threads. There is no 
	guarantee that when a consumer receives a notification that it has not been 
	processed by another consumer.
	
	As such, when a consumer wakes up, it cannot assume that the state it was waiting 
	for is still valid. It may have been valid in the past, but the state may have 
	been changed after the notify() method was called and before the consumer thread 
	woke up. Waiting threads must provide the option to check the state and to return 
	back to a waiting state in case the notification has already been handled. This 
	is why we always put calls to the wait() method in a loop.
	
	3.4. What happens when more than one thread is waiting for notification? Which 
	threads actually get the notification when the notify() method is called?
	It depends on many factors.Java specification doesn’t define which thread gets 
	notified. In runtime, which thread actually receives the notification varies 
	based on several factors, including the implementation of the Java virtual machine 
	and scheduling and timing issues during the execution of the program.
	
	There is no way to determine, even on a single processor platform, which of multiple 
	threads receives the notification.
	
	Just like the notify() method, the notifyAll() method does not allow us to decide 
	which thread gets the notification: they all get notified. When all the threads 
	receive the notification, it is possible to work out a mechanism for the threads 
	to choose among themselves which thread should continue and which thread(s) should 
	call the wait() method again.
	
	3.5. Does the notifyAll() method really wake up all the threads?
	Yes and no. All of the waiting threads wake up, but they still have to re-acquire 
	the object lock. So the threads do not run in parallel: they must each wait for 
	the object lock to be freed. Thus, only one thread can run at a time, and only after 
	the thread that called the notifyAll() method releases its lock.
	
	3.6. Why would you want to wake up all of the threads if only one is going to 
	execute at all?
	There are a few reasons. For example, there might be more than one condition to 
	wait for. Since we cannot control which thread gets the notification, it is entirely 
	possible that a notification wakes up a thread that is waiting for an entirely 
	different condition.
	
	By waking up all the threads, we can design the program so that the threads decide 
	among themselves which thread should execute next. Another option could be when 
	producers generate data that can satisfy more than one consumer. Since it may be 
	difficult to determine how many consumers can be satisfied with the notification, 
	an option is to notify them all, allowing the consumers to sort it out among themselves.
 */

public class ProducerConsumerExampleWithWaitAndNotify {
	public static void main(String[] args) {
		List<Integer> taskQueue = new ArrayList<Integer>();
		int MAX_CAPACITY = 5;
		Thread tProducer = new Thread(new Producer(taskQueue, MAX_CAPACITY), "Producer");
		Thread tConsumer = new Thread(new Consumer(taskQueue), "Consumer");
		tProducer.start();
		tConsumer.start();
	}
}
