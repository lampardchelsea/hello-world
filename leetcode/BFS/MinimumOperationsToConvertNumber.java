https://leetcode.com/problems/minimum-operations-to-convert-number/description/
You are given a 0-indexed integer array nums containing distinct numbers, an integer start, and an integer goal. There is an integer x that is initially set to start, and you want to perform operations on x such that it is converted to goal. You can perform the following operation repeatedly on the number x:
If 0 <= x <= 1000, then for any index i in the array (0 <= i < nums.length), you can set x to any of the following:
- x + nums[i]
- x - nums[i]
- x ^ nums[i] (bitwise-XOR)
Note that you can use each nums[i] any number of times in any order. Operations that set x to be out of the range 0 <= x <= 1000 are valid, but no more operations can be done afterward.
Return the minimum number of operations needed to convert x = start into goal, and -1 if it is not possible.
 
Example 1:
Input: nums = [2,4,12], start = 2, goal = 12
Output: 2
Explanation: We can go from 2 → 14 → 12 with the following 2 operations.- 2 + 12 = 14- 14 - 2 = 12

Example 2:
Input: nums = [3,5,7], start = 0, goal = -4
Output: 2
Explanation: We can go from 0 → 3 → -4 with the following 2 operations. - 0 + 3 = 3- 3 - 7 = -4
Note that the last operation sets x out of the range 0 <= x <= 1000, which is valid.

Example 3:
Input: nums = [2,8,16], start = 0, goal = 1
Output: -1
Explanation: There is no way to convert 0 into 1.
 
Constraints:
- 1 <= nums.length <= 1000
- -109 <= nums[i], goal <= 109
- 0 <= start <= 1000
- start != goal
- All the integers in nums are distinct.
--------------------------------------------------------------------------------
Attempt 1: 2026-08-15
Solution 1: BFS + Level Order Traversal (10 min)
class Solution {
    public int minimumOperations(int[] nums, int start, int goal) {
        // visited 数组记录 [0, 1000] 范围内的数字是否访问过
        boolean[] visited = new boolean[1001];
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);
        visited[start] = true;
        // 当前 BFS 层数（已经走了多少步）
        int steps = 0;
        while(!q.isEmpty()) {
            int size = q.size();
            for(int i = 0; i < size; i++) {
                int x = q.poll();
                // 遍历 nums 数组中的每一个 x，尝试三种运算
                for(int num : nums) {
                    int[] tmp = new int[] {x + num, x - num, x ^ num};
                    for(int next : tmp) {
                        // 如果这一步正好到达 goal，直接返回 steps + 1
                        if(next == goal) {
                            return steps + 1;
                        }
                        // 只有合法的中间状态（0~1000）才允许入队继续搜索
                        // 如果 next 超出范围，它只能是最终状态（即 goal），否则就丢弃
                        if(next >= 0 && next <= 1000 && !visited[next]) {
                            q.offer(next);
                            visited[next] = true;
                        }
                    }
                }
            }
            steps++;
        }
        return -1;
    }
}

Time Complexity: O(1001 * n) ≈ O(n)，其中 n 是 nums 的长度。因为状态只有 1001 种，每个状态最多被访问一次。
Space Complexity: O(1001)，用于 visited 数组和队列。

Refer to Deepseek
这道题 (LeetCode 2059) 和刚才的 560（哈希表）、862（单调队列）截然不同。它本质上是一个 “图上最短路径” 问题。
因为每一步都是等权的（每次操作计数为 1），且数字范围被限制在 0 ~ 1000 之间，所以广度优先搜索 (BFS) 是这道题最优且最直观的解法。
--------------------------------------------------------------------------------
核心思路：为什么是 BFS？
1.有限状态空间：无论怎么变换，有效的中间数字必须保持在 [0, 1000] 之间（除非这一下直接命中 goal）。
2.等权图：从数字 v，我们可以通过 +x、-x、^x 变成另外三个数。每条“边”的权重都是 1。
3.最短路径：要求“最少操作次数”，在等权图中，BFS 第一次遇到目标时，走的路径一定是最短的。
--------------------------------------------------------------------------------
Java 代码实现
import java.util.*;

class Solution {
    public int minimumOperations(int[] nums, int start, int goal) {
        // 特判：如果已经等于目标，0 步
        if (start == goal) return 0;
        
        // visited 数组记录 [0, 1000] 范围内的数字是否访问过
        boolean[] visited = new boolean[1001];
        Queue<Integer> queue = new LinkedList<>();
        
        queue.offer(start);
        visited[start] = true;
        
        int steps = 0; // 当前 BFS 层数（已经走了多少步）
        
        while (!queue.isEmpty()) {
            int size = queue.size(); // 当前层的节点数量
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                
                // 遍历 nums 数组中的每一个 x，尝试三种运算
                for (int x : nums) {
                    int[] nextVals = {
                        cur + x,
                        cur - x,
                        cur ^ x
                    };
                    
                    for (int next : nextVals) {
                        // 如果这一步正好到达 goal，直接返回 steps + 1
                        if (next == goal) {
                            return steps + 1;
                        }
                        
                        // 只有合法的中间状态（0~1000）才允许入队继续搜索
                        // 如果 next 超出范围，它只能是最终状态（即 goal），否则就丢弃
                        if (next >= 0 && next <= 1000 && !visited[next]) {
                            visited[next] = true;
                            queue.offer(next);
                        }
                    }
                }
            }
            // 当前层遍历完毕，步数加 1（准备进入下一层）
            steps++;
        }
        
        // 队列空了都没找到 goal，说明不可达
        return -1;
    }
}
--------------------------------------------------------------------------------
图解示例（手推流程）
输入：nums = [2, 3], start = 5, goal = 1
- 初始：队列 [5]，steps=0。
- 第 1 层（从 5 出发）：
- 对 x=2：5+2=7（入队），5-2=3（入队），5^2=7（重复，跳过）。
- 对 x=3：5+3=8（入队），5-3=2（入队），5^3=6（入队）。
- 未命中 1。steps 变为 1。
- 第 2 层（从 7,3,8,2,6 出发）：
- 从 3 出发，x=2：3-2=1。
- 检测到 next == goal，立即返回 steps + 1 = 2。
结果：5 -> 3 -> 1，两步完成（5 - 2 = 3，再 3 - 2 = 1）。
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：O(1001 * n) ≈ O(n)，其中 n 是 nums 的长度。因为状态只有 1001 种，每个状态最多被访问一次。
- 空间复杂度：O(1001)，用于 visited 数组和队列。
--------------------------------------------------------------------------------
为什么不能像之前 560/862 那样做？
我们把它放进你的“算法武器库”里对比一下，就能立刻看清它的定位：
题号核心特征主要矛盾数据武器560 (和为K)数组前缀，计数存在负数，无法滑窗哈希表862 (和≥K)数组前缀，最短长度存在负数，需要比较索引远近单调队列2059 (本题)数值状态转移状态空间小（0~1000），求最少步数BFS 广搜
一句话总结：当你发现题目是在一个有限的数字区间内来回做 +、-、^ 运算时，BFS 就是唯一正确的条件反射。这道题的核心坑点在于中间状态必须落在 0~1000 之间，只有直接命中 goal 时才允许越界。掌握这个模板后，类似的“最少操作转换数字”类题目（如 2039, 2468 等）都可以通用。

Q1: 对于Leetcode 2059. Minimum Operations to Convert Number 必须要用visited吗？
结论是：必须使用，而且是非用不可。
如果没有 visited 数组（或 Set），BFS 不仅会超时（TLE），甚至可能永远无法终止（陷入死循环）。
为什么？原因有二：
1. 防止“原地鬼打墙”（保证算法终止）
操作中有 + x 和 - x。假设 nums 里有一个 x = 1。
- 从 5 出发，+1 变成 6。
- 从 6 出发，-1 又会变回 5。
如果没有 visited，BFS 的队列会变成：5 -> 6 -> 5 -> 6 -> 5 -> 6 ...。左右横跳，无限循环，永远无法走到目标（或者队列撑爆内存）。
2. 保证时间复杂度（避免指数爆炸）
题目限制了中间状态必须在 [0, 1000] 之间，这意味着整个图最多只有 1001 个节点。
- 有 visited：每个数字最多入队一次，处理每个数字时遍历 nums 数组（长度 n）。复杂度是严格的 O(1001 * n)。
- 无 visited：由于环的存在，同一个数字会被反复入队无数次。BFS 的层数越深，重复访问的冗余计算呈指数级增长，绝对会超时。
--------------------------------------------------------------------------------
如果不用 boolean[]，用 HashSet 行吗？
行。本质上只要是“记录已访问”的机制就是必须的。
Set<Integer> visited = new HashSet<>();
visited.add(start);
// 在入队前检查 if (!visited.contains(next)) { visited.add(next); queue.offer(next); }
但在这道题里，因为状态空间是固定的 0~1000，boolean[] 比 HashSet 更快、更省内存，所以标准答案都用数组。
--------------------------------------------------------------------------------
唯一不用 visited 的特例（本题不适用）
只有在有向无环图 (DAG) 中（比如只能单向增加，不能减少），或者题目保证不会产生环时，才可以不用 visited。
但本题可以 + 也可以 -，显然是个无向图/有环图，所以 visited 是这道题解法成立的基石，缺少它整个 BFS 逻辑就崩塌了。


Refer to
L1658.Minimum Operations to Reduce X to Zero (Ref.L918,L1423)
