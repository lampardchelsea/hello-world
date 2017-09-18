/**
 * Refer to
 * http://www.lintcode.com/en/problem/expression-expand/
 * https://leetcode.com/problems/decode-string/description/
 * Given an encoded string, return it's decoded string.
   The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets 
   is being repeated exactly k times. Note that k is guaranteed to be a positive integer.
   You may assume that the input string is always valid; No extra white spaces, square brackets are well-formed, etc.
   Furthermore, you may assume that the original data does not contain any digits and that digits 
   are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
   
    Examples:
    s = "3[a]2[bc]", return "aaabcbc".
    s = "3[a2[c]]", return "accaccacc".
    s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
    
 * Solution
 * Style 1
 * http://www.cnblogs.com/Dylan-Java-NYC/p/6751689.html
 * Style 2
 * https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/DecodeString.java
 * Style 3
 * https://segmentfault.com/a/1190000008883991
*/
// Style 1



// Style 2
// Refer to
// https://github.com/lampardchelsea/hello-world/blob/master/leetcode/Stack/DecodeString.java
public class Solution {
    /*
     * @param s: an expression includes numbers, letters and brackets
     * @return: a string
     */
    public String expressionExpand(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        Stack<Integer> countStack = new Stack<Integer>();
        Stack<String> result = new Stack<String>();
        result.push("");
        int i = 0;
        while(i < s.length()) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                int val = 0;
                int start = i;
                while(s.charAt(i + 1) >= '0' && s.charAt(i + 1) <= '9') {
                    i++;
                }
                int currCount = Integer.parseInt(s.substring(start, i + 1));
                countStack.push(currCount);
            } else if(c == '[') {
                result.push("");
            } else if(Character.isLetter(c)) {
                result.push(result.pop() + c);
            } else if(c == ']') {
                StringBuilder sb = new StringBuilder();
                int times = countStack.pop();
                String str = result.pop();
                for(int j = 0; j < times; j++) {
                    sb.append(str);
                }
                result.push(result.pop() + sb.toString());
            }
            i++;
        }
        return result.pop();
    }
}


// Style 3 (Template from JiuZhang)
// Refer to
// https://segmentfault.com/a/1190000008883991
/**
   这道题真是超级棒的stack DFS样板题啊，在这里给自己写个小小的总结

   思路：
   想到stack并不难，这种嵌套式一般是DFS的思想，先走到最里面最小的那个括号，然后逐渐回到上一层→上一层。
   又∵非递归，“BFS queue， DFS stack”。想到用stack并不难
   Stack non-recursion DFS template
   要点是，处理完之后重新返回stack，才能够回到上一层操作

   这个题具体操作起来真是很多可圈可点的地方，主要是在于String的处理上

   1.reverse
   因为stack的顺序，在这个题中需要每次将每层里的内容reverse。直接StringBuilder的reverse方法不可取：
   因为是reverse每一层。e.g. 3[ab]2[c]层直接从stack取出实际上是cc, ababab将这个reverse后应该得到
   abababcc。这个时候考虑逆向stack，建立一个stack buffer，将stack pop出来的东西再reverse一个顺序，
   逆逆得顺
   
   2.instanceof
   nstanceof是一个很好用的操作符，a instanceof A,判断“一个对象是否是一个类的实例”。作为操作符
   instanceof不可以直接在最前面！取非（比如>=这种也是），而是用 a instanceof A == false之类的判断
   
   3.复制StringBuilder
   add到底append几次，怎么append：直接append add 是不可以的，因为add是在变的，必须要先将第一个add
   保存起来，类似于dummy node，预先保存queue size这种“锚定”
*/
public class Solution {
    /*
     * @param s: an expression includes numbers, letters and brackets
     * @return: a string
     */
    public String expressionExpand(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        Stack<Object> stack = new Stack<Object>();
        int val = 0;
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(Character.isDigit(c)) {
                int start = i;
                while(Character.isDigit(s.charAt(i + 1))) {
                    i++;
                }
                val = Integer.parseInt(s.substring(start, i + 1));
            } else if(c == '[') {
                stack.push(Integer.valueOf(val));
                val = 0;
            } else if(c == ']') {
                String str = popStack(stack);
                Integer times = (Integer)stack.pop();
                while(times-- > 0) {
                    stack.push(str);
                }
            } else if(Character.isLetter(c)) {
                stack.push(String.valueOf(c));
            }
        }
        return popStack(stack);
    }
    
    private String popStack(Stack stack) {
        // We create a buffer to reverse the input string order
        // which will return as original order, because when we
        // push on given stack, string order reverse once, now
        // we reverse twice to get it back to normal
        Stack<String> buffer = new Stack<String>();
        // pop stack until get a number of empty
        while(!stack.isEmpty() && stack.peek() instanceof String) {
            buffer.push((String)stack.pop());
        }
        StringBuilder sb = new StringBuilder();
        while(!buffer.isEmpty()) {
            sb.append(buffer.pop());
        }
        return sb.toString();
    }
}






