https://leetcode.com/problems/maximum-beauty-of-an-array-after-applying-operation/description/
You are given a 0-indexed array nums and a non-negative integer k.
In one operation, you can do the following:
- Choose an index i that hasn't been chosen before from the range [0, nums.length - 1].
- Replace nums[i] with any integer from the range [nums[i] - k, nums[i] + k].
The beauty of the array is the length of the longest subsequence consisting of equal elements.
Return the maximum possible beauty of the array nums after applying the operation any number of times.
Note that you can apply the operation to each index only once.
A subsequence of an array is a new array generated from the original array by deleting some elements (possibly none) without changing the order of the remaining elements.
 
Example 1:
Input: nums = [4,6,1,2], k = 2
Output: 3
Explanation: In this example, we apply the following operations:
- Choose index 1, replace it with 4 (from range [4,8]), nums = [4,4,1,2].
- Choose index 3, replace it with 4 (from range [0,4]), nums = [4,4,1,4].
After the applied operations, the beauty of the array nums is 3 (subsequence consisting of indices 0, 1, and 3).
It can be proven that 3 is the maximum possible length we can achieve.

Example 2:
Input: nums = [1,1,1,1], k = 10
Output: 4
Explanation: In this example we don't have to apply any operations.The beauty of the array nums is 4 (whole array).
 
Constraints:
- 1 <= nums.length <= 10^5
- 0 <= nums[i], k <= 10^5
--------------------------------------------------------------------------------
Attempt 1: 2026-07-17
Solution 1: Sorting + Not Fixed Length Sliding Window (10 min)
class Solution {
    public int maximumBeauty(int[] nums, int k) {
        // 1. 排序，让相近的数靠在一起
        Arrays.sort(nums);
        int left = 0;
        int maxLen = 0;
        int n = nums.length;
        // 2. 滑动窗口：right 不断右移
        for(int right = 0; right < n; right++) {
            // 如果当前窗口内最大值 - 最小值 > 2*k，窗口不合法，左指针右移
            while(nums[right] - nums[left] > 2 * k) {
                left++;
            }
            // 窗口合法，更新答案
            maxLen = Math.max(maxLen, right - left + 1);
        }
        return maxLen;
    }
}

Time Complexity: O(n*logn)
Space Complexity: O(1)

Refer to Deepseek
这道题 (LeetCode 2779) 和刚才的 2294 长得很像，但思考角度恰好反过来。我把它们放在一起对比，你就能一眼看穿本质。
核心思路：排序 + 滑动窗口（双指针）
关键转化：
如果我们要让数组中的某几个数最终都变成同一个值，那么这几个数中，最大值与最小值的差必须 <= 2 * k。
为什么是 2*k？
因为每个数 x 只能变成 [x - k, x + k] 区间内的数。要让区间内的两个数 a 和 b（假设 a <= b）能变成同一个值，它们的区间必须有交集，即 b - a <= 2k。
由于我们只关心“数量”，不关心具体顺序，所以可以先排序。排序后，问题就变成了：
在有序数组中找到最长的连续子数组，使得 nums[right] - nums[left] <= 2*k。
这个子数组的长度，就是最大“美丽度”（能变成相同值的最大元素个数）。
--------------------------------------------------------------------------------
Java 代码实现
import java.util.Arrays;

class Solution {
    public int maximumBeauty(int[] nums, int k) {
        // 1. 排序，让相近的数靠在一起
        Arrays.sort(nums);
        
        int left = 0;
        int ans = 0;
        int n = nums.length;
        
        // 2. 滑动窗口：right 不断右移
        for (int right = 0; right < n; right++) {
            // 如果当前窗口内最大值 - 最小值 > 2*k，窗口不合法，左指针右移
            while (nums[right] - nums[left] > 2L * k) {
                left++;
            }
            // 窗口合法，更新答案
            ans = Math.max(ans, right - left + 1);
        }
        
        return ans;
    }
}
注意事项
- 使用 2L * k 防止 2 * k 在极端情况下（如 k = 1e9）溢出 int。
--------------------------------------------------------------------------------
图解示例
输入：nums = [1, 2, 4, 6], k = 2
- 2k = 4
- 排序后：[1, 2, 4, 6]
- 窗口 [1, 2, 4]：4 - 1 = 3 <= 4，合法，长度 = 3。
- 窗口扩展到 [1, 2, 4, 6]：6 - 1 = 5 > 4，不合法，左指针右移。
- 窗口变为 [2, 4, 6]：6 - 2 = 4 <= 4，合法，长度 = 3。
- 结果：3
--------------------------------------------------------------------------------
横向对比：Leetcode 2294 vs 2779
这两道题并排看，特别有意思，它们共享 “排序 + 滑动窗口” 的外壳，但核心判据完全不同：
对比维度LeetCode 2294 (分区数组)LeetCode 2779 (最大美丽度)目标最少分区数（切得越少越好）最大组大小（堆得越多越好）操作方式一个分区内，最小值为起点，只要 <= min + k 就放进一个桶。所有元素都能被“修改”，目标是让它们最终相等。合法性判据单向约束：当前值 - 分区起点 <= k。双向约束：窗口最大值 - 窗口最小值 <= 2*k。左指针移动时机遇到 > min + k 时，直接重置起点（左指针跳到当前值）。遇到 > 2*k 时，左指针逐个右移缩小窗口（因为起点变了，min 变了）。代码细节int start = nums[left]，然后内部 while 扩展。直接用 nums[right] - nums[left] 作为窗口范围。
--------------------------------------------------------------------------------
为什么是“滑动窗口”而不是“贪心”？
在 2294 中，因为目标是“尽可能少切分”，所以从固定最小值出发贪心吃到饱，是最优的。
在 2779 中，因为目标是“尽可能多容纳”，但窗口的合法范围是动态的。比如 [1, 100] 不合法，我必须缩小左边界。左边界收缩后，新的“最小值”变了，窗口可以继续尝试扩容。所以必须用双指针动态调整，而不是一次性的贪心切割。
--------------------------------------------------------------------------------
总结
- 看到“可以修改 ±k” → 转化为“差值 <= 2k”。
- 看到“最大数量” → 排序 + 滑动窗口（双指针）。
- 看到“最少分区” → 排序 + 贪心（以最小值为锚点切块）。
这道题属于 LeetCode 周赛的经典“排序 + 双指针”题型，代码简洁，但需要想通 2*k 这个关键转化点。掌握之后，它就和 2294 一起成了你的“排序窗口”双生子模板了。

Refer to Kimi
Here is the clean Java solution for Leetcode 2779. Maximum Beauty of an Array After Applying Operation.
Key Insight
Each element nums[i] can be changed to any value in the range [nums[i] - k, nums[i] + k]. We want the maximum number of elements that can be made equal (or have the same value after operation).
This is equivalent to: find the maximum number of overlapping intervals [nums[i] - k, nums[i] + k].
The classic way to solve "maximum overlapping intervals" is:
1.Sort all interval endpoints
2.Use a sliding window / two pointers on the sorted array
But there's an even simpler observation: if we sort nums, then for any target value, the valid elements form a contiguous subarray. Specifically, we want the longest subarray where nums[right] - nums[left] <= 2 * k (because each can shift by k toward each other).
Java Solution: Sliding Window on Sorted Array
import java.util.Arrays;

class Solution {
    public int maximumBeauty(int[] nums, int k) {
        Arrays.sort(nums);
        
        int left = 0;
        int maxBeauty = 0;
        
        for (int right = 0; right < nums.length; right++) {
            // Shrink window if the range is too large
            // nums[right] - nums[left] > 2 * k means we can't make them all equal
            while (nums[right] - nums[left] > 2 * k) {
                left++;
            }
            
            // Current window [left, right] can all be made equal
            maxBeauty = Math.max(maxBeauty, right - left + 1);
        }
        
        return maxBeauty;
    }
}
How it works
StepExplanationSortAfter sorting, elements that can be made equal must be close to each otherSliding windowFor each right, find the smallest left such that nums[right] - nums[left] <= 2kWhy 2k?Element nums[left] can go up by k, element nums[right] can go down by k. They can meet if nums[right] - nums[left] <= 2k
Example Walkthrough
nums = [4,6,1,2], k = 2
- After sort: [1, 2, 4, 6]
- right=0 (1): window [1], diff=0, beauty=1
- right=1 (2): window [1,2], diff=1 ≤ 4, beauty=2
- right=2 (4): window [1,2,4], diff=3 ≤ 4, beauty=3
- right=3 (6): window [1,2,4,6], diff=5 > 4, shrink left → [2,4,6], diff=4 ≤ 4, beauty=3
Result: 3
Complexity
Time$O(n \log n)$ — sorting dominatesSpace$O(1)$ extra (or $O(\log n)$ for sort)
Connection to Your Previous Questions
This problem is structurally very similar to 1438 (Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit):
- 1438: Find longest subarray where max - min <= limit (unsorted, need two deques)
- 2779: Find longest subarray where max - min <= 2k (can sort first, so simple two pointers)
The key difference is that 2779 allows sorting, which reduces it from a hard sliding window problem to a simple two-pointer problem.


Refer to
L2294.Partition Array Such That Maximum Difference Is K
