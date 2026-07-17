https://leetcode.com/problems/count-subarrays-with-fixed-bounds/description/
You are given an integer array nums and two integers minK and maxK.
A fixed-bound subarray of nums is a subarray that satisfies the following conditions:
- The minimum value in the subarray is equal to minK.
- The maximum value in the subarray is equal to maxK.
Return the number of fixed-bound subarrays.
A subarray is a contiguous part of an array.
 
Example 1:
Input: nums = [1,3,5,2,7,5], minK = 1, maxK = 5
Output: 2
Explanation: The fixed-bound subarrays are [1,3,5] and [1,3,5,2].

Example 2:
Input: nums = [1,1,1,1], minK = 1, maxK = 1
Output: 10
Explanation: Every subarray of nums is a fixed-bound subarray. There are 10 possible subarrays.
 
Constraints:
- 2 <= nums.length <= 10^5
- 1 <= nums[i], minK, maxK <= 10^6
--------------------------------------------------------------------------------
Attempt 1: 2026-07-15
Solution 1: Multiple Pointers + Sliding Window (30 min)
class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long count = 0;
        int lastBad = -1;  // 上一次遇到的越界元素位置（小于 minK 或大于 maxK）
        int lastMin = -1;  // 上一次遇到的 minK 的位置
        int lastMax = -1;  // 上一次遇到的 maxK 的位置
        for(int i = 0; i < nums.length; i++) {
            int num = nums[i];
            // 1. 检查是否是“坏元素”
            if(num < minK || num > maxK) {
                // 更新坏元素位置，从此往左的起点全部失效
                lastBad = i;
            }
            // 2. 更新最小值和最大值的最新位置
            if(num == minK) {
                lastMin = i;
            }
            if(num == maxK) {
                lastMax = i;
            }
            // 3. 计算以 i 为右端点的合法子数组数量
            // 左端点的最小起点 = lastBad + 1
            // 左端点的最大允许起点 = Math.min(lastMin, lastMax)
            // 两者相减，若为正则累加
            int validLeftBoundary = Math.min(lastMin, lastMax);
            if(validLeftBoundary > lastBad) {
                count += validLeftBoundary - lastBad;
            }
        }
        return count;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)
Refer to Deepseek
这道题 (LeetCode 2444) 是一个非常巧妙的 “单次遍历 + 维护最后出现位置” 的问题，难度为 Hard。它不像之前 2294 那样可以排序贪心，而是需要基于子数组连续性的精密计数。
核心解题思路
一个子数组要满足“固定边界”，必须同时满足三个条件：
1.必须包含至少一个 minK。
2.必须包含至少一个 maxK。
3.不能包含任何小于 minK 或大于 maxK 的“坏元素”（因为一旦包含，最大/最小值就变了）。
当我们遍历数组，以当前索引 i 作为子数组的右端点时，我们只需要知道三个关键信息：
- lastBad：最后一次出现的“坏元素”的索引。
- lastMin：最后一次出现的 minK 的索引。
- lastMax：最后一次出现的 maxK 的索引。
逻辑推导：
对于固定的右端点 i，左端点 l 能取的范围是 (lastBad, min(lastMin, lastMax)]。
- 因为左端点 必须大于 lastBad，否则坏元素就被包含进来了。
- 同时，左端点 必须小于等于 min(lastMin, lastMax)，否则就无法同时包含 minK 和 maxK。
因此，以 i 结尾的合法子数组数量 = Math.max(0, Math.min(lastMin, lastMax) - lastBad)。
--------------------------------------------------------------------------------
Java 代码实现
class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long ans = 0; // 结果可能很大，用 long
        int lastBad = -1;  // 上一次遇到的越界元素位置（小于 minK 或大于 maxK）
        int lastMin = -1;  // 上一次遇到的 minK 的位置
        int lastMax = -1;  // 上一次遇到的 maxK 的位置

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];

            // 1. 检查是否是“坏元素”
            if (num < minK || num > maxK) {
                lastBad = i; // 更新坏元素位置，从此往左的起点全部失效
            }

            // 2. 更新最小值和最大值的最新位置
            if (num == minK) {
                lastMin = i;
            }
            if (num == maxK) {
                lastMax = i;
            }

            // 3. 计算以 i 为右端点的合法子数组数量
            // 左端点的最小起点 = lastBad + 1
            // 左端点的最大允许起点 = Math.min(lastMin, lastMax)
            // 两者相减，若为正则累加
            int validLeftBoundary = Math.min(lastMin, lastMax);
            if (validLeftBoundary > lastBad) {
                ans += validLeftBoundary - lastBad;
            }
        }
        return ans;
    }
}
--------------------------------------------------------------------------------
图解示例
以 nums = [1, 3, 5, 2, 7, 5], minK = 1, maxK = 5 为例：
索引 inums[i]lastBadlastMinlastMax计算 min(lastMin,lastMax) - lastBad说明（以 i 结尾的合法子数组）01-10-1-1 - (-1) = 0缺少 maxK，0 个13-10-1-1 - (-1) = 0缺少 maxK，0 个25-1020 - (-1) = 1[1,3,5]，共 1 个32-1020 - (-1) = 1[1,3,5,2]，共 1 个（必须包含索引0的1）474020 - 4 = -4 → 0坏元素 7 出现，跨过它左边全废，0 个554050 - 4 = -4 → 0左端点必须 >4，但包含不了索引0的 1，0 个最终答案 = 1 + 1 = 2。
--------------------------------------------------------------------------------
横向对比：从 1438 / 2294 到 2444 的思维演进
这道题完美体现了 “双指针/滑动窗口”问题的两种不同形态，可以帮你建立更清晰的解题框架：
题目核心约束解决问题的关键动作计数方式LeetCode 1438 (最长子数组)窗口内 max - min <= limit，找最长。窗口无效时收缩左边界，直到重新有效。只关心窗口长度 r - l + 1 的最大值。LeetCode 2294 (分割数组)每个子序列内 max - min <= k，找最少分割。排序后，贪心扩展当前分段。累加切割次数。LeetCode 2444 (固定边界子数组)子数组内 必须 有 minK且 有 maxK，且不能越界。维护最近一次 minK、maxK 和越界元素的位置。数学计算min(lastMin, lastMax) - lastBad 累加。
为什么这道题不能简单用“双指针收缩”？
因为双指针收缩通常用于找最长或最短的满足条件的区间。但本题要求 “计数” ，即所有可能的左右端点组合。如果窗口内同时包含多个 minK 和 maxK，左端点可以在很大范围内移动，单纯滑动窗口会重复统计或遗漏。因此，我们必须用“记录最后一次出现位置”的数学方法精确计算。
总结：当你遇到 “统计满足特定最值条件的子数组个数” 且数组元素有明确上下界时，lastBad + lastMin/Max 是非常经典的 O(n) 秒杀技巧，务必掌握。

Refer to Kimi
Here is the standard O(n) one-pass Java solution using the "most recent positions" technique.
class Solution {
    public long countSubarrays(int[] nums, int minK, int maxK) {
        long ans = 0;
        
        int bad = -1;       // most recent index where nums[i] is out of [minK, maxK]
        int leftMin = -1;   // most recent index where nums[i] == minK
        int leftMax = -1;   // most recent index where nums[i] == maxK
        
        for (int i = 0; i < nums.length; i++) {
            // 1. Update the "bad" boundary
            if (nums[i] < minK || nums[i] > maxK) {
                bad = i;
            }
            
            // 2. Update most recent minK and maxK positions
            if (nums[i] == minK) {
                leftMin = i;
            }
            if (nums[i] == maxK) {
                leftMax = i;
            }
            
            // 3. Count valid subarrays ending at i
            //    The left boundary can be anywhere in (bad, min(leftMin, leftMax)]
            int validLeft = Math.min(leftMin, leftMax);
            if (validLeft > bad) {
                ans += validLeft - bad;
            }
        }
        
        return ans;
    }
}
--------------------------------------------------------------------------------
How it works
For every index i, we treat it as the right end of a subarray and ask: how many left boundaries make a valid subarray?
A valid subarray ending at i must satisfy three things:
ConditionWhat guarantees itContains at least oneminKLeft boundary ≤ leftMinContains at least onemaxKLeft boundary ≤ leftMaxContains no out-of-bounds elements (< minK or > maxK)Left boundary > bad
So the left boundary must satisfy:
bad < left_boundary ≤ min(leftMin, leftMax)
If validLeft = min(leftMin, leftMax) is greater than bad, the number of choices is validLeft - bad. Otherwise, there are zero valid subarrays ending at i.
--------------------------------------------------------------------------------
Example Walkthrough
nums = [1,3,5,2,7,5], minK = 1, maxK = 5
inums[i]badleftMinleftMaxvalidLeftvalidLeft > bad?addans01-10-1-1no0013-10-1-1no0025-1020yes (0 > -1)1132-1020yes12474020no02554050no02
Result: 2
The valid subarrays are [1,3,5] and [1,3,5,2].
--------------------------------------------------------------------------------
Complexity
TimeO(n) — single passSpaceO(1) — only three index trackers
The key insight is that we don't enumerate subarrays; we simply track the most recent critical positions and use them to count in $O(1)$ per element.


Refer to
L1248.Count Number of Nice Subarrays (Ref.L930,L992)
L1438.Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
