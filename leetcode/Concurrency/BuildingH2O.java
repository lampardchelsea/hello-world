/**
Refer to
https://leetcode.com/problems/building-h2o/
There are two kinds of threads, oxygen and hydrogen. Your goal is to group these threads to form water molecules. 
There is a barrier where each thread has to wait until a complete molecule can be formed. Hydrogen and oxygen threads 
will be given releaseHydrogen and releaseOxygen methods respectively, which will allow them to pass the barrier. 
These threads should pass the barrier in groups of three, and they must be able to immediately bond with each other 
to form a water molecule. You must guarantee that all the threads from one molecule bond before any other threads 
from the next molecule do.

In other words:
If an oxygen thread arrives at the barrier when no hydrogen threads are present, it has to wait for two hydrogen threads.
If a hydrogen thread arrives at the barrier when no other threads are present, it has to wait for an oxygen thread and another hydrogen thread.
We don’t have to worry about matching the threads up explicitly; that is, the threads do not necessarily know which 
other threads they are paired up with. The key is just that threads pass the barrier in complete sets; thus, if we 
examine the sequence of threads that bond and divide them into groups of three, each group should contain one oxygen 
and two hydrogen threads.

Write synchronization code for oxygen and hydrogen molecules that enforces these constraints.

Example 1:
Input: "HOH"
Output: "HHO"
Explanation: "HOH" and "OHH" are also valid answers.

Example 2:
Input: "OOHHHH"
Output: "HHOHHO"
Explanation: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" and "OHHOHH" are also valid answers.

Constraints:
Total length of input string will be 3n, where 1 ≤ n ≤ 20.
Total number of H will be 2n in the input string.
Total number of O will be n in the input string.
*/

// Solution 1: Synchronized with wait() and notify()
// Refer to
// https://leetcode.com/problems/building-h2o/discuss/721793/CORRECT-and-efficient-C%2B%2B-solution-with-mutex-and-condition-variables.-99
/**
I have scanned through some C++ solutions, and I think most of them are wrong.
Some of them fail to

In other words:

If an oxygen thread arrives at the barrier when no hydrogen threads are present, it has to wait for two hydrogen threads.
If a hydrogen thread arrives at the barrier when no other threads are present, it has to wait for an oxygen thread and another hydrogen thread.
Your goal is not to make sure you print in the correct order but that you are doing what you are told to do.
Specifically, it says do NOT start releasing anything until you have 2 Hs and 1 O.
Some people introduce race conditions or do too much of signaling. I tried to create a correct solution with minimum amount of signaling.

If you find something wrong with my program, please let me know.
I have created a few variables: h and o to keep track of total number of threads waiting. leftH and leftO to indicate how many more O's or H's 
you need to complete this transaction. I have also created bool pairing to signify if we have started pairing( releasing H2O).
Here is the idea: our program can be in two modes: 1) Here, pairing is false. We don't have enough of resources to start pairing and threads 
just keep waiting on the barrier. For example, if the order is HOOOOOOO or HHHHHHHHHH. At some point a thread whose resource we were missing 
is going to enter and this is when we are going to start building H20. That "initiating" thread can be O or H. Whatever it is, it is going 
to start pairing.

2)This is another mode. It is associated with pairing=true. Some thread is going to start pairing. We subtract both leftH and h or leftO 
and o depending on who is initiating.
Then, depending on what other resources and how many of them I need, I am going to signal to other threads. Since, we are after the wait on 
the condition variable, we are sure we have the resources we need to complete this transaction. I have used two condition variables to 
separate threads into two groups. One group is Hydrogen; another group is Oxygen. To avoid too much signaling and get functionality of 
signaling to a type of thread that we need, I made that decision.
Once you start pairing, even if new threads arrive, they should be allowed inside if and only if they are giving you the component that 
you are missing. This is achieved by waiting on one thing or another.

(!paired && o >= 1 && h >= 2)
This is a condition for any thread to start producing H20(Initiator thread)

(pairing && leftH > 0) for Hydrogen or (pairing && leftO > 0) for Oxygen are the conditions to enter in case we have already started building H20.

Last thing about this solution is that if you were the third thread that completed H20, you should reset all the values and say that pairing 
is complete by setting pairing to false.
Bottom line:
I believe this is a good solution. I have one doubt.
I saw some people are using two different mutexes but I don't think it is allowed to do if you are doing reads on the variable that's not 
synchronized. In my case, I am using values related to both H and O. So, we have to use one mutex.

private:
    int h;
    int leftH;
    int o;
    int leftO;
    mutex m;
    condition_variable cvH;
    condition_variable cvO;
    bool pairing;
    // this function to be called when pairing is done.
    void reset(){
        leftH = 2;
        leftO = 1;
        pairing = false;
    }
public:
    H2O() {
        h = 0;
        o = 0;
        reset();
    }

    void hydrogen(function<void()> releaseHydrogen) {
        unique_lock<mutex> ul(m);
        h++;
        cvH.wait(ul, [&](){
            return (!pairing && o >= 1 && h >= 2) || (pairing && leftH > 0);
        });
        
        // if are the first thread to start bonding three together, mark the process as started
        pairing = true;
        leftH--;
        h--;
        
        // if we need more hydrogen
        if(leftH > 0)
            cvH.notify_one();
        
        // if we need more oxygen
        if(leftO > 0)
            cvO.notify_one();
        
        // we are done pairing
        if(leftO == 0 && leftH == 0){
            reset();
        }
        // releaseHydrogen() outputs "H". Do not change or remove this line.
        releaseHydrogen();
    }

    void oxygen(function<void()> releaseOxygen) {
        unique_lock<mutex> ul(m);
        o++;
        cvO.wait(ul, [&](){
            return (!pairing && h >= 2 && o >= 1) || (pairing && leftO > 0);
        });
        // if are the first thread to start bonding three together, mark the process as started
        pairing = true;
        leftO--;
        o--;
        // if we need more hydrogen
        if(leftH == 1)
            cvH.notify_one();
        else if(leftH == 2)
            cvH.notify_all();
        
        // we are done pairing
        if(leftO == 0 && leftH == 0){
            reset();
        }
        // releaseOxygen() outputs "O". Do not change or remove this line.
        releaseOxygen();
    }
*/

// https://leetcode.com/problems/building-h2o/discuss/894910/Java-SynchronizedSemaphore
/**
class H2O {
    private int cntH;
    
    public H2O() {
        cntH = 0;
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        synchronized(this) {
            while (cntH == 2) {
                wait();
            }
            releaseHydrogen.run();
            ++cntH;
            notifyAll();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        synchronized(this) {
            while (cntH != 2) {
                wait();
            }
            releaseOxygen.run();
            cntH -= 2;
            notifyAll();
        }
    }
}
*/

// https://leetcode.com/problems/building-h2o/discuss/897529/Java-Solution
class H2O {
    private int hydrogenCount;
    private int oxygenCount;
    public H2O() {
        hydrogenCount = 0;
        oxygenCount = 0;
    }

    public synchronized void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while(hydrogenCount == 2 && oxygenCount == 0) {
            wait();
        }
        hydrogenCount++;
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        if(hydrogenCount == 2 && oxygenCount == 1) {
            hydrogenCount = 0;
            oxygenCount = 0;
            notifyAll();
        }
    }

    public synchronized void oxygen(Runnable releaseOxygen) throws InterruptedException {
        while(hydrogenCount < 2 && oxygenCount == 1) {
            wait();
        }
        oxygenCount++;
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        if(hydrogenCount == 2 && oxygenCount == 1) {
            hydrogenCount = 0;
            oxygenCount = 0;
            notifyAll();
        }
    }
}

// Solution 2: Semaphore(0) and Semaphore(1)
// Refer to
// 
