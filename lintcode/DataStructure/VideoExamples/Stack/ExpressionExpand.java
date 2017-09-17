/**
 * Refer to
 * http://www.lintcode.com/en/problem/expression-expand/
 * https://leetcode.com/problems/decode-string/description/
 * Given an encoded string, return it's decoded string.
   The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets 
   is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
   You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
   Furthermore, you may assume that the original data does not contain any digits and that digits 
   are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
   
    Examples:
    s = "3[a]2[bc]", return "aaabcbc".
    s = "3[a2[c]]", return "accaccacc".
    s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
    
 * Solution
 * Style 1
 * http://www.cnblogs.com/Dylan-Java-NYC/p/6751689.html
 * Style 2
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/DecodeString.java
 * Style 3
 * 
*/
// Style 1



// Style 2
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/DecodeString.java
public class Solution {
    /*
     * @param s: an expression includes numbers, letters and brackets
     * @return: a string
     */
    public String expressionExpand(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        Stack<Integer> countStack = new Stack<Integer>();
        Stack<String> result = new Stack<String>();
        result.push("");
        int i = 0;
        while(i < s.length()) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                int val = 0;
                int start = i;
                while(s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    i++;
                }
                int currCount = Integer.parseInt(s.substring(start, i + 1));
                countStack.push(currCount);
            } else if(c == '[') {
                result.push("");
            } else if(Character.isLetter(c)) {
                result.push(result.pop() + c);
            } else if(c == ']') {
                StringBuilder sb = new StringBuilder();
                int times = countStack.pop();
                String str = result.pop();
                for(int j = 0; j < times; j++) {
                    sb.append(str);
                }
                result.push(result.pop() + sb.toString());
            }
            i++;
        }
        return result.pop();
    }
}
