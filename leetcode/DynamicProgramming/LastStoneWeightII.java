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

// Solution 1: 1D-DP
// Refer to
// https://leetcode.com/problems/last-stone-weight-ii/discuss/294888/JavaC%2B%2BPython-Easy-Knapsacks-DP
/**
 Chinese explanation for contest this week on bilibili.

Intuition
Same problem as:
Divide all numbers into two groups,
what is the minimum difference between the sum of two groups.
Now it's a easy classic knapsack problem.


Brief Prove
All cases of "cancellation of rocks" can be expressed by two knapsacks.
And the last stone value equals to the difference of these two knapsacks
It needs to be noticed that the opposite proposition is wrong.

Solution 1
Explanation:
Very classic knapsack problem solved by DP.
In this solution, I use dp to record the achievable sum of the smaller group.
dp[x] = 1 means the sum x is possible.

Time Complexity:
O(NS) time,
O(S) space, where S = sum(A).

Java, use array:
    public int lastStoneWeightII(int[] A) {
        boolean[] dp = new boolean[1501];
        dp[0] = true;
        int sumA = 0;
        for (int a : A) {
            sumA += a;
            for (int i = sumA; i >= a; --i)
                dp[i] |= dp[i - a];
        }
        for (int i = sumA / 2; i > 0; --i)
            if (dp[i]) return sumA - i - i;
        return 0;
    }
Follow-up:
As this problem will be quite boring as Q4 (if you read my post),
I'll leave you a slightly harder problem as follow-up just for more fun.
Question: Return the biggest possible weight of this stone?

FAQ (Some high voted questions)
Question: How is it a knapsack problem?
My understanding of Knapsack problem is this-
You are given a set of items , for each of which we have a weight w[i] and value v[i].
Now we have a bag for capacity W and we maximize our profit.
Answer:
w[i] = 1
v[i] = stones[i]
W = sum(stones) / 2

Question: Why the minimum result of cancellation is equal to minimum knapsack partition?
Answer:
One cancellation can be represented as one grouping.
One grouping can be represented as one knapsack partition.
If the grouping difference < max(A), it can be realized by a cancellation.
With the 2 conclusions above,
we can know that the minimum result of cancellation is equal to minimum grouping difference,
which we solved by knapsack problem.

Question: In some version of solution, what does the magic number 1500 mean?
Answer:
The dp[i] present if the sum of one group can be i.
So we can only record the smaller one for less space cost,
which less than half of the upper bound of total sum 3000.
*/

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
When you have the sum for one group (e.g.: [4,8] -> 12), you can just subtract that value from the total sum to 
obtain the sum of the other group.
From the example above. Total sum is 23, the sum for [4,8] is 12, then the sum for the other group is 23-12 = 11. 
And 12-11 = 1;

So, notice that, if aSum is the sum of a group, the difference of the sums of each group is: Math.abs(totalSum - aSum - aSum).
For this reason you are going to see some solutions doing Math.abs(totalSum - (aSum*2)), which is not that obvious.
So, for each possible 'aSum', minimumAnswer = Math.min(minimumAnswer , Math.abs(totalSum - aSum - aSum));

I hope this helps you.
EDIT: Read one of my replies below for explanation on why the problem can be interpreted that way
*/
// Style 1: Find the minimum difference by the way
class Solution {
    // p = subsum name as p, n = another subsum name as n 
    // p + n = sum
    // p - n = diff
    // 2n = sum - diff
    // diff = sum - 2n
    // smallest diff means maximum n, also n range between 0 to n/2
    // now we need to find maximum n
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for(int stone : stones) {
            sum += stone;
        }
        boolean[] dp = new boolean[sum / 2 + 1];
        int minDiff = Integer.MAX_VALUE;
        dp[0] = true;
        for(int i = 0; i < stones.length; i++) {
            for(int j = sum / 2; j >= stones[i]; j--) {
                if(dp[j - stones[i]]) {
                    dp[j] = true;
                }
                if(dp[j]) {
                    minDiff = Math.min(minDiff, sum - 2 * j);
                }
            }
        }
        // for(int i = sum / 2; i >= 0; i--) {
        //     if(dp[i]) {
        //         return minDiff = sum - 2 * i;
        //     }
        // }
        return 0;
    }
}


// Style 2: Find the minimum difference with one more for loop
class Solution {
    // p = subsum name as p, n = another subsum name as n 
    // p + n = sum
    // p - n = diff
    // 2n = sum - diff
    // diff = sum - 2n
    // smallest diff means maximum n, also n range between 0 to n/2
    // now we need to find maximum n
    public int lastStoneWeightII(int[] stones) {
        int sum = 0;
        for(int stone : stones) {
            sum += stone;
        }
        boolean[] dp = new boolean[sum / 2 + 1];
        int minDiff = Integer.MAX_VALUE;
        dp[0] = true;
        for(int i = 0; i < stones.length; i++) {
            for(int j = sum / 2; j >= stones[i]; j--) {
                if(dp[j - stones[i]]) {
                    dp[j] = true;
                }
                // if(dp[j]) {
                //     minDiff = Math.min(minDiff, sum - 2 * j);
                // }
            }
        }
        // The 1st true element you encounter on dp array means the largest 
        // negative sum able to reach, directly return (sum - 2 * i)
        for(int i = sum / 2; i >= 0; i--) {
            if(dp[i]) {
                return minDiff = sum - 2 * i;
            }
        }
        return 0;
    }
}

// Wrong Solution:
// Refer to
// https://leetcode.com/problems/last-stone-weight-ii/discuss/295759/Java-DP-solution-with-explanation/332902
/**
 I just switch the inner for loop and outer for loop, logically i am not sure this switch violate any rule ? 
 But the problem generated, e.g keep input = [2,7,4,1,8,1], expected = 1, my output is 7,
 I have checking the dp array after for loop, the expected result is every element on dp array is true, but 
 the wrong one (after switch the for loop) looks below, and the last element as false will causing the wrong 
 output as 7, since it only have i = 8 and dp[8] = true -> 
 dp = [true, true, true, false, true, false, false, true, true, false, false, false]
*/
class Solution {
    public int lastStoneWeightII(int[] stones) {
    if (stones == null || stones.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
        }
        boolean[] dp = new boolean[sum/2 + 1];
        dp[0] = true; // The first achievable dp spot, but not a solution in this problem
        int minDiff = Integer.MAX_VALUE;
        for (int j=sum/2; j>0; j--) {
            for(int stone : stones) {
                if(j >= stone) {
                    dp[j] |= dp[j-stone];
                    if (dp[j]) {
                        minDiff = Math.min(minDiff, sum - 2*j);
                    }    
                }
            }
        }
        return minDiff;
    }
}





// Solution 2: 2D-DP
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
*/
