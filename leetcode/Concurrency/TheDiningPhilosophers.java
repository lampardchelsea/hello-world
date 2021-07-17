/**
Refer to
https://leetcode.com/problems/the-dining-philosophers/
Five silent philosophers sit at a round table with bowls of spaghetti. Forks are placed between each pair of adjacent philosophers.

Each philosopher must alternately think and eat. However, a philosopher can only eat spaghetti when they have both left and right forks. 
Each fork can be held by only one philosopher and so a philosopher can use the fork only if it is not being used by another philosopher. 
After an individual philosopher finishes eating, they need to put down both forks so that the forks become available to others. 
A philosopher can take the fork on their right or the one on their left as they become available, but cannot start eating before 
getting both forks.

Eating is not limited by the remaining amounts of spaghetti or stomach space; an infinite supply and an infinite demand are assumed.

Design a discipline of behaviour (a concurrent algorithm) such that no philosopher will starve; 
i.e., each can forever continue to alternate between eating and thinking, assuming that no philosopher 
can know when others may want to eat or think.

The problem statement and the image above are taken from wikipedia.org

The philosophers' ids are numbered from 0 to 4 in a clockwise order. Implement the function void wantsToEat(philosopher, 
pickLeftFork, pickRightFork, eat, putLeftFork, putRightFork) where:

philosopher is the id of the philosopher who wants to eat.
pickLeftFork and pickRightFork are functions you can call to pick the corresponding forks of that philosopher.
eat is a function you can call to let the philosopher eat once he has picked both forks.
putLeftFork and putRightFork are functions you can call to put down the corresponding forks of that philosopher.
The philosophers are assumed to be thinking as long as they are not asking to eat (the function is not being called with their number).
Five threads, each representing a philosopher, will simultaneously use one object of your class to simulate the process. 
The function may be called for the same philosopher more than once, even before the last call ends.

Example 1:
Input: n = 1
Output: [[4,2,1],[4,1,1],[0,1,1],[2,2,1],[2,1,1],[2,0,3],[2,1,2],[2,2,2],[4,0,3],[4,1,2],[0,2,1],[4,2,2],[3,2,1],[3,1,1],
[0,0,3],[0,1,2],[0,2,2],[1,2,1],[1,1,1],[3,0,3],[3,1,2],[3,2,2],[1,0,3],[1,1,2],[1,2,2]]
Explanation:
n is the number of times each philosopher will call the function.
The output array describes the calls you made to the functions controlling the forks and the eat function, its format is:
output[i] = [a, b, c] (three integers)
- a is the id of a philosopher.
- b specifies the fork: {1 : left, 2 : right}.
- c specifies the operation: {1 : pick, 2 : put, 3 : eat}.

Constraints:
1 <= n <= 60
*/

// Refer to
// https://www.tutorialspoint.com/dining-philosophers-problem-dpp
/**
The dining philosophers problem states that there are 5 philosophers sharing a circular table and they eat and think alternatively. 
There is a bowl of rice for each of the philosophers and 5 chopsticks. A philosopher needs both their right and left chopstick to eat. 
A hungry philosopher may only eat if there are both chopsticks available.Otherwise a philosopher puts down their chopstick and 
begin thinking again.

The dining philosopher is a classic synchronization problem as it demonstrates a large class of concurrency control problems.

Solution of Dining Philosophers Problem
A solution of the Dining Philosophers Problem is to use a semaphore to represent a chopstick. A chopstick can be picked up by 
executing a wait operation on the semaphore and released by executing a signal semaphore.

The structure of the chopstick is shown below −

semaphore chopstick [5];
Initially the elements of the chopstick are initialized to 1 as the chopsticks are on the table and not picked up by a philosopher.

The structure of a random philosopher i is given as follows −

do {
   wait( chopstick[i] );
   wait( chopstick[ (i+1) % 5] );
   . .
   . EATING THE RICE
   .
   signal( chopstick[i] );
   signal( chopstick[ (i+1) % 5] );
   .
   . THINKING
   .
} while(1);

In the above structure, first wait operation is performed on chopstick[i] and chopstick[ (i+1) % 5]. This means that the philosopher i 
has picked up the chopsticks on his sides. Then the eating function is performed.

After that, signal operation is performed on chopstick[i] and chopstick[ (i+1) % 5]. This means that the philosopher i has eaten and 
put down the chopsticks on his sides. Then the philosopher goes back to thinking.

Difficulty with the solution
The above solution makes sure that no two neighboring philosophers can eat at the same time. But this solution can lead to a deadlock. 
This may happen if all the philosophers pick their left chopstick simultaneously. Then none of them can eat and deadlock occurs.

Some of the ways to avoid deadlock are as follows −
1. There should be at most four philosophers on the table.
2. An even philosopher should pick the right chopstick and then the left chopstick while an odd philosopher should pick the left chopstick 
and then the right chopstick.
3. A philosopher should only be allowed to pick their chopstick if both are available at the same time.
*/

// Solution 1: Semaphore(4) and Reentrant lock
// Refer to
// https://leetcode.com/problems/the-dining-philosophers/discuss/407358/Semaphore-%2B-mutex
/**
Every philosopher starts by picking the left fork and after that he picks the right one. Without any limitations a deadlock 
is possible when all philosophers pick the left fork simultaneously. If we limit the number of philosophers who can pick the 
left fork to 4 there will always be a philosopher who can pick the right fork (the one to the left of a philosopher without any forks).
*/
class DiningPhilosophers {
    private Lock forks[] = new Lock[5];
    private Semaphore semaphore = new Semaphore(4);

    public DiningPhilosophers() {
        for(int i = 0; i < 5; i++) {
            forks[i] = new ReentrantLock();
        }
    }
    
    public void pickFork(int id, Runnable pick) {
        forks[id].lock();
        pick.run();
    }
    
    public void putFork(int id, Runnable put) {
        put.run();
        forks[id].unlock();
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int leftFork = philosopher;
        int rightFork = (philosopher + 4) % 5;
        
        semaphore.acquire();
        
        pickFork(leftFork, pickLeftFork);
        pickFork(rightFork, pickRightFork);
        eat.run();
        putFork(rightFork, putRightFork);
        putFork(leftFork, putLeftFork);
        
        semaphore.release();
    }
}

// Solution 2: Pure Semaphore() no Lock()
// Refer to
// https://leetcode.com/problems/the-dining-philosophers/discuss/411689/Java-Simple-Semaphore-Solution-beats-100

// Semaphore acquire() and release() must be in the same code block
/**
Below wrong version will throw out Line 32: error: unreported exception InterruptedException; must be caught or 
declared to be thrown [in DiningPhilosophers.java]
        forks[id].acquire();

The reason, for acquire(), if not in the same code block of  release() for same Semaphore object then need to add
try catch exception handle around it:
    public void pickFork(int id, Runnable pick) {
        forks[id].acquire();
        pick.run();
    }
    
    public void putFork(int id, Runnable put) {
        put.run();
        forks[id].release();
    }

class DiningPhilosophers {
    private Semaphore[] forks = new Semaphore[5];
    private Semaphore semaphore = new Semaphore(4);

    public DiningPhilosophers() {
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
    }
    
    public void pickFork(int id, Runnable pick) {
        forks[id].acquire();
        pick.run();
    }
    
    public void putFork(int id, Runnable put) {
        put.run();
        forks[id].release();
    }
    
    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int leftFork = philosopher;
        int rightFork = (philosopher + 4) % 5;
        
        semaphore.acquire();
        
        pickFork(leftFork, pickLeftFork);
        pickFork(rightFork, pickRightFork);
        eat.run();
        putFork(leftFork, putLeftFork);
        putFork(rightFork, putRightFork);
        
        semaphore.release();
    }
}

The correct way is make sure acquire() and release() for same Semaphore object in the same code block:
        int leftFork = philosopher;
        int rightFork = (philosopher + 4) % 5;
        forks[leftFork].acquire();
        forks[rightFork].acquire();
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        forks[leftFork].release();
        forks[rightFork].release();

OR

    public void pickFork(int id, Runnable pick) {
        try {
            forks[id].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pick.run();
    }
    
    public void putFork(int id, Runnable put) {
        put.run();
        forks[id].release();
    }
*/

// Correct way
// Style 1:
class DiningPhilosophers {
    private Semaphore[] forks = new Semaphore[5];
    private Semaphore semaphore = new Semaphore(2);

    public DiningPhilosophers() {
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int leftFork = philosopher;
        int rightFork = (philosopher + 4) % 5;
        
        semaphore.acquire();

        forks[leftFork].acquire();
        forks[rightFork].acquire();
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        forks[leftFork].release();
        forks[rightFork].release();
 
        semaphore.release();
    }
}

// Style 2:
class DiningPhilosophers {
    private Semaphore[] forks = new Semaphore[5];
    private Semaphore semaphore = new Semaphore(4);

    public DiningPhilosophers() {
        for(int i = 0; i < 5; i++) {
            forks[i] = new Semaphore(1);
        }
    }
    
    public void pickFork(int id, Runnable pick) {
        try {
            forks[id].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pick.run();
    }
    
    public void putFork(int id, Runnable put) {
        put.run();
        forks[id].release();
    }
    
    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int leftFork = philosopher;
        int rightFork = (philosopher + 4) % 5;
        
        semaphore.acquire();
        
        pickFork(leftFork, pickLeftFork);
        pickFork(rightFork, pickRightFork);
        eat.run();
        putFork(leftFork, putLeftFork);
        putFork(rightFork, putRightFork);
        
        semaphore.release();
    }
}
