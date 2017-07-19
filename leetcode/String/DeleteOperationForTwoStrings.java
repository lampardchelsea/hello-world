/**
 * Refer to
 * https://leetcode.com/problems/delete-operation-for-two-strings/#/description
 *  Given two words word1 and word2, find the minimum number of steps required to make word1 
 *  and word2 the same, where in each step you can delete one character in either string.
	
	Example 1:
	
	Input: "sea", "eat"
	Output: 2
	Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
	
	Note:
	    The length of given words won't exceed 500.
	    Characters in given words can only be lower-case letters.

 * Solution
 * https://leetcode.com/articles/delete-operation-for-two-strings/ 
 * 
 * Also check on
 * https://discuss.leetcode.com/topic/89283/java-dp-solution-same-as-edit-distance
 * 
 */
public class DeleteOperationForTwoStrings {
	// Solution 1: 2D Array DP
	// Refer to
	// https://discuss.leetcode.com/topic/89283/java-dp-solution-same-as-edit-distance
	// Same as Approach #4 Without using LCS Dynamic Programmming [Accepted]
	/**
	 * Instead of finding the length of LCS and then determining the number of deletions required, 
	 * we can make use of Dynamic Programming to directly determine the number of deletions required 
	 * till the current indices of the strings.
	 * In order to do so, we make use of a 2-D dp array. Now, dp[i][j] refers to the number of 
	 * deletions required to equalize the two strings if we consider the strings' length upto (i−1)th​​ 
	 * index and (j−1)th index for s1s1s1 and s2s2s2 respectively. Again, we fill in the dp array 
	 * in a row-by-row order. Now, in order to fill the entry for dp[i][j], we need to consider 
	 * two cases only:
	 * The characters s1[i−1] and s2[j−1] match with each other. In this case, we need to replicate 
	 * the entry corresponding to dp[i−1][j−1] itself. This is because, the matched character doesn't 
	 * need to be deleted from any of the strings.
	 * The characters s1[i−1] and s2[j−1] don't match with each other. In this case, we need to delete 
	 * either the current character of s1 or s2. Thus, an increment of 1 needs to be done 
	 * relative to the entries corresponding to the previous indices. The two options available at this 
	 * moment are dp[i−1][j] and dp[i][j−1]. Since, we are keeping track of the minimum number of 
	 * deletions required, we pick up the minimum out of these two values.
	 * At the end, dp[m][n] gives the required minimum number of deletions. Here, m and n refer to the 
	 * lengths of s1 and s2.
	 * 
	 * Note: Be careful, don't need to compare with dp[i - 1][j - 1] + 2
	 * 
	 * Complexity Analysis
	 * Time complexity : O(m∗n). We need to fill in the dp array of size mxn. Here, m and n refer to 
	 * the lengths of s1 and s2.
	 * Space complexity : O(m∗n). dp array of size mxn is used.
	 */
	public int minDistance(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        if(len1 == 0) {
            return len2;
        }
        if(len2 == 0) {
            return len1;
        }
        int m = len1 + 1; // Y-axis
        int n = len2 + 1; // X-axis
    	// dp[i][j] stands for distance of first i chars of word1 and first j chars of word2
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            dp[i][0] = i;
        }
        for(int j = 0; j < n; j++) {
            dp[0][j] = j;
        }
        for(int i = 1; i < m; i++) {
            for(int j = 1; j < n; j++) {
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                	// Don't need to compare with dp[i - 1][j - 1] + 2
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + 2);
                }
            }
        }
        return dp[m - 1][n - 1];
    }
	
	// Solution 2: 2D Array DP with LCS
	// Refer to
	// https://leetcode.com/articles/delete-operation-for-two-strings/ 
	// Approach #3 Using Longest Common Subsequence- Dynamic Programming [Accepted]
	/**
	 * Another method to obtain the value of lcs is to make use of Dynamic Programming. 
	 * We'll look at the implementation and carry-on alongside the idea behind it.
	 * We make use of a 2-D dp, in which dp[i][j] represents the length of the longest 
	 * common subsequence among the strings s1 and s2 considering their lengths upto 
	 * (i−1)th​​ index and (j−1)th index only respectively. We fill the dp array in 
	 * row-by-row order.
	 * In order to fill the entry for dp[i][j], we can have two cases:
	 * The characters s1[i−1] and s2[j−1] match with each other. In this case, the entry 
	 * for dp[i][j] will be one more than the entry obtained for the strings considering 
	 * their lengths upto one lesser index, since the matched character adds one to the 
	 * length of LCS formed till the current indices. Thus, the dp[i][j] entry is updated 
	 * as dp[i][j]=1+dp[i−1][j−1]. Note that dp[i−1][j−1] has been used because the matched 
	 * character belongs to both s1 and s2.
	 * The characters s1[i−1] and s2[j−1] don't match with each other. In this case, 
	 * we can't increment the current entry as compared to entries corresponding to the 
	 * previous indices, but we need to replicate the previous entry again to indicate 
	 * that the length of LCS upto the current indices also remains the same. But, which 
	 * entry to pick up? Now, since the current character hasn't matched, we have got 
	 * two options. We can remove the current character from consideration from either 
	 * s1 or s2 and use the corresponding dp entries given by dp[i−1][j]and dp[i][j−1] 
	 * respectively. Since we are considering the length of LCS upto the current indices 
	 * we need to pick up the larger entry out of these two to update the current dp entry.
	 * At the end, again, we obtain the number of deletions required as m+n−2∗dp[m][n], 
	 * where m and n refer to the lengths of s1 and s2. dp[m][n] now refers to the length 
	 * of LCS among the two given strings.
	 * 
	 * Complexity Analysis
	 * Time complexity : O(m∗n). We need to fill in the dp array of size mxn. Here, m and n refer to 
	 * the lengths of s1 and s2.
	 * Space complexity : O(m∗n). dp array of size mxn is used.
	 */
	public int minDistance2(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        if(len1 == 0) {
            return len2;
        }
        if(len2 == 0) {
            return len1;
        }
        int m = len1 + 1; // Y-axis
        int n = len2 + 1; // X-axis
        // dp[i][j] stands for the length of longest common subsequene(LCS)
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                // As one string is empty, the LCS length always 0
                if(i == 0 || j == 0) {
                    continue;
                }
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return len1 + len2 - 2 * dp[m - 1][n - 1];
	}
	
	// Solution 3: 1D Array DP
	/**
	 * We can observe that in the last approach, in order to update the current dp entries, 
	 * we need only the values of the previous row of dp. Thus, rather than using a 2-D array, 
	 * we can do the same job by making use of a 1-D dp array.
	 * Thus, now, dp[i] refers to the number of deletions that need to be made in order to 
	 * equalize the strings s1 and s2 if we consider string s1 upto the (i−1)th​​ index and string 
	 * s2 upto the last to current index of s2.
	 * Now, we make the updations for the current row in an array temp of the same size as dp, 
	 * and use the dp entries as if they correspond to the previous row's entries. When, 
	 * the whole temp array has been filled, we copy it the dp array so that dp array now 
	 * reflects the new row's entries.
	 * 
	 * Complexity Analysis
       Time complexity : O(m∗n). We need to fill in the dp array of size n, m times. Here, m 
                         and n refer to the lengths of s1s1s1 and s2s2s2.
       Space complexity : O(n). dp array of size n is used.
	 */
	public int minDistance3(String word1, String word2) {
		int len1 = word1.length();
		int len2 = word2.length();
		if(len1 == 0) {
			return len2;
		}
		if(len2 == 0) {
			return len1;
		}
		int m = len2 + 1;
		int n = len1 + 1;
		// dp[i] refers to the number of deletions that need to be made in order to equalize 
		// the strings s1 and s2 if we consider string s1 upto the (i−1)th index and string 
		// s2 upto the last to current index of s2
		int[] dp = new int[m];
	    for(int i = 0; i < n; i++) {
	    	// Tricky part: We can observe that in the last approach, in order to update 
	    	// the current dp entries, we need only the values of the previous row of dp. 
	    	// Thus, rather than using a 2-D array, we can do the same job by making use 
	    	// of a 1-D dp array.
	    	int[] temp = new int[m];
	    	for(int j = 0; j < m; j++) {
	    		if(i == 0 || j == 0) {
	    			temp[j] = i + j;
	    		} else if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
	    	    	temp[j] = dp[j - 1];
	    	    } else {
	    	    	temp[j] = 1 + Math.min(dp[j], temp[j - 1]);
	    	    }
	    	}
	    	dp = temp;
	    }
	    return dp[len2];
	}
	
	
	
}

