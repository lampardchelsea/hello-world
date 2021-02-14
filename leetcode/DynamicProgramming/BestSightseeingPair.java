/**
Refer to
https://leetcode.com/problems/best-sightseeing-pair/
Given an array A of positive integers, A[i] represents the value of the i-th sightseeing spot, and two sightseeing spots i and j have distance j - i between them.

The score of a pair (i < j) of sightseeing spots is (A[i] + A[j] + i - j) : the sum of the values of the sightseeing spots, minus the distance between them.

Return the maximum score of a pair of sightseeing spots.

Example 1:
Input: [8,1,5,2,6]
Output: 11
Explanation: i = 0, j = 2, A[i] + A[j] + i - j = 8 + 5 + 0 - 2 = 11

Note:
2 <= A.length <= 50000
1 <= A[i] <= 1000
*/

// Solution 1: DP
// Refer to
// https://leetcode.com/problems/best-sightseeing-pair/discuss/260909/JavaPython-Descriptive-solution.O(N)-Time-or-O(1)-Space.-Very-similar-to-Kadence-Algo!
/**
Thought:
Divide the score into 2 parts - A[i]+i and A[j]-j. For each j value, we will check if the sum of maximum A[i]+i value found so far and current A[j]-j is greater than the res.

The goal is to keep track of:
Maximum So far and add it to the cur_cell and maintain maximum result
Here, max_so_far contains : A[i] + i
Original Given Formula : A[i] + A[j] + i - j

Break in two parts : A[i] + i and A[j] -j
Keep MAX_VALUE of first part among the elements seen so far
Add the current element to max_so_far and check the result is changing or not
Also, keep updating the max_so_far at each step
*/
class Solution {
    public int maxScoreSightseeingPair(int[] A) {
        // Intitialize maximum so far value of A[i] + i (A[0] + 0)
        int max_so_far = A[0];
        int result = 0;
        for(int k = 1; k < A.length; k++) {
            // Update result if a higher value of max_so_far + A[k] - k is found
            result = Math.max(result, max_so_far + A[k] - k);
            // Update maximum so far when scanning the array
            max_so_far = Math.max(max_so_far, A[k] + k);
        }
        return result;
    }
}

