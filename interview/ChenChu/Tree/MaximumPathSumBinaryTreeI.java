/**
 Refer to
 https://hjweds.gitbooks.io/leetcode/maximum-path-sum-binary-tree-i.html
 https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/
 Find the maximum path sum between two leaves of a binary tree
 Given a binary tree in which each node element contains a number. Find the maximum possible sum 
 from one leaf node to another.
 The maximum sum path may or may not go through root. For example, in the following binary tree, 
 the maximum sum is 27(3 + 6 + 9 + 0 – 1 + 10). Expected time complexity is O(n).
 If one side of root is empty, then function should return minus infinite (INT_MIN in case of C/C++)
         -15
        /    \
      5        6
     / \      / \
   -8   1    3   9
  /  \
 2    6
*/

// Solution 1:
// Refer to
// https://www.geeksforgeeks.org/find-maximum-path-sum-two-leaves-binary-tree/
