/**
 Refer to
 https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/
 We are given a binary tree (with root node root), a target node, and an integer value K.

Return a list of the values of all nodes that have a distance K from the target node.  
The answer can be returned in any order.

Example 1:
Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, K = 2
Output: [7,4,1]
                         3
                    5         1
                  6   2     0   8
                    7   4
                         
Explanation: 
The nodes that are a distance 2 from the target node (with value 5)
have values 7, 4, and 1.

Note that the inputs "root" and "target" are actually TreeNodes.
The descriptions of the inputs above are just serializations of these objects.

Note:
The given tree is non-empty.
Each node in the tree has unique values 0 <= node.val <= 500.
The target node is a node in the tree.
0 <= K <= 1000
*/
// Solution 1: DFS with back travel to ancestor
// Refer to
// https://www.geeksforgeeks.org/print-nodes-distance-k-given-node-binary-tree/
/**
 Given a binary tree, a target node in the binary tree, and an integer value k, 
 print all the nodes that are at distance k from the given target node. 
 No parent pointers are available.
 E.g
                            20
                     8             22
                 4      12
                     10    14 

 Consider the tree shown in diagram
 Input: target = pointer to node with data 8.
 root = pointer to node with data 20.
 k = 2.
 Output : 10 14 22

 If target is 14 and k is 3, then output should be 4 20

 There are two types of nodes to be considered.
1) Nodes in the subtree rooted with target node. 
   For example if the target node is 8 and k is 2, then such nodes are 10 and 14.
2) Other nodes, may be an ancestor of target, or a node in some other subtree. 
   For target node 8 and k is 2, the node 22 comes in this category.

Finding the first type of nodes is easy to implement. Just traverse subtrees rooted 
with the target node and decrement k in recursive call. When the k becomes 0, print 
the node currently being traversed (See this for more details). Here we call the 
function as printkdistanceNodeDown().

How to find nodes of second type? For the output nodes not lying in the subtree with 
the target node as the root, we must go through all ancestors. For every ancestor, 
we find its distance from target node, let the distance be d, now we go to other 
subtree (if target was found in left subtree, then we go to right subtree and vice 
versa) of the ancestor and find all nodes at k-d distance from the ancestor.
*/

// Also refer to
// https://www.cnblogs.com/grandyang/p/10686922.html
// https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/discuss/143798/1ms-beat-100-simple-Java-dfs-using-hashmap-with-explanation

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
    List<Integer> result = new ArrayList<Integer>();
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        if(root == null || target == null || K < 0) {
            return result;
        }
        distanceBetweenCurrentRootAndTargetNode(root, target, K);
        return result;
    }
    
    // Prints all nodes at distance K from a given target node.
    // The K distance nodes may be upward or downward. 
    // This function returns distance of root from target node, 
    // it returns -1 if target node is not present in tree rooted 
    // with root. 
    private int distanceBetweenCurrentRootAndTargetNode(TreeNode root, TreeNode target, int K) {
        // Base Case: If tree is empty, return -1 
        if(root == null) {
            return -1;
        }
        // If target is same as root. Use the downward function 
	// to add all nodes at distance K in subtree rooted with 
	// target or root 
        if(root.val == target.val) {
            printKDistanceNodeDown(root, K);
            return 0;
        }
        // Recursive for left subtree
        int dl = distanceBetweenCurrentRootAndTargetNode(root.left, target, K);
        // Check if target node was found in left subtree
        if(dl != -1) {
            // If root is at distance k from target, add root to result
            // Note that dl is distance of root's left child from target,
            // for root need plus 1 more edge to match distance K
            // Else go to right subtree and add all (K - dl -2) distance nodes 
            // Note that the right child is 2 edges away from left child 
            if(dl + 1 == K) {
                result.add(root.val);
            } else {
                printKDistanceNodeDown(root.right, K - dl - 2);
            }
            return dl + 1;
        }
        // MIRROR OF ABOVE CODE FOR RIGHT SUBTREE 
	// Note that we reach here only when node was not found in left subtree 
        int dr = distanceBetweenCurrentRootAndTargetNode(root.right, target, K);
        if(dr != -1) {
            if(dr + 1 == K) {
                result.add(root.val);
            } else {
                printKDistanceNodeDown(root.left, K - dr - 2);
            }
            return dr + 1;
        }
        // If target was neither present in left nor in right subtree
        return -1;
    }
    
    // Recursive function to print all the nodes at distance k in 
    // tree (or subtree) rooted with given root.
    private void printKDistanceNodeDown(TreeNode root, int K) {
        // Base case
        if(root == null || K < 0) {
            return;
        }
        // If we reach a K distant node, add it to result
        if(K == 0) {
            result.add(root.val);
            return;
        }
        // Recursive for left and right subtrees
        printKDistanceNodeDown(root.left, K - 1);
        printKDistanceNodeDown(root.right, K - 1);
    }
}

// Solution 2: DFS with 
