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
指针 j = 0 时指向T中的第一个b，然后开始匹配T，当 i = 6， j = 2 时，此时完全包含了T。暴力搜索解法中此时i会回到1继续找，而这里，我们通过
向前再次匹配T，会在 i = 3，j = 0 处停下，然后继续向后找，这样S中重复的b就会被跳过，从而大大的提高了效率，但是最坏情况下的时间复杂度还是 
O(mn)。旋转，跳跃，我闭着眼，尘嚣看不见，你沉醉了没？博主已经沉醉在这双指针之舞中了
*/

