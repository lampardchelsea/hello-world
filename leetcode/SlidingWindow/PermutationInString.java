/**
Refer to
https://leetcode.com/problems/permutation-in-string/
Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. 
In other words, one of the first string's permutations is the substring of the second string.

Example 1:
Input: s1 = "ab" s2 = "eidbaooo"
Output: True
Explanation: s2 contains one permutation of s1 ("ba").

Example 2:
Input:s1= "ab" s2 = "eidboaoo"
Output: False

Constraints:
The input strings only contain lower case letters.
The length of both given strings is in range [1, 10,000].
*/

// Solution 1: Fixed length sliding window
// Refer to
// https://leetcode.com/problems/permutation-in-string/discuss/102588/Java-Solution-Sliding-Window
// Template refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/FindKLengthSubstringsWithNoRepeatedCharacters.java
class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int s1_len = s1.length();
        int s2_len = s2.length();
        if(s2_len < s1_len) {
            return false;
        }
        int[] s1_freq = new int[26];
        for(int i = 0; i < s1_len; i++) {
            s1_freq[s1.charAt(i) - 'a']++;
        }
        for(int i = 0; i < s2_len; i++) {
            s1_freq[s2.charAt(i) - 'a']--;
            if(i >= s1_len) {
                if(allZero(s1_freq)) {
                    return true;
                }
                s1_freq[s2.charAt(i - s1_len) - 'a']++;
            }
            if(i >= s1_len - 1) {
                if(allZero(s1_freq)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean allZero(int[] arr) {
        for(int a : arr) {
            if(a != 0) {
                return false;
            }
        }
        return true;
    }
}
