/**
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
 * return its level order traversal as:
[
  [3],
  [9,20],
  [15,7]
]
*/

// Solution 1: BFS with two queues
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 * 
 * The major design of this part is use different queue to record current level
 * nodes and next level nodes, and after each level inserting into level arraylist
 * which used for print out, will replace the current level queue with next level
 * queue, and recreate a new next level queue.
 * 
 * Note: There is a very similar way problem to use BST and queue
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Queue/MinimumDepthofBinaryTreeQueueSolution.java
 * 
 */
public class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();
        
        if(root == null) {
            return result;
        }
        
        q1.add(root);
        
        while(!q1.isEmpty()) {
            // Note: Everytime enter in while loop, which means
            // everytime when we start scan a new level, only use 
            // a new arraylist to record nodes value of this level
            List<Integer> level = new ArrayList<Integer>();
            int size = q1.size();
            for(int i = 0; i < size; i++) {
                TreeNode x = q1.poll();
                level.add(x.val);
                if(x.left != null) {
                    q2.add(x.left);
                }
                if(x.right != null) {
                    q2.add(x.right);
                }
            }

            result.add(level);
          
            // Replace empty q1 with next level nodes recorded into q2
            q1 = q2;
            q2 = new LinkedList<TreeNode>();
        }
        
        return result;
    }
}


// Soltuon 2: BFS with one queue
// Refer to
// https://discuss.leetcode.com/topic/28535/java-clean-and-concise-using-a-queue/2
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 *
 * This solution is more simple, actually it is not necessary to use two
 * queue to record current level nodes and next level nodes, because every
 * time when enter while loop we will use a new arraylist to record new
 * nodes value, no need to consider these nodes come from current level
 * queue or next level queue.
 */
public class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        if(root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            // Note: Everytime enter in while loop, which means
            // everytime when we start scan a new level, only use 
            // a new arraylist to record nodes value of this level
            List<Integer> curr = new ArrayList<Integer>();
            for(int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                curr.add(node.val);
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(curr);
        }
        return result;
    }
}


// Solution 3: Traverse (One kind of DFS)
// Refer to
// https://discuss.leetcode.com/topic/7332/java-solution-using-dfs
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
    List<List<Integer>> result = new ArrayList<List<Integer>>();
    public List<List<Integer>> levelOrder(TreeNode root) {
        helper(result, root, 0);
        return result;
    }
    
    private void helper(List<List<Integer>> result, TreeNode root, int height) {
        if(root == null) {
            return;
        }
        if(height >= result.size()) {
            result.add(new LinkedList<Integer>());
        }
        result.get(height).add(root.val);
        helper(result, root.left, height + 1);
        helper(result, root.right, height + 1);
    }
}
