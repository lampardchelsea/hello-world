/**
 Refer to
 https://leetcode.com/problems/coin-change-2/
 You are given coins of different denominations and a total amount of money. Write a function to 
 compute the number of combinations that make up that amount. You may assume that you have infinite 
 number of each kind of coin.

Example 1:
Input: amount = 5, coins = [1, 2, 5]
Output: 4
Explanation: there are four ways to make up the amount:
5=5
5=2+2+1
5=2+1+1+1
5=1+1+1+1+1

Example 2:
Input: amount = 3, coins = [2]
Output: 0
Explanation: the amount of 3 cannot be made up just with coins of 2.

Example 3:
Input: amount = 10, coins = [10] 
Output: 1

Note:
You can assume that

0 <= amount <= 5000
1 <= coin <= 5000
the number of coins is less than 500
the answer is guaranteed to fit into signed 32-bit integer
*/

// Solution 1: Native DFS (TLE)
class Solution {
    public int change(int amount, int[] coins) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        helper(result, new ArrayList<Integer>(), amount, coins, 0);
        return result.size();
    }
    
    private void helper(List<List<Integer>> result, List<Integer> list, int amount, int[] coins, int index) {
        if(amount == 0) {
            result.add(new ArrayList<Integer>(list));
        }
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                list.add(coins[i]);
                helper(result, list, amount - coins[i], coins, i);
                list.remove(list.size() - 1);
            }    
        }
    }
}

// Solution 2: Native DFS without result.size()
class Solution {
    public int change(int amount, int[] coins) {
        return helper(amount, coins, 0);
    }
    
    private int helper(int amount, int[] coins, int index) {
        if(amount == 0) {
            return 1;
        }
        int result = 0;
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                result += helper(amount - coins[i], coins, i);
            }    
        }
        return result;
    }
}

// Solution 3: Top down DFS + Memoization
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/162839/Another-approach-%3A-memorized-recursion-Java
class Solution {
    public int change(int amount, int[] coins) {
        // Test out by amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        // Test out by amount = 7, coins = []
        if(coins.length == 0) {
            return 0;
        }
        Integer[][] memo = new Integer[coins.length][1 + amount];
        return helper(amount, coins, 0, memo);
    }
    
    private int helper(int amount, int[] coins, int index, Integer[][] memo) {
        if(amount == 0) {
            return 1;
        }
        if(memo[index][amount] != null) {
            return memo[index][amount];
        }
        int result = 0;
        for(int i = index; i < coins.length; i++) {
            if(amount >= coins[i]) {
                result += helper(amount - coins[i], coins, i, memo);
            }    
        }
        memo[index][amount] = result;
        return result;
    }
}

// Solution 4: 2D array Bottom up DP
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake%3A-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln
// Be careful how we initialize the 1st row for Style 1
// Style 1:int[][] dp = new int[coins.length][1 + amount];
class Solution {
    public int change(int amount, int[] coins) {
        // Test out by amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        // Test out by amount = 7, coins = []
        if(coins.length == 0) {
            return 0;
        }
        int[][] dp = new int[coins.length][1 + amount];
        for(int i = 0; i < coins.length; i++) {
            dp[i][0] = 1;
        }
        // Initialize the 1st row with tricky, since infinite coins supplied, 
        // not require 'i == coins[0]', just 'i % coins[0] == 0' is fine
        for(int i = 0; i <= amount; i++) {
            dp[0][i] = (i % coins[0] == 0 ? 1 : 0);
        }
        for(int i = 1; i < coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j >= coins[i]) {
                    dp[i][j] += dp[i][j - coins[i]];
                }
            }
        }
        return dp[coins.length - 1][amount];
    }
}

// Style 2: int[][] dp = new int[1 + coins.length][1 + amount];
class Solution {
    public int change(int amount, int[] coins) {
        // Test out by amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        // Test out by amount = 7, coins = []
        if(coins.length == 0) {
            return 0;
        }
        int[][] dp = new int[1 + coins.length][1 + amount];
        for(int i = 0; i <= coins.length; i++) {
            dp[i][0] = 1;
        }
        for(int i = 1; i <= coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i - 1][j];
                if(j >= coins[i - 1]) {
                    dp[i][j] += dp[i][j - coins[i - 1]];
                }
            }
        }
        return dp[coins.length][amount];
    }
}

// A more detail description of this problem: based on 518. Coin Changes 2
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake%3A-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln
// https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake:-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln/306232
// https://leetcode.com/problems/coin-change-2/discuss/141076/Logical-Thinking-with-Clear-Java-Code
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

// Solution 5: 1D array Bottom up DP
// Refer to
// https://leetcode.com/problems/coin-change-2/discuss/99212/Knapsack-problem-Java-solution-with-thinking-process-O(nm)-Time-and-O(m)-Space
class Solution {
    public int change(int amount, int[] coins) {
        // Test out by amount = 0, coins = []
        if(amount == 0) {
            return 1;
        }
        // Test out by amount = 7, coins = []
        if(coins.length == 0) {
            return 0;
        }
        int[] dp = new int[1 + amount];
        dp[0] = 1;
        for(int i = 1; i <= coins.length; i++) {
            for(int j = 1; j <= amount; j++) {
                if(j >= coins[i - 1]) {
                    dp[j] += dp[j - coins[i - 1]];
                }
            }
        }
        return dp[amount];
    }
}
