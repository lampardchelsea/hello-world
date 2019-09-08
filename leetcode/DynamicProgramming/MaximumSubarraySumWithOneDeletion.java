/**
 Refer to
 https://leetcode.com/problems/maximum-subarray-sum-with-one-deletion/
 Given an array of integers, return the maximum sum for a non-empty subarray (contiguous elements) with 
 at most one element deletion. In other words, you want to choose a subarray and optionally delete one 
 element from it so that there is still at least one element left and the sum of the remaining elements 
 is maximum possible.

Note that the subarray needs to be non-empty after deleting one element.

Example 1:
Input: arr = [1,-2,0,3]
Output: 4
Explanation: Because we can choose [1, -2, 0, 3] and drop -2, thus the subarray [1, 0, 3] becomes the maximum value.

Example 2:
Input: arr = [1,-2,-2,3]
Output: 3
Explanation: We just choose [3] and it's the maximum sum.

Example 3:
Input: arr = [-1,-1,-1,-1]
Output: -1
Explanation: The final subarray needs to be non-empty. You can't choose [-1] and delete -1 from it, then get 
an empty subarray to make the sum equals to 0.

Constraints:
1 <= arr.length <= 10^5
-10^4 <= arr[i] <= 10^4
*/

// Solution 1: Similar way as leetcode 53. Maximum Subarray (Kadane’s algorithm)
// Refer to
// https://www.geeksforgeeks.org/maximum-sum-subarray-removing-one-element/
/**
If element removal condition is not applied, we can solve this problem using Kadane’s algorithm but 
here one element can be removed also for increasing maximum sum. This condition can be handled using 
two arrays, forward and backward array, these arrays store the current maximum subarray sum from starting 
to ith index, and from ith index to ending respectively.
In below code, two loops are written, first one stores maximum current sum in forward direction in fw[] 
and other loop stores the same in backward direction in bw[]. Getting current maximum and updation is same 
as Kadane’s algorithm.
Now when both arrays are created, we can use them for one element removal conditions as follows, at each 
index i, maximum subarray sum after ignoring i’th element will be fw[i-1] + bw[i+1] so we loop for all 
possible i values and we choose maximum among them.
Total time complexity and space complexity of solution is O(N)
*/

// https://leetcode.com/problems/maximum-subarray/
// https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/
/**
 Kadane’s Algorithm:

Initialize:
    max_so_far = 0
    max_ending_here = 0

Loop for each element of the array
  (a) max_ending_here = max_ending_here + a[i]
  (b) if(max_ending_here < 0)
            max_ending_here = 0
  (c) if(max_so_far < max_ending_here)
            max_so_far = max_ending_here
return max_so_far
Explanation:
Simple idea of the Kadane’s algorithm is to look for all positive contiguous segments of the array 
(max_ending_here is used for this). And keep track of maximum sum contiguous segment among all 
positive segments (max_so_far is used for this). Each time we get a positive sum compare it with 
max_so_far and update max_so_far if it is greater than max_so_far
*/
class Solution {
    public int maximumSum(int[] arr) {
        int n = arr.length;
        int[] fw = new int[n];
        int[] bw = new int[n];
        fw[0] = arr[0];
        int max_so_far = arr[0];
        for(int i = 1; i < n; i++) {
            fw[i] = Math.max(arr[i], fw[i - 1] + arr[i]);
            max_so_far = Math.max(fw[i], max_so_far);
        }
        bw[n - 1] = arr[n - 1];
        for(int i = n - 2; i >= 0; i--) {
            bw[i] = Math.max(arr[i], bw[i + 1] + arr[i]);
            max_so_far = Math.max(bw[i], max_so_far);
        }
        int result = max_so_far;
        for(int i = 1; i < n - 1; i++) {
            result = Math.max(result, fw[i - 1] + bw[i + 1]);
        }
        return result;
    }
}
