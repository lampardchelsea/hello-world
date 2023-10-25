import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Refer to
 * https://leetcode.com/problems/decode-string/#/description
 * Given an encoded string, return it's decoded string.
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets 
 * is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 * You may assume that the input string is always valid; No extra white spaces, 
 * square brackets are well-formed, etc.
 * Furthermore, you may assume that the original data does not contain any digits and that 
 * digits are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].

	Examples:
	s = "3[a]2[bc]", return "aaabcbc".
	s = "3[a2[c]]", return "accaccacc".
	s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 * 
 * 
 * Solution
 * https://discuss.leetcode.com/topic/57250/java-short-and-easy-understanding-solution-using-stack
 *
 * Note:
 * The idea very like
 * https://leetcode.com/problems/mini-parser/#/description
 * 
 * Follow up
 * https://discuss.leetcode.com/topic/57250/java-short-and-easy-understanding-solution-using-stack/6
 * 
    (1) I replaced Strings with StringBuilders to avoid recreating Strings due to immutability
    (2) To calc the multiplier, I used some fancy logic from @sampsonchan. See his solution.
    (3) Since Java Doc says not to use Stack anymore, I used a double-ended Q (Deque)
    (4) I used verbose variable names

   public static StringBuilder decode(String s) {
      // Stack is deprecated so using double-ended Q
      Deque<Integer> multipliers = new ArrayDeque<>();
      Deque<StringBuilder> result = new ArrayDeque<>();
      result.push(new StringBuilder());
      int multiplier = 0;

      // Would be nice to use an 'enhanced' for loop, but don't want
      // the expense of converting the String to an array (ie toCharArray)
      // for (char ch : s.toCharArray()) {
      for (int i = 0; i < s.length(); i++) {
         char ch = s.charAt(i);
         if (Character.isDigit(ch)) {
            multiplier = multiplier * 10 + ch - '0';
            multipliers.push(multiplier);
         } else if (ch == '[') {
            result.push(new StringBuilder());
            multiplier = 0; //reset
         } else if (ch == ']') {
            StringBuilder str2Multiply = result.pop();
            int times = multipliers.pop();
            StringBuilder multipliedStr = new StringBuilder();
            for (int j = 0; j < times; j += 1) {
               multipliedStr.append(str2Multiply);
            }
            result.push(result.pop().append(multipliedStr));
         } else {
            result.push(result.pop().append(ch));
         }
      }

      return result.pop();
   }
 *
 * Why should I use Deque over Stack?
 * https://stackoverflow.com/questions/12524826/why-should-i-use-deque-over-stack
 * Q: I need a Stack datastructure for my use case. I should be able to push items into the datastructure 
 *    and I only want to retrieve the last item from the Stack . The JavaDoc for Stack says :
 *    A more complete and consistent set of LIFO stack operations is provided by the Deque interface and 
 *    its implementations, which should be used in preference to this class. For example:
 *        Deque<Integer> stack = new ArrayDeque<>();
 *    I definitely do not want synchronized behavior here as I will be using this datastructure local to 
 *    a method . Apart from this why should I prefer Deque over Stack here ?
 *    P.S: The javadoc from Deque says :
 *    Deques can also be used as LIFO (Last-In-First-Out) stacks. This interface should be used in 
 *    preference to the legacy Stack class.
 *    
 * A: For one thing, it's more sensible in terms of inheritance. The fact that Stack extends Vector is 
 *    really strange, in my view. Early in Java, inheritance was overused IMO - Properties being another 
 *    example.
 *    For me, the crucial word in the docs you quoted is consistent. Deque exposes a set of operations 
 *    which is all about being able to fetch/add/remove items from the start or end of a collection, 
 *    iterate etc - and that's it. There's deliberately no way to access an element by position, which 
 *    Stack exposes because it's a subclass of Vector.
 *    Oh, and also Stack has no interface, so if you know you need Stack operations you end up committing 
 *    to a specific concrete class, which isn't usually a good idea.
 */
public class DecodeString {
    public String decodeString(String s) {
        Stack<Integer> count = new Stack<Integer>();
        // The evolution process of result stack
        // Don't forget the initial push of empty string and push empty string when encounter '['
        // E.g s = "3[a]2[bc]"
        //  0         1            2          3        4           5               6                7             8            9
        // [""] -> ["", ""] -> ["", "a"] -> [""] -> ["aaa"] -> ["aaa", ""] -> ["aaa", "b"] -> ["aaa", "bc"] -> ["aaa"] -> ["aaabcbc"]
        // initial push of empty string --> step 0
        // push empty string when encounter '[' --> step 1, 5
        Stack<String> result = new Stack<String>();
        int i = 0;
        // If not initial result stack with empty string
        // will throw java.util.EmptyStackException
        // because we try to pop out first when encounter ']'
        // E.g s = "3[a]2[bc]"
        result.push("");
        while(i < s.length()) {
            // Find continuous number if exist
            char ch = s.charAt(i);
            if(ch >= '0' && ch <= '9') {
                int start = i;
                while(s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    i++;
                }
                int currCount = Integer.parseInt(s.substring(start, i + 1));
                count.push(currCount);
            } else if(ch == '[') {
                // If not initial result stack with empty string
                // will throw java.util.EmptyStackException
                // because we try to pop out first when encounter '['
                // E.g s = "3[a]2[bc]"
                result.push("");
            } else if(ch == ']') {
                String str = result.pop();
                StringBuilder sb = new StringBuilder();
                int times = count.pop();
                for(int j = 0; j < times; j++) {
                    sb.append(str);
                }
                result.push(result.pop() + sb.toString());
            } else {
                result.push(result.pop() + ch);
            }
            i++;
        }
        return result.pop();
    }
    
    /**
     * Refer to
     * https://discuss.leetcode.com/topic/57250/java-short-and-easy-understanding-solution-using-stack/6
     * (1) I replaced Strings with StringBuilders to avoid recreating Strings due to immutability
     * (2) To calc the multiplier, I used some fancy logic from @sampsonchan. See his solution.
     * (3) Since Java Doc says not to use Stack anymore, I used a double-ended Q (Deque)
     * (4) I used verbose variable names
     */
    public String decodeString2(String s) {
        // return result.pop();
        Deque<Integer> count = new ArrayDeque<Integer>();
        Deque<StringBuilder> result = new ArrayDeque<StringBuilder>();
        int i = 0;
        result.push(new StringBuilder());
        while(i < s.length()) {
            char ch = s.charAt(i);
            if(ch >= '0' && ch <= '9') {
                int start = i;
                while(s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    i++;
                }
                int number = Integer.parseInt(s.substring(start, i + 1));
                count.push(number);
            } else if(ch == '[') {
                result.push(new StringBuilder());
            } else if(ch == ']') {
                int times = count.pop();
                StringBuilder sb = new StringBuilder();
                StringBuilder str = result.pop();
                for(int j = 0; j < times; j++) {
                    sb.append(str);
                }
                result.push(result.pop().append(sb));
            } else {
                result.push(result.pop().append(ch));
            }
            i++;
        }
        return result.pop().toString();
    }
    
    
    
    public static void main(String[] args) {
    	DecodeString d = new DecodeString();
    	String s = "3[a]2[bc]";
    	String result = d.decodeString(s);
    	System.out.println(result);
    }
}











































































































https://leetcode.com/problems/decode-string/

Given an encoded string, return its decoded string.

The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is being repeated exactly k times. Note that k is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, k. For example, there will not be input like 3a or 2[4].

The test cases are generated so that the length of the output will never exceed 105.

Example 1:
```
Input: s = "3[a]2[bc]"
Output: "aaabcbc"
```

Example 2:
```
Input: s = "3[a2[c]]"
Output: "accaccacc"
```

Example 3:
```
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"
```

Constraints:
- 1 <= s.length <= 30
- s consists of lowercase English letters, digits, and square brackets '[]'.
- s is guaranteed to be a valid input.
- All the integers in s are in the range [1, 300].
---
Attempt 1: 2023-10-22

Solution 1: Two Stacks (120 min)

Style 1: We must create new StringBuilder object 'sb' for each level of brackets, don't use 'sb.setLength(0)'
```
class Solution {
    public String decodeString(String s) {
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> resultStack = new Stack<>();
        int num = 0;
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
```

Style 2: A more readable way
```
class Solution {
    public String decodeString(String s) {
        Stack<Integer> intStack = new Stack<>();
        Stack<StringBuilder> resultStack = new Stack<>();
        int num = 0;
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
```

Refer to
https://leetcode.com/problems/decode-string/solutions/87534/simple-java-solution-using-stack/comments/92413
```
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
```

Refer to
https://grandyang.com/leetcode/394/
我们也可以用迭代的方法写出来，当然需要用 stack 来辅助运算，我们用两个 stack，一个用来保存个数，一个用来保存字符串，我们遍历输入字符串，如果遇到数字，我们更新计数变量 cnt；如果遇到左括号，我们把当前 cnt 压入数字栈中，把当前t压入字符串栈中；如果遇到右括号时，我们取出数字栈中顶元素，存入变量k，然后给字符串栈的顶元素循环加上k个t字符串，然后取出顶元素存入字符串t中；如果遇到字母，我们直接加入字符串t中即可，参见代码如下：
```
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
```

---
Solution 2: Recursion (120 min)

Style 1: For loop
```
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
```

Style 2: While loop
```
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
```

Refer to
https://grandyang.com/leetcode/394/
这道题让我们把一个按一定规则编码后的字符串解码成其原来的模样，编码的方法很简单，就是把重复的字符串放在一个括号里，把重复的次数放在括号的前面，注意括号里面有可能会嵌套括号，这题可以用递归和迭代两种方法来解，我们首先来看递归的解法，把一个括号中的所有内容看做一个整体，一次递归函数返回一对括号中解码后的字符串。给定的编码字符串实际上只有四种字符，数字，字母，左括号，和右括号。那么我们开始用一个变量i从0开始遍历到字符串的末尾，由于左括号都是跟在数字后面，所以首先遇到的字符只能是数字或者字母，如果是字母，直接存入结果中，如果是数字，循环读入所有的数字，并正确转换，那么下一位非数字的字符一定是左括号，指针右移跳过左括号，对之后的内容调用递归函数求解，注意我们循环的停止条件是遍历到末尾和遇到右括号，由于递归调用的函数返回了子括号里解码后的字符串，而我们之前把次数也已经求出来了，那么循环添加到结果中即可，参见代码如下：
```
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
```

https://leetcode.com/problems/decode-string/solutions/738090/c-recursion-and-short/
Four cases are considered. 1st when we encounter a [. 2nd when we encounter a digit. 3rd encountering a ]. and last simple words. Please note that index i is passed by reference not by value.
```
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
```

https://leetcode.com/problems/decode-string/solutions/1634874/java-dfs-0ms-easy-understanding/
The idea is: for the string in [ ] pair, we can recurse it as the source string. The codes go into the lower level at '[', and back to parent at ']'.
The int pos is a globe parameter to indicate the current index of char in the String s.
```
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
```
