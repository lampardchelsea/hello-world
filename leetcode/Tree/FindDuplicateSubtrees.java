/**
 Refer to
 https://leetcode.com/problems/find-duplicate-subtrees/
 Given a binary tree, return all duplicate subtrees. For each kind of duplicate subtrees, 
 you only need to return the root node of any one of them.

 Two trees are duplicate if they have the same structure with same node values.

 Example 1:

        1
       / \
      2   3
     /   / \
    4   2   4
       /
      4
 The following are two duplicate subtrees:

      2
     /
    4
 and

    4
 Therefore, you need to return above trees' root in the form of a list.
*/

// Solution 1: Recursive + Encoded + HashMap
// Refer to
// https://leetcode.com/problems/find-duplicate-subtrees/discuss/106011/Java-Concise-Postorder-Traversal-Solution
/**
 We perform postorder (preorder) traversal, serializing and hashing the serials of subtrees 
 in the process. We can recognize a duplicate subtree by its serialization.
*/
class Solution {
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        Map<String, Integer> map = new HashMap<String, Integer>();
        helper(root, map, result);
        return result;
    }
    
    private String helper(TreeNode node, Map<String, Integer> map, List<TreeNode> result) {
        if(node == null) {
            return "#";
        }
        String encoded_tree = node.val + "," + helper(node.left, map, result) + "," + helper(node.right, map, result);
        if(map.getOrDefault(encoded_tree, 0) == 1) {
            result.add(node);
        }
        map.put(encoded_tree, map.getOrDefault(encoded_tree, 0) + 1);
        return encoded_tree;
    } 
}


