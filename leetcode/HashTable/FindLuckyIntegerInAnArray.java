/**
Refer to
https://leetcode.com/problems/find-lucky-integer-in-an-array/
Given an array of integers arr, a lucky integer is an integer which has a frequency in the array equal to its value.

Return a lucky integer in the array. If there are multiple lucky integers return the largest of them. If there is no lucky integer return -1.

Example 1:
Input: arr = [2,2,3,4]
Output: 2
Explanation: The only lucky number in the array is 2 because frequency[2] == 2.

Example 2:
Input: arr = [1,2,2,3,3,3]
Output: 3
Explanation: 1, 2 and 3 are all lucky numbers, return the largest of them.

Example 3:
Input: arr = [2,2,2,3,3]
Output: -1
Explanation: There are no lucky numbers in the array.

Example 4:
Input: arr = [5]
Output: -1

Example 5:
Input: arr = [7,7,7,7,7,7,7]
Output: 7

Constraints:
1 <= arr.length <= 500
1 <= arr[i] <= 500
*/

// Solution 1: HashMap
// Style 1:
class Solution {
    public int findLucky(int[] arr) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int a : arr) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        int result = -1;
        int max = 0;
        for(int k : map.keySet()) {
            if(k == map.get(k)) {
                if(k == max) {
                    result = k;
                } else if(k > max) {
                    max = k;
                    result = k;
                }
            }
        }
        return result;
    }
}

// Style 2:
class Solution {
    public int findLucky(int[] arr) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int a : arr) {
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        int result = -1;
        for(int k : map.keySet()) {
            if(k == map.get(k)) {
                result = Math.max(k, result);
            }
        }
        return result;
    }
}

// Solution 2: Bucket from maximum to minimum number in range
// Refer to
// https://leetcode.com/problems/find-lucky-integer-in-an-array/discuss/554838/JavaPython-3-Two-similar-clean-codes%3A-array-and-HashMapCounter.
class Solution {
    public int findLucky(int[] arr) {
        int[] freq = new int[501];
        for(int a : arr) {
            freq[a]++;
        }
        for(int i = 500; i > 0; i--) {
            if(freq[i] == i) {
                return i;
            }
        }
        return -1;
    }
}
