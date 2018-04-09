import java.util.HashMap;
import java.util.Map;

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
            // s.charAt(end) in map will be -1.
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







public class MinimumWindowSubstring {
	public String minWindow(String s, String t) {
		char[] tChars = t.toCharArray();
        char[] sChars = s.toCharArray();
        // Target string frequency map
        int[] tMap = new int[255];
        // Given string frequency map
        int[] sMap = new int[255];
        // Scan the target string(t) build a frequency map
        for(int i = 0; i < tChars.length; i++) {
        	tMap[tChars[i]]++;
        }
        int tLen = t.length();
        int sLen = s.length();
        // The pointer move from left to right to scan the given string(s)
        int idx = 0;
        // Record how many characters match target string were found
        int charFound = 0;
        // Record minimum length out of each possible case
        int minLen = sLen;
        // Two flags for substring final result, will be updated each loop
        int begin = -1;
        int end = sLen;
        for(int i = 0; i < sLen; i++) {
        	// Increase one for certain indexed character in given string frequency map
        	sMap[sChars[i]]++;
        	// If this indexed character amount still no more than same one in target string(t),
        	// then this is one character in target string, increase counter as one
        	if(sMap[sChars[i]] <= tMap[sChars[i]]) {
        		charFound++;
        	}
        	// If find all characters in target string(t), no same order requirement and
        	// this sequence in given string(s) may longer than target string(t)
        	if(charFound == tLen) {
        		// Remove all unnecessary characters from beginning of current substring,
        		// 'unnecessary' means frequency of this character in current sequence 
        		// is larger than same character in target string(t)
        		while(idx < i && sMap[s.charAt(idx)] > tMap[s.charAt(idx)]) {
        			// Decrease one for this character's frequency in given string frequency map
        			sMap[s.charAt(idx)]--;
        			idx++;
        		}
        		// Move pointer(idx) to the start of current substring, update minimum length,
        		// record begin and end indexes
        		if(i - idx < minLen) {
        			minLen = i - idx;
        			begin = idx;
        			end = i;
        		}
        		// Skip the initial character of current matching candidate substring
        		// and decrease the corresponding frequency of this character in
        		// given string frequency map as one
        		sMap[sChars[idx]]--;
        		// Update pointer for looking the next candidate substring
        		idx++;
        		// Update founded characters
        		charFound--;
        	}
        }
        // Use 'begin' default value to judge wither return empty string or substring
        return begin == -1 ? "" : s.substring(begin, end + 1);
    }
	
	public static void main(String[] args) {
		MinimumWindowSubstring m = new MinimumWindowSubstring();
		String s = "ABC";
		String t = "C";
		String result = m.minWindow(s, t);
		System.out.println("--------->" + result + "<----------");
	}
}

