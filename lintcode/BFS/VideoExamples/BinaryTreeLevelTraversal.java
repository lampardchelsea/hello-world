// Solution 1: BFS
// Refer to
// https://discuss.leetcode.com/topic/28535/java-clean-and-concise-using-a-queue/2
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Level order a list of lists of integer
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if(root == null) {
            return result;
        }
        queue.add(root);
        while(!queue.isEmpty()) {
            int size = queue.size();
            ArrayList<Integer> list = new ArrayList<Integer>();
            for(int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                if(node.left != null) {
                    queue.offer(node.left);
                }
                if(node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(list);
        }
        return result;
    }
}


// Solution 2: DFS (Purely traverse)
// Refer to
// https://discuss.leetcode.com/topic/7332/java-solution-using-dfs
/**
 * Definition of TreeNode:
 * public class TreeNode {
 *     public int val;
 *     public TreeNode left, right;
 *     public TreeNode(int val) {
 *         this.val = val;
 *         this.left = this.right = null;
 *     }
 * }
 */
public class Solution {
    /**
     * @param root: The root of binary tree.
     * @return: Level order a list of lists of integer
     */
    ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        helper(result, root, 0);
        return result;
    }
    
    private void helper(ArrayList<ArrayList<Integer>> result, TreeNode root, int height) {
        if(root == null) {
            return;
        }
        if(height >= result.size()) {
            result.add(new ArrayList<Integer>());
        }
        result.get(height).add(root.val);
        helper(result, root.left, height + 1);
        helper(result, root.right, height + 1);
    }
}

