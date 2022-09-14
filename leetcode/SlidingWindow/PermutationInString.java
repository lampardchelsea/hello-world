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
                // Since we remove element as index at i - s1_len at first by s1_freq[s2.charAt(i) - 'a']--,
                // now we recover it by add 1 after this element out of sliding window
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


Attempt 1: 2022-09-14 (30min, too long since its a fixed length sliding window problem but solution pattern flow into not fixed length sliding window, the fixed length used as condition to check whether find a solution)

class Solution { 
    public boolean checkInclusion(String s1, String s2) { 
        int len1 = s1.length(); 
        int len2 = s2.length(); 
        // Create frequency table based on s1 
        int[] freq = new int[26]; 
        for(int i = 0; i < len1; i++) { 
            // Increase character count on frequency table 
            freq[s1.charAt(i) - 'a']++; 
        } 
        // 'i' is left end pointer, 'j' is right end pointer 
        int i = 0; 
        for(int j = 0; j < len2; j++) { 
            // Decrease character count on frequency table, a reverse  
            // operation rather than frequency table creation, because  
            // we want to identify if same character count has difference  
            // between s1 and s2 inside current sliding window 
            freq[s2.charAt(j) - 'a']--; 
            // When a character count becomes negative means we find a difference 
            // between s1 and s2, attempt to balance count again (make the character 
            // count = 0 again), only way is shinrking the left end pointer 'i' 
            while(freq[s2.charAt(j) - 'a'] < 0) { 
                freq[s2.charAt(i) - 'a']++; 
                i++; 
            } 
            // If substring length of sliding window identified by (j - i + 1) 
            // equal to string s1 length, we find a solution 
            if(j - i + 1 == len1) {
                return true; 
            } 
        } 
        return false; 
    } 
}

Space Complexity: O(n) 
Time Complexity: O(n)

Refer to
https://leetcode.com/problems/permutation-in-string/discuss/102598/Sliding-Window-in-Java-very-similar-to-Find-All-Anagrams-in-a-String
https://leetcode.com/problems/permutation-in-string/discuss/102590/8-lines-slide-window-solution-in-Java/383847

public boolean checkInclusion(String s1, String s2) { 
    int[] counts = new int[26]; 
    for (char c: s1.toCharArray()) counts[c-'a']++; 
    int i = 0, j = 0; 
    while(j < s2.length()) { 
        char c = s2.charAt(j++); 
        counts[c-'a']--; 
        while(counts[c-'a'] < 0) { 
            char c2 = s2.charAt(i++); 
            counts[c2-'a']++; 
        } 
        if (j-i == s1.length()) return true; 
    } 
    return false; 
}
