https://leetcode.com/problems/removing-minimum-and-maximum-from-array/description/
You are given a 0-indexed array of distinct integers nums.
There is an element in nums that has the lowest value and an element that has the highest value. We call them the minimum and maximum respectively. Your goal is to remove both these elements from the array.
A deletion is defined as either removing an element from the front of the array or removing an element from the back of the array.
Return the minimum number of deletions it would take to remove both the minimum and maximum element from the array.

Example 1:
Input: nums = [2,10,7,5,4,1,8,6]
Output: 5
Explanation: 
The minimum element in the array is nums[5], which is 1.
The maximum element in the array is nums[1], which is 10.
We can remove both the minimum and maximum by removing 2 elements from the front and 3 elements from the back.
This results in 2 + 3 = 5 deletions, which is the minimum number possible.

Example 2:
Input: nums = [0,-4,19,1,8,-2,-3,5]
Output: 3
Explanation: 
The minimum element in the array is nums[1], which is -4.
The maximum element in the array is nums[2], which is 19.
We can remove both the minimum and maximum by removing 3 elements from the front.
This results in only 3 deletions, which is the minimum number possible.

Example 3:
Input: nums = [101]
Output: 1
Explanation:  
There is only one element in the array, which makes it both the minimum and maximum element.We can remove it with 1 deletion.
 
Constraints:
- 1 <= nums.length <= 10^5
- -10^5 <= nums[i] <= 10^5
- The integers in nums are distinct.
--------------------------------------------------------------------------------
Attempt 1: 2024-12-26
Solution 1: Math (10 min)
class Solution {
    public int minimumDeletions(int[] nums) {
        int max = -100001;
        int min = 100001;
        int max_index = -1;
        int min_index = -1;
        for(int i = 0; i < nums.length; i++) {
            if(max < nums[i]) {
                max = nums[i];
                max_index = i;
            }
            if(min > nums[i]) {
                min = nums[i];
                min_index = i;
            }
        }
        int index_1 = -1;
        int index_2 = -1;
        if(max_index > min_index) {
            index_1 = min_index;
            index_2 = max_index;
        } else {
            index_1 = max_index;
            index_2 = min_index;
        }
        // Take nums = {2,10,7,5,4,1,8,6} as example
        // index_1 is for max_index = 1
        // index_2 is for min_index = 5
        // 'op_count_1' is operation count before reach index_1 as 1 (remove 2)
        // 'op_count_2' is operation count after index_2 and before reach index_2 as 3 (remove 7,5,4)
        // 'op_count_3' is operation count after index_2 as 2 (remove 8,6)
        // The concept is we only have three choices (op_count_1 + op_count_2, 
        // op_count_1 + op_count_3, op_count_2 + op_count_3), which means 
        // remove the smallest sum up two out of 'op_count_1', 'op_count_2' 
        // and 'op_count_3', then remain part is only need to take out target
        // two elements (minimum  + maximum), so when we find the smallest
        // combinations out of three choices, plus 2 is the answer 
        int op_count_1 = index_1;
        int op_count_2 = index_2 - index_1 - 1;
        int op_count_3 = nums.length - index_2 - 1;
        return 2 + Math.min(op_count_1 + op_count_2, Math.min(op_count_1 + op_count_3, op_count_2 + op_count_3));
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/removing-minimum-and-maximum-from-array/solutions/1599809/c-only-3-cases/
Intuition: Find the two indices of the maximum and minimum elements, say a and b. The order doesn't matter. We only need to consider 3 cases:
1.Delete both from left and right.
2.Delete only from left
3.Delete only from right
Algorithm:
Make sure a <= b, and return the minimum of the following:
- (a + 1) + (N - b) -> delete both from left and right
- b + 1 -> delete from left
- N - a -> delete from right
// OJ: https://leetcode.com/problems/removing-minimum-and-maximum-from-array/
// Author: github.com/lzl124631x
// Time: O(N)
// Space: O(1)
class Solution {
public:
    int minimumDeletions(vector<int>& A) {
        int a = max_element(begin(A), end(A)) - begin(A), b = min_element(begin(A), end(A)) - begin(A), N = A.size();
        if (a > b) swap(a, b);
        return min({ a + 1 + N - b, b + 1, N - a });
    }
};

Refer to Deepseek
For LeetCode 2091, we need to find the minimum number of removals to delete both the minimum and maximum elements from an array. Here's the optimal Java solution:
🏆 Optimal Solution: Three Strategies
class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        if (n <= 2) return n; // Need to remove all elements
        
        // Find indices of min and max
        int minIdx = 0, maxIdx = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }
        
        // Ensure minIdx < maxIdx for easier calculation
        if (minIdx > maxIdx) {
            int temp = minIdx;
            minIdx = maxIdx;
            maxIdx = temp;
        }
        
        // Three possible strategies:
        // 1. Remove both from left (remove up to maxIdx)
        int fromLeft = maxIdx + 1;
        
        // 2. Remove both from right (remove from minIdx to end)
        int fromRight = n - minIdx;
        
        // 3. Remove min from left, max from right
        int fromBothSides = (minIdx + 1) + (n - maxIdx);
        
        // Return minimum of the three
        return Math.min(fromLeft, Math.min(fromRight, fromBothSides));
    }
}
🧠 How It Works: The Three Strategies
For any array with min at index i and max at index j (assuming i < j):
Array: [a0, a1, a2, ..., ai, ..., aj, ..., an-1]
                         ↑         ↑
                         min at i  max at j
Three removal strategies:
1.Remove from left only: Remove all elements from start to j → j + 1 operations
2.Remove from right only: Remove all elements from i to end → n - i operations
3.Remove from both sides: Remove from start to i AND from j to end → (i + 1) + (n - j) operations
🔍 Visual Example
Example 1: nums = [2,10,7,5,4,1,8,6]
- Min (1) at index 5, Max (10) at index 1
- Strategy 1 (left only): Remove indices 0-1 → 2 operations
- Strategy 2 (right only): Remove indices 5-7 → 3 operations
- Strategy 3 (both sides): Remove indices 0-5 (6) AND no right removal → 6 operations
- Minimum = 2 ✓
Example 2: nums = [0,-4,19,1,8,-2,-3,5]
- Min (-4) at index 1, Max (19) at index 2
- Strategy 1 (left only): Remove indices 0-2 → 3 operations
- Strategy 2 (right only): Remove indices 1-7 → 7 operations
- Strategy 3 (both sides): Remove indices 0-1 (2) + indices 2-7 (6) → 8 operations
- Minimum = 3 ✓
📊 Alternative: More Explicit Version
class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        if (n == 1) return 1;
        if (n == 2) return 2;
        
        // Find min and max indices
        int minIdx = 0, maxIdx = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }
        
        // Calculate distances
        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);
        
        // Case 1: Remove from left (through right element)
        int option1 = right + 1;
        
        // Case 2: Remove from right (through left element)
        int option2 = n - left;
        
        // Case 3: Remove left element from left, right element from right
        int option3 = (left + 1) + (n - right);
        
        return Math.min(option1, Math.min(option2, option3));
    }
}
⚡ Optimized One-Pass Solution
class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;
        int minIdx = 0, maxIdx = 0;
        
        // Single pass to find min and max
        for (int i = 0; i < n; i++) {
            if (nums[i] < nums[minIdx]) minIdx = i;
            if (nums[i] > nums[maxIdx]) maxIdx = i;
        }
        
        int left = Math.min(minIdx, maxIdx);
        int right = Math.max(minIdx, maxIdx);
        
        // Three strategies in one line
        return Math.min(right + 1, Math.min(n - left, left + 1 + n - right));
    }
}
📈 Complexity Analysis
AspectValueExplanationTimeO(n)One pass to find min/max indicesSpaceO(1)Only a few variablesOperations3 comparisonsCompare three strategies
💡 Key Insight
The problem reduces to: Given two indices i and j in array of length n, find minimum removals to delete both.
This is purely a positioning problem - the actual values don't matter, only the indices!
🔄 Mathematical Proof
Let l = min(i,j), r = max(i,j):
1.Remove prefix up to r: cost = r + 1
2.Remove suffix from l: cost = n - l
3.Remove both sides: cost = (l + 1) + (n - r)
The minimum is: min(r+1, n-l, l+1+n-r)
🧪 Edge Cases
// Test cases:
1. [5] → 1 (remove only element)
2. [5,2] → 2 (remove both)
3. [1,2,3] → 2 (remove [1,3] or [1,2] or [2,3])
4. Min and max at ends: [1,...,100] → 2 (remove both ends)
5. Min and max adjacent: [10,1,5,20] → 2 (remove [10,1] or [1,5,20])
🎯 Why This Solution is Optimal
1.O(n) time unavoidable: Must scan array to find min/max
2.O(1) space optimal: Only store indices
3.Simple math: Just position calculations, no complex logic
4.Handles all cases: The three strategies cover all possibilities
⚠️ Common Pitfalls
1.Forgetting to handle n ≤ 2: Need special case
2.Assuming minIdx < maxIdx: Should sort indices
3.Overcomplicating: Don't need to actually remove elements
The solution is elegant because it recognizes this is just a geometry problem on indices!

Refer to
L1423.Maximum Points You Can Obtain from Cards (Ref.L1658,L2091,L2379)
L1647.Minimum Deletions to Make Character Frequencies Unique
