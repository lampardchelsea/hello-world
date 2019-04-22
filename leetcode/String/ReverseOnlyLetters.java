/**
 Refer to
 https://leetcode.com/problems/reverse-only-letters/
 Given a string S, return the "reversed" string where all characters that are not a letter stay 
 in the same place, and all letters reverse their positions.

Example 1:
Input: "ab-cd"
Output: "dc-ba"

Example 2:
Input: "a-bC-dEf-ghIj"
Output: "j-Ih-gfE-dCba"

Example 3:
Input: "Test1ng-Leet=code-Q!"
Output: "Qedo1ct-eeLg=ntse-T!"

Note:
S.length <= 100
33 <= S[i].ASCIIcode <= 122 
S doesn't contain \ or "
*/
// Solution 1: Two pointers
class Solution {
    public String reverseOnlyLetters(String S) {
        int i = 0;
        int j = S.length() - 1;
        char[] chars = S.toCharArray();
        while(i < j) {
            char temp_i = chars[i];
            char temp_j = chars[j];
            if(isLetter(temp_i) && isLetter(temp_j)) {
                if(temp_i != temp_j) {
                    char temp = chars[i];
                    chars[i] = chars[j];
                    chars[j] = temp;
                }
                i++;
                j--;
            } else if(!isLetter(temp_i)) {
                i++;
            } else if(!isLetter(temp_j)) {
                j--;
            }
        }
        return new String(chars);
    }
    
    private boolean isLetter(char temp) {
        return (temp >= 'a' && temp <= 'z') || (temp >= 'A' && temp <= 'Z');
    }
}
