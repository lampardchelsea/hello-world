/**
Refer to
https://leetcode.com/problems/find-the-highest-altitude/
There is a biker going on a road trip. The road trip consists of n + 1 points at different altitudes. 
The biker starts his trip on point 0 with altitude equal 0.

You are given an integer array gain of length n where gain[i] is the net gain in altitude between points i 
and i + 1 for all (0 <= i < n). Return the highest altitude of a point.

Example 1:
Input: gain = [-5,1,5,0,-7]
Output: 1
Explanation: The altitudes are [0,-5,-4,1,1,-6]. The highest is 1.

Example 2:
Input: gain = [-4,-3,-2,-1,4,3,2]
Output: 0
Explanation: The altitudes are [0,-4,-7,-9,-10,-6,-3,-1]. The highest is 0.

Constraints:
n == gain.length
1 <= n <= 100
-100 <= gain[i] <= 100
*/

// Solution 1: With additional array
class Solution {
    public int largestAltitude(int[] gain) {
        int len = gain.length;
        int[] ori = new int[len + 1];
        ori[0] = 0;
        for(int i = 1; i <= len; i++) {
            ori[i] = ori[i - 1] + gain[i - 1];
        }
        int max = -101;
        for(int i = 0; i <= len; i++) {
            max = Math.max(ori[i], max);
        }
        return max;
    }
}

// Solution 2: Without additional array
class Solution {
    public int largestAltitude(int[] gain) {
        int cur = 0;
        int max = -101;
        for(int i = 0; i < gain.length; i++) {
            cur += gain[i];
            max = Math.max(cur, max);
        }
        return Math.max(0, max);
    }
}
