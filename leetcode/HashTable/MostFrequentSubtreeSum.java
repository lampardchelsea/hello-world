/**
 * Refer to
 * https://leetcode.com/problems/most-frequent-subtree-sum/#/description
 * Given the root of a tree, you are asked to find the most frequent subtree sum. 
 * The subtree sum of a node is defined as the sum of all the node values formed 
 * by the subtree rooted at that node (including the node itself). So what is the 
 * most frequent subtree sum value? If there is a tie, return all the values with 
 * the highest frequency in any order.
    Examples 1
    Input:

      5
     /  \
    2   -3
    return [2, -3, 4], since all the values happen only once, return all of them in any order.

    Examples 2
    Input:

      5
     /  \
    2   -5
    return [2], since 2 happens twice, however -5 only occur once.
 * Note: You may assume the sum of values in any subtree is in the range of 32-bit signed integer. 
 *
 * Solution
 * https://discuss.leetcode.com/topic/77775/verbose-java-solution-postorder-traverse-hashmap-18ms
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
public class Solution {
    Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    int maxOccur = 0;
    public int[] findFrequentTreeSum(TreeNode root) {
        postOrder(root);
        List<Integer> temp = new ArrayList<Integer>();
        for(Integer i : map.keySet()) {
            if(map.get(i) == maxOccur) {
                temp.add(i);
            }
        }
        int[] result = new int[temp.size()];
        for(int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }
        return result;
    }
    
    private int postOrder(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int left = postOrder(root.left);
        int right = postOrder(root.right);
        int sum = left + right + root.val;
        int occur = map.getOrDefault(sum, 0) + 1;
        map.put(sum, occur);
        maxOccur = Math.max(maxOccur, occur);
        return sum;
    }
}
