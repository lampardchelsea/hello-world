# Lintcode BinaryTree_DivideAndConquer issues
<p>1. [Lintcode binary tree & divide and conquer issues]
<p>重要理论
<p>1.分治法(Divide And Conquer)调用已定义方法本身，不同于遍历法(Traversal)的依赖新定义的helper方法
<p>2.分治法必须返回值（result in return value），递归法返回null(result in parameter)
<p>3.多线程分治法好，不像遍历法在方法参数中传入shared result等类似的全局变量
<p>4.分治法必须要有合并(merge)作为最后一步，但是方法名中没有体现出来
<p>5.DFS同时包括了递归和非递归实现两种类型，分治法和遍历法本身都是递归的一种，分治法更好，没有使用全局变量
<p>6.很好的比喻是遍历法都是亲力亲为自带记事本(e.g shared result, usually global variable)，分治法都是把事情交给下面的人去做
<p>7.题目的含义是找出所有的方案的时候马上考虑DFS，e.g Binary Tree Paths
<p>8.二叉树高度不一定是logn，非平衡二叉树高度在logn到n之间
<p>9.二叉树问题一般都是用通过O(n)的时间，把n的问题，变为了两个n/2的问题，时间复杂度也为O(n)

# Useful links for specific questions
<p>Binary Tree Preorder Traversal
<p>1. [Template] (http://www.jiuzhang.com/solutions/binary-tree-preorder-traversal/)

<p>Maximum Depth Of Binary Tree
<p>1. [Template] (http://www.jiuzhang.com/solutions/maximum-depth-of-binary-tree/)

<p>Binary Tree Paths
<p>1. [Template] (http://www.jiuzhang.com/solutions/binary-tree-paths/)
<p>2. [Explaination on details] (https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/BinaryTreePaths.java) 

<p>Minimum Subtree
<p>1. [Template] (http://www.jiuzhang.com/solutions/minimum-subtree/)
<p>2. [[刷题笔记]LintCode 596 - Minimum Subtree] (http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-3)

<p>Balanced Binary Tree
<p>1. [Template] (http://www.jiuzhang.com/solution/balanced-binary-tree/)

<p>Subtree With Maximum Average
<p>1. [Template] (https://www.jiuzhang.com/solutions/subtree-with-maximum-average/)
<p>2. [[刷题笔记] LintCode 597 - Subtree with Maximum Average] (http://blog.leanote.com/post/westcode/53751fcb4502)

<p>Flatten Binary Tree to Linked List
<p>1. [Template] (https://www.jiuzhang.com/solutions/flatten-binary-tree-to-linked-list/)
<p>2. [Leetcode concise 2 ways] (https://discuss.leetcode.com/topic/11444/my-short-post-order-traversal-java-solution-for-share?page=1)

<p>Invert Binary Tree
<p>1. [Template] (http://www.jiuzhang.com/solutions/invert-binary-tree/?source=zhmhw)

<p>Lowest Common Ancestor
<P>1. [Template] (http://www.jiuzhang.com/solutions/lowest-common-ancestor/)

<p>Binary Tree Longest Consecutive Sequence
<p>1. [[刷题笔记] LeetCode 298 /LintCode 595 - Binary Tree Longest Consecutive Sequence] (http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-5)
<p>2. [Template] (http://www.jiuzhang.com/solutions/binary-tree-longest-consecutive-sequence/)
<p>3. [Easy Java DFS, is there better time complexity solution?] (https://discuss.leetcode.com/topic/28234/easy-java-dfs-is-there-better-time-complexity-solution/2)

<p>Binary Tree Maximum Path Sum
<p>1. [Purely Divide and Conquer] (http://www.jiuzhang.com/solutions/binary-tree-maximum-path-sum/)
<p>2. [Traverse + Divide and Conquer] (https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8)

<p>Validate Binary Search Tree
<p>1. [Template - Purely Traverse] (http://www.jiuzhang.com/solution/validate-binary-search-tree/)
<p>2. [Purely Divide and Conquer] (https://discuss.leetcode.com/topic/7179/my-simple-java-solution-in-3-lines)
<p>3. [Iterative with Stack by InOrder traverse] (https://discuss.leetcode.com/topic/46016/learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-java-solution)

<p>Convert Binary Search Tree to Doubly Linked List
<p>1. [[LintCode] 378 Convert Binary Search Tree to Doubly Linked List 解题报告] (http://demo.aosustudio.com/96F7C9DA60ED8D595C0BEACE369D5364.AHtml)

<p>Lowest Common Ancestor II
<p>1. [Iterative only use half logical as Lowest Common Ancestor Of Binary Tree] (https://xuyiruan.com/2019/02/06/Lowest-Common-Ancestor-Series/)

