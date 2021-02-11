/**
Refer to
https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/
Given a binary array nums, you should delete one element from it.

Return the size of the longest non-empty subarray containing only 1's in the resulting array.

Return 0 if there is no such subarray.

Example 1:
Input: nums = [1,1,0,1]
Output: 3
Explanation: After deleting the number in position 2, [1,1,1] contains 3 numbers with value of 1's.

Example 2:
Input: nums = [0,1,1,1,0,1,1,0,1]
Output: 5
Explanation: After deleting the number in position 4, [0,1,1,1,1,1,0,1] longest subarray with value of 1's is [1,1,1,1,1].

Example 3:
Input: nums = [1,1,1]
Output: 2
Explanation: You must delete one element.

Example 4:
Input: nums = [1,1,0,0,1,1,1,0,1]
Output: 4

Example 5:
Input: nums = [0,0,0]
Output: 0

Constraints:
1 <= nums.length <= 10^5
nums[i] is either 0 or 1.
*/

// Same as Max Consecutive Ones II
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/MaxConsecutiveOnesII.java

// Solution 1: Only need if(nums[j] == 0) not while loop to find previous 0 ?
class Solution {
    public int longestSubarray(int[] nums) {
        int maxLen = 0;
        int zeroCount = 0;
        int j = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                zeroCount++;
            }
            if(zeroCount > 1) {
                if(nums[j] == 0) {
                    zeroCount--;
                }
                j++;
            }
            // We don't write j - i + 1 because we need to give its length after removing the zero
            maxLen = Math.max(maxLen, i - j);
        }
        return maxLen;
    }
}

// Solution 2: Use while loop to find previous 0 ?
class Solution {
    public int longestSubarray(int[] nums) {
        int maxLen = 0;
        int zeroCount = 0;
        int j = 0;
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                zeroCount++;
            }
            if(zeroCount > 1) {
                while(nums[j] != 0) {
                    j++;
                }
                // Additional one more step to skip 0 on index j and move to next index
                j++;
                zeroCount--;
            }
            // We don't write j - i + 1 because we need to give its length after removing the zero
            maxLen = Math.max(maxLen, i - j);
        }
        return maxLen;
    }
}

// Note: Why we can use only if condition to find maximum length of subarray ?
/**
Take example as: nums = [0,1,1,1,0,1,1,0,1]

If debug with console, we find in solution 1, when i = 8, j stop at 3, for solution 2, when i = 8, j stop at 5.
The thing is we don't actually need to while loop to find exactly previous 0 index, since we are trying to find
maximum length between i and j, if i keeps move forward (0 to length - 1) by 1 step each round, if we able to 
find maximum size sliding window, j should either not move forward (0 to length - 1) OR just keep the same pace
as i that move foward by 1 step each round, if we use while loop, even we find exactly previous 0 index that will
not help we find maximum size sliding window since j will move fast then i, in another word, when i just move 1
step, j may move n >= 1 steps to find previous 0 index, it will not help maximum (i - j).

Instead of moving j with while loop, just use if condition will guarantee we find maximum size sliding window since
j at most move 0 or 1 step each round when i must move 1 step each round, during this kind of iteration, if we are
lucky enough to remove the previous 0 when j move forward then we find the answer, we don't have to move j till
actual previous 0 index, such as here when i move up to 8, in if condition we only stop j at 3, but in while loop
you have to move j till 5 to find 2nd 0 in nums array and try to remove it.
*/
