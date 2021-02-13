/**
Refer to
https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
Given an array of integers nums and a positive integer k, find whether it's possible to divide this array into sets of k consecutive numbers
Return True if it is possible. Otherwise, return False.

Example 1:
Input: nums = [1,2,3,3,4,4,5,6], k = 4
Output: true
Explanation: Array can be divided into [1,2,3,4] and [3,4,5,6].

Example 2:
Input: nums = [3,2,1,2,3,4,3,4,5,9,10,11], k = 3
Output: true
Explanation: Array can be divided into [1,2,3] , [2,3,4] , [3,4,5] and [9,10,11].

Example 3:
Input: nums = [3,3,2,2,1,1], k = 3
Output: true

Example 4:
Input: nums = [1,2,3,4], k = 3
Output: false
Explanation: Each array should be divided in subarrays of size 3.

Constraints:
1 <= k <= nums.length <= 105
1 <= nums[i] <= 109

Note: This question is the same as 846: https://leetcode.com/problems/hand-of-straights/
*/

// Solution 1: TreeMap + Greedy remove
// Refer to
// https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/discuss/457569/C%2B%2B-Greedy
// https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/discuss/457569/C++-Greedy/739545
/**
We can use a greedy approach and start with the smallest number and see if the numbers from that number + k exist 
and then keep removing them from the numbers we have, if there is a case where it's not possible then we return false.
*/
class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        for(int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1); // O(nlog(n)) time.
        }
        while(map.size() > 0) {
            int min = map.firstKey(); // O(log(freqMap.size())) time.
            int minFreq = map.get(min); // O(log(freqMap.size())) time.
            // Check if we can create minFreq sequences of k consecutive 
            // numbers with the min as the first number.
            for(int i = 0; i < k; i++) {
                if(map.containsKey(min + i) && map.get(min + i) >= minFreq) {
                    map.put(min + i, map.get(min + i) - minFreq);
                    // Remove min + i if there are no more occurrences left.
                    if(map.get(min + i) == 0) {
                        map.remove(min + i);
                    }
                } else {
                    // min doesn't belong to a group of k consecutive numbers 
                    // so we return false.
                    return false;
                }
            }
        }
        // We managed to put every number into a group of k consecutive numbers.
        return true;
    }
}


