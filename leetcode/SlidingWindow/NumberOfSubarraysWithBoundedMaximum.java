/**
Refer to
https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/
We are given an array A of positive integers, and two positive integers L and R (L <= R).

Return the number of (contiguous, non-empty) subarrays such that the value of the maximum array element in that subarray is at least L and at most R.

Example :
Input: 
A = [2, 1, 4, 3]
L = 2
R = 3
Output: 3
Explanation: There are three subarrays that meet the requirements: [2], [2, 1], [3].
Note:

L, R  and A[i] will be an integer in the range [0, 10^9].
The length of A will be in the range of [1, 50000].
*/

// Solution 1: Not fixed length sliding Window
// Refer to
// https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/discuss/117595/Short-Java-O(n)-Solution
// https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/discuss/117595/Short-Java-O(n)-Solution/117576
// https://leetcode.com/problems/number-of-subarrays-with-bounded-maximum/discuss/117595/Short-Java-O(n)-Solution/117414
/**
The condition A[i]>=L && A[i]<=R,means that A[j:i is a valid subarray and thus we can have (i-j+1) valid subarrays, count is the 
valid subarrays between j to i at this point
The condition A[i]<L means that A[j:i] is still a valid subarray but we need the last element (>=L and <=R) which is within A[j:i], 
thus adding last valid number of subarrays which is count.
Else just move the back pointer forward
*/
class Solution {
    public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int result = 0;
        int count = 0;
        int j = 0;
        for(int i = 0; i < A.length; i++) {
            if(A[i] >= L && A[i] <= R) {
                result += i - j + 1;
                count = i - j + 1;
            } else if(A[i] < L) {
                result += count;
            } else {
                j = i + 1;
                count = 0;
            }
        }
        return result;
    }
}
