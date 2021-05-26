/**
Refer to
https://www.lintcode.com/problem/bold-words-in-string/description
Description
Given a set of keywords words and a string S, make all appearances of all keywords in S bold. 
Any letters between <b> and </b> tags become bold. The returned string should use the least 
number of tags possible, and of course the tags should form a valid combination.

words has length in range [0, 50].
words[i] has length in range [1, 10].
S has length in range [0, 500].
All characters in words[i] and S are lowercase letters.

Example
Example 1:
Input:
["ab", "bc"]
"aabcd"
Output:
"a<b>abc</b>d"
Explanation:
Note that returning "a<b>a<b>b</b>c</b>d" would use more tags, so it is incorrect.

Example 2:
Input:
["bcccaeb","b","eedcbda","aeebebebd","ccd","eabbbdcde","deaaea","aea","accebbb","d"]
"ceaaabbbedabbecbcced"
Output:
"ceaaa<b>bbb</b>e<b>d</b>a<b>bb</b>ec<b>b</b>cce<b>d</b>"

Tags
Company
Google
*/

// Exactly same question for 616. Add Bold Tag in String
// Refer to
/**
Description
Given a string s and a list of strings dict, you need to add a closed pair of bold tag and to wrap the substrings 
in s that exist in dict. If two such substrings overlap, you need to wrap them together by only one pair of closed 
bold tag. Also, if two substrings wrapped by bold tags are consecutive, you need to combine them.

The given dict won't contain duplicates, and its length won't exceed 100. All strings entered have a length in the range [1,1000].

Example
Input: 
s = "abcxyz123"
dict = ["abc","123"]
Output:
"<b>abc</b>xyz<b>123</b>"
Input: 
s = "aaabbcc"
dict = ["aaa","aab","bc"]
Output:
"<b>aaabbc</b>c"
Tags
Company
Google
*/

// Solution 1: Boolean flag set on all positions then scan again to append <b> or </b> based on flag
// Refer to
// https://www.cnblogs.com/grandyang/p/8531642.html
/**
这道题跟之前的那道Add Bold Tag in String是一模一样的，之前还换个马甲，这次连场景都不换了，直接照搬啊？！我也是服气的～
这道题应该没有太多的技巧，就是照题目意思来就行了，我们使用一个数组bold，标记所有需要加粗的位置为true，初始化所有为false。
我们首先要判断每个单词word是否是S的子串，判断的方法就是逐个字符比较，遍历字符串S，找到和word首字符相等的位置，并且比较
随后和word等长的子串，如果完全相同，则将子串所有的位置在bold上比较为true。等我们知道了所有需要加粗的位置后，我们就可以
来生成结果res了，我们遍历bold数组，如果当前位置是true的话，表示需要加粗，那么我们首先看如果是第一个字符，或者其前面的
字符不用加粗，我们加上一个左标签<b>，然后我们将当前字符加入结果res中，然后再判断，如果当前是末尾字符，或者后面一个字符
不用加粗，则需要加上一个右标签</b>；如果当前位置是false，我们直接将字符加入结果res中即可，参见代码如下：
class Solution {
public:
    string boldWords(vector<string>& words, string S) {
        int n = S.size();
        string res = "";
        vector<bool> bold(n, false);      
        for (string word : words) {
            int len = word.size();
            for (int i = 0; i <= n - len; ++i) {
                if (S[i] == word[0] && S.substr(i, len) == word) {
                    for (int j = i; j < i + len; ++j) bold[j] = true;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            if (bold[i]) {
                if (i == 0 || !bold[i - 1]) res += "<b>";
                res.push_back(S[i]);
                if (i == n - 1 || !bold[i + 1]) res += "</b>";
            } else {
                res.push_back(S[i]);
            }
        }
        return res;
    }
};
*/
public class Solution {
    /**
     * @param words: the words
     * @param S: the string
     * @return: the string with least number of tags
     */
    public String boldWords(String[] words, String S) {
        int n = S.length();
        boolean[] status = new boolean[n];
        for(String word : words) {
            int len = word.length();
            // Must include i == n - len, to check the last position
            // as when i == n - len, i + len == n, then S.substring(i, i + len)
            // means S.substring(i, n) will actually cut off and including
            // last position character based on [inclusive, exclusive) rule
            for(int i = 0; i <= n - len; i++) {
                if(S.substring(i, i + len).equals(word)) {
                    for(int j = i; j < i + len; j++) {
                        status[j] = true;
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            // Tricky point: two if conditions are parallel since the position
            // may match both case, and the sequence added for <b>, original
            // character and </b> should be kept, and since we always need
            // original character, put it in the middle
            if(status[i]) {
                if(i == 0 || !status[i - 1]) {
                    sb.append("<b>");
                }
                sb.append(S.charAt(i));
                if(i == n - 1 || !status[i + 1]) {
                    sb.append("</b>");
                }
            } else {
                sb.append(S.charAt(i));
            }
        }
        return sb.toString();
    }
}
