/**
Refer to
https://leetcode.com/problems/longest-mountain-in-array/
You may recall that an array arr is a mountain array if and only if:

arr.length >= 3
There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
Given an integer array arr, return the length of the longest subarray, which is a mountain. Return 0 if there is no mountain subarray.

Example 1:
Input: arr = [2,1,4,7,3,2,5]
Output: 5
Explanation: The largest mountain is [1,4,7,3,2] which has length 5.

Example 2:
Input: arr = [2,2,2]
Output: 0
Explanation: There is no mountain.

Constraints:
1 <= arr.length <= 104
0 <= arr[i] <= 104

Follow up:
Can you solve it using only one pass?
Can you solve it in O(1) space?
*/

// Solution 1: Two Pass
// Refer to
// https://leetcode.com/problems/longest-mountain-in-array/discuss/135593/C%2B%2BJavaPython-1-pass-and-O(1)-space
/**
Intuition:
We have already many 2-pass or 3-pass problems, like 821. Shortest Distance to a Character.
They have almost the same idea.
One forward pass and one backward pass.
Maybe another pass to get the final result, or you can merge it in one previous pass.

Explanation:
In this problem, we take one forward pass to count up hill length (to every point).
We take another backward pass to count down hill length (from every point).
Finally a pass to find max(up[i] + down[i] + 1) where up[i] and down[i] should be positives.

Time Complexity:
O(N)

Java:
    public int longestMountain(int[] A) {
        int N = A.length, res = 0;
        int[] up = new int[N], down = new int[N];
        for (int i = N - 2; i >= 0; --i) if (A[i] > A[i + 1]) down[i] = down[i + 1] + 1;
        for (int i = 0; i < N; ++i) {
            if (i > 0 && A[i] > A[i - 1]) up[i] = up[i - 1] + 1;
            if (up[i] > 0 && down[i] > 0) res = Math.max(res, up[i] + down[i] + 1);
        }
        return res;
    }
*/
class Solution {
    public int longestMountain(int[] arr) {
        int N = arr.length;
        int[] up = new int[N];
        int[] down = new int[N];
        int count = 0;
        for(int i = 0; i < N; i++) {
            if(i > 0 && arr[i] > arr[i - 1]) {
                up[i] = up[i - 1] + 1;
            }
        }
        for(int i = N - 2; i >= 0; i--) {
            if(arr[i] > arr[i + 1]) {
                down[i] = down[i + 1] + 1;
            }
            if(up[i] > 0 && down[i] > 0) {
                count = Math.max(count, up[i] + down[i] + 1);
            }
        }
        return count;
    }
}

// Follow up:
// Can you solve it using only one pass?
// Can you solve it in O(1) space?
// In this solution, I count up length and down length.
// Both up and down length are clear to 0 when A[i - 1] == A[i] or down > 0 && A[i - 1] < A[i].
/**
Java:
    public int longestMountain(int[] A) {
        int res = 0, up = 0, down = 0;
        for (int i = 1; i < A.length; ++i) {
            if (down > 0 && A[i - 1] < A[i] || A[i - 1] == A[i]) up = down = 0;
            if (A[i - 1] < A[i]) up++;
            if (A[i - 1] > A[i]) down++;
            if (up > 0 && down > 0 && up + down + 1 > res) res = up + down + 1;
        }
        return res;
    }
*/
class Solution {
    public int longestMountain(int[] arr) {
        int up = 0;
        int down = 0;
        int count = 0;
        for(int i = 1; i < arr.length; i++) {
            if(down > 0 && arr[i - 1] < arr[i] || arr[i - 1] == arr[i]) {
                up = 0;
                down = 0;
            }
            if(arr[i - 1] < arr[i]) {
                up++;
            }
            if(arr[i - 1] > arr[i]) {
                down++;
            }
            if(up > 0 && down > 0 && up + down + 1 > count) {
                count = up + down + 1;
            }
        }
        return count;
    }
}


