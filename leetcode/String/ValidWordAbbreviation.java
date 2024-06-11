/**
 * Given a non-empty string s and an abbreviation abbr, return whether the string matches with the given abbreviation.
 * A string such as "word" contains only the following valid abbreviations:
 * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
 * Notice that only the above abbreviations are valid abbreviations of the string "word". Any other string is not 
 * a valid abbreviation of "word".
 * 
 * Note:
 * Assume s contains only lowercase letters and abbr contains only lowercase letters and digits.
 * Example 1:
 * Given s = "internationalization", abbr = "i12iz4n":
 * Return true.
 * 
 * Example 2:
 * Given s = "apple", abbr = "a2e":
 * Return false.
*/
/**
 * Refer to
 * https://discuss.leetcode.com/topic/61348/short-and-easy-to-understand-java-solution
 * http://www.cnblogs.com/grandyang/p/5930369.html
 * 
 * 这道题让我们验证单词缩写，关于单词缩写LeetCode上还有两道相类似的题目Unique Word Abbreviation
 * 和Generalized Abbreviation。这道题给了我们一个单词和一个缩写形式，让我们验证这个缩写形式是否是
 * 正确的，由于题目中限定了单词中只有小写字母和数字，所以我们只要对这两种情况分别处理即可。我们使用双
 * 指针分别指向两个单词的开头，循环的条件是两个指针都没有到各自的末尾，如果指向缩写单词的指针指的是一
 * 个数字的话，如果当前数字是0，返回false，因为数字不能以0开头，然后我们要把该数字整体取出来，所以我们
 * 用一个while循环将数字整体取出来，然后指向原单词的指针也要对应的向后移动这么多位数。如果指向缩写单
 * 词的指针指的是一个字母的话，那么我们只要比两个指针指向的字母是否相同，不同则返回false，相同则两个
 * 指针均向后移动一位
 */
public class ValidWordAbbreviation {
	public static boolean validWordAbbreviation(String word, String abbr) {
		// Pointer for word
		int i = 0;
		// Pointer for abbr
		int j = 0;
		int wLen = word.length();
		int aLen = abbr.length();
		while(i < wLen && j < aLen) {
			if(abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
				// The number cannot start with 0 in abbr
				if(abbr.charAt(j) == '0') {
					return false;
				}
				// Retrieve the whole number
				int val = 0;
				while(j < aLen && abbr.charAt(j) >= '0' && abbr.charAt(j) <= '9') {
					val = val * 10 + abbr.charAt(j) - '0';
					j++;
				}
				// Move pointer i as same as val positions
				i += val;
			} else {
				if(word.charAt(i++) != abbr.charAt(j++)) {
					return false;
				}
			}
		}
		// If till end two pointers both move to the final position, 
		// return true, otherwise return false
		return i == wLen && j == aLen;
	}
	
	public static void main(String[] args) {
		String word = "internationalization";
		String abbr = "i12iz4n";
		//String word = "apple";
		//String abbr = "a2e";
		boolean result = validWordAbbreviation(word, abbr);
		System.out.println(result);
	}
}












































































https://www.cnblogs.com/cnoodle/p/16454352.html
A string can be abbreviated by replacing any number of non-adjacent, non-empty substrings with their lengths. The lengths should not have leading zeros.
For example, a string such as "substitution" could be abbreviated as (but not limited to):
- "s10n" ("s ubstitutio n")
- "sub4u4" ("sub stit u tion")
- "12" ("substitution")
- "su3i1u2on" ("su bst i t u ti on")
- "substitution" (no substrings replaced)
The following are not valid abbreviations:
- "s55n" ("s ubsti tutio n", the replaced substrings are adjacent)
- "s010n" (has leading zeros)
- "s0ubstitution" (replaces an empty substring)
Given a string word and an abbreviation abbr, return whether the string matches the given abbreviation.
A substring is a contiguous non-empty sequence of characters within a string.
Example 1:
Input: word = "internationalization", abbr = "i12iz4n"
Output: true
Explanation: The word "internationalization" can be abbreviated as "i12iz4n" ("i nternational iz atio n").

Example 2:
Input: word = "apple", abbr = "a2e"
Output: false
Explanation: The word "apple" cannot be abbreviated as "a2e".
Constraints:
- 1 <= word.length <= 20
- word consists of only lowercase English letters.
- 1 <= abbr.length <= 10
- abbr consists of lowercase English letters and digits.
- All the integers in abbr will fit in a 32-bit integer.
--------------------------------------------------------------------------------
Attempt 1: 2024-06-10
Solution 1: Two Pointers (10 min)
class Solution {
    public boolean validWordAbbreviation(String word, String abbr) {
        // corner case
        if (word == null || abbr == null) {
            return false;
        }

        // normal case
        int i = 0;
        int j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                i++;
                j++;
            } else if (Character.isDigit(abbr.charAt(j)) && abbr.charAt(j) != '0') {
                int num = 0;
                while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
                    num = num * 10 + abbr.charAt(j) - '0';
                    j++;
                }
                i += num;
            } else {
                return false;
            }
        }
        return i == word.length() && j == abbr.length();
    }
}

Time Complexity: O(N)
Space Complexity: O(1)

Refer to
https://www.cnblogs.com/cnoodle/p/16454352.html
思路是双指针，两个指针 i, j 分别指向 word 和 abbr。如果两边指向的字母相同，则分别往前走一步；如果 abbr 这一边指向了一个不是 0 开头的数字，则我们算一下这个数字 num 到底是多少，然后让 i 指针往前走 num 步。如果顺利，最后两个指针应该是同时到达 word 和 abbr 的尾部。
时间O(n)
空间O(1)
Java实现
class Solution {
    public boolean validWordAbbreviation(String word, String abbr) {
        // corner case
        if (word == null || abbr == null) {
            return false;
        }

        // normal case
        int i = 0;
        int j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (word.charAt(i) == abbr.charAt(j)) {
                i++;
                j++;
            } else if (Character.isDigit(abbr.charAt(j)) && abbr.charAt(j) != '0') {
                int num = 0;
                while (j < abbr.length() && Character.isDigit(abbr.charAt(j))) {
                    num = num * 10 + abbr.charAt(j) - '0';
                    j++;
                }
                i += num;
            } else {
                return false;
            }
        }
        return i == word.length() && j == abbr.length();
    }
}
