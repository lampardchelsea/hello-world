/**
 Refer to
 https://leetcode.com/problems/smallest-string-starting-from-leaf/
 Given the root of a binary tree, each node has a value from 0 to 25 representing the letters 'a' to 'z': 
 a value of 0 represents 'a', a value of 1 represents 'b', and so on.

Find the lexicographically smallest string that starts at a leaf of this tree and ends at the root.

(As a reminder, any shorter prefix of a string is lexicographically smaller: for example, "ab" is 
lexicographically smaller than "aba".  A leaf of a node is a node that has no children.)

Example 1:
          a
      b       c
    d   e   d   e
Input: [0,1,2,3,4,3,4]
Output: "dba"

Example 2:
          z
     b        d
   b   d    a   c
Input: [25,1,3,1,3,0,2]
Output: "adz"

Example 3:
          c
     c        b
   b        a
     a
Input: [2,2,1,null,1,0,null,0]
Output: "abc"

Note:
The number of nodes in the given tree will be between 1 and 8500.
Each node in the tree will have a value between 0 and 25.
*/
// Solution 1: DFS
// Refer to
// http://www.noteanddata.com/leetcode-988-Smallest-String-Starting-From-Leaf-java-solution-update.html
/**
 刷题感悟
虽然之前写的代码通过了oj， 但是test case实际上不全， 而自己的思路也有一些问题。 什么时候可以用bottom up, 
什么时候不能用bottom up, 需要进一步思考。 lca作为经典的bottom up, 为什么那个场景可以用bottom up, 而这里不能用，需要好好体会
解题思路分析
原来的版本是错的， 之前在这里写来一个解题报告http://www.noteanddata.com/leetcode-988-Smallest-String-Starting-From-Leaf-java-solution-note.html
可是后来被人指出是错的， 具体在这里https://leetcode.com/problems/smallest-string-starting-from-leaf/discuss/231117/java-dfs-on/246263, 
我的问题由@ayyildiz指出， 更多讨论在@jamespb在https://leetcode.com/problems/smallest-string-starting-from-leaf/discuss/244205/divide-and-conquer-technique-doesnt-work-for-this-problem指出。
具体的错误原因，是因为我们采取了bottom up的做法， 然后对于[25,1,null,0,0,1,null,null,null,0]
有两条路径， 一条是ababz, 另外一条是abz， 如果采取bottom up的方法， 那么因为ab小于abab, 所以在子节点的时候就直接返回ab了， 然后最后合成abz,
但是,对于两个长度不相同的字符串， s1 < s2, 不能推出s1+ch < s2+ch
所以， 这个题目应该要用top down的做法，递归的时候，把从上到下的字符串传入， 然后倒过来构造字符串，然后递归到叶节点的时候才返回，
这样，每次返回的字符串都是完整的从叶节点到根节点的字符串，信息都是完整的(而bottom up的做法只有部分字符串), 所以这样做比较应该是正确的结果。
*/
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
    public String smallestFromLeaf(TreeNode root) {
        return helper(root, "");
    }
    
    private String helper(TreeNode root, String s) {
        if(root == null) {
            return s;
        }
        // Appending current node value ahead of string
        s = "" + (char)('a' + root.val) + s;
        if(root.left == null && root.right == null) {
            return s;
        }
        // Could not put statement here since missing append leaf nodes
        // input: [0,1,2,3,4,3,4]
        // output: "ba"
        // expected: "dba"
        //s = "" + (char)('a' + root.val) + s;
        if(root.left == null) {
            return helper(root.right, s);
        }
        if(root.right == null) {
            return helper(root.left, s);
        }
        String left = helper(root.left, s);
        String right = helper(root.right, s);
        return left.compareTo(right) <= 0 ? left : right; 
    }
}

// Solution 2: Another style of DFS
// Refer to
// https://leetcode.com/problems/smallest-string-starting-from-leaf/discuss/231251/Java-2-Concise-DFS-codes-with-comment.
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
    // Dummy value '~' > 'z'
    String result = "~";
    public String smallestFromLeaf(TreeNode root) {
        return helper(root, "");
    }
    
    private String helper(TreeNode root, String temp) {
        // Base case, and in case root is null
        if(root == null) {
            return result;
        }
        // Prepend current char to the path string from root
        temp = (char)(root.val + 'a') + temp;
        // Update result if n is a leaf
        if(root.left == null && root.right == null) {
            if(result.compareTo(temp) > 0) {
                result = temp;
            }
        }
        helper(root.left, temp);
        helper(root.right, temp);
        return result;
    }
}
