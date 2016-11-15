/**
 * http://www.cnblogs.com/springfor/p/3879680.html
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/BinaryTreeLevelOrderTraversal.java
 * This solution is very similar to Binary Tree Level Order Traversal.
*/
// Solution 1: With two queues
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
    public static int minDepth(TreeNode root) {
        if(root == null) {
            return 0;
        }
        
        Queue<TreeNode> q1 = new LinkedList<TreeNode>();
        Queue<TreeNode> q2 = new LinkedList<TreeNode>();
        
        // The minimum depth is the number of nodes along the shortest path from the 
        // root node down to the nearest leaf node. "depth" used for record levels(depth)
        // we have encounter. Initially set up as 1, because "root" already exist.
        int depth = 1;
        q1.add(root);
        
        while(!q1.isEmpty()) {
           // Use for loop to process eech current level node
           int size = q1.size();
           for(int i = 0; i < size; i++) {
               // Poll out one node
               TreeNode x = q1.poll();
               
               // Check it's left child node, if exist, put into next level queue
               if(x.left != null) {
                  q2.add(x.left);
               }
           
               // Check it's right child node, if exist, put into next level queue
               if(x.right != null) {
                  q2.add(x.right);
               }
               
               // Return case is encountering leaf node, if yes, then return current level depth
               if(x.left == null && x.right == null) {
                  return depth;
               }
           }
           
           // After each level for loop, if not find leaf node to return, then depth + 1 and move
           // to next level
           depth++;
           q1 = q2;
           q2 = new LinkedList<TreeNode>();
        }
        
        return depth;
    }
}

// Solution 2: With one queue
// Refer to 
// http://www.cnblogs.com/springfor/p/3879680.html
public class Solution {
  public static int minDepth(TreeNode root){
    if(root == null) {
       return 0;
    }
    
    int depth = 1;
    int currNum = 1;
    int nextNum = 0;
    Queue<TreeNode> q = new LinkedList<TreeNode>();
    q.add(root);
    while(!q.isEmpty()) {
       TreeNode x = q.poll();
       currNum--;
        
       if(x.left == null && x.right == null) {
          return depth;
       } 
       
       if(x.left != null) {
          q.add(x.left);
          nextNum++; 
       } 
        
       if(x.right != null) {
          q.add(x.right);
          nextNum++; 
       } 
       
       if(currNum == 0) {
          currNum = nextNum;
          nextNum = 0;
          depth++; 
       } 
    }  
    
    return depth;  
  }
}


