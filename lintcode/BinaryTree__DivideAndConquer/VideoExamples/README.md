# Lintcode BinaryTree_DivideAndConquer issues
<p>1. [Lintcode binary tree & divide and conquer issues]
<p>重要理论
<p>1.分治法(Divide And Conquer)调用已定义方法本身，不同于遍历法(Traversal)的依赖新定义的helper方法
<p>2.分治法必须返回值（result in return value），递归法返回null(result in parameter)
<p>3.多线程分治法好，不像遍历法在方法参数中传入shared result等类似的全局变量
<p>4.分治法必须要有合并(merge)作为最后一步，但是方法名中没有体现出来
<p>5.DFS同时包括了递归和非递归实现两种类型，分治法和遍历法本身都是递归的一种，分治法更好，没有使用全局变量
<p>6.很好的比喻是遍历法都是亲力亲为自带记事本(e.g shared result)，分治法都是把事情交给下面的人去做
<p>7.题目的含义是找出所有的方案的时候马上考虑DFS，e.g Binary Tree Paths
<p>8.二叉树高度不一定是logn，非平衡二叉树高度在logn到n之间

# Useful links for specific questions
