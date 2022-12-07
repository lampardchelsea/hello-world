/**
 * Refer to
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
 *  Given a binary tree, find the maximum path sum.
 *  For this problem, a path is defined as any sequence of nodes from some starting node to any node 
 *  in the tree along the parent-child connections. The path must contain at least one node and does 
 *  not need to go through the root.

	For example:
	Given the below binary tree,
	
	       1
	      / \
	     2   3
	
	Return 6. 
 * 
 * 
 * Solution 1: Purely Divide and Conquer
 * http://www.jiuzhang.com/solutions/binary-tree-maximum-path-sum/
 * 
 * Solution 2: Traverse + Divide and Conquer
 * https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8
 */
public class BinaryTreeMaximumPathSum {
	private class TreeNode {
		public int val;
		public TreeNode left, right;
		public TreeNode(int val) {
		    this.val = val;
		    this.left = this.right = null;
		}
    }
	
	
	// Solution 1: Purely Divide and Conquer
	// Basic idea very like 'Binary Tree Longest Consecutive Sequence'
	// Set up ResultType for Divide and Conquer
    private class ResultType {
        int singlePath;
        int maxPath;
        public ResultType(int singlePath, int maxPath) {
            this.singlePath = singlePath;
            this.maxPath = maxPath;
        }
    }

    public int maxPathSum(TreeNode root) {
        return helper(root).maxPath;
    }
    
    private ResultType helper(TreeNode root) {
        // Base case
        if(root == null) {
            return new ResultType(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        // No leaf case, divide directly
        ResultType left = helper(root.left);
        ResultType right = helper(root.right);
        // Conquer
        // Find value of singlePath
        // Choose 0 means not include current singlePath value into next recursion as both
        // left and right result < 0, and make it + root.val smaller than root.val itself,
        // also, not include means calculate current singlePath from current root and get
        // rid of previous ones
        int singlePath = Math.max(0, Math.max(left.singlePath, right.singlePath)) + root.val;
        // Update maxPath
        int maxPath = Math.max(left.maxPath, right.maxPath);
        // Use 0 the same way as singlePath calculating
        // Refer to
        // https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8
        // The most tricky point is the global variable maxValue in the following sentence:
        // maxValue = Math.max(maxValue, left + right + node.val);
        // The second maxValue contains the bigger between the left sub-tree and right sub-tree.
        // if (left + right + node.val < maxValue ) then the result will not include the parent 
        // node which means the maximum path is in the left branch or right branch.
        maxPath = Math.max(maxPath, Math.max(0, left.singlePath) + Math.max(0, right.singlePath) + root.val);
        return new ResultType(singlePath, maxPath);
    }
    
    
    // Solution 2: Traverse + Divide and Conquer
    /**
     * Refer to
     * https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java
     * A path from start to end, goes up on the tree for 0 or more steps, then goes down for 0 or more steps. 
     * Once it goes down, it can't go up. Each path has a highest node, which is also the lowest common 
     * ancestor of all other nodes on the path.
     * A recursive method helper(TreeNode node) (1) computes the maximum path sum with highest node is 
     * the input node, update maximum if necessary (2) returns the maximum sum of the path that can be extended 
     * to input node's parent.
     * 
     * Refer to
     * https://discuss.leetcode.com/topic/4407/accepted-short-solution-in-java/8
     * The most tricky point is the global variable maxValue in the following sentence:
     * maxValue = Math.max(maxValue, left + right + node.val);
     * The second maxValue contains the bigger between the left sub-tree and right sub-tree.
     * if (left + right + node.val < maxValue ) then the result will not include the parent node which means 
     * the maximum path is in the left branch or right branch.
     */
    // Set up global variable for Traverse
    // Don't initialize as 0 because the maxValue may < 0
    int maxValue = Integer.MIN_VALUE;
    public int maxPathSum2(TreeNode root) {
        helper2(root);
        return maxValue;
    }

    // As Divide and Conquer way we return int
    public int helper2(TreeNode root) {
        // Base case
        if(root == null) {
            return 0;
        }
        // Divide
        int left = Math.max(0, helper2(root.left));
        int right = Math.max(0, helper2(root.right));
        // Conquer
        // Update maxValue (But not used for return)
        // Same meaning as update 'maxPath'
        maxValue = Math.max(maxValue, left + right + root.val);
        // Returns the maximum sum of the path that can be extended to input node's parent.
        // Same meaning as update 'singlePath'
        return Math.max(left, right) + root.val;
    }   
}


// Re-work
// Solution 1: Recursive in O(N^2) time
// Refer to
// https://afteracademy.com/blog/maximum-path-sum-in-a-binary-tree
/**
 Brute force and Efficient solutions
 We will be discussing two possible solutions for this problem:
 Brute force approach: Traverse left and right subtree of each node and calculate maximum possible sum path.
 Recursive approach: We calculate the maximum path sum rooted at each node and update the max sum during the traversal.
 
 Brute Force:
 We can update the max path sum passing through each node T in the tree by traversing the T's left subtree and right subtree.
 
 Solution steps:
 1.Assume we have nodes numbered 1 to N
 2.sum(i) = Maximum sum of a path containing node(i). Clearly the solution of the problem is max(sum(1), sum(2), ...., sum(N))
 3.Now, what is the maximum sum of a path containing a particular node(i)?
 4.left_result: maximum path sum starting at node(i).left
 5.right_result: maximum path sum starting at node(i).right
 6.sum(i) = max(left_result, 0) + max(right_result, 0) + node(i).val
*/
class Solution {
    int maxValue;
    int left_result;
    int right_result;
    public int maxPathSum(TreeNode root) {
        maxValue = Integer.MIN_VALUE;
        max_path_sum_helper(root);
        return maxValue;
    }

    private void max_path_sum_helper(TreeNode node) {
        if (node == null) {
            return;
        }
        left_result = Integer.MIN_VALUE;
        right_result = Integer.MIN_VALUE;
        // Find maximum path sum starting from node.left
        helper_left(node.left, 0);
        // Find maximum path sum starting from node.right
        helper_right(node.right, 0);
        left_result = Math.max(left_result, 0);
        right_result = Math.max(right_result, 0);
        maxValue = Math.max(left_result + right_result + node.val, maxValue);
        max_path_sum_helper(node.left);
        max_path_sum_helper(node.right);
    }

    private void helper_left(TreeNode node, int sum_so_far) {
        if (node == null) {
            return;
        }
        left_result = Math.max(left_result, sum_so_far + node.val);
        helper_left(node.left, sum_so_far + node.val);
        helper_left(node.right, sum_so_far + node.val);
    }

    private void helper_right(TreeNode node, int sum_so_far) {
        if (node == null) {
            return;
        }
        right_result = Math.max(right_result, sum_so_far + node.val);
        helper_right(node.left, sum_so_far + node.val);
        helper_right(node.right, sum_so_far + node.val);
    }
}


// Re-work
// Solution 2: Recursive in O(N) time
// Refer to
// https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39775/Accepted-short-solution-in-Java
/**
 A path from start to end, goes up on the tree for 0 or more steps, then goes down for 0 or more steps. 
 Once it goes down, it can't go up. Each path has a highest node, which is also the lowest common ancestor 
 of all other nodes on the path.
 A recursive method maxPathDown(TreeNode node) 
 (1) computes the maximum path sum with highest node is the input node, update maximum if necessary 
 (2) returns the maximum sum of the path that can be extended to input node's parent.
*/

// https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39775/Accepted-short-solution-in-Java/37681
/**
 Just want to add a explanation about the last two lines based on my comprehension.
     maxValue = Math.max(maxValue, left + right + node.val);
     return Math.max(left, right) + node.val;
 maxValue is the value which recording whether this current root is the final root, so we use left + right + node.val. 
 But to the upper layer(after return statement), we cannot choose both left and right brunches, so we need to select 
 the larger one, so we use max(left, right) + node.val to prune the lower brunch.
*/

// https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39775/Accepted-short-solution-in-Java/192437
/**
 Each node actually has two roles when it comes to function maxPathDown. When processing the final result maxValue, 
 the node is treated as the highest point of a path. When calculating its return value, it is only part of a path 
 (left or right part), and this return value will be used to calculate path sum of other paths with some other 
 nodes(above the current one) as their highest point.
*/

// https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/39775/Accepted-short-solution-in-Java/190770
/**
 Idea & Cases Explanation. Java + recursion
 At first, I think 2 parameters should be return in recursion. Then I find that we only need to update max once, 
 so I move max to be a member parameter.

Basic idea:
store/update max during post-order traversal.
return maximum branches
a) 0
b) root.val
c) root.val + dfs(root.left)
d) root.val + dfs(root.right)
Whole situation can be broken down to four cases:

1.root
left<0 right<0
max = Math.max(0, root.val + 0 + 0)
return Math.max(0, root.val)

2.root
left>0 right<0
max = Math.max(0, root.val + dfs(root.left) + 0)
return Math.max(0, root.val + dfs(root.left))

3.root
left<0 right>0
max = Math.max(0, root.val + 0 + dfs(root.right) + 0)
return Math.max(0, root.val + dfs(root.right))

4.root
left>0 right>0
max = Math.max(0, root.val + 0 + dfs(root.left) + dfs(root.right))
return Math.max(0, root.val + dfs(root.left) + dfs(root.right))

    int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        if(root == null) return 0;
        backtrack(root);
        return max;
    }
    private int backtrack(TreeNode root){
        if(root == null) return 0;
        int leftSum = Math.max(0, backtrack(root.left)); // left branch less than 0, then not take left branch
        int rightSum = Math.max(0, backtrack(root.right)); // right branch less than 0, then not take right branch 
        max = Math.max(max, leftSum + rightSum + root.val); // root, left + root, right + root, left + right + root;
        return Math.max(0, Math.max(root.val + leftSum, root.val + rightSum)); // take left+root or right+root or root or 0
    }
*/
class Solution {
    int maxValue;
    public int maxPathSum(TreeNode root) {
        maxValue = Integer.MIN_VALUE;
        helper(root);
        return maxValue;
    }
    // Post-order traverse
    private int helper(TreeNode node) {
        if(node == null) {
            return 0;
        }
        int left = Math.max(0, helper(node.left)); // left branch less than 0, then not take left branch
        int right = Math.max(0, helper(node.right)); // right branch less than 0, then not take right branch 
        maxValue = Math.max(maxValue, node.val + left + right); // root, left + root, right + root, left + right + root;
        return Math.max(left, right) + node.val; // take left+root or right+root or root or 0
    }
}

































https://leetcode.com/problems/binary-tree-maximum-path-sum/

A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass through the root.

The path sum of a path is the sum of the node's values in the path.

Given the root of a binary tree, return the maximum path sum of any non-empty path.

Example 1:


```
Input: root = [1,2,3]
Output: 6
Explanation: The optimal path is 2 -> 1 -> 3 with a path sum of 2 + 1 + 3 = 6.
```

Example 2:


```
Input: root = [-10,9,20,null,null,15,7]
Output: 42
Explanation: The optimal path is 15 -> 20 -> 7 with a path sum of 15 + 20 + 7 = 42.
```
 
Constraints:
- The number of nodes in the tree is in the range [1, 3 * 104].
- -1000 <= Node.val <= 1000
---
Attempt 1: 2022-12-06

Solution 1:  Recursive traversal (120 min)
```
/** 
 * Definition for a binary tree node. 
 * public class TreeNode { 
 *     int val; 
 *     TreeNode left; 
 *     TreeNode right; 
 *     TreeNode() {} 
 *     TreeNode(int val) { this.val = val; } 
 *     TreeNode(int val, TreeNode left, TreeNode right) { 
 *         this.val = val; 
 *         this.left = left; 
 *         this.right = right; 
 *     } 
 * } 
 */ 
class Solution { 
    int maxValue; 
    public int maxPathSum(TreeNode root) { 
        maxValue = Integer.MIN_VALUE; 
        helper(root); 
        return maxValue; 
    } 
    private int helper(TreeNode root) { 
        if(root == null) { 
            return 0; 
        } 
        int leftMax = Math.max(0, helper(root.left)); 
        int rightMax = Math.max(0, helper(root.right)); 
        maxValue = Math.max(maxValue, root.val + leftMax + rightMax); 
        return root.val + Math.max(leftMax, rightMax); 
    } 
}

Time Complexity : O(N)   
Space Complexity: O(N)
```

Refer to
https://leetcode.com/problems/binary-tree-maximum-path-sum/solutions/603423/python-recursion-stack-thinking-process-diagram/

This problem requires quite a bit of quirky thinking steps. Take it slow until you fully grasp it.

Basics



Base cases






Important Observations

- These important observations are very important to understand Line 9 and Line 10 in the code.
	- For example, in the code (Line 9), we do something like max(get_max_gain(node.left), 0). The important part is: why do we take maximum value between 0 and maximum gain we can get from left branch? Why 0?
	- Check the two images below first.
	  
	  
	  
- The important thing is "We can only get any sort of gain IF our branches are not below zero. If they are below zero, why do we even bother considering them? Just pick 0 in that case. Therefore, we do max(<some gain we might get or not>, 0).




Going down the recursion stack for one example





- Because of this, we do Line 12 and Line 13. It is important to understand the different between looking for the maximum path INVOLVING the current node in process and what we return for the node which starts the recursion stack. Line 12 and Line 13 takes care of the former issue and Line 15 (and the image below) takes care of the latter issue.


- Because of this fact, we have to return like Line 15. For our example, for node 1, which is the recursion call that node 3 does for max(get_max_gain(node.left), 0), node 1 cannot include both node 6 and node 7 for a path to include node 3. Therefore, we can only pick the max gain from left path or right path of node 1.

Python
```
1. class Solution: 
2.     def maxPathSum(self, root: TreeNode) -> int: 
3. 		max_path = float("-inf") # placeholder to be updated 
4. 		def get_max_gain(node): 
5. 			nonlocal max_path # This tells that max_path is not a local variable 
6. 			if node is None: 
7. 				return 0 
8. 				 
9. 			gain_on_left = max(get_max_gain(node.left), 0) # Read the part important observations 
10. 		gain_on_right = max(get_max_gain(node.right), 0)  # Read the part important observations 
11. 			 
12. 		current_max_path = node.val + gain_on_left + gain_on_right # Read first three images of going down the recursion stack 
13. 		max_path = max(max_path, current_max_path) # Read first three images of going down the recursion stack 
14. 			 
15. 		return node.val + max(gain_on_left, gain_on_right) # Read the last image of going down the recursion stack 
16. 			 
17. 			 
18. 	get_max_gain(root) # Starts the recursion chain 
19. 	return max_path
```

