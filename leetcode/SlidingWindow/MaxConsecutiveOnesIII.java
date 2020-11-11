/**
Refer to
https://leetcode.com/problems/max-consecutive-ones-iii/
Given an array A of 0s and 1s, we may change up to K values from 0 to 1.

Return the length of the longest (contiguous) subarray that contains only 1s. 

Example 1:
Input: A = [1,1,1,0,0,0,1,1,1,1,0], K = 2
Output: 6
Explanation: 
[1,1,1,0,0,1,1,1,1,1,1]
Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.

Example 2:
Input: A = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
Output: 10
Explanation: 
[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
Bolded numbers were flipped from 0 to 1.  The longest subarray is underlined.

Note:
1 <= A.length <= 20000
0 <= K <= A.length
A[i] is 0 or 1
*/

// Solution 1: Not fixed length window using two pointers + criteria
// Refer to
// https://medium.com/@zengruiwang/sliding-window-technique-360d840d5740
/**
Two pointers + criteria: the window size is not fixed, usually it asks you to find the subarray that meets the criteria, 
for example, given an array of integers, find the number of subarrays whose sum is equal to a target value.
*/

// Refer to
// https://leetcode.com/problems/max-consecutive-ones-iii/discuss/247564/JavaC%2B%2BPython-Sliding-Window
/**
Intuition
Translation:
Find the longest subarray with at most K zeros.

Explanation
For each A[j], try to find the longest subarray.
If A[i] ~ A[j] has zeros <= K, we continue to increment j.
If A[i] ~ A[j] has zeros > K, we increment i (as well as j).

Java:
    public int longestOnes(int[] A, int K) {
        int i = 0, j;
        for (j = 0; j < A.length; ++j) {
            if (A[j] == 0) K--;
            if (K < 0 && A[i++] == 0) K++;
        }
        return j - i;
    }
*/

// https://leetcode.com/problems/max-consecutive-ones-iii/discuss/248287/java-sliding-windows-with-comments-in-line
// https://leetcode.com/problems/max-consecutive-ones-iii/discuss/248287/java-sliding-windows-with-comments-in-line/716263
/**
http://www.noteanddata.com/leetcode-1004-Max-Consecutive-Ones-III-java-sliding-window-solution-note.html
below are a sliding windows version with comments inline, this might not be concise enough, but easy to understand

public int longestOnes(int[] A, int K) {
    int max = 0;
    int zeroCount = 0; // zero count in current window
    int i = 0; // slow pointer
    for(int j = 0; j < A.length; ++j) {
        if(A[j] == 0) { // move forward j, if current is 0, increase the zeroCount
            zeroCount++;
        }
        
        // when current window has more than K, the window is not valid any more
        // we need to loop the slow pointer until the current window is valid
        while(zeroCount > K) {  
            if(A[i] == 0) {
                zeroCount--;
            }
            i++;
        }
        max = Math.max(max, j-i+1); // everytime we get here, the current window is valid 
    }
    return max;
}

you don't need to do "while (zeroCount > K)" since zeroCount can only be greater than K by 1 maximum at each iteration
*/
class Solution {
    public int longestOnes(int[] A, int K) {
        int maxLen = 0;
        int zeroCount = 0;
        int i = 0;
        for(int j = 0; j < A.length; j++) {
            if(A[j] == 0) {
                zeroCount++;
            }
            if(zeroCount > K) {
                if(A[i] == 0) {
                    zeroCount--;
                }
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}
