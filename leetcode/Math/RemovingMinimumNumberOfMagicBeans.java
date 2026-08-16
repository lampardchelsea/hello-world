https://leetcode.com/problems/removing-minimum-number-of-magic-beans/description/
You are given an array of positive integers beans, where each integer represents the number of magic beans found in a particular magic bag.
Remove any number of beans (possibly none) from each bag such that the number of beans in each remaining non-empty bag (still containing at least one bean) is equal. Once a bean has been removed from a bag, you are not allowed to return it to any of the bags.
Return the minimum number of magic beans that you have to remove.
 
Example 1:
Input: beans = [4,1,6,5]
Output: 4
Explanation: 
- We remove 1 bean from the bag with only 1 bean.  This results in the remaining bags: [4,0,6,5]
- Then we remove 2 beans from the bag with 6 beans.  This results in the remaining bags: [4,0,4,5]
- Then we remove 1 bean from the bag with 5 beans.  This results in the remaining bags: [4,0,4,4]
We removed a total of 1 + 2 + 1 = 4 beans to make the remaining non-empty bags have an equal number of beans.
There are no other solutions that remove 4 beans or fewer.

Example 2:
Input: beans = [2,10,3,2]
Output: 7
Explanation:
- We remove 2 beans from one of the bags with 2 beans.  This results in the remaining bags: [0,10,3,2]
- Then we remove 2 beans from the other bag with 2 beans.  This results in the remaining bags: [0,10,3,0]
- Then we remove 3 beans from the bag with 3 beans.   This results in the remaining bags: [0,10,0,0]
We removed a total of 2 + 2 + 3 = 7 beans to make the remaining non-empty bags have an equal number of beans.
There are no other solutions that removes 7 beans or fewer.
 
Constraints:
- 1 <= beans.length <= 105
- 1 <= beans[i] <= 105
--------------------------------------------------------------------------------
Attempt 1: 2026-08-15
Solution 1: Sorting + Math (10 min)
class Solution {
    public long minimumRemoval(int[] beans) {
        // 1. 排序，让大小相邻的靠在一起
        Arrays.sort(beans);
        // 2. 计算总豆子数（用 long 防止溢出）
        long total = 0;
        for(int bean : beans) {
            total += bean;
        }
        // 3. 枚举每一个袋子作为“最终值”的基准
        long maxKeep = 0;
        for(int i = 0; i < beans.length; i++) {
            // 如果最终所有非空袋子都是 beans[i] 颗豆子
            // 那么从 i 到末尾的所有袋子都能保留 beans[i] 颗
            long keep = (long) beans[i] * (beans.length - i);
            maxKeep = Math.max(maxKeep, keep);
        }
        // 4. 移除最少 = 总数 - 保留最多
        return total - maxKeep;
    }
}

Refer to Deepseek
这道题 (LeetCode 2171) 是一道排序 + 枚举的经典题目，难度为 Medium。
和之前那些依赖“前缀和 + 哈希表”或“BFS”的题目不同，这道题的解法非常直观：先把袋子按豆子数量排序，然后枚举“最后非空袋子应该有多少颗豆子”。
--------------------------------------------------------------------------------
核心思路：排序 + 枚举最终值
假设我们最终想让所有非空袋子里的豆子数量都等于 X。
- 豆子少于 X 的袋子：只能全部清空（变成 0，因为不能增加豆子）。
- 豆子多于 X 的袋子：减少到 X。
关键数学优化：
为了让移除的豆子数量最少，我们保留的豆子数量就要最多。
如果我们把数组排好序 sorted，并决定让 sorted[i] 成为最终的值 X，那么：
- 索引 >= i 的所有袋子都可以保留至少 X 颗（它们会被削减到 X）。
- 因此，最终保留下来的豆子总数 = X * (数组长度 - i) = sorted[i] * (n - i)。
我们要做的就是遍历所有 i，找出能保留的最大豆子数。
答案 = 总豆子数 - 最大可保留豆子数。
--------------------------------------------------------------------------------
Java 代码实现
import java.util.Arrays;

class Solution {
    public long minimumRemoval(int[] beans) {
        // 1. 排序，让大小相邻的靠在一起
        Arrays.sort(beans);
        int n = beans.length;
        
        // 2. 计算总豆子数（用 long 防止溢出）
        long total = 0;
        for (int b : beans) {
            total += b;
        }
        
        // 3. 枚举每一个袋子作为“最终值”的基准
        long maxKeep = 0;
        for (int i = 0; i < n; i++) {
            // 如果最终所有非空袋子都是 beans[i] 颗豆子
            // 那么从 i 到末尾的所有袋子都能保留 beans[i] 颗
            long keep = (long) beans[i] * (n - i);
            maxKeep = Math.max(maxKeep, keep);
        }
        
        // 4. 移除最少 = 总数 - 保留最多
        return total - maxKeep;
    }
}
--------------------------------------------------------------------------------
图解示例（手推流程）
输入：beans = [4, 1, 6, 5]
1.排序：[1, 4, 5, 6]，总和 total = 16。
2.枚举最终值 X：
索引 i基准值 X = beans[i]保留豆子数 X * (n - i)需要移除的豆子数 16 - 保留数011 * 4 = 412144 * 3 = 124（最优解：把 1 清空，5→4，6→4）255 * 2 = 106366 * 1 = 610
最终答案：4。
--------------------------------------------------------------------------------
为什么最终值一定是数组中的某个元素？
假设我们选了一个不在数组中的数 X = 4.5（虽然豆子是整数，但这里用于证明）。那么所有大于 4.5 的袋子都要削减到 4.5，但我们完全可以把这个目标值提升到 5（数组中的下一个整数），这样：
- 保留的豆子变多了（因为每个袋子多留了 0.5 颗）。
- 且没有违反任何规则（所有袋子依然相等）。
因此，最优解的目标值 X 一定等于某个袋子的原始豆子数。
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：O(n log n)，主要开销在排序上。
- 空间复杂度：O(log n) 到 O(n)（取决于排序算法）。

Refer to
L462.Minimum Moves to Equal Array Elements II (Ref.L453)
L1658.Minimum Operations to Reduce X to Zero (Ref.L918,L1423)


