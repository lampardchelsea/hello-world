/**
 * Refer to
 * https://leetcode.com/problems/maximum-product-of-word-lengths/description/
 * Given a string array words, find the maximum value of length(word[i]) * length(word[j]) where 
   the two words do not share common letters. You may assume that each word will contain only 
   lower case letters. If no such two words exist, return 0.

    Example 1:
    Given ["abcw", "baz", "foo", "bar", "xtfn", "abcdef"]
    Return 16
    The two words can be "abcw", "xtfn".

    Example 2:
    Given ["a", "ab", "abc", "d", "cd", "bcd", "abcd"]
    Return 4
    The two words can be "ab", "cd".

    Example 3:
    Given ["a", "aa", "aaa", "aaaa"]
    Return 0
    No such pair of words.
 *
 *
 * Solution
 * https://discuss.leetcode.com/topic/35539/java-easy-version-to-understand
 * https://www.youtube.com/watch?v=tNM44ZCknp8
 * 
*/
class Solution {
    public int maxProduct(String[] words) {
        if(words == null || words.length == 0) {
            return 0;
        }
        int[] values = new int[words.length];
        for(int i = 0; i < words.length; i++) {
            for(int j = 0; j < words[i].length(); j++) {
                // Convert string into integer value representation
                // E.g "abc"
                // 1st digit 'a' -> 'a' - 'a' = 0 -> 1 << 0 -> 00001
                // 2nd digit 'b' -> 'b' - 'a' = 1 -> 1 << 1 -> 00010
                // 3rd digit 'c' -> 'c' - 'a' = 2 -> 1 << 2 -> 00100
                // values[0] |= 00001 | 00010 | 00100 = 00111 = 7
                // same thing for "ab" can be represent as 3
                values[i] |= 1 << words[i].charAt(j) - 'a';
            }
        }
        int result = 0;
        for(int i = 0; i < words.length; i++) {
            for(int j = i + 1; j < words.length; j++) {
                // E.g "abc" and "ab" -> 7 & 3 = 00111 & 00011 = 00011 != 0
                // on each position, the '1' represent a unique character
                // already occur, such as if 'b' occur, then 2nd position
                // from left will present as 1, 'c' occur, then 3rd position
                // from left will present as 1... Implement boolean operation 
                // '&' for two words will figure out whether duplicate
                // character happen in these 2 strings, no matter that character
                // like 'b' or 'c' occur at which place in original string,
                // only when two integer value representation after '&' equals
                // 0, we can say they have no duplicate characters
                if((values[i] & values[j]) == 0) {
                    if(words[i].length() * words[j].length() > result) {
                        result = words[i].length() * words[j].length();
                    }
                }       
            }
        }
        return result;
    }
}
