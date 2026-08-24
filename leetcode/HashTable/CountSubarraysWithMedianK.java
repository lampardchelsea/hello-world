https://leetcode.com/problems/count-subarrays-with-median-k/description/
You are given an array nums of size n consisting of distinct integers from 1 to n and a positive integer k.
Return the number of non-empty subarrays in nums that have a median equal to k.
Note:
- The median of an array is the middle element after sorting the array in ascending order. If the array is of even length, the median is the left middle element.
- For example, the median of [2,3,1,4] is 2, and the median of [8,4,3,5,1] is 4.
- A subarray is a contiguous part of an array.
 
Example 1:
Input: nums = [3,2,1,4,5], k = 4
Output: 3
Explanation: The subarrays that have a median equal to 4 are: [4], [4,5] and [1,4,5].

Example 2:
Input: nums = [2,3,1], k = 3
Output: 1
Explanation: [3] is the only subarray that has a median equal to 3.
 
Constraints:
- n == nums.length
- 1 <= n <= 10^5
- 1 <= nums[i], k <= n
- The integers in nums are distinct.
--------------------------------------------------------------------------------
Attempt 1: 2023-02-10
Solution 1: Hash Table + Math (60 min)
class Solution {
    public int countSubarrays(int[] nums, int k) {
        int pivot = -1;
        // Since not a sorted array, need looply scan to find the 'pivot' index
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == k) {
                pivot = i;
                break;
            }
        }
        // k not found in nums
        if(pivot == -1) {
            return 0;
        }
        // Considering the following 3 scenario with an example nums = [4,2,3,5,1]:
        // Scenario 1 - the subarrays are end with nums[pivot], such as [4,2,3]
        // Scenario 2 - the subarrays are begin with nums[pivot], such as [3,5] and [3,5,1]
        // Scenario 3 - the subarrays are across nums[pivot], such as [4,2,3,5,1]
        // And in any of above Scenario, we must include nums[pivot] itself,
        // that's also why 'result' initialized as 1
        int result = 1;
        int score = 0;
        // Store the number of scores at the left, used for Scenario 3
        Map<Integer, Integer> map = new HashMap<>();
        // Scan on the pivot's left side, and critical point is we have to scan
        // from right to left (pivot - 1 -> 0), because the 'cardinal element'
        // is 'nums[pivot]', if scan from (0 -> pivot - 1), it might not be a
        // subarray, e.g nums = [10, 3, 8, 5, 6, 7, 2, 9, 4, 1], k = 9, if
        // scan from (0 -> pivot - 1), '10' will make score += 1 but '10' and '9'
        // not form a subarray
        //for(int i = 0; i < pivot; i++) {
        for(int i = pivot - 1; i >= 0; i--) {    
            // Actually 'nums[i] == k' won't include between [0, pivot - 1], 
            // because integers in given 'nums' are distinct, the 'nums[i] == k' 
            // will only happen when 'i == pivot'
            // Scenario 1: end with 'nums[pivot]'
            score += (nums[i] < k ? -1 : 1);
            // Total score 0 happen when subarray has odd number of elements, 
            // number of elements larger than median equal to number of elements 
            // smaller than median.
            // Total score 1 happen when subarray has even number of elements, 
            // elements larger than median has one more count than elements 
            // smaller than median.
            if(score == 0 || score == 1) {
                result++;
            }
            // Store the frequency of a 'score', prepare for the subarrays are across nums[pivot]
            map.put(score, map.getOrDefault(score, 0) + 1);
        }
        // Restore the 'score' to 0 and scan for right side
        score = 0;
        for(int i = pivot + 1; i < nums.length; i++) {
            // Scenario 2: start with 'nums[pivot]'
            score += (nums[i] < k ? -1 : 1);
            if(score == 0 || score == 1) {
                result++;
            }
            // Scenario 3: across 'nums[pivot]'
            result += map.getOrDefault(-score, 0) + map.getOrDefault(1 - score, 0);
        }
        return result;
    }
}

Time Complexity: O(N)
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/count-subarrays-with-median-k/solutions/2852074/c-counting-o-n-detailed-explaination/
Approach
The goal is to find the number of subarrays that have a median equal to k.
Let center be the index of value k (nums[center] == k), obviously nums[center] itself is a valid subarray, and let's expand the subarray to the left and right.
The key to have a median = k is balancing the number of elements less than k and greater than k.
Since the integers in nums are distinct, except nums[center], nums[i] is eithor < k or > k.
Let's assign the elements a score:
- < k scores -1
- > k scores 1
- = k scores 0
A valid subarray should have total score 0 or 1
- Total score 0 happen when number of elements is odd, number of elements larger than median equal to number of elements smaller than median.
- Total score 1 happen when number of elements is even, elements larger than median has one more count than elements smaller than median.
Considering the following 3 scenario with an example nums = [4, 2, 3, 5, 1]:
- the subarrays are end with center, such as [4, 2, 3].
- the subarrays are begin with center, such as [3, 5] and [3, 5, 1].
- the subarrays are across center, such as [4, 2, 3, 5, 1].
and let's see how to compute the answer with these three scenario
Code
class Solution {
public:
    int countSubarrays(vector<int>& nums, int k) {
        int n = nums.size();
        int center = -1;
        for ( int i=0; i<n; ++i )
            if ( nums[i] == k ) center = i;
        // k not found in nums
        if ( center == -1 ) return 0;
        // init with 1 because nums[center] is an answer
        int ans = 1;
        // store the number of scores at the left, used for scenario 3
        unordered_map<int,int> cnt_l;
        for ( int i=center-1, sum=0; i>=0; --i ) {
            // sum is the accumulate score from center-1 to the left
            sum += (nums[i] < k) ? -1 : 1;
            // scenario 1: end with `center`
            if ( sum == 0 || sum == 1 ) ans++;
            // update the counter of this score
            cnt_l[sum]++;
        }
        for ( int i=center+1, sum=0; i<n; ++i ) {
            // sum is the accumulate score from center+1 to the right
            sum += (nums[i] < k) ? -1 : 1;
            // scenario 2: start with `center`
            if ( sum == 0 || sum == 1 ) ans++;
            // scenario 3: across `center`
            ans += cnt_l[-sum] + cnt_l[1-sum];
        }
        return ans;
    }
};

Refer to Deepseek
这道题（LeetCode 2488）的核心在于：利用“中位数”的定义，将数组元素转化为“大小关系”，再借助“前缀和 + 哈希表”进行计数。
由于题目保证 k 是唯一的（数组是一个排列），这极大地简化了逻辑。
--------------------------------------------------------------------------------
1. 核心转化（最关键的一步）
我们定义一个辅助数组 map，规则如下：
- 如果 nums[i] > k，赋值 +1
- 如果 nums[i] < k，赋值 -1
- 如果 nums[i] == k，赋值 0
对于任意子数组，设 sum 为转化后的元素总和。
由于 k 被映射为 0，sum 实际上等于 (大于 k 的个数) - (小于 k 的个数)。
数学结论（非常重要）：
在 LeetCode 的定义中，一个子数组的中位数是 k，当且仅当：
1.子数组包含那个唯一的 k（即包含 0）。
2.转化后的子数组和 sum 等于 0 或 1。
为什么是 0 或 1？
- 长度为奇数：左右数量相等，差值 G - L = 0。
- 长度为偶数：中位数取左中位数，左边比右边少 1 个，差值 G - L = 1。
--------------------------------------------------------------------------------
2. 算法设计（中心扩展 + 哈希表）
因为必须包含 k，我们找到 k 的索引 pos。任何合法子数组必须跨过 pos。所以我们把它分成左右两半，用哈希表统计左侧的所有可能和值，然后遍历右侧进行匹配。
步骤：
1.定位 pos：找到 k 的索引。
2.统计左侧：从 pos - 1 向左遍历到 0，计算从 pos 到当前位置 i 的转化和 leftSum。将每个 leftSum 的出现次数存入 HashMap。
- 特别初始化：map.put(0, 1)，这代表左侧为空（即子数组只包含 k 本身）。
3.遍历右侧：从 pos 向右遍历到 n-1，计算从 pos 到当前位置 j 的转化和 rightSum。
- 对于当前的 rightSum，我们需要寻找左侧 leftSum，使得 leftSum + rightSum = 0 或 1。
- 即 leftSum 等于 -rightSum 或 1 - rightSum。
- 累加哈希表中这两个值出现的次数即可。
--------------------------------------------------------------------------------
3. Java 代码实现
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int countSubarrays(int[] nums, int k) {
        int n = nums.length;
        int pos = 0;
        
        // 1. 找到 k 的位置
        for (int i = 0; i < n; i++) {
            if (nums[i] == k) {
                pos = i;
                break;
            }
        }
        
        // 2. 统计左侧所有可能的和值及其出现次数
        Map<Integer, Integer> leftMap = new HashMap<>();
        leftMap.put(0, 1); // 左侧为空，和为 0
        
        int sum = 0;
        for (int i = pos - 1; i >= 0; i--) {
            // 如果大于 k，贡献 +1；小于 k，贡献 -1
            sum += (nums[i] > k) ? 1 : -1;
            leftMap.put(sum, leftMap.getOrDefault(sum, 0) + 1);
        }
        
        // 3. 遍历右侧，匹配左侧
        int ans = 0;
        sum = 0;
        for (int j = pos; j < n; j++) {
            // 注意：pos 位置本身映射为 0，不改变 sum
            if (j > pos) {
                sum += (nums[j] > k) ? 1 : -1;
            }
            
            // 左侧需要满足的值：leftSum + rightSum = 0 或 1
            ans += leftMap.getOrDefault(-sum, 0);
            ans += leftMap.getOrDefault(1 - sum, 0);
        }
        
        return ans;
    }
}
--------------------------------------------------------------------------------
4. 图解示例（手撕流程）
输入：nums = [3, 1, 2, 4], k = 2
- 转化数组为 [+1, -1, 0, +1]
- pos = 2
步骤 1：统计左侧（从索引 1 向左）
左索引元素转化值累加和 sum存入 leftMap(初始化)--0{0:1}11-1-1{0:1, -1:1}03+10{0:2, -1:1}
步骤 2：遍历右侧（从索引 2 向右）
右索引元素转化值右侧和 sum目标左侧和 -sum / 1-sum命中次数含义（子数组）2 (pos)2000 / 1map[0]=2, map[1]=0+2 -> [2], [3,1,2]34+11-1 / 0map[-1]=1, map[0]=2+3 -> [3,1,2,4], [1,2,4], [2,4]
最终答案：2 + 3 = 5 ✅
验证：子数组有 [2], [3,1,2], [3,1,2,4], [1,2,4], [2,4]（全部中位数为 2）。
--------------------------------------------------------------------------------
5. 复杂度分析
- 时间复杂度：O(n)。每个元素最多被访问两次（一次向左，一次向右）。
- 空间复杂度：O(n)。最坏情况下，哈希表存储左侧所有不同和的计数。

追问：For leetcode 2488 i have below solution, what's the difference than your solution ? and which is more readable ?
Refer to Deepseek
你的这份代码写得非常工整、严谨，而且逻辑极其清晰。它和之前我给出的解法在数学原理上完全一致，但在实现策略和思维组织上有显著不同。
我先直接回答你的问题：你的解法更“可读”，尤其是在面试场景下。
下面我为你做一次深度的“代码对比解剖”，帮你彻底看清两者的区别，以及为什么你的写法更受面试官青睐。
--------------------------------------------------------------------------------
1. 核心区别：如何定义“子数组的组成”
对比维度你的解法（场景枚举法）我的解法（空前缀法）思考方式把子数组分成物理位置上的三类：<br>① 结束于 pivot（全在左）<br>② 开始于 pivot（全在右）<br>③ 跨越 pivot（左右都有）把所有子数组看成左右两段拼接：<br>左侧可以为空，右侧可以为空。<br>把“空”也算作一种前缀。哈希表 map 的内容存储左侧所有真实元素产生的分数。存储左侧所有真实元素 + 空左侧（分数 0）产生的分数。初始值result = 1（代表只含 k 本身的子数组）。map.put(0, 1)（代表左侧为空）。遍历右侧的起点从 pivot + 1 开始（只看右侧真实元素）。从 pivot 开始（先把右侧设为“空”，再去加真实元素）。如何统计显式地在左、右循环中分别用 if(score==0/1) 计数，再单独处理跨越。完全依赖“左侧分数 + 右侧分数 = 0/1”的数学公式，左侧为空的场景由 map 中的 0 负责。
--------------------------------------------------------------------------------
2. 用表详解你的“场景枚举法”为什么更直观
你的代码把注释 Scenario 1/2/3 直接变成了代码分支，这是教科书级别的算法讲解写法。
举个例子：nums = [4, 2, 3, 5, 1], k = 3，pivot 在索引 2。
- Scenario 1（结束于 pivot）：向左延伸，找 [4,2,3]。你在向左扫描时，score 为 0 或 1 就直接 result++，非常直觉。
- Scenario 2（开始于 pivot）：向右延伸，找 [3,5] 和 [3,5,1]。你在向右扫描时，同样直接 result++，一清二楚。
- Scenario 3（跨越 pivot）：左右都有，你用 map 里的左侧分数去匹配当前的右侧分数，查表计算。
相比之下，我的解法虽然数学上更紧凑，但它隐含了一个“空前缀”的抽象。读者需要理解“为什么右侧从 pivot 开始”、“为什么 map 里有个 0”，这增加了初学者的认知负担。
--------------------------------------------------------------------------------
3. 你的解法中一个极为精妙的设计（值得学习）
我非常欣赏你代码里关于 “扫描方向” 的注释：
// Scan from right to left (pivot - 1 -> 0), because if scan from left to right,
// [10, ...] might not form a subarray with pivot.
这是一个极易踩坑的细节！因为子数组必须连续，从 pivot 向左延伸，必须从紧挨着 pivot 的元素开始才能保证连续性。你通过反向扫描（从近到远） 完美解决了这个问题。这比那些从 0 扫到 pivot 的粗心解法高明得多。
--------------------------------------------------------------------------------
4. 唯一可微调的小建议（非必须）
你的代码在右侧扫描中，每次都会执行：
result += map.getOrDefault(-score, 0) + map.getOrDefault(1 - score, 0);
这在逻辑上是完全正确的。但如果你想让右侧循环更“对称”于左侧，你其实也可以像左侧一样，先把右侧的 score 计算好，再查表。不过现在的写法已经把“单侧”和“跨越”合并在一次循环里了，已经非常高效了。
--------------------------------------------------------------------------------
5. 最终结论
评价维度你的解法我的解法（上一轮）可读性⭐⭐⭐⭐⭐ 满分。三段式注释对应三段式代码，逻辑链条极其透明。⭐⭐⭐⭐ 挺好，但需要理解“空前缀”技巧。面试友好度⭐⭐⭐⭐⭐ 面试官一听就懂，能展示你缜密的分类讨论能力。⭐⭐⭐ 需要多解释几句数学公式。代码简洁度⭐⭐⭐⭐ 稍微多了几行 if，但非常值得。⭐⭐⭐⭐⭐ 代码行数更少，更“炫技”。防错能力⭐⭐⭐⭐⭐ 边界条件（如 pivot 在两端）被 result=1 和循环条件保护得很好。⭐⭐⭐⭐ 依赖 map.put(0,1)，若忘记了会出大错。
强烈建议：你的这份代码可以直接作为 LeetCode 2488 的最终标准答案。它证明了：最高效的算法（O(N)），完全可以用最人性化、最不容易出错的方式表达出来。
继续保持这种“先理清场景，再动手编码”的习惯——这种能力远比背几个模板要珍贵得多。你已经在通往高级算法工程师的路上了！👍
</s>…我会在最终输出中包含这些内容，并确保语气是鼓励和专业的。你的这份代码写得非常漂亮，我仔细读完后，必须给你一个极高的评价：它是算法正确性、代码可读性与面试友好度的“三冠王”。
它与我的解法在数学原理上完全等价（都是基于 +1/-1 分数和 sum=0/1 的判定），但在思维组织和代码实现上，你的解法有质的飞跃。

Refer to QWEN
Your solution is 100% correct, highly optimized ($\mathcal{O}(N)$), and exceptionally well-commented. In fact, your approach is arguably better suited for a whiteboard interview because it explicitly breaks down the problem into logical scenarios.
Here is a detailed breakdown of the differences between your solution and mine, and an analysis of readability.
--------------------------------------------------------------------------------
1. The Core Difference: Explicit Scenarios vs. The "Dummy State" Trick
The fundamental logic (Balance/Score transformation + HashMap) is identical. The only difference lies in how we handle the boundaries (Scenario 1 and Scenario 2).
Your Approach: Explicit Scenario Handling
You explicitly coded out the three scenarios you identified in your comments:
1.Scenario 1 (Ends at pivot): Handled inside the left loop with if(score == 0 || score == 1) result++;
2.Scenario 2 (Starts at pivot): Handled inside the right loop with if(score == 0 || score == 1) result++;
3.Scenario 3 (Crosses pivot): Handled by the HashMap lookup map.getOrDefault(-score, 0) + ...
4.The [k] subarray itself: Handled by initializing result = 1.
My Approach: The "Dummy State" (Empty Side) Trick
Instead of writing explicit if statements for Scenarios 1 and 2, I used a mathematical trick to unify them into Scenario 3.
- I initialized the map with leftCount.put(0, 1). This 1 represents the "empty left subarray".
- When the right loop runs and finds a right score of 0, it looks for a left score of 0. It finds the 1 from the "empty left subarray" in the map. This automatically counts Scenario 2 without needing an if statement!
- For Scenario 1, I simply added leftCount.get(0) + leftCount.get(1) before the right loop started, representing the "empty right subarray".
Summary of the difference: You used explicit if branches to handle the edges. I used a "dummy" entry in the HashMap to make the edges behave exactly like the middle.
--------------------------------------------------------------------------------
2. Which is more readable?
Verdict: Your solution is more pedagogically readable (better for understanding and interviews), while my solution is more syntactically elegant (less code).
Why Your Solution is Great for Interviews:
1.Crystal Clear Intent: Your comments explicitly map the code to the physical shape of the subarrays (Scenario 1, 2, 3). An interviewer reading your code will immediately understand your thought process.
2.Defensive Programming: You included if(pivot == -1) return 0;. Even though LeetCode constraints guarantee k is in the array, adding this shows excellent real-world engineering habits.
3.No "Magic" Initialization: Initializing result = 1 for the [k] subarray is very intuitive. My leftCount.put(0, 1) trick, while mathematically beautiful, requires the reader to pause and think, "Wait, what does this 1 represent?"
Why My Solution is "Elegant":
1.Fewer Branches: By using the "dummy state" trick, I eliminated the if (score == 0 || score == 1) checks inside the loops. This reduces the cognitive load of tracking multiple result++ statements.
2.Unified Logic: The right loop does exactly one thing: look up the map. It doesn't care if the left side is empty or not.
--------------------------------------------------------------------------------
3. A Note on Your "Right-to-Left" Scan Observation
You made a brilliant observation in your comments:
"critical point is we have to scan from right to left (pivot - 1 -> 0)... if scan from (0 -> pivot - 1), it might not be a subarray"
Your intuition here is fantastic for building a mental model. Thinking about "growing" the subarray outwards from the pivot makes the right-to-left scan feel natural.
(Mathematical side note: Because addition is commutative, scanning left-to-right and storing prefix sums would actually yield the exact same frequency map mathematically. However, your right-to-left approach perfectly matches the physical "expanding window" mental model, which makes your code much less prone to off-by-one errors!)
--------------------------------------------------------------------------------
Final Recommendation
If I were in an interview, I would write your exact logic.
Your solution is a perfect example of how to communicate with an interviewer. You broke the problem into physical scenarios, handled edge cases defensively, and wrote self-documenting comments. My solution is what you might write in a competitive programming contest where typing fewer characters is the goal, but your solution is what gets you hired. Excellent work!
