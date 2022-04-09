/**
Refer to
https://leetcode.com/problems/combination-sum-iv/
Given an array of distinct integers nums and a target integer target, return the number of possible combinations that add up to target.

The test cases are generated so that the answer can fit in a 32-bit integer.

Example 1:
Input: nums = [1,2,3], target = 4
Output: 7
Explanation:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
Note that different sequences are counted as different combinations.

Example 2:
Input: nums = [9], target = 3
Output: 0

Constraints:
1 <= nums.length <= 200
1 <= nums[i] <= 1000
All the elements of nums are unique.
1 <= target <= 1000

Follow up: What if negative numbers are allowed in the given array? How does it change the problem? What limitation we need to add to 
the question to allow negative numbers?
*/

// Critical point 1: Permutation OR Combination
// This problem is definitely a permuatation problem, because the order matters, in Combination Sum II we have to use order-1 beacuse different order matters
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation/191809
/**
Some comment about the iterative solution with different orders of loops:
order-1:

for each sum in dp[]
    for each num in nums[]
        if (sum >= num)
            dp[sum] += dp[sum-num];
order-2:

for each num in nums[]
    for each sum in dp[]  >= num
        dp[sum] += dp[sum-num];

order-1 is used to calculate the number of combinations considering different sequences
order-2 is used to calculate the number of combinations NOT considering different sequences

Give an example nums[] = {1, 2, 3}, target = 4
order-1 considers the number of combinations starting from 1, 2, and 3, respectively, so all sequences are considered as the graph below.

1 --> 1 --> 1 --> 1 --> (0)
1 --> 1 --> 2 --> (0)
1 --> 2 --> 1 --> (0)
1 --> 3 --> (0)

2 --> 1 --> 1 --> (0)
2 --> 2 --> (0)

3 --> 1 --> (0)

order-2 considers the number of combinations starting from 0 (i.e., not picking anyone), and the index of the num picked next must be >= the 
index of previous picked num, so different sequences are not considered, as the graph below.

(0) --> 1 --> 1 --> 1 --> 1
(0) --> 1 --> 1 --> 2
(0) --> 1 --> 3
(0) --> 2 --> 2
*/
/

// Critical point 2: What's the difference between CombinationSumII and PermutationII ?
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/CombinationSumII.java
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/PermutationsII.java
/**
The actual big difference is force "i = startIndex" and "i + 1" in computing CombinationSumII, because it make sure all 
elements only use once, the index increasing logic not apply to PermutationII because we can still come back to previous 
element to build a same elements combination but now build with different order as a new permutation of that combination

e.g
====================================================
CombinationSumII
Sort set first, then force "i = startIndex" and "i + 1" to compute combination for a set includes same elements {1,1,7} 
and target 8, the result will be only {1,7}, and to skip duplicate "1" in {1,1,7} we use logic:
for(int i = startIndex; i < candidates.length; i++) {
    if(i > startIndex && candidates[i] == candidates[i - 1]) {
        continue;
    }
......
}
====================================================
PermutationII
Sort set first, then NOT force "i = startIndex" and "i + 1" to compute permutation for a set includes same elements {1,1,7} 
and target 8, the result will be {1,7} and {7,1}, even the "1" here only comes from the first "1" in {1,1,7} and never comes 
from the second "1" in {1,1,7}, but since we can come back to initial index as "i = 0" to start every recursive level, so 
after pick up "7" we can come back to pick up first "1" again, and to skip duplicate "1" in {1,1,7} we use logic:
boolean[] visited = new boolean[candidates.length];
for(int i = 0; i < candidates.length; i++) {
    if(visited[i] || (i > 0 && !visited[i - 1] && candidates[i] == candidates[i - 1])) {
        continue;
    }
......
}
====================================================
*/


// Solution 1: Native DFS
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
/**
Think about the recurrence relation first. How does the # of combinations of the target related to the # of combinations of numbers that are smaller than the target?

So we know that target is the sum of numbers in the array. Imagine we only need one more number to reach target, this number can be any one in the array, right? 
So the # of combinations of target, comb[target] = sum(comb[target - nums[i]]), where 0 <= i < nums.length, and target >= nums[i].

In the example given, we can actually find the # of combinations of 4 with the # of combinations of 3(4 - 1), 2(4- 2) and 1(4 - 3). As a result, 
comb[4] = comb[4-1] + comb[4-2] + comb[4-3] = comb[3] + comb[2] + comb[1].

Then think about the base case. Since if the target is 0, there is only one way to get zero, which is using 0, we can set comb[0] = 1.

EDIT: The problem says that target is a positive integer that makes me feel it's unclear to put it in the above way. Since target == 0 only happens when in 
the previous call, target = nums[i], we know that this is the only combination in this case, so we return 1.
*/
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if (target == 0) {
            return 1;
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += combinationSum4(nums, target - nums[i]);
            }
        }
        return res;
    }
}

// Solution 2: Top Down DP (Memoization)
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
/**
Now for a DP solution, we just need to figure out a way to store the intermediate results, to avoid the same combination sum being calculated many times. 
We can use an array to save those results, and check if there is already a result before calculation. We can fill the array with -1 to indicate that the 
result hasn't been calculated yet. 0 is not a good choice because it means there is no combination sum for the target.
*/
class Solution {
    private int[] dp;

    public int combinationSum4(int[] nums, int target) {
        dp = new int[target + 1];
        Arrays.fill(dp, -1);
        dp[0] = 1;
        return helper(nums, target);
    }

    private int helper(int[] nums, int target) {
        if (dp[target] != -1) {
            return dp[target];
        }
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            if (target >= nums[i]) {
                res += helper(nums, target - nums[i]);
            }
        }
        dp[target] = res;
        return res;
    }
}


// Solution 3: Bottom UP 2D-DP (Standard 0-1 Knapsack problem)
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/702432/Java-or-1D-or-2D-or-Bottom-Up-or-Top-Down
/**
As bottom up are meant to be start with the base cases and the fill the transition table.
Here the base case is if the amount is 0, however many nums given only 1 way to make 0 (no way)
In 2D bottom up last col of each row contains the number of ways to make the 'row' amount.
Understand this transition for top down to bottom up.
---------------------------------------------------------------------------------------------------------------------------------------

In bottom up dp there is no rule involved. Its just that the values computed before can be used. In this problem for each row(amount) 
different coins are being used so the last col signifies the given amount using all the coins. Now for a new amount we need those ways.
Example for amount = 5 and 1, 2, 3
1
11 | 2
111, 21 | 12 | 3
1111, 211, 121, 31 | 112, 22 | 13
11111, 2111, 1211, 311, 1121, 221, 131 | 1112, 212, 122, 32 | 113, 23

In the above example if you notice. row no. 4 has 7 ways
Now
to create the 5th row what we do is concatenate the coins and see if it is less than the curr target

1111, 211, 121, 31 | 112, 22 | 13 ------>(using coin 1)------>11111, 2111, 1211, 311, 1121, 221, 131
111, 21 | 12 | 3------>(using coin 2)------>1112, 212, 122, 32
11 | 2------>(using coin 3)------>113, 23
---------------------------------------------------------------------------------------------------------------------------------------

In this problem we are typically looking for "Permutations" because the order of numbers inside any given arrangement matters, so as an example, 
given these 3 arrangements => [123, 132, 231] we are using same numbers but with diff. order!! and we still count them distinctly as 3 diff. ways! 
and this is the main difference(but not the only one) between a combination and a permutation, combinations on the other side would have considered 
all our 3 arrangements as only 1 way of arranging numbers because in combinations order doesn't matter while in permutations it does matter.

Coming back to this problem after this brief intro. if we want to re-use a permutation of a given sum that we already calculated before, it will 
be located in the last cell of that sum where we have used all our given items(remember a permutation is only valid if it consists of all numbers 
in the given input array) i.e. dp[i - coins[j - 1]][coins.length], where:
i - coins[j - 1]: will get us to the row of the previous sum we are looking for.
[coins.length]: last column where we exhausted all our given items and this cell have the permutations count for this sum.

I also want to clear something out, when using 2D space to solve this problem, it doesn't matter if you used rows to represent sum and columns 
for items or vice versa, both will work just fine if you make sure for each sum you consume all numbers before moving on to the next sum
*/

// Critical point 3: In bottom up 2D-DP solution how to understand dp[i][j] = dp[i][j] + dp[i - nums[j - 1]][nums.length] ?
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/702432/Java-or-1D-or-2D-or-Bottom-Up-or-Top-Down
/**
Why dp[i][j] = dp[i][j] + dp[i - nums[j - 1]][nums.length] ?
usually it suppose to be
dp[i][j] = dp[i][j] + dp[i - nums[j - 1]][j]

e.g
i = 4, j = 1 -> which means target = 4, use up to first one elements of nums
(1)if not use the first element in nums(first element is nums[0] = 1, not use 1)
dp[4][1] = dp[4][1 - 1] = 0
(2)if use the first element in nums(first element is nums[0] = 1, use 1)
if(4 >= nums[1 - 1]) -> true
use correct formula:
dp[i][j] = dp[i][j] + dp[i - nums[j - 1]][nums.length]
dp[4][1] = d[4][1] + dp[4 - nums[1 - 1]][3] = dp[4][1] + dp[3][3] = 4
use wrong formula what will happen ?
dp[i][j] = dp[i][j] + dp[i - nums[j - 1]][j]
dp[4][1] = d[4][1] + dp[4 - nums[1 - 1]][1] = dp[4][1] + dp[3][1] = 2

What's the difference ?
For correct answer dp[i - nums[j - 1]][nums.length] = dp[3][3] = 4
because we try to get target = 4 by only using first element as 1 but based on
previously calculated all permutations for target = 3 "by using all elements",
the critical part is "by using all elements", under this case, only last column 
value for target = 3 means "using all elements"

1
11 | 2
111, 21 | 12 | 3 -> target = 3 "using all elements" have 4 ways => this is the value on last column at row = 4 (dp[4][3])
now by using only first element as 1 to get target 4, it will be also 4 ways as below
3rd row all 4 ways plus first element as 1
111 + 1, 21 + 1, 12 + 1, 3 + 1
=> 1111, 211, 121, 31

Note: 
If we extend as "using only first two elements as 1 and 2 to get target 4", it will be 6 ways as below
3rd row all 4 ways plus first element as 1 have 4 ways (detail above)
2nd row all 2 ways plus second element as 2
11 + 2, 2 + 2
=> 112, 22

If we extend as "using all three elements as 1, 2 and 3 to get target 4", it will be 7 ways as below
3rd row all 4 ways plus first element as 1 have 4 ways (detail above)
2nd row all 2 ways plus second element as 2 (detail above)
1 + 3
=> 13

=================================================================
How about the wrong answer dp[i - nums[j - 1]][j] = dp[3][1] = 2 ?
that means by using only first element as 1 but based on previously calculated permutations
for target = 3 "by using only first element as 1", its not get target = 3 "by using all elements",
which means currently we have target = 2 "by using all elements" as 2 ways (dp[3][3]), based on
it to get target = 3 "by using only first element as 1", will be only 2 ways
11 | 2 -> target = 2 "using all elements" have 2 ways => this is the value on last column at row = 3 (dp[3][3])
now by using only first element as 1 to get target 3, it will be also 2 ways as below
11 + 1, 2 + 1
=> 111, 21
then based on these 2 ways to get target = 3, still "by using only first element as 1" to get target = 4
111 + 1, 21 + 1
=> 1111, 211
that's how 2 ways for target = 4 comes from
*/
class Solution {
    public int combinationSum4(int[] nums, int target) {
        int[][] dp = new int[target + 1][nums.length + 1];
        if (nums.length == 0) return 0;
        for (int i = 0; i <= nums.length; i++)
            dp[0][i] = 1;
        for (int i = 1; i <= target; i++) {
            for (int j = 1; j <= nums.length; j++) { // try to use each num
                // If we don't pick up current num
                dp[i][j] = dp[i][j - 1];
                // If we pick up current num
                if (i >= nums[j - 1])
                    dp[i][j] = dp[i][j] + dp[i - nums[j - 1]][nums.length];
            }
        }
        return dp[target][nums.length];
    }
}

// Solution 4: Bottom Up 1D-DP
// Refer to
// https://leetcode.com/problems/combination-sum-iv/discuss/702432/Java-or-1D-or-2D-or-Bottom-Up-or-Top-Down
// https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation
class Solution {
    public int combinationSum4(int[] nums, int target) {
        // dp[i] : ways to make i amount using all the nums
        int[] dp = new int[target + 1];
        // Then think about the base case. Since if the target is 0, 
        // there is only one way to get zero, which is using 0, we can set comb[0] = 1.
        // EDIT: The problem says that target is a positive integer that makes me feel 
        // it's unclear to put it in the above way. Since target == 0 only happens when 
        // in the previous call, target = nums[i], we know that this is the only 
        // combination in this case, so we return 1.
        dp[0] = 1;

        // Double counting
        // this replicates the below recursion
        // because for each target we tend to use the first num nums[0]
        // and ech num can be used repeatedly
        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j])
                    dp[i] = dp[i] + dp[i - nums[j]]; // ways are added for for each sum "i"
            }
        }

        return dp[target];
    }
}
