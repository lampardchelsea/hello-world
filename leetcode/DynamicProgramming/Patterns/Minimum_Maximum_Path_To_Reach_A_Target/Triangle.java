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
// Style 1: Start from level = 0 (Top most root node), since only 1 root node value, should be added as same
// to both left and right paths
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

// Style 2: Start from level = 1 (2nd level if exist), since not like root node only 1 
// value, now have 2 values start with, need to respectively add left value OR right value
// Refer to
// https://leetcode.com/problems/triangle/discuss/705169/JAVA-Simple-recursive-1ms
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        return helper(triangle, 1, 0) + triangle.get(0).get(0);
    }
    
    private int helper(List<List<Integer>> triangle, int row, int pos) {
        if(row == triangle.size()) {
            return 0;
        }
        int left_val = triangle.get(row).get(pos);
        int right_val = triangle.get(row).get(pos + 1);
        int left = helper(triangle, row + 1, pos) + left_val;
        int right = helper(triangle, row + 1, pos + 1) + right_val;
        return Math.min(left, right);
    }
}

// Solution 2: Top Down DP Memoization (2D-DP)
// Style 1:
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

// Style 2:
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        Integer[][] memo = new Integer[triangle.size()][triangle.size()];
        return helper(triangle, 1, 0, memo) + triangle.get(0).get(0);
    }
    
    private int helper(List<List<Integer>> triangle, int row, int pos, Integer[][] memo) {
        if(row == triangle.size()) {
            return 0;
        }
        if(memo[row][pos] != null) {
            return memo[row][pos];
        }
        int left_val = triangle.get(row).get(pos);
        int right_val = triangle.get(row).get(pos + 1);
        int left = helper(triangle, row + 1, pos, memo) + left_val;
        int right = helper(triangle, row + 1, pos + 1, memo) + right_val;
        memo[row][pos] = Math.min(left, right);
        return memo[row][pos];
    }
}

// Solution 3: Bottom Up DP (2D-DP -> N * N)
// Refer to
// https://leetcode.com/problems/triangle/discuss/38730/DP-Solution-for-Triangle/36543
/**
This problem is quite well-formed in my opinion. The triangle has a tree-like structure, which would lead people to think about 
traversal algorithms such as DFS. However, if you look closely, you would notice that the adjacent nodes always share a 'branch'. 
In other word, there are overlapping subproblems. Also, suppose x and y are 'children' of k. Once minimum paths from x and y to 
the bottom are known, the minimum path starting from k can be decided in O(1), that is optimal substructure. Therefore, dynamic 
programming would be the best solution to this problem in terms of time complexity.

What I like about this problem even more is that the difference between 'top-down' and 'bottom-up' DP can be 'literally' pictured 
in the input triangle. For 'top-down' DP, starting from the node on the very top, we recursively find the minimum path sum of each 
node. When a path sum is calculated, we store it in an array (memoization); the next time we need to calculate the path sum of the 
same node, just retrieve it from the array. However, you will need a cache that is at least the same size as the input triangle 
itself to store the pathsum, which takes O(N^2) space. With some clever thinking, it might be possible to release some of the 
memory that will never be used after a particular point, but the order of the nodes being processed is not straightforwardly seen 
in a recursive solution, so deciding which part of the cache to discard can be a hard job.

'Bottom-up' DP, on the other hand, is very straightforward: we start from the nodes on the bottom row; the min pathsums for these 
nodes are the values of the nodes themselves. From there, the min pathsum at the ith node on the kth row would be the lesser of the 
pathsums of its two children plus the value of itself, i.e.:

minpath[k][i] = min( minpath[k+1][i], minpath[k+1][i+1]) + triangle[k][i];
*/
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int rowNum = triangle.size();
        int colNum = triangle.get(rowNum - 1).size();
        int[][] dp = new int[rowNum][colNum];
        for(int i = 0; i < colNum; i++) {
            dp[rowNum - 1][i] = triangle.get(rowNum - 1).get(i);
        }
        for(int row = rowNum - 2; row >= 0; row--) {
            for(int col = 0; col < triangle.get(row).size(); col++) {
                dp[row][col] = triangle.get(row).get(col) + Math.min(dp[row + 1][col], dp[row + 1][col + 1]);
            }
        }
        return dp[0][0];
    }
}

// Solution 4: Bottom Up DP (1D-DP Optimize Space from (n * n) to (2 * n))
// Refer to
// https://leetcode.com/problems/triangle/discuss/159686/Java-Recursive-greaterTop-Down-greater-Bottom-up-greater-Bottom-Up-%2B-Optimal-Space
/**
Optimize Space from (n * n) to (2 * n): 
You can see that dp[i][j] only depends on previous(lower row, more close to leaf) row, we 
can optimize the space by only using 2 rows instead of the matrix. Let's say dp and dp1.
Every time you finish updating dp1, dp have previous value, you can copy dp1 to dp as the 
previous row value need update to new row value.

   2
  3 4
 6 5 7
4 1 8 3
                                                     dp = dp1                          dp = dp1                              dp = dp1
dp: [0, 0, 0, 0] -> [4, 1, 8, 3] -> [4, 1, 8, 3]  -> [7, 6, 10, 0] -> [7, 6, 10, 0] -> [9, 10, 10, 0] -> [9, 10, 10, 0]   -> [11, 10, 10, 0]
dp1:[0, 0, 0, 0] -> [0, 0, 0, 0] -> [7, 6, 10, 0] -> [7, 6, 10, 0] -> [9, 10, 10, 0] -> [9, 10, 10, 0] -> [11, 10, 10, 0] -> [11, 10, 10, 0]
*/
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int rowNum = triangle.size();
        int colNum = triangle.get(rowNum - 1).size();
        int[] dp = new int[colNum];
        int[] dp1 = new int[colNum];
        for(int i = 0; i < colNum; i++) {
            dp[i] = triangle.get(rowNum - 1).get(i);
        }
        for(int row = rowNum - 2; row >= 0; row--) {
            for(int col = 0; col < triangle.get(row).size(); col++) {
                dp1[col] = triangle.get(row).get(col) + Math.min(dp[col], dp[col + 1]);
            }
            dp = dp1;
        }
        return dp[0];
    }
}

// Solution 5: Bottom Up DP (1D-DP Optimize Space from (2 * n) to n)
// Refer to
// https://leetcode.com/problems/triangle/discuss/38730/DP-Solution-for-Triangle
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/Document/Pattern_1_0_1_Knapsack/How_downgrade_2D_to_1D_and_why_loop_backwards.txt
/**
Think about why no need reverse for inner for-loop, is that because previous re-assign dp[i] will not affect later dp[i] need to re-assign ?
You can also see that, the column indices of dp[row + 1][col + 1] and dp[row][col] are >= col. 
The conclusion you can get is: the elements of previous (lower row more close to leaf) row 
whose column index is < col(i.e. dp[row + 1][0 : col - 1]) will not affect the update of 
dp[row][col] since we will not touch them:

new dp[0] defined by dp[0], dp[1] --> safe to update dp[0] 
(e.g initialized dp[] = {4,1,8,3}, dp[0] = 4, dp[1] = 1, new dp[0] = min(4, 1) + 6 = 7, now dp[] = {7,1,8,3})
new dp[1] defined by dp[1], dp[2] --> try to update dp[1], even we have new dp[0], it not affect new dp[1], safe to update dp[1] 
(e.g in the same loop for which previous update for dp[0] from 4 to 7, currently dp[0] = 7, dp[1] = 1, dp[2] = 8, new dp[1] = min(1, 8) + 5 = 6, 
it not use new dp[0] = 7 to define the new dp[1], so safe to update dp[1] from 1 to 6)

   2
  3 4
 6 5 7
4 1 8 3

dp: [0, 0, 0, 0] -> [4, 1, 8, 3]  -> [7, 6, 10, 3] -> [9, 10, 10, 3] -> [11, 10, 10, 3]
*/
class Solution {
    public int minimumTotal(List<List<Integer>> triangle) {
        int rowNum = triangle.size();
        int colNum = triangle.get(rowNum - 1).size();
        int[] dp = new int[colNum];
        for(int i = 0; i < colNum; i++) {
            dp[i] = triangle.get(rowNum - 1).get(i);
        }
        for(int row = rowNum - 2; row >= 0; row--) {
            for(int col = 0; col < triangle.get(row).size(); col++) {
                dp[col] = triangle.get(row).get(col) + Math.min(dp[col], dp[col + 1]);
            }
        }
        return dp[0];
    }
}
