/**
 * Refer to
 * http://www.lintcode.com/en/problem/binary-tree-serialization/
 * Design an algorithm and write code to serialize and deserialize a binary tree. Writing the tree to 
 * a file is called 'serialization' and reading back from the file to reconstruct the exact same 
 * binary tree is 'deserialization'.
 * NoticeThere is no limit of how you deserialize or serialize a binary tree, LintCode will take your 
 * output of serialize as the input of deserialize, it won't check the result of serialize.
 * Have you met this question in a real interview? Yes
	Example
	An example of testdata: Binary tree {3,9,20,#,#,15,7}, denote the following structure:
	
	  3
	 / \
	9  20
	  /  \
	 15   7
	Our data serialization use bfs traversal. 
 * This is just for when you got wrong answer and want to debug the input.
 * You can use other method to do serializaiton and deserialization.
 *
 * Solution
 * https://discuss.leetcode.com/topic/28029/easy-to-understand-java-solution?page=1
 * https://www.jiuzhang.com/solution/binary-tree-serialization/
*/ 

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
class Solution {
    /**
     * This method will be invoked first, you should design your own algorithm 
     * to serialize a binary tree which denote by a root node to a string which
     * can be easily deserialized by your own "deserialize" method later.
     */
    private static final String spliter = ",";
    private static final String NN = "#";
    public String serialize(TreeNode root) {
        if(root == null) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        buildString(root, sb);
        // Add "{}" at start and end position
        sb.insert(0, "{");
        sb.insert(sb.length(), "}");
        //sb.append("}");
        return sb.toString();
    }
    
    // We don't include "{}" during the traverse
    public void buildString(TreeNode root, StringBuilder sb) {
        if(root == null) {
            sb.append(NN).append(spliter);
        } else {
            // Preorder traverse
            sb.append(root.val).append(spliter);
            buildString(root.left, sb);
            buildString(root.right, sb);
        }
    }
    
    
    
    /**
     * This method will be invoked second, the argument data is what exactly
     * you serialized at method "serialize", that means the data is not given by
     * system, it's given by your own serialize method. So the format of data is
     * designed by yourself, and deserialize it here as you serialize it in 
     * "serialize" method.
     */
    public TreeNode deserialize(String data) {
        if(data.equals("{}")) {
            return null;
        }
        Queue<String> nodes = new LinkedList<>();
        String tmp = data.substring(1, data.length() - 1);
        nodes.addAll(Arrays.asList(tmp.split(spliter)));
        return buildTree(nodes);
    }
    
    
    public TreeNode buildTree(Queue<String> nodes) {
        String val = nodes.poll();
        if(val.equals(NN)) {
            return null;
        } else {
            TreeNode root = new TreeNode(Integer.valueOf(val));
            root.left = buildTree(nodes);
            root.right = buildTree(nodes);
            return root;
        }
    }
}

