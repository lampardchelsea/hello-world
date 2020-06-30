import java.util.ArrayList;
import java.util.List;

/**
 * Refer to
 * http://www.lintcode.com/en/problem/binary-tree-paths/
 * Given a binary tree, return all root-to-leaf paths.
 * Have you met this question in a real interview?
	Example
	Given the following binary tree:
	   1
	 /   \
	2     3
	 \
	  5
	
	All root-to-leaf paths are:
	[
	  "1->2->5",
	  "1->3"
	]
 *
 * Solution
 * http://www.jiuzhang.com/solutions/binary-tree-paths/
 */
public class BinaryTreePaths {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	// Traverse
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<String>();
        if(root == null) {
            return paths;
        }
        helper(root, String.valueOf(root.val), paths);
        return paths;
    }
    
    
    /**
     * Start with path = "1"
     * "1"
     * "1 -> 2" (root = 1, add root.left.val = 2)
     * "1 -> 2 -> 4" (root = 2, add root.left.val = 4)
     * "1 -> 2 -> 5" (root = 2, add root.right.val = 5)
     * "1 -> 3" (root = 1, add root.right.val = 3)
     */
    public void helper(TreeNode root, String path, List<String> paths) {
        // Base case
        if(root == null) {
            return;
        }
        if(root.left == null && root.right == null) {
            paths.add("" + path);
        }
        // Divide and merge
        // Note: The order here must be 'path' before 'root.left.val' or 'root.right.val'
        // which exactly reverse than the order in Divide and Conquer way, which is
        // 'path' after 'root.left.val' or 'root.right.val'
        if(root.left != null) {
            helper(root.left, path + "->" + root.left.val, paths);            
        }
        if(root.right != null) {
            helper(root.right, path + "->" + root.right.val, paths);   
        }
    }
    
    // Divide and Conquer
    public List<String> binaryTreePaths2(TreeNode root) {
    	List<String> result = new ArrayList<String>();
    	// Base case
    	if(root == null) {
    		return result;
    	}
    	// Leaf case
    	if(root.left == null && root.right == null) {
    		result.add("" + root.val);
    	}
    	// Divide (We can call this method itself without create
    	// a new helper method as we only need same type input
    	// as parameter)
    	List<String> leftPaths = binaryTreePaths2(root.left);
    	List<String> rightPaths = binaryTreePaths2(root.right);
    	// Merge
    	for(String path : leftPaths) {
    		// Note: Must put 'root.val' before 'path' as the
    		// Divide and Conquer way based on bottom-up order
    		// which will reach to bottom first as node '4' or
    		// node '5' first, then back to parent node as '2',
    		// then '1'. This process exactly reverse to the
    		// traverse way
    		/**
    		 * result value change process:
    		 * [4] (root is 4)
    		 * [5] (root is 5)
    		 * [2->4] (root is 2)
    		 * [2->5] (root is 2)
    		 * [1->2->4] (root is 1)
    		 * [1->2->5] (root is 1)
    		 * [3] (root is 3)
    		 * [1->3] (root is 1)
    		 */
    		result.add(root.val + "->" + path);
    	}
    	for(String path : rightPaths) {
    		result.add(root.val + "->" + path);
    	}
    	return result;
    }
    
    
    public static void main(String[] args) {
    	/**
    	 *       1
    	 *      / \
    	 *     2   3
    	 *    / \
    	 *   4   5
    	 */
    	BinaryTreePaths b = new BinaryTreePaths();
    	TreeNode one = b.new TreeNode(1);
    	TreeNode two = b.new TreeNode(2);
    	TreeNode three = b.new TreeNode(3);
    	TreeNode four = b.new TreeNode(4);
    	TreeNode five = b.new TreeNode(5);
    	one.left = two;
    	one.right = three;
    	two.left = four;
    	two.right = five;
    	List<String> result = b.binaryTreePaths2(one);
    	for(String s : result) {
    		System.out.println(s);
    	}
    }
    
}

// Re-work
// Solution 1: Recursive with String concatenation
// Refer to
// https://leetcode.com/problems/binary-tree-paths/discuss/68258/Accepted-Java-simple-solution-in-8-lines
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<String>();
        helper(root, result, "");
        return result;
    }
    
    private void helper(TreeNode root, List<String> result, String str) {
        if(root == null) {
            return;
        }
        if(root.left == null && root.right == null) {
            result.add(str + root.val);
        }
        if(root.left != null) {
            helper(root.left, result, str + root.val + "->");
        }
        if(root.right != null) {
            helper(root.right, result, str + root.val + "->");
        }
    }
}


// Solution 2: Recursive with StringBuilder
// Refer to
// https://leetcode.com/problems/binary-tree-paths/discuss/68258/Accepted-Java-simple-solution-in-8-lines/70164
/**
The time complexity for the problem should be O(n), since we are basically visiting each node in the tree. 
Yet an interviewer might ask you for further optimization when he or she saw a string concatenation. 
A string concatenation is just too costly. A StringBuilder can be used although a bit tricky since it 
is not immutable like string is.
When using StringBuilder, We can just keep track of the length of the StringBuilder before we append anything to 
it before recursion and afterwards set the length back. Another trick is when to append the "->", since we don't 
need the last arrow at the end of the string, we only append it before recurse to the next level of the tree.  
*/

// https://leetcode.com/problems/binary-tree-paths/discuss/68265/Java-solution-using-StringBuilder-instead-of-string-manipulation.
/**
I think this is by far the on the best solutions in Java.
1. If we use str1 + str2 it will bring a lot of unnecessary clones;
2. If we clone StringBuffer it's as bad as cloning string;
3. If we do concat root + "for(path : childrenPaths)" in every recursion call, it will not be time efficient;
4. If we do iteration, the queues are not slick enough in this question.
*/
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        helper(root, result, sb);
        return result;
    }
    
    private void helper(TreeNode root, List<String> result, StringBuilder sb) {
        if(root == null) {
            return;
        }
        // When using StringBuilder, We can just keep track of the length 
        // of the StringBuilder before we append anything to it before 
        // recursion and afterwards set the length back.
        int len = sb.length();
        sb.append(root.val);
        if(root.left == null && root.right == null) {
            result.add(sb.toString());
        } else {
            // Another trick is when to append the "->", since we don't 
            // need the last arrow at the end of the string, we only 
            // append it before recurse to the next level of the tree.
            sb.append("->");
            helper(root.left, result, sb);
            helper(root.right, result, sb);
        }
        sb.setLength(len);
    }
}


