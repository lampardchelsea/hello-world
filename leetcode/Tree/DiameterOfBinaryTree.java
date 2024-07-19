/**
 Refer to
 https://leetcode.com/problems/diameter-of-binary-tree/
 Given a binary tree, you need to compute the length of the diameter of the tree. 
 The diameter of a binary tree is the length of the longest path between any two 
 nodes in a tree. This path may or may not pass through the root.

Example:
Given a binary tree 
          1
         / \
        2   3
       / \     
      4   5    
Return 3, which is the length of the path [4,2,1,3] or [5,2,1,3].

Note: The length of path between two nodes is represented by the number of edges between them.
*/
// Solution 1:
// Refer to (1st link a bit wrong with additional '+1' in calculating diameter = 1 + leftDepth + rightDepth,
// the 2nd link correct this by diameter = 1 + leftDepth + rightDepth)
// the 3rd link for get maximum depth of current root on binary tree
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101120/Java-easy-to-understand-solution
// https://www.programcreek.com/2014/05/leetcode-maximum-depth-of-binary-tree-java/
/**
  For every node, length of longest path which pass it = MaxDepth of its left subtree + MaxDepth of its right subtree,
  and we just need to scan every node by recursive method
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
class Solution {
    int maxDiameter = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        helper(root);
        return maxDiameter;
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int leftDepth = helper(root.left);
        int rightDepth = helper(root.right);
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}

// Re-work
// Solution 1:O(N^2) solution
// Refer to
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101120/Java-easy-to-understand-solution
/**
This solution is intuitive but the performance is not good because of the overlapping subproblems when calculate depth.
diameterOfBinaryTree is called on every node. In each call, it traverses all descendants of that node to get the depth.
for root node, it is n => n + 1 - 2^0
for all nodes on 2nd level, it is 2 * (n - 1) / 2 => n - 1 => n + 1 - 2^1
for all nodes on 3rd level, it is 4 * (n - 3) / 4 => n - 3 => n + 1 - 2^2
for all nodes on 4th level, it is 8 * (n - 7) / 8 => n - 7 => n + 1 - 2^3
...
for all nodes on last level, it is n + 1 - 2^(h-1). h is max tree depth.
Add them up, the result is (n+1) * h - (1 + 2 + 4 ... + 2^(h-1)). In worst case, the latter part is n (all nodes 
in the tree), so time complexity is O(n*logn).
*/
class Solution {
    public int diameterOfBinaryTree(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int dia = depth(root.left) + depth(root.right);
        int ldia = diameterOfBinaryTree(root.left);
        int rdia = diameterOfBinaryTree(root.right);
        return Math.max(dia, Math.max(ldia, rdia));
    }
    
    private int depth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return 1 + Math.max(depth(root.left), depth(root.right));
    }
}

// Solution 2: O(N) DFS solution
// Refer to
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth/193803
// https://leetcode.com/problems/diameter-of-binary-tree/discuss/101132/Java-Solution-MaxDepth/309240
/**
 So although the longest path doesn't have to go through the root node, it has to pass the root node of 
 some subtree of the tree (because it has to be from one leaf node to another leaf node, otherwise we 
 can extend it for free). The longest path that passes a given node as the ROOT node is T = left_height+right_height. 
 So you just calculate T for all nodes and output the max T.
*/
class Solution {
    // Global veriable to track maximum diameter in each round
    int maxDiameter;
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        if(root == null) {
            return 0;
        }
        helper(root);
        return maxDiameter;
    }
    
    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        // The length of path between two nodes is represented by 
        // the number of edges between them, so no need 'left + right + 1'
        maxDiameter = Math.max(maxDiameter, left + right);
        return Math.max(left, right) + 1;
    }
}










































































https://leetcode.com/problems/diameter-of-binary-tree/
Given the root of a binary tree, return the length of the diameter of the tree.
The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
The length of a path between two nodes is represented by the number of edges between them.

Example 1:


Input: root = [1,2,3,4,5]
Output: 3
Explanation: 3 is the length of the path [4,2,1,3] or [5,2,1,3].

Example 2:
Input: root = [1,2]
Output: 1

Constraints:
- The number of nodes in the tree is in the range [1, 10^4].
- -100 <= Node.val <= 100
--------------------------------------------------------------------------------
Attempt 1: 2023-01-12
Solution 1:  Divide and Conquer (10 min)
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
    int maxDiameter = 0; 
    public int diameterOfBinaryTree(TreeNode root) { 
        if(root == null) { 
            return 0; 
        } 
        helper(root); 
        return maxDiameter; 
    }

    private int helper(TreeNode root) { 
        if(root == null) { 
            return 0; 
        } 
        int left = helper(root.left); 
        int right = helper(root.right); 
        // The length of path between two nodes is represented by  
        // the number of edges between them, so no need 'left + right + 1' 
        maxDiameter = Math.max(maxDiameter, left + right); 
        return Math.max(left, right) + 1; 
    } 
}

Time Complexity : O(N)    
Space Complexity: O(N)

Refer to
https://leetcode.com/problems/diameter-of-binary-tree/solutions/101132/java-solution-maxdepth/
For every node, length of longest path which pass it = MaxDepth of its left subtree + MaxDepth of its right subtree.
public class Solution {
    int max = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return max;
    }
    
    private int maxDepth(TreeNode root) {
        if (root == null) return 0;
        
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        
        max = Math.max(max, left + right);
        
        return Math.max(left, right) + 1;
    }
}

Refer to
https://algo.monster/liteproblems/543
Problem Description
The task is to find the diameter of a binary tree. The diameter is the longest path between any two nodes in the tree, which does not necessarily have to involve the root node. Here, the length of the path is given by the number of edges between the nodes. Therefore, if a path goes through the sequence of nodes A -> B -> C, the length of this path is 2 since there are two edges involved (A to B and B to C).
Intuition
To solve this problem, we use a depth-first search (DFS) approach. The intuition behind using DFS is to explore as far as possible down each branch before backtracking, which helps in calculating the depth (or height) of each subtree rooted at node.
For every node, we compute the depth of its left and right subtrees, which are the lengths of the longest paths from this node to a leaf node down the left and right subtrees, respectively. The diameter through this node is the sum of these depths, as it represents a path from the deepest leaf in the left subtree, up to the current node, and then down to the deepest leaf in the right subtree.
The key is to recognize that the diameter at each node is the sum of the left and right subtree depths, and we are aiming to find the maximum diameter over all nodes in the tree. Hence, during the DFS, we keep updating a global or external variable with the maximum diameter found so far.
One crucial aspect of the solution is that for every DFS call, we return the depth of the current subtree to the previous caller (parent node) in order to compute the diameter at the parent level. The current node depth is calculated as 1 + max(left, right), accounting for the current node itself plus the deeper path among its left or right subtree.
This approach allows us to travel only once through the tree nodes, maintaining a time complexity of O(N), where N is the number of nodes in the tree.
Solution Approach
To implement the diameter calculation for a binary tree, we use DFS to traverse the tree. Here's a step-by-step breakdown of the algorithm used in the provided code:
- Define the dfs Function: This recursive function takes a node of the tree as an argument. Its purpose is to compute the depth of the subtree rooted at that node and update the ans variable with the diameter passing through that node.
- Base Case: If the current node is None, which means we've reached beyond a leaf node, we return a depth of 0.
- Recursive Search: We calculate the depth of the left subtree (left) and the depth of the right subtree (right) by making recursive calls to dfs(root.left) and dfs(root.right).
- Update Diameter: We update the ans variable (which is declared with the nonlocal keyword to refer to the ans variable in the outer scope) with the maximum of its current value and the sum of left and right. This represents the largest diameter found at the current node because it is the sum of the path through the left child plus the path through the right child.
- Returning Depth: Each call to dfs returns the depth of the subtree it searched. The depth is the maximum between the depths of its left and right subtrees, increased by 1 to account for the current node.
- Start DFS: We call dfs starting at the root node to initiate the depth-first search of the tree.
- Return the Result: After traversing the whole tree, ans holds the length of the longest path, which is the diameter of the binary tree.
The overall complexity of the solution is O(N), where N is the number of nodes in the binary tree since each node is visited exactly once.
This solution uses a DFS pattern to explore the depth of each node’s subtrees and a global or "helper" scope variable to track the cumulative maximum diameter found during the entire traversal. The use of recursion and tracking a global maximum is a common strategy for tree-based problems where computations in child nodes need to influence a result variable at a higher scope.
The use of a nonlocal keyword in Python is essential here as it allows the nested dfs helper function to modify the ans variable defined in the outer diameterOfBinaryTree function's scope.
Example Walkthrough
Let's walk through an example to illustrate the solution approach using the depth-first search (DFS) algorithm to find the diameter of a binary tree.
Consider a binary tree:
     1
    / \
   2   3
  / \
 4   5
In this example, the longest path (diameter) is between node 4 and node 3, which goes through node 2 and node 1. Here's how we can apply the solution approach to this tree:
- We start DFS with the root node. Let's call dfs(1):
- It's not None, so we continue with the recursion.
- We encounter node 1 and make recursive calls to its left and right children:
- dfs(2) is called for the left subtree.
- dfs(3) is called for the right subtree.
- Inside dfs(2):
- We call dfs(4) for the left child and return 1 (since 4 is a leaf node).
- We call dfs(5) for the right child and return 1 (since 5 is a leaf node).
- We update ans to be max(ans, left + right) which at this point is max(0, 1 + 1) = 2.
- We return 1 + max(left, right) to indicate the depth from node 2 to the deepest leaf which equals 1 + 1 = 2.
- Inside dfs(3):
- Since node 3 is a leaf node, we return 1.
- Back to the dfs(1), with the returned values:
- We received 2 from the left subtree (dfs(2)).
- We received 1 from the right subtree (dfs(3)).
- We update ans with the sum of the left and right which is max(2, 2 + 1) = 3. This is the diameter passing through the root.
- DFS is called throughout the entire tree, and the maximum value of ans is updated accordingly.
- Since we've traversed the whole tree, the final ans is 3, which is the length of the longest path (diameter) in our binary tree, passing from node 4 to 3 via nodes 2 and 1.
The key steps in the example above show the recursive nature of the solution, updating a global maximum diameter as the recursion unfolds, and the use of depth calculations to facilitate this process. The time complexity is O(N) as we visit each node exactly once in the process.
Solution Implementation
/**
 * Definition for a binary tree node.
 * This class represents a node in a binary tree, with a value and pointers to its left and right child nodes.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
  
    TreeNode() {}

    TreeNode(int val) { this.val = val; }
  
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

/**
 * This class contains methods to solve for the diameter of a binary tree.
 */
class Solution {
    private int maxDiameter; // Holds the maximum diameter found

    /**
     * Finds the diameter of a binary tree, which is the length of the longest path between any two nodes in a tree.
     * This path may or may not pass through the root.
     * 
     * @param root the root node of the binary tree
     * @return the diameter of the binary tree
     */
    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        depthFirstSearch(root);
        return maxDiameter;
    }

    /**
     * A recursive method that calculates the depth of the tree and updates the maximum diameter.
     * The path length between the nodes is calculated as the sum of the heights of left and right subtrees.
     *
     * @param node the current node
     * @return the maximum height from the current node
     */
    private int depthFirstSearch(TreeNode node) {
        if (node == null) {
            // Base case: if the current node is null, return a height of 0
            return 0;
        }
        // Recursively find the height of the left and right subtrees
        int leftHeight = depthFirstSearch(node.left);
        int rightHeight = depthFirstSearch(node.right);
      
        // Update the maximum diameter if the sum of heights of the current node's subtrees is greater
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight);
      
        // Return the max height seen up to the current node, including the current node's height (which is 1)
        return 1 + Math.max(leftHeight, rightHeight);
    }
}
Time and Space Complexity
Time Complexity
The time complexity of the diameterOfBinaryTree method is O(n), where n is the number of nodes in the binary tree. This is because the auxiliary function dfs is called exactly once for each node in the tree. In the dfs function, the work done at each node is constant time, primarily consisting of calculating the maximum of the left and right heights and updating the ans variable if necessary.
Space Complexity
The space complexity of the diameterOfBinaryTree method is O(h), where h is the height of the binary tree. This accounts for the maximum number of recursive calls that stack up on the call stack at any point during the execution of the dfs function. In the worst case, where the tree is skewed (forms a straight line), the height of the tree can be n, leading to a space complexity of O(n). However, in a balanced tree, the height h would be O(log n), leading to a more efficient space utilization.

Refer to
L124.P9.7.Binary Tree Maximum Path Sum (Ref.L543)
