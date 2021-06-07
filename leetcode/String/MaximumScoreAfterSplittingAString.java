/**
Refer to
https://leetcode.com/problems/maximum-score-after-splitting-a-string/
Given a string s of zeros and ones, return the maximum score after splitting the string into two non-empty substrings (i.e. left substring and right substring).

The score after splitting a string is the number of zeros in the left substring plus the number of ones in the right substring.

Example 1:
Input: s = "011101"
Output: 5 
Explanation: 
All possible ways of splitting s into two non-empty substrings are:
left = "0" and right = "11101", score = 1 + 4 = 5 
left = "01" and right = "1101", score = 1 + 3 = 4 
left = "011" and right = "101", score = 1 + 2 = 3 
left = "0111" and right = "01", score = 1 + 1 = 2 
left = "01110" and right = "1", score = 2 + 1 = 3

Example 2:
Input: s = "00111"
Output: 5
Explanation: When left = "00" and right = "111", we get the maximum score = 2 + 3 = 5

Example 3:
Input: s = "1111"
Output: 3

Constraints:
2 <= s.length <= 500
The string s consists of characters '0' and '1' only.
*/

// Solution 1: PreSum ones and zeros
// Refer to
// https://leetcode.com/problems/maximum-score-after-splitting-a-string/discuss/597716/Java-5-Liner-(One-Pass)
/**
This can be done in single pass as below. With this approach we do not know the number of ones on right while iterating, 
so I have added TotalOne's to the result before returning.

Logic behind this -
Result = Max of (ZerosOnLeft + OnesOnRight)
= Max of (ZerosOnLeft + (TotalOnes - OnesOnLeft))
= Max of (ZerosOnLeft - OnesOnLeft) + TotalOnes (as TotalOnes is constant)

public int maxScore(String s) {
	int zeros = 0, ones = 0, max = Integer.MIN_VALUE;
	for(int i=0;i<s.length();i++) {
		if(s.charAt(i) == '0') zeros++; else ones++;
		if(i != s.length()-1) max = Math.max(zeros - ones, max);
	}
	return max + ones;
}
*/

// My style
// Precompute a prefix sum of ones ('1'), Iterate from left to right counting the number of zeros ('0'), 
// then use the precomputed prefix sum for counting ones ('1'). Update the answer.
class Solution {
    public int maxScore(String s) {
        int n = s.length();
        int[] pre_zeros = new int[n];
        int[] pre_ones = new int[n];
        pre_zeros[0] = s.charAt(0) == '0' ? 1 : 0;
        pre_ones[0] = s.charAt(0) == '1' ? 1 : 0;
        for(int i = 1; i < n; i++) {
            pre_zeros[i] = pre_zeros[i - 1] + (s.charAt(i) == '0' ? 1 : 0);
            pre_ones[i] = pre_ones[i - 1] + (s.charAt(i) == '1' ? 1 : 0);
        }
        int max = 0;
        // 'i' must end at n - 2 not n - 1, so i < n - 1 not i < n, 
        // because we have to split out at least one element for 
        // right side, test out by "00"
        for(int i = 0; i < n - 1; i++) {
            max = Math.max(max, pre_zeros[i] + pre_ones[n - 1] - pre_ones[i]);
        }
        return max;
    }
}

