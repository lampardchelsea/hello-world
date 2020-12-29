/**
Refer to
https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/
Given an array arr, replace every element in that array with the greatest element among the elements to its right, 
and replace the last element with -1.

After doing so, return the array.

Example 1:
Input: arr = [17,18,5,4,6,1]
Output: [18,6,6,6,1,-1]

Constraints:
1 <= arr.length <= 10^4
1 <= arr[i] <= 10^5
*/

// Solution 1: Traverse from right to left
// Refer to
// https://leetcode.com/problems/replace-elements-with-greatest-element-on-right-side/discuss/463227/JavaPython-3-Scan-from-right-to-left.
class Solution {
    public int[] replaceElements(int[] arr) {
        int n = arr.length;
        int curMax = -1;
        for(int i = n - 1; i >= 0; i--) {
            int temp = arr[i];
            arr[i] = curMax;
            curMax = Math.max(curMax, temp);
        }
        return arr;
    }
}
