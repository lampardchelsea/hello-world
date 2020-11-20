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
