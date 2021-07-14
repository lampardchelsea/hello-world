/**
Refer to
https://leetcode.com/problems/fizz-buzz-multithreaded/
You have the four functions:

printFizz that prints the word "Fizz" to the console,
printBuzz that prints the word "Buzz" to the console,
printFizzBuzz that prints the word "FizzBuzz" to the console, and
printNumber that prints a given integer to the console.
You are given an instance of the class FizzBuzz that has four functions: fizz, buzz, fizzbuzz and number. 
The same instance of FizzBuzz will be passed to four different threads:

Thread A: calls fizz() that should output the word "Fizz".
Thread B: calls buzz() that should output the word "Buzz".
Thread C: calls fizzbuzz() that should output the word "FizzBuzz".
Thread D: calls number() that should only output the integers.
Modify the given class to output the series [1, 2, "Fizz", 4, "Buzz", ...] where the ith token (1-indexed) of the series is:

"FizzBuzz" if i is divisible by 3 and 5,
"Fizz" if i is divisible by 3 and not 5,
"Buzz" if i is divisible by 5 and not 3, or
i if i is not divisible by 3 or 5.

Implement the FizzBuzz class:

FizzBuzz(int n) Initializes the object with the number n that represents the length of the sequence that should be printed.
void fizz(printFizz) Calls printFizz to output "Fizz".
void buzz(printBuzz) Calls printBuzz to output "Buzz".
void fizzbuzz(printFizzBuzz) Calls printFizzBuzz to output "FizzBuzz".
void number(printNumber) Calls printnumber to output the numbers.

Example 1:
Input: n = 15
Output: [1,2,"fizz",4,"buzz","fizz",7,8,"fizz","buzz",11,"fizz",13,14,"fizzbuzz"]

Example 2:
Input: n = 5
Output: [1,2,"fizz",4,"buzz"]

Constraints:
1 <= n <= 50
*/

// Solution 1: Synchronized with wait() and notify()
// Style 1: 
// Refer to
// https://leetcode.com/problems/fizz-buzz-multithreaded/discuss/385395/Java-using-synchronized-with-short-explanation.
/**
So I just keep a shared currentNumber showing where we're up to. Each thread has to then check whether or not it should take its turn. 
If it's not the current thread's turn, it calls wait(), thus suspending and giving up the monitor. After each increment of currentNumber, 
we need to call notifyAll() so that the other threads are woken and can re-check the condition. They will all be given a chance to run

The implicit locks, (aka monitors) provided by synchronized can be a bit confusing at first to understand, so I recommend reading up 
on them if you are confused. I personally found these sites useful:

https://docs.oracle.com/javase/tutorial/essential/concurrency/index.html
https://howtodoinjava.com/java/multi-threading/java-synchronized/

You might notice something slightly unusual in my wait code, i.e.

if (currentNumber % 3 != 0 || currentNumber % 5 == 0) {
     wait();
     continue;
}
When perhaps you would have expected the more traditional form of:

while (currentNumber % 3 != 0 || currentNumber % 5 == 0) {
     wait();
}

Seeing as we are always told that waiting conditions must be a loop, right? (Because sometimes it will have to wait more 
than once for its turn).

The reason I have done this though is because otherwise there'd have to be an additional check in the waiting loop for if 
the currentNumber value is still below n.

while (currentNumber % 3 != 0 || currentNumber % 5 == 0) {
     wait();
	 if (currentNumber > n) return;
}

While this does work, I felt it was probably the more convoluted option. But it's definitely an option, and I'd be interested 
to hear thoughts on this "design" decision. In effect, it is a wait in a loop, it's just that it utilises the outer loop's 
condition (by using continue).

I probably could pull some of the repetition into a seperate method. One challenge was that 3 of the methods took a Runnable, 
and one took an IntConsumer.

class FizzBuzz {
    
    private int n;
    private int currentNumber = 1;
    
    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while (currentNumber <= n) {
            if (currentNumber % 3 != 0 || currentNumber % 5 == 0) {
                wait();
                continue;
            }
            printFizz.run();
            currentNumber += 1;
            notifyAll();
        }
    }

    // printBuzz.run() outputs "buzz".
    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while (currentNumber <= n) {
            if (currentNumber % 5 != 0 || currentNumber % 3 == 0) {
                wait();
                continue;
            }
            printBuzz.run();
            currentNumber += 1;
            notifyAll();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (currentNumber <= n) {
            if (currentNumber % 15 != 0) {
                wait();
                continue;
            }
            printFizzBuzz.run();
            currentNumber += 1;
            notifyAll();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        while (currentNumber <= n) {
            if (currentNumber % 3 == 0 || currentNumber % 5 == 0) {
                wait();
                continue;
            }
            printNumber.accept(currentNumber);
            currentNumber += 1;
            notifyAll();
        }
    }

My question for Style 1:
@Hai_dee Good point on use continue; to implement same effect as if (currentNumber > n) return; , but still have one doubt, 
could you help me to understand why still need to an additional check as if (currentNumber > n) return;, because for my understanding,
the outside while loop while(currentNumber <= n) {...} already guarantee currentNumber <= n, where is the chance for currentNumber > n ?
Actually, if I tried to remove if (currentNumber > n) return;, it error out as print additional 16 when last input is 15.
*/

// Style 2:
// Refer to
// https://leetcode.com/problems/fizz-buzz-multithreaded/discuss/890801/Java-Solution-with-Locks
/**
class FizzBuzz {
    private int n;
    private int count;
    private Object lock;

    public FizzBuzz(int n) {
        this.n = n;
        this.count = 1;
        this.lock = new Object();
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while(count<=n) {
            synchronized (lock) {
                if (count<=n && count % 3 == 0 && count % 5 != 0) {
                    printFizz.run();
                    count++;
                }
            }

        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while(count<=n) {
            synchronized (lock) {
                if (count<=n && count % 5 == 0 && count % 3 != 0) {
                    printBuzz.run();
                    count++;
                }
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while(count<=n) {
            synchronized (lock) {
                if (count<=n && count % 5 == 0 && count % 3 == 0) {
                    printFizzBuzz.run();
                    count++;
                }
            }
        }

    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while(count<=n) {
            synchronized (lock) {
                if (count<=n && count % 3 != 0 && count % 5 != 0) {
                    printNumber.accept(count);
                    count++;
                }
            }
        }
    }
}

My question for Style 2:
@anjana9 after go through 4 methods, looks like in each method, inside while(counter <= n) {...} loop, there is one more 
same check on counter again as if(counter <= n && ...) {...}, I tried to remove counter <= n in each if condition, but 
error out as print additional 16 when Last executed input 15. Could you share some idea why we need that additional check 
on counter as if(counter <= n && ...) ? For my understanding, the outside while loop as while(counter <= n) {...} already 
guarantee counter <= n, right ?
*/

// Best style: Other 3 threads are consumer when 1 thread is a producer
// strictly follow pattern as:
// https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
// Refer to
// https://leetcode.com/problems/fizz-buzz-multithreaded/discuss/398076/Intuitive-Java-Solution-With-Explanation
/**
Idea
Use Guarded Blocks and the logic is similar to producer-consumer problem from
https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html

class FizzBuzz {
    private int n;
    private int counter = 1;
    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while(true){
            while(counter <= n && (counter % 3 != 0 || counter % 5 == 0)){
                wait();
            }
            if(counter > n) break;
            printFizz.run();
            ++counter;
            notifyAll();
        }
    }

    // printBuzz.run() outputs "buzz".
    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while(true){
            while(counter <= n && (counter % 5 != 0 || counter % 3 == 0)){
                wait();
            }
            if(counter > n) break;
            printBuzz.run();
            ++counter;
            notifyAll();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while(true){
            while(counter <= n && !(counter % 3 == 0 && counter % 5 == 0)){
                wait();
            }
            if(counter > n) break;
            printFizzBuzz.run();
            ++counter;
            notifyAll();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        while(true){
            while(counter <= n && (counter % 3 == 0 || counter % 5 == 0)){
                wait();
            }
            if(counter > n) break;
            printNumber.accept(counter);
            ++counter;
            notifyAll();
        }
    }
}

My question for best style:
@naveen_kothamasu after go through 4 methods, looks like in each method, after while(counter <= n...) { wait(); } section, 
there is one more same check on counter again as if(counter > n) break;, I tried to remove it and change outside while(true) 
loop into while(counter <= n), but error out as print additional 16 by Last executed input 15. Could you share some idea why 
we need that additional check on counter as if(counter > n) break; ? For my understanding, after changed outside while loop 
as while(counter <= n) {...} we already guarantee counter <= n, right ?
*/

// Q: Basically, we have one doubt on why we need to add additional check for if(counter > n) again ? The outside while(counter <= n)
// loop check on counter OR even the insider while(counter <= n && ...) loop check on counter NOT guarantee counter <= n ?
// A: This can be identified by below simulation client test
// Refer to
// https://stackoverflow.com/questions/62786471/writing-a-test-case-for-multi-threaded-fizzbuzz
/**
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);

        Runnable printFizz = () -> System.out.println("fizz");
        Runnable printBuzz = () -> System.out.println("buzz");
        Runnable printFizzBuzz = () -> System.out.println("fizzbuzz");
        IntConsumer printNumber = number -> System.out.println(number);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(printFizz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(printBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(printFizzBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
   }
*/

// Most important points:
// Also we find out the outside while(counter <= n) loop has no actual use, just while(true) is fine, but the insider 
// while(counter <= n && ...) loop is critical, and the additional if(counter > n) break is also critical to block
// print out additional element when current thread is waked up from wait() from other threads notifyAll(), there is
// a chance for counter > n since counter++ happen on other thread but deliver into current thread, which will skip
// outside while(counter <= n) or inside while(counter <= n && ...) loop when break out wait() method, this can be
// observed by tracking Eclipse debug with above demo main method: adding breakpoint at 4 methods inside while loop
// line, then observe the change by press F8 (jump between different thread) and press F6 when it process on current
// thread. OR we can manually observe the thread status jumping between 'stepping' and 'suspended', the debuging thread
// status titled with 'suspended' one means it under Eclipse's control now (just use mouth click to choose that thread 
// is fine) and able to press F6

// Note1: For understaing 'stepping' and 'suspended' refer to below:
// Refer to
// https://stackoverflow.com/questions/11474769/trying-to-understand-why-a-thread-could-be-blocked-in-eclipse
/**
Q: Trying to understand why a thread could be blocked in eclipse?
I am trying to get to the bottom of a threading problem in a web application. I am using Eclipse. 
The problem is when one thread is a certain stage in a very long winded piece of code, another thread can't go 
any further even though it should. It has the appearance of a blocked thread. Eclipse has marked it as "stepping". 
It makes no sense why it has stopped as there are no monitors, semaphores, sychnronization blocks anywhere near the code.

Can I use anything in eclipse to understand any reason why the thread looks like it is being blocked?

I can hit the pause button, which suspends the thread. When I click the resume button, it goes back to "stepping", 
but can't complete this simple line of code:

long beginTime = System.currentTimeMillis();

A: In Eclipse, marking a thread as "Stepping" means that you are stepping through the code, either with step-in or 
(more likely) step-over, and the thread is blocked somehow either doing some IO, waiting for a synchronization lock, 
calling wait(), etc.. Eclipse is not controlling the thread and is waiting for the thread to move past the block before 
it can continue with the "stepping". Once Eclipse has control back of the thread at the next line, your thread is changed 
to being "Suspended".

For example, while debugging the following test code:

// i put a break point here
System.out.println("Foo");
Object lock = new Object();
synchronized (lock) {
    // when I step over this line, my thread is labeled as "Stepping"
    lock.wait();
}
System.out.println("Boo");

Once I step over a method which blocks the thread, the thread gets labeled as "Stepping".
*/

// Note 2: Why should wait() always be called inside a loop ?
// Refer to
// https://stackoverflow.com/questions/1038007/why-should-wait-always-be-called-inside-a-loop
/**
Q: 
I have read that we should always call a wait() from within a loop:
while (!condition) { obj.wait(); }
It works fine without a loop so why is that?

A:
You need not only to loop it but check your condition in the loop. Java does not guarantee that your thread 
will be woken up only by a notify()/notifyAll() call or the right notify()/notifyAll() call at all. Because 
of this property the loop-less version might work on your development environment and fail on the production 
environment unexpectedly.

For example, you are waiting for something:

synchronized (theObjectYouAreWaitingOn) {
   while (!carryOn) {
      theObjectYouAreWaitingOn.wait();
   }
}
An evil thread comes along and:

theObjectYouAreWaitingOn.notifyAll();
If the evil thread does not/can not mess with the carryOn you just continue to wait for the proper client.

Edit: Added some more samples. The wait can be interrupted. It throws InterruptedException and you might need 
to wrap the wait in a try-catch. Depending on your business needs, you can exit or suppress the exception and 
continue waiting.
*/

// Pattern: Other 3 threads are consumer when 1 thread is a producer (https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html)
class FizzBuzz {
    private int n;
    private int counter = 1;
    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        while(counter <= n){ // can be changed to while(true)
            while(counter <= n && (counter % 3 != 0 || counter % 5 == 0)){ // we can add breakpoint here for debugging
                wait();
            }
	    // The additional if(counter > n) break is also critical to block print out additional element when current 
            // thread is waked up from wait() from other threads notifyAll(), there is a chance for counter > n 
            // since counter++ happen on other thread but deliver into current thread, which will skip outside 
            // while(counter <= n) or inside while(counter <= n && ...) loop when break out wait() method, this can be
            // observed by tracking Eclipse debug with above demo main method
            if(counter > n) break;
            printFizz.run();
            ++counter;
            notifyAll();
        }
    }

    // printBuzz.run() outputs "buzz".
    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        while(counter <= n){
            while(counter <= n && (counter % 5 != 0 || counter % 3 == 0)){
                wait();
            }
            if(counter > n) break;
            printBuzz.run();
            ++counter;
            notifyAll();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while(counter <= n){
            while(counter <= n && !(counter % 3 == 0 && counter % 5 == 0)){
                wait();
            }
            if(counter > n) break;
            printFizzBuzz.run();
            ++counter;
            notifyAll();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        while(counter <= n){
            while(counter <= n && (counter % 3 == 0 || counter % 5 == 0)){
                wait();
            }
            if(counter > n) break;
            printNumber.accept(counter);
            ++counter;
            notifyAll();
        }
    }
    
    // Test client
    public static void main(String[] args) {
        FizzBuzz fizzBuzz = new FizzBuzz(15);

        Runnable printFizz = () -> System.out.println("fizz");
        Runnable printBuzz = () -> System.out.println("buzz");
        Runnable printFizzBuzz = () -> System.out.println("fizzbuzz");
        IntConsumer printNumber = number -> System.out.println(number);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(printFizz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(printBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(printFizzBuzz);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(printNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
    }
}

// OR equal to below format
class FizzBuzz {
    private int n;
    private int counter = 1;
    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        synchronized(this) {
            while(true){
                while(counter <= n && (counter % 3 != 0 || counter % 5 == 0)){
                    wait();
                }
                if(counter > n) break;
                printFizz.run();
                ++counter;
                notifyAll();
            }
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        synchronized(this) {
            while(true){
                while(counter <= n && (counter % 5 != 0 || counter % 3 == 0)){
                    wait();
                }
                if(counter > n) break;
                printBuzz.run();
                ++counter;
                notifyAll();
            }
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        synchronized(this) {
            while(true){
                while(counter <= n && !(counter % 3 == 0 && counter % 5 == 0)){
                    wait();
                }
                if(counter > n) break;
                printFizzBuzz.run();
                ++counter;
                notifyAll();
            }
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        synchronized(this) {
            while(true){
                while(counter <= n && (counter % 3 == 0 || counter % 5 == 0)){
                    wait();
                }
                if(counter > n) break;
                printNumber.accept(counter);
                ++counter;
                notifyAll();
            }
        }
    }
}
