/**
 * Refer to
 * https://leetcode.com/problems/average-of-levels-in-binary-tree/description/
 * Given a non-empty binary tree, return the average value of the nodes on each level in the form of an array.
    Example 1:
    Input:
        3
       / \
      9  20
        /  \
       15   7
    Output: [3, 14.5, 11]
    Explanation:
    The average value of nodes on level 0 is 3,  on level 1 is 14.5, and on level 2 is 11. Hence return [3, 14.5, 11].
    Note:
    The range of node's value is in the range of 32-bit signed integer.
 *
 * Solution 
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
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> result = new ArrayList<Double>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if(root == null) {
            return result;
        }
        queue.offer(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            double sum = 0.0;
            for(int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                sum += node.val;
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(sum / size);
        }
        return result;
    }
}

Time Complexity: O(N)
The time complexity of the given code is O(N), where N is the number of nodes in the binary tree. 
This is because the algorithm uses a queue to traverse each node exactly once. During the traversal, 
each node's value is accessed and added to a sum, and its children are potentially added to the queue.
Space Complexity: O(W)
The space complexity of the code can be considered as O(W), where W is the maximum width of the tree 
or the maximum number of nodes at any level of the tree. This occurs because the queue stores a level 
of the tree at most, which, in the worst case, can be all the leaf nodes of a full binary tree at the 
last level.
