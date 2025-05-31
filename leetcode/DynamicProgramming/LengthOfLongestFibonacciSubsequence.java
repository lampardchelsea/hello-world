/**
Refer to
https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
A sequence X1, X2, ..., Xn is Fibonacci-like if:

n >= 3
Xi + Xi+1 = Xi+2 for all i + 2 <= n
Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest 
Fibonacci-like subsequence of arr. If one does not exist, return 0.

A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr, 
without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].

Example 1:
Input: arr = [1,2,3,4,5,6,7,8]
Output: 5
Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].

Example 2:
Input: arr = [1,3,7,11,12,14,18]
Output: 3
Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].

Constraints:
3 <= arr.length <= 1000
1 <= arr[i] < arr[i + 1] <= 109
*/

// Solution 1: 2D DP
// Style 1: dp[a, b] represents the length of fibo sequence ends up with (a, b)
// Refer to
// https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/discuss/152343/C%2B%2BJavaPython-Check-Pair
/**
Solution 2
Another solution is kind of dp.
dp[a, b] represents the length of fibo sequence ends up with (a, b)
Then we have dp[a, b] = (dp[b - a, a] + 1 ) or 2
The complexity reduce to O(N^2).
In C++/Java, I use 2D dp and index as key.
In Python, I use value as key.

Time Complexity:
O(N^2)

Java
    public int lenLongestFibSubseq(int[] A) {
        int res = 0;
        int[][] dp = new int[A.length][A.length];
        Map<Integer, Integer> index = new HashMap<>();
        for (int j = 0; j < A.length; j++) {
            index.put(A[j], j);
            for (int i = 0; i < j; i++) {
                int k = index.getOrDefault(A[j] - A[i], -1);
                dp[i][j] = (A[j] - A[i] < A[i] && k >= 0) ? dp[k][i] + 1 : 2;
                res = Math.max(res, dp[i][j]);
            }
        }
        return res > 2 ? res : 0;
    }
*/
class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        int result = 0;
        int n = arr.length;
        // dp[a, b] represents the length of fibo sequence ends up with (a, b)
        // Then we have dp[a, b] = (dp[b - a, a] + 1 ) or 2
        int[][] dp = new int[n][n];
        // <value, index>
        Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
        // j for later number, i for previous number
        for(int j = 0; j < n; j++) {
            indexMap.put(arr[j], j);
            for(int i = 0; i < j; i++) {
                int k = indexMap.getOrDefault(arr[j] - arr[i], -1);
                // For a moment I couldn't understand the following condition:
                // dp[i][j] = (arr[j] - arr[i] < arr[i] && ...)
                // Finally figured out by running a simple example:
                // A = {1, 2, 3}
                // when j = 2 and i = 0, arr[j] - arr[i] = 2, since arr[j] - arr[i] > arr[i] 
                // in this case, we can't expand the length.
                // If only keep condition as 'k > -1' will error out on test case:
                // [1,2,3,4,5,6,7,8], output = 4, expected = 5
                // The math behind arr[j] - arr[i] > arr[i] is actually because we define
                // dp[i][j] as last two numbers in sequence, and there relation should not
                // be arr[j] > 2 * arr[i] because arr[j] = arr[i] + arr[i - 1], and
                // arr[i] > arr[i - 1], so not able to double, this check is necessary
                // for construct a fibo sequence
                if(arr[j] - arr[i] < arr[i] && k > -1) {
                    dp[i][j] = dp[k][i] + 1;
                } else {
                    dp[i][j] = 2;
                }
                result = Math.max(result, dp[i][j]);
            }
        }
        return result > 2 ? result : 0;
    }
}

// Style 2: dp[a, b] represents the length of fibo sequence start with (a, b)
// Refer to
// https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/discuss/152332/Java-clean-DP-O(n2)-time-O(n2)-space/377212
class Solution {
    public int lenLongestFibSubseq(int[] A) {
        int res = 0, len = A.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            map.put(A[i], i);
        }
        int[][] dp = new int[len][len];//dp[i][j]代表A[i],A[j]开头的fib seq的最大长度
        for (int i = len - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (dp[j][i] == 0) {
                    dp[j][i] = 2;
                }
                int next = A[j] + A[i];
                if (map.containsKey(next)) {
                    dp[j][i] = dp[i][map.get(next)] + 1;
                    res = Math.max(res, dp[j][i]);
                }
            }
        }
        return res >= 3 ? res : 0;
    }
}










































































https://leetcode.com/problems/length-of-longest-fibonacci-subsequence/
A sequence x1, x2, ..., xn is Fibonacci-like if:
- n >= 3
- xi + xi+1 == xi+2 for all i + 2 <= n
Given a strictly increasing array arr of positive integers forming a sequence, return the length of the longest Fibonacci-like subsequence of arr. If one does not exist, return 0.
A subsequence is derived from another sequence arr by deleting any number of elements (including none) from arr, without changing the order of the remaining elements. For example, [3, 5, 8] is a subsequence of [3, 4, 5, 6, 7, 8].
 
Example 1:
Input: arr = [1,2,3,4,5,6,7,8]
Output: 5
Explanation: The longest subsequence that is fibonacci-like: [1,2,3,5,8].

Example 2:
Input: arr = [1,3,7,11,12,14,18]
Output: 3
Explanation: The longest subsequence that is fibonacci-like: [1,11,12], [3,11,14] or [7,11,18].
 
Constraints:
- 3 <= arr.length <= 1000
- 1 <= arr[i] < arr[i + 1] <= 10^9
--------------------------------------------------------------------------------
Attempt 1: 2025-05-27
Solution 1: DFS (60 min)
class Solution {
    int maxLen = 0;
    public int lenLongestFibSubseq(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for(int num : arr) {
            set.add(num);
        }
        // Try all possible starting pairs (i, j)
        for(int i = 0; i < arr.length - 2; i++) {
            for(int j = i + 1; j < arr.length - 1; j++) {
                int a = arr[i];
                int b = arr[j];
                // Initial length = 2 (a and b)
                helper(set, a, b, 2);
            }
        }
        return maxLen >= 3 ? maxLen : 0;
    }

    private void helper(Set<Integer> set, int a, int b, int len) {
        int c = a + b;
        if(set.contains(c)) {
            maxLen = Math.max(maxLen, len + 1);
            helper(set, b, c, len + 1);
        }
    }
}

Time Complexity: O(n^2*L), L = max sequence length (worst-case exponential)
Space Complexity: O(L), recursion depth (stack space)

Solution 2: DP + Hash Table (60 min)
class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        int len = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        for(int i = 0; i < len; i++) {
            map.put(arr[i], i);
        }
        // Use a 2D array dp where dp[i][j] represents 
        // the length of the longest Fibonacci sequence 
        // ending with arr[i] and arr[j]
        // Specifically, it stores the number of pairs 
        // in the sequence before (arr[i], arr[j])
        int[][] dp = new int[len][len];
        int maxLen = 0;
        // For each pair (i,j) where i < j, check if there 
        // exists a previous element arr[k] such that 
        // arr[k] + arr[i] = arr[j]
        for(int j = 2; j < len; j++) {
            for(int i = j - 1; i >= 1; i--) {
                int prev = arr[j] - arr[i];
                // Since array is strictly increasing
                if(prev >= arr[i]) {
                    break;
                }
                if(map.containsKey(prev)) {
                    int k = map.get(prev);
                    dp[i][j] = dp[k][i] + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        // Why + 2 ?
        // The DP table counts transitions (steps between pairs), not total elements
        // - A sequence with maxLen steps has:
        // - maxLen + 2 elements (initial pair + maxLen additions)
        return maxLen == 0 ? 0 : maxLen + 2;
    }
}

Time Complexity: O(n^2) - We use nested loops through the array
Space Complexity: O(n^2) - For the DP table and hashmap

Refer to Deepseek
Problem Description
Given a strictly increasing array arr of positive integers, we need to find the length of the longest subsequence that forms a Fibonacci-like sequence where:
- A[i] + A[j] = A[k] for all i < j < k
- The sequence should have at least 3 elements
Approach
We'll use dynamic programming to solve this problem efficiently:
1.Index Mapping: Create a hashmap to store value-to-index mappings for O(1) lookups
2.DP Table: Use a 2D array dp where dp[i][j] represents the length of the longest Fibonacci sequence ending with arr[i] and arr[j]
3.Iterate Pairs: For each pair (i,j) where i < j, check if there exists a previous element arr[k] such that arr[k] + arr[i] = arr[j]
4.Update Lengths: Update the DP table and keep track of the maximum length found
Solution Code
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indexMap.put(arr[i], i);
        }
        int[][] dp = new int[n][n];
        int maxLen = 0;
        for (int j = 2; j < n; j++) {
            for (int i = j - 1; i >= 1; i--) {
                int prev = arr[j] - arr[i];
                if (prev >= arr[i]) break; // Since array is strictly increasing
                if (indexMap.containsKey(prev)) {
                    int k = indexMap.get(prev);
                    dp[i][j] = dp[k][i] + 1;
                    maxLen = Math.max(maxLen, dp[i][j]);
                }
            }
        }
        return maxLen == 0 ? 0 : maxLen + 2;
    }
}
Explanation
1.Index Mapping: We first create a hashmap to store each value's index for quick lookup later.
2.DP Initialization: The dp table is initialized with zeros. dp[i][j] will store the length of sequences ending with arr[i] and arr[j].
3.Nested Loops:
- Outer loop iterates through potential sequence endings (j)
- Inner loop iterates through potential second-to-last elements (i)
4.Fibonacci Check: For each pair (i,j), we calculate the required previous value prev = arr[j] - arr[i]
5.Update DP Table: If prev exists in the array (and is before i), we update dp[i][j] based on dp[k][i]
6.Result Calculation: The maximum length found is returned (adding 2 because we count pairs but need sequence length)
Complexity Analysis
- Time Complexity: O(n²) - We use nested loops through the array
- Space Complexity: O(n²) - For the DP table and hashmap
This solution efficiently finds the longest Fibonacci-like subsequence by leveraging dynamic programming and hashmap lookups.
Regarding the DP solution, why final answer needs maxLen + 2 ? why + 2 ?
The + 2 in the final result (maxLen + 2) is needed because the DP table tracks the count of "steps" in the Fibonacci sequence, not the actual length of the sequence. Here's why:
1. How DP Table Works
- dp[i][j] represents:

- The length of the Fibonacci subsequence ending with arr[i] and arr[j].
- Specifically, it stores the number of pairs in the sequence before (arr[i], arr[j]).
2. Why dp[i][j] = dp[k][i] + 1?
- When we find a valid prev = arr[j] - arr[i] (at index k), it means:

- The triplet (arr[k], arr[i], arr[j]) forms a Fibonacci sequence.
- The value dp[k][i] gives the length of the sequence before (arr[k], arr[i]).
- By adding (arr[i], arr[j]), we increment the count: dp[i][j] = dp[k][i] + 1.
3. Example
Consider arr = [1, 2, 3, 5, 8]:
- For (i=2, j=3) (values 3, 5):

- prev = 5 - 3 = 2 (exists at k=1).
- dp[1][2] = 0 (no prior pairs before (2, 3)).
- dp[2][3] = dp[1][2] + 1 = 1.
- This means the sequence [2, 3, 5] has 1 step (from (2,3) to (3,5)), but the actual subsequence length is 3.
4. Why + 2?
- The DP table counts transitions (steps between pairs), not total elements.
- A sequence with maxLen steps has:

- maxLen + 2 elements (initial pair + maxLen additions).
- Example:

- maxLen = 1 → Sequence length = 3 ([a, b, a+b]).
- maxLen = 2 → Sequence length = 4 ([a, b, a+b, a+2b]).
5. Edge Case
- If maxLen = 0, return 0 (no valid sequence of length ≥ 3).
Key Insight
The + 2 accounts for:
1.The first two elements (arr[k] and arr[i]), and
2.The last element (arr[j]) in the subsequence.
Without + 2, we’d only count the "steps" between elements, not the total length.
Final Answer
The maxLen + 2 converts the step count (stored in DP) to the actual subsequence length. This ensures we include all elements in the Fibonacci-like sequence.
For example:
- If maxLen = 3 (3 steps), the sequence has 3 + 2 = 5 elements:
[a, b, a+b, a+2b, 2a+3b]

If initially i am not comes up with DP solution, how can i start with a native DFS solution ?
If you're not initially thinking of a DP solution for LeetCode 873 (Length of Longest Fibonacci Subsequence), a DFS (backtracking) approach is a good starting point. While it won't be as efficient as DP for larger inputs, it helps build intuition before optimizing.
Native DFS Approach
1.Generate All Possible Triplets:
- For each starting pair (arr[i], arr[j]), recursively check if the sum exists in the remaining array.
- If found, continue building the sequence.
2.Base Case:
- If no next Fibonacci number is found, return the current sequence length.
3.Optimizations:
- Use a HashSet for O(1) lookups.
- Early termination if remaining elements can't form a longer sequence.
DFS Solution Code
import java.util.HashSet;
import java.util.Set;

class Solution {
    private int maxLen = 0;
    public int lenLongestFibSubseq(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int num : arr) set.add(num);
        // Try all possible starting pairs (i, j)
        for (int i = 0; i < arr.length - 2; i++) {
            for (int j = i + 1; j < arr.length - 1; j++) {
                int a = arr[i], b = arr[j];
                dfs(set, a, b, 2); // Initial length = 2 (a and b)
            }
        }
        return maxLen >= 3 ? maxLen : 0;
    }

    private void dfs(Set<Integer> set, int a, int b, int len) {
        int next = a + b;
        if (set.contains(next)) {
            maxLen = Math.max(maxLen, len + 1);
            dfs(set, b, next, len + 1); // Recurse with (b, next)
        }
    }
}
Key Steps
1.Preprocess with HashSet:
- Convert arr to a HashSet for O(1) lookups of Fibonacci candidates.
2.Start with All Pairs:
- For each (arr[i], arr[j]), initiate DFS to extend the sequence.
3.DFS Recursion:
- If a + b exists in the set, increment the length and recurse with (b, a + b).
4.Track Maximum Length:
- Update maxLen whenever a longer sequence is found.
Why DFS First?
- Intuitive: Mimics how you'd solve the problem manually.
- Foundation for DP: Helps recognize overlapping subproblems (which DP optimizes).
Time & Space Complexity
MetricComplexityExplanationTimeO(n² * L)L = max sequence length (worst-case exponential)SpaceO(L)Recursion depth (stack space)
Optimizing to DP
Once you see DFS works but is slow, notice:
1.Overlapping Subproblems: The same (a, b) pairs are checked repeatedly.
2.Optimal Substructure: The solution can be built from smaller subsequences.
This leads to the DP solution (shown earlier) with O(n²) time.
Key Takeaway
Start with DFS to understand the problem, then optimize to DP for efficiency! 🚀

Refer to
L509.Fibonacci Number (Ref.L70,L746,L842,L873)
