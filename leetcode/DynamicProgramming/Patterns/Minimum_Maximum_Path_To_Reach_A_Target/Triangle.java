/**
Refer to
https://leetcode.com/problems/triangle/
Given a triangle array, return the minimum path sum from top to bottom.

For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the current row, 
you may move to either index i or index i + 1 on the next row.

Example 1:
Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
Output: 11
Explanation: The triangle looks like:
   2
  3 4
 6 5 7
4 1 8 3
The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined above).

Example 2:
Input: triangle = [[-10]]
Output: -10

Constraints:
1 <= triangle.length <= 200
triangle[0].length == 1
triangle[i].length == triangle[i - 1].length + 1
-104 <= triangle[i][j] <= 104

Follow up: Could you do this using only O(n) extra space, where n is the total number of rows in the triangle?
*/

// Solution 1: Native DFS (TLE)
// Style 1: Start from level = 0 (Top most root node)
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        return helper(triangle, 0, 0);
    }
    
    private int helper(List<List<Integer>> triangle, int row, int pos) {
        if(row == triangle.size()) {
            return 0;
        }
        int val = triangle.get(row).get(pos);
        int left = helper(triangle, row + 1, pos) + val;
        int right = helper(triangle, row + 1, pos + 1) + val;
        return Math.min(left, right);
    }
}

// Style 2: Start from level = 1 (2nd level if exist)
// Refer to
// https://leetcode.com/problems/triangle/discuss/705169/JAVA-Simple-recursive-1ms


// Solution 2: Top Down DP Memoization (2D-DP)
// Refer to
// https://leetcode.com/problems/triangle/discuss/705169/JAVA-Simple-recursive-1ms
/**
Taking the example given in question, we can see the indices have a pattern to follow, at each level its left and 
right child are previous_index, previous_index+1, this can be understood in seconds once u jot it down on paper.

Finally, you can think of it as a tree and find the path with minium value, the only important fact being that two 
or more nodes at the same level will encounter same children on the next node which would mean multiple computations 
so you can just save those results to be used again next time without going through the whole recursive process again.
*/
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        Integer[][] memo = new Integer[triangle.size()][triangle.size()];
        return helper(triangle, 0, 0, memo);
    }
    
    private int helper(List<List<Integer>> triangle, int row, int pos, Integer[][] memo) {
        if(row == triangle.size()) {
            return 0;
        }
        if(memo[row][pos] != null) {
            return memo[row][pos];
        }
        int val = triangle.get(row).get(pos);
        // At each level its left and right child are previous_index, previous_index + 1
        int left = helper(triangle, row + 1, pos, memo) + val;
        int right = helper(triangle, row + 1, pos + 1, memo) + val;
        memo[row][pos] = Math.min(left, right);
        return memo[row][pos];
    }
}
