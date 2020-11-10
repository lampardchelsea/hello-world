/**
 Refer to
 https://www.cnblogs.com/Dylan-Java-NYC/p/11980644.html
 原题链接在这里：https://leetcode.com/problems/find-k-length-substrings-with-no-repeated-characters/

题目：
Given a string S, return the number of substrings of length K with no repeated characters.

Example 1:
Input: S = "havefunonleetcode", K = 5
Output: 6
Explanation: 
There are 6 substrings they are : 'havef','avefu','vefun','efuno','etcod','tcode'.

Example 2:
Input: S = "home", K = 5
Output: 0
Explanation: 
Notice K can be larger than the length of S. In this case is not possible to find any substring.

Note:
1 <= S.length <= 10^4
All characters of S are lowercase English letters.
1 <= K <= 10^4
*/

// Solution 1: Sliding Window
// Refer to
// https://www.cnblogs.com/Dylan-Java-NYC/p/11980644.html
/**
Ask for the number of Size K window having no repeated characters.
Have runner to point the char in S. When frequency of this char is already >0, 
which means it appears before, then have count of repeated characters plus 1.
If runner >= K, then decrement S.charAt(runner-K) frequency. If its frequency 
is > 1 before decrement, then count of repeated characters minus 1.
If runner >= K-1 and there is no repeated characters, then res++.
Time Complexity: O(n). n = S.length.
Space: O(1).
*/

// https://www.jianshu.com/p/5871c1654762
/**
基本上这种给定一个String然后看起来像是要把String扫描一遍并且一边扫描一边进行某种操作的题目都是可以适用sliding window算法的了。
这道题感觉比3. Longest Substring Without Repeating Characters 还要简单一些因为长度是固定的，所以在
HashMap chars.size() == K 的时候就可以直接挪动左指针，不需要拉锯了。
class Solution {
    // "havefunonleetcode"
    // i=4 => left=0, subString = havef, count++ = 1,left=1,chars.keySet()={avef}
    // ...一边挪动left一边移除map里的earlier index
    // i=7 => S.charAt(7)='o', chars.remove(S.charAt(left++)), chars.put(S.charAt(i), i) => left=3, subString="efuno" count++ = 4
    // i=8 => S.charAt(8)='n', chars.get('n') != null => remove all chars before and including chars.get('n'), chars.keys={o,n}, chars.size() < K, 不添加
    // ...
    // Runtime: 10 ms, faster than 45.42% of Java online submissions for Find K-Length Substrings With No Repeated Characters.
    // Memory Usage: 35.8 MB, less than 100.00% of Java online submissions for Find K-Length Substrings With No Repeated Characters.
    public int numKLenSubstrNoRepeats(String S, int K) {
        if (S == null || S == "" || S.length() < K) {
            return 0;
        }
        
        int left = 0;
        int count = 0;
        Map<Character, Integer> chars = new HashMap<Character, Integer>();
        for (int i = 0; i < S.length(); i++) {
            Character curr = S.charAt(i);
            if (chars.get(curr) != null) {
                int prev = chars.get(curr);
                while (left <= prev) {
                    chars.remove(S.charAt(left++));
                }
            }
            chars.put(S.charAt(i), i);
            if (chars.size() >= K && left < S.length()) {
                count++;
                chars.remove(S.charAt(left++));
            }
        }
        
        return count;
    }
}
Runtime低于54.58%的submission应该主要还是因为使用了HashMap。换成之前的根据ASCII码个数设置的256数组试试
class Solution {
    // Runtime: 3 ms, faster than 92.09% of Java online submissions for Find K-Length Substrings With No Repeated Characters.
    // Memory Usage: 35.6 MB, less than 100.00% of Java online submissions for Find K-Length Substrings With No Repeated Characters.
    public int numKLenSubstrNoRepeats(String S, int K) {
        if (S == null || S == "" || S.length() < K) {
            return 0;
        }
        
        int[] chars = new int[256];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = Integer.MIN_VALUE;
        }
        int count = 0;
        int left = 0;
        int right = 0;
        while (right < S.length()) {
            // 如果是==left也一定要挪动left的位置（往后一格才能避重）
            if (chars[S.charAt(right)] >= left) {
                left = chars[S.charAt(right)] + 1;
            }
            
            chars[S.charAt(right)] = right;
            if (right - left + 1 == K) {
                count++;
                left++;
            }
            right++;
        }
        
        return count;
    }
}
*/

