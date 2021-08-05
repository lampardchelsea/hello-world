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
<p>Minimum Path Sum
<p>1. [Template] (http://www.jiuzhang.com/solutions/minimum-path-sum/)
<p>2. [10-lines 28ms O(n)-space DP solution in C++ with Explanations] (https://discuss.leetcode.com/topic/15269/10-lines-28ms-o-n-space-dp-solution-in-c-with-explanations)

<p>Unique Paths
<p>1. [Template] (https://www.jiuzhang.com/solution/unique-paths/)
<p>2. [0ms, 5-lines DP Solution in C++ with Explanations] (https://discuss.leetcode.com/topic/15265/0ms-5-lines-dp-solution-in-c-with-explanations)

<p>Unique Paths II
<p>1. [Template] (https://www.jiuzhang.com/solution/unique-paths-ii/)

<p>Climbing Stairs
<p>1. [Template] (https://leetcode.com/articles/climbing-stairs/)

<p>Jump Game
<p>1. [Approach #3 (Dynamic Programming Bottom-up) [Time limit exceeded]] (https://leetcode.com/articles/jump-game/)
<p>2. [Template (DP)] (https://www.jiuzhang.com/solution/jump-game/)

<p>Jump Game II
<p>1. [Template (Same style as Jump Game)] (http://blog.unieagle.net/2012/09/29/leetcode%E9%A2%98%E7%9B%AE%EF%BC%9Ajump-game-ii%EF%BC%8C%E4%B8%80%E7%BB%B4%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92/
)
<p>2. [Template (Different style)] (https://www.jiuzhang.com/solution/jump-game-ii/)
<p>3. [中文解释] (https://segmentfault.com/a/1190000003488956)

<p>Perfect Square
<p>1. [An easy understanding DP solution in Java] (https://discuss.leetcode.com/topic/26400/an-easy-understanding-dp-solution-in-java)

<p>Largest Divisible Subset
<p>1. [Template] (https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/LargestDivisableSubset.java)

<p>House Robber
<p>1. [Template] (https://www.jiuzhang.com/solution/house-robber/)
<p>2. [中文解释] (http://www.cnblogs.com/grandyang/p/4383632.html)

<p>House Robber II
<p>1. [中文解释] (http://blog.csdn.net/xudli/article/details/45886721)
<p>2. [中文解释2] (http://www.cnblogs.com/grandyang/p/4518674.html)
<p>3. [Template] (https://www.jiuzhang.com/solution/house-robber-ii/)
<p>4. [Explaination] (https://discuss.leetcode.com/topic/14375/simple-ac-solution-in-java-in-o-n-with-explanation)

<p>Coins In A Line
<p>1. [中文解释] (https://aaronice.gitbooks.io/lintcode/content/dynamic_programming/coins_in_a_line.html)
<p>2. [Template] (https://www.jiuzhang.com/solution/coins-in-a-line/)
<p>3. [递推公式dp[n] = dp[n - 3]] (https://github.com/awangdev/LintCode/blob/master/Java/Coins%20in%20a%20Line.java)






