/**
Refer to
https://hjweds.gitbooks.io/leetcode/lowest-common-ancestor-i.html
Given two nodes in a binary tree, find their lowest common ancestor.
Assumptions
There is no parent pointer for the nodes in the binary tree
The given two nodes are guaranteed to be in the binary tree
Examples
    5
  /   \
 9     12
/ \
2 3 14
The lowest common ancestor of 2 and 14 is 5
The lowest common ancestor of 2 and 9 is 9
*/

// Solution:
// base case : root == null || root == one || root == two时返回root
// 对root.left, root.right进行recursion，都不null时返回root，否则返回非空的
  public TreeNode lowestCommonAncestor(TreeNode root,
      TreeNode one, TreeNode two) {
    if (root == null || root == one || root == two) {
      return root;
    }
    TreeNode left = lowestCommonAncestor(root.left, one, two);
    TreeNode right = lowestCommonAncestor(root.right, one, two);
    if (left != null && right != null) {
      return root;
    }
    return left == null ? right : left;
  }
// 若是BST，可以：
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val > p.val && root.val > q.val){
            return lowestCommonAncestor(root.left, p, q);
        }else if(root.val < p.val && root.val < q.val){
            return lowestCommonAncestor(root.right, p, q);
        }else{
            return root;
        }
    }
iterative:
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        while (true) {
            if (root.val > p.val && root.val > q.val)
                root = root.left;
            else if (root.val < p.val && root.val < q.val)
                root = root.right;
            else
                return root;
        }
    }
