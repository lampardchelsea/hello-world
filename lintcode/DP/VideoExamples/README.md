# Lintcode DP issues
<p>1. [Lintcode DP issues]
<p>
DFS与DP的主要区别：
DFS(Divide and Conquer)不存在重复计算，比如二叉树不存在重复计算的可能性
DP存在重复计算，比如和二叉树很像的Triangle在构建的时候能到达一个节点的路径不止一条，存在重复计算

计算最长 DP DFS
计算最短 DP BFS

DFS 可以用来计算所有最优方案
DP 只能记录一种最优方案

动态规划一般有方向性，比如只能朝下或者朝右走，如果没有规定方向，比如上下左右都能走，
那一定有第三个条件来限制方向性，比如钱每走一步都在减少
比如没有方向性的题Knight Shortest Path，8个方向都能走，只能用BFS解决
而Knight Shortest Path II，只能走4个方向，可以用BFS或DP解决

什么情况下使用动态规划？
• 满足下面三个条件之一：
• 求最大值最小值
• 判断是否可行
• 统计方案个数
• 则 极有可能 是使用动态规划求解


什么情况下不使用动态规划？
求出所有 具体 的方案而非方案 个数 -> 只能使用DFS
• http://www.lintcode.com/problem/palindrome-partitioning/
• 输入数据是一个 集合 而不是 序列 -> 集合是无序的，序列是有序的
• http://www.lintcode.com/problem/longest-consecutive-sequence/
• 暴力算法的复杂度已经是多项式级别
• 动态规划擅长与优化指数级别复杂度(2^n,n!)到多项式级别复杂度(n^2,n^3)
• 不擅长优化n^3到n^2
• 则 极不可能 使用动态规划求解

minimum path sum
1.想清楚问题需要几维解决
2.自顶向下的方法中初始状态是啥
3.初始化不能由function得到的部分（通常第0行，0列）
4.能够用function循环得到的部分

接龙型动态规划
Longest Increasing Subsequence
Russian doll envelopes
Frog jump
起点，终点不好找


动规四要素vs 递归三要素
• 状态State
• 灵感，创㐀力，存储小规模问题的结果
• 方程Function
• 状态之间的联系，怎么通过小的状态，来算大的状态
• 初始化Initialization
• 最极限的小状态是什么, 起点
• 答案Answer
• 最大的那个状态是什么，终点


# Useful links for specific questions
<p>Triangle
<p>1. [Template of 3 ways(bottom up/ top down/ traverse + memorization)] (http://www.jiuzhang.com/solutions/triangle/)
<p>2. [DP Solution for Triangle] (https://discuss.leetcode.com/topic/1669/dp-solution-for-triangle/2?page=1)

<p>Minimum Path Sum
<p>1. [Template] (http://www.jiuzhang.com/solutions/minimum-path-sum/)
<p>2. [10-lines 28ms O(n)-space DP solution in C++ with Explanations] (https://discuss.leetcode.com/topic/15269/10-lines-28ms-o-n-space-dp-solution-in-c-with-explanations)




