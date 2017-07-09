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
 * Solution:
 * https://discuss.leetcode.com/topic/77775/verbose-java-solution-postorder-traverse-hashmap-18ms
 * Idea is post-order traverse the tree and get sum of every sub-tree, put sum to count mapping 
 * to a HashMap. Then generate result based on the HashMap.
 * 
 * 
 * Improvement:
 * https://discuss.leetcode.com/topic/77775/verbose-java-solution-postorder-traverse-hashmap-18ms/5
 * Similar solution without browsing the whole hashmap again to find frequent sums. (Why I do this? 
 * Because I was asked to do so during an interview...)
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int fre = 0;
        List<Integer> res = new ArrayList<Integer>();
        public int[] findFrequentTreeSum(TreeNode root) {
            if(root == null) return new int[0];
            helper(root);
            int[] ret = new int[res.size()];
            for(int i = 0; i < res.size(); i ++){
                ret[i] = res.get(i);
            }
            return ret;
        }
        
        public void helper(TreeNode root){
            if(root == null) return ;
            int sum = computeSum(root);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
            if(map.get(sum) == fre){
                res.add(sum);
            }else if(map.get(sum) > fre){
                res.clear();
                res.add(sum);
            }
            fre = Math.max(fre, map.get(sum));
            helper(root.left);
            helper(root.right);
        }
        
        public int computeSum(TreeNode root){
            int s = root.val;
            if(root.left != null) s += computeSum(root.left);
            if(root.right != null) s += computeSum(root.right);
            return s;
        }
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
