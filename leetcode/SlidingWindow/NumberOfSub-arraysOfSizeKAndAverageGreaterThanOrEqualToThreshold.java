https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/description/
Given an array of integers arr and two integers k and threshold, return the number of sub-arrays of size k and average greater than or equal to threshold.
 
Example 1:
Input: arr = [2,2,2,2,5,5,5,8], k = 3, threshold = 4
Output: 3
Explanation: Sub-arrays [2,5,5],[5,5,5] and [5,5,8] have averages 4, 5 and 6 respectively. All other sub-arrays of size 3 have averages less than 4 (the threshold).

Example 2:
Input: arr = [11,13,17,23,29,31,7,5,2,3], k = 3, threshold = 5
Output: 6
Explanation: The first 6 sub-arrays of size 3 have averages greater than 5. Note that averages are not integers.
 
Constraints:
- 1 <= arr.length <= 105
- 1 <= arr[i] <= 104
- 1 <= k <= arr.length
- 0 <= threshold <= 104
--------------------------------------------------------------------------------
Attempt 1: 2026-08-16
Solution 1: Fixed Length Sliding Window (10 min)
Refer to Deepseek
这道题 (LeetCode 1343) 是滑动窗口系列里最简单、最标准的形态，难度为 Medium 但实际偏 Easy。
因为窗口大小是固定的 k，我们不需要像 209 那样动态伸缩左边界，只需要像传送带一样，每次右移一步，减去离开窗口的元素，加上新进入的元素即可。
核心技巧：将“平均值 >= threshold”转化为“总和 >= threshold * k”，避免浮点数运算。
--------------------------------------------------------------------------------
Java 代码实现
class Solution {
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        int n = arr.length;
        // 1. 将除法转化为乘法，避免浮点数精度问题
        int target = threshold * k; 
        
        // 2. 计算第一个窗口的和
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }
        
        int count = 0;
        // 检查第一个窗口是否满足条件
        if (windowSum >= target) count++;
        
        // 3. 滑动窗口：从索引 k 开始遍历到末尾
        for (int i = k; i < n; i++) {
            // 窗口右移：加入 arr[i]，移除 arr[i - k]
            windowSum += arr[i] - arr[i - k];
            
            // 检查当前窗口
            if (windowSum >= target) count++;
        }
        
        return count;
    }
}
--------------------------------------------------------------------------------
图解示例
输入：arr = [2, 2, 2, 2, 5, 5, 5, 8], k = 3, threshold = 4
转化目标：target = 4 * 3 = 12
步骤窗口 [left, right]窗口和 windowSum是否 >= 12?计数 count(初始)[0, 2] = [2, 2, 2]6否0滑动 1[1, 3] = [2, 2, 2]6 + (2 - 2) = 6否0滑动 2[2, 4] = [2, 2, 5]6 + (5 - 2) = 9否0滑动 3[3, 5] = [2, 5, 5]9 + (5 - 2) = 12是1滑动 4[4, 6] = [5, 5, 5]12 + (5 - 2) = 15是2滑动 5[5, 7] = [5, 5, 8]15 + (8 - 5) = 18是3
最终返回 3 ✅
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：O(n)。数组遍历一次，每个元素进入窗口一次，离开窗口一次。
- 空间复杂度：O(1)。
--------------------------------------------------------------------------------
与之前同类型题目的横向对比（完善你的武器库）
这道题是固定窗口大小的计数题，与之前做过的可变窗口形成了鲜明对比：
- 题目说 "大小为 K" → 固定窗口 → 先算第一个窗口，然后 sum += arr[i] - arr[i-k]。
- 题目说 "最短/最长" 或 "总和 >= target" → 可变窗口 → 用 while 动态调整左指针。
这道题唯一要留意的就是把乘法 threshold * k 写成 long 防止溢出（虽然这道题约束不大会溢出，但养成习惯更安全）。掌握了这个模板，类似“大小为 K 的子数组平均值/最大值”的题都可以秒杀了。

追问: 如果我不想用两个for循环呢？
完全正确！ 你的这段代码是 LeetCode 1343 的标准 “一趟遍历（One-pass）” 写法，相比于我之前的“先算第一个窗口 + 再滑动”的双循环，你的写法更优雅、更简洁，逻辑上没有任何问题。
你的代码成功地将“初始化窗口”和“滑动窗口”合并到了同一个 for 循环中，利用 j 作为左指针延迟删除，是面试官非常喜欢的写法。
--------------------------------------------------------------------------------
代码逻辑验证
我带你快速走一遍你的逻辑（以 arr=[2,2,2,2,5], k=3, threshold=4 为例）：
1.i=0,1,2：只累加 sum，不删除。当 i=2（>=k-1）时，检查第一个窗口 [2,2,2]（和6 < 12，不计数）。
2.i=3：sum 加 2 变成 8，因为 i>=k，执行 sum -= arr[0]（2），j++，窗口变为 [2,2,2]（索引1,2,3），和变为 6。检查条件，不计数。
3.i=4：sum 加 5 变成 11，因为 i>=k，执行 sum -= arr[1]（2），j++，窗口变为 [2,2,5]（索引2,3,4），和变为 9。检查条件（9 >= 12？否）。
逻辑完全正确。
--------------------------------------------------------------------------------
唯一需要留意的小隐患（整数溢出）
虽然这道题的约束（1 <= k <= arr.length <= 10^5, 1 <= threshold <= 10^4）使得 k * threshold 最大为 10^9，远小于 int 的极限 2.147e9，所以不会溢出。但为了写出健壮且专业的代码，建议将 target 提升为 long，或者将 threshold 强转为 long。
你的代码只需微调一句：
// 将 int 改为 long，防止极极端测试用例下的溢出
long target = (long) k * threshold;
同时，sum 最大也可能达到 10^5 * 10^4 = 10^9，在 int 范围内，所以 sum 用 int 是安全的。如果你追求极致的严谨，也可以把 sum 定义为 long。
--------------------------------------------------------------------------------
优化后的最终版（基于你的思路）
class Solution {
    public int numOfSubarrays(int[] arr, int k, int threshold) {
        long target = (long) k * threshold; // 防止溢出
        int sum = 0;
        int count = 0;
        int left = 0; // 左指针
        
        for (int right = 0; right < arr.length; right++) {
            sum += arr[right]; // 右指针扩展
            
            // 当窗口大小超过 k 时，移除左指针指向的元素
            if (right >= k) {
                sum -= arr[left];
                left++;
            }
            
            // 窗口大小达到 k 时，判断是否满足条件
            if (right >= k - 1 && sum >= target) {
                count++;
            }
        }
        return count;
    }
}
--------------------------------------------------------------------------------
总结对比（为什么你的解法更优？）
对比维度我之前给的双循环法你的单循环法（One-pass）代码行数较多（初始化和滑动分开）更少更紧凑循环次数2 个循环（但总 O(n)）1 个循环（本质上仍是 O(n)，但常数更小）可读性清晰但略显啰嗦非常优雅，利用了 right >= k 的判断将初始化融入滑动过程
你的解法其实是 1343 题的最优解，也是绝大多数高赞答案采用的写法。恭喜你，这说明你已经彻底掌握了固定大小滑动窗口的延迟删除技巧！


Refer to
L2090.K Radius Subarray Averages (Ref.L560)
L2488.Count Subarrays With Median K
L2772.Apply Operations to Make All Array Elements Equal to Zero (Ref.L523,L1343)
