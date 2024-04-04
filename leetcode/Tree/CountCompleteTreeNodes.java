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

// Solution 3: Use the nature of complete binary tree / perfect binary tree / full binary tree
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/Document/Full_Compelete_Perfect_Tree.pdf
/**
 完全二叉树 (Complete Binary Tree)：
 A Complete Binary Tree （CBT) is a binary tree in which every level, except possibly the last, is completely filled, 
 and all nodes are as far left as possible.
 对于一颗二叉树，假设其深度为d（d>1）。除了第d层外，其它各层的节点数目均已达最大值，且第d层所有节点从左向右连续地紧密排列，
 这样的二叉树被称为完全二叉树；换句话说，完全二叉树从根结点到倒数第二层满足完美二叉树，最后一层可以不完全填充，其叶子结点都靠左对齐。
 
 完美二叉树 (Perfect Binary Tree)：
 A Perfect Binary Tree(PBT) is a tree with all leaf nodes at the same depth. All internal nodes have degree 2.
 二叉树的第i层至多拥有 (2 ^ i) - 1 个节点数；深度为k的二叉树至多总共有 (2 ^ (k + 1)) -1 个节点数，
 而总计拥有节点数匹配的，称为“满二叉树”；

 完满二叉树 (Full Binary Tree):
 A Full Binary Tree (FBT) is a tree in which every node other than the leaves has two children.
 换句话说，所有非叶子结点的度都是2。（只要你有孩子，你就必然是有两个孩子）
*/

// https://www.cnblogs.com/grandyang/p/4567827.html
/**
 我们还是要来利用一下完全二叉树这个条件，不然感觉对出题者不太尊重。通过上面对完全二叉树跟完美二叉树的定义比较，可以看出二者的关系是，
 完美二叉树一定是完全二叉树，而完全二叉树不一定是完美二叉树。那么这道题给的完全二叉树就有可能是完美二叉树，若是完美二叉树，节点个数
 很好求，为2的h次方减1，h为该完美二叉树的高度。若不是的话，只能老老实实的一个一个数结点了。思路是由 root 根结点往下，分别找最靠左边
 和最靠右边的路径长度，如果长度相等，则证明二叉树最后一层节点是满的，是满二叉树，直接返回节点个数，如果不相等，则节点个数为左子树的
 节点个数加上右子树的节点个数再加1(根节点)，其中左右子树节点个数的计算可以使用递归来计算
*/

// https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
/**
 A Different Solution - 544 ms
 Here's one based on victorlee's C++ solution.
class Solution {
    public int countNodes(TreeNode root) {
        if (root == null)
            return 0;
        TreeNode left = root, right = root;
        int height = 0;
        while (right != null) {
            left = left.left;
            right = right.right;
            height++;
        }
        if (left == null)
            return (1 << height) - 1;
        return 1 + countNodes(root.left) + countNodes(root.right);
    }
}

Note that that's basically this:
public int countNodes(TreeNode root) {
    if (root == null)
        return 0;
    return 1 + countNodes(root.left) + countNodes(root.right)
}

That would be O(n). But... the actual solution has a gigantic optimization. It first walks all the way left and right to determine 
the height and whether it's a full tree, meaning the last row is full. If so, then the answer is just 2^height-1. And since always 
at least one of the two recursive calls is such a full tree, at least one of the two calls immediately stops. 
Again we have runtime O(log(n)^2).
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
        // If root == null, depth is 0, but height is -1, here we use depth concept only
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

// Solution 4: DFS Concise Java solutions O(log(n)^2)
// Refer to
// https://www.cnblogs.com/grandyang/p/4567827.html
/**
 这道题还有一个标签是 Binary Search，但是在论坛上看了一圈下来，并没有发现有经典的二分搜索的写法，只找到了下面这个类似二分
 搜索的解法，感觉应该不算严格意义上的二分搜素法吧，毕竟 left，right 变量和 while 循环都没有，只是隐约有点二分搜索法的影子
 在里面，即根据条件选左右分区。首先我们需要一个 getHeight 函数，这是用来统计当前结点的左子树的最大高度的，因为一直走的是
 左子结点，若当前结点不存在，则返回 -1。我们对当前结点调用 getHeight 函数，得到左子树的最大高度h，若为 -1，则说明当前结点
 不存在，直接返回0。否则就对右子结点调用 getHeight 函数，若返回值为 h-1，说明左子树是一棵完美二叉树，则左子树的结点个数是 
 2^h-1 个，再加上当前结点，总共是 2^h 个，即 1<<h，此时再加上对右子结点调用递归函数的返回值即可。若对右子结点调用 getHeight 
 函数的返回值不为 h-1，说明右子树一定是完美树，且高度为 h-1，则总结点个数为 2^(h-1)-1，加上当前结点为 2^(h-1)，即 1<<(h-1)，
 然后再加上对左子结点调用递归函数的返回值即可。这样貌似也算一种二分搜索法吧
*/

// https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
/**
 Explanation
 The height of a tree can be found by just going left. Let a single node tree have height 0. Find the height h of 
 the whole tree. If the whole tree is empty, i.e., has height -1, there are 0 nodes.
 Otherwise check whether the height of the right subtree is just one less than that of the whole tree, meaning left 
 and right subtree have the same height.
 If yes, then the last node on the last tree row is in the right subtree and the left subtree is a full tree of 
 height h-1. So we take the 2^h-1 nodes of the left subtree plus the 1 root node plus recursively the number of nodes 
 in the right subtree.
 If no, then the last node on the last tree row is in the left subtree and the right subtree is a full tree of height 
 h-2. So we take the 2^(h-1)-1 nodes of the right subtree plus the 1 root node plus recursively the number of nodes in 
 the left subtree.
 Since I halve the tree in every recursive step, I have O(log(n)) steps. Finding a height costs O(log(n)). 
 So overall O(log(n)^2).
*/
class Solution {
    public int countNodes(TreeNode root) {
        int h = getHeight(root);
        if(h < 0) {
            return 0;
        }
        int r_h = getHeight(root.right);
        if(r_h == h - 1) {
            return (1 << h) + countNodes(root.right);
        } else {
            return (1 << h - 1) + countNodes(root.left);
        }
    }
    
    // For complete tree we can only check left subtree height
    // A tree with only root node has height 0 and a tree with zero nodes 
    // would be considered as empty. An empty tree has height of -1
    private int getHeight(TreeNode node) {
        if(node == null) {
            return -1;
        }
        return 1 + getHeight(node.left);
    }
}

// Solution 5: BFS Concise Java solutions O(log(n)^2)
// Refer to
// https://www.cnblogs.com/grandyang/p/4567827.html
/**
 我们也可以写成迭代的形式，用一个 while 循环，感觉好处是调用 getHeight 函数的次数变少了，因为开头计算的高度h可以一直用，每下一层后，h自减1即可
*/

// https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
/**
 Here's an iterative version as well, with the benefit that I don't recompute h in every step.
*/
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        int count = 0;
        int h = getHeight(root);
        while(root != null) {
            if(getHeight(root.right) == h - 1) {
                count += 1 << h;
                root = root.right;
            } else {
                count += 1 << (h - 1);
                root = root.left;
            }
            h--;
        }
        return count;
    }
    
    private int getHeight(TreeNode node) {
        if(node == null) {
            return -1;
        }
        return 1 + getHeight(node.left);
    }
}




















































































https://leetcode.com/problems/count-complete-tree-nodes/description/
Given the root of a complete binary tree, return the number of the nodes in the tree.
According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree, and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
Design an algorithm that runs in less than O(n) time complexity.
 
Example 1:

Input: root = [1,2,3,4,5,6]
Output: 6

Example 2:
Input: root = []
Output: 0

Example 3:
Input: root = [1]
Output: 1
 
Constraints:
- The number of nodes in the tree is in the range [0, 5 * 10^4]..
- 0 <= Node.val <= 5 * 10^4
- The tree is guaranteed to be complete.
--------------------------------------------------------------------------------
Attempt 1: 2024-04-02
Solution 1: DFS (10 min)
Style 1: return int
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int countNodes(TreeNode root) {
        return helper(root);
    }

    private int helper(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return 1 + helper(root.left) + helper(root.right);
    }
}

Style 2: void return + global variable
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int count = 0;
    public int countNodes(TreeNode root) {
        helper(root);
        return count;
    }

    private void helper(TreeNode root) {
        if(root == null) {
            return;
        }
        count++;
        helper(root.left);
        helper(root.right);
    }
}

Solution 2: DFS + Complete Binary Tree attribute usage (10 min)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public int countNodes(TreeNode root) {
        int l_height = leftHeight(root);
        int r_height = rightHeight(root);
        if(l_height == r_height) {
            return (int)Math.pow(2, l_height) - 1;
        }
        return 1 + countNodes(root.left) + countNodes(root.right);
    }

    private int leftHeight(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return 1 + leftHeight(root.left);
    }

    private int rightHeight(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return 1 + rightHeight(root.right);        
    }
}

Refer to
https://www.cnblogs.com/grandyang/p/4567827.html
我们还是要来利用一下完全二叉树这个条件，不然感觉对出题者不太尊重。通过上面对完全二叉树跟完美二叉树的定义比较，可以看出二者的关系是，完美二叉树一定是完全二叉树，而完全二叉树不一定是完美二叉树。那么这道题给的完全二叉树就有可能是完美二叉树，若是完美二叉树，节点个数很好求，为2的h次方减1，h为该完美二叉树的高度。若不是的话，只能老老实实的一个一个数结点了。思路是由 root 根结点往下，分别找最靠左边和最靠右边的路径长度，如果长度相等，则证明二叉树最后一层节点是满的，是满二叉树，直接返回节点个数，如果不相等，则节点个数为左子树的节点个数加上右子树的节点个数再加1(根节点)，其中左右子树节点个数的计算可以使用递归来计算
https://leetcode.com/problems/count-complete-tree-nodes/discuss/61958/Concise-Java-solutions-O(log(n)2)
class Solution {
    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
    public int countNodes(TreeNode root) {
        int h = height(root);
        return h < 0 ? 0 :
               height(root.right) == h-1 ? (1 << h) + countNodes(root.right)
                                         : (1 << h-1) + countNodes(root.left);
    }
}
Explanation
The height of a tree can be found by just going left. Let a single node tree have height 0. Find the height h of the whole tree. If the whole tree is empty, i.e., has height -1, there are 0 nodes.
Otherwise check whether the height of the right subtree is just one less than that of the whole tree, meaning left and right subtree have the same height.
If yes, then the last node on the last tree row is in the right subtree and the left subtree is a full tree of height h-1. So we take the 2^h-1 nodes of the left subtree plus the 1 root node plus recursively the number of nodes in the right subtree.
If no, then the last node on the last tree row is in the left subtree and the right subtree is a full tree of height h-2. So we take the 2^(h-1)-1 nodes of the right subtree plus the 1 root node plus recursively the number of nodes in the left subtree.
Since I halve the tree in every recursive step, I have O(log(n)) steps. Finding a height costs O(log(n)). So overall O(log(n)^2).
Iterative Version
Here's an iterative version as well, with the benefit that I don't recompute h in every step.
class Solution {
    int height(TreeNode root) {
        return root == null ? -1 : 1 + height(root.left);
    }
    public int countNodes(TreeNode root) {
        int nodes = 0, h = height(root);
        while (root != null) {
            if (height(root.right) == h - 1) {
                nodes += 1 << h;
                root = root.right;
            } else {
                nodes += 1 << h-1;
                root = root.left;
            }
            h--;
        }
        return nodes;
    }
}

--------------------------------------------------------------------------------
Refer to
https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Tree/Document/Full_Compelete_Perfect_Tree.pdf
完全二叉树 (Complete Binary Tree)：
A Complete Binary Tree （CBT) is a binary tree in which every level, except possibly the last, is completely filled,
and all nodes are as far left as possible.
对于一颗二叉树，假设其深度为d（d>1）。除了第d层外，其它各层的节点数目均已达最大值，且第d层所有节点从左向右连续地紧密排列，
这样的二叉树被称为完全二叉树；换句话说，完全二叉树从根结点到倒数第二层满足完美二叉树，最后一层可以不完全填充，其叶子结点都靠左对齐。

完美二叉树 (Perfect Binary Tree)：
A Perfect Binary Tree(PBT) is a tree with all leaf nodes at the same depth. All internal nodes have degree 2.
二叉树的第i层至多拥有 (2 ^ i) - 1 个节点数；深度为k的二叉树至多总共有 (2 ^ (k + 1)) -1 个节点数，而总计拥有节点数匹配的，称为“满二叉树”；

完满二叉树 (Full Binary Tree):
A Full Binary Tree (FBT) is a tree in which every node other than the leaves has two children.
换句话说，所有非叶子结点的度都是2。（只要你有孩子，你就必然是有两个孩子）

Refer to
https://towardsdatascience.com/5-types-of-binary-tree-with-cool-illustrations-9b335c430254
1. Full Binary Tree
Full Binary Tree is a Binary Tree in which every node has 0 or 2 children.

Valid and Invalid Structure of Full Binary Tree
Interesting Fact: For Full Binary Tree, following equation is always true.
Number of Leaf nodes = Number of Internal nodes + 1

2. Complete Binary Tree
Complete Binary Tree has all levels completely filled with nodes except the last level and in the last level, all the nodes are as left side as possible.

Valid and Invalid Structure of Complete Binary Tree
Interesting Fact: Binary Heap is an important use case of Complete Binary tree.

3. Perfect Binary Tree
Perfect Binary Tree is a Binary Tree in which all internal nodes have 2 children and all the leaf nodes are at the same depth or same level.

Valid and Invalid Structure of Perfect Binary Tree
Interesting Fact: Total number of nodes in a Perfect Binary Tree with height H is 2^H - 1.

4. Balanced Binary Tree
Balanced Binary Tree is a Binary tree in which height of the left and the right sub-trees of every node may differ by at most 1.

Valid and Invalid Structure of Balanced Binary Tree
Interesting Fact: AVL Tree and Red-Black Tree are well-known data structure to generate/maintain Balanced Binary Search Tree. Search, insert and delete operations cost O(log n) time in that.

5. Degenerate(or Pathological) Binary Tree
Degenerate Binary Tree is a Binary Tree where every parent node has only one child node.

Valid and Invalid Structure of Degenerate Binary Tree
Interesting Fact: Height of a Degenerate Binary Tree is equal to Total number of nodes in that tree.

Refer to
L104.Maximum Depth of Binary Tree (Ref.L222)
L1448.Count Good Nodes in Binary Tree
L333.Largest BST Subtree (Ref.L98,L222)
