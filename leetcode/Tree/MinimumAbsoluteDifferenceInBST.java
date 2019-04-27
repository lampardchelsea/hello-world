/**
 Refer to
 https://leetcode.com/problems/minimum-absolute-difference-in-bst/
 Given a binary search tree with non-negative values, find the minimum absolute difference between values of any two nodes.

Example:

Input:

   1
    \
     3
    /
   2

Output:
1

Explanation:
The minimum absolute difference is 1, which is the difference between 2 and 1 (or between 2 and 3).

Note: There are at least two nodes in this BST.
*/
// Solution 1: Store previous node and in-order traverse on BST since it gurantee the ascending sorted order already
// Refer to
// https://leetcode.com/problems/minimum-absolute-difference-in-bst/discuss/99905/Two-Solutions-in-order-traversal-and-a-more-general-way-using-TreeSet
// The most common idea is to first inOrder traverse the tree and compare the delta between each of the adjacent values. 
// It's guaranteed to have the correct answer because it is a BST thus inOrder traversal values are sorted.
// Time complexity O(N), space complexity O(1).
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
    int min = Integer.MAX_VALUE;
    TreeNode prev = null;
    public int getMinimumDifference(TreeNode root) {
        if(root == null) {
            return min;
        }
        getMinimumDifference(root.left);
        //prev = root.left;
        if(prev != null && min > root.val - prev.val) {
            min = root.val - prev.val;
        }
        prev = root;
        getMinimumDifference(root.right);
        return min;
    }
}

// Solution 2: Pre-Order traverse with TreeSet ceiling and floor method to get interval between adjacent values
// What if it is not a BST? (Follow up of the problem) The idea is to put values in a TreeSet and then every 
// time we can use O(lgN) time to lookup for the nearest values.
// TreeSet API
// ceiling -> Returns the least element in this set greater than or equal to the given element, or null if there is no such element.
// floor -> Returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
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
    int min = Integer.MAX_VALUE;
    TreeSet<Integer> set = new TreeSet<Integer>();
    public int getMinimumDifference(TreeNode root) {
        if(root == null) {
            return min;
        }
        if(!set.isEmpty()) {
            if(set.ceiling(root.val) != null) {
                min = Math.min(min, set.ceiling(root.val) - root.val);
            }
            if(set.floor(root.val) != null) {
                min = Math.min(min, root.val - set.floor(root.val));
            }    
        }
        set.add(root.val); // Pre-order traversal
        getMinimumDifference(root.left);
        getMinimumDifference(root.right);
        return min;
    }
}

// Solution 3: Native list with sorting solutoin and find all interval between two numbers to find minimum, no need TreeSet
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
    int min = Integer.MAX_VALUE;
    List<Integer> list = new ArrayList<Integer>();
    public int getMinimumDifference(TreeNode root) {
        if(root == null) {
            return min;
        }
        addToList(list, root);
        Collections.sort(list);
        for(int i = 0; i < list.size() - 1; i++) {
            int j = i + 1;
            min = Math.min(min, list.get(j) - list.get(i));
        }
        return min;
    }
    
    private void addToList(List<Integer> list, TreeNode root) {
        if(root == null) {
            return;
        }
        addToList(list, root.left);
        list.add(root.val);
        addToList(list, root.right);
    }
}
