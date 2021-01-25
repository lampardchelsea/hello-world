/**
Refer to
https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/
Given an integer array sorted in non-decreasing order, there is exactly one integer in the array that occurs more than 25% of the time.
Return that integer.
Example 1:
Input: arr = [1,2,2,6,6,6,6,7,10]
Output: 6
Constraints:
1 <= arr.length <= 10^4
0 <= arr[i] <= 10^5
*/

// Solution 1: Native freq map
class Solution {
    public int findSpecialInteger(int[] arr) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int a : arr) {
            freq.put(a, freq.getOrDefault(a, 0) + 1);
        }
        int result = -1;
        for(int k : freq.keySet()) {
            if(freq.get(k) > arr.length / 4) {
                result = k;
            }
        }
        return result;
    }
}

// Solution 2: Binary Search
