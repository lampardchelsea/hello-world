/**
Refer to
https://leetcode.com/problems/goat-latin/
A sentence sentence is given, composed of words separated by spaces. Each word consists of lowercase and uppercase letters only.

We would like to convert the sentence to "Goat Latin" (a made-up language similar to Pig Latin.)

The rules of Goat Latin are as follows:

If a word begins with a vowel (a, e, i, o, or u), append "ma" to the end of the word.
For example, the word 'apple' becomes 'applema'.
 
If a word begins with a consonant (i.e. not a vowel), remove the first letter and append it to the end, then add "ma".
For example, the word "goat" becomes "oatgma".
 
Add one letter 'a' to the end of each word per its word index in the sentence, starting with 1.
For example, the first word gets "a" added to the end, the second word gets "aa" added to the end and so on.
Return the final sentence representing the conversion from sentence to Goat Latin. 

Example 1:
Input: sentence = "I speak Goat Latin"
Output: "Imaa peaksmaaa oatGmaaaa atinLmaaaaa"

Example 2:
Input: sentence = "The quick brown fox jumped over the lazy dog"
Output: "heTmaa uickqmaaa rownbmaaaa oxfmaaaaa umpedjmaaaaaa overmaaaaaaa hetmaaaaaaaa azylmaaaaaaaaa ogdmaaaaaaaaaa"

Notes:
sentence contains only uppercase, lowercase and spaces. Exactly one space between each word.
1 <= sentence.length <= 150.
*/

// Solution 1:
// Refer to
// https://leetcode.com/problems/goat-latin/discuss/127024/C%2B%2BJavaPython-Straight-Forward-Solution
/**
Explanation
Build a vowel set.(optional)
Split 'S' to words.
For each word, check if it starts with a vowel. (O(1) complexity with set).
If it does, keep going. If it does not, rotate the first letter to the end.
Add it to result string.

    public String toGoatLatin(String S) {
        Set<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        String res = "";
        int i = 0, j = 0;
        for (String w : S.split("\\s")) {
            res += ' ' + (vowels.contains(w.charAt(0)) ? w : w.substring(1) + w.charAt(0)) + "ma";
            for (j = 0, ++i; j < i; ++j) {
                res += "a";
            }
        };
        return res.substring(1);
    }
*/
class Solution {
    public String toGoatLatin(String sentence) {
        Set<Character> vowels = new HashSet<Character>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        String appendix = "a";
        StringBuilder sb = new StringBuilder();
        String[] strs = sentence.split("\\s+");
        for(String str : strs) {
            sb.append(vowels.contains(str.charAt(0)) ? str : str.substring(1) + str.charAt(0)).append("ma");
            sb.append(appendix).append(" ");
            appendix = appendix + "a";
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }
}
