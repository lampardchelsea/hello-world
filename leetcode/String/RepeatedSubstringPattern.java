/**
 * Given a non-empty string check if it can be constructed by taking a substring of it and 
 * appending multiple copies of the substring together. You may assume the given string consists 
 * of lowercase English letters only and its length will not exceed 10000.
 * Example 1:
 * Input: "abab"
 * Output: True
 * Explanation: It's the substring "ab" twice.
 * 
 * Example 2:
 * Input: "aba"
 * Output: False
 *
 * Example 3:
 * Input: "abcabcabcabc"
 * Output: True
 * Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
*/
// Solution 1:
// Refer to
// https://discuss.leetcode.com/topic/67992/java-simple-solution-with-explanation
// http://www.cnblogs.com/grandyang/p/6087347.html
/**
 * 这道题给了我们一个字符串，问其是否能拆成n个重复的子串。那么既然能拆分成多个子串，那么每个子串的长度肯定
 * 不能大于原字符串长度的一半，那么我们可以从原字符串长度的一半遍历到1，如果当前长度能被总长度整除，说明可
 * 以分成若干个子字符串，我们将这些子字符串拼接起来看跟原字符串是否相等。 如果拆完了都不相等，返回false。
*/
public class Solution {
    public boolean repeatedSubstringPattern(String str) {
        int length = str.length();
        for(int i = length / 2; i >= 1; i--) {
            if(length % i == 0) {
                int n = length / i;
                StringBuilder sb = new StringBuilder();
                String s = str.substring(0, i);
                for(int j = 0; j < n; j++) {
                    sb.append(s);
                }
                if(sb.toString().equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}

// Solution 2: 48ms to 18ms
// Added a small check in your code and time reduced from 48 ms to 18 ms. 
// Just return if any of the substring will not match. No need to create 
// the whole string.
public class Solution {
    public boolean repeatedSubstringPattern(String str) {
        int length = str.length();
        for(int i = length / 2; i >= 1; i--) {
            if(length % i == 0) {
                int n = length / i;
                StringBuilder sb = new StringBuilder();
                String s = str.substring(0, i);
                int j;
                for(j = 1; j < n; j++) {
                    if(!s.equals(str.substring(j * i, j * i + i))) {
                        break;
                    }
                }
                if(j == n) {
                    return true;
                }
            }
        }
        return false;
    }
}

