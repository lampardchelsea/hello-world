/**
 Refer to
 https://leetcode.com/problems/edit-distance/
 Given two words word1 and word2, find the minimum number of operations required to convert word1 to word2.

You have the following 3 operations permitted on a word:

Insert a character
Delete a character
Replace a character
Example 1:

Input: word1 = "horse", word2 = "ros"
Output: 3
Explanation: 
horse -> rorse (replace 'h' with 'r')
rorse -> rose (remove 'r')
rose -> ros (remove 'e')
Example 2:

Input: word1 = "intention", word2 = "execution"
Output: 5
Explanation: 
intention -> inention (remove 't')
inention -> enention (replace 'i' with 'e')
enention -> exention (replace 'n' with 'x')
exention -> exection (replace 'n' with 'c')
exection -> execution (insert 'u')
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/edit-distance/discuss/25846/C%2B%2B-O(n)-space-DP
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
