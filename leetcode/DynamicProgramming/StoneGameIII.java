https://leetcode.com/problems/stone-game-iii/description/
Alice and Bob continue their games with piles of stones. There are several stones arranged in a row, and each stone has an associated value which is an integer given in the array stoneValue.
Alice and Bob take turns, with Alice starting first. On each player's turn, that player can take 1, 2, or 3 stones from the first remaining stones in the row.
The score of each player is the sum of the values of the stones taken. The score of each player is 0 initially.
The objective of the game is to end with the highest score, and the winner is the player with the highest score and there could be a tie. The game continues until all the stones have been taken.
Assume Alice and Bob play optimally.
Return "Alice" if Alice will win, "Bob" if Bob will win, or "Tie" if they will end the game with the same score.
 
Example 1:
Input: stoneValue = [1,2,3,7]
Output: "Bob"
Explanation: Alice will always lose. Her best move will be to take three piles and the score become 6. Now the score of Bob is 7 and Bob wins.

Example 2:
Input: stoneValue = [1,2,3,-9]
Output: "Alice"
Explanation: Alice must choose all the three piles at the first move to win and leave Bob with negative score.
If Alice chooses one pile her score will be 1 and the next move Bob's score becomes 5. In the next move, Alice will take the pile with value = -9 and lose.
If Alice chooses two piles her score will be 3 and the next move Bob's score becomes 3. In the next move, Alice will take the pile with value = -9 and also lose.
Remember that both play optimally so here Alice will choose the scenario that makes her win.

Example 3:
Input: stoneValue = [1,2,3,6]
Output: "Tie"
Explanation: Alice cannot win this game. She can end the game in a draw if she decided to choose all the first three piles, otherwise she will lose.
 
Constraints:
- 1 <= stoneValue.length <= 5 * 10^4
- -1000 <= stoneValue[i] <= 1000
--------------------------------------------------------------------------------
Attempt 1: 2025-12-27
Solution 1: Native DFS (10 min, TLE 160/185)
Only value return is suitable for this problem
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int result = helper(stoneValue, 0);
        if(result > 0) {
            return "Alice";
        } else if(result < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }

    private int helper(int[] stoneValue, int index) {
        int n = stoneValue.length;
        if(index >= n) {
            return 0;
        }
        int best = Integer.MIN_VALUE;
        int sum = 0;
        for(int X = 1; X <= 3; X++) {
            if(index + X > n) {
                break;
            }
            sum += stoneValue[index + X - 1];
            int opponentBest = helper(stoneValue, index + X);
            int ourStones = sum - opponentBest;
            best = Math.max(best, ourStones);
        }
        return best;
    }
}

Time Complexity: O(3^n)
Space Complexity: O(n)

Solution 2: DFS + Memoization (10 min)
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        Integer[] memo = new Integer[stoneValue.length + 1];
        int result = helper(stoneValue, 0, memo);
        if(result > 0) {
            return "Alice";
        } else if(result < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }

    private int helper(int[] stoneValue, int index, Integer[] memo) {
        int n = stoneValue.length;
        if(index >= n) {
            return 0;
        }
        if(memo[index] != null) {
            return memo[index];
        }
        int best = Integer.MIN_VALUE;
        int sum = 0;
        for(int X = 1; X <= 3; X++) {
            if(index + X > n) {
                break;
            }
            sum += stoneValue[index + X - 1];
            int opponentBest = helper(stoneValue, index + X, memo);
            int ourStones = sum - opponentBest;
            best = Math.max(best, ourStones);
        }
        return memo[index] = best;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 3: DP (10 min)
Style 1: O(n) space
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        int[] dp = new int[n + 1];
        for(int index = n - 1; index >= 0; index--) {
            int best = Integer.MIN_VALUE;
            int sum = 0;
            for(int X = 1; X <= 3; X++) {
                if(index + X > n) {
                    break;
                }
                sum += stoneValue[index + X - 1];
                int opponentBest = dp[index + X];
                int ourStones = sum - opponentBest;
                best = Math.max(best, ourStones);
            }
            dp[index] = best;
        }
        if(dp[0] > 0) {
            return "Alice";
        } else if(dp[0] < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Style 2: Space-Optimized O(1) space
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        // Since we only need next 3 values, use rolling array
        int[] dp = new int[4]; // dp[i % 4] for position i
        for(int index = n - 1; index >= 0; index--) {
            int best = Integer.MIN_VALUE;
            int sum = 0;
            for(int X = 1; X <= 3; X++) {
                if(index + X > n) {
                    break;
                }
                sum += stoneValue[index + X - 1];
                int opponentBest = dp[(index + X) % 4];
                int ourStones = sum - opponentBest;
                best = Math.max(best, ourStones);
            }
            dp[index % 4] = best;
        }
        if(dp[0] > 0) {
            return "Alice";
        } else if(dp[0] < 0) {
            return "Bob";
        } else {
            return "Tie";
        }
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
Here's the Java solution for LeetCode 1406. Stone Game III:
🎯 Problem Understanding
Two players take stones from either end. Each player can take 1, 2, or 3 stones on their turn. Alice starts first. Return:
- "Alice" if Alice wins
- "Bob" if Bob wins
- "Tie" if they tie
📝 Native DFS Solution
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        int result = dfs(stoneValue, 0);
        
        if (result > 0) return "Alice";
        if (result < 0) return "Bob";
        return "Tie";
    }
    
    private int dfs(int[] stones, int index) {
        int n = stones.length;
        
        // BASE CASE: No stones left
        if (index >= n) {
            return 0;
        }
        
        // Try taking 1, 2, or 3 stones
        int best = Integer.MIN_VALUE;
        int sum = 0;
        
        for (int X = 1; X <= 3; X++) {
            if (index + X > n) break;
            
            // Sum of stones taken in this move
            sum += stones[index + X - 1];
            
            // Our stones = sum taken - opponent's best from remaining
            int opponentBest = dfs(stones, index + X);
            int ourStones = sum - opponentBest;
            
            best = Math.max(best, ourStones);
        }
        
        return best;
    }
}
⚡ Optimized DP Solution with Memoization
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        
        // dp[i] = maximum score advantage current player can get
        // starting from position i
        Integer[] memo = new Integer[n];
        
        int result = dfs(stoneValue, 0, memo);
        
        if (result > 0) return "Alice";
        if (result < 0) return "Bob";
        return "Tie";
    }
    
    private int dfs(int[] stones, int index, Integer[] memo) {
        int n = stones.length;
        
        if (index >= n) return 0;
        
        // Return memoized result
        if (memo[index] != null) {
            return memo[index];
        }
        
        int best = Integer.MIN_VALUE;
        int sum = 0;
        
        // Try taking 1, 2, or 3 stones
        for (int X = 1; X <= 3; X++) {
            if (index + X > n) break;
            
            sum += stones[index + X - 1];
            int opponentBest = dfs(stones, index + X, memo);
            int ourStones = sum - opponentBest;
            
            best = Math.max(best, ourStones);
        }
        
        memo[index] = best;
        return best;
    }
}
🔄 Bottom-Up DP Solution
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        
        // dp[i] = maximum score advantage current player can get
        // starting from position i to end
        int[] dp = new int[n + 1];
        // Base case: dp[n] = 0 (no stones left)
        
        // Process from end to beginning
        for (int i = n - 1; i >= 0; i--) {
            // Try taking 1, 2, or 3 stones
            int best = Integer.MIN_VALUE;
            int sum = 0;
            
            for (int X = 1; X <= 3; X++) {
                if (i + X > n) break;
                
                sum += stoneValue[i + X - 1];
                int opponentBest = dp[i + X];
                int ourStones = sum - opponentBest;
                
                best = Math.max(best, ourStones);
            }
            
            dp[i] = best;
        }
        
        int result = dp[0];
        
        if (result > 0) return "Alice";
        if (result < 0) return "Bob";
        return "Tie";
    }
}
📊 Space-Optimized DP Solution (O(1) space)
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        
        // Since we only need next 3 values, use rolling array
        int[] dp = new int[4]; // dp[i % 4] for position i
        
        for (int i = n - 1; i >= 0; i--) {
            int best = Integer.MIN_VALUE;
            int sum = 0;
            
            for (int X = 1; X <= 3; X++) {
                if (i + X > n) break;
                
                sum += stoneValue[i + X - 1];
                
                // opponentBest = dp[(i + X) % 4] if i+X < n, else 0
                int opponentBest = (i + X < n) ? dp[(i + X) % 4] : 0;
                int ourStones = sum - opponentBest;
                
                best = Math.max(best, ourStones);
            }
            
            dp[i % 4] = best;
        }
        
        int result = dp[0];
        
        if (result > 0) return "Alice";
        if (result < 0) return "Bob";
        return "Tie";
    }
}
🧮 Alternative DP with Prefix Sum
class Solution {
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        
        // Prefix sum for quick range sum calculation
        int[] prefix = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + stoneValue[i - 1];
        }
        
        // dp[i] = max advantage from position i
        int[] dp = new int[n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            int best = Integer.MIN_VALUE;
            
            for (int X = 1; X <= 3; X++) {
                if (i + X > n) break;
                
                // Stones taken = sum from i to i+X-1
                int stonesTaken = prefix[i + X] - prefix[i];
                int opponentBest = dp[i + X];
                int ourStones = stonesTaken - opponentBest;
                
                best = Math.max(best, ourStones);
            }
            
            dp[i] = best;
        }
        
        int result = dp[0];
        
        if (result > 0) return "Alice";
        if (result < 0) return "Bob";
        return "Tie";
    }
}
🔍 Example Walkthrough
For stoneValue = [1,2,3,7]:
Recursive/DP calculation:
dp[4] = 0

i=3: Can only take 1 stone (stones[3]=7)
  best = 7 - dp[4] = 7 - 0 = 7
  dp[3] = 7

i=2: Can take 1 or 2 stones
  X=1: stones[2]=3 - dp[3]=3-7=-4
  X=2: stones[2]+stones[3]=3+7=10 - dp[4]=10-0=10
  best = max(-4, 10) = 10
  dp[2] = 10

i=1: Can take 1, 2, or 3 stones
  X=1: 2 - dp[2]=2-10=-8
  X=2: 2+3=5 - dp[3]=5-7=-2  
  X=3: 2+3+7=12 - dp[4]=12-0=12
  best = max(-8, -2, 12) = 12
  dp[1] = 12

i=0: Can take 1, 2, or 3 stones
  X=1: 1 - dp[1]=1-12=-11
  X=2: 1+2=3 - dp[2]=3-10=-7
  X=3: 1+2+3=6 - dp[3]=6-7=-1
  best = max(-11, -7, -1) = -1
  dp[0] = -1
Result: dp[0] = -1 → Bob wins by 1 point
📈 Complexity Analysis
SolutionTimeSpaceNotesNative DFSO(3ⁿ)O(n)Exponential, too slowMemoized DFSO(n)O(n)Each index computed onceBottom-up DPO(n)O(n)Clean iterativeSpace-optimized DPO(n)O(1)Best solution
🎯 Key Differences from Stone Game II
1.Fixed moves: Always 1, 2, or 3 stones (not variable)
2.No M parameter: Simpler state (index) only
3.Linear DP: 1D array instead of 2D
4.Easier recurrence: dp[i] = max(sum[i..i+X-1] - dp[i+X]) for X=1,2,3
🧪 Test Cases
public static void main(String[] args) {
    Solution sol = new Solution();
    
    // Test 1: Alice wins
    int[] test1 = {1,2,3,6};
    System.out.println(sol.stoneGameIII(test1)); // "Alice"
    
    // Test 2: Bob wins  
    int[] test2 = {1,2,3,-9};
    System.out.println(sol.stoneGameIII(test2)); // "Bob"
    
    // Test 3: Tie
    int[] test3 = {1,2,3,-1,-2,-3,7};
    System.out.println(sol.stoneGameIII(test3)); // "Tie"
}
The space-optimized DP solution (O(1) space) is the most efficient, but all DP solutions have O(n) time complexity and are acceptable for LeetCode constraints (n ≤ 50000).



Refer to
L877.Stone Game (Ref.L1690)
L1140.Stone Game II (Ref.L877,L1690)
L1510.Stone Game IV (Ref.L877,L1140,L1406,L1690)
L1563.Stone Game V (Ref.L877,L1140,L1406,L1510)
L1686.Stone Game VI
L1690.Stone Game VII (Ref.L1770)
L1872.Stone Game VIII
