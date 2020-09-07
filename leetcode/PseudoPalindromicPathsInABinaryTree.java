/**
Refer to
https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/
Given a binary tree where node values are digits from 1 to 9. A path in the binary tree is said to be pseudo-palindromic 
if at least one permutation of the node values in the path is a palindrome.

Return the number of pseudo-palindromic paths going from the root node to leaf nodes.

Example 1:
        2
      3   1
    3  1    1
    
Input: root = [2,3,1,3,1,null,1]
Output: 2 
Explanation: The figure above represents the given binary tree. There are three paths going from the root node to leaf nodes: 
the red path [2,3,3], the green path [2,1,1], and the path [2,3,1]. Among these paths only red path and green path are 
pseudo-palindromic paths since the red path [2,3,3] can be rearranged in [3,2,3] (palindrome) and the green path [2,1,1] 
can be rearranged in [1,2,1] (palindrome).

Example 2:
        2
     1     1
   1   3
         1

Input: root = [2,1,1,1,3,null,null,null,null,null,1]
Output: 1 
Explanation: The figure above represents the given binary tree. There are three paths going from the root node to leaf nodes: 
the green path [2,1,1], the path [2,1,3,1], and the path [2,1]. Among these paths only the green path is pseudo-palindromic 
since [2,1,1] can be rearranged in [1,2,1] (palindrome).

Example 3:

Input: root = [9]
Output: 1

Constraints:
The given binary tree will have between 1 and 10^5 nodes.
Node values are digits from 1 to 9.
*/

// Solution 1: DFS pre-order traversal + array clone
// Refer to
// https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/discuss/648538/JavaPython-3-2-similar-recursive-O(n)-codes-w-brief-explanation-and-analysis.
/**
 1.Recurse from root to the leaves and count the frequencies of same numbers in each path;
 2.If there is at most 1 odd frequency in a path, then it is a valid path.
*/
// Style 1: helper as void return and use global variable
class Solution {
    int result = 0;
    public int pseudoPalindromicPaths (TreeNode root) {
        helper(root, new int[10]);
        return result;
    }
    
    private void helper(TreeNode root, int[] map) {
        if(root == null) {
            return;
        }
        map[root.val]++;
        if(root.left == null && root.right == null) {
            int count = 0;
            for(int i = 0; i < map.length; i++) {
                if(map[i] % 2 == 1) {
                    count++;
                }
            }
            if(count <= 1) {
                result += 1;
            }
        }
        helper(root.left, map.clone());
        helper(root.right, map.clone());
    }
}

// Style 2: return int value and no global variable
public int pseudoPalindromicPaths (TreeNode root) {
    return preorder(root, new int[10]);
}

private int preorder(TreeNode n, int[] count) {
    if (n == null)
        return 0;
    ++count[n.val];
    if (n.left == null && n.right == null) {
        int numOfOdd = 0;
        for (int c : count)
            numOfOdd += c % 2;
        return numOfOdd > 1 ? 0 : 1;
    }
    return preorder(n.left, count.clone()) + preorder(n.right, count.clone());               
}


// Solution 2: DFS pre-order traversal + backtracking
// Refer to
// https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/discuss/648517/Palindrome-Property-Trick-Java-Solution-Explained
/**
Note that we maintain a count array of size 9 (numbers 1 to 9).
The array stores the count of each number in the tree path from root to leaf.

Steps -
- Traverse the tree from root to all the path. Normal tree traversal.
Keep track of count of all the numbers in every path from root to leaf.
- When we reach the leaf, we have to check if current path is pseudo random palindrome or not.

Trick
We know that in palindrome,every number occurs even number of times .
But in odd length palindrome, only one number can occur odd number of times.
We have used that property in isPalindrome method to check if numbers are pseudo palindrome or not.
*/

// Why did you write this line explicitly(map[root.val] = map[root.val]-1 ?
// https://leetcode.com/problems/pseudo-palindromic-paths-in-a-binary-tree/discuss/648517/Palindrome-Property-Trick-Java-Solution-Explained/554820
/**
 map is an array. In java, for arrays the references of the arrays are passed in method parameters. In short, all the recursive calls point 
 to same array reference. In this case when you update any value in map array, the same array is used in other recursive call as well. 
 Hence it is necessary to backtrack and update the value back . More details here - https://stackoverflow.com/a/12757860
*/
class Solution {
    int result = 0;
    public int pseudoPalindromicPaths (TreeNode root) {
        helper(root, new int[10]);
        return result;
    }
    
    private void helper(TreeNode root, int[] map) {
        if(root == null) {
            return;
        }
        map[root.val]++;
        if(root.left == null && root.right == null) {
            int count = 0;
            for(int i = 0; i < map.length; i++) {
                if(map[i] % 2 == 1) {
                    count++;
                }
            }
            if(count <= 1) {
                result += 1;
            }
        }
        helper(root.left, map);
        helper(root.right, map);
        map[root.val]--;
    }
}
