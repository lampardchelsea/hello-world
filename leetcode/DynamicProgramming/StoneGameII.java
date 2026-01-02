https://leetcode.com/problems/stone-game-ii/description/
Alice and Bob continue their games with piles of stones. There are a number of piles arranged in a row, and each pile has a positive integer number of stones piles[i]. The objective of the game is to end with the most stones.
Alice and Bob take turns, with Alice starting first.
On each player's turn, that player can take all the stones in the first X remaining piles, where 1 <= X <= 2M. Then, we set M = max(M, X). Initially, M = 1.
The game continues until all the stones have been taken.
Assuming Alice and Bob play optimally, return the maximum number of stones Alice can get.
 
Example 1:
Input: piles = [2,7,9,4,4]
Output: 10
Explanation:
- If Alice takes one pile at the beginning, Bob takes two piles, then Alice takes 2 piles again. Alice can get 2 + 4 + 4 = 10 stones in total.
- If Alice takes two piles at the beginning, then Bob can take all three piles left. In this case, Alice get 2 + 7 = 9 stones in total.
So we return 10 since it's larger.
Example 2:
Input: piles = [1,2,3,4,5,100]
Output: 104
 
Constraints:
- 1 <= piles.length <= 100
- 1 <= piles[i] <= 10^4
--------------------------------------------------------------------------------
Attempt 1: 2025-12-25
Solution 1: Native DFS (120 min)
Style 1: Without Prefix Sum void return
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] presum = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + piles[i - 1];
        }
        int[] result = new int[1];
        helper(piles, presum, 0, 1, result);
        return result[0];
    }

    private void helper(int[] piles, int[] presum, int index, int M, int[] result) {
        int n = piles.length;
        
        // BASE CASE 1: No piles left
        if(index >= n) {
            return;
        }
        
        // Calculate total remaining stones from current position
        // presum[0] = 0, presum[1] = piles[0], presum[2] = piles[0] + piles[1]...etc
        int remainSum = presum[n] - presum[index];
        
        int localBest = 0;
        // Try all possible X values (1 to 2M)
        for(int X = 1; X <= 2 * M; X++) {
            // Boundary check: can't take more piles than exist
            if(index + X > n) {
                break;
            }
            int[] opponentBest = new int[1];
            // Recursive call: opponent's optimal result from next state
            // M updates to max(M, X) for opponent's turn
            helper(piles, presum, index + X, Math.max(X, M), opponentBest);
            
            // ZERO-SUM GAME FORMULA:
            // Our stones = total stones from current position - opponent's best stones
            // This works because:
            // Let stonesTaken = piles[index] + ... + piles[index+X-1]
            // Let remainingAfter = piles[index+X] + ... + piles[n-1]
            // Then: remainSum = stonesTaken + remainingAfter
            // Our total = stonesTaken + (remainingAfter - opponentBest[0])
            //            = (stonesTaken + remainingAfter) - opponentBest[0]
            //            = remainSum - opponentBest[0]
            
            // Track the maximum stones we can get
            localBest = Math.max(localBest, remainSum - opponentBest[0]);
        }
        result[0] = localBest;
    }
}

Style 2: Without Prefix Sum int return (TLE 26/92)
class Solution {
    public int stoneGameII(int[] piles) {
        return helper(piles, 0, 1);
    }

    private int helper(int[] piles, int index, int M) {
        int n = piles.length;
        
        // BASE CASE 1: No piles left
        if(index >= n) {
            return 0;
        }
        
        // Calculate total remaining stones from current position
        int remainSum = 0;
        for(int i = index; i < n; i++) {
            remainSum += piles[i];
        }
        
        // BASE CASE 2: OPTIMIZATION - Can take all remaining piles
        // When 2M >= remaining piles count, the optimal move is to take EVERYTHING
        // This prevents opponent from getting any stones from this segment
        // Which improve Pass Test Cases from 25 to 26
        if(2 * M >= n - index) {
            return remainSum;  // Take all remaining stones
        }
        
        // RECURSIVE CASE: Find best move among all possibilities
        int best = 0;
        
        // Try all possible X values (1 to 2M)
        for(int X = 1; X <= 2 * M; X++) {
            // Boundary check: can't take more piles than exist
            if(index + X > n) {
                break;
            }
            
            // Recursive call: opponent's optimal result from next state
            // M updates to max(M, X) for opponent's turn
            int opponentBest = helper(piles, index + X, Math.max(X, M));
            
            // ZERO-SUM GAME FORMULA:
            // Our stones = total stones from current position - opponent's best stones
            // This works because:
            // Let stonesTaken = piles[index] + ... + piles[index+X-1]
            // Let remainingAfter = piles[index+X] + ... + piles[n-1]
            // Then: remainSum = stonesTaken + remainingAfter
            // Our total = stonesTaken + (remainingAfter - opponentBest)
            //            = (stonesTaken + remainingAfter) - opponentBest
            //            = remainSum - opponentBest
            int ourStones = remainSum - opponentBest;
            
            // Track the maximum stones we can get
            best = Math.max(best, ourStones);
        }
        
        return best;
    }
}

Time Complexity: O(2^n × n^2)
Space Complexity: O(n)

Style 3: With Prefix Sum int return
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] presum = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + piles[i - 1];
        }
        return helper(piles, presum, 0, 1);
    }

    private int helper(int[] piles, int[] presum, int index, int M) {
        int n = piles.length;
        
        // BASE CASE 1: No piles left
        if(index >= n) {
            return 0;
        }
        
        // Calculate total remaining stones from current position
        // presum[0] = 0, presum[1] = piles[0], presum[2] = piles[0] + piles[1]...etc
        int remainSum = presum[n] - presum[index];
        
        // BASE CASE 2: OPTIMIZATION - Can take all remaining piles
        // When 2M >= remaining piles count, the optimal move is to take EVERYTHING
        // This prevents opponent from getting any stones from this segment
        if(2 * M >= n - index) {
            return remainSum;  // Take all remaining stones
        }
        
        // RECURSIVE CASE: Find best move among all possibilities
        int best = 0;
        
        // Try all possible X values (1 to 2M)
        for(int X = 1; X <= 2 * M; X++) {
            // Boundary check: can't take more piles than exist
            if(index + X > n) {
                break;
            }
            
            // Recursive call: opponent's optimal result from next state
            // M updates to max(M, X) for opponent's turn
            int opponentBest = helper(piles, presum, index + X, Math.max(X, M));
            
            // ZERO-SUM GAME FORMULA:
            // Our stones = total stones from current position - opponent's best stones
            // This works because:
            // Let stonesTaken = piles[index] + ... + piles[index+X-1]
            // Let remainingAfter = piles[index+X] + ... + piles[n-1]
            // Then: remainSum = stonesTaken + remainingAfter
            // Our total = stonesTaken + (remainingAfter - opponentBest)
            //            = (stonesTaken + remainingAfter) - opponentBest
            //            = remainSum - opponentBest
            int ourStones = remainSum - opponentBest;
            
            // Track the maximum stones we can get
            best = Math.max(best, ourStones);
        }
        
        return best;
    }
}

Time Complexity: O(2^n × n^2)
Space Complexity: O(n)

Solution 2: DFS + Memoization (10 min)
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] presum = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + piles[i - 1];
        }
        // index range 0 to n - 1, M range 0 to n
        Integer[][] memo = new Integer[n][n + 1];
        return helper(piles, presum, 0, 1, memo);
    }

    private int helper(int[] piles, int[] presum, int index, int M, Integer[][] memo) {
        int n = piles.length;
        
        // BASE CASE 1: No piles left
        if(index >= n) {
            return 0;
        }
        
        // Calculate total remaining stones from current position
        // presum[0] = 0, presum[1] = piles[0], presum[2] = piles[0] + piles[1]...etc
        int remainSum = presum[n] - presum[index];
        
        // BASE CASE 2: OPTIMIZATION - Can take all remaining piles
        // When 2M >= remaining piles count, the optimal move is to take EVERYTHING
        // This prevents opponent from getting any stones from this segment
        if(2 * M >= n - index) {
            return remainSum;  // Take all remaining stones
        }
        
        if(memo[index][M] != null) {
            return memo[index][M];
        }

        // RECURSIVE CASE: Find best move among all possibilities
        int best = 0;
        
        // Try all possible X values (1 to 2M)
        for(int X = 1; X <= 2 * M; X++) {
            // Boundary check: can't take more piles than exist
            if(index + X > n) {
                break;
            }
            
            // Recursive call: opponent's optimal result from next state
            // M updates to max(M, X) for opponent's turn
            int opponentBest = helper(piles, presum, index + X, Math.max(X, M), memo);
            
            // ZERO-SUM GAME FORMULA:
            // Our stones = total stones from current position - opponent's best stones
            // This works because:
            // Let stonesTaken = piles[index] + ... + piles[index+X-1]
            // Let remainingAfter = piles[index+X] + ... + piles[n-1]
            // Then: remainSum = stonesTaken + remainingAfter
            // Our total = stonesTaken + (remainingAfter - opponentBest)
            //            = (stonesTaken + remainingAfter) - opponentBest
            //            = remainSum - opponentBest
            int ourStones = remainSum - opponentBest;
            
            // Track the maximum stones we can get
            best = Math.max(best, ourStones);
        }
        
        return memo[index][M] = best;
    }
}

Time Complexity: O(n^3)
Space Complexity: O(n)

Solution 3: 2D DP (10 min)
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] presum = new int[n + 1];
        for(int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + piles[i - 1];
        }
        int[][] dp = new int[n][n + 1];
        for(int index = n - 1; index >= 0; index--) {
            int remainSum = presum[n] - presum[index];
            for(int M = n; M >= 1; M--) {
                if(2 * M >= n - index) {
                    dp[index][M] = remainSum;
                    continue;
                }
                int best = 0;
                for(int X = 1; X <= 2 * M; X++) {
                    if(index + X > n) {
                        break;
                    }
                    int ourStones = remainSum - dp[index + X][Math.max(X, M)];
                    best = Math.max(best, ourStones);
                }
                dp[index][M] = best;
            }
        }
        return dp[0][1];
    }
}

Time Complexity: O(n^3)
Space Complexity: O(n)

Refer to Deepseek & Kimi
Give Native DFS solution ?
Below is a native DFS (no memo, no DP table, no pruning) solution for LeetCode 1140. Stone Game II.
It is intentionally written to mirror the exact recursive thought process you would use on a white-board before adding any optimisation.
The complexity is O(2ⁿ) – fast enough only for very small inputs – but it shows the core minimax idea in the clearest possible way.
class Solution {
    /**
     * Native DFS – no memoisation.
     * Returns the maximum stones the current player can collect
     * from the sub-array piles[idx .. n-1] with current limit M.
     */
    public int stoneGameII(int[] piles) {
        return dfs(piles, 0, 1);
    }

    /**
     * @param piles the whole array (never changed)
     * @param idx   left-most index still in play
     * @param M     current value of M (max piles allowed this turn is 2*M)
     * @return      best stones the player whose turn it is can achieve
     *              from idx onward, assuming both sides play optimally
     */
    private int dfs(int[] piles, int idx, int M) {
        int n = piles.length;

        /* ---------- 1. base case: no piles left  ---------- */
        if (idx == n) return 0;

        /* ---------- 2. compute total stones remaining ---------- */
        int totalRemaining = 0;
        for (int i = idx; i < n; i++) totalRemaining += piles[i];

        /* ---------- 3. try every legal move ---------- */
        int best = 0;                       // best score this player can get
        int maxTake = Math.min(2 * M, n - idx); // cannot go beyond array end

        for (int k = 1; k <= maxTake; k++) {
            /* ----- 4. opponent faces idx+k with new M ----- */
            int newM = Math.max(M, k);
            int opponentBest = dfs(piles, idx + k, newM);

            /* ----- 5. maximise (what we get) = total - opponentBest ----- */
            best = Math.max(best, totalRemaining - opponentBest);
        }
        return best;
    }
}
Walk-through of the key ideas
1.totalRemaining is the sum of all stones still on the table.
Whatever we leave to the opponent, we get the rest:
ourScore = totalRemaining - opponentScore.
2.We loop over every legal count of piles k (1 … 2·M).
For each k we recursively compute the best the opponent can score from the remaining suffix.
3.We choose the k that maximises our own score (totalRemaining - opponentScore).
4.No memoisation ⇒ every call recomputes everything; clarity first, speed later.

Optimized Version with Precomputation
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // ============================================
        // STEP 1: PRE-COMPUTE SUFFIX SUMS ARRAY
        // ============================================
        // suffix[i] = sum of all stones from index i to the end
        // This allows O(1) calculation of "remaining stones" at any position
        // Example: piles = [2,7,9,4,4]
        // suffix = [26,24,17,8,4,0] where:
        //   suffix[0] = 2+7+9+4+4 = 26 (all stones)
        //   suffix[1] = 7+9+4+4 = 24 (stones from index 1 onward)
        //   suffix[2] = 9+4+4 = 17
        //   suffix[5] = 0 (no stones left)
        int[] suffix = new int[n + 1];
        // Build from right to left for efficient calculation
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }
        
        // ============================================
        // STEP 2: START THE GAME WITH DFS
        // ============================================
        // Alice starts at index 0 with initial M = 1
        return dfsOptimized(piles, suffix, 0, 1);
    }
    
    /**
     * Optimized DFS function using memoization-like approach
     * 
     * @param piles   Original array of stone piles
     * @param suffix  Precomputed suffix sums array
     * @param index   Current position in the piles (0-based index)
     * @param M       Current M value (player can take 1 to 2M piles)
     * @return        Maximum stones the current player can collect from this position
     */
    private int dfsOptimized(int[] piles, int[] suffix, int index, int M) {
        int n = piles.length;
        
        // ============================================
        // BASE CASE 1: NO PILES LEFT
        // ============================================
        // If index is beyond the array, no stones to collect
        if (index >= n) {
            return 0;
        }
        
        // ============================================
        // BASE CASE 2: CAN TAKE ALL REMAINING PILES
        // ============================================
        // If 2M >= remaining piles count, current player can take EVERYTHING
        // This is the optimal greedy move because:
        // 1. You get all remaining stones immediately
        // 2. Opponent gets ZERO stones from this segment
        // 3. No need to consider other moves when you can take everything
        if (2 * M >= n - index) {
            // suffix[index] gives us all stones from index to end in O(1)
            return suffix[index];
        }
        
        // ============================================
        // RECURSIVE CASE: TRY ALL POSSIBLE MOVES
        // ============================================
        int best = 0; // Best stones current player can get from this position
        
        // X represents how many piles the current player will take
        // X can be any integer from 1 to 2*M (inclusive)
        for (int X = 1; X <= 2 * M; X++) {
            // Boundary check: can't take more piles than exist
            if (index + X > n) {
                break;
            }
            
            // ============================================
            // KEY RECURSIVE CALL: OPPONENT'S OPTIMAL PLAY
            // ============================================
            // After current player takes X piles, game continues from index+X
            // M updates to max(M, X) for opponent's turn
            int opponentBest = dfsOptimized(piles, suffix, index + X, Math.max(M, X));
            
            // ============================================
            // ZERO-SUM GAME CALCULATION
            // ============================================
            // Critical Insight: This is a zero-sum game
            // Total stones from current position = suffix[index]
            // If opponent gets opponentBest stones from the remaining piles,
            // then current player gets: total - opponentBest
            // 
            // Why this works without tracking currentStones separately:
            // Let currentStones = stones from piles[index...index+X-1]
            // Let remainingStones = suffix[index+X]
            // Then: total = currentStones + remainingStones
            // Our stones = currentStones + (remainingStones - opponentBest)
            //            = (currentStones + remainingStones) - opponentBest
            //            = suffix[index] - opponentBest
            // So we don't need to calculate currentStones at all!
            int ourStones = suffix[index] - opponentBest;
            
            // Update best result if this X gives more stones
            best = Math.max(best, ourStones);
        }
        
        return best;
    }
}
🧮 Mathematical Foundation of the Solution
Recurrence Relation Explained:
Let f(i, M) = maximum stones current player can get starting at index i with parameter M

Base Cases:
1. f(i, M) = 0 if i >= n
2. f(i, M) = suffix[i] if 2M ≥ n-i

Recursive Case:
f(i, M) = max over X in [1, 2M] of {
    suffix[i] - f(i+X, max(M, X))
}
Example Walkthrough:
For piles = [2, 7, 9, 4, 4]:
Initial call: dfs(0, 1)
  suffix[0] = 26 (total stones)
  
  Try X=1: opponentBest = dfs(1, 1) = ?
  Try X=2: opponentBest = dfs(2, 2) = ?
  Choose max(26-dfs(1,1), 26-dfs(2,2))
🔄 Comparison with Original Unoptimized Version
AspectOriginal Native DFSOptimized VersionSum CalculationO(n) per call (loop to sum)O(1) per call (suffix array)Total ComplexityO(n × 2^n) exponentialO(n³) with memoizationMemory UsageO(n) stack spaceO(n²) with memo tableReadabilityMore intuitive logicMore efficient, same logic
⚡ Adding Memoization for Full Optimization
The current solution still has exponential time. Here's the fully optimized version with memoization:
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // 1. Precompute suffix sums
        int[] suffix = new int[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }
        
        // 2. Create memoization table
        // memo[i][M] = best result from position i with parameter M
        // Initialize with -1 to indicate "not computed"
        int[][] memo = new int[n][n + 1]; // M can be at most n
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        
        // 3. Start DFS with memoization
        return dfsWithMemo(piles, suffix, memo, 0, 1);
    }
    
    private int dfsWithMemo(int[] piles, int[] suffix, int[][] memo, int index, int M) {
        int n = piles.length;
        
        if (index >= n) return 0;
        
        // Check memoization table first
        if (memo[index][M] != -1) {
            return memo[index][M];
        }
        
        // Can take all remaining piles
        if (2 * M >= n - index) {
            memo[index][M] = suffix[index];
            return suffix[index];
        }
        
        int best = 0;
        
        for (int X = 1; X <= 2 * M; X++) {
            if (index + X > n) break;
            
            int opponentBest = dfsWithMemo(piles, suffix, memo, index + X, Math.max(M, X));
            int ourStones = suffix[index] - opponentBest;
            
            best = Math.max(best, ourStones);
        }
        
        // Store result in memo table before returning
        memo[index][M] = best;
        return best;
    }
}
🎯 Key Takeaways from the Solution
1.Suffix Sum Optimization: Precomputing suffix sums transforms O(n) operations into O(1) lookups.
2.Zero-Sum Game Property: Our stones = Total stones - Opponent's stones elegantly handles turn-taking without tracking whose turn it is.
3.State Definition: The game state is fully defined by (index, M) - no need for additional parameters.
4.Greedy Base Case: When 2M ≥ remaining piles, taking everything is always optimal.
5.Optimal Substructure: The solution for a larger problem depends on optimal solutions to smaller subproblems, making DP/memoization effective.
The commented solution shows how a seemingly complex game can be solved with a clean recurrence relation and strategic optimization.

I am curious for below solution, why we can use a "looks like" local variable 'best' instead of a global variable 'best' to recording the best result for Alice ? Usually if related to find 'maximum, best... etc' solution, we prefer using DFS pattern like 'void return + global variable' or 'if the root node not exactly recording what required, will be value return + global variable', instead here we use 'value return + local variable' but still able to resolve the find 'maximum, best... etc' solution among all solutions ? Can you explain why ? 
We are looking at a return-value DFS solution for Stone Game II. The question is why we can use a local variable best to track the maximum for the current state, and why this is sufficient without a global variable.
In this return-value DFS, each call returns the maximum stones the current player can get from the current state (index, M). The local variable best is used to track the maximum over all possible moves (choices of X) from the current state.
The key is that the function returns the value for the current player at the current state. The recursive calls return the opponent's best from the next state, and then we compute our stones as suffix[index] - opponentBest. This works because the game is zero-sum: the total stones from the current index onward is fixed, and if the opponent gets opponentBest from the remaining, then the current player gets the rest.
The local best variable is used to find the maximum among all possible moves (X) for the current state. Once we have tried all X, we return the best we can do from this state.
This is a standard pattern in game theory DP: the value of a state is the maximum (for the current player) over all moves of (current gain from the move + value of the resulting state for the opponent). However, note that in this problem, the current gain is not simply added because we are using the zero-sum property to compute our gain as total_remaining - opponent_best.
So why doesn't this need a global variable? Because the function is defined to return the maximum stones for the current player from the current state. The root call (from index 0, M=1) returns the maximum stones Alice can get from the starting state. The recursion takes care of the rest.
Let me break down the logic:
1.At each state (index, M), the current player can choose X from 1 to 2M.
2.For each X, the current player takes piles[index ... index+X-1] (which we don't explicitly sum because we use the zero-sum property).
3.Then the game continues from state (index+X, max(M, X)) with the opponent to play.
4.The opponent will play optimally from that state, so the opponent will get dfs(index+X, max(M, X)) stones (from the perspective of the opponent, that's their maximum).
5.The total stones from the current index onward is suffix[index]. So if the opponent gets opponentBest from the remaining, then the current player gets suffix[index] - opponentBest.
6.We try all X and choose the one that gives the current player the maximum, which is stored in the local variable best.
7.This value (best) is then returned as the value of the current state.
The root call returns the maximum stones Alice can get from the initial state.
So the local variable best is sufficient because it is computing the maximum for the current state, and the recursion ensures that each state's value is computed based on the optimal play of both players.
In contrast, the void return style would require a global variable because we are exploring all paths and updating a global maximum for Alice. But in the return-value style, we are using the recursion to compute the value of each state, and the root state's value is exactly what we want.
Therefore, the return-value DFS with local variable best is a more elegant and efficient way to solve this problem, and it avoids the need for a global variable.
However, note that the current solution does not have memoization. We can add memoization to avoid recomputation. The memo table would store the result for each state (index, M) so that we don't recompute the same state multiple times.
Let me add memoization to the solution:
We can create a 2D array memo[n][n+1] (since M can be at most n) and initialize it with -1. Then in dfsOptimized, we check if the state has been computed before.
Here is the memoized version:
Note: I'll adjust the function to include memoization.
Excellent question! This gets to the heart of recursive thinking versus imperative thinking. The key insight is that each recursive call represents a completely different game state with its own "best" value.
🧠 Understanding the "Local Best" vs "Global Best"
The Mental Model:
Think of each recursive call as a parallel universe where the game starts from that exact state. In each universe, there's a "best" outcome for the player whose turn it is in that universe.
Parallel Universe 1: Game starts at state (0,1) - Alice's turn
  Local best = What's best for Alice from this starting point

Parallel Universe 2: Game starts at state (2,2) - Could be Alice's or Bob's turn
  Local best = What's best for the CURRENT player in this state
Why Local best Works:
private int dfs(int index, int M) {
    // At THIS state (index, M), for the CURRENT player...
    int best = 0;  // Local to THIS state
    
    for (int X = 1; X <= 2*M; X++) {
        // Ask: "If I make move X, what's the best the opponent can do?"
        int opponentBest = dfs(index + X, max(M, X));
        
        // My stones = total remaining - opponent's best
        int myStones = suffix[index] - opponentBest;
        
        // Track the best move FOR ME at THIS state
        best = Math.max(best, myStones);
    }
    
    return best;  // Return the best I can do FROM THIS STATE
}
📊 Visualization: The Recursion Tree
Let's trace piles = [2, 7, 9, 4, 4]:
Level 0: dfs(0,1) - Alice's turn (ROOT)
  best = 0 initially
  
  Try X=1: opponentBest = dfs(1,1) = ?
    myStones = 26 - dfs(1,1)
    best = max(0, 26-dfs(1,1))
    
  Try X=2: opponentBest = dfs(2,2) = ?
    myStones = 26 - dfs(2,2)
    best = max(previous, 26-dfs(2,2))
    
  Returns: best value for Alice from start

Level 1: dfs(1,1) - Bob's turn
  best = 0 initially (Bob's local best!)
  
  Try X=1: opponentBest = dfs(2,1) = ?
    Bob's stones = 24 - dfs(2,1)  // Bob wants to maximize THIS
    best = max(0, 24-dfs(2,1))
    
  Try X=2: opponentBest = dfs(3,2) = ?
    Bob's stones = 24 - dfs(3,2)
    best = max(previous, 24-dfs(3,2))
    
  Returns: best value for Bob from state (1,1)
🔄 Comparison: Local vs Global Variable Approaches
1. Return-Value with Local Variable (Current Solution):
int dfs(state) {
    int localBest = 0;
    for (each move) {
        int result = makeMove(move);
        localBest = max(localBest, result);
    }
    return localBest;  // Best from THIS state
}
Logic: "What's the best outcome for the current player from this state?"
2. Void Return with Global Variable (Alternative):
void dfs(state, currentScore, globalBest) {
    if (gameOver) {
        globalBest = max(globalBest, currentScore);
        return;
    }
    for (each move) {
        makeMove(move);
        dfs(nextState, newScore, globalBest);
        undoMove(move);
    }
}
Logic: "Explore all paths, track the absolute best score seen."
3. Return-Value with Global Variable (Hybrid):
int dfs(state) {
    int localBest = 0;
    for (each move) {
        int result = makeMove(move);
        localBest = max(localBest, result);
        globalBest = max(globalBest, localBest);  // Track globally too
    }
    return localBest;
}
🤔 Why This Works: Mathematical Properties
Property 1: Optimal Substructure
The optimal solution at state (i,M) can be constructed from optimal solutions of subproblems (i+X, max(M,X)).
Property 2: Zero-Sum Game
Because it's zero-sum:
Alice's stones = Total stones - Bob's stones
Bob's stones = Total stones - Alice's stones
This creates a closed-form recurrence where we don't need to track absolute scores.
Property 3: Current Player Perspective
At each state, we only care: "What's best for the player whose turn it is now?" The function signature dfs(index, M) implicitly means "best for current player."
🧪 Test Your Understanding
Which approach would you use for these problems?
Problem TypeBest ApproachWhyFind maximum path sum in treeReturn-value + local maxEach subtree has its own "best path"Check if path exists to targetReturn-value (boolean)Each node returns "can reach from here?"Find ALL paths to targetVoid + global listNeed to collect all solutionsGame theory (minimax)Return-value + local bestEach state has value for current player
🔍 Deep Dive: The Recursive Contract
The function dfs(index, M) has an implicit contract:
1.Input: A game state (index, M)
2.Output: Maximum stones the current player can get from this state
3.Assumption: Both players play optimally
Because of this contract, we don't need to know:
- Whose turn it is (implicit in state)
- Current scores (irrelevant for relative advantage)
- What happened before (only future matters)
💡 The Elegance of Functional Recursion
The return-value approach is functional:
- No side effects
- Same input → same output
- Easy to memoize
- Mathematically pure
The void approach is imperative:
- Modifies external state
- Harder to reason about
- Difficult to memoize
- More prone to bugs
🎯 When to Use Which Pattern
Use Return-Value + Local Variable When:
1.Problem has optimal substructure
2.Solution can be expressed as f(state) = best over moves of g(move, f(nextState))
3.You only need the value, not the path
4.You want to add memoization
Use Void + Global Variable When:
1.Need to collect all solutions or paths
2.Problem doesn't have clean recurrence
3.Need to track additional state (like visited nodes)
4.Searching for any solution (not optimal)
📝 Revisiting Stone Game II
For Stone Game II, the recurrence is:
f(i, M) = max over X of [suffix[i] - f(i+X, max(M, X))]
This is perfect for return-value + local variable because:
1.Each state's value depends only on future states
2.We don't care about the actual sequence of moves
3.The zero-sum property gives us a clean formula
4.We can memoize f(i, M) easily
✅ Summary
The "local best" variable works because each recursive call represents a complete subproblem with its own optimal value. By composing these local optimal values using the recurrence relation, we build up to the global optimal value at the root.
This is the essence of dynamic programming thinking: breaking a problem into subproblems, solving each optimally, and combining solutions. The return-value DFS with local variable is essentially recursive DP without explicit memoization table.
The beauty is that the recursion naturally explores all possibilities, and the local best variable at each level ensures we choose the optimal move for that specific game state, which propagates up to give us the optimal result for the entire game.


What's the time and space complexity for Memoization solution ?
Excellent analysis! Let me break down the time and space complexity for your solution:
📊 Time Complexity: O(n³)
Why O(n³)?
Let's analyze step by step:
1.Number of States:
- index: 0 to n-1 → n possibilities
- M: 1 to n → n possibilities
- Total states: n × n = O(n²)
2.Work per State:
for(int X = 1; X <= 2 * M; X++) {
    // O(1) work per iteration
}
- In worst case, M ≈ n, so loop runs up to 2n iterations
- Each iteration: O(1) work (presum array gives O(1) remainSum)
- Work per state: O(n)
3.Total Time:
Total time = (Number of states) × (Work per state)
           = O(n²) × O(n)
           = O(n³)
Detailed Breakdown:
- Memo table size: n × (n+1) ≈ n² entries
- Each entry computed once (thanks to memoization)
- Computing each entry: loops up to min(2M, n-index) times
- Worst case: When index=0, M=n/2 → loop runs n times
- Total operations: Σ (over all states) of O(min(2M, n-index)) ≈ O(n³)
💾 Space Complexity: O(n²)
Breakdown:
1.Memoization Table:
Integer[][] memo = new Integer[n][n + 1];
- Size: n × (n+1) integers
- Space: O(n²)
2.Prefix Sum Array:
int[] presum = new int[n + 1];
- Size: n+1 integers
- Space: O(n)
3.Recursion Stack:
helper(piles, presum, index + X, Math.max(X, M), memo);
- Maximum depth: n (when taking 1 pile each time)
- Space: O(n)
4.Total Space:
Total space = memo table + presum array + recursion stack
            = O(n²) + O(n) + O(n)
            = O(n²)  (dominant term)
🔍 Visualizing the Complexity
For n = 100:
States: 100 × 100 = 10,000
Work per state: up to 200 iterations
Total operations: ~2 million → Fast enough
For n = 500:
States: 500 × 500 = 250,000
Work per state: up to 1,000 iterations
Total operations: ~250 million → Might be borderline
⚡ Optimization Potential
1. Reduce Memo Table Bounds:
// M can't exceed n/2 in practice (since 2M would exceed n)
Integer[][] memo = new Integer[n][n/2 + 1];  // Instead of n+1
Saves ~50% space, but still O(n²)
2. Early Pruning:
// If we already have a good result, skip some X values
if (ourStones == remainSum) {
    // Can't do better than taking everything
    memo[index][M] = remainSum;
    return remainSum;
}
📈 Comparison with Other Approaches
AlgorithmTimeSpacen LimitYour Memoized DFSO(n³)O(n²)n ≤ 200Bottom-up DPO(n³)O(n²)n ≤ 200Space-Optimized DPO(n³)O(n²)n ≤ 200Native DFS (no memo)O(2ⁿ × n²)O(n)n ≤ 20
🧮 Mathematical Derivation
The recurrence is:
T(i, M) = sum over X=1 to min(2M, n-i) of [1 + T(i+X, max(M, X))]
This expands to:
T(n) = Σ_{i=0}^{n-1} Σ_{M=1}^{n} min(2M, n-i)
Approximately:
T(n) ≈ Σ_{i=0}^{n-1} Σ_{M=1}^{n} (2M)  [in worst case]
      ≈ Σ_{i=0}^{n-1} n²
      ≈ n³
✅ Verification with Example
For piles = [2, 7, 9, 4, 4] (n=5):
Memo table accesses:
- (0,1), (0,2), (0,3), (0,4), (0,5)
- (1,1), (1,2), (1,3), (1,4)
- (2,1), (2,2), (2,3)
- (3,1), (3,2)
- (4,1)

Total states computed: 5+4+3+2+1 = 15
Each state: up to min(2M, n-i) iterations
Total operations: ~30 → Much less than 5³=125
🎯 Key Takeaways
1.Your solution is optimal for the problem structure
2.O(n³) is the best known for Stone Game II
3.The presum array optimization is crucial for O(1) remainSum
4.Memoization eliminates exponential duplication
5.Space could be reduced but O(n²) is acceptable for typical constraints
Your implementation strikes a good balance between clarity and efficiency. For LeetCode constraints (n ≤ 100), O(n³) ≈ 1,000,000 operations is perfectly fine.

How about the DP solution ?
Here's the exact mapping from your native DFS to a 2D DP solution:
🔄 Direct Mapping: Native DFS → 2D DP
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        
        // ============================================
        // STEP 1: PRE-COMPUTE PREFIX SUMS (SAME AS DFS)
        // ============================================
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + piles[i - 1];
        }
        
        // ============================================
        // STEP 2: CREATE DP TABLE
        // dp[i][M] = maximum stones current player can get
        // starting from index i with parameter M
        // THIS MAPS TO: helper(piles, presum, i, M)
        // ============================================
        int[][] dp = new int[n + 1][n + 1];  // Extra row for dp[n][*] = 0
        
        // ============================================
        // STEP 3: FILL DP TABLE BOTTOM-UP
        // Process from END to BEGINNING (reverse of recursion)
        // ============================================
        for (int i = n - 1; i >= 0; i--) {
            // Calculate total stones from i to end (for all M)
            int remainSum = presum[n] - presum[i];
            
            for (int M = 1; M <= n; M++) {
                // ============================================
                // BASE CASE 1 FROM DFS: Can take all remaining piles
                // if(2 * M >= n - index) return remainSum;
                // ============================================
                if (2 * M >= n - i) {
                    dp[i][M] = remainSum;
                    continue;
                }
                
                // ============================================
                // RECURSIVE CASE FROM DFS:
                // int best = 0;
                // for(int X = 1; X <= 2 * M; X++) {
                //     if(index + X > n) break;
                //     int opponentBest = helper(piles, presum, index + X, Math.max(X, M));
                //     int ourStones = remainSum - opponentBest;
                //     best = Math.max(best, ourStones);
                // }
                // return best;
                // ============================================
                int best = 0;
                
                for (int X = 1; X <= 2 * M; X++) {
                    if (i + X > n) break;
                    
                    // In DFS: opponentBest = helper(index + X, max(M, X))
                    // In DP: opponentBest = dp[index + X][max(M, X)]
                    int nextM = Math.max(M, X);
                    int opponentBest = dp[i + X][nextM];
                    
                    // Same formula: ourStones = remainSum - opponentBest
                    int ourStones = remainSum - opponentBest;
                    
                    best = Math.max(best, ourStones);
                }
                
                dp[i][M] = best;
            }
        }
        
        // ============================================
        // STEP 4: RETURN RESULT FOR INITIAL STATE
        // return helper(piles, presum, 0, 1);
        // ============================================
        return dp[0][1];
    }
}
📊 Mapping Table: DFS → DP
DFS ComponentDP EquivalentExplanationhelper(index, M)dp[index][M]DP table stores memoized resultsBase case: index >= n → 0dp[n][M] = 0 for all MExtra row for terminationBase case: 2M ≥ n-index → remainSumif(2M ≥ n-i) dp[i][M] = remainSumSame conditionremainSum = presum[n] - presum[index]remainSum = presum[n] - presum[i]Same calculationfor(X = 1; X <= 2M; X++)for(X = 1; X <= 2M; X++)Same loopopponentBest = helper(index+X, max(M,X))opponentBest = dp[i+X][max(M,X)]Lookup in DP tableourStones = remainSum - opponentBestourStones = remainSum - opponentBestSame formulabest = Math.max(best, ourStones)best = Math.max(best, ourStones)Same updatereturn bestdp[i][M] = bestStore result in tablereturn helper(0, 1)return dp[0][1]Return initial state
🔍 Visual Execution Flow
DFS (Top-Down) Flow:
helper(0,1)
  ↓ calls
helper(1,1)  and  helper(2,2)
  ↓ calls        ↓ calls
helper(2,1)      helper(3,2) ...
DP (Bottom-Up) Flow:
Compute dp[4][M]  (last pile)
  ↓
Compute dp[3][M]  (needs dp[4][M])
  ↓  
Compute dp[2][M]  (needs dp[3][M], dp[4][M])
  ↓
Compute dp[1][M]  (needs dp[2][M], dp[3][M])
  ↓
Compute dp[0][M]  (needs dp[1][M], dp[2][M])
🧩 Example: piles = [2, 7, 9, 4, 4]
Prefix Sum: presum = [0, 2, 9, 18, 22, 26]
DP Table Construction:
i=4 (last pile):
  remainSum = 26-22 = 4
  M=1: 2*1=2 ≥ 1 → dp[4][1]=4
  M=2: 2*2=4 ≥ 1 → dp[4][2]=4
  ...

i=3:
  remainSum = 26-18 = 8
  M=1: 2*1=2 < 2 → compute
    X=1: opponentBest=dp[4][1]=4 → ourStones=8-4=4
    X=2: opponentBest=dp[5][2]=0 → ourStones=8-0=8
    dp[3][1]=max(4,8)=8
  M=2: 2*2=4 ≥ 2 → dp[3][2]=8

i=2:
  remainSum = 26-9 = 17
  M=1: 2*1=2 < 3 → compute
    X=1: opponentBest=dp[3][1]=8 → ourStones=17-8=9
    X=2: opponentBest=dp[4][2]=4 → ourStones=17-4=13
    dp[2][1]=max(9,13)=13
  M=2: 2*2=4 ≥ 3 → dp[2][2]=17

i=1:
  remainSum = 26-2 = 24
  M=1: 2*1=2 < 4 → compute
    X=1: opponentBest=dp[2][1]=13 → ourStones=24-13=11
    X=2: opponentBest=dp[3][2]=8 → ourStones=24-8=16
    dp[1][1]=max(11,16)=16
  M=2: compute...
  
i=0:
  remainSum = 26-0 = 26
  M=1: 2*1=2 < 5 → compute
    X=1: opponentBest=dp[1][1]=16 → ourStones=26-16=10
    X=2: opponentBest=dp[2][2]=17 → ourStones=26-17=9
    dp[0][1]=max(10,9)=10
Result: dp[0][1] = 10 ✓
⚡ Optimized Version with Space Reduction
We can optimize space by noticing we only need the next few rows:
class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + piles[i - 1];
        }
        
        // dp[i][M] - we only need current and next rows
        int[][] dp = new int[n + 1][n + 1];
        
        for (int i = n - 1; i >= 0; i--) {
            int remainSum = presum[n] - presum[i];
            
            // The maximum useful M at position i
            int maxM = Math.min(n, (n - i + 1) / 2 + 1);
            
            for (int M = 1; M <= maxM; M++) {
                if (2 * M >= n - i) {
                    dp[i][M] = remainSum;
                    continue;
                }
                
                int best = 0;
                for (int X = 1; X <= 2 * M; X++) {
                    if (i + X > n) break;
                    int nextM = Math.max(M, X);
                    int opponentBest = dp[i + X][nextM];
                    best = Math.max(best, remainSum - opponentBest);
                }
                dp[i][M] = best;
            }
        }
        
        return dp[0][1];
    }
}
📈 Complexity Analysis
MetricNative DFS2D DPImprovementTimeO(2ⁿ × n²)O(n³)Exponential → PolynomialSpaceO(n) stackO(n²) tableMore memory, but enables memoizationPractical n≤ 20≤ 20010x larger
🎯 Key Insight
The DP solution is exactly the iterative version of memoized DFS:
- DFS with memo: dp[i][M] = memo.getOrCompute(i, M, computeFunction)
- Bottom-up DP: Pre-compute dp[i][M] for all i,M in optimal order
Both compute the same recurrence, just in different orders!



Refer to
L877.Stone Game (Ref.L1690)
L1406.Stone Game III (Ref.L877,L1140,L1690)
L1510.Stone Game IV (Ref.L877,L1140,L1406,L1690)
L1563.Stone Game V (Ref.L877,L1140,L1406,L1510)
L1686.Stone Game VI
L1690.Stone Game VII (Ref.L1770)
L1872.Stone Game VIII
DFS return logic
