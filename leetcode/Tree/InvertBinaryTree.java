// Solution
// https://leetcode.com/articles/invert-binary-tree/
/**
 *  * Approach #1 (Recursive) [Accepted]
 * This is a classic tree problem that is best-suited for a recursive approach.
 * Algorithm
 * The inverse of an empty tree is the empty tree. The inverse of a tree with root r, and subtrees right
 * and left, is a tree with root r, whose right subtree is the inverse of left, and whose left subtree 
 * is the inverse of right

		public TreeNode invertTree(TreeNode root) {
		    if (root == null) {
		        return null;
		    }
		    TreeNode right = invertTree(root.right);
		    TreeNode left = invertTree(root.left);
		    root.left = right;
		    root.right = left;
		    return root;
		}

  * Complexity Analysis
  * Since each node in the tree is visited only once, the time complexity is O(n), where n is the number of 
  * nodes in the tree. We cannot do better than that, since at the very least we have to visit each node 
  * to invert it.
  * Because of recursion, O(h) function calls will be placed on the stack in the worst case, where h is 
  * the height of the tree. Because hEO(n), the space complexity is O(n).
  * 
  * Approach #2 (Iterative) [Accepted]
  * Alternatively, we can solve the problem iteratively, in a manner similar to breadth-first search.
  * Algorithm
  * The idea is that we need to swap the left and right child of all nodes in the tree. So we create a queue 
  * to store nodes whose left and right child have not been swapped yet. Initially, only the root is in the 
  * queue. As long as the queue is not empty, remove the next node from the queue, swap its children, 
  * and add the children to the queue. Null nodes are not added to the queue. Eventually, the queue will 
  * be empty and all the children swapped, and we return the original root.

		public TreeNode invertTree(TreeNode root) {
		    if (root == null) return null;
		    Queue<TreeNode> queue = new LinkedList<TreeNode>();
		    queue.add(root);
		    while (!queue.isEmpty()) {
		        TreeNode current = queue.poll();
		        TreeNode temp = current.left;
		        current.left = current.right;
		        current.right = temp;
		        if (current.left != null) queue.add(current.left);
		        if (current.right != null) queue.add(current.right);
		    }
		    return root;
		}

  * Complexity Analysis
  * Since each node in the tree is visited / added to the queue only once, the time complexity is O(n), where 
  * n is the number of nodes in the tree.
  * Space complexity is O(n), since in the worst case, the queue will contain all nodes in one level of the 
  * binary tree. For a full binary tree, the leaf level has ceiling (n/2) leaves.
*/




/**
 * Invert a binary tree.

     4
   /   \
  2     7
 / \   / \
1   3 6   9
to
     4
   /   \
  7     2
 / \   / \
9   6 3   1

 * Trivia:
 * This problem was inspired by this original tweet by Max Howell:
 * Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so fuck off.
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

// Solution 1: DFS Buttom-Top traverse invert
/**
		       1                  1                  1                 1
	             /   \              /   \              /   \             /   \
	  	    2     3            3     2            3     2           3     2
	 	   / \   /    -->     /     / \   -->      \   / \   -->     \   / \ 
		  6   5 9            9     6   5            9 5   6           9  5  6
	 	 /                        /                      /                   \
		8                        8                      8                     8
*/
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        
	// First recursively go to the leave nodes of the tree
        if(root.left != null) {
            invertTree(root.left);
        }
        
        if(root.right != null) {
            invertTree(root.right);
        }
        
	// Then invert the left and right subtree (start with leaves 
	// right & left as null, after that will go up from bottom
	// level by level to invert left and right subtree)
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        
        return root;
    }
}

// Solution 2: DFS Top-Bottom traverse invert
/**
		       1                  1                  1                 1
	             /   \              /   \              /   \             /   \
	  	    2     3            2     3            2     3           3     2
	 	   / \   /    -->     / \    /   -->     / \      \   -->    \   / \ 
		  6   5 9            6   5  9           5   6      9          9 5   6   
	 	 /                    \                      \                       \
		8                      8                      8                       8
*/
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        if(root == null) {
            return null;
        }
        
	// First we invert left and right subtree (start with root's left
	// and right node, after that will go down from root to invert
	// left and right subtree)
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        
	// Then recursively go to the leave nodes of the tree
        if(root.left != null) {
            invertTree(root.left);
        }
        
        if(root.right != null) {
            invertTree(root.right);
        }
        
        return root;
    }
}

// Solution 3: BFS first enqueue then exchange
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        if(root == null) {
            return null;
        }
        
        queue.add(root);
        
        while(!queue.isEmpty()) {
            TreeNode x = queue.poll();
            
            if(x.left != null) {
                queue.add(x.left);
            }
            if(x.right != null) {
                queue.add(x.right);
            }
            
            TreeNode tmp = x.left;
            x.left = x.right;
            x.right = tmp;
        }
        
        return root;
    }
}

// Solution 4: BFS first exchange then enqueue
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        if(root == null) {
            return null;
        }
        
        queue.add(root);
        
        while(!queue.isEmpty()) {
            TreeNode x = queue.poll();
            
            TreeNode tmp = x.left;
            x.left = x.right;
            x.right = tmp;
            
            if(x.left != null) {
                queue.add(x.left);
            }
            if(x.right != null) {
                queue.add(x.right);
            }
        }
        
        return root;
    }
}
