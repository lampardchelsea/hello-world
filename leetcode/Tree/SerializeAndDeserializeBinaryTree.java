import java.util.Arrays;
import java.util.Queue;

/**
 * Refer to
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/description/
 * Serialization is the process of converting a data structure or object into a sequence of bits 
 * so that it can be stored in a file or memory buffer, or transmitted across a network connection 
 * link to be reconstructed later in the same or another computer environment.
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on 
 * how your serialization/deserialization algorithm should work. You just need to ensure that a 
 * binary tree can be serialized to a string and this string can be deserialized to the original 
 * tree structure.

	For example, you may serialize the following tree
	
	    1
	   / \
	  2   3
	     / \
	    4   5
	as "[1,2,3,null,null,4,5]", 

 * just the same as how LeetCode OJ serializes a binary tree. You do not necessarily need to follow 
 * this format, so please be creative and come up with different approaches yourself.
 * Note: Do not use class member/global/static variables to store states. Your serialize and 
 * deserialize algorithms should be stateless.
 * 
 * Solution
 * https://discuss.leetcode.com/topic/28029/easy-to-understand-java-solution?page=1
 * 
 * https://www.jiuzhang.com/solution/binary-tree-serialization/
 *
 */
public class SerializeAndDeserializeBinaryTree {
	private class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		TreeNode(int x) { val = x; }
    }
	
    private static final String spliter = ",";
    private static final String NN = "#";
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        return sb.toString();
    }

    private void buildString(TreeNode root, StringBuilder sb) {
        if(root == null) {
            sb.append(NN).append(spliter);
        } else {
            sb.append(root.val).append(spliter);
            buildString(root.left, sb);
            buildString(root.right, sb);
        }
    }
    
    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<String>();
        queue.addAll(Arrays.asList(data.split(spliter)));
        return buildTree(queue);
    }
    
    private TreeNode buildTree(Queue<String> queue) {
        String val = queue.poll();
        if(val.equals(NN)) {
            return null;
        } else {
            TreeNode root = new TreeNode(Integer.valueOf(val));
            root.left = buildTree(queue);
            root.right = buildTree(queue);
            return root;
        }
    }
}

