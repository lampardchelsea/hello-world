/**
 Refer to
 https://www.cnblogs.com/grandyang/p/5616158.html
 Given a binary tree, collect a tree's nodes as if you were doing this: 
 Collect and remove all leaves, repeat until the tree is empty.

Example:
Input: [1,2,3,4,5]
  
          1
         / \
        2   3
       / \     
      4   5    

Output: [[4,5,3],[2],[1]]

Explanation:
1. Removing the leaves [4,5,3] would result in this tree:
          1
         / 
        2          

2. Now removing the leaf [2] would result in this tree:
          1          

3. Now removing the leaf [1] would result in the empty tree:
          []         
Credits:
Special thanks to @elmirap for adding this problem and creating all test cases.
*/
// Solution 1: Remove each leave node (reset as null) and add to list at same time
// Refer to
// https://www.cnblogs.com/grandyang/p/5616158.html
/**
 下面这种DFS方法没有用计算深度的方法，而是使用了一层层剥离的方法，思路是遍历二叉树，
 找到叶节点，将其赋值为NULL，然后加入leaves数组中，这样一层层剥洋葱般的就可以得到最终结果了
 // Note: Below is template for remove leaves in a Binary Tree
 // BJP4 Exercise 17.12: removeLeaves
 // Write a method removeLeaves that removes the leaves from a tree. 
 // A leaf node that has empty left and right subtrees.  If your method 
 // is called on an empty tree, the method does not change the tree 
 // because there are no nodes of any kind (leaf or not).
 public void removeLeaves() {
  overallRoot = removeLeavesHelper(overallRoot);
 }

 public SearchTreeNode<E> removeLeavesHelper(SearchTreeNode<E> root) {
  // If current node is leave node, remove it
  if(root == null || (root.left == null && root.right == null)) {
   return null;
  } else {
   root.left = removeLeavesHelper(root.left);
   root.right = removeLeavesHelper(root.right);
   return root;
  }
 }
*/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
// https://leetcode.com/discuss/110406/simple-java-recursive-1ms-solution
// This is pretty straight forward but the general idea is to simply prune the 
// leaves at each iteration of the while loop until the root itself is pruned. 
// We can do this using the x = change(x) paradigm for modifying a tree. 
// Whenever we come across a leaf node, we know we must add it to our result 
// but then we prune it by just returning null. 
// Clean solution. In worst cases, it can be O(N^2) right?
class Solution {
    public List<List<Integer>> findLeaves(TreeNode root) {
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    if(root == null) {
        return result;
    }
    while(root != null) {
        List<Integer> list = new ArrayList<Integer>();
        root = removeLeavesHelper(list, root);
        result.add(list);
    }
    return result;
	}
	
	private TreeNode removeLeavesHelper(List<Integer> list, TreeNode root) {
    if(root == null) {
        return null;
    } else if(root.left == null && root.right == null) {
        list.add(root.val);
        return null;
    }
    root.left = removeLeavesHelper(list, root.left);
    root.right = removeLeavesHelper(list, root.right);
    return root;
	}
}
