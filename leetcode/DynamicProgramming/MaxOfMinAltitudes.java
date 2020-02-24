/**
 Refer to
 https://leetcode.com/discuss/interview-question/383669/
 Given a matrix with r rows and c columns, find the maximum score of a path starting at [0, 0] and ending at [r-1, c-1]. 
 The score of a path is the minimum value in that path. For example, the score of the path 8 → 4 → 5 → 9 is 4.

Don't include the first or final entry. You can only move either down or right at any point in time.

Example 1:
Input:
[[5, 1],
 [4, 5]]
Output: 4
Explanation:
Possible paths:
5 → 1 → 5 => min value is 1
5 → 4 → 5 => min value is 4
Return the max value among minimum values => max(4, 1) = 4.

Example 2:
Input:
[[1, 2, 3]
 [4, 5, 1]]
Output: 4
Explanation:
Possible paths:
1-> 2 -> 3 -> 1
1-> 2 -> 5 -> 1
1-> 4 -> 5 -> 1
So min of all the paths = [2, 2, 4]. Note that we don't include the first and final entry.
Return the max of that, so 4.

Related problems:
https://leetcode.com/problems/minimum-path-sum/
https://leetcode.com/problems/unique-paths-ii/
https://leetcode.com/problems/path-with-maximum-minimum-value (premium) is a different problem. In this problem we can only move in 2 directions.
*/

// Solution 1: DP
// Refer to
// https://leetcode.com/discuss/interview-question/383669/Amazon-or-OA-2019-or-Max-Of-Min-Altitudes/350117
