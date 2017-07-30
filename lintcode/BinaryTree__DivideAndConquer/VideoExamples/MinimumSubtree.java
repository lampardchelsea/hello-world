/**
 * Refer to
 * http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-3
 * Given a binary tree, find the subtree with minimum sum. Return the root of the subtree.
    Example
    Given a binary tree:

             1
           /   \
         -5     2
         / \   /  \
        0   2 -4  -5 
    return the node 1.
* 
* Solution
* http://www.jiuzhang.com/solutions/minimum-subtree/
* http://blog.leanote.com/post/westcode/%5B%E5%88%B7%E9%A2%98%E7%AC%94%E8%AE%B0%5D-LeetCode-LintCode-3
* 思路
* 第一种做法是比较直接的方法，之所以划分为 traver + divide&conquer 是因为全局变量的应用。
* 没什么特别复杂的，做法就是把 minSubroot 和 minSum 都当成全局变量存起来，然后每个 node 都遍历一遍，用递归（分治）得到
* 两子树的 sum 拿来算当前 node 的 sum，然后和全局的 minSum 进行比较。如何小于就把当前 subroot 和 sum 更新成minSubroot 和 minSum。
* 第二种做法是只用 divide conquer，也就是不用全局变量。
* 不用全局变量那么就需要创建一个 custom class 来保存minSubroot 之类的信息。 所以我这里用了 这个 ResultType class。 
* 每个 ResultType object 里面存着3个东西：
    这个 subtree 下算出来的 minSum
    minSubroot
    当前 subtree 的 sum， 注意这个和（1）的区别，这个是当前 tree 的所有 sum，（1）当前树下的 min subtree 的 sum
* 再 helper 里也是用递归来把 result 从下往上传，然后和当前的这个 root 的 sum 进行比较和更新。最后把 minSubroot 传回 顶部
* 复杂度分析
* 时间
* O(n), 每个 treenode traverse 一遍，每遍 O(1)
* 空间
    Combo: O(1), no extra space needed
    Divide & Conquer: O(n), 每个 node allocate 一个 result type
* 总结
* 这题总体来说思路还是很简单的。重点是对两种不同方法的理解和应用。还有对 result type 的应用和练习。
*/

// Solution 1: Traverse + Divide And Conquer
public class Solution {
    private TreeNode subTree = null;
    private int subSum = Integer.MAX_VALUE;
    /**
     * @param root the root of binary tree
     * @return the root of the minimum subtree
     */
    public TreeNode findSubtree(TreeNode root) {
        helper(root);
        return subTree;
    }
    
    // Actually as must return current sum value, not
    // exactly match the traverse requirement as
    // return void, so its combination of Traverse
    // and Divide & Conquer
    public int helper(TreeNode root) {
        // Base case
        if(root == null) {
            return root;
        }
        int sum = helper(root.left) + helper(root.right) + root.val;
        if(sum <= subSum) {
            subSum = sum;
            subTree = root;
        }
        // Must return current sum value as recursively calling inside helper
        // when calculate sum
        return sum; 
    }
    
}


// Solution 2: Pure Divide And Conquer
public class Solution {    
    public TreeNode findSubtree(TreeNode root) {
        // Important: We need to create a new named method as 'helper()'
        // even with same kind of parameter, because we need to return
        // different kind of object, not as TreeNode but ResultType
        ResultType result = helper(root);
        return result.minSubtree;
    }
    
    public ResultType helper(TreeNode node) {
        // Base case
        if(node == null) {
            return new ResultType(null, Integer.MAX_VALUE, 0);
        }
        // Divide
        ResultType leftResult = helper(root.left);
        ResultType rightResult = helper(root.right);
        // Merge
        ResultType result = new ResultType(node, leftResult.sum + rightResult.sum + node.val, leftResult.sum + rightResult.sum + node.val);
        if(leftResult.minSum <= result.minSum) {
            result.minSum = leftResult.minSum;
            result.minSubtree = leftResult.minSubtree;
        }
        if(rightResult.minSum <= result.minSum) {
            result.minSum = rightResult.minSum;
            result.minSubtree = rightResult.minSubtree;
        }
        return result;
    }
}
// Create helper class for returning multiple values
class ResultType {
    public TreeNode minSubtree;
    public int sum;
    public int minSum;
    public ResultType(TreeNode minSubtree, int minSum, int sum) {
        this.minSubtree = minSubtree;
        this.minSum = minSum;
        this.sum = sum;
    }
}



