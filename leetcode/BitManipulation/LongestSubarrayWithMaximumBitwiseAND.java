https://leetcode.com/problems/longest-subarray-with-maximum-bitwise-and/description/
You are given an integer array nums of size n.
Consider a non-empty subarray from nums that has the maximum possible bitwise AND.
- In other words, let k be the maximum value of the bitwise AND of any subarray of nums. Then, only subarrays with a bitwise AND equal to k should be considered.
Return the length of the longest such subarray.
The bitwise AND of an array is the bitwise AND of all the numbers in it.
A subarray is a contiguous sequence of elements within an array.
 
Example 1:
Input: nums = [1,2,3,3,2,2]
Output: 2
Explanation:
The maximum possible bitwise AND of a subarray is 3.
The longest subarray with that value is [3,3], so we return 2.

Example 2:
Input: nums = [1,2,3,4]
Output: 1
Explanation:
The maximum possible bitwise AND of a subarray is 4.
The longest subarray with that value is [4], so we return 1.
 
Constraints:
- 1 <= nums.length <= 10^5
- 1 <= nums[i] <= 10^6
--------------------------------------------------------------------------------
Attempt 1: 2025-11-13
Solution 1: Bit Manipulation (30 min)
class Solution {
    public int longestSubarray(int[] nums) {
        int max = 0;
        int maxLen = 0;
        int curMaxLen = 0;
        for(int num : nums) {
            if(num > max) {
                // Found new maximum value
                max = num;
                maxLen = 1;
                curMaxLen = 1;
            } else if(num == max) {
                // Continue current sequence of maximum valu
                curMaxLen++;
                maxLen = Math.max(maxLen, curMaxLen);
            } else {
                // Reset current sequence is necessary
                /**
                Input
                nums = [311155,311155,311155,311155,311155,311155,311155,311155,201191,311155]
                Output = 9
                Expected = 8
                 */
                curMaxLen = 0;
            }
        }
        return maxLen;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to Deepseek
For LeetCode 2419 "Longest Subarray With Maximum Bitwise AND", here's the Java solution with a key insight about bitwise AND properties:
🎯 Efficient One-Pass Solution
class Solution {
    public int longestSubarray(int[] nums) {
        int maxVal = 0;
        int maxLength = 0;
        int currentLength = 0;
        
        for (int num : nums) {
            if (num > maxVal) {
                // Found new maximum value
                maxVal = num;
                maxLength = 1;
                currentLength = 1;
            } else if (num == maxVal) {
                // Continue current sequence of maximum values
                currentLength++;
                maxLength = Math.max(maxLength, currentLength);
            } else {
                // Reset current sequence
                currentLength = 0;
            }
        }
        
        return maxLength;
    }
}
🔍 The Key Insight
The maximum bitwise AND of any subarray equals the maximum element in the array.
Why?
- Bitwise AND can only decrease or stay the same as you include more elements
- The maximum possible AND is achieved by taking a subarray containing only the maximum element
- Any subarray containing the maximum element will have AND ≤ the maximum element
- Any subarray not containing the maximum element will have AND < the maximum element
💡 More Explicit Solution
class Solution {
    public int longestSubarray(int[] nums) {
        // Step 1: Find the maximum value in the array
        int maxNum = 0;
        for (int num : nums) {
            maxNum = Math.max(maxNum, num);
        }
        
        // Step 2: Find the longest contiguous sequence of maxNum
        int maxLen = 0;
        int currentLen = 0;
        
        for (int num : nums) {
            if (num == maxNum) {
                currentLen++;
                maxLen = Math.max(maxLen, currentLen);
            } else {
                currentLen = 0;
            }
        }
        
        return maxLen;
    }
}
🧠 Step-by-Step Examples
Example 1: nums = [1,2,3,3,2,1]
Max value = 3
Find longest contiguous 3s: [3,3] → length = 2
Result: 2
Example 2: nums = [1,2,3,4,5,6]
Max value = 6
Find longest contiguous 6s: [6] → length = 1
Result: 1
Example 3: nums = [5,5,5,5,5]
Max value = 5  
Find longest contiguous 5s: [5,5,5,5,5] → length = 5
Result: 5
🔬 Bitwise AND Property Proof
For any subarray containing the maximum element maxVal:
AND(subarray) ≤ maxVal
For a subarray containing only maxVal:
AND([maxVal, maxVal, ..., maxVal]) = maxVal
Therefore, the maximum AND value is exactly maxVal, and we want the longest subarray where all elements equal maxVal.
📊 Complexity Analysis
- Time Complexity: O(n) - two passes through the array
- Space Complexity: O(1) - only a few variables
🚀 One-Pass Optimized Version
class Solution {
    public int longestSubarray(int[] nums) {
        int maxVal = nums[0];
        int maxLen = 1;
        int currLen = 1;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > maxVal) {
                maxVal = nums[i];
                maxLen = 1;
                currLen = 1;
            } else if (nums[i] == maxVal) {
                currLen++;
                maxLen = Math.max(maxLen, currLen);
            } else {
                currLen = 0;
            }
        }
        
        return maxLen;
    }
}
🎯 Key Points
1.Maximum AND = Maximum Element: The insight that makes this problem simple
2.Contiguous Sequence: We need the longest contiguous block of the maximum element
3.One-Pass Possible: Can find max value and longest sequence simultaneously
4.No Complex Bit Manipulation: Despite being a bitwise problem, the solution is straightforward
This solution efficiently finds the answer by leveraging the mathematical properties of the bitwise AND operation!

Refer to
L1805.Number of Different Integers in a String (Ref.L2419)
L2038.Remove Colored Pieces if Both Neighbors are the Same Color (Ref.L2419)
L2411.Smallest Subarrays With Maximum Bitwise OR (Ref.L2419)
