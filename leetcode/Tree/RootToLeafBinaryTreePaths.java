/**
 * Given a binary tree, return all root-to-leaf paths.
 * For example, given the following binary tree:

   1
 /   \
2     3
 \
  5
  
 * All root-to-leaf paths are: 
 * ["1->2->5", "1->3"]
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
// Solution 1: Pass array list result into recursive method every time
public class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        // The version from https://segmentfault.com/a/1190000003465753
        // set result as class member variable, which do not need to
        // pass into recursive method each time
        List<String> result = new ArrayList<String>();
        if(root == null) {
            return result;
        } else {
            dfs(root, result, String.valueOf(root.val));
        }
         
        return result;
    }
    
    // Every time pass in mutable type List<String> object "result" to record
    // http://stackoverflow.com/questions/40499420/why-static-member-variable-not-work-for-retain-value-in-recursive-method
    public void dfs(TreeNode x, List<String> result, String path) {
        // As test, no matter in-order/pre-order/post-order, all accept
        if(x.left == null && x.right == null) {
            result.add(path);
            // Add return here can improve the execution time, when we hit the leave nodes
            // and after adding its value into result, will return directly, no need to 
            // do below condition check.
            //return;
        }
        
        if(x.left != null) {
            dfs(x.left, result, path + "->" + String.valueOf(x.left.val));
        }
        
        if(x.right != null) {
            dfs(x.right, result, path + "->" + String.valueOf(x.right.val));
        }
    }
}

// Solution 2: Set array list result as class memeber variable, no need to pass into recursive method
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    List<String> result = new ArrayList<String>();
    
    public List<String> binaryTreePaths(TreeNode root) {
        if(root == null) {
            return result;
        } else {
            dfs(root, String.valueOf(root.val));
        }
         
        return result;
    }
    
    public void dfs(TreeNode x, String path) {
	// As test, no matter in-order/pre-order/post-order, all accept
        if(x.left == null && x.right == null) {
            result.add(path);
            return;
        }
        
        if(x.left != null) {
            dfs(x.left, path + "->" + String.valueOf(x.left.val));
        }
        
        if(x.right != null) {
            dfs(x.right, path + "->" + String.valueOf(x.right.val));
        }
    }
}




// Version with unit test
import java.util.ArrayList;
import java.util.List;


public class Solution {
	public TreeNode root;
	
	private static class TreeNode {
		private int val;
		private TreeNode left, right;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	public static List<String> binaryTreePaths(TreeNode root) {
		List<String> result = new ArrayList<String>();
		if(root == null) {
			return result;
		} else {
			dfs(root, result, String.valueOf(root.val));
		}
		
		return result;
	}
	
	public static void dfs(TreeNode x, List<String> result, String path) {
		if(x.left == null && x.right == null) {
			result.add(path);
		}
		
		if(x.left != null) {
			dfs(x.left, result, path + "->" + String.valueOf(x.left.val));
		}
		
		if(x.right != null) {
			dfs(x.right, result, path + "->" + String.valueOf(x.right.val));
		}
	}
	
	public static void main(String[] args) {
		Solution s = new Solution();
		s.root = new TreeNode(1);
		s.root.left = new TreeNode(2);
		s.root.right = new TreeNode(3);
		s.root.left.right = new TreeNode(5);
		
		List<String> result = binaryTreePaths(s.root);
		System.out.println(result.toString());
	}
}
