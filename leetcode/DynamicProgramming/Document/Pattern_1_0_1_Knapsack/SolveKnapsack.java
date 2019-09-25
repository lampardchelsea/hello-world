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
/**
 The time complexity of the above algorithm is exponential O(2^n), where ‘n’ represents the total 
 number of items. This can also be confirmed from the above recursion tree. As we can see that we 
 will have a total of ‘31’ recursive calls – calculated through (2^n) + (2^n) - 1, which is 
 asymptotically equivalent to O(2^n).
 The space complexity is O(n). This space will be used to store the recursion stack. Since our 
 recursive algorithm works in a depth-first fashion, which means, we can’t have more than ‘n’ 
 recursive calls on the call stack at any time.
*/
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

// Solution 2: Top-down Dynamic Programming with Memoization
/**
 What is the time and space complexity of the above solution? 
 Since our memoization array dp[profits.length][capacity+1] stores the results for 
 all the subproblems, we can conclude that we will not have more than N*C subproblems 
 (where ‘N’ is the number of items and ‘C’ is the knapsack capacity). This means 
 that our time complexity will be O(N*C).
 The above algorithm will be using O(N*C) space for the memoization array. Other than 
 that we will use O(N) space for the recursion call-stack. So the total space 
 complexity will be O(N*C + N), which is asymptotically equivalent to O(N*C)
*/
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

// Solution 3: Bottom-up Dynamic Programming
/**
 The above solution has time and space complexity of O(N*C), where ‘N’ 
 represents total items and ‘C’ is the maximum capacity.
*/
class Solution {
    public int solveKnapsack(int[] profits, int[] weights, int capacity) {
        // basic checks
        if(capacity <= 0 || profits.length == 0 || weights.length != profits.length) {
            return 0;   
        }
        // Essentially, we want to find the maximum profit for every 
        // sub-array and for every possible capacity. 
        // dp[i][c] will represent the maximum knapsack profit for 
        // capacity 'c' calculated from the first 'i' items.
        int[][] dp = new int[weights.length][1 + capacity];
        // populate the capacity = 0 columns, with '0' capacity we have '0' profit
        for(int i = 0; i < weights.length; i++) {
            dp[i][0] = 0;
        }
        // if we have only one weight, we will take it if it is not more than the capacity
        for(int i = 0; i <= capacity; i++) {
            if(weights[0] <= i) {
                dp[0][i] = profits[0];
            }
        }
        // process all sub-arrays for all the capacities
        // So, for each item at index ‘i’ (0 <= i < items.length) and capacity ‘c’ 
        // (0 <= c <= capacity), we have two options:
        // (1)Exclude the item at index ‘i’. In this case, we will take whatever 
        //    profit we get from the sub-array excluding this item => dp[i-1][c]
        // (2)Include the item at index ‘i’ if its weight is not more than the 
        //    capacity. In this case, we include its profit plus whatever profit 
        //    we get from the remaining capacity and from remaining 
        //    items => profit[i] + dp[i-1][c-weight[i]]
        for(int i = 1; i < weights.length; i++) {
            for(int j = 1; j <= capacity; j++) {
                int profits1 = 0;
                int profits2 = 0;
                // include the item, if it is not more than the capacity
                if(weights[i] <= j) {
                    profits1 = dp[i - 1][j - weights[i]] + profits[i];
                }
                // exclude the item
                profits2 = dp[i - 1][j];
                // take maximum
                dp[i][j] = Math.max(profits1, profits2);
            }
        }
        // maximum profit will be at the bottom-right corner.
        return dp[weights.length - 1][capacity];
    }
}

// Solution 4: O(C) space complexity Bottom-up Dynamic Programming
/**
 The above solution is similar to the previous solution, the only difference is that we use i%2 
 instead if i and (i-1)%2 instead if i-1. This solution has a space complexity of O(2*C) = O(C), 
 where ‘C’ is the maximum capacity of the knapsack.
*/
class Solution {
    public int solveKnapsack(int[] profits, int[] weights, int capacity) {
        // basic checks
        if(capacity <= 0 || profits.length == 0 || weights.length != profits.length) {
            return 0;   
        }
        // we only need one previous row to find the optimal solution, 
        // overall we need '2' rows, solution 4 is similar to the 
        // previous solution, the only difference is that we use 'i%2' 
        // instead if 'i' and '(i-1)%2' instead if 'i-1'
        int[][] dp = new int[2][1 + capacity];
        // if we have only one weight, we will take it if it is not more than the capacity
        for(int i = 0; i <= capacity; i++) {
            if(weights[0] <= i) {
                dp[0][i] = profits[0];
                dp[1][i] = profits[0];
            }
        }
        // process all sub-arrays for all the capacities
        for(int i = 1; i < weights.length; i++) {
            for(int j = 1; j <= capacity; j++) {
                int profits1 = 0;
                int profits2 = 0;
                // include the item, if it is not more than the capacity
                if(weights[i] <= j) {
                    profits1 = dp[(i - 1) % 2][j - weights[i]] + profits[i];
                }
                // exclude the item
                profits2 = dp[(i - 1) % 2][j];
                // take maximum
                dp[i % 2][j] = Math.max(profits1, profits2);
            }
        }
        // maximum profit will be at the bottom-right corner.
        return dp[(weights.length - 1) % 2][capacity];
    }
}

// Solution 5: Single array Bottom-up Dynamic Programming
/**
 This space optimization solution can also be implemented using a single array. 
 It is a bit tricky though, but the intuition is to use the same array for the 
 previous and the next iteration!
 If you see closely, we need two values from the previous iteration: dp[c] and 
 dp[c-weight[i]]
 Since our inner loop is iterating over c:0-->capacity, let’s see how this might 
 affect our two required values:
 1.When we access dp[c], it has not been overridden yet for the current iteration, 
   so it should be fine.
 2.dp[c-weight[i]] might be overridden if "weight[i] > 0". Therefore we can't use 
   this value for the current iteration.
 To solve the second case, we can change our inner loop to process in the reverse 
 direction: c:capacity-->0. This will ensure that whenever we change a value in dp[], 
 we will not need it anymore in the current iteration.
*/
class Solution {
    public int solveKnapsack(int[] profits, int[] weights, int capacity) {
        // basic checks
        if(capacity <= 0 || profits.length == 0 || weights.length != profits.length) {
            return 0;   
        }
        int[] dp = new int[1 + capacity];
        // if we have only one weight, we will take it if it is not more than the capacity
        for(int i = 0; i <= capacity; i++) {
            if(weights[0] <= i) {
                dp[i] = profits[0];
            }
        }
        // process all sub-arrays for all the capacities
        for(int i = 1; i < weights.length; i++) {
            for(int j = capacity; j >= 0; j--) {
                int profits1 = 0;
                int profits2 = 0;
                // include the item, if it is not more than the capacity
                // To solve the second case, we can change our inner loop to process 
                // in the reverse direction: c:capacity-->0. This will ensure that 
                // whenever we change a value in dp[], we will not need it anymore 
                // in the current iteration.
                if(weights[i] <= j) {
                    profits1 = dp[j - weights[i]] + profits[i];
                }
                // exclude the item
                profits2 = dp[j];
                // take maximum
                dp[j] = Math.max(profits1, profits2);
            }
        }
        // maximum profit will be at the bottom-right corner.
        return dp[capacity];
    }
}

/**
 To explain why we did below on Solution 4, we add 1 print line as below:
     public int solveKnapsack(int[] profits, int[] weights, int capacity) {
        // basic checks
        if(capacity <= 0 || profits.length == 0 || weights.length != profits.length) {
            return 0;   
        }
        int[] dp = new int[1 + capacity];
        // if we have only one weight, we will take it if it is not more than the capacity
        for(int i = 0; i <= capacity; i++) {
            if(weights[0] <= i) {
                dp[i] = profits[0];
            }
        }
        // process all sub-arrays for all the capacities
        for(int i = 1; i < weights.length; i++) {
            //for(int j = capacity; j >= 0; j--) {
         // test as move forward as wrong solution
        	for(int j = 0; j <= capacity; j++) {
                int profits1 = 0;
                int profits2 = 0;
                // include the item, if it is not more than the capacity
                // To solve the second case, we can change our inner loop to process 
                // in the reverse direction: c:capacity-->0. This will ensure that 
                // whenever we change a value in dp[], we will not need it anymore 
                // in the current iteration.
                if(weights[i] <= j) {
                    profits1 = dp[j - weights[i]] + profits[i];
                }
                // exclude the item
                profits2 = dp[j];
                // take maximum
                dp[j] = Math.max(profits1, profits2);
                // Add print line to clearify why we loop backwards
                System.out.println("i=" + i + " j=" + j + " -> dp=" + Arrays.toString(dp));
            }           
        }
        // maximum profit will be at the bottom-right corner.
        return dp[capacity];
    }
    
    If we setup capacity as 6 and other condition no change, we observe below output
    Correct as loop backwards
    i=1 j=6 -> dp=[0, 1, 1, 1, 1, 1, 7]
    i=1 j=5 -> dp=[0, 1, 1, 1, 1, 7, 7]
    i=1 j=4 -> dp=[0, 1, 1, 1, 7, 7, 7]
    i=1 j=3 -> dp=[0, 1, 1, 7, 7, 7, 7]
    i=1 j=2 -> dp=[0, 1, 6, 7, 7, 7, 7]
    i=1 j=1 -> dp=[0, 1, 6, 7, 7, 7, 7]
    i=1 j=0 -> dp=[0, 1, 6, 7, 7, 7, 7]
    i=2 j=6 -> dp=[0, 1, 6, 7, 7, 7, 17]
    i=2 j=5 -> dp=[0, 1, 6, 7, 7, 16, 17]
    i=2 j=4 -> dp=[0, 1, 6, 7, 11, 16, 17]
    i=2 j=3 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=2 j=2 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=2 j=1 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=2 j=0 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=6 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=5 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=4 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=3 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=2 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=1 -> dp=[0, 1, 6, 10, 11, 16, 17]
    i=3 j=0 -> dp=[0, 1, 6, 10, 11, 16, 17]
    17

    Wrong as loop forward
    i=1 j=0 -> dp=[0, 1, 1, 1, 1, 1, 1]
    i=1 j=1 -> dp=[0, 1, 1, 1, 1, 1, 1]
    i=1 j=2 -> dp=[0, 1, 6, 1, 1, 1, 1]
    i=1 j=3 -> dp=[0, 1, 6, 7, 1, 1, 1]
    i=1 j=4 -> dp=[0, 1, 6, 7, 12, 1, 1]  => i=1,j=4 state depends on i=1,j=2 state, but i=1,j=2 state changed from 1 to 6
    i=1 j=5 -> dp=[0, 1, 6, 7, 12, 13, 1]
    i=1 j=6 -> dp=[0, 1, 6, 7, 12, 13, 18]
    i=2 j=0 -> dp=[0, 1, 6, 7, 12, 13, 18]
    i=2 j=1 -> dp=[0, 1, 6, 7, 12, 13, 18]
    i=2 j=2 -> dp=[0, 1, 6, 7, 12, 13, 18]
    i=2 j=3 -> dp=[0, 1, 6, 10, 12, 13, 18]
    i=2 j=4 -> dp=[0, 1, 6, 10, 12, 13, 18]
    i=2 j=5 -> dp=[0, 1, 6, 10, 12, 16, 18]
    i=2 j=6 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=0 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=1 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=2 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=3 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=4 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=5 -> dp=[0, 1, 6, 10, 12, 16, 20]
    i=3 j=6 -> dp=[0, 1, 6, 10, 12, 16, 20]
    20
    
    The wrong solution is error out when i = 1, j = 4, in the code logic,
    profits1 = 6 + 6 = 12 which not possible, since capacity = 4 only allow
    combination of profits[1] + profits[3] = 1 + 10 = 11 as highest profit.
    if(weights[i] <= j) {
        profits1 = dp[j - weights[i]] + profits[i];
    }
    here dp[j - weights[i]] is dp[4 - weights[1]] = dp[2] = 6, however is
    this dp[2] = 6 correct ? Actually if checking back one more step, dp[2]
    updated from 1 to 6 when i = 1, j = 2, in simple, its state changed and
    following state (e.g i = 1, j = 4) will depends on the changed state.
    Compare to correct solution, when encounter i = 1, j = 4, it will looking
    for i = 1, j = 2, and its state keep as 1 with no changed when we loop
    backwards(compare with i = 1, j = 5), since dp[j] dpends on dp[j - weights[i]], 
    dp[j - weights[i]] no change is important.
    So if dp[i] depends on dp[i - j], which means previous state, dp[i - j] should
    keep as no change and loop backwards.
*/
