/**
 Refer to
 https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/
 Return any binary tree that matches the given preorder and postorder traversals.

Values in the traversals pre and post are distinct positive integers.

Example 1:
Input: pre = [1,2,4,5,3,6,7], post = [4,5,2,6,7,3,1]
Output: [1,2,3,4,5,6,7]
 
Note:
1 <= pre.length == post.length <= 30
pre[] and post[] are both permutations of 1, 2, ..., pre.length.
It is guaranteed an answer exists. If there exists multiple answers, you can return any of them.
*/
// Solution 1:
// Refer to
// https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/discuss/161268/C++JavaPython-One-Pass-Real-O(N)/174387
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
    public TreeNode constructFromPrePost(int[] pre, int[] post) {
        if(pre == null || pre.length == 0 || post == null || post.length == 0) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < post.length; i++) {
            map.put(post[i], i);
        }
        return helper(pre, 0, pre.length - 1, post, 0, post.length - 1, map);
    }
    
    public TreeNode helper(int[] pre, int preStart, int preEnd, int[] post, int postStart, int postEnd, Map<Integer, Integer> map) {
        if(preStart > preEnd || postStart > postEnd) {
            return null;
        }
        TreeNode root = new TreeNode(pre[preStart]);
        /**
            Note 1:
            Get the next root index on pre array by 
            map.get(pre[preStart + 1])
            e.g after 1 (which already used to create root) is 2 as next
            then try to locate 2's index on post array (which stored on map)
            then we can split post array as [postStart, rootIndexOnPost]
            and [rootIndexOnPost + 1, postEnd - 1]
            additional '-1' means exclude the already used root value which
            always on current array's last position
            e.g in first round, 1 is used and it at post array's last position
            
            Note 2:
            Must including condition check as if(preStart + 1 <= preEnd),
            otherwise, when doing recursive, will generate ArrayIndexOutOfBoundException
            e.g ArrayIndexOutOfBoundException: 7
            Last executed input
            pre = [1,2,4,5,3,6,7]
            post = [4,5,2,6,7,3,1]
        */
        if(preStart + 1 <= preEnd) {
            int rootIndexOnPost = map.get(pre[preStart + 1]);
            int deltaIndex = rootIndexOnPost - postStart;
            root.left = helper(pre, preStart + 1, preStart + 1 + deltaIndex, post, postStart, postStart + deltaIndex, map);
            root.right = helper(pre, preStart + 1 + deltaIndex + 1, preEnd, post, postStart + deltaIndex + 1, postEnd - 1, map);  
        }
        return root;
    }
}
