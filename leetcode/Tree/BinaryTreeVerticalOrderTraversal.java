import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Refer to
 * http://www.cnblogs.com/grandyang/p/5278930.html
 * Given a binary tree, return the vertical order traversal of its nodes' values. 
 * (ie, from top to bottom, column by column).

   If two nodes are in the same row and column, the order should be from left to right.
	
	Examples:
	Given binary tree [3,9,20,null,null,15,7],
	
	    3
	   / \
	  9  20
	    /  \
	   15   7
	 
	
	return its vertical order traversal as:
	
	[
	  [9],
	  [3,15],
	  [20],
	  [7]
	]
	 
	
	Given binary tree [3,9,20,4,5,2,7],
	
	    _3_
	   /   \
	  9    20
	 / \   / \
	4   5 2   7
	 
	
	return its vertical order traversal as:
	
	[
	  [4],
	  [9],
	  [3,5,2],
	  [20],
	  [7]
	]
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/31954/5ms-java-clean-solution
 * The following solution takes 5ms.
	BFS, put node, col into queue at the same time
	Every left child access col - 1 while right child col + 1
	This maps node into different col buckets
	Get col boundary min and max on the fly
	Retrieve result from cols
	Note that TreeMap version takes 9ms
 * 
 * More elegant way
 *  public List<List<Integer>> verticalOrder(TreeNode root) {
	    List<List<Integer>> res = new ArrayList<>();
	    if (root == null) {
	        return res;
	    }
	    
	    Map<Integer, ArrayList<Integer>> map = new HashMap<>();
	    Queue<TreeNode> q = new LinkedList<>();
	    Queue<Integer> cols = new LinkedList<>();
	
	    q.add(root); 
	    cols.add(0);
	
	    int min = 0;
	    int max = 0;
	    
	    while (!q.isEmpty()) {
	        TreeNode node = q.poll();
	        int col = cols.poll();
	        
	        if (!map.containsKey(col)) {
	            map.put(col, new ArrayList<Integer>());
	        }
	        map.get(col).add(node.val);
	
	        if (node.left != null) {
	            q.add(node.left); 
	            cols.add(col - 1);
	            min = Math.min(min, col - 1);
	        }
	        
	        if (node.right != null) {
	            q.add(node.right);
	            cols.add(col + 1);
	            max = Math.max(max, col + 1);
	        }
	    }
	
	    for (int i = min; i <= max; i++) {
	        res.add(map.get(i));
	    }
	
	    return res;
	}
  
  or 
  
  public List<List<Integer>> verticalOrder(TreeNode root) {
      List<List<Integer>> cols = new ArrayList<>();
      if (root == null) {
          return cols;
      }

      int[] range = new int[] {0, 0};
      getRange(root, range, 0);

      for (int i = range[0]; i <= range[1]; i++) {
          cols.add(new ArrayList<Integer>());
      }

      Queue<TreeNode> queue = new LinkedList<>();
      Queue<Integer> colQueue = new LinkedList<>();

      queue.add(root);
      colQueue.add(-range[0]);

      while (!queue.isEmpty()) {
          TreeNode node = queue.poll();
          int col = colQueue.poll();

          cols.get(col).add(node.val);

          if (node.left != null) {
              queue.add(node.left);   
              colQueue.add(col - 1);
          } 
          if (node.right != null) {
              queue.add(node.right);
              colQueue.add(col + 1);
          }
      }
 * 
 * 
 * http://www.cnblogs.com/grandyang/p/5278930.html
 * 这道题让我们竖直遍历二叉树，并把每一列存入一个二维数组，我们看题目中给的第一个例子，3和15属于同一列，
 * 3在前，第二个例子中，3,5,2在同一列，3在前，5和2紧随其后，那么我们隐约的可以感觉到好像是一种层序遍历
 * 的前后顺序，那么我们如何来确定列的顺序呢，我们可以把根节点给个序号0，然后开始层序遍历，凡是左子节点
 * 则序号减1，右子节点序号加1，这样我们可以通过序号来把相同列的节点值放到一起，我们用一个map来建立序号
 * 和其对应的节点值的映射，用map的另一个好处是其自动排序功能可以让我们的列从左到右，由于层序遍历需要用
 * 到queue，我们此时queue里不能只存节点，而是要存序号和节点组成的pair，这样我们每次取出就可以操作序号，
 * 而且排入队中的节点也赋上其正确的序号
 */
public class BinaryTreeVerticalOrderTraversal {
	private class TreeNode {
		TreeNode left;
		TreeNode right;
		int val;
		public TreeNode(int x) {
			this.val = x;
		}
	}
	
	public List<List<Integer>> verticalOrder(TreeNode root) {
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		if(root == null) {
			return result;
		}
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		Queue<Pair> queue = new LinkedList<Pair>();
		Pair rootPair = new Pair(root, 0);
		queue.offer(rootPair);
		while(!queue.isEmpty()) {
			// Level traverse
			int size = queue.size();
			for(int i = 0; i < size; i++) {
				Pair pair = queue.poll();
				TreeNode node = pair.node;
				int index = pair.colIndex;
				if(map.get(index) == null) {
					List<Integer> list = new ArrayList<Integer>();
					list.add(node.val);
					map.put(index, list);
				} else {
					map.get(index).add(node.val);
				}
				TreeNode leftNode = node.left;
				TreeNode rightNode = node.right;
				if(leftNode != null) {
					int leftIndex = pair.colIndex - 1;
					Pair leftPair = new Pair(leftNode, leftIndex);
					queue.offer(leftPair);
				}
				if(rightNode != null) {
					int rightIndex = pair.colIndex + 1;
					Pair rightPair = new Pair(rightNode, rightIndex);
					queue.offer(rightPair);
				}
			}
		}
		Object[] keys = map.keySet().toArray();
		Arrays.sort(keys);
		for(Object key : keys) {
			int sortedKey = (Integer)key;
			result.add(map.get(sortedKey));
		}
		return result;
	}
	
	private class Pair {
		TreeNode node;
		int colIndex;
		public Pair(TreeNode node, int colIndex) {
			this.node = node;
			this.colIndex = colIndex;
		}
	}
	
	public static void main(String[] args) {
		BinaryTreeVerticalOrderTraversal b = new BinaryTreeVerticalOrderTraversal();
		
		/**
		 * E.g Test case 1
			    3
			   / \
			  9  20
			    /  \
			   15   7
		 */
//		TreeNode three = b.new TreeNode(3); 
//		TreeNode nine = b.new TreeNode(9);
//		TreeNode twenty = b.new TreeNode(20);
//		TreeNode fifteen = b.new TreeNode(15);
//		TreeNode seven = b.new TreeNode(7);
//		three.left = nine;
//		three.right = twenty;
//		twenty.left = fifteen;
//		twenty.right = seven;
		
		/**
		 * E.g Test case 2
			    	    _3_
					   /   \
					  9    20
					 / \   / \
					4   5 2   7
		 */
		TreeNode three = b.new TreeNode(3); 
		TreeNode nine = b.new TreeNode(9);
		TreeNode twenty = b.new TreeNode(20);
		TreeNode four = b.new TreeNode(4);
		TreeNode five = b.new TreeNode(5);
		TreeNode two = b.new TreeNode(2);
		TreeNode seven = b.new TreeNode(7);		
		three.left = nine;
		three.right = twenty;
		nine.left = four;
		nine.right = five;
		twenty.left = two;
		twenty.right = seven;
		List<List<Integer>> result = b.verticalOrder(three);
		for(List<Integer> list: result) {
			System.out.println(" ");
			for(Integer i : list) {
				System.out.print(i + " ");
			}
		}
	}

}
