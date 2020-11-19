/**
Refer to
https://leetcode.com/problems/minimum-window-substring/
Given two strings s and t, return the minimum window in s which will contain all the characters in t. 
If there is no such window in s that covers all characters in t, return the empty string "".

Note that If there is such a window, it is guaranteed that there will always be only one unique minimum window in s.

Example 1:
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"

Example 2:
Input: s = "a", t = "a"
Output: "a"

Constraints:
1 <= s.length, t.length <= 105
s and t consist of English letters.

Follow up: Could you find an algorithm that runs in O(n) time?
*/

// Solution 1: Not fixed length sliding window + HashMap
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/String/MinimumWindowSubstring.java
/**
 * Refer to
 * https://leetcode.com/problems/minimum-window-substring/#/description
 *  Given a string S and a string T, find the minimum window in S which will contain 
 *  all the characters in T in complexity O(n).
	For example,
	S = "ADOBECODEBANC"
	T = "ABC"
	Minimum window is "BANC".
 * Note:
 * If there is no such window in S that covers all characters in T, return the empty string "".
 * If there are multiple such windows, you are guaranteed that there will always be only 
 * one unique minimum window in S. 
 * 
 * Solution
 * https://segmentfault.com/a/1190000003707313
 * 双指针法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 用一个哈希表记录目标字符串每个字母的个数，一个哈希表记录窗口中每个字母的个数。先找到第一个有效的窗口，
 * 用两个指针标出它的上界和下界。然后每次窗口右界向右移时，将左边尽可能的右缩，右缩的条件是窗口中字母的
 * 个数不小于目标字符串中字母的个数。
 * 注意
 * 用一个数组来保存每个字符出现的次数，比哈希表容易
 * 保存结果子串的起始点初值为-1，方便最后判断是否有正确结果
 * 
 * https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems/25861
 * https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems/25848
 * https://discuss.leetcode.com/topic/30941/here-is-a-10-line-template-that-can-solve-most-substring-problems
 * 
 */
class Solution {
    public String minWindow(String s, String t) {
        /**
         1. Use two pointers: start and end to represent a window.
         2. Move end to find a valid window.
         3. When a valid window is found, move start to find a smaller window.
        */
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        // Use s and t together to fill map with all characters frequency
        // charcater only present in s will set frequency as 0
        for(char c : s.toCharArray()) {
            map.put(c, 0);
        }
        for(char c : t.toCharArray()) {
            if(map.containsKey(c)) {
                map.put(c, map.get(c) + 1);    
            }
        }
        int start = 0;
        int end = 0;
        int minStart = 0;
        // count represents the number of chars of t to be found in s.
        int count = t.length();
        int minLen = Integer.MAX_VALUE;
        while(end < s.length()) {
            // If char in s exists in t, decrease counter
            if(map.get(s.charAt(end)) > 0) {
                count--;
            }
            // Decrease map.get(s.charAt(end)). If character not exist in t, 
            // s.charAt(end) in map will be set as -1.
            map.put(s.charAt(end), map.get(s.charAt(end)) - 1);
            end++;
            // When we found a valid window, move start to find smaller window.
            while(count == 0) {
                if(end - start < minLen) {
                    minLen = end - start;
                    minStart = start;
                }
                map.put(s.charAt(start), map.get(s.charAt(start)) + 1);
                // For character not exist in t, s.charAt(start) + 1 = (-1) + 1 = 0,
                // only character exist in t, will be positive
                if(map.get(s.charAt(start)) > 0) {
                    count++;
                }
                start++;
            }
        }
        if(minLen != Integer.MAX_VALUE) {
            return s.substring(minStart, minStart + minLen);
        }
        return "";
    }
}

// Solution 2: Promote by just using int[] array
// Refer to
// https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
// https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems/25816
class Solution {
    public String minWindow(String s, String t) {
        int[] freq = new int[128]; // A-Z(65-90) a-z(97-122)
        char[] t_chars = t.toCharArray();
        for(char c : t_chars) {
            freq[c]++;
        }
        int count = t.length();
        int minLen = Integer.MAX_VALUE;
        int minStart = 0;
        int i = 0;
        for(int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            if(freq[c] > 0) {
                count--;
            }
            freq[c]--;
            while(count == 0) {
                if(minLen > j - i + 1) {
                    minLen = j - i + 1;
                    minStart = i;
                }
                char c1 = s.charAt(i);
                freq[c1]++;
                if(freq[c1] > 0) {
                    count++;
                }
                i++;
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLen);
    }
}


