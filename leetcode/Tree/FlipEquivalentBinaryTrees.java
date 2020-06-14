/**
 Refer to
 https://leetcode.com/problems/flip-equivalent-binary-trees/
 For a binary tree T, we can define a flip operation as follows: choose any node, and 
 swap the left and right child subtrees.

A binary tree X is flip equivalent to a binary tree Y if and only if we can make X equal 
to Y after some number of flip operations.

Write a function that determines whether two binary trees are flip equivalent.  The trees 
are given by root nodes root1 and root2.

Example 1:
Input: root1 = [1,2,3,4,5,6,null,null,null,7,8], root2 = [1,3,2,null,6,4,5,null,null,null,null,8,7]
Output: true
Explanation: We flipped at nodes with values 1, 3, and 5.
                1                          1
          2          3                3         2    
       4    5      6                    6     4    5
          7   8                                  8   7
Note:
Each tree will have at most 100 nodes.
Each value in each tree will be a unique integer in the range [0, 99]
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
// Solution 1: Similar way as same tree check, just modify condition when root1.val == root2.val
// Refer to
// https://leetcode.com/problems/same-tree/
// https://leetcode.com/problems/flip-equivalent-binary-trees/discuss/200514/Java-3-liner-with-explanation-time-and-space%3A-O(n).
/**
 Update:
For some time, I forgot the following constraint and changed the comlexity from O(n) to O(n ^ 2):
Each value in each tree will be a unique integer in the range [0, 99]

The follows are correct only without the above condition.
Complexity analysis corrected from O(n) to O(n ^ 2), credit to @coder_coder.
Analysis:

In worst case, the recursion corresponds to a perfect quaternary (means 4-nary) tree, which 
has 4 ^ d = N ^ 2 nodes, and we have to traverse all nodes. d = logN is the depth of the binary tree.

One worst case for input:
two perfect binary trees: root1 & root2.

Root1's nodes are all 0s;
Root2's nodes are all 0s, with the exception that left and right bottoms are both 1s.
Time & Space: O(n ^ 2).
*/
class Solution {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if(root1 == null && root2 == null) {
            return true;
        } else if(root1 == null || root2 == null) {
            return false;
        } else {
            if(root1.val == root2.val) {
                return flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) 
                || flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left);
            } else {
                return false;
            }
        }
    }
}

// Re-work
// Solution 1: Recursive DFS
// Refer to
// https://leetcode.com/problems/flip-equivalent-binary-trees/solution/
/**
Approach 1: Recursion
Intuition
If root1 and root2 have the same root value, then we only need to check if their children are equal (up to ordering.)
Algorithm
There are 3 cases:
1.If root1 or root2 is null, then they are equivalent if and only if they are both null.
2.Else, if root1 and root2 have different values, they aren't equivalent.
3.Else, let's check whether the children of root1 are equivalent to the children of root2. 
There are two different ways to pair these children. (left VS. left + right VS. right / left VS. right + right VS. left)
*/

// https://leetcode.com/problems/flip-equivalent-binary-trees/discuss/200514/JavaPython-3-DFS-3-liners-and-BFS-with-explanation-time-and-space%3A-O(n).
/**
 If at least one of the two root nodes is null, are they equal? if yes, true; if no, false;
 otherwise, neither node is null; if the values of the two nodes are:
 2a) NOT equal, return false;
 2b) equal, compare their children recursively. --> and we have two modes for compare child, left VS. left + right VS. right / left VS. right + right VS. left
*/
class Solution {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        if(root1 == null || root2 == null) {
            return root1 == root2;
        }
        return root1.val == root2.val && (flipEquiv(root1.left, root2.left) && flipEquiv(root1.right, root2.right) || flipEquiv(root1.left, root2.right) && flipEquiv(root1.right, root2.left));
    }
}

// Solution 2: Iterative (BFS)
// Refer to
// https://leetcode.com/problems/flip-equivalent-binary-trees/discuss/200514/JavaPython-3-DFS-3-liners-and-BFS-with-explanation-time-and-space%3A-O(n).
class Solution {
    public boolean flipEquiv(TreeNode root1, TreeNode root2) {
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();
        q1.offer(root1);
        q2.offer(root2);
        while(!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode n1 = q1.poll();
            TreeNode n2 = q2.poll();
            if(n1 == null || n2 == null) {
                if(n1 == n2) {
                    continue;
                } else {
                    return false;
                }
            }
            if(n1.val != n2.val) {
                return false;
            }
            // Two equal models based on switch left / right
            if((n1.left == null && n2.left == null) || (n1.left != null && n2.left != null && n1.left.val == n2.left.val)) {
                q1.offer(n1.left);
                q1.offer(n1.right);
            } else {
                q1.offer(n1.right);
                q1.offer(n1.left);
            }
            q2.offer(n2.left);
            q2.offer(n2.right);
        }
        return q1.isEmpty() && q2.isEmpty();
    }
}


