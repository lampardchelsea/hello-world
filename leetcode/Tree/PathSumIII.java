/**
 * Refer to
 * https://leetcode.com/problems/path-sum-iii/description/
 * You are given a binary tree in which each node contains an integer value.

    Find the number of paths that sum to a given value.

    The path does not need to start or end at the root or a leaf, but it must go downwards 
    (traveling only from parent nodes to child nodes).

    The tree has no more than 1,000 nodes and the values are in the range -1,000,000 to 1,000,000.

    Example:

    root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8

          10
         /  \
        5   -3
       / \    \
      3   2   11
     / \   \
    3  -2   1

    Return 3. The paths that sum to 8 are:

    1.  5 -> 3
    2.  5 -> 2 -> 1
    3. -3 -> 11
 *
 * Solution
 * https://leetcode.com/problems/path-sum-iii/discuss/91884/Simple-AC-Java-Solution-DFS
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
    /**
     * Refer to
     * https://leetcode.com/problems/path-sum-iii/discuss/91884/Simple-AC-Java-Solution-DFS
     * Each time find all the path start from current node
       Then move start node to the child and repeat.
       Time Complexity should be O(N^2) for the worst case and O(NlogN) for balanced binary Tree.
    */
    public int pathSum(TreeNode root, int sum) {
        if(root == null) {
            return 0;
        }
        return helper(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum);
    }
    
    private int helper(TreeNode root, int remain) {
        int result = 0;
        if(root == null) {
            return result;
        }
        if(root.val == remain) {
            result++;
        }
        result += helper(root.left, remain - root.val);
        result += helper(root.right, remain - root.val);
        return result;
    }
}
