/**
Refer to
https://leetcode.com/problems/longest-substring-of-all-vowels-in-order/
A string is considered beautiful if it satisfies the following conditions:

Each of the 5 English vowels ('a', 'e', 'i', 'o', 'u') must appear at least once in it.
The letters must be sorted in alphabetical order (i.e. all 'a's before 'e's, all 'e's before 'i's, etc.).
For example, strings "aeiou" and "aaaaaaeiiiioou" are considered beautiful, but "uaeio", "aeoiu", and "aaaeeeooo" are not beautiful.

Given a string word consisting of English vowels, return the length of the longest beautiful substring of word. 
If no such substring exists, return 0.

A substring is a contiguous sequence of characters in a string.

Example 1:
Input: word = "aeiaaioaaaaeiiiiouuuooaauuaeiu"
Output: 13
Explanation: The longest beautiful substring in word is "aaaaeiiiiouuu" of length 13.

Example 2:
Input: word = "aeeeiiiioooauuuaeiou"
Output: 5
Explanation: The longest beautiful substring in word is "aeiou" of length 5.

Example 3:
Input: word = "a"
Output: 0
Explanation: There is no beautiful substring, so return 0.

Constraints:
1 <= word.length <= 5 * 105
word consists of characters 'a', 'e', 'i', 'o', and 'u'.
*/

// Solution 1: Not fixed length sliding window
// Refer to
// https://leetcode.com/problems/longest-substring-of-all-vowels-in-order/discuss/1175134/JavaPython-3-Sliding-window-codes-w-brief-explanation-and-analysis.
/**
1.Loop through input string and maintain a sliding window to fit in beautiful substrings;
2.Use a Set seen to check the number of distinct vowels in the sliding window;
3.Whenever adjacent letters is not in alpahbetic order, start a new window and new Set;
4.For each iteration of the loop, add the correponding char and check the size of the Set seen; If the size reaches 5, update the longest length;
5.Return the longest length after the termination of the loop.

    public int longestBeautifulSubstring(String word) {
        int longest = 0;
        Set<Character> seen = new HashSet<>();
        for (int lo = -1, hi = 0; hi < word.length(); ++hi) {
            if (hi > 0 && word.charAt(hi - 1) > word.charAt(hi)) {
                seen = new HashSet<>();
                lo = hi - 1;
            }
            seen.add(word.charAt(hi));
            if (seen.size() == 5) {
                longest = Math.max(longest, hi - lo);
            }
        }
        return longest;
    }
Credit to @MtDeity, who improve the following Python 3 code.

    def longestBeautifulSubstring(self, word: str) -> int:
        seen = set()
        lo, longest = -1, 0
        for hi, c in enumerate(word):
            if hi > 0 and c < word[hi - 1]:
                seen = set()
                lo = hi - 1    
            seen.add(c)    
            if len(seen) == 5:
                longest = max(longest, hi - lo)
        return longest

Analysis:
There are at most 5 characters in Set seen, which cost space O(1). Therefore,
Time: O(n), space: O(1)
*/
class Solution {
    public int longestBeautifulSubstring(String word) {
        Set<Character> set = new HashSet<Character>();
        int max = 0;
        int n = word.length();
        int j = 0;
        for(int i = 0; i < n; i++) {
            if(i >= 1 && word.charAt(i) < word.charAt(i - 1)) {
                // When encounter character not ascending refresh set
                // which used for count 5 vowels and record current 
                // position as new round start
                set.clear();
                j = i;
            }
            set.add(word.charAt(i));
            if(set.size() == 5) {
                max = Math.max(max, i - j + 1);
            }
        }
        return max;
    }
}
