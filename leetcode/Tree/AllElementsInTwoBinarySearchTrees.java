/**
Refer to
https://leetcode.com/problems/all-elements-in-two-binary-search-trees/
Given two binary search trees root1 and root2.
 
Return a list containing all the integers from both trees sorted in ascending order.
 
Example 1:
Input: root1 = [2,1,4], root2 = [1,0,3]
Output: [0,1,1,2,3,4]

Example 2:
Input: root1 = [0,-10,10], root2 = [5,1,7,0,2]
Output: [-10,0,0,1,2,5,7,10]

Example 3:
Input: root1 = [], root2 = [5,1,7,0,2]
Output: [0,1,2,5,7]

Example 4:
Input: root1 = [0,-10,10], root2 = []
Output: [-10,0,10]

Example 5:
Input: root1 = [1,null,8], root2 = [8,1]
Output: [1,1,8,8]

Constraints:
Each tree has at most 5000 nodes.
Each node's value is between [-10^5, 10^5].
*/

// Solution 1: Inorder traversal + Merge two sorted array into one array
// Refer to
// https://www.geeksforgeeks.org/merge-two-sorted-arrays/
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
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<Integer>();
        List<Integer> list2 = new ArrayList<Integer>();
        List<Integer> result = new ArrayList<Integer>();
        inorder(root1, list1);
        inorder(root2, list2);
        int i = 0;
        int j = 0;
        int size1 = list1.size();
        int size2 = list2.size();
        while(i < size1 && j < size2) {
            if(list1.get(i) < list2.get(j)) {
                result.add(list1.get(i));
                i++;
            } else {
                result.add(list2.get(j));
                j++;
            }
        }
        while(i < size1) {
            result.add(list1.get(i));
            i++;
        }
        while(j < size2) {
            result.add(list2.get(j));
            j++;
        }
        return result;
    }
    
    private void inorder(TreeNode node, List<Integer> list) {
        if(node == null) {
            return;
        }
        inorder(node.left, list);
        list.add(node.val);
        inorder(node.right, list);
    }
}

// Solution 2: Two stacks + Inorder traverse
// Refer to
// https://leetcode.com/problems/all-elements-in-two-binary-search-trees/discuss/464073/C++-One-Pass-Traversal/416303
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/BinaryTreeInorderTraversal.java
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
    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> result = new ArrayList<Integer>();
        Stack<TreeNode> stack1 = new Stack<TreeNode>();
        Stack<TreeNode> stack2 = new Stack<TreeNode>();
        while(!stack1.isEmpty() || root1 != null || !stack2.isEmpty() || root2 != null) {
            while(root1 != null) {
                stack1.push(root1);
                root1 = root1.left;
            }
            while(root2 != null) {
                stack2.push(root2);
                root2 = root2.left;
            }
            if(stack2.isEmpty() || !stack1.isEmpty() && stack1.peek().val <= stack2.peek().val) {
                root1 = stack1.pop();
                result.add(root1.val);
                root1 = root1.right;
            } else {
                root2 = stack2.pop();
                result.add(root2.val);
                root2 = root2.right;
            }
        }
        return result;
    }
}
