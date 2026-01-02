https://leetcode.com/problems/stone-game-viii/description/
Alice and Bob take turns playing a game, with Alice starting first.
There are n stones arranged in a row. On each player's turn, while the number of stones is more than one, they will do the following:
1.Choose an integer x > 1, and remove the leftmost x stones from the row.
2.Add the sum of the removed stones' values to the player's score.
3.Place a new stone, whose value is equal to that sum, on the left side of the row.
The game stops when only one stone is left in the row.
The score difference between Alice and Bob is (Alice's score - Bob's score). Alice's goal is to maximize the score difference, and Bob's goal is the minimize the score difference.
Given an integer array stones of length n where stones[i] represents the value of the ith stone from the left, return the score difference between Alice and Bob if they both play optimally.
 
Example 1:
Input: stones = [-1,2,-3,4,-5]
Output: 5
Explanation:
- Alice removes the first 4 stones, adds (-1) + 2 + (-3) + 4 = 2 to her score, and places a stone of  value 2 on the left. stones = [2,-5].
- Bob removes the first 2 stones, adds 2 + (-5) = -3 to his score, and places a stone of value -3 on  the left. stones = [-3].
The difference between their scores is 2 - (-3) = 5.

Example 2:
Input: stones = [7,-6,5,10,5,-2,-6]
Output: 13
Explanation:
- Alice removes all stones, adds 7 + (-6) + 5 + 10 + 5 + (-2) + (-6) = 13 to her score, and places a  stone of value 13 on the left. stones = [13].
The difference between their scores is 13 - 0 = 13.

Example 3:
Input: stones = [-10,-12]
Output: -22
Explanation:- Alice can only make one move, which is to remove both stones. She adds (-10) + (-12) = -22 to her  score and places a stone of value -22 on the left. stones = [-22].The difference between their scores is (-22) - 0 = -22.
 
Constraints:
- n == stones.length
- 2 <= n <= 10^5
- -10^4 <= stones[i] <= 10^4
--------------------------------------------------------------------------------
Attempt 1: 2025-12-31
Solution 1: Native DFS (180 min, TLE 9/78)
The most tricky part of this problem is "Place a new stone, whose value is equal to that sum, on the left side of the row.", this means no matter in which round who take the move, will always suitable to use 'presum[index]' to cover its take
class Solution {
    public int stoneGameVIII(int[] stones) {
        // Build prefix sum array to quickly get sum of first x stones
        int n = stones.length;
        int[] presum = new int[n];
        presum[0] = stones[0];
        for(int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + stones[i];
        }
        // Alice goes first, and must take at least 2 stones (x > 1)
        // So she can choose to take 2, 3, 4, ..., n stones
        // We start DFS from index 1 (which means taking first 2 stones: indices 0,1)
        return helper(presum, 1, n);
    }

    // Pure recursive DFS - returns the maximum score difference 
    // (current player's total score - opponent's total score)
    // starting from position 'index' (0-indexed)
    private int helper(int[] presum, int index, int n) {
        // Base case: if only one stone remains in the game
        // This happens when index == n-1 (we've taken first n-1 original stones)
        if(index == n - 1) {
            // Current player takes the final move
            return presum[index];
        }
        // Try all possible moves from current position
        // At position 'index', current player can choose to take stones
        // from current boundary up to any position from 'index' to 'n-1'
        int best = Integer.MIN_VALUE;
        for(int i = index; i < n; i++) {
            // If current player takes up to index i, they get prefix[i] points
            int curScore = presum[i];
            // After this move, if i == n-1, game ends
            if(i == n - 1) {
                best = Math.max(best, curScore);
            } else {
                // Otherwise, opponent plays optimally from position i+1
                int opponentBest = helper(presum, i + 1, n);
                // Current player's net advantage = currentScore - opponent's advantage
                best = Math.max(best, curScore - opponentBest);
            }
        }
        return best;
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Solution 2: DFS + Memoization (10 min, TLE 61/78)
class Solution {
    public int stoneGameVIII(int[] stones) {
        // Build prefix sum array to quickly get sum of first x stones
        int n = stones.length;
        int[] presum = new int[n];
        presum[0] = stones[0];
        for(int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + stones[i];
        }
        Integer[] memo = new Integer[n + 1];
        // Alice goes first, and must take at least 2 stones (x > 1)
        // So she can choose to take 2, 3, 4, ..., n stones
        // We start DFS from index 1 (which means taking first 2 stones: indices 0,1)
        return helper(presum, 1, n, memo);
    }

    // Pure recursive DFS - returns the maximum score difference 
    // (current player's total score - opponent's total score)
    // starting from position 'index' (0-indexed)
    private int helper(int[] presum, int index, int n, Integer[] memo) {
        // Base case: if only one stone remains in the game
        // This happens when index == n-1 (we've taken first n-1 original stones)
        if(index == n - 1) {
            // Current player takes the final move
            return presum[index];
        }
        if(memo[index] != null) {
            return memo[index];
        }
        // Try all possible moves from current position
        // At position 'index', current player can choose to take stones
        // from current boundary up to any position from 'index' to 'n-1'
        int best = Integer.MIN_VALUE;
        for(int i = index; i < n; i++) {
            // If current player takes up to index i, they get prefix[i] points
            int curScore = presum[i];
            // After this move, if i == n-1, game ends
            if(i == n - 1) {
                best = Math.max(best, curScore);
            } else {
                // Otherwise, opponent plays optimally from position i+1
                int opponentBest = helper(presum, i + 1, n, memo);
                // Current player's net advantage = currentScore - opponent's advantage
                best = Math.max(best, curScore - opponentBest);
            }
        }
        return memo[index] = best;
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 3: 1D DP (10 min, TLE 64/78)
class Solution {
    public int stoneGameVIII(int[] stones) {
        // Build prefix sum array to quickly get sum of first x stones
        int n = stones.length;
        int[] presum = new int[n];
        presum[0] = stones[0];
        for(int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + stones[i];
        }
        // Alice goes first, and must take at least 2 stones (x > 1)
        // So she can choose to take 2, 3, 4, ..., n stones
        // We start DFS from index 1 (which means taking first 2 stones: indices 0,1)
        int[] dp = new int[n];
        dp[n - 1] = presum[n - 1];
        for(int index = n - 2; index >= 1; index--) {
            // Try all possible moves from current position
            // At position 'index', current player can choose to take stones
            // from current boundary up to any position from 'index' to 'n-1'
            int best = Integer.MIN_VALUE;
            for(int i = index; i < n; i++) {
                // If current player takes up to index i, they get prefix[i] points
                int curScore = presum[i];
                // After this move, if i == n-1, game ends
                if(i == n - 1) {
                    best = Math.max(best, curScore);
                } else {
                    // Otherwise, opponent plays optimally from position i+1
                    int opponentBest = dp[i + 1];
                    // Current player's net advantage = currentScore - opponent's advantage
                    best = Math.max(best, curScore - opponentBest);
                }
            }
            dp[index] = best;
        }
        return dp[1];
    }
}

Time Complexity: O(n^2)
Space Complexity: O(n)

Solution 4: Single variable DP (30 min)
class Solution {
    public int stoneGameVIII(int[] stones) {
        // Build prefix sum array to quickly get sum of first x stones
        int n = stones.length;
        int[] presum = new int[n];
        presum[0] = stones[0];
        for(int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + stones[i];
        }
        // dp represents the best score difference for the current player
        // starting from the current index. We start from the base case.
        // Base case: if we start at the last index, the score is presum[n-1].
        int dp = presum[n - 1];
        // We work backwards from n-2 down to 1.
        // Why down to 1? Because the first move must take at least 2 stones,
        // so the first possible start index is 1.
        for(int index = n - 2; index >= 1; index--) {
            // The line dp = Math.max(dp, presum[i] - dp) is the optimized 
            // form of the for loop in the DFS. The first dp in the max function 
            // represents the best outcome from all choices i+1 to n-1 (i.e., dp[i+1]). 
            // The term presum[i] - dp represents the outcome of choosing to 
            // stop at index i. Taking the maximum of these two is exactly 
            // what the DFS does by trying all possibilities and taking the best.
            // At each step, the current player can choose to take up to index i.
            // The outcome of that choice is (presum[i] - dp).
            // But they will choose the best possible outcome, which is the maximum
            // between taking now or the best outcome already calculated (which
            // represents taking later).
            // This directly maps to the max logic in the DFS.
            dp = Math.max(dp, presum[index] - dp);
        }
        return dp;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to QWEN
How Native DFS works ?
Perfect! Here's a pure DFS solution without any memoization that directly follows the game rules as described in the problem:
According to the problem, "each time we take the leftmost x stones, add their sum to our score, and then put a stone with this [sum] on the left side" , and "the game stops when only one stone is left in the row" .
class Solution {
    public int stoneGameVIII(int[] stones) {
        // Build prefix sum array to quickly get sum of first x stones
        int n = stones.length;
        int[] prefix = new int[n];
        prefix[0] = stones[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }
        
        // Alice goes first, and must take at least 2 stones (x > 1)
        // So she can choose to take 2, 3, 4, ..., n stones
        // We start DFS from index 1 (which means taking first 2 stones: indices 0,1)
        return dfs(1, prefix, n);
    }
    
    // Pure recursive DFS - returns the maximum score difference 
    // (current player's total score - opponent's total score)
    // starting from position 'pos' (0-indexed)
    private int dfs(int pos, int[] prefix, int n) {
        // Base case: if only one stone remains in the game
        // This happens when pos == n-1 (we've taken first n-1 original stones)
        if (pos == n - 1) {
            return prefix[pos]; // Current player takes the final move
        }
        
        // Try all possible moves from current position
        // At position 'pos', current player can choose to take stones
        // from current boundary up to any position from 'pos' to 'n-1'
        int bestScoreDiff = Integer.MIN_VALUE;
        
        for (int i = pos; i < n; i++) {
            // If current player takes up to index i, they get prefix[i] points
            int currentScore = prefix[i];
            
            // After this move, if i == n-1, game ends
            if (i == n - 1) {
                bestScoreDiff = Math.max(bestScoreDiff, currentScore);
            } else {
                // Otherwise, opponent plays optimally from position i+1
                int opponentScoreDiff = dfs(i + 1, prefix, n);
                // Current player's net advantage = currentScore - opponent's advantage
                bestScoreDiff = Math.max(bestScoreDiff, currentScore - opponentScoreDiff);
            }
        }
        
        return bestScoreDiff;
    }
}
How this pure DFS works:
1.Game State: The parameter pos represents the current leftmost boundary of available stones. Initially, pos = 1 because Alice must take at least 2 stones (x > 1) .
2.Move Options: At any state pos, the current player can choose to take any number of stones from the current boundary to the end (positions pos through n-1).
3.Scoring: When taking stones up to index i, the player scores prefix[i] points, and "place a stone with value equal to that sum on the left side" .
4.Recursion: After the current player's move, the opponent faces the same decision problem starting from position i+1.
5.Optimal Play: Each player chooses the move that maximizes their score difference (their score minus opponent's score).
This solution has exponential time complexity O(2^n) but perfectly demonstrates the recursive game theory structure without any optimization.

How DFS memoization solution works ?
Here is your native DFS solution with memoization added. The key is to cache the result of each dfs(pos) call, as the optimal score difference from any given position pos is always the same, regardless of how we got there.
class Solution {
    private Integer[] memo; // Memoization array

    public int stoneGameVIII(int[] stones) {
        // Build prefix sum array to quickly get sum of first x stones
        int n = stones.length;
        int[] prefix = new int[n];
        prefix[0] = stones[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + stones[i];
        }
        
        // Initialize memo array
        memo = new Integer[n];
        
        // Alice goes first, and must take at least 2 stones (x > 1)
        // So she can choose to take 2, 3, 4, ..., n stones
        // We start DFS from index 1 (which means taking first 2 stones: indices 0,1)
        return dfs(1, prefix, n);
    }
    
    // Memoized recursive DFS - returns the maximum score difference 
    // (current player's total score - opponent's total score)
    // starting from position 'pos' (0-indexed)
    private int dfs(int pos, int[] prefix, int n) {
        // Base case: if only one stone remains in the game
        // This happens when pos == n-1 (we've taken first n-1 original stones)
        if (pos == n - 1) {
            return prefix[pos]; // Current player takes the final move
        }
        
        // Check memoization cache
        if (memo[pos] != null) {
            return memo[pos];
        }
        
        // Try all possible moves from current position
        // At position 'pos', current player can choose to take stones
        // from current boundary up to any position from 'pos' to 'n-1'
        int bestScoreDiff = Integer.MIN_VALUE;
        
        for (int i = pos; i < n; i++) {
            // If current player takes up to index i, they get prefix[i] points
            int currentScore = prefix[i];
            
            // After this move, if i == n-1, game ends
            if (i == n - 1) {
                bestScoreDiff = Math.max(bestScoreDiff, currentScore);
            } else {
                // Otherwise, opponent plays optimally from position i+1
                int opponentScoreDiff = dfs(i + 1, prefix, n);
                // Current player's net advantage = currentScore - opponent's advantage
                bestScoreDiff = Math.max(bestScoreDiff, currentScore - opponentScoreDiff);
            }
        }
        
        // Store result in memo before returning
        memo[pos] = bestScoreDiff;
        return bestScoreDiff;
    }
}
This solution now uses memoization to cache the result for each state pos, which prevents redundant calculations. Each state pos is computed only once, reducing the time complexity significantly .

How 1D DP solution works ?
You're absolutely right! Let me create the exact 1D DP mapping that directly corresponds to your DFS function.
Looking at your DFS:
- helper(presum, index, n) returns the best score difference starting from index
- Base case: index == n-1 returns presum[n-1]
- For other indices: tries all i from index to n-1 and takes the maximum
The direct 1D DP mapping is:
class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        // Build prefix sum array
        int[] presum = new int[n];
        presum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + stones[i];
        }

        // dp[i] represents the maximum score difference (current player - opponent)
        // when the game starts at index i (exactly like helper(presum, i, n))
        int[] dp = new int[n];
        
        // Base case: when index == n-1
        // This directly maps to: if(index == n - 1) { return presum[index]; }
        dp[n - 1] = presum[n - 1];
        
        // Fill DP table from right to left (bottom-up)
        // This replaces the recursive calls in DFS
        for (int index = n - 2; index >= 1; index--) {
            // This directly maps to the for loop in DFS:
            // for(int i = index; i < n; i++) {
            //     int curScore = presum[i];
            //     if(i == n - 1) {
            //         best = Math.max(best, curScore);
            //     } else {
            //         int opponentBest = helper(presum, i + 1, n);
            //         best = Math.max(best, curScore - opponentBest);
            //     }
            // }
            
            int best = Integer.MIN_VALUE;
            for (int i = index; i < n; i++) {
                int curScore = presum[i];
                if (i == n - 1) {
                    best = Math.max(best, curScore);
                } else {
                    // opponentBest = helper(presum, i + 1, n) 
                    // which is now stored in dp[i + 1]
                    int opponentBest = dp[i + 1];
                    best = Math.max(best, curScore - opponentBest);
                }
            }
            dp[index] = best;
        }
        
        // Return dp[1] which corresponds to helper(presum, 1, n)
        return dp[1];
    }
}
This is the exact 1D DP mapping of your DFS solution:
1.State mapping: dp[index] ↔ helper(presum, index, n)
2.Base case mapping: dp[n-1] = presum[n-1] ↔ if(index == n - 1) return presum[index]
3.Transition mapping: The inner loop in DP exactly replicates the for loop logic in DFS
4.Recursive call mapping: helper(presum, i + 1, n) ↔ dp[i + 1]
5.Final answer mapping: return dp[1] ↔ return helper(presum, 1, n)
This solution has O(n²) time complexity and O(n) space complexity, which is the direct DP equivalent of your memoized DFS. The space-optimized version I showed earlier is a further optimization that leverages the mathematical property that we only need to track the maximum value seen so far.

How DFS convert to single variable DP ?
Yes, the native DFS solution can be converted to a bottom-up DP solution. The key insight is to notice that the DFS tries all possible splits from the current index to the end, but we can optimize this by working backwards and keeping track of the best possible outcome.
From DFS to Bottom-Up DP
Let's analyze what the DFS is doing:
1.helper(presum, index, n) returns the best score difference (current player - opponent) when the game starts at index.
2.To calculate helper(index), it loops from i = index to n-1.
- If i == n-1, the result is presum[n-1].
- Otherwise, the result is presum[i] - helper(i+1).
3.It takes the maximum of all these possibilities.
Now, in a bottom-up approach, we start from the base case and build our way up.
- Base Case: dp[n-1] = presum[n-1].
- This directly maps to the DFS base case: if(index == n - 1) { return presum[index]; }.
- Recurrence: For any index from n-2 down to 1, we need to compute the maximum value of presum[i] - dp[i+1] for all i from index to n-1.
However, if we implement this recurrence directly, we would have a nested loop (for index and then for i), resulting in O(n²) time, which is what the naive DFS with memoization also gives.
The crucial optimization for a true bottom-up DP is to realize that we don't need to recalculate the maximum every time.
Let's define dp[i] as the maximum score difference the current player can achieve when the game starts at index i.
We can see that:
dp[i] = max(presum[i] - dp[i+1], presum[i+1] - dp[i+2], ..., presum[n-1])
Now, if we look at dp[i+1]:
dp[i+1] = max(presum[i+1] - dp[i+2], ..., presum[n-1])
This means we can rewrite dp[i] as:
dp[i] = max(presum[i] - dp[i+1], dp[i+1])
This is the core of the efficient DP. But we can go one step further. Notice that the final answer is dp[1].
We can use a single variable to keep track of the maximum value we've seen so far as we iterate from the end.
The Bottom-Up DP Solution
Here is the exact bottom-up DP solution that maps the logic of your DFS but in an iterative, optimized way:
class Solution {
    public int stoneGameVIII(int[] stones) {
        int n = stones.length;
        // Build prefix sum array
        int[] presum = new int[n];
        presum[0] = stones[0];
        for (int i = 1; i < n; i++) {
            presum[i] = presum[i - 1] + stones[i];
        }

        // dp represents the best score difference for the current player
        // starting from the current index. We start from the base case.
        // Base case: if we start at the last index, the score is presum[n-1].
        int dp = presum[n - 1];

        // We work backwards from n-2 down to 1.
        // Why down to 1? Because the first move must take at least 2 stones,
        // so the first possible start index is 1.
        for (int i = n - 2; i >= 1; i--) {
            // At each step, the current player can choose to take up to index i.
            // The outcome of that choice is (presum[i] - dp).
            // But they will choose the best possible outcome, which is the maximum
            // between taking now or the best outcome already calculated (which
            // represents taking later).
            // This directly maps to the max logic in the DFS.
            dp = Math.max(dp, presum[i] - dp);
        }

        return dp;
    }
}
How this maps directly to your DFS:
1.Initial State: dp = presum[n-1] is the direct translation of the DFS base case if(index == n - 1) return presum[index];.
2.Backward Iteration: The loop for (int i = n - 2; i >= 1; i--) replaces the recursive calls that start from 1 and go deeper. We build the solution from the known end state back to the start.
3.State Transition: The line dp = Math.max(dp, presum[i] - dp) is the optimized form of the for loop in the DFS. The first dp in the max function represents the best outcome from all choices i+1 to n-1 (i.e., dp[i+1]). The term presum[i] - dp represents the outcome of choosing to stop at index i. Taking the maximum of these two is exactly what the DFS does by trying all possibilities and taking the best.
This bottom-up solution has O(n) time and O(1) extra space (not counting the prefix sum array), which is the most efficient way to solve this problem . The core idea is that the naive min-max DP is O(n²), but the problem's structure allows for this critical optimization .


Refer to
L877.Stone Game (Ref.L1690)
L1140.Stone Game II (Ref.L877,L1690)
L1406.Stone Game III (Ref.L877,L1140,L1690)
L1510.Stone Game IV (Ref.L877,L1140,L1406,L1690)
L1563.Stone Game V (Ref.L877,L1140,L1406,L1510)
L1686.Stone Game VI
L1690.Stone Game VII (Ref.L1770)
2029

