/**
 Refer to
 https://www.geeksforgeeks.org/find-distance-root-given-node-binary-tree/
 Given root of a binary tree and a key x in it, find distance of the given key from root. 
 Distance means number of edges between two nodes.
 Examples:
Input : x = 45,
        Root of below tree
        5
      /    \
    10      15
    / \    /  \
  20  25  30   35
       \
       45
Output : Distance = 3             
There are three edges on path
from root to 45.

For more understanding of question,
in above tree distance of 35 is two
and distance of 10 is 1
*/
class Solution {
    public int findDistance(TreeNode root, int x) {
        // Returns -1 if x doesn't exist in tree. Else
        // returns distance of x from root
        if(root == null) {
            return -1;    
        }
        int distance = -1;
        // Check if x is present at root or in left
        // subtree or right subtree.
        if(root.val == x || (distance = findDistance(root.left, x) >= 0) 
            || (distance = findDistance(root.right, x)) >= 0) {
            return distance + 1;
        }
        return distance;
    }
    
    // Follow up: If BST, then if condition can be implement
    public int findDistance(TreeNode root, int x) {
        // Returns -1 if x doesn't exist in tree. Else
        // returns distance of x from root
        if(root == null) {
            return -1;    
        }
        int distance = -1;
        // Check if x is present at root or in left
        // subtree or right subtree.
        if(root.val == x) {
            return distance + 1;
        } else if(root.val > x) {
            return findDistance(root..left, x) + 1;
        } else if(root.val < x) {
            return findDistance(root.right, x) + 1;
        }
    }
    
}
