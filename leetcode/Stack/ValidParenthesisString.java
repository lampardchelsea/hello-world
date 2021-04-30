/**
Refer to
https://leetcode.com/problems/valid-parenthesis-string/
Given a string s containing only three types of characters: '(', ')' and '*', return true if s is valid.

The following rules define a valid string:

Any left parenthesis '(' must have a corresponding right parenthesis ')'.
Any right parenthesis ')' must have a corresponding left parenthesis '('.
Left parenthesis '(' must go before the corresponding right parenthesis ')'.
'*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string "".

Example 1:
Input: s = "()"
Output: true

Example 2:
Input: s = "(*)"
Output: true

Example 3:
Input: s = "(*))"
Output: true

Constraints:
1 <= s.length <= 100
s[i] is '(', ')' or '*'.
*/

// Solution 1: Two stack and use specific star stack to handle '*'
// Refer to
// https://leetcode.com/problems/valid-parenthesis-string/discuss/107572/Java-using-2-stacks.-O(n)-space-and-time-complexity.
/**
The basic idea is to track the index of the left bracket and star position.
Push all the indices of the star and left bracket to their stack respectively.
STEP 1
Once a right bracket comes, pop left bracket stack first if it is not empty. If the left bracket stack is empty, pop the star stack if it is not empty. A false return can be made provided that both stacks are empty.

STEP 2
Now attention is paid to the remaining stuff in these two stacks. Note that the left bracket CANNOT appear after the star as there is NO way to balance the bracket. In other words, whenever there is a left bracket index appears after the Last star, a false statement can be made. Otherwise, pop out each from the left bracket and star stack.

STEP 3
A correct sequence should have an empty left bracket stack. You don't need to take care of the star stack.

Final Remarks:
Greedy algorithm is used here. We always want to use left brackets to balance the right one first as the * symbol is a wild card. There is probably an O(1) space complexity but I think this is worth mentioning.

    public boolean checkValidString(String s) {
        Stack<Integer> leftID = new Stack<>();
        Stack<Integer> starID = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(')
                leftID.push(i);
            else if (ch == '*')
                starID.push(i);
            else {
                if (leftID.isEmpty() && starID.isEmpty())   return false;
                if (!leftID.isEmpty())
                    leftID.pop();
                else 
                    starID.pop();
            }
        }
        while (!leftID.isEmpty() && !starID.isEmpty()) {
            if (leftID.pop() > starID.pop()) 
                return false;
        }
        return leftID.isEmpty();
    }
*/
class Solution {
    public boolean checkValidString(String s) {
        int n = s.length();
        Stack<Integer> stack = new Stack<Integer>();
        Stack<Integer> star_stack = new Stack<Integer>();
        // Greedy algorithm is used here. We always want to use left brackets 
        // to balance the right one first as the * symbol is a wild card
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(i);
            } else if(c == '*') {
                star_stack.push(i);
            } else {
                // Once a right bracket comes, pop left bracket stack first if 
                // it is not empty. If the left bracket stack is empty, pop the 
                // star stack if it is not empty. A false return can be made 
                // provided that both stacks are empty since when no '(' and no
                // '*' but seen a ')' it will have no way to remove anymore
                if(stack.isEmpty() && star_stack.isEmpty()) {
                    return false;
                }
                // Stack has higher priority to pop out than star stack, because
                // we always try to remove '(' first when seen a ')', the left
                // '*' can recognize as empty and able to ignore
                if(!stack.isEmpty()) {
                    stack.pop();
                } else if(!star_stack.isEmpty()) {
                    star_stack.pop();
                }
            }
        }
        // Now attention is paid to the remaining stuff in these two stacks. 
        // Note that the left bracket CANNOT appear after the star as there 
        // is NO way to balance the bracket. In other words, whenever there 
        // is a left bracket index appears after the Last star, a false 
        // statement can be made. Otherwise, pop out each from the left 
        // bracket and star stack.
        while(!stack.isEmpty() && !star_stack.isEmpty()) {
            if(stack.pop() > star_stack.pop()) {
                return false;
            }
        }
        // A correct sequence should have an empty left bracket stack. 
        // You don't need to take care of the star stack.
        return stack.isEmpty();
    }
}
