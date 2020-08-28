/**
Refer to
https://leetcode.com/problems/sum-root-to-leaf-numbers/
Given a binary tree containing digits from 0-9 only, each root-to-leaf path could represent a number.
An example is the root-to-leaf path 1->2->3 which represents the number 123.
Find the total sum of all root-to-leaf numbers.
Note: A leaf is a node with no children.

Example:
Input: [1,2,3]
    1
   / \
  2   3
Output: 25
Explanation:
The root-to-leaf path 1->2 represents the number 12.
The root-to-leaf path 1->3 represents the number 13.
Therefore, sum = 12 + 13 = 25.

Example 2:
Input: [4,9,0,5,1]
    4
   / \
  9   0
 / \
5   1
Output: 1026
Explanation:
The root-to-leaf path 4->9->5 represents the number 495.
The root-to-leaf path 4->9->1 represents the number 491.
The root-to-leaf path 4->0 represents the number 40.
Therefore, sum = 495 + 491 + 40 = 1026.
*/

// Solution 1: Recursive Solution (Pre-order traversal)
// Refer to
// https://leetcode.com/problems/sum-root-to-leaf-numbers/discuss/41363/Short-Java-solution.-Recursion./39619
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
    public int sumNumbers(TreeNode root) {
        return helper(root, 0);
    }
    
    private int helper(TreeNode node, int currSum) {
        if(node == null) {
            return 0;
        }
        currSum = 10 * currSum + node.val;
        if(node.left == null && node.right == null) {
            return currSum;
        }
        int leftSum = helper(node.left, currSum);
        int rightSum = helper(node.right, currSum);
        return leftSum + rightSum;
    }
}

// Solution 2: DFS iterative
// Refer to
// https://leetcode.com/problems/sum-root-to-leaf-numbers/discuss/41474/Java-iterative-and-recursive-solutions.
// Style 1: Avoid null point exception by check left, right child node not null
class Node {
    TreeNode node;
    int sum;
    public Node(TreeNode node, int sum) {
        this.node = node;
        this.sum = sum;
    }
}

class Solution {
    public int sumNumbers(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int result = 0;
        Stack<Node> stack = new Stack<Node>();
        Node node = new Node(root, 0);
        stack.push(node);
        while(!stack.isEmpty()) {
            Node curNode = stack.pop();
            TreeNode curTreeNode = curNode.node;
            int curSum = curNode.sum;
            curSum = curSum * 10 + curTreeNode.val;
            if(curTreeNode.left == null && curTreeNode.right == null) {
                result += curSum;
            }
            // Avoid null point exception by check left, right child node not null,
            // then no node contains null treenode will push onto stack
            if(curTreeNode.left != null) {
                stack.push(new Node(curTreeNode.left, curSum));
            }
            if(curTreeNode.right != null) {
                stack.push(new Node(curTreeNode.right, curSum));
            }
        }
        return result;
    }
}

// Style 2: Avoid null point exception by check current treenode not null in next iteration
class Node {
    TreeNode node;
    int sum;
    public Node(TreeNode node, int sum) {
        this.node = node;
        this.sum = sum;
    }
}

class Solution {
    public int sumNumbers(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int result = 0;
        Stack<Node> stack = new Stack<Node>();
        Node node = new Node(root, 0);
        stack.push(node);
        while(!stack.isEmpty()) {
            Node curNode = stack.pop();
            TreeNode curTreeNode = curNode.node;
            int curSum = curNode.sum;
            // Avoid null point exception by check current treenode not null in next iteration
            // which may introduce by new Node(curTreeNode.left, curSum) or new Node(curTreeNode.right, curSum)
            if(curTreeNode != null) {
                curSum = curSum * 10 + curTreeNode.val;
                if(curTreeNode.left == null && curTreeNode.right == null) {
                    result += curSum;
                }
                stack.push(new Node(curTreeNode.left, curSum));
                stack.push(new Node(curTreeNode.right, curSum));
            }
        }
        return result;
    }
}
