/**
 Refer to
 https://leetcode.com/problems/increasing-order-search-tree/
 Given a binary search tree, rearrange the tree in in-order so that the leftmost node in the tree is now the 
 root of the tree, and every node has no left child and only 1 right child.

Example 1:
Input: [5,3,6,2,4,null,8,1,null,null,null,7,9]

       5
      / \
    3    6
   / \    \
  2   4    8
 /        / \ 
1        7   9

Output: [1,null,2,null,3,null,4,null,5,null,6,null,7,null,8,null,9]

 1
  \
   2
    \
     3
      \
       4
        \
         5
          \
           6
            \
             7
              \
               8
                \
                 9  
 

Constraints:

The number of nodes in the given tree will be between 1 and 100.
Each node will have a unique integer value from 0 to 1000.
*/

// Solution 1: Approach 1: In-Order Traversal
// Refer to
// https://leetcode.com/problems/increasing-order-search-tree/solution/
/**
Intuition
The definition of a binary search tree is that for every node, all the values of the left branch are less than 
the value at the root, and all the values of the right branch are greater than the value at the root.
Because of this, an in-order traversal of the nodes will yield all the values in increasing order.

Algorithm
Once we have traversed all the nodes in increasing order, we can construct new nodes using those values to form the answer.
class Solution {    
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> vals = new ArrayList();
        inorder(root, vals);
        TreeNode ans = new TreeNode(0), cur = ans;
        for (int v: vals) {
            cur.right = new TreeNode(v);
            cur = cur.right;
        }
        return ans.right;
    }

    public void inorder(TreeNode node, List<Integer> vals) {
        if (node == null) return;
        inorder(node.left, vals);
        vals.add(node.val);
        inorder(node.right, vals);
    }
}

Complexity Analysis
Time Complexity: O(N), where N is the number of nodes in the given tree.
Space Complexity: O(N), the size of the answer.
*/
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
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        inorder(root, list);
        TreeNode newRoot = new TreeNode(list.get(0));
        // Traverse with a new pointer, since need to return original 'newRoot', similar to linked list idea
        TreeNode tmp = newRoot;
        for(int i = 1; i < list.size(); i++) {
            tmp.right = new TreeNode(list.get(i));
            tmp = tmp.right;
        }
        return newRoot;
    }
    
    private void inorder(TreeNode node, List<Integer> list) {
        if(node == null) {
            return;
        }
        inorder(node.left, list);
        // Only record values instead of object since we finally need re-construct tree not move/change original tree
        list.add(node.val);
        inorder(node.right, list);
    }
}

// Solution 2: Approach 2: Traversal with Relinking
// Refer to
// https://leetcode.com/problems/increasing-order-search-tree/solution/
/**
 Intuition and Algorithm
 We can perform the same in-order traversal as in Approach 1. During the traversal, we'll construct the answer on the fly, 
 reusing the nodes of the given tree by cutting their left child and adjoining them to the answer.
 Complexity Analysis
 Time Complexity: O(N), where N is the number of nodes in the given tree.
 Space Complexity: O(H) in additional space complexity, where H is the height of the given tree, and the size of the implicit 
                   call stack in our in-order traversal.
*/
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
    TreeNode tmp;
    public TreeNode increasingBST(TreeNode root) {
        // Create new root as one more pre-head
        TreeNode newRoot = new TreeNode(0);
        tmp = newRoot;
        inorder(root);
        return newRoot.right;
    }
    
    private void inorder(TreeNode node) {
        if(node == null) {
            return;
        }
        inorder(node.left);
        node.left = null;
        tmp.right = node;
        tmp = node;
        inorder(node.right);
    }
}

// Solution 3: Iterative + Stack
// Refer to
// https://leetcode.com/problems/increasing-order-search-tree/discuss/165885/C++JavaPython-Self-Explained-5-line-O(N)/218381
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/BinaryTreeInorderTraversal.java
/**
 Just need to mix Binary Tree Inorder Traversal with stack and edit how to handle inorder part
*/
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
    public TreeNode increasingBST(TreeNode root) {
        TreeNode tmp = new TreeNode(0);
        TreeNode cur = tmp;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        while(!stack.isEmpty() || root != null) {
            while(root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            root.left = null;
            cur.right = root;
            cur = root;
            root = root.right;
        }
        return tmp.right;
    }
}
