/**
 * Given a binary tree and a sum, determine if the tree has a root-to-leaf path such 
 * that adding up all the values along the path equals the given sum.
 * For example:
 * Given the below binary tree and sum = 22,
              5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1
 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
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

// Solution 1: Use same way as ArrayList to record each path value and pass into next recursion
// Refer to http://www.programcreek.com/2014/05/leetcode-binary-tree-paths-java/
// http://stackoverflow.com/a/4370101/6706875
public class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        List<Integer> result = new ArrayList<Integer>();
        
        if(root == null) {
            return false;
        }
        
        dfs(root, results, result);
        
        for(int i = 0; i < results.size(); i++) {
            int pathValue = 0;
            List<Integer> tmp = results.get(i);
            for(int j = 0; j < tmp.size(); j++) {
                pathValue += tmp.get(j);
            }
            
            if(pathValue == sum) {
                return true;
            }
        }
        
        return false;
    }
    
    // A more easy understanding version with use of ArrayList, which also used by PathSum.java
    // http://www.programcreek.com/2014/05/leetcode-binary-tree-paths-java/
    // The major difference between previous solution and this one is 3rd parameter on dfs method
    // change from "String path" into "List<Integer>", and related change on final result recording
    // as List<List<Integer>> to record each path.
    public void dfs(TreeNode root, List<List<Integer>> results, List<Integer> result) {
        result.add(root.val);
        
        if(root.left == null && root.right == null) {
            results.add(result);
            return;
        }
        
        // How to pass current recursion content which stored in "curr" ArrayList
        // into next recursion, the most effect way is using contructor provide
        // by ArrayList(Collection<? extends E> c), same effect as "String path"
        // modification(since String is immutable, but '+' work on String as StringBuilder)
        if(root.left != null) {
            List<Integer> newTemp = new ArrayList<Integer>(result);
            dfs(root.left, results, newTemp);
        }
        
        if(root.right != null) {
            List<Integer> newTemp = new ArrayList<Integer>(result);
            dfs(root.right, results, newTemp);
        }
    }
}

// Best Recursive Way
// http://www.cnblogs.com/springfor/p/3879825.html
public class Solution {
    public boolean hasPathSum(TreeNode root, int sum) {
        if(root == null) {
            return false;
        }
        
        sum -= root.val;
        
        if(root.left == null && root.right == null) {
            return sum == 0;
        } else {
            return hasPathSum(root.left, sum) || hasPathSum(root.right, sum);
        }
    }
}

// Use template on Divide and Conquer
/**
 * Refer to
 * https://leetcode.com/problems/path-sum/description/
 *  Given a binary tree and a sum, determine if the tree has a root-to-leaf path 
 *  such that adding up all the values along the path equals the given sum.
 *  For example:
 *  Given the below binary tree and sum = 22,

              5
             / \
            4   8
           /   / \
          11  13  4
         /  \      \
        7    2      1

 * return true, as there exist a root-to-leaf path 5->4->11->2 which sum is 22.
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/3149/accepted-my-recursive-solution-in-java/16
 * A:if(root.left == null && root.right == null && sum - root.val == 0) return true;
 * B:if(root.left == null && root.right == null) return sum == root.val;
 * the two expression are not equivalence.
 * when root is a leaf node,
 * in A solution, if sum - root.val == 0 got false, the code will enter next recursive.
 * but in B, whether sum equals to root.val or not, it will return a result, the code 
 * exit, never enter next recursive
 *
 */
public class PathSum {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
    public boolean hasPathSum(TreeNode root, int sum) {
        // Base case
        if(root == null) {
            return false;
        }
        // Leaf case
        if(root.left == null && root.right == null) {
            return sum == root.val;
        }
        // Divide (Since we can apply same format, no need for
        // creating new helper method)
        boolean left = hasPathSum(root.left, sum - root.val);
        boolean right = hasPathSum(root.right, sum - root.val);
        // Conquer
        return left || right;
    }
	
	
}


// Re-work
// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/path-sum/discuss/36378/AcceptedMy-recursive-solution-in-Java/34584



// Re-work
// Solution 2: Iterative
// Refer to
// https://leetcode.com/problems/path-sum/discuss/36534/My-java-no-recursive-method/34652













