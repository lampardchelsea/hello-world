/**
 Same question as 1123. Lowest Common Ancestor of Deepest Leaves
 https://leetcode.com/problems/lowest-common-ancestor-of-deepest-leaves/
 Refer to
 https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
 Given a binary tree rooted at root, the depth of each node is the shortest distance to the root.
 A node is deepest if it has the largest depth possible among any node in the entire tree.
 The subtree of a node is that node, plus the set of all descendants of that node.
 Return the node with the largest depth such that it contains all the deepest nodes in its subtree.
 
 Example 1:
 Input: [3,5,1,6,2,0,8,null,null,7,4]
 Output: [2,7,4]
 Explanation:
 We return the node with value 2, colored in yellow in the diagram.
 The nodes colored in blue are the deepest nodes of the tree.
 The input "[3, 5, 1, 6, 2, 0, 8, null, null, 7, 4]" is a serialization of the given tree.
 The output "[2, 7, 4]" is a serialization of the subtree rooted at the node with value 2.
 Both the input and output have TreeNode type.
 
 Note:
 The number of nodes in the tree will be between 1 and 500.
 The values of each node are unique.
*/

// Solution 1: Two Pass recursive
// Refer to
// https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/solution/
// https://leetcode.com/articles/smallest-subtree-with-all-the-deepest-nodes/
/**
 Approach 1: Paint Deepest Nodes
 Intuition
 We try a straightforward approach that has two phases.
 The first phase is to identify the nodes of the tree that are deepest. To do this, we have to 
 annotate the depth of each node. We can do this with a depth first search.
 Afterwards, we will use that annotation to help us find the answer:
 1. If the node in question has maximum depth, it is the answer.
 2. If both the left and right child of a node have a deepest descendant, then the answer is this parent node.
 3. Otherwise, if some child has a deepest descendant, then the answer is that child.
 4. Otherwise, the answer for this subtree doesn't exist.
 
 Algorithm
 In the first phase, we use a depth first search dfs to annotate our nodes.
 In the second phase, we also use a depth first search answer(node), returning the answer for the subtree 
 at that node, and using the rules above to build our answer from the answers of the children of node.
 
 Note that in this approach, the findLCA function returns answers that have the deepest nodes of the entire tree, 
 not just the subtree being considered.
 
 Complexity Analysis
 Time Complexity: O(N), where N is the number of nodes in the tree.
 Space Complexity: O(N).
*/
class Solution {
    Map<TreeNode, Integer> depth;
    int max_depth;
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        max_depth = -1;
        depth = new HashMap<TreeNode, Integer>();
        // Root depth start with 0, its parent set as NULL and depth -1
        depth.put(null, -1);
        // Mapping depth to node (start with root)
        helper(root, null);
        for(int d : depth.values()) {
            if(d > max_depth) {
                max_depth = d;
            }
        }
        return findLCA(root);
    }
    
    private void helper(TreeNode node, TreeNode parent) {
        if(node == null) {
            return;
        }
        depth.put(node, depth.get(parent) + 1);
        helper(node.left, node);
        helper(node.right, node);
    }
    
    private TreeNode findLCA(TreeNode node) {
        if(node == null) {
            return node;
        }
        // If the node in question has maximum depth (leaves), it is the answer.
        if(depth.get(node) == max_depth) {
            return node;
        }
        TreeNode left = findLCA(node.left);
        TreeNode right = findLCA(node.right);
        // If both the left and right child of a node have a deepest 
        // descendant, then the answer is this parent node.
        if(left != null && right != null) {
            return node;
        }
        // Otherwise, if some child has a deepest descendant, 
        // then the answer is that child.
        if(left != null) {
            return left;
        }
        if(right != null) {
            return right;
        }
        // Otherwise, the answer for this subtree doesn't exist.
        return null;
    }
}

// Solution 2: One Pass recursive
// Refer to
// https://leetcode.com/articles/smallest-subtree-with-all-the-deepest-nodes/
/**
 
*/


