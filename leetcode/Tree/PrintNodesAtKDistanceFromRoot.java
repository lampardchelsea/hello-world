/**
 Refer to
 https://www.geeksforgeeks.org/print-nodes-at-k-distance-from-root/
 Given a root of a tree, and an integer k. Print all the nodes which are at k distance from root.
For example, in the below tree, 4, 5 & 8 are at distance 2 from root.
            1
          /   \
        2      3
      /  \    /
    4     5  8 
*/
// Solution 1:
// Refer to
// https://www.geeksforgeeks.org/print-nodes-at-k-distance-from-root/
class Solution {
    public List<Integer> printNodes(TreeNode root, int k) {
        List<Integer> result = new ArrayList<Integer>();
        if(root == null) {
            return result;
        }
        if(k == 0) {
            result.add(root.val);
        }
        printNodes(root.left, k - 1);
        printNodes(root.right, k - 1);
        return result;
    }
}
