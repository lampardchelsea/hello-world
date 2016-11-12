/**
 * http://www.programcreek.com/2014/05/leetcode-sum-root-to-leaf-numbers-java/
 * http://web.mit.edu/6.005/www/fa15/classes/10-recursion/
 * 
 * When to Use Recursion Rather Than Iteration
 * We’ve seen two common reasons for using recursion:
 * The problem is naturally recursive (e.g. Fibonacci)
 * The data is naturally recursive (e.g. filesystem)
 * Another reason to use recursion is to take more advantage of immutability. In an ideal recursive implementation, 
 * all variables are final, all data is immutable, and the recursive methods are all pure functions in the sense that 
 * they do not mutate anything. The behavior of a method can be understood simply as a relationship between its parameters 
 * and its return value, with no side effects on any other part of the program. This kind of paradigm is called functional 
 * programming, and it is far easier to reason about than imperative programming with loops and variables.
 * 
 * In iterative implementations, by contrast, you inevitably have non-final variables or mutable objects that are modified 
 * during the course of the iteration. Reasoning about the program then requires thinking about snapshots of the program 
 * state at various points in time, rather than thinking about pure input/output behavior.
 * 
 * One downside of recursion is that it may take more space than an iterative solution. Building up a stack of recursive 
 * calls consumes memory temporarily, and the stack is limited in size, which may become a limit on the size of the problem 
 * that your recursive implementation can solve.
 * 
 * Common Mistakes in Recursive Implementations
 * Here are two common ways that a recursive implementation can go wrong:
 * 
 * The base case is missing entirely, or the problem needs more than one base case but not all the base cases are covered.
 * The recursive step doesn’t reduce to a smaller subproblem, so the recursion doesn’t converge.
 * Look for these when you’re debugging.
 * On the bright side, what would be an infinite loop in an iterative implementation usually becomes a StackOverflowError 
 * in a recursive implementation. A buggy recursive program fails faster.
*/
public class Solution {
    public TreeNode root;

    private static class TreeNode {
       private String val;
       private TreeNode left, right;
       public TreeNode(String x) {
	  this.val = x;
       }
     }
    
    public static int sumOfLeftLeaves(TreeNode root) {
       if(root == null) {
         return 0;
       } else {
         return sumOfLeftLeavesRec(root, false);
       }
    }
    
    public static int sumOfLeftLeavesRec(TreeNode x, boolean isLeft) {
        if(x == null) {
          return 0;
        }
      
        int sum = 0;
        if(x.left == null && x.right == null && isLeft) {
          sum += x.val;
        } else {
          sum += sumOfLeftLeavesRec(x.left, true);
          sum += sumOfLeftLeavesRec(x.right, false);
        }
      
        return sum;
    }
  
    public static void main(String[] args) {
	/*
	 * The tree used for test
	 *	       1
	 *		 /   \
	 *		2     3
	 *	   / \   /
	 *	  6   5 9
	 *	 /
	 *	8
	*/
	Solution s = new Solution();
	s.root = new TreeNode("1");
	s.root.left = new TreeNode("2");
	s.root.right = new TreeNode("3");
	s.root.left.left = new TreeNode("6");
	s.root.left.right = new TreeNode("5");
	s.root.left.left.left = new TreeNode("8");
	s.root.right.left = new TreeNode("9");
	
	int result = sumOfLeftLeaves(s.root);
	System.out.println(result);
    }
}
