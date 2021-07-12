/**
Refer to
https://www.daimajiaoliu.com/daima/476295287100400
Implement a thread safe bounded blocking queue that has the following methods:

BoundedBlockingQueue(int capacity) The constructor initializes the queue with a maximum capacity.

void enqueue(int element) Adds an element to the front of the queue. 
If the queue is full, the calling thread is blocked until the queue is no longer full.

int dequeue() Returns the element at the rear of the queue and removes it. 
If the queue is empty, the calling thread is blocked until the queue is no longer empty.

int size() Returns the number of elements currently in the queue.

Your implementation will be tested using multiple threads at the same time. 
Each thread will either be a producer thread that only makes calls to the enqueue method or a consumer thread that 
only makes calls to the dequeue method. The size method will be called after every test case.

Please do not use built-in implementations of bounded blocking queue as this will not be accepted in an interview.

Example 1:
Input:
1
1
["BoundedBlockingQueue","enqueue","dequeue","dequeue","enqueue","enqueue","enqueue","enqueue","dequeue"]
[[2],[1],[],[],[0],[2],[3],[4],[]]

Output:
[1,0,2,2]

Explanation:
Number of producer threads = 1
Number of consumer threads = 1

BoundedBlockingQueue queue = new BoundedBlockingQueue(2);   // initialize the queue with capacity = 2.

queue.enqueue(1);   // The producer thread enqueues 1 to the queue.
queue.dequeue();    // The consumer thread calls dequeue and returns 1 from the queue.
queue.dequeue();    // Since the queue is empty, the consumer thread is blocked.
queue.enqueue(0);   // The producer thread enqueues 0 to the queue. The consumer thread is unblocked and returns 0 from the queue.
queue.enqueue(2);   // The producer thread enqueues 2 to the queue.
queue.enqueue(3);   // The producer thread enqueues 3 to the queue.
queue.enqueue(4);   // The producer thread is blocked because the queue's capacity (2) is reached.
queue.dequeue();    // The consumer thread returns 2 from the queue. The producer thread is unblocked and enqueues 4 to the queue.
queue.size();       // 2 elements remaining in the queue. size() is always called at the end of each test case.

Example 2:
Input:
3
4
["BoundedBlockingQueue","enqueue","enqueue","enqueue","dequeue","dequeue","dequeue","enqueue"]
[[3],[1],[0],[2],[],[],[],[3]]

Output:
[1,0,2,1]

Explanation:
Number of producer threads = 3
Number of consumer threads = 4

BoundedBlockingQueue queue = new BoundedBlockingQueue(3);   // initialize the queue with capacity = 3.

queue.enqueue(1);   // Producer thread P1 enqueues 1 to the queue.
queue.enqueue(0);   // Producer thread P2 enqueues 0 to the queue.
queue.enqueue(2);   // Producer thread P3 enqueues 2 to the queue.
queue.dequeue();    // Consumer thread C1 calls dequeue.
queue.dequeue();    // Consumer thread C2 calls dequeue.
queue.dequeue();    // Consumer thread C3 calls dequeue.
queue.enqueue(3);   // One of the producer threads enqueues 3 to the queue.
queue.size();       // 1 element remaining in the queue.

Since the number of threads for producer/consumer is greater than 1, we do not know how the threads will be scheduled in the 
operating system, even though the input seems to imply the ordering. Therefore, any of the output [1,0,2] or [1,2,0] or [0,1,2] 
or [0,2,1] or [2,0,1] or [2,1,0] will be accepted.
*/

// Why we have to separately create an object (similar to mutex) and use synchronized key word on it rather than synchronized 
// on whole class object ('this' as keyword) ?
// e.g 1114. Print in Order / 1115. Print FooBar Alternately / 1116. Print Zero Even Odd / 1117. Building H2O are all
// directly synchronized on whole class object ('this' as keyword), but not for this problem ?
// Because we don't want to block the size() method, if using either of below 2 ways to synchronized, it will block on whole
// class object, which means block 'this' object, all related methods will be blocked, so you will have no way to run size()
// method when enqueue() or dequeue() running.

// Refer to
// Synchronized block will lock the whole object or the method alone?
// https://stackoverflow.com/questions/44963842/synchronized-block-will-lock-the-whole-object-or-the-method-alone
/**
Q:
I have multiple methods in a class and most of the methods are having critical sections(shared data). So I made those methods as synchronized. 
Say thread t1 is running one of the synchronized block . At the same time thread t2 can access the other methods critical section ?

class Sample{

    synchronized public void method1(){

    }

    synchronized public void method2(){

    }

    synchronized public void method3(){

    }

    public void method4(){

    }

}

A: https://stackoverflow.com/a/44964544/6706875
synchronized is always locking on an object. In case of a synchronized method, the object is this. So basically these two methods do the same:

synchronized public void method1() {
  // do something
}
and

public void method1() {
  synchronized(this){
    // do something
  }
}

As long as one thread has the lock on the lock object, no other thread can lock this object. So in your example, 
the synchronized methods (one, two and three) can never be executed at the same time. method4 is not synchronized, 
so it can access the object anytime.

If you want a more fine-grained locking, because method1and method2 should be exclusive and method3and method4 you 
could use for example something like this:

class Sample{
  private final Object lock1 = new Object();
  private final Object lock2 = new Object();

  public void method1(){
    synchronized(lock1) {
      // do something
    }
  }
  public void method2(){
    synchronized(lock1) {
      // do something
    }
  }

  public void method3(){
    synchronized(lock2) {
      // do something
    }
  }
  public void method4(){
    synchronized(lock2) {
      // do something
    }
  }
}
You can then even use the synchonized(lock) method to just wrap the statements that need to be synchronized, not the whole method:

public void method() {
  // some code
  synchronized(lock) {
    // code that must be synchronized
  }
  // some other code
}

With this approach you can keep the lock duration to a minimum.
*/

// Solution with already implemented blocking queue in Java
// Refer to
// https://blog.csdn.net/zhangpeterx/article/details/104250099
/**
这道题目是LeetCode并发系列题目，题目的意思很简单，设计一个有界阻塞队列，难点在于要求是线程安全的。
Java标准库中线程安全的队列(queue)有几个，其中ArrayBlockingQueue适合这道题目
*/
class BoundedBlockingQueue {
    private ArrayBlockingQueue queue = null;

    public BoundedBlockingQueue(int capacity) {
        queue = new ArrayBlockingQueue(capacity);
    }

    public void enqueue(int element) throws InterruptedException {
        queue.put(element);
    }

    public int dequeue() throws InterruptedException {
        return (int) queue.take();
    }

    public int size() {
        return queue.size();
    }
}

// Solution 1: Synchronized with wait() and notify() NOT on whole class object but separately created mutex object
// Refer to
// https://www.daimajiaoliu.com/daima/476295287100400
// https://blog.csdn.net/zhangpeterx/article/details/104250099
// 最容易想到的方法是使用锁，确保同时只有一个线程访问数据，可以使用synchronized关键字。
class BoundedBlockingQueue {
    private final Queue queue;
    private final Object mutex = new Object();
    private int capacity;
    
    public BoundedBlockingQueue(int capacity) {
        queue = new LinkedList<Integer>();
        this.capacity = capacity;
    }
    
    public void enqueue(int element) throws InterruptedException {
        synchronized(mutex) {
            while(size() >= capacity) {
                mutex.wait();
            }
            queue.add(element);
            mutex.notifyAll();
        }
    }
    
    public int dequeue() throws InterruptedException {
        int result;
        synchronized(mutex) {
            while(size() <= 0) {
                mutex.wait();
            }
            result = (int) mutex.remove();
            mutex.notifyAll();
        }
        return result;
    }

    public int size() {
        return queue.size();
    }
}

// Solution 2: Synchronized with wait() and notify() NOT on whole class object but separately lock on queue
// Refer to
// https://leetcode.jp/leetcode-1188-design-bounded-blocking-queue-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
/**
解题思路分析：
这是一道多线程题目，我首先想到了使用Semaphore来做线程管理，后来又仔细读了题目之后发现，题目要求不能使用现有的方法来实现，
也就是说我们需要自己来实现类似Semaphore的线程管理。

看下解题时需要考虑到的核心思想。队列Queue是有数量上限要求的，在达到数量上限之后，我们无法再向Queue中添加元素，所有待添加
元素应该等待另一个线程删除Queue中元素之后才能再次添加。反过来，当Queue中元素个数为0时，待删除的操作也需要等待，直到其他线
程有元素添加进来。

我们可以实时监控Queue的元素个数，在执行插入操作时，如果Queue中元素个数已经达到上限，我们需要阻塞当前线程，直到队列中元素个
数小于上限。同理，执行删除操作时，我们也要实时监控元素个数是否为0，如果队列为空，我们需要阻塞当前线程。
*/
class BoundedBlockingQueue {
    Queue<Integer> q = new LinkedList<>(); // 队列Queue
    public BoundedBlockingQueue(int capacity) {
        size = capacity; // 队列上限
    }

    public void enqueue(int element) throws InterruptedException {
        synchronized(q){ // 保证Queue不会同时被多个线程操作
            // 如果已经达到存储上限，阻塞当前线程
            while(q.size()==size){
                q.wait();
            }
            // 将元素添加至队列
            q.offer(element);
            // 通知所有线程队列已经被更新
            q.notifyAll();
        }
    }

    public int dequeue() throws InterruptedException {
        synchronized(q){ // 保证Queue不会同时被多个线程操作
            // 如果队列为空，阻塞当前线程
            while(q.size()==0){
                q.wait();
            }
            // 删除队列一个元素
            int num = q.poll();
            // 通知所有线程队列已经被更新
            q.notifyAll();
            // 返回删除元素
            return num;
        }
    }

    public int size() {
        return q.size();
    }
}

// Solution 3: Semaphore(capacity) with Semaphore(0)
// Refer to
// https://code.dennyzhang.com/design-bounded-blocking-queue

