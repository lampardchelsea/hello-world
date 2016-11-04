/**
 * Given a binary tree, return the bottom-up level order traversal of its nodes' values. 
 * (ie, from left to right, level by level from leaf to root).
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
 * return its bottom-up level order traversal as:
[
  [15,7],
  [9,20],
  [3]
]
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
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        
        if(root == null) {
            return result;
        }
        
        queue.add(root);
        
        while(!queue.isEmpty()) {
            List<Integer> level = new ArrayList<Integer>();
            
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                TreeNode x = queue.poll();
                level.add(x.val);
            
                if(x.left != null) {
                    queue.add(x.left);
                }
                if(x.right != null) {
                    queue.add(x.right);
                }
            }

            result.add(level);
        }
        
        // Use Collections reverse() method to reverse the result arraylist
        // or use self-define method to reverse the result.
        // E.g When create the result we can use 
        // 1. resultList.add(0, valuesInCurrentLevel);
        // Refer to https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html#add(int,%20E)
        // 2. List<ArrayList<Integer>> reversedResult = new  ArrayList<ArrayList<Integer>>();
        //    for(int i=result.size()-1; i>=0; i--){
        //       reversedResult.add(result.get(i));
        //    }
        Collections.reverse(result);
        return result;
    }
}
