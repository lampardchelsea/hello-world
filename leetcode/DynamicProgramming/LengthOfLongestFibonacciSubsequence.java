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
Solution 1: DFS + Hash Table (60 min)
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

Solution 2: DFS + Binary Search (30 min)
class Solution {
    int maxLen = 0;
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        if(n < 3) {
            return 0;
        }
        // Try all possible starting pairs (i, j)
        for(int i = 0; i < n - 2; i++) {
            for(int j = i + 1; j < n - 1; j++) {
                helper(arr, i, j, 2);
            }
        }
        return maxLen >= 3 ? maxLen : 0;
    }

    // DFS to build Fibonacci sequence starting with arr[i], arr[j]
    private void helper(int[] arr, int i, int j, int curLen) {
        if(curLen >= 3) {
            maxLen = Math.max(maxLen, curLen);
            // No 'return' here since return here would stop search prematurely!
            // With this return, we'd only find sequences of length 3, not the longer ones!
        }
        // Calculate next Fibonacci number
        int next = arr[i] + arr[j];
        // Binary search to find if next value exists in arr
        int nextIdx = binarySearch(arr, next);
        if(nextIdx != -1) {
            // Found next number, continue DFS
            helper(arr, j, nextIdx, curLen + 1);
        }
    }

    // Find target occurrence
    private int binarySearch(int[] arr, int val) {
        int lo = 0;
        int hi = arr.length - 1;
        while(lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] == val) {
                return mid;
            } else if(arr[mid] > val) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        // If not found return -1 means no target value in arr
        return -1;
    }
}

Time Complexity: O(n^2 * logn * L), L = max sequence length (worst-case exponential)
Space Complexity: O(L), recursion depth (stack space)

Solution 3: DP + Hash Table (60 min)
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

Evolute from Native DFS and promote with Binary Search
I'll provide a DFS (backtracking) solution for LeetCode 873. While DP is more efficient for this problem, a DFS approach helps understand the recursive structure.
🔄 Native DFS Solution
class Solution {
    private int maxLen = 0;
    
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        // Try all possible starting pairs (i, j)
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                dfs(arr, i, j, 2);
            }
        }
        
        return maxLen >= 3 ? maxLen : 0;
    }
    
    // DFS to build Fibonacci sequence starting with arr[i], arr[j]
    private void dfs(int[] arr, int prevIdx, int currIdx, int length) {
        // Update max length if we have at least 3 elements
        if (length >= 3) {
            maxLen = Math.max(maxLen, length);
        }
        
        // Calculate next Fibonacci number
        int nextVal = arr[prevIdx] + arr[currIdx];
        
        // Binary search to find if nextVal exists after currIdx
        int nextIdx = binarySearch(arr, currIdx + 1, arr.length - 1, nextVal);
        
        if (nextIdx != -1) {
            // Found next number, continue DFS
            dfs(arr, currIdx, nextIdx, length + 1);
        }
        // If not found, this path ends (implicit backtracking)
    }
    
    // Binary search helper
    private int binarySearch(int[] arr, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
📝 Alternative DFS with Memoization (More Efficient)
class Solution {
    public int lenLongestFibSubseq(int[] arr) {
        int n = arr.length;
        if (n < 3) return 0;
        
        int maxLen = 0;
        // Memoization: memo[i][j] = length of Fib seq ending with arr[i], arr[j]
        int[][] memo = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = dfsMemo(arr, i, j, memo);
                maxLen = Math.max(maxLen, len);
            }
        }
        
        return maxLen >= 3 ? maxLen : 0;
    }
    
    private int dfsMemo(int[] arr, int i, int j, int[][] memo) {
        // If already computed, return cached result
        if (memo[i][j] > 0) {
            return memo[i][j];
        }
        
        // Base: any pair is length 2
        int length = 2;
        
        // Find next Fibonacci number
        int nextVal = arr[i] + arr[j];
        int nextIdx = binarySearch(arr, j + 1, arr.length - 1, nextVal);
        
        if (nextIdx != -1) {
            // Extend sequence
            length = 1 + dfsMemo(arr, j, nextIdx, memo);
        }
        
        // Cache result
        memo[i][j] = length;
        return length;
    }
    
    // Same binary search helper
    private int binarySearch(int[] arr, int left, int right, int target) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }
}
🧠 How DFS Works for This Problem
DFS Intuition:
For a Fibonacci-like sequence [..., a, b, c]:
- We need a + b = c
- Given starting pair (arr[i], arr[j]), find arr[k] where arr[k] = arr[i] + arr[j]
- Recursively continue with (arr[j], arr[k])
Example: arr = [1, 2, 3, 4, 5, 6, 7, 8]
DFS Trace for starting pair (1, 2):
dfs(1, 2, length=2)
  next = 1 + 2 = 3 → found at index 2
  dfs(2, 3, length=3)
    next = 2 + 3 = 5 → found at index 4
    dfs(3, 5, length=4)
      next = 3 + 5 = 8 → found at index 7
      dfs(5, 8, length=5)
        next = 5 + 8 = 13 → not found
      length = 5
  maxLen = max(0, 5) = 5
Result: Longest sequence [1, 2, 3, 5, 8] has length 5
📊 Complexity Analysis
AspectBasic DFSDFS with MemoDP (Optimal)TimeO(n² * L)O(n² * log n)O(n²)SpaceO(L)O(n²)O(n²)Where L = sequence length
Why DFS is less efficient:
- Basic DFS: Each starting pair explores entire sequence (could be O(L))
- With memoization: Still O(n² log n) due to binary search per state
- DP solution is O(n²) with simpler logic
🔍 Comparison: DFS vs DP Mindset
// DFS mindset (Recursive thinking):
"Start with (a, b), find c = a + b, then continue with (b, c)"

// DP mindset (Bottom-up building):  
"For each pair (i, j), check if there's k where arr[k] + arr[i] = arr[j]"
// Build dp[i][j] = dp[k][i] + 1
⚠️ Why DFS is Suboptimal for This Problem
1.Overlapping subproblems: Same (i, j) pair explored multiple times
2.Binary search overhead: Need O(log n) per step to find next element
3.Deep recursion: Could hit stack limits for long sequences
🎯 When to Use DFS Anyway
DFS is useful for:
- Understanding problem structure before optimizing
- Interview discussions to show multiple approaches
- Problems where sequence length is small
The memoized DFS is essentially top-down DP (recursive), which mirrors the bottom-up DP solution but with recursion overhead.
I recommend understanding the DFS approach, but using the DP solution for actual submissions as it's more efficient and cleaner!

What's the space and time complexity for DFS + Harsh Table vs. DFS + Binary Search ?
Great question! Let me analyze the exact time and space complexity for both DFS approaches you provided.
📊 Complexity Analysis Table
AspectDFS + Hash TableDFS + Binary SearchTime ComplexityO(n² * M)O(n² * log n * M)Space ComplexityO(n + M)O(M)Where M = max sequence lengthHash OperationsO(1) averageN/ASearch per stepO(1)O(log n)Best forLarge n, small MSmall n, sorted array
🔍 Detailed Breakdown
1. DFS + Hash Table Solution
// Time Complexity Analysis:
for(i: 0 to n-2) {               // O(n) iterations
  for(j: i+1 to n-1) {           // O(n) iterations → O(n²) starting pairs
    helper(set, a, b, 2);        // Each DFS explores up to M steps
  }
}

// helper() explores sequence: O(M) where M = max sequence length
// Total: O(n² * M)
Space Complexity:
- HashSet: O(n) to store all elements
- Recursion stack: O(M) for deepest sequence
- Total: O(n + M)
Example Worst Case: arr = [1, 2, 3, 5, 8, 13, 21, ...] (full Fibonacci)
- n = length of array
- M ≈ log(max_value) (Fibonacci grows exponentially)
- For arr[0]=1, arr[1]=2, DFS explores O(M) steps
- Actual: O(n² * M)
2. DFS + Binary Search Solution
// Time Complexity Analysis:
for(i: 0 to n-2) {               // O(n)
  for(j: i+1 to n-1) {           // O(n) → O(n²) starting pairs
    dfs(arr, i, j, 2);           // Each DFS: O(M * log n)
  }
}

// Each dfs() call: binary search = O(log n)
// Sequence length M steps → O(M * log n)
// Total: O(n² * M * log n)
Space Complexity:
- No hash set: O(1) extra space
- Recursion stack: O(M)
- Total: O(M)
🧮 Mathematical Justification
Hash Table Version:
T(n) = Σ_{i=0}^{n-2} Σ_{j=i+1}^{n-1} O(M_ij)
     ≤ Σ_{i=0}^{n-2} Σ_{j=i+1}^{n-1} O(M_max)
     = O(n² * M_max)

Where M_ij = length of sequence starting at (i,j)
      M_max = maximum possible sequence length
Binary Search Version:
T(n) = Σ_{i=0}^{n-2} Σ_{j=i+1}^{n-1} Σ_{k=1}^{M_ij} O(log n)
     = O(n² * M_max * log n)
📈 Practical Comparison with Example
Input: arr = [1, 2, 3, 4, 5, 6, 7, 8], n=8
StepHash TableBinary SearchCheck (1,2)O(1) lookup → find 3Binary search in [3..8] → O(log 8)=3Check (2,3)O(1) → find 5Binary search → O(log 8)=3Check (3,5)O(1) → find 8Binary search → O(log 8)=3Check (5,8)O(1) → not foundBinary search → O(log 8)=3Total for (1,2)4 operations4×3=12 operations
For n=1000:
- Hash: ~n² × M ≈ 1M × 20 = 20M operations
- Binary: ~n² × M × log n ≈ 1M × 20 × 10 = 200M operations
⚠️ Important Caveat: The Real Upper Bound for M
Key Insight: Fibonacci sequence grows exponentially, so:
M_max ≤ log_φ(max_value) ≈ 1.44 * log₂(max_value)
Where φ ≈ 1.618 (golden ratio)
Since max_value ≤ 10^9 (per problem constraints):
M_max ≤ 1.44 * log₂(10^9) ≈ 1.44 × 30 ≈ 43
So M is bounded by ~45, not by n!
Revised Complexities:
- Hash Table: O(n² * 45) = O(n²)
- Binary Search: O(n² * 45 * log n) = O(n² log n)
🎯 Final Verdict
SolutionTimeSpaceWhen to UseDFS + Hash TableO(n²)O(n)Better choice - faster, simplerDFS + Binary SearchO(n² log n)O(1)When memory is tight
Recommendation: Use DFS + Hash Table as it's more efficient due to O(1) lookups vs O(log n) binary search, and M is bounded (~45).
Both are less optimal than the DP solution (O(n²) time, O(n²) space) which avoids repeated DFS exploration of the same subsequences.

I have one doubt, when in below solution there is no "return" in this section ?
// Update max length if we have at least 3 elements
if (length >= 3) {
    maxLen = Math.max(maxLen, length);
    // i cannot see return here
}
Excellent observation! You're right to question this. There's no return statement after updating maxLen, and this is correct for the DFS logic. Let me explain why:
🔄 Why No Return After Updating maxLen?
The code is designed to continue searching for longer sequences even after finding one that's at least length 3.
private void dfs(int[] arr, int prevIdx, int currIdx, int length) {
    // Update max length if we have at least 3 elements
    if (length >= 3) {
        maxLen = Math.max(maxLen, length);
        // NO RETURN HERE - we want to continue searching!
    }
    
    // Try to extend the sequence further
    int nextVal = arr[prevIdx] + arr[currIdx];
    int nextIdx = binarySearch(arr, currIdx + 1, arr.length - 1, nextVal);
    
    if (nextIdx != -1) {
        // Continue DFS to find EVEN LONGER sequence
        dfs(arr, currIdx, nextIdx, length + 1);
    }
    // If not found, function returns naturally (void return)
}
📝 What Happens Step-by-Step
Let's trace with arr = [1, 2, 3, 5, 8] starting with (1, 2):
dfs(arr, 0, 1, length=2)
  ├── length=2 (<3) → skip maxLen update
  ├── nextVal = 1+2 = 3 → found at index 2
  └── dfs(arr, 1, 2, length=3)
        ├── length=3 (≥3) → maxLen = max(0,3)=3
        ├── nextVal = 2+3 = 5 → found at index 3
        └── dfs(arr, 2, 3, length=4)
              ├── length=4 → maxLen = max(3,4)=4
              ├── nextVal = 3+5 = 8 → found at index 4
              └── dfs(arr, 3, 4, length=5)
                    ├── length=5 → maxLen = max(4,5)=5
                    ├── nextVal = 5+8 = 13 → NOT FOUND
                    └── Returns (sequence ends)
If we added return after maxLen = Math.max(maxLen, length);:
// WRONG VERSION with early return:
if (length >= 3) {
    maxLen = Math.max(maxLen, length);
    return; // This would stop search prematurely!
}
With this return, we'd only find sequences of length 3, not the longer ones!
🎯 The DFS Search Pattern
Think of it as exploring a tree of possibilities:
Start: (1,2), length=2
├── Find 3: (2,3), length=3 ✓ (update maxLen=3)
│   └── Find 5: (3,5), length=4 ✓ (update maxLen=4)
│       └── Find 8: (5,8), length=5 ✓ (update maxLen=5)
│           └── No 13: stop
├── (Other paths from different starting pairs...)

The DFS should continue down the path as long as it can find next Fibonacci numbers.
🔄 Alternative: When Would We Use Return?
We would use return if the problem asked for any Fibonacci-like sequence (not necessarily the longest):
// If we just need to know IF a sequence exists (not longest)
private boolean dfsExists(int[] arr, int prevIdx, int currIdx, int length) {
    if (length >= 3) {
        return true; // Found at least length 3
    }
    
    int nextVal = arr[prevIdx] + arr[currIdx];
    int nextIdx = binarySearch(arr, currIdx + 1, arr.length - 1, nextVal);
    
    if (nextIdx != -1) {
        return dfsExists(arr, currIdx, nextIdx, length + 1);
    }
    
    return false; // No extension possible
}

📊 Visual Comparison
With Return (WRONG)Without Return (CORRECT)Stops at first valid lengthContinues to find longerFinds: length=3 onlyFinds: length=3, then 4, then 5maxLen would be 3maxLen correctly becomes 5
💡 Key Insight
The absence of return is intentional because:
1.We want to explore all possible extensions
2.The function returns naturally when no more extensions exist
3.maxLen tracks the maximum depth reached in the search
So your observation is correct - there's no return, and that's exactly what allows the algorithm to find the longest (not just first) Fibonacci-like subsequence!

So in general when should we add "return", when we should not add "return" ?
Excellent question! This is a fundamental concept in DFS/backtracking. Let me give you a clear framework:
🎯 Decision Framework: When to Use return in DFS
1. USE return when: Finding ANY solution (early termination)
// Pattern: Return immediately when condition met
private boolean dfsFindAny(TreeNode node, int target) {
    if (node == null) return false;
    
    if (node.val == target) {
        return true;  // ✅ RETURN: Found it, stop searching
    }
    
    return dfsFindAny(node.left, target) || dfsFindAny(node.right, target);
}
Use cases:
- LeetCode 112: Path Sum (any path equals target)
- Checking if element exists in tree
- Any "exists" or "has" problem
2. DO NOT USE return when: Finding ALL solutions or BEST solution
// Pattern: Continue exploring all possibilities
private void dfsFindAll(TreeNode node, List<Integer> result) {
    if (node == null) return;
    
    result.add(node.val);  // Collect ALL nodes
    
    dfsFindAll(node.left, result);  // No return - explore left
    dfsFindAll(node.right, result); // No return - explore right
    // Only returns when both subtrees done
}
Use cases:
- LeetCode 873: Longest Fibonacci (need longest)
- Collecting all nodes in traversal
- Finding maximum/minimum (need to see all)
📊 Decision Matrix
GoalReturn?ExampleWhyAny solution✅ YesPath exists?Stop when foundAll solutions❌ NoAll pathsNeed to explore allCount solutions❌ NoCount pathsNeed to count allBest solution❌ NoLongest pathNeed to compare allCollect all❌ NoInorder traversalVisit every node
🔄 Two Fundamental DFS Patterns
Pattern A: Early Return (Find ANY)
boolean dfsFindAny(State state) {
    if (isGoal(state)) return true;           // ✅ RETURN when found
    
    for (State next : getNextStates(state)) {
        if (dfsFindAny(next)) return true;    // ✅ RETURN if child found
    }
    
    return false;  // No solution in this branch
}
Pattern B: Exhaustive Search (Find ALL/BEST)
void dfsFindBest(State state) {
    if (isValid(state)) {
        updateBest(state);  // Update global best
        // ❌ NO RETURN - continue searching for better
    }
    
    for (State next : getNextStates(state)) {
        dfsFindBest(next);  // Explore all children
        // ❌ NO RETURN - must explore all branches
    }
    
    // Returns only when all children explored
}
🧪 Practical Examples
Example 1: LeetCode 112 (Path Sum) - USE RETURN
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    
    // If leaf and sum matches, FOUND!
    if (root.left == null && root.right == null && root.val == targetSum) {
        return true;  // ✅ RETURN: Found a path
    }
    
    // Check left or right
    return hasPathSum(root.left, targetSum - root.val) ||
           hasPathSum(root.right, targetSum - root.val);
}
Example 2: LeetCode 113 (Path Sum II) - NO RETURN
public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
    List<List<Integer>> result = new ArrayList<>();
    dfs(root, targetSum, new ArrayList<>(), result);
    return result;
}

private void dfs(TreeNode node, int remaining, List<Integer> path, 
                 List<List<Integer>> result) {
    if (node == null) return;
    
    path.add(node.val);
    
    if (node.left == null && node.right == null && node.val == remaining) {
        result.add(new ArrayList<>(path));  // Found one, but ❌ NO RETURN
    }
    
    dfs(node.left, remaining - node.val, path, result);   // Keep exploring
    dfs(node.right, remaining - node.val, path, result);  // Keep exploring
    
    path.remove(path.size() - 1);  // Backtrack
}
Example 3: LeetCode 437 (Path Sum III) - NO RETURN
public int pathSum(TreeNode root, int targetSum) {
    if (root == null) return 0;
    
    // Count paths starting from root + paths in subtrees
    return countPaths(root, targetSum) + 
           pathSum(root.left, targetSum) +   // ❌ NO RETURN - must check left
           pathSum(root.right, targetSum);   // ❌ NO RETURN - must check right
}
💡 Key Insights
Rule of Thumb:
1.Question asks "if exists", "whether", "has" → USE return
2.Question asks "all", "longest", "maximum", "count" → NO return
Memory Aid:
- "Any" = Any time you find it, you're done → return
- "All/Best" = Need to see everything → no return
Test Your Understanding:
For these LeetCode problems, would you use return?
- 236. Lowest Common Ancestor → ✅ USE (find ancestor)
- 129. Sum Root to Leaf Numbers → ❌ NO (need all paths)
- 257. Binary Tree Paths → ❌ NO (collect all paths)
- 572. Subtree of Another Tree → ✅ USE (check if subtree exists)
🎯 Simple Decision Flowchart
Start DFS
  ↓
Is this a "find ANY" problem?
  ├── YES → Use early return pattern
  │       (return when found)
  │
  └── NO → Is this "find ALL/BEST"?
         ├── YES → Use exhaustive pattern  
         │       (no return, track globally)
         │
         └── NO → Usually exhaustive
Remember: "return" means "stop searching this branch". Use it when you want to prune the search space; avoid it when you need to explore everything!

Deepseek promoted answer based on Kimi answer
Kimi's answer is excellent - it provides a more nuanced, optimization-focused perspective that complements my problem-type categorization. Let me synthesize both approaches into a comprehensive framework.
🎯 Synthesized Framework: When to Use return in DFS
Three Core Principles
PrincipleWhen to returnWhen NOT to returnExample1. Problem GoalFinding ANY solutionFinding ALL/BEST solutionsPath exists vs longest path2. Search PruningBranch cannot improve answerBranch may still improveCurrent path + max possible ≤ best3. Solution CompletenessFound optimal & no better existsFound partial, may extendFound exact match vs found length 3
🔄 The Complete Decision Algorithm
private void dfs(State state, int currentScore) {
    // PRINCIPLE 2: Prune invalid/unpromising branches EARLY
    if (!isValid(state)) return;                      // ✅ RETURN: Invalid
    if (currentScore + optimisticEstimate(state) <= bestScore) return; // ✅ RETURN: Can't beat best
    
    // PRINCIPLE 3: Update but continue if extension possible
    if (isSolution(state)) {
        updateBest(state);                            // Update global best
        
        // DECISION POINT: Can extensions yield better solution?
        if (canBeExtended(state)) {
            // ❌ NO RETURN: Continue searching for longer/better
        } else {
            return;                                   // ✅ RETURN: No extensions possible
        }
    }
    
    // Continue exploring all children
    for (State child : getChildren(state)) {
        dfs(child, currentScore + 1);
        // ❌ NO return here unless implementing "find any"
    }
}
📊 Expanded Decision Matrix
Scenarioreturn?ReasonCode PatternInvalid state✅ YesCannot proceedif (invalid) return;Branch bound exceeded✅ YesPruning optimizationif (current + maxPossible ≤ best) return;Found ANY solution✅ YesEarly terminationif (found) return true;Found exact match (no extensions)✅ YesComplete solutionif (exactMatch) { best = current; return; }Found partial, may extend❌ NoCould get longer/betterif (length ≥ 3) updateBest(); // continueCollecting all solutions❌ NoNeed exhaustive searchresult.add(path); // explore all childrenFinding maximum/minimum❌ NoMust see all possibilitiesmaxLen = Math.max(maxLen, len); // continue
🧪 Practical Examples Improved
Example A: LeetCode 873 (Longest Fibonacci)
private void dfs(int[] arr, int i, int j, int len) {
    // Update but DON'T return - could extend further
    if (len >= 3) maxLen = Math.max(maxLen, len); // ❌ NO RETURN
    
    // Try to extend
    int next = arr[i] + arr[j];
    int k = binarySearch(arr, j+1, next);
    
    if (k != -1) dfs(arr, j, k, len + 1);
    // Implicit return only when no extension
}
Example B: LeetCode 112 (Path Sum) - WITH Pruning
public boolean hasPathSum(TreeNode root, int target) {
    if (root == null) return false;
    
    // Early return when found
    if (root.left == null && root.right == null && root.val == target) {
        return true; // ✅ RETURN: Found exact match, no children to extend
    }
    
    // Prune if remaining sum impossible
    if (target - root.val < 0 && allPositive) {
        return false; // ✅ RETURN: Can't reach target with positive numbers
    }
    
    return hasPathSum(root.left, target - root.val) ||
           hasPathSum(root.right, target - root.val);
}
Example C: LeetCode 79 (Word Search) - Mixed Strategy
private boolean dfs(char[][] board, String word, int i, int j, int index) {
    // Multiple return conditions:
    if (index == word.length()) return true; // ✅ RETURN: Found complete word
    if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return false; // ✅ RETURN: Out of bounds
    if (board[i][j] != word.charAt(index)) return false; // ✅ RETURN: Mismatch
    
    // Temporary mark and continue search
    char temp = board[i][j];
    board[i][j] = '#';
    
    // Search all directions WITHOUT early return (need to try all)
    boolean found = dfs(board, word, i+1, j, index+1) ||
                    dfs(board, word, i-1, j, index+1) ||
                    dfs(board, word, i, j+1, index+1) ||
                    dfs(board, word, i, j-1, index+1);
    
    board[i][j] = temp;
    return found; // Return only after trying all directions
}
💡 Ultimate Decision Flowchart
Start DFS
  ↓
Is state invalid or cannot beat best? → RETURN (prune)
  ↓
Does this match problem goal exactly? 
  ├── YES → Can it be extended to better solution?
  │   ├── YES → Update best, continue (NO RETURN)
  │   └── NO → Update best, RETURN
  │
  └── NO → Continue searching children
        ├── If "find any": RETURN when first found
        └── If "find all/best": NO RETURN, explore all
🏆 Key Takeaways from Both Approaches
1.Kimi's insight is crucial: Even when you find a solution, ask "Can extensions yield a better solution?" before returning.
2.My categorization helps: Knowing if the problem asks for "any" vs "all/best" gives you the starting point.
3.Combine both: First categorize the problem, then apply pruning logic within that category.
4.Default to no return: When in doubt, don't return unless you're certain no better solution exists in the current branch.
The best approach is to start with problem categorization (any vs all/best), then apply pruning logic to optimize within that framework. This gives you both correctness and efficiency!


Refer to
L509.Fibonacci Number (Ref.L70,L746,L842,L873)
