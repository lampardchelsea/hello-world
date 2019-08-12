/**
 Refer to
 https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/
 You have d dice, and each die has f faces numbered 1, 2, ..., f.

Return the number of possible ways (out of fd total ways) modulo 10^9 + 7 to roll the dice so 
the sum of the face up numbers equals target.

Example 1:
Input: d = 1, f = 6, target = 3
Output: 1
Explanation: 
You throw one die with 6 faces.  There is only one way to get a sum of 3.

Example 2:
Input: d = 2, f = 6, target = 7
Output: 6
Explanation: 
You throw two dice, each with 6 faces.  There are 6 ways to get a sum of 7:
1+6, 2+5, 3+4, 4+3, 5+2, 6+1.

Example 3:
Input: d = 2, f = 5, target = 10
Output: 1
Explanation: 
You throw two dice, each with 5 faces.  There is only one way to get a sum of 10: 5+5.

Example 4:
Input: d = 1, f = 2, target = 3
Output: 0
Explanation: 
You throw one die with 2 faces.  There is no way to get a sum of 3.

Example 5:
Input: d = 30, f = 30, target = 500
Output: 222616187
Explanation: 
The answer must be returned modulo 10^9 + 7.

Constraints:
1 <= d, f <= 30
1 <= target <= 1000
*/

// Note: Why we need to use MOD ?
// Refer to
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/356057/Java-O(d-*-f-*-target)-dp-straightforward-and-fast/322685
// 1000000007 is commonly used in programming competitions to prevent integer overflow for answers. 
// 1000000007 is the first prime number over a billion. It has some other cool characteristics 
// as well: https://primes.utm.edu/curios/page.php/1000000007.html

// Solution 1 and 2: Two styles of TLE native DFS
// Complexity Analysis
// Runtime: O(f ^ d).
// Memory: O(d) for the stack.
// Refer to
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/355841/Java-Memo-DFS
// Style 1: 'result' as member variable with void return helper
class Solution {
    //int result = 0;
    int MOD = 1000000007;
    
    int result = 0;
    public int numRollsToTarget(int d, int f, int target) {
        helper(d, f, target);
        return result;
    }
    
    private void helper(int d, int f, int target) {
        if(d == 0 && target == 0) {
            result++;
            return;
        }
        if(d == 0 || target == 0) {
            return;
        }
        for(int i = 1; i <= f; i++) {
            if(target >= i) {
                helper(d - 1, f, target - i); 
            } else {
                break;
            }
            
        }
        return;
    }
}

// Style 2: 'result' as return variable on helper method
class Solution {
    public int numRollsToTarget(int d, int f, int target) {
        return helper(d, f, target);
    }
    
    private int helper(int d, int f, int target) {
        int result = 0;
        if(d == 0 && target == 0) {
            return 1;
        }
        if(d == 0 || target == 0) {
            return 0;
        }
        for(int i = 1; i <= f; i++) {
            if(target >= i) {
                result = (result + (helper(d - 1, f, target - i) % MOD)) % MOD; 
            } else {
                break;
            }
            
        }
        return result;
    }
}

// DFS Memoization (Top down DP)
// Refer to
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/355841/Java-Memo-DFS
class Solution {
    int MOD = 1000000007;
    public int numRollsToTarget(int d, int f, int target) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        return helper(d, f, target, map);
    }
    
    private int helper(int d, int f, int target, Map<String, Integer> map) {
        if(d == 0 && target == 0) {
            return 1;
        }
        if(d == 0 || target == 0) {
            return 0;
        }
        String str = "" + d + "_" + target;
        if(map.containsKey(str)) {
            return map.get(str);
        }
        int result = 0;
        for(int i = 1; i <= f; i++) {
            if(target >= i) {
                result = (result + helper(d - 1, f, target - i, map) % MOD) % MOD;
            } else {
                break;
            }
        }
        map.put(str, result);
        return result;
    }
}

// DP bottom up
// Refer to
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/355940/C%2B%2B-Coin-Change-2
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/356057/Java-O(d-*-f-*-target)-dp-straightforward-and-fast
/**
 Bottom-Up DP
We can define our dp[i][k] as number of ways we can get k using i dices. 
As an initial point, there is one way to get to 0 using zero dices.
Then, for each dice i and face j, accumulate the number of ways we can get to k.
*/
class Solution {
    int MOD = 1000000007;
    public int numRollsToTarget(int d, int f, int target) {
        // dp[i][k] means number of ways we can get k using i dices
        int[][] dp = new int[1 + d][1 + target];
        // As an initial point, there is one way to get to 0 using zero dices
        dp[0][0] = 1;
        for(int i = 1; i <= d; i++) {
            for(int j = 1; j <= target; j++) {
                //If j(target) could not larger than largest possible sum of i dices (i * f)
                if(j <= i * f) {
                    // k must be smaller than either f and j, since it picked
                    // out from dice faces numbers
                    for(int k = 1; k <= f && k <= j; k++) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][j - k]) % MOD;
                    }
                }
            }
        }
        return dp[d][target];
    }
}

// DP bottom up (Optimize 2d dp to 1d with rolling array)
// Refer to
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/355940/C%2B%2B-Coin-Change-2
// https://leetcode.com/problems/number-of-dice-rolls-with-target-sum/discuss/356749/Java-DP.-Optimized-Space-Solution
// We can define our dp[i][k] as number of ways we can get k using i dices. 
// As an initial point, there is one way to get to 0 using zero dices.
// Then, for each dice i and face j, accumulate the number of ways we can get to k.
// Note that for the bottom-up solution, we can reduce our memory complexity as we only need to store counts for the previous dice.
class Solution {
    public int numRollsToTarget(int d, int f, int target) {
        int MOD = 1000000007;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for(int i = 1; i <= d; i++) {
            // Create rolling array
            int[] dp1 = new int[target + 1];
            for(int j = 1; j <= f; j++) {
                for(int k = j; k <= target; k++) {
                    dp1[k] = (dp1[k] + dp[k - j]) % MOD;
                }
            }
            dp = dp1;
        }
        return dp[target];
    }
}

