https://leetcode.com/problems/stone-game-vii/description/
Alice and Bob take turns playing a game, with Alice starting first.
There are n stones arranged in a row. On each player's turn, they can remove either the leftmost stone or the rightmost stone from the row and receive points equal to the sum of the remaining stones' values in the row. The winner is the one with the higher score when there are no stones left to remove.
Bob found that he will always lose this game (poor Bob, he always loses), so he decided to minimize the score's difference. Alice's goal is to maximize the difference in the score.
Given an array of integers stones where stones[i] represents the value of the ith stone from the left, return the difference in Alice and Bob's score if they both play optimally.
 
Example 1:
Input: stones = [5,3,1,4,2]
Output: 6
Explanation: 
- Alice removes 2 and gets 5 + 3 + 1 + 4 = 13 points. Alice = 13, Bob = 0, stones = [5,3,1,4].
- Bob removes 5 and gets 3 + 1 + 4 = 8 points. Alice = 13, Bob = 8, stones = [3,1,4].
- Alice removes 3 and gets 1 + 4 = 5 points. Alice = 18, Bob = 8, stones = [1,4].
- Bob removes 1 and gets 4 points. Alice = 18, Bob = 12, stones = [4].
- Alice removes 4 and gets 0 points. Alice = 18, Bob = 12, stones = [].
The score difference is 18 - 12 = 6.

Example 2:
Input: stones = [7,90,5,1,100,10,10,2]
Output: 122
 
Constraints:
- n == stones.length
- 2 <= n <= 1000
- 1 <= stones[i] <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2025-12-22
Solution 1: Native DFS (10 min, TLE 9/68)
Style 1: Explicit set parameters as 'aliceScore' and 'bobScore'
class Solution {
    public int stoneGameVII(int[] stones) {
        return helper(stones, 0, stones.length - 1, 0, 0, true);
    }

    private int helper(int[] stones, int left, int right, int aliceScore, int bobScore, boolean isAliceTurn) {
        if(left > right) {
            return aliceScore - bobScore;
        }
        int curSum = 0;
        for(int i = left; i <= right; i++) {
            curSum += stones[i];
        }
        // Option 1: Remove left stone
        int removeLeft = curSum - stones[left];
        // Option 2: Remove right stone
        int removeRight = curSum - stones[right];
        // Alice's turn: she tries to maximize (alice - bob)
        if(isAliceTurn) {
            int resultRemoveLeft = helper(stones, left + 1, right, aliceScore + removeLeft, bobScore, false);
            int resultRemoveRight = helper(stones, left, right - 1, aliceScore + removeRight, bobScore, false);
            // Alice chooses the move that gives her maximum advantage
            return Math.max(resultRemoveLeft, resultRemoveRight);
        // Bob's turn: he tries to minimize (alice - bob)
        } else {
            int resultRemoveLeft = helper(stones, left + 1, right, aliceScore, bobScore + removeLeft, true);
            int resultRemoveRight = helper(stones, left, right - 1, aliceScore, bobScore + removeRight, true);
            // Bob chooses the move that minimizes Alice's advantage
            return Math.min(resultRemoveLeft, resultRemoveRight);            
        }
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Style 2: Explicit set parameters as 'aliceScore' and 'bobScore' + Prefix Sum
class Solution {
    public int stoneGameVII(int[] stones) {
        int[] presum = new int[stones.length + 1];
        // presum[0] = 0, presum[1] = stones[0],
        // presum[2] = stones[0] + stones[1]...
        // sum[i...j] = presum[j + 1] - presum[i]
        for(int i = 1; i <= stones.length; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        return helper(presum, stones, 0, stones.length - 1, 0, 0, true);
    }

    private int helper(int[] presum, int[] stones, int left, int right, int aliceScore, int bobScore, boolean isAliceTurn) {
        if(left > right) {
            return aliceScore - bobScore;
        }
        // Get sum in O(1) using prefix sum array
        int curSum = presum[right + 1] - presum[left];
        // Option 1: Remove left stone
        int removeLeft = curSum - stones[left];
        // Option 2: Remove right stone
        int removeRight = curSum - stones[right];
        // Alice's turn: she tries to maximize (alice - bob)
        if(isAliceTurn) {
            int resultRemoveLeft = helper(presum, stones, left + 1, right, aliceScore + removeLeft, bobScore, false);
            int resultRemoveRight = helper(presum, stones, left, right - 1, aliceScore + removeRight, bobScore, false);
            // Alice chooses the move that gives her maximum advantage
            return Math.max(resultRemoveLeft, resultRemoveRight);
        // Bob's turn: he tries to minimize (alice - bob)
        } else {
            int resultRemoveLeft = helper(presum, stones, left + 1, right, aliceScore, bobScore + removeLeft, true);
            int resultRemoveRight = helper(presum, stones, left, right - 1, aliceScore, bobScore + removeRight, true);
            // Bob chooses the move that minimizes Alice's advantage
            return Math.min(resultRemoveLeft, resultRemoveRight);            
        }
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Style 3: No parameters as 'aliceScore' and 'bobScore' + Prefix Sum + Switch person flag
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        // presum[0] = 0, presum[1] = stones[0],
        // presum[2] = stones[0] + stones[1]...
        // sum[i...j] = presum[j + 1] - presum[i]
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        return helper(presum, stones, 0, n - 1, 0);
    }

    private int helper(int[] presum, int[] stones, int left, int right, int isAliceTurn) {
        if(left > right) {
            return 0;
        }
        // Get sum in O(1) using prefix sum array
        int curSum = presum[right + 1] - presum[left];
        // Option 1: Remove left stone
        int removeLeft = curSum - stones[left];
        // Option 2: Remove right stone
        int removeRight = curSum - stones[right];
        // Alice's turn: she tries to maximize (alice - bob)
        if(isAliceTurn == 0) {
            // e.g For Alice, if we remove left stone (stones[i]), we get (curSum - stones[i]) 
            // points now, then opponent (Bob) gets dfs(i + 1 ... j) advantage on remaining stones
            int resultRemoveLeft = removeLeft - helper(presum, stones, left + 1, right, 1);
            int resultRemoveRight = removeRight - helper(presum, stones, left, right - 1, 1);
            // Alice chooses the move that gives her maximum advantage
            return Math.max(resultRemoveLeft, resultRemoveRight);
        // Bob's turn: he tries to minimize (alice - bob)
        } else {
            int resultRemoveLeft = removeLeft - helper(presum, stones, left + 1, right, 0);
            int resultRemoveRight = removeRight - helper(presum, stones, left, right - 1, 0);
            // Actually Bob tries to maximize his advantage = minimize Alice's
            // But in our formulation, positive = advantage for current player
            // So when Bob plays, he also wants to maximize "advantage for current player"
            // Below 'Math.min' is wrong, has to be 'Math.max'
            //memo[left][right][isAliceTurn] = Math.min(resultRemoveLeft, resultRemoveRight);
            return Math.max(resultRemoveLeft, resultRemoveRight);
        }
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Style 4: No parameters as 'aliceScore' and 'bobScore' + Prefix Sum + No Switch person flag



Solution 2: DFS + Memoization (60 min)
Style 1: 3D memo (No parameters as 'aliceScore' and 'bobScore' + Prefix Sum + Switch person flag)
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        // presum[0] = 0, presum[1] = stones[0],
        // presum[2] = stones[0] + stones[1]...
        // sum[i...j] = presum[j + 1] - presum[i]
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        // memo[left][right][turn] where turn: 0=Alice, 1=Bob
        Integer[][][] memo = new Integer[n][n][2];
        return helper(presum, stones, 0, n - 1, 0, memo);
    }

    private int helper(int[] presum, int[] stones, int left, int right, int isAliceTurn, Integer[][][] memo) {
        if(left > right) {
            return 0;
        }
        if(memo[left][right][isAliceTurn] != null) {
            return memo[left][right][isAliceTurn];
        }
        // Get sum in O(1) using prefix sum array
        int curSum = presum[right + 1] - presum[left];
        // Option 1: Remove left stone
        int removeLeft = curSum - stones[left];
        // Option 2: Remove right stone
        int removeRight = curSum - stones[right];
        // Alice's turn: she tries to maximize (alice - bob)
        if(isAliceTurn == 0) {
            // e.g For Alice, if we remove left stone (stones[i]), we get (curSum - stones[i]) 
            // points now, then opponent (Bob) gets dfs(i + 1 ... j) advantage on remaining stones
            int resultRemoveLeft = removeLeft - helper(presum, stones, left + 1, right, 1, memo);
            int resultRemoveRight = removeRight - helper(presum, stones, left, right - 1, 1, memo);
            // Alice chooses the move that gives her maximum advantage
            memo[left][right][isAliceTurn] = Math.max(resultRemoveLeft, resultRemoveRight);
        // Bob's turn: he tries to minimize (alice - bob)
        } else {
            int resultRemoveLeft = removeLeft - helper(presum, stones, left + 1, right, 0, memo);
            int resultRemoveRight = removeRight - helper(presum, stones, left, right - 1, 0, memo);
            // Actually Bob tries to maximize his advantage = minimize Alice's
            // But in our formulation, positive = advantage for current player
            // So when Bob plays, he also wants to maximize "advantage for current player"
            // Below 'Math.min' is wrong, has to be 'Math.max'
            //memo[left][right][isAliceTurn] = Math.min(resultRemoveLeft, resultRemoveRight);
            memo[left][right][isAliceTurn] = Math.max(resultRemoveLeft, resultRemoveRight);
        }
        return memo[left][right][isAliceTurn];
    }
}

Time Complexity: O(n^2 * 2)
Space Complexity: O(n^2 * 2)

Style 2: 2D memo (No parameters as 'aliceScore' and 'bobScore' + Prefix Sum + No Switch person flag)


Solution 3: DP (180 min)
Style 1: 3D DP (No parameters as 'aliceScore' and 'bobScore' + Prefix Sum + Switch person flag)
When converting this problem from DFS to 3D DP, we cannot directly copying 'left' and 'right' logic because in Native DFS where 'left' goes from 0 to value as 'right' (which is ascending) and 'right' goes from n - 1 to 'left' (which is descending), so in the 3D DP bottom up solution the exactly mapping logic should be 'left' descending from 'right' to 0 and 'right' ascending from 'left' to n - 1. But the difficulty arouse is both 'left' and 'right' are dynamic variable, themselves cannot be used as start or end index in the for loop. We have to replace 'right' with a 'length' variable which recording the existing subarray length between 'left' and 'right', then we overcome the difficulty. And based on the DFS logic, the length 'top' is n, 'bottom' is 1, and in 3D DP solution we have to accumulate status from bottom to top which is loop on length from 1 to n, and when length is 0 or 1, it is a bit special considered as base case.
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        // dp[left][right][turn] where turn = 0 for Alice, 1 for Bob
        int[][][] dp = new int[n][n][2];
        // Fill DP table by interval length (from smaller to larger intervals)
        for (int length = 1; length <= n; length++) {
            for (int left = 0; left <= n - length; left++) {
                int right = left + length - 1;
                // Get current sum of stones from left to right
                int curSum = presum[right + 1] - presum[left];
                int removeLeft = curSum - stones[left];
                int removeRight = curSum - stones[right];
                if (length == 1) {
                    // Base case: single stone, no gain from removing it
                    dp[left][right][0] = 0;
                    dp[left][right][1] = 0;
                } else {
                    // Alice's turn
                    int aliceLeft = removeLeft - dp[left + 1][right][1];
                    int aliceRight = removeRight - dp[left][right - 1][1];
                    dp[left][right][0] = Math.max(aliceLeft, aliceRight);
                    // Bob's turn
                    int bobLeft = removeLeft - dp[left + 1][right][0];
                    int bobRight = removeRight - dp[left][right - 1][0];
                    dp[left][right][1] = Math.max(bobLeft, bobRight);
                }
            }
        }
        return dp[0][n - 1][0];
    }
}

Time Complexity: O(n^2 * 2)
Space Complexity: O(n^2 * 2)

Style 2: 2D DP (No parameters as 'aliceScore' and 'bobScore' + Prefix Sum + No Switch person flag)
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + stones[i];
        }
        // dp[left][right] = advantage for player whose turn it is
        int[][] dp = new int[n][n];
        // Fill by increasing length
        for (int length = 2; length <= n; length++) {
            for (int left = 0; left <= n - length; left++) {
                int right = left + length - 1;
                int sum = presum[right + 1] - presum[left];
                int removeLeft = (sum - stones[left]) - dp[left + 1][right];
                int removeRight = (sum - stones[right]) - dp[left][right - 1];
                dp[left][right] = Math.max(removeLeft, removeRight);
            }
        }
        return dp[0][n - 1];
    }
}

Time Complexity: O(n^2)
Space Complexity: O(n^2)

Refer to Deepseek
Here's the Native DFS (backtracking) solution for LeetCode 1690:
🔄 Native DFS Solution (Brute Force)
class Solution {
    public int stoneGameVII(int[] stones) {
        // Start DFS from the full array
        return dfs(stones, 0, stones.length - 1, 0, 0, true);
    }
    
    // Native DFS exploring all possible game paths
    private int dfs(int[] stones, int left, int right, int aliceScore, int bobScore, boolean isAliceTurn) {
        // Base case: no stones left
        if (left > right) {
            return aliceScore - bobScore;
        }
        
        // Calculate sum of current stones
        int currentSum = 0;
        for (int i = left; i <= right; i++) {
            currentSum += stones[i];
        }
        
        if (isAliceTurn) {
            // Alice's turn: she tries to maximize (alice - bob)
            
            // Option 1: Remove left stone
            int scoreIfRemoveLeft = currentSum - stones[left];
            int resultRemoveLeft = dfs(stones, left + 1, right, 
                                       aliceScore + scoreIfRemoveLeft, bobScore, false);
            
            // Option 2: Remove right stone
            int scoreIfRemoveRight = currentSum - stones[right];
            int resultRemoveRight = dfs(stones, left, right - 1, 
                                        aliceScore + scoreIfRemoveRight, bobScore, false);
            
            // Alice chooses the move that gives her maximum advantage
            return Math.max(resultRemoveLeft, resultRemoveRight);
            
        } else {
            // Bob's turn: he tries to minimize (alice - bob) = maximize (bob - alice)
            
            // Option 1: Remove left stone (from Bob's perspective)
            int scoreIfRemoveLeft = currentSum - stones[left];
            int resultRemoveLeft = dfs(stones, left + 1, right, 
                                       aliceScore, bobScore + scoreIfRemoveLeft, true);
            
            // Option 2: Remove right stone
            int scoreIfRemoveRight = currentSum - stones[right];
            int resultRemoveRight = dfs(stones, left, right - 1, 
                                        aliceScore, bobScore + scoreIfRemoveRight, true);
            
            // Bob chooses the move that minimizes Alice's advantage
            return Math.min(resultRemoveLeft, resultRemoveRight);
        }
    }
}
⚡ Optimized DFS with Prefix Sum
The above solution recalculates sum each time (O(n) per call). Here's an optimized version:
class Solution {
    public int stoneGameVII(int[] stones) {
        // Precompute prefix sums for O(1) range sum
        int n = stones.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stones[i];
        }
        
        return dfs(stones, prefix, 0, n - 1, 0, 0, true);
    }
    
    private int dfs(int[] stones, int[] prefix, int left, int right, 
                   int aliceScore, int bobScore, boolean isAliceTurn) {
        if (left > right) {
            return aliceScore - bobScore;
        }
        
        // Get sum in O(1) using prefix array
        int currentSum = prefix[right + 1] - prefix[left];
        
        if (isAliceTurn) {
            // Remove left
            int removeLeftScore = currentSum - stones[left];
            int resultLeft = dfs(stones, prefix, left + 1, right, 
                                aliceScore + removeLeftScore, bobScore, false);
            
            // Remove right
            int removeRightScore = currentSum - stones[right];
            int resultRight = dfs(stones, prefix, left, right - 1, 
                                 aliceScore + removeRightScore, bobScore, false);
            
            return Math.max(resultLeft, resultRight);
            
        } else {
            // Bob's turn
            int removeLeftScore = currentSum - stones[left];
            int resultLeft = dfs(stones, prefix, left + 1, right, 
                                aliceScore, bobScore + removeLeftScore, true);
            
            int removeRightScore = currentSum - stones[right];
            int resultRight = dfs(stones, prefix, left, right - 1, 
                                 aliceScore, bobScore + removeRightScore, true);
            
            return Math.min(resultLeft, resultRight);
        }
    }
}
🔍 Understanding the Native DFS Logic
The DFS explores all possible game paths:
Example: stones = [5, 3, 1]

Game tree:
Level 0 (Alice): [5,3,1], sum=9
  ├── Remove left(5): Alice gets 4 points (9-5), state=[3,1]
  │   └── Bob's turn: [3,1], sum=4
  │       ├── Remove left(3): Bob gets 1 point (4-3), state=[1]
  │       │   └── Alice's turn: [1], sum=1
  │       │       └── Remove stone: Alice gets 0 points (1-1)
  │       └── Remove right(1): Bob gets 3 points (4-1), state=[3]
  │           └── Alice's turn: [3], sum=3
  │               └── Remove stone: Alice gets 0 points (3-3)
  └── Remove right(1): Alice gets 8 points (9-1), state=[5,3]
      └── Bob's turn: [5,3], sum=8
          ├── Remove left(5): Bob gets 3 points (8-5), state=[3]
          │   └── Alice's turn: [3], sum=3
          │       └── Remove stone: Alice gets 0 points (3-3)
          └── Remove right(3): Bob gets 5 points (8-3), state=[5]
              └── Alice's turn: [5], sum=5
                  └── Remove stone: Alice gets 0 points (5-5)
📊 Complexity Analysis
AspectValueExplanationTimeO(2ⁿ)Binary tree with n levelsSpaceO(n)Recursion depthStates2ⁿ leaf nodesEach stone removal is a binary choice
For n = 20: 2²⁰ ≈ 1 million operations
For n = 30: 2³⁰ ≈ 1 billion operations (too slow!)
🎯 Why Native DFS Fails LeetCode Constraints
LeetCode constraints: n ≤ 1000
Native DFS: O(2¹⁰⁰⁰) ≈ 10³⁰⁰ operations → Impossible!
Maximum feasible n: ~20-25
🔄 DFS with Memoization (DP)
This is why we need memoization:
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stones[i];
        }
        
        // memo[left][right][turn] where turn: 0=Alice, 1=Bob
        Integer[][][] memo = new Integer[n][n][2];
        return dfsMemo(stones, prefix, 0, n - 1, 0, memo);
    }
    
    // turn: 0 = Alice's turn, 1 = Bob's turn
    private int dfsMemo(int[] stones, int[] prefix, int left, int right, 
                       int turn, Integer[][][] memo) {
        if (left > right) return 0;
        
        if (memo[left][right][turn] != null) {
            return memo[left][right][turn];
        }
        
        int currentSum = prefix[right + 1] - prefix[left];
        
        if (turn == 0) { // Alice's turn
            int removeLeft = (currentSum - stones[left]) - 
                             dfsMemo(stones, prefix, left + 1, right, 1, memo);
            int removeRight = (currentSum - stones[right]) - 
                              dfsMemo(stones, prefix, left, right - 1, 1, memo);
            memo[left][right][turn] = Math.max(removeLeft, removeRight);
            
        } else { // Bob's turn
            int removeLeft = (currentSum - stones[left]) - 
                             dfsMemo(stones, prefix, left + 1, right, 0, memo);
            int removeRight = (currentSum - stones[right]) - 
                              dfsMemo(stones, prefix, left, right - 1, 0, memo);
            memo[left][right][turn] = Math.max(removeLeft, removeRight);
        }
        
        return memo[left][right][turn];
    }
}5
Even this memoized version is O(n²) states × 2 turns = O(2n²), which is feasible!
💡 Key Difference: Native DFS vs Game Theory DP
AspectNative DFSGame Theory DPState(left, right, aliceScore, bobScore, turn)(left, right, turn)What it tracksAbsolute scoresScore differenceBase caseReturn aliceScore - bobScoreReturn 0Turn logicAdd score to playerUse score - opponent_next
🧠 The Native DFS Insight
Native DFS shows the raw game tree:
- Alice tries to maximize (alice - bob)
- Bob tries to minimize (alice - bob) = maximize (bob - alice)
- This is a zero-sum game: Alice's gain = Bob's loss
The DP solution compresses this insight into the recurrence:
dp[i][j] = max( (sum-stones[i]) - dp[i+1][j], 
                (sum-stones[j]) - dp[i][j-1] )
⚠️ Test Native DFS with Small Input
public static void main(String[] args) {
    Solution sol = new Solution();
    int[] stones = {5, 3, 1};
    System.out.println(sol.stoneGameVII(stones)); // Should output 6
}
Trace: Alice removes 1 (gets 8), Bob removes 5 (gets 3), Alice removes 3 (gets 0)
Difference = (8+0) - (3) = 5? Wait, let me calculate properly...
Actually with [5,3,1]:
- Alice removes 1: gets 8, remaining [5,3]
- Bob removes 5: gets 3, remaining [3]
- Alice removes 3: gets 0Final: Alice=8, Bob=3 → difference=5
🎯 Conclusion
The Native DFS:
1.Conceptually clear: Shows the complete game tree
2.Computationally impossible: O(2ⁿ) for n > ~25
3.Helps understand DP: DP memoization compresses repeated states
4.Good for learning: Understand why we track difference not scores
Use Native DFS to understand the problem, then use DP for the actual solution!

Why below Memoization solution is wrong ?
Error out when input as below:
stones = [5,3,1,4,2]
Output = 5
Expected = 6
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        // presum[0] = 0, presum[1] = stones[0],
        // presum[2] = stones[0] + stones[1]...
        // sum[i...j] = presum[j + 1] - presum[i]
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        // memo[left][right][turn] where turn: 0=Alice, 1=Bob
        Integer[][][] memo = new Integer[n][n][2];
        return helper(presum, stones, 0, n - 1, 0, 0, 0, memo);
    }

    private int helper(int[] presum, int[] stones, int left, int right, int aliceScore, int bobScore, int isAliceTurn, Integer[][][] memo) {
        if(left > right) {
            return aliceScore - bobScore;
        }
        if(memo[left][right][isAliceTurn] != null) {
            return memo[left][right][isAliceTurn];
        }
        // Get sum in O(1) using prefix sum array
        int curSum = presum[right + 1] - presum[left];
        // Option 1: Remove left stone
        int removeLeft = curSum - stones[left];
        // Option 2: Remove right stone
        int removeRight = curSum - stones[right];
        // Alice's turn: she tries to maximize (alice - bob)
        if(isAliceTurn == 0) {
            int resultRemoveLeft = helper(presum, stones, left + 1, right, aliceScore + removeLeft, bobScore, 1, memo);
            int resultRemoveRight = helper(presum, stones, left, right - 1, aliceScore + removeRight, bobScore, 1, memo);
            // Alice chooses the move that gives her maximum advantage
            memo[left][right][isAliceTurn] = Math.max(resultRemoveLeft, resultRemoveRight);
        // Bob's turn: he tries to minimize (alice - bob)
        } else {
            int resultRemoveLeft = helper(presum, stones, left + 1, right, aliceScore, bobScore + removeLeft, 0, memo);
            int resultRemoveRight = helper(presum, stones, left, right - 1, aliceScore, bobScore + removeRight, 0, memo);
            // Bob chooses the move that minimizes Alice's advantage
            memo[left][right][isAliceTurn] = Math.min(resultRemoveLeft, resultRemoveRight);            
        }
        return memo[left][right][isAliceTurn];
    }
}
I found the bug! The issue is incorrect memoization logic. Your code memoizes the result based on (left, right, turn) but the memo value depends on (aliceScore, bobScore), which aren't part of the memo key!
🔍 The Bug Analysis
Your state is (left, right, aliceScore, bobScore, turn) but you memoize only (left, right, turn). This is wrong because:
Same (left, right, turn) can have different (aliceScore, bobScore)!
Example:
Path A: Alice got 10, Bob got 5, now at [i..j] with Bob's turn
Path B: Alice got 8, Bob got 3, now at [i..j] with Bob's turn

Same (left,right,turn) but different scores!
If we memoize Path A's result, Path B gets wrong value.
✅ Fixed Solution (Native DFS with Correct Memo)
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        
        // We CAN'T memoize with scores - they're different each path!
        // So we remove scores from state and track difference instead
        return dfs(stones, presum, 0, n - 1, 0);
    }
    
    // Returns: Maximum advantage current player can get from stones[left..right]
    // turn: 0 = Alice, 1 = Bob (but we don't need it with this formulation!)
    private int dfs(int[] stones, int[] presum, int left, int right, int turn) {
        if (left > right) {
            return 0;
        }
        
        int curSum = presum[right + 1] - presum[left];
        
        if (turn == 0) { // Alice's turn: maximize advantage
            int removeLeft = (curSum - stones[left]) - dfs(stones, presum, left + 1, right, 1);
            int removeRight = (curSum - stones[right]) - dfs(stones, presum, left, right - 1, 1);
            return Math.max(removeLeft, removeRight);
        } else { // Bob's turn: minimize Alice's advantage (maximize Bob's)
            // Actually Bob tries to maximize his advantage = minimize Alice's
            // But in our formulation, positive = advantage for current player
            // So when Bob plays, he also wants to maximize "advantage for current player"
            int removeLeft = (curSum - stones[left]) - dfs(stones, presum, left + 1, right, 0);
            int removeRight = (curSum - stones[right]) - dfs(stones, presum, left, right - 1, 0);
            return Math.max(removeLeft, removeRight);
        }
    }
}
🎯 Simpler: Game Theory DP Formulation
Actually, we don't need separate turn logic! The standard DP formulation handles this:
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        
        // memo[left][right] = max advantage for player whose turn it is
        Integer[][] memo = new Integer[n][n];
        return dfs(stones, presum, 0, n - 1, memo);
    }
    
    private int dfs(int[] stones, int[] presum, int left, int right, Integer[][] memo) {
        if (left > right) return 0;
        if (memo[left][right] != null) return memo[left][right];
        
        int curSum = presum[right + 1] - presum[left];
        
        // Remove left: get (sum-stones[left]) points, opponent gets advantage on remaining
        int removeLeft = (curSum - stones[left]) - dfs(stones, presum, left + 1, right, memo);
        
        // Remove right: get (sum-stones[right]) points, opponent gets advantage on remaining
        int removeRight = (curSum - stones[right]) - dfs(stones, presum, left, right - 1, memo);
        
        memo[left][right] = Math.max(removeLeft, removeRight);
        return memo[left][right];
    }
}
🔍 Why Your Code Gives 5 Instead of 6 for [5,3,1,4,2]
Let's trace why your memoization is wrong:
Path 1: Alice removes 2 (right), gets 5+3+1+4=13 points
Path 2: Alice removes 5 (left), gets 3+1+4+2=10 points
Both lead to Bob's turn on [5,3,1,4] (if Alice removed 2) or [3,1,4,2] (if Alice removed 5).
Your code memoizes Bob's optimal move for state (0,3,1) [Bob's turn on first 4 stones], but:
- In Path 1: Alice already has 13 points
- In Path 2: Alice only has 10 points
- Same memo value gives wrong final difference!
📊 Correct Calculation for [5,3,1,4,2]
With correct DP:
Total sum = 15
Optimal play:
1. Alice removes 2 (right) → gets 13 points, remaining [5,3,1,4], sum=13
2. Bob removes 4 (right) → gets 9 points, remaining [5,3,1], sum=9  
3. Alice removes 1 (right) → gets 8 points, remaining [5,3], sum=8
4. Bob removes 3 (right) → gets 5 points, remaining [5], sum=5
5. Alice removes 5 → gets 0 points


Scores: Alice = 13 + 8 + 0 = 21, Bob = 9 + 5 = 14
Difference = 21 - 14 = 7 (not 6, let me recalc...)

Wait, my calculation is wrong. Let me use the DP formula:

dp[0][4] = max(
    (15-5) - dp[1][4],  // Remove left 5: get 10 points
    (15-2) - dp[0][3]   // Remove right 2: get 13 points
)

We need dp[1][4] and dp[0][3]...
Actually, the DP gives 6 as the correct answer! The key insight is we're calculating advantage, not tracking absolute scores.
💡 Key Fix: Remove Score Tracking from State
Your approach with (aliceScore, bobScore) cannot be memoized efficiently. Instead:
1.Track advantage, not scores: dp[left][right] = advantage for current player
2.Use zero-sum property: Alice's advantage = -Bob's advantage
3.Recurrence: advantage = (points I get) - (advantage opponent gets)
✅ Final Working Solution
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + stones[i];
        }
        
        Integer[][] memo = new Integer[n][n];
        return dfs(0, n - 1, presum, stones, memo);
    }
    
    // Returns max advantage current player can get from stones[l..r]
    private int dfs(int l, int r, int[] presum, int[] stones, Integer[][] memo) {
        if (l == r) return 0; // Single stone gives 0 points
        if (memo[l][r] != null) return memo[l][r];
        
        int sum = presum[r + 1] - presum[l];
        
        // Remove left stone
        int takeLeft = (sum - stones[l]) - dfs(l + 1, r, presum, stones, memo);
        
        // Remove right stone
        int takeRight = (sum - stones[r]) - dfs(l, r - 1, presum, stones, memo);
        
        memo[l][r] = Math.max(takeLeft, takeRight);
        return memo[l][r];
    }
}
This gives the correct answer 6 for [5,3,1,4,2]!

How about the DP solution ?
Here's the exact DP translation of your native DFS solution:
🔄 Direct 3D DP Translation
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        
        // 1. Prefix sum
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        
        // 2. dp[left][right][turn] = result of helper(left, right, turn)
        //    turn: 0 = Alice, 1 = Bob
        int[][][] dp = new int[n][n][2];
        
        // 3. Initialize base cases: left > right = 0
        // (Java arrays already initialized to 0)
        
        // 4. Fill DP table (need careful order!)
        // We must fill by increasing length, and need both turns computed
        for (int length = 1; length <= n; length++) {
            for (int left = 0; left <= n - length; left++) {
                int right = left + length - 1;
                
                // Current sum
                int curSum = presum[right + 1] - presum[left];
                int removeLeft = curSum - stones[left];
                int removeRight = curSum - stones[right];
                
                // Fill for Alice's turn (turn = 0)
                if (length == 1) {
                    dp[left][right][0] = 0; // Single stone: 0 advantage
                } else {
                    // Alice's turn: dp[left][right][0]
                    int resultRemoveLeftAlice = removeLeft - dp[left + 1][right][1];
                    int resultRemoveRightAlice = removeRight - dp[left][right - 1][1];
                    dp[left][right][0] = Math.max(resultRemoveLeftAlice, resultRemoveRightAlice);
                }
                
                // Fill for Bob's turn (turn = 1)
                if (length == 1) {
                    dp[left][right][1] = 0; // Single stone: 0 advantage
                } else {
                    // Bob's turn: dp[left][right][1]
                    int resultRemoveLeftBob = removeLeft - dp[left + 1][right][0];
                    int resultRemoveRightBob = removeRight - dp[left][right - 1][0];
                    dp[left][right][1] = Math.max(resultRemoveLeftBob, resultRemoveRightBob);
                }
            }
        }
        
        return dp[0][n - 1][0]; // Start with Alice's turn
    }
}
📊 Alternative
Also cleaner and exactly matches your DFS structure:
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + stones[i - 1];
        }
        
        // dp[left][right][turn] where turn = 0 for Alice, 1 for Bob
        int[][][] dp = new int[n][n][2];
        
        // Fill DP table by interval length (from smaller to larger intervals)
        for (int length = 1; length <= n; length++) {
            for (int left = 0; left <= n - length; left++) {
                int right = left + length - 1;
                
                // Get current sum of stones from left to right
                int curSum = presum[right + 1] - presum[left];
                int removeLeft = (length > 1) ? curSum - stones[left] : 0;
                int removeRight = (length > 1) ? curSum - stones[right] : 0;
                
                if (length == 1) {
                    // Base case: single stone, no gain from removing it
                    dp[left][right][0] = 0;
                    dp[left][right][1] = 0;
                } else {
                    // Alice's turn
                    int aliceLeft = removeLeft - dp[left + 1][right][1];
                    int aliceRight = removeRight - dp[left][right - 1][1];
                    dp[left][right][0] = Math.max(aliceLeft, aliceRight);
                    
                    // Bob's turn
                    int bobLeft = removeLeft - dp[left + 1][right][0];
                    int bobRight = removeRight - dp[left][right - 1][0];
                    dp[left][right][1] = Math.max(bobLeft, bobRight);
                }
            }
        }
        
        return dp[0][n - 1][0];
    }
}
🧠 Why 3D DP is Needed for Your DFS
Your DFS has state (left, right, turn), so the DP needs 3 dimensions:
DFS ParameterDP DimensionSizeleftFirst index0..n-1rightSecond index0..n-1turnThird index0..1 (Alice/Bob)
Total states: n × n × 2 but only n(n+1)/2 × 2 are valid (where left ≤ right)
🔍 Filling Order Matters!
In bottom-up DP, we must fill by increasing subarray length:
Length 1: dp[i][i][0] = dp[i][i][1] = 0
Length 2: dp[0][1], dp[1][2], ...
Length 3: dp[0][2], dp[1][3], ...
...
Length n: dp[0][n-1] = result
Because dp[left][right] depends on:
- dp[left+1][right] (shorter by 1)
- dp[left][right-1] (shorter by 1)
💡 Key Insight: We Don't Actually Need "turn" Dimension!
Look at your recurrence:
// Alice: removeLeft - dfs(..., turn=1)
// Bob: removeLeft - dfs(..., turn=0)
Notice: dfs(left, right, 0) = removeLeft - dfs(left+1, right, 1)
But dfs(left+1, right, 1) = (points for Bob) - dfs(..., 0) = symmetric!
Actually, both players use the same logic:
dp[left][right] = max(removeLeft - dp[left+1][right], removeRight - dp[left][right-1])
Where dp[left][right] = advantage for current player.
So we can simplify to 2D DP:
✅ Simplified 2D DP (Recommended)
class Solution {
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + stones[i];
        }
        
        // dp[left][right] = advantage for player whose turn it is
        int[][] dp = new int[n][n];
        
        // Fill by increasing length
        for (int length = 2; length <= n; length++) {
            for (int left = 0; left <= n - length; left++) {
                int right = left + length - 1;
                int sum = presum[right + 1] - presum[left];
                
                int removeLeft = (sum - stones[left]) - dp[left + 1][right];
                int removeRight = (sum - stones[right]) - dp[left][right - 1];
                
                dp[left][right] = Math.max(removeLeft, removeRight);
            }
        }
        
        return dp[0][n - 1];
    }
}
📈 Complexity Comparison
SolutionDimensionsTimeSpaceStatesYour DFS3 parametersO(2ⁿ)O(n)Exponential3D DP3D arrayO(n²)O(n²)n(n+1)2D DP2D arrayO(n²)O(n²)n(n+1)/2
🎯 Why the 2D DP Works
The key insight is symmetry:
- When it's Alice's turn: advantage = points_I_get - opponent_advantage
- When it's Bob's turn: advantage = points_I_get - opponent_advantage
- Both players use the same formula!
- The "turn" is implicit: if dp[left][right] is Alice's advantage, then on the next move, dp[left+1][right] is Bob's advantage
So we can drop the turn dimension entirely!
💎 Conclusion
Your DFS with (left, right, turn) maps to:
1.3D DP dp[left][right][turn] (direct translation)
2.2D DP dp[left][right] (simplified using symmetry)
The 2D DP is better - same time complexity, half the space, and cleaner code! But the 3D DP shows the exact correspondence to your recursive thinking.




Refer to
L877.Stone Game (Ref.L1690)
L1140.Stone Game II (Ref.L877,L1690)
L1406.Stone Game III (Ref.L877,L1140,L1690)
L1510.Stone Game IV (Ref.L877,L1140,L1406,L1690)
L1563.Stone Game V (Ref.L877,L1140,L1406,L1510)
L1686.Stone Game VI
L1770.Maximum Score from Performing Multiplication Operations (Ref.L1423)
L1872.Stone Game VIII

