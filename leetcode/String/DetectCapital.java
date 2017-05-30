/**
 * Refer to
 * https://leetcode.com/problems/detect-capital/#/description 
 * Given a word, you need to judge whether the usage of capitals in it is right or not.
 * We define the usage of capitals in a word to be right when one of the following cases holds:
    All letters in this word are capitals, like "USA".
    All letters in this word are not capitals, like "leetcode".
    Only the first letter in this word is capital if it has more than one letter, like "Google".
 * Otherwise, we define that this word doesn't use capitals in a right way.
    Example 1:
    Input: "USA"
    Output: True

    Example 2:
    Input: "FlaG"
    Output: False
 *
 * Solution
 * https://discuss.leetcode.com/topic/79911/simple-java-solution-o-n-time-o-1-space
*/
public class Solution {
    public boolean detectCapitalUse(String word) {
        return word.equals(word.toUpperCase()) ||
               word.equals(word.toLowerCase()) ||
               Character.isUpperCase(word.charAt(0)) &&
               word.substring(1).equals(word.substring(1).toLowerCase());
    }
}
