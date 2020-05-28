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
 Intuition
 We can combine both depth first searches in Approach #1 into an approach that does both steps in one pass. 
 We will have some function dfs(node) that returns both the answer for this subtree, and the distance from 
 node to the deepest nodes in this subtree.

 Algorithm
 The TreeNodeWithHeight (on some subtree) returned by our (depth-first search) recursion will have two parts: 
 TreeNodeWithHeight.node: the largest depth node that is equal to or an ancestor of all the deepest nodes of this subtree. 
 TreeNodeWithHeight.height: the number of nodes in the path from the root of this subtree, to the deepest node in this subtree.
 
 We can calculate these answers disjointly for dfs(node):
 
 To calculate the TreeNodeWithHeight.node of our answer:
 If one childResult has deeper nodes, then childResult.node will be the answer.
 If they both have the same depth nodes, then node will be the answer.
 
 The TreeNodeWithHeight.height of our answer is always 1 more than the largest childResult.height we have.
*/

// https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/discuss/146808/C++JavaPython-One-Pass/301421
/**
 * The question is unclear. For example, if we did not have nodes 7 and 4, the answer would
 * be TreeNode(3). If we did not have node 4, the answer would be TreeNode(7) and not
 * TreeNode(2). Similarly, if we did not have 7, the answer would be TreeNode(4) and not
 * TreeNode(2).
 *
 * Intuitively, we should be traversing from the children to the parent and calculate the
 * height from bottom. So the null nodes would have height -1. The leaf nodes would have the
 * height 0 and the root would have the max height.
 * Note: In origial post the null nodes height set as 0 and leaf node height as 1, but these
 * two values violate the rule about 
 * 
 * At each node, we keep a pair<height_of_node_from_bottom, node>. At a given node, if we
 * realize that the leftHeight == rightHeight, it means we have found the deepest subtree
 * rooted at node. If leftHeight > rightHeight, it means the deepest subtree must be rooted
 * at left child. If rightHeight > leftHeight, it means the deepest subtree must be rooted
 * at right child.
 *
 * Which traversal allows us to traverse from bottom-up? Postorder! So we use it in the code.
 */
class Solution {
    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        TreeNodeWithHeight result = helper(root);
        return result.node;
    }
    
    // Important to implement bottom-up traverse, we need Postorder
    private TreeNodeWithHeight helper(TreeNode node) {
        if(node == null) {
            return new TreeNodeWithHeight(null, -1); // Null node height -1, leaf node height 0
        }
        TreeNodeWithHeight L = helper(node.left);
        TreeNodeWithHeight R = helper(node.right);
        if(L.height == R.height) {
            return new TreeNodeWithHeight(node, L.height + 1);
        } else if(L.height > R.height) {
            return new TreeNodeWithHeight(L.node, L.height + 1);
        } else {
            return new TreeNodeWithHeight(R.node, R.height + 1);
        }
    }
}

class TreeNodeWithHeight {
    int height;
    TreeNode node;
    public TreeNodeWithHeight(TreeNode node, int height) {
        this.height = height;
        this.node = node;
    }
}

// Testing version:
public class Solution {
    public static void main(String[] args) {
        /**
         * Test with below binary tree
         * 
         *           3
         *       /       \
                5         1
              /   \	    /   \
             6     2   0     8
                 /   \
                7     4
         * 			3 -> 5 -> 6 -> return L -1 / R -1 -> new TreeNodeWithHeight(node (6), L.height(-1) + 1);
                     -> 2 -> 7 -> return L -1 / R -1 -> new TreeNodeWithHeight(L.node (7), L.height(-1) + 1);
                          -> 4 -> return L -1 / R -1 -> new TreeNodeWithHeight(L.node (4), L.height(-1) + 1);
                     -> return new TreeNodeWithHeight(R.node (2), R.height (1) + 1);
                -> 1 -> 0 -> return L -1 / R -1 -> new TreeNodeWithHeight(0, L.height(-1) + 1);
                     -> 8 -> return L -1 / R -1 -> new TreeNodeWithHeight(8, L.height(-1) + 1);
                 -> return new TreeNodeWithHeight(L.node (1), L.height (1) + 1);
         */
        Solution q = new Solution();
        TreeNode root = q.new TreeNode(3);
        root.left = q.new TreeNode(5);
        root.right = q.new TreeNode(1);
        root.left.left = q.new TreeNode(6);
        root.left.right = q.new TreeNode(2);
        root.left.right.left = q.new TreeNode(7);
        root.left.right.right = q.new TreeNode(4);
        root.right.left = q.new TreeNode(0);
        root.right.right = q.new TreeNode(8);
        TreeNode result = q.subtreeWithAllDeepest(root);
        System.out.println(result.val);
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    class TreeNodeWithHeight {
        int height;
        TreeNode node;
        public TreeNodeWithHeight(TreeNode node, int height) {
            this.height = height;
            this.node = node;
        }
    }

    public TreeNode subtreeWithAllDeepest(TreeNode root) {
        TreeNodeWithHeight result = helper(root);
        return result.node;
    }

    private TreeNodeWithHeight helper(TreeNode node) {
        if (node == null) {
            return new TreeNodeWithHeight(null, -1);
        }
        TreeNodeWithHeight L = helper(node.left);
        TreeNodeWithHeight R = helper(node.right);
        //1. If we realize that the leftHeight == rightHeight, it means we have found 
        //   the deepest subtree rooted at node.
        //2. If leftHeight > rightHeight, it means the deepest subtree must be rooted
        //   at left child.
        //3. If rightHeight > leftHeight, it means the deepest subtree must be rooted
        //   at right child.
        if (L.height == R.height) {
            return new TreeNodeWithHeight(node, L.height + 1);
        } else if (L.height > R.height) {
            return new TreeNodeWithHeight(L.node, L.height + 1);
        } else {
            return new TreeNodeWithHeight(R.node, R.height + 1);
        }
    }
}

