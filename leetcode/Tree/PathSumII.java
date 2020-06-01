import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * https://leetcode.com/problems/path-sum-ii/description/
 *  Given a binary tree and a sum, find all root-to-leaf paths where each path's 
 *  sum equals the given sum.
	For example:
	Given the below binary tree and sum = 22,
	
	              5
	             / \
	            4   8
	           /   / \
	          11  13  4
	         /  \    / \
	        7    2  5   1
	
	return
	[
	   [5,4,11,2],
	   [5,8,4,5]
	]
 * 
 * Solution
 * https://discuss.leetcode.com/topic/5414/dfs-with-one-linkedlist-accepted-java-solution
 * This question is best practice for Traverse way, as a template contains many critical
 * elements which need to be careful when using Traverse
 * There are 10 points here need to care about
 */
public class PathSumII {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
    // 1.Traverse
    // 2.When we given problem as find all solutions must be DFS
    // 3.Global variable work as Traverse way for helper method
    // 4.Choose LinkedList because of frequent add or remove item on list
	List<List<Integer>> result = new LinkedList<List<Integer>>();
	List<Integer> currResult = new LinkedList<Integer>();
	public List<List<Integer>> pathSum(TreeNode root, int sum) {
		if(root == null) {
			return result;
		}
		helper(result, currResult, root, sum);
		return result;
	}
	
	// 5.As basic idea of Traverse way, helper method return void
	public void helper(List<List<Integer>> result, List<Integer> currResult, TreeNode root, int sum) {
		// 6.Base case
		if(root == null) {
			return;
		}
		currResult.add(root.val);
		// 7.Leaf case
		if(root.left == null && root.right == null && sum == root.val) {
			// Add current combination onto final result
			// 8. Deep copy
			result.add(new LinkedList<Integer>(currResult));
			// 9. Don't forget to remove the last item on currResult for next recursion
			currResult.remove(currResult.size() - 1);
			return;
		}
		// 10.Divide
		helper(result, currResult, root.left, sum - root.val);
		helper(result, currResult, root.right, sum - root.val);
		// Don't forget to remove the last item on currResult for next recursion
        // no matter what whether we add to the final result or not
		currResult.remove(currResult.size() - 1);
	}
}

// Re-work
// 有一个写法歪打正着，并没有使用backtrack，但是答案效果是对的，不过最好不要使用，因为思路本身有问题，如果没有基于backtrack
// 却要通过DFS寻找全部解答，基本就是有问题的
public class Solution {
    public static void main(String[] args) {
        /**
         * Test with below binary tree
         * 
         *           3
         *       /       \
                5         1
              /   \     /   \
             6     2   0     8
                 /   \
                7     4
         */
        Solution q = new Solution();
        TreeNode root = q.new TreeNode(3);
        root.left = q.new TreeNode(5);
        root.right = q.new TreeNode(1);
        root.left.left = q.new TreeNode(6);
        root.left.right = q.new TreeNode(2);
        root.left.right.left = q.new TreeNode(7);
        root.left.right.right = q.new TreeNode(4);
        root.right.left = q.new TreeNode(0);
        root.right.right = q.new TreeNode(8);
        List < List < Integer >> result = q.pathSum(root, 14);
        System.out.println(result.toString());
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public List < List < Integer >> pathSum(TreeNode root, int sum) {
        List < List < Integer >> result = new ArrayList < List < Integer >> ();
        helper(root, sum, result, new ArrayList < Integer > ());
        return result;
    }

    private void helper(TreeNode node, int sum, List < List < Integer >> result, List < Integer > list) {
        List < Integer > temp = new ArrayList < Integer > (list);
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null && sum == node.val) {
            result.add(temp);
        }
        temp.add(node.val);
        helper(node.left, sum - node.val, result, temp);
        helper(node.right, sum - node.val, result, temp);
    }
}



