/**
 * Refer to
 * https://leetcode.com/problems/flatten-binary-tree-to-linked-list/description/
 * Flatten a binary tree to a fake "linked list" in pre-order traversal.
 * Here we use the right pointer in TreeNode as the next pointer in ListNode.
 * Notice
 * Don't forget to mark the left child of each node to null. Or you will get Time 
 * Limit Exceeded or Memory Limit Exceeded.
 * Have you met this question in a real interview?
		Example
		
		              1
		               \
		     1          2
		    / \          \
		   2   5    =>    3
		  / \   \          \
		 3   4   6          4
		                     \
		                      5
		                       \
		                        6
 * 
 * Solution
 * https://www.jiuzhang.com/solutions/flatten-binary-tree-to-linked-list/
*/

