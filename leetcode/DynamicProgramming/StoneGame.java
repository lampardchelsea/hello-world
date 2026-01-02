https://leetcode.com/problems/stone-game/description/
Alice and Bob play a game with piles of stones. There are an even number of piles arranged in a row, and each pile has a positive integer number of stones piles[i].
The objective of the game is to end with the most stones. The total number of stones across all the piles is odd, so there are no ties.
Alice and Bob take turns, with Alice starting first. Each turn, a player takes the entire pile of stones either from the beginning or from the end of the row. This continues until there are no more piles left, at which point the person with the most stones wins.
Assuming Alice and Bob play optimally, return true if Alice wins the game, or false if Bob wins.
 
Example 1:
Input: piles = [5,3,4,5]
Output: true
Explanation: 
Alice starts first, and can only take the first 5 or the last 5.
Say she takes the first 5, so that the row becomes [3, 4, 5].
If Bob takes 3, then the board is [4, 5], and Alice takes 5 to win with 10 points.
If Bob takes the last 5, then the board is [3, 4], and Alice takes 4 to win with 9 points.
This demonstrated that taking the first 5 was a winning move for Alice, so we return true.

Example 2:
Input: piles = [3,7,2,3]
Output: true
 
Constraints:
- 2 <= piles.length <= 500
- piles.length is even.
- 1 <= piles[i] <= 500
- sum(piles[i]) is odd.
--------------------------------------------------------------------------------
Attempt 1: 2025-12-24
Solution 1: Native DFS (30 min)
This problem equals to "Find if Alice's maximum advantage (to Bob) is positive", which means 'if Alice's best score > Bob's best score' exist.
Style 1:  void return with global variable to record  (TLE 26/46)
class Solution {
    int bestAlice = 0;
    public boolean stoneGame(int[] piles) {
        int[] result = new int[1];
        helper(piles, 0, piles.length - 1, 0, 0, 0, result);
        return result[0] > 0;
    }

    private void helper(int[] piles, int left, int right, int aliceScore, int bobScore, int isAliceTurn, int[] result) {
        if(left > right) {
            // Game ended, check if Alice wins
            if(aliceScore > bobScore) {
                result[0] = 1;
            }
            return;
        }
        if(isAliceTurn == 0) {
            // Alice take left
            helper(piles, left + 1, right, aliceScore + piles[left], bobScore, 1, result);
            // Alice take right
            helper(piles, left, right - 1, aliceScore + piles[right], bobScore, 1, result);
        } else {
            // Bob take left
            helper(piles, left + 1, right, aliceScore, bobScore + piles[left], 0, result);
            // Bob take right
            helper(piles, left, right - 1, aliceScore, bobScore + piles[right], 0, result);
        }
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Style 2:  boolean return (TLE 26/46)
This solution also have a disadvantage as it is NOT easy to covert into Memoization solution because it has too many variables (left, right, aliceScore, bobScore, isAliceTurn) need to memo, to create a memo it requires 5D memo, instead we have to use HashMap + String key, its not a good pattern
class Solution {
    public boolean stoneGame(int[] piles) {
        return helper(piles, 0, piles.length - 1, 0, 0, 0);
    }

    private boolean helper(int[] piles, int left, int right, int aliceScore, int bobScore, int isAliceTurn) {
        if(left > right) {
            return aliceScore > bobScore;
        }
        if(isAliceTurn == 0) {
            // Alice can win by either move
            return helper(piles, left + 1, right, aliceScore + piles[left], bobScore, 1) 
                || helper(piles, left, right - 1, aliceScore + piles[right], bobScore, 1);
        } else {
            // Bob tries to block all paths
            return helper(piles, left + 1, right, aliceScore, bobScore + piles[left], 0) 
                && helper(piles, left, right - 1, aliceScore, bobScore + piles[right], 0);
        }
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Style 3:  int return 
class Solution {
    public boolean stoneGame(int[] piles) {
        return helper(piles, 0, piles.length - 1) > 0;
    }

    private int helper(int[] piles, int left, int right) {
        if(left > right) {
            return 0;
        }
        int aliceTakeLeft = piles[left] - helper(piles, left + 1, right);
        int aliceTakeRight = piles[right] - helper(piles, left, right - 1);
        return Math.max(aliceTakeLeft, aliceTakeRight);
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Solution 2: DFS + Memoization (10 min)
Style 1: boolean return (TLE, 32/46)
Since we cannot use 5D memoization way, we use HashMap + String key instead, but still time out
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        Map<String, Boolean> memo = new HashMap<>();
        return helper(piles, 0, n - 1, 0, 0, 0, memo);
    }

    private boolean helper(int[] piles, int left, int right, int aliceScore, int bobScore, int isAliceTurn, Map<String, Boolean> memo) {
        if(left > right) {
            return aliceScore > bobScore;
        }
        String key = left + "_" + right + "_" + aliceScore + "_" + bobScore + "_" + isAliceTurn;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        if(isAliceTurn == 0) {
            // Alice can win by either move
            memo.put(key, (helper(piles, left + 1, right, aliceScore + piles[left], bobScore, 1, memo) 
                || helper(piles, left, right - 1, aliceScore + piles[right], bobScore, 1, memo)));
        } else {
            // Bob tries to block all paths
            memo.put(key, (helper(piles, left + 1, right, aliceScore, bobScore + piles[left], 0, memo) 
                && helper(piles, left, right - 1, aliceScore, bobScore + piles[right], 0, memo)));
        }
        return memo.get(key);
    }
}


Time Complexity: O(n^2 × S^2)
Space Complexity: O(n^2 × S^2)
Let:
n = number of piles
S = sum of all piles (S = sum(piles[i]))
Each state is defined by:
left: 0 to n-1 (n possibilities)
right: 0 to n-1 (n possibilities)
aliceScore: 0 to S (S+1 possibilities)
bobScore: 0 to S (S+1 possibilities)
isAliceTurn: 0 or 1 (2 possibilities)
Total possible states: O(n² × S² × 2) = O(n² × S²)

Style 2:  int return 
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        Integer[][] memo = new Integer[n][n];
        return helper(piles, 0, piles.length - 1, memo) > 0;
    }

    private int helper(int[] piles, int left, int right, Integer[][] memo) {
        if(left > right) {
            return 0;
        }
        if(memo[left][right] != null) {
            return memo[left][right];
        }
        int aliceTakeLeft = piles[left] - helper(piles, left + 1, right, memo);
        int aliceTakeRight = piles[right] - helper(piles, left, right - 1, memo);
        return memo[left][right] = Math.max(aliceTakeLeft, aliceTakeRight);
    }
}

Time Complexity: O(n^2)
Space Complexity: O(n^2)

Solution 3: 2D DP (10 min)
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];
        for(int len = 1; len <= n; len++) {
            for(int left = n - len; left >= 0; left--) {
                int right = left + len - 1;
                if(len == 1) {
                    dp[left][right] = piles[left];
                } else {
                    dp[left][right] = Math.max(piles[left] - dp[left + 1][right], piles[right] - dp[left][right - 1]);
                }
            }
        }
        return dp[0][n - 1] > 0;
    }
}

Time Complexity: O(n^2)
Space Complexity: O(n^2)


Refer to Deepseek
For Leetcode 877. Stone Game, let me show you both approaches.
Problem Summary
- Two players (Alice & Bob) take turns taking stones from either end
- Both play optimally
- Alice starts first
- Return whether Alice can win
1. Native DFS with Void Return (Track Scores)
class Solution {
    public boolean stoneGame(int[] piles) {
        int[] result = new int[1]; // Track Alice's advantage
        dfs(piles, 0, piles.length - 1, 0, 0, true, result);
        return result[0] > 0;
    }
    
    private void dfs(int[] piles, int left, int right, 
                    int aliceScore, int bobScore, 
                    boolean isAliceTurn, int[] result) {
        if (left > right) {
            // Game ended, check if Alice wins
            if (aliceScore > bobScore) {
                result[0] = 1;
            }
            return;
        }
        
        if (isAliceTurn) {
            // Alice takes left
            dfs(piles, left + 1, right, aliceScore + piles[left], 
                bobScore, false, result);
            
            // Alice takes right  
            dfs(piles, left, right - 1, aliceScore + piles[right], 
                bobScore, false, result);
        } else {
            // Bob takes left
            dfs(piles, left + 1, right, aliceScore, 
                bobScore + piles[left], true, result);
            
            // Bob takes right
            dfs(piles, left, right - 1, aliceScore, 
                bobScore + piles[right], true, result);
        }
    }
}
2. Another style of Native DFS with Void Return (Track Scores)
class Solution {
    private int bestAlice;   // best score Alice can get from the current state

    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        bestAlice = Integer.MIN_VALUE;
        dfs(piles, 0, n - 1, 0, 0, true);
        int total = java.util.Arrays.stream(piles).sum();
        return bestAlice > total - bestAlice;   // Alice > Bob
    }

    // l, r   : current sub-array bounds
    // alice  : Alice's score so far
    // bob    : Bob's score so far
    // turn   : true -> Alice's turn
    private void dfs(int[] p, int l, int r, int alice, int bob, boolean turn) {
        if (l > r) {                 // base case: no stones left
            bestAlice = Math.max(bestAlice, alice);
            return;
        }
        if (turn) {                  // Alice wants to maximise her score
            dfs(p, l + 1, r, alice + p[l], bob, false);
            dfs(p, l, r - 1, alice + p[r], bob, false);
        } else {                     // Bob wants to minimise Alice's score
            dfs(p, l + 1, r, alice, bob + p[l], true);
            dfs(p, l, r - 1, alice, bob + p[r], true);
        }
    }
}

3. Cleaner Native DFS with Integer Return (Recommended)
class Solution {
    public boolean stoneGame(int[] piles) {
        // Return true if Alice's advantage > 0
        return helper(piles, 0, piles.length - 1) > 0;
    }
    
    // Returns: maximum advantage current player can achieve
    // Positive = advantage for current player
    // Negative = disadvantage for current player
    private int helper(int[] piles, int left, int right) {
        if (left > right) return 0;
        
        // Current player can take left or right
        int takeLeft = piles[left] - helper(piles, left + 1, right);
        int takeRight = piles[right] - helper(piles, left, right - 1);
        
        return Math.max(takeLeft, takeRight);
    }
}
4. Most Efficient DP Solution
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        // dp[i][j] = maximum advantage the current player can get
        // from piles[i...j]
        int[][] dp = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i]; // Only one pile, take it
        }
        
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                // Current player takes either left or right
                dp[i][j] = Math.max(
                    piles[i] - dp[i + 1][j],
                    piles[j] - dp[i][j - 1]
                );
            }
        }
        
        return dp[0][n - 1] > 0;
    }
}
Key Differences:
Boolean/Int Return Approach (Better):
- Returns the advantage the current player can achieve
- Simpler: currentPlayerScore - nextPlayerScore
- Handles both players implicitly through subtraction
- More elegant and concise
Void Return Approach:
- Explicitly tracks Alice's and Bob's scores
- Uses a result array or global variable
- More verbose and harder to memoize
- Less efficient due to extra parameters
For 877. Stone Game, you should know:
1.Mathematically: Alice always wins with even piles (proven mathematically)
2.Simple solution: return true; (always works for given constraints)
3.For learning: Use the integer return DFS to understand the pattern
The integer return approach is what you'll commonly see for these minimax game problems, as it directly leads to memoization and DP solutions.

Can you explain why in below void return Native DFS solution, why we record 'result' by using array type variable as 'int[] result' ? Is that because we have to use an object type instead of primitive type to make sure we can persist the any one time 'set up to 1' on this variable through DFS ?
We are using int[] result = new int[1] because in Java, primitive types (like int) are passed by value, while objects (including arrays) are passed by reference (the reference is passed by value).
In the DFS method, we want to update the result when we find a winning condition for Alice. If we used a primitive int and passed it to the recursive calls, each call would have its own copy and the update in one branch would not be visible to the caller.
By using an array (which is an object), we pass the reference to the same array to every recursive call, so when we set result[0] = 1 in one of the base cases, that change is visible to the original caller (the stoneGame method).
However, note that the above void DFS solution is not efficient because it explores all possible game paths without memoization and does not prune. Also, it sets the result to 1 if there exists at least one path where Alice wins, but it does not stop the recursion early. We could improve by returning early if we already found a win, but in this case, the method is designed to exhaustively search.
Alternatively, we could use a class-level variable or a mutable container (like an AtomicInteger or a custom class with an int field) to achieve the same effect.
But note: the problem is known that Alice always wins for even number of piles (which is given in the problem constraints, the length of piles is even). So the optimal solution is just return true;. However, for the sake of understanding the DFS and DP, we are exploring the recursive solution.
Let me also note that the void DFS method above is not the most efficient way to solve this problem because it does not use memoization and has exponential time. We can improve it by using memoization or converting to DP.
But to answer your question specifically: we use int[] result to have a mutable integer that can be updated in the recursive calls and the change persists.
However, note that in this specific void DFS, we are only setting the result to 1 when Alice wins, but we are not stopping the recursion when we find a win. We could improve by having the DFS return a boolean and stopping early, but then we would not need the result array.
Here is an alternative boolean-returning DFS that stops when a win is found:
class Solution {
    public boolean stoneGame(int[] piles) {
        // Alice starts first, so we check if she can win.
        return canAliceWin(piles, 0, piles.length-1, 0, 0, true);
    }
    
    private boolean canAliceWin(int[] piles, int left, int right, int aliceScore, int bobScore, boolean isAliceTurn) {
        if (left > right) {
            return aliceScore > bobScore;
        }
        
        if (isAliceTurn) {
            // If Alice can win by taking left or right, return true.
            if (canAliceWin(piles, left+1, right, aliceScore + piles[left], bobScore, false)) {
                return true;
            }
            if (canAliceWin(piles, left, right-1, aliceScore + piles[right], bobScore, false)) {
                return true;
            }
            return false;
        } else {
            // Bob's turn: he will try to make Alice lose.
            // For Alice to win, she must survive both of Bob's choices.
            if (!canAliceWin(piles, left+1, right, aliceScore, bobScore + piles[left], true)) {
                return false;
            }
            if (!canAliceWin(piles, left, right-1, aliceScore, bobScore + piles[right], true)) {
                return false;
            }
            return true;
        }
    }
}
But note: the above boolean-returning DFS is also exponential and without memoization, it will be slow for large n.
In summary, the reason for using int[] result in the void DFS is to have a mutable integer that can be updated across recursive calls. However, the problem can be solved more elegantly with a returning DFS (int or boolean) and then memoized or converted to DP.
Given the constraints (piles.length is even and piles.length >= 2), the optimal solution is to return true, but for learning purposes, we explore the recursive and DP solutions.
You are absolutely correct! The use of int[] result instead of a primitive int is precisely because Java passes object references by value, not the object itself.
Why We Need an Array/Reference Type:
1. Primitive Types are Passed by Value
void modifyInt(int x) {
    x = 10;  // This only modifies the local copy
}

int main() {
    int value = 5;
    modifyInt(value);
    System.out.println(value);  // Still 5, not 10!
}
2. Object References are Passed by Value, But We Can Modify the Object
void modifyArray(int[] arr) {
    arr[0] = 10;  // Modifies the actual array
}

int main() {
    int[] array = {5};
    modifyArray(array);
    System.out.println(array[0]);  // Now 10!
}
In Our DFS Context:
Problem with Primitive int:
private void dfs(..., int result) {
    if (winningCondition) {
        result = 1;  // Only modifies local copy
        return;      // Original remains unchanged!
    }
}
Solution with Array int[]:
private void dfs(..., int[] result) {
    if (winningCondition) {
        result[0] = 1;  // Modifies the actual array element
        return;         // Original array sees the change
    }
}
Alternative Approaches:
1. Return a Boolean Instead (Cleaner):
private boolean dfs(...) {
    if (left > right) {
        return aliceScore > bobScore;
    }
    
    if (isAliceTurn) {
        return dfs(...) || dfs(...);  // Alice can win by either move
    } else {
        return dfs(...) && dfs(...);  // Bob tries to block all paths
    }
}
2. Use a Mutable Wrapper Class:
class ResultHolder {
    boolean aliceWins = false;
}

private void dfs(..., ResultHolder result) {
    if (winningCondition) {
        result.aliceWins = true;
    }
}
3. Use Global/Class Variable (Not Recommended):
class Solution {
    private boolean result = false;
    
    public boolean stoneGame(int[] piles) {
        dfs(...);
        return result;
    }
}
Why the Void Approach is Problematic:
The void approach with int[] result has issues:
1.No Early Termination: Even if we find Alice can win on one path, we continue exploring all paths
2.Inefficient: Explores the entire game tree
3.Hard to Optimize: Can't easily add memoization
Key Takeaway:
Using int[] result is a workaround for Java's pass-by-value semantics with primitives, but it's generally better to use a return value approach for DFS problems as it's:
1.Cleaner
2.Easier to memoize
3.Supports early termination
4.More aligned with functional programming principles

Your "Better Implementation (Boolean Return with Memoization)" solution error out when input as below:
piles = [3,7,2,3]
Output = false
Expected = true
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        Boolean[][][] memo = new Boolean[n][n][2];
        return helper(piles, 0, n - 1, 0, 0, 0, memo);
    }

    private boolean helper(int[] piles, int left, int right, int aliceScore, int bobScore, int isAliceTurn, Boolean[][][] memo) {
        if(left > right) {
            return aliceScore > bobScore;
        }
        if (memo[left][right][isAliceTurn] != null) {
            return memo[left][right][isAliceTurn];
        }
        if(isAliceTurn == 0) {
            // Alice can win by either move
            memo[left][right][isAliceTurn] = (helper(piles, left + 1, right, aliceScore + piles[left], bobScore, 1, memo) 
                || helper(piles, left, right - 1, aliceScore + piles[right], bobScore, 1, memo));
        } else {
            // Bob tries to block all paths
            memo[left][right][isAliceTurn] = (helper(piles, left + 1, right, aliceScore, bobScore + piles[left], 0, memo) 
                && helper(piles, left, right - 1, aliceScore, bobScore + piles[right], 0, memo));
        }
        return memo[left][right][isAliceTurn];
    }
}
You're absolutely right! My memoization approach has a critical flaw. Let me analyze the issue:
The Problem:
With piles = [3,7,2,3]:
- Alice starts: Takes 3 (left) or 3 (right)
- State depends not just on [left, right, turn] but also on aliceScore and bobScore
- Different paths can lead to the same [left, right, turn] but with different scores!
Example:
Path 1: Alice takes 3 (left) → Bob takes 7 → Alice takes 2 → Bob takes 3
Scores: Alice = 3+2=5, Bob = 7+3=10

Path 2: Alice takes 3 (right) → Bob takes 3 → Alice takes 7 → Bob takes 2
Scores: Alice = 3+7=10, Bob = 3+2=5
Same [left, right, turn] states but different outcomes!
Correct Approach (Return Score Difference):
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        // memo[left][right] = maximum advantage current player can get
        Integer[][] memo = new Integer[n][n];
        return dfs(piles, 0, n - 1, memo) > 0;
    }
    
    // Returns: maximum advantage current player can achieve
    // Positive = advantage for current player
    // Negative = disadvantage for current player
    private int dfs(int[] piles, int left, int right, Integer[][] memo) {
        if (left > right) return 0;
        if (memo[left][right] != null) return memo[left][right];
        
        // If only one pile left
        if (left == right) {
            memo[left][right] = piles[left];
            return piles[left];
        }
        
        // Current player can take left or right
        int takeLeft = piles[left] - dfs(piles, left + 1, right, memo);
        int takeRight = piles[right] - dfs(piles, left, right - 1, memo);
        
        memo[left][right] = Math.max(takeLeft, takeRight);
        return memo[left][right];
    }
}
Alternative: DP Solution (Most Efficient):
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        int[][] dp = new int[n][n];
        
        // Base case: single pile
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }
        
        // Fill DP table by interval length
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                dp[i][j] = Math.max(
                    piles[i] - dp[i + 1][j],
                    piles[j] - dp[i][j - 1]
                );
            }
        }
        
        return dp[0][n - 1] > 0;
    }
}
Test with [3,7,2,3]:
Recursive DP trace:
dfs(0,3): 
  takeLeft = 3 - dfs(1,3) = 3 - 2 = 1
  takeRight = 3 - dfs(0,2) = 3 - 6 = -3
  max = 1 (take left)
  
dfs(1,3):
  takeLeft = 7 - dfs(2,3) = 7 - 1 = 6
  takeRight = 3 - dfs(1,2) = 3 - 5 = -2
  max = 6 (take left)
  
dfs(2,3):
  takeLeft = 2 - dfs(3,3) = 2 - 3 = -1
  takeRight = 3 - dfs(2,2) = 3 - 2 = 1
  max = 1 (take right)
  
dfs(1,2):
  takeLeft = 7 - dfs(2,2) = 7 - 2 = 5
  takeRight = 2 - dfs(1,1) = 2 - 7 = -5
  max = 5 (take left)
  
dfs(0,2):
  takeLeft = 3 - dfs(1,2) = 3 - 5 = -2
  takeRight = 2 - dfs(0,1) = 2 - 4 = -2
  max = -2
Result: dfs(0,3) = 1 > 0 → Alice wins ✓
Key Insight:
The score difference approach (currentPlayerScore - opponentScore) works because:
1.It eliminates the need to track absolute scores
2.It represents the relative advantage of the current player
3.The game is zero-sum: if Alice gains X advantage, Bob loses X advantage
4.This allows proper memoization since state is [left, right] only
The Mathematical Truth:
For this specific problem (even number of piles, sum of all piles is odd), Alice always wins. So the simplest solution is:
class Solution {
    public boolean stoneGame(int[] piles) {
        return true;  // Alice always wins with optimal play
    }
}
But understanding the DP/memoization approach is valuable for similar game theory problems where the outcome isn't predetermined.

Why my HashMap and String key memoization solution still time out ?
Your memoization approach with HashMap is technically correct but highly inefficient and has scalability issues. Let me analyze:
Issues with Your Approach:
1. Huge State Space:
The key includes aliceScore and bobScore, which can each range from 0 to sum of all piles:
- For n piles with values up to M, score can be up to n * M
- State space: O(n² × (nM)²) which is exponential in practice
2. Redundant Computation:
For the same [left, right, turn], you store different entries based on scores, but the optimal decision depends only on the difference, not absolute scores.
3. Memory Explosion:
Example: piles = [1,2,3,...,100]
- aliceScore and bobScore each have ~5000 possible values
- Total states: 100 × 100 × 2 × 5000 × 5000 ≈ 50 billion states
Why Your Memoization Approach Still Works (for small inputs):
It's correct because:
1.The state (left, right, aliceScore, bobScore, turn) uniquely determines the game
2.If we reach the same state again, the outcome will be the same
3.It's a valid memoization of the exhaustive search
If You Really Want Boolean Return with Memoization:
Here's a more efficient boolean approach:
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        // memo[left][right][turn] = null if not computed, Boolean otherwise
        Boolean[][][] memo = new Boolean[n][n][2];
        return helper(piles, 0, n - 1, 0, memo);
    }
    
    private boolean helper(int[] piles, int left, int right, int turn, Boolean[][][] memo) {
        if (left == right) {
            // Only one pile left - current player takes it
            // For Alice (turn=0): return true if she takes this last pile and leads
            // Actually we need to know the scores...
            // This approach doesn't work well!
        }
        // This shows the limitation of boolean return
    }
}
The Fundamental Issue:
The boolean return approach doesn't work well with memoization for this problem because:
1.The outcome depends on accumulated scores, not just position
2.We can't memoize by [left, right, turn] alone
3.We either need to include scores in state (explodes) or use score difference approach
Time and Space Complexity Analysis
State Space Analysis
Let:
- n = number of piles
- S = sum of all piles (S = sum(piles[i]))
Each state is defined by:
- left: 0 to n-1 (n possibilities)
- right: 0 to n-1 (n possibilities)
- aliceScore: 0 to S (S+1 possibilities)
- bobScore: 0 to S (S+1 possibilities)
- isAliceTurn: 0 or 1 (2 possibilities)
Total possible states: O(n² × S² × 2) = O(n² × S²)
Time Complexity: O(n² × S²)
Explanation:
1.Each state is computed exactly once (memoization)
2.Computing each state involves:
- Creating the key string: O(L) where L is string length
- HashMap operations: O(1) average, O(L) worst-case due to string hashing
- Up to 2 recursive calls (but memoized)
3.The dominant factor is the number of states: O(n² × S²)
Since S can be up to n × max(pile_value), in worst case:
- If max(pile_value) = M
- S ≤ n × M
- Time complexity: O(n⁴ × M²) (exponential/polynomial with large exponent)
Space Complexity: O(n² × S²)
1.Memoization storage: Stores all states → O(n² × S²) entries
2.Recursion stack: Up to O(n) depth
3.Key strings: Each key string length is O(log n + log S)
- Storing all keys: O(n² × S² × (log n + log S))
Practical Implications
For typical Leetcode constraints:
- n ≤ 500 (but often ≤ 20 for exponential solutions)
- piles[i] ≤ 500
- S ≤ 500 × 500 = 250,000
Worst-case state count:
- n² = 250,000
- S² = 62.5 billion
- Total states = 15.6 trillion (impossible!)
Comparison with Better Solutions
SolutionTimeSpacePractical LimitYour HashMapO(n² × S²)O(n² × S²)n ≤ 10, S ≤ 50Score Difference DPO(n²)O(n²)n ≤ 1000MathematicalO(1)O(1)Any size
Why This is Impractical
Example: piles = [500, 500, ..., 500] (100 piles)
- n = 100
- S = 100 × 500 = 50,000
- States ≈ 100² × 50,000² = 25 trillion
Even for n=20, S=1000:
- States ≈ 400 × 1,000,000 = 400 million (still too large)
Optimization Potential
Your solution could be slightly optimized:
String key = left + "," + right + "," + aliceScore + "," + bobScore + "," + isAliceTurn;
But this doesn't change the asymptotic complexity.
When It Might Work
For very small cases:
- n ≤ 10
- Small pile values (so S is small)
- But even then, the score difference DP is better
Conclusion
Time Complexity: O(n² × S²)
Space Complexity: O(n² × S²)
This is exponential/pseudo-polynomial and impractical for the problem constraints. That's why the score difference approach (O(n²) time, O(n²) space) is the standard solution - it eliminates the dependency on S by tracking only the relative advantage, not absolute scores.

2D DP Solution
Here's the detailed conversion explanation with comments:
class Solution {
    public boolean stoneGame(int[] piles) {
        int n = piles.length;
        
        // dp[left][right] = maximum advantage the current player can achieve
        // when playing optimally with stones from index 'left' to 'right'
        // This directly corresponds to the return value of helper(left, right)
        // in the recursive solution
        int[][] dp = new int[n][n];
        
        // Process intervals by increasing length
        // This ensures that when we compute dp[left][right], all smaller
        // intervals (dp[left+1][right] and dp[left][right-1]) are already computed
        for(int len = 1; len <= n; len++) {
            
            // Process intervals from right to left for the current length
            // This order doesn't affect correctness since intervals of the same length
            // don't depend on each other, only on intervals of smaller length
            for(int left = n - len; left >= 0; left--) {
                int right = left + len - 1;
                
                // Base case: Single stone interval
                // In DFS: helper(left, left) would return piles[left] because:
                // - Current player takes the only stone: piles[left]
                // - Then game ends: helper(left+1, left) returns 0
                // So: piles[left] - 0 = piles[left]
                if(len == 1) {
                    dp[left][right] = piles[left];
                } 
                // General case: Multiple stones
                else {
                    // This directly maps to the recursive formula:
                    // helper(left, right) = Math.max(
                    //     piles[left] - helper(left + 1, right),
                    //     piles[right] - helper(left, right - 1)
                    // )
                    
                    // Option 1: Take the leftmost stone
                    // Current player gets piles[left] points
                    // Then opponent plays optimally on the remaining stones [left+1...right]
                    // The opponent's advantage is dp[left+1][right]
                    // So our advantage = piles[left] - opponent's advantage
                    int takeLeftAdvantage = piles[left] - dp[left + 1][right];
                    
                    // Option 2: Take the rightmost stone
                    // Same logic but with the right stone
                    int takeRightAdvantage = piles[right] - dp[left][right - 1];
                    
                    // Current player chooses the move that gives maximum advantage
                    dp[left][right] = Math.max(takeLeftAdvantage, takeRightAdvantage);
                }
            }
        }
        
        // The game starts with the full array [0, n-1]
        // Alice is the first player, so dp[0][n-1] represents Alice's maximum advantage
        // Alice wins if her advantage is positive (> 0)
        // This corresponds to: return helper(0, n-1) > 0;
        return dp[0][n - 1] > 0;
    }
}
Step-by-Step Conversion Table:
Recursive DFS ConceptDP EquivalentExplanationhelper(left, right)dp[left][right]Both represent maximum advantage for current playerBase case: left > right → return 0Base case: len == 1 → dp[i][i] = piles[i]Single stone: current player takes itpiles[left] - helper(left+1, right)piles[left] - dp[left+1][right]Take left stone, subtract opponent's advantagepiles[right] - helper(left, right-1)piles[right] - dp[left][right-1]Take right stone, subtract opponent's advantageMath.max(takeLeft, takeRight)Math.max(takeLeft, takeRight)Current player chooses best optionhelper(0, n-1) > 0dp[0][n-1] > 0Check if Alice (first player) has advantage
Why the Implicit Turn Tracking Works:
The key insight is that we don't need to track whose turn it is because:
1.Zero-sum game: If current player gets advantage X, next player gets disadvantage X
2.Recursive structure:
- Current player's move: +piles[i]
- Then subtract opponent's advantage: -helper(next_state)
- The sign flip -helper(...) automatically switches perspective
Example with Alice & Bob:
Alice's advantage at state [l,r] 
= max(piles[l] - Bob's advantage on [l+1,r],
      piles[r] - Bob's advantage on [l,r-1])
    
But Bob's advantage = Alice's disadvantage on the same state!
So: Alice's advantage = max(piles[l] - Alice's disadvantage on [l+1,r],
                           piles[r] - Alice's disadvantage on [l,r-1])
Computation Order Visualization (n=4):
len=1:    Compute all single stones
          dp[0][0] = 3    dp[1][1] = 7    dp[2][2] = 2    dp[3][3] = 3
          (Base cases)

len=2:    Compute all length-2 intervals (right-to-left)
          left=2: dp[2][3] = max(2-dp[3][3], 3-dp[2][2]) = max(-1,1) = 1
          left=1: dp[1][2] = max(7-dp[2][2], 2-dp[1][1]) = max(5,-5) = 5
          left=0: dp[0][1] = max(3-dp[1][1], 7-dp[0][0]) = max(-4,4) = 4

len=3:    Compute all length-3 intervals (right-to-left)
          left=1: dp[1][3] = max(7-dp[2][3], 3-dp[1][2]) = max(6,-2) = 6
          left=0: dp[0][2] = max(3-dp[1][2], 2-dp[0][1]) = max(-2,-2) = -2

len=4:    Compute full interval
          left=0: dp[0][3] = max(3-dp[1][3], 3-dp[0][2]) = max(-3,5) = 5
Final Verification:
The DP solution gives dp[0][3] = 5 > 0, meaning Alice wins. This matches the mathematical proof that with optimal play, Alice always wins when there are an even number of piles.
The conversion is exact: every recursive call helper(left, right) maps directly to dp[left][right], computed in a bottom-up fashion to avoid recursion overhead.



Refer to
L1140.Stone Game II (Ref.L877,L1690)
L1406.Stone Game III (Ref.L877,L1140,L1690)
L1510.Stone Game IV (Ref.L877,L1140,L1406,L1690)
L1563.Stone Game V (Ref.L877,L1140,L1406,L1510)
L1686.Stone Game VI
L1690.Stone Game VII (Ref.L1770)
L1872.Stone Game VIII
DFS return logic
