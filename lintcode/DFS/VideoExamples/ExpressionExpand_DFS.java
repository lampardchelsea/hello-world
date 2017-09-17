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
 * 
 * Solution
 * https://github.com/lampardchelsea/hello-world/tree/master/lintcode/BinaryTree__DivideAndConquer/VideoExamples
 * Style 1
 * http://www.cnblogs.com/Dylan-Java-NYC/p/6751689.html
 * Style 2
 * http://www.cnblogs.com/lishiblog/p/5874147.html
*/
// Style 1:
// Refer to
// http://www.cnblogs.com/Dylan-Java-NYC/p/6751689.html
// DFS recursion 方法, 终止条件有两个，遇到了']', 或者到了s的末位.
// Time Complexity: O(s.length()). Space: O(s.length()).
// Base on definition here we implement Traversal + Divide and Conquer way together on this problem as
// we use native method (Divide and Conquer) and return void (Traversal), also set i as global variable
// to control the loop condition (Traversal), they are both DFS recursive way
public class Solution {
    /*
     * @param s: an expression includes numbers, letters and brackets
     * @return: a string
     */
    int i = 0;
    public String expressionExpand(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        // Initial global StringBuilder to record each recursion temparay
        // result as DFS Traversal way
        StringBuilder sb = new StringBuilder();
        while(i < s.length()) {
            if(Character.isDigit(s.charAt(i))) {
                int start = i;
                // Loop until character not numeric number, based
                // on problem description this character is '[',
                // need to skip
                while(Character.isDigit(s.charAt(i))) {
                    i++;
                }
                int val = Integer.parseInt(s.substring(start, i));
                // Skip '[' just after numeric value
                i++;
                // DFS to get nested substring in current '[]'
                String nested = expressionExpand(s);
                while(val-- > 0) {
                    sb.append(nested);
                } 
            } else if(Character.isLetter(s.charAt(i))) {
                sb.append(s.charAt(i));
                i++;
            } else if(s.charAt(i) == ']') {
                // Skip ']'
                i++;
                return sb.toString();
            }
        }
        return sb.toString();
    }
}


// Style 2: 
// Refer to
// http://www.cnblogs.com/lishiblog/p/5874147.html
// Base on definition here we implement Traversal + Divide and Conquer way together on this problem as
// we create new helper method (Traversal) and return value not void (Divide and Conquer), also set
// StringBuilder builder as global variable to store each recursion temporary result (Traversal), 
// they are both DFS recursive way
public class Solution {
    /*
     * @param s: an expression includes numbers, letters and brackets
     * @return: a string
     */
    public String expressionExpand(String s) {
       if(s == null || s.length() == 0) {
           return "";
       } 
       StringBuilder sb = new StringBuilder();
       helper(s.toCharArray(), sb, 0);
       return sb.toString();
    }
    
    // Must return 'index', otherwise 'index' will not update to new value
    // after recursive calling (e.g drop back to initial value = 0 when
    // return to previous recursion level), because the 'index' used in
    // control while loop break out condition
    private int helper(char[] chars, StringBuilder sb, int start) {
        if(start >= chars.length) {
            return start;
        }
        int index = start;
        while(index < chars.length && chars[index] != ']') {
            if(Character.isLetter(chars[index])) {
                sb.append(chars[index++]);
            } else {
                // get the following encoded string
                // get the number first
                int val = 0;
                while(Character.isDigit(chars[index])) {
                    val = val * 10 + (chars[index++] - '0');
                }
                // get the string
                StringBuilder tempSb = new StringBuilder();
                index = helper(chars, tempSb, index + 1);
                for(int i = 0; i < val; i++) {
                    sb.append(tempSb);
                }
            }
        }
        return index + 1;
    }
}
