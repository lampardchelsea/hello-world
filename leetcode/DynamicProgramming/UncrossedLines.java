/**
Refer to
https://leetcode.com/problems/uncrossed-lines/
We write the integers of A and B (in the order they are given) on two separate horizontal lines.

Now, we may draw connecting lines: a straight line connecting two numbers A[i] and B[j] such that:

A[i] == B[j];
The line we draw does not intersect any other connecting (non-horizontal) line.
Note that a connecting lines cannot intersect even at the endpoints: each number can only belong to one connecting line.

Return the maximum number of connecting lines we can draw in this way.

Example 1:
Input: A = [1,4,2], B = [1,2,4]
Output: 2
Explanation: We can draw 2 uncrossed lines as in the diagram.
We cannot draw 3 uncrossed lines, because the line from A[1]=4 to B[2]=4 will intersect the line from A[2]=2 to B[1]=2.

Example 2:
Input: A = [2,5,1,2,5], B = [10,5,2,1,5,2]
Output: 3

Example 3:
Input: A = [1,3,7,1,7,5], B = [1,9,2,5,1]
Output: 2

Note:
1 <= A.length <= 500
1 <= B.length <= 500
1 <= A[i], B[i] <= 2000
*/

// Solution 1: DP same as Longest Common Subsequence (LCS)
// Refer to
// https://leetcode.com/problems/uncrossed-lines/discuss/282842/JavaC%2B%2BPython-DP-The-Longest-Common-Subsequence
// https://leetcode.com/problems/uncrossed-lines/discuss/282842/JavaC++Python-DP-The-Longest-Common-Subsequence/268478
/**
understanding of the dp[i][j] may be the key to figure it out
dp[i][j] denote the longest common subsequence between the first i elements of A and the first j elements of B
In the case of Example2
Input: A = [2,5,1,2,5], B = [10,5,2,1,5,2]
below is the correspond martix dp[][]
0 0 0 0 0 0 0
0 0 0 1 1 1 1
0 0 1 1 1 2 2
0 0 1 1 2 2 2
0 0 1 2 2 2 3
0 0 1 2 2 3 3
so the answer is dp[5][6]=3
try to manually deduce a specific case may help to understand it better
*/

// https://www.youtube.com/watch?v=duCx_62nMOA
/**
虽然题目看起来有点复杂，其实就是求最长公共子串的长度。连线互不交叉其实就是公共子串换了一种说法而已。

Rule 1: If adding a pair A[i] == B[j], it will definitely add 1 more pair based on original pairs
    exist   new
A   2  2  |  4
B   3  2  |  4

Rule 2: If adding a pair A[i] != B[j], it will either add 1 more pair or not based on how we do
   e.g if consider 3 in new pair then no change, if consider 2 in new pair then add 1 pair
    exist   new
A   2  2  |  3
B   3  2  |  2

dp[i][j]: Taking first i elements from A and first j elements from B
E.g
A:  2  3  2  4
B:  2  2  3  4
dp[2][3] = 2 means take 2, 3 from A and take 2, 2, 3 from B, we can have 2 uncrossed lines

Still take the same example:
E.g
A:  2  3  2  4
B:  2  2  3  4

DP table: 
Initial [0] on both x-axis and y-axis means:
[0] on y-axis you don't choose any elements in array A.
[0] on x-axis you don't choose any elements in array B.
and fill with 0 means since no elements choosed you cannot link any lines.
The actual index on DP table start from 1 and 1 for both array A and array B,
but when mapping back to array A and array B, the index of elements start
from 0 and 0, so need to use [i - 1] and [j - 1] to indicate
Note: array A use i to iterate, array B use j to iterate

   [0] 1  2  3  4
[0] 0  0  0  0  0  A:
1   0  P1 P2       2
2   0              3
3   0              2
4   0           P3 4
    B: 2  4  3  4
	
For position P1, since we pick up 1st element in both A and B, and they are equal as 2 == 2, 
so based on Rule 1, we have 1 + dp[i - 1][j - 1] where i = 1 and j = 1 in DP table, and we 
know dp[0][0] = 0, so dp[1][1] = 1 + dp[0][0] = 1 as diagonal relation.

For position P2, since we pick up 1 element from A as 2 only and 2 elements from B as 2 and 4,
we have 2 == 2 and 2 != 4, so based on Rule 2, we have Math.max(dp[i - 1][j], dp[i][j - 1]),
here for dp[i - 1][j] means we move 1 row back, that leave recent element from A (which is 2), 
but keep same column, which still have both 2, 4 from B, for dp[i][j - 1] means we move 1 column 
back, that leave recent element from B (which is 4), which still have 2 from A.
In simple for A: 2 and B: 2, 4
Choice 1 as dp[i - 1][j]: A -> empty and B -> 2, 4
Choice 2 as dp[i][j - 1]: A -> 2 and B -> 2
So Math.max(dp[i - 1][j], dp[i][j - 1]) = Math.max(dp[0][2], dp[1][1]) = Math.max(0, 1) = 1

Continue till P3, dp[4][4] = 3
*/
class Solution {
    public int maxUncrossedLines(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                if(i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if(A[i - 1] == B[j - 1]) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }
}
