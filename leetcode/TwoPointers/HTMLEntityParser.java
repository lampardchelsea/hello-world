/**
Refer to
https://leetcode.com/problems/html-entity-parser/
HTML entity parser is the parser that takes HTML code as input and replace all the entities of the special characters by the characters itself.

The special characters and their entities for HTML are:

Quotation Mark: the entity is &quot; and symbol character is ".
Single Quote Mark: the entity is &apos; and symbol character is '.
Ampersand: the entity is &amp; and symbol character is &.
Greater Than Sign: the entity is &gt; and symbol character is >.
Less Than Sign: the entity is &lt; and symbol character is <.
Slash: the entity is &frasl; and symbol character is /.
Given the input text string to the HTML parser, you have to implement the entity parser.

Return the text after replacing the entities by the special characters.

Example 1:
Input: text = "&amp; is an HTML entity but &ambassador; is not."
Output: "& is an HTML entity but &ambassador; is not."
Explanation: The parser will replace the &amp; entity by &

Example 2:
Input: text = "and I quote: &quot;...&quot;"
Output: "and I quote: \"...\""

Example 3:
Input: text = "Stay home! Practice on Leetcode :)"
Output: "Stay home! Practice on Leetcode :)"

Example 4:
Input: text = "x &gt; y &amp;&amp; x &lt; y is always false"
Output: "x > y && x < y is always false"

Example 5:
Input: text = "leetcode.com&frasl;problemset&frasl;all"
Output: "leetcode.com/problemset/all"

Constraints:
1 <= text.length <= 10^5
The string may contain any possible characters out of all the 256 ASCII characters.
*/

// Solution 1: Find '&' first then scan forwards till ';', but need to skip all continuous heading '&' and start only at the last one
// Refer to 1807. Evaluate the Bracket Pairs of a String
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/TwoPointers/EvaluateTheBracketPairsOfAString.java
class Solution {
    public String entityParser(String text) {
        //if(text.equals("&&gt;")) {
        //    return "&>";
        //}
        Map<String, String> map = new HashMap<String, String>();
        map.put("&quot", "\"");
        map.put("&apos", "'");
        map.put("&amp", "&");
        map.put("&gt", ">");
        map.put("&lt", "<");
        map.put("&frasl", "/");
        int n = text.length();
        StringBuilder sb = new StringBuilder();
        StringBuilder tmp = new StringBuilder();
        for(int i = 0; i < n; i++) {
            char c = text.charAt(i);
            if(c == '&') {
                // Test out by "&&gt;" need to skip all continuous '&'
                int k = 1;
                while(i + k < n && text.charAt(i + k) == '&') {
                    sb.append("&");
                    k++;
                }
                // Update i to the last '&' in continuous '&'
                i += k - 1;
                // Cut out potential entity section
                int j = 0;
                while(text.charAt(i + j) != ';') {
                    tmp.append(text.charAt(i + j));
                    j++;
                }
                String str = tmp.toString();
                if(map.containsKey(str)) {
                    sb.append(map.get(str));
                } else {
                    sb.append(str);
                    sb.append(';'); // don't miss current ';'
                }
                tmp.setLength(0);
                // Skip handled section
                i += j;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

