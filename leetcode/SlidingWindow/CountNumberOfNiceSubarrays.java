/**
Refer to
https://leetcode.com/problems/count-number-of-nice-subarrays/
Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on it.

Return the number of nice sub-arrays.

Example 1:
Input: nums = [1,1,2,1,1], k = 3
Output: 2
Explanation: The only sub-arrays with 3 odd numbers are [1,1,2,1] and [1,2,1,1].

Example 2:
Input: nums = [2,4,6], k = 1
Output: 0
Explanation: There is no odd numbers in the array.

Example 3:
Input: nums = [2,2,2,1,2,2,1,2,2,2], k = 2
Output: 16

Constraints:
1 <= nums.length <= 50000
1 <= nums[i] <= 10^5
1 <= k <= nums.length
*/

// Solution 1: Not fixed sliding window + Two Pointers atMost solution --> Same as 992. Subarrays With K Different Integers
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/SubarraysWithKDifferentIntegers.java
// https://leetcode.com/problems/count-number-of-nice-subarrays/discuss/824621/C%2B%2B-Sliding-Window-Solution-O(1)-Space
/**
Solution 1: atMost
Have you read this? 992. Subarrays with K Different Integers
Exactly K times = at most K times - at most K - 1 times
Complexity
Time O(N) for one pass
Space O(1)
Java:
    public int numberOfSubarrays(int[] A, int k) {
        return atMost(A, k) - atMost(A, k - 1);
    }

    public int atMost(int[] A, int k) {
        int res = 0, i = 0, n = A.length;
        for (int j = 0; j < n; j++) {
            k -= A[j] % 2;
            while (k < 0)
                k += A[i++] % 2;
            res += j - i + 1;
        }
        return res;
    }
*/
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        return helper(nums, k) - helper(nums, k - 1);
    }
    
    private int helper(int[] nums, int k) {
        int i = 0;
        int oddCount = 0;
        int result = 0;
        for(int j = 0; j < nums.length; j++) {
            if(nums[j] % 2 == 1) {
                oddCount++;
            }
            while(i <= j && oddCount > k) {
                if(nums[i] % 2 == 1) {
                    oddCount--;
                }
                i++;
            }
            result += j - i + 1; // Add all possible subarray cases between index i to j
        }
        return result;
    } 
}

// Solution 2: Not fixed sliding window + Three Pointers
// Refer to
// https://leetcode.com/problems/count-number-of-nice-subarrays/discuss/419378/JavaC%2B%2BPython-Sliding-Window-O(1)-Space
// https://leetcode.com/problems/count-number-of-nice-subarrays/discuss/419378/JavaC++Python-Sliding-Window-O(1)-Space/458810
/**
Basically it's same as three pointers, since it uses count. If we want to do this way, it should be something like:
    def numberOfSubarrays2(self, nums, k):
        i = count = nice_count = odd_count = 0
        for j in range(len(nums)):
            if nums[j] % 2 == 1:
                odd_count += 1
                count = 0
            while odd_count == k:
                odd_count -= nums[i] % 2
                i += 1
                count += 1
            nice_count += count
        return nice_count
*/
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int i = 0;
        int oddCount = 0;
        int count = 0;
        int result = 0;
        for(int j = 0; j < nums.length; j++) {
            if(nums[j] % 2 == 1) {
                oddCount++;
                count = 0;
            }
            while(oddCount == k) {
                if(nums[i] % 2 == 1) {
                    oddCount--;
                }
                i++;
                count++;
            }
            result += count;
        }
        return result;
    }
}

