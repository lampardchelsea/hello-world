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

// Re-work
// Solution 1: DFS + backtracking
// Refer to
// https://leetcode.com/problems/path-sum-ii/discuss/36683/DFS-with-one-LinkedList-accepted-java-solution
// Also answering why we need deep copy
// Refer to
// https://leetcode.com/problems/path-sum-ii/discuss/36698/Another-accepted-Java-solution/34853
// https://leetcode.com/problems/path-sum-ii/discuss/36698/Another-accepted-Java-solution/34852
/**
 Re: Another accepted Java solution
@jeantimex Thank you for your solution !
I am a fresh man to Java language, and I have a question.
In your void pathSum(TreeNode root, ...) function, I change the block

if (root.left == null && root.right == null && sum == root.val ) {
    res.add(new ArrayList<Integer>(sol));
}
with

if (root.left == null && root.right == null && sum == root.val ) {
    res.add(sol);
}
but It didn't work. Would you mind helping to figure it out? Thank you : )

when the recursion goes up, the sol size will always decrease by1. As a result, 
the final size of sol will be 0. So if you write res.add(sol), it will be null 
list finally. The function of new ArrayList(sol) is to create a new list which 
has same value as sol, rather than res.add(sol) offering the reference to sol.

Also refer to
https://stackoverflow.com/questions/45929473/difference-on-list-add-and-list-addnew-arraylist/45929525#45929525
https://stackoverflow.com/questions/19843506/why-does-my-arraylist-contain-n-copies-of-the-last-item-added-to-the-list
*/
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
        if (node == null) {
            return;
        }
        List < Integer > temp = new ArrayList < Integer > (list);
        temp.add(node.val);
        if (node.left == null && node.right == null && node.val == sum) {
            result.add(temp);
            return;
        }
        helper(node.left, sum - node.val, result, temp);
        helper(node.right, sum - node.val, result, temp);
        temp.remove(temp.size() - 1);
    }
}

// Re-work
// Solution 2: Iterative
// Important criteria: Iterative Inorder Traverse Template
// Refer to
// Preorder, Inorder and Postorder Traversal Iterative Java Solution
// https://leetcode.com/problems/binary-tree-postorder-traversal/discuss/45621/preorder-inorder-and-postorder-traversal-iterative-java-solution
/**
 public List<Integer> inorderTraversal(TreeNode root) {
    List<Integer> list = new ArrayList<>();
    if(root == null) return list;
    Stack<TreeNode> stack = new Stack<>();
    while(root != null || !stack.empty()){
        while(root != null){
            stack.push(root);
            root = root.left;
        }
        root = stack.pop();
        list.add(root.val);
        root = root.right;
    }
    return list;
}
*/

// https://www.cnblogs.com/grandyang/p/4042156.html
/**
 下面这种方法是迭代的写法，用的是中序遍历的顺序，参考之前那道 Binary Tree Inorder Traversal，中序遍历本来是要用
 栈来辅助运算的，由于要取出路径上的结点值，所以用一个 vector 来代替 stack，首先利用 while 循环找到最左子结点，
 在找的过程中，把路径中的结点值都加起来，这时候取出 vector 中的尾元素，如果其左右子结点都不存在且当前累加值正好
 等于 sum 了，将这条路径取出来存入结果 res 中，下面的部分是和一般的迭代中序写法有所不同的地方，由于中序遍历的特点，
 遍历到当前结点的时候，是有两种情况的，有可能此时是从左子结点跳回来的，此时正要去右子结点，则当前的结点值还是算在
 路径中的；也有可能当前是从右子结点跳回来的，并且此时要跳回上一个结点去，此时就要减去当前结点值，因为其已经不属于
 路径中的结点了。为了区分这两种情况，这里使用一个额外指针 pre 来指向前一个结点，如果右子结点存在且不等于 pre，
 直接将指针移到右子结点，反之更新 pre 为 cur，cur 重置为空，val 减去当前结点，st 删掉最后一个结点
*/

// https://leetcode.com/problems/path-sum-ii/discuss/36695/Java-Solution:-iterative-and-recursive/34840
class Solution {
    // In-order traveral
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> list = new ArrayList<List<Integer>>();
        if(root == null) {
            return list;
        }
        List<Integer> path = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        // Sum alone the current path
        int pathSum = 0;
        TreeNode curr = root;
        // Use 'prev' to judge whether the right subtree has been visited
        TreeNode prev = null;
        while(curr != null || !stack.isEmpty()) {
            // Go down all the way to the left leaf node
            // add all the left nodes to the stack 
            while(curr != null) {
                stack.push(curr);
                // Record the current path
                path.add(curr.val);
                // Record the current sum along the current path
                pathSum += curr.val;
                curr = curr.left;
            }
            // Check left leaf node's right subtree 
            // or check if it is not from the right subtree
            // why peek here? 
            // because if it has right subtree, we don't need to push it back
            curr = stack.peek();
            if(curr.right != null && curr.right != prev) {
                curr = curr.right;
                continue; // back to the outer while loop
            }
            // Check leaf
            if(curr.left == null && curr.right == null && pathSum == sum) {
                list.add(new ArrayList<Integer>(path));
                // Why do we need new arraylist here?
                // if we are using the same path variable path
                // path will be cleared after the traversal
            }
            // Pop out the current value
            stack.pop();
            prev = curr;
            // Subtract current node's val from path sum
            pathSum -= curr.val;
            // As this current node is done, remove it from the current path
            path.remove(path.size() - 1);
            // Reset current node to null, so check the next item from the stack
            curr = null;
        }
        return list;
    }
}





