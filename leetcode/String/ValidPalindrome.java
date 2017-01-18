// Refer to
// http://stackoverflow.com/questions/4047808/what-is-the-best-way-to-tell-if-a-character-is-a-letter-or-number-in-java-withou
// https://discuss.leetcode.com/topic/8282/accepted-pretty-java-solution-271ms
public class Solution {
    public boolean isPalindrome(String s) {
        if(s.length() == 0) {
            return true;
        }
        // First transfer string into lower case characaters
        char[] chars = s.toLowerCase().toCharArray();
        int i = 0;
        int j = chars.length - 1;
        while(i < j) {
            // Skip non-alphanumeric char and start next round with continue,
            // otherwise it will start compare on next index char, if it is
            // not alphanumeric, result will wrong
            // e.g input as "A man, a plan, a canal: Panama", expected true,
            // result is wrong
            if(!isAlphaNumeric(chars[i])) {
                i++;
                continue;
            }
            if(!isAlphaNumeric(chars[j])) {
                j--;
                continue;
            }
            if(chars[i] != chars[j]) {
                return false;
            }
            i++;
            j--;
        }
        
        return true;
    }
    
    public boolean isAlphaNumeric(char c) {
        // a - z (97 - 122) or A - Z (65 - 90) or 0 - 9
        if(c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
            return true;
        }
        return false;
    }
}
