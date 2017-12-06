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
   这道题仍然是动态规划的题目，我们总结一下动态规划题目的基本思路。首先我们要决定要存储什么历史信息以及用什么
   数据结构来存储信息。然后是最重要的递推式，就是如从存储的历史信息中得到当前步的结果。最后我们需要考虑的就是
   起始条件的值。
   接下来我们套用上面的思路来解这道题。首先我们要存储的历史信息res[i]是表示到字符串s的第i个元素为止能不能用
   字典中的词来表示，我们需要一个长度为n的布尔数组来存储信息。然后假设我们现在拥有res[0,...,i-1]的结果，我们
   来获得res[i]的表达式。思路是对于每个以i为结尾的子串，看看他是不是在字典里面以及他之前的元素对应的res[j]是
   不是true，如果都成立，那么res[i]为true，写成式子是
   假设总共有n个字符串，并且字典是用HashSet来维护，那么总共需要n次迭代，每次迭代需要一个取子串的O(i)操作，
   然后检测i个子串，而检测是constant操作。所以总的时间复杂度是O(n^2)（i的累加仍然是n^2量级），而空间复杂度
   则是字符串的数量，即O(n)
 
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
