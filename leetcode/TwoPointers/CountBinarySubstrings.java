/**
 Refer to
 https://leetcode.com/problems/count-binary-substrings/
 Give a string s, count the number of non-empty (contiguous) substrings that have the same 
 number of 0's and 1's, and all the 0's and all the 1's in these substrings are grouped consecutively.

Substrings that occur multiple times are counted the number of times they occur.

Example 1:
Input: "00110011"
Output: 6
Explanation: There are 6 substrings that have equal number of consecutive 1's and 0's: "0011", "01", 
"1100", "10", "0011", and "01".

Notice that some of these substrings repeat and are counted the number of times they occur.

Also, "00110011" is not a valid substring because all the 0's (and 1's) are not grouped together.
Example 2:
Input: "10101"
Output: 4
Explanation: There are 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.

Note:
s.length will be between 1 and 50,000.
s will only consist of "0" or "1" characters.
*/
// Solution 1: Ingenious previous substring length and current substring iterator compare
// Refer to
// https://leetcode.com/problems/count-binary-substrings/discuss/108600/Java-O(n)-Time-O(1)-Space
class Solution {
    public int countBinarySubstrings(String s) {
        int count = 0;
        int preRunLength = 0;
        int currRunLength = 1;
        for(int i = 1; i < s.length(); i++) {
            if(s.charAt(i) == s.charAt(i - 1)) {
                currRunLength++;
            } else {
                preRunLength = currRunLength;
                currRunLength = 1;
            }
            if(preRunLength >= currRunLength) {
                count++;
            }
        }
        return count;
    } 
}





















https://leetcode.com/problems/count-binary-substrings/description/
Given a binary string s, return the number of non-empty substrings that have the same number of 0's and 1's, and all the 0's and all the 1's in these substrings are grouped consecutively.
Substrings that occur multiple times are counted the number of times they occur.
 
Example 1:
Input: s = "00110011"
Output: 6
Explanation: There are 6 substrings that have equal number of consecutive 1's and 0's: "0011", "01", "1100", "10", "0011", and "01".
Notice that some of these substrings repeat and are counted the number of times they occur.Also, "00110011" is not a valid substring because all the 0's (and 1's) are not grouped together.

Example 2:
Input: s = "10101"
Output: 4
Explanation: There are 4 substrings: "10", "01", "10", "01" that have equal number of consecutive 1's and 0's.
 
Constraints:
- 1 <= s.length <= 105
- s[i] is either '0' or '1'.
--------------------------------------------------------------------------------
Attempt 1: 2026-05-31
Solution 1: Two Pointers (10 min)
class Solution {
    public int countBinarySubstrings(String s) {
        int result = 0;
        int prev = 0;
        int curr = 1;
        for(int i = 1; i < s.length(); i++) {
            if(s.charAt(i - 1) == s.charAt(i)) {
                curr++;
            } else {
                result += Math.min(curr, prev);
                prev = curr;
                curr = 1;
            }
        }
        // Add the contribution from the last pair of groups
        // e.g s = "00110011"
        result += Math.min(curr, prev);
        return result;
    }
}

Refer to Kimi
Here is an efficient Java solution for LeetCode 696. Count Binary Substrings.
Approach: Group Length Comparison
The key insight is that valid substrings must consist of exactly two adjacent groups of consecutive 0s and 1s with equal length.
For example, in "00110011" the group lengths are [2, 2, 2, 2]. For each adjacent pair of groups, the number of valid substrings is min(prevGroupLength, currGroupLength).
Time Complexity: $O(n)$
Space Complexity: $O(1)$
class Solution {
    public int countBinarySubstrings(String s) {
        int prev = 0;   // length of the previous group
        int curr = 1;   // length of the current group (first char starts a group)
        int count = 0;
        
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                curr++; // same digit, extend current group
            } else {
                // Group changed: add valid substrings from prev+curr pair
                count += Math.min(prev, curr);
                prev = curr;
                curr = 1; // start new group with current char
            }
        }
        
        // Add the contribution from the last pair of groups
        count += Math.min(prev, curr);
        return count;
    }
}

Walkthrough with "00110011"
IndexCharActionprevcurrcount10same as prev02021diff → min(0,2)=021031same22040diff → min(2,2)=221250same22261diff → min(2,2)=221471same224End—min(2,2)=2226
The 6 valid substrings are: "0011", "01", "1100", "10", "0011", "01".

Refer to
L271.Encode and Decode Strings
L2489.Number of Substrings With Fixed Ratio (Ref.L696)
L3234.Count the Number of Substrings With Dominant Ones (Ref.L696)
