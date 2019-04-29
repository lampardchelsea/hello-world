// Refer to
// https://discuss.leetcode.com/topic/3695/my-accepted-java-solution/8
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/array/ConstructBinaryTreeFromInorderAndPostorderTraversal.java
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
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTreeHelper(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1, map);
    }
    
    // inorder: left -> root -> right
    // preorder: root -> left -> right
    // E.g 
    // preorder = {1, 2, 4, 8, 5, 3, 6, 7}
    // inorder = {4, 8, 2, 5, 1, 6, 3, 7}
    public TreeNode buildTreeHelper(int[] preorder, int preorderStart, int preorderEnd, int[] inorder, int inorderStart, int inorderEnd, Map<Integer, Integer> map) {
        if(preorderStart > preorderEnd || inorderStart > inorderEnd) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[preorderStart]);
        int rootIndexOnInorder = map.get(preorder[preorderStart]);
        TreeNode leftChild = buildTreeHelper(preorder, preorderStart + 1, preorderStart + 1 + (rootIndexOnInorder - inorderStart) - 1, inorder, inorderStart, rootIndexOnInorder - 1, map);
        TreeNode rightChild = buildTreeHelper(preorder, preorderStart + 1 + (rootIndexOnInorder - inorderStart) - 1 + 1, preorderEnd, inorder, rootIndexOnInorder + 1, inorderEnd, map);
        root.left = leftChild;
        root.right = rightChild;
        return root;
    }
}

// More detail analysis:
// Refer to
// https://www.cnblogs.com/grandyang/p/4296500.html
/**
由于先序的顺序的第一个肯定是根，所以原二叉树的根节点可以知道，题目中给了一个很关键的条件就是
树中没有相同元素，有了这个条件我们就可以在中序遍历中也定位出根节点的位置，并以根节点的位置将
中序遍历拆分为左右两个部分，分别对其递归调用原函数

我们下面来看一个例子, 某一二叉树的中序和后序遍历分别为：

Preorder:　  　5　　4　　11　　8　　13　　9

Inorder:　　 　11　　4　　5　　13　　8　　9

 

(5)　　4　　11　　8　　13　　9　　　　　　=>　　　　　　　　　   5

11　　4　　(5)　　13　　8　　9　　　　　　　　　　　　　　　　 /　　\

 

(4)　　11　　 　　(8)　　 13　　9　　　　　　=>　　　　　　　　　5

11　　(4)　　　　 13　　(8)　　9　　 　　　　　　　　　　　　　/　　\

　　　　　     　　　　　　　　　　　　　　　　　　　　　　　　4　　　8

 

(11)　　　　 　　(13)　　　　(9)　　　　　　　　=>　　　　　　　 5

(11)　　　　　　 (13)　　　　(9)　　　　 　　　　　　　　　　  /　　\

　　　　　　　　　　　　　　　　　　　　　　　　　　　　　     4　　　8

　　　　　　　　　　　　　　　　　　　　　　　　　　　　    /　　　 /     \

　　　　　　　　　　　　　　　　　　　　　　　　　　　    11　　   13　　  9

做完这道题后，大多人可能会有个疑问，怎么没有由先序和后序遍历建立二叉树呢，
这是因为先序和后序遍历不能唯一的确定一个二叉树，比如下面五棵树：

    1　　　　　　preorder:　　  1　　2　　3
   / \　　　　　  inorder:　　     2　　1　　3
 2    3　　  　　 postorder:　　 2　　3　　1

 

       1   　　　　preorder:　　   1　　2　　3
      / 　　　　　 inorder:　　     3　　2　　1
    2 　　　　     postorder: 　　3　　2　　1
   /
 3

 

       1　　　　    preorder:　　  1　　2　　3
      / 　　　　　  inorder:　　    2　　3　　1
    2 　　　　　　postorder:　　3　　2　　1
      \
       3

 

       1　　　　     preorder:　　  1　　2　　3
         \ 　　　　   inorder:　　    1　　3　　2
          2 　　　　 postorder:　　3　　2　　1
         /
       3

 

       1　　　　     preorder:　　  1　　2　　3
         \ 　　　　　inorder:　　    1　　2　　3
          2 　　　　 postorder:　　3　　2　　1
            \
　　　　3

 从上面我们可以看出，对于先序遍历都为1 2 3的五棵二叉树，它们的中序遍历都不相同，而它们的后序遍历却有相同的，
 所以只有和中序遍历一起才能唯一的确定一棵二叉树。
*/
