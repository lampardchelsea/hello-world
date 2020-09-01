/**
 Refer to
 https://leetcode.com/problems/all-possible-full-binary-trees/
 A full binary tree is a binary tree where each node has exactly 0 or 2 children.
 Return a list of all possible full binary trees with N nodes.  Each element of the answer is the root node of one possible tree.
 Each node of each tree in the answer must have node.val = 0.
 You may return the final list of trees in any order.
 
 Example 1:
 Input: 7
 Output: [[0,0,0,null,null,0,0,null,null,0,0],[0,0,0,null,null,0,0,0,0],[0,0,0,0,0,0,0],[0,0,0,0,0,null,null,null,null,0,0],[0,0,0,0,0,null,null,0,0]]
 Explanation:
          0                 
        0   0  
          0   0
            0   0
            
          0
        0   0
          0   0
        0   0
        
          0
        0   0
      0  0 0  0
         
          0
        0   0
      0   0
        0   0
        
          0
        0   0
      0   0
    0   0
*/

// Solution 1: Recursive: build all possible FBT of leftSubTree and rightSubTree with number n
// Refer to
// https://leetcode.com/problems/all-possible-full-binary-trees/discuss/216853/Java%3A-Easy-with-Examples
/**
 class Solution {
    public List<TreeNode> allPossibleFBT(int N) {
        // Recursive: build all possible FBT of leftSubTree and rightSubTree with number n
        if(N <= 0 || N % 2 == 0) return new ArrayList<>();
        
        //1. if N = 3 , the number of nodes combination are as follows
        //      left    root    right
        //       1       1        1 
        //--------------N = 3, res = 1----------
        
        //2. if N = 5 , the number of nodes combination are as follows
        //      left    root    right
        //       1       1        3 (recursion)
        //       3       1        1 
        //  --------------N = 5, res = 1 + 1 = 2----------
        
        //3. if N = 7 , the number of nodes combination are as follows
        //      left    root    right
        //       1       1        5 (recursion)
        //       3       1        3 
        //       5       1        1
        //  --------------N = 7, res = 2 + 1 + 2 = 5----------
        
        //4. in order to make full binary tree, the node number must increase by 2
        List<TreeNode> res = new ArrayList<>();
        if(N == 1) {
            res.add(new TreeNode(0));
            return res;
        }
        for(int i = 1; i < N; i += 2) {
            List<TreeNode> leftSubTrees = allPossibleFBT(i);
            List<TreeNode> rightSubTrees = allPossibleFBT(N - i - 1);
            for(TreeNode l : leftSubTrees) {
                for(TreeNode r : rightSubTrees) {
                    TreeNode root = new TreeNode(0);
                    root.left = l;
                    root.right = r;
                    res.add(root);
                }
            }
        }
        return res;
    }
}
*/
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/UniqueBinarySearchTreesII.java
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DFS_BackTracking/DifferentWaysToAddParentheses.java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<TreeNode> allPossibleFBT(int N) {
        return helper(N);        
    }
    
    private List<TreeNode> helper(int N) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        if(N % 2 == 0) {
            return result;
        }
        if(N == 1) {
            result.add(new TreeNode(0));
            return result;
        }
        for(int i = 1; i < N; i += 2) {
            List<TreeNode> left = helper(i);
            // Additional -1 for root
            List<TreeNode> right = helper(N - i - 1);
            for(TreeNode l : left) {
                for(TreeNode r : right) {
                    TreeNode root = new TreeNode(0);
                    root.left = l;
                    root.right = r;
                    result.add(root);
                }
            }
        }
        return result;
    }
}

// Solution 2: Memoization



