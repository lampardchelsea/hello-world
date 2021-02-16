/**
Refer to
https://leetcode.com/problems/continuous-subarray-sum/
Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous subarray 
of size at least 2 that sums up to a multiple of k, that is, sums up to n*k where n is also an integer.

Example 1:
Input: [23, 2, 4, 6, 7],  k=6
Output: True
Explanation: Because [2, 4] is a continuous subarray of size 2 and sums up to 6.

Example 2:
Input: [23, 2, 6, 4, 7],  k=6
Output: True
Explanation: Because [23, 2, 6, 4, 7] is an continuous subarray of size 5 and sums up to 42.

Constraints:
The length of the array won't exceed 10,000.
You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
*/

// Solution 1: 
// Refer to
// https://leetcode.com/problems/continuous-subarray-sum/discuss/99499/Java-O(n)-time-O(k)-space
/**
We iterate through the input array exactly once, keeping track of the running sum mod k of the elements in the process. 
If we find that a running sum value at index j has been previously seen before in some earlier index i in the array, then 
we know that the sub-array (i,j] contains a desired sum.

public boolean checkSubarraySum(int[] nums, int k) {
    Map<Integer, Integer> map = new HashMap<>(){{put(0,-1);}};;
    int runningSum = 0;
    for (int i=0;i<nums.length;i++) {
        runningSum += nums[i];
        if (k != 0) runningSum %= k; 
        Integer prev = map.get(runningSum);
        if (prev != null) {
            if (i - prev > 1) return true;
        }
        else map.put(runningSum, i);
    }
    return false;
}
*/

// https://leetcode.com/problems/continuous-subarray-sum/discuss/99499/Java-O(n)-time-O(k)-space/242302
/**
/** Key point: if we can find any two subarray of prefix sum have same mod value, then their difference MUST be
 * divisible by k. So we can use a map to store mod value of each prefix sum in map, with its index. Then check
 * if map contains the same mod value with size > 2 when we have new mod value in every iteration */
public boolean checkSubarraySum(int[] nums, int k) {
    if (nums.length < 2) {
        return false;
    }

    Map<Integer, Integer> map = new HashMap<>();
    // corner case: if the very first subarray with first two numbers in array could form the result, we need to 
    // put mod value 0 and index -1 to make it as a true case
    map.put(0, -1);
    int curSum = 0;
    for (int i = 0; i < nums.length; i++) {
        curSum += nums[i];

        // corner case: k CANNOT be 0 when we use a number mod k
        if (k != 0) {
            curSum = curSum % k;
        }
        if (map.containsKey(curSum)) {
            if (i - map.get(curSum) > 1) {
                return true;
            }
        }
        else {
            map.put(curSum, i);
        }
    }
    return false;
}
*/

// https://leetcode.com/problems/continuous-subarray-sum/discuss/99503/Need-to-pay-attention-to-a-lot-of-corner-cases...
/**
This problem contributed a lot of bugs to my contest score... Let's read the description again, pay attention to red sections:

Given a list of non-negative numbers and a target integer k, write a function to check if the array has a continuous subarray 
of size at least 2 that sums up to the multiple of k, that is, sums up to n*k where n is also an integer.

Some damn it! test cases:

[0], 0 -> false;
[5, 2, 4], 5 -> false;
[0, 0], 100 -> true;
[1,5], -6 -> true;
etc...
public class Solution {
    public boolean checkSubarraySum(int[] nums, int k) {
        // Since the size of subarray is at least 2.
        if (nums.length <= 1) return false;
        // Two continuous "0" will form a subarray which has sum = 0. 0 * k == 0 will always be true.
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0 && nums[i + 1] == 0) return true;
        }

        // At this point, k can't be "0" any longer.
        if (k == 0) return false;
        // Let's only check positive k. Because if there is a n makes n * k = sum, it is always true -n * -k = sum.
        if (k < 0) k = -k;

        Map<Integer, Integer> sumToIndex = new HashMap<>();
        int sum = 0;
        sumToIndex.put(0, -1);

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // Validate from the biggest possible n * k to k
            for (int j = (sum / k) * k; j >= k; j -= k) {
                if (sumToIndex.containsKey(sum - j) && (i - sumToIndex.get(sum - j) > 1)) return true;
            }
            if (!sumToIndex.containsKey(sum)) sumToIndex.put(sum, i);
        }

        return false;
    }
}
*/

class Solution {
    // so many traps here : k can be 0, element of the array can be 0, and also 
    // a single element is not allowed, at least two continuous elements
    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // Not map.put(0, 1) here, since 1 is for count, but -1 for index,
        // If we find that a running sum value at index j has been previously 
        // seen before in some earlier index i in the array, then we know that 
        // the sub-array (i,j] contains a desired sum.
        // If the very first subarray with first two numbers in array could 
        // form the result, we need to put mod value 0 and index -1 to make 
        // it as a true case
        map.put(0, -1);
        int sum = 0;
        // We iterate through the input array exactly once, keeping track of 
        // the running sum mod k of the elements in the process.
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // When modula k cannot be 0
            if(k != 0) {
                // Same way as 974.Subarray Sums Divisible By K
                sum = sum % k;
            }
            // map.get(sum) will get previous seen index for sum
            if(map.containsKey(sum)) {
                // Limitation as: continuous subarray of size at least 2 that 
                // sums up to a multiple of k
                if(i - map.get(sum) > 1) {
                    return true;
                }
            } else {
                map.put(sum, i);
            }         
        }
        return false;
    }
}
