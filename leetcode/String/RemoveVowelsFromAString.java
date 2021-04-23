/**
Refer to
Given a string S, remove the vowels 'a', 'e', 'i', 'o', and 'u' from it, and return the new string.

Example 1:
Input: "leetcodeisacommunityforcoders"
Output: "ltcdscmmntyfrcdrs"

Example 2:
Input: "aeiou"
Output: ""

Note:
1.S consists of lowercase English letters only.
2.1 <= S.length <= 1000
*/
class Solution {
    public String removeVowels(String S) {
        StringBuilder sb = new StringBuilder();
        Set<Character> set = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u'));
        for (char c : S.toCharArray()) {
            if (!set.contains(c)) {
                sb.append(c);
            }
        }        
        return sb.toString();
    }
}
