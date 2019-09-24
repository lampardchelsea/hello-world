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
            profit1 = profits[index] + helper(profits, weights, capacity - weights[index], index + 1)
        }
        // recursive call after excluding the element at the index
        int profit2 = helper(profits, weights, capacity, index + 1);
        return Math.max(profit1, profit2);
    }
}
