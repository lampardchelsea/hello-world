/**
Refer to
https://leetcode.com/problems/grumpy-bookstore-owner/
Today, the bookstore owner has a store open for customers.length minutes.  Every minute, some number of customers 
(customers[i]) enter the store, and all those customers leave after the end of that minute.

On some minutes, the bookstore owner is grumpy.  If the bookstore owner is grumpy on the i-th minute, grumpy[i] = 1, 
otherwise grumpy[i] = 0.  When the bookstore owner is grumpy, the customers of that minute are not satisfied, 
otherwise they are satisfied.

The bookstore owner knows a secret technique to keep themselves not grumpy for X minutes straight, but can only use it once.

Return the maximum number of customers that can be satisfied throughout the day.

Example 1:
Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
Output: 16
Explanation: The bookstore owner keeps themselves not grumpy for the last 3 minutes. 
The maximum number of customers that can be satisfied = 1 + 1 + 1 + 1 + 7 + 5 = 16.

Note:
1 <= X <= customers.length == grumpy.length <= 20000
0 <= customers[i] <= 1000
0 <= grumpy[i] <= 1
*/

// Solution 1: Fixed length sliding window but TLE
/**
76 / 78 test cases passed
*/
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int max = 0;
        int curWindowMax = 0;
        int curTotalMax = 0;
        for(int i = 0; i < customers.length; i++) {
            curWindowMax += customers[i];
            if(i >= X) {
                curWindowMax -= customers[i - X];
            }
            if(i >= X - 1) {
                int pre_remain = 0;
                int after_remain = 0;
                for(int j = 0; j < i - X + 1; j++) {
                    if(grumpy[j] == 0) {
                        pre_remain += customers[j];
                    }
                }
                for(int k = i + 1; k < customers.length; k++) {
                    if(grumpy[k] == 0) {
                        after_remain += customers[k];
                    }
                }
                curTotalMax = curWindowMax + pre_remain + after_remain;
                max = Math.max(curTotalMax, max);
            }
        }
        return max;
    }
}

// Solution 2: Convert the problem into satisfied people + possible max un-satisfied people can be get from sliding window,
//             since in sliding window we can convert these un-satified people into satisfied, so max un-satisfied means
//             we can get max satisfied after converting
// Refer to
// https://leetcode.com/problems/grumpy-bookstore-owner/discuss/299230/JavaPython-3-Sliding-window.
/**
Use a sliding window winOfMakeSatisfied to record the number of unsatisfied customers for X minutes. 
Deduct the unsatisfied customers from left end of the sliding window when it is wider than X:
winOfMakeSatisfied -= grumpy[i - X] * customers[i - X];.
Use satisfied to record the number of satistified customers without grumpy technique.
by the end of iterations, satisfied + max(winOfMakeSatisfied) is the answer.
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int satisfied = 0, maxMakeSatisfied = 0;
        for (int i = 0, winOfMakeSatisfied = 0; i < grumpy.length; ++i) {
            if (grumpy[i] == 0) { satisfied += customers[i]; }
            else { winOfMakeSatisfied += customers[i]; }
            if (i >= X) {
                winOfMakeSatisfied -= grumpy[i - X] * customers[i - X];
            }
            maxMakeSatisfied = Math.max(winOfMakeSatisfied, maxMakeSatisfied);
        }
        return satisfied + maxMakeSatisfied;        
    }
*/
class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int maxUnsatisfied = 0;
        int curWindowUnsatisfied = 0;
        int satisfied = 0;
        for(int i = 0; i < customers.length; i++) {
            if(grumpy[i] == 0) {
                satisfied += customers[i];
            } else {
                curWindowUnsatisfied += customers[i];
            }
            if(i >= X) {
                curWindowUnsatisfied -= customers[i - X] * grumpy[i - X];
            }
            maxUnsatisfied = Math.max(maxUnsatisfied, curWindowUnsatisfied);
        }
        return satisfied + maxUnsatisfied;
    }
}
