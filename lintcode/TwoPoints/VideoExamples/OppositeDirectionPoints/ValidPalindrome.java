/** 
 * Refer to
 * http://www.lintcode.com/en/problem/valid-palindrome/
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
   Notice
    Have you consider that the string might be empty? This is a good question to ask during an interview.
    For the purpose of this problem, we define empty string as valid palindrome.
    Have you met this question in a real interview? Yes
    Example
    "A man, a plan, a canal: Panama" is a palindrome.
    "race a car" is not a palindrome.
 *
 * Solution
 * http://www.jiuzhang.com/solution/valid-palindrome/
 * http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou
 * https://discuss.leetcode.com/topic/8282/accepted-pretty-java-solution-271ms
*/
public class Solution {
    /*
     * @param s: A string
     * @return: Whether the string is a valid palindrome
     */
    public boolean isPalindrome(String s) {
        String str = s.toLowerCase();
        if(str == null || str.length() == 0) {
            return true;
        }
        int i = 0;
        int j = str.length() - 1;
        while(i < j) {
            if(!isValidCharacter(str.charAt(i))) {
                i++;
                continue;
            }
            if(!isValidCharacter(str.charAt(j))) {
                j--;
                continue;
            }
            if(str.charAt(i) != str.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
    
    private boolean isValidCharacter(char c) {
        return Character.isDigit(c) || Character.isLetter(c);
    }
}
