/**
 Refer to
 https://leetcode.com/problems/distinct-subsequences-ii/
 Given a string S, count the number of distinct, non-empty subsequences of S .
 Since the result may be large, return the answer modulo 10^9 + 7.

 Example 1:
 Input: "abc"
 Output: 7
 Explanation: The 7 distinct subsequences are "a", "b", "c", "ab", "ac", "bc", and "abc".

 Example 2:
 Input: "aba"
 Output: 6
 Explanation: The 6 distinct subsequences are "a", "b", "ab", "ba", "aa" and "aba".

 Example 3:
 Input: "aaa"
 Output: 3
 Explanation: The 3 distinct subsequences are "a", "aa" and "aaa".
 
 Note:
 S contains only lowercase letters.
 1 <= S.length <= 2000
*/
// Solution 1: 1D DP time complexity n^2
/**
 dp[i] represents the count of unique subsequence ends with S[i].
 dp[i] is initialized to 1 for S[0 ... i]
 For each dp[i], we define j from 0 to i - 1, we have:
 if S[j] != S[i], dp[i] += dp[j]
 if S[j] == S[i], do nothing to avoid duplicates.
 Then result = sum(dp[0], ... dp[n - 1])
 Time complexity: O(n^2)
*/
class Solution {
    public int distinctSubseqII(String S) {
        int M = 1000000007;
        int result = 0;
        int n = S.length();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < i; j++) {
                if(S.charAt(i) != S.charAt(j)) {
                    dp[i] += dp[j];
                    dp[i] %= M;
                }
            }
            result += dp[i];
            result %= M;
        }
        return result;
    }
}

// Solution 2: O(N) time
/**
 Furthermore, we can use a sum to represent sum(dp[0], ..., dp[i - 1]).
 And also a count array, in which count[S.charAt(i) - 'a'] represents the count of presented subsequence ends with S.charAt(i).
 Then dp[i] = sum - count[S.charAt(i) - 'a'].
 Time complexity: O(n)
*/
class Solution {
    public int distinctSubseqII(String S) {
        int n = S.length(), M = (int)1e9 + 7;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int[] count = new int[26];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            int index = S.charAt(i) - 'a';
            dp[i] += sum - count[index];
            dp[i] = (dp[i] + M) % M;
            sum = (sum + dp[i]) % M;
            count[index] = (count[index] + dp[i]) % M;
        }
        return sum;
    }
