/**
Refer to
https://leetcode.com/problems/valid-mountain-array/
Given an array of integers arr, return true if and only if it is a valid mountain array.

Recall that arr is a mountain array if and only if:
arr.length >= 3
There exists some i with 0 < i < arr.length - 1 such that:
arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
arr[i] > arr[i + 1] > ... > arr[arr.length - 1]

Example 1:
Input: arr = [2,1]
Output: false

Example 2:
Input: arr = [3,5,5]
Output: false

Example 3:
Input: arr = [0,3,2,1]
Output: true

Constraints:
1 <= arr.length <= 104
0 <= arr[i] <= 104
*/

// Solution 1: One person climb mountain.
// Refer to
// https://leetcode.com/problems/valid-mountain-array/discuss/194900/C++JavaPython-Climb-Mountain/229316
class Solution {
    public boolean validMountainArray(int[] arr) {
        // Test out arr[0] >= arr[1] case by [9,8,7,6,5,4,3,2,1,0]
        if(arr.length <= 2 || arr[0] >= arr[1]) {
            return false;
        }
        int i = 1;
        while(i < arr.length - 1 && arr[i] > arr[i - 1]) {
            i++;
        }
        while(i < arr.length && arr[i] < arr[i - 1]) {
            i++;
        }
        return i == arr.length;
    }
}

// Solution 2: Two people climb from left and from right separately
// Refer to
// https://leetcode.com/problems/valid-mountain-array/discuss/194900/C%2B%2BJavaPython-Climb-Mountain
/**
Two people climb from left and from right separately.
If they are climbing the same mountain,
they will meet at the same point.
*/

