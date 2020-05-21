/**
 Refer to
 https://leetcode.com/problems/convert-bst-to-greater-tree/
 Given a Binary Search Tree (BST), convert it to a Greater Tree such 
 that every key of the original BST is changed to the original key plus 
 sum of all keys greater than the original key in BST.

Example:
Input: The root of a Binary Search Tree like this:
              5
            /   \
           2     13
           
Output: The root of a Greater Tree like this:
             18
            /   \
          20     13
          
This is same problem as below:
Binary Search Tree to Greater Sum Tree
https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
Given the root of a binary search tree with distinct values, modify it so that every node has 
a new value equal to the sum of the values of the original tree that are greater than or equal to node.val.

As a reminder, a binary search tree is a tree that satisfies these constraints:

The left subtree of a node contains only nodes with keys less than the node's key.
The right subtree of a node contains only nodes with keys greater than the node's key.
Both the left and right subtrees must also be binary search trees.
*/

// Solution 1: Reverse in-order traversal
// Style 1: With global veriable
// Refer to
// https://leetcode.com/problems/convert-bst-to-greater-tree/solution/
// https://leetcode.com/problems/convert-bst-to-greater-tree/discuss/100506/Java-Recursive-O(n)-time/152561
// https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/discuss/286725/JavaC%2B%2BPython-Revered-Inorder-Traversal
/**
 We need to do the work from biggest to smallest, right to left.
 pre will record the previous value the we get, which the total sum of bigger values.
 For each node, we update root.val with root.val + pre.
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
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if(root == null) {
            return null;
        }
        convertBST(root.right);
        root.val += sum; 
        sum = root.val;
        convertBST(root.left);
        return root;
    }
}

// Style 2: With helper method and not use global variable
// Refer to
// https://leetcode.com/problems/convert-bst-to-greater-tree/discuss/100506/Java-Recursive-O(n)-time/104632
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
    public TreeNode convertBST(TreeNode root) {
        helper(root, 0);
        return root;
    }
    
    private int helper(TreeNode root, int sum) {
        if(root == null) {
            return sum;
        }
        int right = helper(root.right, sum);
        int left = helper(root.left, root.val + right);
        root.val += right;
        return left; // Tricky point, not return root.val
    }
}

// Style 3:
// Refer to
// https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/discuss/286725/JavaC%2B%2BPython-Revered-Inorder-Traversal
class Solution {
    int pre = 0;
    public TreeNode bstToGst(TreeNode root) {
        if (root.right != null) {
            bstToGst(root.right);
        }
        //pre = root.val = pre + root.val;
        root.val = pre + root.val;
        pre = root.val;
        if (root.left != null) {
            bstToGst(root.left);
        }
        return root;
    }
}

// Solution 2: Iterative with stack
// Refer to
// https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/discuss/286906/Java-3-iterative-and-recursive-codes-w-comments-and-explanation.
/**
 因为中序遍历有递归和迭代两种写法，逆中序遍历同样也可以写成迭代的形式
 Iterative version: use stack to pop out the nodes in reversed in order sequence.
 1.Initially, use cur to point to the root,
 2.push into Stack the right-most path of current subtree;
 3.pop out a node, update sum and the node value;
 4.point cur to the node's left child, if any;
 Repeat the above till the stack is empty and cur has no left child.
*/
class Solution {
    public TreeNode bstToGst(TreeNode root) {
        int sum = 0;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode cur = root;
        while(cur != null || !stack.isEmpty()) {
            // Save right-most path of the current subtree
            while(cur != null) {
                stack.push(cur);
                cur = cur.right;                
            }
            // Pop out by reversed in-order.
            cur = stack.pop();
            // Update sum.
            sum += cur.val;
            // Update node value.
            cur.val = sum;
            // Move to left branch.
            cur = cur.left;
        }
        return root;   
    }
}
