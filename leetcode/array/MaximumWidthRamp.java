/**
Refer to
https://leetcode.com/problems/maximum-width-ramp/
Given an array A of integers, a ramp is a tuple (i, j) for which i < j and A[i] <= A[j].  The width of such a ramp is j - i.

Find the maximum width of a ramp in A.  If one doesn't exist, return 0.

Example 1:
Input: [6,0,8,2,1,5]
Output: 4
Explanation: 
The maximum width ramp is achieved at (i, j) = (1, 5): A[1] = 0 and A[5] = 5.

Example 2:
Input: [9,8,1,0,1,9,4,0,4,1]
Output: 7
Explanation: 
The maximum width ramp is achieved at (i, j) = (2, 9): A[2] = 1 and A[9] = 1.

Note:
2 <= A.length <= 50000
0 <= A[i] <= 50000
*/

// Solution 1: Two arrays
// Refer to
// https://leetcode.com/problems/maximum-width-ramp/discuss/209582/O(n)-time-O(n)-space-using-two-array
/**
Use two array to solve this problem. min_arr[i] is the minimum of the first i + 1 elements of A, max_arr[j] is the maximum 
of the last len(A) - j elements of A.

The idea of this solution is: Suppose (i, j) is the answer, then A[i] must be the smallest element among the first i + 1 
elements of A and A[j] must be the largeset element among the last len(A) - j elements of A. Otherwise, we can always 
find an element smaller than A[i] or larger than A[j], so that (i, j) is not the maximum width ramp.

For example, the input is [6, 0, 8, 2, 1, 5]. Then min_arr=[6, 0, 0, 0, 0, 0] and max_arr=[8, 8, 8, 5, 5, 5].

We can find the ramp use two points, left and right. left starts from the beginning of min_arr[i] and right starts from 
the beginning of max_arr[i]. Increase right by 1 when min_arr[left] <= max_arr[right], else increase left by 1.

def maxWidthRamp(self, A):
    min_arr = [A[0]] * len(A)
    for i in range(1, len(A)):
        min_arr[i] = min(min_arr[i - 1], A[i])
    max_arr = [A[-1]] * len(A)
    for i in range(len(A) - 2, -1, -1):
        max_arr[i] = max(max_arr[i + 1], A[i])

    ans = 0
    left = 0
    right = 0
    while right < len(A):
        if min_arr[left] <= max_arr[right]:
            ans = max(ans, right - left)
            right += 1
        else:
            left += 1
    return ans
*/
class Solution {
    public int maxWidthRamp(int[] A) {
        int n = A.length;
        // min_arr[i] is the minimum of the first i + 1 elements of A
        int[] min_arr = new int[n];
        min_arr[0] = A[0];
        for(int i = 1; i < n; i++) {
            min_arr[i] = Math.min(min_arr[i - 1], A[i]);    
        }
        // max_arr[j] is the maximum of the last len(A) - j elements of A
        int[] max_arr = new int[n];
        max_arr[n - 1] = A[n - 1];
        for(int i = n - 2; i >= 0; i--) {
            max_arr[i] = Math.max(max_arr[i + 1], A[i]);
        }
        int result = 0;
        int j = 0;
        int i = 0;
        while(j < n) {
            if(min_arr[i] <= max_arr[j]) {
                result = Math.max(result, j - i);
                j++;
            } else {
                i++;
            }
        }
        return result;
    }
}
