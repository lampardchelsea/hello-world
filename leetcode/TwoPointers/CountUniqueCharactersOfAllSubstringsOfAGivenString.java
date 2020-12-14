/**
Refer to
https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/
Let's define a function countUniqueChars(s) that returns the number of unique characters on s, for example if 
s = "LEETCODE" then "L", "T","C","O","D" are the unique characters since they appear only once in s, therefore 
countUniqueChars(s) = 5.

On this problem given a string s we need to return the sum of countUniqueChars(t) where t is a substring of s. 
Notice that some substrings can be repeated so on this case you have to count the repeated ones too.

Since the answer can be very large, return the answer modulo 10 ^ 9 + 7.

Example 1:
Input: s = "ABC"
Output: 10
Explanation: All possible substrings are: "A","B","C","AB","BC" and "ABC".
Evey substring is composed with only unique letters.
Sum of lengths of all substring is 1 + 1 + 1 + 2 + 2 + 3 = 10

Example 2:
Input: s = "ABA"
Output: 8
Explanation: The same as example 1, except countUniqueChars("ABA") = 1.

Example 3:
Input: s = "LEETCODE"
Output: 92

Constraints:
0 <= s.length <= 10^4
s contain upper-case English letters only.
*/

// Solution 1: Two Pointers + One pass O(N)
// Refer to
// https://www.cnblogs.com/grandyang/p/11616485.html
/**
这道题给了我们一个字符串S，要统计其所有的子串中不同字符的个数之和，这里的子串是允许重复的，而且说结果需要对一个超大数取余，
这暗示了返回值可能会很大，这样的话对于纯暴力的解法，比如遍历所有可能的子串并统计不同字符的个数的这种解法肯定是不行的。
这道题还真是一点没有辱没其 Hard 标签，确实是一道很有难度的题，不太容易想出正确解法。还好有 [李哥 lee215 的帖子]
(https://leetcode.com/problems/unique-letter-string/discuss/128952/One-pass-O(N)-Straight-Forward)，一个帖子的
点赞数超过了整个第一页所有其他帖子的点赞数之和，简直是刷题界的 Faker，你李哥永远是你李哥。这里就按照李哥的帖子来讲解吧，
首先来看一个字符串 CACACCAC，若想让第二个A成为子串中的唯一，那么必须要知道其前后两个相邻的A的位置，比如 CA(CACC)AC，
括号中的子串 CACC 中A就是唯一的存在，同样，对于 CAC(AC)CAC，括号中的子串 AC 中A也是唯一的存在。这样就可以观察出来，
只要左括号的位置在第一个A和第二个A之间（共有2个位置），右括号在第二个A和第三个A之间（共有3个位置），这样第二个A在6个子串
中成为那个唯一的存在。换个角度来说，只有6个子串可以让第二个A作为单独的存在从而在结果中贡献。这是个很关键的转换思路，
与其关注每个子串中的单独字符个数，不如换个角度，对于每个字符，统计其可以在多少个子串中成为单独的存在，同样可以得到正确的结果。
这样的话，每个字母出现的位置就很重要了，由于上面的分析说了，只要知道三个位置，就可以求出中间的字母的贡献值，为了节省空间，
只保留每个字母最近两次的出现位置，这样加上当前位置i，就可以知道前一个字母的贡献值了。这里使用一个长度为 26x2 的二维数组 idx，
因为题目中限定了只有26个大写字母。这里只保留每个字母的前两个出现位置，均初始化为 -1。然后遍历S中每个字母，对于每个字符减去A，
就是其对应位置，此时将前一个字母的贡献值累加到结果 res 中，假如当前字母是首次出现，也不用担心，前两个字母的出现位置都是 -1，
相减后为0，所以累加值还是0。然后再更新 idx 数组的值。由于每次都是计算该字母前一个位置的贡献值，所以最后还需要一个 for 
循环去计算每个字母最后出现位置的贡献值，此时由于身后没有该字母了，就用位置N来代替即可
*/

// Refer to
// https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/discuss/128952/C%2B%2BJavaPython-One-pass-O(N)
/**
Intuition
Let's think about how a character can be found as a unique character.

Think about string "XAXAXXAX" and focus on making the second "A" a unique character.
We can take "XA(XAXX)AX" and between "()" is our substring.
We can see here, to make the second "A" counted as a uniq character, we need to:

insert "(" somewhere between the first and second A
insert ")" somewhere between the second and third A
For step 1 we have "A(XA" and "AX(A", 2 possibility.
For step 2 we have "A)XXA", "AX)XA" and "AXX)A", 3 possibilities.

So there are in total 2 * 3 = 6 ways to make the second A a unique character in a substring.
In other words, there are only 6 substring, in which this A contribute 1 point as unique string.

Instead of counting all unique characters and struggling with all possible substrings,
we can count for every char in S, how many ways to be found as a unique char.
We count and sum, and it will be out answer.

Explanation
index[26][2] record last two occurrence index for every upper characters.
Initialise all values in index to -1.
Loop on string S, for every character c, update its last two occurrence index to index[c].
Count when loop. For example, if "A" appears twice at index 3, 6, 9 seperately, we need to count:
For the first "A": (6-3) * (3-(-1))"
For the second "A": (9-6) * (6-3)"
For the third "A": (N-9) * (9-6)"

Complexity
One pass, time complexity O(N).
Space complexity O(1).
*/
class Solution {
    public int uniqueLetterString(String s) {
        int[][] index = new int[26][2];
        for(int i = 0; i < 26; i++) {
            Arrays.fill(index[i], -1);
        }
        int result = 0;
        int mod = 1000000007;
        int n = s.length();
        for(int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'A';
            result = (result + (i - index[c][1]) * (index[c][1] - index[c][0]) % mod) % mod;
            index[c][0] = index[c][1];
            index[c][1] = i;
        }
        for(int c = 0; c < 26; c++) {
            result = (result + (n - index[c][1]) * (index[c][1] - index[c][0]) % mod) % mod;
        }
        return result;
    }
}
