https://leetcode.com/problems/categorize-box-according-to-criteria/description/
Given four integers length, width, height, and mass, representing the dimensions and mass of a box, respectively, return a string representing the category of the box.
- The box is "Bulky" if:
- Any of the dimensions of the box is greater or equal to 104.
- Or, the volume of the box is greater or equal to 109.
- If the mass of the box is greater or equal to 100, it is "Heavy".
- If the box is both "Bulky" and "Heavy", then its category is "Both".
- If the box is neither "Bulky" nor "Heavy", then its category is "Neither".
- If the box is "Bulky" but not "Heavy", then its category is "Bulky".
- If the box is "Heavy" but not "Bulky", then its category is "Heavy".
Note that the volume of the box is the product of its length, width and height.
 
Example 1:
Input: length = 1000, width = 35, height = 700, mass = 300
Output: "Heavy"
Explanation: 
None of the dimensions of the box is greater or equal to 104. Its volume = 24500000 <= 109. So it cannot be categorized as "Bulky".
However mass >= 100, so the box is "Heavy".
Since the box is not "Bulky" but "Heavy", we return "Heavy".

Example 2:
Input: length = 200, width = 50, height = 800, mass = 50
Output: "Neither"
Explanation: 
None of the dimensions of the box is greater or equal to 104.Its volume = 8 * 106 <= 109. So it cannot be categorized as "Bulky".
Its mass is also less than 100, so it cannot be categorized as "Heavy" either. 
Since its neither of the two above categories, we return "Neither".
 
Constraints:
- 1 <= length, width, height <= 105
- 1 <= mass <= 103
--------------------------------------------------------------------------------
Attempt 1: 2026-07-19
Solution 1: Math (10 min)
Refer to Deepseek
这道题 (LeetCode 2525) 和前面那些复杂的 DP、滑动窗口、二叉树遍历完全不在一个量级。这是一道纯模拟 (Pure Simulation) 题，甚至可以说是 LeetCode 上最简单的题之一（难度 Easy）。
这道题唯一的“坑点”在于整数溢出 (Integer Overflow)。因为 length * width * height 可能超过 int 的最大值（例如 10^9 * 10^9 * 10^9），所以计算体积时必须用 long 类型。
--------------------------------------------------------------------------------
核心逻辑（直接翻译题目）
1.判断是否“Bulky (笨重)”：
- 任意一个维度 >= 10^4，或者
- 体积 >= 10^9。
2.判断是否“Heavy (沉重)”：
- 质量 >= 100。
3.根据组合返回对应的字符串：
- Bulky + Heavy → "Both"
- Bulky + 不是 Heavy → "Bulky"
- 不是 Bulky + Heavy → "Heavy"
- 都不是 → "Neither"
--------------------------------------------------------------------------------
Java 代码实现
class Solution {
    public String categorizeBox(int length, int width, int height, int mass) {
        // 1. 计算体积（必须用 long，防止溢出）
        long volume = (long) length * width * height;
        
        // 2. 判断两个核心条件
        boolean isBulky = (length >= 10000) || 
                          (width >= 10000) || 
                          (height >= 10000) || 
                          (volume >= 1_000_000_000L);
                          
        boolean isHeavy = (mass >= 100);
        
        // 3. 返回对应分类
        if (isBulky && isHeavy) {
            return "Both";
        } else if (isBulky) {
            return "Bulky";
        } else if (isHeavy) {
            return "Heavy";
        } else {
            return "Neither";
        }
    }
}
--------------------------------------------------------------------------------
为什么必须用 long？
如果不加 (long) 强转，length * width * height 会在 int 范围内计算，结果溢出变成负数或奇怪的值，导致判断错误。
- 正确写法：(long) length * width * height（只要第一个因子是 long，整个乘法自动升级为 long 运算）。
--------------------------------------------------------------------------------
复杂度分析
- 时间复杂度：O(1)。只做了一些简单的比较。
- 空间复杂度：O(1)。没有使用额外空间。
--------------------------------------------------------------------------------
思维角度对比（给刷题同学的一点总结）
这道题几乎没有什么“算法思想”，它就是一道**纯粹的“条件分支 (If-Else)”**题。
在 LeetCode 的题海中，我们经常会遇到三种类型的题目：
1.复杂算法型（如 312 戳气球、1425 子序列 DP）：需要推导状态转移方程。
2.观察优化型（如 1438 固定差、2779 最大美丽度）：需要发现排序或单调队列的特性。
3.纯模拟/规则型（如本题 2525，以及 1275 井字棋）：逻辑极其简单，但非常考验你对“边界条件”和“数据类型溢出”的敏感度。
结论：做这类题时，写完逻辑后要第一时间检查 int 是否会溢出，这是唯一的失分点。代码本身 5 分钟就能写完，但如果不小心，可能因为溢出而 WA (Wrong Answer) 一次。


Refer to
L1275.Find Winner on a Tic Tac Toe Game (Ref.L2525)
