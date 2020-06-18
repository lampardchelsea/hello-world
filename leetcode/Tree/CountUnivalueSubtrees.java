/**
 Refer to
 https://www.cnblogs.com/grandyang/p/5206862.html
 Given a binary tree, count the number of uni-value subtrees.
 A Uni-value subtree means all nodes of the subtree have the same value.
 Example :
 Input:  root = [5,1,5,5,5,null,5]

              5
             / \
            1   5
           / \   \
          5   5   5

 Output: 4
*/

// Solution 1: Two Pass DFS Traverse
// Refer to
// https://www.cnblogs.com/grandyang/p/5206862.html
/**
 这道题让我们求相同值子树的个数，就是所有节点值都相同的子树的个数，之前有道求最大 BST 子树的题 
 Largest BST Subtree，感觉挺像的，都是关于子树的问题，解题思路也可以参考一下，这里可以用递归来做，
 第一种解法的思路是先序遍历树的所有的节点，然后对每一个节点调用判断以当前节点为根的字数的所有节点
 是否相同，判断方法可以参考之前那题 Same Tree，用的是分治法的思想，分别对左右字数分别调用递归
*/




