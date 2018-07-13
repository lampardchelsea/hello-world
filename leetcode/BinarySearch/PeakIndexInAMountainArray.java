/**
 * Refer to
 * https://leetcode.com/problems/peak-index-in-a-mountain-array/description/
 * Let's call an array A a mountain if the following properties hold:

    A.length >= 3
    There exists some 0 < i < A.length - 1 such that A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1]
    Given an array that is definitely a mountain, return any i such that 
    A[0] < A[1] < ... A[i-1] < A[i] > A[i+1] > ... > A[A.length - 1].

    Example 1:

    Input: [0,1,0]
    Output: 1
    Example 2:

    Input: [0,2,1,0]
    Output: 1
    Note:

    3 <= A.length <= 10000
    0 <= A[i] <= 10^6
    A is a mountain, as defined above.
 * 
 * Solution
 * https://leetcode.com/problems/peak-index-in-a-mountain-array/discuss/139840/Java-O(n)-and-O(log(n))-code
*/
class Solution {
    public int peakIndexInMountainArray(int[] A) {
        if(A == null || A.length == 0) {
            return -1;
        }
        int start = 0;
        int end = A.length - 1;
        while(start + 1 < end) {
            int mid = start + (end - start) / 2;
            if(A[mid + 1] < A[mid]) {
                end = mid;
            } else if(A[mid - 1] < A[mid]) {
                start = mid;
            } else {
                start = mid;
            }
        }
        if(A[start] < A[end]) {
            return end;
        }
        return start;
    }
}
