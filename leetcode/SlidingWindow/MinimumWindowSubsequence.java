/**
Refer to
https://www.lintcode.com/problem/minimum-window-subsequence/description
Given strings S and T, find the minimum (contiguous) substring W of S, so that T is a subsequence of W.

If there is no such window in S that covers all characters in T, return the empty string "". If there are 
multiple such minimum-length windows, return the one with the smallest starting index.

Example
Example 1:
Input：S="jmeqksfrsdcmsiwvaovztaqenprpvnbstl"，T="u"
Output：""
Explanation： unable to match

Example 2:
Input：S = "abcdebdde"， T = "bde"
Output："bcde"
Explanation："bcde" is the answer and "deb" is not a smaller window because the elements of T in the window 
must occur in order.

Notice
All the strings in the input will only contain lowercase letters.
The length of S will be in the range [1, 20000].
The length of T will be in the range [1, 100].
*/

// Solution 1: Not fixed length sliding window + Two points
// Refer to
// https://www.cnblogs.com/grandyang/p/8684817.html
/**
论坛上的 danzhutest大神 提出了一种双指针的解法，其实这是优化过的暴力搜索的方法，而且居然 beat 了 100%，给跪了好嘛？！而且这双指针的跳跃
方式犹如舞蹈般美妙绝伦，比那粗鄙的暴力搜索双指针不知道高到哪里去了？！举个栗子来说吧，比如当 S = "bbbbdde", T = "bde" 时，我们知道暴力
搜索的双指针在S和T的第一个b匹配上之后，就开始检测S之后的字符能否包含T之后的所有字符，当匹配结束后，S的指针就会跳到第二个b开始匹配，由于
有大量的重复b出现，所以每一个b都要遍历一遍，会达到平方级的复杂度，会被 OJ 无情拒绝。而下面这种修改后的算法会跳过所有重复的b，使得效率大大
提升，具体是这么做的，当第一次匹配成功后，我们的双指针往前走，找到那个刚好包含T中字符的位置，比如开始指针 i = 0 时，指向S中的第一个b，
指针 j = 0 时指向T中的第一个b，然后开始匹配T，当 i = 6， j = 2 时，此时完全包含了T。在暴力搜索解法中此时i会回到1继续找，但是在这里，我们
通过内层while循环逆序向前在刚刚找到的已经匹配了T的S的子串中再次寻找更短的子串使其能够匹配T，会在 i = 3，j = 0 处停下，这样就能跳过之前
已经找到的S的子串中所有重复的b(从 i = 0 到 i = 2 的b都被跳过)，然后重复顺序加逆序查找这个过程继续向后找，从而大大的提高了效率，但是最坏
情况下的时间复杂度还是O(mn)。旋转，跳跃，我闭着眼，尘嚣看不见，你沉醉了没？博主已经沉醉在这双指针之舞中了
*/

// Refer to
// http://shibaili.blogspot.com/2018/12/727-minimum-window-subsequence.html
/**
Solution #1, 指针
找到一个匹配之后，以结尾为起始点，倒退着往前找。这样找到的是在[i, j]里最短的符合要求的substring。最坏结果是找到跟原来一摸一样的。
O(m * n) time. 对Complexity的需要研究一下

ref： https://leetcode.com/problems/minimum-window-subsequence/discuss/109356/JAVA-two-pointer-solution-(12ms-beat-100)-with-explaination
class Solution {
    public String minWindow(String s, String t) {
        int si = 0, ti = 0;
        String rt = s + "123";
        while (si < s.length()) {
            if (s.charAt(si) == t.charAt(ti)) {
                if (ti == t.length() - 1) {
                    int end = si;                    
                    while (ti >= 0) {
                        while (s.charAt(si) != t.charAt(ti)) {
                            si--;
                        }
                        ti--;
                        si--;
                    }
                    
                    si++;
                    if (rt.length() > end - si + 1) {
                        rt = s.substring(si, end + 1);
                    }
                }
                ti++;
            }
            
            si++;
        }
        
        return rt.equals(s + "123") ? "" : rt;
    }
}
*/
public class Solution {
    /**
     * @param S: a string
     * @param T: a string
     * @return: the minimum substring of S
     */
    public String minWindow(String S, String T) {
        int i = 0; // index i for scanning S
        int j = 0; // index j for scanning T
        String tmp = S + ".";
        // Outside while loop try to find first substring that T as subsequence,
        // for now ignore requirement for minimum substring
        // e.g S = "bbbbdde", T = "bde", then first substring will be "bbbbdde"
        while(i < S.length()) {
            if(S.charAt(i) == T.charAt(j)) {
                // When we found full T as subsequence as substring of S we start
                // move backwards to remove duplicate chars of first char in
                // current found substring of S
                // e.g currently we found first substring as "bbbbdde" of S that
                // contains T = "bde" as subsequence, after inner while loop, it
                // will remove all duplicate 'b' in front of last 'b', so the 
                // minimum substring will update as "bdde"
                if(j == T.length() - 1) {
                    int end = i;
                    while(j >= 0) {
                        while(S.charAt(i) != T.charAt(j)) {
                            i--;
                        }
                        i--;
                        j--;
                    }
                    // Make up the additional -1 in while loop
                    i++;
                    // Update the substring to minimum length one
                    if(tmp.length() > end - i + 1) {
                        tmp = S.substring(i, end + 1);
                    }
                }
                // When we find one digital in S match T increase index for scanning T
                j++;
            }
            i++;
        }
        // In case not found (no change) then return empty String
        return tmp.equals(S + ".") ? "" : tmp;
    }
}


Solution 2 (360min, too long to come up with dp[i][j] = k definition and dp equation)

public class Solution { 
    /** 
     * @param s: a string 
     * @param t: a string 
     * @return: the minimum substring of S 
     */ 
    public String minWindow(String s, String t) { 
        // Write your code here 
        // dp[i][j] = k denotes length = i substring of s(s[0...i]), there exists a subsequence 
        // corresponding to length = j substring of t(t[0...j]) starting at index k of s, k = -1 for not exists 
        // ---------------------------------------------------------------- 
        // Example 1: 
        // s = "b" 
        // t = "b" 
        // dp = new int[1 + s.length()][1 + t.length()] = new int[2][2] 
        //    * b 
        //  * ? ? 
        //  b ? 0 
        // dp[1][1] = 0 denotes start from index = 0 length = 1 substring of s(as "b"), there exists 
        // a subsequence corresponding to length = 1 substring of t(as "b") 
        // ---------------------------------------------------------------- 
        // Example 2: 
        // s = "d" 
        // t = "b" 
        // dp = new int[1 + s.length()][1 + t.length()] = new int[2][2] 
        //    * d 
        //  * ? ? 
        //  b ? -1 
        // dp[1][1] = -1 denotes not k exists after go over s and t 
        // ---------------------------------------------------------------- 
        // Example 3: 
        // s = "dbd" 
        // t = "bd" 
        // dp = new int[1 + s.length()][1 + t.length()] = new int[3][2] 
        //    *  b  d 
        //  * ?  ?  ? 
        //  d ? -1 -1 
        //  b ?  1 -1 
        //  d ?  1  1 
        // '?' question mark is boundary, for now just leave it as placeholder, let's check value 1 and -1 positions: 
        // (1) For dp[i][j] if j > i, then substring length of t > s, surely substring of s has no subsequence match  
        // substring of t, dp[i][j] must be -1 
        // (2) For dp[2][1] = 1, the substring of s as "db" has a subsequence as "b" match target subtring of t as "b", 
        // its starting index = 1 in s 
        // (3) For dp[3][1] = 1, the substring of s as "dbd" has a subsequence as "b" match target substring of t as "b", 
        // its starting index = 1 in s 
        // (4) For dp[3][2] = 1, the substring of s as "dbd" has a subsequence as "bd" match target substring of t as "bd", 
        // its starting index = 1 in s 
        // Compare (2) and (4), starting index = 1 of case (4) that "dbd" contains "bd" equals to case (2) that "bd"  
        // contains "b" when both case (2) subtring s and t expand same char 'd' into case (4), so when s[i] = t[j],  
        // dp[i][j] = dp[i - 1][j - 1] 
        // Compare (2) and (3), starting index = 1 of case (3) that "dbd" contains "b" equals to case (2) that "bd"  
        // contains "b" when only case (2) subtring s "db" expand char 'd' into case (3), so when s[i] != t[j], 
        // dp[i][j] = dp[i - 1][j] 
        // Now come back for '?' question mark: 
        // For first line dp[0][0] = 0 special one, and since j always > i on first line, so dp[i][j] all = -1 on first line 
        // For first column dp[i][0] = i, means length = i substring of s(s[0...i]), there exists a subsequence corresponding 
        // to length = 0 substring of t(empty string "") starting at index i of s (start at i, end at i, also empty string "") 
        // Compeleted dp table is below: 
        //    *  b  d 
        //  * 0 -1 -1  
        //  d 1 -1 -1 
        //  b 2  1 -1 
        //  d 3  1  1 
        // To find out if substring of s contains subsequence match t, we have to scan line by line of dp table, only if 
        // dp[i][t_len] != -1 means full t string contained successfully as a subsequence of s substring, and since we 
        // record start index as dp[i][t_len] of that substring in s and end index as i, we can calculate each candidate 
        // specifically and then compare all of them to find a global minimum substring 
         
        int minLen = Integer.MAX_VALUE; 
        int minStart = -1; 
        // Initialize dp table 
        int s_len = s.length(); 
        int t_len = t.length(); 
        int[][] dp = new int[1 + s_len][1 + t_len]; 
        // Initialize first column (dp[0][0] as 0, no need re-assign) 
        for(int i = 1; i <= s_len; i++) { 
            dp[i][0] = i; 
        } 
        // Initialize all remain columns as -1 
        for(int i = 0; i <= s_len; i++) { 
            for(int j = 1; j <= t_len; j++) { 
                dp[i][j] = -1; 
            } 
        } 
        // Assign value to remain cells in dp table with equations and find minimum substring line by line, 
        // no need to check first line or column as first line keep as -1 and first column set value for  
        // containing empty string t "" which not the case for actual input t 
        for(int i = 1; i <= s_len; i++) { 
            // Math.min(i, t_len) as upper boundary for j because when t_len larger than current substring s 
            // length as i will be always invalid as case (1) discussed 
            for(int j = 1; j <= Math.min(i, t_len); j++) { 
                if(s.charAt(i - 1) == t.charAt(j - 1)) { 
                    dp[i][j] = dp[i - 1][j - 1]; 
                } else { 
                    dp[i][j] = dp[i - 1][j]; 
                } 
            } 
            // Match condition dp[i][t_len] != -1 means find a candidate 
            if(dp[i][t_len] != -1) { 
                int len = i - dp[i][t_len]; 
                if(minLen > len) { 
                    minLen = len; 
                    minStart = dp[i][t_len]; 
                } 
            } 
        } 
        return minStart == - 1 ? "" : s.substring(minStart, minStart + minLen); 
    } 
}

Space Complexity: O(mn)    
Time Complexity: O(mn)

How DP solution comes up ?
https://www.cnblogs.com/grandyang/p/8684817.html

这道题给了我们两个字符串S和T，让我们找出S的一个长度最短子串W，使得T是W的子序列，如果长度相同，取起始位置靠前的。清楚子串和子序列的区别，
那么题意就不难理解，题目中给的例子也很好的解释了题意。我们经过研究可以发现，返回的子串的起始字母和T的起始字母一定相同，这样才能保证最短。
那么你肯定会想先试试暴力搜索吧，以S中每个T的起始字母为起点，均开始搜索字符串T，然后维护一个子串长度的最小值。如果是这种思路，那么还是
趁早打消念头吧，博主已经替你试过了，OJ 不依。原因也不难想，假如S中有大量的连续b，并且如果T也很长的话，这种算法实在是不高效啊。
根据博主多年经验，这种玩字符串且还是 Hard 的题，十有八九都是要用动态规划 Dynamic Programming 来做的，那么就直接往 DP 上去想吧。
DP 的第一步就是设计 dp 数组，像这种两个字符串的题，一般都是一个二维数组，想想该怎么定义。确定一个子串的两个关键要素是起始位置和长度，
那么我们的 dp 值到底应该是定起始位置还是长度呢？That is a question! 仔细想一想，其实起始位置是长度的基础，因为我们一旦知道了起始位置，
那么当前位置减去起始位置，就是长度了，所以我们 dp 值定为起始位置。那么 dp[i][j] 表示范围S中前i个字符包含范围T中前j个字符的子串的起始位置，
注意这里的包含是子序列包含关系。然后就是确定长度了，有时候会使用字符串的原长度，有时候会多加1，看个人习惯吧，这里博主长度多加了个1。

OK，下面就是重中之重啦，求状态转移方程。一般来说，dp[i][j] 的值是依赖于之前已经求出的dp值的，在递归形式的解法中，dp数组也可以看作是记忆
数组，从而省去了大量的重复计算，这也是 dp 解法凌驾于暴力搜索之上的主要原因。牛B的方法总是最难想出来的，dp 的状态转移方程就是其中之一。
在脑子一片浆糊的情况下，博主的建议是从最简单的例子开始分析，比如 S = "b", T = "b", 那么我们就有 dp[1][1] = 0，因为S中的起始位置为0，
长度为1的子串可以包含T。如果当 S = "d", T = "b"，那么我们有 dp[1][1] = -1，因为我们的dp数组初始化均为 -1，表示未匹配或者无法匹配。
下面来看一个稍稍复杂些的例子，S = "dbd", T = "bd"，我们的dp数组是：

   ∅  b  d
∅  ?  ?  ?
d  ? -1 -1
b  ?  1 -1
d  ?  1  1

这里的问号是边界，我们还不知道如何初给边界赋值，我们看到，为 -1 的地方是对应的字母不相等的地方。我们首先要明确的是 dp[i][j] 中的j不能大于i，
因为T的长度不能大于S的长度，所以j大于i的 dp[i][j] 一定都是-1的。再来看为1的几个位置，首先是 dp[2][1] = 1，这里表示db包含b的子串起始位置为1，
make sense！然后是 dp[3][1] = 1，这里表示 dbd 包含b的子串起始位置为1，没错！然后是 dp[3][2] = 1，这里表示 dbd 包含 bd 的起始位置为1，
all right! 那么我们可以观察出，当 S[i] == T[j] 的时候，实际上起始位置和 dp[i - 1][j - 1] 是一样的，比如 dbd 包含 bd 的起始位置和 db 
包含b的起始位置一样，所以可以继承过来。那么当 S[i] != T[j] 的时候，怎么搞？其实是和 dp[i - 1][j] 是一样的，比如 dbd 包含b的起始位置和 db 
包含b的起始位置是一样的。

嗯，这就是状态转移方程的核心了，下面再来看边界怎么赋值，由于j比如小于等于i，所以第一行的第二个位置往后一定都是-1，我们只需要给第一列赋值即可。
通过前面的分析，我们知道了当 S[i] == T[j] 时，我们取的是左上角的 dp 值，表示当前字母在S中的位置，由于我们dp数组提前加过1，所以第一列的数只要
赋值为当前行数即可。最终的 dp 数组如下：

   ∅  b  d
∅  0 -1 -1
d  1 -1 -1
b  2  1 -1
d  3  1  1

为了使代码更加简洁，我们在遍历完每一行，检测如果 dp[i][n] 不为-1，说明T已经被完全包含了，且当前的位置跟起始位置都知道了，我们计算出长度来更新
一个全局最小值 minLen，同时更新最小值对应的起始位置 start，最后取出这个全局最短子串，如果没有找到返回空串即可
