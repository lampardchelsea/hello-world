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

// This problem is similar to 921. Minimum Add to Make Parentheses Valid
// Refer to
// https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
// The only difference is only need to build "()" not "())" with consecutive "))", the similar solution as only need integer stack is below:
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/MinimumAddToMakeParenthesesValid.java
class Solution {
    public int minAddToMakeValid(String S) {
        int count = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(char c: S.toCharArray()) {
            if(c == '(') {
                // A '(' in any condition: empty stack, peek as '(' or ')'
                // will require a ')' to close it, push 1 on stack
                stack.push(1);
            } else {
                if(stack.isEmpty() || stack.peek() == 0) {
                    // If stack is empty or peek as 0 and we have ')' on hand, 
                    // it means we need one '(' to close it, increase count 
                    // and push 0 on stack for identify ')' on stack peek
                    count++;
                    stack.push(0);
                } else if(stack.peek() == 1) {
                    // If stack peek is 1 means one previous '(' pending for
                    // a ')' to close and now we have one ')' in hand, then
                    // use it and pop out the 1
                    stack.pop();
                }
            }
        }
        while(!stack.isEmpty()) {
            count += stack.pop();
        }
        return count;
    }
}

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

// Solution 2:
// Refer to
// https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/discuss/780199/JavaC%2B%2BPython-Straight-Forward-One-Pass
/**
Intuition
Similar to 921. Minimum Add to Make Parentheses Valid.
Just need to pay attention:
left parenthesis '(' must have a corresponding two consecutive right parenthesis '))'.


Explanation
res represents the number of left/right parentheses already added.
right represents the number of right parentheses needed.

1) case )
If we meet a right parentheses , right--.
If right < 0, we need to add a left parentheses before it.
Then we update right += 2 and res++
This part is easy and normal.

2) case (
If we meet a left parentheses,
we check if we have odd number ')' before.
If we right, we have odd ')' before,
but we want right parentheses in paires.
So add one ')' here, then update right--; res++;.
Note that this part is not necessary if two consecutive right parenthesis not required.

Because we have ), we update right += 2.


Dry run
All by @himanshusingh11:

Example 1: Consider ((()(,n= 5 ,i=0,1...4
i=0, we have ( it means we need two right parenthesis (they are in pair) so.. right+=2 => res =0, right =2
i=1, again we have ( it means we need two right parenthesis (they are in pair) so.. right+=2 => res =0, right =4
i=2, again we have ( it means we need two right parenthesis (they are in pair) so.. right+=2 => res =0, right =6
i=3, we have ) we subtract one from right. so.. right-- => res =0, right =5
i=4, we have ( but here right is odd so we need to make it even with right-- and increment res++ => res =1, right =4. 
     Also, as we have got a left parenthesis then we need two right parenthesis (they are in pair) so.. right+=2 => res =1, right =6

finally ans is res + right => 1 +6 == 7

Example 2: ((()
Similarly, we can see when we have right<0 then we increment res by one & add 2 to right as they should be in pairs..

Complexity
Time O(N)
Space O(1)

Java:

    public int minInsertions(String s) {
        int res = 0, right = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                if (right % 2 > 0) {
                    right--;
                    res++;
                }
                right += 2;
            } else {
                right--;
                if (right < 0) {
                    right += 2;
                    res++;
                }
            }
        }
        return right + res;
    }
*/


// =======================================================================================================================================
// Wrong Solution 1:
/**
Test out by: "(()))(()))()())))", expected 4, result 1

Refer to
How output of "(()))(()))()())))" is 4 and not 1 ?
https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/discuss/780043/How-output-of-%22(()))(()))()())))%22-is-4-and-not-1
Q1: For test case "(()))(()))()())))" expected is 4
but consider steps
(()))(()))()()))) => Removing all ()) => ()()()))
()()())) => Removing ()) => ()())
()()) => Removing ()) => ()
() => insert ) => ()) => ""

A1: You need to have consecutive "))" to match parenthesis.

Q2: Please explain your statement using "( ( ( ) ) ) ) ) )"
its expected output is : 0
but according to your logic it is : 6

A2: Closing parentheses should be consecutive to each other, not to an opening parenthesis.
( ( ( ) ) ) ) ) ) -> ( ( ) ) ) ) -> ( ) ) - here we have 3 valid groups of ( ) ), in each group closing parentheses )) go together, one right after another. 
But an opening one ( can be at any position as long as it is before the closing ones. 

------------------------------------------------------------------------------
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
------------------------------------------------------------------------------
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
