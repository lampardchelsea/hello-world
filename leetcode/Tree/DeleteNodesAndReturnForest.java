/**
 Refer to
 https://leetcode.com/problems/delete-nodes-and-return-forest/
 Given the root of a binary tree, each node in the tree has a distinct value.
 After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).
 Return the roots of the trees in the remaining forest.  You may return the result in any order.

 Example 1:
                   1
                2     3
              4  5   6  7
              
 Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
 Output: [[1,2,null,4],[6],[7]]
 
 Constraints:
 The number of nodes in the given tree is at most 1000.
 Each node has a distinct value between 1 and 1000.
 to_delete.length <= 1000
 to_delete contains distinct values between 1 and 1000.
*/
// Solution 1:DFS post-order traversal
// Refer to
// https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328853/JavaC%2B%2BPython-Recursion-Solution
// https://zhongwen.gitbook.io/leetcode-report/medium/1110.-delete-nodes-and-return-forest
/**
If a node is root (has no parent) and isn't deleted, when will we add it to the result.
使用递归的方式，先把待delete的数组转换为set，以方便检测。如果是root而且不在delete set里，则加进最终list；
继续执行左右child node；最后如果是delete的node，return null；如不是，return当前node。

    Set<Integer> to_delete_set;
    List<TreeNode> res;
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        to_delete_set = new HashSet<>();
        res = new ArrayList<>();
        for (int i : to_delete)
            to_delete_set.add(i);
        helper(root, true);
        return res;
    }

    private TreeNode helper(TreeNode node, boolean is_root) {
        if (node == null) return null;
        boolean deleted = to_delete_set.contains(node.val);
        // If a node is root (has no parent) and isn't deleted, when will we add it to the result.
        if (is_root && !deleted) res.add(node);
        node.left = helper(node.left, deleted);
        node.right =  helper(node.right, deleted);
        return deleted ? null : node;
    }
*/

// https://blog.csdn.net/fuxuemingzhu/article/details/101542543
/**
 一个节点被删除时有以下几个情况：
 如果该节点是根节点，形成左右两个子树，此时递归左右子树。
 如果该节点不是根节点，那么需要修改其父节点指向自己的指针为空，并且递归左右子树。
 一个节点一旦被删除，那么其左右孩子就是新的树的根节点。
 如果一个节点是根节点，并且不被删除的情况下，才会放入结果中。
*/

// https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328853/JavaC++Python-Recursion-Solution/523668
/**
 Correct me if i am wrong. 
 (Preorder to add each individual root) if (is_root && !deleted) res.add(node); 
 (Postorder to cut the tree); deleted ? NULL : node; 
 --> Sounds good
*/

// https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328854/Python-Recursion-with-explanation-Question-seen-in-a-2016-interview
/**
Question analysis
The question is composed of two requirements:
To remove a node, the child need to notify its parent about the child's existance.
To determine whether a node is a root node in the final forest, we need to know [1] whether the node is removed (which is trivial), 
and [2] whether its parent is removed (which requires the parent to notify the child)
It is very obvious that a tree problem is likely to be solved by recursion. The two components above are actually examining interviewees' 
understanding to the two key points of recursion:
passing info downwards -- by arguments
passing info upwards -- by return value
*/

// https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/328854/Python-Recursion-with-explanation-Question-seen-in-a-2016-interview/301896
/**
class Solution {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> todelete = new HashSet<>();
        for(int t : to_delete) {
            todelete.add(t);
        }
        List<TreeNode> result = new ArrayList<>();
        walk(root, false, result, todelete);
        return result;
    }

    private TreeNode walk(TreeNode node, boolean parent_exist, List<TreeNode> result, Set<Integer> todelete) {
        if ( node == null) return null;
        if ( todelete.contains(node.val)) {
            node.left = walk(node.left, false, result, todelete);
            node.right = walk(node.right, false, result, todelete);
            return null;
        } else {
            if ( ! parent_exist) result.add(node);
            node.left = walk(node.left, true, result, todelete);
            node.right = walk(node.right, true, result, todelete);
            return node;
        }
    }
}
*/
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
/**
使用递归的方式，先把待delete的数组转换为set，以方便检测。
如果是root而且不在delete set里，则加进最终list；继续执行左右child node；
最后如果是delete的node，return null；如不是，return当前node
*/
class Solution {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> todelete = new HashSet<Integer>();
        for(int t : to_delete) {
            todelete.add(t);
        }
        List<TreeNode> result = new ArrayList<TreeNode>();
        helper(root, false, result, todelete);
        return result;
    }

    // parent_exist = true means current node is root since it has no parent
    // parent_exist = false means current node has its root
    private TreeNode helper(TreeNode node, boolean parent_exist, List<TreeNode> result, Set<Integer> todelete) {
        if(node == null) {
            return null;
        }
        // If will delete the node then return null, if not then return current node
        if(todelete.contains(node.val)) {
            node.left = helper(node.left, false, result, todelete);
            node.right = helper(node.right, false, result, todelete);
            return null;
        } else {
            // If a node is root (has no parent) and isn't deleted, we add it to the result
            if (!parent_exist) {
                result.add(node);
            }
            node.left = helper(node.left, true, result, todelete);
            node.right = helper(node.right, true, result, todelete);
            return node;
        }
    }
}


