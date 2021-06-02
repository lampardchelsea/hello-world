/**
Refer to
https://leetcode.com/problems/count-different-palindromic-subsequences/
Given a string s, return the number of different non-empty palindromic subsequences in s. Since the answer may be very large, return it modulo 109 + 7.

A subsequence of a string is obtained by deleting zero or more characters from the string.

A sequence is palindromic if it is equal to the sequence reversed.

Two sequences a1, a2, ... and b1, b2, ... are different if there is some i for which ai != bi.

Example 1:
Input: s = "bccb"
Output: 6
Explanation: The 6 different non-empty palindromic subsequences are 'b', 'c', 'bb', 'cc', 'bcb', 'bccb'.
Note that 'bcb' is counted only once, even though it occurs twice.

Example 2:
Input: s = "abcdabcdabcdabcdabcdabcdabcdabcddcbadcbadcbadcbadcbadcbadcbadcba"
Output: 104860361
Explanation: There are 3104860382 different non-empty palindromic subsequences, which is 104860361 modulo 109 + 7.

Constraints:
1 <= s.length <= 1000
s[i] is either 'a', 'b', 'c', or 'd'.
*/

// Solution 1: DFS + memo
// Similar strategy as 516. Longest Palindromic Subsequence
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/DynamicProgramming/LongestPalindromicSubsequence.java

// Refer to
// https://www.cnblogs.com/grandyang/p/7942040.html
/**
这道题给了给了我们一个字符串，让求出所有的非空回文子序列的个数，虽然这题限制了字符只有四种，但还是按一般的情况来解吧，可以有 26 个字母。
说最终结果要对一个很大的数字取余，这就暗示了结果会是一个很大的值，对于这种问题一般都是用动态规划 Dynamic Programming 或者是带记忆数组 
memo 的递归来解，二者的本质其实是一样的。先来看带记忆数组 memo 的递归解法，这种解法的思路是一层一层剥洋葱，比如 "bccb"，按照字母来剥，
先剥字母b，确定最外层 "b _ _ b"，这会产生两个回文子序列 "b" 和 "bb"，然后递归进中间的部分，把中间的回文子序列个数算出来加到结果 res 中，
中间的 "cc" 调用递归会返回2，两边都加上b，会得到 "bcb", "bccb"，此时结果 res 为4。然后开始剥字母c，找到最外层 "cc"，此时会产生两个回文
子序列 "c" 和 "cc"，由于中间没有字符串了，所以递归返回0，最终结果 res 为6，按照这种方法就可以算出所有的回文子序列了。

建立一个二维数组 chars，外层长度为 26，里面放一个空数组。这是为了统计每个字母在原字符串中出现的位置，然后定义一个二维记忆数组 memo，
其中 memo[i][j] 表示第i个字符到第j个字符之间的子字符串中的回文子序列的个数，初始化均为0。然后遍历字符串S，将每个字符的位置加入其对应的
数组中，比如对于 "bccb"，那么有：

b -> {0, 3}
c -> {1, 2}

然后在 [0, n] 的范围内调用递归函数，在递归函数中，首先判断如果 start 大于等于 end，返回0。如果当前位置在 memo 的值大于0，说明当前情况
已经计算过了，直接返回 memo 数组中的值。否则进行所有字母的遍历，如果某个字母对应的数组中没有值，说明该字母不曾在字符串中出现，跳过。然后
在字母数组中查找第一个不小于 start 的位置，查找第一个小于 end 的位置，当前循环中，start 为0，end 为4，当前处理字母b，new_start 指向0，
new_end 指向3，如果当前 new_start 指向了 end()，或者其指向的位置大于 end，说明当前范围内没有字母b，直接跳过，否则结果 res 自增1，因为
此时 new_start 存在，至少有个单个的字母b，也可以当作回文子序列，然后看 new_start 和 new_end 如果不相同，说明两者各指向了不同的b，此时 
res 应自增1，因为又增加了一个新的回文子序列 "bb"，下面就是对中间部分调用递归函数了，把返回值加到结果 res 中。此时字母b就处理完了，现在
处理字母c，此时的 start 还是0，end 还是4，new_start 指向1，new_end 指向2，跟上面的分析相同，new_start 在范围内，结果自增1，因为加上
了 "c"，然后 new_start 和 new_end 不同，结果 res 再自增1，因为加上了 "cc"，其中间没有字符了，调用递归的结果是0，for 循环结束，将 
memo[start][end] 的值对超大数取余，并将该值返回即可，参见代码如下：

class Solution {
public:
    int countPalindromicSubsequences(string S) {
        int n = S.size();
        vector<vector<int>> chars(26, vector<int>());
        vector<vector<int>> memo(n + 1, vector<int>(n + 1, 0));
        for (int i = 0; i < n; ++i) {
            chars[S[i] - 'a'].push_back(i);
        }
        return helper(S, chars, 0, n, memo);
    }
    int helper(string S, vector<vector<int>>& chars, int start, int end, vector<vector<int>>& memo) {
        if (start >= end) return 0;
        if (memo[start][end] > 0) return memo[start][end];
        long res = 0;
        for (int i = 0; i < 26; ++i) {
            if (chars[i].empty()) continue;
            auto new_start = lower_bound(chars[i].begin(), chars[i].end(), start);
            auto new_end = lower_bound(chars[i].begin(), chars[i].end(), end) - 1;
            if (new_start == chars[i].end() || *new_start >= end) continue;
            ++res;
            if (new_start != new_end) ++res;
            res += helper(S, chars, *new_start + 1, *new_end, memo);
        }
        memo[start][end] = res % int(1e9 + 7);
        return memo[start][end];
    }
};
*/

// https://leetcode.com/problems/count-different-palindromic-subsequences/discuss/109509/Accepted-Java-Solution-using-memoization
// https://leetcode.com/problems/count-different-palindromic-subsequences/discuss/109509/Accepted-Java-Solution-using-memoization/121900
/**
This solution is fking sexy and, in my opinion, it is better than the top solution.
To help others understand the recursion, it will first check the string [(a...a), b...b, c...c, d...d], focus on (a...a), then goes to next level.
For palindrome a...a, in next level, it will check [aa...a, ab...a, ac...a, ad...a].

class Solution {
    int div=1000000007;
    public int countPalindromicSubsequences(String S) {    
        TreeSet[] characters = new TreeSet[26];
        int len = S.length();
        
        for (int i = 0; i < 26; i++) characters[i] = new TreeSet<Integer>();
        
        for (int i = 0; i < len; ++i) {
            int c = S.charAt(i) - 'a';
            characters[c].add(i);
        }
        Integer[][] dp = new Integer[len+1][len+1];
         return memo(S,characters,dp, 0, len);
    }
    
    public int memo(String S,TreeSet<Integer>[] characters,Integer[][] dp,int start,int end){
        if (start >= end) return 0;
        if(dp[start][end]!=null) return dp[start][end];
       
            long ans = 0;
            
            for(int i = 0; i < 26; i++) {
                Integer new_start = characters[i].ceiling(start);
                Integer new_end = characters[i].lower(end);
              if (new_start == null || new_start >= end) continue;
                 ans++;
                if (new_start != new_end) ans++;
                ans+= memo(S,characters,dp,new_start+1,new_end);
                
            }
            dp[start][end] = (int)(ans%div);
            return dp[start][end];
    }
}
*/
class Solution {
    int mod = 1000000007;
    public int countPalindromicSubsequences(String S) {    
        TreeSet[] characters = new TreeSet[26];
        int len = S.length();
        for(int i = 0; i < 26; i++) {
            characters[i] = new TreeSet<Integer>();
        }
        for(int i = 0; i < len; i++) {
            characters[S.charAt(i) - 'a'].add(i);
        }
        Integer[][] memo = new Integer[len + 1][len + 1];
         return helper(characters, memo, 0, len);
    }
    
    public int helper(TreeSet<Integer>[] characters, Integer[][] memo, int start, int end){
        if(start >= end) {
            return 0;
        }
        if(memo[start][end] != null) {
            return memo[start][end];
        }
        long result = 0;
        for(int i = 0; i < 26; i++) {
            Integer new_start = characters[i].ceiling(start);
            Integer new_end = characters[i].lower(end);
            // checked subsequences are less than start
            // checked subsequences are greater than end
            if(new_start == null || new_start >= end) {
                continue;
            }
            // E.g bb..b / bb..bb
            // 如果当前 new_start 指向了 end()，或者其指向的位置大于 end，说明
            // 当前范围内没有字母b，直接跳过，否则结果 res 自增1，因为此时 new_start 
            // 存在，至少有个单个的字母b，也可以当作回文子序列，然后看 new_start 
            // 和 new_end 如果不相同，说明两者各指向了不同的b，此时 res 应自增1，
            // 因为又增加了一个新的回文子序列 "bb"
            result++;
            if(new_start != new_end) {
                result++;
            }
            result += helper(characters, memo, new_start + 1, new_end);
        }
        memo[start][end] = (int)(result % mod);
        return memo[start][end];
    }
}
