/**
 * Refer to
 * https://leetcode.com/problems/word-break/description/
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, 
   determine if s can be segmented into a space-separated sequence of one or more dictionary words. 
   You may assume the dictionary does not contain duplicate words.

    For example, given
    s = "leetcode",
    dict = ["leet", "code"].

    Return true because "leetcode" can be segmented as "leet code".

    UPDATE (2017/1/4):
    The wordDict parameter had been changed to a list of strings (instead of a set of strings). 
    Please reload the code definition to get the latest changes.
 *
 * Solution
 * http://blog.csdn.net/linhuanmars/article/details/22358863
 * https://segmentfault.com/a/1190000003698693
 * https://www.youtube.com/watch?v=Vrxhx_rSslc
 * https://www.youtube.com/watch?v=ptlwluzeC1I&t=24s
*/

class Solution {
    // Time Complexity: O(n ^ 2)
    // Space Complexity: O(n)
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || s.length() == 0) {
            return true;
        }
        boolean[] dp = new boolean[s.length() + 1];
        // 我们要存储的历史信息dp[i]是表示到字符串s的第i个元素为止能不能用字典中的词来表示
        dp[0] = true;
        // 外层循环递增长度
        for(int i = 1; i <= s.length(); i++) {
            // 内层循环寻找分割点
            for(int j = 0; j < i; j++) {
                if(dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
