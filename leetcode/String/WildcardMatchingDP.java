/**
 * Refer to
 * https://leetcode.com/problems/wildcard-matching/#/description
 * Implement wildcard pattern matching with support for '?' and '*'.
	
	'?' Matches any single character.
	'*' Matches any sequence of characters (including the empty sequence).
	
	The matching should cover the entire input string (not partial).
	
	The function prototype should be:
	bool isMatch(const char *s, const char *p)
	
	Some examples:
	isMatch("aa","a") → false
	isMatch("aa","aa") → true
	isMatch("aaa","aa") → false
	isMatch("aa", "*") → true
	isMatch("aa", "a*") → true
	isMatch("ab", "?*") → true
	isMatch("aab", "c*a*b") → false

 * 
 * Solution 1
 * https://discuss.leetcode.com/topic/3040/linear-runtime-and-constant-space-solution/2?page=1
 * https://segmentfault.com/a/1190000003786247
 * 双指针法
 * 复杂度
 * 时间 O(N) 空间 O(1)
 * 思路
 * 假设我们用两个指针分别指向s和p字符串中要匹配的位置，首先分析下通配符匹配过程中会有哪些情况是成功：
    (1) s的字符和p的字符相等
    (2) p中的字符是?，这时无论s的字符是什么都可以匹配一个
    (3) p中遇到了一个*，这时无论s的字符是什么都没关系
    (4) 之前的都不符合，但是p在之前的位置有一个*，我们可以从上一个*后面开始匹配
    (5) s已经匹配完，但是p后面还有很多连续的`*
 * 这里1和2的情况比较好处理，关键在于如何处理3和4的情况。当我们遇到一个*时，因为之后可能要退回至该位置重新匹配，
 * 我们要将它的下标记录下来，比如idxstar。但是，当我们连续遇到两次4的情况，如何保证我还是能继续匹配s，而不是每次
 * 都退回idxstar+1导致循环呢？所以我们还要记录一个idxmatch，用来记录用上一个*连续匹配到的s中的下标。最后，
 * 对于情况5，我们用一个循环跳过末尾的*跳过就行了。
 * 
 * Solution 2
 * https://discuss.leetcode.com/topic/22516/my-java-dp-solution-using-2d-table/5
 */
public class WildcardMatching {
	// Solution 1: 
	public boolean isMatch(String s, String p) {
        int sIndex = 0;
        int pIndex = 0;
        int match = 0;
        int starIndex = -1;
        while(sIndex < s.length()) {
        	// 当两个指针指向完全相同的字符时，或者p中遇到的是?时
        	if(pIndex < p.length() && (p.charAt(pIndex) == '?' || s.charAt(sIndex) == p.charAt(pIndex))) {
        		sIndex++;
        		pIndex++;
        	// 如果字符不同也没有?，但在p中遇到是*时，我们记录下*的位置，但不改变s的指针
        	} else if(pIndex < p.length() && p.charAt(pIndex) == '*') {
        		starIndex = pIndex;
        		//遇到*后，我们用idxmatch来记录*匹配到的s字符串的位置，和不用*匹配到的s字符串位置相区分
        		match = sIndex;
        		pIndex++;
        	// 如果字符不同也没有?，p指向的也不是*，但之前已经遇到*的话，我们可以从idxmatch继续匹配任意字符
        	} else if(starIndex != -1) {
        		// 用上一个*来匹配，那我们p的指针也应该退回至上一个*的后面
        		pIndex = starIndex + 1;
        		// 用*匹配到的位置递增
        		match++;
        		// s的指针退回至用*匹配到位置
        		sIndex = match;
        	} else {
        		return false;
        	}
        }
        // 因为1个*能匹配无限序列，如果p末尾有多个*，我们都要跳过
        while(pIndex < p.length() && p.charAt(pIndex) == '*') {
        	pIndex++;
        }
     // 如果p匹配完了，说明匹配成功
        return pIndex == p.length();
    }
	
	// Solution 2: 2D Array dp 
	public boolean isMatch2(String s, String p) {
        int m = s.length() + 1;
        int n = p.length() + 1;
        boolean[][] dp = new boolean[m][n];
        dp[0][0] = true;
        
        // Initialize dp[i][0]
        for(int i = 1; i < m; i++) {
            // First, we need to initialize dp[i][0], i = [1,m). 
            // All the dp[i][0] should be false because p has nothing in it.
            dp[i][0] = false;
        }
        
        // Initialize dp[0][j]
        for(int j = 1;  j < n; j++) {
            // Then, initialize dp[0][j], j = [1, n). In this case, 
            // s has nothing, to get dp[0][j] = true, p must be '*', **', '***', etc. 
            // Once p.charAt(j-1) != '*', all the dp[0][j] afterwards will be false.
            // Note:
            // Definition for '*' not same
            // In Regular Expression Matching
            // '*' Matches zero or more of the preceding element.
            // In Wildcard Matching
            // '*' Matches any sequence of characters (including the empty sequence).
            if(p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }
        
        // Then start the typical DP loop.
        // Though this solution is clear and easy to understand. 
        // It is not good enough in the interview. it takes O(mn) time and O(mn) space.
        // Improvement: 
        // 1) optimize 2d dp to 1d dp, this will save space, reduce space complexity to O(N). 
        // 2) use iterative 2-pointer.
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(p.charAt(j - 1) == '*') {
                    // E.g
                    //    j-3  j-2  j-1  j                                          j-3  j-2  j-1  j 
                    // p:            * --> no need to match any chars in s                     *        
                    //         i-2  i-1  i                                     or        i-2  i-1  i
                    // s:            a                                                         ?? --> any length of char sequence
                    //                                                                                as '*' matches any kind
                    // This idea is:
                    // if(dp[i][j-1] == true) --> s(0-->i-1) matches with p(0-->j-2) 
                    // now you see p[j-1] == '*', add this into p, * can match none in s. 
                    // you don't need to add anything into s, you can see now dp[i][j] = true.
                    // if(dp[i-1][j] == true) --> s(0-->i-2) matches with p(0-->j-1) 
                    // now you see p[j-1] == '*' , in the end of s, you can add another 
                    // or more k chars due to the last of pattern is '*', making dp[i][j] or even dp[i+k][j] true;
                    dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
                } else {
                    // E.g
                    //    j-3  j-2  j-1  j         j-3  j-2  j-1  j
                    // p:            a          P:            ?
                    //         i-2  i-1  i   or         i-2  i-1  i
                    // s:            a          s:            a
                    //    
                    if(p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '?') {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }
        }
        return dp[m - 1][n - 1];
	}
	
	public static void main(String[] args) {
		
	}
}
