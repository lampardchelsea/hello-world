/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
    1
   / \
  2   2
 / \ / \
3  4 4  3
 * But the following [1,2,2,null,3,null,3] is not:
    1
   / \
  2   2
   \   \
   3    3
 * Note:
 * Bonus points if you could solve it both recursively and iteratively.
 *
 * Refer to 
 * https://leetcode.com/articles/symmetric-tree/
 * If test with a symmetric tree, 
     	       1
	 		 /   \
	 		2     2
	 	   / \   / \     
	 	  3   4 4   3
	     /           \
	    5             5
	Solution s1 = new Solution();
	s1.root = new TreeNode("1");
	s1.root.left = new TreeNode("2");
	s1.root.right = new TreeNode("2");
	s1.root.left.left = new TreeNode("3");
	s1.root.left.right = new TreeNode("4");
	s1.root.right.left = new TreeNode("4");
	s1.root.right.right = new TreeNode("3");
	s1.root.left.left.left = new TreeNode("5");
	s1.root.right.right.right = new TreeNode("5");
 * 
 * the result will below:
 * Tracking on q
 * q:  after add  1 1
       after poll empty
       after add  2 2 2 2
       after poll 2 2
       after add  2 2 3 3 4 4
       after poll 3 3 4 4
       after add  3 3 4 4 4 4 3 3
       after poll 4 4 4 4 3 3
       after add  4 4 4 4 3 3 5 5 null null
       after poll 4 4 3 3 5 5 null null
       after add  4 4 3 3 5 5 null null null null null null
       after poll 3 3 5 5 null null null null null null
       after add  3 3 5 5 null null null null null null null null null null
       after poll 5 5 null null null null null null null null null null
       after add  5 5 null null null null null null null null null null null null 5 5
       after poll null null null null null null null null null null null null 5 5
       after add  null null null null null null null null null null null null 5 5 null null null null
       after poll null null null null null null null null null null 5 5 null null null null --> (x == null && y == null) {continue;}
       after poll null null null null null null null null 5 5 null null null null
       after poll null null null null null null 5 5 null null null null
       after poll null null null null 5 5 null null null null
       after poll null null 5 5 null null null null
       after poll 5 5 null null null null
       after poll null null null null
       after add  null null null null null null null null --> (x == null && y == null) {continue;}
       after poll null null null null null null
       after poll null null null null
       after poll null null
       after poll empty
*/
