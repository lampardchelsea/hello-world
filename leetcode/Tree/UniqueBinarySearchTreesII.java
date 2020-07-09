/**
 Refer to
 https://leetcode.com/problems/unique-binary-search-trees-ii/
 Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.

Example:
Input: 3
Output:
[
  [1,null,3,2],
  [3,2,null,1],
  [3,1,null,null,2],
  [2,1,3],
  [1,null,2,null,3]
]
Explanation:
The above output corresponds to the 5 unique BST's shown below:

   1         3     3      2      1
    \       /     /      / \      \
     3     2     1      1   3      2
    /     /       \                 \
   2     1         2                 3
 

Constraints:

0 <= n <= 8
*/

// Solution 1: Recursive with O(n^3)
// Refer to
// https://leetcode.com/problems/unique-binary-search-trees-ii/discuss/31494/A-simple-recursive-solution/30203
// https://leetcode.wang/leetCode-95-Unique-Binary-Search-TreesII.html#%E8%A7%A3%E6%B3%95%E5%9B%9B-%E5%8A%A8%E6%80%81%E8%A7%84%E5%88%92-2
/**
解法一完全没有用到查找二叉树的性质，暴力尝试了所有可能从而造成了重复。我们可以利用一下查找二叉树的性质。左子树的所有值小于根节点，右子树的所有值大于根节点。

所以如果求 1...n 的所有可能。

我们只需要把 1 作为根节点，[ ] 空作为左子树，[ 2 ... n ] 的所有可能作为右子树。

2 作为根节点，[ 1 ] 作为左子树，[ 3...n ] 的所有可能作为右子树。

3 作为根节点，[ 1 2 ] 的所有可能作为左子树，[ 4 ... n ] 的所有可能作为右子树，然后左子树和右子树两两组合。

4 作为根节点，[ 1 2 3 ] 的所有可能作为左子树，[ 5 ... n ] 的所有可能作为右子树，然后左子树和右子树两两组合。

...

n 作为根节点，[ 1... n ] 的所有可能作为左子树，[ ] 作为右子树。

至于，[ 2 ... n ] 的所有可能以及 [ 4 ... n ] 以及其他情况的所有可能，可以利用上边的方法，把每个数字作为根节点，然后把所有可能的左子树和右子树组合起来即可。

如果只有一个数字，那么所有可能就是一种情况，把该数字作为一棵树。而如果是 [ ]，那就返回 null。
*/
public List<TreeNode> generateTrees(int n) {
    List<TreeNode> ans = new ArrayList<TreeNode>();
    if (n == 0) {
        return ans;
    }
    return getAns(1, n);

}

private List<TreeNode> getAns(int start, int end) { 
    List<TreeNode> ans = new ArrayList<TreeNode>();
    //此时没有数字，将 null 加入结果中
    if (start > end) {
        ans.add(null);
        return ans;
    }
    //只有一个数字，当前数字作为一棵树加入结果中
    if (start == end) {
        TreeNode tree = new TreeNode(start);
        ans.add(tree);
        return ans;
    }
    //尝试每个数字作为根节点
    for (int i = start; i <= end; i++) {
        //得到所有可能的左子树
        List<TreeNode> leftTrees = getAns(start, i - 1);
         //得到所有可能的右子树
        List<TreeNode> rightTrees = getAns(i + 1, end);
        //左子树右子树两两组合
        for (TreeNode leftTree : leftTrees) {
            for (TreeNode rightTree : rightTrees) {
                TreeNode root = new TreeNode(i);
                root.left = leftTree;
                root.right = rightTree;
                //加入到最终结果中
                ans.add(root);
            }
        }
    }
    return ans;
}

// Another explain
class Solution {
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        if(n == 0) {
            return result;
        }
        return helper(1, n);
    }
    
    private List<TreeNode> helper(int lo, int hi) {
        List<TreeNode> list = new ArrayList<TreeNode>();
        // Base case
        // Refer to
        // https://www.youtube.com/watch?v=GZ0qvkTAjmw
        /**
          Why the base case is lo > hi ?
          Because when we reach to the leave node and you still call
          recursive helper() method, the boundary will become below:
          e.g i = 5 -> left = helper(5,4), right = helper(6,5)
          hence lo > hi always the terminate condition
        */
        if(lo > hi) {
            return list;
        }
        for(int i = lo; i <= hi; i++) {
            List<TreeNode> left = helper(lo, i - 1);
            List<TreeNode> right = helper(i + 1, hi);
            // Create root should in each block since for loop
            // in the block means create multiple root required
            // TreeNode root = new TreeNode(i);
            if(left.size() == 0 && right.size() == 0) {
                TreeNode root = new TreeNode(i);
                list.add(root);
            } else if(right.size() == 0) {
                for(TreeNode l : left) {
                    TreeNode root = new TreeNode(i);
                    root.left = l;
                    list.add(root);
                }
            } else if(left.size() == 0) {
                for(TreeNode r : right) {
                    TreeNode root = new TreeNode(i);
                    root.right = r;
                    list.add(root);
                }
            } else {
                for(TreeNode l : left) {
                    for(TreeNode r : right) {
                        TreeNode root = new TreeNode(i);
                        root.left = l;
                        root.right = r;
                        list.add(root);
                    }
                }   
            }
        }
        return list;
    }
}
