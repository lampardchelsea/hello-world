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
 * Complexity Analysis
 * Because we traverse the entire input tree once, the total run time is O(n), where nn is the total number of nodes in the tree.
 * The number of recursive calls is bound by the height of the tree. In the worst case, 
 * the tree is linear and the height is in O(n). Therefore, space complexity due to recursive calls on the stack is O(n)
 * in the worst case.
*/
