/**
 Refer to
 https://leetcode.com/problems/unique-binary-search-trees-ii/
 Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

Example:
Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
 

Constraints:

0 <= n <= 8
*/

// Solution 1: Recursive with O(n^3)
// Refer to
// https://leetcode.com/problems/unique-binary-search-trees-ii/discuss/31494/A-simple-recursive-solution/30203
class Solution {
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        if(n == 0) {
            return result;
        }
        return helper(1, n);
    }
    
    private List<TreeNode> helper(int start, int end) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        if(start > end) {
            list.add(null);
            return list;
        }
        for(int i = start; i <= end; i++) {
            List<TreeNode> left = helper(start, i - 1);
            List<TreeNode> right = helper(i + 1, end);
            for(TreeNode l : left) {
                for(TreeNode r : right) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    root.right = r;
                    list.add(root);
                }
            }
        }
        return list;
    }
}
