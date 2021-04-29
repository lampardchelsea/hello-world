/**
Refer to
https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/
Given two string arrays word1 and word2, return true if the two arrays represent the same string, and false otherwise.

A string is represented by an array if the array elements concatenated in order forms the string.

Example 1:
Input: word1 = ["ab", "c"], word2 = ["a", "bc"]
Output: true
Explanation:
word1 represents string "ab" + "c" -> "abc"
word2 represents string "a" + "bc" -> "abc"
The strings are the same, so return true.

Example 2:
Input: word1 = ["a", "cb"], word2 = ["ab", "c"]
Output: false

Example 3:
Input: word1  = ["abc", "d", "defg"], word2 = ["abcddefg"]
Output: true

Constraints:
1 <= word1.length, word2.length <= 103
1 <= word1[i].length, word2[i].length <= 103
1 <= sum(word1[i].length), sum(word2[i].length) <= 103
word1[i] and word2[i] consist of lowercase letters.
*/

// Solution 1: Intuitive string concatenation
class Solution {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        String s1 = "";
        String s2 = "";
        for(String w : word1) {
            s1 += w;
        }
        for(String w : word2) {
            s2 += w;
        }
        return s1.equals(s2);
    }
}

// Solution 2: Java using count pointers, no String concatenation
// Refer to
// https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/discuss/944525/Java-using-count-pointers-no-String-concatenation-2ms-memory-100
// https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/discuss/944525/Java-using-count-pointers-no-String-concatenation-2ms-memory-100/777166
class Solution {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        int w1 = 0;
        int w2 = 0;
        int c1 = 0;
        int c2 = 0;
        while(w1 < word1.length && w2 < word2.length) {
            while(c1 < word1[w1].length() && c2 < word2[w2].length()) {
                if(word1[w1].charAt(c1) != word2[w2].charAt(c2)) {
                    return false;
                }
                c1++;
                c2++;
            }
            if(c1 == word1[w1].length()) {
                w1++;
                c1 = 0;
            }
            if(c2 == word2[w2].length()) {
                w2++;
                c2 = 0;
            }
        }
        // Cannot directly return true, test out by
        // word1 = ["abc","d","defg"]
        // word2 = ["abcddef"]
        return w1 == word1.length && w2 == word2.length;
    }
}
