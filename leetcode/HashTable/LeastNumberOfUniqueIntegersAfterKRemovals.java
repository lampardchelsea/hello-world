/**
Refer to
https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/
Given an array of integers arr and an integer k. Find the least number of unique integers after removing exactly k elements.

Example 1:
Input: arr = [5,5,4], k = 1
Output: 1
Explanation: Remove the single 4, only 5 is left.

Example 2:
Input: arr = [4,3,1,1,3,3,2], k = 3
Output: 2
Explanation: Remove 4, 2 and either one of the two 1s or three 3s. 1 and 3 will be left.

Constraints:
1 <= arr.length <= 10^5
1 <= arr[i] <= 10^9
0 <= k <= arr.length
*/

// Solution 1: Java Simple HashMap and Lambda Sort key by values
// Refer to
// https://leetcode.com/problems/least-number-of-unique-integers-after-k-removals/discuss/686669/Java-Simple-HashMap-and-Lambda-Sort
class Solution {
    public int findLeastNumOfUniqueInts(int[] arr, int k) {
        Map<Integer, Integer> freq = new HashMap<Integer, Integer>();
        for(int a : arr) {
            freq.put(a, freq.getOrDefault(a, 0) + 1);
        }
        List<Integer> list = new ArrayList<Integer>(freq.keySet());
        Collections.sort(list, (a, b) -> freq.get(a) - freq.get(b));
        int n = freq.size();
        int i = 0;
        while(k > 0) {
            k -= freq.get(list.get(i));
            // Adding k >= 0 condition in case we cannot even move out one number
            // since smallest count still more than k 
            // e.g 5,5,5,3,3 k=1, smallest count as 2 happen on 3, but just need
            // remove one of 3, still left one 3, so still two numbers there
            if(k >= 0) {
                i++;
            }
        }
        return n - i;
    }
}

// Wrong solution: We should not assume first n/k numbers means n/k groups start
// Test out by
// [12,12,2,11,22,20,11,13,3,21,1,13]
// 3
// 1,2,3
// 11,12,13
// 11,12,13
// 20,21,22

class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        int n = nums.length;
        if(n % k != 0) {
            return false;
        }
        int groups = n / k;
        Set<Integer> set = new HashSet<Integer>();
        for(int num : nums) {
            set.add(num);
        }
        // Find first smallest n/k numbers in the array
        // then check from 'start' to 'start + k - 1' if exist in hashset
        Arrays.sort(nums);
        for(int i = 0; i < groups; i++) {
            int start = nums[i];
            for(int j = 0; j < k; j++) {
                if(!set.contains(start + j)) {
                    return false;
                }
            }
        }
        return true;
    }
}


