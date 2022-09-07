/**
Refer to
https://www.lintcode.com/problem/longest-substring-with-at-most-two-distinct-characters/description
Given a string, find the length of the longest substring T that contains at most 2 distinct characters.
Example
Example 1
Input: “eceba”
Output: 3
Explanation:
T is "ece" which its length is 3.

Example 2
Input: “aaa”
Output: 3
*/

// Solution 1: Not fixed length window using two pointers + criteria
// Refer to
// Template from
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/SlidingWindow/MaxConsecutiveOnesIII.java

// Refer to
// https://medium.com/@zengruiwang/sliding-window-technique-360d840d5740
/**
Two pointers + criteria: the window size is not fixed, usually it asks you to find the subarray that meets the criteria, 
for example, given an array of integers, find the number of subarrays whose sum is equal to a target value.
*/

// Array out of boundary solution
/**
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: -31 
at Solution.lengthOfLongestSubstringTwoDistinct(Solution.java:12) at Main.main(Main.java:17)
*/
public class Solution {
    /**
     * @param s: a string
     * @return: the length of the longest substring T that contains at most 2 distinct characters
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int maxLen = 0;
        int[] freq = new int[26];
        int i = 0;
        for(int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            freq[c - 'a']++;  // This line out of boundary, may because int freq overflow
            int count = 0;
            for(int k = 0; k < 26; k++) {
                if(freq[k] != 0) {
                    count++;
                }
            }
            while(count > 2) {
                char c1 = s.charAt(i);
                freq[c1 - 'a']--;
                if(freq[c1 - 'a'] == 0) {
                    count--;
                }
                i++;
            }
            // Additional + 1 for make up the last i++ will remove 1 more required element
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}

// Good example refer to
// https://github.com/cherryljr/LeetCode/blob/master/Longest%20Substring%20with%20At%20Most%20Two%20Distinct%20Characters.java
/**
 * Using Sliding Window Template
 * Detail explanation about the template is here:
 * https://github.com/cherryljr/LeetCode/blob/master/Sliding%20Window%20Template.java
 *
 * Here is a similar question in LintCode (Just be different from k and two):
 * https://www.lintcode.com/problem/longest-substring-with-at-most-k-distinct-characters/description
 public class Solution {
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int begin = 0, end = 0;
        // The number of distinct character.
        int counter = 0;
        // The length of longest substring.
        int maxLen = 0;

        while (end < s.length()) {
            char cEnd = s.charAt(end);
            map.put(cEnd, map.getOrDefault(cEnd, 0) + 1);
            if (map.get(cEnd) == 1) {
                counter++;  //new char
            }
            end++;

            // counter > 2 means that
            // there are more than two distinct characters in the current window.
            // So we should move the sliding window.
            while (counter > 2) {
                char cBegin = s.charAt(begin);
                map.put(cBegin, map.get(cBegin) - 1);
                if (map.get(cBegin) == 0) {
                    counter--;
                }
                begin++;
            }
            // Pay attention here!
            // We don't get/update the result in while loop above
            // Because if the number of distinct character isn't big enough, we won't enter the while loop
            // eg. s = "abc" (We'd better update the result here to avoid getting 0 length)
            maxLen = Math.max(maxLen, end - begin);
        }

        return maxLen;
    }
}
*/

// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/String/LongestSubstringWithAtMostKDistinctCharacters.java
public class Solution {
    /**
     * @param s: a string
     * @return: the length of the longest substring T that contains at most 2 distinct characters
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int maxLen = 0;
        Map<Character, Integer> freq = new HashMap<Character, Integer>();
        int i = 0;
        for(int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            int count = freq.size();
            while(count > 2) {
                char c1 = s.charAt(i);
                freq.put(c1, freq.get(c1) - 1);
                if(freq.get(c1) == 0) {
                    freq.remove(c1);
                    count--;
                }
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen;
    }
}

Attempt 1: 2022-09-07
Solution 1 (30min)
public class Solution {
    /**
     * @param s: A string
     * @param k: An integer
     * @return: An integer
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // write your code here
        Map<Character, Integer> freq = new HashMap<Character, Integer>();
        int maxLen = Integer.MIN_VALUE;
        // 'i' is left end index, 'j' is right end index
        int i = 0;
        int len = s.length();
        // Before over k distinct characters try to expand right end as much as possible
        for(int j = 0; j < len; j++) {
            char c = s.charAt(j);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
            int size = freq.size();
            // When distinct character number > k, start shrink left end, use while loop
            // in case current string section (left end indicate by 'i') start with same
            // character, e.g "bbeceba" start with two 'b's when j = 3, k = 2 and i = 0,
            // currently substring indicated as "bbec", we have to remove two 'b' to match 
            // k = 2, while loop run two times to increase i from 0 to 2, which means 
            // truncate two 'b's and keep "ec", distinct characters drop back from 3 to 2
            while(size > k) {
                char c1 = s.charAt(i);
                freq.put(c1, freq.get(c1) - 1);
                if(freq.get(c1) == 0) {
                    freq.remove(c1);
                    size--;
                }
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen == Integer.MIN_VALUE ? 0 : maxLen;
    }
}

Solution 2 (30min)
public class Solution {
    /**
     * @param s: A string
     * @param k: An integer
     * @return: An integer
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        // write your code here
        Map<Character, Integer> char_index_map = new HashMap<Character, Integer>();
        int maxLen = Integer.MIN_VALUE;
        // 'i' is left end index, 'j' is right end index
        int i = 0;
        int len = s.length();
        // Before over k distinct characters try to expand right end as much as possible
        for(int j = 0; j < len; j++) {
            // Instead of store {char, frequency} mapping, we store {char, most recent index},
            // the most recent index of character means the latest happening position in String s
            // e.g 'b' in "bbeceba" when j = 3, the most recent index of 'b' is 1 (update from 0)
            char_index_map.put(s.charAt(j), j);
            int size = char_index_map.size();
            // When distinct character number > k, start shrink left end, use while loop
            // in case current string section (left end indicate by 'i') start with same
            // character, e.g "bbeceba" start with two 'b's when j = 3, k = 2 and i = 0,
            // currently substring indicated as "bbec", we have to remove two continuous
            // 'b' to match k = 2, the while loop does the truncate job, it runs one time
            // to increase i from 0 to 1, which means skip all continuous 'b' and reach to
            // the most recent one, the 2nd 'b' is the most recent one since its index = 1
            // match the stored most recent index = 1 when j = 3 (if we have continuous 'b'
            // at beginning, it will skip more, e.g "bbbbeceba" it will skip 3 times), now
            // we can remove distinct character 'b' from map directly by just increase i once,
            // since the next new character 'e' will be the new start of window (i++ after
            // size-- indicate to new character 'e'), distinct characters drop back from 3 to 2
            if(size > k) {
                while(char_index_map.get(s.charAt(i)) != i) {
                    i++;
                }
                char_index_map.remove(s.charAt(i));
                size--;
                i++;
            }
            maxLen = Math.max(maxLen, j - i + 1);
        }
        return maxLen == Integer.MIN_VALUE ? 0 : maxLen;
    }
}
