/**
 Refer to
 https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
 Given a string s that consists of lower case English letters and brackets. 
Reverse the strings in each pair of matching parentheses, starting from the innermost one.

Your result should not contain any bracket.
Example 1:
Input: s = "(abcd)"
Output: "dcba"

Example 2:
Input: s = "(u(love)i)"
Output: "iloveu"

Example 3:
Input: s = "(ed(et(oc))el)"
Output: "leetcode"

Example 4:
Input: s = "a(bcdefghijkl(mno)p)q"
Output: "apmnolkjihgfedcbq"

Constraints:
0 <= s.length <= 2000
s only contains lower case English characters and parentheses.
It's guaranteed that all parentheses are balanced.
*/

// Solution 1: DFS
// Refer to
// https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/discuss/382358/Simple-Java-Sol(Recursion)
// https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/discuss/382372/Bad-test-cases-again!/343582
/**
 Same here, bro, i start with two pointers moving opposite, pointer start moving forward, and pointer 
 end moving backward, and i write below code which handle given 3 test cases well, but failed because 
 of similar missing test case as "sxmdll(q)eki(x)", which expected result as "sxmdllqekix", below code 
 failed to handle since it will chop "(q)eki(x)" instead of first "(q)" then "(x)", if leetcode adding 
 "sxmdll(q)eki(x)" in the description, it will avoid misleading to scan as sandwich. 
*/
class Solution {
    public String reverseParentheses(String s) {
        return helper(s, 0, s.length() - 1, 0);
    }
    
    private String helper(String s, int start, int end, int count) {
    	if(s.length() == 0 || start > end) {
    		return "";
    	}
        int initStart = start;
        if(count % 2 == 0) {
            while(start < s.length() && s.charAt(start) != '(') {
                start++;
            }
            while(end >= 0 && s.charAt(end) != ')') {
                end--;
            }        	
        } else if(count % 2 == 1) {
            while(start < s.length() && s.charAt(start) != ')') {
                start++;
            }
            while(end >= 0 && s.charAt(end) != '(') {
                end--;
            }      	
        }
        // We found a pair
        if(start < s.length() && end > -1) {
        	String prefix = s.substring(initStart, start);
            String suffix = s.substring(end + 1);
            String temp = s.substring(start + 1, end);
            StringBuilder sb = new StringBuilder();
            String reversed = sb.append(temp).reverse().toString();
            String mid = helper(reversed, 0, reversed.length() - 1, count + 1);
            return prefix + mid + suffix;
        } else {
        	return s;
        }
    }
}
// Compare to below correct solution, the simple change is two pointers start and end both initialize as 0 and moving forward
class Solution {
    public String reverseParentheses(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        return helper(s);
    }
    
    private String helper(String s) {
        int start = 0;
        int end = 0;
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(') {
                start = i;
            }
            if(s.charAt(i) == ')') {
                end = i;
                String prefix = s.substring(0, start);
                String suffix = s.substring(end + 1);
                String temp = s.substring(start + 1, end);
                String reversed = new StringBuilder().append(temp).reverse().toString();
                return helper(prefix + reversed + suffix);
            }
        }
        // If no brackets pair anymore directly return current string section
        return s;
    }
}

// Solution 2: Stack + Queue
