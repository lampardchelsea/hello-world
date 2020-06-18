/**
 Refer to
 https://leetcode.com/problems/maximum-binary-tree/
 Given an integer array with no duplicates. A maximum tree building on this array is defined as follow:

The root is the maximum number in the array.
The left subtree is the maximum tree constructed from left part subarray divided by the maximum number.
The right subtree is the maximum tree constructed from right part subarray divided by the maximum number.
Construct the maximum tree by the given array and output the root node of this tree.

Example 1:
Input: [3,2,1,6,0,5]
Output: return the tree root node representing the following tree:

      6
    /   \
   3     5
    \    / 
     2  0   
       \
        1
Note:
The size of the given array will be in the range [1,1000].
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/maximum-binary-tree/solution/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(nums, 0, nums.length);
    }
    
    private TreeNode helper(int[] nums, int i, int j) {
        if(i == j) {
            return null;
        }
        int index = findMaxNumIndexInGivenRange(nums, i, j);
        TreeNode root = new TreeNode(nums[index]);
        root.left = helper(nums, i, index);
        root.right = helper(nums, index + 1, j);
        return root;
    }
    
    private int findMaxNumIndexInGivenRange(int[] nums, int i, int j) {
        int max_i = i;
        for(int m = i; m < j; m++) {
            if(nums[max_i] < nums[m]) {
                max_i = m;
            }
        }
        return max_i;
    }
}

// Re-work
// Solution 1: Recursive O(N^2)
// Refer to
// https://leetcode.com/problems/maximum-binary-tree/discuss/106149/Java-solution-recursion
/**
 Time complexity : O(n^2) 
 The function construct is called nn times. At each level of the recursive tree, we traverse over 
 all the nn elements to find the maximum element. In the average case, there will be a nlogn levels 
 leading to a complexity of O(nlogn). In the worst case, the depth of the recursive tree can grow 
 upto n, which happens in the case of a sorted numsnums array, giving a complexity of O(n^2)
 Space complexity : O(n). 
 The size of the setset can grow upto nn in the worst case. In the average case, the size will be 
 nlogn for nn elements in numsnums, giving an average case complexity of O(logn)
*/
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }
    
    private TreeNode helper(int[] nums, int i, int j) {
        if(i > j) {
            return null;
        }
        int max_index = i;
        for(int k = i + 1; k <= j; k++) {
            if(nums[k] > nums[max_index]) {
                max_index = k;
            }
        }
        TreeNode root = new TreeNode(nums[max_index]);
        root.left = helper(nums, i, max_index - 1);
        root.right = helper(nums, max_index + 1, j);
        return root;
    }
}

// Solution 2: Iterative (O(N))
// Refer to
// https://leetcode.com/problems/maximum-binary-tree/discuss/106156/Java-worst-case-O(N)-solution
// https://leetcode.com/problems/maximum-binary-tree/discuss/106156/Java-worst-case-O(N)-solution/143674
// https://leetcode.com/problems/maximum-binary-tree/discuss/106156/Java-worst-case-O(N)-solution/113618
/**
Let me try to explain the algorithm.
If we have built the max binary tree for nums[0] ~ nums[i - 1], how can we insert nums[i] to the binary tree?
Say the max binary tree for nums[0] ~ nums[i - 1] looks like:

      A
     / \
  ...   B
       / \
    ...   C
         / \
      ...   ...
Say the node for nums[i] is D.
If D.val > A.val, then because A.val is at the left of D.val, we can just move the tree rooted at A to the left child of D.

        D
       /
      A
     / \
  ...   B
       / \
    ...   C
         / \
      ...   ...
If D.val < A.val, then because D.val is at the right of A.val, D must be put into the right subtree of A.
Similarly, if D.val < B.val, then D must be put into the right subtree of B.
Say B.val > D.val > C.val, then D should be the right child of B. (because D.val is at the right of B.val, 
and D.val is the biggest among the numbers at the right of B.val.)
Because C.val < D.val, and C.val is at the left of D.val, C should become left child of D.

      A
     / \
  ...   B
       / \
     ...  D
         /
        C
       / \
    ...   ...
So to update the max binary tree for nums[0] ~ nums[i - 1], we need to know the nodes on the right path of the tree. (A, B, C, ...)
How to maintain the path?
Let's look at the property of the nodes.
A is the biggest among nums[0] ~ nums[i - 1].
B is the biggest for the numbers between A and nums[i] (exclusive).
C is the biggest for the numbers between B and nums[i] (exclusive).
Let's use a stack, and assume that the content of the stack contains the "right path" of nodes before the node for the current number.
For the node of the new number, we should remove the nodes in the stack which are smaller than the current number.
So we pop the stack until the top element of the stack is greater than the current number.
Then, add the node for the current number to the stack.

might be better to include some explanation as the implementation is a bit abstract.

traverse the array once and create the node one by one. and use stack to store a decreasing sequence.
each step, we create a new curNode. compare to the peek of stack,
2.a. keep popping the stack while (stack.peek().val < curNode.val), and set the last popping node to be curNode.left.
Because the last one fulfilling the criteria is the largest number among curNode's left children. => curNode.left = last pop node
2.b. after popping up all nodes that fulfill (stack.peek().val < curNode.val),
thus (stack.peek().val > curNode.val), the stack.peek() is curNode's root => peek.right = curNode
return the bottom node of stack.
*/
// https://leetcode.com/problems/maximum-binary-tree/discuss/106147/c-8-lines-on-log-n-map-plus-stack-with-binary-search
// https://massivealgorithms.blogspot.com/2017/08/leetcode-654-maximum-binary-tree.html
/**
类似单调栈主要思想是维持一个栈，这个栈里面的元素是要从栈底到栈顶保持递减的:
过程:
扫描数组，将每个元素建立一个节点cur；
每次都要判断当前元素是否比栈顶元素大，如果大，就要一直弹出元素，同时，要将当前元素的左孩子设置成弹出的节点: cur.left = stack.pop()；
弹完栈之后，此时栈中的元素都比cur大，此时我们让栈顶的节点的右孩子指向cur；
然后压栈当前元素；
最后返回的是栈底的元素(最大的元素作为根)；
*/



