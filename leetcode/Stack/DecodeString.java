
https://leetcode.com/problems/decode-string/
Given an encoded string, return its decoded string.
The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that 
k is guaranteed to be a positive integer.
You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].
The test cases are generated so that the length of the output will never exceed 10^5.

Example 1:
Input: s = "3[a]2[bc]"
Output: "aaabcbc"

Example 2:
Input: s = "3[a2[c]]"
Output: "accaccacc"

Example 3:
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"

Constraints:
- 1 <= s.length <= 30
- s consists of lowercase English letters, digits, and square brackets '[]'.
- s is guaranteed to be a valid input.
- All the integers in s are in the range [1, 300].
--------------------------------------------------------------------------------
Attempt 1: 2023-10-22
Solution 1: Two Stacks (120 min)
Style 1: Handle as 3 cases: '[', ']' and other (number or char in same case but two logic)
class Solution {
    public String decodeString(String s) {
        Stack<Integer> numberStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder curr = new StringBuilder();
        int number = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '[') {
                // Push current number and current string to stacks
                numberStack.push(number);
                stringStack.push(curr);
                // Reset for current number and reset for new substring,
                // prepare for the new inner section
                curr = new StringBuilder();
                number = 0;
            } else if(c == ']') {
                // Pop count and previous string
                int count = numberStack.pop();
                StringBuilder decoded = stringStack.pop();
                // Append current string repeated count times
                for(int j = 0; j < count; j++) {
                    decoded.append(curr);
                }
                // Critical statement: Continue use global object 'curr'
                // to record the decoded string
                curr = decoded;
            } else {
                // Handle multi-digit numbers
                if(Character.isDigit(c)) {
                    number = number * 10 + (c - '0');
                } else {
                    curr.append(c);
                }
            }
        }
        return curr.toString();
    }
}

Style 2: We must create new StringBuilder object 'sb' for each level of brackets, don't use 'sb.setLength(0)'
class Solution {
    public String decodeString(String s) {
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> resultStack = new Stack<>();
        int num = 0;
        // StringBuilder 'sb' is the running storage responsible for
        // storing any string inside a bracket, it will expand previous
        // stored string to new value when traveling from most inside
        // bracket level to outside bracket level
        // e.g for input s = "3[a2[c]x]"
        // sb changing from "c" -> "acc" -> "accx" -> "accxaccxaccx"
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                num = 10 * num + (c - '0');
            } else if(c == '[') {
                intStack.push(num);
                resultStack.push(sb);
                num = 0;
                //sb.setLength(0); 
                // -> cannot use this to avoid overwriting existing
                // 'preResult' string stored in StringBuilder object
                // 'sb', we have to create new object as a refresh 
                // StringBuilder to receive chars inside current 
                // brackets and build a temporary string as 
                // 'stringInCurrentBrackets'
                sb = new StringBuilder();
            } else if(c == ']') {
                int repeat = intStack.pop();
                //StringBuilder tmp = resultStack.pop();
                StringBuilder tmp = sb;
                sb = resultStack.pop();
                for(int j = 0; j < repeat; j++) {
                    sb.append(tmp);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

Style 3: A more readable way
class Solution {
    public String decodeString(String s) {
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> resultStack = new Stack<>();
        int num = 0;
        // StringBuilder 'sb' is the running storage responsible for
        // storing any string inside a bracket, it will expand previous
        // stored string to new value when traveling from most inside
        // bracket level to outside bracket level
        // e.g for input s = "3[a2[c]x]"
        // sb changing from "c" -> "acc" -> "accx" -> "accxaccxaccx"
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                num = 10 * num + (c - '0');
            } else if(c == '[') {
                intStack.push(num);
                resultStack.push(sb);
                num = 0;
                //sb.setLength(0); 
                // -> cannot use this to avoid overwriting existing
                // 'preResult' string stored in StringBuilder object
                // 'sb', we have to create new object as a refresh 
                // StringBuilder to receive chars inside current 
                // brackets and build a temporary string as 
                // 'stringInCurrentBrackets'
                sb = new StringBuilder();
            } else if(c == ']') {
                // A more readable way
                int repeat = intStack.pop();
                StringBuilder stringInCurrentBrackets = sb;
                StringBuilder preResult = resultStack.pop();
                for(int j = 0; j < repeat; j++) {
                    preResult.append(stringInCurrentBrackets);
                }
                sb = preResult;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

Refer to
https://leetcode.com/problems/decode-string/solutions/87534/simple-java-solution-using-stack/comments/92413
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();
        StringBuilder cur = new StringBuilder();
        int k = 0;
        for (char ch : s.toCharArray()) {
            if (Character.isDigit(ch)) {
                k = k * 10 + ch - '0';
            } else if ( ch == '[') {
                intStack.push(k);
                strStack.push(cur);
                cur = new StringBuilder();
                k = 0;
            } else if (ch == ']') {
                StringBuilder tmp = cur;
                cur = strStack.pop();
                for (k = intStack.pop(); k > 0; --k) cur.append(tmp);
            } else cur.append(ch);
        }
        return cur.toString();

Refer to
https://grandyang.com/leetcode/394/
我们也可以用迭代的方法写出来，当然需要用 stack 来辅助运算，我们用两个 stack，一个用来保存个数，一个用来保存字符串，我们遍历输入字符串，如果遇到数字，我们更新计数变量 cnt；如果遇到左括号，我们把当前 cnt 压入数字栈中，把当前t压入字符串栈中；如果遇到右括号时，我们取出数字栈中顶元素，存入变量k，然后给字符串栈的顶元素循环加上k个t字符串，然后取出顶元素存入字符串t中；如果遇到字母，我们直接加入字符串t中即可，参见代码如下：
    class Solution {
        public:
        string decodeString(string s) {
            string t = "";
            stack<int> s_num;
            stack<string> s_str;
            int cnt = 0;
            for (int i = 0; i < s.size(); ++i) {
                if (s[i] >= '0' && s[i] <= '9') {
                    cnt = 10 * cnt + s[i] - '0';
                } else if (s[i] == '[') {
                    s_num.push(cnt);
                    s_str.push(t);
                    cnt = 0; t.clear();
                } else if (s[i] == ']') {
                    int k = s_num.top(); s_num.pop();
                    for (int j = 0; j < k; ++j) s_str.top() += t;
                    t = s_str.top(); s_str.pop();
                } else {
                    t += s[i];
                }
            }
            return s_str.empty() ? t : s_str.top();
        }
    };

--------------------------------------------------------------------------------
Solution 2: Recursion (120 min, exatly similar 'global index variable' and 'break' style as L1190.Reverse Substrings Between Each Pair of Parentheses (Ref.L856,L1021))
Style 1: For loop
class Solution {
    // The int i is a globle parameter to indicate the current 
    // index of char in the String s.
    int i = 0;
    public String decodeString(String s) {
        return helper(s);
    }

    private String helper(String s) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        for(; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if(c == '[') {
                // Skip '['
                i++;
                String next = helper(s);
                for(int j = 0; j < num; j++) {
                    sb.append(next);
                }
                // reset the value for the next input in same level
                num = 0;
            } else if(c == ']') {
                // Skip ']' but no need 'i++'
                // Why no need to 'i++' here ?
                // Test out: "3[a]2[bc]"
                // Initially start with level 1 recursion, i = 0, char c = '3'
                // Then for loop i++ on level 1 recursion -> i = 1, 
                // char c = '[', i++ -> i = 2, skip '['
                // Then go into level 2 recursion, i keep = 2 (even hit for loop
                // logic, but not hit i++ on for loop yet), char c = 'a'
                // Then for loop i++ on level 2 recursion -> i = 3,
                // char c = ']', break, Important !! -> no i++ here
                // Finish level 2 recursion and move back to level 1, i keep as 3
                // Then for loop i++ on level 1 recursion -> i = 4,
                // char c = '2', then continue similar logic on level 1 recursion
                // ... etc.
                // Note: we don't need i++ to skip ']', it auto skipped when move
                // back to parent level
                //i++; 
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}

Style 2: While loop
class Solution {
    int index = 0;
    public String decodeString(String s) {
        return helper(s);
    }

    private String helper(String s) {
        if(index >= s.length()) {
            return "";
        }
        StringBuilder curr = new StringBuilder();
        int number = 0;
        while(index < s.length()) {
            char c = s.charAt(index);
            index++;
            if(c == '[') {
                // Recursively decode inside brackets
                String next = helper(s);
                // Append repeated string calculate from next recursion level
                for(int i = 0; i < number; i++) {
                    curr.append(next);
                }
                // Reset number to 0 for next [] at the same level
                // e.g 3[a]2[bc], [] for [bc] is the next [] at
                // the same level will be impact if the number not
                // set back to 0, the number will grow into 3*10+2
                // = 32 if not set back, on the other hand, nested
                // brackets like 3[a2[c]] won't be impact even number 
                // not set back to 0, since when enter next recursion 
                // level, the number will initialize as 0 again
                number = 0;
            } else if(c == ']') {
                // End of current level, break out directly to last
                // statement as 'return curr.toString()'
                break;
            } else {
                if(Character.isDigit(c)) {
                    number = number * 10 + (c - '0');
                } else {
                    curr.append(c);
                }
            }
        }
        return curr.toString();
    }
}

Or write as below
class Solution {
    // The int i is a globle parameter to indicate the current 
    // index of char in the String s.
    int i = 0;
    public String decodeString(String s) {
        return helper(s);
    }

    private String helper(String s) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while(i < s.length()) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                num = num * 10 + c - '0';
            } else if(c == '[') {
                // Skip '['
                i++;
                String next = helper(s);
                for(int j = 0; j < num; j++) {
                    sb.append(next);
                }
                // reset the value for the next input in same level
                num = 0;
            } else if(c == ']') {
                // Skip ']' but no need 'i++'
                // Why no need to 'i++' here ?
                // Test out: "3[a]2[bc]"
                // Initially start with level 1 recursion, i = 0, char c = '3'
                // Then for loop i++ on level 1 recursion -> i = 1, 
                // char c = '[', i++ -> i = 2, skip '['
                // Then go into level 2 recursion, i keep = 2 (even hit for loop
                // logic, but not hit i++ on for loop yet), char c = 'a'
                // Then for loop i++ on level 2 recursion -> i = 3,
                // char c = ']', break, Important !! -> no i++ here
                // Finish level 2 recursion and move back to level 1, i keep as 3
                // Then for loop i++ on level 1 recursion -> i = 4,
                // char c = '2', then continue similar logic on level 1 recursion
                // ... etc.
                // Note: we don't need i++ to skip ']', it auto skipped when move
                // back to parent level
                //i++; 
                break;
            } else {
                sb.append(c);
            }
            i++;
        }
        return sb.toString();
    }
}

Refer to
https://grandyang.com/leetcode/394/
这道题让我们把一个按一定规则编码后的字符串解码成其原来的模样，编码的方法很简单，就是把重复的字符串放在一个括号里，把重复的次数放在括号的前面，注意括号里面有可能会嵌套括号，这题可以用递归和迭代两种方法来解，我们首先来看递归的解法，把一个括号中的所有内容看做一个整体，一次递归函数返回一对括号中解码后的字符串。给定的编码字符串实际上只有四种字符，数字，字母，左括号，和右括号。那么我们开始用一个变量i从0开始遍历到字符串的末尾，由于左括号都是跟在数字后面，所以首先遇到的字符只能是数字或者字母，如果是字母，直接存入结果中，如果是数字，循环读入所有的数字，并正确转换，那么下一位非数字的字符一定是左括号，指针右移跳过左括号，对之后的内容调用递归函数求解，注意我们循环的停止条件是遍历到末尾和遇到右括号，由于递归调用的函数返回了子括号里解码后的字符串，而我们之前把次数也已经求出来了，那么循环添加到结果中即可，参见代码如下：
    class Solution {
        public:
        string decodeString(string s) {
            int i = 0;
            return decode(s, i);
        }
        string decode(string s, int& i) {
            string res = "";
            int n = s.size();
            while (i < n && s[i] != ']') {
                if (s[i] < '0' || s[i] > '9') {
                    res += s[i++];
                } else {
                    int cnt = 0;
                    while (s[i] >= '0' && s[i] <= '9') {
                        cnt = cnt * 10 + s[i++] - '0';
                    }
                    ++i;
                    string t = decode(s, i);
                    ++i;
                    while (cnt-- > 0) {
                        res += t;
                    }
                }
            }
            return res;
        }
    };

https://leetcode.com/problems/decode-string/solutions/738090/c-recursion-and-short/
Four cases are considered. 1st when we encounter a [. 2nd when we encounter a digit. 3rd encountering a ]. and last simple words. 
Please note that index i is passed by reference not by value.
class Solution {
public:
    string helper(int &i,string s){
        int num=0;
        string word="";
        for(;i<s.length();i++)
        {
            if(s[i]>='0' and s[i]<='9')
                num=num*10+s[i]-'0';
            else if(s[i]=='[')
            {
                string ans=helper(++i,s);
                for(;num>0;num--)word+=ans;
            }
            else if(s[i]==']')
                return word;
            else
                word+=s[i];
        }
        return word;
    }
    string decodeString(string s) {
        int i=0;
        return helper(i,s);
    }
};

https://leetcode.com/problems/decode-string/solutions/1634874/java-dfs-0ms-easy-understanding/
The idea is: for the string in [ ] pair, we can recurse it as the source string. The codes go into the lower level at '[', and back to parent at ']'.
The int pos is a globe parameter to indicate the current index of char in the String s.
private int pos = 0; 
    public String decodeString(String s) {
        int n = s.length(), repeat = 0;
        StringBuilder buf = new StringBuilder();
        while (pos < n) {
            char c = s.charAt(pos);
            if (c >= 'a' && c <= 'z') {
                buf.append(c);
            } else if (c >= '0' && c <= '9') {
                repeat = repeat * 10 + (c - '0');
            } else if (c == '[') {
                pos++;  // skip the char '['
                String str = decodeString(s);  // pos is diff for each call
                for (int i = 0; i < repeat; i++)
                       buf.append(str);
                repeat = 0;  // reset the value for the next input
            } else if (c == ']')
                break;
            pos++;
        }
        return buf.toString();
    }    

Refer to Deepseek
Problem Understanding
Given an encoded string, decode it following the rule k[encoded_string], where the encoded_string inside the brackets is repeated exactly k times.
Examples:
- "3[a]2[bc]" → "aaabcbc"
- "3[a2[c]]" → "accaccacc"
- "2[abc]3[cd]ef" → "abcabccdcdcdef"
Solution 1: Stack with Two Stacks (Most Common)
class Solution {
    public String decodeString(String s) {
        Stack<Integer> countStack = new Stack<>();
        Stack<StringBuilder> stringStack = new Stack<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0'); // Handle multi-digit numbers
            } else if (c == '[') {
                // Push current k and current string to stacks
                countStack.push(k);
                stringStack.push(current);
                // Reset for new substring
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                // Pop count and previous string
                int count = countStack.pop();
                StringBuilder decoded = stringStack.pop();
                // Append current string repeated count times
                for (int i = 0; i < count; i++) {
                    decoded.append(current);
                }
                current = decoded;
            } else {
                // Regular character
                current.append(c);
            }
        }
        
        return current.toString();
    }
}
Solution 2: Single Stack with Marker
class Solution {
    public String decodeString(String s) {
        Stack<Character> stack = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (c != ']') {
                stack.push(c);
            } else {
                // Get string inside brackets
                StringBuilder sb = new StringBuilder();
                while (!stack.isEmpty() && Character.isLetter(stack.peek())) {
                    sb.insert(0, stack.pop());
                }
                String sub = sb.toString();
                
                // Remove '['
                stack.pop();
                
                // Get number
                StringBuilder numStr = new StringBuilder();
                while (!stack.isEmpty() && Character.isDigit(stack.peek())) {
                    numStr.insert(0, stack.pop());
                }
                int k = Integer.parseInt(numStr.toString());
                
                // Push decoded string back to stack
                for (int i = 0; i < k; i++) {
                    for (char ch : sub.toCharArray()) {
                        stack.push(ch);
                    }
                }
            }
        }
        
        // Build result from stack
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.insert(0, stack.pop());
        }
        
        return result.toString();
    }
}
Solution 3: Recursive DFS (Classic Approach)
class Solution {
    private int index = 0;
    
    public String decodeString(String s) {
        return dfs(s);
    }
    
    private String dfs(String s) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        
        while (index < s.length()) {
            char c = s.charAt(index++);
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                // Recursively decode inside brackets
                String inner = dfs(s);
                // Append repeated string
                for (int i = 0; i < num; i++) {
                    result.append(inner);
                }
                num = 0; // Reset number
            } else if (c == ']') {
                // End of current level
                break;
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
}
Solution 4: Pure Recursion without Global Index
class Solution {
    public String decodeString(String s) {
        return decode(s, new int[]{0});
    }
    
    private String decode(String s, int[] idx) {
        StringBuilder result = new StringBuilder();
        int num = 0;
        
        while (idx[0] < s.length()) {
            char c = s.charAt(idx[0]);
            idx[0]++; // Move index
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                String inner = decode(s, idx);
                for (int i = 0; i < num; i++) {
                    result.append(inner);
                }
                num = 0;
            } else if (c == ']') {
                break;
            } else {
                result.append(c);
            }
        }
        
        return result.toString();
    }
}
Solution 5: Iterative with One Stack (Optimized)
class Solution {
    public String decodeString(String s) {
        Stack<Object> stack = new Stack<>();
        StringBuilder current = new StringBuilder();
        int num = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                // Push current number and string
                stack.push(num);
                stack.push(current);
                // Reset
                num = 0;
                current = new StringBuilder();
            } else if (c == ']') {
                // Pop previous string and number
                StringBuilder prev = (StringBuilder) stack.pop();
                int count = (Integer) stack.pop();
                // Build decoded string
                for (int i = 0; i < count; i++) {
                    prev.append(current);
                }
                current = prev;
            } else {
                current.append(c);
            }
        }
        
        return current.toString();
    }
}
Solution 6: Using String Stack Only
class Solution {
    public String decodeString(String s) {
        Stack<String> stack = new Stack<>();
        StringBuilder current = new StringBuilder();
        int num = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                // Push current number as string
                stack.push(current.toString());
                stack.push(String.valueOf(num));
                // Reset
                current = new StringBuilder();
                num = 0;
            } else if (c == ']') {
                // Pop number and previous string
                int count = Integer.parseInt(stack.pop());
                String prev = stack.pop();
                // Build decoded string
                StringBuilder temp = new StringBuilder(prev);
                for (int j = 0; j < count; j++) {
                    temp.append(current);
                }
                current = temp;
            } else {
                current.append(c);
            }
        }
        
        return current.toString();
    }
}
Detailed Explanation
Key Insight:
- Use stack to handle nested brackets
- When encountering '[', save current context (string and repeat count)
- When encountering ']', pop context and build decoded string
Example Walkthrough:
Example 1: "3[a]2[bc]"
Solution 1 trace:
Step 1: '3' → k = 3
Step 2: '[' → push k=3, push current="", reset
Step 3: 'a' → current="a"
Step 4: ']' → pop count=3, pop prev="", append "a"×3 → current="aaa"
Step 5: '2' → k = 2
Step 6: '[' → push k=2, push current="aaa", reset
Step 7: 'b' → current="b"
Step 8: 'c' → current="bc"
Step 9: ']' → pop count=2, pop prev="aaa", append "bc"×2 → current="aaabcbc"
Result: "aaabcbc"

Example 2: "3[a2[c]]"
Trace:
'3' → k=3
'[' → push 3, push "", current="", k=0
'a' → current="a"
'2' → k=2
'[' → push 2, push "a", current="", k=0
'c' → current="c"
']' → pop count=2, pop prev="a", append "c"×2 → current="acc"
']' → pop count=3, pop prev="", append "acc"×3 → current="accaccacc"
Result: "accaccacc"
Complexity Analysis
Solution 1 (Two Stacks):
- Time: O(n × m) where n = input length, m = maximum repeat count
- Space: O(n) for stacks
Solution 3 (Recursive DFS):
- Time: O(n × m) worst case
- Space: O(d) recursion depth where d = nesting depth
Recommended Solutions
Best for Interviews: Solution 1
class Solution {
    public String decodeString(String s) {
        Stack<Integer> counts = new Stack<>();
        Stack<StringBuilder> result = new Stack<>();
        StringBuilder current = new StringBuilder();
        int k = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                counts.push(k);
                result.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                StringBuilder temp = current;
                current = result.pop();
                int count = counts.pop();
                for (int i = 0; i < count; i++) {
                    current.append(temp);
                }
            } else {
                current.append(c);
            }
        }
        
        return current.toString();
    }
}
Most Elegant: Solution 3 (Recursive)
class Solution {
    private int i = 0;
    
    public String decodeString(String s) {
        return decodeHelper(s);
    }
    
    private String decodeHelper(String s) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        
        while (i < s.length()) {
            char c = s.charAt(i++);
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            } else if (c == '[') {
                String inner = decodeHelper(s);
                for (int j = 0; j < num; j++) {
                    sb.append(inner);
                }
                num = 0;
            } else if (c == ']') {
                break;
            } else {
                sb.append(c);
            }
        }
        
        return sb.toString();
    }
}
Edge Cases
1.Nested brackets: "3[a2[c]]" → "accaccacc"
2.Multiple separate: "2[abc]3[cd]ef" → "abcabccdcdcdef"
3.No brackets: "abc" → "abc"
4.Empty brackets: "3[]" → ""
5.Large numbers: "100[a]" → "aaa..."
6.Single letter: "a" → "a"
Visual Representation
For "3[a2[c]]":
Parse: 3 [ a 2 [ c ] ]
        ↓ ↓ ↓ ↓ ↓ ↓ ↓
Stack: - 3 a 2 c build: acc, accaccacc
Processing flow:
Input: 3 [ a 2 [ c ] ]
Step1: Parse '3' → k=3
Step2: '[' → push 3, push "", reset
Step3: 'a' → current="a"
Step4: '2' → k=2
Step5: '[' → push 2, push "a", reset
Step6: 'c' → current="c"
Step7: ']' → pop 2, pop "a" → "a" + "c"×2 = "acc"
Step8: ']' → pop 3, pop "" → "" + "acc"×3 = "accaccacc"
Comparison with LeetCode 1190
Both problems involve nested structures:
- 1190: Reverse strings inside parentheses
- 394: Repeat strings inside brackets
Similarities:
- Both use stack/recursion to handle nesting
- Both process from innermost to outermost
- Both have O(n) optimal solutions with clever techniques
Differences:
- 394 needs to handle numbers (repeat counts)
- 1190 only needs to reverse, not repeat
- 394's repetition can create much longer outputs
Key Challenges
1.Multi-digit numbers: "100[a]" needs to parse 100, not just 1
2.Nested brackets: Need to handle arbitrary depth
3.Memory efficiency: Output can be exponentially longer than input
4.Order of operations: Process innermost brackets first
Summary
The stack-based solution (Solution 1) is most intuitive:
1.Use two stacks: one for repeat counts, one for partial strings
2.When you see '[': push current count and string, reset
3.When you see ']': pop, repeat current string, append to previous
4.Accumulate result as you go
Time: O(n × m), Space: O(n) where n = input length, m = max repeat count.

Q1: In DFS solution why in recursion helper method handling '[' branch we only have to reset number to 0, no need reset StringBuilder object ?
Excellent observation! You're right to question this. The key insight is understanding how the recursion manages state and scope.
The Short Answer
You don't need to reset StringBuilder curr because each recursive call creates its OWN StringBuilder.
When you make a recursive call helper(s), a new stack frame is created with fresh local variables curr and number. The reset happens automatically because they're new variables in a new scope.
Detailed Explanation: Scope and Recursion Stack
Let's trace through what happens:
1. Each Recursive Call Has Its Own Variables
private String helper(String s) {
    // FRESH VARIABLES for each call:
    StringBuilder curr = new StringBuilder();  // Brand new for this level
    int number = 0;                           // Brand new for this level
    
    while(index < s.length()) {
        // ...
    }
    return curr.toString();
}
When helper calls itself recursively:
- A new stack frame is created
- New curr and number variables are initialized
- The previous level's curr and number are preserved on the stack
2. Visual Example: "3[a2[c]]"
Let's trace the execution:
Initial call: helper("3[a2[c]]")
  curr = "", number = 0
  index=0: '3' → number=3
  index=1: '[' → RECURSIVE CALL (level 1)

Level 1 (inside first [):
  curr = "", number = 0  ← FRESH!
  index=2: 'a' → curr="a"
  index=3: '2' → number=2
  index=4: '[' → RECURSIVE CALL (level 2)

Level 2 (innermost):
  curr = "", number = 0  ← FRESH!
  index=5: 'c' → curr="c"
  index=6: ']' → break, return "c" to level 1

Back to Level 1:
  next = "c" (from level 2)
  For i=0 to 1: curr.append("c") → curr="acc"
  number = 0 (reset for potential more numbers)
  index=7: ']' → break, return "acc" to initial level

Back to Initial:
  next = "acc" (from level 1)
  For i=0 to 2: curr.append("acc") → curr="accaccacc"
  Return "accaccacc"
Why number = 0 But Not curr = new StringBuilder()?
number needs resetting:
- number accumulates digits BEFORE '['
- After processing "[...]", if there might be another number later in SAME level
- Example: "2[a]3[b]" - need number=0 after 'a' to read 3 for 'b'
curr doesn't need resetting:
- curr accumulates results FOR THIS LEVEL
- When you return from recursion, you want to CONTINUE appending to same curr
- curr represents the entire decoded string for this recursion level
What Would Happen If You Reset curr?
If you added curr = new StringBuilder() after processing '[':
if(c == '[') {
    String next = helper(s);
    for(int i = 0; i < number; i++) {
        curr.append(next);
    }
    number = 0;
    // WRONG: curr = new StringBuilder(); // This would lose previous content
}
Example with "a2[b]c":
- Before '[': curr = "a"
- Process "2[b]" → next = "bb"
- Append to curr: curr = "abb"
- If reset curr: loses "a"!
- After ']': curr.append("c") → would be just "c" instead of "abbc"
The Mental Model: "Scoped Builders"
Think of each recursive level having its own workspace:
- Level 0's workspace: Builds final result
- Level 1's workspace: Builds inner bracket content
- Level 2's workspace: Builds innermost bracket content
When Level 1 returns its result to Level 0, Level 0 continues building in its own workspace.
Comparison with Alternative Approach
If we wanted to avoid resetting number, we could structure differently:
private String helper(String s) {
    StringBuilder curr = new StringBuilder();
    
    while(index < s.length()) {
        char c = s.charAt(index);
        index++;
        
        if(Character.isDigit(c)) {
            // Parse full number
            int num = c - '0';
            while(Character.isDigit(s.charAt(index))) {
                num = num * 10 + (s.charAt(index) - '0');
                index++;
            }
            // Next char must be '['
            index++; // skip '['
            String inner = helper(s);
            for(int i = 0; i < num; i++) {
                curr.append(inner);
            }
        } else if(c == ']') {
            break;
        } else {
            curr.append(c);
        }
    }
    
    return curr.toString();
}
This approach reads the entire number before recursion, so no need to reset.
Key Recursion Pattern
This follows a classic pattern:
1.Accumulate prefix before '[' (in curr)
2.Recursively process inside '[...]'
3.Append repeated result to current curr
4.Continue accumulating after ']' (if any)
The curr StringBuilder represents the complete output for this recursion level.
Summary
VariableWhy Reset?Why Not Reset?numberNeed to clear for next number in same level-curr-It's accumulating ALL content for this level
The reset happens naturally through recursion:
- New recursive call = new StringBuilder
- Return from recursion = continue with existing StringBuilder
This is the elegance of recursion: each call gets its own clean state, and the call stack manages the scope automatically.


Refer to
L726.Number of Atoms (Ref.L394)
L1190.Reverse Substrings Between Each Pair of Parentheses (Ref.L856,L1021)
