/**
Refer to
https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/
Given a parentheses string s containing only the characters '(' and ')'. A parentheses string is balanced if:

Any left parenthesis '(' must have a corresponding two consecutive right parenthesis '))'.
Left parenthesis '(' must go before the corresponding two consecutive right parenthesis '))'.
In other words, we treat '(' as openning parenthesis and '))' as closing parenthesis.

For example, "())", "())(())))" and "(())())))" are balanced, ")()", "()))" and "(()))" are not balanced.

You can insert the characters '(' and ')' at any position of the string to balance it if needed.

Return the minimum number of insertions needed to make s balanced.

Example 1:
Input: s = "(()))"
Output: 1
Explanation: The second '(' has two matching '))', but the first '(' has only ')' matching. We need to to add one more ')' 
at the end of the string to be "(())))" which is balanced.

Example 2:
Input: s = "())"
Output: 0
Explanation: The string is already balanced.

Example 3:
Input: s = "))())("
Output: 3
Explanation: Add '(' to match the first '))', Add '))' to match the last '('.

Example 4:
Input: s = "(((((("
Output: 12
Explanation: Add 12 ')' to balance the string.

Example 5:
Input: s = ")))))))"
Output: 5
Explanation: Add 4 '(' at the beginning of the string and one ')' at the end. The string becomes "(((())))))))".

Constraints:
1 <= s.length <= 10^5
s consists of '(' and ')' only.
*/

// Solution 1: Stack simulation
// Refer to
// https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/discuss/779928/Simple-O(n)-stack-solution-with-detailed-explanation
/**
Brief Idea: Maintain a stack. Every time we see a ( we need to have 2 ) to balance. So we would push 2 to the stack. Then when we see ) we 
would check the top of the stack. If the top is 2, that means this is the first ) that will match the previous (. So we would change the top to 1. 
In case the top of stack had 1 it means we are seeing the second ) for some ( and so we just pop this 1 from the stack.

Details of transition:

When we see (:
1. And the stack is empty or has a 2 at the top: we just push another 2.
2. And the stack had 1 at the top: This means we have just seen () and now we are seeing (. In this case, we need to first provide a ) to the previous 
   ( and then we can process the current (. So increment the answer, pop the 1 (which was for last () and push a 2 (for current ().

When we see ):
1. And the stack is empty: We have encountered a lone ). We would need to add ( to the sequence to balance, which would match one of its ) with the current ). 
   So we increment the answer. Moreover, it would also need another ) to match. So we push 1 to the top. This is like saying we have seen () (of which ( was inserted by us).
2. And the stack had 1 at the top: This the second ) for some (. We just pop from the stack.
3. And the stack had 2 at the top: This the first ) for some (. We just pop 2 from the stack and push 1.

At the end of going through the string we just add up all the numbers in stack, which represents the number of ) we need to balance.

class Solution {
public:
    int minInsertions(string s) {
        int ans = 0;
        stack<int> t;
        for (char c : s) {
            if (c == '(') {
                if (t.empty() || t.top() == 2) t.push(2);
                else {
                    t.pop();
                    t.push(2);
                    ans++;
                }
            }
            else {
                if (t.empty()) {
                    t.push(1); ans++;
                } else if (t.top() == 1) {
                    t.pop();
                } else if (t.top() == 2) {
                    t.pop(); t.push(1);
                }
            }
        }
        while (!t.empty()) {
            ans += t.top();
            t.pop();
        }
        return ans;
    }
};
*/
class Solution {
    public int minInsertions(String s) {
        int count = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(char c : s.toCharArray()) {
            if(c == '(') {
                if(stack.isEmpty() || stack.peek() == 2) {
                    // When we see '(' and stack is empty or need 2 ')'
                    // at peek, just push 2 means the new '(' need
                    // another 2 ')'
                    stack.push(2);
                } else if(stack.peek() == 1) {
                    // When we see '(' and need 1 ')' at peek, but we
                    // only have '(' in hand, not helpful on it since
                    // the 1 at peek means we encounter a "()", so we
                    // need to provide a ')' to resolve pending "()",
                    // it cause count increase 1, and stack.pop() will
                    // remove resovled 1 ')' from stack, then since the
                    // new '(' needs another 2 ')', so push 2 on stack
                    count++;
                    stack.pop();
                    stack.push(2);
                }                
            } else {
                // When we see ')' and stack is empty, we need 1 '(' and
                // another 1 ')' to resolve it, incease count for pending
                // 1 '(', push 1 on stack for another 1 ')'
                if(stack.isEmpty()) {
                    count++;
                    stack.push(1);
                } else if(stack.peek() == 1) {
                    // When we see ')' and stack peek is 1, means pending for
                    // 1 ')' to build a "())" and now we have 1 ')' in hand, 
                    // it means we can resolve a previous "())" build requirement 
                    // by using current ')', so pop out 1 sicne it resolved
                    stack.pop();
                } else {
                    // When we see ')' and stack peek is 2, means pending for
                    // 2 ')' to build a "())" and now we have 1 ')' in hand,
                    // decrease it from 2 to 1, so pop out 2 and push 1
                    stack.pop();
                    stack.push(1);
                }
            }
        }
        while(!stack.isEmpty()) {
            count += stack.pop();
        }
        return count;
    }
}


// Wrong Solution 1:
/**
Test out by: "(()))(()))()())))", expected 4, result 1
Wrong order
(()))(()))()())))
(
((
(()
(()) -> (
(   )
(   )(
(   )((
(   )(()
(   )(()) -> ()(
(   )(   )
(   )(   )(
(   )(   )()
(   )(   )()(
(   )(   )()()
(   )(   )()()) -> ()()(
------------------------------------------------------------------------------
(   )(   )(   )) => ()() we can remove since closing parenthesis "))" index are consecutive
(   )(   )      ) => () wrong remove since index not consecutive for "))"
(   ) wrong answer

Correct order
(()))(()))()())))
(
((
(()
(()) -> (
(   )
(   )(
(   )((
(   )(()
(   )(()) -> ()(
(   )(   )
(   )(   )(
(   )(   )()
(   )(   )()(
(   )(   )()()
(   )(   )()()) -> ()()(
-----------------------------------------------------------------------------
(   )(   )(   )) -> ()()
(   )(   )      ) correct answer => not able to remove "())" since "))" not consecutive
*/
// Issue 1: Not match defintion about consecutive "))", able to remove all potential "())" during process, but which is not correct
// Issue 2: while loop issue not able to correctly calculate the count since the whole process is only create a final string that remove all "())", 
//          but not calculate pending '(' or ')' we need to make it up
class Solution {
    public int minInsertions(String s) {
        int n = s.length();
        int count = 0;
        Stack<Character> stack = new Stack<Character>();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(c);
            } else {
                if(!stack.isEmpty() && stack.peek() == '(') {
                    if(i + 1 < n && s.charAt(i + 1) == ')') {
                        stack.pop(); // remove peek '('
                        i += 1; // skip second ')'
                        // Now we remove a "())" based on consecutive indexes
                    } else {
                        stack.push(c);
                        // Currnent char is ')', either next char available as
                        // '(' not able to remove current peek '(', and next 
                        // char will be pushed onto stack by if(c == '(') condition, 
                        // or no next char at all, but both condition will make 
                        // stack end with "()", just push current char ')' on stack
                    }
                } else if(!stack.isEmpty() && stack.peek() == ')') {
                    // 2nd peek ? | peek = ')' | current = ')' 
                    // let's check potential 2nd peek exist or not, and if exist, 
                    // is it '(' to build a "())" which able to remove as whole 
                    char tmp = stack.pop();
                    if(!stack.isEmpty()) {
                        if(stack.peek() == '(') {
                            stack.pop(); // remove 2nd peek '('
                            // No need to restore tmp or push current char ')'
                            // since we remove a "())" as a whole
                        } else {
                            stack.push(tmp); // restore original peek
                            stack.push(c); // insert current char ')'
                            // stack end with ")))"
                        }
                    } else {
                        // tmp as ')' is the only char on stack since after remove
                        // it stack becomes empty, need to restore
                        stack.push(tmp);
                        stack.push(c); // insert current char ')'
                        // stack now with '))'
                    }
                } else {
                    // stack is empty we can insert without condition
                    stack.push(c);
                }
            }
        }
        while(!stack.isEmpty()) {
            if(stack.peek() == '(') {
                count += 2;
                stack.pop();
            } else {
                count++;
                stack.pop();
                if(!stack.isEmpty()) {
                    stack.pop();
                } else {
                    // Already empty and last removed char is ')'
                    // need to make up one '(' and one ')'
                    // since count for ')' already added when remove
                    // the last ')', here is for additional '(' on count
                    count++;
                }
            }
        }
        return count;
    }
}

// Wrong Solution 2:
/**
Test out by: "((())))))", expected 0, result 6
*/
// Issue 1: Not match defintion about consecutive "))", refine based on Wrong Solution 1 but wrongly consider "())" 3 chars must consecutive, 
//          but which is not correct since only require closing brakets "))" need to consecutive.
// Issue 2: while loop issue not able to correctly calculate the count since the whole process is only create a final string that remove all "())", 
//          but not calculate pending '(' or ')' we need to make it up
class Solution {
    public int minInsertions(String s) {
        int n = s.length();
        int count = 0;
        Stack<Node> stack = new Stack<Node>();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(new Node(c, i));
            } else {
                if(!stack.isEmpty() && stack.peek().val == '(') {
                    if(i + 1 < n && s.charAt(i + 1) == ')') {
                        if(stack.peek().index + 1 == i) {
                            stack.pop(); // remove peek '('
                            i += 1; // skip second ')'
                            // Now we remove a "())" based on consecutive indexes
                        } else {
                            // Even peek '(', but not consecutive indexes between
                            // '(' and two ')' we have on hand now, cannot remove
                            // just push current ')' on stack
                            stack.push(new Node(c, i));
                        }
                    } else {
                        stack.push(new Node(c, i));
                        // Currnent char is ')', either next char available as
                        // '(' not able to remove current peek '(', and next 
                        // char will be pushed onto stack by if(c == '(') condition, 
                        // or no next char at all, but both condition will make 
                        // stack end with "()", just push current char ')' on stack
                    }
                } else if(!stack.isEmpty() && stack.peek().val == ')') {
                    // 2nd peek ? | peek = ')' | current = ')' 
                    // let's check potential 2nd peek exist or not, and if exist, 
                    // is it '(' to build a "())" which able to remove as whole 
                    Node tmp = stack.pop();
                    if(!stack.isEmpty()) {
                        if(stack.peek().val == '(') {
                            // Check 2nd peek as '(', peek as ')', current char ')'
                            // as consecutive three chars
                            if(stack.peek().index + 1 == tmp.index && tmp.index + 1 == i) {
                                stack.pop(); // remove 2nd peek '('
                                // No need to restore tmp or push current char ')'
                                // since we remove a "())" as a whole        
                            } else {
                                stack.push(tmp); // restore original peek
                                stack.push(new Node(c, i)); // insert current char ')'
                            }
                        } else {
                            stack.push(tmp); // restore original peek
                            stack.push(new Node(c, i)); // insert current char ')'
                            // stack end with ")))"
                        }
                    } else {
                        // tmp as ')' is the only char on stack since after remove
                        // it stack becomes empty, need to restore
                        stack.push(tmp);
                        stack.push(new Node(c, i)); // insert current char ')'
                        // stack now with '))'
                    }
                } else {
                    // stack is empty we can insert without condition
                    stack.push(new Node(c, i));
                }
            }
        }
        while(!stack.isEmpty()) {
            if(stack.peek().val == '(') {
                count += 2;
                stack.pop();
            } else {
                count++;
                stack.pop();
                if(!stack.isEmpty()) {
                    stack.pop();
                } else {
                    // Already empty and last removed char is ')'
                    // need to make up one '(' and one ')'
                    // since count for ')' already added when remove
                    // the last ')', here is for additional '(' on count
                    count++;
                }
            }
        }
        return count;
    }
}

class Node {
    char val;
    int index;
    public Node(char val, int index) {
        this.val = val;
        this.index = index;
    }
}

// Wrong Solution 3:
/**
Test out by: "(((()(()((())))(((()())))()())))(((()(()()((()()))", expected 31, result 40
             "(((()(()(      (((()   ))()   ))(((()(()()((()   )", generate stack by removing all "())" as good but not able to calcualte pending '(' or ')'
*/
// Issue 1: while loop issue not able to correctly calculate the count since the whole process is only create a final string that remove all "())", 
//          but not calculate pending '(' or ')' we need to make it up
class Solution {
    public int minInsertions(String s) {
        int n = s.length();
        int count = 0;
        Stack<Node> stack = new Stack<Node>();
        for(int i = 0; i < n; i++) {
            char c = s.charAt(i);
            if(c == '(') {
                stack.push(new Node(c, i));
            } else {
                if(!stack.isEmpty() && stack.peek().val == '(') {
                    if(i + 1 < n && s.charAt(i + 1) == ')') {
                        stack.pop(); // remove peek '('
                        i += 1; // skip second ')'
                        // Now we remove a "())" based on consecutive indexes 
                        // for closing parenthesis "))"
                    } else {
                        stack.push(new Node(c, i));
                        // Currnent char is ')', either next char available as
                        // '(' not able to remove current peek '(', and next 
                        // char will be pushed onto stack by if(c == '(') condition, 
                        // or no next char at all, but both condition will make 
                        // stack end with "()", just push current char ')' on stack
                    }
                } else if(!stack.isEmpty() && stack.peek().val == ')') {
                    // 2nd peek ? | peek = ')' | current = ')' 
                    // let's check potential 2nd peek exist or not, and if exist, 
                    // is it '(' to build a "())" which able to remove as whole
                    // when "))" are consecutive indexes
                    Node tmp = stack.pop();
                    if(!stack.isEmpty()) {
                        if(stack.peek().val == '(') {
                            // Check if peek ')' index and current ')' index are 
                            // consecutive or not, if consecutive then able to remove
                            if(tmp.index + 1 == i) {
                                stack.pop(); // remove 2nd peek '('
                                // No need to restore tmp or push current char ')'
                                // since we remove a "())" as a whole         
                            } else {
                                stack.push(tmp); // restore original peek
                                // insert current char ')' since not consecutive indexes
                                stack.push(new Node(c, i));
                            }
                        } else {
                            stack.push(tmp); // restore original peek
                            stack.push(new Node(c, i)); // insert current char ')'
                            // stack end with ")))"
                        }
                    } else {
                        // tmp as ')' is the only char on stack since after remove
                        // it stack becomes empty, need to restore
                        stack.push(tmp);
                        stack.push(new Node(c, i)); // insert current char ')'
                        // stack now with '))'
                    }
                } else {
                    // stack is empty we can insert without condition
                    stack.push(new Node(c, i));
                }
            }
        }
        while(!stack.isEmpty()) {
            if(stack.peek().val == '(') {
                count += 2;
                stack.pop();
            } else {
                count++;
                stack.pop();
                if(!stack.isEmpty()) {
                    stack.pop();
                } else {
                    // Already empty and last removed char is ')'
                    // need to make up one '(' and one ')'
                    // since count for ')' already added when remove
                    // the last ')', here is for additional '(' on count
                    count++;
                }
            }
        }
        return count;
    }
}

class Node {
    char val;
    int index;
    public Node(char val, int index) {
        this.val = val;
        this.index = index;
    }
}

// So change mind to follow the correct way from 
// https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/discuss/779928/Simple-O(n)-stack-solution-with-detailed-explanation
