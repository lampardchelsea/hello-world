/**
Refer to
https://leetcode.com/problems/backspace-string-compare/
Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.

Note that after backspacing an empty text, the text will continue empty.

Example 1:
Input: S = "ab#c", T = "ad#c"
Output: true
Explanation: Both S and T become "ac".

Example 2:
Input: S = "ab##", T = "c#d#"
Output: true
Explanation: Both S and T become "".

Example 3:
Input: S = "a##c", T = "#a#c"
Output: true
Explanation: Both S and T become "c".

Example 4:
Input: S = "a#c", T = "b"
Output: false
Explanation: S becomes "c" while T becomes "b".

Note:
1 <= S.length <= 200
1 <= T.length <= 200
S and T only contain lowercase letters and '#' characters.

Follow up:
Can you solve it in O(N) time and O(1) space?
*/

// Solution 1: Stack
class Solution {
    public boolean backspaceCompare(String S, String T) {
        Stack<Character> s1 = new Stack<Character>();
        Stack<Character> s2 = new Stack<Character>();
        char[] s_chars = S.toCharArray();
        char[] t_chars = T.toCharArray();
        for(char c : s_chars) {
            if(c == '#' && !s1.isEmpty()) {
                s1.pop();
            } else if(c != '#') {
                s1.push(c);
            }
        }
        for(char c : t_chars) {
            if(c == '#' && !s2.isEmpty()) {
                s2.pop();
            } else if(c != '#') {
                s2.push(c);
            }       
        }
        while(!s1.isEmpty() && !s2.isEmpty()) {
            char c1 = s1.pop();
            char c2 = s2.pop();
            if(c1 != c2) {
                return false;
            }
        }
        // Test out by:
        // Input S = "bxj##tw", T = "bxj###tw"
        // Output true 
        // Expected false
        if(!s1.isEmpty() || !s2.isEmpty()) {
            return false;
        }
        return true;
    }
}

// Solution 2: Two Pointers
