/**
 * Refer to
 * https://leetcode.com/problems/recover-binary-search-tree/description/
 * Two elements of a binary search tree (BST) are swapped by mistake.
    Recover the tree without changing its structure.

    Note:
    A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
 * 
 * Solution
 * https://discuss.leetcode.com/topic/3988/no-fancy-algorithm-just-simple-and-powerful-in-order-traversal/2
 * https://www.youtube.com/watch?v=2rsGbHnIDV0
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/Document/bst_inorder_traverse.pdf
 * About the binary search tree inorder traverse
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
    TreeNode firstToExchange = null;
    TreeNode secondToExchange = null;
    //TreeNode prev = new TreeNode(Integer.MIN_VALUE);
    TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        if(root == null) {
            return;
        }
        // Start inorder DFS traverse
        traverse(root);
        // Not exchange TreeNode but its value only
        int temp = firstToExchange.val;
        firstToExchange.val = secondToExchange.val;
        secondToExchange.val = temp;
    }
    
    private void traverse(TreeNode root) {
        if(root == null) {
            return;
        }
        traverse(root.left);
        if(prev != null && prev.val >= root.val) {
            if(firstToExchange == null) {
                firstToExchange = prev;
            }
            // Becareful, the exchanged node here should be 'root',
            // not 'prev', if given inorder traverse as 6, 3, 4, 5, 2
            // corresponding to 2
            secondToExchange = root;
        }
        prev = root;
        traverse(root.right);
    }
}

// Re-work
// Refer to
// https://leetcode.com/problems/recover-binary-search-tree/discuss/32535/No-Fancy-Algorithm-just-Simple-and-Powerful-In-Order-Traversal
// https://leetcode.com/problems/recover-binary-search-tree/discuss/32535/No-Fancy-Algorithm-just-Simple-and-Powerful-In-Order-Traversal/205091
/**
This question appeared difficult to me but it is really just a simple in-order traversal! I got really 
frustrated when other people are showing off Morris Traversal which is totally not necessary here.
 
Let's start by writing the in order traversal:
private void traverse (TreeNode root) {
   if (root == null)
      return;
   traverse(root.left);
   // Do some business
   traverse(root.right);
}
So when we need to print the node values in order, we insert System.out.println(root.val) in the place of 
"Do some business".

What is the business we are doing here?
We need to find the first and second elements that are not in order right?

How do we find these two elements? For example, we have the following tree that is printed as in order traversal:

6, 3, 4, 5, 2

We compare each node with its next one and we can find out that 6 is the first element to swap because 6 > 3 and 2 
is the second element to swap because 2 < 5.

Really, what we are comparing is the current node and its previous node in the "in order traversal".

Let us define three variables, firstElement, secondElement, and prevElement. Now we just need to build the "do 
some business" logic as finding the two elements.
*/
class Solution {
    TreeNode firstElement = null;
    TreeNode secondElement = null;
    TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        // In order traversal to find the two elements
        helper(root);
        // Swap the values of the two nodes
        int temp = firstElement.val;
        firstElement.val = secondElement.val;
        secondElement.val = temp;
    }
    
    private void helper(TreeNode node) {
        if(node == null) {
            return;
        }
        helper(node.left);
        // Start of "do some business", 
        // If first element has not been found, assign it to prevElement (refer to 6 in the example above)
        if(firstElement == null && (prev == null || prev.val >= node.val)) {
            firstElement = prev;
        }
        // If first element is found, assign the second element to the root (refer to 2 in the example above)
        if(firstElement != null && prev.val >= node.val) {
            // Becareful, the exchanged node here should be 'root',
            // not 'prev', if given inorder traverse as 6, 3, 4, 5, 2
            // corresponding to 2
            secondElement = node;
        }
        prev = node;
        // End of "do some business"
        helper(node.right);
    }
}



