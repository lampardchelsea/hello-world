/**
 Refer to
 https://leetcode.com/problems/count-complete-tree-nodes/
 Given a complete binary tree, count the number of nodes.
 
 Note:
 Definition of a complete binary tree from Wikipedia:
 In a complete binary tree every level, except possibly the last, is completely filled, and all nodes in the last level 
 are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.

 Example:
 Input: 
     1
    / \
   2   3
  / \  /
 4  5 6

 Output: 6
*/

// Solution 1: Brutal force BFS
// Runtime: 7 ms, faster than 5.25% of Java online submissions for Count Complete Tree Nodes.
// Memory Usage: 49.2 MB, less than 5.00% of Java online submissions for Count Complete Tree Nodes.
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(root);
        int count = 1;
        while(!q.isEmpty()) {
            TreeNode node = q.poll();
            if(node.left != null) {
                count++;
                q.offer(node.left);
            }
            if(node.right != null) {
                count++;
                q.offer(node.right);
            }
        }
        return count;
    }
}

// Solution 2: Brutal force DFS
// Refer to
// https://www.cnblogs.com/grandyang/p/4567827.html
/**
 其实这道题的最暴力的解法就是直接用递归来统计结点的个数，根本不需要考虑什么完全二叉树还是完美二叉树，递归在手，遇 tree 不愁。
 直接一行搞定碉堡了，这可能是我见过最简洁的 brute force 的解法了吧
 Runtime: 0 ms, faster than 100.00% of Java online submissions for Count Complete Tree Nodes.
 Memory Usage: 42 MB, less than 68.99% of Java online submissions for Count Complete Tree Nodes.
*/
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return helper(root);
    }
    
    private int helper(TreeNode node) {
        if(node == null) {
            return 0;
        }
        int left = helper(node.left);
        int right = helper(node.right);
        return 1 + left + right;
    }
}

// Solution 3:
// Refer to
// https://www.cnblogs.com/grandyang/p/4567827.html
/**
 我们还是要来利用一下完全二叉树这个条件，不然感觉对出题者不太尊重。通过上面对完全二叉树跟完美二叉树的定义比较，可以看出二者的关系是，
 完美二叉树一定是完全二叉树，而完全二叉树不一定是完美二叉树。那么这道题给的完全二叉树就有可能是完美二叉树，若是完美二叉树，节点个数
 很好求，为2的h次方减1，h为该完美二叉树的高度。若不是的话，只能老老实实的一个一个数结点了。思路是由 root 根结点往下，分别找最靠左边
 和最靠右边的路径长度，如果长度相等，则证明二叉树最后一层节点是满的，是满二叉树，直接返回节点个数，如果不相等，则节点个数为左子树的
 节点个数加上右子树的节点个数再加1(根节点)，其中左右子树节点个数的计算可以使用递归来计算
*/

// https://leetcode.com/problems/count-complete-tree-nodes/discuss/61948/Accepted-Easy-Understand-Java-Solution
/**
 public class Solution {
    public int countNodes(TreeNode root) {
        int leftDepth = leftDepth(root);
        int rightDepth = rightDepth(root);
        if (leftDepth == rightDepth)
            return (1 << leftDepth) - 1;
        else
            return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private int rightDepth(TreeNode root) {
        int dep = 0;
        while (root != null) {
            root = root.right;
            dep++;
        }
        return dep;
    }

    private int leftDepth(TreeNode root) {
        int dep = 0;
        while (root != null) {
            root = root.left;
            dep++;
        }
        return dep;
    }
}
*/

// https://leetcode.com/problems/count-complete-tree-nodes/discuss/61948/Accepted-Easy-Understand-Java-Solution/63464
/**
 For those who are confused with (1 << leftDepth) - 1;
 This is done to find the nodes when depth is known.
 Suppose there are N nodes in a tree, Then depth = log2(N + 1)
 1 node gives log2(2) = 1
 3 nodes gives log2(4) = 2
 7 nodes gives log2(8) = 3
 15 nodes gives log2(16) = 4
 what we are doing in this line (1 << leftDepth) - 1 is given Depth we will find Number of nodes, N = (2 ^ Depth) - 1.
 Which the effect is same as Math.pow(2, leftDepth) - 1
*/

// https://leetcode.com/problems/count-complete-tree-nodes/discuss/61948/Accepted-Easy-Understand-Java-Solution/119426
/**
 This is a clean and smart solution, my understanding is as follows:
 A fully completed tree has node number: count = 2 ^ depth - 1
 for example: [1,2,3]
 depth is 2
 count = 2 ^ 2 - 1 = 3
 Compare left height and right height, if equal, use the formular, otherwise recurvisely search left and right at next level
 The search pattern is very similar to binary search, the difference of heights ethier exsits in left side, or right side
 Due to the reason stated in point 3, the time complexity is h ^ 2, there is h times for each level, and h times for 
 calculating height at each level
*/
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int leftDepth = 0;
        int rightDepth = 0;
        // Be careful, need to create 2 new cursor traverse the tree
        // instead of use root, since root must keep for use in recursion
        TreeNode leftChild = root;
        TreeNode rightChild = root;
        while(leftChild != null) {
            leftDepth++;
            leftChild = leftChild.left;
        }
        while(rightChild != null) {
            rightDepth++;
            rightChild = rightChild.right;
        }
        if(leftDepth == rightDepth) {
            return (1 << leftDepth) - 1;
        } else {
            return 1 + countNodes(root.left) + countNodes(root.right);
        }
    }
}
