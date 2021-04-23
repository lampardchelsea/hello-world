/**
Refer to
https://leetcode.com/problems/find-the-winner-of-the-circular-game/
There are n friends that are playing a game. The friends are sitting in a circle and are numbered from 1 to n 
in clockwise order. More formally, moving clockwise from the ith friend brings you to the (i+1)th friend for 
1 <= i < n, and moving clockwise from the nth friend brings you to the 1st friend.

The rules of the game are as follows:

Start at the 1st friend.
Count the next k friends in the clockwise direction including the friend you started at. The counting wraps around 
the circle and may count some friends more than once.
The last friend you counted leaves the circle and loses the game.
If there is still more than one friend in the circle, go back to step 2 starting from the friend immediately 
clockwise of the friend who just lost and repeat.
Else, the last friend in the circle wins the game.
Given the number of friends, n, and an integer k, return the winner of the game.

Example 1:
Input: n = 5, k = 2
Output: 3
Explanation: Here are the steps of the game:
1) Start at friend 1.
2) Count 2 friends clockwise, which are friends 1 and 2.
3) Friend 2 leaves the circle. Next start is friend 3.
4) Count 2 friends clockwise, which are friends 3 and 4.
5) Friend 4 leaves the circle. Next start is friend 5.
6) Count 2 friends clockwise, which are friends 5 and 1.
7) Friend 1 leaves the circle. Next start is friend 3.
8) Count 2 friends clockwise, which are friends 3 and 5.
9) Friend 5 leaves the circle. Only friend 3 is left, so they are the winner.

Example 2:
Input: n = 6, k = 5
Output: 1
Explanation: The friends leave in this order: 5, 4, 6, 2, 3. The winner is friend 1.

Constraints:
1 <= k <= n <= 500
*/

// Solution 1: Shift person to tail to expose dead person at head
// Refer to
// https://leetcode.com/problems/find-the-winner-of-the-circular-game/discuss/1152460/JAVA-Queue-self-explanatory
// https://leetcode.com/problems/find-the-winner-of-the-circular-game/discuss/1152460/JAVA-Queue-self-explanatory/904775
/**
We're filling the Queue with n elements. Then till we have more than 1 elements (who shall be the winner) we will do some stuff.
Which is, we will remove k - 1 elements from starting of the Queue and add those in the back. Then the pop the kth element but don't add it to Queue because he/she is the one who have to die. In the end returnthe last and only element in Queue.
Hope it helps. If any doubt persist, ask me :)

class Solution {
    public int findTheWinner(int n, int k) {//for example let's take n = 4 && k = 3
        Queue<Integer> queue = new LinkedList<>();

        for(int i = 1; i <= n; i++)//fill the Queue [1, 2, 3, 4]
            queue.offer(i);
                    //Queue in corresponding iteration =>   1st iteration   |  2nd iteration |  3rd iteration
        while(queue.size() > 1){//until we have a winner      [1, 2, 3 4]   |    [4, 1, 2]   |    [4, 1]
            int delete = k - 1;//delete = 2 
            while(delete > 0){
                queue.offer(queue.remove());//remove front element and add it in the end
                delete--;
            } // after the loop is ended Queue will be       [3, 4, 1, 2]  |  [2, 4, 1]  |  [4, 1]
            queue.remove();//dead person                      [4, 1, 2]    |   [4, 1]    |    [1]
        }     
        return queue.remove();//winner 1
    }
}
*/
class Solution {
    public int findTheWinner(int n, int k) {
        // For example let's take n = 4 and k = 3
        Queue<Integer> q = new LinkedList<Integer>();
        // Fill the Queue [1, 2, 3, 4]
        for(int i = 1; i <= n; i++) {
            q.offer(i);
        }
        // Queue in corresponding iteration until we have a winner
        // =>   1st iteration   |  2nd iteration  |  3rd iteration
        // =>    [1, 2, 3, 4]   |    [4, 1, 2]    |    [4, 1]
        while(q.size() > 1) {
            // 2 elements pending on move to tail
            int move_to_tail = k - 1;
            while(move_to_tail > 0) {
                // Remove front element and add it in the end
                q.offer(q.remove());
                move_to_tail--;
            }
            // After the loop is ended queue will be
            // [3, 4, 1, 2]  |  [2, 4, 1]  |  [4, 1]
            // Dead person
            q.remove();
        }
        // Winner as 1
        return q.remove();
    }
}

// Solution 2: Josephus problem
// https://leetcode.com/problems/find-the-winner-of-the-circular-game/discuss/1152705/JavaC%2B%2BPython-4-lines-O(n)-time-O(1)-space
/**
Explanation
In the end,n = 1,
the index of winner index is 0 (base-0)

We think with one step back,
when n = 2,
the index of winner is 0 + k,
but we have only n peopple,
so the winner index is (0 + k) % 2 (base-0)

We think with one more step back,
when n = 3,
the index of winner is f(2) + k,
but we have only n peopple,
so the winner index is (f(2) + k) % 3 (base-0)

We think with n more step back,
the index of winner is f(n-1) + k,
but we have only n peopple,
so the winner index is (f(n-1) + k) % n (base-0)

Done.

Complexity
Time O(n)
Space O(1)

Java

    public int findTheWinner(int n, int k) {
        int res = 0;
        for (int i = 1; i <= n; ++i)
            res = (res + k) % i;
        return res + 1;
    }
*/
class Solution {
    public int findTheWinner(int n, int k) {
        int res = 0;
        for (int i = 1; i <= n; ++i)
            res = (res + k) % i;
        return res + 1;
    }
}


