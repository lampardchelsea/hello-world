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


// A more easy understanding version with use of ArrayList, which also used by PathSum.java
// http://www.programcreek.com/2014/05/leetcode-binary-tree-paths-java/
// The major difference between previous solution and this one is 3rd parameter on dfs method
// change from "String path" into "List<Integer>", and related change on final result recording
// as List<List<Integer>> to record each path.
public List<String> binaryTreePaths(TreeNode root) {
    List<String> finalResult = new ArrayList<String>();
 
    if(root == null)
        return finalResult;
 
    List<List<String>> results = new ArrayList<List<String>>();
    List<String> curr = new ArrayList<String>();

    dfs(root, results, curr);
 
    for(ArrayList<String> al : results){
        StringBuilder sb = new StringBuilder();
        sb.append(al.get(0));
        for(int i = 1; i < al.size(); i++){
            sb.append("->" + al.get(i));
        }
 
        finalResult.add(sb.toString());
    }
 
    return finalResult;
}
 
public void dfs(TreeNode root, List<List<String>> list, List<String> curr){
    curr.add(String.valueOf(root.val));
 
    if(root.left == null && root.right == null){
        list.add(curr);
        return;
    }
  
    // How to pass current recursion content which stored in "curr" ArrayList
    // into next recursion, the most effect way is using contructor provide
    // by ArrayList(Collection<? extends E> c), same effect as "String path"
    // modification(since String is immutable, but '+' work on String as StringBuilder)
    // http://stackoverflow.com/a/4370101/6706875
    // https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#ArrayList(java.util.Collection)
    if(root.left != null){
        List<String> temp = new ArrayList<String>(curr);
        dfs(root.left, list, temp);
    }
 
    if(root.right!=null){
        List<String> temp = new ArrayList<String>(curr);
        dfs(root.right, list, temp);
    } 
}

