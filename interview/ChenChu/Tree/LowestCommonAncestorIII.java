/**
Refer to
https://hjweds.gitbooks.io/leetcode/lowest-common-ancestor-iii.html
Given two nodes in a binary tree, find their lowest common ancestor (the given two nodes are not 
guaranteed to be in the binary tree).
Return null If any of the nodes is not in the tree.
Assumptions
There is no parent pointer for the nodes in the binary tree
The given two nodes arenotguaranteed to be in the binary tree
Examples
    5

  /   \

 9     12
/ \
2 3 14
The lowest common ancestor of 2 and 14 is 5
The lowest common ancestor of 2 and 9 is 9
The lowest common ancestor of 2 and 8 is null (8 is not in the tree)
*/

// Solution: 做法与Lowest Common Ancestor I相同，但当返回结果是one或two时，要检查two是否在one的子树里
  public TreeNode lowestCommonAncestor(TreeNode root, TreeNode one, TreeNode two) {
    TreeNode ans = helper(root, one, two);
    if (ans != one && ans != two) {
      return ans;
    }
    boolean exist = false;
    if (ans == one) {
      exist = find(one, two);
    } else {
      exist = find(two, one);
    }
    return exist ? ans : null;
  }
  
  private TreeNode helper(TreeNode root, TreeNode one, TreeNode two) {
    if (root == null || root == one || root == two) {
      return root;
    }
    TreeNode left = helper(root.left, one, two);
    TreeNode right = helper(root.right, one, two);
    if (left != null && right != null) {
      return root;
    }
    return left == null ? right : left;
  }
  
  private boolean find(TreeNode root, TreeNode node) {
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    while (!queue.isEmpty()) {
      TreeNode cur = queue.poll();
      if (cur == node) {
        return true;
      }
      if (cur.left != null) {
        queue.offer(cur.left);
      }
      if (cur.right != null) {
        queue.offer(cur.right);
      }
    }
    return false;
  }
