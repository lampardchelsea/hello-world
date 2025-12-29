https://leetcode.com/problems/stone-game-iv/description/
Alice and Bob take turns playing a game, with Alice starting first.
Initially, there are n stones in a pile. On each player's turn, that player makes a move consisting of removing any non-zero square number of stones in the pile.
Also, if a player cannot make a move, he/she loses the game.
Given a positive integer n, return true if and only if Alice wins the game otherwise return false, assuming both players play optimally.
 
Example 1:
Input: n = 1
Output: true
Explanation: Alice can remove 1 stone winning the game because Bob doesn't have any moves.

Example 2:
Input: n = 2
Output: false
Explanation: Alice can only remove 1 stone, after that Bob removes the last one winning the game (2 -> 1 -> 0).

Example 3:
Input: n = 4
Output: true
Explanation: n is already a perfect square, Alice can win with one move, removing 4 stones (4 -> 0).
 
Constraints:
- 1 <= n <= 10^5
--------------------------------------------------------------------------------
Attempt 1: 2025-12-28
Solution 1: Native DFS (10 min, TLE 19/72)
Style 1: boolean return
class Solution {
    public boolean winnerSquareGame(int n) {
        return helper(n);
    }

    private boolean helper(int n) {
        // BASE CASE: No stones left - current player LOSES
        if(n == 0) {
            return false;
        }
        // Try removing all possible perfect squares <= stones
        for(int i = 1; i * i <= n; i++) {
            // If we remove 'square' stones, opponent gets (stones - square) stones
            // If opponent loses from that state, we win!
            if(!helper(n - i * i)) {
                return true;
            }
        }
        // If no move leads to opponent losing, we lose
        return false;
    }
}

Time Complexity: O(n√n) because for each of n stones, check up to √n perfect squares
Space Complexity: O(n) for DP array

Solution 2: DFS + Memoization (10 min)
class Solution {
    public boolean winnerSquareGame(int n) {
        Boolean[] memo = new Boolean[n + 1];
        return helper(n, memo);
    }

    private boolean helper(int n, Boolean[] memo) {
        // BASE CASE: No stones left - current player LOSES
        if(n == 0) {
            return false;
        }
        if(memo[n] != null) {
            return memo[n];
        }
        // Try removing all possible perfect squares <= stones
        for(int i = 1; i * i <= n; i++) {
            // If we remove 'square' stones, opponent gets (stones - square) stones
            // If opponent loses from that state, we win!
            if(!helper(n - i * i, memo)) {
                return memo[n] = true;
            }
        }
        // If no move leads to opponent losing, we lose
        return memo[n] = false;
    }
}

Time Complexity: O(n√n) because for each of n stones, check up to √n perfect squares
Space Complexity: O(n) for DP array

Solution 3: DP (10 min)
class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];
        // BASE CASE: No stones left - current player LOSES
        //dp[0] = false; // No need to initialize since default false
        for(int stones = 0; stones <= n; stones++) {
            // Try removing all possible perfect squares <= stones
            for(int i = 1; i * i <= stones; i++) {
                // If we remove 'square' stones, opponent gets (stones - square) stones
                // If opponent loses from that state, we win!
                if(!dp[stones - i * i]) {
                    dp[stones] = true;
                    break;
                }
                // If no move leads to opponent losing, we lose
                //dp[stones - i * i] = false; // No need to initialize since default false 
            }
        }
        return dp[n];
    }
}

Time Complexity: O(n√n) because for each of n stones, check up to √n perfect squares
Space Complexity: O(n) for DP array


Refer to Deepseek
Here's the Java solution for LeetCode 1510. Stone Game IV:
🎯 Problem Understanding
- Two players take turns removing perfect square numbers of stones
- Alice starts first with n stones
- Player who cannot make a move loses
- Return true if Alice wins, false otherwise
📝 Native DFS Solution
class Solution {
    public boolean winnerSquareGame(int n) {
        return dfs(n);
    }
    
    private boolean dfs(int stones) {
        // BASE CASE: No stones left - current player LOSES
        if (stones == 0) {
            return false;
        }
        
        // Try removing all possible perfect squares ≤ stones
        for (int x = 1; x * x <= stones; x++) {
            int square = x * x;
            
            // If we remove 'square' stones, opponent gets (stones - square) stones
            // If opponent loses from that state, we win!
            if (!dfs(stones - square)) {
                return true;
            }
        }
        
        // If no move leads to opponent losing, we lose
        return false;
    }
}
⚡ Optimized DP Solution with Memoization
class Solution {
    public boolean winnerSquareGame(int n) {
        // dp[i] = whether current player can win with i stones
        Boolean[] memo = new Boolean[n + 1];
        return dfs(n, memo);
    }
    
    private boolean dfs(int stones, Boolean[] memo) {
        if (stones == 0) return false;
        
        if (memo[stones] != null) {
            return memo[stones];
        }
        
        for (int x = 1; x * x <= stones; x++) {
            int square = x * x;
            
            // If opponent loses from next state, we win
            if (!dfs(stones - square, memo)) {
                memo[stones] = true;
                return true;
            }
        }
        
        memo[stones] = false;
        return false;
    }
}
🔄 Bottom-Up DP Solution
class Solution {
    public boolean winnerSquareGame(int n) {
        // dp[i] = whether current player can win with i stones
        boolean[] dp = new boolean[n + 1];
        dp[0] = false; // Base case: 0 stones = lose
        
        for (int stones = 1; stones <= n; stones++) {
            // Try all perfect squares ≤ stones
            for (int x = 1; x * x <= stones; x++) {
                int square = x * x;
                
                // If removing 'square' stones leaves opponent in losing state
                if (!dp[stones - square]) {
                    dp[stones] = true;
                    break; // Found a winning move
                }
            }
            // If no winning move found, dp[stones] remains false
        }
        
        return dp[n];
    }
}
🚀 Optimized DP with Precomputed Squares
class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];
        dp[0] = false;
        
        // Precompute perfect squares up to n
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }
        
        for (int stones = 1; stones <= n; stones++) {
            for (int square : squares) {
                if (square > stones) break;
                
                if (!dp[stones - square]) {
                    dp[stones] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
}
📊 Space-Optimized DP (Bit Manipulation)
class Solution {
    public boolean winnerSquareGame(int n) {
        // Using bitset to save space
        boolean[] dp = new boolean[n + 1];
        
        for (int stones = 1; stones <= n; stones++) {
            // Check all perfect squares
            for (int x = 1; x * x <= stones; x++) {
                if (!dp[stones - x * x]) {
                    dp[stones] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
}
🧮 Mathematical Approach (Optimal)
class Solution {
    public boolean winnerSquareGame(int n) {
        // dp[i] = can win with i stones
        boolean[] dp = new boolean[n + 1];
        
        for (int i = 1; i <= n; i++) {
            for (int k = 1; k * k <= i; k++) {
                // If there exists a move that puts opponent in losing position
                if (!dp[i - k * k]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        
        return dp[n];
    }
}
🔍 Example Walkthrough
For n = 7:
Perfect squares ≤ 7: 1, 4
dp[0] = false (lose with 0 stones)

dp[1]: Can remove 1 stone
  leaves 0 stones for opponent → dp[0] = false (opponent loses)
  dp[1] = true ✓

dp[2]: Can remove 1 stone
  leaves 1 stone for opponent → dp[1] = true (opponent wins)
  dp[2] = false

dp[3]: Can remove 1 stone
  leaves 2 stones → dp[2] = false (opponent loses)
  dp[3] = true ✓

dp[4]: Can remove 1 or 4 stones
  Remove 1: leaves 3 → dp[3] = true
  Remove 4: leaves 0 → dp[0] = false ✓
  dp[4] = true

dp[5]: Can remove 1 or 4 stones
  Remove 1: leaves 4 → dp[4] = true
  Remove 4: leaves 1 → dp[1] = true
  dp[5] = false

dp[6]: Can remove 1 or 4 stones
  Remove 1: leaves 5 → dp[5] = false ✓
  dp[6] = true

dp[7]: Can remove 1 or 4 stones
  Remove 1: leaves 6 → dp[6] = true
  Remove 4: leaves 3 → dp[3] = true
  dp[7] = false
Result: dp[7] = false → Bob wins
📈 Complexity Analysis
SolutionTimeSpaceNotesNative DFSO(n√n)O(n)Exponential callsMemoized DFSO(n√n)O(n)Each state computed onceBottom-up DPO(n√n)O(n)Best approachMathematicalO(n√n)O(n)Clean and efficient
Detailed Analysis:
- Time: O(n√n) because for each of n stones, check up to √n perfect squares
- Space: O(n) for DP array
🎯 Key Insights
1.Zero-sum, perfect information game like previous Stone Games
2.State depends only on remaining stones (no M parameter)
3.Perfect squares constraint changes move generation
4.Winning position: exists a move to losing position for opponent
5.Losing position: all moves lead to winning position for opponent
🧪 Test Cases
public static void main(String[] args) {
    Solution sol = new Solution();
    
    // Test cases
    System.out.println(sol.winnerSquareGame(1));  // true - Alice takes 1
    System.out.println(sol.winnerSquareGame(2));  // false - Alice takes 1, Bob wins
    System.out.println(sol.winnerSquareGame(4));  // true - Alice takes 4
    System.out.println(sol.winnerSquareGame(7));  // false - shown above
    System.out.println(sol.winnerSquareGame(17)); // false
    System.out.println(sol.winnerSquareGame(100000)); // true - scales well
}
🔄 Comparison with Other Stone Games
GameMovesStateComplexityStone Game ILeft or right end(left, right)O(n²)Stone Game II1 to 2M piles(index, M)O(n³)Stone Game III1, 2, or 3 stones(index)O(n)Stone Game IVPerfect squares(stones)O(n√n)
💡 Optimization Tips
1.Early termination: If n is perfect square, Alice wins immediately
if (Math.sqrt(n) % 1 == 0) return true;
2.Small n optimization: Precompute results for small n
if (n <= 10) {
    boolean[] small = {false, true, false, true, true, false, true, false, true, true, true};
    return small[n];
}
3.Square precomputation: Compute all squares once
int maxSquare = (int)Math.sqrt(n);
int[] squares = new int[maxSquare];
for (int i = 1; i <= maxSquare; i++) squares[i-1] = i*i;
The bottom-up DP solution is the most practical and recommended approach for this problem.


Refer to
L877.Stone Game (Ref.L1690)
L1140.Stone Game II (Ref.L877,L1690)
L1406.Stone Game III (Ref.L877,L1140,L1690)
L1563.Stone Game V (Ref.L)
1686
L1690.Stone Game VII (Ref.L1770)
1872
2029
