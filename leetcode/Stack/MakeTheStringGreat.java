/**
Refer to
https://leetcode.com/problems/make-the-string-great/
Given a string s of lower and upper case English letters.

A good string is a string which doesn't have two adjacent characters s[i] and s[i + 1] where:

0 <= i <= s.length - 2
s[i] is a lower-case letter and s[i + 1] is the same letter but in upper-case or vice-versa.
To make the string good, you can choose two adjacent characters that make the string bad and remove them. 
You can keep doing this until the string becomes good.

Return the string after making it good. The answer is guaranteed to be unique under the given constraints.

Notice that an empty string is also good.

Example 1:
Input: s = "leEeetcode"
Output: "leetcode"
Explanation: In the first step, either you choose i = 1 or i = 2, both will result "leEeetcode" to be reduced to "leetcode".

Example 2:
Input: s = "abBAcC"
Output: ""
Explanation: We have many possible scenarios, and all lead to the same answer. For example:
"abBAcC" --> "aAcC" --> "cC" --> ""
"abBAcC" --> "abBA" --> "aA" --> ""

Example 3:
Input: s = "s"
Output: "s"

Constraints:
1 <= s.length <= 100
s contains only lower and upper case English letters.
*/

// Solution 1: Stack
class Solution {
    public String makeGood(String s) {
        Stack<Character> stack = new Stack<Character>();
        for(char c : s.toCharArray()) {
            if(!stack.isEmpty() && Math.abs(stack.peek() - c) == 32) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }
}

// Recursive
// Refer to
// https://leetcode.com/problems/make-the-string-great/discuss/781446/very-easy-java-recursive-solution
class Solution {
    public String makeGood(String s) {
        int n = s.length();
        for(int i = 0; i < n - 1; i++) {
            if(Math.abs(s.charAt(i) - s.charAt(i + 1)) == 32) {
                return makeGood(s.substring(0, i) + s.substring(i + 2));
            }
        }
        return s;
    }
}
