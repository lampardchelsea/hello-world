/**
Refer to
https://leetcode.com/problems/goal-parser-interpretation/
You own a Goal Parser that can interpret a string command. The command consists of an alphabet of "G", "()" and/or "(al)" 
in some order. The Goal Parser will interpret "G" as the string "G", "()" as the string "o", and "(al)" as the string "al". 
The interpreted strings are then concatenated in the original order.

Given the string command, return the Goal Parser's interpretation of command.

Example 1:
Input: command = "G()(al)"
Output: "Goal"
Explanation: The Goal Parser interprets the command as follows:
G -> G
() -> o
(al) -> al
The final concatenated result is "Goal".

Example 2:
Input: command = "G()()()()(al)"
Output: "Gooooal"

Example 3:
Input: command = "(al)G(al)()()G"
Output: "alGalooG"

Constraints:
1 <= command.length <= 100
command consists of "G", "()", and/or "(al)" in some order.
*/

// Solution 1:
class Solution {
    public String interpret(String command) {
        int n = command.length();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while(i < n) {
            if(command.charAt(i) == 'G') {
                sb.append('G');
                i++;
            } else if(command.charAt(i) == '(') {
                if(i + 1 < n) {
                    if(command.charAt(i + 1) == ')') {
                        sb.append('o');
                        i += 2;
                    } else if(command.charAt(i + 1) == 'a') {
                        sb.append('a');
                        sb.append('l');
                        i += 4;
                    }
                }
            }
        }
        return sb.toString();
    }
}

// Solutoin 2:
// Refer to
// https://leetcode.com/problems/goal-parser-interpretation/discuss/961358/JavaPython-3-StringBuilderlist-join
class Solution {
    public String interpret(String command) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < command.length(); ++i) {
            char c = command.charAt(i);
            if (c == 'G') {
                sb.append(c);
            }else if (c == '(' && command.charAt(i + 1) == ')') {
                sb.append("o");
                ++i;
            }else {
                sb.append("al");
                i += 3;
            }
        }
        return sb.toString();
    }
}
