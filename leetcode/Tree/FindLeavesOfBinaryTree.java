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

// Solution 2:
// Refer to
// https://www.cnblogs.com/grandyang/p/5616158.html
// http://buttercola.blogspot.com/2018/09/leetcode-366-find-leaves-of-binary-tree.html
/**
 这道题给了我们一个二叉树，让我们返回其每层的叶节点，就像剥洋葱一样，将这个二叉树一层一层剥掉，
 最后一个剥掉根节点。那么题目中提示说要用DFS来做，思路是这样的，每一个节点从左子节点和右子节点
 分开走可以得到两个深度，由于成为叶节点的条件是左右子节点都为空，所以我们取左右子节点中较大值
 加1为当前节点的深度值，知道了深度值就可以将节点值加入到结果res中的正确位置了，求深度的方法我们
 可以参见 Maximum Depth of Binary Tree 中求最大深度的方法，参见代码如下
*/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
	}
        findLeavesHelper(root, result);
        return result;
    }
     
    private int findLeavesHelper(TreeNode root, List<List<Integer>> result) {
        if (root == null) {
            return -1;
        }
        int left = findLeavesHelper(root.left, result);
        int right = findLeavesHelper(root.right, result);
        int depth = Math.max(left, right) + 1;
        if (depth == result.size()) {
            List<Integer> list = new ArrayList<>();
            list.add(root.val);
            result.add(list);
        } else {
            List<Integer> list = result.get(depth);
            list.add(root.val);
	}         
        return depth;
    }
}
