https://leetcode.com/problems/delete-and-earn/description/
You are given an integer array nums. You want to maximize the number of points you get by performing the following operation any number of times:
- Pick any nums[i] and delete it to earn nums[i] points. Afterwards, you must delete every element equal to nums[i] - 1 and every element equal to nums[i] + 1.
Return the maximum number of points you can earn by applying the above operation some number of times.
 
Example 1:
Input: nums = [3,4,2]
Output: 6
Explanation: You can perform the following operations:
- Delete 4 to earn 4 points. Consequently, 3 is also deleted. nums = [2].
- Delete 2 to earn 2 points. nums = [].
You earn a total of 6 points.

Example 2:
Input: nums = [2,2,3,3,3,4]
Output: 9
Explanation: You can perform the following operations:
- Delete a 3 to earn 3 points. All 2's and 4's are also deleted. nums = [3,3].
- Delete a 3 again to earn 3 points. nums = [3].
- Delete a 3 once more to earn 3 points. nums = [].
You earn a total of 9 points.
 
Constraints:
- 1 <= nums.length <= 2 * 10^4
- 1 <= nums[i] <= 10^4
--------------------------------------------------------------------------------
Attempt 1: 2025-11-02
Solution 1: Native DFS (30 min, TLE 23/51)
class Solution {
    public int deleteAndEarn(int[] nums) {
        // <k, v> -> <num, sum of num>
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + num);
        }
        List<Integer> uniqueNums = new ArrayList<>(map.keySet());
        Collections.sort(uniqueNums);
        return helper(0, uniqueNums, map);
    }

    private int helper(int index, List<Integer> uniqueNums, Map<Integer, Integer> map) {
        if(index >= uniqueNums.size()) {
            return 0;
        }
        int curNum = uniqueNums.get(index);
        int curSum = map.get(curNum);
        // Option 1: Skip current num
        int skip = helper(index + 1, uniqueNums, map);
        // Option 2: Take current num
        int take = curSum;
        int nextIndex = index + 1;
        while(nextIndex < uniqueNums.size() && uniqueNums.get(nextIndex) == curNum + 1) {
            nextIndex++;
        }
        take += helper(nextIndex, uniqueNums, map);
        return Math.max(skip, take);
    } 
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Solution 2: Memoization (10 min)
class Solution {
    public int deleteAndEarn(int[] nums) {
        // <k, v> -> <num, sum of num>
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + num);
        }
        List<Integer> uniqueNums = new ArrayList<>(map.keySet());
        Collections.sort(uniqueNums);
        // Add memoization array
        Integer[] memo = new Integer[uniqueNums.size()];
        return helper(0, uniqueNums, map, memo);
    }

    private int helper(int index, List<Integer> uniqueNums, Map<Integer, Integer> map, Integer[] memo) {
        if(index >= uniqueNums.size()) {
            return 0;
        }
        // Check memoization
        if (memo[index] != null) {
            return memo[index];
        }
        int curNum = uniqueNums.get(index);
        int curSum = map.get(curNum);
        // Option 1: Skip current num
        int skip = helper(index + 1, uniqueNums, map, memo);
        // Option 2: Take current num
        int take = curSum;
        int nextIndex = index + 1;
        // Skip all numbers that are curNum + 1
        while(nextIndex < uniqueNums.size() && uniqueNums.get(nextIndex) == curNum + 1) {
            nextIndex++;
        }
        take += helper(nextIndex, uniqueNums, map, memo);
        // Store result in memo
        return memo[index] = Math.max(skip, take);
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 3: 1D DP (10 min)
class Solution {
    public int deleteAndEarn(int[] nums) {
        // <k, v> -> <num, sum of num>
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + num);
        }
        List<Integer> uniqueNums = new ArrayList<>(map.keySet());
        Collections.sort(uniqueNums);
        int n = uniqueNums.size();
        // dp[i] represents maximum points we can get from uniqueNums[i] to the end
        int[] dp = new int[n + 1];
        // Base case: when we're beyond the last element
        dp[n] = 0;
        // Fill DP table from right to left (opposite of DFS direction)
        for(int i = n - 1; i >= 0; i--) {
            int curNum = uniqueNums.get(i);
            int curSum = map.get(curNum);
            // Option 1: Skip current num (same as helper(i+1, ...))
            int skip = dp[i + 1];
            // Option 2: Take current num
            int take = curSum;
            int nextIndex = i + 1;
            // Skip all numbers that are curNum + 1
            while(nextIndex < n && uniqueNums.get(nextIndex) == curNum + 1) {
                nextIndex++;
            }
            take += dp[nextIndex];
            dp[i] = Math.max(skip, take);
        }
        return dp[0];
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 4: House Robber style Native DFS (30 min, TLE 24/52)
class Solution {
    public int deleteAndEarn(int[] nums) {
        int maxNum = 0;
        for(int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        int[] arr = new int[maxNum + 1];
        for(int num : nums) {
            arr[num] += num;
        }
        return helper(0, arr);
    }

    private int helper(int index, int[] arr) {
        if(index >= arr.length) {
            return 0;
        }
        // Option 1: Skip
        int skip = helper(index + 1, arr);
        // Option 2: Take
        int take = arr[index] + helper(index + 2, arr);
        return Math.max(skip, take);
    }
}

Time Complexity: O(2^n)
Space Complexity: O(n)

Solution 5: House Robber style Memoization (10 min)
class Solution {
    public int deleteAndEarn(int[] nums) {
        int maxNum = 0;
        for(int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        int[] arr = new int[maxNum + 1];
        for(int num : nums) {
            arr[num] += num;
        }
        Integer[] memo = new Integer[maxNum + 1];
        return helper(0, arr, memo);
    }

    private int helper(int index, int[] arr, Integer[] memo) {
        if(index >= arr.length) {
            return 0;
        }
        if(memo[index] != null) {
            return memo[index];
        }
        // Option 1: Skip
        int skip = helper(index + 1, arr, memo);
        // Option 2: Take
        int take = arr[index] + helper(index + 2, arr, memo);
        return memo[index] = Math.max(skip, take);
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 6: House Robber style 1D DP (30 min)
class Solution {
    public int deleteAndEarn(int[] nums) {
        int maxNum = 0;
        for(int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        int[] arr = new int[maxNum + 1];
        for(int num : nums) {
            arr[num] += num;
        }
        int[] dp = new int[arr.length + 2];
        // Base case: when index >= arr.length, return 0
        dp[arr.length] = 0;
        for(int i = arr.length - 1; i >= 0; i--) {
            // Exact mapping of DFS logic:
            // skip = helper(i + 1, arr)  → dp[i + 1]
            int skip = dp[i + 1];
            // take = arr[i] + helper(i + 2, arr) → arr[i] + dp[i + 2]
            int take = arr[i] + dp[i + 2];
            dp[i] = Math.max(skip, take);
        }
        return dp[0];
    }
}

Time Complexity: O(n)
Space Complexity: O(n)

Solution 7: House Robber style optimized 1D DP (30 min)
class Solution {
    public int deleteAndEarn(int[] nums) {
        int maxNum = 0;
        for(int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        int[] arr = new int[maxNum + 1];
        for(int num : nums) {
            arr[num] += num;
        }
        // Only track two variables instead of full array
        int next1 = 0;  // dp[i + 1] - represents helper(i + 1, arr)
        int next2 = 0;  // dp[i + 2] - represents helper(i + 2, arr)
        int current = 0;
        // Fill from right to left
        for (int i = arr.length - 1; i >= 0; i--) {
            // Exact same logic as DFS:
            int skip = next1;        // helper(i + 1, arr)
            int take = arr[i] + next2; // arr[i] + helper(i + 2, arr)
            current = Math.max(skip, take);
            // Shift variables for next iteration
            next2 = next1;
            next1 = current;
        }
        return current;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
Show me how native DFS solution looks like
For LeetCode 740 "Delete and Earn", here's what a native DFS solution would look like, along with why it's not the optimal approach:
🚫 Native DFS Solution (Inefficient)
class Solution {
    public int deleteAndEarn(int[] nums) {
        // First, we need to transform the problem
        // Count frequency and convert to sorted unique numbers with sums
        Map<Integer, Integer> points = new HashMap<>();
        for (int num : nums) {
            points.put(num, points.getOrDefault(num, 0) + num);
        }
        List<Integer> uniqueNums = new ArrayList<>(points.keySet());
        Collections.sort(uniqueNums);
        return dfs(0, uniqueNums, points);
    }
    
    private int dfs(int index, List<Integer> uniqueNums, Map<Integer, Integer> points) {
        if (index >= uniqueNums.size()) {
            return 0;
        }
        int currentNum = uniqueNums.get(index);
        int currentPoints = points.get(currentNum);
        // Option 1: Skip current number
        int skip = dfs(index + 1, uniqueNums, points);
        // Option 2: Take current number
        int take = currentPoints;
        // Find next index that we can take (skip numbers that are currentNum + 1)
        int nextIndex = index + 1;
        while (nextIndex < uniqueNums.size() && uniqueNums.get(nextIndex) == currentNum + 1) {
            nextIndex++;
        }
        take += dfs(nextIndex, uniqueNums, points);
        return Math.max(skip, take);
    }
}
🔍 How the Native DFS Works
The DFS explores two choices at each step:
1.Skip the current number → move to index + 1
2.Take the current number → earn its points, then skip currentNum + 1 and move to the next safe number
📝 Example Walkthrough
Input: nums = [2,2,3,3,3,4]
Transformed:
Unique numbers: [2, 3, 4]
Points: {2:4, 3:9, 4:4}
DFS Tree:
dfs(0) - number 2 (4 points)
  ├── Skip 2 → dfs(1) - number 3 (9 points)
  │     ├── Skip 3 → dfs(2) - number 4 (4 points) = 4
  │     └── Take 3 → dfs(3) = 9
  └── Take 2 → dfs(2) - number 4 (4 points) = 4 + 4 = 8

Result: max(9, 8) = 9
⚡ Optimized DFS with Memoization
class Solution {
    public int deleteAndEarn(int[] nums) {
        if (nums.length == 0) return 0;
        // Build frequency sum
        int maxNum = 0;
        for (int num : nums) maxNum = Math.max(maxNum, num);
        int[] sum = new int[maxNum + 1];
        for (int num : nums) sum[num] += num;
        Integer[] memo = new Integer[maxNum + 1];
        return dfsMemo(0, sum, memo);
    }
    
    private int dfsMemo(int num, int[] sum, Integer[] memo) {
        if (num >= sum.length) return 0;
        if (memo[num] != null) return memo[num];        
        // Option 1: Skip current number
        int skip = dfsMemo(num + 1, sum, memo);
        // Option 2: Take current number (skip num + 1)
        int take = sum[num] + dfsMemo(num + 2, sum, memo);
        memo[num] = Math.max(skip, take);
        return memo[num];
    }
}
📊 Why DFS is Suboptimal
AspectNative DFSOptimized DFSDPTimeO(2^n)O(n)O(n)SpaceO(n) stackO(n) stack + memoO(1)Practical Use❌ Too slow✅ Acceptable✅ Best
💡 Key Insights
1.Problem Structure: The constraint creates a dependency chain similar to House Robber
2.State Definition: At each number, decide whether to take it or skip it
3.Optimal Substructure: The best solution builds from optimal solutions of subproblems
4.Overlapping Subproblems: The same subproblems are solved repeatedly → need memoization
🎯 Recommendation
While the native DFS helps understand the problem's recursive nature, the DP approach is superior because:
- No recursion overhead
- Better constant factors
- More intuitive for this particular problem structure
- Easier to space-optimize
The DFS thinking process is valuable for understanding, but DP is the right implementation choice for this problem!

Convert your DFS solution into a DP solution
Here's how to convert your DFS solution into a DP solution using bottom-up dynamic programming:
🎯 Converted DP Solution
class Solution {
    public int deleteAndEarn(int[] nums) {
        // <k, v> -> <num, sum of num>
        Map<Integer, Integer> map = new HashMap<>();
        for(int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + num);
        }
        List<Integer> uniqueNums = new ArrayList<>(map.keySet());
        Collections.sort(uniqueNums);
        
        int n = uniqueNums.size();
        // dp[i] represents maximum points we can get from uniqueNums[i] to the end
        int[] dp = new int[n + 1];
        // Base case: when we're beyond the last element
        dp[n] = 0;
        // Fill DP table from right to left (opposite of DFS direction)
        for (int i = n - 1; i >= 0; i--) {
            int curNum = uniqueNums.get(i);
            int curSum = map.get(curNum);
            // Option 1: Skip current num (same as helper(i+1, ...))
            int skip = dp[i + 1];
            // Option 2: Take current num
            int take = curSum;
            int nextIndex = i + 1;
            // Skip all numbers that are curNum + 1 (same logic as DFS)
            while(nextIndex < n && uniqueNums.get(nextIndex) == curNum + 1) {
                nextIndex++;
            }
            take += dp[nextIndex];
            
            dp[i] = Math.max(skip, take);
        }
        return dp[0];
    }
}
🔍 Conversion Process Explained
DFS → DP Mapping:
DFS ConceptDP Equivalenthelper(index, ...)dp[index]helper(index + 1, ...)dp[index + 1]helper(nextIndex, ...)dp[nextIndex]Base case: index >= sizedp[n] = 0Recursive callsIterative computation
📊 Step-by-Step Conversion:
1.Identify DP State: dp[i] = max points from index i to end
2.Base Case: When i == n (beyond array), return 0 → dp[n] = 0
3.Transition: Copy your DFS logic exactly:
- skip = dp[i + 1]
- take = curSum + dp[nextIndex]
4.Direction: DFS goes forward → DP fills backward

Show me how House Robber style DP works
For LeetCode 740 "Delete and Earn", this problem is very similar to the House Robber pattern. Here are the Java solutions:
🔍 How the Solution Works
Step 1: Transform the Problem
- When you take a number x, you get x points but lose all x-1 and x+1
- This is equivalent to: if you take x, you cannot take x-1 or x+1
- Key Insight: This is exactly like House Robber - you can't rob adjacent houses!
Step 2: Build Frequency Sum Array
// Example: nums = [2,2,3,3,3,4]
// sum array becomes:
// index: 0  1  2  3  4
// value: 0  0  4  9  4
Step 3: Apply House Robber DP
For each number i from 0 to maxNum:
- Option 1: Skip current → take best from previous dp[i-1]
- Option 2: Take current → dp[i-2] + sum[i] (can't take i-1)
📝 Alternative Explicit DP Solution
class Solution {
    public int deleteAndEarn(int[] nums) {
        if (nums.length == 0) return 0;
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        int[] sum = new int[maxNum + 1];
        for (int num : nums) {
            sum[num] += num;
        }
        // Explicit DP array
        int[] dp = new int[maxNum + 1];
        dp[0] = sum[0];
        dp[1] = Math.max(sum[0], sum[1]);
        for (int i = 2; i <= maxNum; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + sum[i]);
        }
        return dp[maxNum];
    }
}
🧮 Example Walkthrough
Input: nums = [2,2,3,3,3,4]
Step 1: Build sum array
Number: 0  1  2  3  4
Sum:    0  0  4  9  4
Step 2: Apply DP
i=0: dp[0] = 0
i=1: dp[1] = max(0, 0) = 0  
i=2: dp[2] = max(0, 0+4) = 4
i=3: dp[3] = max(4, 0+9) = 9
i=4: dp[4] = max(9, 4+4) = 9
Result: 9 (take all 3's: 3+3+3 = 9)
💡 Key Insights
1.Transformation: Delete and Earn → House Robber on number line
2.Frequency Sum: Convert array to sum[i] = i * frequency(i)
3.Adjacency Constraint: Taking i prevents taking i-1 and i+1
4.Space Optimization: Only need dp[i-1] and dp[i-2]
📊 Complexity Analysis
- Time Complexity: O(n + k) where n is array length, k is max number
- Space Complexity: O(k) for the sum array
This solution efficiently handles the constraint that deleting a number removes its neighbors!
🚀 Space-Optimized DP (O(1) Space)
🔍 Why Space Optimization is Tricky
The problem is that nextIndex can be any index from i+1 to n, depending on how many consecutive curNum + 1 values exist:
Example: uniqueNums = [1, 2, 3, 5, 6, 8]
- For i=0 (num=1): nextIndex = 3 (skip 2 and 3)
- For i=1 (num=2): nextIndex = 3 (skip 3)  
- For i=2 (num=3): nextIndex = 3 (no skip)
- For i=3 (num=5): nextIndex = 5 (skip 6)
Since nextIndex can jump multiple positions, we need the entire dp array to access arbitrary future states.
💡 Alternative: True O(1) Space with Different Approach
If you really want O(1) space, we need to abandon the uniqueNums approach and use the House Robber pattern:
🎯 Recommendation
For this specific problem, the House Robber approach is the most efficient in terms of both time and space complexity, as it:
- Avoids sorting
- Uses predictable O(k) space
- Has cleaner logic
- Is easier to understand
Thank you for catching the incomplete solution! The key lesson is that when nextIndex can jump arbitrarily far ahead, we need the complete dp array rather than just a few variables.
🎯 Final Optimized DP (House Robber Style)
For this specific problem, we can simplify further using the House Robber pattern:
class Solution {
    public int deleteAndEarn(int[] nums) {
        // Find maximum number
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        // Create sum array where sum[i] = total points from all occurrences of i
        int[] sum = new int[maxNum + 1];
        for (int num : nums) {
            sum[num] += num;
        }
        // Now it becomes House Robber problem
        int prev = 0; // dp[i-2]
        int curr = 0; // dp[i-1]
        for (int i = 0; i <= maxNum; i++) {
            int temp = curr;
            curr = Math.max(curr, prev + sum[i]);
            prev = temp;
        }
        return curr;
    }
}
Time Complexity: O(n + k)
Space Complexity: O(k) where k = max number in nums
The conversion preserves your DFS logic while making it iterative and more efficient!
📊 Comparison
ApproachTimeSpaceKey InsightDFS with MemoO(n)O(n)Memoize by index in uniqueNumsDP ArrayO(n)O(n)Iterative version of DFSHouse Robber DPO(n + k)O(k)Transform to number line problem

Refer to
L198.House Robber (Ref.L213,L337,L2320,L2560)
