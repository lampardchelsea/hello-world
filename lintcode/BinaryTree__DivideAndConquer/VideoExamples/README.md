# Lintcode BinaryTree_DivideAndConquer issues
<p>1. [Lintcode binary tree & divide and conquer issues]
<p>重要理论
1.分治法调用已定义方法本身，不是递归法的依赖新的helper方法
2.分治法必须返回值（result in return value），递归法返回null(result in parameter)
3.多线程分治法好，不share result
4.分治法必须要有合并作为最后一步
5.DFS同时包括了递归和非递归实现两种类型，分治法和遍历法本身都是递归的一种，分治法更好，没有使用全局变量
6.很好的比喻是遍历法都是亲力亲为自带记事本，分治法都是把事情交给下面的人去做
7.题目的含义是找出所有的方案的时候马上考虑DFS，e.g Binary Tree Paths
8.二叉树高度不一定是logn，非平衡二叉树高度在logn到n之间

# Useful links for specific questions
