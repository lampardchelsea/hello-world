/**
 Refer to
 https://www.lintcode.com/problem/count-univalue-subtrees/description
 Given a binary tree, count the number of uni-value subtrees.
 A Uni-value subtree means all nodes of the subtree have the same value.
 Example :
 Input:  root = [5,1,5,5,5,null,5]

              5
             / \
            1   5
           / \   \
          5   5   5

 Output: 4
*/

// Solution 1: Two Pass DFS Traverse
// Refer to
// https://www.cnblogs.com/grandyang/p/5206862.html
/**
 这道题让我们求相同值子树的个数，就是所有节点值都相同的子树的个数，之前有道求最大 BST 子树的题 
 Largest BST Subtree，感觉挺像的，都是关于子树的问题，解题思路也可以参考一下，这里可以用递归来做，
 第一种解法的思路是先序遍历树的所有的节点，然后对每一个节点调用判断以当前节点为根的字数的所有节点
 是否相同，判断方法可以参考之前那题 Same Tree，用的是分治法的思想，分别对左右字数分别调用递归
*/
public class Solution {
    /**
     * @param root: the given tree
     * @return: the number of uni-value subtrees.
     */
    int count = 0;
    public int countUnivalSubtrees(TreeNode root) {
        if(root == null) {
            return 0;
        }
        if(isUnival(root, root.val)) {
            count++; 
        }
        countUnivalSubtrees(root.left);
        countUnivalSubtrees(root.right);
        return count;
    }
    
    private boolean isUnival(TreeNode root, int val) {
        if(root == null) {
            return true;
        }
        return root.val == val && isUnival(root.left, val) && isUnival(root.right, val);
    }
}

// Solution 2: One Pass DFS Traverse
/**
 但是上面的那种解法不是很高效，含有大量的重复 check，我们想想能不能一次遍历就都搞定，这样想，符合条件
 的相同值的字数肯定是有叶节点的，而且叶节点也都相同(注意单独的一个叶节点也被看做是一个相同值子树)，
 那么可以从下往上 check，采用后序遍历的顺序，左右根，这里还是递归调用函数，对于当前遍历到的节点，
 如果对其左右子节点分别递归调用函数，返回均为 true 的话，那么说明当前节点的值和左右子树的值都相同，
 那么又多了一棵树，所以结果自增1，然后返回当前节点值和给定值(其父节点值)是否相同，从而回归上一层递归
 调用。这里特别说明一下在子函数中要使用的那个单竖杠或，为什么不用双竖杠的或，因为单竖杠的或是位或，
 就是说左右两部分都需要被计算，然后再或，C++ 这里将 true 当作1，false 当作0，然后进行 Bit OR 运算。
 不能使用双竖杠或的原因是，如果是双竖杠或，一旦左半边为 true 了，整个就直接是 true 了，右半边就不会
 再计算了，这样的话，一旦右子树中有值相同的子树也不会被计算到结果 res 中了
*/
public class Solution {
    /**
     * @param root: the given tree
     * @return: the number of uni-value subtrees.
     */
    int count = 0;
    public int countUnivalSubtrees(TreeNode root) {
        isUnival(root, -1);
        return count;
    }
    
    private boolean isUnival(TreeNode node, int val) {
        if(node == null) {
            return true;
        }
        if(!isUnival(node.left, node.val) | !isUnival(node.right, node.val)) {
            return false;
        }
        count++;
        return node.val == val;
    }
}
