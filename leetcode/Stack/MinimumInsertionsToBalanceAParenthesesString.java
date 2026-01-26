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





















































































https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/description/
Given a parentheses string s containing only the characters '(' and ')'. A parentheses string is balanced if:
- Any left parenthesis '(' must have a corresponding two consecutive right parenthesis '))'.
- Left parenthesis '(' must go before the corresponding two consecutive right parenthesis '))'.
In other words, we treat '(' as an opening parenthesis and '))' as a closing parenthesis.
- For example, "())", "())(())))" and "(())())))" are balanced, ")()", "()))" and "(()))" are not balanced.
You can insert the characters '(' and ')' at any position of the string to balance it if needed.
Return the minimum number of insertions needed to make s balanced.
 
Example 1:
Input: s = "(()))"
Output: 1
Explanation: The second '(' has two matching '))', but the first '(' has only ')' matching. We need to add one more ')' at the end of the string to be "(())))" which is balanced.

Example 2:
Input: s = "())"
Output: 0
Explanation: The string is already balanced.

Example 3:
Input: s = "))())("
Output: 3
Explanation: Add '(' to match the first '))', Add '))' to match the last '('.
 
Constraints:
- 1 <= s.length <= 105
- s consists of '(' and ')' only.
--------------------------------------------------------------------------------
Attempt 1: 2026-01-24
Solution 1: Math (120 min)
Style 1: Balance Counter
class Solution {
    public int minInsertions(String s) {
        int insert = 0;
        int closeNeeded = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                // Each '(' needs two ')'
                closeNeeded += 2;
                // If needRight is odd, we need to insert one ')' to make it even
                if(closeNeeded % 2 == 1) {
                    // Insert one ')' to fix the odd count
                    insert++;
                    // Reduce need by 1 to make it even
                    closeNeeded--;
                }
            } else {
                // Offset one closeNeeded with current ')'
                closeNeeded--;
                // If we have more ')' than needed, we need to insert '('
                if(closeNeeded < 0) {
                    // Insert one '('
                    insert++;
                    // Now we need two more ')' since insert a new '('
                    closeNeeded += 2;
                }
            }
        }
        return insert + closeNeeded;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Style 2: Two-character Lookahead
class Solution {
    public int minInsertions(String s) {
        int insert = 0;
        int openCount = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(') {
                openCount++;
            } else {
                // Check if we have two consecutive closing parentheses
                // We have two consecutive ')'
                if(i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    // We have two consecutive ')' hence match a ')'
                    if(openCount > 0) {
                        openCount--;
                    // Too many ')' and need insert a '(' for this pair
                    } else {
                        insert++;
                    }
                    // Skip next ')'
                    i++;
                // We don't have two consecutive ')'
                } else {
                    // Single ')', need to insert one more ')'
                    // Already have a '('
                    if(openCount > 0) {
                        // Need to insert one more ')'
                        insert++;
                        // Match the already having '(' and consume it
                        openCount--;
                    // Not even have a '('
                    } else {
                        // Need to insert one more '(' and one more ')'
                        insert += 2;
                    }
                }
            }
        }
        // Each remaining '(' needs two ')'
        return insert + openCount * 2;
    }
}

Time Complexity: O(n)
Space Complexity: O(1)

Refer to QWEN
Here's the optimal Java solution for LeetCode 1541. Minimum Insertions to Balance a Parentheses String:
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;  // Count of insertions needed
        int openCount = 0;   // Count of unmatched opening parentheses
        
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            
            if (c == '(') {
                openCount++;
                i++;
            } else { // c == ')'
                // Check if we have two consecutive closing parentheses
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    // We have "))" - good, we can use both
                    i += 2; // Skip both closing parentheses
                } else {
                    // We have only one ")" - need to insert another ")"
                    insertions++;
                    i++; // Skip the single closing parenthesis
                }
                
                // Now handle the closing pair (either existing or created by insertion)
                if (openCount > 0) {
                    // Match with an existing opening parenthesis
                    openCount--;
                } else {
                    // No opening parenthesis to match - need to insert "("
                    insertions++;
                }
            }
        }
        
        // Handle remaining unmatched opening parentheses
        // Each unmatched "(" needs two ")" to be balanced
        insertions += openCount * 2;
        
        return insertions;
    }
}
Alternative: Simpler Counter Approach
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int needRight = 0; // Number of right parentheses needed
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                needRight += 2; // Each '(' needs two ')'
                // If needRight is odd, we need to insert one ')' to make it even
                if (needRight % 2 == 1) {
                    insertions++;
                    needRight--; // Reduce need by 1 to make it even
                }
            } else { // c == ')'
                needRight--;
                // If we have more ')' than needed, we need to insert '('
                if (needRight < 0) {
                    insertions++; // Insert '('
                    needRight += 2; // Now we need two more ')'
                }
            }
        }
        
        return insertions + needRight;
    }
}
Key Concepts Explained:
Problem Understanding:
- Input contains only '(' and ')'
- A balanced string requires two ) for each (
- We can insert '(' or ')' anywhere to balance the string
- Goal: find minimum insertions needed
Algorithm Logic (First Approach):
1.Process characters left to right
2.For '(': increment openCount
3.For ')':
- Check if next character is also ')'
- If yes: use both as a complete pair
- If no: insert one ')' to complete the pair
4.After processing a pair of ')':
- If we have unmatched '(', match them
- If no unmatched '(', insert a '('
5.At the end: each remaining unmatched '(' needs two ')'
Example Walkthrough:
Input: "(()))"
- '(' → openCount = 1
- '(' → openCount = 2
- ')' → check next is ')', so use both "))"
- openCount = 2 - 1 = 1 (matched one pair)
- Remaining openCount = 1 → needs 1 × 2 = 2 insertions
- Total: 2
Wait, let me recalculate this example properly:
Input: "(()))"
- i=0: '(' → openCount = 1
- i=1: '(' → openCount = 2
- i=2: ')' → next char i=3 is ')', so use both
- openCount = 2 - 1 = 1 (one opening matched)
- i becomes 4
- i=4: ')' → next doesn't exist, so insert one ')' (insertions = 1)
- Now handle this pair: openCount = 1 > 0, so openCount = 0
- End: openCount = 0
- Total: 1
Actually, "(()))" is already almost balanced. Let me use a better example:
Input: "())"
- i=0: '(' → openCount = 1
- i=1: ')' → next char i=2 is ')', use both
- openCount = 1 - 1 = 0
- Total: 0 ✓
Input: "((("
- Three '(' → openCount = 3
- End: insertions += 3 × 2 = 6
- Total: 6 ✓
Second Approach Logic:
- Track needRight: how many ')' we need
- For each '(': add 2 to needRight
- For each ')': subtract 1 from needRight
- Handle edge cases when needRight becomes odd or negative
Time & Space Complexity:
- Time: O(n) - single pass through the string
- Space: O(1) - only using integer variables
This solution efficiently handles all cases including single closing parentheses, unmatched opening parentheses, and mixed scenarios!

Q1: In your Alternative: Simpler Counter Approach, you have slightly explained why we use 'insertions' separately against 'needRight' and also show on they work together with example, but for me its not that intuitive, can you explain more how we introduce 'insertions' and how 'needRight' not enough to cover the logic OR if no need 'insertions' and 'needRight' is good enough to cover the logic ?
Great question! Let me clarify why we need both insertions and needRight, and whether needRight alone could handle everything.
The Core Issue: Two Different Types of Problems
The key insight is that there are two distinct scenarios that require insertions:
Scenario 1: Too Many Closing Parentheses
- We have more ) than we can match with existing (
- Solution: Insert a ( to balance
- This affects needRight immediately
Scenario 2: Odd Number of Closing Parentheses Needed
- Each ( requires exactly 2 )
- If needRight becomes odd, we have a problem!
- Solution: Insert one ) to make it even, then adjust
Let me walk through the second approach step by step:
public int minInsertions(String s) {
    int insertions = 0;
    int needRight = 0; // How many ')' we need in total
    
    for (char c : s.toCharArray()) {
        if (c == '(') {
            needRight += 2; // Each '(' needs 2 ')'
            
            // CRITICAL: What if needRight becomes odd?
            if (needRight % 2 == 1) {
                insertions++;   // Insert one ')' to fix the odd count
                needRight--;    // Now needRight is even again
            }
        } else { // c == ')'
            needRight--;
            
            // CRITICAL: What if we have too many ')'?
            if (needRight < 0) {
                insertions++;   // Insert one '(' 
                needRight += 2; // Now we need 2 more ')' for this new '('
            }
        }
    }
    
    return insertions + needRight;
}

Why needRight Alone Isn't Enough
Problem 1: Negative needRight
If we only tracked needRight, when it goes negative, we wouldn't know how many insertions we made to fix it.
Example: ")"
- Start: needRight = 0
- Process ')': needRight = -1
- We need to insert '(', but needRight = -1 doesn't tell us we made an insertion
- After fixing: we should have needRight = 1 (since new '(' needs 2 ')', we already have 1 ')')
- But if we just set needRight = 1, we lost track that we made 1 insertion
Problem 2: Odd needRight
When we see '(', we add 2 to needRight. But what if we previously had an odd needRight?
Example: "())("
Let's trace without insertions counter:
- '(': needRight = 2
- ')': needRight = 1
- ')': needRight = 0
- '(': needRight = 2 ← Wait, this seems fine!
But consider: `"()("``
- '(': needRight = 2
- ')': needRight = 1 ← ODD!
- '(': needRight = 3 ← Still odd!
The issue is: we can never have an odd number of needed closing parentheses because each opening parenthesis requires exactly 2 closing ones.
So when needRight becomes odd, we must immediately insert a closing parenthesis to make it even.
Could We Use Only needRight?
Technically yes, but it would be confusing and error-prone. Here's how it might look:
// DON'T do this - hard to understand!
public int minInsertions(String s) {
    int needRight = 0;
    int totalInsertions = 0;
    
    for (char c : s.toCharArray()) {
        if (c == '(') {
            if (needRight % 2 == 1) {
                // Fix odd count by effectively inserting a ')'
                needRight--; // This represents the insertion
                totalInsertions++;
            }
            needRight += 2;
        } else {
            needRight--;
            if (needRight < 0) {
                // Fix by inserting '(' which needs 2 ')'
                needRight += 2;
                totalInsertions++;
            }
        }
    }
    
    return totalInsertions + needRight;
}
But notice: we still need totalInsertions! We can't avoid counting insertions separately because:
1.needRight represents future needs, not past actions
2.Insertions are immediate actions that affect both current state and future needs
3.Final answer = insertions made during processing + remaining needs
The Clean Separation
- needRight: "How many ) do I still need going forward?"
- insertions: "How many characters have I already inserted?"
This separation makes the logic clear and maintainable.

Refer to Deepseek
Problem Understanding
Given a string containing only '(' and ')', return the minimum number of insertions needed to make it balanced. A balanced string follows:
- '(' must be followed by two consecutive ')' (not one)
- '(' and ')' must be in the correct order
Solution 1: Balance Counter (Most Efficient)
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;  // Total insertions needed
        int openNeeded = 0;  // Count of '(' needed to balance
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                // Each '(' needs two ')'
                openNeeded += 2;
                
                // If openNeeded is odd, we need to insert one ')'
                // to make the previous '(' have two ')'
                if (openNeeded % 2 == 1) {
                    insertions++;    // Add a ')'
                    openNeeded--;    // Now we need one less ')'
                }
            } else { // c == ')'
                // Decrease the needed ')'
                openNeeded--;
                
                // If openNeeded becomes -1, we need to insert '('
                if (openNeeded == -1) {
                    insertions++;    // Add a '('
                    openNeeded = 1;  // Now we need one ')' (two total, one from this ')')
                }
            }
        }
        
        // At the end, add any needed ')' for unmatched '('
        return insertions + openNeeded;
    }
}
Solution 2: Simplified Balance Counter
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int balance = 0;  // Positive = need ')', Negative = need '('
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                balance += 2;
                
                // If balance is odd, it means we have an incomplete pair
                // Need to insert one ')' to complete previous pair
                if (balance % 2 == 1) {
                    insertions++;  // Insert ')'
                    balance--;     // One ')' satisfied
                }
            } else { // c == ')'
                balance--;
                
                // Too many ')', need to insert '('
                if (balance == -1) {
                    insertions++;  // Insert '('
                    balance = 1;   // Now we need one ')' (for the inserted '(')
                }
            }
        }
        
        return insertions + balance;
    }
}
Solution 3: Two-character Lookahead
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int open = 0;  // Count of unmatched '('
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                open++;
            } else { // c == ')'
                // Check if next char is also ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    // We have two consecutive ')'
                    if (open > 0) {
                        open--;  // Match with existing '('
                    } else {
                        insertions++;  // Need '(' for this pair
                    }
                    i++;  // Skip next ')'
                } else {
                    // Single ')', need to insert one more ')'
                    if (open > 0) {
                        open--;        // Use one '('
                        insertions++;  // Need one more ')'
                    } else {
                        insertions += 2;  // Need '(' and one more ')'
                    }
                }
            }
        }
        
        // Each remaining '(' needs two ')'
        return insertions + (open * 2);
    }
}
Solution 4: Stack-based (Conceptual)
class Solution {
    public int minInsertions(String s) {
        Stack<Character> stack = new Stack<>();
        int insertions = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                stack.push(c);
            } else { // c == ')'
                // Check if next is also ')'
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    // We have "))"
                    if (!stack.isEmpty()) {
                        stack.pop();  // Match with '('
                    } else {
                        insertions++;  // Need '(' for this pair
                    }
                    i++;  // Skip next ')'
                } else {
                    // Single ')', need to insert one more ')'
                    if (!stack.isEmpty()) {
                        stack.pop();    // Use one '('
                        insertions++;   // Need one more ')'
                    } else {
                        insertions += 2; // Need '(' and ')'
                    }
                }
            }
        }
        
        // Each remaining '(' needs two ')'
        return insertions + (stack.size() * 2);
    }
}
Detailed Explanation
Key Insight:
Unlike regular parentheses (where '(' needs one ')'), here each '(' needs TWO ')' (consecutive).
- Think of '))' as a single "closing unit"
- Each '(' needs one '))' unit
- Track needed closing units with a counter
Example Walkthrough:
Example 1: "(()))"
Solution 1 trace:
i=0: '(' → openNeeded=2, even ✓
i=1: '(' → openNeeded=4, even ✓  
i=2: ')' → openNeeded=3
i=3: ')' → openNeeded=2
i=4: ')' → openNeeded=1
End: insertions=0 + openNeeded=1 = 1

Explanation: Need one more ')' at end: "(())))"
Example 2: "())"
i=0: '(' → openNeeded=2, even ✓
i=1: ')' → openNeeded=1
i=2: ')' → openNeeded=0
End: insertions=0 + openNeeded=0 = 0 ✓

String: "())" is balanced (one '(' with two ')')
Example 3: "))())("
i=0: ')' → openNeeded=-1 → insertions=1, openNeeded=1
i=1: ')' → openNeeded=0
i=2: '(' → openNeeded=2, even ✓
i=3: ')' → openNeeded=1
i=4: ')' → openNeeded=0
i=5: '(' → openNeeded=2, even ✓
End: insertions=1 + openNeeded=2 = 3

Explanation: Insert '(' at beginning, add "))" at end
Result: "(" + "))())(" + "))" = "()))())()))"
Complexity Analysis
Solution 1 (Balance Counter):
- Time: O(n) - single pass through string
- Space: O(1) - only variables
Solution 3 (Two-character lookahead):
- Time: O(n) - single pass with lookahead
- Space: O(1) - only variables
Recommended Solutions
Best for Interviews: Solution 1
class Solution {
    public int minInsertions(String s) {
        int insertions = 0;
        int needed = 0; // Number of ')' needed
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                // Each '(' needs two ')'
                needed += 2;
                
                // If needed is odd, insert one ')'
                if (needed % 2 == 1) {
                    insertions++;
                    needed--;
                }
            } else { // c == ')'
                needed--;
                
                // Too many ')', need '('
                if (needed == -1) {
                    insertions++;  // Insert '('
                    needed = 1;    // Now need one ')'
                }
            }
        }
        
        return insertions + needed;
    }
}
Alternative: Solution 3 (More Intuitive)
class Solution {
    public int minInsertions(String s) {
        int res = 0, left = 0, n = s.length();
        
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                left++;
            } else {
                // Check if we have two consecutive ')'
                if (i < n - 1 && s.charAt(i + 1) == ')') {
                    i++; // Skip next ')'
                    if (left > 0) left--; // Match with '('
                    else res++; // Need '('
                } else {
                    // Single ')'
                    if (left > 0) {
                        left--;
                        res++; // Need one more ')'
                    } else {
                        res += 2; // Need '(' and ')'
                    }
                }
            }
        }
        
        return res + left * 2;
    }
}
Visual Representation
For "))())(":
String:  )  )  (  )  )  (
Index:   0  1  2  3  4  5

Solution 1 trace:
i=0: ')' → needed=-1 → insertions=1, needed=1
i=1: ')' → needed=0
i=2: '(' → needed=2
i=3: ')' → needed=1  
i=4: ')' → needed=0
i=5: '(' → needed=2

Result: insertions=1 + needed=2 = 3
Rule Summary:
1.Each '(' needs '))' (two consecutive ')')
2.Track needed closing parentheses (needed)
3.If needed becomes negative, insert '('
4.If needed is odd, insert one ')'
5.At end, add remaining needed ')'
Edge Cases
1.Empty string: "" → 0
2.Already balanced: "()())" → 0
3.Only '(': "(((" → 6 (each needs two ')')
4.Only ')': "))))" → 6 (need '(' for each pair)
5.Mixed: "(()))(()))()())" → 2
6.Single ')': ")" → 2 (need '(' and one more ')')
Comparison with Regular Parentheses (LeetCode 921)
AspectLeetCode 921LeetCode 1541Pairing'(' ↔ ')' (1:1)'(' ↔ '))' (1:2)SolutionTrack balance, add when neededTrack needed closings, handle odd/evenKey insightBalance should never go negativeNeeded closings can be odd, fix by adding ')'
Why needed % 2 == 1 Check?
When needed is odd, it means:
- We have an incomplete pair of ')'
- Example: needed = 3 means we need 3 ')', but '(' needs them in pairs of 2
- We insert one ')' to make needed = 2 (a complete pair)
Example: "(()"
i=0: '(' → needed=2
i=1: '(' → needed=4  
i=2: ')' → needed=3 (ODD!)
  Insert ')' → insertions=1, needed=2
End: insertions=1 + needed=2 = 3

Result: Insert one ')' now, two ')' at end
Summary
The optimal solution:
1.Initialize insertions = 0, needed = 0
2.For '(': needed += 2, if needed odd: insert ')', needed--
3.For ')': needed--, if needed == -1: insert '(', needed = 1
4.Return insertions + needed
This handles all cases in O(n) time, O(1) space. The key is recognizing that '(' needs two consecutive ')', not just one.


Refer to
L921.Minimum Add to Make Parentheses Valid (Ref.L1249,L1963)
L1963.Minimum Number of Swaps to Make the String Balanced (Ref.L301,L2116)
