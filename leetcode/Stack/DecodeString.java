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

