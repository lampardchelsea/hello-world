/**
  Refer to
  https://leetcode.com/problems/balance-a-binary-search-tree/
  Given a binary search tree, return a balanced binary search tree with the same node values.
  A binary search tree is balanced if and only if the depth of the two subtrees of every node never differ by more than 1.
  If there is more than one answer, return any of them.
  
  Example 1:
  Input: root = [1,null,2,null,3,null,4,null,null]
  Output: [2,1,3,null,null,null,4]
  Explanation: This is not the only correct answer, [3,1,4,null,2,null,null] is also correct.
  
  Constraints:
  The number of nodes in the tree is between 1 and 10^4.
  The tree nodes will have distinct values between 1 and 10^5.
*/

// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/balance-a-binary-search-tree/discuss/539686/JavaC%2B%2B-Sorted-Array-to-BST-O(N)-Clean-code
/**
 Intuitive
 Traverse binary tree in-order to get sorted array
 The problem become 108. Convert Sorted Array to Binary Search Tree
*/
class Solution {
    List<TreeNode> sortedArr = new ArrayList<>();
    public TreeNode balanceBST(TreeNode root) {
        inorderTraverse(root);
        return sortedArrToBST(0, sortedArr.size() - 1);
    }
    
    private void inorderTraverse(TreeNode root) {
        if(root == null) {
            return;
        }
        inorderTraverse(root.left);
        sortedArr.add(root);
        inorderTraverse(root.right);
    }
    
    private TreeNode sortedArrToBST(int start, int end) {
        if(start > end) {
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode node = sortedArr.get(mid);
        node.left = sortedArrToBST(start, mid - 1);
        node.right = sortedArrToBST(mid + 1, end);
        return node;
    }
}

// Solution 2: Iterative
// Refer to
// https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/
// https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/discuss/35218/Java-Iterative-Solution/241775
class Solution {
    List<TreeNode> sortedArr = new ArrayList<TreeNode>();
    public TreeNode balanceBST(TreeNode root) {
        inorderTraverse(root);
        return sortedArrayToBST(sortedArr);
    }
    
    private void inorderTraverse(TreeNode root) {
        if(root == null) {
            return;
        }
        inorderTraverse(root.left);
        sortedArr.add(root);
        inorderTraverse(root.right);
    }
    
    private TreeNode sortedArrayToBST(List<TreeNode> list) {
        if(list == null || list.size() == 0) {
            return null;
        }
        TreeNode root = new TreeNode(0);
        Stack<Object> stack = new Stack<Object>();
        stack.push(list.size() - 1);
        stack.push(0);
        stack.push(root);
        while(!stack.isEmpty()) {
            TreeNode node = (TreeNode)stack.pop();
            int start = (int)stack.pop();
            int end = (int)stack.pop();
            int mid = start + (end - start) / 2;
            node.val = list.get(mid).val;
            if(mid - 1 >= start) {
                node.left = new TreeNode(0);
                stack.push(mid - 1);
                stack.push(start);
                stack.push(node.left);
            }
            if(mid + 1 <= end) {
                node.right = new TreeNode(0);
                stack.push(end);
                stack.push(mid + 1);
                stack.push(node.right);
            }
        }
        return root;
    }
}
