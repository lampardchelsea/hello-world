/**
 Refer to
 https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 Given preorder and inorder traversal of a tree, construct the binary tree.

Note:
You may assume that duplicates do not exist in the tree.

For example, given

preorder = [3,9,20,15,7]
inorder = [9,3,15,20,7]
Return the following binary tree:

    3
   / \
  9  20
    /  \
   15   7
*/

// Solution 1: Recursive
// Refer to
// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution
/**
The basic idea is here:
Say we have 2 arrays, PRE and IN.
Preorder traversing implies that PRE[0] is the root node.
Then we can find this PRE[0] in IN, say it's IN[5].
Now we know that IN[5] is root, so we know that IN[0] - IN[4] is on the left side, IN[6] to the end is on the right side.
Recursively doing this on subarrays, we can build a tree out of it :)
*/

// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution/32854
/**
I hope this helps those folks who can't figure out how to get the index of the right child:
Our aim is to find out the index of right child for current node in the preorder array
We know the index of current node in the preorder array - preStart (or whatever your call it), it's the root of a subtree
Remember pre order traversal always visit all the node on left branch before going to the right ( root -> left -> ... -> right), 
therefore, we can get the immediate right child index by skipping all the node on the left branches/subtrees of current node
The inorder array has this information exactly. Remember when we found the root in "inorder" array we immediately know how many 
nodes are on the left subtree and how many are on the right subtree
Therefore the immediate right child index is preStart + numsOnLeft + 1 (remember in preorder traversal array root is always 
ahead of children nodes but you don't know which one is the left child which one is the right, and this is why we need inorder array)
numsOnLeft = root - inStart.
*/
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return helper(0, 0, inorder.length - 1, preorder, inorder);
    }
    
    private TreeNode helper(int rootIndexInPreorder, int inStart, int inEnd, int[] preorder, int[] inorder) {
        if(inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[rootIndexInPreorder]);
        int rootIndexInInorder = 0;
        for(int i = inStart; i <= inEnd; i++) {
            if(inorder[i] == root.val) {
                rootIndexInInorder = i;
            }
        }
        int leftChildNum = rootIndexInInorder - inStart;
        root.left = helper(rootIndexInPreorder + 1, inStart, rootIndexInInorder - 1, preorder, inorder);
        root.right = helper(rootIndexInPreorder + leftChildNum + 1, rootIndexInInorder + 1, inEnd, preorder, inorder);
        return root;
    }
}

// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution/32871
/**
 One improvement: remember to use HashMap to cache the inorder[] position. This can reduce your solution from 20ms to 5ms.
*/

// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34538/My-Accepted-Java-Solution/32851
/**
 Note that arguments preEnd and inorder are not needed.
*/
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }
        return helper(0, 0, inorder.length - 1, preorder, inMap);
    }
    
    private TreeNode helper(int rootIndexInPreorder, int inStart, int inEnd, int[] preorder, Map<Integer, Integer> inMap) {
        if(inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[rootIndexInPreorder]);
        int rootIndexInInorder = inMap.get(preorder[rootIndexInPreorder]);
        int leftChildNum = rootIndexInInorder - inStart;
        root.left = helper(rootIndexInPreorder + 1, inStart, rootIndexInInorder - 1, preorder, inMap);
        root.right = helper(rootIndexInPreorder + leftChildNum + 1, rootIndexInInorder + 1, inEnd, preorder, inMap);
        return root;
    }
}

// Solution 2: Iterative
// Refer to
// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/discuss/34555/The-iterative-solution-is-easier-than-you-think!/117721
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/ConstructBinarySearchTreeFromPreorderTraversal.java
/**
 Same way as how we handle the iterative solution of Construct Binary Search Tree From Preorder Traversal
*/
class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || preorder.length == 0) {
            return null;
        }
        // Build a map of the indices of the values as they appear in the inorder array
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        Stack<TreeNode> stack = new Stack<TreeNode>();
        TreeNode root = new TreeNode(preorder[0]);
        stack.push(root);
        // For remaining all nodes in preorder, use map build by inorder 
        // to get node's relative position, the operation is same 
        // as Construct Binary Search Tree From Preorder Traversal
        for(int i = 1; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            if(map.get(node.val) < map.get(stack.peek().val)) {
                // The new node is on the left of the last node,
                // so it must be its left child (that's the way preorder works)
                stack.peek().left = node;
            } else {
                // The new node is on the right of the last node,
                // so it must be the right child of either the last node
                // or one of the last node's ancestors.
                // pop the stack until we either run out of ancestors
                // or the node at the top of the stack is to the right of the new node
                TreeNode parent = null;
                while(!stack.isEmpty() && map.get(node.val) > map.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.right = node;
            }
            stack.push(node);
        }
        return root;
    }
}
