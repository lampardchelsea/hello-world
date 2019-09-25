/**
 Given two integer arrays to represent weights and profits of ‘N’ items, 
 we need to find a subset of these items which will give us maximum profit 
 such that their cumulative weight is not more than a given number ‘C’. 
 Each item can only be selected once, which means either we put an item 
 in the knapsack or we skip it.
 
 Items:  A   B   C   D
 profit: 1   6   10  16
 weight: 1   2   3   5
 capacity: 7
*/

// Solution 1: Native DFS
class Solution {
    public int solveKnapsack(int[] profits, int[] weights, int capacity) {
        return helper(profits, weights, capacity, 0);
    }
    
    private int helper(int[] profits, int[] weights, int capacity, int index) {
        // Base case
        if(capacity <= 0 || index >= profits.length) {
            return 0;
        }
        // recursive call after choosing the element at the index
        // if the weight of the element at index exceeds the capacity, we shouldn't process this
        int profit1 = 0;
        if(weights[index] <= capacity) {
            profit1 = profits[index] + helper(profits, weights, capacity - weights[index], index + 1);
        }
        // recursive call after excluding the element at the index
        int profit2 = helper(profits, weights, capacity, index + 1);
        return Math.max(profit1, profit2);
    }
}


class Solution {
    public boolean canPartition(int[] nums) {
        
    }
}


/**
 Given two integer arrays to represent weights and profits of ‘N’ items, 
 we need to find a subset of these items which will give us maximum profit 
 such that their cumulative weight is not more than a given number ‘C’. 
 Each item can only be selected once, which means either we put an item 
 in the knapsack or we skip it.
 
 Items:  A   B   C   D
 profit: 1   6   10  16
 weight: 1   2   3   5
 capacity: 7
*/

// Solution 2: Top-down Dynamic Programming with Memoization
class Solution {
    public int solveKnapsack(int[] profits, int[] weights, int capacity) {
        // Since we have two changing values (capacity and index) in our 
        // recursive function helper(), we can use a two-dimensional array 
        // to store the results of all the solved sub-problems. As mentioned 
        // above, we need to store results for every sub-array (i.e. for 
        // every possible index 'i') and for every possible capacity 'c'.
        // dp[i][c] means maximum profit getting at index i (range between 0
        // to i at most in weights) and capacity c
        Integer[][] dp = new Integer[1 + weights.length][1 + capacity];        
        return helper(profits, weights, capacity, 0, dp);
    }
    
    private int helper(int[] profits, int[] weights, int capacity, int index, Integer[][] dp) {
        if(capacity <= 0 || index >= profits.length) {
            return 0;
        }
        // if we have already solved a similar problem, return the result from memory
        if(dp[index][capacity] != null) {
            return dp[index][capacity];
        }
        int profit1 = 0;
        if(weights[index] <= capacity) {
            profit1 = profits[index] + helper(profits, weights, capacity - weights[index], index + 1, dp);
        }
        int profit2 = helper(profits, weights, capacity, index + 1, dp);
        int result = Math.max(profit1, profit2);
        dp[index][capacity] = result;
        return result;
    }
}
