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
    // Style 1:
    // Refer to
    // https://www.geeksforgeeks.org/find-distance-root-given-node-binary-tree/
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
 
    // Style 2:
    // Refer to
    // https://stackoverflow.com/questions/22585740/get-the-distance-from-a-root-to-a-node-with-given-value-in-a-binary-tree-algori
    public int findDistance(TreeNode root, int x) {
        // Base case: Value is never in an empty subtree
        if(root == null) {
            return -1;
        }
        if(root.val == x) {
            return 0;
        }
        int left = findDistance(root.left, x);
        int right = findDistance(root.right, x);
        // If both left subtree and right subtree not contain value x
        if(left == -1 && right == -1) {
            return -1;
        }
        // If the value was found, return the distance from the root of the subtree + 1
        return Math.max(left, right) + 1;
    }
}
