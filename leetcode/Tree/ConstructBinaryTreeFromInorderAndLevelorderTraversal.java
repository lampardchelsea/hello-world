/**
 Refer to
 https://javabypatel.blogspot.com/2015/08/construct-binary-tree-from-inorder-and-level-order-traversals.html
Let us first understand what we want to achieve? what is the input and what will be the expected output?
Question:
Two traversals are given as input,
int[] inOrder =    { 4, 2, 6, 5, 7, 1, 3 };
int[] levelOrder = { 1, 2, 3, 4, 5, 6, 7 };
By using above 2 given In order and Level Order traversal, construct Binary Tree like shown below,
                1
               / \
              2   3
            /  \
           4    5
               /  \
              6    7 
*/

// https://javabypatel.blogspot.com/2015/08/construct-binary-tree-from-inorder-and-level-order-traversals.html
/**
Algorithm
We just saw that first element of Level order traversal is root node.
Step 1:
Read the first element from Level order traversal which is root node.
Now, we come to know which is root node.(in our case it is 1)
Step 2: 
Search the same element (element 1) in In-order traversal, 
when the element is found in In-order traversal, then we can very well say that all the elements present 
before the node found are Left children/sub-tree and all the elements present after the node found are 
Right children/sub-tree of node found. (as in In-order traversal Left child's are read first and then 
root node and then right nodes.)
In our example,  
(4, 2, 6, 5, 7)  will be part of Left Sub-tree of Node 1.
(3) will be part of Right Sub-tree of Node 1.
Now we got the fresh inOrder array (4, 2, 6, 5, 7)  and (3)
Repeat the  Step 1 and Step 2 for new inOrder arrays.
So, keeping the above rule in mind, 
*/


// Solution 1: Recursive build map with inorder
// Refer to
// https://hjweds.gitbooks.io/leetcode/reconstruct-binary-tree-with-levelorder-and-inorder.html
/**
 Solution: level[]第一个为root, 找到该数值在in[]的位置（利用hashmap），再把level[]剩下的拆分为左半部和右半部两个list(根据index大小)
*/
class Solution {
    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) {
            this.val = val;
        }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode buildTree(int[] inorder, int[] levelorder) {
        Map < Integer, Integer > map = new HashMap < Integer, Integer > ();
        /**
         Why have to use list ?
         Because in levelorder, we cannot split remained array by continuous indexed elements, that kind of solution able
         to implement on Construct Binary Tree From Preorder / Postorder And Inorder Traversal, but not here.
         E.g
         int[] inOrder =    { 4, 2, 6, 5, 7, 1, 3 };
         int[] levelOrder = { 1, 2, 3, 4, 5, 6, 7 };
         Round 1: 1 is the root, 3 should be right child, {4, 2, 6, 5, 7} should be left child
         Round 2: in levelOrder besides root 1, we cannnot split remained array {2, 3, 4, 5, 6, 7} by continuous indexes
                  since 3 is in middle of {2, 3, 4, 5, 6, 7} in level order traverse
         The solution is separately build 2 list to store left / right child based on index mapping relation of root index in inorder array
        */
        List < Integer > levelorderlist = new ArrayList < Integer > ();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
            levelorderlist.add(levelorder[i]);
        }
        return helper(inorder, 0, inorder.length - 1, levelorderlist, map);
    }

    private TreeNode helper(int[] inorder, int inStart, int inEnd, List < Integer > levelorderlist, Map < Integer, Integer > map) {
        if (inStart > inEnd) {
            return null;
        }
        TreeNode root = new TreeNode(levelorderlist.get(0));
        int rootIndexInInorder = map.get(root.val);
        List < Integer > leftList = new ArrayList < Integer > ();
        List < Integer > rightList = new ArrayList < Integer > ();
        for (int i = 1; i < levelorderlist.size(); i++) {
            int num = levelorderlist.get(i);
            int pos = map.get(num);
            if (pos < rootIndexInInorder) {
                leftList.add(num);
            } else {
                rightList.add(num);
            }
        }
        root.left = helper(inorder, inStart, rootIndexInInorder - 1, leftList, map);
        root.right = helper(inorder, rootIndexInInorder + 1, inEnd, rightList, map);
        return root;
    }
}

// Solution 2: 
// Refer to
// https://www.techiedelight.com/construct-binary-tree-from-inorder-level-order-traversals/
class Solution {
    // Recursive function to construct a binary tree from in-order and level-order traversals
    public static Node buildTree(int[] inorder, int start, int end, Map < Integer, Integer > map) {
        // base case
        if (start > end) {
            return null;
        }
        // find the index of root node in inorder[] to determine the
        // boundary of left and right subtree
        int index = start;
        for (int j = start + 1; j <= end; j++) {
            // find node with minimum index in level order traversal
            // That would be the root node of inorder[start, end] ???
            /**
             This should be the most tricky part of solution:
             Based on below test case
             int[] inorder = { 4, 2, 5, 1, 6, 3, 7 };
        	    int[] level =   { 1, 2, 3, 4, 5, 6, 7 };
                          1
                        /   \
                       2     3
                      / \   / \
                     4   5 6   7  
             Looks like try to find pair [1 / 4] and the discipline is [1 / 4] is
             the first pair which value in inorder array < value in levelorder array ???
            */
            if (map.get(inorder[j]) < map.get(inorder[index])) {
                index = j;
            }
        }

        // construct the root node
        Node root = new Node(inorder[index]);
        // recursively construct the left subtree
        root.left = buildTree(inorder, start, index - 1, map);
        // recursively construct the right subtree
        root.right = buildTree(inorder, index + 1, end, map);
        // return root node
        return root;
    }

    // Construct a binary tree from in-order and level-order traversals
    public static Node buildTree(int[] in , int[] level) {
        // create a map to efficiently find index of an element in
        // level-order sequence
        Map < Integer, Integer > map = new HashMap < > ();
        for (int i = 0; i < in .length; i++) {
            map.put(level[i], i);
        }
        // Construct the tree and return it
        return buildTree( in , 0, in .length - 1, map);
    }

    public static void main(String[] args) {
        int[] inorder = { 4, 2, 5, 1, 6, 3, 7 };
        int[] level	= { 1, 2, 3, 4, 5, 6, 7 };
        Node root = buildTree(inorder, level);
    }
}

