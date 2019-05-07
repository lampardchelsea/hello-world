/**
 Refer to
 https://www.geeksforgeeks.org/print-nodes-distance-k-leaf-node/
 Given a Binary Tree and a positive integer k, print all nodes that are distance k from a leaf node.

Here the meaning of distance is different from previous post. Here k distance from a leaf means 
k levels higher than a leaf node. For example if k is more than height of Binary Tree, then nothing 
should be printed. Expected time complexity is O(n) where n is the number nodes in the given Binary Tree.
*/
// Solution 1:
// Refer to
// https://www.geeksforgeeks.org/print-nodes-distance-k-leaf-node/
public class Solution {
	public static void main(String[] args) throws Exception {
		Post p = new Post();
		TreeNode root = p.new TreeNode(1);
		root.left = p.new TreeNode(2);
		root.right = p.new TreeNode(3);
		root.left.left = p.new TreeNode(4);
		root.left.right = p.new TreeNode(5);
		root.right.left = p.new TreeNode(6);
		root.right.right = p.new TreeNode(7);
		root.right.left.right = p.new TreeNode(8);
		List<Integer> result = kDistantFromLeaf(root, 1);
		for(int a : result) {
			System.out.print(a + ",");
		}
	}
	
	static List<Integer> result = new ArrayList<Integer>();
	public static List<Integer> kDistantFromLeaf(TreeNode root, int k) {
	    if(root == null || k <= 0) {
		   return result;
		}
	    int height = findHeight(root);
	    int[] path = new int[height];
		boolean[] visited = new boolean[height];
		helper(root, path, visited, 0, k);
		return result;
	}

	// This function prints all nodes that are distance k from a leaf node 
	// path[] --> Store ancestors of a node 
	// visited[] --> Stores true if a node is printed as output. A node may 
	// be k distance away from many leaves, we want to print it once
	private static void helper(TreeNode root, int[] path, boolean[] visited, int pathLen, int k) {
	    if(root == null) {
		    return;
		}
		// append node on path
		path[pathLen] = root.val;
		/**
		   when add a new node don't forget set its initial status as haven't been printed yet
		   we repeatedly leverage the same boolean array (visited), but since DFS will traverse
		   from root to leave on each path recursively, when we finish a path and move onto a
		   new path, then we will encounter new nodes, no need to create new visited array for
		   this new path, just use the original one and reset the value at corresponding position
		   to false (especially for the position set to true before on already finished path)
		   e.g
		               1
				  2         3
			    4   5     6   7
                            8
			====================================================================
			First path 1 -> 2 -> 4
			1,2,4,0
			false,false,false,false
			1,2,4,0
			false,true,false,false => print 2 and visited[3 - 1 - 1] set to true
			====================================================================
			Second path 1 -> 2 -> 5 but since 2 already printed ignore
			====================================================================
			Third path 1 -> 3 -> 6 -> 8
			1,3,6,8
			false,false,false,false => set all nodes as none visited on third path when add them
			1,3,6,8
			false,false,true,false => print 6 and visited[4 - 1 - 1] set to true
			====================================================================
			Four path 1 -> 3 -> 7 (additional 8 is not removed since its previous 
			                   node on path but current length of path is 1 short)
			1,3,7,8
			false,false,false,false
			1,3,7,8
			false,true,false,false => print 3 and visited[3 - 1 - 1] set to true
			====================================================================
			Result as
			2,6,3
			====================================================================
		*/
		visited[pathLen] = false; 
		pathLen++;
		// it's a leaf, so print the ancestor at distance k only 
		// if the ancestor is not already printed (e.g two child
		// nodes backtrack to the same parent)
		if(root.left == null && root.right == null && pathLen - k - 1 >= 0 && !visited[pathLen - k - 1]) {
		    for(int i = 0; i < path.length; i++) {
		    	System.out.print(path[i] + ",");
		    }
		    System.out.println();
		    for(int i = 0; i < visited.length; i++) {
		    	System.out.print(visited[i] + ",");
		    }
			result.add(path[pathLen - k - 1]);
			visited[pathLen - k - 1] = true;
		    System.out.println();
		    for(int i = 0; i < visited.length; i++) {
		    	System.out.print(visited[i] + ",");
		    }
		    System.out.println();
			return;
		}
		helper(root.left, path, visited, pathLen, k);
		helper(root.right, path, visited, pathLen, k);
	}

	private static int findHeight(TreeNode root) {
	    if(root == null) {
		    return 0;
		}
		return 1 + Math.max(findHeight(root.left), findHeight(root.right));
	}
	
	class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		public TreeNode(int val) {
			this.val = val;
			this.left = null;
			this.right = null;
		}
	}
}
