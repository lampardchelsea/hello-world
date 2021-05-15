/**
Refer to
https://leetcode.com/problems/number-of-good-ways-to-split-a-string/
You are given a string s, a split is called good if you can split s into 2 non-empty strings p and q where its 
concatenation is equal to s and the number of distinct letters in p and q are the same.

Return the number of good splits you can make in s.

Example 1:
Input: s = "aacaba"
Output: 2
Explanation: There are 5 ways to split "aacaba" and 2 of them are good. 
("a", "acaba") Left string and right string contains 1 and 3 different letters respectively.
("aa", "caba") Left string and right string contains 1 and 3 different letters respectively.
("aac", "aba") Left string and right string contains 2 and 2 different letters respectively (good split).
("aaca", "ba") Left string and right string contains 2 and 2 different letters respectively (good split).
("aacab", "a") Left string and right string contains 3 and 1 different letters respectively.

Example 2:
Input: s = "abcd"
Output: 1
Explanation: Split the string as follows ("ab", "cd").

Example 3:
Input: s = "aaaaa"
Output: 4
Explanation: All possible splits are good.

Example 4:
Input: s = "acbadbaada"
Output: 2

Constraints:
s contains only lowercase English letters.
1 <= s.length <= 10^5
*/

// Solution 1: Brute Force (TLE)
class Solution {
    public int numSplits(String s) {
        int n = s.length();
        int result = 0;
        for(int i = 1; i < n; i++) {
            String s1 = s.substring(0, i);
            String s2 = s.substring(i);
            int[] f1 = new int[26];
            int[] f2 = new int[26];
            for(char c1 : s1.toCharArray()) {
                f1[c1 - 'a']++;
            }
            for(char c2 : s2.toCharArray()) {
                f2[c2 - 'a']++;
            }
            int count = 0;
            for(int k = 0; k < 26; k++) {
                if(f1[k] > 0) {
                    count++;
                }
                if(f2[k] > 0) {
                    count--;
                }
            }
            if(count == 0) {
                result++;
            }
        }
        return result;
    }
}

// Solution 2: Sliding Window + Count the unique character at the same pass, only increase count when that character first record in map
// Refer to
// https://leetcode.com/problems/number-of-good-ways-to-split-a-string/discuss/754678/C%2B%2BJava-Sliding-Window
/**
We first count each character (r[ch]), and number of distinct characters (d_r). 
These are initial numbers for our right substring (thus, indicated as r).

As we move our split point from left to right, we "move" the current character to the left substring, 
and update count and distinct characters in left and right substrings.

If the number of distict characters is equal, we increment the result.

public int numSplits(String str) {
    int l[] = new int[26], r[] = new int[26], d_l = 0, d_r = 0, res = 0;
    var s = str.toCharArray();
    for (char ch : s)
        d_r += ++r[ch - 'a'] == 1 ? 1 : 0;
    for (int i = 0; i < s.length; ++i) {
        d_l += ++l[s[i] - 'a'] == 1 ? 1 : 0;
        d_r -= --r[s[i] - 'a'] == 0 ? 1 : 0;
        res += d_l == d_r ? 1 : 0;
    }
    return res;
}
*/
class Solution {
    public int numSplits(String s) {
        int[] left = new int[26];
        int[] right = new int[26];
        int left_count = 0;
        int right_count = 0;
        for(char c : s.toCharArray()) {
            left[c - 'a']++;
            // Count the unique character at the same pass, only 
            // increase count when that character first record in map,
            // in another word, we won't increase count when the same
            // character encounter again to guarantee only get unique
            // character count
            if(left[c - 'a'] == 1) {
                left_count++;
            }
        }
        int result = 0;
        for(char c : s.toCharArray()) {
            // In 2nd scan round, each time gradually move one char
            // from left map to right map, and also increase / decrease
            // unique character count at the same pass only when that
            // character first record in map / remove from map
            right[c - 'a']++;
            // When character first encounter, add into right map
            if(right[c - 'a'] == 1) {
                right_count++;
            }
            left[c - 'a']--;
            // When character not exist in left map, remove it
            if(left[c - 'a'] == 0) {
                left_count--;
            }
            // When left and right map has same unique character count
            // increase final count
            if(left_count == right_count) {
                result++;
            }
        }
        return result;
    }
}
