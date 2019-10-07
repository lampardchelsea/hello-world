/**
 This issue similar to leetcode
 Combination Sum IV
 This Combination Sum question a little different than I, II, III, since it only requires counting the numbers of result,
 not like I, II, III must use backtracking DFS to getting combination sets, so we can use DP (top down and bottom up) here
 Refer to
 https://leetcode.com/problems/combination-sum-iv/
 Given an integer array with all positive numbers and no duplicates, find the number of possible combinations 
 that add up to a positive integer target.

Example:

nums = [1, 2, 3]
target = 4

The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)

Note that different sequences are counted as different combinations.
Therefore the output is 7.
*/

// Solution 1: Native DFS (TLE)
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        return helper(nums, target);
    }
    
    private int helper(int[] nums, int target) {
        if(target == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(target >= nums[i]) {
                result += helper(nums, target - nums[i]);                
            }
        }
        return result;
    }
}

// Solution 2: 2D array top down DP (DFS + Memoization)
/**
 Since from given example we found sequence a and b are consider as different combination (but not permutation),
 we always able to go back to consider previous choice, e.g when you already loop to 2, you can still consider
 1 as choice, that's how we build [2,1,1], so each recursive time need to loop start at index = 0 to make sure
 this happen, here we add additional parameter as 'index' to declare this part, but since always need to start
 from index = 0, this parameter is not necessary, hence no need 2D array as memo as remove index dimemsion. 
 => Check Solution 3
 nums = [1, 2, 3]
 target = 4
 The possible combination ways are:
 (1, 1, 1, 1)
 (1, 1, 2) -> sequence a
 (1, 2, 1)
 (1, 3) 
 (2, 1, 1) -> sequence b
 (2, 2)
 (3, 1)
*/
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Integer[][] dp = new Integer[nums.length][1 + target];
        return helper(nums, target, 0, dp);
    }
    
    private int helper(int[] nums, int target, int index, Integer[][] dp) {
        if(dp[index][target] != null) {
            return dp[index][target];
        }
        if(target == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(target >= nums[i]) {
                result += helper(nums, target - nums[i], 0, dp);                
            }
        }
        dp[index][target] = result;
        return result;
    }
}

// Solution 3: 1D array top down DP (DFS + Memoization) without index as dimension
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        Integer[] dp = new Integer[1 + target];
        // Refer to
        // https://leetcode.com/problems/combination-sum-iv/discuss/85036/1ms-Java-DP-Solution-with-Detailed-Explanation/89680
        // Why dp[0] = 1 ? for{1,2,3},if target = 0,I think the answer is 0.
        // dp[0] is actually target = 1. Note that dp is initialized as
        // new Integer[target + 1].
        // Also: given an integer array with all positive numbers
        // add up to a positive integer target, so target won't be 0.
        dp[0] = 1;
        return helper(nums, target, dp);
    }
    
    private int helper(int[] nums, int target, Integer[] dp) {
        if(dp[target] != null) {
            return dp[target];
        }
        if(target == 0) {
            return 1;
        }
        int result = 0;
        for(int i = 0; i < nums.length; i++) {
            if(target >= nums[i]) {
                result += helper(nums, target - nums[i], dp);                
            }
        }
        dp[target] = result;
        return result;
    }
}

// Solution 4: 1D array Bottom-up Dynamic Programming
/**
 Why we cannot exchange inner / outer loop order for DP ?

 Refer to
 The difference of two java DP solution of Combination Sum IV
 https://leetcode.com/problems/combination-sum-iv/discuss/85063/The-difference-of-two-java-DP-solution-of-Combination-Sum-IV
 solution 1 : (correct one)

public class Solution {
    public int combinationSum4(int[] nums, int target) {
        // 在递归过程中，如果状态集是有限的，我们可以采用动态规划的方法，把每个状态的结果记录下来，以减少计算量。
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {        //  outer loop is  to  traversal  dp array 
            for (int j = 0; j < nums.length; j++) {    // inner loop is to  traversal  nums array  
                if (i - nums[j] >= 0) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }   
}

solution 2:(wrong one )
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        int [] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {        //  outer loop is  to  traversal  nums array and nums array should be ASC ordered
            for (int j = nums[i]; j <= target; j++) {   //  inner loop is to  traversal  dp array  
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}
when give a testcase:
nums = [1, 2, 3]
target = 4
solution 1 got the answer 7:
The possible combination ways are:
(1, 1, 1, 1)
(1, 1, 2)
(1, 2, 1)
(1, 3)
(2, 1, 1)
(2, 2)
(3, 1)
i wanna say this is Permutations not combination.
solution 2 give the answer 4, Obviously， delete the same combination from the above possible Permutations, 
eg (1, 1, 2) (1, 2, 1) (2, 1, 1) (1, 3) (3, 1), the answer is 4.
so, solution 1 could get the Permutation Sum, and solution 2 could get the Combination Sum. this is because 
the count order of dp and nums is different.
 
 Dynamic programming with Combination sum inner loop and outer loop interchangeable ?
 https://stackoverflow.com/questions/40225304/dynamic-programming-with-combination-sum-inner-loop-and-outer-loop-interchangeab
 Q:I am a little confuse about the dynamic programming solution for combination sum, that you are given 
 a list of numbers and a target total, and you want to count how many ways you can sum up to this target sum. 
 Numbers can be reused multiple times. I am confused about the inner loop and outer loop that whether they are 
 interchangeable or not. Can some explain the difference between the following two, and in what case we would 
 use one but not the other, or they are the same.

 int [] counts = new int[total];
 counts[0] = 1;
 // Version (1) 
 for(int i = 0; i <= total; i++) {
    for(int j = 0; j < nums.length; j++) {
        if(i >= nums[j])
           counts[i] += counts[i - nums[j]];
    }
 }
 // Version (2)
 for(int j = 0; j < nums.length; j++)
    for(int i = nums[j]; i <= total; i++) {
        counts[i] += counts[i - nums[j]];
    }
 }
 
 A:The two versions are indeed different, yielding different results.
 I'll use nums = {2, 3} for all examples below.

 Version 1 finds the number of combinations with ordering of elements from nums whose sum is total. 
 It does so by iterating through all "subtotals" and counting how many combinations have the right sum, 
 but it doesn't keep track of the elements. For example, the count for 5 will be 2. This is the result 
 of using the first element (with value 2) and finding 1 combination in nums[3] and another combination  
 for the second element (value 3) with the 1 combination in nums[2]. You should pay attention that both 
 combinations use a single 2 and a single 3, but they represent the 2 different ordered lists [2, 3] & [3, 2].
 In simple, Version 1 is used for computing permutation, order matters.

 Version 2 on the other hand find the number of combinations without ordering of elements from nums 
 whose sum is total. It does so by counting how many combinations have the right sum (fur each subtotal), 
 but contrary to version 1, it "uses" each element completely before moving on to the next element thus 
 avoiding different orderings of the same group. When counting subtotals with the first element (2), 
 all counts will initially be 0 (except the 0 sum sentinel), and any even subtotal will get the new 
 count of 1. When the next element used, it is as if it's coming after all 2's are already in the group, 
 so, contrary to version 1, only [2, 3] is counted, and not [3, 2].
 In simple, Version 2 is used for computing combination, order doesn't matter
 
 By the way, the order of elements in nums doesn't affect the results, as can be understood by the logic explained.
*/
// Correct solution for this issue is computing permutation, order matters, so use version 1
class Solution {
    public int combinationSum4(int[] nums, int target) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[1 + target];
        dp[0] = 1;
        for(int i = 0; i <= target; i++) {             //  outer loop is  to  traversal  dp array 
            for(int j = 0; j < nums.length; j++) {     // inner loop is to  traversal  nums array
                if(i >= nums[j]) {
                    dp[i] += dp[i - nums[j]];
                }
            }
        }
        return dp[target];
    }
}

// Wrong solution
public class Solution {
    public int combinationSum4(int[] nums, int target) {
        int [] dp = new int[target + 1];
        
        dp[0] = 1;
        for (int i = 0; i < nums.length; i++) {        //  outer loop is  to  traversal  nums array and nums array should be ASC ordered
            for (int j = nums[i]; j <= target; j++) {   //  inner loop is to  traversal  dp array  
                dp[j] += dp[j - nums[i]];
            }
        }
        return dp[target];
    }
}

// A more detail description of this problem: based on 518. Coin Changes 2
// Refer to 
// https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake%3A-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln
// https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake:-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln/306232
/**
 Example of code giving wrong answer for outer loop iterating over amount and inner loop iterating over coins:
class Solution {
    public int change(int amount, int[] coins) {
        if(amount<=0)
            return 0;
        int ways[] = new int[amount+1];
        for(int i =0; i<amount+1; i++){
            for(int coin: coins){
                if(coin+i < amount+1 ){
                    ways[i+coin] = ways[i]+ways[i+coin];
                }
                if(i==0){
                    ways[i]=1;
                }
            }
        }
        for(int i=0; i< ways.length; i++){
            System.out.print(i+":"+ways[i]+" ");
        }
        return ways[amount];
    }
}
Correct Solution:
class Solution {
    public int change(int amount, int[] coins) {
        int [] combi = new int[amount+1];
        combi[0] = 1;
        for(int i = 0; i < coins.length; i++){
            for(int j=1; j< amount+1; j++){
                if(j-coins[i]>=0)
                    combi[j] = combi[j]+combi[j-coins[i]];
            }
        }
        for(int a: combi)
            System.out.print(a+" ");
        return combi[amount];
    }
}

The two codes on superificial comparison look equal, but the Code 1 gives a higher number of solutions that the correct answer.
The reason for this is when we create an amount array from 0...Amount, if we iterate over all the coins a solution
can be added twice. For example to create 7:

When amount is 2 and the coin value is 5, it would be counted as 1 way.
When amount is 5 and the coin value is 2, the number of ways become 2.
The set is either case remains 1 coin of 2 and 1 coin of 5. But the first method adds it twice.
So we create use an outer loop of coins so that a combination once used cannot be used again.

================================================================================================================

Well, the real reason why the answer changes because of loops is because of the change in dp definition 
when you try to reduce the space complexity.If we define dp[i][j] as "number of ways to get sum 'j' using 
'first i' coins", then the answer doesn't change because of loop arrangement.

So why does the answer change only when we try to reduce the space complexity?

To get the correct answer, the correct dp definition should be dp[i][j]="number of ways to get sum 'j' using 
'first i' coins". Now when we try to traverse the 2-d array row-wise by keeping only previous row array(to 
reduce space complexity), we preserve the above dp definition as dp[j]="number of ways to get sum 'j' using 
'previous /first i coins' " but when we try to traverse the 2-d array column-wise by keeping only the 
previous column, the meaning of dp array changes to dp[j]="number of ways to get sum 'j' using 'all' coins".

In the below code you can see that if we are not interested in reducing the space complexity, both the loop 
arrangements yield the same answer.

Code:-
class Solution {
    public int change(int amount, int[] coins) {
        if (coins.length == 0) {
            if (amount == 0) return 1;
            return 0;
        }
        int dp[][] = new int[coins.length][amount + 1];
        //Initializing first column
        for (int i = 0; i < coins.length; i++) {
            dp[i][0] = 1;
        }
        //Initializing first row.
        for (int i = 1; i <= amount; i++) {
            if (i % coins[0] == 0) {
                dp[0][i] = 1;
            } else {
                dp[0][i] = 0;
            }
        }
        //Check the difference between interchanging the for loops.
        //Both of them work. You can comment it out and check.
        //1st one
        for (int i = 1; i < coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                if (j >= coins[i]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        //2nd one
        for (int j = 1; j <= amount; j++) {
            for (int i = 1; i < coins.length; i++) {
                if (j >= coins[i]) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - coins[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[coins.length - 1][amount];
    }
}
*/
