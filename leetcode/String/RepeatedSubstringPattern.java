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
// Refer to
// https://discuss.leetcode.com/topic/67992/java-simple-solution-with-explanation/2
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


// Solution 3: KMP (As analysis, not exactly KMP)
// Refer to
// http://www.cnblogs.com/grandyang/p/6087347.html
// https://discuss.leetcode.com/topic/67652/c-o-n-using-kmp-32ms-8-lines-of-code-with-brief-explanation
/**
 * 原作者说是用的KMP算法，LeetCode之前也有一道应用KMP算法来解的题Shortest Palindrome，但是感觉那道题才是KMP算法。
 * 这道题也称为KMP算法感觉怪怪的(关于KMP的详细介绍请参见从头到尾彻底理解KMP)，KMP算法中的next数组是找当前位置的最
 * 大相同前缀后缀的个数，而这道题维护的一位数组dp[i]表示，到位置i-1为止的重复字符串的字符个数，不包括被重复的那个
 * 字符串，什么意思呢，我们举个例子，比如"abcabc"的dp数组为[0 0 0 0 1 2 3]，dp数组长度要比原字符串长度多一个。
 * 那么我们看最后一个位置数字为3，就表示重复的字符串的字符数有3个。如果是"abcabcabc"，那么dp数组为[0 0 0 0 1 2 3 4 5 6]，
 * 我们发现最后一个数字为6，那么表示重复的字符串为“abcabc”，有6个字符。那么怎么通过最后一个数字来知道原字符串是否
 * 由重复的子字符串组成的呢，首先当然是最后一个数字不能为0，而且还要满足dp[n] % (n - dp[n]) == 0才行，因为n - dp[n]
 * 是一个子字符串的长度，那么重复字符串的长度和肯定是一个子字符串的整数倍
*/
public class Solution {
    public boolean repeatedSubstringPattern(String str) {
        // i is index pointer to loop through the str
        int i = 1;
        // j is a counter to store how many characters repeated until current position
        int j = 0;
        int length = str.length();
        int[] dp = new int[length + 1];
        for(int k = 0; k < dp.length; k++) {
            dp[k] = 0;
        }
        while(i < length) {
            if(str.charAt(i) == str.charAt(j)) {
                dp[++i] = ++j;
            } else if(j == 0) {
                ++i;
            } else {
                j = dp[j];
            }
        }
        return dp[length] != 0 && (dp[length] % (length - dp[length]) == 0);
    }
}
