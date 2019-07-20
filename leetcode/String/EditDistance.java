/**
 * Refer to
 * https://leetcode.com/problems/edit-distance/#/description
 * Given two words word1 and word2, find the minimum number of steps required to convert 
 * word1 to word2. (each operation is counted as 1 step.)
 * You have the following 3 operations permitted on a word:
	a) Insert a character
	b) Delete a character
	c) Replace a character
 * 
 * Solution
 * https://discuss.leetcode.com/topic/17639/20ms-detailed-explained-c-solutions-o-n-space
 * This is a classic problem of Dynamic Programming. We define the state dp[i][j] to be the minimum number of 
 * operations to convert word1[0..i - 1] to word2[0..j - 1]. The state equations have two cases: the boundary 
 * case and the general case. Note that in the above notations, both i and j take values starting from 1.
 * 
 * For the boundary case, that is, to convert a string to an empty string, it is easy to see that the mininum 
 * number of operations to convert word1[0..i - 1] to "" requires at least i operations (deletions). 
 * In fact, the boundary case is simply:

    dp[i][0] = i;
    dp[0][j] = j.

 * Now let's move on to the general case, that is, convert a non-empty word1[0..i - 1] to another non-empty 
 * word2[0..j - 1]. Well, let's try to break this problem down into smaller problems (sub-problems). 
 * Suppose we have already known how to convert word1[0..i - 2] to word2[0..j - 2], which is dp[i - 1][j - 1]. 
 * Now let's consider word[i - 1] and word2[j - 1]. If they are euqal, then no more operation is needed and 
 * dp[i][j] = dp[i - 1][j - 1]. Well, what if they are not equal?
 * 
 * If they are not equal, we need to consider three cases:

    Replace word1[i - 1] by word2[j - 1] (dp[i][j] = dp[i - 1][j - 1] + 1 (for replacement));
    Delete word1[i - 1] and word1[0..i - 2] = word2[0..j - 1] (dp[i][j] = dp[i - 1][j] + 1 (for deletion));
    Insert word2[j - 1] to word1[0..i - 1] and word1[0..i - 1] + word2[j - 1] = word2[0..j - 1] (dp[i][j] = dp[i][j - 1] + 1 (for insertion)).

 * Make sure you understand the subtle differences between the equations for deletion and insertion. 
 * For deletion, we are actually converting word1[0..i - 2] to word2[0..j - 1], which costs dp[i - 1][j], 
 * and then deleting the word1[i - 1], which costs 1. The case is similar for insertion.
 * 
 * Putting these together, we now have:

    dp[i][0] = i;
    dp[0][j] = j;
    dp[i][j] = dp[i - 1][j - 1], if word1[i - 1] = word2[j - 1];
    dp[i][j] = min(dp[i - 1][j - 1] + 1, dp[i - 1][j] + 1, dp[i][j - 1] + 1), otherwise.

 * The above state equations can be turned into the following code directly.

	class Solution { 
	public:
	    int minDistance(string word1, string word2) { 
	        int m = word1.length(), n = word2.length();
	        vector<vector<int> > dp(m + 1, vector<int> (n + 1, 0));
	        for (int i = 1; i <= m; i++)
	            dp[i][0] = i;
	        for (int j = 1; j <= n; j++)
	            dp[0][j] = j;  
	        for (int i = 1; i <= m; i++) {
	            for (int j = 1; j <= n; j++) {
	                if (word1[i - 1] == word2[j - 1]) 
	                    dp[i][j] = dp[i - 1][j - 1];
	                else dp[i][j] = min(dp[i - 1][j - 1] + 1, min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
	            }
	        }
	        return dp[m][n];
	    }
	};

 * Well, you may have noticed that each time when we update dp[i][j], we only need dp[i - 1][j - 1], 
 * dp[i][j - 1], dp[i - 1][j]. In fact, we need not maintain the full m*n matrix. Instead, maintaing one 
 * column is enough. The code can be optimized to O(m) or O(n) space, depending on whether you maintain 
 * a row or a column of the original matrix.
 * 
 * The optimized code is as follows.

	class Solution { 
	public:
	    int minDistance(string word1, string word2) {
	        int m = word1.length(), n = word2.length();
	        vector<int> cur(m + 1, 0);
	        for (int i = 1; i <= m; i++)
	            cur[i] = i;
	        for (int j = 1; j <= n; j++) {
	            int pre = cur[0];
	            cur[0] = j;
	            for (int i = 1; i <= m; i++) {
	                int temp = cur[i];
	                if (word1[i - 1] == word2[j - 1])
	                    cur[i] = pre;
	                else cur[i] = min(pre + 1, min(cur[i] + 1, cur[i - 1] + 1));
	                pre = temp;
	            }
	        }
	        return cur[m]; 
	    }
	}; 

 * Well, if you find the above code hard to understand, you may first try to write a two-column version that 
 * explicitly maintains two columns (the previous column and the current column) and then simplify the 
 * two-column version into the one-column version like the above code :-)
 * 
 * https://segmentfault.com/a/1190000003741294
 */
public class EditDistance {
	public int minDistance(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();
		int[][] dp = new int[m + 1][n + 1];
		// For the boundary case, that is, to convert a string to an empty string, 
		// it is easy to see that the mininum number of operations to convert 
		// word1[0..i - 1] to "" requires at least i operations (deletions). 
		// In fact, the boundary case is simply: dp[i][0] = i; dp[0][j] = j.
		for(int i = 1; i <= m; i++) {
			dp[i][0] = i;
		}
		for(int j = 1; j <= n; j++) {
			dp[0][j] = j;
		}
		// Now let's move on to the general case, that is, convert a non-empty 
		// word1[0..i - 1] to another non-empty word2[0..j - 1]. Well, let's try to 
		// break this problem down into smaller problems (sub-problems). Suppose we 
		// have already known how to convert word1[0..i - 2] to word2[0..j - 2], 
		// which is dp[i - 1][j - 1]. 
		for(int i = 1; i <= m; i++) {
			for(int j = 1; j <= n; j++) {
				// Now let's consider word[i - 1] and word2[j - 1]. If they are euqal, 
				// then no more operation is needed and dp[i][j] = dp[i - 1][j - 1]
				if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					// If they are not equal, we need to consider three cases:
					// Replace word1[i - 1] by word2[j - 1] (dp[i][j] = dp[i - 1][j - 1] + 1 (for replacement));
				    // Delete word1[i - 1] and word1[0..i - 2] = word2[0..j - 1] (dp[i][j] = dp[i - 1][j] + 1 (for deletion));
				    // Insert word2[j - 1] to word1[0..i - 1] and word1[0..i - 1] + word2[j - 1] = word2[0..j - 1] (dp[i][j] = dp[i][j - 1] + 1 (for insertion)).
					// Make sure you understand the subtle differences between the equations for deletion and insertion. 
					// For deletion, we are actually converting word1[0..i - 2] to word2[0..j - 1], which costs dp[i - 1][j], 
					// and then deleting the word1[i - 1], which costs 1. The case is similar for insertion.
					dp[i][j] = Math.min(dp[i - 1][j - 1] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j] + 1));
				}
			}
		}
		return dp[m][n];
    }
	
	public static void main(String[] args) {
		
	}
}

// Re-document, refer to
// https://leetcode.com/problems/edit-distance/discuss/25849/Java-DP-solution-O(nm)
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        // f(i, j) := minimum cost (or steps) required to convert first i characters of word1 to first j characters of word2
        int[][] dp = new int[m + 1][n + 1];
        // base case, delete m and n times to convert word1 and word 2 to empty string
        for(int i = 1; i <= m; i++) {
            dp[i][0] = i;
        }
        for(int i = 1; i <= n; i++) {
            dp[0][i] = i;
        }
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                if(word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // f(i, j) = 1 + min{f(i, j - 1), f(i - 1, j), f(i - 1, j - 1)}
                    // f(i, j - 1) represents insert operation
                    // f(i - 1, j) represents delete operation
                    // f(i - 1, j - 1) represents replace operation
                    // Here, we consider any operation from word1 to word2. 
                    // It means, when we say insert operation, we insert a new character
                    // after word1 that matches the jth character of word2. 
                    // So, now have to match i characters of word1 to j - 1 
                    // characters of word2. Same goes for other 2 operations as well.
                    dp[i][j] = 1 + Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}
