/**
Refer to
https://leetcode.com/problems/check-if-n-and-its-double-exist/
Given an array arr of integers, check if there exists two integers N and M such that N is the double of M ( i.e. N = 2 * M).

More formally check if there exists two indices i and j such that :

i != j
0 <= i, j < arr.length
arr[i] == 2 * arr[j]

Example 1:
Input: arr = [10,2,5,3]
Output: true
Explanation: N = 10 is the double of M = 5,that is, 10 = 2 * 5.

Example 2:
Input: arr = [7,1,14,11]
Output: true
Explanation: N = 14 is the double of M = 7,that is, 14 = 2 * 7.

Example 3:
Input: arr = [3,1,7,11]
Output: false
Explanation: In this case does not exist N and M, such that N = 2 * M.

Constraints:
2 <= arr.length <= 500
-10^3 <= arr[i] <= 10^3
*/

// Wrong Solution: Failed on one case --> Input: [-2,0,10,-19,4,6,-8], Output: true, Expected: false
// The issue happens relate to '0' --> 0 * 2 = 0, we cannot use only set to identify it, have to use HashMap
class Solution {
    public boolean checkIfExist(int[] arr) {
        Set<Integer> set = new HashSet<Integer>();
        for(int a : arr) {
            set.add(a);
        }
        for(int a : set) {
            if(set.contains(a * 2)) {
                return true;
            }
        }
        return false;
    }
}

// Solution 1: Use seen set to filter on the fly
// Refer to
// https://leetcode.com/problems/check-if-n-and-its-double-exist/discuss/503441/JavaPython-3-HashSet-w-analysis.
/**
Q & A:
Q: Can you please explain this line ?

if (seen.contains(2 * i) || i % 2 == 0 && seen.contains(i / 2))
A: i is half or 2 times of a number in seen.
*/
class Solution {
    public boolean checkIfExist(int[] arr) {
        Set<Integer> set = new HashSet<Integer>();
        for(int a : arr) {
            if(set.contains(a * 2) || (a % 2 == 0 && set.contains(a / 2))) {
                return true;
            }
            set.add(a);
        }
        return false;
    }
}
