/**
 Refer to
 https://leetcode.com/problems/print-in-order/
 Suppose we have a class:

public class Foo {
  public void first() { print("first"); }
  public void second() { print("second"); }
  public void third() { print("third"); }
}
The same instance of Foo will be passed to three different threads. 
Thread A will call first(), thread B will call second(), and thread C 
will call third(). Design a mechanism and modify the program to ensure 
that second() is executed after first(), and third() is executed after second().

Example 1:
Input: [1,2,3]
Output: "firstsecondthird"
Explanation: There are three threads being fired asynchronously. The input [1,2,3] 
means thread A calls first(), thread B calls second(), and thread C calls third(). 
"firstsecondthird" is the correct output.

Example 2:
Input: [1,3,2]
Output: "firstsecondthird"
Explanation: The input [1,3,2] means thread A calls first(), thread B calls third(), 
and thread C calls second(). "firstsecondthird" is the correct output.

Note:
We do not know how the threads will be scheduled in the operating system, even though 
the numbers in the input seems to imply the ordering. The input format you see is 
mainly to ensure our tests' comprehensiveness.
*/

// Solution 1: Semaphore
// Refer to
// https://leetcode.com/problems/print-in-order/discuss/332890/Java-Basic-semaphore-solution-8ms-36MB
/**
 "Semaphore is a bowl of marbles" - Professor Stark
Semaphore is a bowl of marbles (or locks in this case). 
If you need a marble, and there are none, you wait. You wait until there is one marble and 
then you take it. If you release(), you will add one marble to the bowl (from thin air). 
If you release(100), you will add 100 marbles to the bowl (from thin air).
The thread calling third() will wait until the end of second() when it releases a '3' marble. 
The second() will wait until the end of first() when it releases a '2' marble. Since first() 
never acquires anything, it will never wait. There is a forced wait ordering.
With semaphores, you can start out with 1 marble or 0 marbles or 100 marbles.
A thread can take marbles (up until it's empty) or put many marbles (out of thin air) at a time.
elegant, run2.acquire() makes second sleep while run2 hasn't been released by first , 
run3.acquire() makes third sleep while run3 has not been released by second.
*/
import java.util.concurrent.*;

class Foo {
    Semaphore run2, run3;    
    public Foo() {
        run2 = new Semaphore(0);
        run3 = new Semaphore(0);
    }

    public void first(Runnable printFirst) throws InterruptedException {        
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        run2.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        run2.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        run3.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        run3.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
