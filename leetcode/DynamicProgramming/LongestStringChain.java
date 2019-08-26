/**
 Refer to
 https://leetcode.com/problems/longest-string-chain/
 Given a list of words, each word consists of English lowercase letters.

Let's say word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in word1 
to make it equal to word2.  For example, "abc" is a predecessor of "abac".

A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1, where word_1 is a predecessor 
of word_2, word_2 is a predecessor of word_3, and so on.

Return the longest possible length of a word chain with words chosen from the given list of words.

Example 1:
Input: ["a","b","ba","bca","bda","bdca"]
Output: 4
Explanation: one of the longest word chain is "a","ba","bda","bdca".

Note:
1 <= words.length <= 1000
1 <= words[i].length <= 16
words[i] only consists of English lowercase letters.
*/

// Solution 1: Same DP style as 300. Longest Increasing Subsequence (LIS)
// Refer to
// https://leetcode.com/problems/longest-increasing-subsequence/
/**
 class Solution {
    public int lengthOfLIS(int[] nums) {
        int[] dp = new int[nums.length];
        for(int i = 0; i < nums.length; i++) {
            dp[i] = 1;
        }
        int max = 0;
        for(int i = 0; i < nums.length; i++) {
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
*/
// https://leetcode.com/problems/longest-string-chain/discuss/294890/C++JavaPython-DP-Solution/277492
class Solution {
    public int longestStrChain(String[] words) {
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int[] dp = new int[words.length];
        for(int i = 0; i < words.length; i++) {
            dp[i] = 1;
        }
        int max = 0;
        for(int i = 0; i < words.length; i++) {
            for(int j = 0; j < i; j++) {
                if(isPredecessor(words[j], words[i])) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
    
    private boolean isPredecessor(String word1, String word2) {
        int len1 = word1.length();
        int len2 = word2.length();
        if(len1 - len2 != -1) {
            return false;
        }
        int[] freq = new int[26];
        for(int i = 0; i < word1.length(); i++) {
            freq[word1.charAt(i) - 'a']++;
        }
        for(int i = 0; i < word2.length(); i++) {
            freq[word2.charAt(i) - 'a']--;
        }
        // Note: below check is wrong, since if one character frequence is -2
        // and another character frequence is 1, -2 + 1 = -1 also create -1,
        // but not match the judge condition here, we need only one -1 and 25
        // 0 to make sure a predecessor rule
        // int freqSum = 0;
        // for(int i = 0; i < 26; i++) {
        //     freqSum += freq[i];
        // }
        // return freqSum == -1;
        int count1 = 0;
        int count2 = 0;
        for(int i = 0; i < 26; i++) {
            if(freq[i] == -1) {
                count1++;
            }
            if(freq[i] == 0) {
                count2++;
            }
        }
        if(count1 == 1 && count2 == 25) {
            return true;
        }
        return false;
    }
}
