/**
Refer to
https://leetcode.com/problems/binary-subarrays-with-sum/
In an array A of 0s and 1s, how many non-empty subarrays have sum S?

Example 1:
Input: A = [1,0,1,0,1], S = 2
Output: 4
Explanation: 
The 4 subarrays are bolded below:
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]
[1,0,1,0,1]

Note:
A.length <= 30000
0 <= S <= A.length
A[i] is either 0 or 1.
*/

// Solution 1: Not fixed length sliding window + Template with Subarrays With K Different Integers
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/SubarraysWithKDifferentIntegers.java
// https://leetcode.com/problems/binary-subarrays-with-sum/discuss/186683/C++JavaPython-Sliding-Window-O(1)-Space/500171
class Solution {
    public int numSubarraysWithSum(int[] A, int S) {
        return helper(A, S) - helper(A, S - 1);
    }
    
    private int helper(int[] A, int S) {
        int i = 0;
        int count = 0;
        int sum = 0;
        for(int j = 0; j < A.length; j++) {
            sum += A[j];
            // i < j will error out on below test case:
            // Input [0,0,0,0,0] and 0
            // expected: 15, error output: 10
            while(sum > S && i <= j) {
                sum -= A[i];
                i++;
            }
            count += j - i + 1;
        }
        return count;
    }
}
