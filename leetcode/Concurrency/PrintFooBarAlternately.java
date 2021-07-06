/**
Refer to
https://leetcode.com/problems/print-foobar-alternately/
Suppose you are given the following code:

class FooBar {
  public void foo() {
    for (int i = 0; i < n; i++) {
      print("foo");
    }
  }

  public void bar() {
    for (int i = 0; i < n; i++) {
      print("bar");
    }
  }
}
The same instance of FooBar will be passed to two different threads. Thread A will call foo() while thread B will call bar(). 
Modify the given program to output "foobar" n times.

Example 1:
Input: n = 1
Output: "foobar"
Explanation: There are two threads being fired asynchronously. One of them calls foo(), while the other calls bar(). "foobar" is being output 1 time.

Example 2:
Input: n = 2
Output: "foobarfoobar"
Explanation: "foobar" is being output 2 times.
*/

// Solution 1: Semaphore(0) and Semaphore(1)
// Refer to
// https://leetcode.com/problems/print-foobar-alternately/discuss/333542/Java-Semaphore-solution/827572
/**
class FooBar {
    private int n;
    Semaphore s1 = new Semaphore(0); // s1 does not have a permit
    Semaphore s2 = new Semaphore(1); // s2 has one permit

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            s2.acquire(); // there is one permit so it does not have to wait
        	// printFoo.run() outputs "foo". Do not change or remove this line.
        	printFoo.run();
            s1.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            s1.acquire(); // there is no permit so it will wait for s1.release(); add one permit
            // printBar.run() outputs "bar". Do not change or remove this line.
        	printBar.run();
            s2.release();
        }
    }
}
*/

// Difference between Semaphore initialized with 1 and 0
// https://stackoverflow.com/questions/25563640/difference-between-semaphore-initialized-with-1-and-0
/**
The argument to the Semaphore instance is the number of "permits" that are available. It can be any integer, not just 0 or 1.

For semZero all acquire() calls will block and tryAcquire() calls will return false, until you do a release()

For semOne the first acquire() calls will succeed and the rest will block until the first one releases.

The class is well documented here.

Parameters: permits - the initial number of permits available. This value may be negative, in which case releases 
must occur before any acquires will be granted.
*/

class FooBar {
    private int n;
    Semaphore semZero = new Semaphore(0);
    Semaphore semOne = new Semaphore(1);
    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            semOne.acquire();
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            semZero.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            semZero.acquire();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            semOne.release();
        }
    }
}

// Solution 2: Synchronized wait() and notify() solution
// Refer to
// https://leetcode.com/problems/print-foobar-alternately/discuss/338725/Fast-Java-solution-with-good-explanation.
class FooBar {
    private int n;
    // Set value to true so we can assure that foo() will be called first
    private boolean printfoo = true;
    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized(this) {
                // If the first thread is here, that means it's already acquired
                // this object's lock, so the second thread (or any other) gets
                // blocked until the lock is released.
                while(!printfoo) {
                    // If wait() is called, the first thread releases the lock
                    // (VERY IMPORTANT) and waits until the other thread calls
                    // notifyAll() inside the bar() method. That guarantees our
                    // code won't deadlock.
                    wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                // Here we set printFoo to false in order to assure that if the
                // thread scheduler chooses this thread again, it will stop on the
                // while loop and wait for the second one.
                printfoo = false;
                // When notifyAll() is called, the second thread will get out of
                // the while loop inside bar() and continue its job (to print "bar")
                notifyAll();
            }
        	
        }
    }

    // The whole process is the same with the bar method
    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            synchronized(this) {
                while(printfoo) {
                    wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                printfoo = true;
                // Wake up the first thread
                notifyAll();
            }
        }
        // lock is released
    }
}

