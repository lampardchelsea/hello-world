/**
 * Refer to
 * https://leetcode.com/problems/regular-expression-matching/#/description
 * Implement regular expression matching with support for '.' and '*'.

	'.' Matches any single character.
	'*' Matches zero or more of the preceding element.
	
	The matching should cover the entire input string (not partial).
	
	The function prototype should be:
	bool isMatch(const char *s, const char *p)
	
	Some examples:
	isMatch("aa","a") ? false
	isMatch("aa","aa") ? true
	isMatch("aaa","aa") ? false
	isMatch("aa", "a*") ? true
	isMatch("aa", ".*") ? true
	isMatch("ab", ".*") ? true
	isMatch("aab", "c*a*b") ? true

 *  
 * Solution
 * https://discuss.leetcode.com/topic/40371/easy-dp-java-solution-with-detailed-explanation
 * 
 * https://discuss.leetcode.com/topic/40371/easy-dp-java-solution-with-detailed-explanation/18?page=1
 * E.g 
 * s = aab, p = c*a*, dp[0][0] = true
 * 
 * 2D dp matrix
 * ----------------------------------------------------------
 * |  dp  |   j  |   0   |   1  |   2  |   3  |   4  |   5  |  j = p_index + 1
 * ----------------------------------------------------------
 * |   i  |      |Pattern|  0:c |  1:* |  2:a |  3:* |  4:. |  p_index:char_val
 * ----------------------------------------------------------
 * |   0  | Word |   T   |   F  |   T  |   F  |   T  |   F  |
 * ----------------------------------------------------------
 * |   1  |  0:a |   F   |      |      |      |      |      |
 * ---------------------------------------------------------
 * |   2  |  1:a |   F   |      |      |      |      |      |
 * ---------------------------------------------------------
 * |   3  |  2:b |   F   |      |      |      |      |      |
 * ---------------------------------------------------------
 *      s_index:char_val  
 *   i = s_index + 1
 */
public class RegularExpressionMatching {
	public boolean isMatch(String s, String p) {
		int m = s.length() + 1;
		int n = p.length() + 1;
        boolean[][] dp = new boolean[m][n];
        dp[0][0] = true;
        for(int j = 2; j < n; j++) {
        	// The relation between j of dp matrix and 
        	// index of p is p_index = j - 1
        	if(p.charAt(j - 1) == '*' && dp[0][j - 2]) {
        		dp[0][j] = true;
        	}
        }
        
        for(int i = 1; i < m; i++) {
        	for(int j = 1; j < n; j++) {
        		// Refer to
        		// https://discuss.leetcode.com/topic/17852/9-lines-16ms-c-dp-solutions-with-explanations/28?page=2
        		// But only one suggestion. At first i was confused by your description.
        		// Why dp[i][j] = dp[i - 1][j - 1], if (s[i - 1] == p[j - 1] || p[j - 1] == '.') 
        		// never consider p(i) and s(j) ?
        		// But finally i realized that the index 'i' in P[i][j] is corresponding to 
        		// the array index i - 1. So our case here is "p.charAt(j - 1) == s.charAt(i - 1)" 
        		// correspond to dp's largest position(dp[i][j] same as 2D_Array[i - 1][j - 1] 
        		// grid) already match, so its final state based on previous state as dp[i - 1][j - 1]
        		// E,g
        		//     ... j-2  j-1  j              ... j-2   j-1   j
        		// p:    ???     a              p:     ???     .
        		//     ...      i-1  i    or        ...       i-1   i
        		// s:            a              s:             a
        		// 'a' == 'a', to check whether p.substring(0, j) (or express as
        		// p[0] to p[j - 1]) matches s.substring(0, i) (or express as
        		// s[0] to s[i - 1]) depends on whether p.substring(0, j - 1) (or
        		// express as p[0] to p[j - 2]) matches s.substring(0, i - 1) (or
        		// express as s[0] to s[i - 2]), as '???' describe suspend status
        		// for previous digits need to check
        		if(p.charAt(j - 1) == s.charAt(i - 1) || p.charAt(j - 1) == '.') {
        			dp[i][j] = dp[i - 1][j - 1];
        		} else if(p.charAt(j - 1) == '*') {
        			if(p.charAt(j - 2) != s.charAt(i - 1) && p.charAt(j - 2) != '.') {
            			// E.g
            			//    ... j-3  j-2  j-1  j
            			// p:    ???    a    *   
            			//    ...      i-2  i-1  i
            		    // s:                c
            			// 'a' != 'c', treat (a*) in p as empty string, so dp[i][j]
        				// which checking on whether p.substring(0, j) (or express as
        				// p[0] to p[j - 1]) matches s.substring(0, i) (or express as
        				// s[0] to s[i - 1]) depends on whether p.substring(0, j - 2) 
        				// (or express as p[0] to p[j - 3]) matches s.substring(0, i)
        				// (or express as s[0] to s[i - 1]), as '???' describe suspend
        				// status for previous digits need to check
        				dp[i][j] = dp[i][j - 2];
        			} else {
        				// Case 1: p.charAt(j - 2) == s.charAt(i - 1) && p.charAt(j - 1) == '*'
            			//    ... j-3  j-2  j-1  j
            			// p:    ???    a    *   ------> 'a*' treat as single 'a'
            			//    ...      i-2  i-1  i
            		    // s:                a
        				// Because of 'a' = 'a', treat (a*) in p as single a, so dp[i][j]
        				// which checking on whether p.substring(0, j) (or express as
        				// p[0] to p[j - 1]) matches s.substring(0, i) (or express as
        				// s[0] to s[i - 1]) depends on whether p.substring(0, j - 2) 
        				// (or express as p[0] to p[j - 3]) matches s.substring(0, i - 1)
        				// (or express as s[0] to s[i - 2]), as '???' describe suspend
        				// status for previous digits need to check
                        // dp[i][j] = dp[i - 1][j - 2]
                        // 
                        //
                        // Case 2: p.charAt(j - 2) == s.charAt(i - 1) && p.charAt(j - 1) == '*'
                        //    ... j-3  j-2  j-1  j
            			// p:    ???    a    *  ------> 'a*' treat as empty 
            			//    ...      i-2  i-1  i
            		    // s:                a
                        // A little tricky as this case, even 'a' = 'a', treat (a*) in p
                        // as empty string, dp[i][j] which checking p.substring(0, j) 
                        // (or express as p[0] to p[j - 1]) matches s.substring(0, i) 
                        // (or express as s[0] to s[i - 1]) depends on whether 
                        // p.substring(0, j - 2) (or express as p[0] to p[j - 3]) 
                        // matches s.substring(0, i) (or express as s[0] to s[i - 1]), 
                        // as p[j - 2] and p[j - 1] treat as empty, '???' describe suspend 
                        // status for previous digits need to check
                        // dp[i][j] = dp[i][j - 2]
                        // 
                        // 
        				// Case 3: p.charAt(j - 2) == '.' && p.charAt(j - 1) == '*'
            			//    ... j-3  j-2  j-1  j
            			// p:    ???    .    *   
            			//    ...      i-2  i-1  i
            		    // s:                a
        				// means status of previous char in word, there are multiple same chars
                        // dp[i][j] = dp[i - 1][j]
        			    dp[i][j] = dp[i - 1][j - 2] || dp[i][j - 2] || dp[i - 1][j];
        			}
        		}
        	}
        }
        return dp[m - 1][n - 1];
    }
}
