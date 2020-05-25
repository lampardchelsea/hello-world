/**
 Refer to
 https://www.lintcode.com/problem/lowest-common-ancestor-ii/description
 Given the root and two nodes in a Binary Tree. Find the lowest common ancestor(LCA) of the two nodes.
 The lowest common ancestor is the node with largest depth which is the ancestor of both nodes.
 The node has an extra attribute parent which point to the father of itself. The root's parent is null.

 Example
 Example 1:
 Input：{4,3,7,#,#,5,6},3,5
 Output：4
 Explanation：
     4
     / \
    3   7
       / \
      5   6
 LCA(3, 5) = 4
 
 Example 2:
 Input：{4,3,7,#,#,5,6},5,6
 Output：7
 Explanation：
      4
     / \
    3   7
       / \
      5   6
 LCA(5, 6) = 7
*/
/**
 * Definition of ParentTreeNode:
 * 
 * class ParentTreeNode {
 *     public ParentTreeNode parent, left, right;
 * }
 */

// Solution 1: 
// Refer to
// https://xuyiruan.com/2019/02/06/Lowest-Common-Ancestor-Series/
/**
 Thought
 The follow up problem provides extra parent pointer for each TreeNode. With this, we can eliminate the 
 time requires to traverse the entire tree to locate p and q node. Instead, we can trace directly from p 
 node to root and add all its ancester to a Set in ordder. Then trace from q to root and return the first 
 shared node as LCA
*/
public class Solution {
    /*
     * @param root: The root of the tree
     * @param A: node in the tree
     * @param B: node in the tree
     * @return: The lowest common ancestor of A and B
     */
    public ParentTreeNode lowestCommonAncestorII(ParentTreeNode root, ParentTreeNode A, ParentTreeNode B) {
        if(root == null) {
            return null;
        }
        Set<ParentTreeNode> parents_A = new HashSet<ParentTreeNode>();
        while(A != null) {
            parents_A.add(A);
            A = A.parent;
        }
        while(!parents_A.contains(B)) {
            B = B.parent;
        }
        return B;
    }
}
