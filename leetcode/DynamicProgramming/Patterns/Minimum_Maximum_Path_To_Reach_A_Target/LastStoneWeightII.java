/**
 Refer to
 https://leetcode.com/problems/last-stone-weight-ii/
 We have a collection of rocks, each rock has a positive integer weight.
Each turn, we choose any two rocks and smash them together.  Suppose the stones have weights x and y with x <= y.  
The result of this smash is:
If x == y, both stones are totally destroyed;
If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
At the end, there is at most 1 stone left.  Return the smallest possible weight of this stone (the weight is 0 
if there are no stones left.)
Example 1:
Input: [2,7,4,1,8,1]
Output: 1
Explanation: 
We can combine 2 and 4 to get 2 so the array converts to [2,7,1,8,1] then,
we can combine 7 and 8 to get 1 so the array converts to [2,1,1,1] then,
we can combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
we can combine 1 and 1 to get 0 so the array converts to [1] then that's the optimal value.
Note:
1 <= stones.length <= 30
1 <= stones[i] <= 100
*/

// Solution 1: Top Down DP Memoization (0-1 Knapsack 2D-DP)
// Template refer to
// http://www.mathcs.emory.edu/~cheung/Courses/253/Syllabus/DynProg/knapsack3.html
// https://leetcode.com/problems/partition-equal-subset-sum/discuss/90592/01-knapsack-detailed-explanation

// Wny diff = S - 2 * S2  ==> minimize diff equals to  maximize S2 ?
// Refer to
// https://leetcode.com/problems/last-stone-weight-ii/discuss/295325/Why-DP-is-applicable-here/277732
/**
DP is used on the transformed problem. I was also not able to come with that solution. It is not obvious, indeed.
It maybe easy for people with more experience. We should be able to do that with more and more practice.

The problem can be interpreted as the following:
Divide the stones into two groups such that the difference of the sum of weights of each group is minimum.

For the problem at the description:
[2,7,4,1,8,1]
Those groups could be:
1,1,2,7 (sum is 11) --- 4,8 (sum is 12)
Difference: 1

Using DP you can check all possible sums of a group.
When you have the sum for one group (e.g.: [4,8] -> 12), you can just subtract that value from the total sum to obtain the sum of the other group.
From the example above. Total sum is 23, the sum for [4,8] is 12, then the sum for the other group is 23-12 = 11. And 12-11 = 1;

So, notice that, if aSum is the sum of a group, the difference of the sums of each group is: Math.abs(totalSum - aSum - aSum).
For this reason you are going to see some solutions doing Math.abs(totalSum - (aSum*2)), which is not that obvious.
So, for each possible 'aSum', minimumAnswer = Math.min(minimumAnswer , Math.abs(totalSum - aSum - aSum));

I hope this helps you.

EDIT: Read one of my replies below for explanation on why the problem can be interpreted that way

    p = subsum name as p, n = another subsum name as n 
    p + n = sum
    p - n = diff
    2n = sum - diff
    diff = sum - 2n
    smallest diff means maximum n, also n range between 0 to n/2
    now we need to find maximum n
*/

// Refer to
// https://leetcode.com/problems/last-stone-weight-ii/discuss/294888/JavaC++Python-Easy-Knapsacks-DP/393908
/**
dp[i][j] means the max value of putting the first i number into a backpack with capacity of j
public int lastStoneWeightII(int[] stones) {
    int n = stones.length, sum = 0;
    for (int s : stones) sum += s;
    int[][] dp = new int[n + 1][sum / 2 + 1];
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j <= sum / 2; j++) {
            if (j >= stones[i - 1]) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - stones[i - 1]] + stones[i - 1]);
            } else {
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    return sum - 2 * dp[n][sum / 2];
}
*/

// Refer to
// https://leetcode.com/problems/last-stone-weight-ii/discuss/295167/Java-beat-100-with-nice-explanation
/**
This question eaquals to partition an array into 2 subsets whose difference is minimal
(1) S1 + S2  = S
(2) S1 - S2 = diff  

==> -> diff = S - 2 * S2  ==> minimize diff equals to  maximize S2 

Now we should find the maximum of S2 , range from 0 to S / 2, using dp can solve this

dp[i][j]   = {true if some subset from 1st to j'th has a sum equal to sum i, false otherwise}
    i ranges from (sum of all elements) {1..n}
    j ranges from  {1..n}

same as 494. Target Sum

class Solution {
    public int lastStoneWeightII(int[] stones) {
        int S = 0, S2 = 0;
        for (int s : stones) S += s;
        int n = stones.length;
        boolean[][] dp = new boolean[S + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            dp[0][i] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= S / 2; s++) {
                if (dp[s][i - 1] || (s >= stones[i - 1] && dp[s - stones[i - 1]][i - 1])) {
                    dp[s][i] = true;
                    S2 = Math.max(S2, s);
                }
            }
        }
        return S - 2 * S2;
    }
}
*/

class Solution {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int sum = 0;
        for(int stone : stones) {
            sum += stone;
        }
        // dp[i][j] means if its possible or not to use any subset
        // from first i stones to reach target weight (0 to sum / 2)
        boolean[][] dp = new boolean[n + 1][sum / 2 + 1];
        // Initialize target weight as 0, any first i stones able to reach
        for(int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        // Initialize first 0 stones pick up in the array, not possible
        // to reach any target weight (1 to sum / 2), except dp[0][0] since
        // 0 stones for target weight as 0 is fine
        for(int i = 1; i <= sum / 2; i++) {
            dp[0][i] = false;
        }
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= sum / 2; j++) {
                // Case 1: If we don't pick up the (i - 1)th stone
                // If dp[i - 1][j] is true, means first (i - 1) stones able to 
                // reach target weight, so if not pick up the ith stone, the 
                // first i stones definitely able to
                if(dp[i - 1][j]) {
                    dp[i][j] = dp[i - 1][j]; // Same as dp[i][j] = true
                }
                // Case 2: If we pick up the (i - 1)th stone
                // If the (i - 1)th stone weight less than current target weight j, 
                // able to pick up the (i - 1)th stone only when 
                // dp[i - 1][j - stones[i - 1]] is true
                if(j >= stones[i - 1] && dp[i - 1][j - stones[i - 1]]) {
                    dp[i][j] = true;
                }
            }
        }
        // The 1st true element you encounter on dp array means the largest 
        // negative sum able to reach, directly return (sum - 2 * i)
        for(int i = sum / 2; i >= 0; i--) {
            if(dp[n][i]) {
                return sum - 2 * i;
            }
        }
        return 0;
    }
}

// Since only relate to previous row, we can able to make it 1D
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/How_downgrade_2D_to_1D_and_why_loop_backwards.txt
class Solution {
    public int lastStoneWeightII(int[] stones) {
        int n = stones.length;
        int sum = 0;
        for(int stone : stones) {
            sum += stone;
        }
        // Initialize only as 1D-DP
        boolean[] dp = new boolean[sum / 2 + 1];
        dp[0] = true;
        for(int i = 0; i < n; i++) {
            // Reverse the loop order
            for(int j = sum / 2; j >= stones[i]; j--) {
                // Only depends on previous status as dp[j - stones[i]]
                if(dp[j - stones[i]]) {
                    dp[j] = true;
                }
            }
        }
        for(int i = sum / 2; i >= 0; i--) {
            if(dp[i]) {
                return sum - 2 * i;
            }
        }
        return 0;
    }
}
