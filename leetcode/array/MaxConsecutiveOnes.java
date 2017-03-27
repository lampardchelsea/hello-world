/**
 * Given a binary array, find the maximum number of consecutive 1s in this array.
    Example 1:
    Input: [1,1,0,1,1,1]
    Output: 3
 * Explanation: The first two digits or the last three digits are consecutive 1s.
 *     The maximum number of consecutive 1s is 3.
 * Note:
 * The input array will only contain 0 and 1.
 * The length of input array is a positive integer and will not exceed 10,000
*/
// Solution 1: Quick solution --> 12ms (because no Math.max check)
public class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int count = 0;
        int max = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 1) {
                count++;
                // If already the last digit and still
                // not encounter 0 and last digit is 1, 
                // then count > max, because max only 
                // store cases not reach last digit or
                // last digit not 1
                if(i == nums.length - 1) {
                    return max > count ? max : count;
                }
            } else {
                // If not reach the last digit or last digit
                // is 0, then no matter how many times 
                // encounter 0, only store the largest count 
                // value into max
                if(max < count) {
                    max = count;
                }
                count = 0;
            }
        }
        return max;
    }
}

// Solution 2: 15ms
// Refer to
// https://discuss.leetcode.com/topic/75430/easy-java-solution
public class Solution {
    public int findMaxConsecutiveOnes(int[] nums) {
        int count = 0;
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
            	count++;
            	max = Math.max(count, max);
            } else {
                count = 0;
            }
        }
        return max;
    }
}





