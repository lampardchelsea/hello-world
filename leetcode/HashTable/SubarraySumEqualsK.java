
https://leetcode.com/problems/subarray-sum-equals-k/
Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
A subarray is a contiguous non-empty sequence of elements within an array.

Example 1:
Input: nums = [1,1,1], k = 2
Output: 2

Example 2:
Input: nums = [1,2,3], k = 3
Output: 2

Constraints:
- 1 <= nums.length <= 2 * 10^4
- -1000 <= nums[i] <= 1000
- -10^7 <= k <= 10^7
--------------------------------------------------------------------------------
Attempt 1: 2023-02-04
Solution 1:  Native for loop with 2 passes (30 min, first pass is create preSum array, second pass is calculate interval with nested for loop)
class Solution {
    public int subarraySum(int[] nums, int k) {
        int[] preSum = new int[nums.length + 1];
        preSum[0] = 0;
        for(int i = 1; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i - 1];
        }
        int count = 0;
        for(int start = 0; start < preSum.length; start++) {
            for(int end = start + 1; end < preSum.length; end++) {
                if(preSum[end] - preSum[start] == k) {
                    count++;
                }
            }
        }
        return count;
    }
}

Time Complexity: O(n^2)  
Space Complexity: O(n)

Solution 2:  Hash Table + Auxiliary array (30 min)
class Solution { 
    public int subarraySum(int[] nums, int k) { 
        Map<Integer, Integer> map = new HashMap<Integer, Integer>(); 
        //map.put(0, 0); 
        map.put(0, 1); 
        int[] preSum = new int[nums.length + 1]; 
        int count = 0; 
        for(int i = 1; i <= nums.length; i++) { 
            preSum[i] = preSum[i - 1] + nums[i - 1]; 
            if(map.containsKey(preSum[i] - k)) { 
                count += map.get(preSum[i] - k); 
            } 
            map.put(preSum[i], map.getOrDefault(preSum[i], 0) + 1); 
        } 
        return count; 
    } 
}

Time Complexity: O(n) 
Space Complexity: O(n)

Refer to
https://leetcode.com/problems/subarray-sum-equals-k/solutions/803317/java-solution-with-detailed-explanation/
Thinking
1. Use an array to store the sum accumulated from the beginning to a certain position.
Example:
nums = [1,   2,   3  ]
sum  = [1, 1+2, 1+2+3]

2. How to create array "sum" ?
sum[i] = sum[i - 1] + nums[i]
Q : If i == 0, the index is out of range. How to solve this problem ?
A : Set the first element of the array "sum" to 0, and initialize the array "sum" from index 1 rather than 0.
nums = [1,   2,   3  ]
sum  = [0,   1,   1+2, 1+2+3] // Also, the length of "sum" is one more than "nums"  
sum[i] = sum[i - 1] + nums[i - 1]

// Java Version
int[] sum = new int[nums.length + 1];
sum[0] = 0;
for (int i = 1; i < (nums.length + 1); i++)
  sum[i] = sum[i - 1] + nums[i - 1];

3. Using array "sum" to calculate the sum of a subarray
sumOfSubarray = sum[end] - sum[start];
For example : Calculate the sum of "nums" means using the last element of "sum" minus the first element of "sum" which is 0.
nums[0] + nums[1] + nums[2] = sum[3] - sum[0] = 6 - 0

4.Using array "sum"to caculate all possibilities .
Code
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
      
        int[] sum = new int[nums.length + 1];
        sum[0] = 0;
        for (int i = 1; i <= nums.length; i++)
            sum[i] = sum[i - 1] + nums[i - 1];
      
        for (int start = 0; start < sum.length; start++) {
            for (int end = start + 1; end < sum.length; end++) {
                if (sum[end] - sum[start] == k)
                    count++;
            }
        }
      
        return count;
    }
}
Complexity Analysis
- Time complexity : O(n^2).
- Space complexity : O(n).
Optimization by Hashmap
Thinking
1.In the previous method
Step 1. The "nums" array is traversed to calculate all the elements of the sum array
Step 2. Use the nested loop to judge.
key : Can we judge when the array is traversed(Step 1) ?
Transposition
int[] sum = new int[nums.length + 1];
sum[0] = 0;
for (int end = 1; end < (nums.length + 1); end++)
  sum[end] = sum[end - 1] + nums[end - 1];
a. Put each element of "sum" array into hashmap according to this format : (sumi, number of occurence)
b. When constructing the "sum" array, we take the currently constructed element as sum[end], then all the elements before "end" which have been calculated can be regarded as all sum[start] for this "end".
Transform the judgment condition
Obviously, when sum[end] is calculated, all its possible sum[start] are already in the map.
sum[end] - sum[start] == k
sum[end] - k == sum[start]
c. When sumend is calculated, we only need to determine whether there is key == sumend - k in the hashmap and add the number of occurrence to the answer.
Attention : In the previous method, we set the first element of sum to 0. Similarly, we put it in the hashmap, which is (0, 1).
Code
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0, sum = 0;
        HashMap < Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
      
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
Complexity Anaysis
- Time complexity : O(n).
- Space complexity : O(n).
--------------------------------------------------------------------------------
Solution 3:  Hash Table + Single Varaiable (30 min)
Actually no need preSum array, we can replace its functionality with a single variable 'presSum'
class Solution {
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        int preSum = 0;
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        for(int num : nums) {
            preSum += num;
            count += map.getOrDefault(preSum - k, 0);
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }
        return count;
    }
}

Time Complexity: O(n) 
Space Complexity: O(1)

Refer to
https://leetcode.com/problems/subarray-sum-equals-k/solutions/102106/java-solution-presum-hashmap/
Solution 1. Brute force. We just need two loops (i, j) and test if SUM[i, j] = k. Time complexity O(n^2), Space complexity O(1). I bet this solution will TLE.
Solution 2. From solution 1, we know the key to solve this problem is SUM[i, j]. So if we know SUM[0, i - 1] and SUM[0, j], then we can easily get SUM[i, j]. To achieve this, we just need to go through the array, calculate the current sum and save number of all seen PreSum to a HashMap. Time complexity O(n), Space complexity O(n).
public class Solution {
    public int subarraySum(int[] nums, int k) {
        int sum = 0, result = 0;
        Map<Integer, Integer> preSum = new HashMap<>();
        preSum.put(0, 1);
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (preSum.containsKey(sum - k)) {
                result += preSum.get(sum - k);
            }
            preSum.put(sum, preSum.getOrDefault(sum, 0) + 1);
        }
        
        return result;
    }
}
A harder problem
https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/
https://leetcode.com/problems/number-of-submatrices-that-sum-to-target/discuss/803353/java-solution-with-detailed-explanation

Refer to Deepseek
这道题 (LeetCode 560) 和刚才的 862 非常相似——数组里都有负数，因此无法用双指针滑动窗口。但它们的目标完全不同，这就决定了数据结构的差异：
- 862 (Shortest Subarray)：要找最短的长度，所以需要在满足条件时比较索引远近，此时需要单调队列来维护候选起点的"顺序"。
- 560 (Subarray Sum Equals K)：要找等于 K 的子数组个数，我们只关心"数量"，不关心长度，此时只需要哈希表 (HashMap) 来记录前缀和出现的"次数"。
因为负数存在，一旦当前前缀和变大或变小，我们没法像全是正数时那样通过移动左指针来"纠正"窗口，所以必须依赖前缀和公式来精准命中。
--------------------------------------------------------------------------------
Java 代码实现
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraySum(int[] nums, int k) {
        // 1. 哈希表：key 是前缀和，value 是该前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        // 重要初始化：前缀和为 0 的情况出现了一次（表示空前缀）
        // 这是为了处理那些从数组索引 0 开始就满足 sum == k 的子数组
        prefixSumCount.put(0, 1); 
        
        int currentSum = 0;
        int count = 0;
        
        for (int num : nums) {
            // 2. 累加当前前缀和
            currentSum += num;
            
            // 3. 核心公式：若存在一个之前的 prefixSum，使得 currentSum - prefixSum == k
            // 即 prefixSum == currentSum - k
            int target = currentSum - k;
            if (prefixSumCount.containsKey(target)) {
                // 如果存在，说明从这些前缀和对应的位置到当前位置之间，有子数组和为 k
                count += prefixSumCount.get(target);
            }
            
            // 4. 将当前前缀和存入（或更新）哈希表，供后续元素使用
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
}
--------------------------------------------------------------------------------
图解示例（感受哈希表的作用）
输入：nums = [1, 2, 3], k = 3
步骤遍历元素当前前缀和 currentSum目标 currentSum - k哈希表中命中？动作累计结果 count0(初始化)0--放入 {0:1}01111 - 3 = -2否 (不存在)放入 {0:1, 1:1}02233 - 3 = 0是 (存在1次)count += 1；放入 {0:1, 1:1, 3:1}1 (子数组 [1,2])3366 - 3 = 3是 (存在1次)count += 1；放入 {0:1, 1:1, 3:1, 6:1}2 (子数组 [3])
最终返回 2，完美命中。
--------------------------------------------------------------------------------
针对负数情况的验证（哈希表为什么厉害？）
输入：nums = [1, -1, 0], k = 0
期望输出：3 (子数组 [1, -1], [0], [1,-1,0])
- 遍历到 1：currentSum=1, target=1, 命中 0? 否，存入 {0:1, 1:1}。
- 遍历到 -1：currentSum=0, target=0, 命中 {0:1}，count=1 (得到 [1,-1])。存入，{0:2, 1:1}。
- 遍历到 0：currentSum=0, target=0, 命中 {0:2}，count += 2 (得到 [0] 和 [1,-1,0])。总 count=3。
试想如果用双指针，面对负数 -1 和 0，左指针根本不知道何时该移动，哈希表完美解决了这个问题。
--------------------------------------------------------------------------------
与之前高频题目的横向对比（建立终极思维框架）
来画一张清晰的进化图，帮你一秒判断用双指针、单调队列还是哈希表：
题号数组特征查询目标核心数据结构原因LeetCode 209全是正数和 >= target 的最短长度双指针 (滑动窗口)窗口和具有单调性，可安全伸缩。LeetCode 1658全是正数从两边减到 x 的最少步数双指针 (滑动窗口)转化为找中间和为 total-x 的最长窗口，同样单调。LeetCode 862包含负数和 >= k 的最短长度前缀和 + 单调队列负数破坏单调性，必须保留所有候选起点并按前缀和排序，比较索引差。LeetCode 560 (本题)包含负数和 == k 的个数前缀和 + 哈希表只计数，不关心顺序/长度，直接用哈希表 O(1) 查出 current - k 出现过几次。
总结一句话：
刷到子数组和题目，先看有没有负数。
- 没有负数 → 双指针。
- 有负数，且找最短长度 → 单调队列。
- 有负数，且数个数 → 哈希表。
这就是你从 209 → 1658 → 862 → 560 一路刷下来积累的“条件反射”！




Refer to
L713.Subarray Product Less Than K (Ref.L325,L560,L2302)
L724.Find Pivot Index
L1074.Number of Submatrices That Sum to Target (Ref.L560,L2556)
L1658.Minimum Operations to Reduce X to Zero (Ref.L918,L1423)
L2090.K Radius Subarray Averages (Ref.L560)
L2219.Maximum Sum Score of Array (Ref.L560)
