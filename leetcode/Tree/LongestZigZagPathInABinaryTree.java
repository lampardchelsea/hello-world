/**
 Refer to
 https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/
 Given a binary tree root, a ZigZag path for a binary tree is defined as follow:

 Choose any node in the binary tree and a direction (right or left).
 If the current direction is right then move to the right child of the current node otherwise move to the left child.
 Change the direction from right to left or right to left.
 Repeat the second and third step until you can't move in the tree.
 Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).

 Return the longest ZigZag path contained in that tree.

 Example 1:
             1
                 1  -> R
               1   1
           L <-  1   1
                   1 -> R
                     1
                     
 Input: root = [1,null,1,1,1,null,null,1,1,null,1,null,null,null,1,null,1]
 Output: 3
 Explanation: Longest ZigZag path in blue nodes (right -> left -> right).

 Example 2:
              1
     L <-  1     1
              1 -> R
     L <-  1     1
              1 -> R
              
 Input: root = [1,1,1,null,1,null,null,1,1,null,1]
 Output: 4
 Explanation: Longest ZigZag path in blue nodes (left -> right -> left -> right).

 Example 3:
 Input: root = [1]
 Output: 0
 
 Constraints:
 Each tree has at most 50000 nodes..
 Each node's value is between [1, 100].
*/

// Solution 1: Start with 2 direction try
// Refer to
// https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/discuss/534418/Java-DFS-Solution-with-comment-O(N)-Clean-code
/**
class Solution {
    int maxStep = 0;
    public int longestZigZag(TreeNode root) {
        dfs(root, true, 0);  // --> true means go from current node(now is root) to right ??
        dfs(root, false, 0); // --> false means go from current node(now is root) to left ??
        return maxStep;
    }
    private void dfs(TreeNode root, boolean isLeft, int step) {      // ---> isLeft == goToRight ??
        if (root == null) return;
        maxStep = Math.max(maxStep, step); // update max step sofar
        if (isLeft) {
            dfs(root.left, false, step + 1); // keep going from root to left
            dfs(root.right, true, 1); // restart going from root to right
        } else {
            dfs(root.right, true, step + 1); // keep going from root to right
            dfs(root.left, false, 1); // restart going from root to left
        }
    }
}
*/

// https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/discuss/531880/Simple-Java-Code-With-Comments/670284
/**
Style 1:
class Solution {
    static int max = 0;
    public static int longestZigZag(TreeNode root) {
        if (root == null) return -1;// if null return -1
        max = 0;
        helper(root.right, 1, true);// go right
        helper(root.left, 1, false);// go left
        return max;
    }

    private static void helper(TreeNode root, int step, boolean isRight) {
        if (root == null) return;
        max = Math.max(max, step);
        if (isRight) {// if coming from right go left
            helper(root.left, step + 1, false);
            helper(root.right, 1, true);//try again from start
        } else {// else coming from left then go right
            helper(root.right, step + 1, true);
            helper(root.left, 1, false);
        }
    }
}

Style 2:
class Solution {
    public int longestZigZag(TreeNode root) {
        if (root == null) return 0;
        return Math.max(
            longestZigZag(root.left, true, 0),
            longestZigZag(root.right, false, 0)
        );

    }
    
    private int longestZigZag(TreeNode node, boolean left, int longest) {
        if (node == null) return longest;        
        if (left) {
            return Math.max(
                longestZigZag(node.right, false, longest + 1),
                longestZigZag(node.left, true, 0)
            );
        } else {
            return Math.max(
                longestZigZag(node.left, true, longest + 1),
                longestZigZag(node.right, false, 0)
            );
        }
    }
}
*/

// https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/discuss/531867/JavaPython-DFS-Solution/471524
/**
When traversing binary tree if you took "left" child to reach node and now visiting "right" child, you found 1 more depth path. 
So we keep track of the previous direction based on boolean true (considered as right direction) and false (considered as a left direction).
When going left (node.left) if the previous direction was right you get depth+1 else you hit 2 left nodes in a row, 
so you start from ZERO again, same for right child direction.
class Solution {
    int max = 0;
    public int longestZigZag(TreeNode root) {
        if(root == null) return 0;
        path(root.left, 0, false); //try both directions, and choose the better one, true goes right, false goes left
        path(root.right, 0, true);
        return max;
    }
    private void path(TreeNode node, int depth, boolean direction) { //direction: true is right, false is left
        max = Math.max(max, depth);
        if(node == null) return;
        path(node.left, (direction) ? depth+1: 0, false); //if previous direction was right, and now going left, we add 1 to depth, else starting again as 0
        path(node.right, (!direction) ? depth+1: 0, true);
    }
}
*/


// Solution 2: Start without 2 directions
// Refer to
// https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/discuss/531808/Java-Recursion-Try-each-node-as-a-zigzag-root-then-return-valid-sum-to-parent
/**
class Solution {
    int result = 0;
    public int longestZigZag(TreeNode root) {
        dfs(root, true);
        return result;
    }
    
    public int dfs(TreeNode node, boolean isLeft){
        if(node == null){
            return 0;
        }
        //try start here
        int l = dfs(node.left, false);
        int r = dfs(node.right, true);
        result = Math.max(result, l);
        result = Math.max(result, r);
        //return sum for parent
        return 1 + (isLeft ? l : r);
    }
}
*/

// https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/discuss/531808/Java-Recursion-Try-each-node-as-a-zigzag-root-then-return-valid-sum-to-parent/555995
/**
class Solution {
    private int res = 0;
    public int longestZigZag(TreeNode root) {
        dfs(root, true);
        return res;
    }
    private int dfs(TreeNode root, boolean isLeft) {
        if (root == null) return 0;
        int left = dfs(root.left, true);
        int right = dfs(root.right, false);
        res = Math.max(res, Math.max(left, right));
        return isLeft ? 1 + right : 1 + left;
    }
}
*/

