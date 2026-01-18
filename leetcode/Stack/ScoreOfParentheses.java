https://leetcode.com/problems/score-of-parentheses/
Given a balanced parentheses string s, return the score of the string.
The score of a balanced parentheses string is based on the following rule:
- "()" has score 1.
- AB has score A + B, where A and B are balanced parentheses strings.
- (A) has score 2 * A, where A is a balanced parentheses string.
 
Example 1:
Input: s = "()"
Output: 1

Example 2:
Input: s = "(())"
Output: 2

Example 3:
Input: s = "()()"
Output: 2
 
Constraints:
- 2 <= s.length <= 50
- s consists of only '(' and ')'.
- s is a balanced parentheses string.
--------------------------------------------------------------------------------
Attempt 1: 2023-03-24
Solution 1: Stack (60 min)
Style 1: No initial '0' on stack,  Use local variable 'cur_score' to identify "))" and "()" two different cases (read '(' before read ')')
class Solution { 
    public int scoreOfParentheses(String s) { 
        Stack<Integer> stack = new Stack<Integer>(); 
        int result = 0; 
        for(int i = 0; i < s.length(); i++) { 
            char c = s.charAt(i); 
            if(c == '(') { 
                stack.push(0); 
            } else { 
                int cur = stack.pop(); 
                // == 0 indicates no inner parentheses 
                // > 0 indicates have inner parentheses 
                if(cur == 0) { 
                    cur = 1; 
                } else { 
                    cur *= 2; 
                } 
                if(!stack.isEmpty()) { 
                    stack.push(stack.pop() + cur); 
                } else { 
                    result += cur; 
                } 
            } 
        } 
        return result; 
    } 
}

Time complexity: O(n)    
Space complexity: O(n)

Refer to
https://leetcode.com/problems/score-of-parentheses/solutions/197544/Java-Basic-Stack-Based-Solution-O(n)-time-O(n)-space/
class Solution { 
    public int scoreOfParentheses(String S) { 
        int result = 0; 
        Stack<Integer> stack = new Stack<Integer>(); 
        for(char c : S.toCharArray()) { 
            if(c == '(') { 
                // When encounter open bracket, push 0 on stack 
                // to initialize calculate for this section (the 
                // actual calculate will happen when encounter 
                // its corresponding close bracket) 
                stack.push(0); 
            } else { 
                // When encounter a close bracket, pop current 
                // score from stack and start actual calculate 
                int cur_score = stack.pop(); 
                // Based on given example, we have two scenarios: 
                // 1. If current close bracket's corresponding open bracket just  
                // one index ahead, and construct "()", e.g "(()(()))" when encounter  
                // ')' at index = 2, cur_score is 0 and it pushed onto stack because 
                // '(' at index = 1, these 2 chars able to construct "()", so when 
                // calculate we update the current score from 0 to 1 
                // 2. If current close bracket's corresponding open bracket not 
                // one index ahead, e.g "(()(()))" when encounter ')' at index = 6, 
                // its corresponding '(' at index = 3, cur_score is 1 and we should 
                // update from 1 to 1 * 2 = 2 
                if(cur_score == 0) { 
                    cur_score = 1; 
                } else { 
                    cur_score *= 2; 
                } 
                // Most important part: after pop current score (which actually means 
                // pop out current ')'), if the stack not empty, it means at same 
                // level (from current ')' close bracket's perspective), we have  
                // previous calculated section stored onto stack, we have to sum up 
                // previous section and current section, so pop peek value which 
                // means previous section score plus current score and push the sum 
                // onto stack to finish sum up 
                if(!stack.isEmpty()) { 
                    stack.push(stack.pop() + cur_score); 
                } else { 
                    // Or if stack is empty, which means we reach the out most level 
                    // of brackets, just add onto result 
                    result += cur_score; 
                } 
            } 
        } 
        return result; 
    } 
}
Our line of thinking is like this: when you see a new opening bracket, you'll later need to double the "score" of everything inside of it. When you see a closing bracket, pop the stack to retrieve the "score" of what's inside the pair, double the score (because of (A) = 2 * A, given in problem), then use it as a sum term; if it's inside another bracket, then add that to what's now at the top of the stack, because it needs to be part of that "doubling" operation. If the stack is empty, then just add it straight to your final answer.
The ternary operator in the popping operation area is because if you end up with '()', there is nothing inside of it, but the score should be 1, not 0. So, we treat that as a special case.
--------------------------------------------------------------------------------
Style 2: Prepare an initial '0' on stack
Not gonna work for me
Refer to
https://leetcode.com/problems/score-of-parentheses/solutions/1856519/java-c-visually-explained/
How's going Ladies - n - Gentlemen, today we are going to solve another coolest problem i.e. Score of Parentheses
In order to solve this problem we are assigned with few rules :-
"()" has score 1.
AB has score A + B, where A and B are balanced parentheses strings.
(A) has score 2 * A, where A is a balanced parentheses string.
Let's understand our rule's first of all :-
- If our input string is "()" then our score will be 1, so this is our first rule
- If we have input string "()()" then our score will be 1 + 1 i.e. 2, where 1 is a score of a balanced parentheses. So, this is our second rule
- Now last rule say's, if we have given something like "(())" in our input string, our score will be 2 X 1 i.e. 2, it means that we have something like nested parenthesis. Then multiply it by 2.
Let's take one example, inorder to solve this problem :-
Input : "( ( ) ( ( ) ) )"
Output : 6

Okay, so first thing came in our mind is can we solve this problem using stack, and I say yes we'll solve this problem using stack.
- First create one stack of Integer not Character
- So, as we are using Integer, what we gonna put in stack is intially 0 when we encounter (
- And we'll calculate the score when we encounter )


class Solution { 
    public int scoreOfParentheses(String s) { 
        Stack<Integer> st = new Stack<>(); 
        int score = 0; 
        for(int i = 0; i < s.length(); i++){ 
            char ch = s.charAt(i); 
            if(ch == '('){ 
                st.push(score); 
                score = 0; 
            } 
            else { 
                score = st.pop() + Math.max(2 * score, 1); 
            } 
        } 
        return score; 
    } 
}

Time Complexity: BigO(N)
Space Complexity: BigO(N)

Style 3: Prepare an initial '0' on stack, an intuitive solution by handling two cases on second last char as '(' or ')' when last char is ')', and each time when encounter ')' will pop out previous continuously two '(' if exists two, OR almost same but judge two cases on temporary sum > 0 or = 0 when last char is ')', and each time when encounter ')' will pop out previous continuously two '(' if exists two
Judge by last second char
class Solution { 
    public int scoreOfParentheses(String s) { 
        Stack<Integer> stack = new Stack<Integer>(); 
        stack.push(0); 
        for(int i = 0; i < s.length(); i++) { 
            char c = s.charAt(i); 
            if(c == '(') { 
                stack.push(0); 
            } else { 
                char p = s.charAt(i - 1); 
                int cur = stack.pop(); 
                // second last position p as '(' indicates no inner parentheses 
                // second last position p as ')' indicates have inner parentheses  
                if(p == '(') { 
                    stack.push(stack.pop() + 1); 
                } else { 
                    stack.push(stack.pop() + cur * 2); 
                } 
            } 
        } 
        return stack.peek(); 
    } 
}
=======================================================
Judge by last depth value
class Solution { 
    public int scoreOfParentheses(String s) { 
        Stack<Integer> stack = new Stack<Integer>(); 
        stack.push(0); 
        for(int i = 0; i < s.length(); i++) { 
            char c = s.charAt(i); 
            if(c == '(') { 
                stack.push(0); 
            } else { 
                int cur = stack.pop(); 
                // == 0 indicates no inner parentheses
                // > 0 indicates have inner parentheses 
                if(cur == 0) { 
                    stack.push(stack.pop() + 1); 
                } else { 
                    stack.push(stack.pop() + cur * 2); 
                } 
            } 
        } 
        return stack.peek(); 
    } 
}

Time complexity: O(n)     
Space complexity: O(n)

Refer to
https://leetcode.com/problems/score-of-parentheses/editorial/
https://leetcode.com/problems/score-of-parentheses/solutions/141975/c-solution-using-stack-5ms-detail-explained/comments/1311390
Every position in the string has a depth - some number of matching parentheses surrounding it. For example, the dot in (()(.())) has depth 2, because of these parentheses: (__(.__))
Our goal is to maintain the score at the current depth we are on. When we see an opening bracket, we increase our depth, and our score at the new depth is 0. When we see a closing bracket, we add twice the score of the previous deeper part - except when counting (), which has a score of 1.
For example, when counting (()(())), our stack will look like this:
- [0, 0] after parsing (
- [0, 0, 0] after (
- [0, 1] after )
- [0, 1, 0] after (
- [0, 1, 0, 0] after (
- [0, 1, 1] after )
- [0, 3] after )
- [6] after )
    public int scoreOfParentheses(String s) { 
        Stack<Integer> stack = new Stack<>();
        // Prepare a score of current frame 
        stack.push(0); 
         
        for(int i = 0; i < s.length(); i++) { 
            char c = s.charAt(i); 
             
            if(c == '(') { 
                stack.push(0); 
            } else { 
                // c= ')' 
                char p = s.charAt(i - 1); 
                if(p == '(') { 
                    stack.pop(); 
                    stack.push(stack.pop() + 1); 
                } else { 
                    int current = stack.pop(); 
                    stack.push(stack.pop() + current * 2); 
                } 
            } 
        } 
         
        return stack.pop(); 
    }

Refer to
https://leetcode.com/problems/score-of-parentheses/solutions/141975/c-solution-using-stack-5ms-detail-explained/
When travel through S, only meets ')', we need to calculate the score of this pair of parentheses. If we know the score of inner parentheses, such as 3, we can double it and pass to the outer parentheses. But the question is how do we know the score of inner parentheses? Using stack.

Explain with "( ( ) ( ( ) ) )"
1.stack: 0->string_traveled :""When start only 0 in stack, this int will store the total score
2.stack: 0->0->0->string_traveled:"( ("Meet two '(', push two zeros to the stack
3.stack: 0->1->string_traveled: "( ( )"First time meets ')', it balance the last '(', so pop the stack. But 0 indicates no inner 4.parentheses exists, so just pass 1 to parent parenthese.
5.stack: 0->1->0->0->string_traveled: "( ( ) ( ("Keep pushing zeros
6.stack 0->1->1->string_traveled: "( ( ) ( ( )"Balance one '(', and still no inner parenthese, so pass 1 to parent
7.stack 0->3->string_traveled: "( ( ) ( ( ) )"Balance another '(', but the inner is not zero, so double it and add to parent's score
8.stack 6->string_traveled: "( ( ) ( ( ) ) )"Same as last step, double the inner score and add to parent's

int scoreOfParentheses(string S) 
{ 
    stack<int> m_stack; 
    m_stack.push(0); // to keep the total score 
    for(char c:S){ 
        if(c=='(') 
            m_stack.push(0); //When meets '(', just push a zero to stack 
        else{ 
            int tmp=m_stack.top(); //  balance the last '(', it stored the score of inner parentheses 
            m_stack.pop(); 
            int val=0; 
            if(tmp>0) // not zero means inner parentheses exists and double it 
                val=tmp*2; 
            else // zero means no inner parentheses, just using 1 
                val=1; 
            m_stack.top()+=val; // pass the score of this level to parent parenthese 
        }    
    } 
    return m_stack.top(); 
}

--------------------------------------------------------------------------------
Solution 2: Recursion (60 min)
Style 1: Hard to get
class Solution { 
    public int scoreOfParentheses(String s) { 
        return helper(s, 0, s.length()); 
    } 
    private int helper(String s, int start, int end) { 
        int result = 0; 
        int balance = 0; 
        for(int i = start; i < end; i++) { 
            if(s.charAt(i) == '(') { 
                balance++; 
            } else { 
                balance--; 
            } 
            if(balance == 0) { 
                if(i - start == 1) { 
                    result++; 
                } else { 
                    result += 2 * helper(s, start + 1, i); 
                } 
                start = i + 1; 
            } 
        } 
        return result; 
    } 
}

Time complexity: O(n^2)   
Space complexity: O(n)

Refer to
https://leetcode.com/problems/score-of-parentheses/editorial/
Approach 1: Divide and Conquer
Intuition
Split the string into S = A + B where A and B are balanced parentheses strings, and A is the smallest possible non-empty prefix of S.

Algorithm
Call a balanced string primitive if it cannot be partitioned into two non-empty balanced strings.
By keeping track of balance (the number of ( parentheses minus the number of ) parentheses), we can partition S into primitive substrings 
S = P_1 + P_2 + ... + P_n. Then, 
score(S) = score(P_1) + score(P_2) + ... + score(P_n), by definition.
For each primitive substring 
(S[i], S[i+1], ..., S[k]), if the string is length 2, then the score of this string is 1. Otherwise, it's twice the score of the substring (S[i+1], S[i+2], ..., S[k-1]).
class Solution { 
    public int scoreOfParentheses(String S) { 
        return F(S, 0, S.length()); 
    } 
    public int F(String S, int i, int j) { 
        //Score of balanced string S[i:j] 
        int ans = 0, bal = 0; 
        // Split string into primitives 
        for (int k = i; k < j; ++k) { 
            bal += S.charAt(k) == '(' ? 1 : -1; 
            if (bal == 0) { 
                if (k - i == 1) ans++; 
                else ans += 2 * F(S, i+1, k); 
                i = k+1; 
            } 
        } 
        return ans; 
    } 
}
Complexity Analysis
- Time Complexity: O(N^2), where N is the length of S. An example worst case is (((((((....))))))).
- Space Complexity: O(N), the size of the implied call stack.
Style 2: Easy to get (Structure similar to L761.Special Binary String (Ref.L678,L856,L2116))
class Solution {
    public int scoreOfParentheses(String s) {
        return helper(s, 0, s.length() - 1);
    }

    private int helper(String s, int i, int j) {
        // Base case: "()" has score 1
        // i for '(' index, j for ')' index 
        if(i == j - 1) {
            return 1;
        }
        // Check if balanced parentheses reached
        int count = 0;
        for(int k = i; k <= j; k++) {
            if(s.charAt(k) == '(') {
                count++;
            } else {
                count--;
            }
            // Current string [i, k] has balanced parentheses
            if(count == 0) {
                // Whole string is balanced: (A), e.g A = (), whole string = (()),
                // only when k = j = 3 we find balanced parentheses, so strip outside
                // (), and recursion onto inside ()
                if(k == j) {
                    // Strip the outer '(' and ')' and process the inside substring
                    return 2 * helper(s, i + 1, j - 1);
                // Two parts: A + B, e.g A = (), B = (), whole string = ()(),
                // when k = 1, j = 3 (k != j) we already find balanced parentheses,
                // so divide the process into two parts, recursion respectively 
                // onto first () and second (), then conquer both result
                } else {
                    return helper(s, i, k) + helper(s, k + 1, j);
                }
            }
        }
        // Should never reach here for valid input
        return 0;
    }
}

Time Complexity: O(n^2) in worst case (skewed)
Space Complexity: O(n) recursion stack

Refer to Deepseek
Problem Understanding
Given a balanced parentheses string, compute its score:
- () has score 1
- AB has score A + B, where A and B are balanced parentheses strings
- (A) has score 2 * A, where A is a balanced parentheses string
Solution 1: Stack (Most Intuitive)
class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0); // Initial score
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0); // Start new group
            } else {
                // Closing parenthesis
                int current = stack.pop(); // Score of inner group
                int outer = stack.pop();   // Get outer score
                
                // If current is 0, it was "()" -> score 1
                // Otherwise it was "(A)" -> score 2 * current
                int score = outer + Math.max(2 * current, 1);
                stack.push(score);
            }
        }
        
        return stack.pop();
    }
}
Solution 2: Counting Layers (O(1) Space)
class Solution {
    public int scoreOfParentheses(String s) {
        int score = 0;
        int depth = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                depth++;
            } else {
                depth--;
                // If we just closed a "()", add 2^depth
                if (s.charAt(i - 1) == '(') {
                    score += 1 << depth; // 2^depth
                }
            }
        }
        
        return score;
    }
}
Solution 3: Recursive Divide and Conquer
class Solution {
    public int scoreOfParentheses(String s) {
        return score(s, 0, s.length() - 1);
    }
    
    private int score(String s, int left, int right) {
        if (left + 1 == right) { // Base case: "()"
            return 1;
        }
        
        int balance = 0;
        for (int i = left; i <= right; i++) {
            if (s.charAt(i) == '(') balance++;
            else balance--;
            
            // Found a balanced substring
            if (balance == 0) {
                if (i == right) {
                    // Whole string is balanced: (A)
                    return 2 * score(s, left + 1, right - 1);
                } else {
                    // Two parts: A + B
                    return score(s, left, i) + score(s, i + 1, right);
                }
            }
        }
        
        return 0; // Should never reach here for valid input
    }
}
Solution 4: Stack with Array (No Wrapper Objects)
class Solution {
    public int scoreOfParentheses(String s) {
        int[] stack = new int[s.length()];
        int top = -1;
        stack[++top] = 0; // Initial score
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack[++top] = 0;
            } else {
                int current = stack[top--];
                int outer = stack[top--];
                int score = outer + Math.max(2 * current, 1);
                stack[++top] = score;
            }
        }
        
        return stack[top];
    }
}
Solution 5: Iterative with While Loop
class Solution {
    public int scoreOfParentheses(String s) {
        int score = 0;
        int open = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                open++;
            } else {
                open--;
                if (s.charAt(i - 1) == '(') {
                    score += (1 << open);
                }
            }
        }
        
        return score;
    }
}
Solution 6: Stack of Scores (Alternative Approach)
class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> scores = new Stack<>();
        int currentScore = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                // Push current score and start new score
                scores.push(currentScore);
                currentScore = 0;
            } else {
                // Closing: currentScore is score inside this pair
                // If currentScore is 0, it was "()" -> score 1
                // Otherwise it was "(A)" -> score 2 * currentScore
                currentScore = scores.pop() + Math.max(2 * currentScore, 1);
            }
        }
        
        return currentScore;
    }
}
Detailed Explanation
Key Insight:
- Each pair of parentheses contributes 2^depth to the total score
- But only when it's a core () pair (not multiplied)
Example Walkthrough:
Example 1: "()"
- Depth when closing: 0
- Score: 2^0 = 1
Example 2: "(())"
- First () at depth 1: contributes 2^1 = 2
- Total: 2
Example 3: "()()"
- First () at depth 0: contributes 2^0 = 1
- Second () at depth 0: contributes 2^0 = 1
- Total: 1 + 1 = 2
Example 4: "(()(()))"
( ( ) ( ( ) ) )
0 1 1 1 2 2 1 0  depths

Only these contribute:  2^1 + 2^2 = 2 + 4 = 6
          () at depth 1 ^   ^ () at depth 2
Complexity Analysis
Solution 1 (Stack):
- Time: O(n) - single pass through string
- Space: O(n) - stack in worst case
Solution 2 (Counting Layers):
- Time: O(n) - single pass
- Space: O(1) - only variables
Solution 3 (Recursive):
- Time: O(n²) in worst case (skewed)
- Space: O(n) recursion stack
Recommended Solutions
For Interviews: Solution 2 (Counting Layers)
class Solution {
    public int scoreOfParentheses(String s) {
        int score = 0;
        int depth = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                depth++;
            } else {
                depth--;
                if (s.charAt(i - 1) == '(') {
                    score += 1 << depth; // 2^depth
                }
            }
        }
        
        return score;
    }
}
Why: O(1) space, elegant mathematical solution
For Clarity: Solution 1 (Stack)
class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0);
            } else {
                int inner = stack.pop();
                int outer = stack.pop();
                stack.push(outer + Math.max(2 * inner, 1));
            }
        }
        
        return stack.pop();
    }
}
Why: Directly implements the problem rules, easy to understand
Step-by-Step Example
Input: "(())()"
Solution 1 (Stack) trace:
Start: stack = [0]
'(': stack = [0, 0]
'(': stack = [0, 0, 0]
')': pop 0, pop 0 -> push 0 + max(2*0,1) = 1
    stack = [0, 1]
')': pop 1, pop 0 -> push 0 + max(2*1,1) = 2
    stack = [2]
'(': stack = [2, 0]
')': pop 0, pop 2 -> push 2 + max(2*0,1) = 3
    stack = [3]
Result: 3
Solution 2 (Counting) trace:
String: ( ( ) ) ( )
Depth:  0 1 2 1 0 1 0

Only score when ')' follows '(':
i=2: depth=1, score += 2^1 = 2
i=5: depth=0, score += 2^0 = 1
Total: 3
Edge Cases
1.Single pair: "()" → 1
2.Nested pairs: "(())" → 2
3.Multiple pairs: "()()" → 2
4.Mixed: "(()())" → 4
5.Deep nesting: "((()))" → 4
Mathematical Formula
The score can be computed as:
Score = sum(2^depth) for every "()" pair
where depth = number of '(' before this pair
This is why Solution 2 is so efficient - it directly computes this formula.

Q1: Can you show an example to explain how the two conditions happen ?
1.Whole string is balanced: (A)
2.Two parts: A + B
class Solution {
    public int scoreOfParentheses(String s) {
        return score(s, 0, s.length() - 1);
    }
    
    private int score(String s, int left, int right) {
        if (left + 1 == right) { // Base case: "()"
            return 1;
        }
        
        int balance = 0;
        for (int i = left; i <= right; i++) {
            if (s.charAt(i) == '(') balance++;
            else balance--;
            
            // Found a balanced substring
            if (balance == 0) {
                if (i == right) {
                    // Whole string is balanced: (A)
                    return 2 * score(s, left + 1, right - 1);
                } else {
                    // Two parts: A + B
                    return score(s, left, i) + score(s, i + 1, right);
                }
            }
        }
        
        return 0; // Should never reach here for valid input
    }
}
Excellent question! Let me walk through examples to show how these two conditions work in the recursive solution.
Understanding the Two Cases
Case 1: Whole string is balanced (A)
- Means: The entire substring from left to right is one balanced parentheses pair enclosing something
- Example: "(())", "(()())", "((()))"
Case 2: Two parts A + B
- Means: The substring can be split into two separate balanced parts concatenated together
- Example: "()()", "(())()", "()(())"
Visual Examples
Example 1: "(())" - Case 1 (Whole string balanced)
String: ( ( ) )
Index: 0 1 2 3
score("(())", 0, 3)
left = 0, right = 3

Trace through loop:
i=0: '(' → balance=1
i=1: '(' → balance=2
i=2: ')' → balance=1
i=3: ')' → balance=0 ← First time balance=0

Check: i == right? Yes (3 == 3)
So this is Case 1: Whole string is balanced (A)

Return: 2 * score("(())", 1, 2)
Example 2: "()()" - Case 2 (Two parts A + B)
String: ( ) ( )
Index: 0 1 2 3
score("()()", 0, 3)
left = 0, right = 3

Trace through loop:
i=0: '(' → balance=1
i=1: ')' → balance=0 ← First time balance=0

Check: i == right? No (1 != 3)
So this is Case 2: Two parts A + B

Return: score("()()", 0, 1) + score("()()", 2, 3)
More Complex Examples
Example 3: "(()(()))" - Case 1
String: ( ( ) ( ( ) ) )
Index: 0 1 2 3 4 5 6 7
score("(()(()))", 0, 7)
left = 0, right = 7

Trace:
i=0: '(' → balance=1
i=1: '(' → balance=2
i=2: ')' → balance=1
i=3: '(' → balance=2
i=4: '(' → balance=3
i=5: ')' → balance=2
i=6: ')' → balance=1
i=7: ')' → balance=0 ← First time balance=0 at i=7

Check: i == right? Yes (7 == 7)
Case 1: Whole string is balanced

Return: 2 * score("(()(()))", 1, 6)
Example 4: "(())()" - Case 2
String: ( ( ) ) ( )
Index: 0 1 2 3 4 5
score("(())()", 0, 5)
left = 0, right = 5

Trace:
i=0: '(' → balance=1
i=1: '(' → balance=2
i=2: ')' → balance=1
i=3: ')' → balance=0 ← First time balance=0 at i=3

Check: i == right? No (3 != 5)
Case 2: Two parts A + B

Return: score("(())()", 0, 3) + score("(())()", 4, 5)
Complete Recursive Tree for "(())()"
score("(())()", 0, 5)
├── i=3: balance=0, i≠right → Case 2
│   ├── score("(())()", 0, 3)  // First part: "(())"
│   │   ├── i=3: balance=0, i=right → Case 1
│   │   │   └── 2 * score("(())()", 1, 2)  // Inside "(())"
│   │   │       ├── i=2: balance=0, i=right → Case 1? No!
│   │   │       │   Actually left+1=right (1+1=2) → Base case "()"
│   │   │       │   └── return 1
│   │   │       └── return 2 * 1 = 2
│   │   └── return 2
│   └── score("(())()", 4, 5)  // Second part: "()"
│       ├── left+1=right (4+1=5) → Base case "()"
│       └── return 1
└── return 2 + 1 = 3
Why This Logic Works
Key Insight:
When we scan left to right, the first time balance becomes 0 tells us something important:
1.If it happens at the end (i == right):
- The parentheses from left to right form one complete unit
- Must be of form (A) where A is balanced
- Score = 2 × score(A)
2.If it happens before the end (i < right):
- The string can be split at position i
- First part: left to i is balanced
- Second part: i+1 to right is balanced
- Score = score(first part) + score(second part)
Why Check balance == 0 Only Once?
Because parentheses strings are balanced, we only need to find the first complete balanced segment. After that:
- Either we're done (Case 1)
- Or we split and recurse on both parts (Case 2)
Edge Cases Demonstration
Case 1 Only: "((()))"
((()))
012345

i=5: First time balance=0
i == right → Case 1
Case 2 Only: "()()()"
()()()
012345

i=1: First time balance=0  
i != right → Case 2
Split into "()" + "()()"
Mixed: "(()())()"
(()())()
01234567

i=5: First time balance=0
i != right → Case 2  
Split into "(()())" + "()"
Alternative View: Parentheses Matching
Think of it like matching parentheses:
- When balance == 0, we've closed all open parentheses
- If this happens at the very end → whole string is one matched pair
- If this happens earlier → we have multiple independent matched groups
Debug Visualization
For "(())()":
String: ( ( ) ) ( )
Index:  0 1 2 3 4 5
Balance:1 2 1 0 1 0
         ↑
         First balance=0 at i=3
         Since 3 != 5, split at index 3
For "(()(()))":
String: ( ( ) ( ( ) ) )
Index:  0 1 2 3 4 5 6 7
Balance:1 2 1 2 3 2 1 0
                         ↑
                         First balance=0 at i=7
                         Since 7 == 7, whole string is one unit
This recursive approach elegantly handles both cases by simply checking where the first complete balance occurs relative to the end of the current substring.

Q2: Can you explain what's the logic behind statement 'Math.max(2 * current, 1);' in below solution ?
class Solution {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0); // Initial score
        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0); // Start new group
            } else {
                // Closing parenthesis
                int current = stack.pop(); // Score of inner group
                int outer = stack.pop();   // Get outer score
                // If current is 0, it was "()" -> score 1
                // Otherwise it was "(A)" -> score 2 * current
                int score = outer + Math.max(2 * current, 1);
                stack.push(score);
            }
        }
        return stack.pop();
    }
}
Excellent question! This Math.max(2 * current, 1) is the core logic that handles two different cases in one elegant expression. Let me break it down:
What current Represents
current is the score of whatever was inside the parentheses we just closed:
int current = stack.pop(); // Score of inner group
- When we encounter ), we pop the top of stack
- This value represents the score calculated so far for the current group
The Two Cases
Case 1: "()" → current = 0
When we have a simple ():
Stack before: [outer, 0]
When we see '(' → push 0: [outer, 0, 0]
When we see ')' → pop: current = 0
Since current = 0, it means the parentheses contained nothing → it was ()
Score should be: 1
Case 2: "(A)" → current > 0
When we have parentheses containing something:
Example: (()) or (()()) etc.
Stack before closing: [outer, X] where X > 0
When we see ')' → pop: current = X
Since current > 0, it means the parentheses contained some scored content
Score should be: 2 * current
Why Math.max(2 * current, 1) Works
Let's test both cases:
Case 1: current = 0
2 * current = 2 * 0 = 0
Math.max(0, 1) = 1 ✓
Case 2: current > 0 (let's say current = 2)
2 * current = 2 * 2 = 4
Math.max(4, 1) = 4 ✓
Step-by-Step Examples
Example 1: "()"
Start: stack = [0]

1. '(' → push 0: [0, 0]
2. ')' → pop current=0, pop outer=0

   score = 0 + Math.max(2*0, 1) = 0 + 1 = 1
   push: [1]
Result: 1 ✓
Example 2: "(())"
Start: stack = [0]

1. '(' → push 0: [0, 0]
2. '(' → push 0: [0, 0, 0]
3. ')' → pop current=0, pop outer=0

   score = 0 + Math.max(2*0, 1) = 1
   push: [0, 1]
4. ')' → pop current=1, pop outer=0

   score = 0 + Math.max(2*1, 1) = 0 + 2 = 2
   push: [2]
Result: 2 ✓

Example 3: "()()"
Start: stack = [0]

1. '(' → [0, 0]
2. ')' → current=0, outer=0 → score=1 → [1]
3. '(' → [1, 0]
4. ')' → current=0, outer=1 → score=1+1=2 → [2]

Result: 2 ✓
Example 4: "(()(()))"
Let's trace this complex example:
String: ( ( ) ( ( ) ) )
Stack trace:
[0]
( → [0, 0]
( → [0, 0, 0]
) → current=0 → score=1 → [0, 1]
( → [0, 1, 0]
( → [0, 1, 0, 0]
) → current=0 → score=1 → [0, 1, 1]
) → current=1 → score=2*1=2 → outer=1 → score=1+2=3 → [0, 3]
) → current=3 → score=2*3=6 → outer=0 → score=0+6=6 → [6]
Result: 6 ✓
Alternative Without Math.max
We could write it more explicitly:
int score;
if (current == 0) {
    // Case: "()"
    score = outer + 1;
} else {
    // Case: "(A)"
    score = outer + 2 * current;
}
stack.push(score);
But Math.max(2 * current, 1) is more concise and elegant because:
- When current = 0: 2*0=0, so max(0,1)=1
- When current > 0: 2*current > 1, so max(2*current,1)=2*current
Why This Mathematical Trick Works
The key insight is that for valid parentheses:
- current is always non-negative
- The only time 2 * current < 1 is when current = 0
- For current ≥ 1, 2 * current ≥ 2 > 1
So Math.max(2 * current, 1) correctly distinguishes:
- () case: gives 1
- (A) case: gives 2 * current
Visual Representation
current value | 2*current | Math.max(2*current, 1) | Meaning
-------------|-----------|------------------------|---------
0            | 0         | 1                     | "()" 
1            | 2         | 2                     | "(1)" where inner score=1
2            | 4         | 4                     | "(2)" where inner score=2
3            | 6         | 6                     | "(3)" where inner score=3

...          | ...       | ...                   | ...
Edge Case Verification
What if current could be negative? (It can't in valid parentheses)
- The algorithm assumes valid input
- In valid parentheses, scores are always non-negative
- current = 0 only for empty parentheses ()
Connection to Problem Definition
Recall the rules:
1.() has score 1 → Math.max(2*0, 1) = 1
2.(A) has score 2 × score(A) → Math.max(2*score(A), 1) = 2*score(A) when score(A) > 0
3.AB has score A + B → handled by outer + ... part
The Math.max(2 * current, 1) elegantly combines rules 1 and 2!
Summary
Math.max(2 * current, 1) is a clever one-liner that handles:
- Base case (): current = 0 → returns 1
- Recursive case (A): current > 0 → returns 2 * current
This works because in valid parentheses strings, current (the score inside parentheses) is always non-negative, and the only time 2 * current < 1 is when current = 0, which corresponds exactly to the () case that should score 1.

Q2: Also why we add this line 'stack.push(0); // Initial score' ?
Excellent question! The stack.push(0) is crucial and often misunderstood. Let me explain why we need this initial score.
What the Initial 0 Represents
stack.push(0); // Initial score
This 0 represents the cumulative score of everything processed so far at the current nesting level. Think of it as the "outer context" score.
Why We Need It: The Stack Structure
The stack stores scores at different nesting levels:
Stack structure:
[score_at_level_0, score_at_level_1, score_at_level_2, ...]
      ^                   ^                   ^
   Overall score      Current group       Inner group
   accumulated       being evaluated      being evaluated
Without Initial 0 - What Would Happen?
Let's trace "()" without initial 0:
// WRONG: No initial 0
Stack<Integer> stack = new Stack<>();

for (char c : s.toCharArray()) {
    if (c == '(') {
        stack.push(0); // Start new group
    } else {
        int current = stack.pop(); // Score of inner group
        int outer = stack.pop();   // PROBLEM: Stack empty!
        // java.util.EmptyStackException!
        int score = outer + Math.max(2 * current, 1);
        stack.push(score);
    }
}
Step-by-Step Trace WITH Initial 0
Example 1: "()"
Step 0: Initial stack = [0]
          ↑
          This is our "outer context" score

Step 1: '(' → push new group score (0)
        stack = [0, 0]
                ↑  ↑
            outer  current group starts

Step 2: ')' → 
        pop current = 0  (score inside parentheses)
        pop outer = 0    (previous cumulative score)
        score = 0 + Math.max(2*0, 1) = 0 + 1 = 1
        push: [1]
        
Result: 1 ✓
Example 2: "(())"
Step 0: stack = [0]

Step 1: '(' → push 0: [0, 0]

Step 2: '(' → push 0: [0, 0, 0]

Step 3: ')' → 
        pop current = 0
        pop outer = 0
        score = 0 + 1 = 1
        push: [0, 1]
        // Now: [outer_context, inner_group_score]

Step 4: ')' → 
        pop current = 1 (score inside outer parentheses)
        pop outer = 0   (outer context)
        score = 0 + Math.max(2*1, 1) = 0 + 2 = 2
        push: [2]
        
Result: 2 ✓
The Role of Initial 0 as "Outer Context"
Think of it as having a virtual outer parentheses that contains everything:
Implicit: (  (())()  )
Explicit:       ↑
           What we actually process
           
Initial 0 = score outside our virtual parentheses (which is 0)
More Complex Example: "()()"
Step 0: stack = [0]

Step 1: '(' → [0, 0]
Step 2: ')' → pop current=0, outer=0 → score=1 → [1]
        // First "()" evaluated to 1, stored as outer context

Step 3: '(' → [1, 0]
        // Outer context = 1, starting new group

Step 4: ')' → pop current=0, outer=1 → score=1+1=2 → [2]
        // Add new score to outer context
        
Result: 2 ✓
Why Not Start with Empty Stack?
Because we always need two values when we see ')':
1.current - score inside the closing parentheses
2.outer - cumulative score before this group
The initial 0 ensures we always have an outer value to pop, even for the very first parentheses.
Alternative View: Stack as Accumulator
The stack is like an accumulator of scores at different depths:
Depth 0: Overall accumulated score (starts at 0)
Depth 1: Score of current parentheses group
Depth 2: Score inside that group, etc.
When we finish a group (see ')'), we:
1.Get the group's internal score (current)
2.Get the accumulated score so far (outer)
3.Add the group's contribution
4.Update the accumulated score
Visual Metaphor
Think of it like Russian nesting dolls:
Initial: [0]  ← Empty outermost doll

When we see '(':
  We open a new doll → push 0 for its contents

When we see ')':
  We close the current doll:
  1. Take out what's inside (current score)
  2. Look at the outer doll (outer score)
  3. Calculate this doll's value
  4. Put it back into outer doll's context
The initial 0 is the empty outermost doll that contains everything.
Without Initial 0 - Edge Cases Fail
Let's test "((()))" without initial 0:
Processing "((()))":
Step 1: '(' → push 0: [0]
Step 2: '(' → push 0: [0, 0]
Step 3: '(' → push 0: [0, 0, 0]
Step 4: ')' → pop current=0, pop outer=0 → score=1 → [0, 1]
Step 5: ')' → pop current=1, pop outer=0 → score=2 → [2]
Step 6: ')' → pop current=2, pop outer=??? → ERROR! No outer to pop!
With initial 0:
Step 0: [0]
Step 1: '(' → [0, 0]
Step 2: '(' → [0, 0, 0]
Step 3: '(' → [0, 0, 0, 0]
Step 4: ')' → [0, 0, 1]
Step 5: ')' → [0, 2]
Step 6: ')' → [4] ✓
Summary of Why We Need Initial 0
1.Always have an outer context: Even for the first parentheses, there's an "outer" score (initially 0)
2.Consistent stack operations: Every ')' needs to pop two values: current and outer
3.Accumulator pattern: The stack stores accumulated scores at different nesting levels
4.Handles concatenation: The outer score accumulates multiple group scores (like in "()()")
The initial 0 is essentially saying: "Before we process any parentheses, our accumulated score is 0." This creates a consistent framework where every group adds its score to an outer context.


Refer to
L678.Valid Parenthesis String (Ref.L761,L2116)
L761.Special Binary String (Ref.L678,L2116)
L2116.Check if a Parentheses String Can Be Valid (Ref.L301,L678,L1963)
